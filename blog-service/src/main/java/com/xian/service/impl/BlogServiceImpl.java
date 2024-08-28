package com.xian.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xian.common.constants.RedisConstants;
import com.xian.common.constants.commonConstants;
import com.xian.common.enums.LikesModuleEnum;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.common.enums.RoleEnum;
import com.xian.common.exception.CustomException;
import com.xian.mapper.BlogMapper;
import com.xian.model.behavior.pojo.Collect;
import com.xian.model.behavior.pojo.Likes;
import com.xian.model.blog.pojo.Blog;
import com.xian.model.role.pojo.Account;
import com.xian.model.role.pojo.User;
import com.xian.service.BlogService;
import com.xian.service.CollectService;
import com.xian.service.CommentService;
import com.xian.service.LikesService;
import com.xian.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 博客信息业务处理
 **/
@Service
@Slf4j
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private LikesService likesService;

    @Resource
    private CollectService collectService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommentService commentService;


    /**
     * 新增
     */
    public void add(Blog blog) {
        blog.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        blogMapper.insert(blog);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        // 删除博客的点赞数据
        likesService.deleteAllBlogLikes(id);
//        删除博客的收藏数据
        collectService.deleteAllBlogCollect(id);
//      删除评论数据
        commentService.deleteAllBlogComment(id);
        blogMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            blogMapper.deleteById(id);
        }
    }

    /**
     * 更新
     * 更新数据库和更新缓存同时完成，添加事务注解开启事务
     *
     * @return
     */
    @Transactional
    public boolean updateById(Blog blog) {
        Integer id = blog.getId();
        if (id == null) {
            throw new CustomException("300", "id不能为空");
        }
//        1、更新数据库
        blogMapper.updateById(blog);
//        2、 删除缓存
        stringRedisTemplate.delete(RedisConstants.CACHE_BLOG_KEY + id);
        return true;
    }

    /**
     * 根据ID查询
     */
    public Blog selectById(Integer id) {
//      缓存穿透
//        Blog blog = queryWithPassThrough(id);
//        调用工具类解决缓存穿透
//        Blog blog= redisCache.getWithPassThrough(RedisConstants.CACHE_BLOG_KEY,id,Blog.class,this::getById,RedisConstants.CACHE_BLOG_TTL,TimeUnit.SECONDS);

//        互斥锁解决缓存击穿
        Blog blog = queryWithMutex(id);
//        Blog blog = redisCache.getWithMutex(RedisConstants.CACHE_BLOG_KEY,id,Blog.class,this::selectById,20L,TimeUnit.SECONDS);
//        Blog blog = redisCache.getWithLogicalExpire(RedisConstants.CACHE_BLOG_KEY,id,Blog.class,this::selectById,20L,TimeUnit.SECONDS);

        if (blog == null) {
            return null;
        }
//        设置博客其他信息
        User user = userService.selectById(blog.getUserId());
        if (user == null) {
            user = new User();
            user.setAvatar(commonConstants.USER_DEFAULT_AVATAR);
            user.setUsername(commonConstants.USER_DEFAULT_USERNAME);
            user.setName(commonConstants.USER_DEFAULT_USERNAME);
        }
        List<Blog> userBlogList = blogMapper.selectUserBlog(user.getId());
        user.setBlogCount(userBlogList.size());
        //  当前用户收到的点赞和收藏的数据
        int userLikesCount = 0;
        int userCollectCount = 0;
        for (Blog b : userBlogList) {
            Integer fid = b.getId();
            int likesCount = likesService.selectByFidAndModule(fid, LikesModuleEnum.BLOG.getValue());
            userLikesCount += likesCount;

            int collectCount = collectService.selectByFidAndModule(fid, LikesModuleEnum.BLOG.getValue());
            userCollectCount += collectCount;
        }
        user.setLikesCount(userLikesCount);
        user.setCollectCount(userCollectCount);


        blog.setUser(user);  // 设置作者信息
        // 查询当前博客的点赞数据
        int likesCount = likesService.selectByFidAndModule(id, LikesModuleEnum.BLOG.getValue());
        blog.setLikesCount(likesCount);
        Likes userLikes = likesService.selectUserLikes(id, LikesModuleEnum.BLOG.getValue());
        blog.setUserLike(userLikes != null);

        // 查询当前博客的收藏数据
        int collectCount = collectService.selectByFidAndModule(id, LikesModuleEnum.BLOG.getValue());
        blog.setCollectCount(collectCount);
        Collect userCollect = collectService.selectUserCollect(id, LikesModuleEnum.BLOG.getValue());
        blog.setUserCollect(userCollect != null);

        return blog;
    }

    /**
     * 互斥锁解决缓存击穿
     *
     * @param id
     * @return
     */
    public Blog queryWithMutex(Integer id) {
        //        使用redis缓存做查询
        String key = RedisConstants.CACHE_BLOG_KEY + id;
//        1、从redis中查询博客缓存
        String blogJson = stringRedisTemplate.opsForValue().get(key);
        Blog blog;
//        2、判断是否存在
        if (StrUtil.isNotBlank(blogJson)) {
            //        3、如果命中 则返回
            blog = JSONUtil.toBean(blogJson, Blog.class);
            log.info("在缓存中查询到博客++++++++++++++++++++：" + blog.getId());
        } else {
//            判断是不是空数据 解决缓存穿透问题
            if (blogJson != null) {
                throw new CustomException(ResultCodeEnum.BLOG_NOT_EXIST_ERROR);
            }
            //  实现缓存重建
//            获取互斥锁
            String lockKey = RedisConstants.LOCK_BLOG_KEY + id;
            try {
                boolean isLock = tryLock(lockKey);
//            判断是否成功
                if (!isLock) {
//            失败则休眠并重试
                    Thread.sleep(50);
//                    queryWithMutex(id);
                }
//        4、未命中， 去查询数据库
                blog = blogMapper.selectById(id);
//        5、数据库不存在 返回错误
                if (blog == null) {
//                如果为空 就缓存空数据
                    stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                    throw new CustomException(ResultCodeEnum.BLOG_NOT_EXIST_ERROR);
                }
//        6、数据库存在 写缓存 并返回
                stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(blog), RedisConstants.CACHE_BLOG_TTL, TimeUnit.MINUTES);
                log.info("在shujuku中查询到博客++++++++++++++++++++：" + blog.getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
//            释放互斥锁
                unLock(lockKey);
            }

        }
        return blog;
    }


    /**
     * 缓存穿透
     *
     * @param id
     * @return
     */
    public Blog queryWithPassThrough(Integer id) {
        //        使用redis缓存做查询
        String key = RedisConstants.CACHE_BLOG_KEY + id;
//        1、从redis中查询博客缓存
        String blogJson = stringRedisTemplate.opsForValue().get(key);
        Blog blog;
//        2、判断是否存在
        if (StrUtil.isNotBlank(blogJson)) {
            //        3、如果命中 则返回
            blog = JSONUtil.toBean(blogJson, Blog.class);
            log.info("在缓存中查询到博客++++++++++++++++++++：" + blog.getId());
        } else {
//            判断是不是空数据 解决缓存穿透问题
            if (blogJson != null) {
                throw new CustomException(ResultCodeEnum.BLOG_NOT_EXIST_ERROR);
            }

//        4、未命中， 去查询数据库
            blog = blogMapper.selectById(id);
//        5、数据库不存在 返回错误
            if (blog == null) {
//                如果为空 就缓存空数据
                stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                throw new CustomException(ResultCodeEnum.BLOG_NOT_EXIST_ERROR);
            }
//        6、数据库存在 写缓存 并返回
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(blog), RedisConstants.CACHE_BLOG_TTL, TimeUnit.MINUTES);
            log.info("在shujuku中查询到博客++++++++++++++++++++：" + blog.getId());
        }
        return blog;
    }

    /**
     * 尝试获取锁
     *
     * @param key
     * @return
     */
    private boolean tryLock(String key) {
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(aBoolean);

    }

    /**
     * 释放锁
     *
     * @param key
     */
    private void unLock(String key) {
        stringRedisTemplate.delete(key);
    }


    /**
     * 查询所有
     */
    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    /**
     * 分页查询
     */
    public PageInfo<Blog> selectPage(Blog blog, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectAll(blog);
        for (Blog b : list) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return PageInfo.of(list);
    }

    public List<Blog> selectTop() {
        List<Blog> blogList = this.selectAll(null);
        blogList = blogList.stream().sorted((b1, b2) -> b2.getReadCount().compareTo(b1.getReadCount()))
                .limit(20)
                .collect(Collectors.toList());
        return blogList;
    }


    public Set<Blog> selectRecommend(Integer blogId) {
        Blog blog = blogMapper.selectById(blogId);
        String tags = blog.getTags();
        Set<Blog> blogSet = new HashSet<>();
        if (ObjectUtil.isNotEmpty(tags)) {
            List<Blog> blogList = this.selectAll(null);
            JSONArray tagsArr = JSONUtil.parseArray(tags);
            for (Object tag : tagsArr) {
                // 筛选出包含当前博客标签的其他的博客列表
                Set<Blog> collect = blogList.stream().filter(b -> b.getTags().contains(tag.toString()) && !blogId.equals(b.getId()))
                        .collect(Collectors.toSet());
                blogSet.addAll(collect);
            }
        }
        blogSet = blogSet.stream().limit(5).collect(Collectors.toSet());
        blogSet.forEach(b -> {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        });
        return blogSet;
    }

    /**
     * 查询用户发布的博客
     *
     * @param id
     * @return
     */
    @Override
    public List<Blog> getByUserId(Integer id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        return blogMapper.selectList(wrapper);
    }

    public void updateReadCount(Integer blogId) {
        blogMapper.updateReadCount(blogId);
    }

    public PageInfo<Blog> selectUser(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        return this.selectPage(blog, pageNum, pageSize);
    }

    // 查询用户点赞的数据
    public PageInfo<Blog> selectLike(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectLike(blog);
        PageInfo<Blog> pageInfo = PageInfo.of(list);
        List<Blog> blogList = pageInfo.getList();
        for (Blog b : blogList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }


    public PageInfo<Blog> selectCollect(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectCollect(blog);
        PageInfo<Blog> pageInfo = PageInfo.of(list);
        List<Blog> blogList = pageInfo.getList();
        for (Blog b : blogList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }


    public PageInfo<Blog> selectComment(Blog blog, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            blog.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> list = blogMapper.selectComment(blog);
        PageInfo<Blog> pageInfo = PageInfo.of(list);
        List<Blog> blogList = pageInfo.getList();
        for (Blog b : blogList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.BLOG.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }

}
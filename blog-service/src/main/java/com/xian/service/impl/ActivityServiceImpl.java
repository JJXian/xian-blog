package com.xian.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.common.result.Result;
import com.xian.model.activity.dtos.ActivityDTO;
import com.xian.model.activity.pojo.Activity;
import com.xian.model.activity.pojo.ActivitySign;
import com.xian.model.behavior.pojo.Collect;
import com.xian.model.behavior.pojo.Likes;
import com.xian.common.enums.LikesModuleEnum;
import com.xian.common.enums.RoleEnum;
import com.xian.mapper.ActivityMapper;
import com.xian.model.role.pojo.Account;
import com.xian.service.ActivityService;
import com.xian.service.CommentService;
import com.xian.service.LikesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xian.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动业务处理
 **/
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper,Activity> implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivitySignServiceImpl activitySignService;

    @Resource
    private LikesService likesService;

    @Autowired
    private CollectServiceImpl collectService;

    @Autowired
    private CommentService commentService;

    /**
     * 新增
     */
    public Result add(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO,activity);
        activityMapper.insert(activity);
        return Result.success();
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
//      删除活动相关的点赞和评论和收藏
        likesService.deleteAllBlogLikes(id);
        collectService.deleteAllBlogCollect(id);
        commentService.deleteAllBlogComment(id);
//        同时删除报名信息
        activitySignService.deleteAllSign(id);
        activityMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            activityMapper.deleteById(id);
        }
    }

    /**
     * 修改
     *
     * @return
     */
    public boolean updateById(Activity activity) {

        log.info(activity.getContent());
        activityMapper.updateById(activity);
        return true;
    }

    /**
     * 根据ID查询
     */
    public Result selectById(Integer id) {
        Activity activity = activityMapper.selectById(id);

        this.setAct(activity, TokenUtils.getCurrentUser());

        int likesCount = likesService.selectByFidAndModule(id, LikesModuleEnum.ACTIVITY.getValue());
        int collectCount = collectService.selectByFidAndModule(id, LikesModuleEnum.ACTIVITY.getValue());
        activity.setLikesCount(likesCount);
        activity.setCollectCount(collectCount);

        Likes likes = likesService.selectUserLikes(id, LikesModuleEnum.ACTIVITY.getValue());
        activity.setIsLike(likes != null);

        Collect collect = collectService.selectUserCollect(id, LikesModuleEnum.ACTIVITY.getValue());
        activity.setIsCollect(collect != null);
        return Result.success(activity);
    }

    /**
     * 查询所有
     */
    public List<Activity> selectAll(Activity activity) {
        return activityMapper.selectAll(activity);
    }

    /**
     * 分页查询
     */
    public PageInfo<Activity> selectPage(Activity activity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectAll(activity);
        PageInfo<Activity> pageInfo = PageInfo.of(list);
        List<Activity> activityList = pageInfo.getList();
        Account currentUser = TokenUtils.getCurrentUser();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    // 设置活动额外信息
    private void setAct(Activity act, Account currentUser) {
        act.setIsEnd(DateUtil.parseDate(act.getEnd()).isBefore(new Date()));  // 活动的结束时间在当前时间之前  就表示活动结束了
        ActivitySign activitySign = activitySignService.selectByActivityIdAndUserId(act.getId(), currentUser.getId());
        act.setIsSign(activitySign != null);
    }

    /**
     * 热门活动
     */
    public List<Activity> selectTop() {
        List<Activity> activityList = this.selectAll(null);
        activityList = activityList.stream().sorted((b1, b2) -> b2.getReadCount().compareTo(b1.getReadCount()))
                .limit(2)
                .collect(Collectors.toList());
        return activityList;
    }

    public void updateReadCount(Integer activityId) {
        activityMapper.updateReadCount(activityId);
    }

    // 查询出用户报名的活动列表
    public PageInfo<Activity> selectUser(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectUser(activity);
        PageInfo<Activity> pageInfo = PageInfo.of(list);
        List<Activity> activityList = pageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    public PageInfo<Activity> selectLike(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectLike(activity);
        PageInfo<Activity> pageInfo = PageInfo.of(list);
        List<Activity> activityList = pageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    public PageInfo<Activity> selectCollect(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectCollect(activity);
        PageInfo<Activity> pageInfo = PageInfo.of(list);
        List<Activity> activityList = pageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

    public PageInfo<Activity> selectComment(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            activity.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectComment(activity);
        PageInfo<Activity> pageInfo = PageInfo.of(list);
        List<Activity> activityList = pageInfo.getList();
        for (Activity act : activityList) {
            this.setAct(act, currentUser);
        }
        return pageInfo;
    }

}
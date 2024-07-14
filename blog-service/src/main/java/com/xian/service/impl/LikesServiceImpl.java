package com.xian.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.mapper.CollectMapper;
import com.xian.model.role.pojo.Account;
import com.xian.model.behavior.pojo.Likes;
import com.xian.mapper.LikesMapper;
import com.xian.service.LikesService;
import com.xian.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class LikesServiceImpl extends ServiceImpl<LikesMapper,Likes> implements LikesService {

    @Resource
    LikesMapper likesMapper;



    public void set(Likes likes) {
        Account currentUser = TokenUtils.getCurrentUser();
        likes.setUserId(currentUser.getId());
        Likes dblLikes = likesMapper.selectUserLikes(likes);
        if (dblLikes == null) {
            likesMapper.insert(likes);
        } else {
            likesMapper.deleteById(dblLikes.getId());
        }

    }

    /**
     * 删除博客的所有点赞信息
     * @param fid 关联的博客/活动id
     */
    public void deleteAllBlogLikes(Integer fid){
//        likesMapper.deleteByFid(fid);
        QueryWrapper<Likes> wrapper  = new QueryWrapper<>();
        wrapper.eq("fid",fid);
        likesMapper.delete(wrapper);
    }

    /**
     * 查询当前用户是否点过赞
     */
    public Likes selectUserLikes(Integer fid, String module) {
        Account currentUser = TokenUtils.getCurrentUser();
        Likes likes = new Likes();
        likes.setUserId(currentUser.getId());
        likes.setFid(fid);
        likes.setModule(module);
        return likesMapper.selectUserLikes(likes);
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }

}

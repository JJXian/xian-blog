package com.xian.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.entity.*;
import com.xian.enums.LikesModuleEnum;
import com.xian.enums.RoleEnum;
import com.xian.mapper.ActivityMapper;
import com.xian.service.ActivityService;
import com.xian.service.LikesService;
import com.xian.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动业务处理
 **/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper,Activity> implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    ActivitySignServiceImpl activitySignService;

    @Resource
    LikesService likesService;

    @Resource
    CollectServiceImpl collectService;

    /**
     * 新增
     */
    public void add(Activity activity) {
        activityMapper.insert(activity);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
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
        activityMapper.updateById(activity);
        return false;
    }

    /**
     * 根据ID查询
     */
    public Activity selectById(Integer id) {
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
        return activity;
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
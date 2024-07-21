package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xian.common.result.Result;
import com.xian.model.activity.dtos.ActivityDTO;
import com.xian.model.activity.pojo.Activity;

import java.util.List;

public interface ActivityService extends IService<Activity> {
    Result add(ActivityDTO activityDTO);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    boolean updateById(Activity activity);

    void updateReadCount(Integer activityId);

    Result selectById(Integer id);

    List<Activity> selectAll(Activity activity);

    PageInfo<Activity> selectPage(Activity activity, Integer pageNum, Integer pageSize);

    PageInfo<Activity> selectUser(Activity activity, Integer pageNum, Integer pageSize);

    PageInfo<Activity> selectLike(Activity activity, Integer pageNum, Integer pageSize);

    PageInfo<Activity> selectCollect(Activity activity, Integer pageNum, Integer pageSize);

    PageInfo<Activity> selectComment(Activity activity, Integer pageNum, Integer pageSize);

    List<Activity> selectTop();
}

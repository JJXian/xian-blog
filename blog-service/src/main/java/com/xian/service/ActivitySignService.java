package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.activity.pojo.ActivitySign;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ActivitySignService extends IService<ActivitySign> {
    void add(ActivitySign activitySign);

    void deleteById(Integer id);

    void userDelete(Integer activityId, Integer useId);

    void deleteBatch(List<Integer> ids);

    PageInfo<ActivitySign> selectPage(ActivitySign activitySign, Integer pageNum, Integer pageSize);
}

package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.common.result.Result;
import com.xian.model.activity.dtos.ActivitySignDTO;
import com.xian.model.activity.pojo.ActivitySign;
import com.github.pagehelper.PageInfo;

import java.awt.geom.RectangularShape;
import java.util.List;

public interface ActivitySignService extends IService<ActivitySign> {
    Result add(ActivitySignDTO activitySignDTO);

    void deleteById(Integer id);

    void userDelete(Integer activityId, Integer useId);

    void deleteBatch(List<Integer> ids);

    PageInfo<ActivitySign> selectPage(ActivitySign activitySign, Integer pageNum, Integer pageSize);

    void deleteAllSign(Integer id);

}

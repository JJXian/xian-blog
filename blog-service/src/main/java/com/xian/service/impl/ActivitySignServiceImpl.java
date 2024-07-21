package com.xian.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.common.result.Result;
import com.xian.model.activity.dtos.ActivitySignDTO;
import com.xian.model.role.pojo.Account;
import com.xian.model.activity.pojo.ActivitySign;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.common.exception.CustomException;
import com.xian.mapper.ActivitySignMapper;
import com.xian.service.ActivitySignService;
import com.xian.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ActivitySignServiceImpl  extends ServiceImpl<ActivitySignMapper,ActivitySign> implements ActivitySignService {

    @Resource
    ActivitySignMapper activitySignMapper;

    /**
     * 活动报名
     * @param activitySignDTO
     * @return
     */
    public Result add(ActivitySignDTO activitySignDTO) {
        ActivitySign activitySign = new ActivitySign();
        BeanUtils.copyProperties(activitySignDTO,activitySign);

        Account currentUser = TokenUtils.getCurrentUser();
        ActivitySign as = this.selectByActivityIdAndUserId(activitySign.getActivityId(), currentUser.getId());  // 查看用户是否已经报名
        if (as != null) {
//            throw new CustomException(ResultCodeEnum.ACTIVITY_SIGN_ERROR);
            return Result.error(ResultCodeEnum.ACTIVITY_SIGN_ERROR);
        }
        activitySign.setUserId(currentUser.getId());
        activitySign.setTime(DateUtil.now());
        activitySignMapper.insert(activitySign);
        return Result.success();
    }

    public ActivitySign selectByActivityIdAndUserId(Integer actId, Integer userId) {
        return activitySignMapper.selectByActivityIdAndUserId(actId, userId);
    }

    public PageInfo<ActivitySign> selectPage(ActivitySign activitySign, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivitySign> list = activitySignMapper.selectAll(activitySign);
        return PageInfo.of(list);
    }

    /**
     * 根据id删除报名信息
     * @param id
     */
    public void deleteById(Integer id) {
        activitySignMapper.deleteById(id);
    }

    /**
     * 删除活动的时候同时删除所有的报名信息
     * @param id
     */
    public void deleteAllSign(Integer id){
        QueryWrapper<ActivitySign> wrapper = new QueryWrapper<>();
        wrapper.eq("activity_id",id);
        activitySignMapper.delete(wrapper);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public void userDelete(Integer activityId, Integer useId) {
        activitySignMapper.userDelete(activityId, useId);
    }
}

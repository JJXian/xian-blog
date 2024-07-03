package com.xian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xian.activity.pojo.ActivitySign;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ActivitySignMapper extends BaseMapper<ActivitySign> {

//    @Insert("insert into activity_sign ( activity_id, user_id, time ) values ( #{activityId}, #{userId}, #{time})")
//    void insert(ActivitySign activitySign);

    @Select("select * from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    ActivitySign selectByActivityIdAndUserId(@Param("activityId") Integer actId, @Param("userId") Integer userId);

//    @Select("select activity_sign.*, activity.name as activityName, user.name as userName from activity_sign " +
//            "left join activity on activity_sign.activity_id = activity.id " +
//            "left join user on activity_sign.user_id = user.id " +
//            "order by id desc")
    List<ActivitySign> selectAll(ActivitySign activitySign);

//    @Delete("delete from activity_sign where id = #{id}")
//    void deleteById(Integer id);

    @Delete("delete from activity_sign where activity_id = #{activityId} and user_id = #{userId}")
    void userDelete(@Param("activityId") Integer activityId,  @Param("userId") Integer useId);
}

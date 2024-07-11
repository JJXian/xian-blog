package com.xian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xian.model.role.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

//    void insert(User user);

    User selectByUsername(String username);

//    void deleteById(Integer id);

    int updateById(User user);

//    User selectById(Integer id);

    List<User> selectAll(User user);

    @Select("select * from user where email = #{email}")
    User selectByEmail(String email);

}

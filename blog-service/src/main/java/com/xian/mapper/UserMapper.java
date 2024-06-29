package com.xian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xian.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{

//    void insert(User user);

    User selectByUsername(String username);

//    void deleteById(Integer id);

    int updateById(User user);

//    User selectById(Integer id);

    List<User> selectAll(User user);

}

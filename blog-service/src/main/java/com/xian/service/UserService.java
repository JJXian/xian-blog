package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService extends IService<User> {

    void add(User user);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    User selectById(Integer id);

    List<User> selectAll(User user);

    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    boolean updateById(User user);
}

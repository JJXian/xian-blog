package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.common.result.Result;
import com.xian.model.role.pojo.Account;
import com.xian.model.role.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface UserService extends IService<User> {

    Result add(User user);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    User selectById(Integer id);

    List<User> selectAll(User user);

    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    Result updateByUser(User user);

    Account login(Account account);

    void register(Account account);

    void updatePassword(Account account);

//    boolean updateById(User user);
}

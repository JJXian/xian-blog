package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.entity.Account;
import com.xian.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface AdminService extends IService<Admin>{
    void add(Admin admin);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    boolean updateById(Admin admin);

//    Admin selectById(Integer id);

    List<Admin> selectAll(Admin admin);

    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    Account login(Account account);

    void updatePassword(Account account);
}

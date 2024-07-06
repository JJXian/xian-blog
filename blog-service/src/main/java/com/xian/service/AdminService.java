package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.common.result.Result;
import com.xian.model.role.pojo.Account;
import com.xian.model.role.pojo.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface AdminService extends IService<Admin>{
    Result add(Admin admin);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Result updateAdmin(Admin admin);

//    Admin selectById(Integer id);

    List<Admin> selectAll(Admin admin);

    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    Account login(Account account);

    void updatePassword(Account account);
}

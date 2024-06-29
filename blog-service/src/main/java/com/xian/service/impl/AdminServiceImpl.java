package com.xian.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.common.Constants;
import com.xian.entity.Account;
import com.xian.entity.Admin;
import com.xian.enums.ResultCodeEnum;
import com.xian.enums.RoleEnum;
import com.xian.exception.CustomException;
import com.xian.mapper.AdminMapper;
import com.xian.service.AdminService;
import com.xian.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员业务处理
 **/
@Service

public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 新增
     */
    public void add(Admin admin) {
//        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
//         修改为mybatis-plus
//        1、构建查询条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("username",admin.getUsername());
        Admin dbAdmin = adminMapper.selectOne(wrapper);
//        Admin dbAdmin =lambdaQuery()
//                .eq(Admin::getUsername,admin.getUsername()).one();
//        System.out.println(dbAdmin.getUsername()+"---------------------------------------------------");
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        admin.setRole(RoleEnum.ADMIN.name());
        adminMapper.insert(admin);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public boolean updateById(Admin admin) {
        int row = adminMapper.updateById(admin);
        if(row!=0){
            return true;
        }
        return false;

    }

    /**
     * 根据ID查询
     */
//    public Admin selectById(Integer id) {
//        return adminMapper.selectById(id);
//    }

    /**
     * 查询所有
     */
    public List<Admin> selectAll(Admin admin) {
        return adminMapper.selectAll(admin);
    }

    /**
     * 分页查询
     */
    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    public Account login(Account account) {
//        Account dbAdmin = adminMapper.selectByUsername(account.getUsername());

        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("username",account.getUsername());
        Admin dbAdmin = adminMapper.selectOne(wrapper);


        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
//        进行MD5加密
        String password = account.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }

//        if (!account.getPassword().equals(dbAdmin.getPassword())) {
//            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
//        }
        // 生成token
        String tokenData = dbAdmin.getId() + "-" + RoleEnum.ADMIN.name();
        String token = TokenUtils.createToken(tokenData, dbAdmin.getPassword());
        dbAdmin.setToken(token);
        return dbAdmin;
    }

//    /**
//     * 修改密码
//     */
//    public void updatePassword(Account account) {
//        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
//        if (ObjectUtil.isNull(dbAdmin)) {
//            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
//        }
//        if (!account.getPassword().equals(dbAdmin.getPassword())) {
//            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
//        }
//        dbAdmin.setPassword(account.getNewPassword());
//        adminMapper.updateById(dbAdmin);
//    }
    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
//        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());

        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("username",account.getUsername());
        Admin dbAdmin = adminMapper.selectOne(wrapper);

        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
//        保存为MD5加密密码
        String password = account.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        String newPassword = account.getNewPassword();
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        dbAdmin.setPassword(newPassword);


        adminMapper.updateById(dbAdmin);
    }

}
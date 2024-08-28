package com.xian.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xian.common.constants.commonConstants;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.common.enums.RoleEnum;
import com.xian.common.exception.CustomException;
import com.xian.common.regex.RegexUtils;
import com.xian.common.result.Result;
import com.xian.mapper.AdminMapper;
import com.xian.model.role.dtos.AdminDTO;
import com.xian.model.role.dtos.LoginDTO;
import com.xian.model.role.pojo.Account;
import com.xian.model.role.pojo.Admin;
import com.xian.service.AdminService;
import com.xian.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员业务处理
 **/
@Service

public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 新增
     */
    public Result add(AdminDTO adminDTO) {
//        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
//         修改为mybatis-plus
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDTO, admin);

//        1、构建查询条件
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("username", admin.getUsername());
        Admin dbAdmin = adminMapper.selectOne(wrapper);
//       已存在
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            String password = DigestUtils.md5DigestAsHex(commonConstants.USER_DEFAULT_PASSWORD.getBytes());
            admin.setPassword(password);
        }
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
//        手机号校验
        String phone = admin.getPhone();
        if (!phoneRight(phone)) {
            return Result.error(ResultCodeEnum.PHONE_SYTLE_ERROR);
        }
//        邮箱校验
        String email = admin.getEmail();
        if (!emailRight(email)) {
            return Result.error(ResultCodeEnum.EMAIL_SYTLE_ERROR);
        }
        if (admin.getAvatar() == null || admin.getAvatar().isEmpty()) {
            admin.setAvatar(commonConstants.USER_DEFAULT_AVATAR);
        }

        admin.setRole(RoleEnum.ADMIN.name());
        adminMapper.insert(admin);
        return Result.success();
    }

    /**
     * 校验手机号
     */
    private boolean phoneRight(String phone) {
        if (phone != null && !phone.isEmpty() && !RegexUtils.isPhoneInvalid(phone)) {
            return false;
        }
        return true;
    }

    /**
     * 校验email
     */
    private boolean emailRight(String email) {
        if (email != null && !email.isEmpty() && !RegexUtils.isEmailInvalid(email)) {
            return false;
        }
        return true;
    }


    /**
     * 删除
     */
    public Result deleteById(Integer id) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("id", id);
        Admin admin = adminMapper.selectOne(wrapper);
        if (admin.getUsername().equals("admin")) {
            return Result.error("5001", "权限不足,请联系超级管理员！");
        }
        adminMapper.deleteById(id);
        return Result.success();
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
    public Result updateAdmin(Admin admin) {
//        手机号校验
        String phone = admin.getPhone();
        if (!phoneRight(phone)) {
            return Result.error(ResultCodeEnum.PHONE_SYTLE_ERROR);
        }
//        邮箱校验
        String email = admin.getEmail();
        if (!emailRight(email)) {
            return Result.error(ResultCodeEnum.EMAIL_SYTLE_ERROR);
        }

        int row = adminMapper.updateById(admin);
        if (row != 0) {
            return Result.success();
        }
        return Result.error();
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
//    public Account login(Account account) {
//
//        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
//                .select("*")
//                .eq("username",account.getUsername());
//        Admin dbAdmin = adminMapper.selectOne(wrapper);
//
//
//        if (ObjectUtil.isNull(dbAdmin)) {
//            throw new com.xian.common.exception.CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
//
//        }
////        进行MD5加密
//        String password = account.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        if (!password.equals(dbAdmin.getPassword())) {
//            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
//        }
//
////        if (!account.getPassword().equals(dbAdmin.getPassword())) {
////            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
////        }
//        // 生成token
//        String tokenData = dbAdmin.getId() + "-" + RoleEnum.ADMIN.name();
//        String token = TokenUtils.createToken(tokenData, dbAdmin.getPassword());
//        dbAdmin.setToken(token);
//        return dbAdmin;
//    }
    public Result login(LoginDTO loginDTO) {

        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("username", loginDTO.getUsername());
        Admin dbAdmin = adminMapper.selectOne(wrapper);


        if (ObjectUtil.isNull(dbAdmin)) {
            throw new com.xian.common.exception.CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);

        }
//        进行MD5加密
        String password = loginDTO.getPassword();
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
        return Result.success(dbAdmin);
    }

//    }

    /**
     * 修改密码
     */
    public void updatePassword(Account account) {

        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .select("*")
                .eq("username", account.getUsername());
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
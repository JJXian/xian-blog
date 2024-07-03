package com.xian.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.common.Constants;
import com.xian.common.Result;
import com.xian.common.regex.RegexUtils;
import com.xian.enums.ResultCodeEnum;
import com.xian.role.pojo.Account;
import com.xian.role.pojo.User;
import com.xian.enums.RoleEnum;
import com.xian.exception.CustomException;
import com.xian.mapper.UserMapper;
import com.xian.service.UserService;
import com.xian.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private UserMapper userMapper;

    public void add(User user) {
        // 业务方法
        // 1. 判断用户账号是否重复
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        // 2. 判断用户密码是不是空
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD); // 默认密码 123
        }
        // 3. 判断用户名称是不是空
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        // 4. 默认用户角色
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);

    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }


    @Override
    public Result updateByUser(User user) {
        //验证手机号
        String phone = user.getPhone();
        if(phone!=null && !phone.isEmpty() && !RegexUtils.isPhoneInvalid(phone)){
            return Result.error(ResultCodeEnum.PHONE_SYTLE_ERROR);
        }
//        验证邮箱
        String email = user.getEmail();
        if(email!=null && !email.isEmpty() && !RegexUtils.isEmailInvalid(email)){
            return Result.error(ResultCodeEnum.PHONE_SYTLE_ERROR);
        }

        userMapper.updateById(user);
        return Result.success();
    }

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }



    /**
     * 登录
     */
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        String password = account.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }

//        if (!account.getPassword().equals(dbUser.getPassword())) {
//            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
//        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

//    public Account login(Account account) {
//        Account dbUser = userMapper.selectByUsername(account.getUsername());
//        if (ObjectUtil.isNull(dbUser)) {
//            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
//        }
//        if (!account.getPassword().equals(dbUser.getPassword())) {
//            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
//        }
//        // 生成token
//        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
//        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
//        dbUser.setToken(token);
//        return dbUser;
//    }

    /**
     * 注册
     */
    public void register(Account account) {
        User user = new User();
        BeanUtils.copyProperties(account, user);
        this.add(user);
    }

    /**
     * 修改密码
     * @param account
     */
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //        保存为MD5加密密码
        String password = account.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        String newPassword = account.getNewPassword();
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        dbUser.setPassword(newPassword);

//        if (!account.getPassword().equals(dbUser.getPassword())) {
//            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
//        }
//        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

}

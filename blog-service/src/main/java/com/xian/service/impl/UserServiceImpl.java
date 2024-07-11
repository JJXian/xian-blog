package com.xian.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.common.constants.RedisConstants;
import com.xian.common.constants.commonConstants;
import com.xian.common.result.Result;
import com.xian.common.regex.RegexUtils;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.model.role.dtos.LoginDTO;
import com.xian.model.role.dtos.RegisterDTO;
import com.xian.model.role.pojo.Account;
import com.xian.model.role.pojo.User;
import com.xian.common.enums.RoleEnum;
import com.xian.common.exception.CustomException;
import com.xian.mapper.UserMapper;
import com.xian.service.UserService;
import com.xian.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Result add(User user) {
        // 业务方法
        // 1. 判断用户账号是否重复
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null) {
            return Result.error(ResultCodeEnum.USER_EXIST_ERROR);
        }

        //        手机号校验
        String phone = user.getPhone();
        if(!phoneRight(phone)){
            return Result.error(ResultCodeEnum.PHONE_SYTLE_ERROR);
        }
//        邮箱校验
        String email = user.getEmail();
        if(!emailRight(email)){
            return Result.error(ResultCodeEnum.EMAIL_SYTLE_ERROR);
        }

        // 2. 判断用户密码是不是空
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(commonConstants.USER_DEFAULT_PASSWORD); // 默认密码 123
        }
        // 3. 判断用户名称是不是空
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        // 4. 默认用户角色
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);
        return Result.success();
    }


    /**
     * 校验手机号
     */
    private boolean phoneRight(String phone){
        if(phone != null && !phone.isEmpty() && !RegexUtils.isPhoneInvalid(phone)){
            return false;
        }
        return true;
    }

    /**
     * 校验email
     */
    private boolean emailRight(String email){
        if(email != null && !email.isEmpty() && !RegexUtils.isEmailInvalid(email)){
            return false;
        }
        return true;
    }

    public void deleteById(Integer id) {
//        TODO 根据id删除用户，判断用户是否还有博客没删除，有则不能删除
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }


    @Override
    public Result updateByUser(User user) {
        //        手机号校验
        String phone = user.getPhone();
        if(!phoneRight(phone)){
            return Result.error(ResultCodeEnum.PHONE_SYTLE_ERROR);
        }
//        邮箱校验
        String email = user.getEmail();
        if(!emailRight(email)){
            return Result.error(ResultCodeEnum.EMAIL_SYTLE_ERROR);
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

//    public Account login(Account account) {
//        Account dbUser = userMapper.selectByUsername(account.getUsername());
//        if (ObjectUtil.isNull(dbUser)) {
////            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
//            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
//        }
//        String password = account.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        if (!password.equals(dbUser.getPassword())) {
//            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
//        }
//
////        if (!account.getPassword().equals(dbUser.getPassword())) {
////            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
////        }
//        // 生成token
//        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
//        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
//        dbUser.setToken(token);
//        return dbUser;
//    }
    /**
     * 登录
     */
    public Result login(LoginDTO loginDTO) {
        Account dbUser = userMapper.selectByUsername(loginDTO.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
//            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
//        对密码进行md5加密
        String password = loginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(dbUser.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.createToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return Result.success(dbUser);
    }

    /**
     * 注册
     */
    public Result register(RegisterDTO registerDTO) {
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);

        String email = registerDTO.getEmail();
        String emailKey = RedisConstants.REGISTER_MAIL_CODE_KEY+email;
        String emailCode = (String) stringRedisTemplate.opsForValue().get(emailKey);

        if(!emailCode.equals(registerDTO.getMailVerify())){
            return Result.error("5001","验证码错误");
        }

//        设置默认密码和头像
        String password = registerDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        user.setAvatar(commonConstants.USER_DEFAULT_AVATAR);
        this.add(user);
        return  Result.success();
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public Result EmailVerify(String email) {
        // 1.校验邮箱
        if (!RegexUtils.isEmailInvalid(email)) {
            // 1.2.如果不符合，返回错误信息
            return Result.error(ResultCodeEnum.EMAIL_SYTLE_ERROR);
        }
        // 2.检查上一次发送时间，此处并未记录真实发送时间，仅通过验证码的ttl来判断
        String key = RedisConstants.REGISTER_MAIL_CODE_KEY + email;
        int sendCodeInterval = 60;
        Long keyExpire = stringRedisTemplate.getExpire(key);
//        log.debug("check REGISTER_MAIL_CODE_TTL,{}", keyExpire);
        if (keyExpire != null && RedisConstants.REGISTER_MAIL_CODE_TTL - keyExpire < sendCodeInterval) {
            // 2.1不合符，拒绝发送
            return Result.error("5001","发送频繁");
        }
        // 3.检查邮箱是否已经被注册
        if(userMapper.selectByEmail(email) != null){
            return Result.error("5001","该邮箱已经被注册了！");
        }
        return Result.success();
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
        userMapper.updateById(dbUser);
    }



}

package com.xian.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.xian.common.constants.RedisConstants;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.common.regex.RegexUtils;
import com.xian.common.result.Result;
import com.xian.mapper.UserMapper;
import com.xian.model.email.EmailDTO;
import com.xian.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @Author: jjxian
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 注入邮件工具类
     */
    @Resource
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.nickname}")
    private String nickname;


    @Override
    public Result sendEmail(EmailDTO emailDTO) {
        String email = emailDTO.getEmail();
        // 1.校验邮箱
        if (!RegexUtils.isEmailInvalid(email)) {
            // 1.2.如果不符合，返回错误信息
            return Result.error(ResultCodeEnum.EMAIL_SYTLE_ERROR);
        }
        // 2.检查上一次发送时间，此处并未记录真实发送时间，仅通过验证码的ttl来判断
        String key = RedisConstants.REGISTER_MAIL_CODE_KEY + email;
        int sendCodeInterval = 60;
        Long keyExpire = stringRedisTemplate.getExpire(key);
        if (keyExpire != null && RedisConstants.REGISTER_MAIL_CODE_TTL - keyExpire < sendCodeInterval) {
            // 2.1不合符，拒绝发送
            return Result.error("5001","发送频繁");
        }
        // 3.检查邮箱是否已经被注册
        if(userMapper.selectByEmail(email) != null){
            return Result.error("5001","该邮箱已经被注册了！");
        }

        // 创建一个邮件
        SimpleMailMessage message = new SimpleMailMessage();

        // 设置发件人
        message.setFrom(nickname+'<'+sender+'>');

        // 设置收件人
        message.setTo(email);

        // 设置邮件主题
        message.setSubject("欢迎注册技术研习中心");

        //生成六位随机数
        String code = RandomUtil.randomNumbers(6);

        //将验证码存入redis，有效期为5分钟
        stringRedisTemplate.opsForValue().set(RedisConstants.REGISTER_MAIL_CODE_KEY +email, code,RedisConstants.REGISTER_MAIL_CODE_TTL , TimeUnit.SECONDS);
        message.setText("【验证码】亲爱的用户：\n" + "你正在注册技术研习中心，你的邮箱验证码为：" + code + "，此验证码有效时长5分钟，请勿转发他人。");

        // 发送邮件
        mailSender.send(message);

        return Result.success();
    }



}

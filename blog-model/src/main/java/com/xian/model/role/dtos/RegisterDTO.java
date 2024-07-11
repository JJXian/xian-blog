package com.xian.model.role.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: jjxian
 */
@Data
public class RegisterDTO {
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;

    /**
     * 邮箱
     */
    @NotNull
    String email;

    /**
     * 邮箱验证码
     */
    @NotBlank(message = "邮箱验证码不能为空")
    String mailVerify;

}

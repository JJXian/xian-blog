package com.xian.model.role.dtos;

import lombok.Data;

/**
 * @Author: jjxian
 */
@Data
public class RegisterDTO {
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
}

package com.xian.model.role.dtos;

import lombok.Data;

/**
 * @Author: jjxian
 */
@Data
public class LoginDTO {

    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 角色标识 */
    private String role;

}

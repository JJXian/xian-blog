package com.xian.model.role.dtos;

import lombok.Data;

/**
 * @Author: jjxian
 */
@Data
public class AdminDTO {
    /**用户名*/
    private String username;
    /** 姓名 */
    private String name;
    /** 电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 头像 */
    private String avatar;

}

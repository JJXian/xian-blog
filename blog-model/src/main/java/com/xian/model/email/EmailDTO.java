package com.xian.model.email;

import lombok.Data;

/**
 * @Author: jjxian
 */
@Data
public class EmailDTO {

    /**
     * 邮箱
     */
    String email;
    // 默认构造函数
    public EmailDTO() {}

    // 带参构造函数
    public EmailDTO(String email) {
        this.email = email;
    }

    // Getter和Setter方法
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

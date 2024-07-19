package com.xian.common.config;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xian.common.constants.commonConstants;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.common.exception.CustomException;
import com.xian.model.role.pojo.Account;
import com.xian.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @Author: jjxian
 */

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前用户的用户名
        Account ac =  TokenUtils.getCurrentUser();
        String username = ac.getUsername();

        if (commonConstants.EXPERIENCE_ACCOUNT_USERNAME.equals(username)) {
            // 允许GET请求
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                return true;
            } else {
                throw new CustomException(ResultCodeEnum.ACCOUNT_NOT_ALLOW);
            }
        }
        return true; // 其他用户允许所有请求
    }
}

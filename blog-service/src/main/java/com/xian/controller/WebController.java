package com.xian.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xian.common.annotation.RateLimiter;
import com.xian.common.enums.LimitType;
import com.xian.common.result.Result;
import com.xian.model.role.dtos.LoginDTO;
import com.xian.model.role.dtos.RegisterDTO;
import com.xian.model.role.pojo.Account;
import com.xian.common.enums.ResultCodeEnum;
import com.xian.common.enums.RoleEnum;
import com.xian.service.AdminService;
import com.xian.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        if (ObjectUtil.isEmpty(loginDTO.getUsername()) || ObjectUtil.isEmpty(loginDTO.getPassword())
                || ObjectUtil.isEmpty(loginDTO.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(loginDTO.getRole())) {
            return adminService.login(loginDTO);
        }else if (RoleEnum.USER.name().equals(loginDTO.getRole())) {
            return userService.login(loginDTO);
        }
        return Result.error(ResultCodeEnum.PARAM_ERROR);

//        return Result.success(loginDTO);
    }


    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO) {
//        if (StrUtil.isBlank(registerDTO.getUsername()) || StrUtil.isBlank(registerDTO.getPassword())
//                || ObjectUtil.isEmpty(registerDTO.getRole())) {
        if (StrUtil.isBlank(registerDTO.getUsername()) || StrUtil.isBlank(registerDTO.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return userService.register(registerDTO);
//        if (RoleEnum.USER.name().equals(account.getRole())) {
//            userService.register(account);
//        } else {
//            return Result.error(ResultCodeEnum.PARAM_ERROR);
//        }
//        return Result.success();
    }


    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

}

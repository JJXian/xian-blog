package com.xian.controller;

import com.xian.common.Result;
import com.xian.behavior.pojo.Likes;
import com.xian.service.LikesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/likes")
public class LikesController {

    @Resource
    LikesService likesService;

    // 点赞和取消
    @PostMapping("/set")
    public Result set(@RequestBody Likes likes) {
        likesService.set(likes);
        return Result.success();
    }

}

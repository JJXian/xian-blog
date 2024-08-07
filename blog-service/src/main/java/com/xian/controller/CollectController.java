package com.xian.controller;

import com.xian.common.result.Result;
import com.xian.model.behavior.pojo.Collect;
import com.xian.service.CollectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    CollectService collectService;

    // 收藏和取消
    @PostMapping("/set")
    public Result set(@RequestBody Collect collect) {
        collectService.set(collect);
        return Result.success();
    }

}

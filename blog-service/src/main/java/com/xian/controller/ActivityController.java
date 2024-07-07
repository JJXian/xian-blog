package com.xian.controller;

import com.xian.common.result.Result;
import com.xian.model.activity.pojo.Activity;
import com.xian.service.ActivityService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动前端操作接口
 **/
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Activity activity) {
        activityService.add(activity);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
//        activityService.deleteById(id);
        activityService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        activityService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Activity activity) {
        activityService.updateById(activity);
        return Result.success();
    }

    @PutMapping("/updateReadCount/{activityId}")
    public Result updateReadCount(@PathVariable Integer activityId) {
        activityService.updateReadCount(activityId);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
//        Activity activity = activityService.selectById(id);
//        return Result.success(activity);
        return activityService.selectById(id);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Activity activity) {
        List<Activity> list = activityService.selectAll(activity);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Activity> page = activityService.selectPage(activity, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户报名的活动列表
     */
    @GetMapping("/selectUser")
    public Result selectUser(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Activity> page = activityService.selectUser(activity, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户点赞的活动列表
     */
    @GetMapping("/selectLike")
    public Result selectLike(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Activity> page = activityService.selectLike(activity, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户收藏的活动列表
     */
    @GetMapping("/selectCollect")
    public Result selectCollect(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Activity> page = activityService.selectCollect(activity, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询用户评论的活动列表
     */
    @GetMapping("/selectComment")
    public Result selectComment(Activity activity,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Activity> page = activityService.selectComment(activity, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 热门活动
     */
    @GetMapping("/selectTop")
    public Result selectTop() {
        List<Activity> list = activityService.selectTop();
        return Result.success(list);
    }

}
package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.model.behavior.pojo.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CommentService extends IService<Comment> {
    void add(Comment comment);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    boolean updateById(Comment comment);

    Comment selectById(Integer id);

    List<Comment> selectAll(Comment comment);

    List<Comment> selectForUser(Comment comment);

    PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize);

    Integer selectCount(Integer fid, String module);
}

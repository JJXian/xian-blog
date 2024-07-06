package com.xian.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.model.role.pojo.Account;
import com.xian.model.behavior.pojo.Comment;
import com.xian.common.enums.RoleEnum;
import com.xian.mapper.CommentMapper;
import com.xian.service.CommentService;
import com.xian.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务处理
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增
     */
    public void add(Comment comment) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            comment.setUserId(currentUser.getId());
        }
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);  // 先插入数据  拿到主键ID  再设置数据
        if (comment.getRootId() == null) {
            comment.setRootId(comment.getId());
            commentMapper.updateById(comment); // 注意 更新一下 root_id
        }

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            commentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     *
     * @return
     */
    public boolean updateById(Comment comment) {
        commentMapper.updateById(comment);
        return false;
    }

    /**
     * 根据ID查询
     */
    public Comment selectById(Integer id) {
        return commentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Comment> selectAll(Comment comment) {
        return commentMapper.selectAll(comment);
    }

    public List<Comment> selectForUser(Comment comment) {
        List<Comment> commentList = commentMapper.selectForUser(comment);  // 查询一级的评论
        for (Comment c : commentList) {  // 查询回复列表
            Comment param = new Comment();
            param.setRootId(c.getId());
            List<Comment> children = this.selectAll(param);
            children = children.stream().filter(child -> !child.getId().equals(c.getId())).collect(Collectors.toList());  // 排除当前查询结果里最外层节点
            c.setChildren(children);
        }
        return commentList;
    }

    /**
     * 分页查询
     */
    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public Integer selectCount(Integer fid, String module) {
        return commentMapper.selectCount(fid, module);
    }

}
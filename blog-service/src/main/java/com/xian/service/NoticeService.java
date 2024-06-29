package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoticeService extends IService<Notice> {

    void add(Notice notice);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    boolean updateById(Notice notice);

    Notice selectById(Integer id);

    List<Notice> selectAll(Notice notice);

    PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);
}

package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.model.blog.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void add(Category category);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    boolean updateById(Category category);

    Category selectById(Integer id);

    List<Category> selectAll(Category category);

    PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize);
}

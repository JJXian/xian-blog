package com.xian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xian.sys.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作notice相关数据接口
*/
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
      * 新增
    */
//    int insert(Notice notice);

    /**
      * 删除
    */
//    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Notice notice);

    /**
      * 根据ID查询
    */
//    Notice selectById(Integer id);

    /**
      * 查询所有
    */
    List<Notice> selectAll(Notice notice);

}
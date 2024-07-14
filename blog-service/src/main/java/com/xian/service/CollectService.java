package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.model.behavior.pojo.Collect;

public interface CollectService extends IService<Collect> {
    void set(Collect collect);

    void deleteAllBlogCollect(Integer id);

    int selectByFidAndModule(Integer fid, String module);

    Collect selectUserCollect(Integer fid, String module);


}

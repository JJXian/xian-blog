package com.xian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xian.model.behavior.pojo.Collect;

public interface CollectService extends IService<Collect> {
    void set(Collect collect);


}

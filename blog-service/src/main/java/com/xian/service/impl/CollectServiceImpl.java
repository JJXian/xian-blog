package com.xian.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xian.model.role.pojo.Account;
import com.xian.model.behavior.pojo.Collect;
import com.xian.mapper.CollectMapper;
import com.xian.service.CollectService;
import com.xian.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper,Collect> implements CollectService {

    @Resource
    CollectMapper collectMapper;

    public void set(Collect collect) {
        Account currentUser = TokenUtils.getCurrentUser();
        collect.setUserId(currentUser.getId());
        Collect dblCollect = collectMapper.selectUserCollect(collect);
        if (dblCollect == null) {
            collectMapper.insert(collect);
        } else {
            collectMapper.deleteById(dblCollect.getId());
        }
    }

    /**
     * 查询当前用户是否收藏过
     */
    public Collect selectUserCollect(Integer fid, String module) {
        Account currentUser = TokenUtils.getCurrentUser();
        Collect collect = new Collect();
        collect.setUserId(currentUser.getId());
        collect.setFid(fid);
        collect.setModule(module);
        return collectMapper.selectUserCollect(collect);
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return collectMapper.selectByFidAndModule(fid, module);
    }

    /**
     * 删除博客的所有收藏信息
     * @param fid 关联的博客/活动id
     */
    public void deleteAllBlogCollect(Integer fid){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("fid",fid);
        collectMapper.delete(wrapper);
    }

}

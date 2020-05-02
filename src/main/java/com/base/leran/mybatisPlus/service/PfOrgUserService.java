package com.base.leran.mybatisPlus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.leran.mybatisPlus.entiy.PfOrgUser;

import java.util.List;

public interface PfOrgUserService extends IService<PfOrgUser> {
    void addUser(PfOrgUser pfOrgUser);

    public List<PfOrgUser> findAllPage(Page<PfOrgUser> page, QueryWrapper queryWrapper);
}

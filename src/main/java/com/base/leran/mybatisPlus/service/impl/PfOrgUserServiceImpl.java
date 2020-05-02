package com.base.leran.mybatisPlus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.leran.mybatisPlus.entiy.PfOrgUser;
import com.base.leran.mybatisPlus.mapper.PfOrgUserMapper;
import com.base.leran.mybatisPlus.service.PfOrgUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("slave_tempo")
public class PfOrgUserServiceImpl extends ServiceImpl<PfOrgUserMapper, PfOrgUser> implements PfOrgUserService {

    @Override
    public void addUser(PfOrgUser pfOrgUser) {
        super.baseMapper.insert(pfOrgUser);
        Page<Object> page = new Page<>(0, 2);
        baseMapper.insert(pfOrgUser);
    }

    @Override
    public List<PfOrgUser> findAllPage(Page<PfOrgUser> page, QueryWrapper queryWrapper) {
        Page<PfOrgUser> list = baseMapper.selectPage(page, queryWrapper);
        return list.getRecords();
    }
}

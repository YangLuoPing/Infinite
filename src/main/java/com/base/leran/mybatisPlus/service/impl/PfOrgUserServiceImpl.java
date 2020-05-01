package com.base.leran.mybatisPlus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.leran.mybatisPlus.entiy.PfOrgUser;
import com.base.leran.mybatisPlus.mapper.PfOrgUserMapper;
import com.base.leran.mybatisPlus.service.PfOrgUserService;
import org.springframework.stereotype.Service;

@Service
@DS("slave_tempo")
public class PfOrgUserServiceImpl extends ServiceImpl<PfOrgUserMapper, PfOrgUser> implements PfOrgUserService {

    @Override
    public void addUser(PfOrgUser pfOrgUser) {
        baseMapper.insert(pfOrgUser);
    }
}

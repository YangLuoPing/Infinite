package com.base.leran.mybatisPlus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.leran.mybatisPlus.entiy.PfOrgUser;

public interface PfOrgUserService extends IService<PfOrgUser> {
    void  addUser(PfOrgUser pfOrgUser);
}

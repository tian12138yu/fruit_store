package com.javapandeng.service.impl;

import com.javapandeng.base.BaseDao;
import com.javapandeng.base.BaseServiceImpl;
import com.javapandeng.mapper.ManageMapper;
import com.javapandeng.po.Manage;
import com.javapandeng.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author tjy
 * @Date 2020/7/26 16:07
 */
@Service
public class ManageServiceImpl extends BaseServiceImpl<Manage> implements ManageService {

    @Autowired
    ManageMapper manageMapper;
    @Override
    public BaseDao<Manage> getBaseDao() {
        return manageMapper;
    }
}

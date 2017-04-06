package com.cg.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.cg.common.entity.CgAdmin;
import com.cg.core.dao.IAdminDao;


@Repository("adminDao")
public class AdminDaoImpl extends BaseDaoImpl<CgAdmin> implements IAdminDao {

}

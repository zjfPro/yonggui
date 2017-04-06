package com.cg.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.cg.common.entity.CgAdminLog;
import com.cg.core.dao.IAdminLogDao;

 
@Repository("adminLogDao")
public class AdminLogDaoImpl extends BaseDaoImpl<CgAdminLog> implements IAdminLogDao{

}

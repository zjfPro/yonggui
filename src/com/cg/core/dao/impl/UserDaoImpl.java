package com.cg.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.cg.common.entity.CgUser;
import com.cg.core.dao.IUserDao;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<CgUser> implements IUserDao{

}

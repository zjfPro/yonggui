package com.cg.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.cg.common.entity.CgShopFront;
import com.cg.core.dao.IShopFrontDao;
import com.cg.core.dao.IUserDao;

@Repository("shopFrontDao")
public class ShopFrontDaoImpl extends BaseDaoImpl<CgShopFront> implements IShopFrontDao{

}

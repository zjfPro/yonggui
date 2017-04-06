package com.cg.core.service;

import com.cg.common.entity.CgInvestor;

public interface IinvestorService extends IBaseService<CgInvestor>{
	public Object checkInvestorLogin(String account,String password) throws Exception;
}

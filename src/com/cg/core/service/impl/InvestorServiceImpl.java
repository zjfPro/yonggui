package com.cg.core.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgInvestor;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.Page;
import com.cg.common.utils.PageUtil;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IinvestorService;

@Service
public class InvestorServiceImpl extends BaseServiceImpl<CgInvestor> implements IinvestorService{
	@Autowired
	private IBaseDao baseDao;

	@Override
	public Object checkInvestorLogin(String account, String password)
			throws Exception {
		if (isEmpty(account)) {
			return "账号不能为空";
		}
		if (isEmpty(password)) {
			return "密码不能为空";
		}
		if (password.length() < 6) {
			return "密码不能小于6位";
		}
		String mima = encrypt(password);
		CgInvestor findEntity = (CgInvestor) baseDao.findEntity("from CgInvestor where account=? and password=?",
				new Object[] { account, mima });
		
		if(findEntity==null){
			return "帐号或密码错误";
		}
		
		return findEntity;
	}
	
	private String encrypt(String str) throws Exception {
		return CustomMixedEncryptionUtil.encrypt(str, str.substring(0, 6));
	}
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public CgInvestor save(CgInvestor entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CgInvestor saveByAdmin(CgInvestor entity, CgInvestor adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CgInvestor update(CgInvestor entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CgInvestor updateByAdmin(CgInvestor entity, CgInvestor adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateForHql(String hql, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CgInvestor entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByAdmin(CgInvestor entity, CgInvestor adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CgInvestor> find(String hql) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CgInvestor findEntity(Class<CgInvestor> c, Serializable id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CgInvestor findEntity(String hql, Object[] obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CgInvestor findEntity(String hql, List<Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> find(String hql, List<Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> find(String hql, Object param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> findSQL(String sql, Object[] param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageUtil<CgInvestor> find(String hql, List<Object> params,
			PageUtil<CgInvestor> pageUtil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageUtil<CgInvestor> find(String hql, List<Object> params,
			PageUtil<CgInvestor> pageUtil, String countHQL) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(CgInvestor entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int executeHQL(String hql, Object[] params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CgInvestor> find(String queryString, List<Object> params,
			Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> find(String queryString, Object param, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> find(String queryString, Object[] params, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CgInvestor> find(String queryString, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count(String hql, List<Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTransactional(CgInvestor... entitys) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTransactional(CgInvestor... entitys) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransactional(CgInvestor... entitys) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newTransactional(CgInvestor[] saveEntitys,
			CgInvestor[] updateEntitys, CgInvestor[] deleteEntitys)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}

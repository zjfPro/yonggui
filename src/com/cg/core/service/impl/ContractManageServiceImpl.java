package com.cg.core.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgContract;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IContractManageService;


@Service("contractManageService")
public class ContractManageServiceImpl implements IContractManageService{
	@Autowired
	private IBaseDao baseDao;

	@Override
	public void deleteContract(String[] ids, CgAdminLog adminLog)
			throws Exception {
		Object[] objs = new Object[ids.length-1];
		int i = 0;
		String hqls = "";
		for (String id : ids) {
			if(id != null){
			hqls+="  cis.id = ?  or";
			objs[i++]= String.valueOf(id) ;
			}
		}
		String hql = "delete from CgContract cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
		if (adminLog != null) {
		baseDao.save(adminLog);
		}
	}

	@Override
	public void saveContent(CgContract contract, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
			}
		baseDao.save(contract);
	}

	@Override
	public void updateContent(CgContract infos, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
			}
		baseDao.update(infos);
	}

}

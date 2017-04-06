package com.cg.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IUserRecordService;

@Service("userRecordService")
public class UserRecordServiceImpl implements IUserRecordService{
	@Autowired
	private IBaseDao baseDao;
	@Override
	public void addUserRecord(CgCarUseRecords useRecords, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.save(useRecords);
		
	}
	@Override
	public void deleteshopFront(String[] ids, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		Object[] objs = new Object[ids.length-1];
		int i = 0;
		String hqls = "";
		for (String id : ids) {
			if(id != null){
			hqls+="  cis.id = ?  or";
			objs[i++]= String.valueOf(id) ;
			}
		}
		String hql = "delete from CgCarUseRecords cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
	}
	@Override
	public void updateModifyshopFront(CgCarUseRecords useRecordst,
			CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.update(useRecordst);
		
	}
}

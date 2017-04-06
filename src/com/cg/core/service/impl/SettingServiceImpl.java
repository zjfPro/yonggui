package com.cg.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgSetting;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.ISeriesManagerService;
import com.cg.core.service.ISettingService;

@Service("settingService")
public class SettingServiceImpl implements ISettingService{
	@Autowired
	private IBaseDao baseDao;
	@Override
	public void addSetting(CgSetting cgSetting, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.save(cgSetting);
		
	}
	
	@Override
	public void updateSetting(CgSetting sCgSetting, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.update(sCgSetting);
		
	}

	@Override
	public void deleteSetting(String[] ids, CgAdminLog adminLog)
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
		String hql = "delete from CgSetting cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
	}

}

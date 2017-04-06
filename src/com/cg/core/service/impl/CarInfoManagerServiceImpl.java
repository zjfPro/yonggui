package com.cg.core.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarInfo;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.ICarInfoManagerService;

@Service("carInfoManagerService")
public class CarInfoManagerServiceImpl implements ICarInfoManagerService{
	@Autowired
	private IBaseDao baseDao;
	@Override
	public void saveInfo(CgCarInfo carInfo, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
			}
		carInfo.setId("cci-" + UUID.randomUUID());
		baseDao.save(carInfo);
	}
	
	@Override
	public void deleteCarInfo(String[] ids, CgAdminLog adminLog)
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
		String hql = "delete from CgCarInfo cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
	}

	@Override
	public void updateInfo(CgCarInfo infos, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
			}
		baseDao.update(infos);
		
	}

}

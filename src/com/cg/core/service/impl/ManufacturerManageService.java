package com.cg.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IManufacturerManageService;

@Service("manufacturerManageService")
public class ManufacturerManageService implements IManufacturerManageService{
	@Autowired
	private IBaseDao baseDao;

	@Override
	public void updateManufacturer(CgCarBrand brand, String ccId,
			CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		CgCarBrand brand2 = (CgCarBrand)baseDao.findEntity(CgCarBrand.class, ccId);
		String hql = "UPDATE CgCarBrand SET manufacturer = ? WHERE manufacturer = ?";
		Object[] params = {brand.getManufacturer(),brand2.getManufacturer()};
		baseDao.updateForHql(hql, params);
	}

	@Override
	public void saveManufacturer(CgCarBrand carBrand, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.save(carBrand);
	}

	@Override
	public void deleteSeries(String[] idarrays, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		Object[] objs = new Object[idarrays.length-1];
		int i = 0;
		String hqls = "";
		for (String id : idarrays) {
			if(id != null){
			hqls+="  cis.id = ?  or";
			objs[i++]= String.valueOf(id) ;
			}
		}
		String hql = "delete from CgCarBrand cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
		
	}
}

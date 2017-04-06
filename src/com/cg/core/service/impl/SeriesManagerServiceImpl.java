package com.cg.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.ISeriesManagerService;

@Service("seriesManagerService")
public class SeriesManagerServiceImpl implements ISeriesManagerService{
	@Autowired
	private IBaseDao baseDao;
	
	/** 修改系列 */
	@Override
	public void updateSeries(CgCarBrand brand, String ccId, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		CgCarBrand brand2 = (CgCarBrand)baseDao.findEntity(CgCarBrand.class, ccId);
		String hql = "UPDATE CgCarBrand SET series = ? WHERE series = ?";
		Object[] params = {brand.getSeries(), brand2.getSeries()};
		baseDao.updateForHql(hql, params);
	}

	@Override
	public void saveSeries(CgCarBrand brand2, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.save(brand2);
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

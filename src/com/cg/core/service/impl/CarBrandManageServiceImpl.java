package com.cg.core.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.ICarBrandManageService;

@Service("carBrandManageService")
public class CarBrandManageServiceImpl implements ICarBrandManageService{
	@Autowired
	private IBaseDao baseDao;
	
	/** 新增品牌 */
	@Override
	public void addCarBrand(CgCarBrand carBrand, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		carBrand.setId("ccb-" + UUID.randomUUID());
		baseDao.save(carBrand);
		if (adminLog != null) {
		baseDao.save(adminLog);
		}
	}
	
	/** 删除品牌 */
	@Override
	public void deleteCarBrand(String[] idarrays, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
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
		if (adminLog != null) {
		baseDao.save(adminLog);
		}
	}
	
	/** 修改品牌 */
	@Override
	public void updateCarBrand(CgCarBrand brand, String ccId,
			CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		CgCarBrand brand2 = (CgCarBrand)baseDao.findEntity(CgCarBrand.class, ccId);
		String hql = "UPDATE CgCarBrand SET brand = ? WHERE brand = ?";
		Object[] params = {brand.getBrand(), brand2.getBrand()};
		baseDao.updateForHql(hql, params);
	}

}

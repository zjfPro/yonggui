package com.cg.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IModelManagerService;

@Service("modelManagerService")
public class ModelManagerServiceImpl implements IModelManagerService{
	@Autowired
	private IBaseDao baseDao;
	@Override
	public void updateModel(CgCarBrand brand, String ccId, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		CgCarBrand brand2 = (CgCarBrand)baseDao.findEntity(CgCarBrand.class, ccId);
		String hql = "UPDATE CgCarBrand SET model = ? WHERE model = ?";
		Object[] params = {brand.getModel(),brand2.getModel()};
		baseDao.updateForHql(hql, params);
	}
	@Override
	public void saveModel(CgCarBrand brand2, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.save(brand2);
	}
	@Override
	public void deleteModel(String[] ids, CgAdminLog adminLog) throws Exception {
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
		String hql = "delete from CgCarBrand cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
	}

}

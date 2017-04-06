package com.cg.core.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;
import com.cg.common.entity.CgCarStructure;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IStructureService;

@Service("structureService")
public class StructureServiceImpl implements IStructureService{
	@Autowired
	private IBaseDao baseDao;
	@Override
	public void addStructure(CgCarStructure structure, CgAdminLog adminLog)
			throws Exception {
		// TODO Auto-generated method stub
		structure.setId("ccs-" + UUID.randomUUID());
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		baseDao.save(structure);
	}
	
	@Override
	public void updateStructure(CgCarStructure structure, CgAdminLog adminLog,String ccId)
			throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		CgCarStructure brand2 = (CgCarStructure)baseDao.findEntity(CgCarStructure.class, ccId);
		String hql = "UPDATE CgCarStructure SET name = ? where id = ? ";
		Object[] params = {structure.getName(),brand2.getId()};
		baseDao.updateForHql(hql, params);
	}

	@Override
	public void deleteStructure(String[] idarrays, CgAdminLog adminLog)
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
		String hql = "delete from CgCarStructure cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
		if (adminLog != null) {
		baseDao.save(adminLog);
		}
	}

}

package com.cg.core.service.impl;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarType;
import com.cg.common.entity.CgStaffManageRoleContainer;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.ICarTypeManageService;
import com.sun.org.apache.bcel.internal.generic.NEW;
@Service("carTypeManageService")
public class CarTypeManageServiceImpl implements ICarTypeManageService{
	@Autowired
	private IBaseDao baseDao;

	/** 删除车型 */
	@Override
	public void deleteCarType(String[] ids,CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
		Object[] objs = new Object[ids.length-1];
		int i = 0;
		String hqls = "";
		for (String id : ids) {
			if(id != null){
			hqls+="  cis.id = ?  or";
			objs[i++]= String.valueOf(id) ;
			}
		}
		String hql = "delete from CgCarType cis where "+hqls.substring(0, hqls.length()-3);
		baseDao.executeHQL(hql,objs);
		if (adminLog != null) {
		baseDao.save(adminLog);
		}
	}

	/** 添加车型 */
	@Override
	public void addCarType(CgCarType carType,CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
			carType.setId("cct-" + UUID.randomUUID());
			baseDao.save(carType);
			if (adminLog != null) {
			baseDao.save(adminLog);
			}
	}
	
	/** 修改车型 */
	@Override
	public void updateCarType(CgCarType carType,CgAdminLog adminLog,String ccId) throws Exception {
		// TODO Auto-generated method stub
		if (adminLog != null) {
			baseDao.save(adminLog);
		}
		String hql = "update CgCarType set carBrandId = ?,carStructureId = ?, engine = ?, transmissionCase = ?, speedMax = ?," +
				" accelerateTime = ?, gasolineConsumption = ?, displacement = ?,fuelForm = ?,fuelLabel = ?,info = ? where id = ?";
		Object[] params = {carType.getCarBrandId(),carType.getCarStructureId(),carType.getEngine(),carType.getTransmissionCase(),carType.getSpeedMax(),carType.getAccelerateTime(),
				carType.getGasolineConsumption(),carType.getDisplacement(),carType.getFuelForm(),carType.getFuelLabel(),carType.getInfo(),ccId };
		baseDao.updateForHql(hql, params);
	}
}

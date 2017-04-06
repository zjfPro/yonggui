package com.cg.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgFinancialDetails;
import com.cg.common.entity.CgStaffLog;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IRentalOrderService;
@Service
public class RentalOrderServiceImpl implements IRentalOrderService{

	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public void updateOrder(CgCarRentalOrder order, CgAdminLog adminLog,
			CgStaffLog cgStaffLog,CgFinancialDetails cfd) throws Exception {
		if(adminLog!=null){
			baseDao.save(adminLog);
		}
		if(cgStaffLog!=null){
			baseDao.save(cgStaffLog);
		}
		if(cfd!=null){
			baseDao.save(cfd);
		}
		baseDao.update(order);
	}

	@Override
	public void updateOrderForQuche(
			CgCarRentalOrder order,
			CgAdminLog adminLog, 
			CgStaffLog cgStaffLog, 
			CgCarUseRecords cur,
			CgCarInfo carInfo, 
			CgCarRentalItem ccri) throws Exception {
		if(order!=null){
			baseDao.update(order);
		}
		if(adminLog!=null){
			baseDao.save(adminLog);
		}
		if(cgStaffLog!=null){
			baseDao.save(cgStaffLog);
		}
		if(cur!=null){
			baseDao.save(cur);
		}
		if(carInfo!=null){
			baseDao.update(carInfo);
		}
		if(ccri!=null){
			baseDao.update(ccri);
		}
	}

}

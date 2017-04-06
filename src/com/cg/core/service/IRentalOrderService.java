package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarInfo;
import com.cg.common.entity.CgCarRentalItem;
import com.cg.common.entity.CgCarRentalOrder;
import com.cg.common.entity.CgCarUseRecords;
import com.cg.common.entity.CgFinancialDetails;
import com.cg.common.entity.CgStaffLog;

public interface IRentalOrderService {
	public void updateOrder(CgCarRentalOrder order,CgAdminLog adminLog,CgStaffLog cgStaffLog,CgFinancialDetails cfd) throws Exception;
	public void updateOrderForQuche(CgCarRentalOrder order,CgAdminLog adminLog,CgStaffLog cgStaffLog,CgCarUseRecords cur,
			CgCarInfo carInfo,CgCarRentalItem ccri) throws Exception;
}

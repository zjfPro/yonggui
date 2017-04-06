package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarUseRecords;

public interface IUserRecordService {

	/** 新增车辆使用记录 */
	public void addUserRecord(CgCarUseRecords useRecords, CgAdminLog adminLog)throws Exception;
	
	/** 删除车辆使用记录 */
	public void deleteshopFront(String[] ids, CgAdminLog adminLog)throws Exception;

	/** 修改车辆使用记录 */
	public void updateModifyshopFront(CgCarUseRecords useRecordst,
			CgAdminLog adminLog)throws Exception;

}

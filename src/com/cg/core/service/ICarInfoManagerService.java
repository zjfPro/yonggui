package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarInfo;

public interface ICarInfoManagerService {
	
	/** 新增 */
	public void saveInfo(CgCarInfo carInfo, CgAdminLog adminLog)throws Exception;
	
	/** 删除 */
	public void deleteCarInfo(String[] ids, CgAdminLog adminLog)throws Exception;
	
	/** 更新 */
	public void updateInfo(CgCarInfo infos, CgAdminLog adminLog)throws Exception;

}

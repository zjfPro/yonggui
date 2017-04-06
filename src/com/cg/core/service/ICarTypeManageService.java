package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarType;

public interface ICarTypeManageService {
	
	/**删除车型*/
	public void deleteCarType(String[] ids,CgAdminLog adminLog)throws Exception;
	
	/** 添加车型 */
	public void addCarType(CgCarType carType,CgAdminLog adminLog)throws Exception;
	
	/** 修改 */
	public void updateCarType(CgCarType carType,CgAdminLog adminLog,String ccId)throws Exception;

}

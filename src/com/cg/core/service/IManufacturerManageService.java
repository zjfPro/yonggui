package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;

public interface IManufacturerManageService {

	/** 修改厂商 */
	public void updateManufacturer(CgCarBrand brand, String ccId, CgAdminLog adminLog)throws Exception;
	
	/** 新增厂商 */
	public void saveManufacturer(CgCarBrand carBrand, CgAdminLog adminLog)throws Exception;
	
	/** 删除厂商 */
	public void deleteSeries(String[] idarrays, CgAdminLog adminLog)throws Exception;

}

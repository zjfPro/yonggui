package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;

public interface ISeriesManagerService {
	
	/** 修改系列 */
	public void updateSeries(CgCarBrand brand, String ccId, CgAdminLog adminLog)throws Exception;
	
	/** 新增系列 */
	public void saveSeries(CgCarBrand brand2, CgAdminLog adminLog)throws Exception;

	/** 删除系列 */
	public void deleteSeries(String[] idarrays, CgAdminLog adminLog)throws Exception;
	
}

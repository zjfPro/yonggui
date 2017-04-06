package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;

public interface ICarBrandManageService {
	/** 新增品牌 */
	public void addCarBrand(CgCarBrand carBrand, CgAdminLog adminLog)throws Exception;
	
	/** 修改品牌 */
	public void updateCarBrand(CgCarBrand brand, String ccId,
			CgAdminLog adminLog)throws Exception;

	/** 删除品牌 */
	public void deleteCarBrand(String[] idarrays, CgAdminLog adminLog)throws Exception;

}

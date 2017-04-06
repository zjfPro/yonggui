package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarBrand;

public interface IModelManagerService {
	
	/** 修改型号 */
	public void updateModel(CgCarBrand brand, String ccId, CgAdminLog adminLog)throws Exception;

	/** 新增型号 */
	public void saveModel(CgCarBrand brand2, CgAdminLog adminLog)throws Exception;
	
	/** 删除型号 */
	public void deleteModel(String[] ids, CgAdminLog adminLog)throws Exception;

}

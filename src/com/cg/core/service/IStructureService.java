package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgCarStructure;

public interface IStructureService {
	
	/** 新增 */
	public void addStructure(CgCarStructure structure, CgAdminLog adminLog)throws Exception;
	
	/** 修改 */
	public void updateStructure(CgCarStructure structure, CgAdminLog adminLog,String ccId)throws Exception;
	
	/** 删除 */
	public void deleteStructure(String[] idarrays, CgAdminLog adminLog)throws Exception;

}

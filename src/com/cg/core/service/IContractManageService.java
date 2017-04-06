package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgContract;

public interface IContractManageService {
	
	/** 删除 */
	public void deleteContract(String[] ids, CgAdminLog adminLog)throws Exception;
	/** 新增 */
	public void saveContent(CgContract contract, CgAdminLog adminLog)throws Exception;
	/** 修改 */
	public void updateContent(CgContract infos, CgAdminLog adminLog)throws Exception;

}

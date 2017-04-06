package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgSetting;

public interface ISettingService {
	
	/** 新增 */
	public void addSetting(CgSetting cgSetting, CgAdminLog adminLog)throws Exception;
	
	/** 修改 */
	public void updateSetting(CgSetting sCgSetting, CgAdminLog adminLog)throws Exception;

	/** 删除 */
	public void deleteSetting(String[] ids, CgAdminLog adminLog)throws Exception;

}

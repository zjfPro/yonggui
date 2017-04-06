package com.cg.core.service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgUser;

public interface IUserService extends IBaseService<CgUser> {
	
	/**
	 * 添加会员
	 * @param cgUser
	 * @param adminLog
	 * @throws Exception 
	 */
	public void saveUser(CgUser cgUser, CgAdminLog adminLog) throws Exception;
	/**
	 * 设置管理员
	 * @param id
	 * @param userId
	 * @param adminLog
	 */
	public void updateManager(String id, String userId, CgAdminLog adminLog)throws Exception;
	
	/**
	 * 更新会员
	 * @param cgUser
	 * @param adminLog
	 * @throws Exception
	 */
	public void update(CgUser cgUser, CgAdminLog adminLog)throws Exception;
	/**
	 * 删除会员
	 * @param cgUser
	 * @param adminLog
	 */
	public void deleteUser(CgUser cgUser, CgAdminLog adminLog)throws Exception;
	
	
}

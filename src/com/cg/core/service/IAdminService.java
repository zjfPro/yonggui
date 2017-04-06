package com.cg.core.service;

import java.sql.Timestamp;

import com.cg.common.entity.CgAdmin;

public interface IAdminService {

	/**
	 * 检查登录
	 * 
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public Object checkLogin(CgAdmin admin) throws Exception;

	/**
	 * 修改用户密码
	 * 
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
	 * @param password
	 * @return 密码修改结果
	 * @throws Exception
	 */
	public String updatePwd(CgAdmin admin, String oldPassword, String newPassword, String password) throws Exception;

	/**
	 * 记录当前的登录时间和登录IP
	 * 
	 * @param id
	 *            被记录的管理id
	 * @param ip
	 *            本次登录的ip
	 * @param time
	 *            本次登录的时间
	 * @throws Exception
	 *             记录异常
	 */
	public void updateIpAndTime(String id, String ip, Timestamp time) throws Exception;
}

package com.cg.core.service;

 
import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.utils.PageUtil;

public interface IAdminManagerService {

	/**
	 * 删除管理员及其相关的数据 不能删超管
	 */
	public boolean deleteAdmin(String id) throws Exception;
	
	
	/**
	 * 删除管理员权限数据
	 */
	public boolean addAdminRole(String id,String[] ids) throws Exception;
	
	/**
	 * 获取管理员列表
	 * @param pageUtil
	 * @param findname
	 * @return
	 * @throws Exception
	 */
	public PageUtil<CgAdmin> getAdminList(PageUtil<CgAdmin> pageUtil,String findname) throws Exception;
	
	/**
	 * 获取管理员日志列表
	 * @param pageUtil
	 * @param findname
	 * @param findType 
	 * @return
	 * @throws Exception
	 */
	public PageUtil<CgAdminLog> getAdminLogList(PageUtil<CgAdminLog> pageUtil,String findname, Integer findType) throws Exception;


	/**
	 * 获取管理员
	 * @param aid
	 * @return
	 * @throws Exception
	 */
	CgAdmin getAdmin(String aid) throws Exception;


	/**
	 * 修改密码
	 * @param admin
	 * @param adminLog
	 * @throws Exception
	 */
	void updatePwd(CgAdmin admin, CgAdminLog adminLog) throws Exception;
	
	
}

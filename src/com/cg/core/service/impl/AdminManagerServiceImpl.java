package com.cg.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgAdminManageRole;
import com.cg.common.utils.PageUtil;
import com.cg.core.dao.IAdminDao;
import com.cg.core.dao.IAdminLogDao;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IAdminManagerService;

 

@Service("adminManagerService")
public class AdminManagerServiceImpl implements IAdminManagerService {

	@Resource(name = "adminDao")
	private IAdminDao adminDao;

	@Resource(name = "adminLogDao")
	private IAdminLogDao adminLogDao;

	@Autowired
	private IBaseDao baseDao;

	@Override
	public PageUtil<CgAdmin> getAdminList(PageUtil<CgAdmin> pageUtil,
			String findname) throws Exception {
		// 获取管理员列表
		String hql = "from CgAdmin ca where 1=1";
		// 参数
		List<Object> params = new ArrayList<Object>();

		if (findname != null && !"".equals(findname)) {
			hql += " and ca.nickname like ?";
			params.add("%" + findname + "%");
		}

		return this.adminDao.find(hql + " order by uid asc", params, pageUtil);
	}

	@Override
	public PageUtil<CgAdminLog> getAdminLogList(PageUtil<CgAdminLog> pageUtil,
			String findname, Integer findType) throws Exception {
		// 获取管理员日志列表
		String hql = "from CgAdminLog cal where 1=1";
		// 参数
		List<Object> params = new ArrayList<Object>();

		if (findname != null && !"".equals(findname)) {
			hql += " and cal.admin.nickname like ?";
			params.add("%" + findname + "%");
		}
		if (findType != null && findType != -1000) {
			hql += " and cal.actionType = ?";
			params.add(findType);
		}
		return this.adminLogDao.find(hql + " order by uid desc", params,
				pageUtil);
	}

	@Override
	public boolean deleteAdmin(String id) {
		try {
			// 1.删除关联的日志
			this.adminDao.executeHQL("delete CgAdminLog where adminId=? ",
					new Object[] { id });

			// 2.删除关联的权限
			this.adminDao.executeHQL(
					"delete CgAdminManageRole where userId=? ",
					new Object[] { id });

			// 3.删除admin
			this.adminDao.executeHQL("delete CgAdmin where id=? ",
					new Object[] { id });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addAdminRole(String id, String[] ids) throws Exception {
		try {
			// 清空原本的权限
			this.adminDao.executeHQL(
					"delete CgAdminManageRole where userId=? ",
					new Object[] { id });

			// 重新分配
			for (String modelId : ids) {
				CgAdminManageRole adminManageRole = new CgAdminManageRole();
				adminManageRole.setId("camr-"+UUID.randomUUID());
				adminManageRole.setAdminManageModelId(modelId);
				adminManageRole.setUserId(id);
				adminManageRole.setAddtime(new Timestamp(System
						.currentTimeMillis()));
				this.baseDao.save(adminManageRole);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public CgAdmin getAdmin(String aid) throws Exception{
		return adminDao.findEntity("from CgAdmin where id = ?", new Object[]{aid});
	}
	
	@Override
	public void updatePwd(CgAdmin admin,CgAdminLog adminLog) throws Exception{
		// 修改密码
		if(adminLog != null){
			adminLogDao.save(adminLog);
		}
		adminDao.update(admin);
	}

}

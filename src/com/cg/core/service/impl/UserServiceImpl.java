package com.cg.core.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgUser;
import com.cg.core.dao.IAdminLogDao;
import com.cg.core.dao.IBaseDao;
import com.cg.core.dao.IUserDao;
import com.cg.core.service.IUserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<CgUser> implements
		IUserService {

	@Resource(name = "userDao")
	private IUserDao userDao;
	@Resource(name="adminLogDao")
	private IAdminLogDao adminLogDao;
	@Autowired
	private IBaseDao baseDao;
	
	
	/**
	 * 添加会员
	 */
	@Override
	public void saveUser(CgUser cgUser, CgAdminLog adminLog) throws Exception {
				userDao.save(cgUser);
				if(adminLog != null){
					adminLogDao.save(adminLog);
				}
	}
	
	/**
	 * 设置管理员
	 */
	@Override
	public void updateManager(String id, String userId, CgAdminLog adminLog)throws Exception {
		// TODO Auto-generated method stub
		CgUser cgUser = this.userDao.findEntity("from CgUser csn where csn.id = ?", new Object[] { userId });
		cgUser.setId(userId);
		this.userDao.update(cgUser);
		if (adminLog != null) {
			adminLogDao.save(adminLog);
		}
	}
	
	/**
	 * 更新会员
	 */
	@Override
	public void update(CgUser cgUser, CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
		baseDao.update(cgUser);
		if(adminLog!=null){
			adminLogDao.save(adminLog);
		}
	}
	
	/**
	 * 删除会员
	 */
	@Override
	public void deleteUser(CgUser cgUser, CgAdminLog adminLog) throws Exception {
		// TODO Auto-generated method stub
		baseDao.updateForHql("delete from CgUser where id = ?", new Object[] { cgUser.getId() });
		if(adminLog!=null){
			adminLogDao.save(adminLog);
		}
	}
}

package com.cg.core.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cg.common.entity.CgAdmin;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.MD5Util;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {

	@Resource
	private IBaseDao<CgAdmin> baseDao;

	// writer interface
	@Override
	public Object checkLogin(CgAdmin loginAdmin) throws Exception {
		if (isEmpty(loginAdmin.getAccount())) {// 账号为空
			return "账号不能为空";
		}
		if (isEmpty(loginAdmin.getPassword())) {// 密码为空
			return "密码不能为空";
		}
		if (loginAdmin.getPassword().length() < 6) {
			return "密码不能小于6位";
		}
		CgAdmin cgAdmin = baseDao.findEntity("from CgAdmin where account=? and password=?",
				new Object[] { loginAdmin.getAccount(), encrypt(loginAdmin.getPassword()) });
		if (cgAdmin == null) {// 账号不存在
			return "管理员账号或密码不正确";
		}
		if (cgAdmin.getStatus() == -1) {
			return "此管理员账号账号被冻结";
		}
		return cgAdmin;
	}

	// writer interface
	@Override
	public String updatePwd(CgAdmin admin, String oldPassword, String newPassword, String password) throws Exception {
		if (isEmpty(newPassword)) {
			return "密码不能为空";
		}
		if (!newPassword.equals(password)) {
			return "密码输入不一致";
		}
		String regex = "[\\d\\w]+";
		if (!newPassword.matches(regex)) {// 密码合法性校验
			return "密码只能由数字、字母、下划线组成";
		}
		if(admin == null){
			return "修改密码失败";
		}
		CgAdmin cgAdmin = baseDao.findEntity(CgAdmin.class, admin.getId());
		if (!cgAdmin.getPassword().equals(encrypt(oldPassword))) {
			return "密码不正确";
		}
		cgAdmin.setPassword(encrypt(newPassword));
		baseDao.update(cgAdmin);
		return "1";
	}

	/**
	 * 判断给定字符串是否为空
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return true表示为空，否则为false
	 */
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 将给定的字符串加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return 密文
	 * @throws Exception
	 *             加密异常
	 */
	private String encrypt(String str) throws Exception {
		return MD5Util.string2MD5(CustomMixedEncryptionUtil.encrypt(str, str.substring(0, 6)));
	}

	// writer interface
	@Override
	public void updateIpAndTime(String id, String ip, Timestamp time) throws Exception {
		baseDao.updateForHql("update CgAdmin set lastLoginIp = ?, lastLoginDate = ? where id = ?",
				new Object[] { ip, time, id });
	}

}

package com.cg.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.cgcheck.CgCheckException;
import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgStaff;
import com.cg.common.entity.CgStaffModelMapping;
import com.cg.common.utils.CustomMixedEncryptionUtil;
import com.cg.common.utils.GetIpUtil;
import com.cg.common.utils.MD5Util;
import com.cg.core.dao.IBaseDao;
import com.cg.core.service.IStaffManagerService;

@Service
public class StaffManagerServiceImpl implements IStaffManagerService{
	@Autowired
	private IBaseDao baseDao;

	@Override
	public void addStaff(CgStaff staff, CgAdminLog adminLog, String ids)
			throws Exception {
		baseDao.save(staff);
		if(adminLog!=null){
			baseDao.save(adminLog);
		}
		if(!ids.equals("")){
			ids = ids.substring(0, ids.length()-1);
			String[] split = ids.split(",");
			for (String id : split) {
				CgStaffModelMapping csmm = new CgStaffModelMapping();
				csmm.setStaffId(staff.getId());
				csmm.setStaffManageModelId(Integer.valueOf(id));
				baseDao.save(csmm);
			}
		}
		

	}

	@Override
	public void updateStaff(CgStaff staff, CgAdminLog adminLog, String ids)
			throws Exception {
		baseDao.update(staff);
		if(adminLog!=null){
			baseDao.save(adminLog);
		}
		
		String hql = "delete from CgStaffModelMapping where staffId = ?";
		List<Object> parmas = new ArrayList<Object>();
		parmas.add(staff.getId());
		baseDao.executeHQL(hql, parmas);
		
		String[] split = ids.split(",");
		for (String id : split) {
			CgStaffModelMapping csmm = new CgStaffModelMapping();
			csmm.setStaffId(staff.getId());
			csmm.setStaffManageModelId(Integer.valueOf(id));
			baseDao.save(csmm);
		}
	}

	@Override
	public void deleteStaff(String ids,HttpSession session,HttpServletRequest request) throws Exception {
		String[] split = ids.split(",");
		for (String id : split) {
			
			CgStaff loginStaff = (CgStaff) session.getAttribute("staff");
			
			if(loginStaff!=null){
				if(loginStaff.getId().equals(id)){
					throw new CgCheckException("不能删除自己");
				}
			}
			
			String hql = "delete from CgStaff where id = ?";
			List<Object> parmas = new ArrayList<Object>();
			parmas.add(id);
			baseDao.executeHQL(hql, parmas);
			
			String hql2 = "delete from CgStaffModelMapping where staffId = ?";
			List<Object> parmas2 = new ArrayList<Object>();
			parmas2.add(id);
			baseDao.executeHQL(hql2, parmas2);
			
			
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			CgAdminLog adminLog = null;
			if (admin != null) {
				adminLog = new CgAdminLog();
				adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
				adminLog.setActionIp(GetIpUtil.getIpAddr(request));
				adminLog.setActionType(1);
				adminLog.setActionDesc("删除员工");
				adminLog.setActionContent("管理员：" + admin.getNickname() + "，删除员工：" + id);
				adminLog.setAdminId(admin.getId());
				adminLog.setAdmin(admin);
				adminLog.setId("cal-" + UUID.randomUUID().toString());
				baseDao.save(adminLog);
			}
			
			
		}
	}

	@Override
	public Object checkStaffLogin(String account, String password)
			throws Exception {
		if (isEmpty(account)) {
			return "账号不能为空";
		}
		if (isEmpty(password)) {
			return "密码不能为空";
		}
		if (password.length() < 6) {
			return "密码不能小于6位";
		}
		String mima = encrypt(password);
		CgStaff findEntity = (CgStaff) baseDao.findEntity("from CgStaff where account=? and password=?",
				new Object[] { account, mima });
		
		if(findEntity==null){
			return "员工帐号或密码错误";
		}
		
		if(findEntity.getStatus()==0){
			return "此员工帐号未启用";
		}
		if(findEntity.getStatus()==-1){
			return "此员工帐号已禁用";
		}
		if(findEntity.getStatus()==-2){
			return "此员工已离职";
		}
		if(findEntity.getStatus()==-3){
			return "此员工已删除";
		}
		
		return findEntity;
	}
	
	private String encrypt(String str) throws Exception {
		return CustomMixedEncryptionUtil.encrypt(str, str.substring(0, 6));
	}
	
	private boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}

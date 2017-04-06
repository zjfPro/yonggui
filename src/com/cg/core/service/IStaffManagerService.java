package com.cg.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgStaff;

public interface IStaffManagerService {
	public Object checkStaffLogin(String account,String password) throws Exception;
	public void addStaff(CgStaff staff,CgAdminLog adminLog,String ids) throws Exception;
	public void updateStaff(CgStaff staff,CgAdminLog adminLog,String ids) throws Exception;
	public void deleteStaff(String ids,HttpSession session,HttpServletRequest request) throws Exception;
}

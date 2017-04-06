package com.cg.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IAuthorityManagementService {

	public void addCgStaffManageRoleContainerMapping(String ids,String name,String shopFrontId) throws Exception;
	public void updateCgStaffManageRoleContainerMapping(String ids,String name,String csmrcID,String shopFrontId) throws Exception;
	public void deleteModel(String ids) throws Exception;
	public void deleteRole(HttpServletRequest request,HttpSession session,String ids) throws Exception;
}

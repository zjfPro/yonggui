package com.cg.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAdmin;
import com.cg.core.service.IAdminService;

@Controller
@RequestMapping("manager/index")
public class AdminController {

	@Resource
	private IAdminService adminService;

	@RequestMapping("modify")
	@ResponseBody
	public String updatePwd(HttpServletRequest req, String oldPassword, String newPassword, String password)
			throws Exception {
		CgAdmin admin = (CgAdmin) req.getSession().getAttribute("admin");
		return adminService.updatePwd(admin, oldPassword, newPassword, password);
	}


}

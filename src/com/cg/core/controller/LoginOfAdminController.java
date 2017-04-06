package com.cg.core.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgAdmin;
import com.cg.core.service.IAdminService;
import com.cg.core.service.IBaseService;

/**
 * 用于控制admin登录过程
 * 
 */
@Controller
public class LoginOfAdminController {
	@Autowired
	private IBaseService baseService;
	@Resource
	private IAdminService adminService;

	// 登录页
	@RequestMapping("loginOfAdmin")
	public String loginOfAdmin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		if (session.getAttribute("admin") != null||session.getAttribute("staff") != null) {
			printWriter("<script>alert('您已登录,如果想切换帐号,请先注销登录(右上角按钮)');history.back();</script>", response);
			return "index/index";
		}
		return "index/loginOfAdmin";
	}

	// 点击验证登录
	@RequestMapping("login/adminLogin")
	@ResponseBody
	public String login(HttpServletRequest req, CgAdmin admin) throws Exception {
		Object obj = adminService.checkLogin(admin);
		if (obj instanceof String) {// 登录失败
			return obj.toString();
		}
		CgAdmin cgAdmin = (CgAdmin) obj;
		HttpSession session = req.getSession();
		
		session.removeAttribute("staff");
		session.removeAttribute("admin");
		session.removeAttribute("roles");

		session.setAttribute("admin", cgAdmin);
		session.setMaxInactiveInterval(3600 * 24);

		return "1";
	}

	public void printWriter(String msgs, HttpServletResponse response)
			throws Exception {
		// 指定输出内容类型和编码
		response.setContentType("text/html;charset=utf-8");
		// 获取输出流，然后使用
		PrintWriter out = response.getWriter();
		try {
			// 直接进行文本操作
			out.print(msgs);
			out.flush();
			out.close();
		} catch (Exception ex) {
			out.println(ex.toString());
		}
	}
}

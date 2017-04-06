package com.cg.core.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cg.common.entity.CgStaff;
import com.cg.core.service.IBaseService;
import com.cg.core.service.IStaffManagerService;

/**
 * 用于控制员工(staff)登录过程
 * 
 */
@Controller
public class LoginOfStaffController {
	@Autowired
	private IBaseService baseService;
	@Autowired
	private IStaffManagerService staffManagerService;

	// 登录页
	@RequestMapping("login")
	public String gotoLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession(true);
		if (session.getAttribute("admin") != null||session.getAttribute("staff") != null) {
			printWriter("<script>alert('您已登录,如果想切换帐号,请先注销登录(右上角按钮)');history.back();</script>", response);
			return "index/index";
		}
		return "index/login";
	}

	// 点击验证登录
	@RequestMapping("login/staffLogin")
	@ResponseBody
	public String staffLogin(HttpServletRequest req, String account,String password) throws Exception {
		Object obj = staffManagerService.checkStaffLogin(account, password);
		if (obj instanceof String) {// 登录失败
			return obj.toString();
		}
		CgStaff staff = (CgStaff) obj;
		HttpSession session = req.getSession();
		
		session.removeAttribute("staff");
		session.removeAttribute("admin");
		session.removeAttribute("roles");
		
		List<String> roles = baseService.find("select cgStaffManageModel.url  from CgStaffModelMapping where staffId = ?",
				staff.getId());
		String rolesStr = roles.toString();
		session.setAttribute("roles", rolesStr);

		session.setAttribute("staff", staff);
		session.setMaxInactiveInterval(3600 * 24);

		return "1";
	}

	// 登录成功后进入后台主页
	@RequestMapping("index")
	public String gotoIndex(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.getAttribute("staff") == null&&session.getAttribute("admin") == null) {
			return "redirect:login";
		}
		return "index/index";
	}

	@RequestMapping("center")
	public String gotoMain() {
		return "index/center";

	}
	
	//注销登录
	@RequestMapping("out")
	public String out(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		//清除所有session
		session.removeAttribute("staff");
		session.removeAttribute("admin");
		session.removeAttribute("roles");
		return "redirect:login";

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

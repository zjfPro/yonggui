package com.cg.core.interceptor;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cg.common.entity.CgAdmin;
import com.cg.common.entity.CgAdminLog;
import com.cg.common.entity.CgAdminManageRole;
import com.cg.core.service.IBaseService;

/**
 * Spring拦截器
 * 
 */
public class ManageAdminInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IBaseService baseService;

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			HttpSession session = request.getSession(true);
			CgAdmin admin = (CgAdmin) session.getAttribute("admin");
			if (admin == null) {
				// 跳转登陆
				response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath() + "/manager/ic/login");
				return false;
			} else if ("admin".equals(admin.getAccount())) {
				return true;
			} else if (session.getAttribute("roles") != null) {
				String uri = request.getRequestURI();
				uri = uri.substring(uri.indexOf("manager"));
				
				String roles =   (String) session.getAttribute("roles");// 获取权限集
					if (roles.replaceAll(" ", "").indexOf(uri) > 0){
						return true;
					}
				System.out.println(uri);
				printWriter("<script>alert('您没有权限！');history.back();</script>", response);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 根据给定的权限生成一条操作日记
	 * 
	 * @param request
	 *            请求，用来获取远端计算机的IP
	 * @param admin
	 *            当前的登录的用户
	 * @param role
	 *            当前用户所拥有的权限
	 * @return {@link CgAdminLog}操作日记
	 */
	private CgAdminLog role2Log(HttpServletRequest request, CgAdmin admin, CgAdminManageRole role) {
		CgAdminLog adminLog = new CgAdminLog();
		adminLog.setId("cal-" + UUID.randomUUID());
		adminLog.setActionDate(new Timestamp(System.currentTimeMillis()));
		adminLog.setActionContent(role.getAdminManageModel().getText());
		adminLog.setActionIp(getIpAddr(request));
		adminLog.setActionDesc("系统记录");
		adminLog.setAdminId(admin.getId());
		return adminLog;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/*
	 * 输出页面
	 */
	public void printWriter(String msgs, HttpServletResponse response) throws Exception {
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

package com.cg.core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 后台登陆过滤器 zjx
 * 
 * @author Administrator
 * 
 */
public class ManageLoginInterceptor extends HandlerInterceptorAdapter {

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			HttpSession session = request.getSession(true);
			if (session.getAttribute("staff") == null&&session.getAttribute("admin") == null) {
				// 跳转登陆
				response.sendRedirect(request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath()
						+ "/login");
				return false;
			}
			
			if(session.getAttribute("staff") != null){
				String uri = request.getRequestURI();
//				uri = uri.substring(uri.indexOf("control"));
				
				String roles =   (String) session.getAttribute("roles");// 获取权限集
				
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
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

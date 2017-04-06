package com.cg.core.interceptor;

import java.io.PrintWriter;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * XSS拦截器
 * @author zjx
 * 
 */
public class XSSInterceptor extends HandlerInterceptorAdapter {

	private static final String regExp = "(.*\\s*)((<\\s*script\\s*)|(<\\s*embed\\s*)|(<\\s*style\\s*)|(<\\s*img\\s*)|(<\\s*image\\s*)|(<\\s*frame\\s*)|(<\\s*object\\s*)|(<\\s*iframe\\s*)|(<\\s*a\\s*)|(<\\s*frameset\\s*)|(<\\s*meta\\s*)|(<\\s*xml\\s*)|(<\\s*applet\\s*)|(\\s*onmouse\\s*)|(<\\s*link\\s*)|(\\s*onload\\s*)|(\\s*onblur\\s*)|(\\s*onchange\\s*)|(\\s*onclick\\s*)|(\\s*ondblclick\\s*)|(\\s*onfocus\\s*)|(\\s*onkey\\s*)|(\\s*onselect\\s*)|(\\s*alert\\s*\\())(.*\\s*)";
 
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String, String[]> params = request.getParameterMap();
 
		boolean ok=true;
		for (String[] strs : params.values()) {
			if (Pattern.compile(regExp, Pattern.CASE_INSENSITIVE).matcher(strs[0]).matches()) {
				printWriter("<script>alert('您输入格式不合法');history.back();</script>", response);
				return false;
			}
		}
	
		return ok;
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

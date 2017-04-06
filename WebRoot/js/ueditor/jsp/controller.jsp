<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>

<%@ page import="java.io.File"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.Properties"%>
<%
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	
	try {
		java.lang.StringBuffer dirPath = new java.lang.StringBuffer();
		dirPath.append(rootPath);
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("WEB-INF");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("classes");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("conf");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("path.properties");
		
		java.util.Properties pps = new java.util.Properties();
		pps.load(new java.io.FileInputStream(dirPath.toString()));
	
		out.write(new ActionEnter( request , pps.getProperty("path")+System.getProperty("file.separator")+"ueditor"+System.getProperty("file.separator") , rootPath ).exec());
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	
%>
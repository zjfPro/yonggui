<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>帮助中心-公司简介</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
</head>

<body>

<div class="head-box">
  <div class="w1200 clearfix">
    <a class="head-logo" href="<%=basePath%>pc/index"><img src="<%=basePath%>images/head_logo.png"></a>
    <div class="nav-box">
      <ul>
      	<c:if test="${!empty sessionScope.investor  }">
      		<li><a onclick="out()">注销登录</a></li>
      		<li><a href="<%=basePath%>pc/investorPc/go_to/personCenter">个人中心</a></li>
      	</c:if>
        <c:if test="${ empty sessionScope.investor  }">
       		<li><a href="<%=basePath%>pc/investorPc/go_to/investorLogin">登录</a></li>
       	</c:if>
        <li><a href="<%=basePath%>pc/about">关于邕桂</a></li>
        <li><a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a></li>
        <li><a href="<%=basePath%>control/news/pc/newsInformationList?type=0">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li class="curr"><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->


<div class="wrap-box">
  <div class="head-bgbox" style="background-image:url(images/help.jpg)"></div>
  <div class="box w1200 clearfix">
	<div class="sidebar-box">
      <div class="sidebar">
        <h2>帮助中心</h2>
        <ul>
        	 <c:if test="${helps.helpType==1}">
          	  <li class="on"><a href="<%=basePath%>pc/help?helpId=1">租车指南</a></li>
          	 </c:if>
          	  <c:if test="${helps.helpType!=1}">
          	  <li><a href="<%=basePath%>pc/help?helpId=1">租车指南</a></li>
          	 </c:if>
          	 
          	 <c:if test="${helps.helpType==2}">
          	 <li class="on"><a href="<%=basePath%>pc/help?helpId=2">租车流程</a></li>
          	 </c:if>
          	 	 <c:if test="${helps.helpType!=2}">
          	 <li><a href="<%=basePath%>pc/help?helpId=2">租车流程</a></li>
          	 </c:if>
          	 
          	  <c:if test="${helps.helpType==3}">
          	 <li class="on"><a href="<%=basePath%>pc/help?helpId=3">企业租赁</a></li>
          	 </c:if>
          	 	 <c:if test="${helps.helpType!=3}">
          	 <li><a href="<%=basePath%>pc/help?helpId=3">企业租赁</a></li>
          	 </c:if>
          	 
          	  <c:if test="${helps.helpType==4}">
          	 <li class="on"><a href="<%=basePath%>pc/help?helpId=4">长期租赁</a></li>
          	 </c:if>
          	 	 <c:if test="${helps.helpType!=4}">
          	 <li><a href="<%=basePath%>pc/help?helpId=4">长期租赁</a></li>
          	 </c:if>
          	 
          	  <c:if test="${helps.helpType==5}">
          	 <li class="on"><a href="<%=basePath%>pc/help?helpId=5">短期租赁</a></li>
          	 </c:if>
          	 	 <c:if test="${helps.helpType!=5}">
          	 <li><a href="<%=basePath%>pc/help?helpId=5">短期租赁</a></li>
          	 </c:if>
        </ul>
      </div>
    </div>
    <!--sidebarbox end-->
    <div class="main-box">
      <div class="position-box">
    	<h2>您现在在的位置：<a class="on" href="<%=basePath%>pc/index">首页</a> > <a class="on" href="<%=basePath%>pc/help?helpId=1">帮助中心</a>
 	  </div>
      <div class="us-box">
        <h2 style="text-align:center;font-size:30px">${helps.title}</h2>
        <a>${helps.content}</a>
      </div>
    </div>
  </div>
</div>

<div class="footer-box">
  <div class="w1200">
    <div class="qr-codebox">
      <div class="cr-code"><img src="<%=basePath%>images/footer_qr_code.jpg"></div>
    </div>
    <div class="footer">
      <div class="firend-link">
        <a href="<%=basePath%>pc/index">首页</a>
        <a href="<%=basePath%>pc/about">公司介绍</a>
        <a href="<%=basePath%>pc/carList">车型展示</a>
        <a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a>
        <a id="retroaction" href="javascript:">客户反馈</a>
        <a href="<%=basePath%>pc/about">关于邕桂</a>
        <a href="<%=basePath%>pc/help?helpId=1">帮助中心</a>
      </div>
      <div class="subfooter">
        <p>
          <span>联系电话：(0771)2867380</span>
          <span>邮箱：123456789@qq.com</span>
          <span>地址：广西首府南宁民族大道12号丽原·天际A座32楼A3207</span>
        </p>
        <p>
          <span>广西邕桂汽车服务有限公司</span>
          <span>蜀ICP备14022404号</span>
        </p>
      </div>
    </div>

  </div>

</div>    
  <%@include file="/WEB-INF/views/include/fankui.jsp"%>
  
</body>
</html>
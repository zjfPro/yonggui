<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>关于邕桂-公司简介</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
</head>

<body>

<div class="head-box">
  <div class="w1200 clearfix">
    <a class="head-logo" href="index.html"><img src="<%=basePath%>images/head_logo.png"></a>
    <div class="nav-box">
      <ul>
        <li><a href="<%=basePath%>pc/about">关于邕桂</a></li>
        <li><a href="activity-list.html">公司活动</a></li>
        <li><a href="new-list.html">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li class="curr"><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->


<div class="wrap-box">
  <div class="head-bgbox" style="background-image:url(<%=basePath%>images/head_bg_us.jpg)"></div>


  <div class="box w1200 clearfix">
	<div class="sidebar-box">
      <div class="sidebar">
        <h2>关于邕桂</h2>
        <ul>
         <c:if test="${abouts.id==1}">
          <li class="on"><a href="<%=basePath%>pc/contact?aboutId=1">联系方式</a></li>
         </c:if> 
         <c:if test="${abouts.id!=1}">
          <li><a href="<%=basePath%>pc/contact?aboutId=1">联系方式</a></li>
         </c:if>
         <c:if test="${abouts.id!=1}">
           <li class="on"><a href="<%=basePath%>pc/about">公司简介</a></li>
         </c:if>
         <c:if test="${abouts.id==1}">
           <li><a href="<%=basePath%>pc/about">公司简介</a></li>
         </c:if>
        </ul>
      </div>
    </div>
    <!--sidebarbox end-->
    <div class="main-box">
      <div class="position-box">
    	<h2>您现在在的位置：<a  href="<%=basePath%>pc/index">首页</a> > <a>关于邕桂</a> > <a class="on" href="<%=basePath%>pc/contact?aboutId=1">联系方式</a></h2>
 	  </div>
      <div class="us-box">
       <h2 style="text-align:center;font-size:30px">${abouts.title}</h2>
      <a style="text-align:center;">${abouts.content}</a>
      </div>
      <div class="contact-box clearfix">
        <div class="subcontact">
          <p>地址|Add：${abouts.address }</p>
          <p>手机|Mob:${abouts.phone }</p>
          <p>电话热线：${abouts.oftenPhones }</p>
          <p>邮编|Zip：${abouts.postcode }</p>
          <p>邮箱|Fax：${abouts.wx }</p>
        </div>
        <div class="contact-qr">
          <div class="sub-qr sub-qr-left"><img src="<%=basePath%>images/qr_img01.jpg"></div>
          <div class="sub-qr sub-qr-right"><img src="<%=basePath%>images/qr_img02.jpg"></div>
        </div>
        
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

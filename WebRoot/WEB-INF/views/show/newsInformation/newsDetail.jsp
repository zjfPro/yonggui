<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>新闻详情</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
</head>
<script type="text/javascript">
	function out(){
		var queren = confirm("确认要注销登录吗?");
    	if(queren){
    		window.location.href="<%=basePath%>pc/investorPc/out";
    	}
	}
	
</script>
<body>

<div class="head-box">
  <div class="w1200 clearfix">
    <a class="head-logo" href="index.html"><img src="images/head_logo.png"></a>
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
        <li class="curr"><a href="<%=basePath%>control/news/pc/newsInformationList?type=0">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->


<div class="wrap-box">
  <div class="head-bgbox" style="background-image:url(images/head_bg_news.jpg)"></div>


  <div class="box w1200 clearfix">
	<div class="sidebar-box">
      <div class="sidebar">
        <h2>新闻资讯</h2>
        <ul>
          <li <c:if test="${requestScope.type==0}"> class="on"</c:if> >
          	<a href="<%=basePath%>control/news/pc/newsInformationList?type=0">公司新闻</a>
          </li>
          <li <c:if test="${requestScope.type==1}"> class="on"</c:if> >
          	<a href="<%=basePath%>control/news/pc/newsInformationList?type=1">行业资讯</a>
          </li>
        </ul>
      </div>
    </div>
    <!--sidebarbox end-->
    <div class="main-box">
      <div class="position-box">
    	<h2>您现在在的位置：<a href="#">首页</a> > <a class="on" href="#">新闻资讯</a></h2>
 	  </div>
      
      <div class="news-list">
        <div class="n-detail-title">
          <h3>${requestScope.fnotice.title}</h3>
          <p><em>来源：${requestScope.fnotice.admin.nickname}</em><em>发布日期：${requestScope.fnotice.addtime}</em></p>
        </div>
        <div class="n-detail-cont">
			${requestScope.fnotice.noticeContent}
        </div>
        <!-- <div class="n-detail-list">
          <p><em>上一篇：</em><a href="news-detail.html">活动习近平主持中共中央政治局会议</a></p>
          <p><em>下一篇：</em><a href="news-detail.html">活动庆祝中国共产党成立95周年大会将于7月1日上午在京举行</a></p>
        </div> -->
      </div>
      
      
    </div>
  </div>
</div>
      
      
      


<div class="footer-box">
  <div class="w1200">
    <div class="qr-codebox">
      <div class="cr-code"><img src="images/footer_qr_code.jpg"></div>
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
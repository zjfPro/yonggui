<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>产品中心</title>
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
  <div class="position-box w1200">
    <h2>您现在在的位置：<a href="<%=basePath%>pc/index">首页</a> > <a href="<%=basePath%>pc/flow">租车流程</a></h2>
  </div>
</div>

<div class="product-box">
  <ul>
  <li class="subdouble">
      <div class="w1200">
        <div class="product-img"><img src="<%=basePath%>images/business_bigimg02.png"></div>
        <div class="product-info">
          <h2>租车流程</h2>
          <div class="pro-text">
            <p><img src="<%=basePath%>images/zuche_01.png"></p>
          </div>
        </div>       
      </div>
    </li>
  </ul>
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

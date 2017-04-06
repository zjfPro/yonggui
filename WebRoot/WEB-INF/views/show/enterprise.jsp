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
    <h2>您现在在的位置：<a href="<%=basePath%>pc/index">首页</a> > <a href="<%=basePath%>pc/enterprise">企业租车</a></h2>
  </div>
</div>

<div class="product-box">
  <ul>
    <li class="subsingle">
      <div class="w1200">
        <div class="product-img"><img src="<%=basePath%>images/business_bigimg03.png"></div>
        <div class="product-info">
          <h2>企业租赁</h2>
          <div class="pro-text">
            <p>（1）半日租 ：以车辆使用时间4小时以内的车辆租用期为单位；</p>
            <p>（2）日&nbsp&nbsp&nbsp&nbsp租 ：日租以24小时为基本租用期。仿效机票价格体系，根据预定的提前天数、租期的天数、
            				提车的城市门店、用车的时间段的不同，享受不同的折扣，最低5折起。 越提前预定，折扣越大；租期越长，折扣越大；</p>
            <p>（3）月&nbsp&nbsp&nbsp&nbsp租 ：至尊租车为了方便了客户更长租车租期的需求，提供以月为单位的租车模式，根据提车门店、用车时间段的不同，
            				享受不同的优惠价格。月租价格相比日租价格更加便宜，只需支付一个月租金和一个月押金；</p>
            <p>（4）年&nbsp&nbsp&nbsp&nbsp租 ：以年为单位的基本租用期，按月结算，价格比月租更优惠。</p>
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

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
<title>个人中心-订单详情</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
</head>
<script type="text/javascript">
	function out(){
		var queren = confirm("确认要注销登录吗?");
    	if(queren){
    		window.location.href="<%=basePath%>pc/investorPc/out";
    	}
	}

</script>
<body class="bg-f6fafb">

<div class="head-box">
  <div class="w1200 clearfix">
    <a class="head-logo" href="index.html"><img src="images/head_logo.png"></a>
    <div class="nav-box">
      <ul>
        <c:if test="${!empty sessionScope.investor  }">
      		<li><a onclick="out()">注销登录</a></li>
      		<li  class="curr"><a href="<%=basePath%>pc/investorPc/go_to/personCenter">个人中心</a></li>
      	</c:if>
      	<c:if test="${ empty sessionScope.investor  }">
      		<li><a href="<%=basePath%>pc/investorPc/go_to/investorLogin">登录</a></li>
      	</c:if>
        <li><a href="<%=basePath%>pc/about">关于邕桂</a></li>
        <li><a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a></li>
        <li><a href="<%=basePath%>control/news/pc/newsInformationList?type=0">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->


<div class="user-box">
  <div class="user-wrap w1200">
    <div class="ord-head">
      <div class="ord-subhead">订单总价：<span class="money">￥${requestScope.ccro.buyMoney }</span></div>
      <p>订单号：${requestScope.ccro.number }<em>|</em>租车人：${requestScope.ccro.userName } <em>|</em>下单时间：${requestScope.ccro.addtime }</p>
    </div>
    <div class="ord-body">
      <div class="ord-tab-dtl">
        <table class="ord-dtl-tab">
          <thead>
            <tr>
              <th colspan="3">基本信息</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td rowspan="2"><div class="ord-info-img"><img src="${requestScope.ccro.cgCarRentalItem.cgCarInfo.picture }"></div></td>
              <td style="border-left:1px solid #dbe4eb;">取车时间：</td>
              <td style="border-left:1px solid #dbe4eb;">取车地点：</td>
            </tr>
            <tr>
              <td style="border-left:1px solid #dbe4eb;">还车时间：</td>
              <td style="border-left:1px solid #dbe4eb;">还车地点：</td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="3">车辆名称：${requestScope.ccro.cgCarRentalItem.cgCarInfo.carType.carBrand.info }  车牌号：${requestScope.ccro.cgCarRentalItem.cgCarInfo.plateNumber }</td>
            </tr>
          </tfoot>
        </table>
      </div>
      <div class="cost-detail">
        <div class="cost-head">
          <h2>费用明细</h2>
        </div>
        <div class="cost-body">
          <ul>
            <li>
              <p>车辆租赁费及门店服务费</p>
              <span class="money">￥</span>
            </li>
            <li>
              <p>基本保险费</p>
              <span class="money">￥</span>
            </li>
            <li>
              <p>手续费</p>
              <span class="money">￥</span>
            </li>
          </ul>
        </div>
        <div class="cost-tfoot">
          合计：<span class="money">￥${requestScope.ccro.buyMoney }</span>
        </div>
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
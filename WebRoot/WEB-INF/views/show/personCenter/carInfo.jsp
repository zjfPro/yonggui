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
<title>个人中心-车辆信息</title>
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
	function pageTurning(pageNo,totalPages,bOrn){
		if(pageNo==1&&bOrn=='back'){
			return;
		}else if(pageNo==totalPages&&bOrn=='next'){
			return;
		}else{
    		window.location.href="<%=basePath%>pc/investorPc/carInfo?page="+pageNo+"&click="+bOrn;
		};
	
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
  <div class="u-head w1200">
    <a href="#">首页</a><span>></span><span>车辆信息</span>
  </div>
  <div class="user-wrap w1200 clearfix">
    <div class="user-sidebar-box">
      <div class="user-sidebar clearfix">
        <ul>
          
          <li><a href="<%=basePath%>pc/investorPc/go_to/personCenter">订单管理</a></li>
          <li><a href="">财务管理</a></li>
          <li class="on"><a href="<%=basePath%>pc/investorPc/carInfo">车辆信息</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carUseRecord">车辆使用</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carUpkeep">车辆保养</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carInsurance">车辆保险</a></li>
          <li><a href="<%=basePath%>pc/investorPc/investorInfo">个人信息</a></li>
          
        </ul>
      </div>
    </div>
    <!--user-sidebar-box end-->
    <div class="user-main">
      <div class="cart-infobox">
        <div class="cart-info">
          <ul>
          	<c:forEach items="${requestScope.pageUtil.datas}" var="data" varStatus="status">
          		<li class="clearfix">
	              <div class="cart-info-img"><img src="${data.carInfo.picture }"></div>
	              <div class="cart-subinfo">
	                <dl>
	                  <dt>车牌号码：</dt>
	                  <dd>${data.carInfo.plateNumber } </dd>
	                </dl>
	                <dl>
	                  <dt>车辆类型：</dt>
	                  <dd>${data.carInfo.carType.typeName } </dd>
	                </dl>
	                <dl>
	                  <dt>添加时间：</dt>
	                  <dd>${data.carInfo.addtime } </dd>
	                </dl>
	                <dl>
	                  <dt>签约开始时间：</dt>
	                  <dd>${data.carInfo.startTimeOfContract } </dd>
	                </dl>
	                <dl>
	                  <dt>签约结束时间：</dt>
	                  <dd>${data.carInfo.endTimeOfContract } </dd>
	                </dl>
	              </div>
	            </li>
          	</c:forEach>
          </ul>
        </div>
        <div class="page-box">
          <a onclick="pageTurning('${requestScope.pageUtil.page }','${requestScope.pageUtil.totalPages}','back')">上一页</a>
          	${requestScope.pageUtil.page}/${requestScope.pageUtil.totalPages}
          <a onclick="pageTurning('${requestScope.pageUtil.page }','${requestScope.pageUtil.totalPages}','next')">下一页</a>
        </div>
      </div>
      
    </div>
    <!--user-main end-->
  
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
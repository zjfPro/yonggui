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
<title>车型展示</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
<link rel="stylesheet" href="oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="oa/css/list.css" type="text/css" />
<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>
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
			<a class="head-logo" href="index.html"><img
				src="<%=basePath%>images/head_logo.png"> </a>
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
			       <li class="curr"><a href="<%=basePath%>pc/carList">车型展示</a></li>
			       <li><a href="<%=basePath%>pc/index">首页</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!--head-box end-->


	<div class="wrap-box">
		<div class="head-bgbox" style="background-image:url(<%=basePath%>images/show.jpg)"></div>
		<div class="position-box w1200">
			<h2>
				您现在在的位置：<a href="<%=basePath%>pc/index">首页</a> > <a class="on" href="<%=basePath%>pc/carList">车型展示</a>
			</h2>
		</div>
		<form id="topageform" name="topageform" method="post"
			action="<%=basePath%>pc/carList">

			<div class="typebox w1200">
				<dl>
					<dt>车型类别</dt>
					<dd class="subtype">
						<div class="car-classify">
							<c:if test="${requestScope.carType!=null}">
							
								<a href="<%=basePath%>pc/carList">全部</a> 
							</c:if>
							<c:if test="${requestScope.carType==null}">
							
								<a class="on" href="<%=basePath%>pc/carList">全部</a> 
							</c:if>
							<c:forEach items="${requestScope.structure}" var="data" varStatus="status">
							
								<c:if test="${requestScope.carType==data.name}">
									<a class="on" href="<%=basePath%>pc/carList?carType=${data.name}">${data.name}</a> 
								</c:if>
								<c:if test="${requestScope.carType!=data.name}">
									<a href="<%=basePath%>pc/carList?carType=${data.name}">${data.name}</a> 
								</c:if>
							</c:forEach>
							
						</div>
					</dd>
				</dl>
			</div>
			<div class="car-list w1200">
				<ul>

			<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">

					<li class="clearfix">
						<div class="clist-img">
							<a><img src="${data.cgCarInfo.picture}"> </a>
						</div>
						<div class="clist-info">
							<h2>
								<a>${data.cgCarInfo.carType.carBrand.info}</a>
							</h2>
							<div class="subclist">
								<dl>
									<dt>价格：</dt>
									<dd>￥${data.unitPrice} / ${data.unitNumber}${data.unitName}</dd>
								</dl>
								
								<dl>
									<dt>押金：</dt>
									<dd>￥${data.foregiftMoney}</dd>
								</dl>
								<p class="site">取车地点：${data.cgShopFront.address}</p>
							</div>
						</div>
						<div class="clist-btn">
							<a href="pc/itemDetail?itemID=${data.id}">查看详情</a>
						</div>
					</li>

			</c:forEach>

				</ul>

			</div>
			
					<table>
					<thead></thead><tbody></tbody>
					
					<tfoot>
							<tr>
								<td colspan="16">
								<%@include file="/WEB-INF/views/include/showPage.jsp"%></td>
							</tr>
						</tfoot>
					</table>
			
		</form>
	</div>



	<div class="footer-box">
		<div class="w1200">
			<div class="qr-codebox">
				<div class="cr-code">
					<img src="images/footer_qr_code.jpg">
				</div>
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
						<span>联系电话：(0771)2867380</span> <span>邮箱：123456789@qq.com</span> <span>地址：广西首府南宁民族大道12号丽原·天际A座32楼A3207</span>
					</p>
					<p>
						<span>广西邕桂汽车服务有限公司</span> <span>蜀ICP备14022404号</span>
					</p>
				</div>
			</div>
		</div>
	</div>

<%@include file="/WEB-INF/views/include/fankui.jsp"%>
</body>
</html>
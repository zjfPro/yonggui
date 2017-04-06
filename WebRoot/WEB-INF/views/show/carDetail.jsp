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
<title>车型展示--详情</title>
<link rel="stylesheet" href="css/style.css">
<script src="js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
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
				src="images/head_logo.png">
			</a>
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
		<div class="position-box w1200">
			<h2>
				您现在在的位置：<a href="<%=basePath%>pc/index">首页</a> > <a class="on" href="<%=basePath%>pc/carList">车型展示</a> > ${item.cgCarInfo.carType.carBrand.info}
			</h2>
		</div>
		<div class="head-car clearfix">
			<div class="head-car-img">
				<img src="${item.cgCarInfo.picture}">
			</div>
			<div class="clist-info">
				<h2>${item.cgCarInfo.carType.carBrand.info}</h2>
				<div class="subclist clearfix">
					<dl>
						<dt>价格：</dt>
						<dd>￥${item.unitPrice} / ${item.unitNumber}${item.unitName}</dd>
					</dl>

					<dl>
						<dt>押金：</dt>
						<dd>￥${item.foregiftMoney}</dd>
					</dl>
				</div>
				<p class="site">地点：${item.cgShopFront.address}</p>
				<p>
					<a class="reserva-btn" id="reservaBtn">立即预定</a>
				</p>
			</div>
		</div>

		<div class="car-detali w1200">
			<div class="car-title">
				<h2>车况介绍</h2>
			</div>
			<div class="car-main">
				<p>${item.title}</p>
				${item.content}

			</div>
		</div>


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

	<!-----立即预定表单 弹窗-------->
	<div class="maskbox" id="maskBox"></div>
	<form action="" method="post" id="carForm">
		<input name="itemID" value="${item.id}" />
		<div class="attendbox" id="attendBox">
			<div class="attend">
				<span class="close-att" id="closeAtt"></span>
				<div class="att-head">
					<h2>请填写当前信息</h2>
				</div>
				<div class="att-body">
					<dl>
						<dt>车辆名称：</dt>
						<dd>
							<h3>${item.cgCarInfo.carType.carBrand.info}</h3>
						</dd>
					</dl>
					<dl>
						<dt>价格：</dt>
						<dd>
							<span class="price">￥${item.unitPrice} /
								${item.unitNumber}${item.unitName}</span>
						</dd>
					</dl>
					<dl>
						<dt>取车地点：</dt>
						<dd>
							<h3>${item.cgShopFront.address}</h3>
						</dd>
					</dl>
					<dl>
						<dt>姓名：</dt>
						<dd>
							<input id="userName" class="att-text" type="text"
								placeholder="请输入您的真实姓名" name="userName"> <span
								class="att-hidden" id="uNPrompt"></span>
						</dd>
					</dl>
					<dl>
						<dt>联系方式：</dt>
						<dd>
							<input id="userPhone" class="att-text" type="text"
								placeholder="请输入您的联系方式" name="userPhone"> <span
								class="att-hidden" id="uPPrompt"></span>
						</dd>
					</dl>

					<dl>
						<dt>身份证号码：</dt>
						<dd>
							<input id="userNumber" class="att-text" type="text"
								placeholder="请输入您的身份证号" name="userNumber"><span class="att-hidden"
								id="uNumPrompt"></span>
						</dd>
					</dl>
					<dl>
					
			        <dt>租车时间：</dt>
			        <dd>
			          <span class="ico-sel"><i></i><input id="borrowTime" class="att-text" type="text" readonly onClick="WdatePicker()" />
			          </span><span class="att-hidden" id="uBPrompt"></span>
			        </dd>
			      </dl>
			      <dl>
			        <dt>还车时间：</dt>
			        <dd>
			          <span class="ico-sel"><i></i><input id="alsoTime" class="att-text" type="text" readonly onClick="WdatePicker()" /></span>
			          <span class="att-hidden" id="uAPrompt"></span>
			        </dd>
			      </dl>

					<dl>
						<dt></dt>
						<dd>
							<input id="attendBtn" class="att-btn" type="button" value="提交">
						</dd>
					</dl>
				</div>

			</div>
		</div>
	</form>
	<script>
		$(function() {
			$("#reservaBtn").click(function() {
				$("#attendBox").show();
				$("#maskBox").show();
				$('#attendBtn').show();
			})
			$("#closeAtt").click(function() {
				$("#attendBox").hide();
				$("#maskBox").hide();
			})
			$("#attendBtn").click(function() {
				reservaVerify();

			})
			$("#certificate").live("change", "focus", function() {
				var val = $("#certificate option:selected").attr("value");
				$("#certificateNum").val(val);
			})
		})
		function reservaVerify() {
			
			var borrowTime = $("#borrowTime");
			var uBPrompt = $("#uBPrompt");
			var alsoTime = $("#alsoTime");
			var uAPrompt = $("#uAPrompt");
			var userName = $("#userName");
			var uNPrompt = $("#uNPrompt");
			var userPhone = $("#userPhone");
			var uPPrompt = $("#uPPrompt");
			var certificateNum = $("#certificateNum");
			var userNumber = $("#userNumber");
			var uNumPrompt = $("#uNumPrompt");

			if (userName.val() == "" || userName.val() == null) {
				uNPrompt.html("请输入您的姓名");
				userName.focus();
				return;
			}
			uNPrompt.html("");

			if (userPhone.val() == "" || userPhone.val() == null) {
				uPPrompt.html("请输入您的手机号");
				userPhone.focus();
				return;
			}
			uPPrompt.html("");

			if (userNumber.val() == "" || userNumber == null) {
				userNumber.focus();
				uNumPrompt.html("请输入您的身份证号码");
				return;
			}
			uNumPrompt.html("");
			if (!(/(^[1-9]{1}[0-9]{14}$)|(^[1-9]{1}[0-9]{17}$)|(^[1-9]{1}[0-9]{16}([0-9]|[xX])$)/)
					.test(userNumber.val())) {
				userNumber.focus();
				uNumPrompt.html("身份证号码格式有误，请重新输入");
				return;
			}
			uNumPrompt.html("");
			
			if(borrowTime.val()=="" || borrowTime.val()==null){
				uBPrompt.html("请选择租车时间");
				borrowTime.focus();
				return;
			}
			uBPrompt.html("");
			
			if(alsoTime.val()=="" || alsoTime.val()==null){
				uAPrompt.html("请选择还车时间");
				alsoTime.focus();
				return;
			}
			uAPrompt.html("");
			
			$.ajax({
				  async: false,
				  type: "POST",
				  data: $("#carForm").serialize(),
				  url: "<%=basePath%>pc/addItemOrder",
				  success: function(data){
				 	if(data == "1"){
				 		alert("预定成功，请到门店所在位置办理手续，谢谢");
				 		$('#attendBtn').hide();
					}else{
						alert(data);
					}
				}
			});

		}
	</script>


</body>
</html>
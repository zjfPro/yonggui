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
<title>个人中心-个人信息</title>
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
    <a href="#">首页</a><span>></span><span>财务管理</span>
  </div>
  <div class="user-wrap w1200 clearfix">
    <div class="user-sidebar-box">
      <div class="user-sidebar clearfix">
        <ul>
          <li><a href="<%=basePath%>pc/investorPc/go_to/personCenter">订单管理</a></li>
          <li><a href="financial-management.html">财务管理</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carInfo">车辆信息</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carUseRecord">车辆使用</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carUpkeep">车辆保养</a></li>
          <li><a href="<%=basePath%>pc/investorPc/carInsurance">车辆保险</a></li>
          <li class="on"><a href="<%=basePath%>pc/investorPc/investorInfo">个人信息</a></li>
        </ul>
      </div>
    </div>
    <!--user-sidebar-box end-->
    <form name="form" id="myForm" method="post" >
    <div class="user-main">
      <div class="withdrawal-box">
        <ul>
 		  <li>
            <label>姓名</label>
            <input type="text" name="id" id="investorId" value="${requestScope.investor.id }" style="display:none">
            <input type="text" class="wit-text" name="name" readonly="readonly" style="background:gray"
            		id="userName" placeholder="请填写真实姓名" value="${requestScope.investor.name }">
            <span class="hint" id="hintName"></span>
          </li>
          <li>
            <label>身份证</label>
            <input type="text" class="wit-text" name="idcard" readonly="readonly" style="background:gray"
            		id="userID" placeholder="请输入身份证号码" value="${requestScope.investor.idcard }">
            <span class="hint" id="hintID"></span>
          </li>
          <li>
            <label>手机号码</label>
            <input type="text" class="wit-text" name="phone" id="userPhone" placeholder="请输入手机号码" value="${requestScope.investor.phone }">
            <span class="hint" id="hintPhone"></span>
          </li>
          <li id="sexradio">
            <label>性别</label>
            <span class="sex"><input type="radio" name="sex" id="boy" value="1" <c:if test="${requestScope.investor.sex ==1 }">checked='checked'</c:if> >
            	<label for="boy"> 男</label>
            </span>
            <span class="sex"><input type="radio" name="sex" id="girl" value="2" <c:if test="${requestScope.investor.sex ==2 }">checked='checked'</c:if> >
            	<label for="girl"> 女</label>
            </span>
            <span class="sex"><input type="radio" name="sex" id="secrecy" value="0" <c:if test="${requestScope.investor.sex ==0 }">checked='checked'</c:if > >
            	<label for="secrecy"> 保密</label>
            </span>
          </li>
          <li>
            <label>地址</label>
            <input type="text" class="wit-text" name="address" id="address" value="${requestScope.investor.address }">
          </li>
          
		  <li>
            <label>&nbsp;</label>
            <input class="withdrawalBtn" id="userInfoBtn" type="button" value="保存">
          </li>
        </ul>
      </div>
    </div>
    </form>
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
  
<script>
$(function(){
	$("#userInfoBtn").click(function(){
		userInfoVerify();
	})
	
})
function userInfoVerify(){
	var userName=$("#userName");
	var investorId=$("#investorId");
	var userID=$("#userID");
	var userPhone=$("#userPhone");
	var address=$("#address");
	
	var hintName=$("#hintName");
	var hintID=$("#hintID");
	var hintPhone=$("#hintPhone");
	
	if(userName.val()==null || userName.val()==""){
		userName.focus();
		hintName.html("姓名不能为空，请输入您的真实姓名");
		return;
	}
	hintName.html("");
	
	if(userID.val()==null || userID.val()==""){
		userID.focus();
		hintID.html("身份证不能为空，请输入身份证号码");
		return;
	}
	hintName.html("");
	if(!(/(^[1-9]{1}[0-9]{14}$)|(^[1-9]{1}[0-9]{17}$)|(^[1-9]{1}[0-9]{16}([0-9]|[xX])$)/).test(userID.val())){
		userID.focus();
		hintID.html("身份证不能为空，请输入身份证号码");
		return;
	}
	hintName.html("");
	
	if(userPhone.val()==null || userPhone.val()==""){
		userPhone.focus();
		hintPhone.html("手机号不能为空，请输入手机号");
		return;
	}
	hintPhone.html("");
	if(!(/^([1][3-8])\d{9}$/.test(userPhone.val()))){
		userPhone.focus();
		hintPhone.html("手机号格式有误，请重新输入");
		return;
	}
	hintPhone.html("");
	
	var sex = $('#sexradio input[type="radio"]:checked').val();
	$.ajax({
		type : "POST",
		url : "<%=basePath%>pc/investorPc/updateInfo",
		data : {"id":investorId.val(),
				"name":userName.val(),
				"idcard":userID.val(),
				"phone":userPhone.val(),
				"sex":sex,
				"address":address.val()
				},
		success : function(msg) {
			if(msg=="ok"){
				alert("保存成功");
			}else{
				alert("操作失败");
			}
		}
	});
	
}
</script>
<%@include file="/WEB-INF/views/include/fankui.jsp"%>
</body>
</html>
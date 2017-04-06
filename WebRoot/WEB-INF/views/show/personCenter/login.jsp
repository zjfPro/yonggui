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
<title>首页</title>
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
      		<li><a href="<%=basePath%>pc/investorPc/go_to/personCenter">个人中心</a></li>
      	</c:if>
      	<c:if test="${ empty sessionScope.investor  }">
      		<li class="curr"><a href="<%=basePath%>pc/investorPc/go_to/investorLogin">登录</a></li>
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

<div class="login-box">
  <div class="formbox">
    <div class="subform">
      <form id="pcLoginForm">
        <h2>欢迎登录</h2>
        <ul>
          <li>
            <div class="ico_user"><input class="form-text" id="user" type="text" name="account"></div>
            <p class="hint" id="hintUser"></p>
          </li>
          <li>
            <div class="ico_password"><input class="form-text" id="uPw" type="password" name="password"></div>
            <p class="hint" id="hintPW"></p>
          </li>
        </ul>
        <div class="form-btm">
          <input type="checkbox" checked="checked" id="remember">
          <label for="remember">30天内自动登录</label>
          <!--<a class="pw-link" href="#">忘记密码？</a>-->
        </div>
        <div class="form-btn">
          <input class="login-btn" id="loginBtn" type="button" value="立即登录">
        </div>
      </form>
    </div>
  </div>

</div>
<!--login-box end-->

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
	$("#loginBtn").click(function(){
		var user=$("#user");
		var uPw=$("#uPw");
		var hintUser=$("#hintUser");
		var hintPW=$("#hintPW");
		
		if(user.val()=="" || user.val()==null){
			hintUser.text("请输入用户名");
			user.focus();
			return;
		}
		hintUser.html("");
		
		if(uPw.val()=="" || uPw.val()==null){
			hintPW.html("请输入登录密码");
			uPw.focus();
			return;
		}
		hintPW.html("");
		
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#pcLoginForm").serialize(),
			  url: "<%=basePath%>pc/investorPc/login/investorLogin",
			  success: function(data){
			 	if(data == "1"){
			 		location.href = "<%=basePath%>pc/index";
				}else{
					alert(data);
				}
			}
		});
		
	})
})
</script>
<%@include file="/WEB-INF/views/include/fankui.jsp"%>
</body>
</html>
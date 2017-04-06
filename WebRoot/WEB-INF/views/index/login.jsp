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
<title>登录</title>
<script src="oa/js/jquery-1.8.0.min.js"></script>
<style>
*{margin:0; padding:0;}
html,body{height:100%;}
body{font-family:"宋体"; font-size:12px; color:#333; background:url(oa/images/login_bg.jpg) no-repeat #0290ea center center;}
ul,li{list-style:none;}
img{border:none;}
a{color:#333; text-decoration:none;}
a:hover{color:#333; text-decoration:none;}
input{font-size:12px;}
.login-box{width:422px; height:283px; position:absolute; top:50%; left:50%; margin:-141px 0 0 -211px; background:url(oa/images/login_form.png) no-repeat;}
.login-box ul{padding:74px 0 0 125px;}
.login-box li{position:relative; height:20px; padding-bottom:16px;}
.login-box li .item{font-weight:bold; color:#2a4a61; display:inline-block; width:51px;}
.login-box li .inp-txt{width:140px; height:18px; line-height:18px; padding:0 3px; border:1px solid #a5b7ca; background-color:#fff;}
.login-box li .inp-btn{height:20px; padding:0 15px; border:1px solid #bac4cd; background-color:#d9e2eb; color:#2a4a61; border-radius:2px; overflow:hidden; cursor:pointer;}
.login-box li .inp-btn.col{border:1px solid #195d86; background-color:#2d8bbf; color:#fff;}
.login-box li *,.login-box li label *{vertical-align:middle;}
.login-box .hint{position:absolute; bottom:0; color:#F00; margin-left:51px;}


</style>
</head>

<body>
<div class="login-box">
  <form id="loginForm">
    <ul>
      <li><label class="item">用户名</label><input type="text" class="inp-txt" id="userName" name="account"><p class="hint" id="hintName"></p></li>
      <li><label class="item">密&ensp;&ensp;码</label><input type="password" class="inp-txt" id="userPassword" name="password">
      <p class="hint" id="hintPassword"></p></li>
      <li style="padding-left:51px; color:#2a4a61;"><label><input type="checkbox" class="inp-che"><span>&ensp;记住密码</span></label></li>
      <li style="padding-left:51px;"><input type="button" class="inp-btn col" id="loginBtn" value="员工登录">&ensp;&ensp;
      <input type="button" class="inp-btn" id="loginBtnAdmin" value="管理员登录">
      </li>
    </ul>
  </form>
</div>
<script>
function adminLogin(){
	window.location.href="<%=basePath%>loginOfAdmin";
}

$(function(){
	$("#loginBtn").click(function(){
		loginVerify("0");
	})
	$("#loginBtnAdmin").click(function(){
		loginVerify("1");
	})
})
function loginVerify(butType){
	var userName=$("#userName");
	var userPassword=$("#userPassword");
	var hintName=$("#hintName");
	var hintPassword=$("#hintPassword");
	
	if(userName.val()=="" || userName.val()==null){
		hintName.html("请输入用户名");
		userName.focus();
		return false;
	}
	hintName.html("");
	
	if(userPassword.val()=="" || userPassword.val()==null){
		hintPassword.html("请输入密码");
		userPassword.focus();
		return false;
	}
	hintPassword.html("");
	
	if(butType=="0"){
		$.ajax({
			  async: false,
			   type: "POST",
			   data: $("#loginForm").serialize(),
			  url: "<%=basePath%>login/staffLogin",
			  success: function(data){
			 	if(data == "1"){
			 		location.href = "<%=basePath%>index";
				}else{
					alert(data);
				}
			}
		});
	}else{
		$.ajax({
			  async: false,
			   type: "POST",
			   data: $("#loginForm").serialize(),
			  url: "<%=basePath%>login/adminLogin",
			  success: function(data){
			 	if(data == "1"){
			 		location.href = "<%=basePath%>index";
				}else{
					alert(data);
				}
			}
		});
	}
	
}
</script>
</body>
</html>
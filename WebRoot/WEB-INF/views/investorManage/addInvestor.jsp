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
<title>G.net综合信息服务管理平台</title>
<link rel="stylesheet" href="<%=basePath%>oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>oa/css/list.css" type="text/css" />
<script src="<%=basePath%>oa/js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>oa/js/fixPNG.js" type="text/javascript"></script>
<!-- 导入图片上传js -->
<script type="text/javascript" src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	
	function cleaninput(id){
		$("#"+id).val("");
	}
	
	//图片上传预览    IE是用了滤镜。
	function previewImage(file, div) {
		var MAXWIDTH = 150;
		var MAXHEIGHT = 150;
		/* var div = document.getElementById('preview'); */
		if (file.files && file.files[0]) {
			div.innerHTML = '<img id=imghead width=150 height=150>';
			var img = document.getElementById('imghead');
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				img.style.width = '100px';
				img.style.height = '100px';
				//             img.style.marginLeft = rect.left+'px';
				img.style.marginTop = '0px';
			}
			var reader = new FileReader();
			reader.onload = function(evt) {
				img.src = evt.target.result;
			}
			reader.readAsDataURL(file.files[0]);
		} else //兼容IE
		{
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id=imghead>';
			var img = document.getElementById('imghead');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:100px;height:100px;margin-top:0px;"+sFilter+src+"\"'></div>";
		}
	}
	function clacImgZoomParam(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
		};
		if (width > maxWidth || height > maxHeight) {
			rateWidth = width / maxWidth;
			rateHeight = height / maxHeight;

			if (rateWidth > rateHeight) {
				param.width = maxWidth;
				param.height = Math.round(height / rateWidth);
			} else {
				param.width = Math.round(width / rateHeight);
				param.height = maxHeight;
			}
		}

		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		return param;
	}
	//调用预览
	function up_img(file) {
		var div = document.getElementById('display_img');
		previewImage(file, div);
	}
	
	function checkForm() {
		var idc = document.getElementById("idcard").value;
		var phone = document.getElementById("phone").value;
		var account = document.getElementById("account").value;
		var oldaccount = '${requestScope.investor.account}';
		var password = document.getElementById("password").value;
		var name = document.getElementById("name").value;
		var id = '${requestScope.investor.id}';
		
		if(name==""||name.length==0){
			alert("名字不能为空！");
			return;
		}else if(account==""||account.length==0){
			alert("账号不能为空！");
			return;
		}else if(id.length==0&&password==""){
			alert("密码不能为空！");
			return;
		}else if(phone.length==0&&phone==""){
			alert("电话号码不能为空！");
			return;
		}
		
		if(!idcardCheck(idc)||!phoneCheck(phone)||!accountCheck(account,oldaccount)||!pwdCheck(password)){
			return;
		}else{
			//document.getElementById("form").submit();
			$.ajaxFileUpload({
				type: "POST",
  				secureuri: false, //是否需要安全协议，一般设置为false
             	fileElementId: 'logo', //文件上传域的ID
				url: "<%=basePath%>control/investor/add/addInvestor",
				secureuri: false, //是否需要安全协议，一般设置为false
				data: $("#form").serializeObject(),
				dataType:"text",
				success: function(data){
				 	if(data == "1"){
				 		alert("保存成功");
				 		frameElement.api.close();
					}else if (data == "0") {
						alert("添加失败");
					}else{
						alert(data);
					}
				}
			});
		}
		
	}
	
	function pwdCheck(pwd){
		var id = '${requestScope.investor.id}';
		if(id.length>0){//编辑时不校验
			return true;
		}
		if(pwd==null||pwd==""){
			return true;
		}
		var t = /^[0-9A-Za-z]{6,20}$/;
		if(!t.test(pwd)){
			alert("请输入6-20位数字或字母的密码");
			return false;
		}else{
			return true;
		}
	}
	
	function accountCheck(account,oldaccount){
		if(account==oldaccount){
			return true;
		}
		if(account==null||account==""){
			return true;
		}
		var t =/^[A-Za-z0-9]+$/;
		if(!t.test(account)){
			alert("输入的账号格式不正确！");
			return false;
		}else{
			var t=true;
			$.ajax({
				type : "POST",
				url : "<%=basePath%>control/investor/check/checkAccount",
				data : {"account":account},
				async: false,
				success : function(msg) {
					if(msg=="1"){
						alert("输入的此账号已被占用！");
						t= false;
					}else{
						t= true;
					}
				}
			});
			return t;
			
		}
	}
	
	function phoneCheck(phone){
		var t = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
		if(phone==null||phone==""){
			return true;
		}
		if (!t.test(phone)) {
			alert("请输入正确的手机号码");
			return false;
		}else{
			return true;
		}
	}
	
	function idcardCheck(idcard){
		if(idcard==null||idcard==""){
			return true;
		}
		var t="";
		if(idcard.length==15){
			t = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		}else{
			t=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
		}
		if (!t.test(idcard)) {
			alert("输入的身份证号码格式不正确");
			return false;
		}else{
			return true;
		}
	}
	
	function changeThePassWord(id){
		var password = window.prompt("请输入新的密码").trim();
		var t = /^[0-9A-Za-z]{6,20}$/;
		if(!t.test(password)){
			alert("请输入6到20位的数字或字母");
		}else{
			$.ajax({
				url:"<%=basePath%>control/investor/update/updatePassWord",
				type:"post",
				data:{"id":id, "password":password},
				success:function(result){
					if(result == "ok"){
						alert("修改成功");
					}else{
						alert("修改失败");
					}
				},
				error:function(){
					alert("修改失败");
				}
			});
		}
	}
</script>
</head>

<body class="width500">
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<span>投资人信息</span>
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="control/investor/add/addInvestor"	enctype="multipart/form-data">
					<table class="nav_tab">
						<tbody>
						<tr>
							<th>照片：</th>
							<td>
								<div style="float:left">
									<input type="file" name="file" id="logo"
										onchange="up_img(this)" />
								</div>
								<div id="display_img"
									style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
									<img id="imghead" style="width: 100px;height: 100px;"
										src="${requestScope.investor.picture }" />
								</div>  
							</td>
						</tr>
						
						<tr>
							<th>名字：</th>
							<td>
								<input type="text" style="display:none" name="id" id="id" value="${requestScope.investor.id}" />
								<input type="text" class="text width300" name="name" id="name" value="${requestScope.investor.name}"  />
							</td>
						</tr>
						
						<tr>
							<th>身份证号码：</th>
							<td>
								<input type="text" class="text width300" name="idcard" id="idcard" value="${requestScope.investor.idcard}" onchange="idcardCheck(this.value)" />
							</td>
						</tr>
						<tr>
							<th>电话号码：</th>
							<td>
								<input type="text" class="text width300" name="phone" id="phone" value="${requestScope.investor.phone}" onchange="phoneCheck(this.value)" />
							</td>
						</tr>
						<tr>
							<th>账号：</th>
							<td>
								<input type="text" class="text width300" name="account" id="account" value="${requestScope.investor.account}" onchange="accountCheck(this.value,'${requestScope.investor.account}');" />
							</td>
						</tr>
						<tr>
							<th>密码：</th>
							<td>
								<input type="password" class="text width300" name="password" id="password" value="${requestScope.investor.password}" onchange="pwdCheck(this.value)" 
									<c:if test="${not empty requestScope.investor and not empty requestScope.investor.password}">
										disabled="disabled"
									</c:if>
								/>
								
								<c:if test="${not empty requestScope.investor and not empty requestScope.investor.password}">
									<a onclick="changeThePassWord('${requestScope.investor.id}')">重置密码</a>
								</c:if>
							</td>
						</tr>
						<%-- <tr class="line_g">
							<td width="10%">编号：</td>
							<td>
								${requestScope.investor.number}
							</td>
						</tr> --%>
						</tbody>
					</table>
					<div class="nt-btmbox">
				    	<input class="btn" type="button" value="保存" onclick="checkForm()">
				    	<!-- <input type="button" value="取消" class="btn" onclick="history.go(-1)" /> -->
				    </div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

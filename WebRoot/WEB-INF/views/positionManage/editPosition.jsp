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
<link rel="stylesheet" href="oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="oa/css/list.css" type="text/css" />
<script src="oa/js/jquery-1.8.0.min.js"></script>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>

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
	
	function checkForm() {
		var name = document.getElementById("name").value;
		var shopFrontId = document.getElementById("shopFrontId").value;
		var type = document.getElementById("type").value;
		if(shopFrontId==""||shopFrontId.length==0){
			alert("请选择门店");
			return;
		}else if(name==""||name.length==0){
			alert("请输入职位名称");
			return;
		}else if(type==""||type.length==0){
			alert("请输入职位等级");
			return;
		}else if(!check(type)){
			return;
		}else{
			//document.getElementById("form").submit();
			$.ajax({
				  async: false,
				  type: "POST",
				  data: $("#form").serialize(),
				  url: "<%=basePath%>control/position/add/addPosition",
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
	
	function check(data){
		var reg = /^[-\+]?\d+(\.\d+)?$/;
		if(reg.test(data)){
			return true;
		}else{
			alert("职位等级请输入数字");
			return false;
		}
	}
	
	function shopFrontChange(id){
		if(id==""||id.length==0){
			$("#staffManageRoleContainerId").empty();
			return;
		}else{
			$.ajax({
				type:'post',
				url : "<%=basePath%>control/dept/show/staffManageRoleContainer?id="+id,
				dataType : 'json',
				success : function(data) {
					if(data!="0"){
						var str = "<option value=''>请选择</option>";
						$.each(data,function(i,n){
							str += "<option value="+n.id+">"+n.name+"</option>";
						});
						$("#staffManageRoleContainerId").html(str);
					}
				}
			});
		
		}
	}
	
</script>
</head>

<body class="width400">
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<span>修改职位信息</span>
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="control/position/add/addPosition"	enctype="multipart/form-data">
					<table class="nav_tab">
						<tr>
							<th>门店：</th>
							<td>
								<input type="text" style="display:none" name="id" id="id" value="${requestScope.position.id}" />
							    <select name="shopFrontId" id="shopFrontId" class="text width300" onchange="shopFrontChange(this.value)">
									<option value="">请选择门店</option>
									<c:forEach items="${requestScope.shopFrontList }" var="data">
										<option value="${data.id }" <c:if test="${data.id==requestScope.position.shopFrontId }">selected="selected"</c:if>>${data.name }</option>
									</c:forEach>
							    </select>
							</td>
						</tr>
						<tr>
							<th>角色：</th>
							<td>
							    <select name="staffManageRoleContainerId" id="staffManageRoleContainerId" class="text width300">
									<option value="">请选择</option>
									<c:forEach items="${requestScope.staffManageRoleContainerList }" var="data">
										<option value="${data.id }" <c:if test="${data.id==requestScope.position.staffManageRoleContainerId }">selected="selected"</c:if>>${data.name }</option>
									</c:forEach>
							    </select>
							</td>
						</tr>
						<tr>
							<th>职位名称：</th>
							<td>
								<input type="text" class="text width300" name="name" id="name" value="${requestScope.position.name}"  />
							</td>
						</tr>
						<tr>
							<th>职位等级：</th>
							<td>
							    <%-- <select name="type" id="type" style="width: 200px;" class="text width300">
									<option value="0" <c:if test="${requestScope.position.type==0}">selected="selected"</c:if>>0</option>
									<option value="1" <c:if test="${requestScope.position.type==1}">selected="selected"</c:if>>1</option>
									<option value="2" <c:if test="${requestScope.position.type==2}">selected="selected"</c:if>>2</option>
							    </select> --%>
							    <input type="text" class="text width300" name="type" id="type" value="${requestScope.position.type}" onchange="check(this.value)" />
							</td>
						</tr>
						
					</table>
					<div class="nt-btmbox">
				    	<input class="btn" type="button" value="保存" onclick="checkForm()">
				    </div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

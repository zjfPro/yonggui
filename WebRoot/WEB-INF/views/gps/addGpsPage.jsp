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
<script src="oa/js/My97DatePicker/WdatePicker.js"></script>

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
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/gps/add/addGps",
			  success: function(data){
			 	if(data == "1"){
			 		alert("添加成功");
			 		frameElement.api.close();
				}else if (data == "-1") {
					alert("添加失败");
					location.reload();
				}else{
					alert(data);
				}
			}
		});
	}
	function checkCom(val) {
		var t = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
		var phone = $("#ownerPhone").val().trim();
		if (!t.test(phone)) {
			alert("请输入正确的车主联系方式！！！");
			return false;
		}

	}
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span> <a>GPS信息</a><span>></span>增加车辆GPS
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<table class="left_table">
						<tr>
							<th>车牌：</th>
					      	<td>
					        <div class="list_date">
					            <select name="carInfoId" id="carInfoId" class="nt-select">
					            	<option value="">请选择</option>
				               		<c:forEach items="${requestScope.infos }" var="data">
										<option value="${data.id }">${data.plateNumber }</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
							<!-- <td width="10%">驾驶员：</td>
							<td><input type="text" class="text width300" name="driverId" id="driverId"/><a onclick="cleaninput('driverId')">清空</a></td> -->
						<th>驾驶员：</th>
				           <td>
					        <div class="list_date">
					            <select name="driverId" id="driverId" class="nt-select">
					            	<option value="">请选择</option>
				               		<c:forEach items="${requestScope.drivers }" var="data">
										<option value="${data.id }">${data.name}</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
						</tr>
						<tr>
							<th>车载电话：</th>
							<td><input type="text" class="nt-text" name="phone" id="phone"/></td>
							<th>车主电话：</th>
							<td><input type="text" class="nt-text" name="ownerPhone" id="ownerPhone" onchange="checkCom()"/></td>
						</tr>
						<tr>
							<th>车载终端：</th>
							<td><input type="text" class="nt-text" name="carTerminal" id="carTerminal"/></td>
						</tr>
					</table>
				</form>
				<div class="nt-btmbox">
        			<input class="btn" type="button" value="保存" onclick="checkForm()">
      			</div>
			</div>
		</div>
	</div>

</body>
</html>

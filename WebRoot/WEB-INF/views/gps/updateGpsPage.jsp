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
			  url: "<%=basePath%>control/gps/update/updateGps",
			  success: function(data){
			 	if(data == "1"){
			 		alert("修改成功");
			 		frameElement.api.close();
				}else if (data == "-1") {
					alert("修改失败");
					location.reload();
				}else{
					alert(data);
				}
			}
		});
	}
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="infolist">
				<form name="form" id="form" method="post">
				<input type="hidden" id="id" name="id" value="${requestScope.gps.id }" />
					<table class="left_table">
						<tr>
							<th>车牌：</th>
					      	<td>
					        <div class="list_date">
					            <select name="carInfoId" id="carInfoId" class="nt-select">
					            	<option value="${requestScope.gps.infos.id }">${requestScope.gps.infos.plateNumber }</option>
				               		<c:forEach items="${requestScope.infos }" var="data">
										<option value="${data.id }">${data.plateNumber }</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
						<th>驾驶员：</th>
				           <td>
					        <div class="list_date">
					            <select name="driverId" id="driverId" class="nt-select">
					            	<option value="${requestScope.gps.drivers.id }">${requestScope.gps.drivers.name }</option>
				               		<c:forEach items="${requestScope.drivers }" var="data">
										<option value="${data.id }">${data.name}</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
						</tr>
						<tr>
							<th>车载电话：</th>
							<td><input type="text" class="nt-text" name="phone" id="phone" value="${requestScope.gps.phone }"/>
							</td>
							<th>车主电话：</th>
							<td><input type="text" class="nt-text" name="ownerPhone" id="ownerPhone" value="${requestScope.gps.ownerPhone }"/>
							</td>
						</tr>
						<tr>
							<th>车载终端：</th>
							<td><input type="text" class="nt-text" name="carTerminal" id="carTerminal" value="${requestScope.gps.carTerminal }"/>
							</td>
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

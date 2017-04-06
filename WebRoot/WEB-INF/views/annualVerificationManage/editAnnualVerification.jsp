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
		//document.getElementById("form").submit();
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/avmc/update/editAnnualVerification",
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
</script>
</head>

<body class="width400">
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<span>修改车辆年审信息</span>
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="control/avmc/update/editAnnualVerification" enctype="multipart/form-data">
					<table class="nav_tab">
						<tbody>
						<tr>
							<th>年审车辆：</th>
							<td>
								<input type="text" style="display:none" name="id" id="id" value="${requestScope.cav.id}" />
								${requestScope.cav.carInfo.carType.carBrand.info}(门店：${requestScope.cav.carInfo.shopFront.name},车牌：${requestScope.cav.carInfo.plateNumber} )
							</td>
						</tr>
						
						<tr >
							<th>审核意见：</th>
							<td>
								<input type="text" class="text width300" name="opinion" id="opinion" value="${requestScope.cav.opinion}" />
							</td>
						</tr>
						<tr >
							<th>是否通过：</th>
							<td>
								<select name="ispass" id="ispass" style="width: 200px;" class="text width300">
									<option value="1" <c:if test="${requestScope.cav.ispass==1 }">selected="selected"</c:if>>通过</option>
									<option value="0" <c:if test="${requestScope.cav.ispass==0 }">selected="selected"</c:if>>未通过</option>
							    </select>
							</td>
						</tr>
						<!-- <tr >
							<td colspan="2"><input type="button" value="保存" class="btn"
								onclick="checkForm()" /> <input type="button" value="取消"
								class="btn" onclick="history.go(-1)" /></td>
						</tr> -->
						</tbody>
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

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
			  url: "<%=basePath%>control/setting/update/updateSetting",
			  success: function(data){
			 	if(data == "1"){
			 		alert("修改成功");
				frameElement.api.close();
				}else if (data == "0") {
					alert("修改失败");
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
				<input type="hidden" id="ccId" name="ccId" value="${requestScope.setting.id }" />
					<table class="left_table">
					<tr class="line_g">
					    <td width="40%" align="right">门店：</td>
					      <td>
					        <div class="list_date">
					            <select name="shopFrontId" id="shopFrontId" onchange="dataChange(this.value)" class="nt-text">
					            	<option value="${requestScope.setting.shopFront.id }">${requestScope.setting.shopFront.name }</option>
					            </select>
					         </div>
					        </td>
					    </tr>
						<tr class="line_g">
							<td width="40%" align="right">车辆最小库存：</td>
							<td><input type="text" class="nt-text" name="carStockMinAlert" id="carStockMinAlert" 
													value="${requestScope.setting.carStockMinAlert }"/></td>
						</tr>
						
						<tr class="line_g">
							<td width="40%" align="right">车辆最大库存：</td>
							<td><input type="text" class="nt-text" name="carStockMaxAlert" id="carStockMaxAlert"
													value="${requestScope.setting.carStockMaxAlert }"/></td>
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

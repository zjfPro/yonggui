<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

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
	
	function cleanPage(){
    	window.location.href="<%=basePath%>control/carUpkeep/select/cgCarUpkeepList";
	}
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "post",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/carUpkeep/update/gotoUpdateCgCarUpkeep",
			  success: function(data){
			 	if(data == "1"){
			 	alert("修改成功")
			 		/* var queren = confirm("修改成功,是否返回车辆保养列表?");
			 		if(queren){
			 			cleanPage();
			 		} */
			 	frameElement.api.close();
				}else{
					alert(data);
				}
			}
		});
	}
	
	$(document).ready(function(){
		
		var hiddencarInfoId = $("#hiddenCarInfoId").val();
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getAllCgCarInfo',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择车辆...</option>";
					$.each(data,function(i,n){
						if(n.id==hiddencarInfoId){
							
							str += "<option selected='selected' value='"+n.id+"'>"+"车牌:"+n.plateNumber+"</option>";
						}else{
							str += "<option value='"+n.id+"'>"+"车牌:"+n.plateNumber+"</option>";
						}
						
						
					});
					$("#carInfoId").html(str);
				}else{
					alert(data.errInfo);
				}
				
			}
		});
		
	});
	
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				修改车辆保养记录
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
				<input id="ccuID" name="ccuID" value="${ccu.id}"  type="hidden"/>
					<table class="nav_tab">

						
						<tr class="line_g">
							<th width="10%">保养人员姓名：</th>
							<td><input type="text" class="nt-text" name="upkeepPeople" id="upkeepPeople" value="${ccu.upkeepPeople}"/></td>
							<th width="10%">车辆：</th>
							<td>
							<input id="hiddenCarInfoId" name="hiddenCarInfoId" value="${ccu.carInfoId}"  type="hidden"/>
							<select name="carInfoId" id="carInfoId" class="nt-select"></select>
							</td>
						</tr>
						
						<tr class="line_g">
							<th width="10%">保养日期：</th>
							<td><input type="text" id="theUpkeepTime" name="theUpkeepTime" class="nt-text"
								value="<fmt:formatDate value="${ccu.upkeepTime}" pattern="yyyy-MM-dd"/>"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
							<th width="10%">里程表读数：</th>
							<td><input type="text" class="nt-text" name="mileage" id="mileage" value="${ccu.mileage}"/></td>
						</tr>
						<tr class="line_g">
							<th width="10%">保养项目：</th>
							<td><input type="text" class="nt-text" name="upkeepProject" id="upkeepProject" value="${ccu.upkeepProject}"/></td>
							<th width="10%">更换发动机机油型号：</th>
							<td><input type="text" class="nt-text" name="uodateEngineOilType" id="uodateEngineOilType" value="${ccu.uodateEngineOilType}"/></td>
						</tr>
						<tr class="line_g">
							<th width="10%">上次保养里程表读数：</th>
							<td><input type="text" class="nt-text" name="lastMileage" id="lastMileage" value="${ccu.lastMileage}"/></td>
							<th width="10%">上次保养日期：</th>
					<td><input type="text" id="theLastUpkeepTime" name="theLastUpkeepTime" class="nt-text"
					value="<fmt:formatDate value="${ccu.lastUpkeepTime}" pattern="yyyy-MM-dd"/>"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
						</tr>
						
						<!-- <tr class="line_g">
							<td colspan="2">
								<input type="button" value="保存" class="btn" onclick="checkForm()"/> 
								<input type="button" value="取消" class="btn" onclick="cleanPage();"/>
							</td>
						</tr> -->
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

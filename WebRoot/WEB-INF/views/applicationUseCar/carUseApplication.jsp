<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<title>用车申请</title>
<link rel="stylesheet" href="<%=basePath%>oa/css/global.css" type="text/css"/>
<link rel="stylesheet" href="<%=basePath%>oa/css/list.css" type="text/css"/>
<script src="<%=basePath%>oa/js/fixPNG.js" type="text/javascript"></script>
<script src="<%=basePath%>oa/js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath %>oa/js/jquery.validate.js"></script>
<script src="<%=basePath %>oa/js/validate.expand.js"></script>

<!-- 导入图片上传js -->
<script type="text/javascript" src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	$(function() {
		var validate = $("#form").validate({
			debug : true, //调试模式取消submit的默认提交功能   
			//errorClass: "label.error", //默认为错误的样式类为：error   
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules : {
				oddNumbers:{
					required :true,
				},
				deptId:{
					required :true,
				},
				person:{
					required :true,
				},
				usecarReason:{
					required :true,
				},
				startTimes:{
					required :true,
				},
				endtimes:{
					required :true,
				},
				rideHead:{
					required :true,
				}
				
			},
			messages : {
				oddNumbers :{
					required : "<span style='color:red'>该项不能为空</span>",
				},
				deptId :{
					required : "<span style='color:red'>该项不能为空</span>",
				},
				person :{
					required : "<span style='color:red'>该项不能为空</span>",
				},
				usecarReason :{
					required : "<span style='color:red'>该项不能为空</span>",
				},
				startTimes :{
					required : "<span style='color:red'>该项不能为空</span>",
				},
				endtimes :{
					required : "<span style='color:red'>该项不能为空</span>",
				},
				rideHead :{
					required : "<span style='color:red'>该项不能为空</span>",
				}
				
			}
		});
		
		$("#addBtn").click(function() {
			var id = '${requestScope.cauc.id}';
			if (!validate.form()) {
				return;
			}else{
				//document.getElementById("form").submit();
				$.ajaxFileUpload({
					type: "POST",
	  				secureuri: false, //是否需要安全协议，一般设置为false
	             	fileElementId: 'accessory', //文件上传域的ID
					url: "<%=basePath%>control/applicationUseCar/add/addCarUseApplication",
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
		});
	
	});
	
	function change(){
		$("#accessoryShow").hide();
	};
</script>
</head>

<body class="width800">

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="current"><span>用车申请</span></div>
    <div class="infolist">
		<form name="form" id="form" method="post" action="control/applicationUseCar/add/addCarUseApplication" enctype="multipart/form-data">
	      <table class="nav_tab">
	        <thead>
	        </thead>
	        <tbody>
	          <tr>
	            <th style="width:80px;">单号：</th>
	            <td colspan="7">
	            	<input class="nt-text" type="text" name="oddNumbers" value="${requestScope.cauc.oddNumbers}" style="width:145px;">
	            	<input type="text" name="id" value="${requestScope.cauc.id}" style="display: none">
	            </td>
	          </tr>
	          <tr>
	            <th style="width:80px;">申请类型：</th>
	            <td>
	              <select class="nt-select" style="width:145px;">
	                <option>请选择</option>
	              </select>
	            </td>
	            <th style="width:80px;">申请部门：</th>
	            <td><input class="nt-text" type="text" name="deptId" value="${requestScope.cauc.deptId}" style="width:145px;"></td>
	            <th style="width:80px;">申请人：</th>
	            <td><input class="nt-text" type="text" name="person" value="${requestScope.cauc.person}" style="width:145px;"></td>
	            <th style="width:80px;">用车类型：</th>
	            <td>
	              <select class="nt-select" style="width:145px;">
	                <option>请选择</option>
	              </select></td>
	          </tr>
	          
	          <tr>
	            <th style="width:80px;">用车理由：</th>
	            <td colspan="7"><textarea class="nt-area" name="usecarReason">${requestScope.cauc.usecarReason}</textarea></td>
	          </tr>
	          
	          
	          <tr>
	            <th style="width:80px;">开始时间：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" name="startTimes" value="${requestScope.cauc.startTime}" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:145px;">
	            </td>
	            <th style="width:80px;">结束时间：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" name="endtimes" value="${requestScope.cauc.endtime}" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:145px;">
	            </td>
	            <th style="width:80px;">出发地点：</th>
	            <td><input class="nt-text" type="text" name="pointOfDeparture" value="${requestScope.cauc.pointOfDeparture}" style="width:145px;"></td>
	            <th style="width:80px;">目的地点：</th>
	            <td><input class="nt-text" type="text" name="destination" value="${requestScope.cauc.destination}" style="width:145px;"></td>
	          </tr>
	          
	          <tr>
	            <th style="width:80px;">乘车人数：</th>
	            <td><input class="nt-text" type="text" name="numberOfPeope" value="${requestScope.cauc.numberOfPeope}" style="width:145px;"></td>
	            <th style="width:80px;">乘车负责人：</th>
	            <td><input class="nt-text" type="text" name="rideHead" value="${requestScope.cauc.rideHead}" style="width:145px;"></td>
	            <th style="width:80px;">申请单类型：</th>
	            <td>
	            	<select class="nt-select" style="width:145px;">
	                	<option>请选择</option>
	                	<option>正常</option>
	            	</select>
	           	</td>
	          </tr>
	         
	        </tbody>
	        <tfoot>
	          <th style="width:80px;">附件上传：</th>
	          <td colspan="7">
	          	<input type="file" id="accessory" name="file" onchange="change()";>
	          	<a href="<%=basePath%>${requestScope.cauc.accessory}" target="_blank" 
	          		style="color:blue;" id="accessoryShow">
	          		${fn:split(requestScope.cauc.accessory,"/")[3]}
	          	</a>
	          </td>
	        </tfoot>
	      </table>
	      
	      <div class="nt-btmbox">
	        <input class="btn" type="button" value="保存" id="addBtn">
	      </div>
	    </form>
    </div>
  </div>
</div>

</body>
</html>

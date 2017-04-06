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

<script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ueditor/lang/zh-cn/zh-cn.js"></script>

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
		window.location.href="<%=basePath%>control/rentalOrder/select/rentalOrderList";
	}
	
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/rentalOrder/update/gotoUpdateOrderStatus",
			  success: function(data){
			 	if(data == "1"){
			 		var queren = confirm("审核提交成功,是否返回订单列表?");
			 		if(queren){
			 			cleanPage();
			 		}
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
			<div class="current">
				<a>个人桌面</a><span> > </span> <a>基础信息</a><span>></span>审核订单
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
				<input id="shenhedd" name="shenhedd" value="${shenhedd.id}"  type="hidden"/>
					<table class="left_table">

						
						
						<tr class="line_g">
							<td width="10%">订单编号：</td>
							<td>${shenhedd.number}</td>
						</tr>
						<tr class="line_g">
							<td width="10%">车辆(车牌号)：</td>
							<td>${shenhedd.cgCarRentalItem.cgCarInfo.plateNumber}</td>
						</tr>
						<tr class="line_g">
							<td width="10%">租车用户：</td>
							<td>${shenhedd.userName}</td>
						</tr>
						<tr class="line_g">
							<td width="10%">租车用户联系电话：</td>
							<td>${shenhedd.userPhone}</td>
						</tr>
						<tr class="line_g">
							<td width="10%">门店：</td>
							<td>${shenhedd.cgShopFront.name}</td>
						</tr>
						<tr class="line_g">
							<td width="10%">审核结果：</td>
							<td>
								是否通过审核:
								<select name="status" id="status" style="width: 200px;">
										<option <c:if test="${shenhedd.status==1}">selected="selected"</c:if> value="1">通过</option>
										<option <c:if test="${shenhedd.status==2}">selected="selected"</c:if> value="2">不通过</option>
							    </select>
							</td>
						</tr>
						
						
						<tr class="line_g">
							<td colspan="2">
								<input type="button" value="提交" class="btn" onclick="checkForm()"/>
								<input type="button" value="取消" class="btn" onclick="cleanPage();"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

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
			  url: "<%=basePath%>control/rentalOrder/update/gotoPerfectOrder",
			  success: function(data){
			 	if(data == "1"){
			 		var queren = confirm("资料提交成功,是否返回订单列表?");
			 		if(queren){
			 			cleanPage();
			 		}
				}else{
					alert(data);
				}
			}
		});
	}
	
	$(document).ready(function(){
		var hiddencarInfoId = $("#hiddenCarInfoId").val();
		var mendianshow='';
		var shopFrontId='';
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getRentalItem',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择上架车辆...</option>";
					$.each(data,function(i,n){
						
						if(n.cgCarInfo.plateNumber==hiddencarInfoId){
							mendianshow=n.cgShopFront.name;
							shopFrontId=n.cgShopFront.id;
							str += "<option selected='selected' value='"+n.id+"'>"+"车牌:"+n.cgCarInfo.plateNumber+"</option>";
							getOptionData(n.id);
						}else{
							str += "<option value='"+n.id+"'>"+"车牌:"+n.cgCarInfo.plateNumber+"</option>";
						}
					});
					$("#carRentalItemId").html(str);
					$("#mendianshow").html(mendianshow);
					$("#shopFrontId").val(shopFrontId);
				}else{
					alert(data.errInfo);
				}
				
			}
		});
		
	});
	
	function getOptionData(optionValue){
		if(optionValue==''){
			$("#staffId").find("option").remove();
			$("#contractId").find("option").remove();
			$("#shopFrontId").val('');
			$("#mendianshow").html('');
		}else{
			getStaff(optionValue);
			getContract(optionValue);
		}
	}
	
	function getStaff(staffValue){
		var hiddenstaffId = $("#hiddenstaffId").val();
		var mdian='';
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getStaffByItemId?itemID='+staffValue,
			dataType:'json',
			success:function(data){
				if(data.length>0){
					var str = "<option value=''>请选择业务员...</option>";
					$.each(data,function(i,n){
						if(mdian==''){
							mdian=n.cgShopFront.name;
						}
						if(n.id==hiddenstaffId){
							str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
						}else{
							str += "<option value="+n.id+">"+n.name+"</option>";
						}
						
					});
					
					$("#staffId").html(str);
					$("#mendianshow").html(mdian);
				}else{
					alert(data.errInfo);
					$("#staffId").find("option").remove();
				}
			}
		});
	}
	
	function getContract(contractValue){
		var hiddencontractId = $("#hiddencontractId").val();
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getContract?itemID='+contractValue,
			dataType:'json',
			success:function(data){
				if(data.length>0){
					var str = "<option value=''>请选择租车合同...</option>";
					$.each(data,function(i,n){
						
						if(n.id==hiddencontractId){
							str += "<option selected='selected' value="+n.id+">"+n.number+"</option>";
						}else{
							str += "<option value="+n.id+">"+n.number+"</option>";
						}
						
					});
					
					$("#contractId").html(str);
				}else{
					alert(data.errInfo);
					$("#contractId").find("option").remove();
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
				<a>个人桌面</a><span> > </span> <a>基础信息</a><span>></span>完善订单
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
				<input id="perfectdd" name="perfectdd" value="${wanshandd.id}"  type="hidden"/>
					<table class="left_table">

						
						
						<tr class="line_g">
							<td width="10%">订单编号：</td>
							<td>${wanshandd.number}</td>
						</tr>
						<tr class="line_g">
							<td width="10%">租车用户：</td>
							<td>
								<input type="text" class="text width300" name="userName" id="userName" value="${wanshandd.userName}"/>
								
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">租车用户联系电话：</td>
							<td>
							<input type="text" class="text width300" name="userPhone" id="userPhone" value="${wanshandd.userPhone}"/>
							
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">车辆(车牌号)：</td>
							<td>
								<input id="hiddenCarInfoId" name="hiddenCarInfoId" value="${wanshandd.cgCarRentalItem.cgCarInfo.plateNumber}"  type="hidden"/>
								<select name="carRentalItemId" id="carRentalItemId" style="width: 300px;" onchange="getOptionData(this.value);"></select>
							
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">门店：</td>
							<td>
								<input id="shopFrontId" name="shopFrontId" value=""  type="hidden"/>
								<p id="mendianshow"></p>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">业务员：</td>
							<td>
							<input id="hiddenstaffId" name="hiddenstaffId" value="${wanshandd.staffId}"  type="hidden"/>
							<select name="staffId" id="staffId" style="width: 300px;"></select>
							
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">订单合同：</td>
							<td>
								<input id="hiddencontractId" name="hiddencontractId" value="${wanshandd.contractId}"  type="hidden"/>
								<select name="contractId" id="contractId" style="width: 300px;"></select>
							</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">审核状态：</td>
							<td>
								<c:if test="${wanshandd.status==0}"><span style="color: orange;">未审核</span></c:if> <c:if
											test="${wanshandd.status==1}"><span style="color: green;">已通过</span></c:if> <c:if
											test="${wanshandd.status==2}"><span style="color: red;">未通过</span></c:if>
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

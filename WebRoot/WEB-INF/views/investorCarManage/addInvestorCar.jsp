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
		var money = document.getElementById("money").value;
		var investorId = document.getElementById("investorId").value;
		var carInfoId = document.getElementById("carInfoId").value;
		var bonus = document.getElementById("bonus").value;
		var id = '${requestScope.investorCar.id}';
		var api = frameElement.api;
		if(investorId==""||investorId.length==0){
			alert("请选择投资人");
			return;
		}else if(carInfoId==""||carInfoId.length==0){
			alert("请选择车辆");
			return;
		}else if(!moneyCheck(money)||!bonusCheck(bonus)){
			return;
		}else if(!check(investorId,carInfoId)){
			return;
		}else{
			//document.getElementById("form").submit();//
			$.ajax({
				  async: false,
				  type: "POST",
				  data: $("#form").serialize(),
				  url: "<%=basePath%>control/investorCar/add/addInvestorCar",
				  success: function(data){
				 	if(data == "1"){
				 		alert("保存成功");
				 		api.close();
					}else if (data == "0") {
						alert("保存失败");
					}else{
						alert(data);
					}
				}
			});
		}
		
	}
	
	function moneyCheck(data){
		if(data==null||data==""){
			return true;
		}
		var reg = /^[-\+]?\d+(\.\d+)?$/;
		if (reg.test(data)) {
			return true;
		} else {
			alert("投资金额/股权输入格式不正确，请输入数字!");
			return false;
		}
	}
	
	/* function bonusCheck(bonus){
		if(bonus==null||bonus==""){
			return true;
		}
		var reg = /^([1-9]|10)$/;
		if (reg.test(bonus)) {
			return true;
		} else {
			alert("分成输入格式不正确，请输入1-9的正整数!");
			return false;
		}
	} */
	
	function bonusCheck(bonus){
		if(bonus==null||bonus==""){
			return true;
		}
        var k=/^(100|[1-9]?\d(\.\d\d?\d?)?)%$|0$/;  
        if(k.test(bonus)){
        	return true;
        }else{
        	alert("分成输入格式不正确，1%-100%的的百分比!");
			return false;
        }
    }
	
	function check(investorId,carInfoId){
		var investorId_o = '${requestScope.investorCar.investorId }';
		var carInfoId_o = '${requestScope.investorCar.carInfoId }';
		if(investorId==investorId_o&&carInfoId==carInfoId_o){
			return true;
		}
		
		var t=true;
		$.ajax({
			type : "POST",
			url : "<%=basePath%>control/investorCar/check/checkInvestorCar",
			data : {"investorId":investorId,"carInfoId":carInfoId},
			async: false,
			success : function(msg) {
				if(msg=="1"){
					alert("您选择的投资人跟车辆已经关联！");
					t= false;
				}else{
					t= true;
				}
			}
		});
		return t;
	}
</script>
</head>

<body class="width500">
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<span>车辆投资记录信息</span>
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="control/investorCar/add/addInvestorCar" enctype="multipart/form-data">
					<table class="nav_tab">
						<tbody>
						<tr>
							<th>投资人：</th>
							<td>
								<input type="text" style="display:none" name="id" id="id" value="${requestScope.investorCar.id}" />
							    <select name="investorId" id="investorId" style="width: 200px;" class="text width300">
									<option value="">请选择</option>
									<c:forEach items="${requestScope.investorList }" var="data">
										<option value="${data.id }" <c:if test="${data.id==requestScope.investorCar.investorId }">selected="selected"</c:if>>${data.name }</option>
									</c:forEach>
							    </select>
							</td>
							<th>车辆：</th>
							<td>
							    <select name="carInfoId" id="carInfoId" style="width: 200px;" class="text width300">
									<option value="">请选择</option>
									<c:forEach items="${requestScope.carList }" var="data">
										<option value="${data.id }" <c:if test="${data.id==requestScope.investorCar.carInfoId }">selected="selected"</c:if>>${data.carType.carBrand.info }(门店：${data.shopFront.name } 车牌：${data.plateNumber })</option>
									</c:forEach>
							    </select>
							</td>
						</tr>
						
						<%-- <tr>
							<th>投资类型：</th>
							<td>
							    <select name="type" id="type" style="width: 200px;" class="text width300">
									<option value="0" <c:if test="${requestScope.investorCar.type==0}">selected="selected"</c:if>>全资</option>
									<option value="1" <c:if test="${requestScope.investorCar.type==1}">selected="selected"</c:if>>股份</option>
							    </select>
							</td>
							<th>股权：</th>
							<td>
								<input type="text" class="text width200" name="stockRight" id="stockRight" value="${requestScope.investorCar.stockRight}" onchange="moneyCheck(this.value)" />
							</td>
						</tr> --%>
						
						<tr>
							<th>投资金额：</th>
							<td>
								<input type="text" class="text width200" name="money" id="money" value="${requestScope.investorCar.money}" onchange="moneyCheck(this.value)" />
							</td>
							<th>分成：</th>
							<td>
								<input type="text" class="text width200" name="bonus" id="bonus" value="${requestScope.investorCar.bonus}" onchange="bonusCheck(this.value)" />
							</td>
						</tr>
						
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

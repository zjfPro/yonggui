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
    	window.location.href="<%=basePath%>control/carInsurance/select/cgCarInsuranceList";
	}
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/carInsurance/add/gotoAddCgCarInsurance",
			  success: function(data){
			 	if(data == "1"){
			 	alert("添加成功");
			 	
			 	frameElement.api.close();
				}else{
					alert(data);
				}
			}
		});
	}
	
	
	$(document).ready(function(){
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getAllCgCarInfo',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择车辆...</option>";
					$.each(data,function(i,n){
						str += "<option value='"+n.id+"'>"+"车牌:"+n.plateNumber+"</option>";
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

<body class="width900">
	<!-- content -->
	<div class="content">
		<div class="content_box">
		<div class="current">添加车辆保险</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<table class="nav_tab">
						<thead>
				          <tr>
				            <th colspan="8">必填</th>
				          </tr>
				        </thead>
						
						<tr>
							<th>车牌号：</th>
							<td>
								<select name="carInfoId" id="carInfoId" class="nt-select"></select>
							</td>
							<th>经办人：</th>
							<td><input type="text" class="nt-text" style="width: 120px;" name="insured" id="insured" /></td>
							<th>保险名称：</th>
							<td><input type="text" class="nt-text" name="insuranceName" id="insuranceName"/></td>
							<th>保险单号：</th>
							<td><input type="text" class="nt-text" name="insuranceCode" id="insuranceCode"/></td>
						</tr>
						<tr>
							<th>缴费总计：</th>
							<td><input type="text" class="nt-text" name="totalPrices" id="totalPrices"/></td>
								<th>承保公司：</th>
						<td><input type="text" class="nt-text" name="insurer" id="insurer"/></td>
							<th>投保日期：</th>
						
						<td><input type="text" id="theStartTime" name="theStartTime" class="nt-text"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
              					 
              					 <th>到期日期：</th>
						<td><input type="text" id="theEndTime" name="theEndTime" class="nt-text"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
						</tr>
					</table>
					
					<table class="nav_tab">
						<thead>
				          <tr>
				            <th colspan="8">可填</th>
				          </tr>
				        </thead>
						<tbody>
					          <tr>
					            <th>交强险缴费金额：</th>
					            <td>
					              <input class="nt-text" type="text" name="compulsoryInsuranceMoney" id="compulsoryInsuranceMoney"></td>
					            
					            <th>车辆损失险缴费金额：</th>
					            <td><input class="nt-text" type="text" name="vehicleDamage" id="vehicleDamage"></td>
					            
					            <th>商业第三者责任险缴费金额：</th>
					            <td>
					              <input class="nt-text" type="text" name="thirdPartyLiability" id="thirdPartyLiability"></td>
					            
					            <th>全车盗抢险缴费金额：</th>
					            <td><input class="nt-text" type="text" name="robberyTheft" id="robberyTheft"></td>
					           
					          </tr>
					          
					          <tr>
					            <th>司机座位责任险缴费金额：</th>
					            <td>
					              <input class="nt-text" type="text" name="driversSeatLiability" id="driversSeatLiability"></td>
					            
					            <th>乘客座位责任险缴费金额：</th>
					            <td><input class="nt-text" type="text" name="passengerSeatLiability" id="passengerSeatLiability"></td>
					            <th>玻璃单独破碎险缴费金额：</th>
					            <td>
					              <input class="nt-text" type="text" name="breakageOfGlass" id="breakageOfGlass"></td>
					            
					            <th>车损险不计免赔条款缴费金额：</th>
					            <td><input class="nt-text" type="text" name="carDamage" id="carDamage"></td>
					            
					          </tr>
					          
					          <tr>
					            <th>三责险不计免赔条款缴费金额：</th>
					            <td>
					              <input class="nt-text" type="text" name="threeLiabilityInsurance" id="threeLiabilityInsurance">
					            </td>
					            <th>车身油漆单独损伤险缴费金额：</th>
					            <td colspan="8">
					              <input class="nt-text" type="text" name="paintDamage" id="paintDamage"></td>
					          </tr>
					          <tr>
					            <th>备注：</th>
					            <td colspan="7"><input class="nt-text-long" type="text" name="remark" id="remark"></td>
					          </tr>
					        </tbody>
						
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

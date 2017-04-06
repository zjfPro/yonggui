<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>车辆事故登记</title>
<link rel="stylesheet" href="oa/css/global.css" type="text/css"/>
<link rel="stylesheet" href="oa/css/list.css" type="text/css"/>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script src="oa/js/js_checked.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="oa/js/jquery-1.8.0.min.js"></script>
<script src="oa/js/jquery.validate.js"></script>
<script src="oa/js/validate.expand.js"></script>
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
			  url: "<%=basePath%>control/accident/update/updateAccident",
			  success: function(data){
			 	if(data == "1"){
			 		alert("修改成功");
			 		frameElement.api.close();
				}else if (data == "0") {
					alert("修改失败");
					location.reload();
				}else{
					alert(data);
				}
			}
		});
	}
	
	function accountDriverId(data){
			if(data!=null){
				$.ajax({
					type : "POST",
					url : "<%=basePath%>control/peccancy/account/driverId",
					data : {"carUseRecordsId":data},
					success : function(msg) {
						if(msg=="1"){
							cleanAll();
						}else if(msg=="ok"){
							alert("该客户不存在车辆使用记录中");
						}
					}
				});
			}
		}

</script>
</head>

<body>

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="infolist">
    <form name="form" id="form" method="post">
    <input type="hidden" name="id" value="${requestScope.data.id}" />
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">车辆事故登记</th>
          </tr>
        </thead>
          <tr>
            <th>付款状态：</th>
            <td>
              <select name="payStatus" class="nt-select">
               <option ${requestScope.data.payStatus == 0 ? "selected" : "" } value="0">未付款</option>
               <option ${requestScope.data.payStatus == 1 ? "selected" : "" } value="1">已付款</option>
              </select>
            </td>
            <th>支付时间：</th>
            <td><input type="text" id="payTimes" name="payTimes" class="Wdate nt-text" 
            		   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${requestScope.data.payTime}" pattern="yyyy-MM-dd"/>"></td>
            
           <th>操作员工：</th>
           <td>
	        <div class="list_date">
	            <select name="staffId" id="staffId" class="nt-select">
	            	<option value="${requestScope.data.staff.id }">${requestScope.data.staff.name }</option>
               		<c:forEach items="${requestScope.staffs }" var="data">
						<option value="${data.id }">${data.name}</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
          </tr>
          
          <tr>
            <th>门店驾驶员：</th>
           <td>
	        <div class="list_date">
	            <select name="driverId" id="driverId" class="nt-select">
	            	<option value="${requestScope.data.drivers.id}">${requestScope.data.drivers.name}</option>
               		<c:forEach items="${requestScope.drivers }" var="data">
						<option value="${data.id }">${data.name}</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
             <th>借车人名字：</th>
            <td>
	        <div class="list_date">
	            <select name="carUseRecordsId" id="carUseRecordsId" class="nt-select">
	            	<option value="${requestScope.data.records.id}">${requestScope.data.records.useUserName}</option>
               		<c:forEach items="${requestScope.records }" var="data">
						<option value="${data.id }">${data.useUserName}</option>
					</c:forEach>
	            </select>
	                  借车人驾驶员：
	            <input class="nt-text" type="text" id="driverName" name="driverName" value="${requestScope.data.driverName}"/>
	         </div>
	        </td>			
             <th>车牌：</th>
            <td>
            	<div class="list_date">
	            <select name="carInfoId" id="carInfoId" class="nt-select">
	            	<option value="${requestScope.data.infos.id}">${requestScope.data.infos.plateNumber}</option>
               		<c:forEach items="${requestScope.infos }" var="data">
						<option value="${data.id }">${data.plateNumber}</option>
					</c:forEach>
	            </select>
	         </div>
            
            </td>
            
          </tr>
     	 </table>
     	 <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">违章记录</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th>事故时间：</th>
            <td><input type="text" id="trafficAccidentTimes" name="trafficAccidentTimes" class="Wdate nt-text" 
            			onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${requestScope.data.trafficAccidentTime}" pattern="yyyy-MM-dd"/>"></td>
            <th>事故地点：</th>
            <td><input class="nt-text" type="text" id="trafficAccidentAddress" name="trafficAccidentAddress" value="${requestScope.data.trafficAccidentAddress}"></td>
            <th>事故情况：</th>
            <td><input class="nt-text" type="text" id="trafficAccidentInfo" name="trafficAccidentInfo" value="${requestScope.data.trafficAccidentInfo}"></td>
            <th>事故描述：</th>
            <td><input class="nt-text" type="text" id="peccancyDescribe" name="peccancyDescribe" value="${requestScope.data.peccancyDescribe}"></td>
          </tr>
          
          <tr>
            <th>责任认定：</th>
            <td colspan="7"><input class="nt-text-long" type="text" id="responsibility" name="responsibility" value="${requestScope.data.responsibility}"></td>
          </tr>
        </tbody>
        <tfoot></tfoot>
        </table>
        
        <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">处理结果</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <th>赔偿金额：</th>
            <td><input class="nt-text" type="text" id="compensateMoney" name="compensateMoney" value="${requestScope.data.compensateMoney}"></td>
            <th>个人负担比例：</th>
            <td><input class="nt-text" type="text" id="driverProportion" name="driverProportion" value="${requestScope.data.driverProportion}" placeholder="输入数字"></td>
            <th>公司负担比例：</th>
            <td><input class="nt-text" type="text" id="companyProportion" name="companyProportion" value="${requestScope.data.companyProportion}" placeholder="输入数字"></td>
            <th>个人负担金额：</th>
            <td><input class="nt-text" type="text" id="driverFineMoney" name="driverFineMoney" value="${requestScope.data.driverFineMoney}"></td>
          </tr>
          
          <tr>
            <th>公司负担金额：</th>
             <td><input class="nt-text" type="text" id="companyFineMoney" name="companyFineMoney" value="${requestScope.data.companyFineMoney}"></td>
            <th>公司额外罚款：</th>
             <td><input class="nt-text" type="text" id="companyPunishmentMoney" name="companyPunishmentMoney" value="${requestScope.data.companyPunishmentMoney}"></td>
          </tr>
          <tr>
            <th>备注：</th>
            <td colspan="7"><input class="nt-text-long" type="text" id="remark" name="remark" value="${requestScope.data.remark}"></td>
          </tr>
        </tbody>
        <tfoot></tfoot>
      </table>
	   	 <div class="nt-btmbox">
			<input type="button" value="保存" class="btn" onclick="checkForm()"/> 
		</div>
      </form>
    </div>
  </div>
</div>
</body>
</html>


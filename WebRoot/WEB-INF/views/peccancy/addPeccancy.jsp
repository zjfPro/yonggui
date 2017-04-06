<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>车辆违章登记</title>
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
			  url: "<%=basePath%>control/peccancy/add/addPeccancy",
			  success: function(data){
			 	if(data == "1"){
			 		alert("添加成功");
			 		frameElement.api.close();
				}else if (data == "0") {
					alert("添加失败");
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
							alert("该借车人不存在");
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
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">车辆违章登记</th>
          </tr>
        </thead>
          <tr>
            <th>付款状态：</th>
            <td>
              <select name="payStatus" class="nt-select">
                <option value="0">未付款</option>
                <option value="1">已付款</option>
              </select>
            </td>
            <th>支付时间：</th>
            <td><input type="text" id="payTimes" name="payTimes" class="Wdate nt-text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
            
           <th>操作员工：</th>
           <td>
	        <div class="list_date">
	            <select name="staffId" id="staffId" class="nt-select">
	            	<option value="">请选择</option>
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
	            	<option value="">可选择</option>
               		<c:forEach items="${requestScope.drivers }" var="data">
						<option value="${data.id }">${data.name}</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
            <th>借车人名字：</th><!-- <input class="nt-text" type="text" id="carUseRecordsId" name="carUseRecordsId" onchange="accountDriverId(this.value)" placeholder="输入借车人名字"> -->
            <td>
	        <div class="list_date">
	            <select name="carUseRecordsId" id="carUseRecordsId" class="nt-select">
	            	<option value="">请选择</option>
               		<c:forEach items="${requestScope.records }" var="data">
						<option value="${data.id }">${data.useUserName}</option>
					</c:forEach>
	            </select>
	                   借车人驾驶员：
	              <input class="nt-text" type="text" id="driverName" name="driverName" placeholder="请输入驾驶员名字"/>
	         </div>
	        </td>
             <th>车牌：</th>
            <td>
            	<div class="list_date">
	            <select name="carInfoId" id="carInfoId" class="nt-select">
	            	<option value="">请选择</option>
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
            <th>违章时间：</th>
            <td><input type="text" id="peccancyTimes" name="peccancyTimes" class="Wdate nt-text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
            <th>违章地点：</th>
            <td><input class="nt-text" type="text" id="peccancyAddress" name="peccancyAddress"></td>
            <th>违章情况：</th>
            <td><input class="nt-text" type="text" id="peccancyInfo" name="peccancyInfo"></td>
            <th>情况描述：</th>
            <td><input class="nt-text" type="text" id="peccancyDescribe" name="peccancyDescribe"></td>
          </tr>
          
          <tr>
            <th>处罚意见：</th>
            <td colspan="7"><input class="nt-text-long" type="text" id="punish" name="punish"></td>
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
            <th>处罚金额：</th>
            <td><input class="nt-text" type="text" id="fineMoney" name="fineMoney"></td>
            <th>个人负担比例：</th>
            <td><input class="nt-text" type="text" id="driverProportion" name="driverProportion" placeholder="输入数字"></td>
            <th>公司负担比例：</th>
            <td><input class="nt-text" type="text" id="companyProportion" name="companyProportion" placeholder="输入数字"></td>
            <th>个人负担金额：</th>
            <td><input class="nt-text" type="text" id="driverFineMoney" name="driverFineMoney"></td>
          </tr>
          
          <tr>
            <th>公司负担金额：</th>
             <td><input class="nt-text" type="text" id="companyFineMoney" name="companyFineMoney"></td>
            <th>公司额外罚款：</th>
             <td><input class="nt-text" type="text" id="companyPunishmentMoney" name="companyPunishmentMoney"></td>
          </tr>
          <tr>
            <th>备注：</th>
            <td colspan="7"><input class="nt-text-long" type="text" id="remark" name="remark"></td>
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


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
			  url: "<%=basePath%>control/record/update/updateUserRecord",
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
	function dataChange(pid){
			if(pid != -1000){
				$.ajax({
					type:"post",
					url:"<%=basePath%>control/record/select/carBrandList",
					data:{pid:pid},
					dataType:"json",
					success:function(data){
						if(data != null){
							var str = "";
							$.each(data,function(i,n){
								str += "<option value='"+n.id+"'>"+n.name+"</option>";
							});
							$("#satffId").html(str);
							$("#handoverStaffId").html(str);
						}
					}
				  });
				}else if(data == null){
				$("#satffId").html("<option value='-100'>请选择</option>");
			}
		}
	
</script>
</head>

<body class="width900">

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="infolist">
     <form name="form" id="form" method="post">
     <input type="hidden" id="ccId" name="ccId" value="${requestScope.records.id }" />
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">必填</th>
          </tr>
        </thead>
        <tbody>
          <tr>
          <th>门店：</th>
	      <td>
	        <div class="list_date">
	            <select name="shopFrontId" id="shopFrontId" onchange="dataChange(this.value)" class="nt-select">
            	<option value="${requestScope.records.shopFront.id }">${requestScope.records.shopFront.name }</option>
              		<c:forEach items="${requestScope.fronts }" var="data">
					<option value="${data.id }">${data.name }</option>
				</c:forEach>
            	</select>
	         </div>
	        </td>
	        <th>借车操作员工：</th>
		     <td>
		        <div class="list_date">
		            <select name="satffId" id="satffId" class="nt-select">
		            	<option value="${requestScope.records.staff.id }">${requestScope.records.staff.name }</option>
		            	<c:forEach items="${requestScope.yuangong }" var="data">
							<option value="${data.id }">${data.name }</option>
						</c:forEach>
		            </select>
		        </div>
		       </td>
		       <th>借车人名字：</th>
		       <td><input type="text" name="useUserName" id="useUserName" class="nt-text" value="${requestScope.records.useUserName }"/></td>
          </tr>
          <tr>
            <th>借车人联系方式：</th>
			<td><input type="text" name="useUserPhone" id="useUserPhone" class="nt-text" value="${requestScope.records.useUserPhone }"/></td>
            <th>车牌：</th>
             <td>
				<div class="list_date">
				<select name="carInfoId" id="carInfoId" class="nt-select">
	            	<option value="${requestScope.records.infos.id }">${requestScope.records.infos.plateNumber }</option>
               		<c:forEach items="${requestScope.infos }" var="data">
						<option value="${data.id }">${data.plateNumber}</option>
					</c:forEach>
	            </select>
	            </div>
	          </td>
            <th>当前里程数：</th>
			<td><input type="text" name="currentMileage" id="currentMileage" class="nt-text" value="${requestScope.records.currentMileage }"/></td>
          </tr>
          
          <tr>
            <th>归还时间：</th>
			<td><input type="text" id="returnTimes" name="returnTimes" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${requestScope.records.returnTime }" pattern="yyyy-MM-dd"/>"
              					  class="nt-text" ><a onclick="cleaninput('returnTimes')">清空</a></td>
			<th>归还时的里程数：</th>
			<td><input type="text" name="returnMileage" id="returnMileage" class="nt-text" value="${requestScope.records.returnMileage }"/></td>
			<th>编号：</th>
			<td><input type="text" name="number" id="number" class="nt-text" value="${requestScope.records.number }"/></td>
          </tr>
		  <tr>
		  <th>租车订单：</th>
		  <td>
	        <div class="list_date">
	            <select name="carRentalOrderId" id="carRentalOrderId" class="nt-select">
	            	<option value="${requestScope.records.orders.id }">${requestScope.records.orders.number }</option>
               		<c:forEach items="${requestScope.orders }" var="data">
						<option value="${data.id }">${data.number }</option>
					</c:forEach>
	            </select>
	         </div>
	        </td> 
           <th>还车操作员工：</th>
	      	<td>
	        <div class="list_date">
	            <select name="handoverStaffId" id="handoverStaffId" class="nt-select">
	             <option value="${requestScope.records.staff2.id }">${requestScope.records.staff2.name }</option>
	             <c:forEach items="${requestScope.yuangong }" var="data">
						<option value="${data.id }">${data.name }</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
          </tr>
        </tbody>
        <tfoot></tfoot>
      </table>
      </form>
      <div class="nt-btmbox">
       <input type="button" value="保存" class="btn" onclick="checkForm()"/> 
      </div>
    </div>
  </div>
</div>
</body>
</html>

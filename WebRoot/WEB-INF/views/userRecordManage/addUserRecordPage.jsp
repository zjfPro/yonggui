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
			  url: "<%=basePath%>control/record/add/addUserRecord",
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
		function checkCom(val) {
		var t = /^(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
		var phone = $("#useUserPhone").val().trim();
		if (!t.test(phone)) {
			alert("请输入正确的联系方式！！！");
			return false;
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
            	<option value="">请选择</option>
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
		            </select>
		        </div>
		       </td>
		       <th>借车人名字：</th>
		       <td><input type="text" name="useUserName" id="useUserName" class="nt-text" /></td>
          </tr>
          <tr>
            <th>借车人联系方式：</th>
			<td><input type="text" name="useUserPhone" id="useUserPhone" class="nt-text" onchange="checkCom(this.value)"/></td>
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
            <th>当前里程数：</th>
			<td><input type="text" name="currentMileage" id="currentMileage" class="nt-text" /></td>
          </tr>
          
          <tr>
            <th>归还时间：</th>
			<td><input type="text" id="returnTimes" name="returnTimes" 
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="nt-text" ></td>
			<th>归还时的里程数：</th>
			<td><input type="text" name="returnMileage" id="returnMileage" class="nt-text" /></td>
			<th>编号：</th>
			<td><input type="text" name="number" id="number" class="nt-text" /></td>
          </tr>
		  <tr>
		  <th>租车订单：</th>
		  <td>
	        <div class="list_date">
	            <select name="carRentalOrderId" id="carRentalOrderId" class="nt-select">
	            	<option value="">请选择</option>
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

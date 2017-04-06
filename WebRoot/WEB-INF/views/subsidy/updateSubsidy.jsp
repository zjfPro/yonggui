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
<title>车辆补贴</title>
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
			  url: "<%=basePath%>control/subsidy/update/updateSubsidy",
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

</script>
</head>

<body>

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="infolist">
     <form name="form" id="form" method="post">
      <input type="hidden" name="id" value="${requestScope.subsidy.id}" />
        <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">出车补贴</th>
          </tr>
        </thead>
        <tbody>
          <tr>
          <th>驾驶员：</th>
           <td>
	        <div class="list_date">
	            <select name="driverId" id="driverId" class="nt-select">
	            	<option value="${requestScope.subsidy.drivers.id}">${requestScope.subsidy.drivers.name}</option>
               		<c:forEach items="${requestScope.drivers }" var="data">
						<option value="${data.id }">${data.name}</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
            <th>车牌：</th>
            <td><input class="nt-text" type="text" id="plateNumber" name="plateNumber" value="${requestScope.subsidy.plateNumber}"></td>
          </tr>
          
          <tr>
            <th>申报费用：</th>
            <td><input class="nt-text" type="text" id="money" name="money" value="${requestScope.subsidy.money}"></td>
            
            <th>支付时间：</th>
             <td><input type="text" id="payTimes" name="payTimes" class="Wdate nt-text" 
             		 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${requestScope.subsidy.payTime}" pattern="yyyy-MM-dd"/>"></td>
          </tr>
          <tr>
            <th>理由：</th>
            <td><input class="nt-text" type="text" id="reason" name="reason" value="${requestScope.subsidy.reason}"></td>
            <th>回复内容：</th>
            <td><input class="nt-text" type="text" id="reply" name="reply" value="${requestScope.subsidy.reply}"></td>
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


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
	
	function cleanPage(){
		window.location.href="<%=basePath%>control/subsidy/select/subsidyList";
	}
	
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/subsidy/update/updateCheck",
			  success: function(data){
			 	if(data == "1"){
			 		alert("审核成功");
			 		frameElement.api.close();
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
	        ${requestScope.subsidy.drivers.name}
	        </td>
            <th>车牌：</th>
            <td>${requestScope.subsidy.plateNumber}</td>
          </tr>
          
          <tr>
            <th>申报费用：</th>
            <td>${requestScope.subsidy.money}</td>
            
            <th>支付时间：</th>
             <td><fmt:formatDate value="${requestScope.subsidy.payTime}" pattern="yyyy-MM-dd"/></td>
          </tr>
          <tr>
            <th>理由：</th>
            <td>${requestScope.subsidy.reason}</td>
            <th>回复内容：</th>
            <td>${requestScope.subsidy.reply}</td>
          </tr>
          <tr>
		 <th>审核：</th>
			<td><select name="status" id="status"  class="nt-select">
				<option <c:if test="${requestScope.subsidy.status==1}">selected="selected"</c:if> value="1">通过</option>
				<option <c:if test="${requestScope.subsidy.status==2}">selected="selected"</c:if> value="2">审批中...</option>
				<option <c:if test="${requestScope.subsidy.status==-1}">selected="selected"</c:if> value="-1">不通过</option>
			</select><td/>
		 </tr>
        </tbody>
        <tfoot></tfoot>
      </table>
	   	 <div class="nt-btmbox">
			<input type="button" value="确定" class="btn" onclick="checkForm()"/> 
		</div>
      </form>
    </div>
  </div>
</div>
</body>
</html>


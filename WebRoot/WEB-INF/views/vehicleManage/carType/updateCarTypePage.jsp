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
			  url: "<%=basePath%>control/carType/update/updateCarType",
			  success: function(data){
			 	if(data == "1"){
			 		alert("修改成功");
			 		frameElement.api.close();
				}else if (data == "0") {
					alert("修改失败");
				}else{
					alert(data);
				}
			}
		});
	}
	function checkRate(val) {
		var t = /^[1-9]+[0-9]*]*$/;
		var shuz = $("#displacement").val().trim();
		if (!t.test(shuz)) {
			alert("排量只能输入整数！！！");
			return false;
		}

	}
</script>
</head>

<body class="width900">

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="current">车辆类型</div>
    <div class="infolist">
     <form name="form" id="form" method="post">
     <input type="hidden" id="ccId" name="ccId" value="${requestScope.type.id }" />
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">必填</th>
          </tr>
        </thead>
        <tbody>
          <tr>
          <th>车辆品牌：</th>
	      <td>
	        <div class="list_date">
	            <select name="carBrandId" id="carBrandId" class="nt-select">
	            	<option value="${requestScope.type.carBrand.id }">${requestScope.type.carBrand.info }</option>
               		<c:forEach items="${requestScope.brands }" var="data">
						<option value="${data.id }">${data.info }</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
	        <th>车身结构：</th>
		     <td>
		        <div class="list_date">
		            <select name="carStructureId" id="carStructureId" class="nt-select">
		            	<option value="${requestScope.type.structure.id }">${requestScope.type.structure.name }</option>
	               		<c:forEach items="${requestScope.structures }" var="data">
							<option value="${data.id }">${data.name }</option>
						</c:forEach>
		            </select>
		         </div>
		       </td>
		       <th>发动机：</th>
		       <td><input type="text" name="engine" id="engine" value="${requestScope.type.engine }" class="nt-text"/></td>
          </tr>
          <tr>
            <th>变速箱：</th>
			<td><input type="text" name="transmissionCase" id="transmissionCase" value="${requestScope.type.transmissionCase }" class="nt-text"/></td>
            <th>最高车速(km/h)：</th>
			<td><input type="text" name="speedMax" id="speedMax" value="${requestScope.type.speedMax }" class="nt-text"/></td>
            <th>官方百公里加速时间(s)：</th>
			<td><input type="text" name="accelerateTime" id="accelerateTime" value="${requestScope.type.accelerateTime }" class="nt-text"/></td>
          </tr>
          
          <tr>
            <th>官方百公里油耗(L/100km)：</th>
			<td><input type="text" name="gasolineConsumption" id="gasolineConsumption" value="${requestScope.type.gasolineConsumption }" class="nt-text"/></td>
			<th>排量(mL)：</th>
			<td><input type="text" name="displacement" id="displacement" value="${requestScope.type.displacement }" class="nt-text" onchange="checkRate()"/></td>
			<th>燃料形式：</th>
			<td><input type="text" name="fuelForm" id="fuelForm" value="${requestScope.type.fuelForm }" class="nt-text"/></td>
          </tr>
		  <tr>
		  <th>燃油标号：</th>
		  <td>
            <select name="fuelLabel" class="nt-select">
               <option ${requestScope.type.fuelLabel == 0 ? "selected" : "" } value="0">0#</option>
               <option ${requestScope.type.fuelLabel == 1 ? "selected" : "" } value="1">90#</option>
               <option ${requestScope.type.fuelLabel == 2 ? "selected" : "" } value="2">93#</option>
               <option ${requestScope.type.fuelLabel == 3 ? "selected" : "" } value="3">97#</option>
               <option ${requestScope.type.fuelLabel == 4 ? "selected" : "" } value="4">92#</option>
               <option ${requestScope.type.fuelLabel == 5 ? "selected" : "" } value="5">95#</option>
               <option ${requestScope.type.fuelLabel == 6 ? "selected" : "" } value="6">98#</option>
            </select>
           <th>描述：</th>
           <td colspan="7"><input class="nt-area" id="info" name="info" value="${requestScope.type.info }"/></td>
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

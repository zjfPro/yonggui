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
		//checkRate();
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/carType/add/addCarType",
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
	            	<option value="">请选择</option>
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
		            	<option value="">请选择</option>
	               		<c:forEach items="${requestScope.structures }" var="data">
							<option value="${data.id }">${data.name }</option>
						</c:forEach>
		            </select>
		         </div>
		       </td>
		       <th>发动机：</th>
		       <td><input type="text" name="engine" id="engine" class="nt-text"/></td>
          </tr>
          <tr>
            <th>变速箱：</th>
			<td><input type="text" name="transmissionCase" id="transmissionCase" class="nt-text"/></td>
            <th>最高车速(km/h)：</th>
			<td><input type="text" name="speedMax" id="speedMax" class="nt-text"/></td>
            <th>官方百公里加速时间(s)：</th>
			<td><input type="text" name="accelerateTime" id="accelerateTime" class="nt-text"/></td>
          </tr>
          
          <tr>
            <th>官方百公里油耗(L/100km)：</th>
			<td><input type="text" name="gasolineConsumption" id="gasolineConsumption" class="nt-text"/></td>
			<th>排量(mL)：</th>
			<td><input type="text" name="displacement" id="displacement" class="nt-text" onchange="checkRate()"/></td>
			<th>燃料形式：</th>
			<td><input type="text" name="fuelForm" id="fuelForm" class="nt-text"/></td>
          </tr>
		  <tr>
		  <th>燃油标号：</th>
		  <td>
            <select name="fuelLabel" class="nt-select">
                 <option value="0">0#</option>
                 <option value="1">90#</option>
                 <option value="2">93#</option>
                 <option value="3">97#</option>
                 <option value="4">92#</option>
                 <option value="5">95#</option>
                 <option value="6">98#</option>
            </select></td> 
           <th>描述：</th>
           <td colspan="7"><textarea class="nt-area" id="info" name="info"></textarea></td>
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

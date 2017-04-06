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
			  url: "<%=basePath%>control/series/add/addSeries",
			  success: function(data){
			 	if(data == "1"){
			 		alert("添加成功");
			 		//$("#series").val("");
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
					url:"<%=basePath%>control/series/select/manufacturerList",
					data:{pid:pid},
					dataType:"json",
					success:function(data){
						if(data != null){
							var str = "";
							$.each(data,function(i,n){
								str += "<option value='"+n.id+"'>"+n.manufacturer+"</option>";
							});
							$("#manufacturer").html(str);
						}
					}
				  });
				}else if(data == null){
				$("#manufacturer").html("<option value='-100'>请选择</option>");
			}
		}
	
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span> <a href="control/series/select/seriesList">车辆系列</a><span>></span>增加系列
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<table class="left_table">
					    <tr class="line_g">
					      <td  width="40%" align="right">品牌：</td>
					      <td>
					        <div>
					            <select name="parentId" id="parentId" onchange="dataChange(this.value)" class="nt-text">
					            	<option value="">请选择</option>
				               		<c:forEach items="${requestScope.brands }" var="data">
										<option value="${data.id }">${data.brand }</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
					    </tr>
						 
						<tr class="line_g">
					      <td width="40%" align="right">厂商：</td>
					      <td>
					        <div class="list_date">
					            <select name="manufacturer" id="manufacturer" class="nt-text">
					            	<option value="-100">请选择</option>
					            </select>
					         </div>
					        </td>
					    </tr>
						<tr class="line_g">
							<td width="40%" align="right">系列：</td>
							<td><input type="text" class="nt-text" name="series" id="series"/><a onclick="cleaninput('series')">清空</a></td>
						</tr>
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

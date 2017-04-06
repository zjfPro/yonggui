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
<title>管理平台</title>
<link rel="stylesheet" href="oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="oa/css/list.css" type="text/css" />
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	 function deleteChecked() {
           var flag = false;
           var deId = document.getElementsByName("ccId");
          	var arrayValue = "[";
          	$.each($("input[name='ccId']:checked"), function(i, n) {
				if (n.value != null && n.value.length > 0) {
					arrayValue += n.value;
					arrayValue += ",";
				}
			});
          	arrayValue += "]";

           
           if (arrayValue.length<3) {
            alert("请选择需要删除的数据！");
            return ;
           } 
           if (window.confirm("确认删除所选中的数据？")) {
        		$.ajax({
    				url : "<%=basePath%>control/amc/delete/deleteAdminlog",
    				data : "ccId=" + arrayValue,
    				success : function(data) {
    					if (data == "1") {
    						alert("删除成功");
    						location.reload(true);
    					} else {
    						alert(data);
    					}
    				}
    			});
          	return true;
           }else{
			return false;
           }
          
         }
         
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a href="#">个人桌面</a> > <span></span> <a href="#">操作列表</a> > <span></span>操作信息
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>umc/userList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="25">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a href="javascript:changeRows();">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="text" value="${requestScope.text}" placeholder="管理员名字"/><input
												class="sea_btn" type="button" onclick="changeRows()" />
										</div>
										<!-- <div class="list_date fright">
												<select name="type"  onchange="changeRows()"  class="select">
													<option value="-1">全部</option>
													<option value="0"></option>
													<option value="1"></option>
												</select>
										</div> -->
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>管理员</th>
								<th>操作时间</th>
								<th>操作类型</th>
								<th>操作内容</th>
								<th>IP地址</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<label><input type="checkbox" id="ccId" name="ccId" value="${data.id}" /> </label>
									</td>
								<td>${data.admin.nickname}</td>
								<td>${data.actionDate}</td>
								<td>${data.actionDesc }</td>
								<td>${data.actionContent }</td>
								<td>${data.actionIp }</td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="25" valign="middle">
								<%@include file="/WEB-INF/views/include/page.jsp"%></td>
							</tr>
						</tfoot>
					</table>
				</form>
			</div>
		</div>
	</div>

	</div>
</body>
</html>

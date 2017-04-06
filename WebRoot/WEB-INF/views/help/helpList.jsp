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
<!---NO.1	新引用样式文件，及js文件--->
<link type="text/css" rel="stylesheet" href="oa/js/popup/default.css">
<link type="text/css" rel="stylesheet" href="oa/js/popup/prettify.css">
<script src="oa/js/popup/prettify.js"></script>
<script src="oa/js/popup/jquery-1.js"></script>
<script src="oa/js/popup/lhgdialog.js"></script>
<script src="oa/js/js_checked.js"></script>
<!---NO.1	End		新引用样式文件，及js文件--->
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
function updateType(id){
 $.dialog({
	title: '修改信息',
	content: 'url:<%=basePath%>control/help/goto/updateHelp?ccId='+id,	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '500px',
	lock: true,			
	background: '#000',
    opacity: 0.4,
    close:changeRows
     });
}      
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a href="<%=basePath%>control/help/select/helpList">帮助中心</a> > <span></span>帮助类型
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post">
					<table class="center_table" id="table_all">
						<thead>
							<tr>
								<td colspan="16">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a href="javascript:changeRows();">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											查询：<input class="sea_text" type="text" name="text" value="${requestScope.text}" placeholder="标题"/><input
												class="sea_btn" type="button" onclick="changeRows()" />
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th width="60">选择</th>
								<th>类型</th>
								<th>标题</th>
								<!-- <th>内容</th> -->
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<label><input type="checkbox" id="ccId" name="ccId" value="${data.id}" /> </label>
									</td>
									<td><c:if test="${data.helpType==1}">租车指南</c:if>
									<c:if test="${data.helpType==2}">租车流程</c:if>
									<c:if test="${data.helpType==3}">企业租凭</c:if>
									<c:if test="${data.helpType==4}">长期租车</c:if>
									<c:if test="${data.helpType==5}">短期租车</c:if>
									</td>
									<td>${data.title}</td>
									<td>
						              <a class="operation-btn" onclick="updateType('${data.id}')">修改</a>
						            </td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="16" valign="middle">
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

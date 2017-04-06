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
<script src="oa/js/jquery.validate.js"></script>
<script src="oa/js/validate.expand.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span> <a>车辆信息</a>
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="" enctype="multipart/form-data">
					<table class="left_table">
						<tr class="line_g">
							<td width="10%">车辆实图：</td>
							<td>
								<div id="display_img"
									style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
									<img id="imghead" style="width: 100px;height: 100px;"
										src="${requestScope.data.picture}"/>
								</div>  
							</td>
						</tr>
						<tr class="line_g">
					      <td>车类型：</td>
					      <td>
					            	${requestScope.data.carType.carBrand.info }
				               		
					        </td>
					        
					    </tr>
						<tr class="line_g">
							<td width="10%">车牌：</td>
							<td>${requestScope.data.plateNumber }</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">车龄：</td>
							<td>${requestScope.data.carAge }</td>
						</tr>
						<tr class="line_g">
							<td width="10%">里程：</td>
							<td>${requestScope.data.mileage }</td>
						</tr>
						<tr class="line_g">
							<td width="10%">车主：</td>
							<td>${requestScope.data.owner }</td>
						</tr>
						<tr class="line_g">
					      <td>门店：</td>
					      <td>
					        ${requestScope.data.shopFront.name }
					        </td>
					    </tr>
						
						
						<tr class="line_g">
							<td width="10%">签约开始时间：</td>
							<td>
							
							<fmt:formatDate value="${requestScope.data.startTimeOfContract}" pattern="yyyy-MM-dd"/>
							</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">签约结束时间：</td>
              					 <td><fmt:formatDate value="${requestScope.data.endTimeOfContract}" pattern="yyyy-MM-dd"/>
              					 </td>
						</tr>
						
					
						<tr class="line_g">
							<td width="10%">备注：</td>
							<td>${requestScope.data.remark }
							</td>
						</tr>
						<tr class="line_g">
							<td colspan="2">
								<input type="button" value="返回" class="btn" onclick="history.go(-1)"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

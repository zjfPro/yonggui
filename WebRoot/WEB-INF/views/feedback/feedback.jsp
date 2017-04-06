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
<script>
	function cleanPage(){
	    window.location.href="<%=basePath%>control/feedback/select/feedbackList";
	}
     
     function deleteChecked(){
            var str="";
            $("input[name='theCheckbox']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+","
                }
            });
            var strs = str.split(",");
            var selcount = (strs.length-1);
            if(selcount!=0){
            	var queren = confirm("删除后无法恢复,确定删除勾选的"+selcount+"条数据?");
            	if(queren){
            		$.ajax({
					type:'post',
					url:'<%=basePath%>control/feedback/delete/deleteFeedback',
					data:{"strs":str},
					success : function(data) {
						if(data=='1'){
							alert("删除成功");
							cleanPage();
						}else{
							alert(data);
						}
					}
				});
            	}
            }else{
            	alert("请勾选需要删除的数据");
            }
     
     }
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span> <a>基础信息</a><span>></span>客户反馈列表
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>control/feedback/select/feedbackList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="20">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a onclick="cleanPage();">刷新</a></li> 
										</ul>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th></th>
								<th>姓名</th>
								<th>联系电话</th>
								<th>qq</th>
								<th>反馈内容</th>
								<th>反馈添加时间</th>
								
							</tr>
							
							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30">
										<p>
											<label><input type="checkbox" name="theCheckbox" value="${data.id}"
												id="theCheckbox" /> </label>
										</p>
									</td>
									<td>${data.name}</td>
									<td>${data.phone}</td>
									<td>${data.qq}</td>
									<td>${data.content}</td>
									<td><fmt:formatDate value="${data.addtime}" pattern="yyyy-MM-dd HH:mm"/></td>
									
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="13" valign="middle">
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

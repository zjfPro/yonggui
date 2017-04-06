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
<script>
	function cleanPage(){
	    window.location.href="<%=basePath%>control/authority/select/cgStaffManageModelList";
	}
	function checkDetail() {
            var str="";
            $("input[name='theCheckbox']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+","
                }
            })
            var strs = str.split(",");
            var selcount = (strs.length-1);
            if(selcount!=0){
            	if(selcount==1){
            		var modelid = strs[0];
            	    window.location.href="control/authority/select/modelDetail?modelid="+modelid; 
            	    
            	}else{
            		alert("你勾选了"+selcount+"条数据,请只勾选一条数据修改");
            	}
            }else{
            		alert("没有勾选数据,请只勾选一条数据修改");
            }
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
					url:'<%=basePath%>control/authority/delete/deleteCcgStaffManageModel',
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
				<a>个人桌面</a><span> > </span> <a>基础信息</a><span>></span>模块列表
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>control/authority/select/cgStaffManageModelList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="13">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a href="control/authority/goto/addCcgStaffManageModel">新增</a></li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a onclick="checkDetail()">修改</a></li> 
											<li><a onclick="cleanPage();">刷新</a></li> 
											<li><a href="control/authority/select/showModel">展示</a></li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" /><input class="sea_btn"
												type="button" />
										</div>
										<div class="list_date fright">
											<form name="form" id="form">
												<select name="jumpMenu" id="jumpMenu"
													onchange="MM_jumpMenu('parent',this,0)" class="select">
													<option>全部</option>
													<option>组织机构</option>
												</select>
											</form>
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th></th>
								<th>模块</th>
								<th>url</th>
								<th>父模块</th>
								<th>类型</th>
							</tr>



							<!-- 						<tr class="hover_bg">
							<td>
								<p>
									<label><input type="checkbox" name="" value="单选框" id="" />
									</label>
								</p>
							</td>
							<td>1</td>
							<td>通报</td>
							<td><a href="#" class="color_red">关于免去贾留华大学生创业网总经理职务的决定</a>
							</td>
							<td>2013-04-27</td>
							<td><a href="#" class="name">赵常升</a>
							</td>
						</tr> -->
							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<p>
											<label><input type="checkbox" name="theCheckbox" value="${data.id}"
												id="theCheckbox" /> </label>
										</p></td>
									<td>${data.text}</td>
									<td>
										<c:if test="${data.url!=''}">${data.url}</c:if>
									    <c:if test="${data.url==''}">..</c:if>
									</td>
									
									<td>
										<c:if test="${data.csmm!=null}">${data.csmm.text} </c:if>
									    <c:if test="${data.csmm==null}">..</c:if>
									</td>
									
									<td>
										<c:if test="${data.csmm!=null}"> 
											<c:if test="${data.type==0}">查询</c:if>
											<c:if test="${data.type==1}">新增</c:if>
											<c:if test="${data.type==2}">修改</c:if>
											<c:if test="${data.type==3}">删除</c:if>
											<c:if test="${data.type==4}">跳转</c:if>
											<c:if test="${data.type==5}">其他</c:if>
										</c:if>
										<c:if test="${data.csmm==null}">..</c:if>
									</td>
									
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

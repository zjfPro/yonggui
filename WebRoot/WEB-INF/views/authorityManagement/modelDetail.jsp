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
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->


<script type="text/javascript">
	function cleanPage(){
	    window.location.href="<%=basePath%>control/authority/select/cgStaffManageModelList";
	}
	function cleaninput(id){
		$("#"+id).val("");
	}
	
	function checkForm(){
		if($.trim($("#modelType").val())==''){
			alert("模块级别不能为空,请选择父模块或子模块");
			return;
		}
		if($.trim($("#text").val())==''){
			alert("模块名称不能为空");
			return;
		}
		if($.trim($("#url").val())==''&&$.trim($("#modelType").val())=='1'){
			alert("模块url不能为空");
			return;
		}
		if($.trim($("#modelType").val())=='1'){
			if($.trim($("#modelTypeData").val())==''||$.trim($("#modelTypeData").val())==null){
				alert("你选择了子模块，请选择父模块");
				return;
			}
			
		}
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/authority/update/gotoUpdateCcgStaffManageModel",
			  success: function(data){
			 	if(data == "1"){
			 		var queren = confirm("修改成功,是否返回模块列表?");
			 		if(queren){
			 			window.location.href="<%=basePath%>control/authority/select/cgStaffManageModelList";
			 		}
				}else{
					alert(data);
				}
			}
		});
	}
	
	function cleanAll(){
		$("#text").val("");
		$("#url").val("");
		$("#modelType").val("");
		$("#modelTypeData").find("option").remove();
	}
	
	function getOptionData(optionValue){
		if(optionValue==""){
			$("#modelTypeData").find("option").remove();
			var str = "<option>...</option>";
					$("#modelTypeData").html(str);
			return;
		}
		if(optionValue=="0"){
			$("#modelTypeData").find("option").remove();
			var str = "<option value=''>父类无类型选择...</option>";
					$("#modelTypeData").html(str);
			return;
		}
		
		$.ajax({
			type:'post',
			url:'<%=basePath%>control/authority/select/getParpenModel',
			dataType:'json',
			success:function(data){
				if(data.cgStaffManageModelList!=null){
					var str = "<option value=''>请选择父模块...</option>";
					$.each(data.cgStaffManageModelList,function(i,n){
						str += "<option value="+n.id+">"+n.text+"</option>";
					});
					$("#modelTypeData").html(str);
					
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
			<div class="current">
				<a href="#">个人桌面</a><span> > </span> <a href="#">基础信息</a><span>></span>修改模块
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<input type="hidden" name="modelID" id="modelID" value="${model.id}"/>
					<table class="left_table">

						<tr class="line_g">
							<td width="10%">模块名称：</td>
							<td><input type="text" class="text width300" name="text" id="text" value="${model.text}"
							/><a onclick="cleaninput('text')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td>模块级别：</td>
							<td><div class="list_date">

									<select name="modelType" id="modelType"
										onchange="getOptionData(this.value);" class="select" >
										<option value="">请选择...</option>
                    					<option value="0" <c:if test="${model.url==''}">selected="selected"</c:if>>父模块</option>
										<option value="1" <c:if test="${model.url!=''}">selected="selected"</c:if>>子模块</option>
									</select>
									&nbsp;&nbsp;
									<select name="modelTypeData" id="modelTypeData" style="width: 200px;">
										<c:if test="${model.url!=''}">
											<option value=''>请选择父模块...</option>
											<c:forEach items="${requestScope.modelPar}" var="data"
													varStatus="status">
						         <option value="${data.id}" <c:if test="${model.csmm.id==data.id}">selected="selected"</c:if>>${data.text}</option>;
											</c:forEach>
										</c:if>
										
										<c:if test="${model.url==''}">
										    <option value=''>父类无类型选择...</option>
										</c:if>
									</select>
									注:如果类型是子模块,此处必选
								</div>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">模块uri：</td>
							<td><input type="text" class="text width300" name="url" id="url"
							value="${model.url}"/><a onclick="cleaninput('url')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">模块类型：</td>
							<td>
							    <select name="type" id="mtype" style="width: 200px;">
							    
										<option value="0" <c:if test="${model.type==0}">selected="selected"</c:if> >查询</option>
										<option value="1" <c:if test="${model.type==1}">selected="selected"</c:if>>新增</option>
										<option value="2" <c:if test="${model.type==2}">selected="selected"</c:if>>修改</option>
										<option value="3" <c:if test="${model.type==3}">selected="selected"</c:if>>删除</option>
										<option value="4" <c:if test="${model.type==4}">selected="selected"</c:if>>跳转</option>
										<option value="5" <c:if test="${model.type==5}">selected="selected"</c:if>>其他</option>
							    </select>
							</td>
						</tr>
						
						<tr class="line_g">
							<td colspan="2">
								<input type="button" value="保存" class="btn" onclick="checkForm()"/> 
								<input type="button" value="取消" class="btn" onclick="cleanPage();"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

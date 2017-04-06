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

<!-- zTreeStyle专用 -->
<link rel="stylesheet" href="js/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">		
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "fatId"
				},
				key: {
					name: "text"
				}
			}
			
		};
		var treeObj;
		
		$(document).ready(function(){
			$.ajax({
				type:'post',
				url:'<%=basePath%>control/authority/select/getAllModel',
				dataType : 'json',
				success : function(data) {
					$.fn.zTree.init($("#treeDemo"), setting,data);
					
					//全展开
					treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.expandAll(true);
					
				}
			});
			
		});
		
		$(document).ready(function(){
			var csmrcID = $("#csmrcID").val();
			$.ajax({
			type:'post',
			url:'<%=basePath%>control/authority/select/getModelOfRole',
			dataType:'json',
			data:{"csmrcID":csmrcID},
			success:function(data){
				if(data.mapings!=null){
					
					$.each(data.mapings,function(i,n){
						var node = treeObj.getNodeByParam("id", n.staffManageModelid, null);
						treeObj.checkNode(node,true,true);
					});
				}else{
					alert(data);
				}
			}
		});
			
		});
		
	$(document).ready(function(){
			
			var mendian = $("#mendianID").val();
			$.ajax({
				type:'post',
				url:'<%=basePath%>ajax/select/getAllShopFront',
				dataType : 'json',
				success : function(data) {
					if(data.length>0){
						var str = "<option value=''>请选择门店...</option>";
						$.each(data,function(i,n){
							if(n.id==mendian){
								str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
							}else{
								str += "<option value="+n.id+">"+n.name+"</option>";
							}
							
						});
						$("#shopFrontId").html(str);
						
					}else{
						alert(data.errInfo);
					}
					
				}
			});
			
	});
</script>


<script type="text/javascript">
	function cleanPage(){
    	window.location.href="<%=basePath%>control/authority/select/csmrcList";
	}
	function cleaninput(id){
		$("#"+id).val("");
	}
	
	function isSelect(sel){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		if(sel=='yes'){
			treeObj.checkAllNodes(true);
		}else{
			treeObj.checkAllNodes(false);
		}
	}
	function isOpen(sel){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		if(sel=='yes'){
			treeObj.expandAll(true);
		}else{
			treeObj.expandAll(false);
		}
	}
	
	function checkForm(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = "";
		$.each(nodes,function(i,n){
			if(!n.isParent){
				ids+=n.id+",";
			}
		});
		var jsname = $("#jsname").val();
		var csmrcID = $("#csmrcID").val();
		var shopFrontId = $("#shopFrontId").val();
		$.ajax({
			  async: false,
			  type: "POST",
			  data: {"nodes":ids,"name":jsname,"csmrcID":csmrcID,"shopFrontId":shopFrontId},
			  url:'<%=basePath%>control/authority/update/gotoUpdateCsmrc',
			  success: function(data){
				  if(data == "1"){
					 	var queren = confirm("修改成功,是否返回角色列表?");
				 		if(queren){
				 			cleanPage();
				 	 	}
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
				<a>个人桌面</a><span> > </span> <a href="javascript:cleanPage();">角色管理</a><span>></span>修改角色
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<input  type="hidden" name="csmrcID" id="csmrcID" value="${csmrc.id}" />
					<table class="left_table">

						<tr class="line_g">
							<td width="10%">角色名称：</td>
							<td><input type="text" class="text width300" name="jsname"
								id="jsname" value="${csmrc.name}"/><a onclick="cleaninput('jsname')">清空</a>
							</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">所属门店：</td>
							<td>
							<input type="hidden" name="mendianID" id="mendianID" value="${csmrc.cgShopFront.id }"/>
							<select name="shopFrontId" id="shopFrontId" style="width: 200px;"></select>
							</td>
						</tr>

						<tr class="line_g">
							<td width="10%">角色模块：</td>
							<td>
							<a onclick="isSelect('yes')">全选</a>&nbsp;&nbsp;&nbsp;<a onclick="isSelect('no')">不选</a>
							
							&nbsp;&nbsp;&nbsp;<a onclick="isOpen('yes')">全展开</a>&nbsp;&nbsp;&nbsp;<a onclick="isOpen('no')">全关闭</a>
								<div>
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</td>
						</tr>
					

						<tr class="line_g">
							<td colspan="2"><input type="button" value="保存" class="btn"
								onclick="checkForm()" /> <input type="button" value="取消"
								class="btn" onclick="cleanPage();" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

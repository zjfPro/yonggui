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

		$(document).ready(function(){
			$.ajax({
				type:'post',
				url:'<%=basePath%>control/roleContainer/select/getAllModel',
				dataType : 'json',
				success : function(data) {
					$.fn.zTree.init($("#treeDemo"), setting,data);
					
					//全展开
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.expandAll(true);
					
				}
			});
			
		});
</script>


<script type="text/javascript">
	function cleanPage(){
    	window.location.href="<%=basePath%>control/authority/select/cgStaffManageModelList";
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
		$.ajax({
			  async: false,
			  type: "POST",
			  data: {"nodes":ids,"name":jsname},
			  url:'<%=basePath%>control/authority/add/gotoAddCsmrc',
			  success: function(data){
			 	alert(data);
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
				<a href="#">个人桌面</a><span> > </span> <a href="#">基础信息</a><span>></span>模块树形展示
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<table class="left_table">

						

						<tr class="line_g">
							<td width="10%">模块：</td>
							<td>
							
							
							&nbsp;&nbsp;&nbsp;<a onclick="isOpen('yes')">全展开</a>&nbsp;&nbsp;&nbsp;<a onclick="isOpen('no')">全关闭</a>
								<div>
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</td>
						</tr>
					

						<tr class="line_g">
							<td colspan="2"><input type="button" value="返回"
								class="btn" onclick="cleanPage();" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

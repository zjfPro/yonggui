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
<title>模板一</title>
<link rel="stylesheet" href="oa/css/global.css" type="text/css"/>
<link rel="stylesheet" href="oa/css/list.css" type="text/css"/>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<!-- zTreeStyle专用 start-->
<link rel="stylesheet" href="js/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<!-- zTreeStyle专用 end-->
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->
</head>
<script type="text/javascript">
	function cleanPage(){
		window.location.href="<%=basePath%>control/roleContainer/select/csmrcList";
	}
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
				showmodel(treeObj);
			}
		});
		
			
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
	//回显模块
	function showmodel(treeObj){
		var csmrcID = $("#csmrcID").val();
		$.ajax({
			type:'post',
			url:'<%=basePath%>control/roleContainer/select/getModelOfRole',
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
			  url:'<%=basePath%>control/roleContainer/update/gotoUpdateCsmrc',
			  success: function(data){
				  if(data == "1"){
				 		alert("修改成功");
				 		frameElement.api.close();
				  }else{
					  alert(data);
				  }
			}
		});
	}

</script>
<body class="width900">

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="current"><span style="color: blue;">必填</span></div>
    <div class="infolist">
    <form name="form" id="form" method="post">
    <input  type="hidden" name="csmrcID" id="csmrcID" value="${csmrc.id}" />
      <table class="nav_tab">
        <tbody>
          <tr>
            <th>角色名称：</th>
            <td>
           		<input type="text" class="nt-text" name="jsname" id="jsname" style="width: 200px;" value="${csmrc.name}"/>
            </td>
           
          </tr>
           <tr>
            <th>所属门店：</th>
            <td><input type="hidden" name="mendianID" id="mendianID" value="${csmrc.cgShopFront.id }"/>
            	<select name="shopFrontId" id="shopFrontId" style="width: 200px;"></select>
            </td>
           
          </tr>
          <tr>
             <th>角色模块：</th>
            <td> 
            	<a onclick="isSelect('yes')">全选</a>&nbsp;&nbsp;&nbsp;<a onclick="isSelect('no')">不选</a>
				&nbsp;&nbsp;&nbsp;<a onclick="isOpen('yes')">全展开</a>&nbsp;&nbsp;&nbsp;<a onclick="isOpen('no')">全关闭</a>
				<div>
					<ul id="treeDemo" class="ztree"></ul>
				</div>
            </td>
           </tr>
          
        </tbody>
      </table>
      <div class="nt-btmbox">
        <input class="btn" type="button" value="提交" onclick="checkForm()">
      </div>
      </form>
      
    </div>
  </div>
</div>

</body>
</html>

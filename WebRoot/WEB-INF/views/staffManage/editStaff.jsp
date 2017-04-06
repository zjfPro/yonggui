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
<link rel="stylesheet" href="oa/css/global.css" type="text/css"/>
<link rel="stylesheet" href="oa/css/list.css" type="text/css"/>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script src="oa/js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<!-- zTreeStyle专用 start-->
<link rel="stylesheet" href="js/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<!-- zTreeStyle专用 end-->
<!-- 导入图片上传js -->
<script type="text/javascript" src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">

//上传  start--------------------------------------------------------------------------------------------------------------
	//图片上传预览    IE是用了滤镜。
	function previewImage(file, div) {
		var MAXWIDTH = 150;
		var MAXHEIGHT = 150;
		/* var div = document.getElementById('preview'); */
		if (file.files && file.files[0]) {
			div.innerHTML = '<img id=imghead width=150 height=150>';
			var img = document.getElementById('imghead');
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				img.style.width = '100px';
				img.style.height = '100px';
				//             img.style.marginLeft = rect.left+'px';
				img.style.marginTop = '0px';
			}
			var reader = new FileReader();
			reader.onload = function(evt) {
				img.src = evt.target.result;
			}
			reader.readAsDataURL(file.files[0]);
		} else //兼容IE
		{
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id=imghead>';
			var img = document.getElementById('imghead');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:100px;height:100px;margin-top:0px;"+sFilter+src+"\"'></div>";
		}
	}
	function clacImgZoomParam(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
		};
		if (width > maxWidth || height > maxHeight) {
			rateWidth = width / maxWidth;
			rateHeight = height / maxHeight;
	
			if (rateWidth > rateHeight) {
				param.width = maxWidth;
				param.height = Math.round(height / rateWidth);
			} else {
				param.width = Math.round(width / rateHeight);
				param.height = maxHeight;
			}
		}
	
		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		return param;
	}
	//调用预览
	function up_img(file) {
		var div = document.getElementById('display_img');
		previewImage(file, div);
	}
//上传  end--------------------------------------------------------------------------------------------------------------	

	$(document).ready(function(){
		var mendian = $("#mendianID").val();
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getAllShopFront',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str="";
					if(data.length==1){
						theShopFront(data[0].id);
					}else{
						var str = "<option value=''>请选择门店...</option>";
					}
					$.each(data,function(i,n){
						if(n.id==mendian){
							str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
							theShopFront(n.id);
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
	
	function theShopFront(id){
		if(id==''){
			$("#decpId").find("option").remove();
			$("#staffPositionId").find("option").remove();
			return;
		}
		var bumen = $("#bumenID").val();
		var zhiwei = $("#zhiweiID").val();
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getShopFrontCgDept',
			data: "id="+id,
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择部门...</option>";
					$.each(data,function(i,n){
						if(n.id==bumen){
							str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
						}else{
							str += "<option value="+n.id+">"+n.name+"</option>";
						}
					});
					$("#decpId").html(str);
					
				}else{
					alert(data.errInfo);
					$("#decpId").find("option").remove();
				}
				
			}
		});
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getShopFrontStaffPosition',
			data: "id="+id,
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择职位...</option>";
					$.each(data,function(i,n){
						if(n.id==zhiwei){
							str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
						}else{
							str += "<option value="+n.id+">"+n.name+"</option>";
						}
					});
					$("#staffPositionId").html(str);
					
				}else{
					alert(data.errInfo);
					$("#staffPositionId").find("option").remove();
				}
				
			}
		});
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
					getModelOfStaff();
				}
			});
			
		});
		
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
		
		//获取员工对应的权限,并回显
		function getModelOfStaff(){
			var theStaffID = $("#theStaffID").val();
			
			$.ajax({
				type:'post',
				url:'<%=basePath%>control/staffManager/select/getModelOfStaff',
				dataType:'json',
				data:{"theStaffID":theStaffID},
				success:function(data){
					if(data.mapingsofStaff!=null){
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						$.each(data.mapingsofStaff,function(i,n){
						
							var node = treeObj.getNodeByParam("id", n.staffManageModelId, null);
							treeObj.checkNode(node,true,true);
						});
					}else{
						alert(data);
					}
				}
			});
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
			$('#models').val(''+ids);
			
			$.ajaxFileUpload({
				type: "POST",
				secureuri: false, //是否需要安全协议，一般设置为false
	         	fileElementId: 'logo', //文件上传域的ID
	      		url: "<%=basePath%>control/staffManager/update/gotoUpdateStaff", //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
	            data: $("#form").serializeObject(),
	            dataType:"text",
				success: function(data){
					if(data == "1"){
						alert("修改成功");
						frameElement.api.close();
					}else if(data == "2"){
						alert("修改成功,您修改了密码,请记住密码");
						frameElement.api.close();
					}else{
						alert(data);
					}
			 	}
			});
		}
</script>

</head>

<body class="width900">

<!-- content -->
<div class="content">
  <div class="content_box">
  <div class="current">修改员工</div>
    <div class="infolist">
    <form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="hidden"  name="theStaffID" id="theStaffID" value="${cgStaffDetail.id }"/>
      <table class="nav_tab">
      <thead>
          <tr>
            <th colspan="8">必填</th>
          </tr>
        </thead>
        <tbody>
        	<tr>
        
			<th >相片：</th>
				<td colspan="7">
					<div style="float:left">
						<input type="file" name="file" id="logo"
							onchange="up_img(this)" />
					</div>
					<div id="display_img"
						style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
						<img id="imghead" style="width: 100px;height: 100px;"
							src="${cgStaffDetail.logo }" />
					</div>  
				</td>
			</tr>
        
          <tr>
            <th>姓名：</th>
            <td>
           		<input type="text" class="nt-text" name="name" id="name" value="${cgStaffDetail.name }"/>
            </td>
            
            <th>门店：</th>
            <td><input type="hidden" name="mendianID" id="mendianID" value="${cgStaffDetail.shopFrontId }"/>
           		<select name="shopFrontId" id="shopFrontId" style="width: 120px;" onchange="theShopFront(this.value)" ></select>
            </td>
            
            <th>部门：</th>
            <td><input type="hidden" name="bumenID" id="bumenID" value="${cgStaffDetail.decpId }"/>
           		<select name="decpId" id="decpId" style="width: 120px;"></select>
            </td>
            
            <th>职位：</th>
            <td><input type="hidden" name="zhiweiID" id="zhiweiID" value="${cgStaffDetail.staffPositionId }"/>
           		<select name="staffPositionId" id="staffPositionId" style="width: 120px;"></select>
            </td>
            
          </tr>
          
          <tr>
          	<th>帐号：</th>
            <td>
           		${cgStaffDetail.account }
            </td>
            
            <th>密码：</th>
            <td>
           		<input type="password" class="nt-text" name="password" id="password"/>
            </td>
            
            <th>编号：</th>
            <td>
           		<input type="text" class="nt-text" name="number" id="number" value="${cgStaffDetail.number }"/>
            </td>
            
            <th>身份证：</th>
            <td>
           		<input type="text" class="nt-text" name="idcard" id="idcard" value="${cgStaffDetail.idcard }"/>
            </td>
            
          </tr>
          
          <tr>
          	<th>赋予权限：</th>
          	<td colspan="8"><input name="models" id="models" type="hidden"/>
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="isSelect('yes')">全选</a>&nbsp;&nbsp;&nbsp;
				<a onclick="isSelect('no')">不选</a>&nbsp;&nbsp;&nbsp;
				<a onclick="isOpen('yes')">全展开</a>&nbsp;&nbsp;&nbsp;
				<a onclick="isOpen('no')">全关闭</a><br/>
          		<div>
					<ul id="treeDemo" class="ztree"></ul>
				</div>
          	</td>
          </tr>
        </tbody>
        <tfoot></tfoot>
      </table>
      
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">可填</th>
          </tr>
        </thead>
        <tbody>
        	<tr>
        	<th>出生年月：</th>
            <td>
           		<input type="text" id="theBirthDate" name="theBirthDate" class="Wdate width85"
           		value="<fmt:formatDate value="${cgStaffDetail.birthDate}" pattern="yyyy-MM-dd"/>"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
            </td>
	            <th>入职时间：</th>
	            <td>
	           		<input type="text" id="theEntryTime" name="theEntryTime" class="Wdate width85"
	           		value="<fmt:formatDate value="${cgStaffDetail.entryTime}" pattern="yyyy-MM-dd"/>"
	              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	            <th>离职时间：</th>
	            <td colspan="2">
	           		<input type="text" id="theQuitTime" name="theQuitTime" class="Wdate width85"
	           		value="<fmt:formatDate value="${cgStaffDetail.quitTime}" pattern="yyyy-MM-dd"/>"
	              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
          </tr>
          
          <tr>
            <th>联系电话：</th>
            <td>
           		<input type="text" class="nt-text" name="phone" id="phone" value="${cgStaffDetail.phone }"/>
            </td>
            <th>备用电话：</th>
            <td>
           		<input type="text" class="nt-text" name="sparePhone" id="sparePhone" value="${cgStaffDetail.sparePhone }"/>
            </td>
            <th>住址：</th>
            <td colspan="2">
           		<input type="text" class="nt-text" name="address" id="address" value="${cgStaffDetail.address }"/>
            </td>
          </tr>
          
          <tr>
          
          <th>状态：</th>
            <td>
	         	<select name="status" id="status" style="width: 120px;">
					<option <c:if test="${cgStaffDetail.status==0 }">selected='selected'</c:if> value="0">未启用</option>
					<option <c:if test="${cgStaffDetail.status==1 }">selected='selected'</c:if> value="1">启用</option>
					<option <c:if test="${cgStaffDetail.status==-1 }">selected='selected'</c:if> value="-1">禁用</option>
					<option <c:if test="${cgStaffDetail.status==-2 }">selected='selected'</c:if> value="-2">离职</option>
			    </select>
            </td>
            <th>备注：</th>
            <td colspan="3">
           		<input type="text" class="nt-text" name="remarks" id="remarks" value="${cgStaffDetail.remarks }"/>
            </td>
          </tr>
          
        </tbody>
        <tfoot></tfoot>
      </table>
      
      
      <div class="nt-btmbox">
        <input class="btn" type="button" value="添加" onclick="checkForm()">
      </div>
      </form>
      
      
    </div>
  </div>
</div>

</body>
</html>

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
<script src="oa/js/My97DatePicker/WdatePicker.js"></script>



<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->




<!-- zTreeStyle专用 start-->
<link rel="stylesheet" href="js/zTree_v3-master/css/demo.css"
	type="text/css">
<link rel="stylesheet"
	href="js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="js/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="js/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<!-- zTreeStyle专用 end-->



<!-- 富文本编辑器相关 -->
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>js/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript"
	src="<%=basePath%>oa/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>oa/js/validate.expand.js"></script>

<!-- 导入图片上传js -->
<script type="text/javascript"
	src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>

<script type="text/javascript">
	
	function cleaninput(id){
		$("#"+id).val("");
	}
	
 	function cleanPage(){
    	window.location.href="<%=basePath%>shopFront/select/shopFrontList";
	}

	$(function() {
		$("#addBtn").click(function() {
			//提交表单
			window.myform.submit();
		});
	});
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
	
	function searchStaff(){
		$.ajax({
			  type: "POST",
			  data: "text="+$("#manageStaff_name").val(),
			  url: "<%=basePath%>control/staffManager/select/searchStaffByNumber",
			  success : function(data) {
				try {
					var jsonobj = eval('(' + data + ')');
					if (jsonobj.length > 1) {
						$("#manageStaff_id").val(jsonobj[0]);
						$("#manageStaff_name").val(
								jsonobj[1] + "[" + jsonobj[2] + "]");
					} else {
						$("#manageStaff_name").val(data);
					}
				} catch (e) {
					$("#manageStaff_name").val(data);
				}

			}
		});
	}
	
	

	function checkIsDouble(checkString, elementName) {
		var reg = /^[-\+]?\d+(\.\d+)?$/;
		if (reg.test(checkString.value)) {
			return true;
		} else {
			alert(elementName + "必须为数字!");
			setFocus(checkString);
			return false;
		}
	}
	
	
	
	
 
	function checkForm() {
 		var b=true;
	 	var longitude =document.getElementById("longitude");
		var latitude =document.getElementById("latitude");
		if (longitude.value!="" && longitude.value.length>0){
			b= checkIsDouble(longitude,"经度");
		}
		if (latitude.value!="" || latitude.value.length>0){
			b= checkIsDouble(latitude,"纬度");
		}
		if (b){
			//数据校验
			//document.getElementById("form").submit();
			$.ajaxFileUpload({
  				type: "POST",
  				secureuri: false, //是否需要安全协议，一般设置为false
             	fileElementId: 'logo', //文件上传域的ID
          		url: "<%=basePath%>shopFront/add/addshopFront", //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                data: $("#form").serializeObject(),
                dataType:"text",
   					 success: function(msg){
   					 if (msg!="1"){
   					 	alert(msg);
   					 }else{
   					 	 alert("添加成功");
   					 	frameElement.api.close();
   					 }
			 	  }
				});
		}
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

</script>
</head>

<body class="width900">

	<!-- content -->
	<div class="content">
		<div class="content_box">

			<div class="infolist">
				<form name="form" id="form" method="post"
					enctype="multipart/form-data">
					<table class="nav_tab">
						<thead>
							<tr>
								<th colspan="8">必填</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>门店名称：</th>
								<td><input type="text" class="text width300" name="name"
									id="name" /></td>
							</tr>
						</tbody>
						<tfoot></tfoot>
					</table>


					<table class="nav_tab">
						<thead>
							<tr>
								<th colspan="8">选填</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>门店图片：</th>
								<td colspan="7">
									<div style="float:left">
										<input type="file" name="file" id="logo"
											onchange="up_img(this)" />
									</div>
									<div id="display_img"
										style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
										<img id="imghead" style="width: 100px;height: 100px;"
											src="<%=request.getContextPath()%>/images/up_defaul.png" />
									</div></td>
							</tr>


							<tr>
								<th>门店地址：</th>
								<td><input type="text" class="text width300" name="address"
									id="address" /></td>
							</tr>
							<tr>
								<th>地址经度：</th>
								<td><input type="text" class="text width300"
									name="longitude" id="longitude" /></td>
							</tr>
							<tr>
								<th>地址纬度：</th>
								<td><input type="text" class="text width300"
									name="latitude" id="latitude" /></td>
							</tr>
							<tr>
								<th>门店介绍：</th>
								<td colspan="2"><div class="table_area">
										<textarea id="editor" name="introduce"
											style="width:100%;height:150px;"></textarea>
									</div></td>
							</tr>

							<c:if test="${sessionScope.admin!=null }">
								<tr>
									<th>店长设置：</th>
									<td><input type="text" class="text width300"
										value="请输入员工编号" name="manageStaff_name" id="manageStaff_name"
										onblur="searchStaff()"
										onfocus="if (this.value=='请输入员工编号' || this.value=='没有结果'){this.value=''}" />
										<input type="hidden" name="manageStaffId" id="manageStaff_id" />
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<div class="nt-btmbox">
						<input class="btn" type="button" value="添加" onclick="checkForm();">
					</div>
				</form>
			</div>
		</div>
	</div>



	<script type="text/javascript">
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor('editor', {
		toolbars : [ [ 'fullscreen', 'source', '|', 'undo', 'redo', '|',
				'bold', 'italic', 'underline', 'fontborder', 'strikethrough',
				'superscript', 'subscript', 'removeformat', 'formatmatch',
				'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
				'backcolor', 'insertorderedlist', 'insertunorderedlist',
				'selectall', 'cleardoc', '|', 'rowspacingtop',
				'rowspacingbottom', 'lineheight', '|', 'customstyle',
				'paragraph', 'fontfamily', 'fontsize', '|',
				'directionalityltr', 'directionalityrtl', 'indent', '|',
				'justifyleft', 'justifycenter', 'justifyright',
				'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
				'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft',
				'imageright', 'imagecenter', '|', 'simpleupload',
				'insertimage', 'pagebreak', 'template', 'background', '|',
				'horizontal', 'date', 'time', 'spechars', '|', 'inserttable',
				'deletetable', 'insertparagraphbeforetable', 'insertrow',
				'deleterow', 'insertcol', 'deletecol', 'mergecells',
				'mergeright', 'mergedown', 'splittocells', 'splittorows',
				'splittocols', 'charts', '|', 'print', 'preview',
				'searchreplace', 'help', 'drafts' ] ]
	});
</script>
</body>
</html>

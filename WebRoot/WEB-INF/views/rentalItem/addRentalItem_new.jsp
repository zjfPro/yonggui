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
	
	$(document).ready(function(){
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getAllCgCarInfo',
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择车辆...</option>";
					$.each(data,function(i,n){
						str += "<option value='"+n.id+","+n.shopFrontId+"'>"+"车牌:"+n.plateNumber+"</option>";
					});
					$("#carInfoId").html(str);
				}else{
					alert(data.errInfo);
				}
				
			}
		});
		
	});
	
	function getOptionData(optionValue){
		if(optionValue==""){
			$("#staffId").find("option").remove();
			$("#shopFrontId").find("option").remove();
			return;
		}
		
		var ids = optionValue.split(",");
		var mendianID=ids[1];
		var mendianTheid='';
		var mendianName='';
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getStaffForFront?mendianID='+mendianID,
			dataType:'json',
			success:function(data){
				if(data.length>0){
					var str = "<option value=''>请选择员工...</option>";
					$.each(data,function(i,n){
						if(mendianTheid==''){
							mendianTheid=n.cgShopFront.id;
							mendianName=n.cgShopFront.name;
						}
						
						str += "<option value="+n.id+">"+n.name+"</option>";
					});
					
					var str2 = "<option value="+mendianTheid+">"+mendianName+"</option>";
					
					$("#staffId").html(str);
					$("#shopFrontId").html(str2);
				}else{
					alert(data.errInfo);
					$("#staffId").find("option").remove();
					$("#shopFrontId").find("option").remove();
				}
			}
		});
		
	}
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/rentalItem/add/gotoAddCgCarRentalItem",
			  success: function(data){
			 	if(data == "1"){
			 		alert("添加成功");
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
 			<div class="current">添加租车项目</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<table class="nav_tab">
					<thead>
			          <tr>
			            <th colspan="8">必填</th>
			          </tr>
			        </thead>
						<tbody>
							
							<tr>
								<th>车辆：</th>
								<td>
								<select name="carInfoId" id="carInfoId" style="width: 120px;" onchange="getOptionData(this.value);" ></select>
								</td>
								
								<th>操作人(员工)：</th>
								<td>
								<select name="staffId" id="staffId" style="width: 120px;"></select>
								</td>
								
								<th>门店：</th>
								<td colspan="8">
								<select name="shopFrontId" id="shopFrontId" style="width: 120px;"></select>
								</td>
								
							</tr>

							<tr>
								<th>价格：</th>
								<td>
									<input type="text" class="nt-text" name="unitPrice" id="unitPrice"/>
								</td>
								<th style="width: 200px;">销售单位 (例:小时/日)：</th>
								<td>
									<input type="text" class="nt-text" name="unitName" id="unitName"/>
								</td>
								<th style="width: 200px;">销售单位数量：</th>
								<td colspan="8">
									<input type="text" class="nt-text" name="unitNumber" id="unitNumber"/>
								</td>
							</tr>
							<tr>
								<th>是否上架</th>
								<td>
									<select name="status" id="status" style="width: 120px;">
										<option value="1">上架</option>
										<option value="-1">下架</option>
									</select>
								</td>
								<th style="width: 200px;">定金：</th>
								<td>
									<input type="text" class="nt-text" name="earnestMoney" id="earnestMoney"/>
								</td>
								<th style="width: 200px;">押金：</th>
								<td colspan="8">
									<input type="text" class="nt-text" name="foregiftMoney" id="foregiftMoney"/>
								</td>
							</tr>
							<tr>
								<th>标题：</th>
								<td colspan="8">
									<input type="text" class="nt-text" name="title" id="title"  style="width: 600px;"/>
								</td>
							</tr>
							<tr>
								<th>内容描述：</th>
								<td colspan="8"><div class="table_area">
										<textarea id="editor" name="content"
											style="width:100%;height:150px;"></textarea>
									</div>
								</td>
							</tr>
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

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
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ueditor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="<%=basePath %>oa/js/jquery.validate.js"></script>
<script src="<%=basePath %>oa/js/validate.expand.js"></script>

<!-- 导入图片上传js -->
<script type="text/javascript" src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	
	$(function(){
		var ue = UE.getEditor('editor',{
		    toolbars: [[
		            'fullscreen', 'source', '|', 'undo', 'redo', '|',
		            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
		            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
		            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
		            'directionalityltr', 'directionalityrtl', 'indent', '|',
		            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
		            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
		            'simpleupload', 'insertimage',  'pagebreak', 'template', 'background', '|',
		            'horizontal', 'date', 'time', 'spechars', '|',
		            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
		            'print', 'preview', 'searchreplace', 'help', 'drafts'
		        ]]});
	});
	
	function cleaninput(id){
		$("#"+id).val("");
	}
	var flag = true;
 	var yanzhen = true;
	$(function() {
		var validate = $("#form").validate({
				debug : true, //调试模式取消submit的默认提交功能   
				//errorClass: "label.error", //默认为错误的样式类为：error   
				focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
				onkeyup : false,
				rules : {
					title:{
						required :true,
					}
						
				},
				messages : {
					title :{
						required : "<span style='color:red'>此处不能为空</span>",
					}
					
				}
			});
			$("#addBtn").click(function() {
				if (!validate.form()) {
					return;
				}else if(!yanzhen){
					alert("只能能上传一张图片");
					return;	
				}else{
					//document.getElementById("form").submit();
					$.ajaxFileUpload({
						type: "POST",
		  				secureuri: false, //是否需要安全协议，一般设置为false
		             	fileElementId: 'logo', //文件上传域的ID
						url: "<%=basePath%>companyProfile/update/companyProfileList",
						secureuri: false, //是否需要安全协议，一般设置为false
						data: $("#form").serializeObject(),
						dataType:"text",
						success: function(data){
						 	if(data == "1"){
						 		alert("修改成功");
							}else {
								alert(data);
							}
						}
					});
				}
			});
			
	
		});
	
	//图片上传预览    IE是用了滤镜。
	function previewImage(file, div, index) {
		var MAXWIDTH = 150;
		var MAXHEIGHT = 150;
		/* var div = document.getElementById('preview'); */
		if (file.files && file.files[0]) {
			div.innerHTML += '<img id="imghead' + index + '" style="width: 100px;height: 100px;" onclick="window.open(this.src);"  >';
			var img = document.getElementById('imghead'+ index);
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				img.style.width = '100px';
				img.style.height = '100px';
				//                 img.style.marginLeft = rect.left+'px';
				img.style.marginTop = '0px';
			}
			img.style.width = '100px';
			var reader = new FileReader();
			reader.onload = function(evt) {
				document.getElementById('imghead'+ index).src = evt.target.result;
			}
			reader.readAsDataURL(file.files[index]);
		} else //兼容IE
		{
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id="imghead"   onclick="window.open(this.src);">';
			var img = document.getElementById('imghead');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:100px;height:100px;margin-top:0px;"+sFilter+src+"\"' ></div>";
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
		flag = true;
		yanzhen=true;
		div.innerHTML = "";
		div.style.width = file.files.length == 0 ? 100
				: file.files.length * 100;
		var p=0;
		for (var i = 0; i < file.files.length; i++) {
			previewImage(file, div, i);
			p+=file.files[i].size;
		}
		if(file.files.length>1){
			alert("只能能上传一张图片");
			yanzhen=false;
		}
   			
	}
	
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<span>公司简介内容</span>
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="<%=basePath%>control/activity/add/addCompanyActivity" enctype="multipart/form-data">
				<input type="hidden" id="jjid" name="jjid" value="${cpDetail.id}" />
					<table class="left_table">
						
						<tr class="line_g">
							<td width="10%">内容描述：</td>
							<td>
								<textarea id="editor" name="content" style="width:800px;height:200px;">${cpDetail.content}</textarea>
							</td>
						</tr>

					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

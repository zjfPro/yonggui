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

<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->


<script type="text/javascript">
	
	function cleaninput(id){
		$("#"+id).val("");
	}
	
	function cleanPage(){
		 window.location.href="<%=basePath%>control/rentalItem/select/rentalItemList";
	}
	
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
	
	function checkForm(){
		$.ajax({
			  async: false,
			  type: "POST",
			  data: $("#form").serialize(),
			  url: "<%=basePath%>control/rentalItem/add/gotoAddCgCarRentalItem",
			  success: function(data){
			 	if(data == "1"){
			 		var queren = confirm("添加成功,是否返回租车项目列表?");
			 		if(queren){
			 			cleanPage();
			 		}
				}else{
					alert(data);
				}
			}
		});
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
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span> <a>基础信息</a><span>></span>添加租车项目
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post">
					<table class="left_table">

						
						<tr class="line_g">
							<td width="10%">车辆：</td>
							
							<td>
								<select name="carInfoId" id="carInfoId" style="width: 300px;" onchange="getOptionData(this.value);"></select>
							</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">操作人(员工)：</td>
							<td>
								<select name="staffId" id="staffId" style="width: 300px;"></select>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">门店：</td>
							<td>
								<select name="shopFrontId" id="shopFrontId" style="width: 300px;"></select>
							</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">价格：</td>
							<td><input type="text" class="text width300" name="unitPrice" id="unitPrice"/><a onclick="cleaninput('unitPrice')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">销售单位(例:小时/日/周/月)：</td>
							<td><input type="text" class="text width300" name="unitName" id="unitName"/><a onclick="cleaninput('unitName')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">销售单位数量：</td>
							<td><input type="text" class="text width300" name="unitNumber" id="unitNumber"/><a onclick="cleaninput('unitNumber')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">是否上架(状态)：</td>
							<td>
								<select name="status" id="status" style="width: 200px;">
										<option value="0">未上架</option>
										<option value="1">上架</option>
										<option value="-1">下架</option>
							    </select>
							</td>
						</tr>
						
						
						<tr class="line_g">
							<td width="10%">定金：</td>
						<td><input type="text" class="text width300" name="earnestMoney" id="earnestMoney"/><a onclick="cleaninput('earnestMoney')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">押金：</td>
              			<td><input type="text" class="text width300" name="foregiftMoney" id="foregiftMoney"/><a onclick="cleaninput('foregiftMoney')">清空</a></td>
						</tr>
						
						
						<tr class="line_g">
							<td width="10%">标题：</td>
							<td><input type="text" class="text width300" name="title" id="title"/><a onclick="cleaninput('title')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">内容描述：</td>
							<td>
								<textarea id="editor" name="content" style="width:1000px;height:500px;"></textarea>
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

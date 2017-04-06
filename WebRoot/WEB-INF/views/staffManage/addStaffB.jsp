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
<link rel="stylesheet" href="oa/css/global.css" type="text/css"/>
<link rel="stylesheet" href="oa/css/list.css" type="text/css"/>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script src="oa/js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<!-- 导入图片上传js -->
 <script type="text/javascript"
	src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>
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


	function checkForm(){
		$.ajaxFileUpload({
				type: "POST",
				secureuri: false, //是否需要安全协议，一般设置为false
         	fileElementId: 'logo', //文件上传域的ID
      		url: "<%=basePath%>control/staffManager/add/addStaff", //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            data: $("#form").serializeObject(),
            dataType:"text",
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
	
	$(document).ready(function(){
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
						str += "<option value="+n.id+">"+n.name+"</option>";
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
		$.ajax({
			type:'post',
			url:'<%=basePath%>ajax/select/getShopFrontCgDept',
			data: "id="+id,
			dataType : 'json',
			success : function(data) {
				if(data.length>0){
					var str = "<option value=''>请选择部门...</option>";
					$.each(data,function(i,n){
						str += "<option value="+n.id+">"+n.name+"</option>";
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
						str += "<option value="+n.id+">"+n.name+"</option>";
					});
					$("#staffPositionId").html(str);
					
				}else{
					alert(data.errInfo);
					$("#staffPositionId").find("option").remove();
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
  <div class="current">添加员工</div>
    <div class="infolist">
    <form name="form" id="form" method="post" enctype="multipart/form-data">
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
							src="<%=basePath%>images/up_defaul.png" />
					</div>  
				</td>
			</tr>
        
          <tr>
            <th>姓名：</th>
            <td>
           		<input type="text" class="nt-text" name="name" id="name"/>
            </td>
            
            <th>门店：</th>
            <td>
           		<select name="shopFrontId" id="shopFrontId" style="width: 120px;" onchange="theShopFront(this.value)" ></select>
            </td>
            
            <th>部门：</th>
            <td>
           		<select name="decpId" id="decpId" style="width: 120px;"></select>
            </td>
            
            <th>职位：</th>
            <td>
           		<select name="staffPositionId" id="staffPositionId" style="width: 120px;"></select>
            </td>
            
          </tr>
          
          <tr>
          	<th>帐号：</th>
            <td>
           		<input type="text" class="nt-text" name="account" id="account"/>
            </td>
            
            <th>密码：</th>
            <td>
           		<input type="password" class="nt-text" name="password" id="password"/>
            </td>
            
            <th>编号：</th>
            <td>
           		<input type="text" class="nt-text" name="number" id="number"/>
            </td>
            
            <th>身份证：</th>
            <td>
           		<input type="text" class="nt-text" name="idcard" id="idcard"/>
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
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
            </td>
	            <th>入职时间：</th>
	            <td>
	           		<input type="text" id="theEntryTime" name="theEntryTime" class="Wdate width85"
	              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	            <th>离职时间：</th>
	            <td colspan="2">
	           		<input type="text" id="theQuitTime" name="theQuitTime" class="Wdate width85"
	              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
          </tr>
          
          <tr>
            <th>联系电话：</th>
            <td>
           		<input type="text" class="nt-text" name="phone" id="phone"/>
            </td>
            <th>备用电话：</th>
            <td>
           		<input type="text" class="nt-text" name="sparePhone" id="sparePhone"/>
            </td>
            <th>住址：</th>
            <td colspan="2">
           		<input type="text" class="nt-text" name="address" id="address"/>
            </td>
          </tr>
          
          <tr>
          
          <th>状态：</th>
            <td>
	         	<select name="status" id="status" style="width: 120px;">
					<option value="1">启用</option>
					<option value="0">未启用</option>
					<option value="-1">禁用</option>
					<option value="-2">离职</option>
			    </select>
            </td>
            <th>备注：</th>
            <td colspan="3">
           		<input type="text" class="nt-text" name="remarks" id="remarks"/>
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

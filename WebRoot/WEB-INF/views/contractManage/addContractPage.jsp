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
<script src="oa/js/jquery-1.7.2.min.js"></script>
<script src="oa/js/jquery-1.12.1.js"></script>
<script src="oa/js/bootstrap.js"></script>

<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script src="oa/js/My97DatePicker/WdatePicker.js"></script>
<script src="oa/js/jquery.validate.js"></script>
<script src="oa/js/validate.expand.js"></script>

<script src="oa/js/ajaxFileUpload.js"></script>
<script type="text/javascript">
	
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
					file:{
						required :true,
					},
					shopFrontId :{
						required :true,
					},
					investorId :{
						required :true
					},
					staffId :{
						required :true
					},
					tzrPhone:{
						required :true
					},
				},
				messages : {
					file :{
						required : "<span style='color:red'>此处不能为空</span>",
					},
					shopFrontId :{
						required : "<span style='color:red'>此处不能为空</span>",
					},
					investorId :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					staffId :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					tzrPhone:{
						required : "<span style='color:red'>此处不能为空</span>"
					},
				}
			});
			$("#addBtn").click(function() {
				if (!validate.form()) {
					return;
				}else{
					//document.getElementById("form").submit();
			$.ajaxFileUpload({
  				type: "POST",
  				secureuri: false, //是否需要安全协议，一般设置为false
             	fileElementId: 'logo', //文件上传域的ID
          		url: "<%=basePath%>control/contract/add/addContract", //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                data: $("#form").serializeObject(),
                dataType:"text",
   					 success: function(msg){
   					 if (msg=="1"){
   					 	alert("添加成功");
   					 	 frameElement.api.close();
   					 }else{
   					 	 alert("添加失败！！！请检查该订单是否存在；或者投资人与电话号码是否匹配");
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
		
			var filemaxsize = 1024*1024*4;//4M
			
			if (file.files.length > 4) {
				flag = false;
				alert("最多只能上传4张图片");
			}
			
			if(p>filemaxsize){
				yanzhen = false;
				alert("上传图片不能大于4M");
			}
			
			
   
	}
	
	function accountCheck(data){
			if(data!=null){
				$.ajax({
					type : "POST",
					url : "<%=basePath%>control/contract/examine/investorId",
					data : {"investorId":data},
					success : function(msg) {
						if(msg=="1"){
							cleanAll();
						}else if(msg=="ok"){
							alert("该投资人不存在");
						}
					}
				});
			}
		}
		
		function accountOrderId(data){
			if(data!=null){
				$.ajax({
					type : "POST",
					url : "<%=basePath%>control/contract/account/OrderId",
					data : {"orderId":data},
					success : function(msg) {
						if(msg=="1"){
							cleanAll();
						}else if(msg=="ok"){
							alert("该订单不存在");
						}
					}
				});
			}
		}
		
		function dataChange(pid){
			if(pid != -1000){
				$.ajax({
					type:"post",
					url:"<%=basePath%>control/contract/select/carBrandList",
					data:{pid:pid},
					dataType:"json",
					success:function(data){
						if(data != null){
							var str = "";
							$.each(data,function(i,n){
								str += "<option value='"+n.id+"'>"+n.name+"</option>";
							});
							$("#staffId").html(str);
						}
					}
				  });
				}else if(data == null){
				$("#staffId").html("<option value='-100'>请选择</option>");
			}
		}
		$(function() {
			var error = $("#error").val();
			if(error!=''){
				alert(error);
			}
		});
</script>
</head>

<body class="width900">

<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="current">
		<a>个人桌面</a><span> > </span> <a>合同列表</a><span>></span>新增合同
	</div>
    <div class="infolist">
     <form name="form" id="form" method="post" action="<%=basePath%>control/contract/add/addContract" enctype="multipart/form-data" target="myIframe">
     <input type="hidden" name="error" id="error"value="${error}" />
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">必填</th>
          </tr>
        </thead>
        <tbody>
          <tr>
          <th width="10%">合同：</th>
	      <td colspan="8">
			<div style="float:left">
				<input type="file" name="file" id="logo" multiple="multiple" class="nt-select"
					onchange="up_img(this)" />
			</div>
			<div id="display_img"
				style="width:450px;height:100px;border:0px solid #000;overflow:hidden;">
				<img id="imghead" style="width: 100px;height: 100px;"
					src="<%=request.getContextPath()%>/images/up_defaul.png" />
			</div> 
			</td>
          </tr>
          <tr>
           <th>门店：</th>
	      <td>
	        <div class="list_date">
	            <select name="shopFrontId" id="shopFrontId" onchange="dataChange(this.value)" class="nt-select">
	            	<option value="">请选择</option>
               		<c:forEach items="${requestScope.fronts }" var="data">
						<option value="${data.id }">${data.name }</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
	       <th>签约人：</th>
	      <td>
	        <div class="list_date">
	            <select name="staffId" id="staffId" class="nt-select">
	            </select>
	         </div>
	        </td>
	       <!--  <th>投资人：</th>
			<td><input type="text" class="nt-text" name="investorId" id="investorId" onchange="accountCheck(this.value)" placeholder="投资人名字"/> -->
			<th>投资人：</th>
			<td>
			<div class="list_date">
			<select name="investorId" id="investorId" class="nt-select">
	            	<option value="">请选择</option>
               		<c:forEach items="${requestScope.investors }" var="data">
						<option value="${data.id }">${data.name }</option>
					</c:forEach>
	            </select>
	          </div>
	         </td>
          </tr>
          
          <tr>
            <td width="10%">投资人电话：</td>
			<td><input type="text" class="nt-text" name="tzrPhone" id="tzrPhone" placeholder="投资人电话"/></td>
			<td width="10%">车牌号：</td>
			<td><input type="text" class="nt-text" name="plateNumber" id="plateNumber" placeholder="输入车牌号"/></td>
			<!-- <td width="10%">订单编号：</td>
			<td><input type="text" class="nt-text" name="orderId" id="orderId" onchange="accountOrderId(this.value)" placeholder="输入订单编号"/></td> -->
			<th>订单编号：</th>
			<td>
			<div class="list_date">
			<select name="orderId" id="orderId" class="nt-select">
	            	<option value="">请选择</option>
               		<c:forEach items="${requestScope.orders }" var="data">
						<option value="${data.id }">${data.number }</option>
					</c:forEach>
	            </select>
	          </div>
	         </td>
          </tr>
          <tr>
          	<td width="10%">合同类型：</td>
			<td><input type="radio" name="type" value="0" checked="checked"/>车辆签约合同
			<input type="radio" name="type" value="1"/>租车合同</td>
			<td width="10%">合同状态：</td>
			<td><input type="radio" name="status" value="0" checked="checked"/>未签订
			<input type="radio" name="status" value="1"/>已签订</td>
			<td width="10%">结算方式：</td>
			<td><input type="radio" name="clearingForm" value="0" checked="checked"/>全额付款
			<input type="radio" name="clearingForm" value="1"/>分期付款</td>
          </tr>
        </tbody>
        <tfoot></tfoot>
      </table>
      
      <table class="nav_tab" id="carTypeTable">
        <thead>
          <tr>
            <th colspan="8">可填</th>
          </tr>
        </thead>
        <tbody>
      	 <tr>
			<td width="10%">合同名称：</td>
			<td><input type="text" class="nt-text" name="name" id="name"/></td>
			<td width="10%">负责人：</td>
			<td><input type="text" class="nt-text" name="principal" id="principal"/></td>
			<td width="10%">合同金额：</td>
			 <td><input type="text" class="nt-text" name="contractMoney" id="contractMoney"/></td>
          </tr>
          <tr>
          	 <td width="10%">签订日期：</td>
			 <td><input type="text" id="contractTimes" name="contractTimes"  class="Wdate nt-text"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
			 <td width="10%">合同开始日期：</td>
			 <td><input type="text" id="startTimes" name="startTimes" class="Wdate nt-text"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
			 <td width="10%">合同结束日期：</td>
			 <td><input type="text" id="endTimes" name="endTimes"  class="Wdate nt-text"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
          </tr>
          <tr>
			<th>备注：</th>
           <td colspan="7"><textarea class="nt-area" id="remark" name="remark"></textarea>
          </tr>
      	
      	</tbody>
      	</table>
      </form>
     <div class="nt-btmbox">
		<input class="btn" type="button" value="保存" class="btn" id="addBtn">
	</div>
    </div>
  </div>
</div>
</body>
</html>

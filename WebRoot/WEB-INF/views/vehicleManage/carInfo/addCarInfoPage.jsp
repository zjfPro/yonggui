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
<script src="oa/js/jquery.validate.js"></script>
<script src="oa/js/validate.expand.js"></script>
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
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	
	function cleaninput(id){
		$("#"+id).val("");
	}
	$(function() {
		var validate = $("#form").validate({
				debug : true, //调试模式取消submit的默认提交功能   
				//errorClass: "label.error", //默认为错误的样式类为：error   
				focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
				onkeyup : false,
				rules : {
					carTypeId :{
						required :true,
					},
					plateNumber :{
						required :true
					},
					annualVerificationId :{
						required :true
					},
					mileage :{
						required :true
					},
					owner :{
						required :true
					},
					shopFrontId :{
						required :true
					},
					
				},
				messages : {
					carTypeId :{
						required : "<span style='color:red'>此处不能为空</span>",
					},
					plateNumber :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					annualVerificationId :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					mileage :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					owner :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					shopFrontId :{
						required : "<span style='color:red'>此处不能为空</span>"
					},
					
				}
			});
			$("#addBtn").click(function() {
				if (!validate.form()) {
					return;
				}else{
					document.getElementById("form").submit();
				}
			});
			
	
		});
	
		function accountCheck(data){
			if(data!=null){
				$.ajax({
					type : "POST",
					url : "<%=basePath%>control/carInfo/examine/checkAccount",
					data : {"staffId":data},
					success : function(msg) {
						if(msg=="1"){
							cleanAll();
						}else if(msg=="ok"){
							alert("该员工不存在");
						}
					}
				});
			}
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
	
	function dataChange(pid){
			if(pid != -1000){
				$.ajax({
					type:"post",
					url:"<%=basePath%>control/carInfo/select/carBrandList",
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
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span> <a href="control/carInfo/select/carInfoList">车辆信息</a><span>></span>增加车辆信息
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" action="<%=basePath%>control/carInfo/add/addCarInfo" enctype="multipart/form-data">
					<table class="left_table">
						<tr class="line_g">
							<td width="10%">车图片：</td>
							<td>
								<div style="float:left">
									<input type="file" name="file" id="logo"
										onchange="up_img(this)" />
								</div>
								<div id="display_img"
									style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
									<img id="imghead" style="width: 100px;height: 100px;"
										src="<%=request.getContextPath()%>/images/up_defaul.png" />
								</div>  
							</td>
						</tr>
						<tr class="line_g">
					      <td>车类型：</td>
					      <td>
					        <div class="list_date">
					            <select name="carTypeId" id="carTypeId" onchange="dataChange(this.value)" class="text width300">
					            	<option value="">请选择</option>
				               		<c:forEach items="${requestScope.types}" var="data">
										<option value="${data.id }">${data.carBrand.info }</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
					    </tr>

						<tr class="line_g">
							<td width="10%">车牌：</td>
							<td><input type="text" class="text width300" name="plateNumber" id="plateNumber"/><a onclick="cleaninput('plateNumber')">(清空)</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">年审：</td>
							<td><input type="text" class="text width300" name="annualVerificationId" id="annualVerificationId"/><a onclick="cleaninput('annualVerificationId')">(清空)</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">车龄：</td>
							<td><input type="text" class="text width300" name="carAge" id="carAge"/><a onclick="cleaninput('carAge')">(清空)</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">里程：</td>
							<td><input type="text" class="text width300" name="mileage" id="mileage"/><a onclick="cleaninput('mileage')">(清空)</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">车主：</td>
							<td><input type="text" class="text width300" name="owner" id="owner"/><a onclick="cleaninput('owner')">(清空)</a></td>
						</tr>
						<tr class="line_g">
						<td>门店：</td>
					      <td>
					        <div class="list_date">
					            <select name="shopFrontId" id="shopFrontId" onchange="dataChange(this.value)" class="text width300">
					            	<option value="">请选择</option>
				               		<c:forEach items="${requestScope.fronts }" var="data">
										<option value="${data.id }">${data.name }</option>
									</c:forEach>
					            </select>
					         </div>
					        </td>
						</tr>
						<tr class="line_g">
					      <td>签约人：</td>
					      <td>
					        <div class="list_date">
					            <select name="staffId" id="staffId" class="text width300">
					            </select>
					         </div>
					        </td>
					    </tr>
						
						
						<tr class="line_g">
							<td width="10%">签约开始时间：</td>
							<td><input type="text" id="startTimeOfContracts" name="startTimeOfContracts" class="Wdate width300"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><a onclick="cleaninput('startTimeOfContracts')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">签约结束时间：</td>
							<td><input type="text" id="endTimeOfContracts" name="endTimeOfContracts" class="Wdate width300"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><a onclick="cleaninput('endTimeOfContracts')">(清空)</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">销售单位名称：</td>
							<td><input type="text" class="text width300" name="unitName" id="unitName"/><a onclick="cleaninput('unitName')">(清空)</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">销售单位数量：</td>
							<td><input type="text" class="text width300" name="unitNumber" id="unitNumber"/><a onclick="cleaninput('unitNumber')">(清空)</a></td>
						</tr>
						
						<tr class="line_g">
						<td width="10%">库存状态：</td>
							<td><input type="radio" name="status" value="0" checked="checked"/>未出库
							<input type="radio" name="status" value="1"/>已出库</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">备注：</td>
							<td colspan="2"><div class="table_area">
									<textarea id="remark" name="remark"
										style="width:500px;height:250px;"></textarea>
								</div> <a onclick="cleaninput('remark')">(清空)</a>
							</td>
						</tr>
						<tr class="line_g">
							<td colspan="2">
								<input type="button" value="保存" class="btn" id="addBtn"/> 
								<input type="button" value="取消" class="btn" onclick="history.go(-1)"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

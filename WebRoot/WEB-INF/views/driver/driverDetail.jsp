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
<title>驾驶员--驾驶员信息</title>
<link rel="stylesheet" href="<%=basePath%>oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>oa/css/list.css" type="text/css" />
<script src="<%=basePath%>oa/js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>oa/js/fixPNG.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath %>oa/js/jquery.validate.js"></script>
<script src="<%=basePath %>oa/js/validate.expand.js"></script>
<!-- 导入图片上传js -->
<script type="text/javascript" src="<%=basePath%>oa/js/ajaxFileUpload.js"></script>
<script type="text/javascript">
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
	$(function() {
		var validate = $("#form").validate({
				debug : true, //调试模式取消submit的默认提交功能   
				//errorClass: "label.error", //默认为错误的样式类为：error   
				focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
				onkeyup : false,
				rules : {
					code:{required :true},
					name:{required :true},
					phone:{required :true,isMobile :true},
					age:{number :true},
					drivingLicence:{required :true},
					shopFrontId:{required :true},
					sex:{required :true},
					status:{required :true},
					
				},
				messages : {
					code :{required : "<span style='color:red'>请填写工号</span>"},
					name :{required : "<span style='color:red'>请填写姓名</span>"},
					phone :{required : "<span style='color:red'>请填写电话</span>",
							isMobile : "<span style='color:red'>请正确填写手机号码</span>"},
					age :{number : "<span style='color:red'>年龄请输入数字</span>"},
					drivingLicence :{required : "<span style='color:red'>请填写驾驶证</span>"},
					shopFrontId :{required : "<span style='color:red'>请选择门店</span>"},
					sex :{required : "<span style='color:red'>请选择性别</span>"},
					status :{required : "<span style='color:red'>请选择驾驶员状态</span>"},
				}
			});
			$("#addBtn").click(function() {
				var idcard = document.getElementById("idcard").value;
				var id = '${requestScope.driver.id}';
				if (!validate.form()||!idcardCheck(idcard)) {
					return;
				}else{
					
					$.ajaxFileUpload({
						type: "POST",
		  				secureuri: false, //是否需要安全协议，一般设置为false
		             	fileElementId: 'logo', //文件上传域的ID
						url: "<%=basePath%>control/driver/add/addDriver",
						secureuri: false, //是否需要安全协议，一般设置为false
						data: $("#form").serializeObject(),
						dataType:"text",
						success: function(data){
						 	if(data == "1"){
						 		alert("保存成功");
						 		frameElement.api.close();
							}else if (data == "0") {
								alert("添加失败");
							}else{
								alert(data);
							}
						}
					});
				}
			});
			
	
		});
	
	function idcardCheck(idcard){
		if(idcard==null||idcard==""){
			return true;
		}
		var t="";
		if(idcard.length==15){
			t = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
		}else{
			t=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
		}
		if (!t.test(idcard)) {
			alert("输入的身份证号码格式不正确");
			return false;
		}else{
			return true;
		}
	}

	function shopFrontChange(id){
		if(id==""||id.length==0){
			$("#deptId").empty();
			$("#positionId").empty();
			return;
		}else{
			$.ajax({
				type:'post',
				url : "<%=basePath%>control/driver/get/depts?id="+id,
				dataType : 'json',
				success : function(data) {
					if(data!="0"){
						$("#deptId").empty();
						var str = "<option value=''>请选择</option>";
						$.each(data,function(i,n){
							str += "<option value="+n.id+">"+n.name+"</option>";
						});
						$("#deptId").html(str);
					}
				}
			});
			
			$.ajax({
				type:'post',
				url : "<%=basePath%>control/driver/get/positions?id="+id,
				dataType : 'json',
				success : function(data) {
					if(data!="0"){
						$("#positionId").empty();
						var str = "<option value=''>请选择</option>";
						$.each(data,function(i,n){
							str += "<option value="+n.id+">"+n.name+"</option>";
						});
						$("#positionId").html(str);
					}
				}
			});
		
		}
	}
</script>


</head>

<body>
<!-- content -->
<div class="content">
  <div class="content_box">
    <div class="current"><span>新增/编辑驾驶员信息</span></div>
    <div class="infolist">
      <form name="form" id="form" method="post" action="control/driver/add/addDriver" enctype="multipart/form-data">
	      <table class="nav_tab">
	        <thead>
	          <tr>
	            <th colspan="8">
					<div style="float:left">
						<input type="file" name="file" id="logo"
							onchange="up_img(this)" />
					</div>
					<div id="display_img"
						style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
						<img id="imghead" style="width: 100px;height: 100px;"
							src="${requestScope.driver.photo }" />
					</div>  
				</th>
	          </tr>
	        </thead>
	        <tbody>
	          <tr>
	            <th style="width:50px;">工号：</th>
	            <td><input class="nt-text" type="text" style="width:150px;" name="code" value="${requestScope.driver.code}"></td>
	            <th style="width:50px;">姓名：</th>
	            <td><input class="nt-text" type="text" style="width:150px;" name="name" value="${requestScope.driver.name}"></td>
	            <th style="width:50px;">门店：</th>
				<td>
					<input type="text" style="display:none" name="id" value="${requestScope.driver.id}" />
				    <select name="shopFrontId" id="shopFrontId" class="nt-select" style="width:150px;" onclick="shopFrontChange(this.value)">
						<option value="">请选择</option>
						<c:forEach items="${requestScope.shopFrontList }" var="data">
							<option value="${data.id }" <c:if test="${requestScope.driver.shopFrontId ==data.id }">selected="selected"</c:if>>${data.name }</option>
						</c:forEach>
				    </select>
				</td>
	            <th style="width:50px;">部门：</th>
	            <td>
	              <select class="nt-select" style="width:150px;" name="deptId" id="deptId">
	              	<c:if test="${!empty requestScope.driver.id }">
	              		<option value="">请选择</option>
						<c:forEach items="${requestScope.deptList }" var="data2">
							<option value="${data2.id }" <c:if test="${data2.id==requestScope.driver.deptId }">selected="selected"</c:if>>${data2.name }</option>
						</c:forEach>
	              	</c:if>
	              </select>
	            </td>
	          </tr>
	          
	          <tr>
	            <th style="width:50px;">驾驶证：</th>
	            <td><input class="nt-text" type="text" style="width:150px;" name="drivingLicence" value="${requestScope.driver.drivingLicence}"></td>
	            <th style="width:50px;">电话：</th>
	            <td><input class="nt-text" type="text" style="width:150px;" name="phone" value="${requestScope.driver.phone}"></td>
	            <th style="width:50px;">性别：</th>
	            <td>
	            	<select class="nt-select" style="width:150px;" name="sex">
	                	<option value="">请选择</option>
	                	<option value="0" <c:if test="${requestScope.driver.sex ==0 }">selected="selected"</c:if>>男</option>
						<option value="1" <c:if test="${requestScope.driver.sex ==1 }">selected="selected"</c:if>>女</option>
	              	</select>
	            </td>
	            <th style="width:50px;">职位：</th>
	            <td>
	            	<select class="nt-select" style="width:150px;" name="positionId" id="positionId">
		                <c:if test="${!empty requestScope.driver.id }">
		              		<option value="">请选择</option>
							<c:forEach items="${requestScope.positionList }" var="data">
								<option value="${data.id }" <c:if test="${data.id==requestScope.driver.positionId }">selected="selected"</c:if>>${data.name }</option>
							</c:forEach>
		              	</c:if>
	            	</select>
	           	</td>
	          </tr>
	          <tr>
	          	<th style="width:50px;">状态：</th>
	            <td><select class="nt-select" style="width:150px;" name="status">
	            	<option value="">请选择</option>
	                <option value="0" <c:if test="${requestScope.driver.status ==0 }">selected="selected"</c:if>>空闲</option>
					<option value="1" <c:if test="${requestScope.driver.status ==1 }">selected="selected"</c:if>>已派车</option>
					<option value="2" <c:if test="${requestScope.driver.status ==2 }">selected="selected"</c:if>>锁定</option>
	              </select>
	            </td>
	          </tr>
	        </tbody>
	        <tfoot></tfoot>
	      </table>
	      <table class="nav_tab">
	        <thead>
	          <tr>
	            <th colspan="8">详细信息：</th>
	          </tr>
	        </thead>
	        <tbody>
	          <tr>
	            <th style="width:80px;">英文名：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="englishName" value="${requestScope.driver.englishName}"></td>
	            <th style="width:80px;">年龄：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="age" value="${requestScope.driver.age}"></td>
	            <th style="width:80px;">民族：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="nation" value="${requestScope.driver.nation}"></td>
	            <th style="width:80px;">身高：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="height" value="${requestScope.driver.height}"></td>
	          </tr>
	          
	          <tr>
	            <th style="width:80px;">血型：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="bloodType" value="${requestScope.driver.bloodType}"></td>
	            <th style="width:80px;">体重：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="weight" value="${requestScope.driver.weight}"></td>
	            <th style="width:80px;">出生日期：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" style="width:140px;" name="birthdays"
	            		value="<fmt:formatDate value='${requestScope.driver.birthday}' pattern='yyyy-MM-dd'/>"
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	            <th style="width:80px;">籍贯：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="nativePlace" value="${requestScope.driver.nativePlace}"></td>
	          </tr>
	          <tr>
	            <th style="width:80px;">身份证：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="idcard" id="idcard" value="${requestScope.driver.idcard}"></td>
	            <th style="width:80px;">家庭电话：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="familyPhone" value="${requestScope.driver.familyPhone}"></td>
	            
	          </tr>
	          
	          <tr>
	            <th style="width:80px;">出生地：</th>
	            <td colspan="7"><input class="nt-text-long" type="text" name="birthplace" value="${requestScope.driver.birthplace}"></td>
	          </tr>
	          <tr>
	            <th style="width:80px;">户口所在地：</th>
	            <td colspan="7"><input class="nt-text-long" type="text" name="domicilePlace" value="${requestScope.driver.domicilePlace}"></td>
	          </tr>
	          <tr>
	            <th style="width:80px;">现住地：</th>
	            <td colspan="7"><input class="nt-text-long" type="text" name="address" value="${requestScope.driver.address}"></td>
	          </tr>
	          
	          <tr>
	            <th style="width:80px;">学历：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="educationBackground" value="${requestScope.driver.educationBackground}"></td>
	            <th style="width:80px;">学位：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="degree" value="${requestScope.driver.degree}"></td>
	            <th style="width:80px;">毕业学校：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="schooltag" value="${requestScope.driver.schooltag}"></td>
	            <th style="width:80px;">专业：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="major" value="${requestScope.driver.major}"></td>
	          </tr>
	          <tr>
	            <th style="width:80px;">毕业时间：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" style="width:140px;" name="timeOfGraduations" 
	            		value="<fmt:formatDate value='${requestScope.driver.timeOfGraduation}' pattern='yyyy-MM-dd'/>" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	            <th style="width:80px;">政治面貌：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="politicsStatus" value="${requestScope.driver.politicsStatus}"></td>
	            <th style="width:80px;">工作时间：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" style="width:140px;" name="workingTimes" 
	            		value="<fmt:formatDate value='${requestScope.driver.workingTime}' pattern='yyyy-MM-dd'/>" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	            <th style="width:80px;">入职时间：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" style="width:140px;" name="entryTimes" 
	            		value="<fmt:formatDate value='${requestScope.driver.entryTime}' pattern='yyyy-MM-dd'/>" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	          </tr>
	          <tr>
	            <th style="width:80px;">简历编号：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="resumeNumber" value="${requestScope.driver.resumeNumber}"></td>
	            <th style="width:80px;">离职时间：</th>
	            <td>
	            	<input class="Wdate nt-text" type="text" style="width:140px;" name="dimissionTimes" 
	            		value="<fmt:formatDate value='${requestScope.driver.dimissionTime}' pattern='yyyy-MM-dd'/>" 
	            		onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	            </td>
	            <th style="width:80px;">Email：</th>
	            <td><input class="nt-text" type="text" style="width:140px;" name="email" value="${requestScope.driver.email}"></td>
	            <th style="width:80px;">免考勤：</th>
	            <td>
	            	<select name="clockingin" id="clockingin" class="nt-select" style="width: 140px;">
	            		<option value="">请选择</option>
						<option value="1" <c:if test="${requestScope.driver.clockingin ==1 }">selected="selected"</c:if>>是</option>
						<option value="0" <c:if test="${requestScope.driver.clockingin ==0 }">selected="selected"</c:if>>否</option>
				    </select>
	            </td>
	          </tr>
	          <tr>
	            <th style="width:80px;">兴趣爱好：</th>
	            <td colspan="7"><input class="nt-text-long" type="text" name="hobbiesAndInterests" value="${requestScope.driver.hobbiesAndInterests}"></td>
	          </tr>
	          <tr>
	            <th style="width:80px;">备注：</th>
	            <td colspan="7"><input class="nt-text-long" type="text" name="remarks" value="${requestScope.driver.remarks}"></td>
	          </tr>
	          
	        </tbody>
	        <tfoot></tfoot>
	      </table>
	      <div class="nt-btmbox">
	      	<input class="btn" type="button" value="保存" id="addBtn">
	      </div>
      </form>
    </div>
  </div>
</div>

</body>
</html>

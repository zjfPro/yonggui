<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>车辆档案</title>
<link rel="stylesheet" href="oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="oa/css/list.css" type="text/css" />
<script src="oa/js/jquery-1.8.0.min.js"></script>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script src="oa/js/js_checked.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="oa/js/jquery.validate.js"></script>
<script src="oa/js/validate.expand.js"></script>
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
function cleaninput(id){
		$("#"+id).val("");
	}
		function checkForm() {
			var b=true;
			var plateNumber = document.getElementById("plateNumber").value;
			var shopFrontId = document.getElementById("shopFrontId").value;
			var mileage = document.getElementById("mileage").value;
			var frameNumber = document.getElementById("frameNumber").value;
			var owner = document.getElementById("owner").value;
			var gpsNumber = document.getElementById("gpsNumber").value;
			if(plateNumber==""||plateNumber.length==0){
				alert("车牌不能为空");
			}else if (shopFrontId==""||shopFrontId.length==0) {
				alert("门店不能为空");
			}else if (mileage==""||mileage.length==0) {
				alert("总里程数不能为空");
			}else if (frameNumber==""||frameNumber.length==0) {
				alert("车架号不能为空");
			}else if (owner==""||owner.length==0) {
				alert("车主不能为空");
			}else if (gpsNumber==""||gpsNumber.length==0) {
				alert("GPS号码不能为空");
			}else if (b) {
				//数据校验
				// alert($("#form").serializeObject());
				//document.getElementById("form").submit();
				$.ajaxFileUpload({
	  				type: "POST",
	  				secureuri: false, //是否需要安全协议，一般设置为false
	             	fileElementId: 'logo', //文件上传域的ID
	          		url: "<%=basePath%>control/carInfo/update/updateCarBrand", //用于文件上传的服务器端请求地址
	                secureuri: false, //是否需要安全协议，一般设置为false
	                data: $("#form").serializeObject(),
	                dataType:"text",
	   					 success: function(msg){
	   					 if (msg="1"){
	   					 	alert("修改成功");
	   					 	frameElement.api.close();
	   					 }else{
	   					 	changeRows();
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
    <div class="current"><a href="control/carInfo/select/carInfoList">个人桌面</a><span> > </span> <a href="control/carInfo/select/carInfoList">车辆管理</a><span>></span>车辆档案</div>
    <div class="infolist">
    <form name="form" id="form" method="post" action="<%=basePath%>control/carInfo/update/updateCarBrand" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${requestScope.data.id}" />
    <input type="hidden" name="error" id="error"value="${error}" />
      <table class="nav_tab">
        <thead>
          <tr>
            <th colspan="8">必填</th>
          </tr>
        </thead>
        <tbody>
	       <tr>
        	 <th>车图片：</th>
	          <td>
				<div style="float:left">
					<input type="file" name="file" id="logo"
						onchange="up_img(this)" />
				</div>
				<div id="display_img"
					style="width: 60px;height: 60px;border:1px solid #000;overflow:hidden;">
					<img id="imghead" style="width: 60px;height: 60px;"
						src="${requestScope.data.picture}"
						src="<%=request.getContextPath()%>/images/up_defaul.png" />
				</div>    
				</td>
	       </tr>
	       <tr>
	       	<th>总里程数：</th>
            <td><input class="nt-text" type="text" name="mileage" id="mileage" value="${requestScope.data.mileage }"/></td>
            <th>车架号：</th>
            <td><input class="nt-text" type="text" id="frameNumber" name="frameNumber" value="${requestScope.data.frameNumber }"></td>
           <%--  <th>车身结构：</th>
	        <td>
	        <div class="list_date">
	            <select name="carStructureId" id="carStructureId" class="nt-select">
	            	<option value="${requestScope.data.carType.structure.name }">${requestScope.data.carType.structure.name }</option>
               		<c:forEach items="${requestScope.structures }" var="data">
						<option value="${data.id }">${data.name }</option>
					</c:forEach>
	            </select>
	         </div>
	        </td> --%>
	       </tr>
          <tr>
            <th>所属门店：</th>
            <td>
             <div class="list_date">
	            <select name="shopFrontId" id="shopFrontId" onchange="dataChange(this.value)" class="nt-text">
	            	<option value="${requestScope.data.shopFront.id }">${requestScope.data.shopFront.name }</option>
	              		<c:forEach items="${requestScope.fronts }" var="data">
						<option value="${data.id }">${data.name }</option>
					</c:forEach>
	            </select>
	        </div>
            </td>
            <th>车牌号：</th>
            <td><input class="nt-text" type="text" name="plateNumber" id="plateNumber" value="${requestScope.data.plateNumber }"/></td>
            <th>车主：</th>
            <td><input class="nt-text" type="text" name="owner" id="owner" value="${requestScope.data.owner }"></td>
            <th>签约人：</th>
           <td>
	        <div class="list_date">
	            <select name="staffId" id="staffId" class="nt-text">
	            <option value="${requestScope.data.staff.id }">${requestScope.data.staff.name }</option>
	            <c:forEach items="${requestScope.staffs }" var="data">
						<option value="${data.id }">${data.name }</option>
				</c:forEach>
	            </select>
         	</div>
	        </td>
           </tr>
          
          <tr>
            <th>GPS号码：</th>
            <td><input class="nt-text" type="text" name="gpsNumber" id="gpsNumber" value="${requestScope.data.gpsNumber }"></td>
            <th>当前状态：</th>
            <td>
              <select name="status" class="nt-select">
	   			<option ${requestScope.data.status == 0 ? "selected" : "" } value="0">未出库</option>
                <option ${requestScope.data.status == 1 ? "selected" : "" } value="1">已出库</option>
              </select></td>
              
            <th>准载数：</th>
            <td><input class="nt-text" type="text" name="loads" id="loads" value="${requestScope.data.loads }"></td>
            <th>用车范围：</th>
            <td><input class="nt-text" type="text" name="scope" id="scope" value="${requestScope.data.scope }"></td>
          </tr>
          
          <tr>
            <th>车型：</th>
	      	<td>
		       <div class="list_date">
	            <select name="carTypeId" id="carTypeId" class="nt-select">
	            	<option value="${requestScope.data.carType.id }">${requestScope.data.carType.carBrand.info }</option>
               		<c:forEach items="${requestScope.types }" var="data">
						<option value="${data.id }">${data.carBrand.info }</option>
					</c:forEach>
	            </select>
	         </div>
	        </td>
			  
            <th>车辆性质：</th>
            <td> <select name="nature" class="nt-select">
				<option ${requestScope.data.nature == 0 ? "selected" : "" } value="0">公车</option>
                <option ${requestScope.data.nature == 1 ? "selected" : "" } value="1">私车</option>
              </select></td>
              
            <th>买入时间：</th>
            <td><input type="text" id="buyTimes" name="buyTimes" class="Wdate nt-text" 
             onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value="${requestScope.data.buyTime}" pattern="yyyy-MM-dd"/>"></td>
            <th>停放位置：</th>
            <td><input class="nt-text" type="text" id="park" name="park" value="${requestScope.data.park }"></td>
          </tr>
        </tbody>
        <tr>
            <th>备注信息：</th>
            <td colspan="7">
              <input class="nt-text-long" id="remark" name="remark" value="${requestScope.data.remark }">
            </td>
          </tr>
        <tfoot></tfoot>
      </table>
      </form>
      <div class="nt-btmbox">
       <input class="btn" type="button" value="保存" onclick="checkForm()">
       <!--  <input class="btn" type="button" value="取消" onclick="history.go(-1)"> -->
      </div>
    </div>
  </div>
</div>
</body>
</html>




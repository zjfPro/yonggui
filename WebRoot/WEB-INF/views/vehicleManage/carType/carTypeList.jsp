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
<title>管理平台</title>
<link rel="stylesheet" href="oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="oa/css/list.css" type="text/css" />
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>
<!---NO.1	新引用样式文件，及js文件--->
<link type="text/css" rel="stylesheet" href="oa/js/popup/default.css">
<link type="text/css" rel="stylesheet" href="oa/js/popup/prettify.css">
<script src="oa/js/popup/prettify.js"></script>
<script src="oa/js/popup/jquery-1.js"></script>
<script src="oa/js/popup/lhgdialog.js"></script>
<script src="oa/js/js_checked.js"></script>
<!---NO.1	End		新引用样式文件，及js文件--->
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	 function deleteChecked() {
           var flag = false;
           var deId = document.getElementsByName("ccId");
          	var arrayValue = "[";
          	$.each($("input[name='ccId']:checked"), function(i, n) {
				if (n.value != null && n.value.length > 0) {
					arrayValue += n.value;
					arrayValue += ",";
				}
			});
          	arrayValue += "]";

           
           if (arrayValue.length<3) {
            alert("请选择需要删除的数据！");
            return ;
           } 
           if (window.confirm("确认删除所选中的数据？")) {
        		$.ajax({
    				url : "<%=basePath%>control/carType/delete/deleteCarType",
    				data : "ccId=" + arrayValue,
    				success : function(data) {
    					if (data == "1") {
    						alert("删除成功");
    						location.reload(true);
    					} else if (data == "-1") {
							alert("删除失败，所选中数据与车辆信息有关联");
						}else{
    						alert(data);
    					}
    				}
    			});
          	return true;
           }else{
			return false;
           }
          
         }
         
         function checkDetail() {
            var str="";
            $("input[name='ccId']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+",";
                }
            });
            var strs = str.split(",");
            var selcount = (strs.length-1);
            if(selcount!=0){
            	if(selcount==1){
            		var ccId = strs[0];
            	    window.location.href="control/carType/goto/updateCarTypePage?ccId="+ccId; 
            	    
            	}else{
            		alert("你勾选了"+selcount+"条数据,请只勾选一条数据修改");
            	}
            }else{
            		alert("没有勾选数据,请只勾选一条数据修改");
            }
     }
         
     function deleteType(id) {
		if (id == null || id.trim() == "") {
			return;
		}
		if (window.confirm('是否删除该数据？')) {
			$.ajax({
				url : "<%=basePath%>control/carType/delete/deleteCarType",
				type : "post",
				data : {
					"id" : id
				},
				success : function(result) {
					if (result == "1") {
						alert("删除成功");
						location.reload();
					} else if (result == "-1") {
						alert("请先删除与该数据相关联的车辆信息");
					}else{
						alert("删除失败");
					}
				}
			});
		}
	}
function addChecked(){
    $.dialog({
	title: '添加车辆类型',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/carType/goto/addCarTypePage',	 //内文是页面，引入页面地址
	/*ok: function(){							//确定按钮---不想要“确定”按钮可直接删掉
		return false;
	},*/
	cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
	cancel: true, /*为true等价于function(){}*/
	max: true,//false 不显示最大化窗口按钮，默认true
	min: true,//false 不显示最小化窗口按钮，默认true
	width: '1000px',//窗口宽度
	height: '400px',//窗口高度
	lock: true,			/* 是否锁屏*/
	background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
    opacity: 0.4,     /* 透明度 */
    close:changeRows
    });
   }
    
function updateType(id){
 $.dialog({
	title: '修改车辆类型',
	content: 'url:<%=basePath%>control/carType/goto/updateCarTypePage?ccId='+id,	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '400px',
	lock: true,			
	background: '#000',
    opacity: 0.4,
    close:changeRows
     });
}    
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a href="control/carType/select/carTypeList">车型信息</a> > <span></span>车型列表
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>umc/userList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="25">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a onclick="addChecked()">新增</a></li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a href="javascript:changeRows();">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="text" value="${requestScope.text}" placeholder="车身结构/类型/燃料形式"/><input
												class="sea_btn" type="button" onclick="changeRows()" />
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>车辆品牌</th>
								<th>车身结构</th>
								<th>描述</th>
								<th>发动机</th>
								<th>变速箱</th>
								<th>最高车速(km/h)</th>
								<th>官方百公里加速时间(s)</th>
								<th>官方百公里油耗(L/100km)</th>
								<th>排量(mL)</th>
								<th>燃料形式(汽油,柴油,油电混合,电动)</th>
								<th>燃油标号</th>
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<label><input type="checkbox" id="ccId" name="ccId" value="${data.id}" /> </label>
									</td>
									<td>${data.carBrand.info}</td>
									<td>${data.structure.name}</td>
									<td>${data.info}</td>
									<td>${data.engine}</td>
									<td>${data.transmissionCase}</td>
									<td>${data.speedMax}</td>
									<td>${data.accelerateTime}</td>
									<td>${data.gasolineConsumption}</td>
									<td>${data.displacement}</td>
									<td>${data.fuelForm}</td>
									<td><c:if test="${data.fuelLabel==0}"><span style="color:blue">0#</span></c:if>
									<c:if test="${data.fuelLabel==1}"><span style="color:blue">90#</span></c:if>
									<c:if test="${data.fuelLabel==2}"><span style="color:blue">93#</span></c:if>
									<c:if test="${data.fuelLabel==3}"><span style="color:blue">97#</span></c:if>
									<c:if test="${data.fuelLabel==4}"><span style="color:blue">92#</span></c:if>
									<c:if test="${data.fuelLabel==5}"><span style="color:blue">95#</span></c:if>
									<c:if test="${data.fuelLabel==6}"><span style="color:blue">98#</span></c:if>
									</td>
									 <td>
									 <!-- href="control/carType/goto/addCarTypePage" -->
						              <a class="operation-btn" onclick="addChecked()">新增</a>
						              <a class="operation-btn" onclick="deleteType('${data.id}')">删除</a>
						              <a class="operation-btn" onclick="updateType('${data.id}')">修改</a>
						              <a class="operation-btn" href="javascript:changeRows();">刷新</a>
						            </td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="25" valign="middle">
								<%@include file="/WEB-INF/views/include/page.jsp"%></td>
							</tr>
						</tfoot>
					</table>
				</form>
			</div>
		</div>
	</div>

	<!---NO.2	弹出层引用实例--->
<script>
$("#addBtn").dialog({
	title: '添加车辆类型',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/carType/goto/addCarTypePage',	 //内文是页面，引入页面地址
	/*ok: function(){							//确定按钮---不想要“确定”按钮可直接删掉
		return false;
	},*/
	cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
	cancel: true, /*为true等价于function(){}*/
	max: true,//false 不显示最大化窗口按钮，默认true
	min: true,//false 不显示最小化窗口按钮，默认true
	width: '1000px',//窗口宽度
	height: '580px',//窗口高度
	lock: true,			/* 是否锁屏*/
	background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
    opacity: 0.4,     /* 透明度 */
   // close:cleanPage
})


$('a[nt_type="editor"]').dialog({
	title: '修改车辆类型',
	content: 'url:<%=basePath%>control/carType/goto/updateCarTypePage?ccId=${data.id}',	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '500px',
	lock: true,			
	background: '#000', 
    opacity: 0.4 
})

$('a[nt_type="see"]').dialog({
	title: '窗口名称',
	content: 'url:add2.html',	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '680px',
	lock: true,			
	background: '#000', 
    opacity: 0.4 
})
</script>
<!---NO.2	End		弹出层引用实例--->
</body>
</html>

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
           var deId = document.getElementsByName("id");
          	var arrayValue = "[";
          	$.each($("input[name='id']:checked"), function(i, n) {
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
    				url : "<%=basePath%>control/carInfo/delete/deleteCarInfo",
    				data : "id=" + arrayValue,
    				success : function(data) {
    					if (data == "1") {
    						alert("删除成功");
    						location.reload(true);
    					} else {
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
            $("input[name='id']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+",";
                }
            });
            var strs = str.split(",");
            var selcount = (strs.length-1);
            if(selcount!=0){
            	if(selcount==1){
            		var id = strs[0];
            	    window.location.href="control/carInfo/goto/updateCarBrandPage?id="+id; 
            	    
            	}else{
            		alert("你勾选了"+selcount+"条数据,请只勾选一条数据修改");
            	}
            }else{
            		alert("没有勾选数据,请只勾选一条数据修改");
            }
     }
         
     function deleteType(ccId) {
		if (ccId == null || ccId.trim() == "") {
			return;
		}
		if (window.confirm('是否删除该数据？')) {
			$.ajax({
				url : "<%=basePath%>control/carInfo/delete/deleteCarInfo",
				type : "post",
				data : {
					"ccId" : ccId
				},
				success : function(result) {
					if (result == "1") {
						alert("删除成功");
						location.reload();
					} else {
						alert("删除失败");
					}
				}
			});
		}
	}
function addChecked(){
    $.dialog({
	title: '添加车辆信息',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/carInfo/goto/addCarInfoPage',	 //内文是页面，引入页面地址
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
    close:changeRows
    });
   }
	
	
function updateInfo(id){
 $.dialog({
	title: '修改车辆信息',
	content: 'url:<%=basePath%>control/carInfo/goto/updateCarBrandPage?id='+id,	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '500px',
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
				<a>个人桌面</a> > <span></span> <a href="control/carInfo/select/carInfoList">车辆信息</a> > <span></span>信息列表
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
											<li>
											<!-- <a href="control/carInfo/goto/addCarInfoPage">新增</a> -->
											<a id="addBtn" href="javascript:">新增</a>
											</li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a href="javascript:changeRows();">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="text" value="${requestScope.text}" placeholder="车主/车牌/门店"/><input
												class="sea_btn" type="button" onclick="changeRows()" />
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>车类型</th>
								<th>车身结构</th>
								<th>车牌</th>
								<th>车架号</th>
								<th>买入时间</th>
								<th>准载数</th>
								<th>用车范围</th>
								<th>里程</th>
								<th>车主</th>
								<th>门店</th>
								<th>签约人</th>
								<th>车图片</th>
								<th>添加时间</th>
								<th>车辆性质</th>
								<th>更新时间</th>
								<th>库存状态</th>
								<th>停放位置</th>
								<th>GPS号码</th>
								<th>备注</th>
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<label><input type="checkbox" id="id" name="id" value="${data.id}" /> </label>
									</td>
									<td>${data.carType.carBrand.info}</td>
									<td>${data.carType.structure.name }</td>
									<td>${data.plateNumber}</td>
									<td>${data.frameNumber}</td>
									<td><fmt:formatDate value="${data.buyTime}" pattern="yyyy-MM-dd"/></td>
									<td>${data.loads}</td>
									<td>${data.scope}</td>
									<td>${data.mileage}</td>
									<td>${data.owner}</td>
									<td>${data.shopFront.name}</td>
									<td>${data.staff.name}</td>
									<td><a href="${data.picture}" target="_blank"><img
											src="${data.picture}" width="40px" height="40px"
											style="width: 40px;height: 40px;" />
									</a></td>
									<td>${data.addtime}</td>
									<td><c:if test="${data.nature==0}"><span style="color:blue">公车</span></c:if>
									<c:if test="${data.nature==1}"><span style="color:blue">私车</span></c:if></td>
									<td>${data.updateTime}</td>
									<td><c:if test="${data.status==0}"><span style="color:red">未出库</span></c:if>
									<c:if test="${data.status==1}"><span style="color:green">已出库</span></c:if></td>
									<td>${data.park}</td>
									<td>${data.remark}</td>
									<td>${data.gpsNumber}</td>
									<td>
						              <a class="operation-btn" onclick="addChecked()">新增</a>
						              <a class="operation-btn" onclick="deleteType('${data.id}')">删除</a>
						              <a class="operation-btn" onclick="updateInfo('${data.id}')">修改</a>
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
	title: '添加车辆信息',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/carInfo/goto/addCarInfoPage',	 //内文是页面，引入页面地址
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
    opacity: 0.4,      /* 透明度 */
    close:changeRows
})

$('a[nt_type="editor"]').dialog({
	title: '添加模板2',
	content: 'url:add2.html',	
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

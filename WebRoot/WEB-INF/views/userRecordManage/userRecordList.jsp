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
    				url : "<%=basePath%>control/record/delete/deleteshopFront",
    				data : "ccId=" + arrayValue,
    				success : function(data) {
    					if (data == "1") {
    						alert("删除成功");
    						location.reload(true);
    					} else if(data == "0") {
							alert("删除失败,该车辆有违章记录");
						}else if (data == "2") {
							alert("删除失败,该车辆有事故记录");
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
            	    window.location.href="control/record/goto/updateUserRecordPage?ccId="+ccId; 
            	    
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
				url : "<%=basePath%>control/record/delete/deleteshopFront",
				type : "post",
				data : {
					"id" : id
				},
				success : function(result) {
					if (result == "1") {
						alert("删除成功");
						location.reload();
					} else if(result == "0") {
							alert("删除失败,该车辆有违章记录");
					}else if (result == "2") {
							alert("删除失败,该车辆有事故记录");
					} else {
						alert("删除失败");
					}
				}
			});
		}
	}
function addChecked(){
    $.dialog({
	title: '添加使用记录',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/record/goto/addUserRecordPage',	 //内文是页面，引入页面地址
	/*ok: function(){							//确定按钮---不想要“确定”按钮可直接删掉
		return false;
	},*/
	cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
	cancel: true, /*为true等价于function(){}*/
	max: true,//false 不显示最大化窗口按钮，默认true
	min: true,//false 不显示最小化窗口按钮，默认true
	width: '900px',//窗口宽度
	height: '300px',//窗口高度
	lock: true,			/* 是否锁屏*/
	background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
    opacity: 0.4,     /* 透明度 */
    close:changeRows
    });
   }
    
function updateType(id){
 $.dialog({
	title: '修改使用记录',
	content: 'url:<%=basePath%>control/record/goto/updateUserRecordPage?ccId='+id,	
	cancelVal: '关闭',				
	cancel: true, 
	width: '900px',
	height: '300px',
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
				<a>个人桌面</a> > <span></span> <a href="control/record/select/userRecordList">使用信息</a> > <span></span>使用列表
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
											<input class="sea_text" type="text" name="text" value="${requestScope.text}" placeholder="借车人/门店"/><input
												class="sea_btn" type="button" onclick="changeRows()" />
										</div>
										<!-- <div class="list_date fright">
												<select name="type"  onchange="changeRows()"  class="select">
													<option value="-1">全部</option>
													<option value="0"></option>
													<option value="1"></option>
												</select>
										</div> -->
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>添加时间</th>
								<th>借车操作员</th>
								<th>借车人</th>
								<th>借车人联系方式</th>
								<th>车牌</th>
								<th>门店</th>
								<th>当前里程数(km)</th>
								<th>归还时间</th>
								<th>归还时的里程数(km)</th>
								<th>编号</th>
								<th>租车订单</th>
								<th>还车操作员</th>
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<label><input type="checkbox" id="ccId" name="ccId" value="${data.id}" /> </label>
									</td>
									<td>${data.addtime}</td>
									<td>${data.staff.name}</td>
									<td>${data.useUserName}</td>
									<td>${data.useUserPhone}</td>
									<td>${data.infos.plateNumber}</td>
									<td>${data.shopFront.name}</td>
									<td>${data.currentMileage}</td>
									<td><fmt:formatDate value="${data.returnTime}" pattern="yyyy-MM-dd"/></td>
									<td>${data.returnMileage}</td>
									<td>${data.number}</td>
									<td>${data.orders.number }</td>
									<td>${data.staff2.name}</td>
									<td>
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

	</div>
</body>
</html>

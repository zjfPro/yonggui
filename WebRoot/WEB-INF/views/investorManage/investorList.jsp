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
<link rel="stylesheet" href="<%=basePath%>oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath%>oa/css/list.css" type="text/css" />
<!-- <script src="oa/js/fixPNG.js" type="text/javascript"></script> -->
<script type="text/javascript" src="<%=basePath%>oa/js/jquery-1.8.0.min.js"></script>

<link type="text/css" rel="stylesheet" href="<%=basePath%>oa/js/popup/default.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>oa/js/popup/prettify.css">
<script src="<%=basePath%>oa/js/popup/prettify.js"></script>
<script src="<%=basePath%>oa/js/popup/jquery-1.js"></script>
<script src="<%=basePath%>oa/js/popup/lhgdialog.js"></script>
<script src="<%=basePath%>oa/js/js_checked.js"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	 function deleteChecked() {
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
    				url : "<%=basePath%>control/investor/delete/deleteInvestor",
					data : "ids=" + arrayValue,
					success : function(data) {
						if (data == "1") {
							window.location.href = "control/investor/select/investorList";
						} else {
							alert(data);
						}
					}
				});
				return true;
			} else {
				return false;
			}

	}
	
	function deleteDate(id){
		if (window.confirm("删除数据不可恢复，确认要删除吗？")) {
        		$.ajax({
    				url : "<%=basePath%>control/investor/delete/deleteInvestor",
					data : "ids=[" + id+"]",
					success : function(data) {
						if (data == "1") {
							window.location.href = "control/investor/select/investorList";
						} else{
							alert("删除有误！");
						}
					}
				});
				return true;
			} else {
				return false;
			}
	}

	function edit(id){
		$.dialog({
			title: '编辑部门',
			content: 'url:<%=basePath%>control/investor/goto/editInvestorView?id='+id,	
			cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
			cancel: true, /*为true等价于function(){}*/
			max: true,//false 不显示最大化窗口按钮，默认true
			min: true,//false 不显示最小化窗口按钮，默认true
			width: '650px',//窗口宽度
			height: '430px',//窗口高度
			lock: true,			/* 是否锁屏*/
			background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
		    opacity: 0.4,      /* 透明度 */
		    close:changeRows
		})
	}

</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a href="javascript:changeRows();">投资人管理</a>
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="25">
									<div class="clear">
										<ul class="zsgc fleft">
											<!-- <li><a href="control/investor/goto/addInvestor">新增</a></li> -->
											<li><a id="addBtn" href="javascript:">新增</a></li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<!-- <li><a onclick="checkDetail()">修改</a></li> -->
											<li><a href="javascript:changeRows();">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											姓名/电话/身份证：<input class="sea_text" type="text" name="text" value="${requestScope.text}" style="width:200px"/>
											<input class="sea_btn" type="button" onclick="changeRows()" />
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>照片</th>
								<th>姓名</th>
								<th>身份证</th>
								<th>电话号码</th>
								<th>账号</th>
								<th>操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30" ><label><input type="checkbox" id="ccId"
											name="ccId" value="${data.id}" /> </label>
									</td>
									<td>
										<a href="${data.picture}" target="_blank"><img
											src="${data.picture}" width="40px" height="40px"
											style="width: 40px;height: 40px;" />
										</a>
									</td>
									<td>${data.name}</td>
									<td>${data.idcard}</td>
									<td>${data.phone}</td>
									<td>${data.account}</td>
									<td>
										<a class="operation-btn" onclick="edit('${data.id}')">编辑</a>
          								<a class="operation-btn" onclick="deleteDate('${data.id}')">删除</a>
          							</td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="25" valign="middle"><%@include
										file="/WEB-INF/views/include/page.jsp"%></td>
							</tr>
						</tfoot>
					</table>
				</form>
			</div>
		</div>
	</div>
<script type="text/javascript">
$("#addBtn").dialog({
		title: '添加投资人',
		//content: '文本文本文本文本',  //内文是文本
		content: "url:<%=basePath%>control/investor/goto/addInvestor",	 //内文是页面，引入页面地址
		/* ok: function(){							//确定按钮---不想要“确定”按钮可直接删掉
			//return true; //点击确定，关闭弹窗
			return false;//点击确定不关闭弹窗
		}, */
		cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
		cancel: true, /*为true等价于function(){}*/
		max: true,//false 不显示最大化窗口按钮，默认true
		min: true,//false 不显示最小化窗口按钮，默认true
		width: '650px',//窗口宽度
		height: '430px',//窗口高度
		lock: true,			/* 是否锁屏*/
		background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
	    opacity: 0.4,      /* 透明度 */
	    close:changeRows
	})

</script>
</body>
</html>

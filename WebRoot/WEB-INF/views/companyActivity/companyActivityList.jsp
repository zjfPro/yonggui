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
<!-- <script src="oa/js/fixPNG.js" type="text/javascript"></script> -->
<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>

<link type="text/css" rel="stylesheet" href="oa/js/popup/default.css">
<link type="text/css" rel="stylesheet" href="oa/js/popup/prettify.css">
<script src="oa/js/popup/prettify.js"></script>
<script src="oa/js/popup/jquery-1.js"></script>
<script src="oa/js/popup/lhgdialog.js"></script>
<script src="oa/js/js_checked.js"></script>

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
    				url : "<%=basePath%>control/activity/delete/deleteCompanyActivity",
					data : "ids=" + arrayValue,
					success : function(data) {
						if (data == "1") {
							window.location.href = "control/activity/select/companyActivityList";
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
    				url : "<%=basePath%>control/activity/delete/deleteCompanyActivity",
					data : "ids=[" + id+"]",
					success : function(data) {
						if (data == "1") {
							window.location.href = "control/activity/select/companyActivityList";
						} else {
							alert("删除失败。");
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
			title: '编辑公司活动信息',
			content: 'url:<%=basePath%>control/activity/goto/editCompanyActivityView?id='+id,	
			cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
			cancel: true, /*为true等价于function(){}*/
			max: true,//false 不显示最大化窗口按钮，默认true
			min: true,//false 不显示最小化窗口按钮，默认true
			width: '1000px',//窗口宽度
			height: '680px',//窗口高度
			lock: true,			/* 是否锁屏*/
			background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
		    opacity: 0.4,      /* 透明度 */
		    close:changeRows
		})
	}

/* 	function checkDetail() {
		var str = "";
		$("input[name='ccId']:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		var strs = str.split(",");
		var selcount = (strs.length - 1);
		if (selcount != 0) {
			if (selcount == 1) {
				var ccId = strs[0];
				window.location.href = "control/activity/goto/editCompanyActivityView?id="
						+ ccId;
			} else {
				alert("你勾选了" + selcount + "条数据,请只勾选一条数据修改");
			}
		} else {
			alert("没有勾选数据,请只勾选一条数据修改");
		}
	} */
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a href="javascript:changeRows();">公司活动</a>
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="25">
									<div class="clear">
										<ul class="zsgc fleft">
											<!-- <li><a href="control/activity/goto/addCompanyActivity">新增</a></li> -->
											<li><a id="addBtn" href="javascript:">新增</a></li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<!-- <li><a onclick="checkDetail()">修改</a></li> -->
											<li><a href="javascript:changeRows();">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											查询：<input class="sea_text" type="text" name="text" value="${requestScope.text}" style="width:200px"/>
											<input class="sea_btn" type="button" onclick="changeRows()" />
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>图片</th>
								<th>标题</th>
								<th width="600px">简介</th>
								<th>状态</th>
								<th>添加时间</th>
								<th>操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30" ><label><input type="checkbox" id="ccId"
											name="ccId" value="${data.id}" /> </label>
									</td>
									<td>
										<c:forEach items="${data.picture.split(',')}" var="url">
											<a href="<%=basePath %>${url}" target="_Blank">	<img src="<%=basePath %>${url}"
											style="width: 50px;height: 50px;margin-top:5px;margin-bottom: 5px" /> </a>
										</c:forEach>
									</td>
									<td>${data.title}</td>
									<td>${data.noticeContentBrief}</td>
									<td>
										<c:if test="${data.status==0}">已发布</c:if>
										<c:if test="${data.status==1}">未发布</c:if>
									</td>
									<td>${data.addtime}</td>
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
<!---NO.2	弹出层引用实例--->
<script>
$("#addBtn").dialog({
		title: '添加公司活动',
		//content: '文本文本文本文本',  //内文是文本
		content: "url:<%=basePath%>control/activity/goto/addCompanyActivity",	 //内文是页面，引入页面地址
		/* ok: function(){							//确定按钮---不想要“确定”按钮可直接删掉
			//return true; //点击确定，关闭弹窗
			return false;//点击确定不关闭弹窗
		}, */
		cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
		cancel: true, /*为true等价于function(){}*/
		max: true,//false 不显示最大化窗口按钮，默认true
		min: true,//false 不显示最小化窗口按钮，默认true
		width: '1000px',//窗口宽度
		height: '680px',//窗口高度
		lock: true,			/* 是否锁屏*/
		background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
	    opacity: 0.4,      /* 透明度 */
	    close:changeRows
	})

</script>
<!---NO.2	End		弹出层引用实例--->
</body>
</html>

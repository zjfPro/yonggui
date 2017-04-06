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
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
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
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->

<script type="text/javascript">
	function cleanPage(){
    	window.location.href="<%=basePath%>shopFront/select/shopFrontList";
	}

 	 function deleteData(arrayValue) {
           if (window.confirm("确认删除所选中的数据？")) {
        		$.ajax({
    				url : "<%=basePath%>shopFront/delete/deleteshopFront",
				data : "ids=[" + arrayValue+"]",
				success : function(data) {
					if (data == "1") {
						window.location.href = "shopFront/select/shopFrontList";
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
    				url : "<%=basePath%>shopFront/delete/deleteshopFront",
				data : "ids=" + arrayValue,
				success : function(data) {
					if (data == "1") {
						window.location.href = "shopFront/select/shopFrontList";
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

	function checkDetail() {
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
				window.location.href = "shopFront/goto/modifyshopFront?id="
						+ ccId;
			} else {
				alert("你勾选了" + selcount + "条数据,请只勾选一条数据修改");
			}
		} else {
			alert("没有勾选数据,请只勾选一条数据修改");
		}
	}
	
	
	  function tanchuang(tit,url){
    	 $.dialog({
    			title: tit,
    			content: 'url:<%=basePath%>'+url,	 //内文是页面，引入页面地址
    			cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
    			cancel: true, /*为true等价于function(){}*/
    			max: true,//false 不显示最大化窗口按钮，默认true
    			min: true,//false 不显示最小化窗口按钮，默认true
    			width: '1000px',//窗口宽度
    			height: '600px',//窗口高度
    			lock: true,			/* 是否锁屏*/
    			background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
    		    opacity: 0.4,      /* 透明度 */
    		    close:cleanPage
    	 });
     }
     
 
	
	
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a href="javascript:changeRows();">门店管理</a>
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="25">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a  onclick="tanchuang('新增门店','shopFront/goto/addshopFront')"  >新增</a></li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<!-- <li><a onclick="checkDetail()">修改</a></li> -->
											<li><a href="javascript:changeRows();" id="changeRows_button">刷新</a></li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="text" value="${requestScope.text}" /><input
												class="sea_btn" type="button" onclick="changeRows()" />
										</div>
										<div class="list_date fright">
											<select name="type" onchange="changeRows()" class="select">
												<option value="-1" <c:if test="${requestScope.type==-1}">selected="selected"</c:if> >全部</option>
												<option value="0"  <c:if test="${requestScope.type==0}">selected="selected"</c:if> >总店</option>
												<option value="1"  <c:if test="${requestScope.type==1}">selected="selected"</c:if> >分店</option>
											</select>
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>logo</th>
								<th>名称</th>
								<th>负责人</th>
								<th>地址</th>
								<th>介绍</th>
								<th>类型</th>
							<th width="14%">操作</th>
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30" ><label><input type="checkbox" id="ccId"
											name="ccId" value="${data.id}" /> </label>
									</td>
									<td><a href="${data.logo}" target="_blank"><img
											src="${data.logo}" width="40px" height="40px"
											style="width: 40px;height: 40px;" />
									</a></td>
									<td>${data.name}</td>
									<td>${data.manageStaff.name}</td>
									<td>${data.address}</td>
									<td>${data.introduce}</td>
									<td>${data.levelSTR}</td>
									<td>${data.addtime}</td>
									<td> <a class="operation-btn" onclick="tanchuang('新增门店','shopFront/goto/addshopFront')"  >新增</a>
						              <a class="operation-btn" onclick="deleteData('${data.id}')">删除</a>
						              <a class="operation-btn" onclick="tanchuang('修改门店','shopFront/goto/modifyshopFront?id=${data.id}')" >修改</a> 
						              <a class="operation-btn" onclick="cleanPage()">刷新</a></td>
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

	</div>
</body>
</html>

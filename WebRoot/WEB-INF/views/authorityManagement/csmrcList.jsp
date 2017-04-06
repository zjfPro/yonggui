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

<!-- 弹窗需要的js css start -->
<link type="text/css" rel="stylesheet" href="oa/js/popup/default.css">
<link type="text/css" rel="stylesheet" href="oa/js/popup/prettify.css">
<script src="oa/js/popup/prettify.js"></script>
<script src="oa/js/popup/jquery-1.js"></script>
<script src="oa/js/popup/lhgdialog.js"></script>
<script src="oa/js/js_checked.js"></script>
<!-- 弹窗需要的js css end -->
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->
<script>

	function cleanPage(){
    	window.location.href="<%=basePath%>control/roleContainer/select/csmrcList";
	}

     function deleteChecked(){
            var str="";
            $("input[name='theCheckbox']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+","
                }
            });
            var strs = str.split(",");
            var selcount = (strs.length-1);
            if(selcount!=0){
            	var queren = confirm("删除后无法恢复,确定删除勾选的"+selcount+"条数据?");
            	if(queren){
            		$.ajax({
						type:'post',
						url:'<%=basePath%>control/roleContainer/delete/deleteCsmrc',
						data:{"strs":str},
						success : function(data) {
							if(data=='1'){
								alert("删除成功");
								cleanPage();
							}else{
								alert(data);
							}
						}
					});
            	}
            }else{
            	alert("请勾选需要删除的数据");
            }
     
     }
     function deleteData(id){
    	var queren = confirm("删除后无法恢复,确定删除?");
     	if(queren){
     		$.ajax({
					type:'post',
					url:'<%=basePath%>control/roleContainer/delete/deleteCsmrc',
					data:{"strs":id},
					success : function(data) {
						if(data=='1'){
							alert("删除成功");
							cleanPage();
						}else{
							alert(data);
						}
					}
				});
     	}
    	 
     }
     function tanchuang(tit){
    	 $.dialog({
    			title: tit,
    			content: 'url:<%=basePath%>control/roleContainer/goto/addJuese',	 //内文是页面，引入页面地址
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
    	 })
     }
     
     function detail(jsid) {
         $.dialog({
 			title: '修改角色',
 			content: 'url:<%=basePath%>control/roleContainer/select/csmrcDetail?jsid='+jsid,	 //内文是页面，引入页面地址
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
 	 	})
     }

</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> ><a href="javascript:changeRows();">角色列表</a>
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>control/authority/select/csmrcList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="13">
									<div class="clear">
										<ul class="zsgc fleft">
											<li>
											
											<a onclick="tanchuang('新增角色')" id="jueseadd">新增</a>
											</li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a onclick="cleanPage();">刷新</a></li> 
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="searchText" placeholder="角色/门店" 
											value="${requestScope.searchText}"/>
											<input class="sea_btn" onclick="changeRows()" type="button" />
										</div>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th></th>
								<th>角色名</th>
								<th>门店</th>
								<th width="7%">操作</th>
							</tr>


							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30">
										<p>
											<label><input type="checkbox" name="theCheckbox" value="${data.id}"
												id="theCheckbox" /> </label>
										</p></td>
									<td>${data.name}</td>
									<td>${data.cgShopFront.name}</td>
									<td>
						              <a class="operation-btn" onclick="tanchuang('新增角色')">新增</a>
						              <a class="operation-btn" onclick="deleteData('${data.id}')">删除</a>
						              <a class="operation-btn" onclick="detail('${data.id}')">修改</a>
						              <a class="operation-btn" onclick="cleanPage()">刷新</a>
						            </td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="13" valign="middle">
								<%@include file="/WEB-INF/views/include/page.jsp"%></td>
							</tr>
						</tfoot>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

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

<!-- 弹窗start -->
<link type="text/css" rel="stylesheet" href="oa/js/popup/default.css">
<link type="text/css" rel="stylesheet" href="oa/js/popup/prettify.css">
<script src="oa/js/popup/prettify.js"></script>
<script src="oa/js/popup/jquery-1.js"></script>
<script src="oa/js/popup/lhgdialog.js"></script>
<script src="oa/js/js_checked.js"></script>
<!-- 弹窗end -->

<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->
<script>
	function cleanPage(){
	    window.location.href="<%=basePath%>companyProfile/select/companyProfileList";
	}
	function checkDetail() {
            var str="";
            $("input[name='theCheckbox']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    str += $(this).val()+","
                }
            })
            var strs = str.split(",");
            var selcount = (strs.length-1);
            if(selcount!=0){
            	if(selcount==1){
            		var staffid = strs[0];
            	    window.location.href="control/staffManager/select/cgStaffDetail?staffid="+staffid; 
            	    
            	}else{
            		alert("你勾选了"+selcount+"条数据,请只勾选一条数据修改");
            	}
            }else{
            		alert("没有勾选数据,请只勾选一条数据修改");
            }
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
					url:'<%=basePath%>control/staffManager/delete/deleteStaff',
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
     
     function tanchuang(tit){
    	 $.dialog({
    			title: tit,
    			content: 'url:<%=basePath%>control/staffManager/goto/addStaffPage',	 //内文是页面，引入页面地址
    			cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
    			cancel: true, /*为true等价于function(){}*/
    			max: true,//false 不显示最大化窗口按钮，默认true
    			min: true,//false 不显示最小化窗口按钮，默认true
    			width: '1000px',//窗口宽度
    			height: '500px',//窗口高度
    			lock: true,			/* 是否锁屏*/
    			background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
    		    opacity: 0.4,      /* 透明度 */
    		    close:cleanPage
    	 })
     }
     
     function detail(ygid) {
         $.dialog({
 			title: '修改公司简介',
 			content: 'url:<%=basePath%>companyProfile/select/companyProfileDetail?cpid='+ygid,	 //内文是页面，引入页面地址
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
     function detailck(ygid) {
         $.dialog({
 			title: '公司简介内容',
 			content: 'url:<%=basePath%>companyProfile/select/chakanDetail?cpid='+ygid,	 //内文是页面，引入页面地址
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
     
     function deleteData(id){
     	var queren = confirm("删除后无法恢复,确定删除?");
      	if(queren){
      		$.ajax({
 					type:'post',
 					url:'<%=basePath%>control/staffManager/delete/deleteStaff',
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

</script>


</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a href="<%=basePath%>companyProfile/select/companyProfileList">公司简介</a>
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>control/staffManager/select/staffList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="16">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a onclick="cleanPage();">刷新</a></li> 
										</ul>
									</div>
								</td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>图片</th>
								<th>标题</th>
								<th>公司简介内容</th>
								<th>备注</th>
								
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30">
										<input type="checkbox" id="theCheckbox" name="theCheckbox" value="${data.id}" />
									</td>
									
									<td>
										<a href="${data.picture}" target="_blank">
											<img
												src="<%=basePath%>${data.picture}" width="40px" height="40px"
												style="width: 40px;height: 40px;" />
										</a>
									</td>
									<td>${data.title}</td>
									<td><a onclick="detailck('${data.id}')">查看内容</a></td>
									<td>${data.remark}</td>
									<td>
						              <a class="operation-btn" onclick="detail('${data.id}')">修改</a>
						              <a class="operation-btn" onclick="cleanPage()">刷新</a>
						            </td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="16" valign="middle">
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

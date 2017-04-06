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
<script>
	function cleanPage(){
	    window.location.href="<%=basePath%>control/carInsurance/select/cgCarInsuranceList";
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
            		var cciID = strs[0];
            	    window.location.href="control/carInsurance/select/cgCarInsuranceDetail?cciID="+cciID; 
            	    
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
					url:'<%=basePath%>control/carInsurance/delete/deleteCarInsurance',
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
function addInsurance(){
    $.dialog({
	title: '添加车辆保险',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/carInsurance/goto/addCgCarInsurance',	 //内文是页面，引入页面地址
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
    close:cleanPage
    });
   }
    
function updateInsurance(id){
 $.dialog({
	title: '修改车辆保险',
	content: 'url:<%=basePath%>control/carInsurance/select/cgCarInsuranceDetail?cciID='+id,	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '580px',
	lock: true,			
	background: '#000',
    opacity: 0.4,
    close:cleanPage
     });
}    

 function deleteInsurance(id) {
		if (id == null || id.trim() == "") {
			return;
		}
		if (window.confirm('是否删除该数据？')) {
			$.ajax({
				url : "<%=basePath%>control/carInsurance/delete/deleteInsurance",
				type : "post",
				data : {
					"idc" : id
				},
				success : function(result) {
					if (result == "1") {
						alert("删除成功");
						location.reload();
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
				<a>个人桌面</a><span> > </span> <a href="<%=basePath%>control/carInsurance/select/cgCarInsuranceList">车辆保险列表</a>
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>control/carInsurance/select/cgCarInsuranceList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="13">
									<div class="clear">
										<ul class="zsgc fleft">
											<li><a onclick="addInsurance()">新增</a></li>
											<li><a onclick="deleteChecked()">删除</a></li>
											<li><a onclick="cleanPage();">刷新</a></li> 
										</ul>
										
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="searchText" placeholder="经办人/承保公司/保险单号/门店" 
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
								
								<th>车辆(车牌)</th>
								<th>保险名称</th>
								<th>承保公司</th>
								<th>保险单号</th>
								<th>缴费总计</th>
								<th>投保日期</th>
								<th>到期时间</th>
								<th>经办人</th>
								<th width="7%">操作</th>
							</tr>
							
							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<p>
											<label><input type="checkbox" name="theCheckbox" value="${data.id}"
												id="theCheckbox" /> </label>
										</p>
									</td>
									
									<td>${data.cgCarInfo.plateNumber}</td>
									<td>${data.insuranceName}</td>
									<td>${data.insurer}</td>
									<td>${data.insuranceCode}</td>
									<td>${data.totalPrices}</td>
									<td>
										<fmt:formatDate value="${data.startTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										<fmt:formatDate value="${data.endTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>${data.insured}</td>
									<td>
									  <a class="operation-btn" onclick="addInsurance()">新增</a>
						              <a class="operation-btn" onclick="deleteInsurance('${data.id}')">删除</a>
						              <a class="operation-btn" onclick="updateInsurance('${data.id}')">修改</a>
						              <a class="operation-btn" href="javascript:changeRows();">刷新</a>
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

	</div>
</body>
</html>

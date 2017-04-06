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
    				url : "<%=basePath%>control/contract/delete/deleteContract",
    				data : "ccId=" + arrayValue,
    				success : function(data) {
    					if (data == "1") {
    						alert("删除成功");
    						location.reload(true);
    					} else if (data == "0") {
							alert("删除失败");
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
            	    window.location.href="control/contract/goto/updateContractPage?ccId="+ccId; 
            	    
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
				url : "<%=basePath%>control/contract/delete/deleteContract",
				type : "post",
				data : {
					"id" : id
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
	title: '添加合同',
	//content: '文本文本文本文本',  //内文是文本
	content: 'url:<%=basePath%>control/contract/goto/addContractPage',	 //内文是页面，引入页面地址
	/*ok: function(){							//确定按钮---不想要“确定”按钮可直接删掉
		return false;
	},*/
	cancelVal: '关闭',						//关闭按钮---不想要“确定”按钮可直接删掉
	cancel: true, /*为true等价于function(){}*/
	max: true,//false 不显示最大化窗口按钮，默认true
	min: true,//false 不显示最小化窗口按钮，默认true
	width: '1000px',//窗口宽度
	height: '600px',//窗口高度
	lock: true,			/* 是否锁屏*/
	background: '#000', /* 背景色 默认的遮罩背景色为:#DCE2F1浅蓝护眼色 */
    opacity: 0.4,     /* 透明度 */
    close:changeRows
    });
   }
    
function updateType(id){
 $.dialog({
	title: '修改车辆类型',
	content: 'url:<%=basePath%>control/contract/goto/updateContractPage?ccId='+id,	
	cancelVal: '关闭',				
	cancel: true, 
	width: '1000px',
	height: '600px',
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
				<a>个人桌面</a> > <span></span> <a href="control/contract/select/contractList">合同列表</a> > <span></span>合同信息
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
											<li 
												style="background: url(<%=basePath%>/oa/images/zsgc_90_bg.png) no-repeat; width: 90px;">
												<a href="control/contract/select/contractList?ccId=1" style="width: 90px;">租车合同</a>
											</li>
											<li 
												style="background: url(<%=basePath%>/oa/images/zsgc_90_bg.png) no-repeat; width: 90px;">
												<a href="control/contract/select/contractList?ccId=0" style="width: 90px;">车辆签约合同</a>
											</li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="text" value="${requestScope.text}" placeholder="门店/投资人/车牌"/><input
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
								<th>合同内容</th>
								<th>门店</th>
								<th>投资人</th>
								<th>车牌号</th>								
								<th>签约员工</th>
								<th>合同编号</th>
								<th>订单编号</th>
								<th>合同类型</th>
								
								<th>合同名称</th>
								<th>负责人</th>
								<th>签订日期</th>
								<th>合同状态</th>
								<th>合同金额</th>
								<th>结算方式</th>
								<th>合同开始时间</th>
								<th>合同结束时间</th>
								<th>备注</th>
								<th width="7%">操作</th>
							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td>
										<label><input type="checkbox" id="ccId" name="ccId" value="${data.id}" /> </label>
									</td>
									<td><c:forEach items="${data.content.split(',')}" var="url">
										<a href="<%=basePath %>${url}" target="_Blank">	<img src="<%=basePath %>${url}"
										style="width: 50px;height: 50px;margin-top:5px;margin-bottom: 5px" /> </a>
									</c:forEach></td>
									<td>${data.front.name}</td>
									<td>${data.investor.name}</td>
									<td>${data.plateNumber}</td>
									<td>${data.staff.name}</td>
									<td>${data.number}</td>
									<td>${data.infos.number}</td>
									<td><c:if test="${data.type==0}"><span style="color:blue">车辆签约合同</span></c:if>
										<c:if test="${data.type==1}"><span style="color:red">租车合同</span></c:if>
									</td>
									
									<td>${data.name}</td>
									<td>${data.principal}</td>
									<td><fmt:formatDate value="${data.contractTime}" pattern="yyyy-MM-dd"/></td>
									<td><c:if test="${data.status==0}"><span style="color:red">未签订</span></c:if>
										<c:if test="${data.status==1}"><span style="color:blue">已签订</span></c:if></td>
									<td>${data.contractMoney}</td>
									<td><c:if test="${data.clearingForm==0}"><span style="color:green">全额付款</span></c:if>
										<c:if test="${data.clearingForm==1}"><span style="color:blue">分期付款</span></c:if></td>
									<td><fmt:formatDate value="${data.startTime}" pattern="yyyy-MM-dd"/></td>
									<td><fmt:formatDate value="${data.endTime}" pattern="yyyy-MM-dd"/></td>
									<td>${data.remark}</td>
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

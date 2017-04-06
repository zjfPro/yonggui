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
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->
<script>
	function cleanPage(){
	    window.location.href="<%=basePath%>control/rentalOrder/select/rentalOrderList";
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
            		var ccriID = strs[0];
            	    window.location.href="<%=basePath%>control/rentalOrder/select/shenheDetail?shenheorderID="+ccriID; 
            	    
            	}else{
            		alert("你勾选了"+selcount+"条数据,请只勾选一条数据审核");
            	}
            }else{
            		alert("没有勾选数据,请只勾选一条数据审核");
            }
     }
	
	function perfect() {
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
        		var ccriID = strs[0];
        	    window.location.href="<%=basePath%>control/rentalOrder/select/wanshanDetail?wanshanorderID="+ccriID; 
        	    
        	}else{
        		alert("你勾选了"+selcount+"条数据,请只勾选一条数据修改");
        	}
        }else{
        		alert("没有勾选数据,请只勾选一条数据修改");
        }
 	}
	
	function payment() {
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
        		var ccriID = strs[0];
        	    window.location.href="<%=basePath%>control/rentalOrder/select/fukuanDetail?fukuanorderID="+ccriID; 
        	    
        	}else{
        		alert("你勾选了"+selcount+"条数据,请只勾选一条订单支付");
        	}
        }else{
        		alert("没有勾选数据,请只勾选一条订单支付");
        }
 	}
	
	function quche() {
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
        		var ccriID = strs[0];
        	    window.location.href="<%=basePath%>control/rentalOrder/select/qucheDetail?qucheorderID="+ccriID; 
        	    
        	}else{
        		alert("你勾选了"+selcount+"条数据,请只勾选一条订单完成取车");
        	}
        }else{
        		alert("没有勾选数据,请只勾选一条订单完成取车");
        }
 	}
	
	function huanche() {
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
        		var ccriID = strs[0];
        	    window.location.href="<%=basePath%>control/rentalOrder/select/huancheDetail?huancheorderID="+ccriID; 
        	    
        	}else{
        		alert("你勾选了"+selcount+"条数据,请只勾选一条订单完成还车");
        	}
        }else{
        		alert("没有勾选数据,请只勾选一条订单完成还车");
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
					url:'<%=basePath%>control/rentalItem/delete/deleteCgCarRentalItem',
							data : {
								"strs" : str
							},
							success : function(data) {
								if (data == '1') {
									alert("删除成功");
									cleanPage();
								} else {
									alert(data);
								}
							}
						});
			}
		} else {
			alert("请勾选需要删除的数据");
		}

	}
</script>
</head>

<body>

	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a> > <span></span> <a>租车信息</a> > <span></span>租车订单列表
			</div>
			<div class="infolist">

				<form id="topageform" name="topageform" method="post"
					action="<%=basePath%>control/rentalOrder/select/rentalOrderList">
					<table class="center_table" id="table_all">

						<thead>
							<tr>
								<td colspan="30">
									<div class="clear">
										<ul class="zsgc fleft">
											<li
												style="background: url(<%=basePath%>/oa/images/zsgc_90_bg.png) no-repeat; width: 90px;"><a
												onclick="checkDetail()" style="width: 90px;">审核订单</a>
											</li>
											<li
												style="background: url(<%=basePath%>/oa/images/zsgc_90_bg.png) no-repeat; width: 90px;"><a
												onclick="perfect()" style="width: 90px;">完善订单</a>
											</li>
											<li><a onclick="payment()">付款</a>
											</li>
											<li><a onclick="quche()">取车</a>
											</li>
											<li><a onclick="huanche()">还车</a>
											</li>
											<%--
											<li><a onclick="deleteChecked()">删除</a>
											</li>
											
											<li><a onclick="cleanPage();">未审核</a>
											</li>
											<li><a onclick="cleanPage();">已通过</a>
											</li>
											<li><a onclick="cleanPage();">未通过</a>
											</li>
											<li><a onclick="cleanPage();">未付款</a>
											</li>
											<li><a onclick="cleanPage();">已付款</a>
											</li>
											--%><li><a onclick="cleanPage();">刷新</a>
											</li>
										</ul>
										<div class="searchbox fright">
											<input class="sea_text" type="text" name="searchText" placeholder="订单/车牌/门店" 
											value="${requestScope.searchText}"/>
											<input class="sea_btn" onclick="changeRows()" type="button" />
										</div>
										
									</div></td>
							</tr>
						</thead>

						<tbody>
							<tr>
								<th>选择</th>
								<th>订单编号</th>
								<th>车辆(车牌)</th>
								<th>租车用户</th>
								<th>租车用户联系电话</th>
								<th>门店</th>
								<th>业务员</th>
								<th>定金</th>
								<th>押金</th>
								<th>支付总价</th>
								<th>付款状态</th>
								<th>审核状态</th>
								<th>添加时间</th>

							</tr>

							<c:forEach items="${requestScope.pageUtil.datas}" var="data"
								varStatus="status">
								<tr class="hover_bg">
									<td class="w30"><input type="checkbox" id="theCheckbox"
										name="theCheckbox" value="${data.id}" /></td>
									<td>${data.number}</td>
									<td>${data.cgCarRentalItem.cgCarInfo.plateNumber}</td>
									<td>${data.userName}</td>
									<td>${data.userPhone}</td>
									<td>${data.cgShopFront.name}</td>

									<td>
										<c:if test="${data.cgStaff!=null}">${data.cgStaff.name}</c:if>
										<c:if test="${data.cgStaff==null}">暂无选定</c:if>
									</td>
									<td>${data.earnestMoney}</td>
									<td>${data.foregiftMoney}</td>
									<td>${data.buyMoney}</td>
									<td>
										<c:if test="${data.payStatus==0}"><span style="color: orange;">未付款</span></c:if> 
										<c:if
											test="${data.payStatus==1}"><span style="color: green;">已付款</span></c:if> 
										<c:if
											test="${data.payStatus==2}"><span style="color: red;">已退款</span></c:if>
									</td>
									<td><c:if test="${data.status==0}"><span style="color: orange;">未审核</span></c:if> <c:if
											test="${data.status==1}"><span style="color: green;">已通过</span></c:if> <c:if
											test="${data.status==2}"><span style="color: red;">未通过</span></c:if></td>
									<td><fmt:formatDate value="${data.addtime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
								</tr>
							</c:forEach>

						</tbody>

						<tfoot>
							<tr>
								<td colspan="16" valign="middle"><%@include
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

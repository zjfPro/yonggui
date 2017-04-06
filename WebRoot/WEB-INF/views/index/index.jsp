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
<link rel="stylesheet" href="oa/css/index.css" type="text/css" />
<link rel="stylesheet" href="oa/js/popup/default.css" type="text/css" />
<link type="text/css" rel="stylesheet" href="oa/js/popup/prettify.css">

<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="oa/js/kandtabs/kandytabs.pack.js"></script>
<script type="text/javascript" src="oa/js/easing.js"></script>
<link rel="stylesheet" href="oa/js/kandtabs/kandytabs.css"
	type="text/css" />
<script src="oa/js/autoheight.js" type="text/javascript"></script>
<script src="js/checkStock.js" type="text/javascript"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,span');
 </script>     
<![endif]-->
<link href="oa/css/lanrenzhijia.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var tab, index = 0;
	$(function() {
		tab = $("#slide").KandyTabs({
			del : true,
			scroll : true,
			trigger : 'click',
			custom : function(b, c, i) {
				$("p", c).fadeOut();
				c.eq(i).find("p").slideDown(1500);
				index = i;
			},
			done : function(btn, cont, tab) {
				$("#slide .tabbtn").each(function(i) {
					if ($(this).text().indexOf("我的桌面") > -1)//如果当前选项卡是我的桌面
					{
						$(this).css({
							"background" : "#027be4",
							"border-bottom" : "1px solid #027be4",
							"font-weight" : "bold",
							"color" : "#ffffff"
						});//修改选项景色
						$(this).find('.tabdel').text("");//	去除关闭按钮
					}
				});
				//setIframeH();//前台设定IFRAME高度 最好在在登录时把高度获取存放到session供其他IFRAME使用
			}
		});

		$("#slide").height($(window).height() - 111);
		$("#slide dd").height($(window).height() - 145);
		$("#slide dd iframe").height($(window).height() - 145);
		$(window).resize(function() {
			$("#slide").height($(window).height() - 111);
			$("#slide dd").height($(window).height() - 145);
			$("#slide dd iframe").height($(window).height() - 145);
		});
	});
	
	function zhuxiao() {
		var queren = confirm("确认要注销吗?");
    	if(queren){
    		window.location.href="<%=basePath%>out";
    	}
		
	}
</script>
</head>

<body style="overflow: hidden;" scroll="no">


	<div class="header">
		<div class="head_logo">
			<img src="oa/images/logo.png" />
		</div>
		<div class="header_right">
			<ul>
				<!-- <li><a href="#" onclick="addTab('list.html','我的首页');" title="首页" id="home"></a></li> -->
				<li><a href="#" title="首页" id="home"></a>
				</li>
				<!-- <li><a href="#" title="更换皮肤" id="theme"></a></li> -->
				<li><a href="#" title="设置" id="Setup"></a>
				</li>
				<li><a href="#" title="刷新" id="refresh"></a>
				</li>
				<li><a onclick="zhuxiao()"  title="注销登录" id="logout"></a>
				</li>
			</ul>
		</div>
	</div>


	<dl id="slide">
		<dt>我的桌面</dt>
		<dd
			style="background:url(oa/images/desk_bg.jpg) center center #0290ea; background-size:cover;">
			<iframe id=centerFrame name="centerFrame" class="centerFrame"
				frameborder="0"
				style="width: 100%;height:100%; overflow-x: hidden; overflow-y:auto"
				noresize="noresize" scrolling="auto" src="<%=basePath%>center"></iframe>
		</dd>
	</dl>


	<div id="footer" region="south" border="false" class="cs-south">
		<!-- 菜单 -->
		<div class="menu-wrap">
			<a class="menu-icon" href="#"><img src="oa/images/menu.jpg">
			</a>

			<div class="menubox">
				<div class="menu-user">
					<div class="img">
						
						
						<c:if test="${sessionScope.admin!=null}">
							<img src="oa/images/people.jpg">
						</c:if>
					
						<c:if test="${sessionScope.staff!=null}">
							
							<img src="${sessionScope.staff.logo}">
						</c:if>
						
					</div>
					<strong>
					
					<c:if test="${sessionScope.admin!=null}">
						${sessionScope.admin.nickname}
					</c:if>
					
					<c:if test="${sessionScope.staff!=null}">
						${sessionScope.staff.name}
					</c:if>
					
					</strong>
				</div>
				<div class="out_quit">
					<a class="logout" onclick="zhuxiao()" title="注销">注销</a> <a class="quit"
						onclick="zhuxiao()" title="退出">退出</a>
				</div>
				<div class="menu">
					<dl>
						<dt>
							<a class="icon_01">报表统计</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_02">订单管理</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('control/rentalOrder/select/rentalOrderList','租车订单管理')">租车订单管理</a>
									</li>
									<li><a onClick="window.addTab('control/rentalItem/select/rentalItemList','租车项目管理')">租车项目管理</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_03">保养维修</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('control/carUpkeep/select/cgCarUpkeepList','车辆保养管理')">车辆保养管理</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_05">驾驶人</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="">驾驶人管理</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_06">投资人管理</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('control/investor/select/investorList','投资人管理')">投资人管理</a>
									</li>
									<li><a onClick="window.addTab('control/contract/select/contractList','合同管理')">合同管理</a>
									</li>
									<li><a onClick="window.addTab('control/investorCar/select/investorCarList','车辆投资记录')">车辆投资记录</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_07" onClick="">客户管理</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('list.html','客户管理')">客户管理</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_08">车辆管理</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('control/carInsurance/select/cgCarInsuranceList','车辆保险管理')">车辆保险管理</a>
									</li>
									<li><a onClick="window.addTab('control/carType/select/carTypeList','车辆类型管理')">车辆类型管理</a>
									</li>
									<li><a onClick="window.addTab('control/carInfo/select/carInfoList','车辆信息管理')">车辆信息管理</a>
									</li>
									<li><a onClick="window.addTab('control/brand/select/brandList','车辆品牌管理')">车辆品牌管理</a>
									</li>
									<li><a onClick="window.addTab('control/manufacturer/select/manufacturerList','车辆厂商管理')">车辆厂商管理</a>
									</li>
									<li><a onClick="window.addTab('control/series/select/seriesList','车辆系列管理')">车辆系列管理</a>
									</li>
									<li><a onClick="window.addTab('control/model/select/modelList','车辆型号管理')">车辆型号管理</a>
									</li>
									<li><a onClick="window.addTab('control/record/select/userRecordList','车辆使用记录')">车辆使用记录</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_09" onClick="">分析统计</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('control/record/select/userRecordList','车辆使用记录')">车辆使用记录</a>
									</li>
									<li><a onClick="window.addTab('control/rentalOrder/select/rentalOrderList','租车订单管理')">租车订单管理</a>
									</li>
									<li><a onClick="window.addTab('control/setting/select/settingList','车辆库存管理')">车辆库存管理</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_10" onClick="">员工管理</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('shopFront/select/shopFrontList','门店管理')">门店管理</a>
									</li>
									<li><a onClick="window.addTab('control/dept/select/deptList','部门管理')">部门管理</a>
									</li>
									<li><a onClick="window.addTab('control/staffManager/select/staffList','员工管理')">员工管理</a>
									</li>
								</ul>
							</div>
						</dd>
					</dl>
					<dl>
						<dt>
							<a class="icon_11" onClick="">管理中心</a>
						</dt>
						<dd>
							<div class="submenu">
								<ul>
									<li><a onClick="window.addTab('control/gps/select/gpsList','GPS管理')">GPS管理</a>
									</li>
									<li><a onClick="window.addTab('control/peccancy/select/peccancyList','违章管理')">违章管理</a>
									</li>
									<li><a onClick="window.addTab('control/accident/select/accidentList','事故管理')">事故管理</a>
									</li>
									<li><a onClick="window.addTab('control/feedback/select/feedbackList','客户反馈')">客户反馈</a>
									</li>
									<li><a onClick="window.addTab('control/news/select/newsInformationList','新闻资讯')">新闻资讯</a>
									</li>
									<li><a onClick="window.addTab('control/activity/select/companyActivityList','公司活动')">公司活动</a>
									</li>
									
								</ul>
							</div>
						</dd>
					</dl>
					
				</div>

			</div>
		</div>




		<div class="footer_right">
			<span class="number">在线人数<strong>999</strong>人</span> <a href="#"
				class="tixing"><img src="oa/images/tixing.png" width="16"
				height="16" />
			</a> <a href="#" class="xiaoxi"><img src="oa/images/youjian.png"
				width="20" height="13" />
			</a> <a href="#" class="liaotian"><img src="oa/images/liaotian.png"
				width="27" height="19" />
			</a>
		</div>
		<div class="clear"></div>


	</div>
</body>
</html>

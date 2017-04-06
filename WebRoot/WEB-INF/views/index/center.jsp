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
<link rel="stylesheet" href="oa/css/global.css" type="text/css"/>
<link rel="stylesheet" href="oa/css/index.css" type="text/css"/>
<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>
<script src="oa/js/cfcoda.js" language="javascript"></script>
<script src="oa/js/time.js" language="javascript"></script>




<script type="text/javascript" src="oa/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="oa/js/kandtabs/kandytabs.pack.js"></script>
<script type="text/javascript" src="oa/js/easing.js"></script>
<link rel="stylesheet" href="oa/js/kandtabs/kandytabs.css" type="text/css" />
<script src="oa/js/autoheight.js" type="text/javascript"></script>
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->
<link href="oa/css/lanrenzhijia.css" rel="stylesheet" type="text/css" />
</head>
<body >
<!-- content -->
<div  style="position:relative;">
  
  
     
   <div id="frame">
   <div id="scroller">
    <div id="content">
        <div class="section">
        <div class="page1">
           <div class="content">
            <div class="second_screen">
               <ul>
                	<li>
        				<p>模块管理</p>
	        			<a onclick="parent.addTab('control/authority/select/cgStaffManageModelList','模块管理')">
	        				<img src="oa/images/icon_5.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>角色管理</p>
	        			<a onclick="parent.addTab('control/roleContainer/select/csmrcList','角色管理')">
	        				<img src="oa/images/icon_36.png"  />
	        			</a>
        			</li>
        			
        			<li>
        				<p>门店管理</p>
	        			<a onclick="parent.addTab('shopFront/select/shopFrontList','门店管理')">
	        				<img src="oa/images/erer_6.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>部门管理</p>
	        			<a onclick="parent.addTab('control/dept/select/deptList','部门管理')">
	        				<img src="oa/images/wqere_5png.png"  />
	        			</a>
        			</li>
        			
        			<li>
        				<p>员工管理</p>
	        			<a onclick="parent.addTab('control/staffManager/select/staffList','员工管理')">
	        				<img src="oa/images/euwhu_2.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆保险管理</p>
	        			<a onclick="parent.addTab('control/carInsurance/select/cgCarInsuranceList','车辆保险管理')">
	        				<img src="oa/images/icon_16.png"  />
	        			</a>
        			</li>
        			
        			<li>
        				<p>车辆保养管理</p>
	        			<a onclick="parent.addTab('control/carUpkeep/select/cgCarUpkeepList','车辆保养管理')">
	        				<img src="oa/images/icon_17.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆类型管理</p>
	        			<a onclick="parent.addTab('control/carType/select/carTypeList','车辆类型管理')">
	        				<img src="oa/images/icon_15.png"  />
	        			</a>
        			</li>
					<!-- <li>
        				<p>品牌管理</p>
	        			<a onclick="parent.addTab('carBrand/select/carBrandList','车辆品牌管理')">
	        				<img src="oa/images/icon_8.png"  />
	        			</a>
        			</li> -->
        			<li>
        				<p>车辆信息管理</p>
	        			<a onclick="parent.addTab('control/carInfo/select/carInfoList','车辆信息管理')">
	        				<img src="oa/images/icon_11.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>合同管理</p>
	        			<a onclick="parent.addTab('control/contract/select/contractList','合同管理')">
	        				<img src="oa/images/icon_1.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>投资人管理</p>
	        			<a onclick="parent.addTab('control/investor/select/investorList','投资人管理')">
	        				<img src="oa/images/icon_19.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>年审管理</p>
	        			<a onclick="parent.addTab('control/avmc/select/annualVerificationList','年审管理')">
	        				<img src="oa/images/wqeqw_3.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆品牌管理</p>
	        			<a onclick="parent.addTab('control/brand/select/brandList','车辆品牌管理')">
	        				<img src="oa/images/icon_12.png"  />
	        			</a>
        			</li>
        			
        			<li>
        				<p>车辆厂商管理</p>
	        			<a onclick="parent.addTab('control/manufacturer/select/manufacturerList','车辆厂商管理')">
	        				<img src="oa/images/icon_22.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆系列管理</p>
	        			<a onclick="parent.addTab('control/series/select/seriesList','车辆系列管理')">
	        				<img src="oa/images/icon_13.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆型号管理</p>
	        			<a onclick="parent.addTab('control/model/select/modelList','车辆型号管理')">
	        				<img src="oa/images/icon_28.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆使用记录</p>
	        			<a onclick="parent.addTab('control/record/select/userRecordList','车辆使用记录')">
	        				<img src="oa/images/icon_34.png"  />
	        			</a>
        			</li>
        			<c:if
					test="${sessionScope.roles.indexOf('control/amc/select/adminloglist') > -1 || sessionScope.admin.account=='admin'}">
        			<li>
        				<p>操作日志管理</p>
	        			<a onclick="parent.addTab('control/amc/select/adminloglist','操作日志列表')">
	        				<img src="oa/images/uwerh_1.png"  />
	        			</a>
        			</li>
        			</c:if>
        			
        			<li>
        				<p>车辆投资记录</p>
	        			<a onclick="parent.addTab('control/investorCar/select/investorCarList','车辆投资记录')">
	        				<img src="oa/images/icon_9.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>职位管理</p>
	        			<a onclick="parent.addTab('control/position/select/positionList','职位管理')">
	        				<img src="oa/images/qwewq_4.png"  />
	        			</a>
        			</li>
        			
        			<li>
        				<p>租车项目管理</p>
	        			<a onclick="parent.addTab('control/rentalItem/select/rentalItemList','租车项目管理')">
	        				<img src="oa/images/icon_23.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>租车订单管理</p>
	        			<a onclick="parent.addTab('control/rentalOrder/select/rentalOrderList','租车订单管理')">
	        				<img src="oa/images/icon_25.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆库存管理</p>
	        			<a onclick="parent.addTab('control/setting/select/settingList','车辆库存管理')">
	        				<img src="oa/images/icon_21.png"  />
	        			</a>
        			</li>
        			<li>
        				<p>车辆结构管理</p>
	        			<a onclick="parent.addTab('control/structure/select/structureList','车辆结构管理')">
	        				<img src="oa/images/icon_10.png"  />
	        			</a>
        			</li>
              </ul>
             
               <ul>
             	 <li>
             	 	<p>GPS管理</p>
        			<a onclick="parent.addTab('control/gps/select/gpsList','GPS管理')">
        				<img src="oa/images/gps_1.png"  />
        			</a>
                 </li>
                 <li>
             	 	<p>违章管理</p>
        			<a onclick="parent.addTab('control/peccancy/select/peccancyList','违章管理')">
        				<img src="oa/images/gps_1.png"  />
        			</a>
                 </li>
                  <li>
             	 	<p>事故管理</p>
        			<a onclick="parent.addTab('control/accident/select/accidentList','事故管理')">
        				<img src="oa/images/ico_shigu.png" width="60" height="58" />
        			</a>s
                 </li>
                 <li>
             	 	<p>客户反馈</p>
        			<a onclick="parent.addTab('control/feedback/select/feedbackList','客户反馈')">
        				<img src="oa/images/ico_kehu.png" width="60" height="58" />
        			</a>
                 </li>
                 <li>
                 	<p>新闻资讯</p>
                 	<a onclick="parent.addTab('control/news/select/newsInformationList','新闻资讯')" title="新闻资讯">
                 		<span><img src="oa/images/ico_new.png" width="56" height="58" /></span>
                  	</a>
                 </li>
                 <li>
                 	<p>公司活动</p>
                 	<a onclick="parent.addTab('control/activity/select/companyActivityList','公司活动')" title="公司活动">
                 		<span><img src="oa/images/ico_huodong.png" width="56" height="58" /></span>
                  	</a>
                 </li>
                 <li>
                 	<p>出车补贴</p>
                 	<a onclick="parent.addTab('control/subsidy/select/subsidyList','出车补贴')">
                 		<span><img src="oa/images/ico_chuche.png" width="56" height="58" /></span>
                  	</a>
                 </li>
                 <li>
                 	<p>驾驶员</p>
                 	<a onclick="parent.addTab('control/driver/select/driverList','驾驶员')" title="驾驶员">
                 		<span><img src="oa/images/ico_jiashiyuan.png" width="60" height="58" /></span>
                  	</a>
                 </li>
                 <li>
                 	<p>用车申请</p>
                 	<a onclick="parent.addTab('control/applicationUseCar/select/applicationUseCarList','用车申请')" title="用车申请">
                 		<span><img src="oa/images/ico_yongche.png" width="60" height="58" /></span>
                  	</a>
                 </li>
                 <li>
                 	<p>帮助中心</p>
                 	<a onclick="parent.addTab('control/help/select/helpList','帮助中心')" title="帮助中心">
                 		<span><img src="oa/images/ico_bangzhu.png" width="60" height="58" /></span>
                  	</a>
                 </li>
               	<li>
                 	<p>联系邕桂</p>
                 	<a onclick="parent.addTab('control/about/select/aboutList','联系邕桂')" title="联系邕桂">
                 		<span><img src="oa/images/ico_gps.png" width="60" height="58" /></span>
                  	</a>
                 </li>
                 
                 <li>
                 	<p>公司简介</p>
                 	<a onclick="parent.addTab('companyProfile/select/companyProfileList','公司简介')" title="联系邕桂">
                 		<span><img src="oa/images/ico_contact.png"/></span>
                  	</a>
                 </li>
               	
              </ul>
            </div>
          </div>
         </div>
      </div>
       
     </div>
  </div>
   
   <div class="clear"></div>
   <!-- -->
   
 </div>
 </div>

 
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<title>帮助中心-公司简介</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
</head>

<body>

<div class="head-box">
  <div class="w1200 clearfix">
    <a class="head-logo" href="<%=basePath%>pc/index"><img src="<%=basePath%>images/head_logo.png"></a>
    <div class="nav-box">
      <ul>
      	<c:if test="${!empty sessionScope.investor  }">
      		<li><a onclick="out()">注销登录</a></li>
      		<li><a href="<%=basePath%>pc/investorPc/go_to/personCenter">个人中心</a></li>
      	</c:if>
        <c:if test="${ empty sessionScope.investor  }">
       		<li><a href="<%=basePath%>pc/investorPc/go_to/investorLogin">登录</a></li>
       	</c:if>
        <li><a href="<%=basePath%>pc/about">关于邕桂</a></li>
        <li><a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a></li>
        <li><a href="<%=basePath%>control/news/pc/newsInformationList?type=0">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li class="curr"><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->


<div class="wrap-box">
  <div class="head-bgbox" style="background-image:url(images/help.jpg)"></div>
  <div class="box w1200 clearfix">
	<div class="sidebar-box">
      <div class="sidebar">
        <h2>帮助中心</h2>
        <ul>
          <li class="on"><a href="<%=basePath%>pc/fingerpost?znid=1">租车指南</a></li>
          <li><a href="<%=basePath%>pc/flow?lcid=2">租车流程</a></li>
          <li><a href="<%=basePath%>pc/enterprise?zpid=3">企业租赁</a></li>
        </ul>
      </div>
    </div>
    <!--sidebarbox end-->
    <div class="main-box">
      <div class="position-box">
    	<h2>您现在在的位置：<a class="on" href="<%=basePath%>pc/index">首页</a> > <a class="on" href="<%=basePath%>pc/help">帮助中心</a> > <a>租车指南</a>
 	  </div>
      
      


      <div class="us-box">
        <!-- 广西邕桂汽车服务有限公司是南宁市运输管理处审批的正规汽车租赁公司，为股份合作制企业，成立于2012年，注册资金达300万元，是一家专注于汽车租赁、公司用车、商务活动、自驾游、企事业单位用车等业务的综合性汽车服务企业，总部设立于风景秀丽的广西首府南宁民族大道12号丽原·天际A座32楼A3207，公司热线：0771-2867380，旗下两家连锁分公司分布在桂平、平南两地，现有职工30人
        	公司自成立以来，以“开拓，求实，高效，守信”为宗旨，从车辆设施配备、车辆清洁、礼貌用语等各个环节加强管理，确保为客户提供优质服务。
        	我们为客户提供的车型多达20余种，现拥有各种类型高、中、低档车辆200多台，90%以上是新车，其中包括：奔驰、奥迪  、天籁、广本、丰田、别克、帕萨特、现代、桑塔纳、捷达等轿车、商务车和豪华大巴，车新价优，适应于各类不同客户的需求，给客户省心，放心，舒心的三大保证，以车新价优，节假日租车不加价，市内免费送车等优质服务为理念，极大地提高了公司的市场竞争能力.
        	公司凭借着较强的经济实力，不断创新的经营理念，诚实务实的经营作风，真诚的敬业精神，可靠的服务质量，完善的管理体制，赢得了众多的客户的赞许，是南宁市受广大客户好评的汽车租赁企业。 
        	资金的投入，系统的管理让我们的团队不断壮大，经过通过不断的努力与创新，现已逐步成为能够面向用户提全面、系统方案的汽车租赁服务供应商，是一个不断创新、能够适应千变万化的市场环境，具有强大竞争发展潜力的企业。不久的将来会在梧州、贵港、合山等地设立连锁分公司。
        <p><img src="images/us_img01.jpg"></p> -->
        <a>${helps.content}</a>
      </div>
        
      
      
      
    </div>
  </div>
</div>
      
      
      


<div class="footer-box">
  <div class="w1200">
    <div class="qr-codebox">
      <div class="cr-code"><img src="<%=basePath%>images/footer_qr_code.jpg"></div>
    </div>
    <div class="footer">
      <div class="firend-link">
        <a href="<%=basePath%>pc/index">首页</a>
        <a href="<%=basePath%>pc/about">公司介绍</a>
        <a href="<%=basePath%>pc/carList">车型展示</a>
        <a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a>
        <a id="retroaction" href="javascript:">客户反馈</a>
        <a href="<%=basePath%>pc/about">关于邕桂</a>
        <a href="<%=basePath%>pc/help">帮助中心</a>
      </div>
      <div class="subfooter">
        <p>
          <span>联系电话：(0771)2867380</span>
          <span>邮箱：123456789@qq.com</span>
          <span>地址：广西首府南宁民族大道12号丽原·天际A座32楼A3207</span>
        </p>
        <p>
          <span>广西邕桂汽车服务有限公司</span>
          <span>蜀ICP备14022404号</span>
        </p>
      </div>
    </div>

  </div>

</div>    
  <%@include file="/WEB-INF/views/include/fankui.jsp"%>
  
</body>
</html>
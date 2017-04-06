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
<title>活动详情</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
</head>
<script type="text/javascript">
	function out(){
		var queren = confirm("确认要注销登录吗?");
    	if(queren){
    		window.location.href="<%=basePath%>pc/investorPc/out";
    	}
	}
	
</script>
<body style="background:#f5f5f5;">

<div class="head-box">
  <div class="w1200 clearfix">
    <a class="head-logo" href="index.html"><img src="images/head_logo.png"></a>
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
        <li class="curr"><a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a></li>
        <li><a href="<%=basePath%>control/news/pc/newsInformationList?type=0">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->


<div class="wrap-box">
  <div class="w1200">
    <div class="position-box">
      <h2>您现在在的位置：<a href="#">首页</a> > <a href="#">公司活动</a> > <a class="on" href="#">活动详情</a></h2>
    </div>
  

    <div class="activity-wrap clearfix">
      <div class="activity-main fleft bgwhite">
        <!--<div class="act-title"><h2>活动介绍</h2></div>-->
        <div class="n-detail-title">
          <h3>${requestScope.notice.title }</h3>
          <p><em>活动日期：${requestScope.notice.addtime }</em></p>
        </div>
        
        <div class="act-cont">
          ${requestScope.notice.noticeContent }
        </div>
      
      </div><!--activity-main end-->
      <div class="detail-side fright">
        <div class="detail-side-info">
          <div class="tle"><h2>最新活动</h2></div>
          <c:forEach items="${requestScope.nList}" var="data" varStatus="status">
	        <c:choose>
				<c:when test="${status.index == 0}">
					<div class="pic">
			           <a href="<%=basePath%>control/activity/pc/companyActivityDetail?id=${data.id}">
			           	<img src="<%=basePath%>${data.picture}">
			           	<span>${data.title}</span>
			           </a>
			           
			        </div>
				</c:when>
				<c:otherwise>
					<div class="txt">
			           <ul>
			             <li><a href="<%=basePath%>control/activity/pc/companyActivityDetail?id=${data.id}">${data.title}</a></li>
			           </ul>
			        </div>
				</c:otherwise>
			</c:choose>
          </c:forEach>
        </div><!--detail-side-info end-->
      </div><!--detail-side end-->
    </div>
    
  </div>

</div>


      
      


<div class="footer-box">
  <div class="w1200">
    <div class="qr-codebox">
      <div class="cr-code"><img src="images/footer_qr_code.jpg"></div>
    </div>
    <div class="footer">
      <div class="firend-link">
        <a href="<%=basePath%>pc/index">首页</a>
        <a href="<%=basePath%>pc/about">公司介绍</a>
        <a href="<%=basePath%>pc/carList">车型展示</a>
        <a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a>
        <a id="retroaction" href="javascript:">客户反馈</a>
        <a href="<%=basePath%>pc/about">关于邕桂</a>
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
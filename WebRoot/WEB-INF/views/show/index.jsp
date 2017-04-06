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
<title>首页</title>
<link rel="stylesheet" href="<%=basePath%>css/style.css">
<script src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
<script src="<%=basePath%>js/jquery.SuperSlide.2.1.1.js"></script>
</head>
<script type="text/javascript">
	function out(){
		var queren = confirm("确认要注销登录吗?");
    	if(queren){
    		window.location.href="<%=basePath%>pc/investorPc/out";
    	}
	}

	function carTypeShow(carTypeName){
		$("#carTypeUl").html('');
		
		$.ajax({
			type:'post',
			url:'<%=basePath%>pc/getShowCarType',
			data: "theCarType="+carTypeName,
			dataType : 'json',
			success : function(data) {
				var str ="";
				if(data.length>0){
					$.each(data,function(i,n){
						str += "<li> <div class=\"subcar\"> <img src=\""+n.cgCarInfo.picture+"\"></div><h3><a>"
						+n.cgCarInfo.carType.carBrand.info+"</a></h3><div class=\"subcar-btn\"><a href=\"pc/itemDetail?itemID="
						+n.id+"\">立即预定</a></div></li>";
					});
					$("#carTypeUl").html(str);
					$("#tishioftype").html('');
				}else{
					$("#tishioftype").html(carTypeName+"无数据");
				}
				
		   }
		});
		
	}

</script>
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
        <li><a href="<%=basePath%>pc/about?aboutId=1">关于邕桂</a></li>
        <li><a href="<%=basePath%>control/activity/pc/companyActivity">公司活动</a></li>
        <li><a href="<%=basePath%>control/news/pc/newsInformationList?type=0">新闻资讯</a></li>
        <li><a href="<%=basePath%>pc/carList">车型展示</a></li>
        <li class="curr"><a href="<%=basePath%>pc/index">首页</a></li>
      </ul>
    </div>
  </div>
</div>
<!--head-box end-->

<div class="banner">
  <div class="banner-img">
    <ul>
      <li style="background-image:url(images/banner01.jpg);"><a href="<%=basePath%>pc/index"></a></li>
      <li style="background-image:url(images/banner01.jpg);"><a href="<%=basePath%>pc/index"></a></li>
      <li style="background-image:url(images/banner01.jpg);"><a href="<%=basePath%>pc/index"></a></li>
    </ul>
  </div>
  <div class="banner-btnbox hd">
    <ul>
      <li class="on">1</li>
      <li>2</li>
      <li>3</li>
    </ul>
  </div>
  <span class="banner-btn prev">1</span>
  <span class="banner-btn next">2</span>
</div><!--banner end-->
<script>
$(".banner").slide({ mainCell:".banner-img ul",autoPlay:true,triggerTime:0 });
</script>

<div class="business-box">
  <div class="subbusiness w1200 clearfix">
    <ul>
      <li>
        <a href="<%=basePath%>pc/help?helpId=4">
          <i class="icon"><img src="<%=basePath%>images/business_img01.png"></i>
          <p>长期租赁</p>
        </a>
      </li>
      <li>
        <a href="<%=basePath%>pc/help?helpId=5">
          <i class="icon"><img src="<%=basePath%>images/business_img02.png"></i>
          <p>短期租赁</p>
        </a>
      </li>
      <li>
        <a href="<%=basePath%>pc/help?helpId=3">
          <i class="icon"><img src="<%=basePath%>images/business_img03.png"></i>
          <p>企业租赁</p>
        </a>
      </li>
    </ul>
  </div>
</div>

  
  <div class="w-box">
    <div class="w1200 pb40">
      <div class="title-box">
        <h2>公司简介</h2>
        <h3>${data.title}</h3>
      </div>
	  <div class="intro-box clearfix">
        <div class="intro-img"><img src="<%=basePath%>images/intro_img.jpg"></div>
        <div class="intro-info">
          <h2>公司简介</h2>
          <div class="intro-text">
             ${data.content}
          </div>
          <div class="intro-btn">
            <a class="intro-link" href="<%=basePath%>pc/about">查看详情</a>
          </div>
        </div>
      </div>
      
    </div> 
  </div><!--w-box end-->
  
  
  <!-----------------2016新增html---------------------->
  <div class="w-box bggray">
    <div class="w1200 pb40">
      
      <div class="title-box">
        <h2>车型展示</h2>
        <div class="car-classify" id="carClassify">
        
		          <%--<c:if test="${requestScope.carType!=null}">
							
								<a href="<%=basePath%>pc/index">全部</a> 
							</c:if>
							<c:if test="${requestScope.carType==null}">
							
								<a class="on" href="<%=basePath%>pc/index">全部</a> 
						</c:if>--%>
						
						<a onclick="carTypeShow('all')">全部</a>
						
						<c:forEach items="${requestScope.structure}" var="data" varStatus="status">
							<%--<c:if test="${requestScope.carType==data.name}">
								<a class="on" href="<%=basePath%>pc/index?carType=${data.name}">${data.name}</a> 
							</c:if>
							<c:if test="${requestScope.carType!=data.name}">
								<a href="<%=basePath%>pc/index?carType=${data.name}">${data.name}</a> 
							</c:if>
						--%>
							<a onclick="carTypeShow('${data.name}')">${data.name}</a>
						</c:forEach>
		          
        </div>
      </div>
      <div style="text-align:center" id="tishioftype">
      </div>
      <div class="carbox" id="carBox" style="text-align:center">
        <ul class="clearfix" id="carTypeUl">
	        
	          <c:forEach items="${requestScope.pageUtil}" var="data"
								varStatus="status">
								
					<li>
	            <div class="subcar"><img src="${data.cgCarInfo.picture}"></div>
	            <h3><a>${data.cgCarInfo.carType.carBrand.info}</a></h3>
	            <div class="subcar-btn">
	              <a href="pc/itemDetail?itemID=${data.id}">立即预定</a>
	            </div>
	          </li>
								
			</c:forEach>
         
        </ul>
      </div>
    </div>
    <div class="btn-box">
        <a class="link" href="<%=basePath%>pc/carList">查看更多</a>
      </div>
  </div>
  <script>
  $(function(){
	 $("#carClassify a").click(function(){
		var thisIndex=$(this).index();
		$(this).addClass("on").siblings().removeClass("on");
		$("#carBox ul").eq(thisIndex).show().siblings().hide();
	 })
  })
  </script>
  
  <!-----------------2016新增html  end---------------------->

  
  <div class="w-box bggray">
    <div class="w1200 pb40">
      
      <div class="title-box">
        <h2>活动</h2>
        <h3>第一时间和你分享最有用的活动</h3>
      </div>
      
      
      <div class="w-cont w-activity clearfix">
        <%-- <c:forEach items="${requestScope.activityList }" var="data" varStatus="status">
        	<c:choose>
        		<c:when test="${status.index == 0}">
        			<div class="leftbox">
        				<a href="<%=basePath%>control/activity/pc/companyActivityDetail?id=${data.id}">
        					<img src="<%=basePath%>${data.picture}">
        					<span class="act-name">${data.title}</span>
        				</a>
        			</div>
        		</c:when>
        		<c:otherwise>
        			<div class="rightbox clearfix">
        				<ul>
        					
        					<li>
        						<a href="<%=basePath%>control/activity/pc/companyActivityDetail?id=${data.id}">
        							<img src="<%=basePath%>${data.picture}">
        							<span class="act-name">${data.title}</span>
        						</a>
        					</li>
        				</ul>
        			</div>
        		</c:otherwise>
        	</c:choose>
        </c:forEach> --%>
      	<div class="leftbox">
      		<c:forEach items="${requestScope.activityList }" var="data" varStatus="status">
      			<c:if test="${status.index == 0}">
      				<a href="<%=basePath%>control/activity/pc/companyActivityDetail?id=${data.id}">
						<img src="<%=basePath%>${data.picture}" style="width: 600px;height: 400px;">
						<span class="act-name">${data.title}</span>
					</a>
      			</c:if>
      		</c:forEach>
 		</div>
 		<div class="rightbox clearfix">
     		<ul>
     			<c:forEach items="${requestScope.activityList }" var="data2" varStatus="status2">
     				<c:if test="${status2.index gt 0}">
	     				<li>
		     				<a href="<%=basePath%>control/activity/pc/companyActivityDetail?id=${data2.id}">
		     					<img src="<%=basePath%>${data2.picture}">
		     					<span class="act-name">${data2.title}</span>
		     				</a>
		     			</li>
		     		</c:if>
     			</c:forEach>	
     		</ul>
     	</div>
      </div>
      <div class="btn-box">
        <a class="link" href="<%=basePath%>control/activity/pc/companyActivity">查看更多</a>
      </div>
    </div> 
  </div><!--w-box end-->
  
  <div class="w-box bggray">
    <div class="w1200 pb40">
      
      <div class="title-box">
        <h2>新闻资讯</h2>
        <h3>第一时间和你分享最有用的资源</h3>
      </div>
      
      <div class="w-cont clearfix">
        <div class="w-news-box fleft">
          <div class="w-news-title">
            <h2>公司新闻</h2>
            <a class="more" href="<%=basePath%>control/news/pc/newsInformationList?type=0">更多>></a>
          </div>
          <div class="w-news-pic">
          	<a href="<%=basePath%>control/news/pc/newsInformationDetail?id=${requestScope.companyNewsList[0].id}&type=0">
          		<img src="<%=basePath%>${requestScope.companyNewsList[0].picture}" style="width: 600px;height: 277px;">
          		<span>${requestScope.companyNewsList[0].title}</span>
          	</a>
          </div>
          <div class="w-news-list">
            <ul>
              <li>
                <a href="<%=basePath%>control/news/pc/newsInformationDetail?id=${requestScope.companyNewsList[1].id}&type=0">
                  <h3>${requestScope.companyNewsList[1].title}</h3>
                  <div class="new-text">${requestScope.companyNewsList[1].noticeContentBrief}</div>
                  <p class="time">${requestScope.companyNewsList[1].addtime}</p>
                </a>
              </li>
              <li>
                <a href="<%=basePath%>control/news/pc/newsInformationDetail?id=${requestScope.companyNewsList[2].id}&type=0">
                  <h3>${requestScope.companyNewsList[2].title}</h3>
                  <div class="new-text">${requestScope.companyNewsList[2].noticeContentBrief}</div>
                  <p class="time">${requestScope.companyNewsList[2].addtime}</p>
                </a>
              </li>
            </ul>
          </div>
        </div><!--w-news-box end-->
        <div class="w-news-box fright">
          <div class="w-news-title">
            <h2>行业资讯</h2>
            <a class="more" href="<%=basePath%>control/news/pc/newsInformationList?type=1">更多>></a>
          </div>
          <div class="w-news-pic">
          	<a href="<%=basePath%>control/news/pc/newsInformationDetail?id=${requestScope.industryNewsList[0].id}&type=1">
          		<img src="<%=basePath%>${requestScope.industryNewsList[0].picture}" style="width: 600px;height: 277px;">
          		<span>${requestScope.industryNewsList[0].title}</span>
          	</a>
          </div>
          <div class="w-news-list">
            <ul>
              <li>
                <a href="<%=basePath%>control/news/pc/newsInformationDetail?id=${requestScope.industryNewsList[1].id}&type=1">
                  <h3>${requestScope.industryNewsList[1].title }</h3>
                  <div class="new-text">${requestScope.industryNewsList[1].noticeContentBrief }</div>
                  <p class="time">${requestScope.industryNewsList[1].addtime }</p>
                </a>
              </li>
              <li>
                <a href="<%=basePath%>control/news/pc/newsInformationDetail?id=${requestScope.industryNewsList[2].id}&type=1">
                  <h3>${requestScope.industryNewsList[2].title }</h3>
                  <div class="new-text">${requestScope.industryNewsList[2].noticeContentBrief }</div>
                  <p class="time">${requestScope.industryNewsList[2].addtime }</p>
                </a>
              </li>
            </ul>
          </div>
        </div>  
      </div>
      
    </div> 
  </div><!--w-box end-->
  
  
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
        <a href="<%=basePath%>pc/help?helpId=1">帮助中心</a>
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
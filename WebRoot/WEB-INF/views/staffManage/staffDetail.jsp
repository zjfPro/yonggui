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
<title>G.net综合信息服务管理平台</title>
<link rel="stylesheet" href="oa/css/global.css" type="text/css" />
<link rel="stylesheet" href="oa/css/list.css" type="text/css" />
<script src="oa/js/jquery-1.8.0.min.js"></script>
<script src="oa/js/fixPNG.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<!-- zTreeStyle专用 start-->
<link rel="stylesheet" href="js/zTree_v3-master/css/demo.css" type="text/css">
<link rel="stylesheet" href="js/zTree_v3-master/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/zTree_v3-master/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/zTree_v3-master/js/jquery.ztree.excheck.js"></script>
<!-- zTreeStyle专用 end-->
<!--[if lt IE 7]>       
 <script src="js/fixPNG.js"></script>         
 <script>
 DD_belatedPNG.fix('img,.nav ul li a,.nav ul li a:hover');
 </script>     
<![endif]-->


<script type="text/javascript">
	

//ajax上传图片 start-------------------------------------------------------------------------------------------------------
jQuery.extend({


  createUploadIframe: function(id, uri)
  {
      //create frame
      var frameId = 'jUploadFrame' + id;

      if(window.ActiveXObject) {
          var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
          if(typeof uri== 'boolean'){
              io.src = 'javascript:false';
          }
          else if(typeof uri== 'string'){
              io.src = uri;
          }
      }
      else {
          var io = document.createElement('iframe');
          io.id = frameId;
          io.name = frameId;
      }
      io.style.position = 'absolute';
      io.style.top = '-1000px';
      io.style.left = '-1000px';

      document.body.appendChild(io);

      return io
  },
  createUploadForm: function(id, fileElementId)
  {
      //create form
      var formId = 'jUploadForm' + id;
      var fileId = 'jUploadFile' + id;
      var form = $('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');
      var oldElement = $('#' + fileElementId);
      var newElement = $(oldElement).clone();
      $(oldElement).attr('id', fileId);
      $(oldElement).before(newElement);
      $(oldElement).appendTo(form);
      //set attributes
      $(form).css('position', 'absolute');
      $(form).css('top', '-1200px');
      $(form).css('left', '-1200px');
      $(form).appendTo('body');
      return form;
  },
  addOtherRequestsToForm: function(form,data)
  {
      // add extra parameter
      var originalElement = $('<input type="hidden" name="" value="">');
      for (var key in data) {
          name = key;
          value = data[key];
          var cloneElement = originalElement.clone();
          cloneElement.attr({'name':name,'value':value});
          $(cloneElement).appendTo(form);
      }
      return form;
  },

  ajaxFileUpload: function(s) {
      // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout
      s = jQuery.extend({}, jQuery.ajaxSettings, s);
      var id = new Date().getTime()
      var form = jQuery.createUploadForm(id, s.fileElementId);
      if ( s.data ) form = jQuery.addOtherRequestsToForm(form,s.data);
      var io = jQuery.createUploadIframe(id, s.secureuri);
      var frameId = 'jUploadFrame' + id;
      var formId = 'jUploadForm' + id;
      // Watch for a new set of requests
      if ( s.global && ! jQuery.active++ )
      {
          jQuery.event.trigger( "ajaxStart" );
      }
      var requestDone = false;
      // Create the request object
      var xml = {}
      if ( s.global )
          jQuery.event.trigger("ajaxSend", [xml, s]);
      // Wait for a response to come back
      var uploadCallback = function(isTimeout)
      {
          var io = document.getElementById(frameId);
          try
          {
              if(io.contentWindow)
              {
                  xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                  xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;

              }else if(io.contentDocument)
              {
                  xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                  xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
              }
          }catch(e)
          {
              jQuery.handleError(s, xml, null, e);
          }
          if ( xml || isTimeout == "timeout")
          {
              requestDone = true;
              var status;
              try {
                  status = isTimeout != "timeout" ? "success" : "error";
                  // Make sure that the request was successful or notmodified
                  if ( status != "error" )
                  {
                      // process the data (runs the xml through httpData regardless of callback)
                      var data = jQuery.uploadHttpData( xml, s.dataType );
                      // If a local callback was specified, fire it and pass it the data
                      if ( s.success )
                          s.success( data, status );

                      // Fire the global callback
                      if( s.global )
                          jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                  } else
                      jQuery.handleError(s, xml, status);
              } catch(e)
              {
                  status = "error";
                  jQuery.handleError(s, xml, status, e);
              }

              // The request was completed
              if( s.global )
                  jQuery.event.trigger( "ajaxComplete", [xml, s] );

              // Handle the global AJAX counter
              if ( s.global && ! --jQuery.active )
                  jQuery.event.trigger( "ajaxStop" );

              // Process result
              if ( s.complete )
                  s.complete(xml, status);

              jQuery(io).unbind()

              setTimeout(function()
              {	try
                  {
                      $(io).remove();
                      $(form).remove();

                  } catch(e)
                  {
                      jQuery.handleError(s, xml, null, e);
                  }

              }, 100)

              xml = null

          }
      }
      // Timeout checker
      if ( s.timeout > 0 )
      {
          setTimeout(function(){
              // Check to see if the request is still happening
              if( !requestDone ) uploadCallback( "timeout" );
          }, s.timeout);
      }
      try
      {
          // var io = $('#' + frameId);
          var form = $('#' + formId);
          $(form).attr('action', s.url);
          $(form).attr('method', 'POST');
          $(form).attr('target', frameId);
          if(form.encoding)
          {
              form.encoding = 'multipart/form-data';
          }
          else
          {
              form.enctype = 'multipart/form-data';
          }
          $(form).submit();

      } catch(e)
      {
          jQuery.handleError(s, xml, null, e);
      }
      if(window.attachEvent){
          document.getElementById(frameId).attachEvent('onload', uploadCallback);
      }
      else{
          document.getElementById(frameId).addEventListener('load', uploadCallback, false);
      }
      return {abort: function () {}};

  },

  uploadHttpData: function( r, type ) {
      var data = !type;
      data = type == "xml" || data ? r.responseXML : r.responseText;
      // If the type is "script", eval it in global context
      if ( type == "script" )
          jQuery.globalEval( data );
      // Get the JavaScript object, if JSON is used.
      if ( type == "json" )
      {
          // If you add mimetype in your response,
          // you have to delete the '<pre></pre>' tag.
          // The pre tag in Chrome has attribute, so have to use regex to remove
          var data = r.responseText;
          var rx = new RegExp("<pre.*?>(.*?)</pre>","i");
          var am = rx.exec(data);
          //this is the desired data extracted
          var data = (am) ? am[1] : "";    //the only submatch or empty
          eval( "data = " + data );
      }
      // evaluate scripts within html
      if ( type == "html" )
          jQuery("<div>").html(data).evalScripts();
      //alert($('param', data).each(function(){alert($(this).attr('value'));}));
      return data;
  }
})
//ajax上传图片 end-------------------------------------------------------------------------------------------------------

//上传  start--------------------------------------------------------------------------------------------------------------
	//图片上传预览    IE是用了滤镜。
	function previewImage(file, div) {
		var MAXWIDTH = 150;
		var MAXHEIGHT = 150;
		/* var div = document.getElementById('preview'); */
		if (file.files && file.files[0]) {
			div.innerHTML = '<img id=imghead width=150 height=150>';
			var img = document.getElementById('imghead');
			img.onload = function() {
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
						img.offsetWidth, img.offsetHeight);
				img.style.width = '100px';
				img.style.height = '100px';
				//             img.style.marginLeft = rect.left+'px';
				img.style.marginTop = '0px';
			}
			var reader = new FileReader();
			reader.onload = function(evt) {
				img.src = evt.target.result;
			}
			reader.readAsDataURL(file.files[0]);
		} else //兼容IE
		{
			var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
			file.select();
			var src = document.selection.createRange().text;
			div.innerHTML = '<img id=imghead>';
			var img = document.getElementById('imghead');
			img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
					img.offsetHeight);
			status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width
					+ ',' + rect.height);
			div.innerHTML = "<div id=divhead style='width:100px;height:100px;margin-top:0px;"+sFilter+src+"\"'></div>";
		}
	}
	function clacImgZoomParam(maxWidth, maxHeight, width, height) {
		var param = {
			top : 0,
			left : 0,
			width : width,
			height : height
		};
		if (width > maxWidth || height > maxHeight) {
			rateWidth = width / maxWidth;
			rateHeight = height / maxHeight;

			if (rateWidth > rateHeight) {
				param.width = maxWidth;
				param.height = Math.round(height / rateWidth);
			} else {
				param.width = Math.round(width / rateHeight);
				param.height = maxHeight;
			}
		}

		param.left = Math.round((maxWidth - param.width) / 2);
		param.top = Math.round((maxHeight - param.height) / 2);
		return param;
	}
	//调用预览
	function up_img(file) {
		var div = document.getElementById('display_img');
		previewImage(file, div);
	}
//上传  end--------------------------------------------------------------------------------------------------------------	

	
	function cleaninput(id){
		$("#"+id).val("");
	}
	
	function cleanPage(){
    	window.location.href="<%=basePath%>control/staffManager/select/staffList";
	}
	
	function checkForm(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = "";
		$.each(nodes,function(i,n){
			if(!n.isParent){
				ids+=n.id+",";
			}
		});
		$('#models').val(''+ids);
		
		var formval = $("#form").serialize();
		
		//ajax上传图片和form数据
		$.ajaxFileUpload
        ( 
            {
        		url: "<%=basePath%>control/staffManager/update/gotoUpdateStaff?"+formval, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'logo', //文件上传域的ID
                data: formval,
                dataType:"text",
                type: 'POST',
                success: function (data)  //服务器成功响应处理函数
                {
                	if(data == "1"){
    			 		var queren = confirm("修改成功,是否返回员工列表?");
    			 		if(queren){
    			 			window.location.href="<%=basePath%>control/staffManager/select/staffList";
    			 		}
    				}else{
    					alert(data);
    				}
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    alert(e);
                }
            }
        )
	}
	
	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "fatId"
				},
				key: {
					name: "text"
				}
			}
			
		};

		$(document).ready(function(){
			$.ajax({
				type:'post',
				url:'<%=basePath%>control/authority/select/getAllModel',
				dataType : 'json',
				success : function(data) {
					$.fn.zTree.init($("#treeDemo"), setting,data);
					
					//全展开
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					treeObj.expandAll(true);
					getModelOfStaff();
				}
			});
			
		});
		
		//获取员工对应的权限,并回显
		function getModelOfStaff(){
			var theStaffID = $("#theStaffID").val();
			
			$.ajax({
				type:'post',
				url:'<%=basePath%>control/staffManager/select/getModelOfStaff',
				dataType:'json',
				data:{"theStaffID":theStaffID},
				success:function(data){
					if(data.mapingsofStaff!=null){
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						$.each(data.mapingsofStaff,function(i,n){
						
							var node = treeObj.getNodeByParam("id", n.staffManageModelId, null);
							treeObj.checkNode(node,true,true);
						});
					}else{
						alert(data);
					}
				}
			});
		}
		
		$(document).ready(function(){
			
			var roleName = $("#roleName").val();
			$.ajax({
				type:'post',
				url:'<%=basePath%>control/staffManager/select/getAllRoleContainer',
				dataType : 'json',
				success : function(data) {
					if(data.csmrcList!=null){
						var str = "<option value=''>请选择角色...</option>";
						$.each(data.csmrcList,function(i,n){
							
							if(n.name==roleName){
								str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
							}else{
								str += "<option value="+n.id+">"+n.name+"</option>";
							}
						});
						$("#stafModel").html(str);
						
					}else{
						alert(data);
					}
					
				}
			});
			
		});
	
	$(document).ready(function(){
			
			var mendian = $("#mendianID").val();
			$.ajax({
				type:'post',
				url:'<%=basePath%>ajax/select/getAllShopFront',
				dataType : 'json',
				success : function(data) {
					if(data.length>0){
						var str = "<option value=''>请选择门店...</option>";
						$.each(data,function(i,n){
							if(n.id==mendian){
								str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
							}else{
								str += "<option value="+n.id+">"+n.name+"</option>";
							}
							
						});
						$("#shopFrontId").html(str);
						
					}else{
						alert(data.errInfo);
					}
					
				}
			});
			
	});
	
	$(document).ready(function(){
	
			var bumen = $("#bumenID").val();
			$.ajax({
				type:'post',
				url:'<%=basePath%>ajax/select/getAllCgDept',
				dataType : 'json',
				success : function(data) {
					if(data.length>0){
						var str = "<option value=''>请选择部门...</option>";
						$.each(data,function(i,n){
							if(n.id==bumen){
								str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
							}else{
								str += "<option value="+n.id+">"+n.name+"</option>";
							}
						});
						$("#decpId").html(str);
						
					}else{
						alert(data.errInfo);
					}
					
				}
			});
			
	});
	
	$(document).ready(function(){
		var zhiwei = $("#zhiweiID").val();
			$.ajax({
				type:'post',
				url:'<%=basePath%>ajax/select/getStaffPosition',
				dataType : 'json',
				success : function(data) {
					if(data.length>0){
						var str = "<option value=''>请选择职位...</option>";
						$.each(data,function(i,n){
							if(n.id==zhiwei){
								str += "<option selected='selected' value="+n.id+">"+n.name+"</option>";
							}else{
								str += "<option value="+n.id+">"+n.name+"</option>";
							}
						});
						$("#staffPositionId").html(str);
					}else{
						alert(data.errInfo);
					}
					
				}
			});
			
	});

	
	function checkTree(optionValue){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var obj = document.getElementById("stafModel");
		var txt = obj.options[obj.selectedIndex].text;
		$('#roleName').val(''+txt);
		if(optionValue==''){
			treeObj.checkAllNodes(false);
			return;
		}
		var csmrcID = optionValue;
			$.ajax({
			type:'post',
			url:'<%=basePath%>control/staffManager/select/getModelOfRole',
			dataType:'json',
			data:{"csmrcID":csmrcID},
			success:function(data){
				if(data.mapings!=null){
					treeObj.checkAllNodes(false);
					$.each(data.mapings,function(i,n){
						var node = treeObj.getNodeByParam("id", n.staffManageModelid, null);
						treeObj.checkNode(node,true,true);
					});
				}else{
					alert(data);
				}
			}
		});
	}
		

	
	function isSelect(sel){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		if(sel=='yes'){
			treeObj.checkAllNodes(true);
		}else{
			treeObj.checkAllNodes(false);
		}
	}
	function isOpen(sel){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		if(sel=='yes'){
			treeObj.expandAll(true);
		}else{
			treeObj.expandAll(false);
		}
	}
	
</script>
</head>

<body>
	<!-- content -->
	<div class="content">
		<div class="content_box">
			<div class="current">
				<a>个人桌面</a><span> > </span><a href="<%=basePath%>control/staffManager/select/staffList">员工列表</a><span>></span>修改员工
			</div>
			<div class="infolist">
				<form name="form" id="form" method="post" enctype="multipart/form-data">
					<input type="hidden"  name="theStaffID" id="theStaffID" value="${cgStaffDetail.id }"/>
					<table class="left_table">
						
						<tr class="line_g">
							<td width="10%">相片：</td>
							<td>
								<div style="float:left">
									<input type="file" name="file" id="logo"
										onchange="up_img(this)" />
								</div>
								<div id="display_img"
									style="width: 100px;height: 100px;border:1px solid #000;overflow:hidden;">
									<img id="imghead" style="width: 100px;height: 100px;"
										src="${cgStaffDetail.logo }" />
								</div>  
							</td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">姓名：</td>
							<td><input type="text" class="text width300" name="name" id="name" value="${cgStaffDetail.name }"/><a onclick="cleaninput('name')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">身份证：</td>
							<td><input type="text" class="text width300" name="idcard" id="idcard" value="${cgStaffDetail.idcard }"/><a onclick="cleaninput('idcard')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">编号：</td>
							<td><input type="text" class="text width300" name="number" id="number" value="${cgStaffDetail.number }"/><a onclick="cleaninput('number')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">职位：</td>
							<td>
								<input type="hidden" name="zhiweiID" id="zhiweiID" value="${cgStaffDetail.staffPositionId }"/>
							<select name="staffPositionId" id="staffPositionId" style="width: 200px;"></select>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">部门：</td>
							<td>
								<input type="hidden" name="bumenID" id="bumenID" value="${cgStaffDetail.decpId }"/>
								<select name="decpId" id="decpId" style="width: 200px;"></select>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">所属门店：</td>
							<td>
							<input type="hidden" name="mendianID" id="mendianID" value="${cgStaffDetail.shopFrontId }"/>
							<select name="shopFrontId" id="shopFrontId" style="width: 200px;"></select>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">账号：</td>
							<td><input type="text" class="text width300" name="account" id="account" value="${cgStaffDetail.account }"/><a onclick="cleaninput('account')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">密码：</td>
							<td><input type="password" class="text width300" name="password" id="password" /> 
							<a onclick="cleaninput('password')">清空</a> &nbsp;&nbsp;
							注：如果需要修改密码请直接输入，将会覆盖原密码
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">入职时间：</td>
							<td><input type="text" id="theEntryTime" name="theEntryTime" class="Wdate width300"
							value="<fmt:formatDate value="${cgStaffDetail.entryTime}" pattern="yyyy-MM-dd"/>"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><a onclick="cleaninput('theEntryTime')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td width="10%">出生年月：</td>
							<td><input type="text" id="theBirthDate" name="theBirthDate" class="Wdate width300"
							value="<fmt:formatDate value="${cgStaffDetail.birthDate}" pattern="yyyy-MM-dd"/>"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><a onclick="cleaninput('theBirthDate')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">联系电话：</td>
							<td><input type="text" class="text width300" name="phone" id="phone" value="${cgStaffDetail.phone }"/><a onclick="cleaninput('phone')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">备用电话：</td>
							<td><input type="text" class="text width300" name="sparePhone" id="sparePhone" value="${cgStaffDetail.sparePhone }"/><a onclick="cleaninput('sparePhone')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">住址：</td>
							<td><input type="text" class="text width300" name="address" id="address" value="${cgStaffDetail.sparePhone }"/><a onclick="cleaninput('address')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">离职时间：</td>
							<td><input type="text" id="theQuitTime" name="theQuitTime" class="Wdate width300"
							value="<fmt:formatDate value="${cgStaffDetail.quitTime}" pattern="yyyy-MM-dd"/>"
              					 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"><a onclick="cleaninput('theQuitTime')">清空</a></td>
						</tr>
						<tr class="line_g">
							<td width="10%">赋予权限：</td>
							<!-- <td>
								<input type="text" class="text width300" name="roleName" id="roleName"/>
								<a onclick="cleaninput('roleName')">清空</a>
							</td> -->
							<td>
								<a onclick="isSelect('yes')">全选</a>&nbsp;&nbsp;&nbsp;
								<a onclick="isSelect('no')">不选</a>&nbsp;&nbsp;&nbsp;
								<a onclick="isOpen('yes')">全展开</a>&nbsp;&nbsp;&nbsp;
								<a onclick="isOpen('no')">全关闭</a><br/>
								请选择角色:<select name="stafModel" id="stafModel" style="width: 200px;" onchange="checkTree(this.value);">
										
								</select>
								&nbsp;
								角色名<input name="roleName" id="roleName" value="${cgStaffDetail.roleName }"/> <input name="models" id="models" type="hidden"/>
								<div>
									<ul id="treeDemo" class="ztree"></ul>
								</div>
							</td>
						</tr>
						<tr class="line_g">
							<td width="10%">状态：</td>
							<td>
							    <select name="status" id="status" style="width: 200px;" class="text width300">
							    
										<option <c:if test="${cgStaffDetail.status==0 }">selected='selected'</c:if> value="0">未启用</option>
										<option <c:if test="${cgStaffDetail.status==1 }">selected='selected'</c:if> value="1">启用</option>
										<option <c:if test="${cgStaffDetail.status==-1 }">selected='selected'</c:if> value="-1">禁用</option>
										<option <c:if test="${cgStaffDetail.status==-2 }">selected='selected'</c:if> value="-2">离职</option>
							    </select>
							</td>
						</tr>
						
						
						<tr class="line_g">
							<td width="10%">备注：</td>
							<td><input type="text" class="text width300" name="remarks" id="remarks" value="${cgStaffDetail.remarks }"/><a onclick="cleaninput('remarks')">清空</a></td>
						</tr>
						
						<tr class="line_g">
							<td colspan="2">
								<input type="button" value="保存" class="btn" onclick="checkForm()"/> 
								<input type="button" value="取消" class="btn" onclick="cleanPage();"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!-----客户反馈 弹窗-------->
<form action="" method="post" id="fankuiForm">
<div class="maskbox" id="maskFB"></div>
<div class="attendbox" id="feedbackBox">
  <div class="attend">
    <span class="close-att" id="closeFB"></span>
    <div class="att-head">
      <h2>请填写当前信息</h2>
    </div>
    <div class="att-body">
      <dl>
        <dt>姓名：</dt>
        <dd><input id="userName" name="userName" class="att-text" type="text" placeholder="请输入您的真实姓名"><span class="att-hidden" id="uNPrompt"></span></dd>
      </dl>
      <dl>
        <dt>联系方式：</dt>
        <dd><input id="userPhone" name="userPhone" class="att-text" type="text" placeholder="请输入您的联系方式"><span class="att-hidden" id="uPPrompt"></span></dd>
      </dl>
      <dl>
        <dt>QQ：</dt>
        <dd><input id="userQQ" name="userQQ" class="att-text" type="text" placeholder="请输入您的QQ号"><span class="att-hidden" id="qqPrompt"></span></dd>
      </dl>


      <dl>
        <dt>反馈内容</dt>
        <dd><textarea id="userFB" name="userFB" class="att-area att-area-s"></textarea><span class="att-hidden" id="fbPrompt"></span></dd>
      </dl>
      <dl>
        <dt></dt>
        <dd><input id="FBBtn" class="att-btn" type="button" value="提交"></dd>
      </dl>
    </div>
  
  </div>
</div>
</form>

<!-----客户反馈 弹窗  End-------->
<!-----客户反馈 弹窗  js-------->
<script>
$(function(){
	$("#retroaction").click(function(){
		$("#maskFB").show();
		$("#feedbackBox").show();
	})
	$("#closeFB").click(function(){
		$("#maskFB").hide();
		$("#feedbackBox").hide();
	})
	$("#FBBtn").click(function(){
		facebookVerify();
	
	})
	
})
function facebookVerify(){
	var userName=$("#userName");
	var uNPrompt=$("#uNPrompt");
	var userPhone=$("#userPhone");
	var uPPrompt=$("#uPPrompt");
	var userQQ=$("#userQQ");
	var qqPrompt=$("#qqPrompt");
	var userFB=$("#userFB");
	var fbPrompt=$("#fbPrompt");
	
	if(userName.val()=="" || userName.val()==null){
		uNPrompt.html("请输入姓名");
		userName.focus();
		return;
	}
	uNPrompt.html("");
	
	
	if(userPhone.val()=="" || userPhone.val()==null){
		uPPrompt.html("请输入您的手机号/电话");
		userPhone.focus();
		return;
	}
	if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(userPhone.val())){
		uPPrompt.html("手机号或电话号码格式有误，请重新输入");
		userPhone.focus();
		return;
	}
	uPPrompt.html("");
	
	if(userFB.val()=="" || userFB.val()==null){
		fbPrompt.html("反馈内容不能为空！");
		userFB.focus();
		return;
	}
	fbPrompt.html("");
	
	$("#maskFB").hide();
	$("#feedbackBox").hide();
	
	$.ajax({
		  async: false,
		  type: "POST",
		  data: $("#fankuiForm").serialize(),
		  url: "pc/addFankui",
		  success: function(data){
		 	if(data == "1"){
		 		alert("反馈成功!");
			}else{
				alert(data);
			}
		}
	});
	
}
</script>
<!-----客户反馈 弹窗  js  end-------->
// JavaScript Document  
  
//检测手机号是否正确  
jQuery.validator.addMethod("isMobile", function(value, element) {  
    var length = value.length;  
    var regPhone = /^1([3578]\d|4[57])\d{8}$/;  
    return this.optional(element) || ( length == 11 && regPhone.test( value ) );    
}, "请正确填写您的手机号码");   
  
//检测用户姓名是否为汉字  
jQuery.validator.addMethod("isChar", function(value, element) {  
    var length = value.length;  
    var regName = /[^\u4e00-\u9fa5]/g;  
    return this.optional(element) || !regName.test( value );    
}, "请正确格式的姓名(暂支持汉字)");  
  
//檢測邮政编码  
jQuery.validator.addMethod("isZipCode", function(value, element) {    
    var tel = /^[0-9]{6}$/;  
    return this.optional(element) || (tel.test(value));  
}, "请正确填写您的邮政编码");  

//验证是否为正整数
jQuery.validator.addMethod("isZhenNum",function(value, element){
	var num = /^[0-9]*[1-9][0-9]*$/;
	return this.optional(element) || (num.test(value)); 
},"数字不是正整数");
//验证人名币格式

jQuery.validator.addMethod("isRMB",function(value, element){
	var reg=/^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	return this.optional(element) || (reg.test(value)); 
},"请输入正确金额");


jQuery.validator.addMethod("isValue",function(value, element){
	var reg=/^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	return this.optional(element) || (reg.test(value)); 
},"请输入正确数字");

//验证小数点
jQuery.validator.addMethod("isTax",function(value, element){
	var reg=/^[1-9]{1}\d*(\.\d{1,5})?$/;
	return this.optional(element) || (reg.test(value)); 
},"请输入正确税率");
//验证密码
jQuery.validator.addMethod("checkPwd",function(value, element){
	var reg=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
	return this.optional(element) || (reg.test(value)); 
},"密码必须由字母与数字组成且不能小于六位");



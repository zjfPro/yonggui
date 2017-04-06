<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<link rel="stylesheet" href="css/global.css" type="text/css"/>
<link rel="stylesheet" href="css/list.css" type="text/css"/>
<script src="js/fixPNG.js" type="text/javascript"></script>

<script type="text/javascript">
	function pageTurning(page, totalPages) {
		if (page < 0) {
			page = 1;
		}
		$("#pageNo").val(page > totalPages ? totalPages : page);//显示总数
		if (page > totalPages || page == 0) {
			return;
		}

		if (page == "" || page == null) {
			alert("请输入要跳转的页数");
			return;
		}
		if (isNaN(page)) {
			alert("请输入数字");
			return false;
		}

		document.topageform.action = window.location.href;
		document.topageform.submit();
	}

	function changeRows() {
		document.topageform.action = window.location.href;
		document.topageform.submit();
	}
	

	$(function(){
	    //全选
	    $('#allCheckBrn1,#allCheckBrn2').on('click',function(){
	        if ($(this).attr('checked')) {
	            $('input[type="checkbox"]').attr('checked',true);
	            $('input[type="checkbox"]:disabled').attr('checked',false);
	        } else {
	            $('input[type="checkbox"]').attr('checked',false);
	        }
	       
	    });
		//反选
		$("#checkReverse").on("click",function(){

			
			if($('#allCheckBrn1').is(":checked") || $('#allCheckBrn2').is(":checked") ){
				$('input[type="checkbox"]').attr('checked',false);
				
			}else{
				
				var checkbox = $(this).parents("table").find('input[nc_type="eachCheckBox"]');
				var checkboxSize = checkbox.length;
				var checkCount = 0;
				
				
				for(var i=0; i <checkboxSize; i++){
					check = $(this).parents("table").find('input[nc_type="eachCheckBox"]').eq(i);
					
					if(check.is(":checked")){
						checkCount++;
					}else{
						checkCount=checkCount;
					}
					
					
					/*if(check.is(":checked")){
						check.attr('checked',false);
					}else{
						check.attr('checked',true);
					}*/
				}
				
				if(checkCount==0){
					$('input[type="checkbox"]').attr('checked',true);
				}else{
					for(var i=0; i <checkboxSize; i++){
					check = $(this).parents("table").find('input[nc_type="eachCheckBox"]').eq(i);
						if(check.is(":checked")){
							check.attr('checked',false);
						}else{
							check.attr('checked',true);
						}
					}
				}
				
				
			}
		})
		//取消
		$("#checkCancel").on("click",function(){
			$('input[type="checkbox"]').attr('checked',false);
		})
		
		
		
	    $('input[nc_type="eachCheckBox"]').on('click',function(){
			var checkbox = $(this).parents("table").find('input[nc_type="eachCheckBox"]');
			var checkboxSize = checkbox.length;
			var checkedCount = 0;
			var check;

	        if (!$(this).attr('checked')) {
	            $('#allCheckBrn1').attr('checked',false);
				$('#allCheckBrn21').attr('checked',false);
				
	        }else{
				for(var i=0; i <checkboxSize; i++){
					check = $(this).parents("table").find('input[nc_type="eachCheckBox"]').eq(i);
					if(check.is(":checked")){
						checkedCount ++;
					}else{
						checkedCount=checkedCount;
					}
				}

				if(checkedCount == checkboxSize){
					$('#allCheckBrn1').attr('checked',true);
					$('#allCheckBrn2').attr('checked',true);
				}
			}
	       
	    });
	    
	});
	
</script>
<input type="hidden" name="page" id="pageNo"
	value="${requestScope.pageUtil.page }" />
<div class="page">
	<div class="check_box">
            <label for="allCheckBrn2"><input id="allCheckBrn2" type="checkbox"> 全选</label>
            <input class="check_btn" id="checkReverse" type="button" value="反选">
            <input class="check_btn" id="checkCancel" type="button" value="取消">
          </div>

	<div class="page fright">
		<ul>
			<li>共<strong class="color_red">${requestScope.pageUtil.total}</strong>条</li>
			<li ><a
			href="javascript:pageTurning(1,${requestScope.pageUtil.totalPages})">首页</a></li>
			<li><a
			href="javascript:pageTurning(${requestScope.pageUtil.page-1},${requestScope.pageUtil.totalPages})">上一页</a></li>
			<li>${requestScope.pageUtil.page}/${requestScope.pageUtil.totalPages}</li>
			<li><a
			href="javascript:pageTurning(${requestScope.pageUtil.page+1},${requestScope.pageUtil.totalPages})">下一页</a>
			</li>
			<li>	<a
			href="javascript:pageTurning(${requestScope.pageUtil.totalPages},${requestScope.pageUtil.totalPages})">尾页</a>
			</li>
			<li>转到第<input type="text" class="page_numble" onkeyup="checkNumber(this)" id='whichPage' />页</li>
			<li><input type="button" value="" class="go mt7"  onclick="pageTurning(document.getElementById('whichPage').value)" />
			</li>
		</ul>
	</div>
</div>

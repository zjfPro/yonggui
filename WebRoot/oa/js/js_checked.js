
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
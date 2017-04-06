	$(function() {
		$.ajax({
			type:'post',
			url:'ajax/select/stockSetting',
			success : function(data) {
				if(data=='1'){
				}else{
					alert(data);
				}
			}
		});
	});
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	
	
	function cc(N,bool){  
   var aa = document.getElementById(N).getElementsByTagName("input");
//   var p_aa = document.getElementById(item_id).parentNode.parentNode;
   for (var i=0; i<aa.length; i++){
    if (aa[i].type=="checkbox"){
    //aa[i].checked = bool==1 ? true : (bool==0 ? false : !aa[i].checked);
	switch(bool)
	{
		case 0:
		aa[i].checked = false;
		aa.item(i).parentNode.parentNode.className = "checkoff";
		break;
		case 1:
		aa[i].checked = true;
		aa.item(i).parentNode.parentNode.className = "checkon";
		break;
		case 2:
		aa[i].checked = !aa[i].checked;
		if(aa[i].checked == true)
		{
			aa.item(i).parentNode.parentNode.className = "checkon";
		}
		else
		{
			aa.item(i).parentNode.parentNode.className = "checkoff";
		}
		break;
		default:
		break;
	}
   } 
   }
}
	
	
</script>
<input type="hidden" name="page" id="pageNo"
	value="${requestScope.pageUtil.page }" />
<div class="page">

	<div class="page_left">
		<span><a  onclick="cc('table_all',1);return false">全选</a>&nbsp;|&nbsp;<a
			 onclick="cc('table_all',2);return false">反选</a>&nbsp;|&nbsp;<a
			 onclick="cc('table_all',0);return false">不选</a>
	</div>

	<div class="page_left">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;共有
			${requestScope.pageUtil.total}条记录，当前第${requestScope.pageUtil.page}/${requestScope.pageUtil.totalPages}页
		</span>
	</div>
	<div class="page_right">
		<a
			href="javascript:pageTurning(1,${requestScope.pageUtil.totalPages})">首页</a>
		<a
			href="javascript:pageTurning(${requestScope.pageUtil.page-1},${requestScope.pageUtil.totalPages})">上一页</a>&nbsp;${requestScope.pageUtil.page}&nbsp;&nbsp;<a
			href="javascript:pageTurning(${requestScope.pageUtil.page+1},${requestScope.pageUtil.totalPages})">下一页</a>
		<a
			href="javascript:pageTurning(${requestScope.pageUtil.totalPages},${requestScope.pageUtil.totalPages})">尾页</a>
		<span>转到第<input type="tel" id="whichPage" class="inp_text1"
			onkeyup="checkNumber(this)">页&nbsp; <input type="button"
			class="inp_btn" value="确定"
			onclick="pageTurning(document.getElementById('whichPage').value)" />
		</span>
	</div>
</div>

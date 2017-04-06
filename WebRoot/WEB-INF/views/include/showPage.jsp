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
</script>
<input type="hidden" name="page" id="pageNo"
	value="${requestScope.pageUtil.page }" />
<div class="page">

	<div class="page fright" style=" margin-left: 550px;">
		<ul>
			<li>共上架<strong class="color_red">${requestScope.pageUtil.total}</strong>辆车</li>
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

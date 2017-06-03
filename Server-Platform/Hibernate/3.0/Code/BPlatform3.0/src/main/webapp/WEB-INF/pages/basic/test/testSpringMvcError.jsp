<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript">
var submitForm = function($dialog, $grid, $pjq) {
	if ($('form').form('validate')) {
		var url = system.contextPath + '/system/systemUserController/save';
		$.post(url, system.serializeObject($('form')), function(result) {
			if (result) {
				$grid.datagrid('reload');
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	}
};

</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>登陆名称</th>
					<td><input name="loginName" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
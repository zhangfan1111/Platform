<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
<title>文件上传示例</title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript" defer>
 function sendDirective() {
	 var directive = $.trim($("#directive").val());
	 if(directive) {
		var url = system.contextPath + '/system/systemLogController/directive';
		$.post(url, {directive:directive}, function(result) {
			if (result) {
				$.messager.alert('提示', result.msg, 'info');
			} else {
				$.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	 }
 }
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>文件上传示例</legend>
			<div id="tt" class="easyui-tabs" style="width:780px;height:540px;">
			    <div title="个人收入能力证明材料" data-options="iconCls:'icon-reload'" style="padding:10px;">
			    	<table class="table" style="width: 100%;">
						<tr>
							<th>个人资信</th>
							<td colspan="3">
								<textarea name="customerCredit" required="required" style="width:600px;height:50px"> </textarea>
								<input type="text" id="directive" style="width: 100px;"/>
								<input type="button" id="directive_btn" value="提交" onclick="sendDirective();"/>
							</td>
						</tr>
					</table>
			    </div>
			    <div title="担保资料" data-options="iconCls:'icon-reload'" style="padding:10px;">
			    	<table class="table" style="width: 100%;">
				    	<tr>
							<th>担保类型</th>
							<td>
								<tags:dictCombobox id="customerAssureType" name="customerAssureType" dict="10.2.13" required="true"></tags:dictCombobox>
							</td>
						</tr>
					</table>
			    </div>
			</div>
		</fieldset>
	</form>
</body>
</html>
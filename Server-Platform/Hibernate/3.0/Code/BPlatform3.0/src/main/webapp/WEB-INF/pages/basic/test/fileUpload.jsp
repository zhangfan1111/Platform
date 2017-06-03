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
	$(document).ready(function() {
		var jsUrl = system.contextPath + '/resources/js/system/file.js';

		jQuery.getScript(jsUrl).done(function() {
		//	alert();

		}).fail(function() {
			$.messager.alert('错误', '资源文件加载失败，请重新打开浏览器，重试！', 'error');
		});
	
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>文件上传示例</legend>
		    	<table class="table" style="width: 100%;">
		    		<tr>
						<th>附件集1</th>
						<td>
							<tags:filemanager edit="${edit}" parentFiledId="customerIDFiles"></tags:filemanager>
						</td>
						<th>附件集2</th>
						<td>
							<tags:filemanager edit="${edit}" parentFiledId="customerRegisterFiles"></tags:filemanager>
						</td>
					</tr>
				</table>
		</fieldset>
		<label>温馨提示：上传格式为(BMP,GIF,JPEG,PNG,JPG)、上传大小不超过10M</label>
	</form>
	<div style="height: 200px;">
		<div id="container">
			<img id="person_img" src="${app}/resources/plug-in/login/images/logo.jpg" style="width: 100px; height: 100px;"/>
			<input type="button" id="addfiles" value="上传图片"/>	
		</div>
		<label>注意：如果放入form中，不要调用form的clear。建议不要放入form中</label>
	</div>
</body>
</html>
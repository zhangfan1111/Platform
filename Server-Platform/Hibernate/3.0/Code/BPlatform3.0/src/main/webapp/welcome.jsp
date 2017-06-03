<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>欢迎页面</title>
<jsp:include page="jsp/common/base.jsp"></jsp:include>
<style type="text/css">
	html,body {
		height: auto;
	}
</style>
</head>
<body style="font-size: 12px; color:#0099FF;">
	<div style="text-align: center; margin: 14% auto;font-size: 12px; color:#0060AA">
		<img style="border-radius: 5px; margin: 20px;" src="${app}/resources/style/images/logo.png"/>
		<div style="font-size: 30px; text-align: center; line-height: 40px;">欢迎使用管理后台</br>请妥善保管好您的账号及密码</div>
	</div>
</body>
</html>
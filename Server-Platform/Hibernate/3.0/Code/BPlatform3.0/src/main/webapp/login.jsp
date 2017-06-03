<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<!DOCTYpE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>成都伟航创达有限责任公司</title>
    <%
	String cPath = request.getContextPath();
	String path = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + cPath;
	
	%>	
    <link rel="stylesheet" href="resources/plug-in/login/css/style.css">
</head>
<body onkeydown="keyLogin();">
<div class="big">
	<img src="<%=request.getContextPath()%>/resources/style/images/logo_2.png" style="position: absolute;top:20px;left: 200px">
    <div class="top_div"></div>
    <div style="background: #EEF0EF; margin: -150px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 300px; text-align: center;border-radius: 8px;">
        <form name="formLogin" id="formLogin" action="index.jsp" check="<%=path%>/system/loginController/login" method="post" autocomplete="off" >
	        <div style="width: 240px; height: 46px; position: relative;top:-50px;left:-10px;color: #ffffff">
	            <span style="font-size: 30px;font-weight: bold;">伟航创达</span><span style="font-size: 18px">后台管理系统</span>
	        </div>
	        <div id="tishi" style="position: relative;top:-20px;color: red;height: 30px;text-align: left;margin-left: 65px;"></div>
	        <div style="margin: 0px 0px 10px; position: relative;top:-10px">
	            <span class="u_logo"></span>
	            <input class="ipt" id="user" name="user" type="text" iscookie="true" placeholder="请输入用户名" value="">
	        </div>
	        <div style="padding: 10px 0px 10px;position: relative;">
	            <span class="p_logo"></span>
	            <input class="ipt" id="password" name="password" type="password" placeholder="请输入密码" value="">
	        </div>
	        <div style="font-size: 13px;text-align: left;margin-left: 65px;height: 35px;line-height: 35px">
	        	<input id="check" type="checkbox" style="position: relative;top: 2px;">
	        	记住用户名
	        </div>
	        <div style="height: 40px; line-height: 50px;margin-top:10px;font-size: 12px;">
	            <a id="login" style="background: #00A2EA; padding: 10px 121px; border-radius: 4px; border-image: none; color: rgb(255, 255, 255); font-weight: bold;" href="#">登录</a>
	        </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="resources/plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="resources/plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="resources/plug-in/login/js/js.js"></script>
</html>
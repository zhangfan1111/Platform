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
<div class="big" style="background: url('${app}/resources/style/images/back/pic_background1.png');background-size: 100% 100%;">
	<img src="<%=request.getContextPath()%>/resources/style/images/logo_2.png" style="position: absolute;top:20px;left: 200px">
    <div class="top_div"></div>
    <div class="login_panel" id="login_panel" style="">
        <form name="formLogin" id="formLogin" action="index.jsp" check="<%=path%>/system/loginController/login" method="post" autocomplete="off" >
	        <div class="project_tit">
	            <span style="font-size: 30px;font-weight: bold;">伟航创达</span><span style="font-size: 18px">后台管理系统</span>
	        </div>
	        <div style="margin: 0px 0px 10px; position: relative">
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
	        <div style="height: 40px; line-height: 50px;font-size: 12px;">
	            <a id="login" href="#">登录</a>
	        </div>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="resources/plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="resources/plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="resources/plug-in/login/js/js.js"></script>
	
<script type="text/javascript">

	//随机改变背景图
	function randomBack(){
		var r = Math.ceil(Math.random() * 16);
		$(".big").css("background", "url('${app}/resources/style/images/back/pic_background" + r + ".png')")
	}
	randomBack();
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%@ include file="/jsp/common/base.jsp"%>
<!-- 在base.jsp里面您需要引入wangEditer.3.0.0 -->
<%
    //获取session
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 测试看起来比较合理的宽高比 640px 330px -->
	<div id="container" style="width: 640px;height:330px;"></div>
</body>
<script type="text/javascript">
	// editor.txt.clear(); // 清空编辑器内容
	// editor.txt.html(); // 获取编辑器内容html
	// editor.txt.text(); // 获取编辑器内容html
    var editor = new window.wangEditor('#container');  // 我修改了wangEditor.js里图片最大上传为2M
    // 服务器地址：SpringMVC处理地址,wangEditor.js已设置，此处不用设置
    // editor.customConfig.debug = location.href.indexOf('wangeditor_debug_mode=1') > 0  // DEBUG模式
    editor.create();
</script>
</html>
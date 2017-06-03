<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<!DOCTYPE html>
<html>
<head>
<title>memory</title>
<jsp:include page="/jsp/common/base.jsp"></jsp:include>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
	if (sessionInfo != null) {
		request.getRequestDispatcher("/jsp/system/main.jsp").forward(request, response);
	} else {
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
%>
</head>
<body>
</body>
</html>
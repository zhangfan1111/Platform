<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="com.memory.platform.modules.system.base.util.SecurityUtil"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
SecurityUtil securityUtil = new SecurityUtil();
%>
<%String version = "20160306";%>

<%
Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
if (null != cookies) {
	for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	}
}
String easyuiTheme = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
if (cookieMap.containsKey("easyuiTheme")) {
	Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
	easyuiTheme = cookie.getValue();
}
%>

<script type="text/javascript">
var system = system || {};
system.contextPath = '${app}';
system.basePath = '${scheme}' + "://" + '${serverName}' + ":" + '${serverPort}';
system.version = '${version}';
system.pixel_0 = '${app}/resources/style/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>

<%-- 引入my97日期时间控件 --%>
<script type="text/javascript" src="${app}/resources/plug-in/My97DatePicker4.8Beta3/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<%-- 引入jQuery --%>
<%
String userAgent = request.getHeader("User-Agent");
if (StringUtils.indexOfIgnoreCase(userAgent, "MSIE") > -1 && (StringUtils.indexOfIgnoreCase(userAgent, "MSIE 6") > -1 || StringUtils.indexOfIgnoreCase(userAgent, "MSIE 7") > -1 || StringUtils.indexOfIgnoreCase(userAgent, "MSIE 8") > -1)) {
	out.println("<script src='" + request.getContextPath() + "/resources/plug-in/jquery-1.9.1.js' type='text/javascript' charset='utf-8'></script>");
} else {
	out.println("<script src='" + request.getContextPath() + "/resources/plug-in/jquery-2.0.3.js' type='text/javascript' charset='utf-8'></script>");
}
%>
<%-- 引入jquery扩展 --%>
<script src="${app}/resources/plug-in/ExtJquery.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入Highcharts --%>
<script src="${app}/resources/plug-in/Highcharts-4.0.3/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="${app}/resources/plug-in/Highcharts-4.0.3/js/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<script src="${app}/resources/plug-in/Highcharts-4.0.3/js/highcharts-3d.js" type="text/javascript" charset="utf-8"></script>
<%-- 引入Highcharts扩展 --%>
<script src="${app}/resources/plug-in/ExtHighcharts.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入EasyUI --%>
<link id="easyuiTheme" rel="stylesheet" href="${app}/resources/plug-in/jquery-easyui-1.5.2/themes/<%=easyuiTheme%>/easyui.css" type="text/css">
<!-- <link rel="stylesheet" href="${app}/resources/plug-in/jquery-easyui-1.5.2/themes/icon.css" type="text/css"> -->
<script type="text/javascript" src="${app}/resources/plug-in/jquery-easyui-1.5.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app}/resources/plug-in/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<%-- 引入EasyUI Portal插件 --%>
<link rel="stylesheet" href="${app}/resources/plug-in/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${app}/resources/plug-in/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<%-- 引入easyui扩展 --%>
<script src="${app}/resources/plug-in/ExtEasyUI.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入扩展图标 --%>
<link rel="stylesheet" href="${app}/resources/style/ExtIcon.css?version=<%=version%>" type="text/css">

<%-- 引入自定义样式 --%>
<link rel="stylesheet" href="${app}/resources/style/ExtCss.css?version=<%=version%>" type="text/css">

<%-- 引入javascript扩展 --%>
<script src="${app}/resources/plug-in/ExtJavascript.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>

<%-- 引入自定义样式 --%>
<link rel="stylesheet" href="${app}/resources/style/platformCss.css" type="text/css">


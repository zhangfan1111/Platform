<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<c:set var="jzo2o" scope="page" >${app}/resources/plug-in/jzo2o/weixin</c:set>
<!DOCTYPE >
<html>
<head>
<meta charset="utf-8">
<title> 图片上传 </title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
<meta  id="viewport" name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<!--css-->
<link type="text/css" rel="stylesheet" href="${app}/resources/plug-in/html5/html5imageUpload.css" charset="utf-8">

<style type="text/css">
</style>
<!--js-->
</head>
<body>
<div id="btnUploadCtrl" style="margin-bottom:0.5em;
		height: 1.5em;
		line-height: 1.5em;
		color: #fff;
		background: #F8BE99;
		-webkit-box-pack: center;
		border: none;
		font-size: 0.4em;">上传图片（双击删除）</div>
<div class="imagebox">
	<div class="addfile">
		<div class="speed">1%</div>
		<input id="file" type="file" class="file" />
	</div>
</div>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/zepto.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/event.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/touch.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/fx.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/detect.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/form.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/ajax.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/data.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/callbacks.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/deferred.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/assets.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/selector.js"></script>
<script type="text/javascript" src="${jzo2o}/js/zepto-1.1.4/stack.js"></script>

<script src="${app}/resources/plug-in/html5/html5imageUpload.js" type="text/javascript"></script>
<script type="text/javascript">
	window.jzo2o = {};
	jzo2o.appPath = '${scheme}://${serverName}:${serverPort}${app}';
</script>

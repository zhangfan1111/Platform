<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
<title>查看大图</title>
<%@ include file="/jsp/common/base.jsp"%>

<style type="text/css">
	.bs-tip {
		color: #FFFFFF;
		background-color: #009900;
		position: relative;
		float: left;
		height: 35px;
		line-height: 35px;
		text-align: center;
		padding: 0px 15px 0px 15px;
		margin-right: 10px;
		margin-top: 5px;
	}
	
	.bs-tip a {
		color: #FFFFFF;
		margin-left: 10px;
		text-decoration: none;
	}
	
	.bs-large-img {
		width: 300px;
		height: 200px;
	}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$('#myCombobox').combobox({
			onSelect: function(rec){
				$('#photos').tooltip({position: rec.value});
	        }
		});
		
		$('#photos').tooltip({
            position: 'right',
            content: '<img class="bs-large-img" src=""/>',
            onShow: function(e) {
            	$('.bs-large-img').attr('src', $(this).attr("src"));
            }
        });
	});
	
	
</script>

</head>
<body>
	显示位置：
	<select id="myCombobox" class="easyui-combobox" name="state" style="width:200px;">
        <option value="top">top</option>
        <option value="left">left</option>
        <option value="right" selected="selected">right</option>
        <option value="bottom">bottom</option>
    </select>
	<div style="position:absolute; top: 40%; right: 50%;">
		<img id="photos" alt="" src="${app}/resources/plug-in/login/images/user_.png" style="width: 100px; height: 100px;"/>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<style>
	.easyui-img-btn {
		margin: 0 -4px;
	}
	.shortcut li {
		float: left;
		list-style: none;
		margin-right: 10px;
		cursor: pointer;
	}
	.both{font-size: 13px;color: #FFFFFF;float: right;margin-right: 10px;}
	.imgs{width:15px;height:15px;position: relative;top:4px}
</style>
<script type="text/javascript" charset="utf-8">
	$(function(){
		 /* 鼠标经过事件 */
		 $("#userInfo").click(function(){
		        $("#layout_north_zxMenu").slideDown();
	              
		    });
		 $("#layout_north_zxMenu").mouseleave(function(){
		        $("#layout_north_zxMenu").slideUp();
	           
		    });
		
	});
	var logoutFun = function() {
		$.post(system.contextPath + '/system/loginController/logout', function(result) {
			location.replace(system.contextPath + '/index.jsp');
		}, 'json');
	};
	var showMyInfoFun = function() {
		var dialog = parent.system.modalDialog({
			title : '我的信息',
			height : 350,
			border : false,
			url : system.contextPath + '/system/systemUserController/form?id=' + '<%=sessionInfo.getUser().getId()%>'
		});
	};
	var openWinFun = function(elt){
		var url = $(elt).attr('url');
		var title = $(elt).attr('title');
		var src = system.contextPath + url;
		if (!system.startWith(url, '/')) {
			src = url;
		}
		var tabs = $('#mainTabs');
		
		var opts = {
			title : title,
			closable : true,
			iconCls : 'ext-icon-shape_square_go',
			content : system.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
			border : false,
			fit : true
		};
		if (tabs.tabs('exists', opts.title)) {
			tabs.tabs('select', opts.title);
		} else {
			tabs.tabs('add', opts);
		}
	}
	
	var openTab = function(menuId) {
		var menus = $('#mainMenu');
		var analyzeNode = menus.tree('find',menuId);
		if(analyzeNode) {
			if(analyzeNode.target) {
				menus.tree('select',analyzeNode.target);
				analyzeNode.target.click();
			}
		}
	}
	
	//   Date:2014-10-09 for:新增首页风格,一级菜单点击事件的切换操作
	$(".shortcut li").bind("click",function(){
		$(this).find(".imag1").hide();
		$(this).find(".imag2").show();
		$(this).siblings().find(".imag2").hide();
		$(this).siblings().find(".imag1").show();
	});
	//   Date:2014-10-09 for:新增首页风格,一级菜单点击事件的切换操作
</script>

<div style="font-size: 24px; position: absolute; left: 20px;color: #ffffff;font-weight: bold;">
	<img src="<%=request.getContextPath()%>/resources/style/images/logo_2.png" style="position: relative;top:10px">
	<!-- <span style="margin-left: 30px">网站管理后台</span> -->
</div>

<div onclick="logoutFun();" class="both" style="cursor: pointer;">
	<img class="imgs" src="<%=request.getContextPath()%>/resources/style/images/icon_tuichu.png" />
	退出
</div>

<div id="userInfo" class="both" style="cursor: pointer;">
	<img class="imgs" src="<%=request.getContextPath()%>/resources/style/images/u68.png" />
	<span href="javascript:void(0);" id="layout_north_kzmbMenu">控制面板</span>
	<img src="<%=request.getContextPath()%>/resources/style/images/icon_xiaosanjiao.png" />
</div>

<div id="sessionInfoDiv" class="both">
	<img class="imgs" src="<%=request.getContextPath()%>/resources/style/images/u66.png" />
	<%
		if (sessionInfo != null) {
			out.print(com.memory.platform.common.util.StringUtil.formateString("{0}", sessionInfo.getUser().getLoginName()));
		}
	%>
</div>

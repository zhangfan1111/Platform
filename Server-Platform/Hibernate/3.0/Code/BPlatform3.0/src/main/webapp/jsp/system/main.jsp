<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
<title>基础平台</title>
<%@ include file="/jsp/common/base.jsp"%>
<style type="text/css">
	#mainMenu div{
		color:#F4F4F4;
		background-color: #263240;
	}
	
	#mainMenu div span .tree-expanded{
		background: none;
	}
	
	#mainMenu div:HOVER{
		background-color: #009FE7;
	}
	
	#mainMenu .tree-node-selected {
		font-weight: bolder;
		background-color: #0B6CBC;
	}
	
	html,body {
		height: 100%;
		margin: 0;
		background-color: #EEF7F7;
		font-family: "arial,helvetica,sans-serif","microsoft yahei";
	}
	
	.main-panel-header {
		height: 25px;
		line-height: 25px;
		border: 0;
		border-bottom: 1px solid #E5E5E5;
	}
	
	.main-panel-header .panel-title{
		height: 25px;
		line-height: 25px;
	}
	
	.menu-panel-header {
		height: 25px;
		line-height: 25px;
		border: 0;
		border-bottom: 1px solid #E5E5E5;
	}
	#layout_north_zxMenu{width: 100px; display: none;position: absolute;top:50px;right: 60px;background-color: #ffffff;opacity:0.9;font-size: 13px;border-radius:6px 6px 6px 6px;text-align: center;padding: 5px 0;}
	.tree-hit{visibility: hidden;}
	.tree-folder + span {font-weight:bold;}
	.tab1{margin: 25px auto}
	.tab1 td{border: 0}
</style>

<script type="text/javascript">
	var mainMenu;
	var mainTabs;
	$(function() {
		mainTabs = $('#mainTabs').tabs({
			fit : true,
			border : false,
			tools : [{
				text : '刷新',
				width: 50,
			//	iconCls : 'ext-icon-arrow_refresh',
				handler : function() {
					var panel = mainTabs.tabs('getSelected').panel('panel');
					var frame = panel.find('iframe');
					try {
						if (frame.length > 0) {
							for (var i = 0; i < frame.length; i++) {
								frame[i].contentWindow.document.write('');
								frame[i].contentWindow.close();
								frame[i].src = frame[i].src;
							}
							if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
								try {
									CollectGarbage();
								} catch (e) {
								}
							}
						}
					} catch (e) {
					}
				}
			},{text : '|',} ,{
				text : '关闭',
				width: 50,
			//	iconCls : 'ext-icon-cross',
				handler : function() {
					var index = mainTabs.tabs('getTabIndex', mainTabs.tabs('getSelected'));
					var tab = mainTabs.tabs('getTab', index);
					if (tab.panel('options').closable) {
						mainTabs.tabs('close', index);
					} else {
						$.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
					}
				}
			} ],
		});
	//});
	//$(function() {

		var loginFun = function() {
			if ($('#loginDialog form').form('validate')) {
				$('#loginBtn').linkbutton('disable');
				$.post(system.contextPath + '/base/syuser!doNotNeedSessionAndSecurity_login.sy', $('#loginDialog form').serialize(), function(result) {
					if (result.success) {
						$('#loginDialog').dialog('close');
					} else {
						$.messager.alert('提示', result.msg, 'error', function() {
							$('#loginDialog form :input:eq(1)').focus();
						});
					}
					$('#loginBtn').linkbutton('enable');
				}, 'json');
			}
		};
		$('#loginDialog').show().dialog({
			modal : true,
			closable : false,
	//		iconCls : 'ext-icon-lock_open',
			buttons : [ {
				id : 'loginBtn',
				text : '登录',
				handler : function() {
					loginFun();
				}
			} ],
			onOpen : function() {
				$('#loginDialog form :input[name="data.pwd"]').val('');
				$('form :input').keyup(function(event) {
					if (event.keyCode == 13) {
						loginFun();
					}
				});
			}
		}).dialog('close');

		$('#passwordDialog').show().dialog({
			modal : true,
			closable : true,
			border : false,
	//		iconCls : 'ext-icon-lock_edit',
			buttons : [ {
				text : '修改',
				handler : function() {
					if ($('#passwordDialog form').form('validate')) {
						$.post(system.contextPath + '/system/systemUserController/updatePwd', {
							'pwd' : $('#pwd').val()
						}, function(result) {
							if (result) {
								$.messager.alert('提示', '密码修改成功！', 'info');
								$('#passwordDialog').dialog('close');
							}
						}, 'json');
					}
				}
			} ],
			onOpen : function() {
				$('#passwordDialog form :input').val('');
			}
		}).dialog('close');

		 mainMenu = $('#mainMenu').tree({
			url : system.contextPath + '/system/systemResourceController/getMainResource',
			idField : 'id',
			textField : 'name',
			state : 'open',
			parentField : 'parentId',
			nodeIconClsAtrribute : 'icon',
			onClick : function(node) {
				if (node!=null&&node.url) {
					var src = system.contextPath + node.url;
					if (!system.startWith(node.url, '/')) {
						src = node.url;
					}
					if (node.position && node.position.length > 0) {
						window.open(node.url, node.position);
					} else {
						var tabs = parent.$('#mainTabs');
						var longs = tabs.tabs('tabs').length;
						for(var i = longs;i > 0;i--){
							tabs.tabs('close',i);
						}
						var opts = {
							title : node.text,
							closable : true,
							iconCls : 'ext-icon-bullet_blue',//node.icon,
							content : system.formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99.5%;" frameBorder="0"></iframe>', src),
							border : false,
							fit : true
						};
						tabs.tabs('add', opts);
				//		if(node.text != $('.main-panel-header .panel-title').html()) {
							/* $('#main_panel').panel("setTitle", node.text);
							$('.main-panel-header .panel-icon').attr("class", "panel-icon" + " " +node.icon);
							$('#main_frame').attr("src", system.basePath + src); */
				//		}
					}
				}
			},
			onDblClick:function(node){//双击展开折叠节点
				if(node!=null){
					if(node.state=="open"){
						$('#mainMenu').tree('collapse', node.target);
					}else{
						$('#mainMenu').tree('expand', node.target);
					}
				}
			},
			onLoadSuccess:function(data){
				$('#mainMenu').prepend('<li><div id="easyui_tree_first" class="tree-node"><span class="tree-indent"></span><span class="tree-icon tree-folder ext-icon-bullet_white"></span><span class="tree-title">首页</span></div></li>')
				$('#easyui_tree_first').click(function(){
					/* $('#main_panel').panel({
						title: "首页",
						iconCls:'ext-icon-heart', 
						headerCls:'main-panel-header',
						fit:true, 
						border: false,
						onOpen: function() {
							$('#main_frame').attr('src', system.contextPath + '/welcome.jsp');
						}
					}); */
					var tabs = parent.$('#mainTabs');
					tabs.tabs("select","首页");
				})
			}
		});


		$('#mainLayout').layout('panel', 'center').panel({
			onResize : function(width, height) {
				system.setIframeHeight('centerIframe', $('#mainLayout').layout('panel', 'center').panel('options').height - 5);
			}
		});

		/* $('#main_panel').panel({
			title: "首页",
			iconCls:'ext-icon-heart', 
			headerCls:'main-panel-header',
			fit:true, 
			border: false,
			onOpen: function() {
				$('#main_frame').attr('src', system.contextPath + '/welcome.jsp');
			}
		}); */
		
	});
</script>
</head>
<body style="overflow: auto;">
	<div style="width:100%; height:100%; min-width: 1024px; max-width: 1920px; margin:0 auto;">
		<div id="mainLayout" class="easyui-layout" data-options="fit: true, border: false">
			<div data-options="region:'north',border:false,href:'${app}/jsp/system/north.jsp'" style="background-color: #263240;height: 70px; overflow: hidden;line-height: 70px;"></div>
			<div data-options="region:'west',href:''" style="width: 220px;border-color: #263240;border-right-style: none">
				<div class="easyui-panel" data-options="headerCls:'menu-panel-header', fit:true, border: false" style="background-color: #263240;">
					<ul id="mainMenu">
					</ul>
				</div>
			</div>
			<div data-options="region:'center'" style="overflow: hidden;">
				<div id="mainTabs" data-options="plain: true, narrow: true,tabHeight:30, border: false" style="padding: 5px;">
					<div title="首页" data-options="iconCls:'ext-icon-bullet_blue'">
						<iframe src="${app}/welcome.jsp" allowTransparency="true" style="border: 0; width: 100%; height: 99.5%;" frameBorder="0"></iframe>
						<%-- <div style="text-align: center; padding: 20px; margin-top: 180px;font-size: 12px; color:#0060AA">
							<img style="border-radius: 5px; margin: 20px;" src="${app}/resources/style/images/logo.png"/>
							<div style="font-size: 30px; text-align: center; line-height: 40px;">欢迎使用基础平台</br>请妥善保管好您的账号及密码</div>
						</div> --%>
					</div>
				</div>
				<!-- <div id="main_panel" style="width: 100%">
					<iframe id="main_frame" src="" allowTransparency="true" style="border:0;width:100%;height:99.5%;" frameBorder="0">
					</iframe>
				</div> -->
			</div>
			<%-- <div data-options="region:'south',href:'${app}/jsp/system/south.jsp',border:false" style="height: 30px; overflow: hidden;"></div> --%>
		</div>
	</div>
	
	<div id="layout_north_zxMenu">
		<div style="margin: 5px 10px;cursor: pointer;" onclick="$('#passwordDialog').dialog('open');">
			<img src="<%=request.getContextPath()%>/resources/style/images/icon_xiugaimima.png" />
			修改密码
		</div>
		<hr style="margin: 0 5px">
		<div style="margin: 5px 10px;cursor: pointer;" onclick="showMyInfoFun();">
			<img src="<%=request.getContextPath()%>/resources/style/images/icon_wodexinxi.png" />
			我的信息
		</div>
	</div>

	<div id="loginDialog" title="解锁登录" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th width="50">登录名</th>
					<td><%=sessionInfo.getUser().getLoginName()%><input name="data.loginname" readonly="readonly" type="hidden" value="<%=sessionInfo.getUser().getLoginName()%>" /></td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="data.pwd" type="password" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="passwordDialog" title="修改密码" style="display: none;height: 220px;width: 350px">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table tab1">
				<tr>
					<td>新密码</td>
					<td><input id="pwd" name="pwd" type="password" class="easyui-textbox" data-options="required:true,height:30" /></td>
				</tr>
				<tr>
					<td>重复密码</td>
					<td><input id="rpwd" name="rpwd" type="password" class="easyui-textbox" data-options="required:true,validType:'same[\'#pwd\']',height:30" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
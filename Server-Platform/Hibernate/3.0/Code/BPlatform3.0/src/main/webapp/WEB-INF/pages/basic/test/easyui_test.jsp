<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>

</head>
<body>
	<div id="win" class="easyui-window" title="My Window" style="width:600px;height:400px;display: none"   
	        data-options="iconCls:'icon-save',modal:true">   
	    Window Content    
	</div>
	
	<input type="button" value="打开窗口" onclick="open2();" />
</body>
</html>

<script type="text/javascript">
	function open1() {
		$('#win').window('open');
		$('#win').window('refresh','http://localhost:8082/sstd-web/jzo2o/weixin/weiXinController/index');
	}
	
	function open2() {
		var dialog = parent.system.modalDialog({
			title : '修改机构',
			url : system.contextPath + '/jzo2o/weixin/weiXinController/index',
			resizable : true,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	}
</script>
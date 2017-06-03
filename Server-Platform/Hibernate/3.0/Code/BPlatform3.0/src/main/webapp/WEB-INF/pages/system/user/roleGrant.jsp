<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<style type="text/css">
fieldset{border: 0}
</style>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		var nodesAdd = $('#tree').tree('getAddChecked');
		var nodesDelete = $('#tree').tree('getDeleteChecked');
		var addIds = [];
		var delIds = [];
		for (var i = 0; i < nodesAdd.length; i++) {
			addIds.push(nodesAdd[i].id);
		}
		for (var i = 0; i < nodesDelete.length; i++) {
			delIds.push(nodesDelete[i].id);
		}
		var po = {userId:'${userId[0]}',addRoleIds:addIds,delRoleIds:delIds};
		var params = $.param(po,true);
		$.post(system.contextPath + '/system/systemUserController/modifyRole?'+params, null, function(result) {
			if (result.success) {
				$dialog.dialog('destroy');
				$pjq.messager.alert('提示', '分配成功！', 'info');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#tree').tree({
			url : system.contextPath + '/system/systemUserController/loadUserRoleTree/',
			parentField : 'parentId',
			checkedField : 'isChecked',
			checkbox : true,
			cascadeCheck : false,
			formatter : function(node) {
				return node.name;
			},
			onBeforeLoad : function(node,param) {
				param['userId'] = '${userId[0]}';
			},
			onLoadSuccess : function(node, data) {
				/*$.post(system.contextPath + '/base/syresource!doNotNeedSecurity_getRoleResources.sy', {
					id : '${userId[0]}'
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
							var node = $('#tree').tree('find', result[i].id);
							if (node) {
								var isLeaf = $('#tree').tree('isLeaf', node.target);
								if (isLeaf) {
									$('#tree').tree('check', node.target);
								}
							}
						}
					}
					parent.$.messager.progress('close');
				}, 'json');*/
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body>
	<fieldset>
		<ul id="tree"></ul>
	</fieldset>
</body>
</html>
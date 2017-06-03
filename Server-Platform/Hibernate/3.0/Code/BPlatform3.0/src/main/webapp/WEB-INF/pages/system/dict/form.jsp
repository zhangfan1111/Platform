<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<style type="text/css">
fieldset{border: 0}
.table td{border: 0}
.iconImg{position: relative;top:4px;}
</style>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			url = system.contextPath + '/system/systemDictController/save';
			$.post(url, system.serializeObject($('form')), function(result) {
				if (result.success) {
					$grid.treegrid('load');
					$dialog.dialog('destroy');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	$(function() {
		if ($(':input[name="id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(system.contextPath + '/system/systemDictController/getById/${id[0]}', {
				id : $(':input[name="id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'code' : result.code,
						'name' : result.name,
						'enName' : result.enName,
						'parentId' : result.parentId,
						'remark' : result.remark,
					});
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<input name="id" value="${id[0] }" type="hidden" />
		<fieldset>
			<table class="table" style="width: 100%;">
				<tr>
					<td>编号</td>
					<td><input name="code" class="easyui-textbox" validType="numberAndPoint" data-options="required:true,height:30"/></td>
					<td>字典名称</td>
					<td><input name="name" class="easyui-textbox" data-options="required:true,height:30" /></td>
				</tr>
				<tr>
					<td>英文名称</td>
					<td><input name="enName" class="easyui-textbox" data-options="required:false,editable:false,height:30" style="width: 155px;" value="" /></td>
					<td>上级字典</td>
					<td>
						<select id="parentId" name="parentId"  class="easyui-combotree" 
								data-options="url:'${app}/system/systemDictController/treeGrid',
												idField:'id',
												textField:'name',
												parentField:'parentId',
												editable:false,
												height:30,
												state:'open'
												" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#parentId').combotree('clear');" title="清空" />
					</td>
				</tr>
				<tr>
					<td>字典描述</td>
					<td colspan="3"><!-- <textarea name="remark" style="width:500px;height:200px;border-radius: 5px 5px 5px 5px;"></textarea> -->
						<input class="easyui-textbox" data-options="width:500,height:200,multiline:true" name="remark" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
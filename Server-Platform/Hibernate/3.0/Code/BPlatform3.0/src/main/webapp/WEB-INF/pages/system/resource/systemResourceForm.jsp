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
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		if ($('form').form('validate')) {
			var url = system.contextPath + '/system/systemResourceController/save';
			$.post(url, system.serializeObject($('form')), function(result) {
				if (result) {
					$grid.treegrid('reload');
					$dialog.dialog('destroy');
					$mainMenu.tree('reload');
				} else {
					$pjq.messager.alert('提示', result.msg, 'error');
				}
			}, 'json');
		}
	};
	var showIcons = function() {
		var dialog = parent.system.modalDialog({
			title : '浏览小图标',
			border:false,
			url : system.contextPath + '/resources/style/icons.jsp',
			buttons : [ {
				text : '确定',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.selectIcon(dialog, $('#icon'));
				}
			} ]
		});
	};
	$(function() {
		if ($(':input[name="id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(system.contextPath + '/system/systemResourceController/getById/${id[0]}', {
				id : $(':input[name="id"]').val(),
			}, function(data) {
					if (data.id != '') {
						$('form').form('load', {
							'id' : data.id,
							'code' : data.code,
							'name' : data.name,
							'url' : data.url,
							'type' : data.type,
							'parentId' : data.parentId,
							'parentCode' : data.parentCode,
							'description' : data.description,
							'icon' : data.icon,
							'orderCode' : data.orderCode,
							'position' : data.position
						});
						$('#icon').attr('class', data.icon);//设置背景图标}
					}
						parent.$.messager.progress('close');
				}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<input name="id" value="${id[0]}" type="hidden" readonly="readonly" />
		<fieldset>
			<table class="table" style="width: 100%;">
				<tr>
					<td>编号</td>
					<td><input name="code" class="easyui-textbox" validType="numberAndPoint" data-options="required:true,height:30"/></td>
					<td>资源名称</td>
					<td><input name="name" class="easyui-textbox" data-options="required:true,height:30" /></td>
				</tr>
				<tr>
					<td>资源路径</td>
					<td><input name="url" class="easyui-textbox" data-options="height:30"/></td>
					<td>资源类型</td>
					<td>
						<select name="type"  class="easyui-combobox" data-options="required:true,editable:false,panelHeight:'auto',height:30" style="width: 155px;">
							<option value="1">1</option>
						    <option value="2">2</option>
						    <option value="3">3</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>上级资源</td>
					<td>
						<select id="parentId" name="parentId"  class="easyui-combotree" data-options="editable:false,state:'open',textField:'name',idField:'id',parentField:'parentId',nodeIconClsAtrribute : 'icon',linkFields:{parentCode:'code'},url:'${app}/system/systemResourceController/getAllResource',height:30" style="width: 155px;"></select><img class="iconImg ext-icon-cross" onclick="$('#parentId').combotree('clear');$('#parentCode').val('');" title="清空" />
						<input id="parentCode" name="parentCode" value="" type="hidden" readonly="readonly" />
					</td>
					<td>资源图标</td>
					<td><input id="icon" name="icon" readonly="readonly" style="padding-left: 18px; width: 134px;height: 25px;border-radius: 5px 5px 5px 5px;border: 1px solid #D4D4D4" /><img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;<img class="iconImg ext-icon-cross" onclick="$('#icon').val('');$('#icon').attr('class','');" title="清空" /></td>
				</tr>
				<tr>
					<td>顺序</td>
					<td><input name="orderCode" class="easyui-numberspinner"  data-options="required:true,min:0,max:100000,editable:false,height:30" style="width: 155px;" value="100" /></td>
					<td>目标</td>
					<td><input id="position" class="easyui-textbox" data-options="height:30" name="position" /></td>
				</tr>
				<tr>
					<td>资源描述</td>
					<td colspan="3"><!-- <textarea name="description"  style="width:500px;height:150px;border-radius: 5px 5px 5px 5px;"></textarea> -->
						<input class="easyui-textbox" data-options="width:500,height:150,multiline:true" name="description" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
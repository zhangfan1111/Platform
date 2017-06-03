<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<style type="text/css">
.table td{border: 0;}
fieldset{border: 0}
</style>
<script type="text/javascript">
var submitForm = function($dialog, $grid, $pjq) {
	if ($('form').form('validate')) {
		var url = system.contextPath + '/system/systemUserController/save';
		$.post(url, system.serializeObject($('form')), function(result) {
			if (result) {
				$grid.datagrid('reload');
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	}
};
	$(function() {
		$("#sex").combobox({
	        url: system.contextPath + '/system/systemDictController/getByCode/10.2.3/',
	        valueField: "code",
	        textField: "name",
	        panelHeight: "auto",
	        editable: false
	    });
		// extend the 'equals' rule
		/* $.extend($.fn.validatebox.defaults.rules, {
		    equals: {
		        validator: function(value,param){
		            return value == $(param[0]).val();
		        },
		        message: '两次密码不一致.'
		    }
		}); */
		
		if ($(':input[name="id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(system.contextPath + '/system/systemUserController/getById/${id[0]}', {
				id : $(':input[name="id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'name' : result.name,
						'loginName' : result.loginName,
						'sex' : result.sex,
						'age' : result.age,
						'createTime' : result.createTime,
						'pwd' : result.pwd,
						'rpwd' : result.pwd,
						'lastLoginTime' : result.lastLoginTime
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
		<fieldset>
			<table class="table" style="width: 100%;">
				<tr>
					<td>编号</td>
					<td><input name="id" value="${id[0]}" class="easyui-textbox" readonly="readonly" data-options="height:30"/></td>
					<td>登陆名称</td>
					<td><input name="loginName" class="easyui-textbox" data-options="required:true,height:30"/></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input id="pwd" name="pwd" type="password" class="easyui-textbox" data-options="required:true,height:30" /> </td>
					<td>确认密码</td>
					<td><input id="rpwd" name="rpwd" type="password" class="easyui-textbox"     
   						 	validType="same['#pwd']" data-options="required:true,height:30"/> </td>
				</tr>
				<tr>
					<td>姓名</td>
					<td><input name="name" class="easyui-textbox" data-options="height:30"/></td>
					<td>性别</td>
					<td><input id="sex" name="sex" class="easyui-combobox" data-options="required:true,height:30"/></td>
				</tr>
				<tr>
					<td>年龄</td>
					<td><input name="age"  class="easyui-numberbox" data-options="height:30"/></td>
					<td></td>
					<td>
						<!-- <input name="lastLoginTime" readonly="readonly" class="easyui-textbox" data-options="height:30"/> -->
						<input type="hidden" name="lastLoginTime"/>
						<input type="hidden" name="createTime"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
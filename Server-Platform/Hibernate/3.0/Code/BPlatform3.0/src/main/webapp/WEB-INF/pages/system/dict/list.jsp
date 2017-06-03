<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript">
	var grid;
	var addFun = function() {
		var dialog = parent.system.modalDialog({
			title : '添加字典信息',
			border: false,
			height:430,
			url : system.contextPath + '/system/systemDictController/form',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var showFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '查看字典信息',
			border: false,
			height:430,
			url : system.contextPath + '/system/systemDictController/form?id='+id
		});
	};
	var editFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '编辑字典信息',
			border: false,
			height:430,
			url : system.contextPath + '/system/systemDictController/form?id='+id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(system.contextPath + '/system/systemDictController/delete/'+id, {
					id : id
				}, function() {
					grid.treegrid('reload');
				}, 'json');
			}
		});
	};
	var grantFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '字典授权',
			border: false,
			height:430,
			url : system.contextPath + '/system/systemDictController/grant/id=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid').treegrid({
			title : '',
			url : system.contextPath + '/system/systemDictController/treeGrid',
			idField : 'id',
			treeField : 'name',
			parentField : 'parentId',
			state : 'closed',
			striped : true,
			singleSelect : true,
			sortName : 'createTime',
			sortOrder : 'asc',
			rownumbers : true,
			pagination : false,
			frozenColumns : [ [ {
				width : '20%',
				title : '字典名称',
				field : 'name',
				sortable : true
			}, {
				width : '10%',
				title : '英文名称',
				field : 'enName',
				sortable : true
			}, {
				width : '10%',
				title : '字典编号',
				field : 'code',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '15%',
				title : '创建时间',
				field : 'createTime',
				sortable : true
			}, {
				width : '22%',
				title : '字典描述',
				field : 'description'
			}, {
				title : '操作',
				field : 'action',
				width : '20%',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("/base/syrole!getById")) {%>
						str += system.formatString('<a href="javascript:void(0);" onclick="showFun(\'{0}\');">[查看]</a>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/syrole!update")) {%>
						str += system.formatString('<a href="javascript:void(0);" onclick="editFun(\'{0}\');">[编辑]</a>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/syrole!delete")) {%>
						str += system.formatString('<a href="javascript:void(0);" onclick="removeFun(\'{0}\');"">[删除]</a>', row.id);
					<%}%>
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
			}
		});
//		$('#grid').datagrid('getPanel').removeClass('lines-both lines-right lines-bottom').addClass('lines-no');
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<%if (securityUtil.havePermission("/base/syrole!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton c1" data-options="plain:true" onclick="addFun();"><img style="margin: 1px 3px 0 0;border: 0" src="<%=request.getContextPath()%>/resources/style/images/icon_tianjia.png" />添加</a></td>
				<%}%>
				<td><div class="datagrid-btn-separator"></div></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true"></table>
	</div>
</body>
</html>
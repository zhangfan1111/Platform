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
			title : '添加资源信息',
			border: false,
			height: 450,
			url : system.contextPath + '/system/systemResourceController/systemResourceForm',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parent.mainMenu);
				}
			} ]
		});
	};
	var showFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '查看资源信息',
			border: false,
			height: 450,
			url : system.contextPath + '/system/systemResourceController/systemResourceForm?id='+ id
		});
	};
	var editFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '编辑资源信息',
			border: false,
			height: 450,
			url : system.contextPath + '/system/systemResourceController/systemResourceForm?id='+ id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, parent.mainMenu);
				}
			} ]
		});
	};
	var removeFun = function(id) {
		parent.$.messager.confirm('询问', '您确定要删除此记录？', function(r) {
			if (r) {
				$.post(system.contextPath + '/system/systemResourceController/delete/'+id, {
					id : id
				}, function() {
					grid.treegrid('reload');
					parent.mainMenu.tree('reload');
				}, 'json');
			}
		});
	};
	var redoFun = function() {
		var node = grid.treegrid('getSelected');
		if (node) {
			grid.treegrid('expandAll', node.code);
		} else {
			grid.treegrid('expandAll');
		}
	};
	var undoFun = function() {
		var node = grid.treegrid('getSelected');
		if (node) {
			grid.treegrid('collapseAll', node.code);
		} else {
			grid.treegrid('collapseAll');
		}
	};
	$(function() {
		grid = $('#grid').treegrid({
			title : '',
			url : system.contextPath + '/system/systemResourceController/treeGrid',
			idField : 'code',
			treeField : 'name',
			parentField : 'parentCode',
			state : 'closed',
			nodeIconClsAtrribute : 'icon',
			rownumbers : true,
			pagination : false,
			lines:true,
			sortName : 'orderCode',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				width : '15%',
				title : '资源名称',
				field : 'name'
			} ] ],
			columns : [ [ {
				width : '10%',
				title : '编码',
				field : 'code'
			},{
				width : '12%',
				title : '图标名称',
				field : 'icon'
			}, {
				width : '10%',
				title : '资源类型',
				field : 'type',
				formatter : function(value, row) {
					return value;
				}
			}, {
				width : '15%',
				title : '资源路径',
				field : 'url',
				formatter : function(value, row) {
					if(value){
						return system.formatString('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				width : '10%',
				title : '资源描述',
				field : 'description',
				formatter : function(value, row) {
					if(value){
						return system.formatString('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				width : '5%',
				title : '排序码',
				field : 'orderCode',
				hidden : false
			}, {
				width : '5%',
				title : '目标',
				field : 'position'
			}, {
				title : '操作',
				field : 'action',
				width : '15%',
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
			onBeforeLoad : function(row, param) {
				if(row) {
					param['code'] = row.code;
					param['id'] = row.id;
				}
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(row, data) {
				if(data == null || data.length==0) {
					row.state='open';
				}
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
			},
			onExpand : function(row, data) {
			}
		});
		
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<%if (securityUtil.havePermission("/base/syresource!save")) {%>
				<td><a href="javascript:void(0);" class="easyui-linkbutton c1" data-options="plain:true" onclick="addFun();"><img style="margin: 1px 3px 0 0;border:0" src="<%=request.getContextPath()%>/resources/style/images/icon_tianjia.png" />添加</a></td>
				<%}%>
				<td><div class="datagrid-btn-separator"></div></td>
				<td><a onclick="redoFun();" href="javascript:void(0);" class="easyui-linkbutton c1" data-options="plain:true"><img style="margin: 1px 3px 0 0;border:0" src="<%=request.getContextPath()%>/resources/style/images/icon_zdzk.png" />展开</a><a onclick="undoFun();" href="javascript:void(0);" style="margin-left: 6px" class="easyui-linkbutton c1" data-options="plain:true"><img style="margin: 1px 3px 0 0;border: 0" src="<%=request.getContextPath()%>/resources/style/images/icon_zdzk.png" />折叠</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
				<td><a onclick="grid.treegrid('reload');" href="javascript:void(0);" class="easyui-linkbutton c1" data-options="plain:true"><img style="margin: 1px 3px 0 0;border:0" src="<%=request.getContextPath()%>/resources/style/images/icon_shuaxin_bai.png" />刷新</a></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true"></table>
	</div>
</body>
</html>
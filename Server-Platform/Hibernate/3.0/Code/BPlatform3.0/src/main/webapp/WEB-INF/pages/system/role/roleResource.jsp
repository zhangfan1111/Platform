<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript">
	var resourceTreegrid,roleTree,currentRole;
	var submitForm = function() {
		if(!currentRole) {
			alert('请选择角色！');
			return;
		}
		var nodesAdd = resourceTreegrid.treegrid('getAddChecked');
		var nodesDelete = resourceTreegrid.treegrid('getDeleteChecked');
		if((!nodesAdd || nodesAdd.length==0) && (!nodesDelete || nodesDelete.length==0)) {
			alert('你没有进行任何授权更改！');
			return;
		}
		
		var addIds = [];
		var delIds = [];
		for (var i = 0; i < nodesAdd.length; i++) {
			addIds.push(nodesAdd[i].id);
		}
		for (var i = 0; i < nodesDelete.length; i++) {
			delIds.push(nodesDelete[i].id);
		}
		var po = {
					master:'SYSTEM_ROLE',
					masterValue:currentRole.id,
					access:'SYSTEM_RESOURCE',
					addAccessIds:addIds,
					delAccessIds:delIds
				};
		var params = $.param(po,true);
		
		parent.$.messager.progress({
			text : '授权处理中，请稍等....'
		});
		$.post(system.contextPath + '/system/systemPrivilegeController/modifyPrivilege?'+params, null, function(result) {
			$('.iconImg').attr('src', system.pixel_0);
			parent.$.messager.progress('close');
			
			if (result.success) {
				$("#resourceTreegrid").treegrid("clearChecked");
				parent.$.messager.alert('提示', '分配成功！', 'info');
			} else {
				parent.$.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	};
	
	$(function() {
		roleTree = $('#roleTree').tree({
			url : system.contextPath + '/system/systemRoleController/treeGrid',
			parentField : 'parentId',
			lines:true,
			formatter : function(node) {
				return node.name;
			},
			onBeforeLoad : function(param) {
				/* parent.$.messager.progress({
					text : '角色加载中....'
				}); */
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', system.pixel_0);
			//	parent.$.messager.progress('close');
			},
			onBeforeSelect : function(node) {
				var selNode = roleTree.tree('getSelected');
				if(selNode && (selNode.id === node.id)) {
					return false;
				}
			},
			onSelect : function(node) {
				currentRole = node;
				resourceTreegrid.treegrid('reload');
				$("#resourceTreegrid").treegrid("clearChecked");
			}
		});
		
		resourceTreegrid = $('#resourceTreegrid').treegrid({
			title : '',
			url : system.contextPath + '/system/systemPrivilegeController/loadSystemResource',
			idField : 'id',
			treeField : 'name',
			parentField : 'parendId',
			state : 'closed',
			nodeIconClsAtrribute : 'icon',
			rownumbers : true,
			pagination : false,
			sortName : 'orderCode',
			sortOrder : 'asc',
			singleSelect: false,
			singleCheck: false,
			selectOnCheck: true,
			checkOnSelect: false,
			frozenColumns : [ [  {
				field : 'ck',
				checkbox:true
			},{
				width : '20%',
				title : '资源名称',
				field : 'name'
			} ] ],
			columns : [ [ {
				width : '10%',
				title : '编码',
				field : 'code',
				hidden : false
			}, {
				width : '15%',
				title : '图标名称',
				field : 'icon'
			}, {
				width : '5%',
				title : '资源类型',
				field : 'type',
				formatter : function(value, row) {
					return value;
				}
			}, {
				width : '30%',
				title : '资源路径',
				field : 'url',
				formatter : function(value, row) {
					if(value){
						return system.formatString('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				width : '13%',
				title : '资源描述',
				field : 'description',
				formatter : function(value, row) {
					if(value){
						return system.formatString('<span title="{0}">{1}</span>', value, value);
					}
				}
			} ] ],
			toolbar : [{
                text: '保存',
                width: 50,
             //   iconCls: 'ext-icon-disk',
                handler: submitForm
            },{text:"|"},/* {
                text: '测试',
                handler: function() {
                	var nodesAdd = resourceTreegrid.treegrid('getAddChecked');
                	console.log('nodesAdd',nodesAdd);
            		var nodesDelete = resourceTreegrid.treegrid('getDeleteChecked');
                	console.log('nodesDelete',nodesDelete);
            		var defaultChecked = resourceTreegrid.treegrid('getDefaultChecked');
                	console.log('defaultChecked',nodesDelete);
                }
            } */],
			onBeforeLoad : function(row, param) {
				param['master'] = 'SYSTEM_ROLE';
				if(currentRole) {
					param['masterValue'] = currentRole.id;
				}
				if(row) {
					param['id'] = row.id;
				}
				parent.$.messager.progress({
					text : '资源加载中....'
				}); 
			},
			onLoadSuccess : function(row, data) {
				if(data == null || data.length==0) {
					row.state='open';
				}
				$(data).each(function(index,item){
					if(item.isChecked === true) {
						$("#resourceTreegrid").treegrid("checkRowDefault", item.id); 
						$("#resourceTreegrid").treegrid("addDefaultCheck", item); 
					} else {
						$("#resourceTreegrid").treegrid("uncheckRowDefault", item.id); 
					}
				});
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
			},
			onExpand : function(row, data) {
			}
		});
	//	$('#grid').datagrid('getPanel').removeClass('lines-both lines-right lines-bottom').addClass('lines-no');
	});
</script>
</head>
<body>
	<div id="bt_loading" class="loading"></div>
	<div class="easyui-layout" fit="true">
    
    <div region="center" title="系统资源" id="bt_function_laout_center">
    	<table id="resourceTreegrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div region="west" style="width: 200px;" title="角色">
        <ul id="roleTree"></ul>
    </div>
</body>
</html>
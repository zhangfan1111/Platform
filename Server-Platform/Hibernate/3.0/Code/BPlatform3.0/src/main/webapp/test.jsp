<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>TEST</title>
<%-- 引入jQuery --%>
<!-- <script src='${app}/resources/js/system/jquery-1.9.1.js' type='text/javascript' charset='utf-8'></script>; -->
<script src='${app}/resources/js/system/jquery-2.0.3.js' type='text/javascript' charset='utf-8'></script>;

<%-- 引入EasyUI --%>
<link id="easyuiTheme" rel="stylesheet" href="${app}/resources/js/system/jquery-easyui-1.3.6/themes/default/easyui.css" type="text/css">
<!-- <link rel="stylesheet" href="${app}/resources/js/system/jquery-easyui-1.3.6/themes/icon.css" type="text/css"> -->
<script type="text/javascript" src="${app}/resources/js/system/jquery-easyui-1.3.6/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${app}/resources/js/system/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript">
var system = system || {};
system.contextPath = '${app}';
system.pixel_0 = '${app}/resources/style/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>

<script type="text/javascript">
	var resourceTreegrid,roleTree;
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
		$.post('/system/systemUserController/modifyRole?'+params, null, function(result) {
			if (result.success) {
				$dialog.dialog('destroy');
				$pjq.messager.alert('提示', '分配成功！', 'info');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	};
	function aaaaa(){
		alert('asd');
	}
	$(function() {
		roleTree = $('#roleTree').tree({
			url : '${app}/system/systemRoleController/treeGrid',
			parentField : 'parentId',
			formatter : function(node) {
				return node.name;
			},
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '角色加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
			}
		});
		
		resourceTreegrid = $('#resourceTreegrid').treegrid({
			title : '',
			url : '${app}/system/systemPrivilegeController/loadSystemResource',
			idField : 'id',
			treeField : 'name',
			rownumbers : true,
			pagination : false,
			sortName : 'orderCode',
			sortOrder : 'asc',
			singleSelect: false,
			singleCheck: false,
			frozenColumns : [ [ {
				/* title:"<input id='asdf' type='checkbox'/>",
				field:'isChecked',
				width:180,
				formatter:function(rowData,rowIndex){
		                return "<input type='checkbox' id='ck_"+rowData.id+"' "+(rowData.isChecked?'checked':'')+" checked/>";
	        	} */
	        	checkbox:true
			},{
				width : '200',
				title : '资源名称',
				field : 'name'
			} ] ],
			columns : [ [ {
				width : '80',
				title : '编码',
				field : 'code',
				hidden : false
			}, {
				width : '200',
				title : '图标名称',
				field : 'icon'
			}, {
				width : '60',
				title : '资源类型',
				field : 'type',
				formatter : function(value, row) {
					return value;
				}
			}, {
				width : '200',
				title : '资源路径',
				field : 'url',
				formatter : function(value, row) {
					if(value){
						return system.formatString('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				width : '200',
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
                iconCls: 'ext-icon-disk',
                handler: null
            },{
                text: '测试',
                handler: function() {
/*                 	var nodesAdd = resourceTreegrid.treegrid('getAddChecked');
                	console.log(nodesAdd);
            		var nodesDelete = resourceTreegrid.treegrid('getDeleteChecked');
                	console.log(nodesDelete); */
                	$("#resourceTreegrid").treegrid("checkRow", 1); 
                }
            }],
			onBeforeLoad : function(row, param) {
				if(row) {
					param['code'] = row.code;
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
				 
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
			},
			onExpand : function(row, data) {
			}
		});
	});
</script>

</head>

<body>
	<div id="bt_loading" class="loading"></div>
	<div class="easyui-layout" fit="true">
    
    <div region="center" title="资源列表" id="bt_function_laout_center">
    	<table id="resourceTreegrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div region="west" style="width: 200px;" title="角色" split='true'>
        <ul id="roleTree"></ul>
    </div>
</body>
</html>
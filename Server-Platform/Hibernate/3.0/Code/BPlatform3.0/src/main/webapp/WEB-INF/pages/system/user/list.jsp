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
			title : '添加用户信息',
			border: false,
			height: 340,
			url : system.contextPath + '/system/systemUserController/form',
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
			title : '查看用户信息',
			border: false,
			height: 340,
			url : system.contextPath + '/system/systemUserController/form?id='+ id
		});
	};
	var editFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '编辑用户信息',
			border: false,
			height: 350,
			url : system.contextPath + '/system/systemUserController/form?id=' + id,
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
				$.post(system.contextPath + '/system/systemUserController/delete', {
					id : id
				}, function() {
					grid.datagrid('reload');
				}, 'json');
			}
		});
	};
	var grantRoleFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '用户角色授权',
			border: false,
			height: 340,
			url : system.contextPath + '/system/systemUserController/roleGrant?userId=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	var grantOrganizationFun = function(id) {
		var dialog = parent.system.modalDialog({
			title : '修改机构',
			border: false,
			height: 340,
			url : system.contextPath + '/system/systemUserController/deptGrant?userId=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			} ]
		});
	};
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : system.contextPath + '/system/systemUserController/dataGrid/',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createTime',
			sortOrder : 'desc',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '10%',
				title : '登录名',
				field : 'loginName',
				sortable : true
			}, {
				width : '15%',
				title : '姓名',
				field : 'name',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '15%',
				title : '创建时间',
				field : 'createTime',
				sortable : true
			}, {
				width : '15%',
				title : '修改时间',
				field : 'updateTime',
				sortable : true
			}, {
				width : '15%',
				title : '最后登录时间',
				field : 'lastLoginTime',
				sortable : true
			}, {
				width : '8%',
				title : '性别',
				field : 'sex',
				sortable : true,formatter:
                    function(value,rec){
            	  	var sex = system.dictCode2Name(rec.sex);
				    return sex; 
				  }},   
		    {
				width : '10%',
				title : '年龄',
				field : 'age',
				hidden : true
			}, {
				width : '5%',
				title : '照片',
				field : 'photo',
				hidden: true,
				formatter : function(value, row) {
					if(value){
						return system.formatString('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				title : '操作',
				field : 'action',
				width : '20%',
				formatter : function(value, row) {
					var str = '';
					<%if (securityUtil.havePermission("/base/syuser!getById")) {%>
						str += system.formatString('<a href="javascript:void(0);" onclick="showFun(\'{0}\');">[查看]</a>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/syuser!update")) {%>
						str += system.formatString('<a href="javascript:void(0);" onclick="editFun(\'{0}\');">[编辑]</a>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/syuser!grantRole")) {%>
						str += system.formatString('<a href="javascript:void(0);"  onclick="grantRoleFun(\'{0}\');"">[角色]</a>', row.id);
					<%}%>
					<%if (securityUtil.havePermission("/base/syuser!grantOrganization")) {%>
						str += system.formatString('<a href="javascript:void(0);"  onclick="grantOrganizationFun(\'{0}\');"">[组织]</a>', row.id);
					<%}%>						
					<%if (securityUtil.havePermission("/base/syuser!delete")) {%>
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
				var p = $(this).datagrid('getPager');  
			    $(p).pagination({ 
			    	layout:['sep','first','prev','links','next','last','sep','refresh']
			    });
				
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
			}
		});
		
//		$('#grid').datagrid('getPanel').removeClass('lines-both lines-right lines-bottom').addClass('lines-no');
		
		$("#sex").combobox({
	        url: system.contextPath + '/system/systemDictController/getByCode/10.2.3/',
	        valueField: "code",
	        textField: "name",
	        panelHeight: "auto",
	        editable: false
	    });
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height:40px">
		<table>
			<tr>
				<td>
					<table>
						<tr>
							<%if (securityUtil.havePermission("/base/syuser!save")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton c1" data-options="plain:true" onclick="addFun();"><img style="margin: 1px 3px 0 0;border: 0" src="<%=request.getContextPath()%>/resources/style/images/icon_tianjia.png" />添加</a></td>
							<%}%>
							<td><div class="datagrid-btn-separator"></div></td>
							<td>
								<input id="searchBox" class="easyui-textbox" style="width: 150px;height: 27px"
									data-options="prompt:'搜索用户名'"/>
							</td>
							<td>
								<a href="javascript:void(0);" class="easyui-linkbutton c1"
								data-options="plain:true"
								onclick="grid.datagrid('load',{'search':$('#searchBox').textbox('getValue')});">
									查询
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="grid" data-options="fit:true"></table>
	</div>
</body>
</html>
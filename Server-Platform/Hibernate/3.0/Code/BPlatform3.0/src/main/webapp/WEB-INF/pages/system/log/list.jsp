<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript">
	var grid;
	$(function() {
		grid = $('#grid').datagrid({
			title : '',
			url : system.contextPath + '/system/systemLogController/dataGrid/',
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
				width : '30%',
				title : '日志内容',
				field : 'logContent',
				sortable : true
			}, {
				width : '20%',
				title : '操作人IP',
				field : 'note',
				sortable : true
			} ] ],
			columns : [ [ {
				width : '20%',
				title : '浏览器',
				field : 'broswer',
				sortable : true
			}, {
				width : '28%',
				title : '操作时间',
				field : 'operateTime',
				sortable : true
			} /*, {
				width : '200',
				title : '操作人',
				field : 'systemUser.name',
				sortable : true,
				formatter : function(value, row) {
					var str = '';
					str += row.systemUser.name;
					return str;
				}
			}*/ ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$(this).datagrid('getPanel').removeClass('lines-both lines-right lines-bottom').addClass('lines-no');
				
				 var p = $(this).datagrid('getPager');  
			     $(p).pagination({ 
			    	 layout:['sep','first','prev','links','next','last','sep','refresh']
			     });
			     
				$('.iconImg').attr('src', system.pixel_0);
				parent.$.messager.progress('close');
				
			}
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
							<%-- <%if (securityUtil.havePermission("/base/syuser!save")) {%>
							<td><a href="javascript:void(0);" class="easyui-linkbutton c1" data-options="plain:true" onclick="addFun();"><img style="margin: 1px 3px 0 0" src="<%=request.getContextPath()%>/resources/style/images/icon_tianjia.png" />添加</a></td>
							<%}%>
							<td><div class="datagrid-btn-separator"></div></td> --%>
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
		<table id="grid" data-options="fit:true,border:true"></table>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.memory.platform.web.session.SessionInfo"%>
<%
	SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
%>
<!DOCTYPE html>
<html>
<head>
<title>添加收款</title>
<%@ include file="/jsp/common/base.jsp"%>
<script type="text/javascript">
	var reType = 'line';//图表类型
	var reparamater;//图表参数集
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});

		function pa(data,type,paramaters) {
			var paramater = paramaters.split(',');
			return {
				exporting : {
					filename : paramater[0] + '分布'
				},
				chart : {
					renderTo: 'container',
		            type: 'column',
		            margin: 75,
		            /* options3d: {
		                enabled: true,
		                alpha: 15,
		                beta: 15,
		                depth: 50,
		                viewDistance: 25
		            } */
				},
				xAxis: {
		            categories: data[0],   //指定x轴分组
		            labels: {
		                rotation: -45
		            }
		        },
		        yAxis: {
		            title: {
		                text: paramater[2] + '(' +paramater[1] + ')'                //指定y轴的标题
		            }
		        },
				title : {
					text : paramater[0] + '分布',
					x: -20 //center
				},
				subtitle: {
		            text: 'Source: WorldClimate.com',
		            x: -20
		        },
				tooltip : {
					pointFormat : '{series.name}: <b>{point.y}</b>',
					valueSuffix: paramater[1]//Y轴单位
				},
				legend: {//X轴选项列表及样式
			        layout: 'vertical',
			        align: 'right',
			        verticalAlign: 'middle',
			        borderWidth: 0
			    },
			    plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: false,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            },
		            column:{
		            }
		        },
				noData : {
					text: '没有数据!',
				},
				series : [ {
					type : type,
					name : paramater[2],
					data : data[1],
					events: {
			            click: function(e) {
			            	//alert(e.point.y);
			                		//location.href = e.point.url;
			                        //上面是当前页跳转，如果是要跳出新页面，那就用
			                        //window.open(e.point.url);
			                        //这里的url要后面的data里给出
			                }
			            },
	            dataLabels: {//在图表上显示数值
                    enabled: true
                },
				}]
			};
		}
		
		
		function reload(url,type,paramaters) {
			$.post(url, function(result) {
				$('#container').highcharts(pa(result,type,paramaters));
				var trs = '';
				$.each(result[1], function(index, item) {
					trs += system.formatString('<tr><td>{0}</td><td>{1}</td></tr>', item.name, item.y);
				});
				$('table tr td table').html('<tr><th>分析维度</th><th>数量</th></tr>');
				$('table tr td table').append(trs);

				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<div data-options="region:'north',border:false" style="height:40px">
		<table class="table">
			<tr>
				<th>
					多维度分析图表
				</th>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table style="width: 100%; height: 100%">
			<tr>
				<td style="width: 60%">
					<div id="container"></div>
				</td>
				<td valign="top">
					<table class="table" style="margin-left: 20px;">
						<tr>
							<th>分析维度</th>
							<th>数量</th>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
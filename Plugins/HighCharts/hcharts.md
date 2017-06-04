#### 说 明 ####
```
  hcharts是一个 兼容 IE6+、完美支持移动端、图表类型丰富、方便快捷的 HTML5 交互性图表库。
  Highcharts 在 4.2.0 开始已经不依赖 jQuery 了，直接用其构造函数既可创建图表。
  主要类型分为以下几大类：
  1. 线图（折线图及曲线图）
  2. 面积图
  3. 柱形图（柱状及条形图）
  4. 饼状图（饼状及环形图）
  5. 散点图及气泡图
  6. 混合图
  7. 动态交互图
  8. 3D图
  9. 仪表图
  10. 热力图(热力及矩形树图)
  11. 其他（极地、蜘蛛、瀑布、金字塔等等）<br>
  详情参考官方介绍 https://www.hcharts.cn/demo/highcharts#line

```
##### 1: 基础折线图示例 #####
 ```
    Step1 引入需要的js库:基本js、导出功能js(表格、图片等)、中文支持、主题等等根据自己的需求进行引入即可
          参见CDN  https://img.hcharts.cn/index.html#jquery  了解更多插件
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="https://img.hcharts.cn/highcharts/themes/sand-signika.js"></script>
 ```

 ``` 
    Step2 需要一个div来显示图形，根据自己的具体需求修改
    <div id="container" style="min-width:400px;height:400px"></div>
 ```
 ```
  // Step3  初始化图并显示,可以根据需求传入参数、但是data和xArray是必不可少的（图表数据、横轴显示信息）,下面是一个hcharts的示例程序
    ## ajax发送两个参数、后台查询获取满足条件的数据并对数据进行封装
     $.ajax({
  			type : "POST",
  			url : system.contextPath + '/checkCountController/checkCount/visitorCount',
  			data : {
  				type : type,
  				date : time
  			},
  			dataType : "json",
  			success : function(data){
  				var timeArray=[]; // 图表数据data
  				var xArray=[]; // 横轴数据
  				var array=time.split('-');
  				var d= new Date(array[0], array[1], 0);  
  				var day = d.getDate();
  				for(i=1;i<=day;i++){
  					var ti=0;
  					for(var j=0;j<data.length;j++){
  						var str = data[j].visit_date;
  						if(parseInt(str.substring(str.length - 2,str.length))==i){
  							ti=data[j].total;
  						}
  					}
            // 循环将数据放入数组中
              timeArray.push(ti);
              xArray.push(i);
  				}
  				getHighcharts(timeArray,xArray,type);
  			},
  			error : function(){
  				$.message.alert("提示:","请求失败!");
  			}
  		}); 
      
    // 获取数据
    @Override
    public List<Map> visitorCount(String type, String date)
        throws Exception {
      StringBuffer sb = new StringBuffer();
      sb.append("select *,COUNT(vi.visit_date) total from vr_visitor vi where 1=1 and type =  " + Integer.parseInt(type));
      if(StringUtils.isNotEmpty(date)){
        sb.append(" and '" + date +  "' = DATE_FORMAT(vi.visit_date, '%Y-%m')");
      }else{
        sb.append(" and DATE_FORMAT(NOW(), '%Y-%m') = DATE_FORMAT(vi.visit_date, '%Y-%m')");
      }
      sb.append(" GROUP BY vi.visit_date ORDER BY vi.visit_date");
      List<Map> list = checkCountInfoDao.findBySql(sb.toString());
      return list;
    }
  // 根据返回的数据绘制图表即可
  function getHighcharts(data,xArray,type){
    if(type == 0){
      picType = "企业";
    } else if (type == 1){
      picType = "商铺";
    }
    $('#container').highcharts({ // 对这个div进行绘制图表即可
      chart: {
              type: 'spline', // 
              margin: [10] //距离上下左右的距离值
        },
      title: {
            text: '' // 图标题
        },
        yAxis: {
            title: {
                text: '访问量' // 纵轴显示文字
            }
        },
        xAxis: {
          categories:xArray, //横轴的数据  
          },
        plotOptions: {
            series: {
                pointStart: 0 // 横轴起始点
            }
        },
        series: [{ // 折现序列、即一个序列代表一条线
            name: picType + '访问量折线图',
            data: data
        }]
    });
  }

// 下面为官方的基本折线图js代码（参考）
/**
  * Highcharts 在 4.2.0 开始已经不依赖 jQuery 了，直接用其构造函数既可创建图表
 **/
var chart = new Highcharts.Chart('container', {
    title: {
        text: '不同城市的月平均气温',
        x: -20
    },
    subtitle: {
        text: '数据来源: WorldClimate.com',
        x: -20
    },
    xAxis: {
        categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
    },
    yAxis: {
        title: {
            text: '温度 (°C)'
        },
        plotLines: [{
            value: 0,
            width: 1,
            color: '#808080'
        }]
    },
    tooltip: {
        valueSuffix: '°C'
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    series: [{
        name: '东京',
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
    }, {
        name: '纽约',
        data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
    }, {
        name: '柏林',
        data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
    }, {
        name: '伦敦',
        data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
    }]
});
  https://code.hcharts.cn/demos/hhhhxL
```

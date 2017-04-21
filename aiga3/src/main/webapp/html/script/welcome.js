define(function(require,exports,module){
	// 通用工具模块
	var Utils = require("global/utils.js");
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('welcome');

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			// 注册日历控件
		    Page.findId('onlineCalendar').datepicker();

		    // 注册流水滚动条
		    Page.findId('onlineLog').slimScroll({
                "height": 570
            });

		    // 图标
			var myChart = echarts.init(Page.findId('onlineCharts')[0]);

		    // 指定图表的配置项和数据
		    var option = {
			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            crossStyle: {
			                color: '#999'
			            }
			        }
			    },
			    toolbox: {
			        feature: {
			            /*dataView: {show: true, readOnly: false},*/
			            magicType: {show: true, type: ['line', 'bar']},
			            restore: {show: true},
			            saveAsImage: {show: true}
			        }
			    },
			    legend: {
			        data:['上次变更数量','异常数量','故障数量','前台成功率','esb成功率','CBOSS成功率'],
			         y: 'bottom'
			    },
			    xAxis: [
			        {
			            type: 'category',
			            data: ['1月','2月','3月','4月','5月','6月'],
			            axisPointer: {
			                type: 'shadow'
			            }
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            name: '',//故障数
			            min: 0,
			            max: 100,
			            interval: 10,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        },
			        {
			            type: 'value',
			            name: '',//上线次数
			            min: 0,
			            max: 10,
			            interval: 2,
			            axisLabel: {
			                formatter: '{value}'
			            }
			        }
			    ],
			    series: [
			        {
			            name:'上次变更数量',
			            type:'bar',
			            itemStyle: {
			                normal: {
			                    color:'#44abe5'
			                }
			            },
			            data:[20, 30, 25, 23, 25, 36]
			        },
			        {
			            name:'异常数量',
			            type:'bar',
			            itemStyle: {
			                normal: {
			                    color:'#8ac14a'
			                }
			            },
			            data:[26, 29, 30, 36, 38, 40]
			        },
			        {
			            name:'故障数量',
			            type:'bar',
			            itemStyle: {
			                normal: {
			                    color:'#ff9a1e'
			                }
			            },
			            data:[20, 22, 33, 35, 43, 46]
			        },{
			            name:'前台成功率',
			            type:'line',
			            yAxisIndex: 1,
			            itemStyle: {
			                normal: {
			                    color:'#44abe5'
			                }
			            },
			            data:[3.8, 4.2, 4.3, 4.5, 6.3, 9.1]
			        },{
			            name:'esb成功率',
			            type:'line',
			            yAxisIndex: 1,
			            itemStyle: {
			                normal: {
			                    color:'#8ac14a'
			                }
			            },
			            data:[3.3, 4.2, 4.7, 5.5, 8.3, 9.5]
			        },{
			            name:'CBOSS成功率',
			            type:'line',
			            yAxisIndex: 1,
			            itemStyle: {
			                normal: {
			                    color:'#ff9920'
			                }
			            },
			            data:[4.0, 4.5, 4.7, 5.5, 6.3, 7.5]
			        }
			    ]
			};

		    // 使用刚指定的配置项和数据显示图表。
		    myChart.setOption(option);
		}
	}
    module.exports = Query;
});


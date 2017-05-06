define(function(require,exports,module){
	// 通用工具模块
	var Utils = require("global/utils.js");
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('welcome');

	// 获取总计结果 入参：planDate=2017-05-04
    srvMap.add("getWelcomeCaseCount", "welcome/getWelcomeCaseCount.json", "/sys/home/caseCount");
    // 流程节点接口
    srvMap.add("getWelcomeFlowList", "welcome/getWelcomeFlowList.json", "/sys/home/flowList");
    srvMap.add("getWelcomeInformation", "welcome/getWelcomeInformation.json", "/sys/home/information");


    var Data = {
        planDate:'2017-05-04' // 获取日期日期
    }

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			this.getWelcomeCaseCount();
			this.getWelcomeFlowList();

			// 注册日历控件
		    Page.findId('onlineCalendar').datepicker();

		    // 注册流水滚动条
		    Page.findId('getWelcomeFlowList').slimScroll({
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
		},
        getWelcomeCaseCount: function() { // 获取流水结果信息
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            var cmd = "planDate="+Data.planDate;
            Rose.ajax.getJson(srvMap.get('getWelcomeCaseCount'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('getWelcomeCaseCount'));
                    Page.findId('getWelcomeCaseCount').append(template(json.data))
                }
            });
        },
        getWelcomeFlowList: function() { // 获取流水结果信息
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            var cmd = "planDate="+Data.planDate;
            Rose.ajax.getJson(srvMap.get('getWelcomeFlowList'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('getWelcomeFlowList'));
                    Page.findId('getWelcomeFlowList').append(template(json.data))
                    var _data = json.data[0];
                    var _maxNum = parseInt(_data.maxNum);
        			var _activeNum = parseInt(_data.activeNum);
                    if(_data){
                    	var _html = '';
                    	if (_maxNum > 0) {
				            for (var i=1;i<=_maxNum;i++){
				                if(i ==_activeNum){
				                    _html+='<li class="active">'+i+'</li>';
				                } else {
				                    _html+='<li>'+i+'</li>';
				                }
				            }
				        }
				        //Page.findName("maxNum").html(_maxNum);
				        Page.findName("complimeNum").html(_html);
				        self.getComplimeNumInfo();
                    }

                }
            });
        },
        getComplimeNumInfo: function() { //查询某一次流水
            var self = this;
            var _complimeNum = Page.findName("complimeNum");
            _complimeNum.find("li").unbind('click');
            _complimeNum.find("li").bind('click', function() {
                var _index = parseInt($(this).text())-1;
                $(this).addClass("active").siblings().removeClass("active");
                Page.findId('getWelcomeFlowList').find("ul").eq(_index).removeClass('hide').siblings().addClass('hide')
            });
        }
	}
	Handlebars.registerHelper("setSmallTag", function(str) {
        return str.replace("%","<small>%</small>");
    });
    Handlebars.registerHelper("getTypeClassName", function(type) {
        if (type == 1) {
            return "fa-check bg-blue"; //正处理
        } else if (type == 2) {
            return "fa-check bg-green"; //已处理
        } else if (type == 3) {
            return "fa-check bg-gray"; //未处理
        }
    });
    Handlebars.registerHelper("getTypeTitle", function(type) {
        if (type == 1) {
            return "正处理"; //正处理
        } else if (type == 2) {
            return "已处理"; //已处理
        } else if (type == 3) {
            return "未处理"; //未处理
        }
    });
    Handlebars.registerHelper("checkIsNotHide", function(index,activeNum) {
        var _index = parseInt(index+1);
        var _activeNum = parseInt(activeNum);
        if (_index == _activeNum) {
            return "";
        }else{
        	return "hide";
        }
    });
    module.exports = Query;
});


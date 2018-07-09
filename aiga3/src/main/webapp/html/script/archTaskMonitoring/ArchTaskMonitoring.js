define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchTaskMonitoring/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('taskMonitoring');

	//一周的任务成功情况
    srvMap.add("getTaskMonitoringList", pathAlias+"getList.json", "arch/taskMonitoring/queryByCondition");
    //各个进程一天不同时间段的任务总数
    srvMap.add("getTaskNumCountList", pathAlias+"getList.json", "arch/taskNumCount/queryByTime");

	var init = {


		init: function() {			 
			this._render();
		},
		
		_render: function() {
			//查询
			this._query_event();
			
			//初始化时间框
			function showMonthFirstDay() {
				var date=new Date();
			 	date.setDate(1);
			 	return Rose.date.dateTime2str(date,"yyyy-MM-dd");
			}
			var _form = Page.findId('queryDataForm');
			_form.find('[name="startDate"]').val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
		},		
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			
			// var _dom = Page.findId('taskMonitoringList');
			// var _domPagination = _dom.find("[name='pagination']");
			_cmd = _cmd.replace(/-/g,"/");
			var _form = Page.findId('queryDataForm');
			var lastTime = _form.find('[name="startDate"]').val();
			console.log("lastTime:"+lastTime);
			var date6 = format(new Date(lastTime).getTime()-1000*60*60*24*1);
			var date5 = format(new Date(lastTime).getTime()-1000*60*60*24*2);
			var date4 = format(new Date(lastTime).getTime()-1000*60*60*24*3);
			var date3 = format(new Date(lastTime).getTime()-1000*60*60*24*4);
			var date2 = format(new Date(lastTime).getTime()-1000*60*60*24*5);
			var date1 = format(new Date(lastTime).getTime()-1000*60*60*24*6);
			var timeArr = [date1,date2,date3,date4,date5,date6,lastTime];
			function format(timestamp)
			{
				var time = new Date(timestamp);
				var y = time.getFullYear();
				var m = time.getMonth()+1;
				var d = time.getDate();
				return y+'-'+m+'-'+d;
			}
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//第一个box图标展示一周的任务成功情况
			Rose.ajax.postJson(srvMap.get('getTaskMonitoringList'),_cmd,function(json, status){
				if(status){
					window.XMS.msgbox.hide();
					console.log("进入第一个box");
					var check = [];
					var session = [];
					var report = [];
					var collect = [];
					var successRate = [];

                    for(var i=0;i<json.data.length;i++){
                        check.push(json.data[i].checkTotal);
                        session.push(json.data[i].sessionTotal);
                        report.push(json.data[i].reportTotal);
                        collect.push(json.data[i].collectTotal);
                        successRate.push(json.data[i].successRate);
                    }
					//折线图展示失败成功率
					var myChart = echarts.init(document.getElementById('taskSumAndSuccessful'));
					var maxTaskNum = getMaxTaskNum();
                    option = {
                        title: {
                            text: '周任务运行分布图',
                            textStyle:{
                                color:'black',
                                fontStyle:'normal',
                                fontWeight:'normal',
                                fontFamily:'sans-serif',
                                fontSize:18,
                            }
                        },
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
                                // dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        legend: {
                            data:['check','session','report','collect']
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: timeArr,
                                axisPointer: {
                                    type: 'shadow'
                                }
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '任务数',
                                min: 0,
                                max: maxTaskNum,
                                interval: maxTaskNum/10,
                                axisLabel: {
                                    formatter: '{value}'
                                }
                            },
                            {
                                type: 'value',
                                name: '成功率',
                                min: 0,
                                max: 100,
                                interval: 10,
                                axisLabel: {
                                    formatter: '{value} %'
                                }
                            }
                        ],
                        series: [
                            {
                                name:'check',
                                type:'bar',
                                data:check,
                            },
                            {
                                name:'session',
                                type:'bar',
                                data:session,
                            },
                            {
                                name:'report',
                                type:'bar',
                                data:report,
                            },
                            {
                                name:'collect',
                                type:'bar',
                                data:collect,
                            },
                            {
                                name:'successRate',
                                type:'line',
                                yAxisIndex: 1,
                                data:successRate,
                            }
                        ]
                    };
                    //为了让柱状图数据不超过图高，需要知道任务数的最大值，这里返回一个柱状图内任务数最大的一个值，并且%100==0
                    function getMaxTaskNum(){
					    var maxvalue = 10;
					    var maxArr = [];
					    maxArr[0] = getArrMax(check);
                        maxArr[1] = getArrMax(session);
                        maxArr[2] = getArrMax(report);
                        maxArr[3] = getArrMax(collect);
                        for(var i=0;i<maxArr.length;i++){
                            if(maxArr[i]>maxvalue)
                                maxvalue=maxArr[i];
                        }
                        //maxvalue加上num之后%100==0
                        if(maxvalue%100!=0){
                            var remainder = maxvalue%100;
                            var num = 100-remainder;
                            maxvalue+=num;
                        }
					    return maxvalue;
                    }
                    function getArrMax(array){
					    var max = 0;
					    for(var i=0;i<array.length;i++){
					        if(array[i]>max){
					            max = array[i];
                            }
                        }
                        return max;
                    }
					myChart.setOption(option);
				}else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});

			//第二个box图标展示各个进程一天不同时间段的任务总数
			Rose.ajax.postJson(srvMap.get('getTaskNumCountList'),_cmd,function(json, status){
				if(status){
				    console.log("进入第二个box");
					window.XMS.msgbox.hide();
					var finishDate = [];
                    var checkTotal = [];
                    var sessionTotal = [];
                    var reportTotal = [];
                    var collectTotal = [];
                    var taskTotal = [];

                    for(var i=0;i<json.data.length;i++){
                        finishDate.push(json.data[i].finishDate);
                        checkTotal.push(json.data[i].checkTotal);
                        sessionTotal.push(json.data[i].sessionTotal);
                        reportTotal.push(json.data[i].reportTotal);
                        collectTotal.push(json.data[i].collectTotal);
                        taskTotal.push(json.data[i].taskTotal);
                    }
					var myChart = echarts.init(document.getElementById('taskRuntimeByTime'));
                    option = {
                        title: {
                            text: '各时段任务总数图',
                            textStyle:{
                                color:'black',
                                fontStyle:'normal',
                                fontWeight:'normal',
                                fontFamily:'sans-serif',
                                fontSize:18,
                            }
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['check','session','report','collect','taskTotal']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataZoom: {
                                    yAxisIndex: 'none'
                                },
                                // dataView: {readOnly: false},
                                magicType: {type: ['line', 'bar']},
                                restore: {},
                                saveAsImage: {}
                            }
                        },
                        xAxis:  {
                            type: 'category',
                            boundaryGap: false,
                            data: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]
                        },
                        yAxis: {
                            name: '任务数',
                            type: 'value',
                            axisLabel: {
                                formatter: '{value} '
                            }
                        },
                        series: [
                            {
                                name:'check',
                                type:'line',
                                data:checkTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name:'session',
                                type:'line',
                                data:sessionTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name:'report',
                                type:'line',
                                data:reportTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name:'collect',
                                type:'line',
                                data:collectTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name:'taskTotal',
                                type:'line',
                                data:taskTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                        ]
                    };
                    option.xAxis.data=finishDate;
                    // option.yAxis.data=
					myChart.setOption(option);
				}else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
			});
			
			
		},
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');			 
			Utils.setSelectData(_form);		 
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				var startDate = _form.find("[name='startDate']").val();
				var today = new Date();
				if(new Date(startDate)>today){
					XMS.msgbox.show('查询时间不能超过今天！', 'error', 2000);
					return
				}
				if(startDate == 0) {
					XMS.msgbox.show('查询时间为空！', 'error', 2000);
					return
				}
				self._getGridList(cmd);
			});		
        },
		
	};
				 
	module.exports = init;
	
});
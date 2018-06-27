define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchTaskMonitoring/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('taskMonitoring');

	//显示查询信息表
    srvMap.add("getTaskMonitoringList", pathAlias+"getList.json", "arch/taskMonitoring/queryByCondition");
	
	var cache = {
			datas : ""
		};
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
			
			var _dom = Page.findId('taskMonitoringList');
			var _domPagination = _dom.find("[name='pagination']");
			_cmd = _cmd.replace(/-/g,"/");
			var _form = Page.findId('queryDataForm');
			var lastTime = _form.find('[name="startDate"]').val();
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
			Rose.ajax.postJson(srvMap.get('getTaskMonitoringList'),_cmd,function(json, status){
				if(status){
					window.XMS.msgbox.hide();									
					var fail = [];
					var success = [];
					for(var i=0;i<json.data.length;i++){
						fail.push(json.data[i].failTotal);
						success.push(json.data[i].successTotal);
					}
					//折线图展示失败成功率
					var myChart = echarts.init(document.getElementById('taskSuccessFail'));
					option = {
						    title: {
						        text: '周任务运行情况'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:['成功任务数','失败任务数']
						    },
						    grid: {
						        left: '3%',
						        right: '6%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: ['周一','周二','周三','周四','周五','周六','周日']
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'成功任务数',
						            type:'line',
						            stack: '总量',
						            data:[120, 132, 101, 134, 90, 230, 210]
						        },
						        {
						            name:'失败任务数',
						            type:'line',
						            stack: '总量',
						            data:[120, 132, 101, 134, 90, 230, 210]
						        }
						    ]
						};
					option.series[0].data = success;
					option.series[1].data = fail;
					option.xAxis.data = timeArr;
					myChart.setOption(option);					
				}else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});
			
/*			Rose.ajax.postJson(srvMap.get('getTaskClassSuccessList'),_cmd,function(json, status){
				if(status){
					window.XMS.msgbox.hide();												
					//折线图展示各个task类一周内的成功率
					var myChart = echarts.init(document.getElementById('taskClassSuccess'));
					option = {
						    title: {
						        text: '周任务运行情况'
						    },
						    tooltip: {
						        trigger: 'axis'
						    },
						    legend: {
						        data:['class1','class2']
						    },
						    grid: {
						        left: '3%',
						        right: '6%',
						        bottom: '3%',
						        containLabel: true
						    },
						    toolbox: {
						        feature: {
						            saveAsImage: {}
						        }
						    },
						    xAxis: {
						        type: 'category',
						        boundaryGap: false,
						        data: ['周一','周二','周三','周四','周五','周六','周日']
						    },
						    yAxis: {
						        type: 'value'
						    },
						    series: [
						        {
						            name:'class1',
						            type:'line',
						            stack: '总量',
						            data:[120, 132, 101, 134, 90, 230, 210]
						        },
						        {
						            name:'class2',
						            type:'line',
						            stack: '总量',
						            data:[120, 132, 101, 134, 90, 230, 210]
						        }
						    ]
						};
					option.xAxis.data = timeArr;
					myChart.setOption(option);					
				}else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});*/
			
			
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
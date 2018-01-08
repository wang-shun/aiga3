define(function(require, exports, module) {
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// Page
	var Page = Utils.initPage('indexOperationChart');	
	
	/*后台接口 start*/
	//获取报表时间类型
    srvMap.add("operationIndexChart", '', "archi/operation/indexChart");	
	/*后台接口 end*/
    //节点
    Dom = {
    	startTime: '',
    	endTime : ''
    };
       
    Data = {
    	queryTime: ''
    };
	//向外暴露的模块
	var init = {
		init: function() {
			this._dom_init();
			//渲染下拉框  绑定按钮事件
			this._load_combo_select();
		},	
		
		_dom_init: function() {
			Dom.startTime = Page.find("[name='startTime']");
			Dom.endTime = Page.find("[name='endTime']");
		},
		
		//渲染下拉框  绑定按钮事件
		_load_combo_select: function() {
			var self = this;
	        var group = Page.findId("selectGroup");
			Dom.startTime.val(Utils.showMonthFirstDay);
			Dom.endTime.val(Utils.showYesterDay);
			//查询按钮事件绑定
			group.find("[name='query']").off('click').on('click',function() {
				self._load_index_echart();
			});	
			self._load_index_echart();
		},

		//调用接口
		_tpl_ajax_data: function(url,param,temp) {
			var dom = Page.findId("logList");	
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	    	Rose.ajax.postJson(srvMap.get(url),param,function(json, status){
				window.XMS.msgbox.hide();
				if(status) {
					var template = Handlebars.compile(temp);
					dom.html(template(json.data));
					Utils.eventClickChecked(dom);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},
		//展示Echart图
		_load_index_echart: function() {
			var param = {
				startTime : Dom.startTime.val(),
				endTime : Dom.endTime.val()				
			}
			var myChart = echarts.init(Page.findId('logEchart')[0]);
			option = {
			    title : {
			        text: '各中心指标',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {	
			    	type: 'scroll',
			        orient: 'vertical',
			        right: 10,
			        top: 40,
			        bottom: 20,
			        data:['A中心CSF','A中心连接数','B中心CSF','B中心连接数','C中心CSF','C中心连接数','D中心CSF','D中心连接数']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: false, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : ['2018-01-01','2018-01-02','2018-01-03','2018-01-04']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'A中心CSF',
			            type:'bar',
			            data:[2.0, 4.9, 7.0, 23.2]
			        },
			        {
			            name:'A中心连接数',
			            type:'bar',
			            data:[2.6, 7.5, 4.5, 22]
			        },
			        {
			            name:'B中心CSF',
			            type:'bar',
			            data:[8, 15, 1.0, 26.4]
			        },
			        {
			            name:'B中心连接数',
			            type:'bar',
			            data:[2, 14, 9.0, 30]
			        },
			        {
			            name:'C中心CSF',
			            type:'bar',
			            data:[6, 24, 30, 18.6]
			        },
			        {
			            name:'C中心连接数',
			            type:'bar',
			            data:[2.6, 5.9, 5, 33]
			        },
			        {
			            name:'D中心CSF',
			            type:'bar',
			            data:[8, 5.9, 9.6, 14.5]
			        },
			        {
			            name:'D中心连接数',
			            type:'bar',
			            data:[5, 1.3, 20, 14]
			        }
			    ]
			};
			Rose.ajax.postJson(srvMap.get('operationIndexChart'),param,function(json, status){
				if(status) {
					if(json && json.data) {
						option.legend.data = json.data.legend;
						option.series = json.data.series;
						if(json.data.xaxis) {
							option.xAxis[0].data = json.data.xaxis;
						}
					}
					myChart.setOption(option);
					window.onresize = myChart.resize;
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
			});

		}
	};
	module.exports = init;
});
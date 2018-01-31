define(function(require,exports, moudle) {
	var Utils = require('global/utils.js');
	// 路径重命名
	var pathAlias = "";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('functionRecord');
	//接口---start
	srvMap.add("getMenuLogData", '', "webservice/menuRecord/findByCondition");
	srvMap.add("getMenuTopData", '', "webservice/menuRecord/topData");
	srvMap.add("getMenuWeekLog", '', "webservice/menuRecord/weekLog");
	//接口---end
	var outexport = {
		init: function() {
			//helper注册
			this._handlebar_help_register()
			//页面参数初始化
			this._param_init();
			//查询事件绑定
			this._btn_query_event();
			//top10数据加载
			this._topList_load();
			//echarts 图加载
			this._load_index_echart();
		},
		_param_init:function(){
			Page.find('[name="startTime"]').val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
			Page.find('[name="endTime"]').val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
		},
		_btn_query_event:function() {
			//查询时间绑定
			Page.find('[name="query"]').off('click').on('click',function() {
				var _cmd = {
					userName : Page.find('[name="userName"]').val(),
					startTime: Page.find('[name="startTime"]').val(),
					endTime:   Page.find('[name="endTime"]').val()
				}
				var _tableDom = Page.findName('useLogTable');
				var _domPagination = _tableDom.find("[name='pagination']");
				Utils.getServerPage(srvMap.get("getMenuLogData"),Utils.jsonToUrl(_cmd),function(json) {
					var template = Handlebars.compile(Page.findTpl('getRecordLogList'));				
    				_tableDom.find("[name='content']").html(template(json.data.content));
				},_domPagination);
			}).click();
		},
		
		_topList_load: function() {
			//获取模板
			var template = Handlebars.compile(Page.findTpl('getRecordTopList'));				
			//活跃用户查询
			Rose.ajax.postJson(srvMap.get("getMenuTopData"),"type=userName",function(json, state){
				Page.findId("functionRecordUser").html(template(json.data));
			});
			//热门菜单查询
			Rose.ajax.postJson(srvMap.get("getMenuTopData"),"type=menuName",function(json, state){
				Page.findId("functionRecordMenu").html(template(json.data));
			});
			//周热门菜单查询
			Rose.ajax.postJson(srvMap.get("getMenuTopData"),"type=menuNameWeek",function(json, state){
				Page.findId("functionRecordMenuWeek").html(template(json.data));
			});
		},
		//展示Echart图
		_load_index_echart: function() {
			var param = {
				startTime : Rose.date.dateTime2str(new Date(new Date().getTime() - 86400000*6),"yyyy-MM-dd"),
				endTime : Rose.date.dateTime2str(new Date(),"yyyy-MM-dd")				
			}
			var myChart = echarts.init(Page.findId('menuLogEchart')[0]);
			option = {
			    title : {
			        text: '',
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
			            type:'line',
			            data:[2.0, 4.9, 7.0, 23.2]
			        },
			        {
			            name:'A中心连接数',
			            type:'line',
			            data:[2.6, 7.5, 4.5, 22]
			        },
			        {
			            name:'B中心CSF',
			            type:'line',
			            data:[8, 15, 1.0, 26.4]
			        },
			        {
			            name:'B中心连接数',
			            type:'line',
			            data:[2, 14, 9.0, 30]
			        },
			        {
			            name:'C中心CSF',
			            type:'line',
			            data:[6, 24, 30, 18.6]
			        },
			        {
			            name:'C中心连接数',
			            type:'line',
			            data:[2.6, 5.9, 5, 33]
			        },
			        {
			            name:'D中心CSF',
			            type:'line',
			            data:[8, 5.9, 9.6, 14.5]
			        },
			        {
			            name:'D中心连接数',
			            type:'line',
			            data:[5, 1.3, 20, 14]
			        }
			    ]
			};
			Rose.ajax.postJson(srvMap.get('getMenuWeekLog'),param,function(json, status){
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

		},
		
		//helper注册
		_handlebar_help_register: function() {
			var handleHelper = Handlebars.registerHelper("nextGroupTitle",function(index) {
				var num = index+1;
				return num;
			});
			var handleHelper = Handlebars.registerHelper("iconContrll",function(index) {
				if(index==0) {
					return "toplist-primicon0";
				} else if(index==1) {
					return "toplist-primicon1";
				}else if(index==2) {
					return "toplist-primicon2";
				}else {
					return '';
				}
			});
		}
	}
	moudle.exports = outexport;
})
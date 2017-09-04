define(function(require, exports, module) {
	//引入Echarts样式
	require("macarons");
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = ""; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiSysHealthReport');
    srvMap.add("getPrimaryDomainList", '', "archi/first/list");
    //根据一级域查询二级子域
    srvMap.add("getSecondByFirst", '', "archi/second/listByfirst");
    //根据二级子域查询三级系统
    srvMap.add("getThirdBySecond", '', "archi/third/findBySec");
    //雷达图数据获取
    srvMap.add("getRadarIndexData", '', "archi/index/getSysIndexData");
    //系统信息查询
    srvMap.add("getSystemInfoCardData", '', "archi/third/findTransPage");
    //系统体检结果查询
    srvMap.add("getSystemHealthReport", '', "archi/report/sysHealth");   
	var cache = {
		datas : ""	
	};
	
	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			self._getEchartsRadar();
			self._load_combo_select();
		},
		//渲染下拉框
		_load_combo_select: function() {
			var self = this;
			var group = Page.findId("selectGroup");
			Utils.setSelectDataPost(group,true);
			group.find('[name="idThird"]').on('change',function() {
		        var _this = $(this);
		        var onlysysId = _this.val();
		        //雷达图加载
				self._getEchartsRadar(onlysysId);
				//系统信息卡加载
				self._getSystemInfoCard(onlysysId);
				//体检结果加载
				self._health_report(onlysysId);
			});
		},
		//系统信息卡
		_getSystemInfoCard: function(onlysysId){
			Rose.ajax.postJson(srvMap.get('getSystemInfoCardData'), 'onlysysId='+onlysysId, function(json, status) {
				if(status) {
					var data = json.data.content[0];
					if(data) {
						var template = Handlebars.compile(Page.findTpl('systemInfoCard'));
						//标题字
						data.titleWord = data.name.charAt(0);
						Page.findId("systemInfoCard").html(template(data));
					} else {
						XMS.msgbox.show("没有查到系统信息", 'error', 2000);
					}
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});
		},
		//系统体检结果
		_health_report: function(onlysysId) {
			Rose.ajax.postJson(srvMap.get('getSystemHealthReport'), 'onlysysId='+onlysysId, function(json, status) {
				if(status) {
					if(json.data) {
						var template = Handlebars.compile(Page.findTpl('systemHealthReport'));
						Page.findId("healthReport").html(template(json.data));
					} else {
						XMS.msgbox.show("没有查到系统体检结果", 'error', 2000);
					}
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});
		},
		//雷达图
		_getEchartsRadar: function(onlysysId) {
			var myChart = echarts.init(Page.findId('echartsRadar')[0],'macarons');
			option = {
			    tooltip: {
			    	show:false
			    },
			    legend: {
			    	enabled: false,
			    },
			    radar: {
			        // shape: 'circle',
			        name: {
			            textStyle: {
			                color: '#0d0d0d',
			                backgroundColor: '#999',
			                borderRadius: 3,
			                padding: [3, 5]
			           }
			        },
			        indicator: [
			           { name: '指标一', max: 6500},
			           { name: '指标二', max: 16000},
			           { name: '指标三', max: 30000},
			           { name: '指标四', max: 38000},
			           { name: '指标五', max: 52000},
			           { name: '指标六', max: 25000}
			        ]
			    },
			    series: [{
			        name: '系统指标',
			        type: 'radar',
			        // areaStyle: {normal: {}},
			        data : [
			            {
			                value : [4300, 10000, 28000, 35000, 50000, 19000],
			                name : '系统指标'
			            }
			        ]
			    }]
			};
			if(onlysysId) {
				Rose.ajax.postJson(srvMap.get('getRadarIndexData'), 'onlysysId='+onlysysId, function(json, status) {
					if(status) {
						if(json.data) {
							option.radar.indicator = json.data.indicator;
							option.series[0].data.value = json.data.value;
						} else {
							XMS.msgbox.show('该系统没有配置指标', 'error', 1500);
						}
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}	
					myChart.setOption(option);
					window.onresize = myChart.resize;
				});
			} else {
				myChart.setOption(option);
				window.onresize = myChart.resize;
			}	
		}
	};
	var handleHelper = Handlebars.registerHelper("indexGrouptitle",function(index){
		//利用+1的时机，在父级循环对象中添加一个_index属性，用来保存父级每次循环的索引
		var chnNumChar = ['零', '一','二','三','四','五','六','七','八','九'];
		var num = index+1;
		//返回+1之后的结果
		return chnNumChar[num];
	});
	module.exports = init;
});
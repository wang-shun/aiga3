define(function(require, exports, module) {
	//引入Echarts样式
	require("macarons");
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = ""; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiSysHealthReport');
	//三级系统下拉框
    srvMap.add("getThirdSysNum", '', "archi/third/list");
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
			this._view_load();
		},

		//查询下拉框数据加载，绑定查询按钮事件
		_view_load: function() {
			var self = this;
			//雷达图
			self._getEchartsRadar();
			//下拉框
			self._load_combo_select();
		},
		//渲染下拉框  查询按钮
		_load_combo_select: function() {
			var self = this;
			var group = Page.findId("selectGroup");
			Utils.setSelectDataPost(group,true);	
			//三级系统查询按钮事件绑定
			group.find("[name='query']").off('click').on('click',function() {
		        var _this = group.find("[name='idThird']");
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
						data.titleWord = data.name.length%6;
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
//			Page.find('[class="score-state-right"]').css("width","0px");
			var current=0;
			var scoreDom = Page.findId("scoreNum");
			function increment(){
				current++;
				scoreDom.html(current);
			}
			Rose.ajax.postJson(srvMap.get('getSystemHealthReport'), 'onlysysId='+onlysysId, function(json, status) {
				if(status) {
					if(json.data) {
						var template = Handlebars.compile(Page.findTpl('systemHealthReport'));
						var index=0;
						var total=0;
						for(var i in json.data) {
							for(var m in json.data[i].sysHealthReportGroups) {
								var sysHealthReportGroup = json.data[i].sysHealthReportGroups[m];
								for(var j in sysHealthReportGroup.sysHealthReportIndexs){
									index++;
									total += parseFloat(sysHealthReportGroup.sysHealthReportIndexs[j].indexValue);
								}
							}
						}
						var average = index==0? 0:total/index;
						var snum = setInterval(increment,50);
						var scoreRight = Page.find('[class="score-state-right"]');
						scoreRight.css("width","0px");
						scoreRight.animate({width:average*4.35+"px"},average*50,function(){
							scoreDom.html(average);
							window.clearInterval(snum);
						});
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
							option.series[0].data[0].value = json.data.value;
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
	var handleHelper = Handlebars.registerHelper("preGroupTitle",function(index) {
		//将阿拉伯数字转成中文
		var chnNumChar = ['零','一','二','三','四','五','六','七','八','九','十'];
		var num = index+1;
		return chnNumChar[num];
	});
	var handleHelper = Handlebars.registerHelper("nextGroupTitle",function(index) {
		var num = index+1;
		return num;
	});
	module.exports = init;
});
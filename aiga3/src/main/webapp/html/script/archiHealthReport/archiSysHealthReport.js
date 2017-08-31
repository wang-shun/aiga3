define(function(require, exports, module) {
	//引入Echarts样式
	require("macarons");
	require("lib/comboSelect/js/jquery.combo.select.js");
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiSysHealthReport');
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
    //根据一级域查询二级子域
    srvMap.add("getSecondByFirst", pathAlias+"secondDomainList.json", "archi/second/listByfirst");
    //根据二级子域查询三级系统
    srvMap.add("getThirdBySecond", pathAlias+"secondDomainList.json", "archi/third/findBySec");
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
			var group = Page.findId("selectGroup");
			Utils.setSelectDataPost(group,true);
		},
		//雷达图
		_getEchartsRadar: function() {
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
				           { name: '销售（sales）', max: 6500},
				           { name: '管理（Administration）', max: 16000},
				           { name: '信息技术（Information Techology）', max: 30000},
				           { name: '客服（Customer Support）', max: 38000},
				           { name: '研发（Development）', max: 52000},
				           { name: '市场（Marketing）', max: 25000}
				        ]
				    },
				    series: [{
				        name: '预算 vs 开销（Budget vs spending）',
				        type: 'radar',
				        // areaStyle: {normal: {}},
				        data : [
				            {
				                value : [4300, 10000, 28000, 35000, 50000, 19000],
				                name : '预算分配（Allocated Budget）'
				            }
				        ]
				    }]
				};
			myChart.setOption(option);
			window.onresize = myChart.resize;
		}
	};
	module.exports = init;
});
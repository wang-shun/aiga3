define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/";
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('quesShow');

    //三级信息变更数据
    srvMap.add("quesStatePie", pathAlias+"getSecView.json", "archi/question/quesStatePie");

	var cache = {
		datas : ""
	};

	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._getChangeMessage();
		},
		
		_getChangeMessage: function(){
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get("quesStatePie"), '', function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					self._graphfir(json);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
  			});
		},

		_graphfir: function(json) {
			var myChart = echarts.init(Page.findId('quesStatePie').find("[class='box-body']")[0]);
			option = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        x: 'left',
			        data:['未解决','跟踪状态','需求单跟踪','任务单跟踪','变更单跟踪','待立项规划','已解决']
			    },
			    series: [
			        {
			            name:'问题状态',
			            type:'pie',
			            selectedMode: 'single',
			            radius: [0, '30%'],

			            label: {
			                normal: {
			                    position: 'inner'
			                }
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:335, name:'未解决'},
			                {value:802, name:'跟踪状态'},
			                {value:251, name:'已解决'}
			            ]
			        },
			        {
			            name:'问题状态',
			            type:'pie',
			            radius: ['40%', '55%'],
			            data:[
			                {value:335, name:'未解决'},
			                {value:310, name:'需求单跟踪'},
			                {value:234, name:'任务单跟踪'},
			                {value:135, name:'变更单跟踪'},
			                {value:123, name:'待立项规划'},
			                {value:251, name:'已解决'},
			            ]
			        }
			    ]
			};
			if(json && json.data) {
				option.legend.data = json.data.legend;
				option.series[0].data = json.data.seriesInner;
				option.series[1].data = json.data.seriesOuter;
			}
			myChart.setOption(option);
			window.onresize = myChart.resize;
		}
		
	};

	module.exports = init;
});
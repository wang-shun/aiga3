define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/";
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiChangeMessage');
    //一级域查询
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");

    //二级数据接口 入参：idFirst level
    srvMap.add("getchangeView", pathAlias+"getSecView.json", "archi/view/changeView");

    var Tpl = {
		getSecView: require('tpl/sysArchiBaselineManage/archiGradingManage/getSecView.tpl')
	};

	var cache = {
		datas : ""
	};


	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		};
    	}
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
			var _form = Page.findId('selectData');
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {
				Rose.ajax.postJson(srvMap.get("getchangeView"),'', function(json, status) {
					if(status) {
						self._graphfir(json);
					}
	  			});
			});
			_queryBtn.click();
		},

		_graphfir: function(json) {
			var myChart = echarts.init(Page.findId('archiView')[0]);
			option = {
			    title : {
			        text: '相关系统域变更情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	
			        data:['业务支撑域','管信域','BOMC域','大数据域','安全域','公共域','网络域','地市域','开放域']
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
			            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'业务支撑域',
			            type:'bar',
			            data:[2.0, 4.9, 7.0, 23.2, 500, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
			        },
			        {
			            name:'管信域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'BOMC域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'大数据域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'安全域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'公共域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'网络域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'地市域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        },
			        {
			            name:'开放域',
			            type:'bar',
			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
			        }
			    ]
			};
			if(json) {
//				option.legend = json.data.legend;
				option.series = json.data.series;
			}
			myChart.setOption(option);
		}
		
	};

	module.exports = init;
});
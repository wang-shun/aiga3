define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/";
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiChangeMessage');

    //三级信息变更数据
    srvMap.add("getchangeView", pathAlias+"getSecView.json", "archi/view/changeView");

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
			var init = true;
			var _form = Page.findId('selectData');
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click', function() {
				var _cmd = Page.findId('querySysDomainForm').serialize();
				if(init) {
					var date = self.formatDate(new Date()); 		
					_cmd = 'beginTime='+date+'&endTime='+date;
					init = false;
			
				}
				if(_cmd.indexOf('beginTime=&')>-1) {
					XMS.msgbox.show('请输入开始时间！', 'error', 2000);
					return
				}
				if(_cmd.charAt(_cmd.length - 1) == '=') {
					XMS.msgbox.show('请输入结束时间！', 'error', 2000);
					return
				}
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get("getchangeView"), _cmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.hide();
						self._graphfir(json);
						self._gridload(json);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
			});
			_queryBtn.click();
		},
		
		//表格加载
		_gridload: function (json) {
			var _dom = Page.findId('sysMessageTable');
			var tableHead = _dom.find("[name='tableHead']");
			var templateHead = Handlebars.compile(Page.findTpl('thirdChangeMessageHead'));
			tableHead.html(templateHead(json.data.xAxis));
			var tableContent = _dom.find("[name='content']");
			var templateContent = Handlebars.compile(Page.findTpl('thirdChangeMessageList'));
			tableContent.html(templateContent(json.data.series));
     		Utils.eventTrClickCallback(_dom);
		},
		
		//时间格式化
		formatDate: function(date) {
			var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				day = '' + d.getDate(),
				year = d.getFullYear(); 
			if (month.length < 2) month = '0' + month;
			if (day.length < 2) day = '0' + day;
			return [year, month].join('-');	
		},

		_graphfir: function(json) {
			var myChart = echarts.init(Page.findId('archiView')[0]);
			option = {
			    title : {
			        text: '系统域变更情况',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {	
			    	y:'bottom',
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
			            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月']
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
			if(json && json.data) {
				option.legend.data = json.data.legend;
				option.series = json.data.series;
				if(json.data.xAxis) {
					option.xAxis[0].data = json.data.xAxis;
				}
			}
			myChart.setOption(option);
			window.onresize = myChart.resize;
		}
		
	};

	module.exports = init;
});
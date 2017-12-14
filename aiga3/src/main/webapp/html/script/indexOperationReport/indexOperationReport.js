define(function(require, exports, module) {
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// Page
	var Page = Utils.initPage('indexOperationReport');	
	
	/*后台接口 start*/
	//获取报表时间类型
    srvMap.add("logReportTimeType", '', "archi/static/logReportTimeType");	
	//获取报表模板名称
    srvMap.add("getLogReportModel", '', "archi/static/getLogReportModel");	
	//各中心CSF服务运行情况日报数据获取
    srvMap.add("csfsrvReport", '', "arch/csfsrv/report");	
	//任务调度运行情况日报数据获取
    srvMap.add("taskdispatchReport", '', "arch/taskdispatch/report");	
	//流程调度运行情况日报数据获取
    srvMap.add("flowdispatchReport", '', "arch/flowdispatch/report");	
	//缓存云平台接入情况日报数据获取
    srvMap.add("cachecloudReport", '', "arch/cachecloud/report");	
	//各中心CSF服务运行情况日报数据获取
    srvMap.add("centermqReport", '', "arch/centermq/report");	
    
	/*后台接口 end*/
    
    //模板
    var Tpl = {
		centerCSFModel: require('tpl/indexOperationReport/day/centerCSFModel.tpl'), 			//各中心CSF服务运行情况日报
		taskSchedulEModel: require('tpl/indexOperationReport/day/taskSchedulEModel.tpl'),		//任务调度运行情况日报
		processSchedulEModel: require('tpl/indexOperationReport/day/processSchedulEModel.tpl'),	//流程调度运行情况日报
		cacheAccessModel: require('tpl/indexOperationReport/day/cacheAccessModel.tpl'),			//缓存云平台接入情况日报
		mqMessageModel: require('tpl/indexOperationReport/day/mqMessageModel.tpl')				//各中心MQ消息队列运行情况日报
    };
    //节点
    Dom = {
    	dayTimeDom: '',
    	monthTimeDom : ''
    };
	//向外暴露的模块
	var init = {
		init: function() {
			this._dom_init();
			//渲染下拉框  绑定按钮事件
			this._load_combo_select();
		},	
		
		_dom_init: function() {
			Dom.dayTimeDom = Page.find("[name='dayModeTime']");
			Dom.monthTimeDom = Page.find("[name='monthModeTime']");
		},
		
		//渲染下拉框  绑定按钮事件
		_load_combo_select: function() {
			var self = this;
	        var group = Page.findId("selectGroup");
			var yesterdsay = new Date(new Date().getTime() - 86400000);
			Dom.dayTimeDom.val(Rose.date.dateTime2str(yesterdsay,"yyyy-MM-dd"));
			Dom.monthTimeDom.val(Rose.date.dateTime2str(yesterdsay,"yyyy-MM"));
			//comboselect
			Utils.setSelectDataPost(group,true);	
			//日期切换
			group.find("[name='reportType']").on('change',function(){
				var typeValue = this.value;		
				if(typeValue == "LOGREPORT_MODEL_MONTH") {
					if(Dom.monthTimeDom.hasClass("show-nothing")) {
						Dom.monthTimeDom.removeClass("show-nothing");
						Dom.dayTimeDom.addClass("show-nothing");
					}	
				} else {
					if(Dom.dayTimeDom.hasClass("show-nothing")) {
						Dom.monthTimeDom.addClass("show-nothing");
						Dom.dayTimeDom.removeClass("show-nothing");
					}
				}
			});

			//查询按钮事件绑定
			group.find("[name='query']").off('click').on('click',function() {
				var modelType = Page.find("[name='reportType']").val(); 
				switch (modelType) {
					case "LOGREPORT_MODEL_DAY":
						//日
						self._dayModelRequest();
						break;
					case "LOGREPORT_MODEL_WEEK":
						//周
						self._weekModelRequest();
						break;
					case "LOGREPORT_MODEL_MONTH":
						//月
						self._monthModelRequest();
						break;
					default:
						 Page.findId("logList").html("");
				}
			});
		},
		
		//调用日报表模板，渲染数据
		_dayModelRequest : function() {
			var self = this;
			var dom = Page.findId("logList"),modelCode = Page.find("[name='reportMode']").val();
			//服务入参
			var _cmd = {
				settMonth: Dom.dayTimeDom.val().replace(/-/g,"")
			};
			// 调用接口
			switch (modelCode) {
			    case "1":
			    	//各中心CSF服务运行情况日报
			        self._tpl_ajax_data('csfsrvReport',_cmd,Tpl.centerCSFModel);
			        break;
			    case "2":
			   		//任务调度运行情况日报
			        self._tpl_ajax_data('taskdispatchReport',_cmd,Tpl.taskSchedulEModel);
			        break;
			    case "3":
			    	//流程调度运行情况日报
			    	self._tpl_ajax_data('flowdispatchReport',_cmd,Tpl.processSchedulEModel);		
			        break;
			    case "4":
			    	//缓存云平台接入情况日报
			    	self._tpl_ajax_data('cachecloudReport',_cmd,Tpl.cacheAccessModel);								
			        break;
			    case "5":
			    	//各中心MQ消息队列运行情况日报
			    	self._tpl_ajax_data('centermqReport',_cmd,Tpl.mqMessageModel);				
			        break;
			    default:
					dom.html("");
			    	Utils.eventClickChecked(dom);
			}
		},
		//调用周报表模板，渲染数据
		_weekModelRequest : function() {
			var modelCode = Page.find("[name='reportMode']").val();
			//服务入参
			var _cmd = {
				settMonth: Dom.dayTimeDom.val().replace(/-/g,"")
			};
			//TODO 调用接口
				switch (modelCode) {
			    default:
					Page.findId("logList").html("");
			}	
		},
		//调用月报表模板，渲染数据
		_monthModelRequest : function(_cmd) {
			var modelCode = Page.find("[name='reportMode']").val();
			//服务入参
			var _cmd = {
				settMonth: Dom.monthTimeDom.val().replace(/-/g,"")
			};
			//TODO 调用接口
			switch (modelCode) {
			    default:
					Page.findId("logList").html("");
			}	
		},
		//调用接口
		_tpl_ajax_data: function(url,param,temp) {
			var dom = Page.findId("logList");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	    	Rose.ajax.postJson(srvMap.get(url),param,function(json, status){
				window.XMS.msgbox.hide();
				if(status) {
					var template = Handlebars.compile(temp);
					dom.html(template());
					Utils.eventClickChecked(dom);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		}		
	};
	module.exports = init;
});
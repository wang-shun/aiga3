define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 用工具模块通
	var Utils = require('global/utils.js');
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('alterReview');

	//交付物评审结论
	srvMap.add("getDeliverableReviewConclusion", "netFlowManage/deliverableReview/getDeliverableReviewConclusion.json", "sys/changerevier/list");
	//保存结论
	srvMap.add("saveConclusion", "netFlowManage/deliverableReview/retMessage.json", "sys/changerevier/save");
	//计划上线需求概况列表
	srvMap.add("getPlanList", "netFlowManage/deliverableReview/getPlanList.json", "sys/planDetailManifest/list");
	//上线系统模块清单
	srvMap.add("getModelList", "netFlowManage/deliverableReview/getModelList.json", "sys/codepath/list");
	//保存模块
	srvMap.add("saveModel", "netFlowManage/deliverableReview/retMessage.json", "sys/codepath/save");
	//计划上线清单列表
	srvMap.add("getOnlineList", "netFlowManage/deliverableReview/getOnlineList.json", "sys/detailManifest/list");
	//功能测试执行情况列表
	srvMap.add("getTestList", "netFlowManage/deliverableReview/getTestList.json", "sys/testSituation/list");
	//保存测试
	srvMap.add("saveTest", "netFlowManage/deliverableReview/retMessage.json", "sys/testSituation/save");
	//测试执行情况列表
	srvMap.add("getRunList", "netFlowManage/deliverableReview/getRunList.json", "sys/testSituation/list");
	//保存测试执行
	srvMap.add("saveRun", "netFlowManage/deliverableReview/retMessage.json", "sys/testSituation/save");
	//测试遗留情况列表
	srvMap.add("getRemnantList", "netFlowManage/deliverableReview/getRemnantList.json", "sys/testLeaveOver/list");
	//功能测试报告列表
	srvMap.add("getReportList", "netFlowManage/deliverableReview/getReportList.json", "sys/requireList/list");
	//数据库配置脚本列表
	srvMap.add("getDatabaseList", "netFlowManage/deliverableReview/getDatabaseList.json", "sys/databaseConfiScript/list");
	//数据库脚本清单
	srvMap.add("getJavascriptList", "netFlowManage/deliverableReview/getJavascriptList.json", "sys/databaseScriptList/list");
	//数据库割接脚本清单
	srvMap.add("getDeliverList", "netFlowManage/deliverableReview/getDeliverList.json", "sys/dbScriptList/list");
	//系统架构变更清单列表
	srvMap.add("getStructureList", "netFlowManage/deliverableReview/getStructureList.json", "sys/review/findNaSystemArchitectureListByPlanId");
	//进程变更清单列表
	srvMap.add("getProgressList", "netFlowManage/deliverableReview/getProgressList.json", "sys/review/findNaProcessChangeListByPlanId");
	//服务变更上线清单列表
	srvMap.add("getServiceList", "netFlowManage/deliverableReview/getServiceList.json", "sys/review/findNaServiceChangeOnlineListByPlanId");
	//主机配置列表
	srvMap.add("getIpConfigurationList", "netFlowManage/deliverableReview/getIpConfigurationList.json", "sys/review/findNaHostConfigListByPlanId");
	//告警屏蔽主机清单列表
	srvMap.add("getIpList", "netFlowManage/changeReview/getIpList.json", "warn/host/findhost");
	//保存主机
	srvMap.add("saveIp", "netFlowManage/changeReview/retMessage.json", "warn/host/savehost");
	//集团需求列表
	srvMap.add("getNeedList", "netFlowManage/deliverableReview/getNeedList.json", "sys/review/findNaGroupRequireListByPlanId");
	//需联调需求列表
	srvMap.add("getCombineList", "netFlowManage/deliverableReview/getCombineList.json", "sys/review/findNaGroupAdjustListByPlanId");
	//生产环境需配置菜单需求列表
	srvMap.add("getConfigureList", "netFlowManage/deliverableReview/getConfigureList.json", "sys/review/findNaHasDeployMenuListByPlanId");
	//编译发布
	/*srvMap.add("publish", "netFlowManage/deliverableReview/retMessage.json", "");*/
	//回退
	srvMap.add("rollback", "netFlowManage/deliverableReview/retMessage.json", "sys/plan/returnToADClod");

	//模板对象
	/*var Tpl={
		getDeliverableReviewConclusion:require('tpl/netFlowManage/deliverableReview/getDeliverableReviewConclusion.tpl'),
		getPlanList:require('tpl/netFlowManage/deliverableReview/getPlanList.tpl'),
		getModelList:require('tpl/netFlowManage/deliverableReview/getModelList.tpl'),
		getOnlineList:require('tpl/netFlowManage/deliverableReview/getOnlineList.tpl'),
		getTestList:require('tpl/netFlowManage/deliverableReview/getTestList.tpl'),
		getRemnantList:require('tpl/netFlowManage/deliverableReview/getRemnantList.tpl'),
		getReportList:require('tpl/netFlowManage/deliverableReview/getReportList.tpl'),
		getDatabaseList:require('tpl/netFlowManage/deliverableReview/getDatabaseList.tpl'),
		getJavascriptList:require('tpl/netFlowManage/deliverableReview/getJavascriptList.tpl'),
		getDeliverList:require('tpl/netFlowManage/deliverableReview/getDeliverList.tpl'),
		getStructureList:require('tpl/netFlowManage/deliverableReview/getStructureList.tpl'),
		getProgressList:require('tpl/netFlowManage/deliverableReview/getProgressList.tpl'),
		getServiceList:require('tpl/netFlowManage/deliverableReview/getServiceList.tpl'),
		getIpConfigurationList:require('tpl/netFlowManage/deliverableReview/getIpConfigurationList.tpl'),
		getNeedList:require('tpl/netFlowManage/deliverableReview/getNeedList.tpl'),
		getCombineList:require('tpl/netFlowManage/deliverableReview/getCombineList.tpl'),
		getConfigureList:require('tpl/netFlowManage/deliverableReview/getConfigureList.tpl')
	};

	var Dom={
		getDeliverableReviewConclusion:'#JS_getDeliverableReviewConclusion',
		getPlanList:'#JS_planList',
		getModelList:'#JS_modelList',
		getOnlineList:'#JS_onlineList',
		getTestList:'#JS_testList',
		getRemnantList:'#JS_remnantList',
		getReportList:'#JS_reportList',
		getDatabaseList:'#JS_databaseList',
		getJavascriptList:'#JS_javascriptList',
		getDeliverList:'#JS_deliverList',
		getStructureList:'#JS_structureList',
		getProgressList:'#JS_progressList',
		getServiceList:'#JS_serviceList',
		getIpConfigurationList:'#JS_ipConfiguration',
		getNeedList:'#JS_needList',
		getCombineList:'#JS_combineList',
		getConfigureList:'#JS_configureList'
	}*/

	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		}
    	},
    	queryListCmd: null
    }

    var alterReview={
    	init:function(){
    		this._render();
    	},
    	_render:function(){
    		this.getDeliverableReviewConclusion();
    		this.getPlanList();
    		this.getModelList();
    		this.getOnlineList();
    		this.getTestList();
    		this.getRunList();
			this.getRemnantList();
    		this.getReportList();
			this.getDatabaseList();
			this.getJavascriptList();
    		this.getDeliverList();
			this.getStructureList();
			this.getProgressList();
			this.getServiceList();
    		this.getIpConfigurationList();
    		this.getIpList();
			this.getNeedList();
			this.getCombineList();
    		this.getConfigureList();
    		this.hdbarHelp();
    	},
		hdbarHelp: function() {
			Handlebars.registerHelper("states", function(value) {
				if (value == 1) {
					return "新增";
				} else if (value == 2) {
					return "修改";
				} else if (value == 3) {
					return "删除";
				}
			});
			Handlebars.registerHelper("isFinishedsss", function(value) {
				if (value == 0) {
					return "否";
				} else if (value == 1) {
					return "是";
				}
			});
			Handlebars.registerHelper("executesss", function(value) {
				if (value == 0) {
					return "否";
				} else if (value == 1) {
					return "是";
				}
			});
		},
    	getDeliverableReviewConclusion:function(){
    		var self=this;
    		var data = Page.getParentCmd();
    		var _cmd = 'onlinePlan=' + data.onlinePlan + '&ext1=2';
    		Rose.ajax.postJson(srvMap.get('getDeliverableReviewConclusion'), _cmd, function(json, status) {
    			if (status) {
    				var _dom = Page.findId('getDeliverableReviewConclusion');
		    		var template = Handlebars.compile(Page.findTpl('getDeliverableReviewConclusion'));
		    		console.log(json.data)
		    		_dom.html(template(json.data));
		    		var _conclusion =  _dom.find("[name='conclusion']");
		    		_conclusion.val(json.data[0].conclusion);
					//引入单选框样式
					Utils.eventTrClickCallback(_dom);
					var _saveConclusion =  Page.findId('getChangeReviewConclusion').find("[name='saveConclusion']");
					_saveConclusion.unbind('click');
					//点击保存
					_saveConclusion.bind('click',function(){
						var _checkObj =	_dom.find("input[type='radio']:checked");
						if(_checkObj.length==0){
						   window.XMS.msgbox.show('请选中结论！', 'error', 2000);
						   return false;
					    }
						var _data= self.getRadioCheckedRow(_dom);
						var cmd = 'reviewId=' + _data.reviewId + '&conclusion=' + _data.conclusion + '&reviewResult=' + _data.reviewResult + '&remark=' + _data.remark + '&ext2=' + _data.ext2 + '&planId=' + data.onlinePlan;
						Rose.ajax.postJson(srvMap.get('saveConclusion'), cmd, function(json, status) {
							if(status) {
									// 保存结论成功后，刷新变更评审结论页
									XMS.msgbox.show('保存成功！', 'success', 2000)
									setTimeout(function(){
										self.getDeliverableReviewConclusion();
									},1000)
							}
						});
					});
					//点击回退
					var _rollback =  _dom.find("[name='rollback']");
					_rollback.bind('click',function(){
						Rose.ajax.postJson(srvMap.get('rollback'), 'planDate=' + data.planDate, function(json, status) {
							if(status) {
								XMS.msgbox.show('回退成功！', 'success', 2000)
							}
						});
					});
	    		}
    		});
    	},
		getPlanList:function(){
    		var self=this;
			var _dom = Page.findId('getPlanList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getPlanList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getPlanList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getModelList:function(){
    		var self=this;
			var _dom = Page.findId('getModelList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getModelList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getModelList'));
        		_dom.find("[name='content']").html(template(json.data.content));
				var _result = _dom.find("select[name='result']");
				var i=0;
				var da=json.data.content;
				_dom.find("tbody").find("tr").each(function(){
					var tdArr = $(this).children();
					tdArr.eq(9).find("select").val(da[i].result);
					i++;
				});
				var _saveModel =  _dom.find("[name='saveModel']");
				_saveModel.unbind('click');
				//点击保存
				_saveModel.bind('click',function(){
				   	var _checkObj =	_dom.find("input[type='checkbox']:checked");
				   	if(_checkObj.length==0){
					   	window.XMS.msgbox.show('请选择要保存的模块！', 'error', 2000);
					   	return false;
				   	}
					var id;
					var result;
					var saveState = [];
					var cmd;
					_dom.find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();
						if(tdArr.eq(0).find("input").is(':checked')){
							id = tdArr.eq(0).find("input").val();
							result = tdArr.eq(9).find("select").val();
							saveState.push({
								"id" : id,
								"result" : result,
								"planId" : data.onlinePlan
							});
						}
					});
					cmd = saveState;
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('saveModel'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('保存成功！', 'success', 2000)
							setTimeout(function() {
								self.getModelList();
							}, 1000)
						}
					});
				});
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
    	},
		getOnlineList:function(){
			var self=this;
			var _dom = Page.findId('getOnlineList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getOnlineList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getOnlineList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getTestList:function(){
    		/*var self=this;
    		var data = Data.getParentCmd();
    		Rose.ajax.postJson(srvMap.get('getTestList'), 'planId=' + data.onlinePlan, function(json, status) {
    			if (status) {
		    		var template=Handlebars.compile(Tpl.getTestList);
		    		console.log(json.data.content)
		    		$(Dom.getTestList).html(template(json.data.content));
					//引入多选框样式
					Utils.eventTrClickCallback($(Dom.getTestList), function() {

					})
					// 分页
					self.initPaging($(Dom.getTestList),10);
					$("#JS_saveTest").unbind('click');
					//点击保存
					$("#JS_saveTest").bind('click',function(){
						var testId;
						var sysName;
						var subSysName;
						var testSituation;
						var saveTest = [];
						var cmd;
						$(Dom.getTestList).find("tbody").find("tr").each(function(){
							var tdArr = $(this).children();
							if(tdArr.eq(0).find("input").is(':checked')){
								testId = tdArr.eq(0).find("input").val();
								sysName = tdArr.eq(1).find("input").val();
								subSysName = tdArr.eq(2).find("input").val();
								testSituation = tdArr.eq(3).find("input").val();
								saveTest.push({
									"testId" : testId,
									"sysName" : sysName,
									"subSysName" : subSysName,
									"testSituation" : testSituation,
									"planId" : data.onlinePlan
								});
							}
						});
						cmd = saveTest;
						console.log(cmd);
						Rose.ajax.postJson(srvMap.get('saveTest'), cmd, function(json, status) {
							if (status) {
								XMS.msgbox.show('保存成功！', 'success', 2000)
								setTimeout(function() {
									self.getTestList();
								}, 1000)
							}
						});
					});
	    		}
    		});*/
			var self=this;
			var _dom = Page.findId('getTestList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getTestList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getTestList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getRunList:function(){
    		var self=this;
			var _dom = Page.findId('getRunList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getRunList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getRunList'));
        		_dom.find("[name='content']").html(template(json.data.content));
				var _saveRun =  _dom.find("[name='saveRun']");
				_saveRun.unbind('click');
				//点击保存
				_saveRun.bind('click',function(){
				   	var _checkObj =	_dom.find("input[type='checkbox']:checked");
				   	if(_checkObj.length==0){
					   	window.XMS.msgbox.show('请选择要保存的情况！', 'error', 2000);
					   	return false;
				   	}
					var testId;
					var ext1;
					var ext2;
					var saveState = [];
					var cmd;
					_dom.find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();
						if(tdArr.eq(0).find("input").is(':checked')){
							testId = tdArr.eq(0).find("input").val();
							ext1 = tdArr.eq(1).find("input").val();
							ext2 = tdArr.eq(2).find("input").val();
							saveState.push({
								"testId" : testId,
								"ext1" : ext1,
								"ext2" : ext2,
								"planId" : data.onlinePlan
							});
						}
					});
					cmd = saveState;
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('saveRun'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('保存成功！', 'success', 2000)
							setTimeout(function() {
								self.getRunList();
							}, 1000)
						}
					});
				});
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
    	},
		getRemnantList:function(){
			var self=this;
			var _dom = Page.findId('getRemnantList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getRemnantList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getRemnantList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getReportList:function(){
			var self=this;
			var _dom = Page.findId('getReportList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getReportList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getReportList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getDatabaseList:function(){
			var self=this;
			var _dom = Page.findId('getDatabaseList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getDatabaseList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getDatabaseList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getJavascriptList:function(){
    		var self=this;
			var _dom = Page.findId('getJavascriptList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getJavascriptList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getJavascriptList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getDeliverList:function(){
    		var self=this;
			var _dom = Page.findId('getDeliverList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getDeliverList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getDeliverList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getStructureList:function(){
			var self=this;
			var _dom = Page.findId('getStructureList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getStructureList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getStructureList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getProgressList:function(){
			var self=this;
			var _dom = Page.findId('getProgressList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getProgressList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getProgressList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getServiceList:function(){
    		var self=this;
			var _dom = Page.findId('getServiceList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getServiceList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getServiceList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getIpConfigurationList:function(){
    		var self=this;
			var _dom = Page.findId('getIpConfigurationList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getIpConfigurationList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getIpConfigurationList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getIpList:function(){
			var self=this;
			var _dom = Page.findId('getIpList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getIpList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getIpList'));
        		_dom.find("[name='content']").html(template(json.data.content));
				var _saveIp =  _dom.find("[name='saveIp']");
				_saveIp.unbind('click');
				//点击保存
				_saveIp.bind('click',function(){
				   	var _checkObj =	_dom.find("input[type='checkbox']:checked");
				   	if(_checkObj.length==0){
					   	window.XMS.msgbox.show('请选择要保存的主机！', 'error', 2000);
					   	return false;
				   	}
					var id;
					var sysName;
					var modelName;
					var ip;
					var hostName;
					var remark;
					var saveIp = [];
					var cmd;
					_dom.find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();
						if(tdArr.eq(0).find("input").is(':checked')){
							id = tdArr.eq(0).find("input").val();
							sysName = tdArr.eq(1).find("input").val();
							modelName = tdArr.eq(2).find("input").val();
							ip = tdArr.eq(3).find("input").val();
							hostName = tdArr.eq(4).find("input").val();
							remark = tdArr.eq(5).find("input").val();
							saveIp.push({
								"id" : id,
								"sysName" : sysName,
								"modelName" : modelName,
								"ip" : ip,
								"hostName" : hostName,
								"remark" : remark,
								"planId" : data.onlinePlan
							});
						}
					});
					cmd = saveIp;
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('saveIp'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('保存成功！', 'success', 2000)
							setTimeout(function() {
								self.getIpList();
							}, 1000)
						}
					});
				});
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
    	},
		getNeedList:function(){
			var self=this;
			var _dom = Page.findId('getNeedList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getNeedList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getNeedList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getCombineList:function(){
			var self=this;
			var _dom = Page.findId('getCombineList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getCombineList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getCombineList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
		getConfigureList:function(){
			var self=this;
			var _dom = Page.findId('getConfigureList');
			var _domPagination = _dom.find("[name='pagination']");
    		var data = Page.getParentCmd();
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getConfigureList'),'planId=' + data.onlinePlan,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getConfigureList'));
        		_dom.find("[name='content']").html(template(json.data.content));
			},_domPagination);
    	},
 		/*
         * 获取单选框当前值
         */
        getRadioCheckedRow:function(obj){
            var _obj = obj.find("input[type='radio']:checked");
            if(_obj.length==0){
                window.XMS.msgbox.show('请先选择一行数据！', 'info', 2000);
                return;
            }
            var data ={};
            _obj.parents("tr").find("input").each(function(){
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key]=value;
            });
			_obj.parents("tr").find("select").each(function(){
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key]=value;
            });
            return data;
        }
    };

	module.exports=alterReview;
});
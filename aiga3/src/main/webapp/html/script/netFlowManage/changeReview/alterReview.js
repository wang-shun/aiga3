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
	//测试执行情况列表
	srvMap.add("getTestList", "netFlowManage/deliverableReview/getTestList.json", "sys/testSituation/list");
	//保存测试
	srvMap.add("saveTest", "netFlowManage/deliverableReview/retMessage.json", "sys/testSituation/save");
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
		getParentCmd:function(){
			return Page.getParentCmd();
		},
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
			/*this.getRemnantList();
    		this.getReportList();
			this.getDatabaseList();
			this.getJavascriptList();
    		this.getDeliverList();
			this.getStructureList();
			this.getProgressList();
			this.getServiceList();
    		this.getIpConfigurationList();
			this.getNeedList();
			this.getCombineList();
    		this.getConfigureList();*/
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
	    		var data = Data.getParentCmd();
	    		var _cmd = 'onlinePlan=1' /*+ data.onlinePlan*/ + '&ext1=2';
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
						var _saveConclusion =  _dom.find("[name='saveConclusion']");
						_saveConclusion.unbind('click');
						//点击保存
						_saveConclusion.bind('click',function(){
							var _checkObj =	_dom.find("input[type='radio']:checked");
							if(_checkObj.length==0){
							   window.XMS.msgbox.show('请选中结论！', 'error', 2000);
							   return false;
						    }
							/*var cmd = $('#JS_getDeliverableReviewConclusion').serialize();
							cmd = cmd + "&planId=" +data.onlinePlan;
							console.log(cmd);*/
							var reviewId;
							var conclusion;
							var reviewResult;
							var remark;
							var ext2;
							var saveState = [];
							var cmd;
							_dom.find("tbody").find("tr").each(function(){
								var tdArr = $(this).children();
								if(tdArr.eq(0).find("input").is(':checked')){
									reviewId = tdArr.eq(0).find("input").val();
									conclusion = tdArr.eq(2).find("select").val();
									reviewResult = tdArr.eq(3).find("input").val();
									remark = tdArr.eq(6).find("input").val();
									ext2 = tdArr.eq(8).find("input").val();
									saveState.push({
										"reviewId" : reviewId,
										"conclusion" : conclusion,
										"reviewResult" : reviewResult,
										"remark" : remark,
										"ext2" : ext2,
										"planId" : /*data.onlinePlan*/"1"
									});
								}
							});
							cmd = saveState;
							console.log(cmd);
							Rose.ajax.postJson(srvMap.get('saveConclusion'), cmd, function(json, status) {
								if(status) {
										// 保存结论成功后，刷新交付物评审结论页
										XMS.msgbox.show('保存成功！', 'success', 2000)
										setTimeout(function(){
											self.getDeliverableReviewConclusion();
										},1000)
								}
							});
						});
						//点击回退
						Page.findId('rollback').bind('click',function(){
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
	    		var data = Data.getParentCmd();

				// 设置服务器端分页
				Utils.getServerPage(srvMap.get('getPlanList'),'planId=1' /*+ data.onlinePlan*/,function(json){
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
	    		var data = Data.getParentCmd();

				// 设置服务器端分页
				Utils.getServerPage(srvMap.get('getModelList'),'planId=1' /*+ data.onlinePlan*/,function(json){
					window.XMS.msgbox.hide();

					// 查找页面内的Tpl，返回值html代码段
					var template = Handlebars.compile(Page.findTpl('getModelList'));
	        		_dom.find("[name='content']").html(template(json.data.content));
					var _result = Page.findId('modelList').find("select[name='result']");
					var i=0;
					var da=json.data.content;
					_dom.find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();
						tdArr.eq(9).find("select").val(da[i].result);
						i++;
					});
					Page.findId('saveModel').unbind('click');
					//点击保存
					Page.findId('saveModel').bind('click',function(){
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
									"planId" : "1"/*data.onlinePlan*/
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
	    		var data = Data.getParentCmd();

				// 设置服务器端分页
				Utils.getServerPage(srvMap.get('getOnlineList'),'planId=1' /*+ data.onlinePlan*/,function(json){
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
	    		var data = Data.getParentCmd();

				// 设置服务器端分页
				Utils.getServerPage(srvMap.get('getTestList'),'planId=1' /*+ data.onlinePlan*/,function(json){
					window.XMS.msgbox.hide();

					// 查找页面内的Tpl，返回值html代码段
					var template = Handlebars.compile(Page.findTpl('getTestList'));
	        		_dom.find("[name='content']").html(template(json.data.content));
	        		var _queryBtn =  _dom.find("[name='query']");
					Page.findId('saveModel').unbind('click');
					//点击保存
					Page.findId('saveModel').bind('click',function(){
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
									"planId" : "1"/*data.onlinePlan*/
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
		getRemnantList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getRemnantList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getRemnantList);
			    		console.log(json.data.content)
			    		$(Dom.getRemnantList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getRemnantList),10);
		    		}
	    		});
    	},
		getReportList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getReportList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getReportList);
			    		console.log(json.data.content)
			    		$(Dom.getReportList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getReportList),10);
		    		}
	    		});
    	},
		getDatabaseList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getDatabaseList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getDatabaseList);
			    		console.log(json.data.content)
			    		$(Dom.getDatabaseList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getDatabaseList),10);
		    		}
	    		});
    	},
		getJavascriptList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getJavascriptList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getJavascriptList);
			    		console.log(json.data.content)
			    		$(Dom.getJavascriptList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getJavascriptList),10);
		    		}
	    		});
    	},
		getDeliverList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getDeliverList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getDeliverList);
			    		console.log(json.data.content)
			    		$(Dom.getDeliverList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getDeliverList),10);
		    		}
	    		});
    	},
		getStructureList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getStructureList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getStructureList);
			    		console.log(json.data.content)
			    		$(Dom.getStructureList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getStructureList),10);
		    		}
	    		});
    	},
		getProgressList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getProgressList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getProgressList);
			    		console.log(json.data.content)
			    		$(Dom.getProgressList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getProgressList),10);
		    		}
	    		});
    	},
		getServiceList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getServiceList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getServiceList);
			    		console.log(json.data.content)
			    		$(Dom.getServiceList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getServiceList),10);
		    		}
	    		});
    	},
		getIpConfigurationList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getIpConfigurationList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getIpConfigurationList);
			    		console.log(json.data.content)
			    		$(Dom.getIpConfigurationList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getIpConfigurationList),10);
		    		}
	    		});
    	},
		getNeedList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getNeedList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getNeedList);
			    		console.log(json.data.content)
			    		$(Dom.getNeedList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getNeedList),10);
		    		}
	    		});
    	},
		getCombineList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getCombineList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getCombineList);
			    		console.log(json.data.content)
			    		$(Dom.getCombineList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getCombineList),10);
		    		}
	    		});
    	},
		getConfigureList:function(){
	    		var self=this;
	    		var data = Data.getParentCmd();
	    		Rose.ajax.postJson(srvMap.get('getConfigureList'), 'planId=' + data.onlinePlan, function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getConfigureList);
			    		console.log(json.data.content)
			    		$(Dom.getConfigureList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getConfigureList),10);
		    		}
	    		});
    	},
		// 事件：分页
        initPaging: function(obj, length) {
            obj.find("table").DataTable({
                "iDisplayLength": length,
                "paging": true,
                "lengthChange": false,
                "searching": false,
                "ordering": false,
                "info": true,
                "autoWidth": false
            });
        }
    };

	module.exports=alterReview;
});
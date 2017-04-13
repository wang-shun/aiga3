define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	var Utils = require('global/utils.js');
	var Page  = Utils.initPage('deliverableReview');

	//交付物评审结论
	srvMap.add("getDeliverableReviewConclusion", "netFlowManage/deliverableReview/getDeliverableReviewConclusion.json", "");
	//保存结论
	srvMap.add("saveConclusion", "netFlowManage/deliverableReview/retMessage.json", "");
	//计划上线需求概况列表
	srvMap.add("getPlanList", "netFlowManage/deliverableReview/getPlanList.json", "");
	//上线系统模块清单
	srvMap.add("getModelList", "netFlowManage/deliverableReview/getModelList.json", "");
	//保存模块
	srvMap.add("saveModel", "netFlowManage/deliverableReview/retMessage.json", "");
	//计划上线清单列表
	srvMap.add("getOnlineList", "netFlowManage/deliverableReview/getOnlineList.json", "");
	//测试执行情况列表
	srvMap.add("getTestList", "netFlowManage/deliverableReview/getTestList.json", "");
	//保存测试
	srvMap.add("saveTest", "netFlowManage/deliverableReview/retMessage.json", "");
	//测试遗留情况列表
	srvMap.add("getRemnantList", "netFlowManage/deliverableReview/getRemnantList.json", "");
	//功能测试报告列表
	srvMap.add("getReportList", "netFlowManage/deliverableReview/getReportList.json", "");
	//数据库配置脚本列表
	srvMap.add("getDatabaseList", "netFlowManage/deliverableReview/getDatabaseList.json", "");
	//数据库脚本清单
	srvMap.add("getJavascriptList", "netFlowManage/deliverableReview/getJavascriptList.json", "");
	//数据库割接脚本清单
	srvMap.add("getDeliverList", "netFlowManage/deliverableReview/getDeliverList.json", "");
	//系统架构变更清单列表
	srvMap.add("getStructureList", "netFlowManage/deliverableReview/getStructureList.json", "");
	//进程变更清单列表
	srvMap.add("getProgressList", "netFlowManage/deliverableReview/getProgressList.json", "");
	//服务变更上线清单列表
	srvMap.add("getServiceList", "netFlowManage/deliverableReview/getServiceList.json", "");
	//主机配置列表
	srvMap.add("getIpConfigurationList", "netFlowManage/deliverableReview/getIpConfigurationList.json", "");

	//模板对象
	var Tpl={
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
		getIpConfigurationList:require('tpl/netFlowManage/deliverableReview/getIpConfigurationList.tpl')
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
		getIpConfigurationList:'#JS_ipConfiguration'
	}

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
    	}
    }

    var deliverableReview={
    	init:function(){
    		this._render();
    		var data = Data.getParentCmd();
    		alert(data.onlinePlan);
    	},
    	_render:function(){
    		this.getDeliverableReviewConclusion();
    		this.getPlanList();
    		this.getModelList();
    		this.getOnlineList();
    		this.getTestList();
			this.getRemnantList();
    		this.getReportList();
			this.getDatabaseList();
			this.getJavascriptList();
    		this.getDeliverList();
    	},
    	getDeliverableReviewConclusion:function(){
	    		var self=this;
	    		Rose.ajax.postJson(srvMap.get('getDeliverableReviewConclusion'), "", function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getDeliverableReviewConclusion);
			    		console.log(json.data)
			    		$(Dom.getDeliverableReviewConclusion).html(template(json.data));
			    		$("#conclusion").val(json.data[0].conclusion);
						//引入单选框样式
						Utils.eventTrClickCallback($(Dom.getDeliverableReviewConclusion), function() {

						})
						$("#JS_saveConclusion").unbind('click');
						//点击保存
						$("#JS_saveConclusion").bind('click',function(){
							var _checkObj =	$('#JS_getDeliverableReviewConclusion').find("input[type='radio']:checked");
							if(_checkObj.length==0){
							   window.XMS.msgbox.show('请选中结论！', 'error', 2000);
							   return false;
						    }
							var cmd = $('#JS_getDeliverableReviewConclusion').serialize();
							/*cmd = cmd + "&reviewId=" +_reviewId;*/
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
		    		}
	    		});
    	},
		getPlanList:function(){
	    		var self=this;
	    		Rose.ajax.postJson(srvMap.get('getPlanList'), "", function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getPlanList);
			    		console.log(json.data.content)
			    		$(Dom.getPlanList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getPlanList),10);
		    		}
	    		});
    	},
		getModelList:function(){
	    		var self=this;
	    		Rose.ajax.postJson(srvMap.get('getModelList'), "", function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getModelList);
			    		console.log(json.data.content)
			    		$(Dom.getModelList).html(template(json.data.content));
			    		var _result = $('#JS_modelList').find("select[name='result']");
						var i=0;
    					var da=json.data.content;
						$(Dom.getModelList).find("tbody").find("tr").each(function(){
							var tdArr = $(this).children();
							tdArr.eq(9).find("select").val(da[i].result);
							i++;
						});
						//引入多选框样式
						Utils.eventTrClickCallback($(Dom.getModelList), function() {

						})
						// 分页
						self.initPaging($(Dom.getModelList),10);
						$("#JS_saveModel").unbind('click');
						//点击保存
						$("#JS_saveModel").bind('click',function(){
							var listId;
							var state;
							var saveState = [];
							var cmd;
							$(Dom.getModelList).find("tbody").find("tr").each(function(){
								var tdArr = $(this).children();
								if(tdArr.eq(0).find("input").is(':checked')){
									listId = tdArr.eq(0).find("input").val();
									state = tdArr.eq(9).find("select").val();
									saveState.push({
										"listId" : listId,
										"state" : state
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
		    		}
	    		});
    	},
		getOnlineList:function(){
	    		var self=this;
	    		Rose.ajax.postJson(srvMap.get('getOnlineList'), "", function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getOnlineList);
			    		console.log(json.data.content)
			    		$(Dom.getOnlineList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getOnlineList),10);
		    		}
	    		});
    	},
		getTestList:function(){
	    		var self=this;
	    		Rose.ajax.postJson(srvMap.get('getTestList'), "", function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getTestList);
			    		console.log(json.data.content)
			    		$(Dom.getTestList).html(template(json.data.content));
			    		/*var _result = $('#JS_modelList').find("select[name='result']");
						var i=0;
    					var da=json.data.content;
						$(Dom.getModelList).find("tbody").find("tr").each(function(){
							var tdArr = $(this).children();
							tdArr.eq(9).find("select").val(da[i].result);
							i++;
						});*/
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
										"testSituation" : testSituation
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
	    		});
    	},
		getRemnantList:function(){
	    		var self=this;
	    		Rose.ajax.postJson(srvMap.get('getRemnantList'), "", function(json, status) {
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
	    		Rose.ajax.postJson(srvMap.get('getReportList'), "", function(json, status) {
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
	    		Rose.ajax.postJson(srvMap.get('getDatabaseList'), "", function(json, status) {
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
	    		Rose.ajax.postJson(srvMap.get('getJavascriptList'), "", function(json, status) {
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
	    		Rose.ajax.postJson(srvMap.get('getDeliverList'), "", function(json, status) {
	    			if (status) {
			    		var template=Handlebars.compile(Tpl.getDeliverList);
			    		console.log(json.data.content)
			    		$(Dom.getDeliverList).html(template(json.data.content));
						// 分页
						self.initPaging($(Dom.getDeliverList),10);
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

	module.exports=deliverableReview;
});
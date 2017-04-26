define(function(require, exports, module) {

	// 通用工具模块
    var Utils = require("global/utils.js");

	var Sidebar = require('global/sidebar.js');

	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('changePlanOnile');

	var pathAlias = "netFlowManage/changePlan/changePlanManage/";
	// 显示变更计划
	srvMap.add("getChangePlanOnlieList", pathAlias + "getChangePlanOnlieList.json", "accept/changePlan/list");
	// 显示变更计划名称
	srvMap.add("queryOnlinePlanName", pathAlias + "getChangePlanOnlieList.json", "sys/cache/changePlan");
	//废弃scrap计划
	srvMap.add("scrap", pathAlias + "scrap.json", "sys/changeplanonile/abandon");
	//取消计划
	srvMap.add("cancel", pathAlias + "scrap.json", "sys/changeplanonile/del");
	//修改计划
	srvMap.add("changePlanupdate", pathAlias + "scrap.json", "sys/changeplanonile/update");
	//保存计划
	srvMap.add("changePlansave", pathAlias + "scrap.json", "sys/changeplanonile/save");
	//上线总结提交/修改
	srvMap.add("submit", pathAlias + "scrap.json", "sys/changeplanonile/resultupdate");
	//上线总结
	srvMap.add("addChangePlanResulForm", pathAlias + "addChangePlanResulForm.json", "sys/changeplanonile/findone");
	//查看需求
	srvMap.add("seerequList", pathAlias + "seeRequList.json", "sys/require/list");
	//保存需求状态
	srvMap.add("saveRequList", pathAlias + "scrap.json", "sys/require/save");
	//查看变更
	srvMap.add("seeChangeList", pathAlias + "seeChangeList.json", "sys/change/list");
	//保存变更状态
	srvMap.add("saveChangeList", pathAlias + "scrap.json", "sys/change/save");
	//上传上线交付物文件显示
	srvMap.add("uploadDeliverables", pathAlias + "getDeliverablesList.json", "produce/plan/findNaFileUpload");
	//变更交付物列表
	srvMap.add("getChangeDeliverableList", pathAlias + "getChangeDeliverableList.json", "");
	//getCategory
	srvMap.add("getCategory", pathAlias + "getDeliverablesList.json", "sys/organize/constants");
	//主机配置导入
	srvMap.add("uploadNaHostConfigList", pathAlias + "getDeliverablesList.json", "produce/plan/uploadNaHostConfigList");
	//服务变更上线清单
	srvMap.add("uploadNaServiceChangeOnlineList", pathAlias + "getDeliverablesList.json", "produce/plan/uploadNaServiceChangeOnlineList");
	//进程变更清单
	srvMap.add("uploadNaProcessChangeList", pathAlias + "getDeliverablesList.json", "produce/plan/uploadNaProcessChangeList");



	// 模板对象
	var Tpl = {
		getChangePlanOnlieList: $("#TPL_getChangPlanOnlieList").html(),
		queryOnlinePlanName: require('tpl/netFlowManage/changePlan/changePlanManage/queryOnlinePlanName.tpl'),
		addChangePlanResulForm: require('tpl/netFlowManage/changePlan/changePlanManage/addChangePlanResulForm.tpl'),
		// addChangePlanForm: require('tpl/netFlowManage/changePlan/changePlanManage/addChangePlanForm.tpl'),
		seeRequForm: require('tpl/netFlowManage/changePlan/changePlanManage/seeRequForm.tpl'),
		seerequList: require('tpl/netFlowManage/changePlan/changePlanManage/seeRequList.tpl'),
		seeChangeList: require('tpl/netFlowManage/changePlan/changePlanManage/seeChangeList.tpl'),
	};


	var Dom = {
		planName: [],
		getChangePlanOnlieList: "#JS_getChangePlanOnlieList",
		queryOnlinePlanName: "#query_OnlinePlanName",
		queryChangePlanOnileBtn: "#JS_queryChangePlanOnileBtn",
		queryChangePlanOnileForm: "#JS_queryChangePlanOnileForm",
		changePlanOnlie: "#JS_changePlanOnlie",
		addChangePlanForm: "#JS_addChangePlanForm",
		addChangePlanResultForm: "#JS_addChangePlanResulForm",
	}
	var getChangePlanOnlie = {
		init: function() {
			this._render();

		},
		_render: function() {
			this.initChangePlanOnlie();
			this.queryOnlinePlanName();
			this.queryChangePlanOnileBtn();
			//重置
			this.reset();
			//废弃
			this.scrap();
			this.cancel();
			this.reviewDel();
			this.hdbarHelp();

			//////////////
			// this.addChangePlan(1);
			// this.addSummary();
			// this.getTaskList();
		},
		hdbarHelp: function() {
			Handlebars.registerHelper("plan_state", function(value) {
				if (value == 1) {
					return "新建";
				} else if (value == 2) {
					return "处理中";
				} else if (value == 3) {
					return "完成";
				} else if (value == 4) {
					return "取消";
				}
			});
			Handlebars.registerHelper("type", function(value) {
				if (value == 0) {
					return "计划上线";
				} else if (value == 1) {
					return "紧急上线";
				} else if (value == 2) {
					return "计划变更";
				} else if (value == 3) {
					return "紧急变更";
				}
			});
			Handlebars.registerHelper("results", function(value) {
				if (value == 1) {
					return "通过";
				} else if (value == 2) {
					return "不通过";
				}
			});
			Handlebars.registerHelper("timelys", function(value) {
				if (value == 1) {
					return "是";
				} else if (value == 2) {
					return "否";
				}
			});
			Handlebars.registerHelper("fileTypes", function(value) {
				if (value == 0) {
					return "否";
				} else if (value == 1) {
					return "文件类型1";
				}
			});
			Handlebars.registerHelper("getFileType", function(value) {
				if (value == 1) {
					return "上线系统模块清单";
				} else if (value == 2) {
					return "计划上线清单";
				} else if (value == 3) {
					return "测试遗留问题清单";
				} else if (value == 4) {
					return "测试情况";
				} else if (value == 5) {
					return "进程变更清单";
				} else if (value == 6) {
					return "服务变更上线清单";
				} else if (value == 7) {
					return "主机类配置";
				} else if (value == 8) {
					return "需联调需求";
				} else if (value == 9) {
					return "生产环境需配置菜单需求";
				} else if (value == 10) {
					return "集团需求";
				}
			});
		},
		//---------------------------------------------------------------------------------//
		///////初始化///////////
		initChangePlanOnlie: function(cmd) {
			var self = this;
			var _domPagination = $("#JS_changePlanOnlie").find("[name='pagination']");
			Utils.getServerPage(srvMap.get('getChangePlanOnlieList'), cmd, function(json) {

				var template = Handlebars.compile(Tpl.getChangePlanOnlieList);
				console.log(json.data)
				$(Dom.getChangePlanOnlieList).find("[name='content']").html(template(json.data.content));
				//新增
				self.addBut();
				//修改
				self.modifyBut();
				//查看交付物
				self.queryDelBut();
				//添加上线总结
		        self.addSummary();
		        //上传交付物
		        self.upload();

				// 绑定单机当前行事件
				self.eventClickChecked($(Dom.getChangePlanOnlieList), function() {

				});
				// 绑定双击当前行事件
				self.eventDClickCallback($(Dom.getChangePlanOnlieList), function() {
					// 请求：用户基本信息
					//self.seeCase();
				})
			}, _domPagination);
		},
		//变更计划名称下拉框
		queryOnlinePlanName: function() {
			var self = this;
			Rose.ajax.postJson(srvMap.get('queryOnlinePlanName'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.queryOnlinePlanName);
					console.log(json.data)
					$(Dom.queryOnlinePlanName).html(template(json.data));
				}
			});
		},
		//重置
		reset: function() {
			var _form = $(Dom.queryChangePlanOnileForm);
			var _reset = _form.find("[name='reset']");
			_reset.unbind('click');
			_reset.bind('click', function() {
				_form.find("[name='important']").val("");
				_form.find("[name='caseName']").val("");
				_form.find("[name='createDate']").val("");
				_form.find("[name='doneDate']").val("");
			});
		},
		//查找
		queryChangePlanOnileBtn: function() {
			var self = this;
			$(Dom.queryChangePlanOnileBtn).unbind('click');
			$(Dom.queryChangePlanOnileBtn).bind('click', function() {
				var cmd = $(Dom.queryChangePlanOnileForm).serialize();
				self.initChangePlanOnlie(cmd);
			});
		},
		//add
		//废弃scrap
		scrap: function() {
			var self = this;
			var _dom = $(Dom.changePlanOnlie);
			var _scrap = _dom.find("[name='scrap']");
			_scrap.unbind('click');
			_scrap.bind('click', function() {
				var _data = self.getTaskRow();
				if (_data) {
					var cmd = "onlinePlan=" + _data.onlinePlan;
					Rose.ajax.postJson(srvMap.get('scrap'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('已废弃！', 'success', 2000)
							setTimeout(function() {
								self.initChangePlanOnlie();
							}, 1000)
						}
					});
				}
			});
		},
		//changePlanOnlie取消cancel
		cancel: function() {
			var self = this;
			var _dom = $(Dom.changePlanOnlie);
			var _scrap = _dom.find("[name='cancel']");
			_scrap.unbind('click');
			_scrap.bind('click', function() {
				var _data = self.getTaskRow();
				if (_data) {
					var cmd = "onlinePlan=" + _data.onlinePlan;
					Rose.ajax.postJson(srvMap.get('cancel'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('已取消！', 'success', 2000)
							setTimeout(function() {
								self.initChangePlanOnlie();
							}, 1000)
						}
					});
				}
			});
		},
		//评审交付物
		reviewDel: function() {
			var self = this;
			var _dom = $(Dom.changePlanOnlie);
			var _reviewDel = _dom.find("[name='reviewDel']");
			_reviewDel.unbind('click');
			_reviewDel.bind('click', function() {
				var _data = self.getTaskRow();
				if (_data) {
					var _cmd = "onlinePlan=" + _data.onlinePlan + "&planDate=" + _data.planDate + "&planState=" + _data.planState+"&onlinePlanName="+_data.onlinePlanName;
					if(_data.types=="0" || _data.types=="1"){
						Sidebar.creatTab({
							id:"100",
							name:'交付物评审',
							href:'view/netFlowManage/deliverableReview/deliverableReview.html',
							cmd:_cmd
						})
					}
				}
			});
		},
		////////*******************************************/////新增//*******************************************////////
		//新增按钮
		addBut: function() {
			var self = this;
			// var _form=$(Dom.addChangePlanForm).find("[name='addChangePlanForm']");
			var _add = $(Dom.changePlanOnlie).find("[name='add']")
			_add.unbind('click');
			_add.bind('click', function() {
				var template = Handlebars.compile(Page.findTpl('addChangePlanForm'));

				$(Dom.addChangePlanForm).html(template(""));
				//弹出层
				$("#JS_addChangePlanFormModal").modal('show');

				self.addChangePlan();
			});
		},
		//保存新增
		addChangePlan: function() {
			var self = this;
			var _add = $(Dom.addChangePlanForm);
			var _submit = _add.find("[name='submit']");
			var _form = _add.find("[name='addChangePlanForm']");
			_submit.unbind('click');
			_submit.bind('click', function() {
				var cmd = _form.serialize()+"&createOpId=1";
				console.log(_form.serialize())
				Rose.ajax.postJson(srvMap.get('changePlansave'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('保存成功！', 'success', 2000)
						setTimeout(function() {
							self.initChangePlanOnlie();
						}, 1000)
					}
				});
			});
		},

		//修改
		updateChangePlan: function(onlinePlan,types,timely) {
			var self = this;
			var _add = $(Dom.addChangePlanForm);
			var _submit = _add.find("[name='submit']");
			var _form = _add.find("[name='addChangePlanForm']");
			_form.find("[name='types']").val(types);
			_form.find("[name='timely']").val(timely);
			_submit.unbind('click');
			_submit.bind('click', function() {

				var cmd = "onlinePlan=" + onlinePlan + "&";
				cmd = cmd + _form.serialize();
				console.log(_form.serialize())
				Rose.ajax.postJson(srvMap.get('changePlanupdate'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('修改成功！', 'success', 2000)
						setTimeout(function() {
							self.initChangePlanOnlie();
						}, 1000)
					}
				});
			});
		},

		//修改按钮
		modifyBut: function() {
			var self = this;
			// var _form=$(Dom.addChangePlanForm).find("[name='addChangePlanForm']");
			var _modify = $(Dom.changePlanOnlie).find("[name='modify']")

			_modify.unbind('click');
			_modify.bind('click', function() {
				var _data = self.getTaskRow();
				var onlinePlan = _data.onlinePlan;
				if (_data) {
					Rose.ajax.postJson(srvMap.get('getChangePlanOnlieList'), "onlinePlan=" + onlinePlan, function(json, status) {
						if (status) {
							var template = Handlebars.compile(Page.findTpl('addChangePlanForm'));
							console.log(json.data.content[0])
							var _form = $(Dom.addChangePlanForm).find("[name='addChangePlanForm']");
							var types=json.data.content[0].types
							var timely = json.data.content[0].timely
							$(Dom.addChangePlanForm).html(template(json.data.content[0]));
							$("#JS_addChangePlanForm").find("[name='head']").html("修改计划");
							console.log($("#JS_addChangePlanForm").find("[name='head']"))

							//弹出层
							$("#JS_addChangePlanFormModal").modal('show');
							self.updateChangePlan(onlinePlan,types,timely);
						}
					});
				}
			});
		},
		////////*******************************************////添加上线总结//*******************************************////////
		//
		addSummary: function() {
			var self = this;
			var _form = $(Dom.addChangePlanResultForm);
			var _addSummary = $(Dom.changePlanOnlie).find("[name='addSummary']")
			_addSummary.unbind('click');
			_addSummary.bind('click', function() {
				var _data = self.getTaskRow();
				var onlinePlan = _data.onlinePlan;
				//弹出层
				$("#JS_addChangePlanResultFormModal").modal('show');

				if (_data) {
					var cmd = "onlinePlan=" + onlinePlan;
					Rose.ajax.postJson(srvMap.get('addChangePlanResulForm'), cmd, function(json, status) {
						if (status) {
							var template = Handlebars.compile(Tpl.addChangePlanResulForm);
							console.log(json.data)
							_form.html(template(json.data));
							_form.find("[name='planState']").val(json.data.planState);
							_form.find("[name='types']").val(json.data.types);
							_form.find("[name='result']").val(json.data.result);
							if(json.data.ext3=="2"){
								_form.find("[name='update']").attr("disabled", true);
								_form.find("[name='submit']").attr("disabled", true);
								_form.find("[name='result']").attr("readonly", readonly);
								_form.find("[name='ext2']").attr("readonly", readonly);
							}else{
								_form.find("[name='update']").removeAttr("disabled");
								_form.find("[name='submit']").removeAttr("disabled");
								_form.find("[name='submit']").removeAttr("readonly");
								_form.find("[name='submit']").removeAttr("readonly");
							}
							$("#submit-button").attr("disabled", true);
							self.resultUpdate();
							self.resultSubmit();
						}
					});
				}
			});
		},
		//修改总结
		resultUpdate: function() {
			var self = this;
			var _addResult = $(Dom.addChangePlanResultForm);
			var _update = _addResult.find("[name='update']");
			_update.unbind('click');
			_update.bind('click', function() {
				var cmd = "ext3=1&";

				cmd = cmd + _addResult.serialize();
				Rose.ajax.postJson(srvMap.get('submit'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('修改成功！', 'success', 2000)
						setTimeout(function() {
							self.initChangePlanOnlie();
							self.addSummary();
						}, 1000)
					}
				});
			});
		},
		//提交总结
		resultSubmit: function() {
			var self = this;
			var _addResult = $(Dom.addChangePlanResultForm);
			var _submit = _addResult.find("[name='submit']");
			_submit.unbind('click');
			_submit.bind('click', function() {
				var cmd = "ext3=2&";
				cmd = cmd + _addResult.serialize();
				Rose.ajax.postJson(srvMap.get('submit'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('提交成功！', 'success', 2000)
						setTimeout(function() {
							self.initChangePlanOnlie();
							self.addSummary();
						}, 1000)
					}
				});
			});
		},
		//////////********************************************查看交付物*************************************************///////
		queryDelBut: function() {
			var self = this;
			var _form = $(Dom.addChangePlanForm).find("[name='seeRequForm']");
			var _queryDel = $(Dom.changePlanOnlie).find("[name='queryDel']");
			_queryDel.unbind('click');
			_queryDel.bind('click', function() {
				var _data = self.getTaskRow();
				var onlinePlan = _data.onlinePlan;
				if (_data) {
					//弹出层
					var template = Handlebars.compile(Tpl.seeRequForm);
					$(Dom.addChangePlanForm).html(template({}));

					$("#JS_addChangePlanFormModal").modal('show');
					var cmd = "onlinePlan=" + onlinePlan;
					Rose.ajax.postJson(srvMap.get('addChangePlanResulForm'), cmd, function(json, status) {
						if (status) {
							var a = json.data.types;
							console.log(json.data)
							if (a == "0" || a == "1") {
								self.seerequList(a,onlinePlan);
							} else {
								self.seeChangeList(a,onlinePlan);
							}
						}
					});
				}
			});
		},
		//查找需求列表
		seerequList: function(a,onlinePlan) {
			var self = this;
			var _form = $(Dom.addChangePlanForm).find("[name='seeRequFormList']");
			var _dom = $(Dom.addChangePlanForm).find("[name='seeRequForm']");
			var cmd = "onlinePlan=" + onlinePlan + "&"+_dom.serialize();
			Rose.ajax.postJson(srvMap.get('seerequList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.seerequList);
					console.log(json.data.content)
					_form.html(template(json.data.content));
					var da=json.data.content;
					var i=0
					$(Dom.addChangePlanForm).find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();
						tdArr.eq(1).find("select").val(da[i].introducedState);
						i++;
					});
					//查询
					self.queSeeSubmit(a,onlinePlan);
					//保存
					self.saveSeeSubmit(a,onlinePlan);
						// 绑定单机当前行事件
					self.eventClickChecked($("#JS_requListab"), function() {

					});

				}
			});
		},
		//查找变更列表
		seeChangeList: function(a,onlinePlan) {
			var self = this;
			var changname=$(Dom.addChangePlanForm).find("[name='changeName']").val();
			var _form = $(Dom.addChangePlanForm).find("[name='seeRequFormList']");
			var _dom = $(Dom.addChangePlanForm).find("[name='seeRequForm']");
			var cmd = "onlinePlan=" + onlinePlan + "&" + _dom.serialize();
			Rose.ajax.postJson(srvMap.get('seeChangeList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.seeChangeList);
					console.log(json.data.content)
					_form.html(template(json.data.content));
					$(Dom.addChangePlanForm).find("[name='changeName']").val(changname);
					var da=json.data.content;
					var i=0
					$(Dom.addChangePlanForm).find("tbody").find("tr").each(function(){
						var tdArr = $(this).children();
						tdArr.eq(1).find("select").val(da[i].resultState);
						i++;
					});
					//查询
					self.queSeeSubmit(a,onlinePlan);
					//保存
					self.saveSeeSubmit(a,onlinePlan);
					// 绑定单机当前行事件
					self.eventClickChecked($("#JS_changeListab"), function() {

					});

				}
			});
		},
		//查询按钮
		queSeeSubmit : function(a,onlinePlan){
			var self = this;
			var _query = $(Dom.addChangePlanForm).find("[name='seeRequForm']").find("[name='query']");
			_query.unbind('click');
			_query.bind('click', function() {
				if (a == "0" || a == "1") {
					self.seerequList(a,onlinePlan);
				} else {
					self.seeChangeList(a,onlinePlan);
				}
			});
		},
		//保存按钮
		saveSeeSubmit : function(a,onlinePlan){
			var self = this;
			var _save = $(Dom.addChangePlanForm).find("[name='seeRequForm']").find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				if (a == "0" || a == "1") {
					self.saveRequList(a,onlinePlan);
					self.initChangePlanOnlie();
				} else {
					self.saveChangeList(a,onlinePlan);
					self.initChangePlanOnlie();
				}
			});
		},
		//保存需求
		saveRequList : function(a,onlinePlan){
			var self = this;
			var id;
			var state;
			var saveState = [];
			var cmd;
			$(Dom.addChangePlanForm).find("tbody").find("tr").each(function(){
				var tdArr = $(this).children();
				if(tdArr.eq(0).find("input").is(':checked')){
					id = tdArr.eq(0).find("input").val();
					state = tdArr.eq(1).find("select").val();
					saveState.push({
						"id" : id,
						"introducedState" : state
					});
				}
			});
			cmd = saveState;
			console.log(cmd);
			Rose.ajax.postJson(srvMap.get('saveRequList'), cmd, function(json, status) {
				if (status) {
					XMS.msgbox.show('保存成功！', 'success', 2000)
					setTimeout(function() {
						if (a == "0" || a == "1") {
							self.seerequList(a,onlinePlan);
						} else {
							self.seeChangeList(a,onlinePlan);
						}
					}, 1000)
				}
			});
		},
		//保存变更
		saveChangeList : function(a,onlinePlan){
			var self = this;
			var id;
			var state;
			var saveState = [];
			var cmd;
			$(Dom.addChangePlanForm).find("tbody").find("tr").each(function(){
				var tdArr = $(this).children();
				if(tdArr.eq(0).find("input").is(':checked')){
					id = tdArr.eq(0).find("input").val();
					state = tdArr.eq(1).find("select").val();
					saveState.push({
						"changeId" : id,
						"resultState" : state
					});
				}
			});
			cmd = saveState;
			console.log(cmd);
			Rose.ajax.postJson(srvMap.get('saveChangeList'), cmd, function(json, status) {
				if (status) {
					XMS.msgbox.show('保存成功！', 'success', 2000)
					setTimeout(function() {
						if (a == "0" || a == "1") {
							self.seerequList(a,onlinePlan);
						} else {
							self.seeChangeList(a,onlinePlan);
						}
					}, 1000)
				}
			});
		},
		//上传评审交付物uploadDeliverables
		upload: function() {
			var self = this;
			// var _form=$(Dom.addChangePlanForm).find("[name='addChangePlanForm']");
			var _upload = $("#JS_changePlanOnlie").find("[name='upload']");
			_upload.unbind('click');
			_upload.bind('click', function() {
				var _data = self.getTaskRow();
				var _ty = Utils.getRadioCheckedRow($(Dom.getChangePlanOnlieList));
				_types = _ty.types;
				if (_data) {
					if (_types=="0" || _types=="1") {
						//弹出层
						$("#JS_addDdeliverablesModal").modal('show').on('shown.bs.modal', function() {
							Utils.setSelectData(Page.findModalCId('uploadDeliveryForm'));
							self.uploadDeliverables(_data.onlinePlan);
							self.uploadAnNiu(_data.onlinePlan);
						})
					}else if (_types=="2" || _types=="3") {
						var _modal = Page.findModal('changeDeliverable');
						//显示弹框
						_modal.modal('show');
						self.getChangeDeliverableList(_data.onlinePlan);
						self.importFile(_data.onlinePlan,_ty.planState,_ty.fileUploadLastTime);
					}
				}
			});
		},
		// 变更交付物列表
		getChangeDeliverableList: function(planId) {
			var self = this;
			var _dom = Page.findId('getChangeDeliverableList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getChangeDeliverableList'), 'planId=' + planId, function(json) {
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段，'#TPL_getChangeDeliverableList' 即传入'getChangeDeliverableList'
				var template = Handlebars.compile(Page.findTpl('getChangeDeliverableList'));
				_dom.find("[name='content']").html(template(json.data.content));
				Utils.eventTrClickCallback(_dom);
			}, _domPagination);
		},
		//显示上传文件信息
		uploadDeliverables:function(onlinePlan){
			var self = this;
            var _cmd = 'planId='+onlinePlan;
            var _dom = Page.findModalCId('addDdeliverablesForm');
            var _content = _dom.find("[name='content']");
            var _domPagination = _dom.find("[name='pagination']");
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('uploadDeliverables'), _cmd, function(json) {
                window.XMS.msgbox.hide();
                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
                var template = Handlebars.compile(Page.findTpl('releaseList'));
                _content.html(template(json.data.content));
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
		},
		//上传上线交付物按钮
		uploadAnNiu:function(planId){
			var self = this;
            var _form = Page.findModalCId('uploadDeliveryForm');
            console.log(_form.length)
            var _saveBtn = _form.find("[name='add']");
            _saveBtn.unbind('click');
            _saveBtn.bind('click', function() {
            	var a=_form.find("[name='environmentType']").val();
            	var cmd = {
            			"file":_form.find("[name='fileName']")[0].files[0],
            			"planId":planId,
            			"fileType":a,
            	}
            	console.log(_form.find("[name='fileName']"));
            	switch(a){
            		case "1"://上线系统模块清单//计划上线清单//测试遗留问题清单//测试情况//进程变更清单//服务变更上线清单//主机类配置//需联调需求//生产环境需配置菜单需求//集团需求
            			var task = srvMap.get('exception');
            			self.jieko(task,cmd,planId)
            			break;
            		case "2"://计划上线清单
            			var task = srvMap.get('processexcels');
            			self.jieko(task,cmd,planId)
            			break;
            		case "3"://测试遗留问题清单
            			var task = srvMap.get('releaseexcel');
            			self.jieko(task,cmd,planId)
            			break;
            		case "4"://测试情况
            			var task = srvMap.get('releasestageexcel');
            			self.jieko(task,cmd,planId)
            			break;
            		case "5"://进程变更清单
            			var task = srvMap.get('uploadNaProcessChangeList');
            			self.jieko(task,cmd,planId)
            			break;
            		case "6"://服务变更上线清单
            			var task = srvMap.get('uploadNaServiceChangeOnlineList');
            			self.jieko(task,cmd,planId)
            			break;
            		case "7"://主机类配置
            			var task = srvMap.get('uploadNaHostConfigList');
            			self.jieko(task,cmd,planId)
            			break;
            		case "8"://需联调需求
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,planId)
            			break;
            		case "9"://生产环境需配置菜单需求
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,planId)
            			break;
            		case "10"://集团需求
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,planId)
            			break;
            	}
            });
		},
		// 变更交付物导入文件
		importFile:function(planId,planState,fileUploadLastTime){
			var self = this;
            var _form = Page.findId('changeDeliverableForm');
            var _importFile = _form.find("[name='importFile']");
            
			if(planState=="3" || planState=="4"){
				_importFile.attr("disabled", true);
			}else{
				_importFile.removeAttr("disabled");
			}
            _importFile.unbind('click');
            _importFile.bind('click', function() {
            	var fileName = _form.find("[name='fileName']")[0].files[0];
            	var category = _form.find("[name='category']").val();
            	var cmd = {
            			"fileName" : fileName,
            			"category" : category,
            			"planId" : planId,
            	}
            	switch(category){
            		// 变更交付物
            		case "20":
            			var task = srvMap.get('importFile');
            			self.interface(task,cmd,planId)
            			break;
            		/*case "2"://需求清单
            			var task = srvMap.get('processexcels');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "3"://设计文档交付物
            			var task = srvMap.get('releaseexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "4"://其他交付物
            			var task = srvMap.get('releasestageexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "5"://平台变更清单
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "6"://测试报告
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "7"://主机类配置
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "8"://进程变更清单
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,planId)
            			break;*/
            	}
            });
		},
		interface : function(task,cmd,planId){
	    	var self = this;
	    	$.ajaxUpload({
                url: task,
                data: cmd,
                success: function(data, status, xhr) {
                    console.log(data);
                    if (status) {
                        window.XMS.msgbox.show('导入文件成功！', 'success', 2000);
                        setTimeout(function() {
                            self.getChangeDeliverableList(planId);
                        }, 1000)
                    }
                }
            });
        },
		jieko : function(task,cmd,planId){
	    	var self = this;
	    	$.ajaxUpload({
                url: task,
                data: cmd,
                success: function(data, status, xhr) {
                    console.log(data);
                    if (status) {
                        window.XMS.msgbox.show('发送成功！', 'success', 2000);
                        setTimeout(function() {
                            self.uploadDeliverables(planId);
                        }, 1000)
                    }
                }
            });
        },

////////*******************************************/////公用//*******************************************////////
		// 获取用例集列表当前选中行
		getTaskRow: function() {
			var _obj = $(Dom.getChangePlanOnlieList).find("input[type='radio']:checked").parents("tr");
			var _onlinePlan = _obj.find("input[name='onlinePlan']");
			var _planDate = _obj.find("input[name='planDate']");
			var _planState = _obj.find("input[name='planState']");
			var _onlinePlanName = _obj.find("input[name='onlinePlanName']");
			var _types = _obj.find("input[name='types']");
			console.log(_onlinePlan)
			var data = {
				onlinePlan: "",
				planDate: "",
				planState: "",
				onlinePlanName: "",
				types: ""
			}
			if (_onlinePlan.length == 0) {
				window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
				return;
			} else {
				data.onlinePlan = _onlinePlan.val();
				data.planDate = _planDate.val();
				data.planState = _planState.val();
				data.onlinePlanName = _onlinePlanName.val();
				data.types = _types.val();
			}
			console.log(data.onlinePlan)
			console.log(data.planDate)
			return data;
		},
		// 事件：单机选中当前行
		eventClickChecked: function(obj, callback) {
			obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue'
			});
			obj.find("tr").bind('click', function(event) {
				$(this).find('.minimal').iCheck('check');
				if (callback) {
					callback();
				}
			});
		},
		// 事件：双击绑定事件
		eventDClickCallback: function(obj, callback) {
			obj.find("tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
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
				"autoWidth": false,
				"scrollX": true,
				"scrollY": false
			});
		}
	};
	module.exports = getChangePlanOnlie;
});
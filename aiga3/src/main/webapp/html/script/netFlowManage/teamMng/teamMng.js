define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('teamMngView');
	// 路径重命名
	var pathAlias = "netFlowManage/teamMng/";

	//分页根据条件查询功能点归属
	srvMap.add("getTeamList", pathAlias + "getTeamList.json", "sys/team/findByName");
	//删除团队
	srvMap.add("delTeamInfo", pathAlias + "retMessage.json", "sys/team/del");
	//新增团队
	srvMap.add("addTeamInfo", pathAlias + "retMessage.json", "sys/team/save");
	//新增员工
	srvMap.add("addEmInfo", pathAlias + "retMessage.json", "sys/employee/save");
	//查询所有员工列表
	srvMap.add("getEmList", pathAlias + "emList.json", "sys/employee/findByName");
	//查询已关联员工列表
	srvMap.add("getEmedList", pathAlias + "emedList.json", "sys/employee/list");

	//批量删除已关联员工
	srvMap.add("delEmed", pathAlias + "retMessage.json", "sys/employee/del");

	//关联员工
	srvMap.add("relEmed", pathAlias + "retMessage.json", "sys/employeeandteam/saveemployee");
	// 模板对象
	var Tpl = {
		getTeamList: require('tpl/netFlowManage/TeamMng/getTeamList.tpl'),
		getEmList: require('tpl/netFlowManage/TeamMng/getEmList.tpl'),
		getEmedList: require('tpl/netFlowManage/TeamMng/getEmedList.tpl'),
	};

	// 容器对象
	var Dom = {
		queryTeamForm: '#JS_queryTeamForm',
		teamList: '#JS_teamList',
		//新增团队
		addTeamModel: '#JS_addTeamModel',
		addTeamInfoForm: '#JS_addTeamInfoForm',
		//员工列表
		emList: '#JS_emList',
		//已关联员工列表
		emedList: '#JS_EmedList',
		queryEmForm: '#JS_queryEmForm',
		//新增团队弹出框
		addTeamModal: '#JS_addTeamModal',
		addTeamInfo: '#JS_addTeamInfo',
		//新增员工弹出框
		addEmModal: '#JS_addEmModal',
		addTeamInfo: '#JS_addEmInfo',
		//关联弹出框
		relTeamerModal: '#JS_relTeamerModal',

	};

	var Data = {
		queryListCmd: null,
		queryEmListCmd: null,
		teamId: null,
	}

	var Query = {
		init: function() {
			// 默认查询所有
			this.getTeamList();
			// 查询表单
			this.queryTeamlistForm();
			//页面跳转
			this.turnTeamer();
		},
		// 按条件查询
		queryTeamlistForm: function() {
			var self = this;
			var _form = $(Dom.queryTeamForm);
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {

				var cmd = _form.serialize();
				self.getTeamList(cmd);
			});
		},
		// 查询团队
		getTeamList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			console.log(_cmd);
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');


			var _dom = Page.findId("teamList");
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getTeamList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				var template = Handlebars.compile($("#TPL_getTeamListTemp").html());
				_dom.find("[name='content']").html(template(json.data.content));
				//删除所选条目
				self.delTeamInfo();
				//新增团队
				self.addTeamInfo();
				//新增员工
				self.addEmInfo();
				//关联
				self.relTeamAndEm();
				Utils.eventTrClickCallback($(Dom.teamList));
			}, _domPagination);



		},
		// 删除所选条目
		delTeamInfo: function() {
			var self = this;
			var _dom = $(Dom.teamList);
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'teamId=' + data.teamId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delTeamInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.getTeamList(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		//新增团队
		addTeamInfo: function() {
			var self = this;
			var _dom = $(Dom.teamList);
			var _add = _dom.find("[name='addTeam']");

			_add.unbind('click');
			_add.bind('click', function() {
				// 弹出层
				$(Dom.addTeamModal).modal('show');
				//组件表单校验初始化
				var _form = $('#JS_addTeamInfo');
				_saveBt = $(Dom.addTeamModal).find("[name='save']");
				Utils.setSelectData(_form);
				// 表单提交
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var cmd = _form.serialize();
						cmd += "&createOpId=12";
						console.log(cmd);
						Rose.ajax.postJson(srvMap.get('addTeamInfo'), cmd, function(json, status) {
							if (status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
									// 关闭弹出层
								$(Dom.addTeamModal).modal('hide');

								setTimeout(function() {
									self.getTeamList();
								}, 1000)
							}
						});
					});
				})
			})
		},
		//新增员工
		addEmInfo: function() {
			var self = this;
			var _dom = $(Dom.teamList);
			var _add = _dom.find("[name='addEm']");
			_add.unbind('click');
			_add.bind('click', function() {
				// 弹出层
				$(Dom.addEmModal).modal('show');
				//组件表单校验初始化
				var _form = $('#JS_addEmInfo');
				_saveBt = $(Dom.addEmModal).find("[name='save']");
				Utils.setSelectData(_form);
				// 表单提交
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var cmd = _form.serialize();
						console.log(cmd);
						Rose.ajax.postJson(srvMap.get('addEmInfo'), cmd, function(json, status) {
							if (status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
									// 关闭弹出层
								$(Dom.addEmModal).modal('hide');

								setTimeout(function() {
									self.getTeamList();
								}, 1000)
							}
						});
					});
				})
			})
		},
		//已有团队关联
		relTeamAndEm: function() {
			var self = this;
			var _dom = $(Dom.teamList);
			var _rel = _dom.find("[name='rel']");
			_rel.unbind('click');
			_rel.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					//跳转
					$(Dom.relTeamerModal).modal('show');
					Data.teamId = data.teamId;

					//查询所有员工信息(去除已关联员工)
					self.getEmList('teamId='+Data.teamId);
					//关联新成员
					self.relEm(Data.teamId);

					self.getEmedList(Data.teamId);
					self.delEmed();
				}

			});
		},
		//查询所有员工信息
		queryEmlistForm: function() {
			var self = this;
			var _form = $(Dom.queryEmForm);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.unbind('click');
			_queryBtn.bind('click', function() {

				var cmd = _form.serialize();
				self.getEmList(cmd);
			});
		},
		// 查询所有员工信息
		getEmList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			console.log(_cmd);
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var _dom = $('#JS_emList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getEmList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				var template = Handlebars.compile($("#TPL_getEmListTemp").html());
				_dom.find("[name='content']").html(template(json.data.content));
				Utils.eventTrClickCallback($(Dom.emList));
			}, _domPagination);



		},
		// 查询已关联员工信息
		getEmedList: function(cmd) {
			var self = this;
			var _cmd = 'teamId=' + cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var _dom = $('#JS_EmedList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getEmedList'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				var template = Handlebars.compile($("#TPL_getEmedListTemp").html());
				_dom.find("[name='content']").html(template(json.data.content));
				Utils.eventTrClickCallback($(Dom.emedList));
			}, _domPagination);



		},
		//批量删除已关联员工
		delEmed: function() {
			var self = this;
			var _dom = $(Dom.emedList);
			var delBt = $("#JS_delEmedBt");
			delBt.unbind('click');
			delBt.bind('click', function(event) {
				var delEmedIds = "list="

				var data = Utils.getCheckboxCheckedRow(_dom);
				if(data){
				for (var k in data) {
					var emId = data[k];
					//拼接
					delEmedIds += emId + ",";
				}

				//去除最后的逗号
				delEmedIds = delEmedIds.substring(0, delEmedIds.length - 1);
				var _cmd = delEmedIds;
				console.log(_cmd);
				//批量删除接口
				Rose.ajax.postJson(srvMap.get('delEmed'), _cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('删除成功！', 'success', 2000)
						setTimeout(function() {

							//问题
							self.getEmedList(Data.teamId);
						}, 1000)
					}
				});
				self.getEmList("teamId="+data.teamId);
			}
			});
		},
		//员工关联
		relEm: function(teamId) {
			var self = this;
			var _form = $(Dom.queryEmForm);
			var _dom = $(Dom.emList);
			var _relBtn = _form.find("[name='rel']");
			_relBtn.unbind('click');
			_relBtn.bind('click', function() {
				var relEmIds = "list="
				var data = Utils.getCheckboxCheckedRow(_dom);
				if (data) {
					for (var k in data) {
						var emId = data[k];
						//拼接
						relEmIds += emId + ",";
					}
					//去除最后的逗号
					relEmIds = relEmIds.substring(0, relEmIds.length - 1);
					var _cmd = relEmIds + '&teamId=' + Data.teamId;
					console.log(_cmd);
					//批量关联接口
					Rose.ajax.postJson(srvMap.get('relEmed'), _cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('关联成功！', 'success', 2000)
							setTimeout(function() {

								//问题
								self.getEmedList(Data.teamId);
								self.getEmList("teamId="+Data.teamId);
							}, 1000)
						}
					});
				}
			});
		},
		//页面跳转
		turnTeamer: function() {

			_backTMLBt = $("#JS_backTeamList");
			_backSaveBt = $("#Js_backAddTeamButton");
			_backSaveBt2 = $("#Js_backAddTeamButton2");
			//返回团队
			_backTMLBt.unbind('click');
			_backTMLBt.bind('click', function() {
				$("#teamMngView").show();
				$("#addTeamView").hide();
			});
			//返回团队管理
			_backSaveBt.unbind('click');
			_backSaveBt.bind('click', function() {
				$("#teamerMngView").hide();
				$("#addTeamView").show();
			});
			//返回团队
			_backSaveBt2.unbind('click');
			_backSaveBt2.bind('click', function() {
				$("#teamerMngView2").hide();
				$("#teamMngView").show();
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
	module.exports = Query;
});
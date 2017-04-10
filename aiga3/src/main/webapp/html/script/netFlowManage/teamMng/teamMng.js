define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "netFlowManage/teamMng/";

	//分页根据条件查询功能点归属
	srvMap.add("getTeamList", pathAlias + "getTeamList.json", "sys/team/findByName");
	//删除团队
	srvMap.add("delTeamtion", pathAlias + "retMessage.json", "sys/team/del");
	//新增团队
	srvMap.add("addTeamtion", pathAlias + "retMessage.json", "sys/team/save");
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
		// 查询功能点
		getTeamList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			console.log(_cmd);
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('getTeamList'), _cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getTeamList);
					$(Dom.teamList).html(template(json.data.content));
					//删除所选条目
					self.delTeamInfo();
					//新增条目
					self.addTeamInfo();
					//关联
					self.relTeamAndEm();
					Utils.eventTrClickCallback($(Dom.teamList));

					self.initPaging($(Dom.teamList), 2);
				}
			});


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
					Rose.ajax.getJson(srvMap.get('delTeamtion'), cmd, function(json, status) {
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
		//新增
		addTeamInfo: function() {
			var self = this;
			var _dom = $(Dom.teamList);
			var _add = _dom.find("[name='add']");
			_add.unbind('click');
			_add.bind('click', function() {
				//弹出框
				$(Dom.addTeamInfoForm).show();
				//组件表单校验初始化
				var _form = $(Dom.addTeamInfoForm);
				// 表单提交
				$("#JS_saveTeamButton").unbind('click');
				$("#JS_saveTeamButton").bind('click', function() {

					Utils.setSelectData(_form);

					var cmd = _form.serialize();
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('addTeamtion'), cmd, function(json, status) {
						if (status) {
							// 添加用户成功后，刷新用户列表页
							XMS.msgbox.show('添加成功！', 'success', 2000)
							setTimeout(function() {
								self.getTeamList(Data.queryListCmd);
							}, 1000);
							$("#teamMngView").show();
							$("#addTeamView").hide();

						}
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
					$("#teamMngView").hide();
					$("#teamerMngView2").show();
					Data.teamId = data.teamId;
					//关联新成员
					self.relEm(Data.teamId);
					//查询所有员工信息
					self.queryEmlistForm();
					self.getEmList();
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
			Rose.ajax.postJson(srvMap.get('getEmList'), _cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getEmList);
					$(Dom.emList).html(template(json.data.content));


					Utils.eventTrClickCallback($(Dom.emList));

					self.initPaging($(Dom.emList), 2);
				}
			});


		},
		// 查询已关联员工信息
		getEmedList: function(data) {
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('getEmedList'), data, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getEmedList);
					$(Dom.emedList).html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.emedList));

					self.initPaging($(Dom.emedList), 2);
				}
			});


		},
		//批量删除已关联员工
		delEmed: function() {
			var self = this;
			var _dom = $(Dom.emedList);
			var delBt = $("#JS_delEmedBt");
			delBt.unbind('click');
			delBt.bind('click', function(event) {
				var delEmedIds= "emId="

				var data = Utils.getCheckboxCheckedRow(_dom);
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
							self.getEmedList();
						}, 1000)
					}
				});
				self.getEmList();
				self.getEmedList(Data.teamId);
				//删除参数初始化
				delEmedIds = "emId:";
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
				var relEmIds="emId="
				var data = Utils.getCheckboxCheckedRow(_dom);
				for (var k in data) {
					var emId = data[k];
					//拼接
					relEmIds += emId + ",";
				}
				//去除最后的逗号
				relEmIds = relEmIds.substring(0, relEmIds.length - 1);
				var _cmd = relEmIds+'&teamId='+Data.teamId;
				console.log(_cmd);
				alert(_cmd);
				//批量关联接口
				Rose.ajax.postJson(srvMap.get('relEmed'),_cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('关联成功！', 'success', 2000)
						setTimeout(function() {

							//问题
							self.getEmedList();
						}, 1000)
					}
				});
				self.getEmList();
				self.getEmedList(Data.teamId);
				//删除参数初始化
				relEmedIds = "emId:";
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
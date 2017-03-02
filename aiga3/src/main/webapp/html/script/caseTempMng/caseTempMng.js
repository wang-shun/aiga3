define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "caseTempMng/";

	// 用例模板列表显示
	srvMap.add("getCaseTempList", pathAlias + "getCaseTempList.json", "/sys/caseTemplate/list");
	//系统大类下拉框显示
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");


	// 模板对象
	var Tpl = {
		getCaseTempList: require('tpl/caseTempMng/getCaseTempList.tpl'),
		getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		getSubSysList: require('tpl/caseTempMng/getSubSysList.tpl'),
		getFunList: require('tpl/caseTempMng/getFunList.tpl'),
	};

	// 容器对象
	var Dom = {
		getCaseTempList: '#JS_getCaseTempList', //table对象
		getSysList: '#query_sysId',
		getSubsysList: '#query_subSysId',
		getFunList: '#query_funId',

	    addCaseTemp:'#JS_addCaseTemp',
	    deleCaseTemp:'#JS_deleCaseTemp',
	    viewCaseTemp:'#JS_viewCaseTemp',
	    createAutoTestTemp:'#JS_createAutoTestTemp',
	    createTest:'#JS_createTest'

	};



	var currentPage = 1;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {
			// 默认只加载组织结构及条件查询
			this.getCaseTempList();
			this.getSysList();
			this.sysSelected();
			this.subsysSelected();
		},

		//系统大类下拉框
		getSysList: function() {
			Rose.ajax.getJson(srvMap.get('getSysList'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSysList);
					$(Dom.getSysList).html(template(json.data));
					console.log(json.data)
				}
			});
		},

		//系统大类下拉框选择事件
		sysSelected: function() {
			var self = this;
			$(Dom.getSysList).change(function() {
				var id = $(Dom.getSysList).val();
				self.getSubSysList(id);
			});
		},
		//系统子类下拉框选择事件
		subsysSelected: function() {
			var self = this;
			$(Dom.getSubsysList).change(function() {
				var id = $(Dom.getSubsysList).val();
				self.getFunList(id);
			});
		},
		//系统子类下拉框
		getSubSysList: function(id) {
			Rose.ajax.getJson(srvMap.get('getSubsysList'), 'id', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSubSysList);
					$(Dom.getSubsysList).html(template(json.data));
					console.log(json.data)
				}
			});
		},
		//功能点下拉框
		getFunList: function(id) {
			Rose.ajax.getJson(srvMap.get('getFunList'), 'id', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getFunList);
					$(Dom.getFunList).html(template(json.data));
					console.log(json.data)
				}
			});
		},

		// 用例模板列表
		getCaseTempList: function() {
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCaseTempList'), 'currentPage', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getCaseTempList);
					console.log(json.data)
					$(Dom.getCaseTempList).html(template(json.data));
					self.eventClickChecked($("#JS_getUserinfoListTable"), function() {

					})
				}
			});
		},
		// 按条件查询模板
		getUserinfo: function() {
			var self = this;
			var _form = $(Dom.getUserinfoForm);
			// 表单校验初始化
			//_form.bootstrapValidator('validate');
			// 表单提交
			_form.find('button[name="submit"]').bind('click', function() {
					
					var cmd = $(Dom.getUserinfoForm).serialize();
					self.getUserinfoList(cmd);
					//});
				})
				// 表单重置
				/*_form.find('button[name="reset"]').bind('click',function(){
					_form.data('bootstrapValidator').resetForm(true);
				})*/
		},
		// 员工列表
		getUserinfoList: function(data) {
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var _url = '';
			if (Data.isOrganize()) {
				_url = srvMap.get('getUserinfoListA')
			} else {
				_url = srvMap.get('getUserinfoListB')
			}
			Rose.ajax.getJson(_url, data, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getUserinfoList);
					console.log(json.data)
						// 待删除：用于测试搜索数据
						/*if(!Data.isOrganize()){
			        	json.data.length = 1;
			        }*/
					$(Dom.getUserinfoList).html(template(json.data));
					XMS.msgbox.hide()

					self.addUserinfo();
					self.startUserinfo();
					self.stopUserinfo();
					self.resetPassword();
					self.clearPower();

					// 绑定双击当前行事件
					self.eventDclickChecked($(Dom.getUserinfoList), function() {
						// 请求：关联组织
						self.getStaffOrgList();
					})

					// 表格分页
					$(Dom.getUserinfoListTable).DataTable({
						"paging": true,
						"lengthChange": false,
						"searching": false,
						"ordering": false,
						"info": true,
						"autoWidth": false
					});
				}
			});
		},
		// 新增用户
		addCaseTemp: function() {
			var self = this;
			$(Dom.addUserinfo).bind('click', function() {
				console.log(Data.organizeId);
				// 如果组织结构未选中或未显示都不行
				if (!Data.isOrganize() || !Data.organizeId) {
					XMS.msgbox.show('请先选择一个组织结构！', 'error', 2000);
					return false;
				}
				// 滚动条
				$(Dom.addUserinfoScroll).slimScroll({
					"height": '420px'
				});
				// 弹出层
				$(Dom.addUserinfoModal).modal('show');

				// 表单校验初始化
				var _form = $(Dom.addUserinfoForm);
				_form.bootstrapValidator('validate');
				// 表单提交
				$(Dom.addUserinfoSubmit).bind('click', function() {

						// 表单校验：成功后调取接口
						_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
							var cmd = _form.serialize();
							console.log(cmd);
							// self.getUserinfoList(cmd);
							XMS.msgbox.show('数据加载中，请稍候...', 'loading')
							Rose.ajax.getJson(srvMap.get('addUserinfo'), cmd, function(json, status) {
								if (status) {
									// 添加用户成功后，刷新用户列表页
									XMS.msgbox.show('添加用户成功！', 'success', 2000)
										// 关闭弹出层
									$(Dom.addUserinfoModal).modal('hide')
									setTimeout(function() {
										self.getUserinfoList();
									}, 1000)
								}
							});
						});
					})
					// 表单重置
				$(Dom.addUserinfoReset).bind('click', function() {
					_form.data('bootstrapValidator').resetForm(true);
				})

			})
		},
		// 启用用户
		startUserinfo: function() {
			var self = this;
			$(Dom.startUserinfo).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _staffId = _data.staffId;
					if (_data.state == '0') {
						Rose.ajax.getJson(srvMap.get('startUserinfo'), 'staffId=' + _staffId, function(json, status) {
							if (status) {
								// 启用成功后，重新加载用户列表
								window.XMS.msgbox.show('员工启用成功！', 'success', 2000)
								setTimeout(function() {
									self.getUserinfoList();
								}, 1000)
							}
						});
					} else {
						XMS.msgbox.show('只允许操作失效员工！', 'error', 2000);
					}
				}
			});
		},
		// 停用用户
		stopUserinfo: function() {
			var self = this;
			$(Dom.stopUserinfo).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _staffId = _data.staffId;
					if (_data.state == '1') {
						Rose.ajax.getJson(srvMap.get('stopUserinfo'), 'staffId=' + _staffId, function(json, status) {
							if (status) {
								// 停用成功后，重新加载用户列表
								window.XMS.msgbox.show('员工停用成功！', 'success', 2000)
								setTimeout(function() {
									self.getUserinfoList();
								}, 1000)
							}
						});
					} else {
						window.XMS.msgbox.show('只允许操作有效员工！', 'error', 2000);
					}
				}
			});
		},
		// 重置密码
		resetPassword: function() {
			var self = this;
			$(Dom.resetPassword).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _staffId = _data.staffId;
					Rose.ajax.getJson(srvMap.get('resetPassword'), 'staffId=' + _staffId, function(json, status) {
						if (status) {
							// self.getUserinfoList();
							window.XMS.msgbox.show('密码重置成功！', 'success', 2000)
						}
					});
				}
			});
		},
		// 清空授权
		clearPower: function() {
			var self = this;
			$(Dom.clearPower).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _staffId = _data.staffId;
					if (confirm('您确认要清除“' + _data.name + '”的权限吗？')) {
						Rose.ajax.getJson(srvMap.get('clearPower'), 'staffId=' + _staffId, function(json, status) {
							if (status) {
								// 停用成功后，重新加载用户列表
								window.XMS.msgbox.show('权限清除成功！', 'success', 2000)
								setTimeout(function() {
									self.getUserinfoList();
								}, 1000)
							}
						});
					}
				}
			});
		},
		getStaffOrgList: function(data) {
			var self = this;
			var _data = self.getUserCheckedRow();
			Data.staffId = _data.staffId;
			Rose.ajax.getJson(srvMap.get('getStaffOrgList'), 'staffId:' + _data.staffId, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getStaffOrgList);
					$(Dom.getStaffOrgList).removeClass('hide');
					$(Dom.getStaffOrgList).html(template(json.data));

					// 接口：新增组织
					self.addStaffOrg();
					// 接口：删除组织
					self.delStaffOrg();
					// 绑定双击当前行事件
					self.eventDclickChecked($(Dom.getStaffOrgList))
				}
			});
		},
		// 新增组织结构
		addStaffOrg: function() {
			var self = this;
			$(Dom.addStaffOrg).bind('click', function() {
				// 弹出层
				$(Dom.addStaffOrgModal).modal('show');
				// 滚动条
				$(Dom.addStaffOrgScroll).slimScroll({
					"height": '320px'
				});
				// 请求关联组织树
				Rose.ajax.getJson(srvMap.get('getOrganizeList'), '', function(json, status) {
					if (status) {
						$.fn.zTree.init($(Dom.addStaffOrgTree), {
							data: {
								key: {
									name: "organizeName"
								},
								simpleData: {
									enable: true,
									idKey: "organizeId",
									pIdKey: "parentOrganizeId"
								}
							},
							callback: {
								onClick: function(event, treeId, treeNode) {
									var _organizeId = treeNode.organizeId;
									var _dom = $(Dom.addStaffOrgForm);
									_dom.find("input[name='staffId']").val(Data.staffId);
									_dom.find("input[name='organizeId']").val(_organizeId);
								}
							}
						}, json.data);
					}
				});
				// 表单提交
				$(Dom.addStaffOrgSubmit).bind('click', function() {
					var cmd = $(Dom.addStaffOrgForm).serialize();
					Rose.ajax.getJson(srvMap.get('addStaffOrg'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('关联组织新增成功！', 'success', 2000)
							$(Dom.addStaffOrgModal).modal('hide')
							setTimeout(function() {
								self.getStaffOrgList();
							}, 1000)
						}
					});
				})
			})
		},
		// 删除组织结构
		delStaffOrg: function() {
			var self = this;
			$(Dom.delStaffOrg).bind('click', function() {
				var _data = self.getStaffOrgCheckedRow();
				if (_data) {
					var cmd = 'staffId=' + Data.staffId + '&organizeId=' + _data.organizeId;
					Rose.ajax.getJson(srvMap.get('delStaffOrg'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('关联组织删除成功！', 'success', 2000)
							setTimeout(function() {
								self.getStaffOrgList();
							}, 1000)
						}
					});
				}
			});
		},
		// 获取员工列表当前选中行
		getUserCheckedRow: function() {
			var _obj = $(Dom.getUserinfoListTable).find("input[type='radio']:checked").parents("tr");
			var _staffId = _obj.find("input[name='staffId']")
			var _name = _obj.find("input[name='staffName']")
			var _state = _obj.find("input[name='staffState']")
			var data = {
				staffId: "",
				name: "",
				state: "",
				code: "",
				organizeName: "",
				organizeId: ""
			}
			if (_staffId.length == 0) {
				window.XMS.msgbox.show('请先选择一个用户！', 'error', 2000);
				return;
			} else {
				data.staffId = _staffId.val();
				data.name = _name.val();
				data.state = _state.val();
			}
			return data;
		},
		// 获取组织列表当前选中行
		getStaffOrgCheckedRow: function() {
			var _obj = $(Dom.getStaffOrgListTable).find("input[type='radio']:checked").parents("tr");
			var _organizeId = _obj.find("input[name='organizeId']")
			var _organizeName = _obj.find("input[name='organizeName']")
			var data = {
				organizeName: _organizeName.val(),
				organizeId: _organizeId.val()
			}
			if (_organizeId.length == 0) {
				window.XMS.msgbox.show('请先选择一个组织结构！', 'error', 2000);
				return;
			}
			return data;
		},

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
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		}
	};
	module.exports = Init;
});
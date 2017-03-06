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

	//删除模板
	srvMap.add("delCaseTemp", pathAlias + "getFunList.json", "/sys/caseTemplate/del");

	// 模板对象
	var Tpl = {
		getCaseTempList: require('tpl/caseTempMng/getCaseTempList.tpl'),
		getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		getSubSysList: require('tpl/caseTempMng/getSubSysList.tpl'),
		getFunList: require('tpl/caseTempMng/getFunList.tpl'),
		getCaseTempForm: require('tpl/caseTempMng/getCaseTempForm.tpl'),
		getFactory: require('tpl/caseTempMng/getFactory.tpl'),

	};

	// 容器对象
	var Dom = {
		getCaseTempList: '#JS_getCaseTempList', //table对象
		getSysList: '#query_sysId',
		getSubsysList: '#query_subSysId',
		getFunList: '#query_funId',

		//功能按钮
	    addCaseTemp:'#JS_addCaseTemp',//新增模板
	    deleCaseTemp:'#JS_deleCaseTemp',//删除
	    viewCaseTemp:'#JS_viewCaseTemp',//查看与编辑
	    createAutoTestTemp:'#JS_createAutoTestTemp',//自动化模板生成
	    createTest:'#JS_createTest',//用例生成

	    //modal
	    modalCaseTempForm:'#modal_CaseTempForm',
	    caseTempForm:'#JS_CaseTempForm',

	    queryCaseTempForm:'#JS_queryCaseTempForm',

	};



	var currentPage = 1;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {
			// 默认只加载组织结构及条件查询
			this.getCaseTempList();
			this.getSysList(Dom.getSysList);
			this.sysSelected();
			this.subsysSelected();
			this.addCaseTemp();
			this.queryCaseTemp();

					
		},

		//系统大类下拉框
		getSysList: function(select) {
			Rose.ajax.getJson(srvMap.get('getSysList'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSysList);
					$(select).html(template(json.data));
					
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
			$('#add_sysId').on("change",function() {
				var id = $('#add_sysId').val();
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
			$("#add_subSysId").change(function() {
				var id = $("#add_subSysId").val();
				self.getFunList(id);
			});			
		},
		//系统子类下拉框
		getSubSysList: function(id) {
			Rose.ajax.getJson(srvMap.get('getSubsysList'), 'id', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSubSysList);
					$(Dom.getSubsysList).html(template(json.data));
					$("#add_subSysId").html(template(json.data));
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
					$("#add_funId").html(template(json.data));
					console.log(json.data)
				}
			});
		},

		// 用例模板列表
		getCaseTempList: function(cmd) {
			var self = this;
			cmd = cmd+'&pageNum='+currentPage;
			Rose.ajax.getJson(srvMap.get('getCaseTempList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getCaseTempList);
					console.log(json.data)
					$(Dom.getCaseTempList).html(template(json.data.content));
					self.deleCaseTemp();
					self.eventClickChecked($("#JS_getUserinfoListTable"), function() {

					})

				}
			});
		},
		// 按条件查询模板
		queryCaseTemp: function() {
			var self = this;
			var _form = $(Dom.queryCaseTempForm);
			// 表单校验初始化
			//_form.bootstrapValidator('validate');
			// 表单提交
			_form.find('button[name="submit"]').bind('click', function() {
					
					var cmd = $(Dom.queryCaseTempForm).serialize();
					self.getUserinfoList(cmd);
					//});
			})
				// 表单重置
				/*_form.find('button[name="reset"]').bind('click',function(){
					_form.data('bootstrapValidator').resetForm(true);
				})*/
		},
		

		// 新增模板
		addCaseTemp: function() {
			var self = this;
			$(Dom.addCaseTemp).bind('click', function() {

				$(Dom.addUserinfoScroll).slimScroll({
					"height": '420px'
				});
				// 弹出层
				$(Dom.modalCaseTempForm).modal('show');

				//j加载form表单
			
				var template = Handlebars.compile(Tpl.getCaseTempForm);
				$(Dom.caseTempForm).html(template());
				self.getSysList("#add_sysId");
				
				// 表单校验初始化
				//var _form = $(Dom.addUserinfoForm);
				//_form.bootstrapValidator('validate');
				// 表单提交
				$(Dom.addUserinfoSubmit).bind('click', function() {

						// 表单校验：成功后调取接口
						//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
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
						// });
					})
					// 表单重置
				$(Dom.addUserinfoReset).bind('click', function() {
					_form.data('bootstrapValidator').resetForm(true);
				})

			})
		},
		// 删除模板
		deleCaseTemp: function() {
			var self = this;

			$(Dom.deleCaseTemp).bind('click', function() {
				var _data = self.getTempCheckedRow();
				if (_data) {
					var _caseId = _data.caseId;
					alert(_caseId);
					Rose.ajax.getJson(srvMap.get('delCaseTemp'), 'caseId=' + _caseId, function(json, status) {
						if (status) {
							// dele成功后，重新加载模板列表
							window.XMS.msgbox.show('模板删除成功！', 'success', 2000)
							setTimeout(function() {
								self.getCaseTempList();
							}, 1000)
						}
					});
					
				}
			});
		},
		// 停用用户
		stopUserinfo: function() {
			var self = this;
			$(Dom.stopUserinfo).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _caseId = _data.staffId;
					if (_data.state == '1') {
						Rose.ajax.getJson(srvMap.get('stopUserinfo'), 'staffId=' + _caseId, function(json, status) {
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
					var _caseId = _data.staffId;
					Rose.ajax.getJson(srvMap.get('resetPassword'), 'staffId=' + _caseId, function(json, status) {
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
					var _caseId = _data.staffId;
					if (confirm('您确认要清除“' + _data.name + '”的权限吗？')) {
						Rose.ajax.getJson(srvMap.get('clearPower'), 'staffId=' + _caseId, function(json, status) {
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
		
		// 获取模板列表当前选中行
		getTempCheckedRow: function() {
			var _obj = $("#JS_getUserinfoListTable").find("input[type='radio']:checked").parents("tr");
			var _caseId = _obj.find("input[name='caseId']")
			
			var _name = _obj.find("input[name='caseName']")
			var data = {
				caseId: "",
				caseName: "",
			}
			if (_caseId.length == 0) {
				window.XMS.msgbox.show('请先选择一个模板！', 'error', 2000);
				return;
			} else {
				data.caseId = _caseId.val();
				data.caseName = _name.val();
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
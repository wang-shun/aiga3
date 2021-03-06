define(function(require,exports,module){

	// 引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 通用工具模块
	var Utils = require('global/utils.js');

	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('environment');

	// 系统大类下拉框显示
	srvMap.add("getSysList", "autoManage/autoCaseTempMng/getSysList.json", "sys/cache/listSysid");
	// 环境列表
	srvMap.add("getEnvironment","environment/getEnvironmentList.json","sys/environment/list");
	// 根据Id查询环境
	srvMap.add("getEnvironmentInfo","environment/getEnvironmentInfo.json","sys/environment/findone");
	// 新增环境
	srvMap.add("addEnvironmentInfo","environment/retMessage.json","sys/environment/save");
	// 删除环境
	srvMap.add("deleteEnvironment","environment/retMessage.json","sys/environment/del");
	// 修改环境
	srvMap.add("updateEnvironmentInfo","environment/retMessage.json","sys/environment/update");
	// 获取机器列表
	srvMap.add("getMachineList","environment/getMachineList.json","sys/envandmachine/rel");
	// 获取已关联的机器列表
    srvMap.add('getRelaMachineList',"environment/getMachineList.json", "sys/environment/rel");
	// 删除环境机器关联
    srvMap.add('delRelaMachine',"environment/retMessage.json", "sys/rel/del");
	// 机器列表关联环境
	srvMap.add("connectEnvironment","environment/retMessage.json","sys/envandmachine/savemachine");

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

    var environment={
    	init:function(){
    		this._render();
    	},
    	_render:function(){
    		this.getEnvironment();
    		this.queryEnvironment();
    		this.hdbarHelp();
    		/*this.getSysList();*/
    	},
		hdbarHelp: function() {
			Handlebars.registerHelper("envTypes", function(value) {
				if (value == 1) {
					return "个人环境配置";
				} else if (value == 2) {
					return "公共环境配置";
				}
			});
			Handlebars.registerHelper("runEnvs", function(value) {
				if (value == 1) {
					return "验收环境";
				} else if (value == 2) {
					return "准发布环境";
				} else if (value == 3) {
					return "生产环境";
				}
			});
			Handlebars.registerHelper("statuses", function(value) {
				if (value == 1) {
					return "离线";
				} else if (value == 2) {
					return "空闲";
				} else if (value == 3) {
					return "占用";
				}
			});
		},
		getEnvironment: function(cmd) {
			var self = this;
			var _dom = Page.findId('getEnvironmentList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getEnvironment'), cmd, function(json) {
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段，'#TPL_getEnvironmentList' 即传入'getEnvironmentList'
				var template = Handlebars.compile(Page.findTpl('getEnvironmentList'));
				_dom.find("[name='content']").html(template(json.data.content));

				// 新增环境
				self.addEnvironmentInfo();
				// 删除环境
				self.deleteEnvironment();
				// 关联机器
				self.connectMachine();
				Utils.eventTrClickCallback(_dom, function() {
					self.updateEnvironmentInfo();
				});
			}, _domPagination);
		},
		// 条件查询
		queryEnvironment: function() {
			var self = this;
			var _form = Page.findId('queryEnvironmentForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.getEnvironment(cmd);
			});
		},
		// 新增环境
		addEnvironmentInfo : function() {
			var self = this;
			var _dom = Page.findId('getEnvironmentList');
			var _addEnvironment = _dom.find("[name='addEnvironment']");
			_addEnvironment.unbind('click');
			_addEnvironment.bind('click', function() {
				var _form = Page.findId('addEnvironmentInfoForm');
				var template = Handlebars.compile(Page.findTpl('addEnvironmentInfoForm'));
				_form.html(template());
				Utils.setSelectData(_form);
				var _modal = Page.findModal('addEnvironmentInfoModal');
				// 显示弹框
				_modal.modal('show');
				var _save = _modal.find("[name='save']");
				_save.unbind('click');
				_save.bind('click', function() {
					var _cmd = _form.serialize();
					Rose.ajax.postJson(srvMap.get('addEnvironmentInfo'), _cmd, function(json, status) {
						if(status) {
							// 新增环境成功后，刷新环境列表页
							XMS.msgbox.show('保存成功！', 'success', 2000)
							_modal.modal('hide');
							setTimeout(function() {
								self.getEnvironment();
							},1000)
						}
					});
				});
			});
		},
		// 删除环境
		deleteEnvironment: function() {
			var self = this;
			var _dom = Page.findId('getEnvironmentList');
			var _deleteEnvironment = _dom.find("[name='deleteEnvironment']");
			_deleteEnvironment.unbind('click');
			_deleteEnvironment.bind('click', function() {
				var _data = Utils.getRadioCheckedRow(_dom);
				if (_data) {
					Rose.ajax.postJson(srvMap.get('deleteEnvironment'), 'envId=' + _data.envId, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self.getEnvironment();
							}, 1000)
						}
					});
				}
			});
		},
		// 关联机器
		connectMachine: function() {
			var self = this;
			var _dom = Page.findId('getEnvironmentList');
			var _connectMachine = _dom.find("[name='connectMachine']");
			_connectMachine.unbind('click');
			_connectMachine.bind('click', function() {
				var _data = Utils.getRadioCheckedRow(_dom);
				if (_data) {
					Rose.ajax.postJson(srvMap.get('getEnvironmentInfo'), 'envId=' + _data.envId, function(json, status) {
						if (status) {
							var _modal = Page.findModal('connectMachineModal');
							// 显示弹框
							_modal.modal('show');
							self.getMachineList(_data.envId);
							self.getRelaMachineList(_data.envId);
						}
					});
				}
			});
		},
        // 机器列表
        getMachineList: function(envId) {
            var self = this;
			var _dom = Page.findId('getMachineList');
			var _domPagination = _dom.find("[name='pagination']");
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getMachineList'), 'envId=' + envId, function(json) {
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getMachineList'));
        		_dom.find("[name='content']").html(template(json.data.content));

				// 机器列表关联环境
				self.connectEnvironment(envId);
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
        },
		// 已关联机器列表
        getRelaMachineList: function(envId) {
            var self = this;
			var _dom = Page.findId('getRelaMachineList');
			var _domPagination = _dom.find("[name='pagination']");
    		XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getRelaMachineList'), 'envId=' + envId, function(json) {
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段
				var template = Handlebars.compile(Page.findTpl('getMachineList'));
        		_dom.find("[name='content']").html(template(json.data.content));

				// 删除关联
				self.delRelaMachine(envId);
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
        },
		// 机器列表关联环境
        connectEnvironment: function(envId) {
            var self = this;
			var _dom = Page.findId('getMachineList');
			var _connectEnvironment = _dom.find("[name='connectEnvironment']");
			_connectEnvironment.unbind('click');
            _connectEnvironment.bind('click',function(){
                var _data = self.getCheckedMachine();
                if (_data) {
                    var _machineIdsArray = [];
                    _data.each(function() {
                        _machineIdsArray.push($(this).val());
                    })
                    var _cmd = "machineId=" + _machineIdsArray.join(",");
                    _cmd = _cmd + "&envId=" + envId;
                    Rose.ajax.postJson(srvMap.get('connectEnvironment'), _cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('关联成功！', 'success', 2000)
                            setTimeout(function() {
								self.getMachineList(envId);
				                self.getRelaMachineList(envId);
                            }, 1000)
                        }
                    });
                }
            });
        },
		// 删除已关联机器
        delRelaMachine: function(envId) {
            var self = this;
			var _dom = Page.findId('getRelaMachineList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
            _del.bind('click',function(){
                var _data = self.getCheckedRelaMachine();
                if (_data) {
                    var _machineIdsArray = [];
                    _data.each(function() {
                        _machineIdsArray.push($(this).val());
                    })
                    var _cmd1 = "&machineIds=" + _machineIdsArray.join(",");
                    var _cmd = "envId=" + envId + _cmd1;
                    Rose.ajax.postJson(srvMap.get('delRelaMachine'), _cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除已关联机器成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRelaMachineList(envId);
                                self.getMachineList(envId);
                            }, 1000)
                        }
                    });
                }
            });
        },
		// 获取选中未关联机器
        getCheckedMachine: function() {
            var _obj = Page.findId('getMachineList').find("input[type='checkbox']:checked").parents("tr");
            var _machineId = _obj.find("input[name='machineId']");
            console.log(_machineId);
            if (_machineId.length == 0) {
                window.XMS.msgbox.show('请先选择一个未关联机器！', 'error', 2000);
                return;
            } else {
                var _data = $(_machineId);
                data = _data;
                console.log(data);
            }
            return data;
        },
		// 获取选中已关联机器
        getCheckedRelaMachine: function() {
            var _obj = Page.findId('getRelaMachineList').find("input[type='checkbox']:checked").parents("tr");
            var _machineId = _obj.find("input[name='machineId']");
            console.log(_machineId);
            if (_machineId.length == 0) {
                window.XMS.msgbox.show('请先选择一个已关联机器！', 'error', 2000);
                return;
            } else {
                var _data = $(_machineId);
                data = _data;
                console.log(data);
            }
            return data;
        },
        // 修改环境
		updateEnvironmentInfo: function() {
			var self = this;
			var _dom = Page.findId('getEnvironmentList');
			var _data = Utils.getRadioCheckedRow(_dom);
			Rose.ajax.postJson(srvMap.get('getEnvironmentInfo'), 'envId=' + _data.envId, function(json, status) {
				if (status) {
					var _form = Page.findId('addEnvironmentInfoForm');
					var template = Handlebars.compile(Page.findTpl('addEnvironmentInfoForm'));
					_form.html(template(json.data));
					Utils.setSelectData(_form);
					// 设置下拉框选中值
					Utils.setSelected(_form);
					/*var _sysName =  _form.find("[name='sysName']");
					_sysName.val(json.data.sysName);*/
					var _modal = Page.findModal('addEnvironmentInfoModal');
					var _formName =  _modal.find("[name='formName']");
			    	_formName.html("修改环境");
					// 显示弹框
					_modal.modal('show');
					var _save = _modal.find("[name='save']");
					_save.unbind('click');
					_save.bind('click', function() {
						var _cmd = _form.serialize();
						_cmd = _cmd + "&envId=" + _data.envId;
						Rose.ajax.postJson(srvMap.get('updateEnvironmentInfo'), _cmd, function(json, status) {
							if(status) {
								// 修改环境成功后，刷新环境列表页
								XMS.msgbox.show('保存成功！', 'success', 2000)
								_modal.modal('hide');
								setTimeout(function() {
									self.getEnvironment();
								},1000)
							}
						});
					});
				}
			});
		},
		/*getSysList: function() {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getSysList'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSysList);
					$("#sysId").html(template(json.data));
					console.log(json.data)
				}
			});
		},*/
    };

	module.exports=environment;
});
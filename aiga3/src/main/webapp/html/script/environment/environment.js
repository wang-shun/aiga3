define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 通用工具模块
	var Utils = require('global/utils.js');

	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('environment');

	// 系统大类下拉框显示
	srvMap.add("getSysList", "autoManage/autoCaseTempMng/getSysList.json", "sys/cache/listSysid");
	// 环境列表
	srvMap.add("getEnvironmentList","environment/getEnvironmentList.json","sys/environment/findall");
	// 查询环境
	srvMap.add("getEnvironment","environment/getEnvironmentList.json","sys/environment/list");
	// 根据Id查询环境
	srvMap.add("getEnvironmentInfo","environment/getEnvironmentInfo.json","sys/environment/findone");
	// 新增环境
	srvMap.add("addEnvironmentInfo","environment/addEnvironmentInfo.json","sys/environment/save");
	// 删除环境
	srvMap.add("deleteEnvironment","environment/deleteEnvironment.json","sys/environment/del");
	// 修改环境
	srvMap.add("updateEnvironmentInfo","environment/updateEnvironmentInfo.json","sys/environment/update");
	// 获取机器列表
	srvMap.add("getMachineList","environment/getMachineList.json","sys/envandmachine/rel");
	// 获取已关联的机器列表
    srvMap.add('getRelaMachineList',"environment/getMachineList.json", "sys/environment/rel");
	// 删除环境机器关联
    srvMap.add('delRelaMachine',"environment/deleteEnvironment.json", "sys/rel/del");
	// 关联机器
	srvMap.add("connectMachine","environment/connectMachine.json","sys/envandmachine/savemachine");

	//模板对象
	/*var Tpl={
		queryEnvironmentForm:require('tpl/environment/queryEnvironmentForm.tpl'),
		getEnvironmentList:require('tpl/environment/getEnvironmentList.tpl'),
		addEnvironmentInfo: require('tpl/environment/addEnvironmentInfo.tpl'),
		getMachineListInEnvironment: require('tpl/environment/getMachineListInEnvironment.tpl'),
		//getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		//getRelaMachineList: require('tpl/environment/getRelaMachineList.tpl')
	};

	var Dom={
		queryEnvironmentForm:'#JS_queryEnvironmentForm',
		getEnvironmentList:'#JS_getEnvironmentList',
		addEnviromentInfoForm:"#JS_addEnvironmentInfoForm",
		addEnvironmentInfoModal:"#JS_addEnvironmentInfoModal",
		connectMachineList:"#JS_connectMachineList",
		connectMachineModal:"#JS_connectMachineModal",
		getRelaMachineList: '#JS_getRelaMachineList', //获取已关联的机器列表
		caseType:[],
		repairsId:[]
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
		addEnvironmentInfo : function(){
			var self = this;
			var _dom = Page.findId('getEnvironmentList');
			var _addEnvironment = _dom.find("[name='addEnvironment']");
			_addEnvironment.unbind('click');
			_addEnvironment.bind('click', function() {
				var _form = Page.findId('addEnvironmentInfoForm');
				var template = Handlebars.compile(Page.findTpl('addEnvironmentInfoForm'));
				//$("#formName").html("新增环境");
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
		deleteEnvironment: function(){
			var self = this;
			var  envId="";
			var num =0 ;
			$("#JS_deleteEnvironment").unbind('click');
			$("#JS_deleteEnvironment").bind('click', function() {
				var  envId="";
				var num =0 ;
			   var _checkObj = $('#JS_getEnvironmentList').find("input[type='radio']:checked");
			   if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要删除的环境！', 'error', 2000);
				   return false;
			   }
			   _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   envId += $(this).val()+",";
				   }else{
					   envId += $(this).val();
				   }
				   num ++;
				});
				Rose.ajax.postJson(srvMap.get('deleteEnvironment'), 'envId=' + envId, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
							  self.getEnvironment();
							}, 1000)
						}
				});
			});
		},
		// 关联机器
		connectMachine: function(cmd){
			var self = this;
			$("#JS_connectMachine").unbind('click');
				$("#JS_connectMachine").bind('click', function() {
					var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
					if(_checkObj.length==0){
					   window.XMS.msgbox.show('请选择要关联的环境！', 'error', 2000);
					   return false;
				    }
					var _envId ="";
					_checkObj.each(function (){
						_envId = $(this).val();
					})
					Rose.ajax.postJson(srvMap.get('getEnvironmentInfo'), 'envId='+_envId, function(json, status) {
						if (status) {
							var _form = $(Dom.connectMachineList);
							var template = Handlebars.compile(Tpl.getMachineListInEnvironment);
							_form.html(template());
							self.getMachineList();
							self.getRelaMachineList();
							self.delRelaMachine();
							//弹出层
							$(Dom.connectMachineModal).modal('show');
							$("#JS_connectMachineSubmit").unbind('click');
							//点击保存
							$("#JS_connectMachineSubmit").bind('click',function(){
								/*var cmd = _form.serialize();
								console.log(cmd);*/

								var  machineId="";
								$("#Tab_getMachine").find("tbody").find("tr").each(function(){
									var tdArr = $(this).children();
									if(tdArr.eq(0).find("input").is(':checked')){
										machineId += tdArr.eq(0).find("input").val()+",";
									}
								});

								var cmd = "machineId="+machineId + "&envId=" +_envId;
								Rose.ajax.postJson(srvMap.get('connectMachine'), cmd, function(json, status) {
									if(status) {
											// 关联机器成功
											XMS.msgbox.show('关联成功！', 'success', 2000)
											setTimeout(function() {
				                                self.getMachineList("envId=" + _envId);
				                                self.getRelaMachineList("envId=" + _envId);
				                            }, 1000)
									}
								});
							});
						}
					});
				});
		},
        // 机器列表
        getMachineList: function(cmd) {
            var self = this;
			var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
            var _envId ="";
			_checkObj.each(function (){
				_envId = $(this).val();
			})
            var _cmd = "envId=" + _envId;
            Rose.ajax.postJson(srvMap.get('getMachineList'), _cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getMachineListInEnvironment);
                    /*console.log(json.data.content);*/
                    $("#formName").html("关联机器");
                    $(Dom.connectMachineList).html(template(json.data.content));

                    //单击选中
                    /*self.eventClickChecked($(Dom.getCaseList));*/
                    //双击关联用例
                    // self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                    //     var _data = self.getCheckedCaseGroup();
                    //     var cmd = "groupId=" + _data.groupId;
                    //     self.getCaseGroupInfo(cmd);
                    // })
					//引入多选框样式
					Utils.eventTrClickCallback($(Dom.connectMachineList), function() {

					})
                    //设置分页
                    self.initPaging($(Dom.connectMachineList), 10)
                }
            });
        },
		// 已关联机器列表
        getRelaMachineList: function(cmd) {
            var self = this;
            var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
            var _envId ="";
			_checkObj.each(function (){
				_envId = $(this).val();
			})
            var cmd = "envId=" + _envId;
            console.log(cmd);
            Rose.ajax.postJson(srvMap.get('getRelaMachineList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getMachineListInEnvironment);
                    console.log(json.data)
                    $(Dom.getRelaMachineList).html(template(json.data));
                    //单击选中
                    /*self.eventClickChecked($(Dom.getRelaMachineList));*/
                    //双击关联用例
                    // self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                    //     var _data = self.getCheckedCaseGroup();
                    //     var cmd = "groupId=" + _data.groupId;
                    //     self.getCaseGroupInfo(cmd);
                    // })
					//引入多选框样式
					Utils.eventTrClickCallback($(Dom.getRelaMachineList), function() {

					})
                    //设置分页
                    self.initPaging($(Dom.getRelaMachineList), 10)
                }
            });
        },
		// 删除已关联机器
        delRelaMachine: function() {
            var self = this;
            $("#JS_deleteConnectMachine").unbind('click');
            $("#JS_deleteConnectMachine").bind('click',function(){
                var _data = self.getCheckedRelaMachine();
                if (_data) {
                    var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
		            var _envId ="";
					_checkObj.each(function (){
						_envId = $(this).val();
					})
                    var _machineIdsArray = [];
                    _data.each(function() {
                        _machineIdsArray.push($(this).val());
                    })
                    var _cmd = "envId=" + _envId;
                    var _cmd1 = "&machineIds=" + _machineIdsArray.join(",");
                    var cmd = _cmd + _cmd1;
                    console.log(cmd);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('delRelaMachine'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除已关联机器成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRelaMachineList("envId=" + _envId);
                                self.getMachineList("envId=" + _envId);
                            }, 1000)
                        }
                    });
                }
            });
        },
		// 获取选中已关联机器
        getCheckedRelaMachine: function() {
            var _obj = $(Dom.getRelaMachineList).find("input[type='checkbox']:checked").parents("tr");
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
					var _sysName =  _form.find("[name='sysName']");
					_sysName.val(json.data.sysName);
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

	module.exports=environment;
});
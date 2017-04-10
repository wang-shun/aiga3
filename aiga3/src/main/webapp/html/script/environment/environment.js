define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	var Utils = require('global/utils.js')

	//系统大类下拉框显示
	srvMap.add("getSysList", "", "sys/cache/listSysid");
	//显示环境列表
	srvMap.add("getEnvironmentList","environment/getEnvironmentList.json","sys/environment/findall");
	//查询环境
	srvMap.add("getEnvironment","environment/getEnvironmentList.json","sys/environment/list");
	//根据Id查询环境
	srvMap.add("getEnvironmentInfo","environment/getEnvironmentInfo.json","sys/environment/findone");
	//增加环境
	srvMap.add("addEnvironmentInfo","environment/addEnvironmentInfo.json","sys/environment/save");
	//删除环境
	srvMap.add("deleteEnvironment","environment/deleteEnvironment.json","sys/environment/del");
	//修改环境
	srvMap.add("updateEnvironmentInfo","environment/updateEnvironmentInfo.json","sys/environment/update");
	//获取机器列表
	srvMap.add("getMachineList","environment/getMachineList.json","sys/machine/list");
	//获取已关联的机器列表
    srvMap.add('getRelaMachineList',"environment/getRelaMachineList.json", "sys/autoGroup/caseRelatGroupList");
	//删除环境机器关联
    srvMap.add('delRelaMachine',"environment/deleteEnvironment.json", "sys/autoGroup/caseRelatGroupDel");
	//关联机器
	srvMap.add("connectMachine","environment/connectMachine.json","sys/envandmachine/savemachine");

	//模板对象
	var Tpl={
		queryEnvironmentForm:require('tpl/environment/queryEnvironmentForm.tpl'),
		getEnvironmentList:require('tpl/environment/getEnvironmentList.tpl'),
		addEnvironmentInfo: require('tpl/environment/addEnvironmentInfo.tpl'),
		getMachineList: require('tpl/environment/getMachineList.tpl'),
		getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		getRelaMachineList: require('tpl/environment/getRelaMachineList.tpl')
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
	}

	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		}
    	}
    }

    var environment={
    	init:function(){
    		this._render();
    	},
    	_render:function(){
    		this.initForm();
    		this.getEnvironment();
    		this.queryEnvironment();
    		this.addEnvironmentInfo();
    		this.hdbarHelp();
    		this.getSysList();
    		Utils.setSelectData($(Dom.queryEnvironmentForm));
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
    	initForm:function(){
	    		var self=this;
	    		var template=Handlebars.compile(Tpl.queryEnvironmentForm);
	    		$(Dom.queryEnvironmentForm).html(template());
    	},
		getEnvironmentList: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getEnvironmentList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getEnvironmentList);
					console.log(json.data)
					$(Dom.getEnvironmentList).html(template(json.data));
					//删除按钮
					self.deleteEnvironment();
					//关联机器
					self.connectMachine();
					//引入单选框样式
					Utils.eventTrClickCallback($(Dom.getEnvironmentList), function() {
						self.updateEnvironmentInfo();
					})
					// 分页
					self.initPaging($(Dom.getEnvironmentList),10);
				}
			});
		},
		getEnvironment: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getEnvironment'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getEnvironmentList);
					console.log(json.data)
					$(Dom.getEnvironmentList).html(template(json.data.content));
					//删除按钮
					self.deleteEnvironment();
					//关联机器
					self.connectMachine();
					//引入单选框样式
					Utils.eventTrClickCallback($(Dom.getEnvironmentList), function() {
						self.updateEnvironmentInfo();
					})
					// 分页
					self.initPaging($(Dom.getEnvironmentList),10);
				}
			});
		},
		// 按条件查询
		queryEnvironment: function() {
			var self = this;
			var _form = $(Dom.queryEnvironmentForm);
			// 表单校验初始化
			//_form.bootstrapValidator('validate');
			// 表单提交
			_form.find('button[name="submit"]').bind('click', function() {
					var cmd = $(Dom.queryEnvironmentForm).serialize();
					/*self.getEnvironmentList(cmd);*/
					alert("按条件查询");
					self.getEnvironment(cmd);
					//});
			})
			// 表单重置
			_form.find('button[name="reset"]').bind('click',function(){
				$("#sysId").val('');
				$("#envName").val('');
				$("#envUrl").val('');
				$("#database").val('');
				$("#regionId").val('');
				$("#soId").val('');
				$("#query_envType").val('');
				$("#query_runEnv").val('');
				$("#creatorId").val('');
				$("#updateTime").val('');
			});
		},
		addEnvironmentInfo : function(cmd){
			var self = this;
			$("#JS_addEnvironment").bind('click', function() {
				var _form = $(Dom.addEnviromentInfoForm);
				var template = Handlebars.compile(Tpl.addEnvironmentInfo);
				$("#formName").html("新增环境");
				_form.html(template());
				Utils.setSelectData($(Dom.addEnviromentInfoForm));
				//弹出层
				$(Dom.addEnvironmentInfoModal).modal('show');
				$("#JS_addEnvironmentInfoSubmit").unbind('click');
				//点击保存
				$("#JS_addEnvironmentInfoSubmit").bind('click',function(){
					var cmd = _form.serialize();
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('addEnvironmentInfo'), cmd, function(json, status) {
						if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addEnvironmentInfoModal).modal('hide');
								setTimeout(function(){
									self.getEnvironment();
								},1000)
						}
					});
				});
			});
		},
		//删除
		deleteEnvironment : function(){
			var self = this;
			var  envId="";
			var num =0 ;
			$("#JS_deleteEnvironment").unbind('click');
			$("#JS_deleteEnvironment").bind('click', function() {
				var  envId="";
				var num =0 ;
			   var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
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
		//关联机器
		connectMachine : function(cmd){
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
							var template = Handlebars.compile(Tpl.getMachineList);
							/*$("#formName").html("关联机器");*/
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
											// 关闭弹出层
											$(Dom.connectMachineModal).modal('hide');
											/*setTimeout(function(){
												self.getEnvironmentList();
											},1000)*/
									}
								});
							});
						}
					});
				});
		},
        //机器列表
        getMachineList: function(cmd) {
            var self = this;
            Rose.ajax.postJson(srvMap.get('getMachineList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getMachineList);
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
                    var template = Handlebars.compile(Tpl.getRelaMachineList);
                    console.log(json.data.content)
                    $(Dom.getRelaMachineList).html(template(json.data.content));
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
                    self.initPaging($(Dom.getRelaMachineList), 4)
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
                    var _relaIdsArray = [];
                    _data.each(function() {
                        _relaIdsArray.push($(this).val());
                    })
                    var _cmd = "envId=" + _envId;
                    var _cmd1 = "&relaIds=" + _relaIdsArray.join(",");
                    var cmd = _cmd + _cmd1;
                    console.log(cmd);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('delRelaMachine'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除已关联机器成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRelaMachineList("envId=" + _envId);
                            }, 1000)
                        }
                    });
                }
            });
        },
		//获取选中已关联机器
        getCheckedRelaMachine: function() {
            var _obj = $(Dom.getRelaMachineList).find("input[type='checkbox']:checked").parents("tr");
            var _relaId = _obj.find("input[name='relaId']");
            console.log(_relaId);
            if (_relaId.length == 0) {
                window.XMS.msgbox.show('请先选择一个已关联机器！', 'error', 2000);
                return;
            } else {
                var _data = $(_relaId);
                data = _data;
                console.log(data);
            }
            return data;
        },
		updateEnvironmentInfo:function(){
			var self = this;
			var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
			var _envId ="";
			_checkObj.each(function (){
				_envId = $(this).val();
			})
			Rose.ajax.postJson(srvMap.get('getEnvironmentInfo'), 'envId='+_envId, function(json, status) {
				if (status) {
					var _form = $(Dom.addEnviromentInfoForm);
					var template = Handlebars.compile(Tpl.addEnvironmentInfo);
					_form.html(template(json.data));
					/*alert(json.data.content[0].envType);*/
					$("#query_envType").val(json.data.envType);
					// 设置下拉框选中值
					Utils.setSelected(_form);
					Utils.setSelectData($(Dom.addEnviromentInfoForm));
					// //弹出层
					$(Dom.addEnvironmentInfoModal).modal('show');
					$("#formName").html("修改环境");
					$("#JS_addEnvironmentInfoSubmit").unbind('click');
					//点击保存
					$("#JS_addEnvironmentInfoSubmit").bind('click',function(){
					var cmd = _form.serialize();
					cmd = cmd + "&envId=" +_envId;
					Rose.ajax.postJson(srvMap.get('updateEnvironmentInfo'), cmd, function(json, status) {
						if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('修改成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addEnvironmentInfoModal).modal('hide');
								setTimeout(function(){
									self.getEnvironment();
								},1000)
						}
								});
					});
				}
			});

		},
		getSysList: function() {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getSysList'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSysList);
					$("#sysId").html(template(json.data));
					console.log(json.data)
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

	module.exports=environment;
});
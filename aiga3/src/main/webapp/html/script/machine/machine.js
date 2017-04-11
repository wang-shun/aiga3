define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	var Utils = require('global/utils.js')

	//显示机器列表
	srvMap.add("getMachineList","machine/getMachineList.json","sys/machine/findall");
	//查询机器
	srvMap.add("getMachine","machine/getMachineList.json","sys/machine/list");
	//根据Id查询机器
	srvMap.add("getMachineInfo","machine/getMachineInfo.json","sys/machine/findone");
	//增加机器
	srvMap.add("addMachineInfo","machine/addMachineInfo.json","sys/machine/save");
	//删除机器
	srvMap.add("deleteMachine","machine/deleteMachine.json","sys/machine/del");
	//修改机器
	srvMap.add("updateMachineInfo","machine/updateMachineInfo.json","sys/machine/update");
	//获取环境列表
	srvMap.add("getEnvironmentListInMachine","machine/getEnvironmentList.json","sys/environment/list");
	//获取已关联的环境列表
    srvMap.add('getRelaEnvironmentList',"machine/getEnvironmentList.json", "sys/machine/rel");
	//删除环境机器关联
    srvMap.add('delRelaEnvironment',"machine/deleteMachine.json", "sys/rel/delete");
	//关联环境
	srvMap.add("connectEnvironment","machine/connectEnvironment.json","sys/envandmachine/saveenv");

	//模板对象
	var Tpl={
		queryMachineForm:require('tpl/machine/queryMachineForm.tpl'),
		getMachineList:require('tpl/machine/getMachineList.tpl'),
		addMachineInfo: require('tpl/machine/addMachineInfo.tpl'),
		getEnvironmentListInMachine: require('tpl/machine/getEnvironmentListInMachine.tpl'),
		getRelaEnvironmentList: require('tpl/machine/getRelaEnvironmentList.tpl')
	};

	var Dom={
		queryMachineForm:'#JS_queryMachineForm',
		getMachineList:'#JS_getMachineList',
		addMachineInfoForm:"#JS_addMachineInfoForm",
		addMachineInfoModal:"#JS_addMachineInfoModal",
		connectEnvironmentList:"#JS_connectEnvironmentList",
		connectEnvironmentModal:"#JS_connectEnvironmentModal",
		getRelaEnvironmentList: '#JS_getRelaEnvironmentList', //获取已关联的环境列表
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

    var machine={
    	init:function(){
    		this._render();
    	},
    	_render:function(){
    		this.initForm();
    		this.getMachineList();
    		this.queryMachine();
    		this.addMachineInfo();
    		this.hdbarHelp();
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
	    		var template=Handlebars.compile(Tpl.queryMachineForm);
	    		$(Dom.queryMachineForm).html(template());
    	},
		getMachineList: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getMachineList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getMachineList);
					console.log(json.data)
					$(Dom.getMachineList).html(template(json.data));
					//删除按钮
					self.deleteMachine();
					//关联环境
					self.connectEnvironment();
					//引入单选框样式
					Utils.eventTrClickCallback($(Dom.getMachineList), function() {
						self.updateMachineInfo();
					})
					// 分页
					self.initPaging($(Dom.getMachineList),10);
				}
			});
		},
		getMachine: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getMachine'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getMachineList);
					console.log(json.data)
					$(Dom.getMachineList).html(template(json.data.content));
					//删除按钮
					self.deleteMachine();
					//关联环境
					self.connectEnvironment();
					//引入单选框样式
					Utils.eventTrClickCallback($(Dom.getMachineList), function() {
						self.updateMachineInfo();
					})
					// 分页
					self.initPaging($(Dom.getMachineList),10);
				}
			});
		},
		// 按条件查询
		queryMachine: function() {
			var self = this;
			var _form = $(Dom.queryMachineForm);
			// 表单校验初始化
			//_form.bootstrapValidator('validate');
			// 表单提交
			_form.find('button[name="submit"]').bind('click', function() {
					var cmd = $(Dom.queryMachineForm).serialize();
					self.getMachine(cmd);
					//});
			})
			// 表单重置
			_form.find('button[name="reset"]').bind('click',function(){
				$("#machineIp").val('');
				$("#machineName").val('');
			});
		},
		addMachineInfo : function(cmd){
			var self = this;
			$("#JS_addMachine").bind('click', function() {
				var _form = $(Dom.addMachineInfoForm);
				var template = Handlebars.compile(Tpl.addMachineInfo);
				$("#formName").html("新增机器");
				_form.html(template());
				//弹出层
				$(Dom.addMachineInfoModal).modal('show');
				$("#JS_addMachineInfoSubmit").unbind('click');
				//点击保存
				$("#JS_addMachineInfoSubmit").bind('click',function(){
					var cmd = _form.serialize();
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('addMachineInfo'), cmd, function(json, status) {
						if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addMachineInfoModal).modal('hide');
								setTimeout(function(){
									self.getMachineList();
								},1000)
						}
					});
				});
			});
		},
		//删除
		deleteMachine : function(){
			var self = this;
			var  machineId="";
			var num =0 ;
			$("#JS_deleteMachine").unbind('click');
			$("#JS_deleteMachine").bind('click', function() {
				var  machineId="";
				var num =0 ;
			   var _checkObj =	$('#JS_getMachineList').find("input[type='radio']:checked");
			   if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要删除的机器！', 'error', 2000);
				   return false;
			   }
			   _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   machineId += $(this).val()+",";
				   }else{
					   machineId += $(this).val();
				   }
				   num ++;
				});
				Rose.ajax.postJson(srvMap.get('deleteMachine'), 'machineId=' + machineId, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
							  self.getMachineList();
							}, 1000)
						}
				});
			});
		},
		//关联环境
		connectEnvironment : function(cmd){
			var self = this;
			$("#JS_connectEnvironment").unbind('click');
				$("#JS_connectEnvironment").bind('click', function() {
					var _checkObj =	$('#JS_getMachineList').find("input[type='radio']:checked");
					if(_checkObj.length==0){
					   window.XMS.msgbox.show('请选择要关联的机器！', 'error', 2000);
					   return false;
				    }
					var _machineId ="";
					_checkObj.each(function (){
						_machineId = $(this).val();
					})
					Rose.ajax.postJson(srvMap.get('getMachineInfo'), 'machineId='+_machineId, function(json, status) {
						if (status) {
							var _form = $(Dom.connectEnvironmentList);
							var template = Handlebars.compile(Tpl.getEnvironmentListInMachine);
							_form.html(template());
							self.getEnvironmentListInMachine();
							self.getRelaEnvironmentList();
							self.delRelaEnvironment();
							//弹出层
							$(Dom.connectEnvironmentModal).modal('show');
							$("#JS_connectEnvironmentSubmit").unbind('click');
							//点击保存
							$("#JS_connectEnvironmentSubmit").bind('click',function(){
								/*var cmd = _form.serialize();
								console.log(cmd);*/
								var  envId="";
								$("#Tab_getEnvironment").find("tbody").find("tr").each(function(){
									var tdArr = $(this).children();
									if(tdArr.eq(0).find("input").is(':checked')){
										envId += tdArr.eq(0).find("input").val()+",";
									}
								});
								var cmd = "envId="+envId + "&machineId=" +_machineId;
								Rose.ajax.postJson(srvMap.get('connectEnvironment'), cmd, function(json, status) {
									if(status) {
											// 关联环境成功
											XMS.msgbox.show('关联成功！', 'success', 2000)
											// 关闭弹出层
											$(Dom.connectEnvironmentModal).modal('hide');
									}
								});
							});
						}
					});
				});
		},
        //环境列表
        getEnvironmentListInMachine: function(cmd) {
            var self = this;
            Rose.ajax.postJson(srvMap.get('getEnvironmentListInMachine'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getEnvironmentListInMachine);
                    /*console.log(json.data.content);*/
                    $("#formName").html("关联环境");
                    $(Dom.connectEnvironmentList).html(template(json.data.content));
                    //单击选中
                    /*self.eventClickChecked($(Dom.getCaseList));*/
                    //双击关联用例
                    // self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                    //     var _data = self.getCheckedCaseGroup();
                    //     var cmd = "groupId=" + _data.groupId;
                    //     self.getCaseGroupInfo(cmd);
                    // })
					//引入多选框样式
					Utils.eventTrClickCallback($(Dom.connectEnvironmentList), function() {

					})
                    //设置分页
                    self.initPaging($(Dom.connectEnvironmentList), 10)
                }
            });
        },
		// 已关联环境列表
        getRelaEnvironmentList: function(cmd) {
            var self = this;
            var _checkObj =	$('#JS_getMachineList').find("input[type='radio']:checked");
            var _machineId ="";
			_checkObj.each(function (){
				_machineId = $(this).val();
			})
            var cmd = "machineId=" + _machineId;
            console.log(cmd);
            Rose.ajax.postJson(srvMap.get('getRelaEnvironmentList'), cmd, function(json, status) {
                if (status) {

                    var template = Handlebars.compile(Tpl.getEnvironmentListInMachine);
                    console.log(json.data)
                    $(Dom.getRelaEnvironmentList).html(template(json.data));

                    var template = Handlebars.compile(Tpl.getRelaEnvironmentList);
                    console.log(json.data)
                    $(Dom.getRelaEnvironmentList).html(template(json.data));

                    //单击选中
                    /*self.eventClickChecked($(Dom.getRelaMachineList));*/
                    //双击关联用例
                    // self.eventDClickCallback($(Dom.getCaseGroupList), function() {
                    //     var _data = self.getCheckedCaseGroup();
                    //     var cmd = "groupId=" + _data.groupId;
                    //     self.getCaseGroupInfo(cmd);
                    // })
					//引入多选框样式
					Utils.eventTrClickCallback($(Dom.getRelaEnvironmentList), function() {

					})
                    //设置分页
                    self.initPaging($(Dom.getRelaEnvironmentList), 4)
                }
            });
        },
		// 删除已关联环境
        delRelaEnvironment: function() {
            var self = this;
            $("#JS_deleteConnectEnvironment").unbind('click');
            $("#JS_deleteConnectEnvironment").bind('click',function(){
                var _data = self.getCheckedRelaEnvironment();
                if (_data) {
                    var _checkObj =	$('#JS_getMachineList').find("input[type='radio']:checked");
		            var _machineId ="";
					_checkObj.each(function (){
						_machineId = $(this).val();
					})
                    var _envIdsArray = [];
                    _data.each(function() {
                        _envIdsArray.push($(this).val());
                    })
                    var _cmd = "machineId=" + _machineId;
                    var _cmd1 = "&envIds=" + _envIdsArray.join(",");
                    var cmd = _cmd + _cmd1;
                    console.log(cmd);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.postJson(srvMap.get('delRelaEnvironment'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除已关联环境成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRelaEnvironmentList("machineId=" + _machineId);
                            }, 1000)
                        }
                    });
                }
            });
        },
		//获取选中已关联环境
        getCheckedRelaEnvironment: function() {
            var _obj = $(Dom.getRelaEnvironmentList).find("input[type='checkbox']:checked").parents("tr");
            var _envId = _obj.find("input[name='envId']");
            console.log(_envId);
            if (_envId.length == 0) {
                window.XMS.msgbox.show('请先选择一个已关联环境！', 'error', 2000);
                return;
            } else {
                var _data = $(_envId);
                data = _data;
                console.log(data);
            }
            return data;
        },
		updateMachineInfo:function(){
			var self = this;
			var _checkObj =	$('#JS_getMachineList').find("input[type='radio']:checked");
			var _machineId ="";
			_checkObj.each(function (){
				_machineId = $(this).val();
			})
			Rose.ajax.postJson(srvMap.get('getMachineInfo'), 'machineId='+_machineId, function(json, status) {
				if (status) {
					var _form = $(Dom.addMachineInfoForm);
					var template = Handlebars.compile(Tpl.addMachineInfo);
					_form.html(template(json.data));
					/*alert(json.data.content[0].envType);
					$("#query_envType").val(json.data.content[0].envType);*/
					// 设置下拉框选中值
					Utils.setSelected(_form);
					// //弹出层
					$(Dom.addMachineInfoModal).modal('show');
					$("#formName").html("修改机器");
					$("#JS_addMachineInfoSubmit").unbind('click');
					//点击保存
					$("#JS_addMachineInfoSubmit").bind('click',function(){
					var cmd = _form.serialize();
					cmd = cmd + "&machineId=" +_machineId;
					Rose.ajax.postJson(srvMap.get('updateMachineInfo'), cmd, function(json, status) {
						if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('修改成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addMachineInfoModal).modal('hide');
								setTimeout(function(){
									self.getMachineList();
								},1000)
						}
								});
					});
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

	module.exports=machine;
});
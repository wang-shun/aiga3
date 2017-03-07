define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "caseTempMng/";

	// 用例模板列表显示
	srvMap.add("getCaseTempList", pathAlias + "getCaseTempList.json", "/sys/template/list");
	//系统大类下拉框显示
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");
	//删除模板
	srvMap.add("delCaseTemp", pathAlias + "getFunList.json", "/sys/template/del");
	//新增用例模板
	srvMap.add("addCaseTemp", pathAlias + "getCaseTempList.json", "/sys/template/add");
	srvMap.add("viewCaseTemp", pathAlias + "getCaseTempList.json", "/sys/template/view");
	//获取模板因子
	srvMap.add("getTempFactory", pathAlias + "getFactoryList.json", "/sys/template/factoryList");
    //获取组件树
	srvMap.add("getCompTree", pathAlias + "getCompTree.json", "/sys/template/compTree");
    //获取组件信息
    srvMap.add("getCompinfo", "componentManage/getCompinfo.json", "");
	//保存组件信息
    srvMap.add("addAutoTestTemp", "componentManage/getCompinfo.json", "/sys/caseTemplat/addAutoTestTemp");    

	// 模板对象
	var Tpl = {
		getCaseTempList: require('tpl/caseTempMng/getCaseTempList.tpl'),
		getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		getSubSysList: require('tpl/caseTempMng/getSubSysList.tpl'),
		getFunList: require('tpl/caseTempMng/getFunList.tpl'),
		getCaseTempForm: require('tpl/caseTempMng/getCaseTempForm.tpl'),
		getFactory: require('tpl/caseTempMng/getFactory.tpl'),
		newAutoCaseTemp: require('tpl/caseTempMng/newAutoCaseTemp.tpl'),
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
	    modalAutoTempForm:'#modal_AutoCaseTempForm',
	    modaltestCaseForm:'#modal_testCaseForm',
	    caseTempForm:'#JS_CaseTempForm',
	    tempFormInner:'#JS_TempFormInner',


	    queryCaseTempForm:'#JS_queryCaseTempForm',
	    //组件树
	    compTree:'#compTree',




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
			this.editCaseTemp();
			this.newAutoCaseTemp();
			this.newTestCase();
					
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
					self.eventClickChecked($(Dom.getCaseTempList), function() {

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
					self.getCaseTempList(cmd);
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
				$("#myModalLabel").html("新增模板");
				//加载form表单
				var template = Handlebars.compile(Tpl.getCaseTempForm);
				$(Dom.tempFormInner).html(template());
				self.getSysList("#add_sysId");
				self.sysSelected();
				self.subsysSelected();				

				//加载因子
				var empty={'data':''};
				var template1 = Handlebars.compile(Tpl.getFactory);
				$("#Form_factory").html(template1(empty));
				$("#addFactory").bind('click', function() {
					$("#Form_factory").append(template1(empty));
					$("#Form_factory:last-child").find("button").bind('click',function(){
						this.parent().parent().remove();
					})
				})				


				var _form = $(Dom.caseTempForm);
				//_form.bootstrapValidator('validate');
				// 表单提交
				$("#JS_addAndSaveCaseTemp").bind('click', function() {

						// 表单校验：成功后调取接口
						//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
							var cmd = _form.serialize();
							console.log(cmd);
							// self.getUserinfoList(cmd);
							Rose.ajax.getJson(srvMap.get('addCaseTemp'), cmd, function(json, status) {
								if (status) {
									// 添加用户成功后，刷新用户列表页
									XMS.msgbox.show('添加模板成功！', 'success', 2000)
										// 关闭弹出层
									$(Dom.modalCaseTempForm).modal('hide')
									setTimeout(function() {
										self.getCaseTempList();
									}, 1000)
								}
							});
						// });
					})

				$(Dom.addUserinfoReset).bind('click', function() {
					_form.data('bootstrapValidator').resetForm(true);
				})

				

			})
		},

		//生成自动化模板
		newAutoCaseTemp: function() {
			var self = this;
			var cmd;
			$(Dom.createAutoTestTemp).bind('click', function() {

				// $(Dom.addUserinfoScroll).slimScroll({
				// 	"height": '420px'
				// });
				
				var _data = self.getCheckedRow(Dom.getCaseTempList);
				if (_data) {
					cmd = "caseId="+_data.caseId;
					$('#tempName1').val(_data.caseName+'_');
					$(Dom.modalAutoTempForm).modal('show');
					self.getCompTree();
					$('#messageAddComp').show();
					$('#compThead').hide();	
					$('#compBody').empty();		
				};

				
			});

			//删除组件
			$("#deleComp").bind('click', function() {
				var _data = self.getCompCheckedRow("#compBody");
				if(_data) {
					_data.obj.remove();
				};
			});

			//保存自动化模板
			$(Dom.modalAutoTempForm).find("button[name='save']").bind('click', function() {
				cmd = cmd+"&tempName="+$('#tempName1').val()+$('#tempName2').val();
				$("#compBody").find("tr").each(function(){
				    var tdArr = $(this).children();
				    cmd = cmd+"&compId="+tdArr.eq(0).find("input").val();//compId
				    cmd = cmd+"&compOrder="+tdArr.eq(3).find("input").val();//compOrder
				 });
				console.log(cmd+"addauto");
				Rose.ajax.getJson(srvMap.get('addAutoTestTemp'), cmd, function(json, status) {
					if (status) {
						// 添加用户成功后，刷新用户列表页
						XMS.msgbox.show('自动化模板生成成功！', 'success', 2000)
						// 关闭弹出层
						$(Dom.modalAutoTempForm).modal('hide')
						
					}
				});				
			});
			$(Dom.modalAutoTempForm).find("button[name='cancel']").bind('click', function() {
				$(Dom.modalAutoTempForm).modal('hide')
				alert();
			});
		},	

        newTestCase: function() {
			var self = this;
			$(Dom.createTest).bind('click', function() {

				// $(Dom.addUserinfoScroll).slimScroll({
				// 	"height": '420px'
				// });
			var _data = self.getCheckedRow(Dom.getCaseTempList);
			if (_data) {
				$(Dom.modaltestCaseForm).modal('show');
			};
				//加载form表单
				

			})
		},					

		// 查看与编辑模板
		editCaseTemp: function() {
			var self = this;
			$(Dom.viewCaseTemp).bind('click', function() {

				
				//加载form表单
				var _data = self.getCheckedRow(Dom.getCaseTempList);
				if (_data) {
					$(Dom.addUserinfoScroll).slimScroll({
						"height": '420px'
					});
					// 弹出层
					$(Dom.modalCaseTempForm).modal('show');
					$("#myModalLabel").html("查看与编辑");					
					var _caseId = _data.caseId;
					alert(_caseId);
					Rose.ajax.getJson(srvMap.get('viewCaseTemp'), 'caseId=' + _caseId, function(json, status) {
						if (status) {
							var template = Handlebars.compile(Tpl.getCaseTempForm);
							self.getSysList("#add_sysId");
							self.sysSelected();
							self.subsysSelected();
							$(Dom.tempFormInner).html(template(json.data));
							$("#add_important").val(json.data.important);
							$("#add_sysId").val(json.data.sysId);
							$("#add_subSysId").val(json.data.subSysId);
							$("#add_funId").val(json.data.funId);
							$("#add_busiId").val(json.data.busiId);
							
						}
					});
					//加载因子列表
					Rose.ajax.getJson(srvMap.get('viewCaseTemp'), 'caseId=' + _caseId, function(json, status) {
						if (status) {
							var template1 = Handlebars.compile(Tpl.getFactory);
							$("#Form_factory").html(template1(json.data));
							var empty={'data':''};
							$("#addFactory").bind('click', function() {
								$("#Form_factory").append(template1(empty));
							})	
						}
					});					
				}				
				
				var _form = $(Dom.caseTempForm);
				//_form.bootstrapValidator('validate');
				// 表单提交
				$("#JS_addAndSaveCaseTemp").bind('click', function() {

						// 表单校验：成功后调取接口
						//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
							var cmd = _form.serialize();
							console.log(cmd);
							// self.getUserinfoList(cmd);
							Rose.ajax.getJson(srvMap.get('addCaseTemp'), cmd, function(json, status) {
								if (status) {
									
									XMS.msgbox.show('修改模板成功！', 'success', 2000)
										// 关闭弹出层
									$(Dom.modalCaseTempForm).modal('hide')
									setTimeout(function() {
										self.getUserinfoList();
									}, 1000)
								}
							});
						// });
					})

				$(Dom.addUserinfoReset).bind('click', function() {
					_form.data('bootstrapValidator').resetForm(true);
				})

				

			})
		},		
		// 删除模板
		deleCaseTemp: function() {
			var self = this;

			$(Dom.deleCaseTemp).bind('click', function() {
				var _data = self.getCheckedRow(Dom.getCaseTempList);
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
		
        //获取组件信息
		getCompinfo:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCompinfo'), cmd, function(json, status) {
					if(status) {
						$('#messageAddComp').hide();
						$('#compThead').show();
	            		var template = Handlebars.compile(Tpl.newAutoCaseTemp);
	            		$("#compBody").append(template(json.data));
						self.eventClickChecked($("#compBody"), function() {

						})	            		
					}
		  	});			
		},

		getCompTree:function(){
			var self = this;
			var setting = {
				check: {
					enable: false
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
		                pIdKey: "pid"		
					}
				},
					
				callback:{
					 beforeClick: function(treeId, treeNode, clickFlag) {
                        return (!treeNode.isParent);
                     }, 
					 onClick: function(event, treeId, treeNode){
                        console.log(treeNode);
                        var cmd = "compId=" + treeNode.id;
                        self.getCompinfo(cmd);
                        //self.addComp(cmd1);
					 }
				}			
			};				

			Rose.ajax.getJson(srvMap.get('getCompTree'), '', function(json, status) {
					if(status) {
	            		$.fn.zTree.init($(Dom.compTree), setting, json.data);
					}
		  	});
		},


		// 获取列表当前选中行
		getCheckedRow: function(obj) {
			var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
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

		//获取选中组件
		getCompCheckedRow: function() {
			var _obj = $("#compBody").find("input[type='radio']:checked").parents("tr");
			var _Id = _obj.find("input[name='compId']")
			
			var _name = _obj.find("input[name='compName']")
			var data = {
				obj:"",
				compId: "",
				compName: "",
			}
			if (_Id.length == 0) {
				window.XMS.msgbox.show('请先选择一个组件！', 'error', 2000);
				return;
			} else {
				data.compId = _Id.val();
				data.compName = _name.val();
				data.obj=_obj;
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
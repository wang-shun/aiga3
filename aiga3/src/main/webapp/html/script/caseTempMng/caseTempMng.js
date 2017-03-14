define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "caseTempMng/";

	// 用例模板列表显示 ok
	srvMap.add("getCaseTempList", pathAlias + "getCaseTempList.json", "case/template/list");
	//系统大类下拉框显示 OK
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框 OK
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框 OK
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");
	//删除模板 ok
	srvMap.add("delCaseTemp", pathAlias + "getFunList.json", "case/template/del");
	//获取模板信息 ok
    srvMap.add("getCaseTempInfo", pathAlias +"getCaseTempInfo.json", "case/template/get"); 	
	//新增用例模板 Ok
	srvMap.add("addCaseTemp", pathAlias + "getCaseTempList.json", "case/template/save");
	//修改用例模板 
	srvMap.add("updateCaseTemp", pathAlias + "getCaseTempList.json", "case/template/update");
    //获取组件树 
	srvMap.add("getCompTree", pathAlias + "getCompTree.json", "sys/cache/commenCompTree");
    //获取组件信息 OK
    srvMap.add("getCompinfo", "componentManage/getCompinfo.json", "sys/component/findone");
	//保存自动化模板
    srvMap.add("addAutoTestTemp", "componentManage/getCompinfo.json", "auto/template/saveList");
	//保存测试用例
    srvMap.add("addTestCase", "componentManage/getCompinfo.json", "case/instance/save"); 
    

	// 模板对象
	var Tpl = {
		getCaseTempList: require('tpl/caseTempMng/getCaseTempList.tpl'),
		getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		getSubSysList: require('tpl/caseTempMng/getSubSysList.tpl'),
		getFunList: require('tpl/caseTempMng/getFunList.tpl'),
		getCaseTempForm: require('tpl/caseTempMng/getCaseTempForm.tpl'),
		getFactorList: require('tpl/caseTempMng/getFactorList.tpl'),
		compList: require('tpl/caseTempMng/compList.tpl'),
		getFactorForm: require('tpl/caseTempMng/getFactorForm.tpl'),
		getTestFactorList: require('tpl/caseTempMng/getTestFactorList.tpl'),

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
	    modalTestCaseForm:'#modal_testCaseForm',
	    caseTempForm:'#JS_CaseTempForm',
	    testForm:'#JS_TestForm',

	    queryCaseTempForm:'#JS_queryCaseTempForm',
	    //组件树
	    compTree:'#compTree',

	    //
	    factorForm:'#JS_factorForm',
	    factorList:'#JS_factorList',
	    testFactorList:'#JS_testCaseFactorList',
	};

	//下拉框容器
	var dropChoice1 = {
		
		getSysList: '#query_sysId',
		getSubsysList: '#query_subSysId',
		getFunList: '#query_funId',		
	};
	var dropChoice2 = {
		
		getSysList: '#add_sysId',
		getSubsysList: '#add_subSysId',
		getFunList: '#add_funId',			
	}	

	

	var currentPage = 1;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {

			this.getCaseTempList();
			this.getSysList(dropChoice1);
			this.addCaseTemp();
			this.queryCaseTemp();
			this.editCaseTemp();
			this.newAutoCaseTemp();
			this.newTestCase();
					
		},

		//系统大类下拉框
		getSysList: function(obj,callback) {
			var self = this;
			Rose.ajax.getJson(srvMap.get('getSysList'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSysList);
					$(obj.getSysList).html(template(json.data));
					self.sysSelected(obj);
					if(callback){
						callback();

					}
					console.log(json.data)
				}
				
			});
		},

		//系统大类下拉框选择事件
		sysSelected: function(obj) {
			var self = this;
			$(obj.getSysList).find("select").change(function() {
				var id = $(obj.getSysList).find("select").val();
				console.log(id);
				self.getSubSysList(id,obj);
			});
			
		},

		//系统子类下拉框选择事件
		subsysSelected: function(obj) {
			var self = this;
			$(obj.getSubsysList).find("select").change(function() {
				var id = $(obj.getSubsysList).find("select").val();
				self.getFunList(id,obj);
			});		
		},
		//系统子类下拉框
		getSubSysList: function(id,obj) {
			var self = this;
			Rose.ajax.getJson(srvMap.get('getSubsysList'), 'sysid='+id, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getSubSysList);
					$(obj.getSubsysList).html(template(json.data));
					console.log(json.data)
					self.subsysSelected(obj);
				}
			});
		},
		//功能点下拉框
		getFunList: function(id,obj) {
			Rose.ajax.getJson(srvMap.get('getFunList'), 'subsysid='+id, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getFunList);
					$(obj.getFunList).html(template(json.data));
					console.log(json.data)
				}
			});
		},

		// 用例模板列表
		getCaseTempList: function(cmd) {
			var self = this;
			cmd = cmd+'&pageNum='+currentPage;
			Handlebars.registerHelper("transformatImp",function(value){
          		if(value==1){
            		return "一级用例";
          		}else if(value==2){
           		 	return "二级用例";
         		}else if(value==3){
           		 	return "三级用例";
         		}else if(value==4){
           		 	return "四级用例";
         		}
        	});
			Handlebars.registerHelper("transformatType",function(value){
          		if(value==1){
            		return "UI类";
          		}else if(value==2){
           		 	return "接口类";
         		}else if(value==3){
           		 	return "后台进程类";
         		}
        	});        	
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
				_form.find('button[name="reset"]').bind('click',function(){
					$("#queryCaseName").val('');
					$("#query_important").val('');
					$("#query_busi").val('');
					$("#query_sysId").find('select').val('');
					$("#query_subSysId").find('select').val('');
					$("#query_funId").find('select').val('');
				});
		},
		

		// 新增模板
		addCaseTemp: function() {
			var self = this;
			$(Dom.addCaseTemp).bind('click', function() {

				$("#JS_factoryBody").slimScroll({
					"height": '300px'
				});				
			    // 弹出层
				$(Dom.modalCaseTempForm).modal('show');
				$("#myModalLabel").html("新增模板");
				//加载form表单
				var template = Handlebars.compile(Tpl.getCaseTempForm);
				$(Dom.caseTempForm).html(template());
				$("#JS_messageAddFactor").show();
				$('#factorThead').hide();
				$('#JS_factorList').hide();
				$(Dom.factorList).empty();				
				//加载下拉框
				self.getSysList(dropChoice2)
				
				self.addFactor();		
				self.deleFactor();



				var _form = $(Dom.caseTempForm);
				//_form.bootstrapValidator('validate');
				// 表单提交
				$("#JS_SaveCaseTemp").unbind('click');
				$("#JS_SaveCaseTemp").bind('click', function() {

						// 表单校验：成功后调取接口
						//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
							var cmd = _form.serialize();

							// $(Dom.factorList).find("tr").each(function(){
							//     var tdArr = $(this).children();
							//     cmd = cmd+"&factorName="+tdArr.eq(1).find("input").val();
							//     cmd = cmd+"&remark="+tdArr.eq(2).find("input").val();
							//  });	
							var factors = [];
							$(Dom.factorList).find("tr").each(function(){
							    var tdArr = $(this).children();
//							    cmd = cmd+"&factorName="+tdArr.eq(1).find("input").val();
//							    cmd = cmd+"&remark="+tdArr.eq(2).find("input").val();
							    
							    factors.push({
							    	"factorName":tdArr.eq(1).find("input").val(),
							    	"remark":tdArr.eq(2).find("input").val()
							    });
							});
							cmd += "&factors="+JSON.stringify(factors);
							console.log(cmd);						
							Rose.ajax.postJson(srvMap.get('addCaseTemp'), cmd, function(json, status) {
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
			});
		$(Dom.modalCaseTempForm).find("button[name='cancel']").unbind('click');
			$(Dom.modalCaseTempForm).find("button[name='cancel']").bind('click', function() {
				$(Dom.modalCaseTempForm).modal('hide');
			});				
		},
		//查看与编辑
		editCaseTemp: function() {
			var self = this;
			$(Dom.viewCaseTemp).bind('click', function() {

				var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
				if (_data) {				
					$(Dom.modalCaseTempForm).modal('show');
					$("#myModalLabel").html("查看编辑模板");
					//加载form表单
					self.getCaseTempInfo("caseId="+_data.caseId);
					self.addFactor();
					self.deleFactor();
					

					$("#JS_factoryBody").slimScroll({
						"height": '300px'
					});					
					var _form = $(Dom.caseTempForm);
					//_form.bootstrapValidator('validate');
					// 表单提交
					$("#JS_SaveCaseTemp").unbind('click')
					$("#JS_SaveCaseTemp").bind('click', function() {

							// 表单校验：成功后调取接口
							//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
								var cmd = _form.serialize()+"&caseId="+_data.caseId;
								var cmd = {};
								
								// var caseId = _data.caseId;
								// var caseName = $("#add_caseName").val();
								// var important = $("#add_important").val();
								// var sysId = $("#add_sysId").find("select").val();
								// var subsysId = $("#add_subSysId").find("select").val();
								// var funId = $("#add_funId").find("select").val();
								// var busiId = $("#add_busiId").val();
								// var caseType = $("#add_caseType").find("select").val();
								// var operateDesc = $("#JS_add_operateDesc").val();
								var id;
								var name;
								var remark;
								// self.getUserinfoList(cmd);
								// cmd = {"caseName":caseName,
								// 		"caseId":caseId,
								// 		"important":important,
								// 		"sysId":sysId,
								// 		"subsysId":subsysId,
								// 		"funId":funId ? funId : "",
								// 		"busiId":busiId ? busiId: "",
								// 		"caseType":caseType,
								// 		"operateDesc":operateDesc
								// 		};
								var factors = [];
								$(Dom.factorList).find("tr").each(function(){
								    var tdArr = $(this).children();
								    // cmd = cmd+"&factorId="+tdArr.eq(0).find("input").val();
								    // cmd = cmd+"&factorName="+tdArr.eq(1).find("input").val();
								    // cmd = cmd+"&remark="+tdArr.eq(2).find("input").val();
									id = tdArr.eq(0).find("input").val();
								    name = tdArr.eq(1).find("input").val();
								    remark = tdArr.eq(2).find("input").val();
								    
								    factors.push({
								    	"factorId":id,
								    	"factorName":name,
								    	"remark":remark
								    });
								 });
								cmd += "&factors="+JSON.stringify(factors);
								console.log(cmd);						
								Rose.ajax.postJson(srvMap.get('updateCaseTemp'), cmd, function(json, status) {
									if (status) {
										XMS.msgbox.show('修改模板成功！', 'success', 2000)
										// 关闭弹出层
										$(Dom.modalCaseTempForm).modal('hide')
										setTimeout(function() {
											self.getCaseTempList();
										}, 1000)
									}
								});
							// });
						});
				}

				
			});
			$(Dom.modalCaseTempForm).find("button[name='cancel']").unbind('click');
			$(Dom.modalCaseTempForm).find("button[name='cancel']").bind('click', function() {
				$(Dom.modalCaseTempForm).modal('hide');
			});			
		},	
		//生成自动化模板
		newAutoCaseTemp: function() {
			var self = this;
			var caseId;
			$(Dom.createAutoTestTemp).bind('click', function() {

				$("#JS_compTree").slimScroll({
					"height": '320px'
				});
				$("#JS_compBody").slimScroll({
					"height": '320px'
				});				
				//获取当前选中模板
				var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
				if (_data) {
					caseId = _data.caseId;
					$('#tempName1').val(_data.caseName+'_');
					$(Dom.modalAutoTempForm).modal('show');
					self.getCompTree();
					$('#messageAddComp').show();
					$('#compThead').hide();
					$('#compBody').empty();		
				};

				
			});

			//删除组件comp
			this.deleComp();
			this.getInfoForAuto();
			//保存自动化模板
			$(Dom.modalAutoTempForm).find("button[name='save']").unbind();
			$(Dom.modalAutoTempForm).find("button[name='save']").bind('click', function() {


				var name = $('#tempName1').val()+$('#tempName2').val();
				
				var cmd = {
					'caseId':caseId,
					'tempName':name,
				};
				var compRequestList = [];
				$("#compBody").find("tr").each(function(){
				    var tdArr = $(this).children();
				    var compId = tdArr.eq(0).find("input").val();
				    var compOrder = tdArr.eq(3).find("input").val();
				    compRequestList.push({'compId':compId,'compOrder':compOrder});
				 });
				cmd["compRequestList"] = compRequestList;

				console.log(cmd);
				Rose.ajax.postJson(srvMap.get('addAutoTestTemp'), cmd, function(json, status) {
					if (status) {
						// 添加用户成功后，刷新用户列表页
						XMS.msgbox.show('自动化模板生成成功！', 'success', 2000)
						// 关闭弹出层
						$(Dom.modalAutoTempForm).modal('hide')
					}
				});				
			});
			$(Dom.modalAutoTempForm).find("button[name='cancel']").bind('click', function() {
				$(Dom.modalAutoTempForm).modal('hide');
			});
		},


		//生成测试用例
        newTestCase: function() {
			var self = this;
			var cmd;
			$(Dom.createTest).bind('click', function() {
				var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
				if (_data) {
					cmd = 'caseId='+_data.caseId;
					$(Dom.modalTestCaseForm).modal('show');
					$("#JS_bodyFactor").slimScroll({
						"height": '300px'
					});					
					$('#testName1').val(_data.caseName+'_');
					Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
						if(status) {
							var factor_template = Handlebars.compile(Tpl.getTestFactorList);
							$(Dom.testFactorList).html(factor_template(json.data.factors));
							self.eventClickChecked($(Dom.testFactorList), function() {

							})
						}
				  	});


				};
			});
			//保存测试用例
			$(Dom.testForm).find("button[name='save']").bind('click', function() {
				cmd = cmd+"&testName="+$('#testName1').val()+$('#testName2').val();
				
				var factors = [];
				
				$(Dom.testFactorList).find("tr").each(function(){
				    var tdArr = $(this).children();
				    if(tdArr.eq(0).find("input").is(':checked')){

//					    cmd = cmd+"&factorId="+tdArr.eq(0).find("input").val();
//					    cmd = cmd+"&factorName="+tdArr.eq(1).find("input").val();
//					    cmd = cmd+"&remark="+tdArr.eq(2).find("input").val();
//					    cmd = cmd+"&factorValue="+tdArr.eq(3).find("input").val();
//					    cmd = cmd+"&factorOrder="+tdArr.eq(4).find("input").val();
					    
					    factors.push({
					    	"factorId":tdArr.eq(0).find("input").val(),
					    	"factorName":tdArr.eq(1).find("input").val(),
					    	//"remark":tdArr.eq(2).find("input").val(),
					    	"factorValue":tdArr.eq(3).find("input").val(),
					    	"factorOrder":tdArr.eq(4).find("input").val()
					    });
					}
				 });
				
				cmd += "&factors="+JSON.stringify(factors);
				
				console.log(cmd);
				Rose.ajax.postJson(srvMap.get('addTestCase'), cmd, function(json, status) {
					if (status) {
						// 添加用户成功后，刷新用户列表页
						XMS.msgbox.show('测试用例生成成功！', 'success', 2000)
						// 关闭弹出层
						$(Dom.modalTestCaseForm).modal('hide')
						
					}
				});				
			});
			$(Dom.testForm).find("button[name='cancel']").bind('click', function() {
				$(Dom.modalTestCaseForm).modal('hide');
			});			
		},					


			
		// 删除模板
		deleCaseTemp: function() {
			var self = this;
			$(Dom.deleCaseTemp).unbind('click');
			$(Dom.deleCaseTemp).bind('click', function() {
				var _data = self.getCaseTempCheckedRow(Dom.getCaseTempList);
				if (_data) {
					var _caseId = "caseId="+_data.caseId;
					console.log(_caseId);
					Rose.ajax.getJson(srvMap.get('delCaseTemp'),_caseId, function(json, status) {
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
		
//获取模板信息
		getCaseTempInfo:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
					if(status) {
						var factor_template = Handlebars.compile(Tpl.getFactorList);
						var caseTemp_template = Handlebars.compile(Tpl.getCaseTempForm);
						$("#JS_messageAddFactor").hide();
						$('#factorThead').show();
						$(Dom.caseTempForm).html(caseTemp_template(json.data));
						//加载下拉框
						//self.getSysList("#add_sysId");
						self.getSysList(dropChoice2,function(){
							$(Dom.caseTempForm).find("select[name='important']").val(json.data.important);
							$(Dom.caseTempForm).find("select[name='sysId']").val(json.data.sysId);
							$(Dom.caseTempForm).find("select[name='sysId']").change();
							$(Dom.caseTempForm).find("select[name='subSysId']").val(json.data.subSysId);
							$(Dom.caseTempForm).find("select[name='subSysId']").change();
							$(Dom.caseTempForm).find("select[name='funId']").val(json.data.funId);
							$(Dom.caseTempForm).find("select[name='busiId']").val(json.data.busiId);
							$(Dom.caseTempForm).find("select[name='caseType']").val(json.data.caseType);
							$(Dom.caseTempForm).find("textarea[name='operateDesc']").val(json.data.operateDesc);								
						});
						$(Dom.factorList).html(factor_template(json.data.factors));
							self.eventClickChecked($(Dom.factorList), function() {
						})
					}
		  	});			
		},
		getInfoForAuto:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getCaseTempInfo);
					$("#JS_getCaseTempInfo").html(template(json.data));
				}
		  	});			
		},		
		
//新增因子
		addFactor:function(cmd){
			var self = this;
			$("#JS_addFactor").unbind('click');
			$("#JS_addFactor").bind('click',function(){
				
				var factor_template = Handlebars.compile(Tpl.getFactorList);
				var empty={'data':''};
				$("#JS_messageAddFactor").hide();
				$('#factorThead').show();
				$('#JS_factorList').show();
				$(Dom.factorList).append(factor_template(empty));
				self.eventClickChecked($(Dom.factorList), function() {
				})
				// $("#compTable").slimScroll({
				// 	"height": '300px',
				// 	alwaysVisible: true,
				// });				
			});
		},
		//删除因子
		deleFactor:function(cmd){

			var self = this;
			$('#JS_delFactor').unbind('click');
			$('#JS_delFactor').bind('click',function(){
				var factor = self.getCheckedRow(Dom.factorList);
				if(factor.find("input[name='factorName']").length==0){
					window.XMS.msgbox.show('请先选择一个因子！', 'error', 2000);
					return;						
				}else{
					factor.remove();
				}
				if($(Dom.factorList+" tr").length == 0){
					$("#JS_messageAddFactor").show();
					$('#factorThead').hide();
					$('#JS_factorList').hide();							
				};
			});

		},
		//删除组件
		deleComp:function(cmd){

			var self = this;
			$('#deleComp').unbind('click');
			$('#deleComp').bind('click',function(){
				var comp = self.getCheckedRow("#compTable");
				if(comp.find("input[name='compId']").length==0){
					window.XMS.msgbox.show('请先选择一个组件！', 'error', 2000);
					return;						
				}else{
					comp.remove();
				}
				if($("#compBody"+" tr").length == 0){
					$("#messageAddComp").show();
					$('#compThead').hide();				
				};
			});		

		},	

        //获取组件信息
		getCompinfo:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCompinfo'), cmd, function(json, status) {
					if(status) {
						$('#messageAddComp').hide();
						$('#compThead').show();
	            		var template = Handlebars.compile(Tpl.compList);
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
		getCaseTempCheckedRow: function(obj) {
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

		// 获取列表当前选中行
		getCheckedRow: function(obj){
			var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
			return _obj;
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
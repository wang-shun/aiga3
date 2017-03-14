define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');

	var pathAlias = "case/caseSet/";
	// 显示用例集列表
	srvMap.add("getCaseSetList", pathAlias+"getCaseSetList.json", "sys/case/queryCase");
	// 根据Id查询用例集
	srvMap.add("getCaseById", pathAlias+"updateCaseinfo.json", "sys/case/queryCaseById");
	//新增用列集
	srvMap.add("addCaseSetinfo", pathAlias+"addCaseSetinfo.json", "sys/case/addCase");
	//用例类型
	srvMap.add("getCaseTypeSelect", pathAlias+"getCaseTypeSelect.json", "sys/organize/constants");
	//用例类型
	srvMap.add("getRepairManSelect", pathAlias+"getCaseTypeSelect.json", "sys/case/repairMan");
	//修改用例集
	srvMap.add("updateCaseinfo", pathAlias+"updateCaseinfo.json", "sys/case/updateCase");
	//删除用例集
	srvMap.add("deleCaseSet", pathAlias+"deleteCase.json", "sys/case/deleteCase");
	//关联用例集
	srvMap.add("connectCaseCollection", pathAlias+"getCaseSetList.json", "sys/case/connectCaseCollection");
	//关联用例（查询未显示用例）
	srvMap.add("queryUnconnectCase", pathAlias+"useCaseList.json", "sys/case/queryUnconnectCase");
	//系统大类下拉框显示 OK
	srvMap.add("getSysList", "caseTempMng/getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框 OK
	srvMap.add("getSubsysList", "caseTempMng/getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框 OK
	srvMap.add("getFunList", "caseTempMng/getFunList.json", "sys/cache/listFun");
	//关联用例（关联事件）
	srvMap.add("relCaseBtn", pathAlias+"addCaseSetinfo.json", "sys/case/connectCase");
	//关联全部用例
	srvMap.add("connectAllCase", pathAlias+"addCaseSetinfo.json", "sys/case/connectAllCase");
	//查看已关联用例sys/case/connectAllCase
	srvMap.add("queryConnectCawseById", pathAlias+"useCaseList.json", "sys/case/queryConnectCaseById");
	//删除已关联用例sys/case/connectAllCase
	srvMap.add("deleteConnectCase", pathAlias+"addCaseSetinfo.json", "sys/case/deleteConnectCase");
	//查询未关联用例组
	srvMap.add("queryUnconnectCaseGroup", pathAlias+"connectCaseList.json", "sys/case/queryUnconnectCaseGroup");


	// 模板对象
	var Tpl = {
		getCaseSetList: require('tpl/case/caseSet/getCaseSetinfoList.tpl'),
		addCaseSetinfo: require('tpl/case/caseSet/addCaseSetinfo.tpl'),
		queryCaseSetForm: require('tpl/case/caseSet/queryCaseSetForm.tpl'),
		connectCaseCollectionList: require('tpl/case/caseSet/connectCaseCollectionList.tpl'),
		connectCaseList: require('tpl/case/caseSet/connectCaseList.tpl'),
		connectCaseList: require('tpl/case/caseSet/connectCaseList.tpl'),
		useCaseList: require('tpl/case/caseSet/useCaseList.tpl'),
		useCaseLists: require('tpl/case/caseSet/useCaseLists.tpl'),
		getSysList: require('tpl/caseTempMng/getSysList.tpl'),
		getSubSysList: require('tpl/caseTempMng/getSubSysList.tpl'),
		getFunList: require('tpl/caseTempMng/getFunList.tpl'),
		queryCaseGroupList: require('tpl/case/caseSet/queryCaseGroupList.tpl'),
		queryCaseGroupsList: require('tpl/case/caseSet/queryCaseGroupsList.tpl'),
		queryCaseGroupForm: require('tpl/case/caseSet/queryCaseGroupForm.tpl'),
		queryUnconnectCaseGroupList: require('tpl/case/caseSet/queryUnconnectCaseGroupList.tpl'),
	};


	var Dom = {
		getCaseSetList:"#JS_getCaseSetList",
		getCaseSetinfoListTable:"#JS_getCaseSetinfoListTable",
		addCaseSet:"#JS_addCaseSet",
		addCaseSetinfoModal:"#JS_addCaseSetinfoModal",
		addCaseSetinfoForm:"#JS_addCaseSetinfoForm",
		deleCaseSet:"#JS_deleCaseSet",
		buttonCaseCollection:"#JS_connectCaseCollection",
		caseType:[],
		repairsId:[],
		caseAllId:"",
	}

	//下拉框容器
	var dropChoice1 = {
		
		getSysList: '#query_sysConnId',
		getSubsysList: '#query_subSysConnId',
		getFunList: '#queryConn_funId',		
	};
	var dropChoice2 = {
		
		getSysList: '#add_sysId',
		getSubsysList: '#add_subSysId',
		getFunList: '#add_funId',			
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


	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			this.getCaseTypeSelect();
			this.getrepairsIdSelect();
			this.initOrganize();
			// this.getCaseSetList();
			this.buttonReset();
			this.addCaseSetinfo();
		},


	//-------------------------------系统大类，子类，功能点下拉框------------------------------------------//
		//系统大类下拉框
		getSysList: function(obj,callback) {
			var self = this;
			// alert("111")
			Rose.ajax.postJson(srvMap.get('getSysList'), '', function(json, status) {

				if (status) {
					var template = Handlebars.compile(Tpl.getSysList);
					$(obj.getSysList).html(template(json.data));
					if(callback){
						callback();

					}
					console.log(json.data)
				}
				self.sysSelected(obj);
								

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
			Rose.ajax.postJson(srvMap.get('getSubsysList'), 'sysid='+id, function(json, status) {
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
			Rose.ajax.postJson(srvMap.get('getFunList'), 'subsysid='+id, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getFunList);
					$(obj.getFunList).html(template(json.data));
					console.log(json.data)
				}
			});
		},

		
	//---------------------------------------------------------------------------------//
        ///////初始化///////////
		initOrganize: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getCaseSetList'), cmd, function(json, status) {
				
				if (status) {
					var template = Handlebars.compile(Tpl.getCaseSetList);
					console.log(json.data);
					$(Dom.getCaseSetList).html(template(json.data));
					// self.addControl();

					// self.deleControl();
					//修改按钮
					self.updateCaseinfoButton();
					//删除按钮
					self.deleCaseSet();

					//关联用例集
					self.buttonCaseCollection();
                    
					// 重置按钮
					self.buttonReset();
					
					//关联用例
					self.connectCase();

					// 绑定单机当前行事件
				    self.eventClickChecked($(Dom.getCaseSetinfoListTable),function(){

				    });
				    // 绑定双击当前行事件
				    self.eventDClickCallback($(Dom.getCaseSetinfoListTable),function(){
				    	// 请求：用户基本信息
						// self.getControlinfo();
				    })
				    
				}
			});
		},
///*******************************************///界面1/用例集显示查询//*******************************************////
		//条件查询
		getCaseSetList : function(){
			var self = this;
			var _form = $("#JS_queryCaseSetForm");
			
			var template = Handlebars.compile(Tpl.queryCaseSetForm);
			_form.html(template({"queryCaseType":Dom.caseType}));
			$("#querycaseType").val("");

			$("#queryBtn").bind('click', function() {
				var cmd = _form.serialize();
				self.initOrganize(cmd);
			});
		},
		//条件查询重置
		buttonReset : function(){
			$("#reset").bind('click', function() { 
				$("#collectName").val("");
				$("#querycaseType").val("");
			});
		},
		
///*******************************************///界面2/新增用列集///*******************************************/////
		addCaseSetinfo : function(cmd){
			var self = this;
			$(Dom.addCaseSet).bind('click', function() {

				var _form = $(Dom.addCaseSetinfoForm);

				var template = Handlebars.compile(Tpl.addCaseSetinfo);

				_form.html(template({"caseType":Dom.caseType,"repairsId":Dom.repairsId}));
				$("#caseType").val("");
				$("#repairsId").val("");
				$("#caseNum").val("");
				//弹出层
				$(Dom.addCaseSetinfoModal).modal('show');
				$("#JS_addCaseSetinfoSubmit").unbind('click');
				//点击保存
				$("#JS_addCaseSetinfoSubmit").bind('click',function(){
					var cmd = _form.serialize();
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('addCaseSetinfo'), cmd, function(json, status) {
						if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addCaseSetinfoModal).modal('hide');
								setTimeout(function(){
									self.initOrganize();
								},1000)
						}
					});
				});

			});
		},
		//获取用例集类型
		getCaseTypeSelect:function(){
			var self = this;
			var cmd="category=collectType";
			Rose.ajax.postJson(srvMap.get('getCaseTypeSelect'), cmd, function(json, status) {
				if (status) {
					Dom.caseType = json.data;
					console.log(Dom.caseType);
					self.getCaseSetList();
				}
			});
		},

		//获取维护人
		getrepairsIdSelect:function(){
			Rose.ajax.postJson(srvMap.get('getRepairManSelect'), 'repairMan', function(json, status) {
				if (status) {
					Dom.repairsId = json.data;
				}
			});
		},
		///////****************///修改//修改//********************////////
		//修改
		updateCaseinfo:function(){
		var self = this;
		   var _checkObj =	$('#JS_getCaseSetinfoListTable').find("input[type='checkbox']:checked");
		   if(_checkObj.length==0){
			   alert("请选择要修改的用例集!");
			   return false;
		   }
		   if(_checkObj.length>1){
			   alert("请选择一条记录修改!");
			   return false;
		   }
			var _collectId ="";
			_checkObj.each(function (){
				_collectId = 	$(this).val();
			})
			var cmd = "collectId=" +_collectId;
			Rose.ajax.postJson(srvMap.get('getCaseById'), cmd, function(json, status) {
				if (status) {
					var _form = $(Dom.addCaseSetinfoForm);
					var template = Handlebars.compile(Tpl.addCaseSetinfo);
					var a = JSON.stringify(json.data.caseType);
					var b = JSON.stringify(json.data.repairsId);
					var c = json.data;
					c["caseType"]=Dom.caseType;
					c["repairsId"]=Dom.repairsId;
					_form.html(template(c));
					$("#caseType").val(a);
					$("#repairsId").val(b);
					// //弹出层
					$(Dom.addCaseSetinfoModal).modal('show');
					$("#formName").html("修改用例集");
					$("#JS_addCaseSetinfoSubmit").unbind('click');
					//点击保存
					$("#JS_addCaseSetinfoSubmit").bind('click',function(){
					var cmd = _form.serialize();
					console.log(cmd);
					Rose.ajax.postJson(srvMap.get('addCaseSetinfo'), cmd, function(json, status) {
						if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('修改成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addCaseSetinfoModal).modal('hide');
								setTimeout(function(){
									self.initOrganize();
								},1000)
						}
					});
					});
				}
			});


		},
		//点击修改用例集按钮
		updateCaseinfoButton : function(){
			var self = this;
			$("#JS_viewCaseSet").unbind('click');
			$("#JS_viewCaseSet").bind('click',function(){
					self.updateCaseinfo();
			});
		},
		//删除
		deleCaseSet : function(){
			var self = this;
			var  collectIds="";
			var num =0 ;
			$(Dom.deleCaseSet).unbind('click');
			$(Dom.deleCaseSet).bind('click', function() {
				var  collectIds="";
				var num =0 ;
			   var _checkObj =	$('#JS_getCaseSetinfoListTable').find("input[type='checkbox']:checked");
			   if(_checkObj.length==0){
				   alert("请选择要删除的用例集!");
				   return false;
			   }
			   _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   collectIds += $(this).val()+",";		
				   }else{
					   collectIds += $(this).val();		
				   }
				   num ++;
				});
				 Rose.ajax.postJson(srvMap.get('deleCaseSet'), 'collectId=' + collectIds, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
							  self.initOrganize();
							

							}, 1000)
						}
					});
			});
		},


////////*******************************************//关联用例集/关联用例集////*******************************************////////
		//关联用例集
		connectCaseCollection : function(){
			var self = this;
			   var _checkObj =	$('#JS_getCaseSetinfoListTable').find("input[type='checkbox']:checked");
			   if(_checkObj.length==0){
				   alert("请选择要修改的用例集!");
				   return false;
			   }
			   if(_checkObj.length>1){
				   alert("请选择一条记录修改!");
				   return false;
			   }
				var _collectId =""
				_checkObj.each(function (){
					_collectId = 	$(this).val();
				})
				var cmd = {
					"collectId": _collectId
					}
			var cmd = "collectId="+_collectId+"&collectIds=";
			Rose.ajax.postJson(srvMap.get('getCaseSetList'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.connectCaseCollectionList);
					console.log("11"+json.data);
					$("#JS_connectCaseSetinfo").html(template(json.data));

					// //弹出层
					$("#JS_connectCaseSetinfoModal").modal('show');

					// 绑定单机当前行事件
				    self.eventClickChecked($("#JS_connectCaseCollectionList"),function(){

				    })
				    $("#JS_connectCaseCollectionButton").bind('click',function(){
				    	$("#JS_connectCaseCollectionList").find("tr").each(function(){
					    	var tdArr = $(this).children();
					    	if(tdArr.eq(0).find("input").is(':checked')){
						    	cmd = cmd+tdArr.eq(0).find("input").val()+",";
						    	console.log(cmd);
							}
					 	});
				    	
				    	Rose.ajax.postJson(srvMap.get('connectCaseCollection'), cmd, function(json, status) {
				    		if (status) {
				    			// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('关联成功！', 'success', 2000)
								// 关闭弹出层
								$("#JS_connectCaseSetinfoModal").modal('hide');
								setTimeout(function(){
									self.initOrganize();
								},1000)
				    		}
				    	});
				    });  
				}
			});

		},

		//关联用例集按钮
		buttonCaseCollection : function(){
			var self = this;
			$(Dom.buttonCaseCollection).unbind('click');
			$(Dom.buttonCaseCollection).bind('click',function(){
					self.connectCaseCollection();
			});
		},

////////*******************************************//关联用例/关联用例////*******************************************////////
		//关联用例
		connectCaseList : function(){
			var self = this;
			var _data = self.getCaseSetRow();
			var _collectId = _data.collectId;

			Rose.ajax.getJson(srvMap.get('getCaseById'), "collectId="+_collectId, function(json, status) {


				if (status) {
					var _form = $(Dom.addCaseSetinfoForm);
					var template = Handlebars.compile(Tpl.connectCaseList);
					// console.log(json.data);
					
					$("#collectId").val(_collectId);
					var cmd = $("#JS_queryUnconnectCaseForm").serialize();//"collectId="+_collectId;_form.serialize();

					$("#JS_connectCaseSetinfo").html(template(json.data));

					var template = Handlebars.compile(Tpl.queryCaseGroupForm);
					$("#Js_queryCaseGroupForm").html(template({}));
					// //弹出层
					$("#JS_connectCaseSetinfoModal").modal('show');

					var a = $("#types").val();
					//用例组
					self.queryUnconnectCase(_collectId,a);

					self.getSysList(dropChoice1);
					//未选定用例查寻
					self.connectCaseBtn(_collectId);

					//关联用例
					self.relCaseBtn(_collectId);

					//已关联按钮查询
					self.connectBtn(_collectId);

					//Table1
					self.casetable1(_collectId);
					self.casetable2(_collectId);
				}

			});
		},

		//关联用例按钮Table1
		casetable1 : function(collectId){
			var self = this;
			$("#JS_casetable1").unbind('click');
			$("#JS_casetable1").bind('click',function(){
				var template = Handlebars.compile(Tpl.queryCaseGroupForm);
				$("#Js_queryCaseGroupForm").html(template({}));
				self.queryConnectCaseById($("#collectId1").val(),$("#types").val());
			});
		},
		//关联用例组按钮Table2
		casetable2 : function(collectId){
			$("#collectId3").val(collectId)
			var self = this;
			$("#JS_casetable2").unbind('click');
			$("#JS_casetable2").bind('click',function(){
				// var template = Handlebars.compile(Tpl.queryCaseGroupForm);
				// $("#Js_queryCaseGroupForm").html(template({}));
				self.queryUnconnectCaseGroup(collectId);
				//关联用例组
				self.relCaseGroupBtn(collectId);
				//查询点击事件
				self.connectCaseGroupBtn(collectId);
			});
		},
		
		//关联用例按钮
		connectCase : function(){
			var self = this;
			$("#JS_connectCase").unbind('click');
			$("#JS_connectCase").bind('click',function(){
				var _data = self.getCaseSetRow();
				if(_data){
					self.connectCaseList();
				}
			});
		},
		//未关联用例组显示
		queryUnconnectCase : function(md,a){//sys/case/queryUnconnectCase
			var self = this;
			$("#collectId1").val(md);
			var _form = $("#JS_queryUnconnectCaseForm");
			var cmd = _form.serialize();
			alert(cmd);
			
			var cm = "collectId="+md+"&caseIds=";
			Rose.ajax.getJson(srvMap.get('queryUnconnectCase'), cmd, function(json, status) {

				if (status) {

					for (var i = json.data.length - 1; i >= 0; i--) {
							cm = cm+json.data[i].caseId+","
					}
					cm = cm+"&types="+a;
					Dom.caseAllId=cm;
					alert(cm);
					if (a=="1") {
						var template = Handlebars.compile(Tpl.useCaseList);

					}else if (a=="2"){
						var template = Handlebars.compile(Tpl.useCaseLists);
					}
					console.log(json.data);
				   
					$("#JS_useConnectCaseList").html(template(json.data));

					// 绑定单机当前行事件
					self.eventClickChecked($("#JS_useCaseListTable"),function(){
				    });
				    // 绑定单机当前行事件
					self.eventClickChecked($("#JS_useCaseListsTable"),function(){
				    });

				    //关联全部用例
					self.relCaseAllBtn(md);

					//已关联用例
					self.queryConnectCaseById(md,a);

					//删除已关联用例
					self.deleteConnectCase(md);

				}

			});
			
		},
		//---------------------------用例/用例组查询----------------------------------------//
		connectCaseBtn : function(md){
			var self = this;
			$("#collectId1").val(md);
			var _form = $("#JS_queryUnconnectCaseForm");
			var cmd = _form.serialize();
			$("#connectCaseBtn").unbind('click');
			$("#connectCaseBtn").bind('click',function(){
				var a = $("#types").val();
				self.queryUnconnectCase(cmd,a);
			});

		},


		//关联用例
		relCaseBtn : function(collectId){
			var self = this;
			$("#relCaseBtn").unbind('click');
			$("#relCaseBtn").bind('click',function(){
				var cmd="collectId="+collectId+"&caseIds=";
				var a = $("#types").val();
				$("#JS_useConnectCaseList").find("tr").each(function(){
			    	var tdArr = $(this).children();
			    	if(tdArr.eq(0).find("input").is(':checked')){
				    	cmd = cmd+tdArr.eq(0).find("input").val()+",";
				    	console.log(cmd);
					}
			 	});
			 	cmd = cmd+"&types="+a;
			 	console.log("7777777");
			 	console.log(cmd);
				Rose.ajax.postJson(srvMap.get('relCaseBtn'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('关联成功！', 'success', 2000)
						$("#collectId1").val(collectId);
						var _form = $("#JS_queryUnconnectCaseForm");
						var cm = _form.serialize();
						setTimeout(function(){
							self.queryUnconnectCase(cm,a);
							self.queryConnectCaseById(collectId,a);
						},1000)
					}
				});
			});
		},

		//关联全部
		relCaseAllBtn : function(collectId){
			var self = this;
			$("#relCaseAllBtn").unbind('click');
			$("#relCaseAllBtn").bind('click',function(){
				$("#collectId1").val(collectId);
				var cmd = $("#JS_queryUnconnectCaseForm").serialize();
				Rose.ajax.postJson(srvMap.get('connectAllCase'), cmd, function(json, status) {
					if (status) {
						var a = $("#types").val();
						XMS.msgbox.show('关联成功！', 'success', 2000)
						setTimeout(function(){
							self.queryUnconnectCase(cmd,a);
							self.queryConnectCaseById(collectId,a);
						},1000)
					}
				});
			});
		},
		//显示关联的用例
		queryConnectCaseById:function(collectId,a){
			var self = this;
			$("#collectId2").val(cmd);
			$("#types1").val(a);
			var cmd = $("#Js_queryCaseGroupForm").serialize();
			Rose.ajax.postJson(srvMap.get('queryConnectCawseById'), cmd, function(json, status) {
				if (status) {
					if (a=="1") {
						var template = Handlebars.compile(Tpl.queryCaseGroupList);
					}
					else if (a=="2") {
						var template = Handlebars.compile(Tpl.queryCaseGroupsList);
					}
					
					console.log(json.data);
					$("#Js_queryCaseGroupList").html(template(json.data));
					self.eventClickChecked($("#JS_queryCaseGroupListTable"),function(){
				    });
				}
			});
		},

		//已关联用例查询
		connectBtn: function(collectId){    //a表示用例类型
			var self = this;
			$("#JS_connectBtn").unbind('click');
			$("#JS_connectBtn").bind('click',function(){
				var a = $("#types1").val();
				self.queryConnectCaseById(collectId,a);
			});
		},

		//删除已关联用例
		deleteConnectCase : function(collectId){
			var self = this;
			$("#JS_deleteConnectCase").unbind('click');
			$("#JS_deleteConnectCase").bind('click',function(){
				var ids="";
				var  cmd="collectId="+collectId+"&ids=";
				var num =0 ;
				var _checkObj =	$('#Js_queryCaseGroupList').find("input[type='checkbox']:checked");
			   if(_checkObj.length==0){
				   alert("请选择要删除的用例!");
				   return false;
			   }
			   _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   ids += $(this).val()+",";		
				   }else{
					   ids += $(this).val();		
				   }
				   num ++;
				});
			   console.log("555555")
			   
			   var a=$("#types1").val();
			   cmd=cmd+ids+"&types="+a;
			   console.log(cmd);
				 Rose.ajax.postJson(srvMap.get('deleCaseSet'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
							  self.queryConnectCaseById(collectId,a);
							}, 1000)
						}
					});
			});
		},

 		//未关联用例组显示
 		queryUnconnectCaseGroup : function(collectId){
 			var self = this;
 			$("#collectId3").val(collectId);
 			var cmd = $("#Js_queryUnconnectCaseGroupForm").serialize();
 			alert(cmd);
 			Rose.ajax.postJson(srvMap.get('queryUnconnectCaseGroup'), cmd, function(json, status) {
 				var template = Handlebars.compile(Tpl.queryUnconnectCaseGroupList);
 				console.log(json.data)
 				$("#Js_queryUnconnectCaseGroupList").html(template(json.data));
 				// 绑定单机当前行事件
				self.eventClickChecked($("#JS_connectCaseList"),function(){
			    });
 			});
 		},

 		//查询点击事件
 		connectCaseGroupBtn:function(collectId){
 			var self = this;
 			$("#connectCaseGroupBtn").unbind('click');
			$("#connectCaseGroupBtn").bind('click',function(){
				self.queryUnconnectCaseGroup(collectId);
			});
 		},

 		//关联用例组

 		relCaseGroupBtn : function(collectId){
 			var self = this;
			
			$("#relCaseGroupBtn").unbind('click');
			$("#relCaseGroupBtn").bind('click',function(){
				var a = "0";
				var cmd="";
				cmd="collectId="+collectId+"&caseIds=";
				$("#JS_connectCaseList").find("tr").each(function(){
			    	var tdArr = $(this).children();
			    	if(tdArr.eq(0).find("input").is(':checked')){
				    	cmd = cmd+tdArr.eq(0).find("input").val()+",";
				    	console.log(cmd);
					}
			 	});
			 	console.log(cmd);
			 	cmd = cmd+"&types="+"0";
			 	console.log("7777777");
			 	console.log(cmd);
				Rose.ajax.postJson(srvMap.get('relCaseBtn'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('关联成功！', 'success', 2000)
						$("#collectId1").val(collectId);
						setTimeout(function(){
							self.queryUnconnectCaseGroup(collectId);
						},1000)
					}
				});
			});
 		},


////////*******************************************/////公用//*******************************************////////
		// 获取用例集列表当前选中行
		getCaseSetRow : function(){
			var _obj = $(Dom.getCaseSetinfoListTable).find("input[type='checkbox']:checked").parents("tr");
			var _collectId = _obj.find("input[name='collectId']");
			console.log(_collectId)
			var data = {
				collectId: "",
		    }
		    if(_collectId.length==0){
		    	window.XMS.msgbox.show('请先选择一个用例集！', 'error', 2000);
		    	return;
		    }else{
		    	data.collectId= _collectId.val();
		    }
		    console.log(data.collectId)
		    return data;
		},
		// 事件：单机选中当前行
		eventClickChecked:function(obj,callback){
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
		// 事件：双击绑定事件
		eventDClickCallback:function(obj,callback){
			obj.find("tr").bind('dblclick ', function(event) {
		        	if (callback) {
						callback();
					}
		    });
		}
	};
	module.exports = init;
});
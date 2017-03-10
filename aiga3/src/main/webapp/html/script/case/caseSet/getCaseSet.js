define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 显示用例集列表
	srvMap.add("getCaseSetList", "case/caseSet/getCaseSetList.json", "sys/case/queryCase");

	// 根据Id查询用例集
	srvMap.add("getCaseById", "case/caseSet/getCaseSetList.json", "sys/case/queryCaseById");
	
	//新增用列集
	srvMap.add("addCaseSetinfo", "case/caseSet/addCaseSetinfo.json", "sys/case/addCase");

	//用例类型
	srvMap.add("getCaseTypeSelect", "case/caseSet/getCaseTypeSelect.json", "/sys/organize/constants");

	//用例类型
	srvMap.add("getRepairManSelect", "case/caseSet/getCaseTypeSelect.json", "sys/case/repairMan");

	//修改用例集
	srvMap.add("updateCaseinfo", "case/caseSet/updateCaseinfo.json", "sys/case/updateCase");

	//删除用例集
	srvMap.add("deleCaseSet", "case/caseSet/deleteCase.json", "sys/case/deleteCase");

	//关联用例集
	srvMap.add("connectCaseCollection", "case/caseSet/getCaseSetList.json", "sys/case/connectCaseCollection");


	// 模板对象
	var Tpl = {
		getCaseSetList: require('tpl/case/caseSet/getCaseSetinfoList.tpl'),
		addCaseSetinfo: require('tpl/case/caseSet/addCaseSetinfo.tpl'),
		queryCaseSetForm: require('tpl/case/caseSet/queryCaseSetForm.tpl'),
		connectCaseCollectionList: require('tpl/case/caseSet/connectCaseCollectionList.tpl'),
	// connectCaseList: require('tpl/case/caseSet/connectCaseList.tpl'),
		connectCaseList: require('tpl/case/caseSet/connectCaseList.tpl'),

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
        ///////初始化///////////
		initOrganize: function(cmd) {
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCaseSetList'), cmd, function(json, status) {
				
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
					//self.connectCase();

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
		// 获取组织列表当前选中行
//		getControlRow : function(){
//			var _obj = $(Dom.getControlinfoListTable).find("input[type='checkbox']:checked").parents("tr");
//			var _collectId = _obj.find("input[name='collectId']");
//			alert(_obj.length);
//			var data = {
//				collectId: "",
//		    }
//		    if(_collectId.length==0){
//		    	window.XMS.msgbox.show('请先选择用例集！', 'error', 2000);
//		    	return;
//		    }else{
//		    	data.collectId = _collectId.val();
//		    }
//		    return data;
//		},
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
					Rose.ajax.getJson(srvMap.get('addCaseSetinfo'), cmd, function(json, status) {
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
			Rose.ajax.getJson(srvMap.get('getCaseTypeSelect'), cmd, function(json, status) {
				if (status) {
					Dom.caseType = json.data;
					console.log(Dom.caseType);
					self.getCaseSetList();
				}
			});
		},

		//获取维护人
		getrepairsIdSelect:function(){
			Rose.ajax.getJson(srvMap.get('getRepairManSelect'), 'repairMan', function(json, status) {
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
			Rose.ajax.getJson(srvMap.get('getCaseById'), cmd, function(json, status) {
				alert(JSON.stringify(json.data));
				alert(status);
				if (status) {
					var _form = $(Dom.addCaseSetinfoForm);
					var template = Handlebars.compile(Tpl.addCaseSetinfo);
					var a = json.data.content[0]["caseType"];
					var b = json.data.content[0]["repairsId"];
					console.log(json.data.content[0]);
					var c = json.data.content[0];


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
					Rose.ajax.getJson(srvMap.get('addCaseSetinfo'), cmd, function(json, status) {
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
				 Rose.ajax.getJson(srvMap.get('deleCaseSet'), 'collectId=' + collectIds, function(json, status) {
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
			Rose.ajax.getJson(srvMap.get('getCaseSetList'), '', function(json, status) {
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
				    	
				    	Rose.ajax.getJson(srvMap.get('connectCaseCollection'), cmd, function(json, status) {
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


//		connectCaseList : function(){
//			var self = this;
//			var _data = self.getCaseSetRow();
//			var _collectId = _data.collectId;
			// var cmd = "collectId="+_collectId+"&collectIds=";
			// Rose.ajax.getJson(srvMap.get('getCaseSetList'), '', function(json, status) {
			// 	if (status) {
					// var _form = $(Dom.addCaseSetinfoForm);
//					var template = Handlebars.compile(Tpl.connectCaseList);
//					// console.log(json.data);
//					$("#JS_connectCaseSetinfo").html(template({}));
//
//					// //弹出层
//					$("#JS_connectCaseSetinfoModal").modal('show');
//		connectCaseList : function(){
//			var self = this;
//			var _data = self.getCaseSetRow();
//			var _collectId = _data.collectId;
//			var cmd = "collectId="+_collectId;
//			Rose.ajax.getJson(srvMap.get('getCaseSetList'), cmd, function(json, status) {
//				if (status) {
//					var _form = $("#JS_connectCaseSetForm");
//					var template = Handlebars.compile(Tpl.connectCaseList);
//					console.log(json.data[0]);
//					_form.html(template(json.data[0]));
//
//					// //弹出层
//					$("#JS_connectCaseSetinfoModal").modal('show');


					// 绑定单机当前行事件
				    // self.eventClickChecked($("#JS_connectCaseCollectionList"),function(){

				    // })
				// }
//				// });
//		},
//		connectCase : function(){
//			var self = this;
//			$("#JS_connectCase").bind('click',function(){
//				var _data = self.getCaseSetRow();
//				if(_data){
//					self.connectCaseList();
//				}
//			});
//		},

//				}
//				});
//		},
		connectCase : function(){
			var self = this;
			$("#JS_connectCase").bind('click',function(){
				// var _checkObj =	$('#JS_getCaseSetinfoListTable').find("input[type='checkbox']:checked");
				var _data = self.getCaseSetRow();
				if(_data){
					self.connectCaseList();
				}
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
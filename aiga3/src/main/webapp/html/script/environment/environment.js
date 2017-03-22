define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');

	//显示环境列表
	srvMap.add("getEnvironmentList","environment/getEnvironmentList.json","");
	//根据Id查询环境
	srvMap.add("getEnvironmentInfo","environment/getEnvironmentInfo.json","");
	//增加环境
	srvMap.add("addEnvironmentInfo","environment/addEnvironmentInfo.json","");
	//删除环境
	srvMap.add("deleteEnvironment","environment/deleteEnvironment.json","");
	//修改环境
	srvMap.add("updateEnvironmentInfo","environment/updateEnvironmentInfo.json","");

	//模板对象
	var Tpl={
		queryEnvironmentForm:require('tpl/environment/queryEnvironmentForm.tpl'),
		getEnvironmentList:require('tpl/environment/getEnvironmentList.tpl'),
		addEnvironmentInfo: require('tpl/environment/addEnvironmentInfo.tpl')
	};

	var Dom={
		queryEnvironmentForm:'#JS_queryEnvironmentForm',
		getEnvironmentList:'#JS_getEnvironmentList',
		addEnviromentInfoForm:"#JS_addEnvironmentInfoForm",
		addEnvironmentInfoModal:"#JS_addEnvironmentInfoModal",
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
    		this.getEnvironmentList();
    		this.queryEnvironment();
    		this.addEnvironmentInfo();
    	},
    	initForm:function(){
	    		var self=this;
	    		/*Rose.ajax.postJson(srvMap.get('getEnvironmentList'),cmd,function(json,status){
	    			if(status){
	    				var template=Handlerbars.compile(Tpl.queryEnvironmentForm);
	    			}
	    		});*/
	    		var template=Handlebars.compile(Tpl.queryEnvironmentForm);
	    		$(Dom.queryEnvironmentForm).html(template());
				//修改按钮
				self.updateEnvironmentInfo();
				//删除按钮
				self.deleteEnvironment();
    	},
		getEnvironmentList: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getEnvironmentList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getEnvironmentList);
					console.log(json.data)
					$(Dom.getEnvironmentList).html(template(json.data.content));
					/*self.deleCaseTemp();
					self.eventClickChecked($(Dom.getCaseTempList), function() {
					})*/
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
					self.getEnvironmentList(cmd);
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
				/*$("#caseType").val("");
				$("#repairsId").val("");
				$("#caseNum").val("");*/
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
									self.getEnvironmentList();
								},1000)
						}
					});
				});
			});
		},
		//删除
		deleteEnvironment : function(){
			var self = this;
			var  sysId="";
			var num =0 ;
			$("#JS_deleteEnvironment").unbind('click');
			$("#JS_deleteEnvironment").bind('click', function() {
				var  sysId="";
				var num =0 ;
			   var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
			   if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要删除的环境！', 'error', 2000);
				   return false;
			   }
			   _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   sysId += $(this).val()+",";
				   }else{
					   sysId += $(this).val();
				   }
				   num ++;
				});
				Rose.ajax.postJson(srvMap.get('deleteEnvironment'), 'sysId=' + sysId, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
							  self.getEnvironmentList();
							}, 1000)
						}
				});
			});
		},
		//修改
		updateEnvironmentInfo:function(){
			var self = this;
			$("#JS_updateEnvironmentInfo").unbind('click');
			$("#JS_updateEnvironmentInfo").bind('click',function(){
				var _checkObj =	$('#JS_getEnvironmentList').find("input[type='radio']:checked");
		   		if(_checkObj.length==0){
			   	    window.XMS.msgbox.show('请选择要修改的环境！', 'error', 2000);
			        return false;
			    }
				var _sysId ="";
				_checkObj.each(function (){
					_sysId = 	$(this).val();
				})
				var cmd = "sysId=" +_sysId;
				Rose.ajax.postJson(srvMap.get('updateEnvironmentInfo'), cmd, function(json, status) {
					if (status) {
						var _form = $(Dom.addEnviromentInfoForm);
						var template = Handlebars.compile(Tpl.addEnvironmentInfo);
						/*var a = JSON.stringify(json.data.sysId);
						var b = JSON.stringify(json.data.repairsId);*/
						var c = json.data;
						/*c["caseType"]=Dom.caseType;
						c["repairsId"]=Dom.repairsId;*/
						_form.html(template(c));
						/*$("#caseType").val(a);
						$("#repairsId").val(b);*/
						// //弹出层
						$(Dom.addEnvironmentInfoModal).modal('show');
						$("#formName").html("修改环境");
						$("#JS_addEnvironmentInfoSubmit").unbind('click');
						//点击保存
						$("#JS_addEnvironmentInfoSubmit").bind('click',function(){
						var cmd = _form.serialize();
						console.log(cmd);
						Rose.ajax.postJson(srvMap.get('updateEnvironmentInfo'), cmd, function(json, status) {
							if(status) {
									// 添加用户成功后，刷新用户列表页
									XMS.msgbox.show('修改成功！', 'success', 2000)
									// 关闭弹出层
									$(Dom.addEnvironmentInfoModal).modal('hide');
									setTimeout(function(){
										self.getEnvironmentList();
									},1000)
							}
									});
						});
					}
				});
			});
		},
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
		}
    };

	module.exports=environment;
});
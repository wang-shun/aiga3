define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	var Utils = require('global/utils.js')

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
							  self.getEnvironmentList();
							}, 1000)
						}
				});
			});
		},
		//关联机器
		connectMachine : function(){
			var self = this;
			$("#JS_connectMachine").unbind('click');
			$("#JS_connectMachine").bind('click',function(){
				var _data = self.getCaseSetRow();
				if(_data){
					self.connectCaseList();
				}
			});
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
					var c = json.data;
					_form.html(template(c));
					// 设置下拉框选中值
					Utils.setSelected(_form);
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
									self.getEnvironmentList();
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

	module.exports=environment;
});
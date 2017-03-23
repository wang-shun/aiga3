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

	//模板对象
	var Tpl={
		queryMachineForm:require('tpl/machine/queryMachineForm.tpl'),
		getMachineList:require('tpl/machine/getMachineList.tpl'),
		addMachineInfo: require('tpl/machine/addMachineInfo.tpl')
	};

	var Dom={
		queryMachineForm:'#JS_queryMachineForm',
		getMachineList:'#JS_getMachineList',
		addMachineInfoForm:"#JS_addMachinenfoForm",
		addMachineInfoModal:"#JS_addMachineInfoModal",
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
					var c = json.data;
					_form.html(template(c));
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
define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');

	var pathAlias = "case/task/";
	// 显示用例集列表
	srvMap.add("getTaskList", pathAlias+"getTaskList.json", "auto/task/listInfo");
	// 删除
	srvMap.add("parTask", pathAlias+"parTask.json", "/auto/task/delete");
	//重跑
	srvMap.add("taskRestart", pathAlias+"parTask.json", "/auto/task/restart");
	//终止
	srvMap.add("taskTermination", pathAlias+"parTask.json", "/auto/task/stop");	

	// 模板对象
	var Tpl = {
		getTaskList: require('tpl/case/task/getTaskList.tpl'),
		impResult: require('tpl/case/task/impResult.tpl'),
		impType: require('tpl/case/task/impType.tpl'),
	};


	var Dom = {
		getTaskList:"#JS_getTaskList",
		queryTask:"#JS_queryTask",
		parTask:"#JS_parTask",
		taskRestart:"#JS_taskRestart",
		taskTermination:"#JS_taskTermination",
	}
	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			this.initTask();
			this.getTaskList();
		},

	//---------------------------------------------------------------------------------//
		//执行类型
		impType:function(){
			var template = Handlebars.compile(Tpl.impType);
			$("#JS_impType").html(template());
		},
		impResult:function(){
			var template = Handlebars.compile(Tpl.impResult);
			$("#JS_impResult").html(template());
		},
        ///////初始化///////////
		initTask: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getTaskList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getTaskList);
					console.log(json.data);
					alert(template(json.data));
					$(Dom.getTaskList).html(template(json.data));
					self.impType();
					self.impResult();
					self.parTask();
					self.taskRestart();
					self.taskTermination();
					// 绑定单机当前行事件
				    self.eventClickChecked($(Dom.getTaskList),function(){

				    });
				    // 绑定双击当前行事件
				    self.eventDClickCallback($(Dom.getTaskList),function(){
				    	// 请求：用户基本信息
						//self.seeCase();
				    })
				    
				}
			});
		},
		getTaskList:function(){
			var self=this;
			$(Dom.queryTask).unbind('click');
			$(Dom.queryTask).bind('click',function(){
				var cmd = $("#JS_queryTaskForm").serialize();
				self.initTask(cmd);
			});
		},
		//删除任务
		parTask:function(){
			var self=this;
			$(Dom.parTask).unbind('click');
			$(Dom.parTask).bind('click',function(){
				var _data = self.getTaskRow();
				if(_data){
					var cmd = "taskId="+_data.taskId;
					Rose.ajax.postJson(srvMap.get('parTask'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function(){
								self.initTask();
							},1000)
						}
					});
				}
				
			});
		},
		//执行重跑
		taskRestart:function(){
			var self=this;
			$(Dom.taskRestart).unbind('click');
			$(Dom.taskRestart).bind('click',function(){
				var _data = self.getTaskRow();
				if(_data){
					var cmd = "taskId="+_data.taskId;
					Rose.ajax.postJson(srvMap.get('taskRestart'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('重跑中！', 'success', 2000)
							setTimeout(function(){
								self.initTask();
							},1000)
						}
					});
				}
				
			});
		},
		//执行终止
		taskTermination:function(){
			var self=this;
			$(Dom.taskTermination).unbind('click');
			$(Dom.taskTermination).bind('click',function(){
				var _data = self.getTaskRow();
				if(_data){
					var cmd = "taskId="+_data.taskId;
					Rose.ajax.postJson(srvMap.get('taskTermination'), cmd, function(json, status) {
						if (status) {
							XMS.msgbox.show('执行终止！', 'success', 2000)
							setTimeout(function(){
								self.initTask();
							},1000)
						}
					});
				}
				
			});
		},

////////*******************************************/////公用//*******************************************////////
		// 获取用例集列表当前选中行
		getTaskRow : function(){
			var _obj = $(Dom.getTaskList).find("input[type='radio']:checked").parents("tr");
			var _taskId = _obj.find("input[name='taskId']");
			console.log(_taskId)
			var data = {
				taskId: "",
		    }
		    if(_taskId.length==0){
		    	window.XMS.msgbox.show('请先选择一个任务！', 'error', 2000);
		    	return;
		    }else{
		    	data.taskId= _taskId.val();
		    }
		    console.log(data.taskId)
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
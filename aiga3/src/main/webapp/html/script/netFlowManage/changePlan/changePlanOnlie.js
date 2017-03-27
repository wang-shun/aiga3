define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');

	var pathAlias = "case/changePlanManage/";
	// 显示用例集列表
	srvMap.add("getChangePlanOnlieList", pathAlias+"getChangePlanOnlieList.json", "auto/task/listInfo");
	

	// 模板对象
	var Tpl = {
		getChangePlanOnlieList: require('tpl/case/changePlanManage/getChangePlanOnlieList.tpl'),
	};


	var Dom = {
		planName:[],
		getChangePlanOnlieList:"#JS_getChangePlanOnlieList",
	}
	var getChangePlanOnlie = {
		init: function() {
			this._render();
		},
		_render: function() {
			this.initChangePlanOnlie();
			// this.getTaskList();
		},

	//---------------------------------------------------------------------------------//
        ///////初始化///////////
		initChangePlanOnlie: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getChangePlanOnlieList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getChangePlanOnlieList);
					Dom.planName=json.data.onlinePlanName;
					console.log(json.data)
					$(Dom.getChangePlanOnlieList).html(template(json.data));
					

					// 绑定单机当前行事件
				    self.eventClickChecked($(Dom.getChangePlanOnlieList),function(){

				    });
				    // 绑定双击当前行事件
				    self.eventDClickCallback($(Dom.getChangePlanOnlieList),function(){
				    	// 请求：用户基本信息
						//self.seeCase();
				    })
				    self.initPaging($(Dom.getChangePlanOnlieList),8)
				    
				}
			});
		},
		//重置
		JS_resetTask:function(){
			$("#JS_resetTask").unbind('click');
			$("#JS_resetTask").bind('click',function(){
				$("#JS_queryTaskForm").data('bootstrapValidator').resetForm(true);
			});
		},
		//查找
		getTaskList:function(){
			var self=this;
			$(Dom.queryTask).unbind('click');
			$(Dom.queryTask).bind('click',function(){
				var cmd = $("#JS_queryTaskForm").serialize();
				self.initTask(cmd);
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
                "autoWidth": false,
                "scrollX": true,
                "scrollY": false
            });
        }
	};
	module.exports = getChangePlanOnlie;
});
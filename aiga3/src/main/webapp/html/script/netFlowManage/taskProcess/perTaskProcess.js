define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "netFlowManage/taskProcess/perTaskProcess/";

	var Utils = require("global/utils.js");

	// 功能验收子任务列表显示
	srvMap.add("perTaskList", "netFlowManage/taskProcess/funTaskProcess/funTaskList.json", "accept/subTask/list");
	//关联接口列表显示
	srvMap.add("interfaceList", pathAlias + "interfaceList.json", "sys/subTaskPlanExp/getNaPlanCaseResultExtSum");
	//同步测试结果
	srvMap.add("synTestResults", pathAlias + "synTestResults.json", "sys/subTaskPlanExp/copyDataFromCSHP03");
	//测试结果列表显示
	srvMap.add("getTestResults", pathAlias + "getTestResults.json", "sys/subTaskPlanExp/getNaPlanCaseResultExt");
	//
	srvMap.add("delTR", pathAlias + "synTestResults.json", "sys/subTaskPlanExp/deleteNaPlanCaseResultExt");
	//保存备注
	srvMap.add("savTR", pathAlias + "synTestResults.json", "sys/subTaskPlanExp/saveRemark");
	//保存测试人员
	srvMap.add("savInMan", pathAlias + "synTestResults.json", "sys/subTaskPlanExp/saveOperatId");
	//处理人
	srvMap.add("getDealOpIdList", "netFlowManage/netFlow/onlineTaskDistribute/getDealOpIdList.json", "accept/onlineTask/dealOp");

	// 模板对象
	var Tpl = {
		perTaskList: $("#TPL_perTaskList").html(), //计划列表
		interfaceList: $("#TPL_interfaceList").html(),
		perTaskProcessList: $("#TPL_perTaskProcessList").html(),

	};

	// 容器对象
	var Dom = {
		perTaskList: '#Js_perTaskList',
		interfaceList: '#Js_interfaceList', 
		queryPerTaskForm:'#Js_queryPerTaskForm',
		synTestResults:"#JS_synTestResults",//同步测试结果
		queTestResults:"#Js_queTestResults",//查看测试结果
		modalTestResults:"#modal_getTestResults",
		perTaskProcessList:"#Js_perTaskProcessList",
		delTR:"#JS_delTR",
		savTR:"#Js_savTR",
		savInMan:"#Js_savInMan",

	};
	var busiData;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {

			this.hdbarHelp();
			this.getPerTaskList("");
			
		},

		hdbarHelp: function() {
			Handlebars.registerHelper("transformatTaskType", function(value) {
				if (value == 2) {
					return "自动化用例";
				} else if (value == 1) {
					return "手工用例";
				} else {
					return "未定义";
				}
			});
			Handlebars.registerHelper("transformatState", function(value) {
				if (value == 0) {
					return "未处理";
				} else if (value == 1) {
					return "处理中";
				} else if (value == 2) {
					return "处理完成";
				} else {
					return "未定义";
				}
			});
			Handlebars.registerHelper("transformatCaseState", function(value) {
				if (value == 0) {
					return "未处理";
				} else if (value == 1) {
					return "处理完成";
				}  else {
					return "未定义";
				}
			});
		},
		//显示任务列表
		getPerTaskList: function(cmd) {
			var self = this;
			if (cmd=="") {
				var cm = "taskType=2";
			}else{

			}
			var cm = "taskType=2&"+cmd;
			Rose.ajax.postJson(srvMap.get('perTaskList'), cm, function(json, status) {
				if (status) {

					var template = Handlebars.compile(Tpl.perTaskList);
					console.log(json.data)
					$(Dom.perTaskList).html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.perTaskList));
					//同步测试结果
					self.synTestResults();
					//queTestResults查看测试结果
					self.queTestResults();
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
					// 绑定双击当前行事件
                    self.eventDClickCallback($(Dom.perTaskList),function(){
                    	var data = self.getSelectedInfo();
                        self.getInterfaceList(data.taskId);
                    })

					self.initPaging($(Dom.perTaskList), 5, true);
				}
			});
		},
		queryPerTaskList:function(){
			var self = this;
			var _form = $(Dom.queryPerTaskForm);
			// 表单提交
			_form.find('button[name="query"]').bind('click', function() {

					var cmd = _form.serialize();
					self.getPerTaskList(cmd);
				})
				// 表单重置
			_form.find('button[name="reset"]').bind('click', function() {

			});
		},
		//同步测试结果
		synTestResults:function(){
			var self = this;
			$(Dom.synTestResults).bind('click', function() {
				Rose.ajax.postJson(srvMap.get('synTestResults'), cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('同步成功', 'success', 2000);
						setTimeout(function() {
							self.getPerTaskList("");
						}, 1000)
					}
				});
			})
		},
		//查看测试结果
		queTestResults : function(){
			var self = this;
			var btn = $(Dom.queTestResults);
			btn.unbind('click');
			btn.bind('click', function() {
				var data = self.getSelectedInfo();
				if (data) {
					var _modal = $(Dom.modalTestResults);
					_modal.modal('show').on('shown.bs.modal', function() {
						var cmd = data.taskId;

						self.getTestResults(cmd);

					});
				}
			});
		},
		//显示测试数据
		getTestResults: function(cmd) {
			var self = this;
			var _table = $(Dom.perTaskProcessList);
			var cm = "taskId=" + cmd;
			Rose.ajax.postJson(srvMap.get('getTestResults'), cm, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.perTaskProcessList);
					console.log(json.data)
					_table.html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.perTaskProcessList));
					self.initPaging($(Dom.perTaskProcessList), 5, true);
					//删除
					self.delTR(cmd);
					//保存
					self.savTR(cmd)
				}
			});
		},
		//测试数据删除
		delTR : function(cmd){
			var self = this;
			var _delete = $(Dom.delTR)
			_delete.unbind('click');
			_delete.bind('click', function() {
				var resultId="";
				var num =0 ;
			    var _checkObj =	$(Dom.perTaskProcessList).find("input[type='checkbox']:checked");
			    if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要删除的测试结果！', 'error', 2000);
				   return false;
			    }
			    _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   resultId += $(this).val()+",";		
				   }else{
					   resultId += $(this).val();		
				   }
				   num ++;
				});
				console.log(resultId);
				Rose.ajax.postJson(srvMap.get('delTR'), "taskId=" + cmd+"&resultIds="+resultId, function(json, status) {
					if (status) {
						XMS.msgbox.show('删除成功！', 'success', 2000)
						setTimeout(function() {
							self.getTestResults(cmd);
						}, 1000)
					}
				});
			});
		},
		//保存测试数据备注
		savTR : function(cm){
			var self = this;
            var _savTR = $(Dom.savTR)
            _savTR.unbind('click');
            _savTR.bind('click',function(){

                var id;
                var remark;
                var list = [];
                var cmd={
                    "taskId":cm,
                    "list" :[]
                };
                var _checkObj =	$(Dom.perTaskProcessList).find("input[type='checkbox']:checked");
			    if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要保存的测试结果！', 'error', 2000);
				   return false;
			    }
                $(Dom.perTaskProcessList).find("tbody").find("tr").each(function(){
                    var tdArr = $(this).children();
                    if(tdArr.eq(0).find("input").is(':checked')){
                        id = tdArr.eq(0).find("input").val();
                        remark = tdArr.eq(1).find("input").val();
                        cmd.list.push({
                            "id" : id,
                            "remark" : remark
                        });
                    }
                });
                // cmd.list.push(list);
                Rose.ajax.postJson(srvMap.get('savTR'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('保存成功！', 'success', 2000);
                        setTimeout(function() {
                            self.getTestResults(cm);
                        }, 1000)
                    }
                });

            });
		},
		//保存处理人
		savInMan : function(cm){
			alert("1")
			var self = this;
            var _savInMan = $(Dom.savInMan)
            _savInMan.unbind('click');
            _savInMan.bind('click',function(){
                var id;
                var remark;
                var list = [];
                var cmd={
                    "taskId":cm,
                    "list" :[]
                };
                var _checkObj =	$(Dom.interfaceList).find("input[type='checkbox']:checked");
			    if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要保存的关联结果！', 'error', 2000);
				   return false;
			    }
                $(Dom.interfaceList).find("tbody").find("tr").each(function(){
                    var tdArr = $(this).children();
                    if(tdArr.eq(0).find("input").is(':checked')){
                        id = tdArr.eq(0).find("input").val();
                        remark = tdArr.eq(4).find("select").val();
                        cmd.list.push({
                            "id" : id,
                            "operatId" : remark
                        });
                    }
                });
                // cmd.list.push(list);
                Rose.ajax.postJson(srvMap.get('savInMan'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('保存成功！', 'success', 2000);
                        setTimeout(function() {
                            self.getTestResults(cm);
                        }, 1000)
                    }
                });

            });
		},
		//显示接口列表
		getInterfaceList:function(cmd) {
			var self = this;
			var cm = "taskId="+cmd
			Rose.ajax.postJson(srvMap.get('interfaceList'), cm, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.interfaceList);
					console.log(json.data)
					$(Dom.interfaceList).html(template(json.data.content));
					Utils.eventTrClickCallback($(Dom.interfaceList));
					// Utils.setScroll($(Dom.getAutoPlanList),380px);
					Utils.setSelectData($(Dom.interfaceList));
					self.savInMan(cmd);
					self.initPaging($(Dom.interfaceList), 5, true);
				}
			});
		},

		//获取选中当前行数据
		getSelectedInfo: function() {
			var obj = this.getCheckedRow(Dom.perTaskList);
			var data = {
				taskId: ""
			};
			if (obj.length == 0) {
				window.XMS.msgbox.show('请先选择一个任务！', 'info', 2000);
				return;
			} else {
				data.taskId = obj.find("[name='taskId']").val();
			}
			return data;
		},
		// 获取复选列表当前选中行
		getCheckedRow: function(obj) {
			var _obj = $(obj).find("input[type='radio']:checked").parents("tr");
			return _obj;
		},
		// 事件：双击绑定事件
        eventDClickCallback:function(obj,callback){
            obj.find("tr").bind('dblclick ', function(event) {
                    if (callback) {
                        callback();
                    }
            });
        },
		initPaging: function(obj, length, scrollX) {
			obj.find("table").DataTable({
				"iDisplayLength": length,
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"autoWidth": false,
				"info": true,
				"language": {
					"emptyTable": "暂无数据...",
					"infoEmpty": "第0-0条，共0条"
				},
				"scrollX": scrollX
			});
		},

	};
	module.exports = Init;
});
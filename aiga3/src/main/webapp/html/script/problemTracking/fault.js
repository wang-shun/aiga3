define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');

	var pathAlias = "problemTracking/fault/";
	// 显示故障异常
	srvMap.add("getFaultList", pathAlias + "getFaultList.json", "accept/onlinePlanBug/list");
	srvMap.add("saveFaultList", pathAlias + "saveFaultList.json", "accept/onlinePlanBug/save");
	srvMap.add("deleteFaultList", pathAlias + "saveFaultList.json", "accept/onlinePlanBug/delete");
	
	// 显示变更计划名称
	srvMap.add("queryOnlinePlanName","netFlowManage/changePlan/changePlanManage/getChangePlanOnlieList.json", "sys/cache/changePlan");


	// 模板对象
	var Tpl = {
		getFaultList: require('tpl/problemTracking/fault/getFaultList.tpl'),
		addFaultForm: require('tpl/problemTracking/fault/addFaultForm.tpl'),
		queryOnlinePlanName: require('tpl/netFlowManage/changePlan/changePlanManage/queryOnlinePlanName.tpl'),
	};


	var Dom = {
		getFaultList: "#JS_getFaultList",
		queryFaultForm:"#JS_queryFaultForm",
		addFaultFormModal:"#JS_addFaultFormModal",
	}
	var getFault = {
		init: function() {
			this._render();

		},
		_render: function() {
			this.hdbarHelp();
			this.getFaultList();
			this.queryFaultForm();
			this.queryOnlinePlanName();
		},
		hdbarHelp: function() {
			Handlebars.registerHelper("bugLevels", function(value) {
				if (value == 1) {
					return "红色";
				} else if (value == 2) {
					return "橙色";
				} else if (value == 3) {
					return "黄色";
				} else if (value == 4) {
					return "蓝色";
				} else if (value == 4) {
					return "灰色";
				}
			});
			Handlebars.registerHelper("td_types", function(value) {
				if (value == 1) {
					return "故障";
				} else if (value == 2) {
					return "异常";
				}
			});
			Handlebars.registerHelper("resoves", function(value) {
				if (value == 1) {
					return "是";
				} else if (value == 2) {
					return "否";
				}
			});
		},
		//---------------------------------------------------------------------------------//
		queryOnlinePlanName: function() {
			var self = this;
			var _onlinePlan=$("#JS_queryFaultForm").find("[name='onlinePlans']");
			Rose.ajax.postJson(srvMap.get('queryOnlinePlanName'), '', function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.queryOnlinePlanName);
					console.log(json.data)
					console.log("1111111111111")
					_onlinePlan.html(template(json.data));
				}
			});
		},
		///////初始化///////////
		getFaultList: function(cmd) {
			var self = this;
			var _getFaultList = $(Dom.getFaultList).find("[name='getFaultList']");
			Rose.ajax.postJson(srvMap.get('getFaultList'), cmd, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getFaultList);
					console.log(json.data.content)
					_getFaultList.html(template(json.data.content));
					self.addFault();
					self.updateFault();
					self.deleteFault();
					//新增
					// self.addBut();
					// //修改
					// self.modifyBut();
					// //查看交付物
					// self.queryDelBut();
					// //添加上线总结
			  //       self.addSummary();

					// 绑定单机当前行事件
					self.eventClickChecked($(Dom.getFaultList), function() {

					});
					// 绑定双击当前行事件
					self.eventDClickCallback($(Dom.getFaultList), function() {
						// 请求：用户基本信息
						//self.seeCase();
					})
					self.initPaging($(Dom.getFaultList), 10)

				}
			});
		},
		//查询按钮
		queryFaultForm:function(){
			var self = this;
			var _form = $(Dom.queryFaultForm);
			var _query = _form.find("[name='query']");
			_query.unbind('click');
			_query.bind('click', function() {
				var cmd = _form.serialize();
				self.getFaultList(cmd);
			});
		},
		//新增
		addFault : function(){
			var self = this;
			var _save = $(Dom.getFaultList).find("[name='save']");

			_save.unbind('click');
			_save.bind('click', function() {
				var template = Handlebars.compile(Tpl.addFaultForm);
				$("#JS_addFaultForm").html(template({}));
				Rose.ajax.postJson(srvMap.get('queryOnlinePlanName'), '', function(json, status) {
					if (status) {
						var template = Handlebars.compile(Tpl.queryOnlinePlanName);
						console.log(json.data)
						console.log("1111111111111")
						$("#JS_addFaultForm").find("[name='onlinePlans']").html(template(json.data));
					}
				});
				//// 弹出层
				$(Dom.addFaultFormModal).modal('show');

				self.saveFault("");

			});
		},
		//保存按钮
		saveFault : function(cmd){
			var self = this;
			var _save = $("#JS_addFaultForm").find("[name='save']"); 
			var _form = $("#JS_addFaultForm").find("[name='addFaultForm']");
			_save.unbind('click');
			_save.bind('click', function() {
				cmd = cmd + _form.serialize();
				Rose.ajax.postJson(srvMap.get('saveFaultList'), cmd, function(json, status) {
					if (status) {
						XMS.msgbox.show('保存成功！', 'success', 2000)
						$(Dom.addFaultFormModal).modal('hide');
						setTimeout(function() {
							self.getFaultList();
						}, 1000)
					}
				});
			});
		},

		//修改按钮
		updateFault : function(){
			var self = this;
			var _save = $(Dom.getFaultList).find("[name='edit']");
			var _form = $("#JS_addFaultForm").find("[name='addFaultForm']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _data = self.getTaskRow();
				var _id = _data.id;
				if (_data) {
					Rose.ajax.postJson(srvMap.get('getFaultList'), "id=" + _id, function(json, status) {
						if (status) {
							var template = Handlebars.compile(Tpl.addFaultForm);
							_form.html(template(json.data.content[0]));
							//// 弹出层
							$(Dom.addFaultFormModal).modal('show');
							var cmd="id=" + _id+"&"
							self.saveFault(cmd);
						}
					});
				}

			});
		},
		//删除
		deleteFault : function(){
			var self = this;
			var _delete = $(Dom.getFaultList).find("[name='delete']");
			_delete.unbind('click');
			_delete.bind('click', function() {
				var id="";
				var num =0 ;
			    var _checkObj =	$(Dom.getFaultList).find("input[type='checkbox']:checked");
			    if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要删除的故障&异常！', 'error', 2000);
				   return false;
			    }
			    _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   id += $(this).val()+",";		
				   }else{
					   id += $(this).val();		
				   }
				   num ++;
				});
				console.log(id);
				Rose.ajax.postJson(srvMap.get('deleteFaultList'), "id="+id, function(json, status) {
					if (status) {
						XMS.msgbox.show('删除成功！', 'success', 2000)
						setTimeout(function() {
							self.getFaultList();
						}, 1000)
					}
				});
			});
		},
////////*******************************************/////公用//*******************************************////////
		// 获取用例集列表当前选中行
		getTaskRow: function() {
			var _obj = $(Dom.getFaultList).find("input[type='checkbox']:checked").parents("tr");
			var _id = _obj.find("input[name='id']");
			console.log(_id)
			var data = {
				id: "",
			}
			if (_id.length == 0) {
				window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
				return;
			} else {
				data.id = _id.val();
			}
			console.log(data.id)
			return data;
		},
		// 事件：单机选中当前行
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
		// 事件：双击绑定事件
		eventDClickCallback: function(obj, callback) {
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
	module.exports = getFault;
});
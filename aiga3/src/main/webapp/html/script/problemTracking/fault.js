define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 通用工具模块
    var Utils = require("global/utils.js");
	 // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('fault');

	var pathAlias = "problemTracking/fault/";
	// 显示故障异常
	srvMap.add("getFaultList", pathAlias + "getFaultList.json", "accept/onlinePlanBug/list");
	srvMap.add("saveFaultList", pathAlias + "saveFaultList.json", "accept/onlinePlanBug/save");
	srvMap.add("deleteFaultList", pathAlias + "saveFaultList.json", "accept/onlinePlanBug/delete");
	srvMap.add("findOne", pathAlias + "saveFaultList.json", "accept/onlinePlanBug/findOne");
	
	// 显示变更计划名称
	srvMap.add("queryOnlinePlanName","netFlowManage/changePlan/changePlanManage/getChangePlanOnlieList.json", "sys/cache/changePlan");


	// 模板对象
	var Tpl = {
		// getFaultList: require('tpl/problemTracking/fault/getFaultList.tpl'),
		// addFaultForm: require('tpl/problemTracking/fault/addFaultForm.tpl'),
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
			this.bugLevel($(Dom.queryFaultForm));
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
				} else if (value == 5) {
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
					_onlinePlan.html(template(json.data));
				}
			});
		},
		bugLevel: function(bug){
			var _bugType=bug.find("[name='bugType']")
			_bugType.change(function() {
				var i=_bugType.val()
				if(i=="1"){
					bug.find("[name='bugLevel']").removeAttr("disabled");
				}else if(i=="2"){
					bug.find("[name='bugLevel']").val("");
					bug.find("[name='bugLevel']").attr("disabled","disabled");
				}
			});
		},
		///////初始化///////////
		getFaultList: function(cmd) {
			var self = this;
			var _dom = Page.findId('getFaultList');
            var _domPagination = _dom.find("[name='pagination']");
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getFaultList'), cmd, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
                var template = Handlebars.compile(Page.findTpl('getFautList'));

                _dom.find("[name='content']").html(template(json.data.content));
                self.addFault();
				self.updateFault();
				self.deleteFault();

                Utils.eventTrClickCallback(_dom);
            // 绑定双击当前行事件
			// 	self.eventDClickCallback($(Dom.getFaultList), function() {
			// 		// 请求：用户基本信息
			// 		//self.seeCase();
			// 	})

            }, _domPagination);

		},
		//查询按钮
		queryFaultForm:function(){
			var self = this;
			var _form = Page.findId('queryFaultForm');
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
			var _save = Page.findId('getFaultList').find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var template = Handlebars.compile(Page.findTpl('addFaultForm'));
				$("#JS_addFaultForm").html(template({}));
				self.bugLevel($("#JS_addFaultForm"));
				Rose.ajax.postJson(srvMap.get('queryOnlinePlanName'), '', function(json, status) {
					if (status) {
						var template = Handlebars.compile(Tpl.queryOnlinePlanName);
						console.log(json.data)
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
			var _form = $(Dom.addFaultFormModal).find("[name='addFaultForm']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _data = self.getTaskRow();
				var _bugId = _data.bugId;
				if (_data) {
					Rose.ajax.postJson(srvMap.get('findOne'), "bugId=" + _bugId, function(json, status) {
						if (status) {
							var template = Handlebars.compile(Page.findTpl('addFaultForm'));
							console.log(json.data)
							$("#JS_addFaultForm").html(template(json.data));
							var onlinePlans=json.data.onlinePlans;
							$("#JS_addFaultForm").find("[name='bugType']").val(json.data.bugType);
							$("#JS_addFaultForm").find("[name='resove']").val(json.data.resove);
							$("#JS_addFaultForm").find("[name='bugLevel']").val(json.data.bugLevel);
							var _bugType=$("#JS_addFaultForm").find("[name='bugType']")
							if(_bugType.val()=="2"){
								$("#JS_addFaultForm").find("[name='bugLevel']").attr("disabled","disabled");
							}else if (_bugType.val()=="1") {
								$("#JS_addFaultForm").find("[name='bugLevel']").removeAttr("disabled");
							}
							_bugType.change(function() {
								var i=_bugType.val()
								if(i=="1"){
									$("#JS_addFaultForm").find("[name='bugLevel']").removeAttr("disabled");
									$("#JS_addFaultForm").find("[name='bugLevel']").val(json.data.bugLevel);
								}else if(i=="2"){
									$("#JS_addFaultForm").find("[name='bugLevel']").val("");
									$("#JS_addFaultForm").find("[name='bugLevel']").attr("disabled","disabled");
								}
							});
							Rose.ajax.postJson(srvMap.get('queryOnlinePlanName'), '', function(json, status) {
								if (status) {
									var template = Handlebars.compile(Tpl.queryOnlinePlanName);
									console.log(json.data)
									$("#JS_addFaultForm").find("[name='onlinePlans']").html(template(json.data));
									$("#JS_addFaultForm").find("[name='onlinePlans']").val(onlinePlans);
									//// 弹出层
									$(Dom.addFaultFormModal).modal('show');
									
									var cmd="bugId=" + _bugId+"&"
									self.saveFault(cmd);
								}
							});
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
				var bugId="";
				var num =0 ;
			    var _checkObj =	$(Dom.getFaultList).find("input[type='checkbox']:checked");
			    if(_checkObj.length==0){
				   window.XMS.msgbox.show('请选择要删除的故障&异常！', 'error', 2000);
				   return false;
			    }
			    _checkObj.each(function (){
				   if(num!=(_checkObj.length-1)){
					   bugId += $(this).val()+",";		
				   }else{
					   bugId += $(this).val();		
				   }
				   num ++;
				});
				console.log(bugId);
				Rose.ajax.postJson(srvMap.get('deleteFaultList'), "bugIds="+bugId, function(json, status) {
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
			var _obj = $("#JS_getFaultList").find("input[type='checkbox']:checked").parents("tr");
			var _bugId = _obj.find("input[name='bugId']");
			console.log(_bugId)
			var data = {
				bugId: "",
			}
			if (_bugId.length == 0) {
				window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
				return;
			}else if(_bugId.length > 1){
				window.XMS.msgbox.show('请选择一条计划修改！', 'error', 2000);
			   return;
			} else {
				data.bugId = _bugId.val();
			}
			console.log(data.bugId)
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
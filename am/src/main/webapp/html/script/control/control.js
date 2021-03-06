define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 初始化列表
	srvMap.add("getControlShow", "control/getControl.json", "sys/ctrl/list");

	//树的现实
	srvMap.add("controlTree", "control/controlTree.json", "sys/component/compTree");

	//查询
	srvMap.add("getControlList", "control/getControlList.json", "sys/ctrl/list");

	//新增
	srvMap.add("addcontrol", "control/saveControl.json", "sys/ctrl/save");

	//删除
	srvMap.add("deleControl", "control/deleControl.json", "/sys/ctrl/del");

	//修改
	srvMap.add("updateControl", "control/updateControl.json", "sys/ctrl/update");


	// 模板对象
	var Tpl = {
		getContral: require('tpl/control/getControl.tpl'),
		getControlinfo:require('tpl/control/getControlinfo.tpl')
	};


	var Dom = {
		addStaffOrg: '#JS_addStaffOrg',
		getControlList: '#JS_getControlList',
		delStaffOrg: '#JS_delStaffOrg',
		addControlinfoModal:'#JS_addControlinfoModal',
		addControlinfo:'#JS_addControlinfo',
		addControlinfoForm:'#JS_addControlinfoForm',
		addControlinfoSubmit:'#JS_addControlinfoSubmit',
		updateControlinfo:'#JS_updateControlinfo',
		getControlinfoListTable:'#JS_getControlinfoListTable',
		deleControl:'#JS_deleControlinfo'
	}
	var Data = {
        funId:"",
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		}
    	}
    }


	var getContral = {
		init: function() {
			this._render();
		},
		_render: function() {
			this.queriesControl();
			this.Tree();

		},

		Tree:function(){
			var self = this;
			Rose.ajax.postJson(srvMap.get('controlTree'), '', function(json, status) {
				if (status) {
					var setting = {
						// check: {
						// 	enable: true
						// },
						data: {
							key: {
								name: "name"
							},
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "pId"
							}
						},
						callback: {
							 beforeClick: function(treeId, treeNode, clickFlag) {
						        return (treeNode.ifLeaf !== "N");
						     }, 
							 onClick: function(event, treeId, treeNode){
						        var _funId = treeNode.id;
						        console.log(_funId);
						        //存储在全局变量中
						        Data.funId = _funId;
						        var cmd = "funId="+_funId;
						        self.initOrganize(cmd);
							 }
						}

						};
					$.fn.zTree.init($("#treeDemo"), setting, json.data);
				}
				//滚动条
				$("#controlTree").slimScroll({
                    "height": '420px'
                });
			});
		},

				///////初始化///////////
		initOrganize: function(cmd) {
			var self = this;
			Rose.ajax.postJson(srvMap.get('getControlShow'), cmd, function(json, status) {
				
				if (status) {
					var template = Handlebars.compile(Tpl.getContral);
					console.log(json.data.content);
					$(Dom.getControlList).html(template(json.data.content));
					self.addControl();

					self.deleControl();
					self.updateControl();

					// 绑定单机当前行事件
				    self.eventClickChecked($("#JS_getControlinfoListTable"),function(){

				    })
				    // 绑定双击当前行事件
				    self.eventDClickCallback($("#JS_getControlinfoListTable"),function(){
				    	// 请求：用户基本信息
						self.getControlinfo();
				    })
				    
				}
			});
		},

		////条件查询、
		queriesControl: function() {
			var self = this;
			$("#queriesControl").unbind('click');
			$("#queriesControl").bind('click', function() {
				var cmd = $("#JS_getCompInfoForm").serialize();
				self.initOrganize(cmd);
				Data.funId = "";
			});

		},
		//添加控件
		addControl:function(){
			var self = this;
			$(Dom.addControlinfo).bind('click', function() {
				if (Data.funId == "") {
					window.XMS.msgbox.show('请选择一个功能点！', 'error', 2000);
					return null;
				} 
				var _form = $(Dom.addControlinfoForm);

				// 弹出层
				$(Dom.addControlinfoModal).modal('show');

				var template = Handlebars.compile(Tpl.getControlinfo);
            	_form.html(template({}));

            	$(Dom.addControlinfoSubmit).bind('click',function(){
            		var _dom = $("#JS_addControlinfoForm").find("input[name='funId']");
					_dom.val(Data.funId);

					// 表单校验：成功后调取接口
					// _form.bootstrapValidator('validate').on('success.form.bv', function(e) {
			            var cmd = _form.serialize();
			            console.log(cmd);

			  			Rose.ajax.postJson(srvMap.get('addcontrol'), cmd, function(json, status) {
							if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addControlinfoModal).modal('hide')
								setTimeout(function(){
									var cmd = "funId="+Data.funId;
									self.initOrganize(cmd);
								},1000)
							}
			  			});
		  		})
			});
		},

		//获取组件信息
		getControlinfo:function(){
			var self = this;
//			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var _data = self.getControlRow();
			var _ctrlId = _data.ctrlId;
			var cmd = {
				"ctrlId": _ctrlId,
				"ctrlName": $("#ctrlName").val(),
				"cearteId": $("#cearteName").val(),
				"time1": $("#time1").val(),
				"time2": $("#time2").val()
			}
			console.log(cmd);
			Rose.ajax.postJson(srvMap.get('getControlShow'), cmd, function(json, status) {
				if(status) {
					// 表单校验初始化
			        var _form = $(Dom.addControlinfoForm);
			        var template = Handlebars.compile(Tpl.getControlinfo);
			        var a = json.data.content[0]["ctrlType"];
	            	_form.html(template(json.data.content[0]));
		        	$("#ctrlType").val(a);
	            	XMS.msgbox.hide();

				    // 弹出层
					$(Dom.addControlinfoModal).modal('show');


					$(Dom.addControlinfoSubmit).bind('click',function(){

						// 表单校验：成功后调取接口
						// _form.bootstrapValidator('validate').on('success.form.bv', function(e) {
				            
				            var cm = "&ctrlId="+_ctrlId;
				            var cmd = _form.serialize()+cm;

				            console.log(cmd);
				  			XMS.msgbox.show('数据加载中，请稍候...', 'loading')
				  			Rose.ajax.postJson(srvMap.get('updateControl'), cmd, function(json, status) {
								if(status) {
									// 添加用户成功后，刷新用户列表页
									XMS.msgbox.show('保存成功！', 'success', 2000)
									// 关闭弹出层
									$(Dom.addControlinfoModal).modal('hide')
									setTimeout(function(){
										var cmd = "funId="+Data.funId;
										self.initOrganize(cmd);
									},1000)
								}
				  			});
					})
				}
  			});
		},

		//修改
		updateControl:function(){
			var self = this;
			$(Dom.updateControlinfo).unbind('click');
			$(Dom.updateControlinfo).bind('click', function() {
				var _data = self.getControlRow();
				if(_data){
					self.getControlinfo();
				}
			});
		},
		//删除
		deleControl:function(){
			var self = this;
			$(Dom.deleControl).unbind('click');
			$(Dom.deleControl).bind('click', function() {
				var _data = self.getControlRow();
				if (_data) {
					var _ctrlId = _data.ctrlId;
					Rose.ajax.postJson(srvMap.get('deleControl'), 'ctrlId=' + _ctrlId, function(json, status) {
						if (status) {
							// dele成功后，重新加载模板列表
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								var cmd = "funId="+Data.funId;
								self.initOrganize(cmd);
							}, 1000)
						}
					});
				}
			});

		},

		// 获取组织列表当前选中行
		getControlRow : function(){
			var _obj = $(Dom.getControlinfoListTable).find("input[type='radio']:checked").parents("tr");
			var _ctrlId = _obj.find("input[name='ctrlId']")
			var data = {
		        ctrlId: "",
		    }
		    if(_ctrlId.length==0){
		    	window.XMS.msgbox.show('请先选择一个控件！', 'error', 2000);
		    	return;
		    }else{
		    	data.ctrlId = _ctrlId.val();
		    }
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
	module.exports = getContral;
});
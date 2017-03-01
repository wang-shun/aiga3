define(function(require,exports,module){
	// 路径重命名
	var pathAlias = "systemManage/staffManage/";

	// 组织结构列表查询
	srvMap.add("getOrganizeList", pathAlias + "getOrganizeList.json", "sys/organize/treeList");
	// 员工列表按组织查询
	srvMap.add("getUserinfoListA", pathAlias + "getUserinfoList.json", "aiga/staff/listA");
	// 员工列表按条件查询
	srvMap.add("getUserinfoListB", pathAlias + "getUserinfoList.json", "aiga/staff/listB");
	// 新增员工
	srvMap.add("addUserinfo", pathAlias + "retMessage.json", "aiga/staff/save");
	// 修改员工
	srvMap.add("updateUserinfo", pathAlias + "retMessage.json", "aiga/staff/update");
	// 启用员工
	srvMap.add("startUserinfo", pathAlias + "retMessage.json", "aiga/staff/start");
	// 停员员工
	srvMap.add("stopUserinfo", pathAlias + "retMessage.json", "aiga/staff/stop");
	// 修改操作员密码
	srvMap.add("changePassword", pathAlias + "retMessage.json", "aiga/staff/changePass");
	// 重置操作员密码
	srvMap.add("resetPassword", pathAlias + "retMessage.json", "aiga/staff/resetPass");
	// 清空权限
	srvMap.add("clearPower", pathAlias + "retMessage.json", "aiga/staff/clearPower");
	// 操作员关联组织列表
	srvMap.add("getStaffOrgList", pathAlias + "getStaffOrgList.json", "aiga/staff/staffOrgList");
	// 操作员关联组织新增
	srvMap.add("addStaffOrg", pathAlias + "retMessage.json", "aiga/staff/staffOrgAdd");
	// 操作员关联组织删除
	srvMap.add("delStaffOrg", pathAlias + "retMessage.json", "aiga/staff/staffOrgDel");
	// 员工管理查询接口
	srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "sys/role/query");

	// 模板对象
    var Tpl = {
        getUserinfoList: require('tpl/systemManage/staffManage/getUserinfoList.tpl'),
        getStaffOrgList: require('tpl/systemManage/staffManage/getStaffOrgList.tpl')
    };

    // 容器对象
    var Dom = {
        getUserinfoList: '#JS_getUserinfoList',
      	getUserinfoListTable: '#JS_getUserinfoListTable',
        getOrganizeListTree: '#JS_getOrganizeListTree',
        getStaffOrgList: '#JS_getStaffOrgList',
        getStaffOrgListTable: '#JS_getStaffOrgListTable',
        getUserinfoForm: '#JS_getUserinfoForm',
        addUserinfo: '#JS_addUserinfo',
        addUserinfoModal:'#JS_addUserinfoModal',
        addUserinfoScroll:'#JS_addUserinfoScroll',
        addUserinfoForm:'#JS_addUserinfoForm',
        addUserinfoReset:'#JS_addUserinfoReset',
        addUserinfoSubmit:'#JS_addUserinfoSubmit',
        startUserinfo: '#JS_startUserinfo',
        stopUserinfo: '#JS_stopUserinfo',
        updateUserinfo: '#JS_updateUserinfo',
        changePassword: '#JS_changePassword',
        resetPassword: '#JS_resetPassword',
        clearPower: '#JS_clearPower',
        addStaffOrg: '#JS_addStaffOrg',
        addStaffOrgTree: '#JS_addStaffOrgTree',
        addStaffOrgModal: '#JS_addStaffOrgModal',
        addStaffOrgScroll:'#JS_addStaffOrgScroll',
        addStaffOrgForm:'#JS_addStaffOrgForm',
        addStaffOrgSubmit:'#JS_addStaffOrgSubmit',
        delStaffOrg: '#JS_delStaffOrg'
    };

    var Data = {
    	organizeId: null,
    	addOrganizeId: null, // 添加关联组织ID
    	staffId: null,
    	isOrganize: function(){
    		return $("#isOrganize").hasClass('active') ? true : false;
    	}
    }

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			// 默认只加载组织结构及条件查询
			this.getOrganizeList();
			this.getUserinfo();
		},
		// 组织结构
		getOrganizeList: function(){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getOrganizeList'), '', function(json, status) {
				if(status) {
					$.fn.zTree.init($(Dom.getOrganizeListTree), {
						data: {
							key: {
								name:"organizeName"
							},
							simpleData: {
								enable: true,
								idKey:"organizeId",
								pIdKey:"parentOrganizeId"
							}
						},
						callback:{
							 onClick: function(event, treeId, treeNode){
							 	var _organizeId = treeNode.organizeId;
							 	var _data = "organizeId="+_organizeId;
							 	// 存储在全局变量
							 	Data.organizeId = _organizeId;
							 	self.getUserinfoList(_data)
							 }
						}
					}, json.data);
				}
	  		});
		},
		// 按条件查询用户
		getUserinfo: function(){
			var self = this;
			var _form = $(Dom.getUserinfoForm);
			// 表单校验初始化
			//_form.bootstrapValidator('validate');
			// 表单提交
			_form.find('button[name="submit"]').bind('click',function(){

				// 表单校验：成功后调取接口
				//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
		            // e.preventDefault();
		            // 隐藏关联组织
		            $(Dom.getStaffOrgList).addClass('hide');
		            var cmd = $(Dom.getUserinfoForm).serialize();
		  			self.getUserinfoList(cmd);
	        	//});
	  		})
	  		// 表单重置
	  		/*_form.find('button[name="reset"]').bind('click',function(){
	  			_form.data('bootstrapValidator').resetForm(true);
	  		})*/
		},
		// 员工列表
		getUserinfoList: function (data){
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			var _url = '';
			if(Data.isOrganize()){
				_url = srvMap.get('getUserinfoListA')
	        }else{
	        	_url = srvMap.get('getUserinfoListB')
	        }
			Rose.ajax.getJson(_url, data, function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getUserinfoList);
					console.log(json.data)
					// 待删除：用于测试搜索数据
					/*if(!Data.isOrganize()){
			        	json.data.length = 1;
			        }*/
            		$(Dom.getUserinfoList).html(template(json.data));
            		XMS.msgbox.hide()

            		self.addUserinfo();
            		self.startUserinfo();
					self.stopUserinfo();
					self.resetPassword();
					self.clearPower();

					// 绑定双击当前行事件
				    self.eventDclickChecked($(Dom.getUserinfoList),function(){
				    	// 请求：关联组织
			        	self.getStaffOrgList();
				    })

					// 表格分页
					$(Dom.getUserinfoListTable).DataTable({
			          "paging": true,
			          "lengthChange": false,
			          "searching": false,
			          "ordering": false,
			          "info": true,
			          "autoWidth": false
			        });
				}
	  		});
		},
		// 新增用户
		addUserinfo:function(){
			var self = this;
			$(Dom.addUserinfo).bind('click', function() {
				console.log(Data.organizeId);
				// 如果组织结构未选中或未显示都不行
				if(!Data.isOrganize() || !Data.organizeId){
					XMS.msgbox.show('请先选择一个组织结构！', 'error', 2000);
					return false;
		        }
		        // 滚动条
		        $(Dom.addUserinfoScroll).slimScroll({
			        "height": '420px'
			    });
			    // 弹出层
				$(Dom.addUserinfoModal).modal('show');

		        // 表单校验初始化
		        var _form = $(Dom.addUserinfoForm);
				_form.bootstrapValidator('validate');
				// 表单提交
				$(Dom.addUserinfoSubmit).bind('click',function(){

					// 表单校验：成功后调取接口
					_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
			            var cmd = _form.serialize();
			            console.log(cmd);
			  			// self.getUserinfoList(cmd);
			  			XMS.msgbox.show('数据加载中，请稍候...', 'loading')
			  			Rose.ajax.getJson(srvMap.get('addUserinfo'), cmd, function(json, status) {
							if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加用户成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addUserinfoModal).modal('hide')
								setTimeout(function(){
									self.getUserinfoList();
								},1000)
							}
			  			});
		        	});
		  		})
		  		// 表单重置
		  		$(Dom.addUserinfoReset).bind('click',function(){
		  			_form.data('bootstrapValidator').resetForm(true);
		  		})

			})
		},
		// 启用用户
		startUserinfo:function(){
			var self = this;
			$(Dom.startUserinfo).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if(_data){
					var _staffId =_data.staffId;
					if(_data.state == '0'){
						Rose.ajax.getJson(srvMap.get('startUserinfo'), 'staffId='+_staffId, function(json, status) {
							if(status) {
								// 启用成功后，重新加载用户列表
								window.XMS.msgbox.show('员工启用成功！', 'success', 2000)
								setTimeout(function(){
									self.getUserinfoList();
								},1000)
							}
			  			});
					}else{
						XMS.msgbox.show('只允许操作失效员工！', 'error', 2000);
					}
				}
			});
		},
		// 停用用户
		stopUserinfo:function(){
			var self = this;
			$(Dom.stopUserinfo).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if(_data){
					var _staffId =_data.staffId;
					if(_data.state == '1'){
						Rose.ajax.getJson(srvMap.get('stopUserinfo'), 'staffId='+_staffId, function(json, status) {
							if(status) {
								// 停用成功后，重新加载用户列表
								window.XMS.msgbox.show('员工停用成功！', 'success', 2000)
								setTimeout(function(){
									self.getUserinfoList();
								},1000)
							}
			  			});
					}else{
						window.XMS.msgbox.show('只允许操作有效员工！', 'error', 2000);
					}
				}
			});
		},
		// 重置密码
		resetPassword:function(){
			var self = this;
			$(Dom.resetPassword).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if(_data){
					var _staffId =_data.staffId;
					Rose.ajax.getJson(srvMap.get('resetPassword'), 'staffId='+_staffId, function(json, status) {
						if(status) {
							// self.getUserinfoList();
							window.XMS.msgbox.show('密码重置成功！', 'success', 2000)
						}
		  			});
				}
			});
		},
		// 清空授权
		clearPower:function(){
			var self = this;
			$(Dom.clearPower).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if(_data){
					var _staffId =_data.staffId;
					if (confirm('您确认要清除“' + _data.name + '”的权限吗？')) {
                        Rose.ajax.getJson(srvMap.get('clearPower'), 'staffId='+_staffId, function(json, status) {
							if(status) {
								// 停用成功后，重新加载用户列表
								window.XMS.msgbox.show('权限清除成功！', 'success', 2000)
								setTimeout(function(){
									self.getUserinfoList();
								},1000)
							}
		  				});
                    }
				}
			});
		},
		getStaffOrgList: function(data){
			var self = this;
			var _data = self.getUserCheckedRow();
			Data.staffId = _data.staffId;
			Rose.ajax.getJson(srvMap.get('getStaffOrgList'), 'staffId:'+ _data.staffId, function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getStaffOrgList);
					$(Dom.getStaffOrgList).removeClass('hide');
            		$(Dom.getStaffOrgList).html(template(json.data));

            		// 接口：新增组织
			        self.addStaffOrg();
            		// 接口：删除组织
			        self.delStaffOrg();
            		// 绑定双击当前行事件
				    self.eventDclickChecked($(Dom.getStaffOrgList))
				}
  			});
		},
		// 新增组织结构
		addStaffOrg: function(){
			var self = this;
			$(Dom.addStaffOrg).bind('click', function() {
			    // 弹出层
				$(Dom.addStaffOrgModal).modal('show');
				// 滚动条
		        $(Dom.addStaffOrgScroll).slimScroll({
			        "height": '320px'
			    });
		        // 请求关联组织树
			    Rose.ajax.getJson(srvMap.get('getOrganizeList'), '', function(json, status) {
					if(status) {
						$.fn.zTree.init($(Dom.addStaffOrgTree), {
							data: {
								key: {
									name:"organizeName"
								},
								simpleData: {
									enable: true,
									idKey:"organizeId",
									pIdKey:"parentOrganizeId"
								}
							},
							callback:{
								 onClick: function(event, treeId, treeNode){
								 	var _organizeId = treeNode.organizeId;
								 	var _dom = $(Dom.addStaffOrgForm);
								 	_dom.find("input[name='staffId']").val(Data.staffId);
								 	_dom.find("input[name='organizeId']").val(_organizeId);
								 }
							}
						}, json.data);
					}
		  		});
		  		// 表单提交
				$(Dom.addStaffOrgSubmit).bind('click',function(){
					 var cmd = $(Dom.addStaffOrgForm).serialize();
					 Rose.ajax.getJson(srvMap.get('addStaffOrg'), cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.show('关联组织新增成功！', 'success', 2000)
							$(Dom.addStaffOrgModal).modal('hide')
							setTimeout(function(){
								self.getStaffOrgList();
							},1000)
						}
		  			});
		  		})
			})
		},
		// 删除组织结构
		delStaffOrg: function(){
			var self = this;
			$(Dom.delStaffOrg).bind('click', function() {
				var _data = self.getStaffOrgCheckedRow();
				if(_data){
					var cmd = 'staffId='+Data.staffId +'&organizeId='+_data.organizeId;
					Rose.ajax.getJson(srvMap.get('delStaffOrg'), cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.show('关联组织删除成功！', 'success', 2000)
							setTimeout(function(){
								self.getStaffOrgList();
							},1000)
						}
		  			});
				}
			});
		},
		// 获取员工列表当前选中行
		getUserCheckedRow : function(){
			var _obj = $(Dom.getUserinfoListTable).find("input[type='radio']:checked").parents("tr");
			var _staffId = _obj.find("input[name='staffId']")
			var _name = _obj.find("input[name='staffName']")
			var _state = _obj.find("input[name='staffState']")
			var data = {
				staffId: "",
		        name: "",
		        state: "",
		        code: "",
		        organizeName: "",
		        organizeId:""
		    }
		    if(_staffId.length==0){
		    	window.XMS.msgbox.show('请先选择一个用户！', 'error', 2000);
		    	return;
		    }else{
		    	data.staffId = _staffId.val();
		    	data.name = _name.val();
		    	data.state = _state.val();
		    }
		    return data;
		},
		// 获取组织列表当前选中行
		getStaffOrgCheckedRow : function(){
			var _obj = $(Dom.getStaffOrgListTable).find("input[type='radio']:checked").parents("tr");
			var _organizeId = _obj.find("input[name='organizeId']")
			var _organizeName= _obj.find("input[name='organizeName']")
			var data = {
		        organizeName: _organizeName.val(),
		        organizeId:_organizeId.val()
		    }
		    if(_organizeId.length==0){
		    	window.XMS.msgbox.show('请先选择一个组织结构！', 'error', 2000);
		    	return;
		    }
		    return data;
		},
		// 事件：双击选中当前行
		eventDclickChecked:function(obj,callback){
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
		}
	};
	module.exports = Query;
});


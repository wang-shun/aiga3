define(function(require,exports,module){
	// 路径重命名
	var pathAlias = "systemManage/staffManage/";

	// 组织结构列表查询
	srvMap.add("getOrganizeList", pathAlias + "getOrganizeList.json", "/sys/role/list");
	// 员工列表查询
	srvMap.add("getUserinfoList", pathAlias + "getUserinfoList.json", "/aiga/staff/listA");
	// 新增员工
	srvMap.add("addUserinfo", pathAlias + "retMessage.json", "/aiga/staff/save");
	// 修改员工
	srvMap.add("updateUserinfo", pathAlias + "retMessage.json", "/aiga/staff/update");
	// 启用员工
	srvMap.add("startUserinfo", pathAlias + "retMessage.json", "/aiga/staff/start");
	// 停员员工
	srvMap.add("stopUserinfo", pathAlias + "retMessage.json", "/aiga/staff/stop");
	// 修改操作员密码
	srvMap.add("changePassword", pathAlias + "retMessage.json", "/aiga/staff/changePass");
	// 重置操作员密码
	srvMap.add("resetPassword", pathAlias + "retMessage.json", "/aiga/staff/resetPass");
	// 清空权限
	srvMap.add("clearPower", pathAlias + "retMessage.json", "/aiga/staff/clearPower");
	// 操作员关联组织列表
	srvMap.add("getStaffOrgList", pathAlias + "getStaffOrgList.json", "/aiga/staff/staffOrgList");
	// 操作员关联组织新增
	srvMap.add("addStaffOrg", pathAlias + "retMessage.json", "/aiga/staff/staffOrgAdd");
	// 操作员关联组织删除
	srvMap.add("delStaffOrg", pathAlias + "retMessage.json", "/aiga/staff/staffOrgDel");
	// 员工管理查询接口
	srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	// 模板对象
    var Tpl = {
        getUserinfoList: require('tpl/home/getUserinfoList.tpl')
    };

    // 容器对象
    var Dom = {
        getUserinfoList: '#Page_getUserinfoList',
        getUserinfoForm: '#JS_getUserinfoForm',
        addUserinfo: '#JS_addUserinfo',
        startUserinfo: '#JS_startUserinfo',
        stopUserinfo: '#JS_stopUserinfo',
        updateUserinfo: '#JS_updateUserinfo',
        changePassword: '#JS_changePassword',
        resetPassword: '#JS_resetPassword',
        clearPower: '#JS_clearPower'
    };

    var Data = {
    	organizeId: null,
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
					$.fn.zTree.init($("#Tree_getOrganizeList"), {
						check: {
							enable: true
						},
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
							 	var _data = "organizeId:"+_organizeId;
							 	// 存储在全局变量
							 	Data.organizeId = _organizeId;
							 	self.getUserinfoList(_data)
							 }
						}
					}, json.data.organizeList);
				}
	  		});
		},
		// 按条件查询用户
		getUserinfo: function(){
			var self = this;
			var _form = $(Dom.getUserinfoForm);
			// 表单提交
			_form.find('button[name="submit"]').bind('click',function(){

				// 表单校验：成功后调取接口
				_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
		            var cmd = $("#Form_getUserinfo").serialize();
		  			self.getUserinfoList(cmd);
	        	});
	  		})
	  		// 表单重置
	  		_form.find('button[name="reset"]').bind('click',function(){
	  			_form.data('bootstrapValidator').resetForm(true);
	  		})
		},
		// 员工列表
		getUserinfoList: function (data){
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading')
			Rose.ajax.getJson(srvMap.get('getUserinfoList'), data, function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getUserinfoList);
					console.log(json.data[0])
					// 待删除：用于测试搜索数据
					if(!Data.isOrganize()){
			        	json.data.length = 1;
			        }
            		$(Dom.getUserinfoList).html(template(json.data));
            		XMS.msgbox.hide()

            		self.addUserinfo();
            		self.startUserinfo();
					self.stopUserinfo();
					self.resetPassword();
					self.clearPower();

				    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				      checkboxClass: 'icheckbox_square-blue',
				      radioClass: 'iradio_square-blue'
				    });


					// 表格分页
					$('#Table_getUserinfoList').DataTable({
			          "paging": true,
			          "lengthChange": false,
			          "searching": false,
			          "ordering": false,
			          "info": true,
			          "autoWidth": false
			        });

					// 事件：双击选中当前行数据
			        $('#Table_getUserinfoList').find("tr").bind('click', function(event) {
			        	$(this).find('.minimal').iCheck('check');
			        });

				}
	  		});
		},
		// 新增用户
		addUserinfo:function(){
			$(Dom.addUserinfo).bind('click', function() {
				console.log(Data.organizeId);
				// 如果组织结构未选中或未显示都不行
				if(!Data.isOrganize() || !Data.organizeId){
					XMS.msgbox.show('请先选择一个组织结构！', 'error', 2000);
					return false;
		        }
		        $('#Scroll_addUserinfo').slimScroll({
			        "height": '350px'
			    });
				$('#myModal').modal('show');
			})
		},
		// 启用用户
		startUserinfo:function(){
			var self = this;
			$(Dom.startUserinfo).bind('click', function() {
				var _data = self.getCheckedRow();
				if(_data){
					var _stafId =_data.staffId;
					if(_data.state == '失效'){
						Rose.ajax.postJson(srvMap.get('startUserinfo'), 'stafId:'+_stafId, function(json, status) {
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
				var _data = self.getCheckedRow();
				if(_data){
					var _stafId =_data.staffId;
					if(_data.state == '有效'){
						Rose.ajax.postJson(srvMap.get('stopUserinfo'), 'stafId:'+_stafId, function(json, status) {
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
				var _data = self.getCheckedRow();
				if(_data){
					var _stafId =_data.staffId;
					Rose.ajax.postJson(srvMap.get('resetPassword'), 'stafId:'+_stafId, function(json, status) {
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
				var _data = self.getCheckedRow();
				if(_data){
					var _stafId =_data.staffId;
					if (confirm('您确认要清除“' + _data.name + '”的权限吗？')) {
                        Rose.ajax.postJson(srvMap.get('clearPower'), 'stafId:'+_stafId, function(json, status) {
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
		// 获取当前选中行
		getCheckedRow : function(){
			var _obj = $('#Table_getUserinfoList').find("input[type='radio']:checked").parents("tr");
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
		}
	};
	module.exports = Query;
});


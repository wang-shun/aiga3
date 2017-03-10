define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "caseInstanceMng/";

	srvMap.add("list", pathAlias + "getCaseTempList.json", "case/instance/list");
	srvMap.add("delete", pathAlias + "getCaseTempList.json", "case/instance/del");
	srvMap.add("funcList", "componentManage/getFunList.json", "sys/component/compTree");

	// 模板对象
	var Tpl = {

	};
	

	// 容器对象
	var Dom = {
		queryForm : "#JS_queryCaseInstanceForm",
		table: "#caseInstanceTable",
		delBtn: "#JS_delCaseInstance"
	};

	var fundId = 0;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {
			//加载功能树
			
			this.initFunctionTree();
			
			this.getCaseInstanceList();
			
			this.addQueryFormListener();
			this.addBtnListener();
			
			// 默认只加载组织结构及条件查询
		},
		
		initFunctionTree: function(){
			Rose.ajax.getJson(srvMap.get('funcList'), '', function(json, status) {
				if(status) {
					var setting = {
						data: {
							key: {
								name:"name"
							},
							simpleData: {
								enable: true,
							}
						},
						callback:{
//							 beforeClick: function(treeId, treeNode, clickFlag) {
//                                return (treeNode.ifLeaf !== "N");
//                             }, 
							 onClick: function(event, treeId, treeNode){
                                var _funId = treeNode.id;
                                console.log(_funId);
                                //存储在全局变量中
                                fundId = _funId;
                                var cmd = "funId=" + fundId;
                                console.log(cmd)
							 }
						}
					};
					$.fn.zTree.init($("#Tree_getRightTree"), setting, json.data);
				}
			});
		},
		
		// 按条件查询模板
		addQueryFormListener: function() {
			var _form = $(Dom.queryForm);
			
			_form.submit(function(e){
				$(Dom.table).bootstrapTable('refresh');
				return false;
			});
			
			_form.find('button[name="submit"]').on('click', function() {
				$(Dom.table).bootstrapTable('refresh');
			});
		},
		
		// 按条件查询模板
		addBtnListener: function() {
			
			$(Dom.delBtn).click(function(){
				var ids = $.map($(Dom.table).bootstrapTable('getSelections'), function (row) {
		            return row.testId;
		        })
		        
		        console.log(ids);
		        
		        var date = {
					caseIds : ids
				}
		        
		        Rose.ajax.postJson(srvMap.get('delete'), date, function(json, status) {
					if(status) {
						$(Dom.table).bootstrapTable('refresh');
					}
				});
			});
			
			$(Dom.table).on('check.bs.table uncheck.bs.table ' +
	                'check-all.bs.table uncheck-all.bs.table', function () {
				$(Dom.delBtn).prop('disabled', !$(Dom.table).bootstrapTable('getSelections').length);
	        });
			
		},


		// 用例模板列表
		getCaseInstanceList: function(cmd) {
			var self = this;
			/**/
			$(Dom.table).bootstrapTable({
				url: srvMap.get('list'),
				method : "post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				queryParams : function(params){
					return jQuery.extend(params, $(Dom.queryForm).serializeJSON());
				},
				responseHandler : function(data){
					return data["data"];
				},
		        totalField: 'totalElements',
		        dataField: 'content',
				pagination: true,
				sidePagination: "server",
				queryParamsType : "page",
				pageNumber: 1,
		        pageSize: 10,
		        pageList: [10, 25, 50, 100],
		        idField: "testId",
		        columns :[
		        	{
                        checkbox: true
                    },
		        	{
		        		title: '测试用例名称',
		        		field: 'testName'
		        	},
		        	{
		        		title: '操作说明',
		        		field: 'testDesc'
		        	},
		        	{
		        		title: '预期结果',
		        		field: 'preResult'
		        	},
		        	{
		        		title: '是否实现自动化'
		        	},
		        	{
		        		title: '可实现自动化'
		        	},
		        	{
		        		title: '已实现自动化'
		        	},
		        	{
		        		title: '操作',
		        		formatter : function(){
		        			return [
		        	            '<a class="run" href="javascript:void(0)" title="Like">',
		        	            '运行',
		        	            '</a>  ',
		        	            '<a class="edit" href="javascript:void(0)" title="Remove">',
		        	            '编辑',
		        	            '</a>',
		        	            '<a class="copy" href="javascript:void(0)" title="Remove">',
		        	            '复制',
		        	            '</a>'
		        	        ].join('');
		        		}
		        	}
		        ]
			});
			
		},
		

		

		// 新增模板
		addCaseTemp: function() {
			var self = this;
			$(Dom.addCaseTemp).bind('click', function() {

				$(Dom.addUserinfoScroll).slimScroll({
					"height": '420px'
				});
				// 弹出层
				$(Dom.modalCaseTempForm).modal('show');

				//j加载form表单
			
				var template = Handlebars.compile(Tpl.getCaseTempForm);
				$(Dom.caseTempForm).html(template());
				self.getSysList("#add_sysId");
				
				// 表单校验初始化
				//var _form = $(Dom.addUserinfoForm);
				//_form.bootstrapValidator('validate');
				// 表单提交
				$(Dom.addUserinfoSubmit).bind('click', function() {

						// 表单校验：成功后调取接口
						//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
							var cmd = _form.serialize();
							console.log(cmd);
							// self.getUserinfoList(cmd);
							XMS.msgbox.show('数据加载中，请稍候...', 'loading')
							Rose.ajax.getJson(srvMap.get('addUserinfo'), cmd, function(json, status) {
								if (status) {
									// 添加用户成功后，刷新用户列表页
									XMS.msgbox.show('添加用户成功！', 'success', 2000)
										// 关闭弹出层
									$(Dom.addUserinfoModal).modal('hide')
									setTimeout(function() {
										self.getUserinfoList();
									}, 1000)
								}
							});
						// });
					})
					// 表单重置
				$(Dom.addUserinfoReset).bind('click', function() {
					_form.data('bootstrapValidator').resetForm(true);
				})

			})
		},
		// 删除模板
		deleCaseTemp: function() {
			var self = this;

			$(Dom.deleCaseTemp).bind('click', function() {
				var _data = self.getTempCheckedRow();
				if (_data) {
					var _caseId = _data.caseId;
					alert(_caseId);
					Rose.ajax.getJson(srvMap.get('delCaseTemp'), 'caseId=' + _caseId, function(json, status) {
						if (status) {
							// dele成功后，重新加载模板列表
							window.XMS.msgbox.show('模板删除成功！', 'success', 2000)
							setTimeout(function() {
								self.getCaseTempList();
							}, 1000)
						}
					});
					
				}
			});
		},
		// 停用用户
		stopUserinfo: function() {
			var self = this;
			$(Dom.stopUserinfo).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _caseId = _data.staffId;
					if (_data.state == '1') {
						Rose.ajax.getJson(srvMap.get('stopUserinfo'), 'staffId=' + _caseId, function(json, status) {
							if (status) {
								// 停用成功后，重新加载用户列表
								window.XMS.msgbox.show('员工停用成功！', 'success', 2000)
								setTimeout(function() {
									self.getUserinfoList();
								}, 1000)
							}
						});
					} else {
						window.XMS.msgbox.show('只允许操作有效员工！', 'error', 2000);
					}
				}
			});
		},
		// 重置密码
		resetPassword: function() {
			var self = this;
			$(Dom.resetPassword).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _caseId = _data.staffId;
					Rose.ajax.getJson(srvMap.get('resetPassword'), 'staffId=' + _caseId, function(json, status) {
						if (status) {
							// self.getUserinfoList();
							window.XMS.msgbox.show('密码重置成功！', 'success', 2000)
						}
					});
				}
			});
		},
		// 清空授权
		clearPower: function() {
			var self = this;
			$(Dom.clearPower).bind('click', function() {
				var _data = self.getUserCheckedRow();
				if (_data) {
					var _caseId = _data.staffId;
					if (confirm('您确认要清除“' + _data.name + '”的权限吗？')) {
						Rose.ajax.getJson(srvMap.get('clearPower'), 'staffId=' + _caseId, function(json, status) {
							if (status) {
								// 停用成功后，重新加载用户列表
								window.XMS.msgbox.show('权限清除成功！', 'success', 2000)
								setTimeout(function() {
									self.getUserinfoList();
								}, 1000)
							}
						});
					}
				}
			});
		},
		getStaffOrgList: function(data) {
			var self = this;
			var _data = self.getUserCheckedRow();
			Data.staffId = _data.staffId;
			Rose.ajax.getJson(srvMap.get('getStaffOrgList'), 'staffId:' + _data.staffId, function(json, status) {
				if (status) {
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
		addStaffOrg: function() {
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
					if (status) {
						$.fn.zTree.init($(Dom.addStaffOrgTree), {
							data: {
								key: {
									name: "organizeName"
								},
								simpleData: {
									enable: true,
									idKey: "organizeId",
									pIdKey: "parentOrganizeId"
								}
							},
							callback: {
								onClick: function(event, treeId, treeNode) {
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
				$(Dom.addStaffOrgSubmit).bind('click', function() {
					var cmd = $(Dom.addStaffOrgForm).serialize();
					Rose.ajax.getJson(srvMap.get('addStaffOrg'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('关联组织新增成功！', 'success', 2000)
							$(Dom.addStaffOrgModal).modal('hide')
							setTimeout(function() {
								self.getStaffOrgList();
							}, 1000)
						}
					});
				})
			})
		},
		
		// 获取模板列表当前选中行
		getTempCheckedRow: function() {
			var _obj = $("#JS_getUserinfoListTable").find("input[type='radio']:checked").parents("tr");
			var _caseId = _obj.find("input[name='caseId']")
			
			var _name = _obj.find("input[name='caseName']")
			var data = {
				caseId: "",
				caseName: "",
			}
			if (_caseId.length == 0) {
				window.XMS.msgbox.show('请先选择一个模板！', 'error', 2000);
				return;
			} else {
				data.caseId = _caseId.val();
				data.caseName = _name.val();
			}
			return data;
		},
		

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
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		}
	};
	module.exports = Init;
});
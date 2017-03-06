define(function(require,exports,module){

	//引入公用模块
    //路径重命名
    var pathAlias = "componentManage/";
	//获取所有功能菜单	
	srvMap.add("getFuncList", "componentManage/getFunList.json", "");
	//根据当前功能点ID调取组件
	srvMap.add("getCompList","componentManage/getCompList.json","");
	// 查询组件信息
	srvMap.add("getCompinfo", pathAlias + "getCompinfo.json", "");
	// 添加组件
	srvMap.add("addComp", pathAlias + "retMessage.json", "");
	//修改组件
	srvMap.add("updateComp", pathAlias + "retMessage.json","");
	//删除选中组件
	srvMap.add("delComp", pathAlias + "retMessage.json","");
    // 组件列表按条件查询
	srvMap.add("queryCompInfo", pathAlias + "queryCompInfo.json", "");
	//请求控件树
	srvMap.add("getCompCtrTree", pathAlias + "getCompCtrTree.json", "");
	//请求参数列表
	srvMap.add("getParameterList",pathAlias + "getParameterList.json","");
	//
    var Tpl = {
    	getQueryInfo:require('tpl/componentManage/getQueryInfo.tpl'),
    	getCompInfoForm: require('tpl/componentManage/getCompInfoForm.tpl'),
    	getParameterList: require('tpl/componentManage/getParameterList.tpl'),
    	addParameterForm: require('tpl/componentManage/addParameterForm.tpl')
    };
    var Mod = {
    	getQueryInfo:'#Page_queryInfo'
    };
    var Dom = {
    	getCompInfoForm:"#JS_getCompInfoForm",
    	addComp:"#JS_addComp",
    	delComp:"#JS_delComp",
    	getQueryInfo:"#JS_getQueryInfo",
    	addCompModal:"#JS_addCompModal",
    	addCompCtrTree:"#JS_addCompCtrTree",
    	addCompInfoForm:"#JS_addCompInfoForm",
    	addCompSubmit:"#JS_addCompSubmit",
    	updateComp:"#JS_updateComp",
    	addParameterForm:"#JS_addParameterForm",
    	getParameterList:"#JS_getParameterList"
    }
    var Data = {
        funId:null
    }
	var indexInfoQuery = {
		init: function(){
			this._render();
		},     
		_render: function() {
			this.getLeftTree();
			this.queryCompInfo();
			this.delComp();
			this.addComp();
			this.updateComp();
			//展示表单
		},
		// getStaffRoleList: function(){
		// 	    var self = this;
		// 	    Rose.ajax.getJson(srvMap.get('getStaffRoleList'), '', function(json, status) {                    
		// 		if(status) {
		// 			var template = Handlebars.compile(Tpl2.getStaffRoleList);
		// 			console.log(json.data)
  //           		$(Mod2.getStaffRoleList).html(template(json.data));
  //           		 //iCheck
		// 		    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
		// 		      checkboxClass: 'icheckbox_minimal-blue',
		// 		      radioClass: 'iradio_minimal-blue'
		// 		    });
		// 	        // 事件：双击选中当前行数据
		// 	        $(Dom.getRoleFuncTable).find("tr").bind('click', function(event) {			        	
		// 	        	$(this).find('.minimal').iCheck('check');
		// 	        	var _data = self.getCheckedRole();
		// 	        	var _roleId =_data.roleId;
		// 	        	var cmd = "roleId="+_roleId;
		// 	        	Data.roleId = _roleId;
		// 	        	console.log(cmd);
		// 	        	self.getRoleFuncCheckedList(cmd);
		// 	        });
		// 	        // 滚动条
		// 	        $(Dom.getRoleFuncTable).parent().slimScroll({
		// 		        "height": '500px'
		// 		    });
		// 		}
	 //  		});
		// },
		// getRoleFuncCheckedList :function(cmd){
  //       	var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTree");
  //           treeObj.checkAllNodes(false);
  //       	Rose.ajax.getJson(srvMap.get('getRoleFuncCheckedList'), cmd, function(json, status) {
		// 		if(status) {
		// 			var _json = json.data;
		// 			console.log(_json);
		// 			var zTree_Menu = $.fn.zTree.getZTreeObj("Tree_getRightTree");  

		// 			for(i in _json){
		// 				var node = zTree_Menu.getNodeByParam('funcId',_json[i].funcId);  
	 //                	zTree_Menu.checkNode(node,true);//指定选中ID的节点  
	 //                	zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开  
		// 			}
	                
		// 		}
		// 	})
  //       },
  		//获取左侧组件树
		getLeftTree:function(){
			var self = this
				Rose.ajax.getJson(srvMap.get('getFuncList'), '', function(json, status) {
				if(status) {
					console.log(json.data)
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
							 beforeClick: function(treeId, treeNode, clickFlag) {
                                return (treeNode.ifleaf !== "N");
                             }, 
							 onClick: function(event, treeId, treeNode){
                                var _funId = treeNode.id;
                                console.log(_funId);
                                //存储在全局变量中
                                Data.funId = _funId;
                                var cmd = _funId;
                                self.getCompByFunId(cmd);
                                //self.addComp(cmd1);
							 }
						}
					};
					$.fn.zTree.init($("#Tree_getRightTree"), setting, json.data);


	                
				}
	  		});

		},
		//通过功能点获取右侧组件表格数据
		getCompByFunId:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCompList'), 'cmd', function(json, status) {                    
				if(status) {
					var template = Handlebars.compile(Tpl.getQueryInfo);
					console.log(json.data)
            		$(Mod.getQueryInfo).html(template(json.data));
 					//icheck
            		$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				      checkboxClass: 'icheckbox_minimal-blue',
				      radioClass: 'iradio_minimal-blue'
				    });
				    // 事件：双击选中当前行数据
			        $('#JS_getQueryInfo').find("tr").bind('click', function(event) {
			        	$(this).find('.minimal').iCheck('check');
			        });
  			}
  		});
		},
		// 添加组件
		addComp:function(){
			var self = this;
			$(Dom.addComp).unbind('click');
			$(Dom.addComp).bind('click', function() {
				// 如果组织结构未选中或未显示都不行
				if(Data.funId == null){
					XMS.msgbox.show('请先选择一个功能点！', 'info', 2000);
					return;
		        }
		        //组件表单校验初始化
		        var _form = $(Dom.addCompInfoForm);
		        var template = Handlebars.compile(Tpl.getCompInfoForm);
            	_form.html(template({}));
            	//参数表单校验初始化
            	var _form1 = $(Dom.addParameterForm);
            	var template = Handlebars.compile(Tpl.addParameterForm);
            	_form1.html(template({}));
		        // 滚动条
		     //    $(Dom.addUserinfoScroll).slimScroll({
			    //     "height": '420px'
			    // });
			    // 弹出层
				$(Dom.addCompModal).modal('show');

				//创建时间锁定
				_form.find("[name='createTime']").val("2017-03-03 15:29:30").attr("readonly",true);
				//最后修改人锁定
				_form.find("[name='updateName']").val("张三").attr("readonly",true);
				//组件创建人锁定
				_form.find("[name='creator']").val("张三").attr("readonly",true);
				//请求控件树
			    Rose.ajax.getJson(srvMap.get('getCompCtrTree'), '', function(json, status) {
					if(status) {
						$.fn.zTree.init($(Dom.addCompCtrTree), {
							data: {
								key: {
									name:"name"
								},
								simpleData: {
									enable: true,
								}
							},
							callback:{
								beforeClick: function(treeId, treeNode, clickFlag) {
                                	return (treeNode.ifleaf !== "N");
                             }, 
								onClick: function(event, treeId, treeNode){
								 	var _compCtrId = treeNode.id;
								 	var _data = self.getScript(json.data,_compCtrId);
								 	var _dom = $(Dom.addCompInfoForm);
								 	alert(_data.script);
								 	 _dom.find("textarea[name='compScript']").append(_data.script+"\r\n");
								 	// _dom.find("input[name='organizeId']").val(_organizeId);
								 }
							}
						}, json.data);
					}
		  		});


				// _form.bootstrapValidator('validate');
				// 表单提交
				$(Dom.addCompSubmit).bind('click',function(){

					// 表单校验：成功后调取接口
					// _form.bootstrapValidator('validate').on('success.form.bv', function(e) {
			            var cmd = _form.serialize();
			            console.log(cmd);
			  			// self.getUserinfoList(cmd);
			  			XMS.msgbox.show('数据加载中，请稍候...', 'loading')
			  			Rose.ajax.getJson(srvMap.get('addComp'), cmd, function(json, status) {
							if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('添加组件成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addCompModal).modal('hide')
								setTimeout(function(){
									self.getCompByFunId();
								},1000)
							}
			  			});
		        	//});
		  		})
		 //  		// 表单重置
		 //  		/*$(Dom.addUserinfoReset).bind('click',function(){
		 //  			_form.data('bootstrapValidator').resetForm(true);
		 //  		})*/

			// })
		})
		},
		//获取参数列表
		getParameterList: function(){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getParameterList'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getParameterList);
            		$(Dom.getParameterList).html(template(json.data));

            		// 添加、删除
            		// self.addRoleinfo();
            		// self.delRoleinfo();

            		// 点击选中行
				    self.eventClickChecked($(Dom.getParameterList));

				    // 绑定双击当前行事件
				    self.eventDClickCallback($(Dom.getParameterList),function(){
			        	// self.getRoleinfo();
				    })

				    //设置分页
				    self.initPaging($(Dom.getParameterList),8)

				}
	  		});
		},
		//获取组件信息
		getCompinfo:function(){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCompinfo'), '', function(json, status) {
				if(status) {
					// 表单校验初始化
			        var _form = $(Dom.addCompInfoForm);
			        var template = Handlebars.compile(Tpl.getCompInfoForm);
			        console.log(json.data);
	            	_form.html(template(json.data));
	            	$("#compScript").val(json.data.compScript)
	            	self.getParameterList();
			        // 滚动条
			     //    $(Dom.addUserinfoScroll).slimScroll({
				    //     "height": '420px'
				    // });
				    // 弹出层
					$(Dom.addCompModal).modal('show');

					// 提交
					$(Dom.addCompSubmit).bind('click',function(){

					// 表单校验：成功后调取接口
					// _form.bootstrapValidator('validate').on('success.form.bv', function(e) {
			            var cmd = _form.serialize();
			            console.log(cmd);
			  			// self.getUserinfoList(cmd);
			  			XMS.msgbox.show('数据加载中，请稍候...', 'loading')
			  			Rose.ajax.getJson(srvMap.get('addComp'), cmd, function(json, status) {
							if(status) {
								// 添加用户成功后，刷新用户列表页
								XMS.msgbox.show('修改组件成功！', 'success', 2000)
								// 关闭弹出层
								$(Dom.addUserinfoModal).modal('hide')
								setTimeout(function(){
									self.getCompByFunId();
								},1000)
							}
			  			});
		        	//});
		  		})
				}
  			});
		},
		//修改组件
		updateComp:function(){
			var self = this;
			$(Dom.updateComp).bind('click', function() {
				var _data = self.getCheckedComp();
				if(_data){
					self.getCompinfo();
				}
			});
		},
		//通过表单条件查询组件
		queryCompInfo: function(){
			var self = this;
			var _form = $(Dom.getCompInfoForm);
			// 表单校验初始化
			//_form.bootstrapValidator('validate');
			// 表单提交
			_form.find('button[name="submit"]').bind('click',function(){

				// 表单校验：成功后调取接口
				//_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
		            // e.preventDefault();
		            var cmd = $(Dom.getCompInfoForm).serialize();
		            console.log(cmd);
		  			self.getCompByQuery(cmd);
	        	//});
	  		})
	  		// 表单重置
	  		/*_form.find('button[name="reset"]').bind('click',function(){
	  			_form.data('bootstrapValidator').resetForm(true);
	  		})*/
		},
		//搜索组件
		getCompByQuery:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('queryCompInfo'), 'cmd', function(json, status) {                    
				if(status) {
					var template = Handlebars.compile(Tpl.getQueryInfo);
					console.log(json.data)
            		$(Mod.getQueryInfo).html(template(json.data));
 					//icheck
            		$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				      checkboxClass: 'icheckbox_minimal-blue',
				      radioClass: 'iradio_minimal-blue'
				    });
				    // 事件：双击选中当前行数据
			        $('#Table_getQueryInfo').find("tr").bind('click', function(event) {
			        	$(this).find('.minimal').iCheck('check');
			        });
  			}
  		});
		},
		//删除组件
		delComp:function(){
  			var self = this;
  				$(Dom.delComp).bind('click', function() {
					var _data = self.getCheckedComp();
						if(_data){
							var cmd = {
								compId:""
							}
							cmd.compId = _data.compId;
							XMS.msgbox.show('数据加载中，请稍候...', 'loading');
							console.log(cmd);
							Rose.ajax.getJson(srvMap.get('delComp'), 'cmd', function(json, status) {
							if(status) {
								window.XMS.msgbox.show('删除成功！', 'success', 2000)
									setTimeout(function(){
								self.getCompByFunId();
							},1000)
						}
		  			});
				}
			});
		},
		getScript : function(arrayData,_compCtrId){
			var data = {};
			for(i in arrayData){
				if(arrayData[i].id==_compCtrId){
					data = arrayData[i];
				}
			}
			return data;
		},
		//获取选中组件
		getCheckedComp : function(){
			var _obj = $(Dom.getQueryInfo).find("input[type='radio']:checked").parents("tr");
			var _compId = _obj.find("input[name='compId']")
		    if(_compId.length==0){
		    	window.XMS.msgbox.show('请先选择一个组件！', 'error', 2000);
		    	return;
		    }else{
 				var data = {
					compId: ""
			    }
		    	data.compId = _compId.val();
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
		},
		// 事件：双击绑定事件
		initPaging:function(obj,length){
			obj.find("table").DataTable({
			  "iDisplayLength":length,
	          "paging": true,
	          "lengthChange": false,
	          "searching": false,
	          "ordering": false,
	          "info": true,
	          "autoWidth": false
	        });
		}

	};
	module.exports = indexInfoQuery;
});


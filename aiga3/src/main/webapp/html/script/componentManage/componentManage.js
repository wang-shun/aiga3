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
	//根据参数ID获取参数信息
	srvMap.add("getParamInfo",pathAlias + "getParamInfo.json","");
	//添加参数
	srvMap.add("addParamInfo",pathAlias + "retMessage.json","");
	//保存参数
	srvMap.add("updateParamInfo",pathAlias + "retMessage.json","");
	//删除参数
	srvMap.add("delParamInfo",pathAlias + "retMessage.json","");
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
        funId:null,
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		}
    	}
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
                                return (treeNode.ifLeaf !== "N");
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
			Rose.ajax.getJson(srvMap.get('getCompList'), cmd, function(json, status) {                    
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
            	self.getParamInfo();
            	self.addParamInfo();
            	self.delParamInfo();
            	self.updateParamInfo();
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
								 	 _dom.find("textarea[name='compScript']").append(_data.compScript+"\r\n");
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
		//获取参数列表(根据组件ID)
		getParameterListById: function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getParameterList'), cmd, function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getParameterList);
            		$(Dom.getParameterList).html(template(json.data));

            		// 添加、删除
            		self.addParamInfo();
            		self.delParamInfo();

            		// 点击选中行
				    self.eventClickChecked($(Dom.getParameterList));

				    // 绑定双击当前行事件
				    self.eventDClickCallback($(Dom.getParameterList),function(){
			        	 self.getParamInfoByID();
				    })

				    //设置分页
				    self.initPaging($(Dom.getParameterList),8)

				}
	  		});
		},
		// 获取参数信息
		getParamInfo: function(){
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getParamInfo'), '', function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					$(Dom.addParameterForm).removeClass('hide');
					json.data["type"]="新增角色";
					var template = Handlebars.compile(Tpl.addParameterForm);
            		$(Dom.addParameterForm).html(template(json.data))
            		// 提交保存
            		 self.updateParamInfo('update');
				}
  			});

		},
		// 获取参数信息(根据参数ID)
		getParamInfoByID: function(){
			var self = this;
			var _data = self.getCheckedParamRow();
			var cmd = 'paramId='+_data.paramId;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getParamInfo'), cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					$(Dom.addParameterForm).removeClass('hide');
					json.data["type"]="修改参数";
					var template = Handlebars.compile(Tpl.addParameterForm);
            		$(Dom.addParameterForm).html(template(json.data))
            		// 提交保存
            		self.updateParamInfo('update');
				}
  			});

		},
		// 添加参数
		addParamInfo: function(){
			var self = this;
			var _domAdd = $(Dom.getParameterList).find("[name='add']");
			_domAdd.bind('click', function() {
				$(Dom.addParameterForm).removeClass('hide');
				var json = Data.setPageType("添加参数")
		        var template = Handlebars.compile(Tpl.addParameterForm);
            	$(Dom.addParameterForm).html(template(json.data))
            	// 添加时移除roleId
				$(Dom.addParameterForm).find("[name='ParamId']").remove();
            	// 提交保存
            	self.updateParamInfo('save');
			});

		},
		//保存参数
		updateParamInfo:function(type){
			var self = this;
			var _srvMap = type == "save" ? 'addParamInfo' : 'updateParamInfo';
    		var _domSave = $(Dom.addParameterForm).find("[name='save']");
    		_domSave.bind('click', function() {
				var cmd = $(this).parents("form").serialize();
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');

				Rose.ajax.getJson(srvMap.get(_srvMap), cmd, function(json, status) {
					if(status) {
						window.XMS.msgbox.show('保存成功！', 'success', 2000)
						setTimeout(function(){
							self.getParameterListById();
						},1000)
					}
	  			});
  			});
		},
		// 删除角色
		delParamInfo: function(){
			var self = this;
			var _domDel = $(Dom.getParameterList).find("[name='del']");
			_domDel.bind('click', function() {
				var _data = self.getCheckedParamRow();
				if(_data){
					var cmd = 'ParamId='+_data.ParamId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delParamInfo'), cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function(){
								self.getParameterListById();
								$(Dom.addParameterForm).addClass('hide');
							},1000)
						}
		  			});
				}
			});
		},
		//获取组件信息(根据组件ID)
		getCompinfo:function(cmd){
			var self = this;
			Rose.ajax.getJson(srvMap.get('getCompinfo'), cmd, function(json, status) {
				if(status) {
					// 表单校验初始化
			        var _form = $(Dom.addCompInfoForm);
			        var template = Handlebars.compile(Tpl.getCompInfoForm);
			        console.log(json.data);
	            	_form.html(template(json.data));
	            	$("#compScript").val(json.data.compScript)
	            	self.getParameterListById(cmd);
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
			$(Dom.updateComp).unbind('click');
			$(Dom.updateComp).bind('click', function() {
				var _data = self.getCheckedComp();
				var cmd = _data.compId;
				if(cmd){
					self.getCompinfo(cmd);
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
			Rose.ajax.getJson(srvMap.get('queryCompInfo'), cmd, function(json, status) {                    
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
		// 获取参数列表当前选中参数ID
		getCheckedParamRow : function(){
			var _obj = $(Dom.getParameterList).find("input[type='radio']:checked").parents("tr");
			var _id = _obj.find("input[name='paramId']")
			var data = {
		        paramId: _id.val()
		    }
		    if(_id.length==0){
		    	window.XMS.msgbox.show('请先选择一个组织结构！', 'info', 2000);
		    	return;
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


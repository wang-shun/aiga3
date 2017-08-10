define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesShenqingView');
	//分页根据条件查询功能点归属
	srvMap.add("getDataMaintainList", pathAlias + "dataMaintain.json", "sys/property/getPropertyCorrelationList");
	//新增备份
	srvMap.add("addDataMaintain", pathAlias + "retMessage.json", "sys/property/addPropertyCorrelation");
	//删除备份
	srvMap.add("delDataMaintain", pathAlias + "retMessage.json", "sys/property/delPropertyCorrelation");
	//修改备份
	srvMap.add("updateDataMaintain", pathAlias + "retMessage.json", "sys/property/updatePropertyCorrelation");
	//属性下拉菜单
	srvMap.add("getPropertyName", pathAlias + "retMessage.json", "sys/backup/getPropertyConfigList");
	//数据库下拉菜单
	srvMap.add("getDbList", pathAlias + "retMessage.json", "sys/property/getDbList");
	//cfgId下拉菜单

	srvMap.add("getCfgIdList", pathAlias + "retMessage.json", "sys/property/getCigIdList");

	srvMap.add("getPropertyConfigList", pathAlias + "propertyConfig.json", "sys/property/getPropertyFieldList");

	//问题展示
	srvMap.add("getQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/question/list");
	//新增问题
	srvMap.add("saveQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/save");
	//修改问题
	srvMap.add("updateQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/update");
	//刪除問題
	srvMap.add("deleQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/delete");
    //问题分类下拉框
    srvMap.add("getQuestypeList", "", "sys/cache/listQuestype");
    //一级分类下拉框
    srvMap.add("getFirstcategoryList", "", "sys/cache/listFirstcategory");
    //二级分类下拉框
    srvMap.add("getSecondcategoryList", "", "sys/cache/listSecondcategory");
    //三级分类下拉框
    srvMap.add("getThirdcategoryList", "", "sys/cache/listThirdcategory");
    //级联查询
    srvMap.add("getQueryQuesInfo", "", "archi/question/queryInfo");
    //所属系统静态数据  
	srvMap.add("getBelongSystem", "", "archi/third/list");
    //所属处理科室静态数据  
	srvMap.add("staticDealApartment", pathAlias+"getSysMessageList.json", "archi/static/archiDealApartment");
    //所属工单状态静态数据  
	srvMap.add("staticProductState", pathAlias+"getSysMessageList.json", "archi/static/archiProductState");
    //所属问题分类静态数据  
	srvMap.add("staticQuesCategory", pathAlias+"getSysMessageList.json", "archi/static/archiQuesCategory");
    //批量导入文件类型静态数据  
	srvMap.add("staticFileCategory", pathAlias+"getSysMessageList.json", "archi/static/archiFileCategory");
	//上传文件
    srvMap.add("uploadFile", pathAlias + "getDeliverablesList.json", "group/require/uploadFile");
	//一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
	//显示系统信息表
//	srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findByConditionPage");
    srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
 	//get id
    srvMap.add("getEventFindALL", pathAlias+"getSysMessageList.json", "archi/event/findAll");
    //get question sequence currval id
    srvMap.add("getCurrvalId", pathAlias+"getSysMessageList.json", "archi/question/getCurrvalId");
    //下载文档
    srvMap.add("downloadFile", pathAlias + "getDeliverablesList.json", "sys/changeplanonile/downloadFileBatch");
    //下载模板
    srvMap.add("downloadTemp", pathAlias + "getDeliverablesList.json", "sys/changeplanonile/downloadFile");
    //进程变更清单
    srvMap.add("uploadNaProcessChangeList", pathAlias + "getDeliverablesList.json", "produce/plan/uploadNaProcessChangeList");
    var cache = {
		datas : ""	
	};
	var idcache = {
		quesId : ""
	}
    // 模板对象
	var Tpl = {
		//getDataMaintainTemp: $('#JS_getDataMaintainTemp'),
		getQuestionInfoList: require('tpl/archiQuesManage/quesRequest.tpl')
		//modifyQuesIdentifiedInfo: $("#TPL_modifyQuesIdentifiedInfo").html()

	};

	/*// 容器对象
	var Dom = {
		queryDataMaintainForm: '#JS_queryDataMaintainForm',
		getDataMaintainList: '#JS_getDataMaintainList',
		addDataMaintainModal: "#JS_addDataMaintainModal",
		addDataMaintainInfo: "#JS_addDataMaintainInfo",
		updateDataMaintainModal: "#JS_updateDataMaintainModal",
		updateMaintainInfo: "#JS_updateDataMaintainInfo",
	};*/

	var Data = {
		queryListCmd: null
	};

	var Query = {
		init: function() {
			this.batchAdd();
			
			this.initialise();
			
//			var planId = '99999';
			
			this.searchBox();
			
			this.uploadAnNiu();
			
			this.judgeQuesType();
			// 默认查询所有
			this.getDataMaintainList();
			// 初始化查询表单
			this.queryDataMaintainForm();
			
			this._querydomain();
			//映射
			this.hdbarHelp();
		},
		batchAdd: function(){
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			var _batchBtn = _form.find("[name='batchAdd']");
			_batchBtn.unbind('click').bind('click', function() {
				var _modal = Page.findModal('addDdeliverablesModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);

				self.downloadTempShanxian();

			});
		},
		initialise: function(){
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
		},
		searchBox: function(){
			var self = this;
			var _dom = Page.findId('queryDataMaintainForm');
			var _checkBt = _dom.find("[name='checkbox']");

			_checkBt.unbind('click');
			_checkBt.bind('click', function() {
//				var template = Handlebars.compile(Page.findTpl('getSearchBoxTemp'));
//				Page.findId('insertBelongProj').html(template(data));
				var _modal = Page.findModal('addSearchBoxModal');
				_modal.modal('show');
//				Utils.setSelectData(_modal);
				var sureBt = _modal.find("[name='sure']");
				sureBt.unbind('click');
				sureBt.bind('click',function(){
					_modal.modal('hide');
//					var node = _modal.find("[name='sysMessageQuery']");
					var node = Page.findId('sysMessageQuery');
					var value = Utils.getRadioCheckedRow(node);
					var idThird = value.idThird;
					for(var i=0;i<cache.datas.length;i++){
						if(cache.datas[i].idThird==idThird){
							var nameValue = cache.datas[i].name;
							_dom.find("[name='belongProject']").val(nameValue);
							break;
						}
					};
//					var value = $("#sbbelongProject").find("option:selected").text();
//					var value = node.text();
					
				});
			});
		},
		//上传上线交付物按钮
        uploadAnNiu: function() {
        	
        	var _cmd = "";
			Rose.ajax.postJson(srvMap.get('getCurrvalId'),_cmd,function(json, status){
//				alert(json.data.quesId);
				if(status) {
					idcache.quesId=json.data.quesId;
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
			
        	var planId = idcache.quesId + 1;
            var self = this;
            var _form = Page.findModalCId('queryDataMaintainForm');
            console.log(_form.length);
	        Utils.checkForm(Page.findId('queryDataMaintainForm'),function(){
	    		var a = '99999';
	            var cmd = {
	                "file": _form.find("[name='fileName']")[0].files[0],
	                "planId": planId,
	                "fileType": a
	            };
	            console.log(_form.find("[name='fileName']"));
	            console.log(a);
	                var task = srvMap.get('uploadFile');
	                self.jieko(task, cmd, planId);
	        });
        },
        //上传批量上线交付物按钮
        uploadAnNiuBatch: function(planId) {
            var self = this;
            var _form = Page.findModalCId('addDdeliverablesForm');
            console.log(_form.length)
            var _saveBtn = _form.find("[name='importFile']");
            _saveBtn.unbind('click');
            _saveBtn.bind('click', function() {
            	Utils.checkForm(Page.findId('addDdeliverablesForm'),function(){
	                var a = _form.find("[name='fileName']").val();
	                console.log(a);
	                var cmd = {
	                    "file": _form.find("[name='fileName']")[0].files[0],
	                    "planId": planId,
	                    "fileType": a,
	                }
	                console.log(_form.find("[name='fileName']"));
	                console.log(a);
	                switch (a) {
	                    case "99": //架构疑难问题申报表
	                        var task = srvMap.get('Path');
	                        self.jieko(task, cmd, planId)
	                        break;
	                    case "104": //架构疑难问题申报表
	                        var task = srvMap.get('uploadNaProcessChangeList');
	                        self.jieko(task, cmd, planId)
	                        break;
	                }
	            });
            });
        },
        jieko: function(task, cmd, planId) {
            var self = this;
            $.ajaxUpload({
                url: task,
                data: cmd,
                success: function(date, status, xhr) {
                    console.log(date);
                    if (date.retCode==200) {
                        //window.XMS.msgbox.show('上传成功！', 'success', 2000);
   						return true;
                    }else{
                        window.XMS.msgbox.show(date.retMessage, 'error', 2000);
                        return false;
                    }
                }
            });
        },
		//判断下拉框quesTypez值
		judgeQuesType: function(){
			$("#quesType").unbind('click');
			$("#quesType").bind('click',function(){
				var checkValue=$("#quesType").val();  //获取Select选择的Value 
				if(checkValue=="2" || checkValue=="3"){
					$("#firstCategorydiv").hide();     
					$("#secondCategorydiv").hide();          
					$("#thirdCategorydiv").hide();           
					$("#defectLeveldiv").attr({style:"display:display"});
				}else if(checkValue=="1"){
					$("#firstCategorydiv").show();   
					$("#secondCategorydiv").show();          
					$("#thirdCategorydiv").show();   
					$("#defectLeveldiv").attr({style:"display:none"});
				}
			});
		},
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.unbind('click').bind('click', function() {
				planId = idcache.quesId + 1;
				var upState = false;
		        Utils.checkForm(Page.findId('queryDataMaintainForm'),function(){
		    		var a = '99999';

		            var cmd = {
		                "file": _form.find("[name='fileName']")[0].files[0],
		                "planId": planId,
		                "fileType": a
		            };
		            
		            if(cmd.file) {
		    			upState = true;
		    			var task = srvMap.get('uploadFile');
	                	if(!self.jieko(task, cmd, planId)){
	                		return;
	                	}  
		    		}        
		        });
//				self.getDataMaintainList(cmd);
				Utils.checkForm(_form, function() {
					var _cmd = _form.serialize();
					_cmd=_cmd.replace(/-/g,"/");
					//XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					console.log(_cmd);
					
					if(_cmd.indexOf('occurEnvironment=&')>-1) {
						XMS.msgbox.show('标题为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('abstracts=&')>-1) {
						XMS.msgbox.show('问题描述为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('quesType=&')>-1) {
						XMS.msgbox.show('问题分类为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('quesType=1')>-1){
						if(_cmd.indexOf('firstCategory=&')>-1) {
							XMS.msgbox.show('一级分类为空！', 'error', 2000);
							return
						}
						if(_cmd.indexOf('secondCategory=&')>-1) {
							XMS.msgbox.show('二级分类为空！', 'error', 2000);
							return
						}
						if(_cmd.indexOf('thirdCategory=&')>-1) {
							XMS.msgbox.show('三级分类为空！', 'error', 2000);
							return
						}
					}
					if(_cmd.indexOf('priority=&')>-1) {
						XMS.msgbox.show('优先级为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('belongProject=&')>-1) {
						XMS.msgbox.show('所属系统为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('modifyDate=&')>-1) {
						XMS.msgbox.show('期望时间为空！', 'error', 2000);
						return
					}
					/*
					  if(_cmd.indexOf('appointedPerson=&')>-1) {
						XMS.msgbox.show('处理科室为空！', 'error', 2000);
						return
					}
					 */
					if(_cmd.indexOf('appointedPerson=&')>-1) {
						XMS.msgbox.show('处理科室为空！', 'error', 2000);
						return
					}
					if(_cmd.charAt(_cmd.length-1)=='=') {
						XMS.msgbox.show('请选择所属巡检事件', 'error', 2000);
						return
					}

					if(_cmd!=null){
						if(_cmd.indexOf('idFirst=&')>-1){
							_cmd=_cmd.replace("idFirst=&","idFirst=0&");
						}
					}
					Rose.ajax.postJson(srvMap.get('saveQuestionInfo'), _cmd, function(json, status) {
						if (status) {
							// 数据备份成功后，刷新用户列表页
							if(upState) {
								XMS.msgbox.show('文件上传完毕，申报成功！', 'success', 2000);
							} else {
								XMS.msgbox.show('申报成功！', 'success', 2000);
							}
//							XMS.msgbox.show('添加成功！', 'success', 2000);

//							alert("恭喜，申报成功！");
							setTimeout(function() {
								self.getDataMaintainList();
							}, 1000);
							Page.findId('queryDataMaintainForm')[0].reset();
//							Page.findId('queryDataMaintainForm').hide();
//							// 关闭弹出层
//							Page.findModal('addDataMaintainModal').modal('hide');
							
						}
					});
				});
				
				
				
			});

		},
		// 查询数据维护
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');

			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getQuestionInfoList'), _cmd, function(json, status) {//getQuestionInfoList
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getQuestionInfoList);
				_dom.find("[name='content']").html(template(json.data));
				//美化单机
				Utils.eventTrClickCallback(_dom);
				//新增
				self.addDataMaintain();
				//删除
				self.delDataMaintain();
				//双击修改
				self.eventDClickCallback(_dom, function() {
					//获得当前单选框值
					var data = Utils.getRadioCheckedRow(_dom);

//					alert(data.quesId);
					self.updateDataMaintain(data.quesId, json.data);
				});
			}, _domPagination);
		},
		//新增数据维护
		addDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _addBt = _dom.find("[name='add']");

			_addBt.unbind('click');
			_addBt.bind('click', function() {
				//alert(Page.findModal('addDataMaintainModal').html());
				Page.findModal('addDataMaintainModal').modal('show');
				Page.findModal('addDataMaintainModal').on('hide.bs.modal', function() {
					Utils.resetForm(Page.findId('addDataMaintainInfo'));
				});
				var _form = Page.findId('addDataMaintainInfo');
				Utils.setSelectData(_form);
				var _saveBt = Page.findModal('addDataMaintainModal').find("[name = 'save']");
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var _cmd = _form.serialize();
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						console.log(_cmd);
						Rose.ajax.postJson(srvMap.get('saveQuestionInfo'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000);
								setTimeout(function() {
									self.getDataMaintainList();
								}, 1000);
								// 关闭弹出层
								Page.findModal('addDataMaintainModal').modal('hide');
							}
						});
					});
				});

			});

		},
		//删除数据备份
		delDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'quesId=' + data.quesId;
					//alert(cmd);//////////
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('deleQuestionInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self.queryDataMaintainForm(Data.queryListCmd);
							}, 1000);
						}
					});
				}
			});
		},
		updateDataMaintain: function(Id, json) {
		
			var self = this;
			var i=0;
			while(json[i].quesId != Id){
				i++;
			}
			var data = json[i];
//			var _dom = Page.findModal('updateDataMaintainModal');
			
//			var index = _dom.attr("temp");
//			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
//			Page.findId('updateModal').html(template(json.data[index]));
//			var _modal = Page.findId('updateDataMaintainModal');
//			_modal.modal('show');
//			Utils.setSelectData(_modal);
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
			
//			_dom.modal('show');
			var html = "<input readonly='readonly' type='text' class='form-control' value='" + Id + "' />";
			_dom.find("#JS_name").html(html);

			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Page.findId('updateDataMaintainInfo');
				Utils.setSelectData(_form);
				var _cmd = _form.serialize();
				_cmd = _cmd + "&quesId=" + Id;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.getJson(srvMap.get('updateQuestionInfo'), _cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('更新成功！', 'success', 2000);
						setTimeout(function() {
							self.queryDataMaintainForm(Data.queryListCmd);
							_dom.modal('hide');
						}, 1000);
					}
				});
			});

		},
		/*--------------------------------------------------*/
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				//用于解决long型不可空传的问题
				if(cmd.charAt(cmd.length - 1) == '=') {
					cmd+='0';
				}
				self._getTableDataList(cmd);
			});
		},

		// 查询表格数据
		_getTableDataList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd) {
				var _cmd = cmd;
			}
			var _dom = Page.findId('sysMessageQuery');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getSysMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
		},
		/*--------------------------------------------------*/

		//// 上线交付物下载模板
        downloadTempShanxian: function() {
            var self = this;
            var _form = Page.findId('uploadDeliveryForm');
            var _downloadTemp = _form.find("[name='downloadTemp']");
            _downloadTemp.unbind();
            _downloadTemp.click(function() {
            	var a = _form.find("[name='fileName']").val();
            	var cmd = "fileName=";
                if (a) {
                	switch (a) {
                		    case "99": //架构疑难问题申报表
    	                        var cmd = cmd+"架构疑难问题申报表.xlsx";
    	                        break;
    	                    case "104": //架构疑难问题申报表
    	                        var cmd = cmd+"架构疑难问题申报表.xlsx";
    	                        break;
    	                }
    	                cmd = cmd + "&id=77"
                        $(this).attr("href", srvMap.get('downloadTemp')+"?"+cmd);
                        return true;
                }else{
                    window.XMS.msgbox.show('请选择要下载文件类型！', 'error', 2000)
                    return false;
                }
            });
        },
		
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tbody tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		},
		//映射处理
		hdbarHelp: function() {
			Handlebars.registerHelper("transformatRoot", function(value) {
                if (value == '1') {
                    return "技术巡检";
                } else if (value == '2') {
                    return "系统巡检";
                } else if (value == '3') {
                    return "疑难问题";
                }
            });
			Handlebars.registerHelper("transformatFirst", function(value) {
                if (value == '1001') {
                    return "容量规划";
                } else if (value == '1002') {
                    return "高可用";
                } else if (value == '1003') {
                    return "分层";
                } else if (value == '1004') {
                	return "柔性可用";
                } else if (value == '1005') {
                    return "日志";
                } else if (value == '1006') {
                    return "配置";
                } else if (value == '1007') {
                    return "监控";
                } else if (value == '1008') {
                    return "安全";
                }
            });
			Handlebars.registerHelper("transformatSecond", function(value) {
                if (value == '2001') {
                    return "系统容量";
                } else if (value == '2002') {
                    return "业务容量";
                } else if (value == '2003') {
                    return "系统性能";
                } else if (value == '2004') {
                    return "系统级别";
                } else if (value == '2005') {
                    return "容灾等级";
                } else if (value == '2006') {
                    return "演练结果";
                } else if (value == '2007') {
                    return "核心进程主备";
                } else if (value == '2008') {
                    return "IaaS";
                } else if (value == '2009') {
                    return "PaaS";
                } else if (value == '2010') {
                    return "SaaS";
                } else if (value == '2011') {
                    return "交互应用";
                } else if (value == '2012') {
                    return "数据访问";
                } else if (value == '2013') {
                    return "业务设计";
                } else if (value == '2014') {
                    return "服务设计";
                } else if (value == '2015') {
                    return "其他柔性手段";
                } else if (value == '2016') {
                    return "完整性";
                } else if (value == '2017') {
                    return "便捷性";
                } else if (value == '2018') {
                    return "合规性";
                } else if (value == '2019') {
                    return "集中配置";
                } else if (value == '2020') {
                    return "配置刷新";
                } else if (value == '2021') {
                    return "稽核手段";
                } else if (value == '2022') {
                    return "采集方式";
                } else if (value == '2023') {
                    return "端到端监控";
                } else if (value == '2024') {
                    return "数据一致性稽核";
                } else if (value == '2025') {
                    return "单笔业务跟踪";
                } else if (value == '2026') {
                    return "账号密码";
                } else if (value == '2027') {
                    return "4A系统对接";
                } else if (value == '2028') {
                    return "权限管控粒度";
                }
            });
			Handlebars.registerHelper("transformatThird", function(value) {
                if (value == '3001') {
                    return "数据库容量";
                } else if (value == '3002') {
                    return "网络带宽";
                } else if (value == '3003') {
                    return "TPCC";
                } else if (value == '3004') {
                    return "存储";
                } else if (value == '3005') {
                    return "工单并发量";
                } else if (value == '3006') {
                    return "移动号码新入网业务";
                } else if (value == '3007') {
                    return "合约业务";
                } else if (value == '3008') {
                    return "移动宽带新入网业务";
                } else if (value == '3009') {
                    return "亲情网和流量包办理";
                } else if (value == '3010') {
                    return "用户量";
                } else if (value == '3011') {
                    return "主机运营指标";
                } else if (value == '3012') {
                    return "业务并发量";
                } else if (value == '3013') {
                    return "CPU";
                } else if (value == '3014') {
                    return "内存";
                } else if (value == '3015') {
                    return "系统级别";
                } else if (value == '3016') {
                    return "容灾等级";
                } else if (value == '3017') {
                    return "演练结果";
                } else if (value == '3018') {
                    return "核心进程主备";
                } else if (value == '3019') {
                    return "硬件标准化";
                } else if (value == '3020') {
                    return "软件定义化";
                } else if (value == '3021') {
                    return "组件标准化";
                } else if (value == '3022') {
                    return "DCOS化";
                } else if (value == '3023') {
                    return "服务统一接入管控";
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
				"autoWidth": false
			});
		}
	};
	module.exports = Query;
});
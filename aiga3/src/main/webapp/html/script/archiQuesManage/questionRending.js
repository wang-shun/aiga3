define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('questionRendingView');
	//问题展示
	srvMap.add("getInspectQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/inspect/list");
	//新增问题
	srvMap.add("saveInspectQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/inspect/save");
	//修改问题
	srvMap.add("updateQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/update")
	//刪除問題
	srvMap.add("deleQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/delete");
    //问题分类下拉框
    srvMap.add("getRootList", "", "sys/cache/listRootid");
    //一级分类下拉框
    srvMap.add("getFirstList", "", "sys/cache/listFirstid");
    //二级分类下拉框
    srvMap.add("getSecondList", "", "sys/cache/listSecondid");
    //三级分类下拉框
    srvMap.add("getThirdList", "", "sys/cache/listThirdid");
    //级联查询
    srvMap.add("getInspectQueryQuesInfo", "", "archi/inspect/queryInfo");
        //问题分类下拉框
    srvMap.add("getQuestypeList", "", "sys/cache/listQuestype");
    //一级分类下拉框
    srvMap.add("getFirstcategoryList", "", "sys/cache/listFirstcategory");
    //二级分类下拉框
    srvMap.add("getSecondcategoryList", "", "sys/cache/listSecondcategory");
    //三级分类下拉框
    srvMap.add("getThirdcategoryList", "", "sys/cache/listThirdcategory");
    //所属工单状态静态数据  
	srvMap.add("staticProductState", pathAlias+"getSysMessageList.json", "archi/static/archiProductState");
    //所属问题状态静态数据  
	srvMap.add("staticQuestionState", pathAlias+"getSysMessageList.json", "archi/static/archiQuestionState");
	//显示系统信息表
    srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
	//get附件名
    srvMap.add("getFileName", pathAlias+"getSysMessageList.json", "archi/question/getFileName");
	//get附件名update
    srvMap.add("findByPlanIdAndFileType", pathAlias+"getSysMessageList.json", "archi/question/findByPlanIdAndFileType");
    //下载文档
    srvMap.add("downloadFile", pathAlias + "getDeliverablesList.json", "sys/changeplanonile/downloadFileBatch");
    //下载模板
    srvMap.add("downloadTemp", pathAlias + "getDeliverablesList.json", "sys/changeplanonile/downloadFile");
    //事件单ID
    srvMap.add("getEventFindALL", pathAlias+"getSysMessageList.json", "archi/event/findAll");
    //所属处理科室静态数据  
	srvMap.add("staticDealApartment", pathAlias+"getSysMessageList.json", "archi/static/archiDealApartment");
    //角色校验
    srvMap.add("questionRoleCheck", pathAlias + "getDeliverablesList.json", "archi/question/roleCheck");

    // 模板对象
	var Tpl = {
		getQuestionInfoList: require('tpl/archiQuesManage/quesTemplate.tpl')
	};
	var cache = {
		datas : '',
		selectData : '',
		role : ''

	};
	var idcache = {
		quesId : '',
		fileName : '',
		datas : ''
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
	}

	var Query = {
		init: function() {
			this._role_check();
			
			this.jumpPage();
			
			this.searchBox();
			
			this._querydomain();
			// 默认查询所有
//			this.getDataMaintainList();
			// 初始化查询表单
			this.queryDataMaintainForm();
			//下载文档
//			this.downloadFile(idcache.quesId);
			//映射
			this.hdbarHelp();
		},
		//角色校验 
		_role_check: function() {
			Rose.ajax.postJson(srvMap.get('questionRoleCheck'),'',function(json, status){
				if(status) {							
					cache.role = json.data.roles;
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});
		},
		
		jumpPage : function(){
			var syscmd = Page.getParentCmd();
			var result = Utils.jsonToUrl(syscmd);
			if(result!=null){
				var self = this;
				var _form = Page.findId('queryDataMaintainForm');
				Utils.setSelectData(_form);
				var _queryBtn = _form.find("[name='query']");
				_queryBtn.unbind('click').bind('click', function() {
					var cmd = result;
					self.getDataMaintainList(cmd);
				});
				_queryBtn.click();
				
			}
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
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.unbind('click').bind('click', function() {
				var cmd = _form.serialize();
				self.getDataMaintainList(cmd);
			});

		},
		// 查询数据维护
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = cmd;
//			_cmd=_cmd.replace(/-/g,"/");
			if(_cmd!=null){
				if(_cmd.indexOf('quesId=&')>-1){
					_cmd=_cmd.replace("quesId=&","quesId=0&");
				}
				if(_cmd.indexOf('idFirst=&')>-1){
					_cmd=_cmd.replace("idFirst=&","idFirst=0&");
				}
			}
//			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');

			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页getQueryQuesInfo
			Utils.getServerPage(srvMap.get('getInspectQueryQuesInfo'), _cmd, function(json, status) {//getQuestionInfoList
				cache.datas = json.data.content;
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getQuestionInfoList);
				_dom.find("[name='content']").html(template(json.data.content));//
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
						Rose.ajax.postJson(srvMap.get('saveInspectQuestionInfo'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
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
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.queryDataMaintainForm(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		updateDataMaintain: function(Id, json) {
		
			var self = this;
			var i=0;
			while(json.content[i].quesId != Id){
				i++;
			}
			var data = json.content[i];
			idcache.quesId=Id;
			if(data.modifyDate){
				data.modifyDate = data.modifyDate.replace(/-/g,"/");
				data.createDate = data.modifyDate;
			}
			if(cache.role){
				console.log(cache.role);
				console.log(cache.role.charAt('SYS_QUESTION_CONFIRM'));
				console.log(cache.role.charAt('ROLE')>-1);
			}

			if(cache.role == 'SYS_QUESTION_QRY'){
				$("#archiXiugai").attr({style:"display:display"});
			}else if(cache.role == 'SYS_QUESTION_CONFIRM'){
				$("#archiIdentify").attr({style:"display:display"});
			}else if(cache.role == 'SYS_QUESTION_SOLVED'){
				$("#archiJiejue").attr({style:"display:display"});
			}else if(cache.role == 'ROLE'){
				$("#archiXiugai").attr({style:"display:display"});
				$("#archiIdentify").attr({style:"display:display"});
				$("#archiJiejue").attr({style:"display:display"});
			}
//			var cmd = 'quesId=' + Id;
//			Rose.ajax.postJsonSync(srvMap.get('getFileName'), cmd,function(json2, status){
			var cmd = 'planId=' + Id + '&fileType=99999' 
			Rose.ajax.postJsonSync(srvMap.get('findByPlanIdAndFileType'), cmd,function(json2, status){
				if(status) {
//					idcache.quesId=json.data.quesId;
					if(json2.data!=null){
						idcache.fileName=json2.data.fileName;
						idcache.datas=json2.data;
					}
				} else {
					XMS.msgbox.show(json2.retMessage, 'error', 2000);
				}					
			});
			if(idcache.fileName){
				data.fileName = idcache.fileName;
			}else{
				data.fileName = "无";
			}
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
			
        	_dom.off('shown.bs.modal').on('shown.bs.modal', function () {
				self.downloadFile(idcache.quesId);
			});
			
			
//			_dom.modal('show');
			var html = "<input readonly='readonly' type='text' class='form-control' value='" + Id + "' />";
			_dom.find("#JS_name").html(html);

			var _identify = _dom.find("[name='identify']");
			_identify.unbind('click');
			_identify.bind('click', function() {
				Page.findId('modalMessage').val("");
				Page.findId('identifiedName').val("");
				var textModal = Page.findId('modal');
				textModal.off('shown.bs.modal').on('shown.bs.modal', function () {
//					var data = cache.selectData;
//					data.modifyDate = data.modifyDate.replace(/-/g,"/");
//					data.createDate = data.createDate.replace(/-/g,"/");
//					data.applyTime = data.applyTime.replace(/-/g,"/");
					//通过
					textModal.find("[name='pass']").off('click').on('click', function(){
						data.sysVersion = '已确认';
						data.state ="未解决";
						data.identifiedInfo = Page.findId('modalMessage').val();
						data.identifiedName = Page.findId('identifiedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {							
								textModal.modal('hide');
								_dom.modal('hide');
								Page.findId('sysMessageFrom').modal('hide');
								XMS.msgbox.show('认定成功！通过！！！', 'success', 2000);	
								setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//不通过
					textModal.find("[name='noPass']").off('click').on('click', function(){
						data.sysVersion = '已否决';
						data.identifiedInfo = Page.findId('modalMessage').val();
						data.identifiedName = Page.findId('identifiedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {
								textModal.modal('hide');
								_dom.modal('hide');
								Page.findId('sysMessageFrom').modal('hide');
								XMS.msgbox.show('认定成功！ 不通过！！！', 'success', 2000);	
								setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);								
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
				});
				textModal.modal('show');
				
/*				var _form = Page.findId('updateDataMaintainInfo');
				Utils.setSelectData(_form);
				var _cmd = _form.serialize();
				_cmd = _cmd + "&quesId=" + Id;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.getJson(srvMap.get('updateQuestionInfo'), _cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('更新成功！', 'success', 2000)
						setTimeout(function() {
							self.queryDataMaintainForm(Data.queryListCmd);
							_dom.modal('hide');
						}, 1000)
					}
				});*/
			});
			
			var _xiugai = _dom.find("[name='xiugai']");
			_xiugai.unbind('click');
			_xiugai.bind('click', function() {
				var _form = Page.findId('updateDataMaintainInfo');
				Utils.setSelectData(_form);
				var _cmd = _form.serialize();
				_cmd=_cmd.replace(/-/g,"/");
				_cmd = _cmd + "&id=" + Id;
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
					if(status) {							
						_dom.modal('hide');
						XMS.msgbox.show('问题修改成功！！！', 'success', 5000);	
					setTimeout(function() {
							Page.findId('queryDataMaintainForm').find("[name='query']").click();
						}, 1500);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
			
			});	
			
			var _jiejue = _dom.find("[name='jiejue']");
			_jiejue.unbind('click');
			_jiejue.bind('click', function() {				
				Page.findId('solveModalMessage').val("");
				Page.findId('solvedName').val("");
				var solveTextModal = Page.findId('solveModal');
				solveTextModal.off('shown.bs.modal').on('shown.bs.modal', function () {
//					var data = cache.selectData;
//					data.modifyDate = data.modifyDate.replace(/-/g,"/");
//					data.createDate = data.createDate.replace(/-/g,"/");
//					data.applyTime = data.applyTime.replace(/-/g,"/");
					//开需求单
					var _openRequest = solveTextModal.find("[name='openRequest']");
					_openRequest.unbind('click');
					_openRequest.bind('click', function() {
						data.state ="需求单跟踪";
						data.solvedInfo = Page.findId('solveModalMessage').val();
						data.solvedName = Page.findId('solvedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {
								solveTextModal.modal('hide');
								_dom.modal('hide');
								XMS.msgbox.show('开需求单成功！！！', 'success', 5000);	
								window.open("http://apc.zj.chinamobile.com/");  
							setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//开任务单
					var _openTask = solveTextModal.find("[name='openTask']");
					_openTask.unbind('click');
					_openTask.bind('click',function(){
						data.state ="任务单跟踪";
						data.solvedInfo = Page.findId('solveModalMessage').val();
						data.solvedName = Page.findId('solvedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {	
								solveTextModal.modal('hide');
								_dom.modal('hide');
								XMS.msgbox.show('开任务单成功！！！', 'success', 5000);	
								window.open("http://apc.zj.chinamobile.com/");  
								setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//开变更单
					var _openUpdate = solveTextModal.find("[name='openUpdate']");
					_openUpdate.unbind('click');
					_openUpdate.bind('click',function(){
						data.state ="变更单跟踪";
						data.solvedInfo = Page.findId('solveModalMessage').val();
						data.solvedName = Page.findId('solvedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {	
								solveTextModal.modal('hide');
								_dom.modal('hide');
								XMS.msgbox.show('开变更单成功！！！', 'success', 5000);	
								window.open("http://apc.zj.chinamobile.com/"); 
								setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//后续立项解决
					var _afterSolved = solveTextModal.find("[name='afterSolved']");
					_afterSolved.unbind('click');
					_afterSolved.bind('click',function(){
						data.state ="待立项规划";
						data.solvedInfo = Page.findId('solveModalMessage').val();
						data.solvedName = Page.findId('solvedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {
								solveTextModal.modal('hide');
								_dom.modal('hide');
								XMS.msgbox.show('后续立项解决成功！！！', 'success', 5000);	
								window.open("http://apc.zj.chinamobile.com/"); 
								setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//关单
					var _closeAll = solveTextModal.find("[name='closeAll']");
					_closeAll.unbind('click');
					_closeAll.bind('click',function(){
						data.state ="已解决";
						data.solvedInfo = Page.findId('solveModalMessage').val();
						data.solvedName = Page.findId('solvedName').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('updateQuestionInfo'),_cmd,function(json, status){
							if(status) {
								solveTextModal.modal('hide');
								_dom.modal('hide');
								XMS.msgbox.show('问题解决成功！！！', 'success', 5000);	
								setTimeout(function() {
									Page.findId('queryDataMaintainForm').find("[name='query']").click();
								}, 1500);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
				});
			solveTextModal.modal('show');
			});	

		},
        //上线交付物下载文档
        downloadFile: function(planId) {
            var self = this;
            var _dom = Page.findId('updateDataMaintainInfo');
            var _deleteDocument = _dom.find("[name='add']");
            _deleteDocument.unbind('click');
            _deleteDocument.bind('click', function() {
//            	alert("aaaaaaaaaaaaa");
//            	var _data = Utils.getCheckboxCheckedRow(_dom);
            	var _data = idcache.datas;
                if (_data) {
                	XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                	var cmd = "ids=";
//                    for (var i in _data) {
//                        cmd += _data.id + ',';
//                    }
                    cmd += _data.id + ',';
                	
                    cmd = cmd.substring(0, cmd.length - 1);
                    var downloadurl = srvMap.get('downloadFile')+"?"+cmd;
                	_deleteDocument.attr("href", downloadurl.toString());
                    setTimeout(function() {
//                        self.uploadDeliverables(planId);
                    }, 1000) 
                }else{
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
                if (value == '1' || value == '技术巡检') {
                    return "技术巡检";
                } else if (value == '2' || value == '系统巡检') {
                    return "系统巡检";
                } else if (value == '3' || value == '疑难问题') {
                    return "疑难问题";
                }
            });
			Handlebars.registerHelper("transformatFirst", function(value) {
                if (value == '1001' || value == '容量规划') {
                    return "容量规划";
                } else if (value == '-' || value == '无'){
                	return "无";
                } else if (value == '1002' || value == '高可用') {
                    return "高可用";
                } else if (value == '1003' || value == '分层') {
                    return "分层";
                } else if (value == '1004' || value == '柔性可用') {
                	return "柔性可用";
                } else if (value == '1005' || value == '日志') {
                    return "日志";
                } else if (value == '1006' || value == '配置') {
                    return "配置";
                } else if (value == '1007' || value == '监控') {
                    return "监控";
                } else if (value == '1008' || value == '安全') {
                    return "安全";
                }
            });
			Handlebars.registerHelper("transformatSecond", function(value) {
                if (value == '2001' || value == '系统容量') {
                    return "系统容量";
                } else if (value == '-' || value == '无'){
                	return "无";
                } else if (value == '2002' || value == '业务容量') {
                    return "业务容量";
                } else if (value == '2003' || value == '系统性能') {
                    return "系统性能";
                } else if (value == '2004' || value == '系统级别') {
                    return "系统级别";
                } else if (value == '2005' || value == '容灾等级') {
                    return "容灾等级";
                } else if (value == '2006' || value == '演练结果') {
                    return "演练结果";
                } else if (value == '2007' || value == '核心进程主备') {
                    return "核心进程主备";
                } else if (value == '2008' || value == 'IaaS') {
                    return "IaaS";
                } else if (value == '2009' || value == 'PaaS') {
                    return "PaaS";
                } else if (value == '2010' || value == 'SaaS') {
                    return "SaaS";
                } else if (value == '2011' || value == '交互应用') {
                    return "交互应用";
                } else if (value == '2012' || value == '数据访问') {
                    return "数据访问";
                } else if (value == '2013' || value == '业务设计') {
                    return "业务设计";
                } else if (value == '2014' || value == '服务设计') {
                    return "服务设计";
                } else if (value == '2015' || value == '其他柔性手段') {
                    return "其他柔性手段";
                } else if (value == '2016' || value == '完整性') {
                    return "完整性";
                } else if (value == '2017' || value == '便捷性') {
                    return "便捷性";
                } else if (value == '2018' || value == '合规性') {
                    return "合规性";
                } else if (value == '2019' || value == '集中配置') {
                    return "集中配置";
                } else if (value == '2020' || value == '配置刷新') {
                    return "配置刷新";
                } else if (value == '2021' || value == '稽核手段') {
                    return "稽核手段";
                } else if (value == '2022' || value == '采集方式') {
                    return "采集方式";
                } else if (value == '2023' || value == '端到端监控') {
                    return "端到端监控";
                } else if (value == '2024' || value == '数据一致性稽核') {
                    return "数据一致性稽核";
                } else if (value == '2025' || value == '单笔业务跟踪') {
                    return "单笔业务跟踪";
                } else if (value == '2026' || value == '账号密码') {
                    return "账号密码";
                } else if (value == '2027' || value == '4A系统对接') {
                    return "4A系统对接";
                } else if (value == '2028' || value == '权限管控粒度') {
                    return "权限管控粒度";
                }
            });
			Handlebars.registerHelper("transformatThird", function(value) {
                if (value == '3001' || value == '数据库容量') {
                    return "数据库容量";
                } else if (value == '-' || value == '无'){
                	return "无";
                } else if (value == '3002' || value == '网络带宽') {
                    return "网络带宽";
                } else if (value == '3003' || value == 'TPCC') {
                    return "TPCC";
                } else if (value == '3004' || value == '存储') {
                    return "存储";
                } else if (value == '3005' || value == '工单并发量') {
                    return "工单并发量";
                } else if (value == '3006' || value == '移动号码新入网业务') {
                    return "移动号码新入网业务";
                } else if (value == '3007' || value == '合约业务') {
                    return "合约业务";
                } else if (value == '3008' || value == '移动宽带新入网业务') {
                    return "移动宽带新入网业务";
                } else if (value == '3009' || value == '亲情网和流量包办理') {
                    return "亲情网和流量包办理";
                } else if (value == '3010' || value == '用户量') {
                    return "用户量";
                } else if (value == '3011' || value == '主机运营指标') {
                    return "主机运营指标";
                } else if (value == '3012' || value == '业务并发量') {
                    return "业务并发量";
                } else if (value == '3013' || value == 'CPU') {
                    return "CPU";
                } else if (value == '3014' || value == '内存') {
                    return "内存";
                } else if (value == '3015' || value == '系统级别') {
                    return "系统级别";
                } else if (value == '3016' || value == '容灾等级') {
                    return "容灾等级";
                } else if (value == '3017' || value == '演练结果') {
                    return "演练结果";
                } else if (value == '3018' || value == '核心进程主备') {
                    return "核心进程主备";
                } else if (value == '3019' || value == '硬件标准化') {
                    return "硬件标准化";
                } else if (value == '3020' || value == '软件定义化') {
                    return "软件定义化";
                } else if (value == '3021' || value == '组件标准化') {
                    return "组件标准化";
                } else if (value == '3022' || value == 'DCOS化') {
                    return "DCOS化";
                } else if (value == '3023' || value == '服务统一接入管控') {
                    return "服务统一接入管控";
                } else if (value == '3024' || value == '应用无状态化') {
                    return "应用无状态化";
                } else if (value == '3025' || value == '提供标准接口') {
                    return "提供标准接口";
                } else if (value == '3026' || value == '会话无状态化') {
                    return "会话无状态化";
                } else if (value == '3027' || value == '读写分离') {
                    return "读写分离";
                } else if (value == '3028' || value == '数据缓存') {
                    return "数据缓存";
                } else if (value == '3029' || value == '数据库异常隔离') {
                    return "数据库异常隔离";
                } else if (value == '3030' || value == '业务流程不依赖非关键业务') {
                    return "业务流程不依赖非关键业务";
                } else if (value == '3031' || value == '业务服务不依赖非关键原子') {
                    return "业务服务不依赖非关键原子";
                } else if (value == '3032' || value == '服务隔离') {
                    return "服务隔离";
                } else if (value == '3033' || value == '服务降级') {
                    return "服务降级";
                } else if (value == '3034' || value == '快速失败') {
                    return "快速失败";
                } else if (value == '3035' || value == '熔断') {
                    return "熔断";
                } else if (value == '3036' || value == '分中心隔离') {
                    return "分中心隔离";
                } else if (value == '3037' || value == '弹性扩展') {
                    return "弹性扩展";
                } else if (value == '3038' || value == '服务集成') {
                    return "服务集成";
                } else if (value == '3039' || value == '核心系统/非核心系统交互屏蔽') {
                    return "核心系统/非核心系统交互屏蔽";
                } else if (value == '3040' || value == '日志输出是否完整') {
                    return "日志输出是否完整";
                } else if (value == '3041' || value == '日志查询方式') {
                    return "日志查询方式";
                } else if (value == '3042' || value == '传送大数据平台') {
                    return "传送大数据平台";
                } else if (value == '3043' || value == 'BOMC日志分析') {
                    return "BOMC日志分析";
                } else if (value == '3044' || value == '基础参数') {
                    return "基础参数";
                } else if (value == '3045' || value == '业务规则') {
                    return "业务规则";
                } else if (value == '3046' || value == '刷新时间') {
                    return "刷新时间";
                } else if (value == '3047' || value == '刷新方式') {
                    return "刷新方式";
                } else if (value == '3048' || value == '业务影响') {
                    return "业务影响";
                } else if (value == '3049' || value == '稽核手段') {
                    return "稽核手段";
                } else if (value == '3050' || value == '对接BOMC系统') {
                    return "对接BOMC系统";
                } else if (value == '3051' || value == '统一采集') {
                    return "统一采集";
                } else if (value == '3052' || value == '唯一流水号') {
                    return "唯一流水号";
                } else if (value == '3053' || value == '监控工具') {
                    return "监控工具";
                } else if (value == '3054' || value == '数据一致性稽核') {
                    return "数据一致性稽核";
                } else if (value == '3055' || value == '单笔业务跟踪') {
                    return "单笔业务跟踪";
                } else if (value == '3056' || value == '加密存储') {
                    return "加密存储";
                } else if (value == '3057' || value == '定期修改且在线生效') {
                    return "定期修改且在线生效";
                } else if (value == '3058' || value == '系统工号') {
                    return "系统工号";
                } else if (value == '3059' || value == '操作员工号') {
                    return "操作员工号";
                } else if (value == '3060' || value == '权限管控粒度') {
                    return "权限管控粒度";
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
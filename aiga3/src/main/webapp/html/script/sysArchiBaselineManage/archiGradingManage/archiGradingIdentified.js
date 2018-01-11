define(function(require, exports, module) {
	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiGradingIdentified');	
	
	var cache = {
		datas : '',
		selectData : '',
		roleCheck : false
	};
	//显示认定信息表
	srvMap.add("getSysGradingMessageList", pathAlias+"getSysMessageList.json", "webservice/archiGrading/findByConditionPage");
	//信息认定
	srvMap.add("MessageGranding", pathAlias+"getSysMessageList.json", "webservice/archiGrading/messageGranding");
	//数据翻译
	srvMap.add("MessageTranslate", pathAlias+"getSysMessageList.json", "webservice/archiGrading/gradingTranslate");	
	//一级域下拉框查询
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "webservice/archiFirst/list");
    //根据一级查询二级子域
    srvMap.add("getSecondByFirst", pathAlias+"secondDomainList.json", "webservice/archiSecond/listByfirst"); 
	//获取附件信息
    srvMap.add("getFileInfo", pathAlias+"getSysMessageList.json", "archi/question/findByPlanIdAndFileType");
    //下载文档
    srvMap.add("downloadFile", pathAlias + "getDeliverablesList.json", "sys/changeplanonile/downloadFileBatch"); 
    //角色校验
    srvMap.add("idenifyRoleCheck", pathAlias + "getDeliverablesList.json", "webservice/archiGrading/roleCheck");
	var init = {
		init: function() {
			this._render();
		},
		
		_render: function() {
			var self = this;
			self._queryEventInit();
			self._band_btn_event();
			self._role_check();
			//初始化数据查询
			self._init_data_query();
		},
		
		//初始化数据查询
		_init_data_query: function() {
			var _data = Page.getParentCmd();
			if(_data) {
				this._getSysMessageList(Utils.jsonToUrl(_data));
			}
		},
		 
		//角色校验 
		_role_check: function() {
			Rose.ajax.postJson(srvMap.get('idenifyRoleCheck'),'',function(json, status){
				if(status) {							
					cache.roleCheck = json.data.isRole;
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}	
			});
		},
		
		//下方认定和取消按钮
		_band_btn_event: function() {
			var _from = Page.findId('sysMessageFrom');
			_from.find("[name='identify']").off('click').on('click',function() {
				var data = cache.selectData;
				//编号校验 不允许异常数据认定通过
				if(data.ext1 == '3' && data.description == '新增') {
    				var pass = Page.findId('modal').find("[name='pass']");
					var sysValue = Page.findId('selectData').find('[name="sysId"]').val();
					var _sysValue = $.trim(sysValue);
					var condition =  /^\d{1,8}$/;
					if(!_sysValue) {
	                    XMS.msgbox.show('编号为空！', 'error', 1000);
        				if(!pass.hasClass('show-nothing')) {
        					pass.addClass('show-nothing');
        				}
					} else {
		                if(_sysValue.length !=8 || !condition.test(_sysValue) ){          
		                    XMS.msgbox.show('请输入8位纯数字编号！', 'error', 2000);
	        				if(!pass.hasClass('show-nothing')) {
	        					pass.addClass('show-nothing');
	        				}
						} else {
	        				if(pass.hasClass('show-nothing')) {
	        					pass.removeClass('show-nothing');
	        				}
						    data.sysId = _sysValue;
						}
					}	               
				}
				data.idBelong = Page.findId('selectData').find('[name="idBelong"]').val();
				Page.findId('modalMessage').val("");
				var textModal = Page.findId('modal');
				textModal.off('shown.bs.modal').on('shown.bs.modal', function () {		
					//解决时间格式不能传输的问题
					data.modifyDate = data.modifyDate.replace(/-/g,"/");
					data.createDate = data.createDate.replace(/-/g,"/");
					data.applyTime = data.applyTime.replace(/-/g,"/");
					var isRun = false;
					//通过
					textModal.find("[name='pass']").off('click').on('click', function(){
				         if(isRun) {
				             return;
				         } else {
				        	 isRun = true;
					         setTimeout(function(){
					             isRun=false;
					         },1500); //点击后相隔多长时间可执行
				         }
						data.state = '审批通过';
						data.identifiedInfo = Page.findId('modalMessage').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('MessageGranding'),_cmd,function(json, status){
							if(status) {							
								textModal.modal('hide');
								Page.findId('sysMessageFrom').modal('hide');
								if(json.retMessage) {
									//云管异常
									console.log(json.retMessage);
									XMS.msgbox.show(json.retMessage, 'info', 2000);
									setTimeout(function() {
										Page.findId('querySysDomainForm').find("[name='query']").click();
									}, 2000);
								} else {
									XMS.msgbox.show('认定成功，数据已归档！', 'success', 1000);
									setTimeout(function() {
										Page.findId('querySysDomainForm').find("[name='query']").click();
									}, 1000);
								}
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//不通过
					textModal.find("[name='noPass']").off('click').on('click', function(){
				         if(isRun){
				             return;
				         } else {
				        	 isRun = true;
					         setTimeout(function(){
					             isRun=false;
					         },1500); //点击后相隔多长时间可执行
				         }
						data.state = '审批未通过';
						data.identifiedInfo = Page.findId('modalMessage').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('MessageGranding'),_cmd,function(json, status){
							if(status) {
								textModal.modal('hide');
								Page.findId('sysMessageFrom').modal('hide');
								XMS.msgbox.show('认定成功', 'success', 1500);					
								setTimeout(function() {
									Page.findId('querySysDomainForm').find("[name='query']").click();
								}, 1000);								
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
				});
				textModal.modal('show');
			});
		},
		
		//绑定查询按钮事件
		_queryEventInit: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				self._getSysMessageList(cmd);
			});
		},

		// 查询表格数据
		_getSysMessageList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('SysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysGradingMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				cache.datas = json.data.content;
				var template = Handlebars.compile(Page.findTpl('getGrandingMessageList'));
				
        		_dom.find("[name='content']").html(template(json.data.content));
        		//绑定表格的click事件
        		Utils.eventDClickCallback(_dom,function(isChecked,_input) {
        			var applyId = _input[0].value;
        			var allDatas = cache.datas;
        			if(allDatas) {
        				//获取当前选中数据
        				var index = 0;
        				while(allDatas[index].applyId != applyId) {
        					index++;
        				}
        				var selectData = $.extend(true,{},allDatas[index]);
        				//附件信息获取
        				if(selectData.fileId) {
        					var fileCondition = '';
        					//存在 cloudOrderId 为云管附件 type为3
        					if(selectData.cloudOrderId) {
        						fileCondition = 'planId=' + selectData.fileId + '&fileType=3';
        					} else {
        						fileCondition = 'planId=' + selectData.fileId + '&fileType=88888';
        					}
            				Rose.ajax.postJsonSync(srvMap.get('getFileInfo'), fileCondition,function(json2, status){
            					if(status) {      					
            						selectData.fileName=json2.data.fileName?json2.data.fileName:"没有可下载文件";
            						selectData.fileIndex=json2.data.id;
            					} else {
            						XMS.msgbox.show(json2.retMessage, 'error', 2000);
            					}					
            				});
        				} else {
            				selectData.fileName = "没有可下载文件";
        				}
        				//拼装数据翻译服务的入参
        				var _cmdTrans;
        				if(selectData.idBelong) {
        					_cmdTrans = 'idBelong='+selectData.idBelong+'&ext1='+selectData.ext1+'&sysState='+selectData.sysState+'&idThird='+selectData.sysId;
        				} else {
        					_cmdTrans = 'idBelong=0&ext1='+selectData.ext1+'&sysState='+selectData.sysState;
        				}     
        				//重置认定通过的样式  可显示
        				var pass = Page.findId('modal').find("[name='pass']");
        				if(pass.hasClass('show-nothing')) {
        					pass.removeClass('show-nothing');
        				}
        				//参数校验 异常则隐藏认定通过按钮
        				if(selectData.state == "申请") {
             				if(selectData.ext1 == '3' && selectData.description == '新增') {
            				} else if(selectData.description == '删除') {
            				} else {
            					var _sysValue = $.trim(selectData.sysId);
            					if(!_sysValue) {
            	                    XMS.msgbox.show('编号为空！', 'error', 1000);
        	        				if(!pass.hasClass('show-nothing')) {
        	        					pass.addClass('show-nothing');
        	        				}
            					}
            	                var condition =  /^\d{1,8}$/;
            	                if(_sysValue.length !=8 || !condition.test(_sysValue) ){          
            	                    XMS.msgbox.show('编号不为8位纯数字！', 'error', 2000);
        	        				if(!pass.hasClass('show-nothing')) {
        	        					pass.addClass('show-nothing');
        	        				}
            					}
            				}
        				}  

        				//获取系统建设状态和所属域的名称
        				Rose.ajax.postJsonSync(srvMap.get('MessageTranslate'),_cmdTrans,function(json3, status){
    						if(!status) {
    							XMS.msgbox.show(json3.retMessage, 'error', 2000);
    	        				if(!pass.hasClass('show-nothing')) {
    	        					pass.addClass('show-nothing');
    	        				}
    						} else {
    							if(json3.data) {
        							selectData.idBelongName = json3.data.idBelongName;
        							selectData.sysStateName = json3.data.sysStateName;
        							selectData.idFirstName = json3.data.idFirstName;
    							}
    						}
		      				cache.selectData = selectData;
	        				var type = selectData.ext1;
	        				var templateFrom;
	        				if(type == '1') {
	        					templateFrom = Handlebars.compile(Page.findTpl('primaryMessageFrom'));
	        				} else if(type == '2') {
	        					templateFrom = Handlebars.compile(Page.findTpl('secondMessageFrom'));
	        				} else {
	        					//设置三级系统模板
	        					templateFrom = Handlebars.compile(Page.findTpl('thirdMessageFrom'));
	        					//设置所属于是否可选，编号是否可修改
	        					if(selectData.description == '新增' && selectData.state == "申请") {
	        						selectData.sysId = json3.data?json3.data.adviceThirdId:0;
	        						selectData.isSelected = 1;
	        						selectData.disabledType = '';
	        					} else if(selectData.description == '修改' && selectData.state == "申请") {
	        						selectData.isSelected = 1;
	        						selectData.disabledType = 'readonly="readonly"';
	        					} else {
	        						selectData.disabledType = 'readonly="readonly"';
	        					}
	        				}    
	        				var _selectDataModal = Page.findId('selectData');
	        				_selectDataModal.html(templateFrom(selectData));
	        				var _modal = Page.findId('sysMessageFrom');
	        				_modal.modal('show');
	        				Utils.setSelectData(_modal);
	        				//模态框加载后事件
	        				_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
	        					//认定页允许所属域修改
	        					if(selectData.description != '删除' && selectData.state == "申请") {
	        						var firstDom = Page.findId('selectData').find('[name="idFirst"]');
	        						var selectDom = Page.findId('selectData').find('[name="idBelong"]');
									firstDom.val(json3.data.idFirst);
									firstDom.change();
									setTimeout(function() {
										selectDom.val(selectData.idBelong);
									},300);					        	                      						 
	        					}
	        					//附件下载事件绑定	        				
             					var downloadButton = _modal.find('[name="download"]');
	        					downloadButton.off('click').on('click',function() {
	        						if(selectData.fileId) {
		        						var downloadParam = 'ids=' + selectData.fileIndex;
		        	                    var downloadurl = srvMap.get('downloadFile')+"?"+downloadParam;
		        	                    downloadButton.attr("href", downloadurl.toString());
	        						} else {
	        							XMS.msgbox.show('没有可下载的附件！', 'error', 1000);
	        						}
	        					});        					   
	        				});
	        				if(selectData.state == '申请') {
	        					_selectDataModal.find("[name='identifiedModal']").addClass('show-nothing');
	        					//判断用户是否有认定权限
	        					if(cache.roleCheck == 'true') {
		        					Page.findId('IdentifyButtom').find("[name='identify']").removeClass('show-nothing');
	        					} else {
	        						//隐藏认定按钮
	        						Page.findId('IdentifyButtom').find("[name='identify']").addClass('show-nothing');
	        					}
	        				} else {
	        					//非申请状态的单子不允许认定操作
	        					_selectDataModal.find("[name='identifiedModal']").removeClass('show-nothing');
	        					Page.findId('IdentifyButtom').find("[name='identify']").addClass('show-nothing');
	        				}           				
    					});  				
        			}
        		});
			},_domPagination);
		}
	};
	module.exports = init;
});
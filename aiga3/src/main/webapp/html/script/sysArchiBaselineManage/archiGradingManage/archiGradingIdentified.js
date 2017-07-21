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
		selectData : ''	
	};
	//显示认定信息表
	srvMap.add("getSysGradingMessageList", pathAlias+"getSysMessageList.json", "archi/grading/findByConditionPage");
	
	//信息认定
	srvMap.add("MessageGranding", pathAlias+"getSysMessageList.json", "archi/grading/messageGranding");

	//数据翻译
	srvMap.add("MessageTranslate", pathAlias+"getSysMessageList.json", "archi/grading/gradingTranslate");
	
	var init = {
		init: function() {
			this._render();
		},
		
		_render: function() {
			var self = this;
			self._querydomain();
			self._band_btn_event();
		},
		
		//下方认定和取消按钮
		_band_btn_event: function() {
			var _from = Page.findId('sysMessageFrom');
			_from.find("[name='identify']").off('click').on('click',function() {
				var data = cache.selectData;
				//编号校验
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
				         if(isRun){
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
								XMS.msgbox.show('认定成功，数据已归档！', 'success', 2000);	
								setTimeout(function() {
									Page.findId('querySysDomainForm').find("[name='query']").click();
								}, 1500);
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
								XMS.msgbox.show('认定成功', 'success', 2000);	
								setTimeout(function() {
									Page.findId('querySysDomainForm').find("[name='query']").click();
								}, 1500);								
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
				});
				textModal.modal('show');
			});
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
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
				var _cmd = cmd;
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
        		Utils.eventClickChecked(_dom,function(isChecked,_input) {
        			var applyId = _input[0].value;
        			var allDatas = cache.datas;
        			if(allDatas) {
        				var index = 0;
        				while(allDatas[index].applyId != applyId) {
        					index++;
        				}
        				var selectData = allDatas[index];
        				var _cmdTrans;
        				if(selectData.idBelong) {
        					_cmdTrans = 'idBelong='+selectData.idBelong+'&ext1='+selectData.ext1+'&sysState='+selectData.sysState;
        				} else {
        					_cmdTrans = 'idBelong=0&ext1='+selectData.ext1+'&sysState='+selectData.sysState;
        				}     
        				var pass = Page.findId('modal').find("[name='pass']");
        				if(pass.hasClass('show-nothing')) {
        					pass.removeClass('show-nothing');
        				}
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

        				//信息翻译
        				Rose.ajax.postJsonSync(srvMap.get('MessageTranslate'),_cmdTrans,function(json, status){
    						if(!status) {
    							XMS.msgbox.show(json.retMessage, 'error', 2000);
    	        				if(!pass.hasClass('show-nothing')) {
    	        					pass.addClass('show-nothing');
    	        				}
    						} else {
    							if(json.data) {
        							selectData.idBelongName = json.data.idBelongName;
        							selectData.sysStateName = json.data.sysStateName;
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
	        					templateFrom = Handlebars.compile(Page.findTpl('thirdMessageFrom'));
	        					if(selectData.description == '新增' && selectData.state == "申请") {
	        						selectData.disabledType = '';
	        					} else {
	        						selectData.disabledType = 'readonly="readonly"';
	        					}
	        				}    
	        				var _selectDataModal = Page.findId('selectData');
	        				_selectDataModal.html(templateFrom(selectData));
	        				var _modal = Page.findId('sysMessageFrom');
	        				_modal.modal();
	        				if(selectData.state == '申请') {
	        					_selectDataModal.find("[name='identifiedModal']").addClass('show-nothing');
	        					Page.findId('IdentifyButtom').find("[name='identify']").removeClass('show-nothing');
	        				} else {
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
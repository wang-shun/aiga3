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
			var _from = Page.findId('SysMessageFrom');
			_from.find("[name='identify']").off('click').on('click',function() {
				Page.findId('modalMessage').val("");
				var textModal = Page.findId('modal');
				textModal.off('shown.bs.modal').on('shown.bs.modal', function () {
					var data = cache.selectData;
					data.modifyDate = data.modifyDate.replace(/-/g,"/");
					data.createDate = data.createDate.replace(/-/g,"/");
					data.applyTime = data.applyTime.replace(/-/g,"/");
					//通过
					textModal.find("[name='pass']").off('click').on('click', function(){
						data.state = '审批通过';
						data.identifiedInfo = Page.findId('modalMessage').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('MessageGranding'),_cmd,function(json, status){
							if(status) {
								textModal.modal('hide');
								XMS.msgbox.show('认定成功，数据已归档！', 'success', 2000);	
								setTimeout(function() {
									Page.findId('querySysDomainForm').find("[name='query']").click();
								}, 1000);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
					//不通过
					textModal.find("[name='noPass']").off('click').on('click', function(){
						data.state = '审批未通过';
						data.identifiedInfo = Page.findId('modalMessage').val();
						var _cmd = jQuery.param(data);
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('MessageGranding'),_cmd,function(json, status){
							if(status) {
								textModal.modal('hide');
								XMS.msgbox.show('认定成功', 'success', 2000);	
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
			_from.find("[name='cancel']").off('click').on('click',function() {
				if(!Page.findId('SysMessageFrom').hasClass('show-nothing')) {
					Page.findId('SysMessageFrom').addClass('show-nothing');
				}
			});
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				if(!Page.findId('SysMessageFrom').hasClass('show-nothing')) {
					Page.findId('SysMessageFrom').addClass('show-nothing');
				}
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
				var template = Handlebars.compile(Page.findTpl('getSysMessageList'));
				
        		_dom.find("[name='content']").html(template(json.data.content));
        		Utils.eventClickChecked(_dom,function(isChecked,_input) {
        			var applyId = _input[0].value;
        			var allDatas = cache.datas;
        			if(allDatas) {
        				if(Page.findId('SysMessageFrom').hasClass('show-nothing')) {
        					Page.findId('SysMessageFrom').removeClass('show-nothing');
        				}
        				var index = 0;
        				while(allDatas[index].applyId != applyId) {
        					index++;
        				}
        				var selectData = allDatas[index];
        				cache.selectData = selectData;
        				var type = selectData.ext1;
        				var templateFrom;
        				if(type == '1') {
        					templateFrom = Handlebars.compile(Page.findTpl('primaryMessageFrom'));
        				} else if(type == '2') {
        					templateFrom = Handlebars.compile(Page.findTpl('secondMessageFrom'));
        				} else {
        					templateFrom = Handlebars.compile(Page.findTpl('thirdMessageFrom'));
        				}    
        				var _selectDataModal = Page.findId('selectData');
        				_selectDataModal.html(templateFrom(selectData));
        				if(selectData.state == '申请') {
        					_selectDataModal.find("[name='identifiedModal']").addClass('show-nothing');
        					Page.findId('IdentifyButtom').removeClass('show-nothing');
        				} else {
        					_selectDataModal.find("[name='identifiedModal']").removeClass('show-nothing');
        					Page.findId('IdentifyButtom').addClass('show-nothing');
        				}      				
        			}
        		});
			},_domPagination);
		}
	};
	module.exports = init;
});
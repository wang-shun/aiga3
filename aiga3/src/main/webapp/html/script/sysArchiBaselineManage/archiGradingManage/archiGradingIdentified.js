define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiGradingIdentified');	
	
	var cache = {
		datas : ""
	};
	//显示系统信息表
	srvMap.add("getSysGradingMessageList", pathAlias+"getSysMessageList.json", "archi/grading/sysGradingMessageList");

	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		};
    	}
    };


	var init = {
		init: function() {
			this._render();
		},
		
		_render: function() {
			var self = this;
			self._querydomain();
			self._band_btn_event();
		},
		
		_band_btn_event: function() {
			var _from = Page.findId('SysMessageFrom');
			_from.find("[name='identify']").off('click').on('click',function() {
				Page.findId('modalMessage').val("");
				Page.findId('modal').modal('show');
			});
			_from.find("[name='cancel']").off('click').on('click',function() {
				alert('取消');
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
			Data.queryListCmd = _cmd;
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
        			var index = _input[0].value;
        			var allDatas = cache.datas;
        			if(allDatas) {
        				if(Page.findId('SysMessageFrom').hasClass('show_nothing')) {
        					Page.findId('SysMessageFrom').removeClass('show_nothing');
        				}
        				var selectData = allDatas[index];
        				var type = selectData.domainType;
        				var templateFrom;
        				if(type == '1') {
        					templateFrom = Handlebars.compile(Page.findTpl('primaryMessageFrom'));
        				} else if(type == '2') {
        					templateFrom = Handlebars.compile(Page.findTpl('secondMessageFrom'));
        				} else {
        					templateFrom = Handlebars.compile(Page.findTpl('thirdMessageFrom'));
        				}     				
        				Page.findId('selectData').html(templateFrom(selectData));
        			}
        		});
			},_domPagination);
		}
	};
	module.exports = init;
});
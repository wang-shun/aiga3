define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('sysMessageQuery');

	//系统信息查询
    srvMap.add("getTransList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
    //三级系统信息导出
    srvMap.add("getThirdExport", pathAlias+"getSysMessageList.json", "excel/export/sysMessage");
	var cache = {
		datas : ""
	};
	
	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._btn_event();
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_btn_event: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			var _exportBtn = _form.find("[name='export']");
			_exportBtn.off('click').on('click',function() {
				location.href = srvMap.get('getThirdExport'); 
			});
			_queryBtn.off('click').on('click',function() {
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
			Utils.getServerPage(srvMap.get('getTransList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getFullSysMessageList'));				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventClickChecked(_dom,function(isChecked,_input) {
        			var idThird = _input[0].value;
        			var allDatas = cache.datas;
        			if(allDatas) {
        				var index = 0;
        				while(allDatas[index].idThird != idThird) {
        					index++;
        				}
        				var selectData = allDatas[index];
        				var template = Handlebars.compile(Page.findTpl('fullSystemMessageFrom'));
        				Page.findId('queryThirdMessageModal').html(template(selectData));
        				var _modal = Page.findId('fullSystemMessageModal');
        				_modal.modal();
        			}
        		});
			},_domPagination);
		}	
	};
	module.exports = init;
});
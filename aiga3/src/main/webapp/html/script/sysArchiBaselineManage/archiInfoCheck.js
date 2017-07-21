define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiInfoCheck');
    //分层错误信息查询
	srvMap.add("getlevelist", pathAlias+"getSysMessageList.json", "archi/level/reset");
	var cache = {
		datas : ""	
	};
	
	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			self._getWrongDataList();
		},
		//错误分层数据
		_getWrongDataList: function(){
			var self = this;
			var _dom = Page.findId('wrongMessageQuery');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getlevelist'),'',function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getWrongMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data));
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
		}	
	};
	module.exports = init;
});
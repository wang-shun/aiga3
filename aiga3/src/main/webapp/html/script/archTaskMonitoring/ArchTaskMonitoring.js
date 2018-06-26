define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchTaskMonitoring/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('taskMonitoring');

	//显示查询信息表
    srvMap.add("getTaskMonitoringList", pathAlias+"getList.json", "arch/taskMonitoring/queryByCondition");
	
	var cache = {
			datas : ""
		};
	var init = {
				
		init: function() {			 
			this._render();
		},
		
		_render: function() {
			//查询
			this._query_event();
			//this._getGridList();
		},		
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			
			var _dom = Page.findId('taskMonitoringList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			_cmd = _cmd.replace(/-/g,"/");
			Utils.getServerPage(srvMap.get('getTaskMonitoringList'),_cmd,function(json){
				window.XMS.msgbox.hide();				
				var template = Handlebars.compile(Page.findTpl('taskMonitoringTemp'));				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
			},_domPagination);
		},
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');			 
			Utils.setSelectData(_form);		 
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();				
				var finishDate = _form.find("[name='finishDate']").val();
				if(finishDate == 0) {
					XMS.msgbox.show('查询时间为空！', 'error', 2000);
					return
				}
				self._getGridList(cmd);
			});		
        }                      
	};
				 
	module.exports = init;
	
});
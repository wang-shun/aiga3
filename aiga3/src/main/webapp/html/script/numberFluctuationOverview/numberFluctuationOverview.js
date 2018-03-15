define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "numberFluctuationOverview/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('numberFluctuation');

	//一级数据库下拉框
	srvMap.add("primary", pathAlias+"primaryDatabase.json", "webservice/static/primaryDatabase");	
	//二级数据库下拉框
	srvMap.add("second", pathAlias+"secondDatabase.json", "webservice/static/secondDatabase");
	//查询接口
	srvMap.add("getNumberFluctuation", pathAlias+"getNumberFluctuation.json", "");
	
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
			this._getGridList();
			
		},
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('connectionPoolList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
//			_cmd = _cmd.replace(/-/g,"/");
			
			Rose.ajax.postJson(srvMap.get('getNumberFluctuation'),_cmd,function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Page.findTpl('numberFluctuationTemp'));
					var tablebtn = _dom.find("[name='content']");
					tablebtn.html(template(json.data.content));
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');			 
			Utils.setSelectData(_form);		 
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();				
				var primaryDatabase = _form.find("[name='primaryDatabase']").val();
				var secondDatabase = _form.find("[name='secondDatabase']").val();
				if(primaryDatabase == 0) {
					XMS.msgbox.show('一级数据库为空！', 'error', 2000);
					return
				}
				if(secondDatabase == 0) {
					XMS.msgbox.show('二级数据库为空！', 'error', 2000);
					return
				}
				self._getGridList(cmd);
			});		
        },   
	};
				 
	module.exports = init;
	
});
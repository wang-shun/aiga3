define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchAigaFunction/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archAigaFunction');


	//显示查询信息表
	srvMap.add("getArchAigaList", pathAlias+"getList.json", "archi/aigaFunction/listDbConnects");	
	
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
			var _dom = Page.findId('archAigaFunctionList');
			var _baseChange = Page.findId('baseDataChange');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			_cmd = _cmd.replace(/-/g,"/");
				
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getArchAigaList'),_cmd,function(json){
				window.XMS.msgbox.hide();				
				var template = Handlebars.compile(Page.findTpl('archAigaFunctionTemp'));
				var templateTwo = Handlebars.compile(Page.findTpl('baseDataChangeTemp'));
        		var tablebtn = _dom.find("[name='content']");
   
        		tablebtn.html(template(json.data.content));
        		_baseChange.html(templateTwo(json.data.content));
        		Utils.eventTrClickCallback(_dom);

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
				self._getGridList(cmd);
			});		
        },
                          
	};
				 
	module.exports = init;
	
});
define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchAigaFunction/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archAigaFunction');


	//显示查询信息表
	srvMap.add("getArchAigaList", pathAlias+"getList.json", "webservice/archiGrading/sysMonthReport");	
	
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
		},		
		
		// 查询表格数据
		_getGridList: function(cmd){
			debugger;
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('archAigaFunctionList');
			
			//XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			_cmd = _cmd.replace(/-/g,"/");
			//调用服务
			Rose.ajax.postJson(srvMap.get('getArchAigaList'),_cmd,function(json, status){
				debugger;
				if(status) {
					debugger
					var _baseChange = Page.findId('baseDataChange');
					var template = Handlebars.compile(Page.findTpl('baseDataChangeTemp'));
					_baseChange.html(template(json.data.sysMonthApplyReport));
					/*json.data.b*/
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
				cmd = cmd.replace(/-/g,"");
				self._getGridList(cmd);
				
			});		
        },
                          
	};
				 
	module.exports = init;
	
});
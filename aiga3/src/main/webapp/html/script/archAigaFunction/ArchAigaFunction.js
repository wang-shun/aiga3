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
	srvMap.add("getThirdList", pathAlias+"getThirdList.json", "webservice/archiGrading/thirdAddReport");	

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
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('archAigaFunctionList');
			var _form = Page.findId('queryDataForm');
			var _baseChange = Page.findId('baseDataChange');
			var divList = _baseChange.find("[name='divList']");
			var _table = Page.findId('dataMaintainListTable');	
			var trList = _table.find("[name='trList']");
			//查询时间校验
			var _applyTime = _form.find("[name='applyTime']").val();
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			if(month>0 && month <10){
				month = '0'+month;
			}
			var dateTime = year + '-' + month;
			if(_applyTime > dateTime){
				XMS.msgbox.show('查询月份大于当前时间！', 'error', 2000);
				divList.remove();
				trList.remove();
				return
			}
			
			_cmd = _cmd.replace(/-/g,"/");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//调用服务
			Rose.ajax.postJson(srvMap.get('getArchAigaList'),_cmd,function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					var _baseChange = Page.findId('baseDataChange');					
					var template = Handlebars.compile(Page.findTpl('baseDataChangeTemp'));
					_baseChange.html(template(json.data.sysMonthApplyReport));					
					//判空校验
					var _span = _baseChange.find("[name='span']").length;
					if(_span == 0){
						_baseChange.css ('display','none');
					}else{
						_baseChange.css ('display','block');
					}					
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});	
			//调用服务
			Rose.ajax.postJson(srvMap.get('getThirdList'),_cmd,function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					//三级域列表
					var _table = Page.findId('dataMaintainListTable');	
					var content = _table.find("[name='content']");
					var templateTwo = Handlebars.compile(Page.findTpl('archAigaFunctionTemp'));
					content.html(templateTwo(json.data));					
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
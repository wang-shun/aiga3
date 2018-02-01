define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "connectionPoolConfiguration/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('connectionPool');


	//显示查询信息表
	srvMap.add("poolConfigurationList", pathAlias+"getList.json", "dbconnect/configure/query");

	//业务系统下拉框
	srvMap.add("businessSystem", pathAlias+"workplanState.json", "webservice/static/businessSystem");
	//查询状态下拉框
	srvMap.add("queryState", pathAlias+"workState.json", "webservice/static/queryState");

	
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
			this._applydomain();
			var _form = Page.findId('queryDataForm');
			var cmd = _form.serialize();
			this._getGridList(cmd);
			
		},
		
		//初始化时间框
		_time:function(){			
			//初始化时间框
			function showMonthFirstDay() {     
				var date=new Date();
			 	date.setDate(1);
			 	return Rose.date.dateTime2str(date,"yyyy-MM-dd");   
			}
			var _form = Page.findId('queryDataForm'); 
			_form.find("[name='insertTime']").val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
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
			_cmd = _cmd.replace(/-/g,"/");

			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('poolConfigurationList'),_cmd,function(json){
				window.XMS.msgbox.hide();				
				var template = Handlebars.compile(Page.findTpl('connectionPoolTemp'));				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));

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
				var insertTime = _form.find("[name='insertTime']").val();

				if(insertTime == 0) {
					XMS.msgbox.show('采集时间为空！', 'error', 2000);
					return
				}
				/*if(cmd.indexOf('+')>-1){
					cmd = cmd.replace(/\+/g,'');
				}*/
				self._getGridList(cmd);
			});		
        },                      
	};
				 
	module.exports = init;
	
});
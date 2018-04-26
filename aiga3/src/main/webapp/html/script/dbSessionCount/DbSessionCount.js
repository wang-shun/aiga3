define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "DbSessionCount/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('dbSessionCount');


	//显示查询信息表
    srvMap.add("getDbSessionCountList", pathAlias+"getList.json", "archi/DbSessionCount/queryByCondition");
	
	
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
			var _dom = Page.findId('dbSessionCountList');
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			_cmd = _cmd.replace(/-/g,"");
					
			/*//初始化时间框
			function showMonthFirstDay() {     
				var date=new Date();
			 	date.setDate(1);
			 	return Rose.date.dateTime2str(date,"yyyy-MM-dd");   
			}
			var _form = Page.findId('queryDataForm'); 
			_form.find('[name="createTime"]').val(showMonthFirstDay());*/

			//调用服务
			Rose.ajax.postJson(srvMap.get('getDbSessionCountList'),_cmd,function(json, status){
				if(status) {
					debugger
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Page.findTpl('dbSessionCountTemp'));				
	        		var tablebtn = _dom.find("[name='content']");
	        		tablebtn.html(template(json.data));

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
				
				var createTime = _form.find("[name='createTime']").val();
//				if(createTime == 0) {
//					XMS.msgbox.show('查询时间为空！', 'error', 2000);
//					return
//				}
				self._getGridList(cmd);
			});		
        },
                      
	};
				 
	module.exports = init;
	
});
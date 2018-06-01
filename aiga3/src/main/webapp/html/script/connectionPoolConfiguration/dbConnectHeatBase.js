define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "connectionPoolConfiguration/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('connectionHeatBase');


	//显示查询信息表
	srvMap.add("poolConfigurationList", pathAlias+"poolConfigurationList.json", "webservice/configure/query");
	//显示查询信息表
	srvMap.add("heatbasequery", pathAlias+"poolConfigurationList.json", "webservice/dbconnect/heatbasequery");
	//查询状态下拉框 center
	srvMap.add("heatbaseselect1", pathAlias+"distinctCenter.json", "webservice/dbconnect/heatbaseselect1");
	//查询状态下拉框 center
	srvMap.add("heatbaseselect2", pathAlias+"distinctCenter.json", "webservice/dbconnect/heatbaseselect2");
	//查询状态下拉框
	srvMap.add("heatbasequeryState", pathAlias+"queryState.json", "webservice/dbconnect/heatbasequeryState");
	//查询状态下拉框
	srvMap.add("heatbasequeryDetail", pathAlias+"queryState.json", "webservice/dbconnect/heatbasequeryDetail");
	
	//业务系统下拉框
	srvMap.add("businessSystem", pathAlias+"businessSystem.json", "webservice/static/businessSystem");
	//查询状态下拉框
	srvMap.add("queryState", pathAlias+"queryState.json", "webservice/static/queryState");
	//查询状态下拉框 center
	srvMap.add("distinctCenter", pathAlias+"distinctCenter.json", "webservice/configure/distinctCenter");
	//查询文字
	srvMap.add("getText", pathAlias+"getText.json", "webservice/configure/getText");
	//
	srvMap.add("heatbasefindAll", pathAlias+"getText.json", "webservice/heatbase/findAll");
	
	var cache = {
			datas : ""
	};
	var init = {
				
		init: function() {			 
			this._render();
		},
		
		_render: function() {
			//查询
			this._time();
			var _form = Page.findId('queryDataForm');
			var cmd = _form.serialize();
			this._getGridList(cmd);
			this._query_event();
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
/*			Rose.ajax.postJson(srvMap.get('getText'),_cmd,function(jsontxt, status){
				if(status) {
					window.XMS.msgbox.hide();
					var templateText = Handlebars.compile(Page.findTpl('connectionPoolTempText'));
					var _text = Page.findId('connectionPoolText');
        			_text.html(templateText(jsontxt.data[0]));
        			//判空校验
					var _textA = _text.find("[name='textShow']").length;
					var _textB = _text.find("[name='textShowTwo']");
					if(_textA != 0){
						_textB.css ('display','none');
					}else{
						_textB.css ('display','block');
					}
        			//打印查询月份
					var _form = Page.findId('queryDataForm');
					var _insertTime = _form.find("[name='insertTime']").val();
					var timeShow = Page.findId('text');
					timeShow.find("[name='timeShow']").text(_insertTime);
					_text.find("[name='timeShowTwo']").text(_insertTime);
				} else {
					XMS.msgbox.show(jsontxt.retMessage, 'error', 2000);
				}					
			});	*/
			Rose.ajax.postJson(srvMap.get('heatbasequery'), _cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Page.findTpl('connectionPoolTemp'));                
	                var tablebtn = _dom.find("[name='content']");
	                tablebtn.html(template(json.data));
	        		Utils.eventTrClickCallback(_dom);
	        		//详细------按钮
	        		tablebtn.find("[class='btn btn-primary btn-table-detail']").off('click').on('click', function() {
	        			var selectIndexName = $(this).attr("data-indexName");
	        			var selectMosule = $(this).attr("data-module");
	        			var selectDb = $(this).attr("data-db");
	        			var selectDate = $(this).attr("data-date");
	        			var incmd = "indexName="+selectIndexName+"&key3="+selectMosule+"&db="+selectDb+"&insertTime="+selectDate.substring(0,10);
	        			var _domIn = Page.findId('connectionPoolList');
						var _domPaginationIn = _domIn.find("[name='paginationIn']");
	        			Utils.getServerPage(srvMap.get('heatbasequeryDetail'),incmd,function(injson){
					        var template2 = Handlebars.compile(Page.findTpl('connectionPoolTempIn'));
							Page.findId('changeModal').find("[name='content']").html(template2(injson.data));
			        		var _modal = Page.findId('showDetailModal');
							_modal.modal('show');
							Utils.setSelectData(_modal);
							_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
							});		
						},_domPaginationIn);
	        		});
	        		//美化单机
					Utils.eventTrClickCallback(_dom);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
  			});
		},
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');
			Utils.setSelectDataPost(_form,true);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();				
				var insertTime = _form.find("[name='insertTime']").val();
				if(insertTime == 0) {
					XMS.msgbox.show('采集时间为空！', 'error', 2000);
					return
				}
				self._getGridList(cmd);
			});		
        }   
	};
				 
	module.exports = init;
	
});
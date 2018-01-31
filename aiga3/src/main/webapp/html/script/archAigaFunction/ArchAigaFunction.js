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
			this._time();
			this._query_event();
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
			 	return Rose.date.dateTime2str(date,"yyyy-MM");   
			}
			var _form = Page.findId('queryDataForm'); 
			_form.find("[name='applyTime']").val(Rose.date.dateTime2str(new Date(),"yyyy-MM"));
		},
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('archAigaFunctionList');

			_cmd = _cmd.replace(/-/g,"");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			//调用服务
			Rose.ajax.postJson(srvMap.get('getArchAigaList'),_cmd,function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					var _monthReportNowData = Page.findId('monthReportNowData');
					var templateA = Handlebars.compile(Page.findTpl('baseDataChangeTempA'));
					_monthReportNowData.html(templateA(json.data.sysMonthApplyReport));
					//打印查询月份
					var _form = Page.findId('queryDataForm');
					var _applyTime = _form.find("[name='applyTime']").val();
					_monthReportNowData.find("[name='timeShow']").text(_applyTime);
					//末尾加句号
					var _punctuation = _monthReportNowData.find("[name='punctuation']").last();
					if(_punctuation.text() == '、'){
						_punctuation.text('。');
					}
					//判空校验
					var _spanA = _monthReportNowData.find("[name='span']").length;
					if(_spanA == 0){
						_monthReportNowData.css ('display','none');
					}else{
						_monthReportNowData.css ('display','block');
					}
					
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});	
			
			//调用服务
			Rose.ajax.postJson(srvMap.get('getArchAigaList'),_cmd,function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					var _monthReportAllData = Page.findId('monthReportAllData');
					var templateB = Handlebars.compile(Page.findTpl('baseDataChangeTempB'));
					_monthReportAllData.html(templateB(json.data.sysMonthApplyReport));
					var _form = Page.findId('queryDataForm');
					var _applyTime = _form.find("[name='applyTime']").val();
					_monthReportAllData.find("[name='timeShow']").text(_applyTime);
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
					//列表判空校验
					var listLi = _table.find("[name='listLi']");
					for(var i =0;i<listLi.length;i++){
						if(listLi[i].innerHTML == ""){							
							listLi[i].remove();
						}
					}
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
				//查询时间校验
				var _applyTime = _form.find("[name='applyTime']").val();
				var _monthReportNowData = Page.findId('monthReportNowData');
				var _monthReportAllData = Page.findId('monthReportAllData');
				
				var divListA = _monthReportNowData.find("[name='divListA']");
				var divListB = _monthReportAllData.find("[name='divListB']");

				var _table = Page.findId('dataMaintainListTable');	
				var trList = _table.find("[name='trList']");
				if(_applyTime == 0){
					XMS.msgbox.show('请选择查询月份！', 'error', 2000);
					divListA.remove();
					divListB.remove();
					trList.remove();
					return
				}
				var date = new Date();
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				if(month>0 && month <10){
					month = '0'+month;
				}
				var dateTime = year + '-' + month;
				if(_applyTime > dateTime){
					XMS.msgbox.show('查询月份大于当前时间！', 'error', 2000);
					divListA.remove();
					divListB.remove();
					trList.remove();
					return
				}
				self._getGridList(cmd);
				
			});		
        },
                          
	};
				 
	module.exports = init;
	
});
define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "errorCode/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('errorCodePool');


	//显示查询信息表
	srvMap.add("querybylist", pathAlias+"poolConfigurationList.json", "webservice/csferrcode/querybylist");
	//查询状态下拉框 center
	srvMap.add("select1", pathAlias+"distinctCenter.json", "webservice/csferrcode/select1");

	//显示查询信息表
	srvMap.add("poolConfigurationList", pathAlias+"poolConfigurationList.json", "webservice/configure/query");
	//显示查询信息表    前七天
	srvMap.add("queryPre7DayData", pathAlias+"poolConfigurationList.json", "webservice/configure/queryPre7DayData");

	//业务系统下拉框
	srvMap.add("businessSystem", pathAlias+"businessSystem.json", "webservice/static/businessSystem");
	//查询状态下拉框
	srvMap.add("queryState", pathAlias+"queryState.json", "webservice/static/queryState");
	//查询状态下拉框 center
	srvMap.add("distinctCenter", pathAlias+"distinctCenter.json", "webservice/configure/distinctCenter");
	//查询状态下拉框 db
	srvMap.add("distinctDb", pathAlias+"distinctDb.json", "webservice/configure/distinctDb");
	//excle export uncover
	srvMap.add("uncover", pathAlias+"getText.json", "webservice/csferrcode/uncover");
	//excle export unstandard
	srvMap.add("unstandard", pathAlias+"getText.json", "webservice/csferrcode/unstandard");
	
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
			 	return Rose.date.yesterdayTime2str(date,"yyyy-MM-dd");   
			}
			var _form = Page.findId('queryDataForm'); 
			_form.find("[name='insertTime']").val(Rose.date.yesterdayTime2str(new Date(),"yyyy-MM-dd"));
		},
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var stime = _cmd.substring(11,21);
			var ftime = stime.replaceAll('-','');
			var scenter = _cmd.split('center')[1]==''?'':_cmd.split('center=')[1];
			var _topcmd = {
				insertTime:ftime,
				center:scenter
			}
			var _dom = Page.findId('connectionPoolList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');			
			
			// 设置服务器端分页
			Rose.ajax.postJson(srvMap.get('querybylist'),_topcmd,function(json){
				window.XMS.msgbox.hide();		
				var template = Handlebars.compile(Page.findTpl('connectionPoolTemp'));                
                var tablebtn = _dom.find("[name='content']");
                tablebtn.html(template(json.data));
        		Utils.eventTrClickCallback(_dom);
        		//展示报告内容
				var templateText = Handlebars.compile(Page.findTpl('errorCodeTempText'));
				var _text = Page.findId('errorCodeText');
				var result ={
					total:"0",
					percentage:"0",
					standard:"0"
				}
				var tmp = 0;
				tmp = json.data.length;
				var totalPercentage = 0;
				var totalStandard = 0;
				for(index in json.data){
					totalPercentage += json.data[index].percentage;
					totalStandard += json.data[index].standard;
				}
				result.total=tmp;
				result.percentage=totalPercentage/tmp;
				result.standard=totalStandard/tmp;
    			_text.html(templateText(result));
    			//打印查询月份
				var _form = Page.findId('queryDataForm');
				var _insertTime = _form.find("[name='insertTime']").val();
				var timeShow = Page.findId('text');
				timeShow.find("[name='timeShow']").text(_insertTime);
				_text.find("[name='timeShowTwo']").text(_insertTime);
        		
        		//下载未覆盖------按钮
        		tablebtn.find("[class='btn btn-primary btn-table-uncover']").off('click').on('click', function() {
					var selectCenter = $(this).attr("data-center");
        			var selectDate = $(this).attr("data-date");
        			var url = srvMap.get('uncover')+"?center="+selectCenter+"&insertTime="+selectDate;
					location.href = encodeURI(encodeURI(url));
        		});
        		//下载不规范------按钮
        		tablebtn.find("[class='btn btn-primary btn-table-unstandard']").off('click').on('click', function() {
					var selectCenter = $(this).attr("data-center");
        			var selectDate = $(this).attr("data-date");
        			var url = srvMap.get('unstandard')+"?center="+selectCenter+"&insertTime="+selectDate;
					location.href = encodeURI(encodeURI(url));
        		});
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
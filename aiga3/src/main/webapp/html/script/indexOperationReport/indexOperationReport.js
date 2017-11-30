define(function(require, exports, module) {
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// Page
	var Page = Utils.initPage('indexOperationReport');	
	
	/*后台接口 start*/
	//三级系统下拉框
    srvMap.add("logReportTimeType", '', "archi/static/logReportTimeType");	
	//三级系统下拉框
    srvMap.add("getLogReportModel", '', "archi/static/getLogReportModel");	
	/*后台接口 end*/
    
    //模板
    var Tpl = {
    		centerCSFModel: require('tpl/indexOperationReport/centerCSFModel.tpl')
    };
	//向外暴露的模块
	var init = {
		init: function() {
			//下拉框
			this._load_combo_select();
		},	
		//渲染下拉框  查询按钮
		_load_combo_select: function() {
			var self = this;
			var group = Page.findId("selectGroup");
			var timeDom = group.find("[name='modeTime']");
			var yesterdsay = new Date(new Date().getTime() - 86400000);
			timeDom.val(Rose.date.dateTime2str(yesterdsay,"yyyy-MM-dd"));
			//comboselect
			Utils.setSelectDataPost(group,true);	
			//查询按钮事件绑定
			group.find("[name='query']").off('click').on('click',function() {
				var dom = Page.findId("logList");
				//第一个模板加载
				//TODO 调用接口
				var param = Page.find("[name='reportMode']").val(); 
				if(param==1) {
					//各中心CSF服务运行情况日报
					var template = Handlebars.compile(Tpl.centerCSFModel);
					dom.html(template());
				} else {
					dom.html("");
				}
			});
		},
	};
	module.exports = init;
});
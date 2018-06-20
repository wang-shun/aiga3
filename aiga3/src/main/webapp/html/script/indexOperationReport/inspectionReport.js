define(function(require, exports, module) {
	// 通用工具模块
	var Utils = require("global/utils.js");
	var Page = Utils.initPage('inspectionReport');		
    srvMap.add("yesterDayInspection", '', "inspect/report/yesterDayInspection");
        
	var init = {
		init: function() {
			this._getHtmlInfo();
		},	
		
		_getHtmlInfo: function() {
			var self = this;
	        var _form = Page.findId("queryDataForm");
	        var dayTimeDom = Page.find("[name='cjDate']");
	        var yesterdsay = new Date(new Date().getTime() - 86400000);
			dayTimeDom.val(Rose.date.dateTime2str(yesterdsay,"yyyy-MM-dd"));				        
			//查询按钮事件绑定
			_form.find("[name='query']").off('click').on('click',function() {
				if(dayTimeDom.val() == 0){
					XMS.msgbox.show('请输入查询时间！', 'error', 2000);
					return;
				}
		        var dom = Page.findId("logList");
		        var _wrap = Page.find("[name='wrap']");
				var cmd = _form.serialize();
		        var _cmd = cmd.replace(/-/g,"");
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
		    	Rose.ajax.postJson(srvMap.get("yesterDayInspection"),_cmd,function(json, status){
					window.XMS.msgbox.hide();
					if(json.data == null){
						XMS.msgbox.show('查询的时间暂无数据！', 'error', 2000);
						return;
					}else if(status) {
						_wrap.css("display","block");
						dom.html(json.data);
						Utils.eventClickChecked(dom);
					}else{
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});				
			});	
		}
	};
	module.exports = init;
});
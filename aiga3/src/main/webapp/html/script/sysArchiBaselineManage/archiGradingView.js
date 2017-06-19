define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('sysMessageQuery');
    //一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/grading/primaryDomainList");
	//显示系统信息表
	srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/grading/sysMessageList");
	
	var cache = {
		datas : ""	
	};
	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		};
    	}
    };

	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
		_primaryView: function() {
			var content = '<div class="border-view-out">';
			content += '<div id="JS_SaaS" class="border-view">';
			content += '<label class="label-left-sty">SaaS</label>';		
			for(var i=0;i<=8;i++) {
				if(i%3==0 && i!=0) {
					content += '<label class="sys-sty" style="margin-left:'+(50*i+50)+'px;width:30px;height: 125px;">某某某系统</label>';
				} else {
					content += '<label class="sys-sty" style="margin-left:'+(50*i+50)+'px;width:30px;height: 85px;">某某某系统</label>';
				}	
				if(i==8) {
					content += '<label class="" style="margin-left:'+(50*i+50)+'px;width:30px;"></label></div>';	
				}
			}
			content += '<div id="JS_PaaS" class="border-view">';
			content += '<label class="label-left-sty" style="float:left;">PaaS</label>';
			content += '<div class="paas-sty">';
			content += '<div class="border-view-inside">';
			content += '<label class="label-left-sty" style="float:left;">BPaaS</label>';
			content += '<div class="paas-sty">';
			content += '<div id="JS_BPaaS" class="noborder-view"></div>';
			content += '<div id="JS_DaaS" class="border-view"><label class="label-left-sty">DaaS</label></div>';
			content += '</div>';
			content += '</div>';
			content += '<div id="JS_UPaaS" class="border-view-inside"><label class="label-left-sty">UPaaS</label></div>';
			content += '<div id="JS_IPaaS" class="border-view-inside"><label class="label-left-sty">IPaaS</label></div>';
			content += '<div id="JS_TPaaS" class="border-view-inside"><label class="label-left-sty">TPaaS</label></div>';
			content += '</div>';
			content += '</div>';
			content += '<div id="JS_IaaS" class="border-view"><label class="label-left-sty">IaaS</label></div>';
			content += ' </div>';
			var _dom = Page.findId('sysMessageView');
			var template = Handlebars.compile(content);
    		var view = _dom.find("[name='archiView']");
    		view.html(template());
		},
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('select_data');
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				self._primaryView();
//				var cmd = _form.serialize();
//				self._getTableDataList(cmd);
			});
		},

		// 查询表格数据
		_getTableDataList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd) {
				var _cmd = cmd;
			}
			Data.queryListCmd = _cmd;
			var _dom = Page.findId('sysMessageQuery');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getSysMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
		}	
	};
	module.exports = init;
});
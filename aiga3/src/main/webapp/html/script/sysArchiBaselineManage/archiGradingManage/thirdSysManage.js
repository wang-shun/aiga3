define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('thirdSysManage');	
    //一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/grading/primaryDomainList");
    //根据一级查询二级子域
    srvMap.add("getSecondDomainList", pathAlias+"secondDomainList.json", "archi/grading/secondDomainList");
    //二级子域查询
    srvMap.add("getAllSecondDomainList", pathAlias+"secondDomainList.json", "archi/grading/allSecondDomainList");
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
			var self = this;
			self._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
	
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			var _applyBtn = _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				self._getCaseTempList(cmd);
			});
			_applyBtn.off('click').on('click',function() {
				var _modal = Page.findId('thirdApplyModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
			});
		},

		// 查询表格数据
		_getCaseTempList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				var _cmd = cmd;
			}
			Data.queryListCmd = _cmd;
			var _dom = Page.findId('thirdSysMessageList');
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
        		tablebtn.find("[class='btn btn-primary btn-table-update']").off('click').on('click', function() {
        			self._band_table_btn($(this),"update");
        		});
        		tablebtn.find("[class='btn btn-primary btn-table-delete']").off('click').on('click', function() {
        			self._band_table_btn($(this),"delete");
        		});
			},_domPagination);
		},
		
		_band_table_btn: function(dom, type) {
			if(type == 'update') {
				var index = dom.attr("temp");
				var template = Handlebars.compile(Page.findTpl('thirdUpdateFrom'));
				Page.findId('updateModal').html(template(cache.datas[index]));
				var _modal = Page.findId('thirdUpdateModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
//				_dom.modal('hide');
			}
			if(type == 'delete') {
				var temp = dom.attr("temp");
				alert("删除"+temp);
			}
		},

		// 事件：双击绑定事件
		eventDClickCallback:function(obj,callback){
			obj.find("tr").bind('dblclick ', function(event) {
		        	if (callback) {
						callback();
					}
		    });
		}
	};
	module.exports = init;
});
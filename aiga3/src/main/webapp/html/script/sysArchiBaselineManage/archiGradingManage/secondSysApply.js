define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('secondSysApply');	
    //二级子域查询
    srvMap.add("getAllSecondDomainList", pathAlias+"secondDomainList.json", "archi/grading/allSecondDomainList");

	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		}
    	}
    };


	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			this._querySelect();
			this._apply_btn();
		},
		
		_apply_btn: function() {
			Page.findId('applySubmit').off('click').on('click',function() {
			     alert("申请");
			});
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_querySelect: function() {
			Utils.setSelectData(Page.find('selectLoad'));
		},


	};
	module.exports = init;
});
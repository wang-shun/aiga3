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
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");

    //二级数据接口 入参：idFirst level
    srvMap.add("getSecView", pathAlias+"getSecView.json", "archi/view/secView");

    var Tpl = {
		getSecView: require('tpl/sysArchiBaselineManage/archiGradingManage/getSecView.tpl')
	}

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

		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('selectData');
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){

				var _idFirst = _form.find("[name='primaryDomain']").val();
				var cmd= "idFirst="+_idFirst+"&level=122";
				Rose.ajax.postJson(srvMap.get("getSecView"),cmd, function(json, status) {
					if(status) {
						var template = Handlebars.compile(Tpl.getSecView);
		        		Page.findName("archiView").html(template(json.data));

		        		//计算高度值
		        		self.setSidebarHeight();
					}
	  			});
			});
		},
		setSidebarHeight:function(){
			Page.find('.mxgif-sidebar').each(function(){
				var _thiz = $(this);
				var _pHeight = _thiz.parent().height();
				_thiz.height(_pHeight);
			})
		}
	};


	module.exports = init;
});
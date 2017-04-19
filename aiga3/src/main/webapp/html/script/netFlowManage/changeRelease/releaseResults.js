define(function(require,exports,module){

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('changeRelease');

	// 路径重命名
	var pathAlias = "netFlowManage/changeRelease/releaseResults/";

	//查询发布结果表格
	srvMap.add("getReleaseResultsList", pathAlias + "getReleaseResultsList.json", "sys/cache/listSysid");



    var Data = {
    	queryListCmd: null
    }

	var Query = {
		init: function(){
			// 默认查询所有
			this.getReleaseResultsList();
			// 初始化查询表单
			// this.queryCaseTempForm();
		},
		// 按条件查询
		// queryCaseTempForm: function(){
		// 	var self = this;
		// 	var _form = Page.findId('queryCaseTempForm');
		// 	Utils.setSelectData(_form);
		// 	var _queryBtn =  _form.find("[name='query']");
		// 	_queryBtn.bind('click', function() {
		// 		var cmd = _form.serialize();
		// 		self.getCaseTempList(cmd);
		// 	});
		// },
		//发布结果表格显示
		getReleaseResultsList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				var _cmd = cmd;
			}
			// Data.queryListCmd = _cmd;
			var _dom = Page.findId('releaseResultsList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getReleaseResultsList'),_cmd,function(json){
				window.XMS.msgbox.hide();

				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('releaseList'));
        		_dom.find("[name='content']").html(template(json.data.content));

        		// // 编辑模板
        		// self.updateCaseTempInfo();
        		// // 生成用例
        		// self.generateCaseInfo();
        		// // 废弃模板
        		// self.delCaseTempInfo();
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);

		}
		
	};
	module.exports = Query;
});


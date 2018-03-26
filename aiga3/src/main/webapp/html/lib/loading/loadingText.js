define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchNoticeTitle/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	//var Page = Utils.initPage('index');


	//显示查询信息表
	srvMap.add("getNoticeTitleList", pathAlias+"getNoticeTitleList.json", "arch/archNoticeTitle/queryByCondition");		
	var cache = {
			datas : ""
		};
	var init = {
				
		init: function() {			 
			this._render();
		},
		
		_render: function() {
			//查询
			this._getGridList();
		},		
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = $('#gg');
			_cmd = _cmd.replace(/-/g,"/");
			// 设置服务器端
			Rose.ajax.postJson(srvMap.get('getNoticeTitleList'),_cmd,function(json, status){
				var source = $('#TPL_noticeDescriptionTemp').html();
				var template = Handlebars.compile(source);				
        		var tablebtn = _dom.find("[name='noticeText']");
        		tablebtn.html(template(json.data));
        		_dom.find("[name='titleNotice']").click(function(){
        			var value = $(this).val();
        			self.updateDataMain(value, json.data);
        		});
       		
			});
			
						
		},
        
      //更新数据
		updateDataMain: function(Id, json) {
			var self = this;
			var i=0;			
			while(json[i].id != Id){
				i++;
			}
			var data = json[i];
			
			
			var template = Handlebars.compile($('#TPL_noticeTitleFromTemp').html());
			$('#JS_noticeTitleModal').html(template(data));

		},

		
	};
				 
	module.exports = init;
	
});
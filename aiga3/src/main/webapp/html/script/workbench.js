define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");
    // 初始化页面ID，易于拷贝，不需要带'#'
    var Page = Utils.initPage('workbench');

    srvMap.add("getOwnHomeInfo", "", "sys/home/taskInfo");

    var Query = {
        init: function() {
            this._render();
        },
        _render: function() {
            this.getOwnHomeInfo();
        },
        getOwnHomeInfo: function() { // 获取待办任务信息
            var self = this;         
	        Rose.ajax.postJson(srvMap.get('getOwnHomeInfo'), '', function(json, status) {
	            if (status) {
	                var template = Handlebars.compile(Page.findTpl('getOwnHomeInfo'));
	                if(json.data.hasSysRole != true) {
	                	json.data.sysRoleSty = 'show-nothing';
	                }
	                Page.findId('getOwnHomeInfo').html(template(json.data));
	            }
	        });
        }
    }
    module.exports = Query;
});
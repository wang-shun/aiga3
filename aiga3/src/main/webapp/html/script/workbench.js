define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");
    // 初始化页面ID，易于拷贝，不需要带'#'
    var Page = Utils.initPage('workbench');

    srvMap.add("getOwnHomeInfo", "workbench/getOwnHomeInfo.json", "sys/ownHome/info");

    var Query = {
        init: function() {
            this._render();
        },
        _render: function() {
            this.getOwnHomeInfo();
        },
        getOwnHomeInfo: function() { // 获取流水结果信息
            var self = this;
          var template = Handlebars.compile(Page.findTpl('getOwnHomeInfo'));
          Page.findId('getOwnHomeInfo').html(template(''))
            
//            Rose.ajax.postJson(srvMap.get('getOwnHomeInfo'), '', function(json, status) {
//                if (status) {
//                    var template = Handlebars.compile(Page.findTpl('getOwnHomeInfo'));
//                    Page.findId('getOwnHomeInfo').html(template(json.data))
//                }
//            });
        }
    }
    module.exports = Query;
});
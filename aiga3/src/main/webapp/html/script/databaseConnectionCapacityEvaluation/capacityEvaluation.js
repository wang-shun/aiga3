define(function(require, exports, module) {
    //引入icheck的js
    require("icheck/js/icheck.min.js");
    // 通用工具模块
    var Utils = require("global/utils.js");
    var pathAlias = "databaseConnectionCapacityEvaluation/";
    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('capacityEvaluation');
    var init = {
        init: function() {
            $('input').iCheck({
                labelHover : false,
                cursor : true,
                checkboxClass : 'icheckbox_square-blue',
                radioClass : 'iradio_square-blue',
                increaseArea : '20%'
            });
        }
    };
    module.exports = init;
});

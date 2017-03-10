/*
 * 配置数据和 tpl
 * 0 为本地， 1 为远程
 * 本地接口为：8888，当接口为8888时自动调用本地数据
 */
var conf = 1; //控制服务和tpl
var _url = window.location.href;

if(_url.indexOf("8888")>=0){
    conf= 0
}
var srvMap = (function($){
    var srcPref = ["/html/mock/","/aiga3/"];

    var dataArray = [
         {
             //默认示例
             "default":srcPref[conf]+"default/default.json"
         },
         {
            //默认示例
            "default":srcPref[conf]+"default"
         }
    ]
    /*
     *  srvMap.add("default", "data/default.json", "DEFAULT");
     */
    return {
        add: function(uid, mockSrc, srvSrc) {
            dataArray[0][uid] = srcPref[conf] + mockSrc;
            dataArray[1][uid] = srcPref[conf] + srvSrc;
        },
        get: function(uid) {
            return dataArray[conf][uid];
        },
        dataArrays:function(){
            return dataArray[conf];
        }
    };
})(jQuery);

/*
 * 配置 seajs 路径
 *
 */
var timeStamp = '$1?ver=' + new Date().getTime();
var basePref = ["/html/","/aiga3/html/"];
seajs.config({
    'map': [
        [/^(.*\.(?:css|js))(.*)$/i, timeStamp]
    ],
    base: basePref[conf],
    alias: {
        'AdminLTE':'lib/AdminLTE/js/app.js',
        'iCheckCSS':'lib/iCheck/all.css',
        'iCheckJS':'lib/iCheck/icheck.min.js',
        'zTreeCSS':'lib/ztree/3.5.28/css/zTreeStyle/zTreeStyle.css',
        'zTreeJS':'lib/ztree/3.5.28/js/jquery.ztree.core.js',
        'zTreeExcheckJS':'lib/ztree/3.5.28/js/jquery.ztree.excheck.js',
        'datatablesCSS':'lib/datatables/dataTables.bootstrap.css',
        'datatablesJS':'lib/datatables/jquery.dataTables.min.js',
        'msgboxCSS':'lib/xsm-msgbox/0.2.0/css/xsm-msgbox.css',
        'msgboxJS':'lib/xsm-msgbox/0.2.0/js/xsm-msgbox.min.js',
        'slimScroll':'lib/slimScroll/jquery.slimscroll.min.js',
        'bootValidatorCSS':'lib/bootstrapvalidator/dist/css/bootstrapValidator.min.css',
        'bootValidatorJS':'lib/bootstrapvalidator/dist/js/bootstrapValidator.min.js',
        'My97DatePicker':'lib/My97DatePicker/WdatePicker.js',
        'rose':'lib/rose/1.0.0/Rose.src.js',
        'json2':'lib/json2/1.0/json2.js',
        'handlebars':'lib/handlebars/3.0.3/handlebars.js',
        'artDialog':'lib/artDialog/4.1.7/artDialog.js',
        'jquery.serializejson':'lib/jquery.serializejson/jquery.serializejson.js',
        'btable-css':'lib/bootstrap-table/bootstrap-table.css',
        'btable':'lib/bootstrap-table/bootstrap-table.js',
        'btable-locale':'lib/bootstrap-table/locale/bootstrap-table-zh-CN.js'
    },
    preload: [
        'json2',
        'handlebars',
        'rose',
        'iCheckCSS',
        'iCheckJS',
        'datatablesCSS',
        'datatablesJS',
        'msgboxCSS',
        'msgboxJS',
        'slimScroll',
        'bootValidatorCSS',
        'bootValidatorJS',
        'My97DatePicker',
        'zTreeCSS',
        'zTreeJS',
        'AdminLTE',
        'jquery.serializejson',
        'btable-css',
        'btable',
        'btable-locale'
    ],
    // 设置路径，方便跨目录调用
    // var navbar = require('global/navbar'); => 加载的是 http://path/scr/script/global/js/navbar.js
    paths: {
        'script':'script',
        'tpl':'tpl',
        'global': 'script/global',
        'page' : 'script/page'
    }

});



// 加载helpers
seajs.use(['lib/handlebars/3.0.3/helpers'],function(helper){

});




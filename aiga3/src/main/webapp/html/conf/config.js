/*
 * 配置数据和 tpl
 * 0 为本地， 1 为远程
 */
var conf = 0; //控制服务和tpl
var srvMap = (function($){
    var srcPref = ["/html/mock/","index/dst/"];
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
seajs.config({
    'map': [
        [/^(.*\.(?:css|js))(.*)$/i, timeStamp]
    ],
    base: "/html/",
    alias: {
        'AdminLTE':'lib/AdminLTE/js/app.js',
        'iCheckCSS':'lib/iCheck/all.css',
        'iCheckJS':'lib/iCheck/icheck.min.js',
        'zTreeCSS':'lib/ztree/3.5.28/css/zTreeStyle/zTreeStyle.css',
        'zTreeJS':'lib/ztree/3.5.28/js/jquery.ztree.core.js',
        'zTreeExcheckJS':'lib/ztree/3.5.28/js/jquery.ztree.excheck.js',
        'datatablesCSS':'lib/datatables/dataTables.bootstrap.css',
        'datatablesJS':'lib/datatables/jquery.dataTables.min.js',
        'rose':'lib/rose/1.0.0/Rose.src.js',
        'json2':'lib/json2/1.0/json2.js',
        'handlebars':'lib/handlebars/3.0.3/handlebars.js',
        'artDialog':'lib/artDialog/4.1.7/artDialog.js'
    },
    preload: [
        'AdminLTE',
        'iCheckCSS',
        'iCheckJS',
        'datatablesCSS',
        'datatablesJS',
        'zTreeCSS',
        'zTreeJS',
        'zTreeExcheckJS',
        'json2',
        'handlebars',
        'rose'
    ],
    // 设置路径，方便跨目录调用
    // var navbar = require('global/navbar'); => 加载的是 http://path/scr/script/global/js/navbar.js
    paths: {
        'script':'script',
        'tpl':'tpl',
        'global': 'script/global',
        'page' : 'script/page'
    },
});


// 加载helpers
seajs.use(['lib/handlebars/3.0.3/helpers'],function(helper){

});




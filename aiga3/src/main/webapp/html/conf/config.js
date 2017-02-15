/*
 * 配置数据和 tpl
 * 0 为本地， 1 为远程
 */
var conf = 0; //控制服务和tpl
var srvMap = (function($){
	var srcPref = ["mock/",""];
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



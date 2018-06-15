define(function(require, exports, module) {
    require("lib/iCheck/icheck.min.js")
    // 通用工具模块
    var Utils = require("global/utils.js");
    var pathAlias = "databaseConnectionCapacityEvaluation/";
    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('capacityEvaluation');
    //数据库表格
    srvMap.add("getEvalDb", pathAlias+"", "webservice/configure/getEvalDb");
    //查询信息表
    srvMap.add("getEvalution", pathAlias+"", "webservice/configure/getEvalution");
    var init = {
        init: function() {
            this._render();
        },
        _render: function() {
            this._load_table();
            //查询
            this._query_event();
            
            //加载icheck样式
            this._icheck();
        },
        _load_table:function(){
            this._load_table_html(srvMap.get("getEvalDb"));
        },
        _load_table_html:function(url,cmd){
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(url, cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Page.findTpl('tableList'));
                    var tablebtn = Page.findId("tableForm");
                    tablebtn.html(template(json.data));
                }
            });
        },
        _checkbox:function (name) {
                obj=Page.findName(name);
                checkbox_values = [];
                for(k in obj){
                    if(obj[k].checked){
                        var radio_value=this._radio(obj[k].value);
                        console.log(radio_value);
                        var databases=[];
                        databases.push(obj[k].value);
                        databases.push(radio_value);
                        checkbox_values.push(databases);
                    }
                }
                return checkbox_values;
        },
        _radio:function(name){
            var radio_value=$("input[type='radio'][name='"+name+"']:checked").val()
            return radio_value;
        },
        
        _icheck:function(){
        	debugger
        	$('#Page_capacityEvaluation input').iCheck({
    		    checkboxClass: 'icheckbox_minimal-red',
    		    radioClass: 'iradio_minimal-red',
    		    increaseArea: '20%' // optional
    		});
        },

        //绑定查询按钮事件
        _query_event: function() {
            var self = this;
            var _form = Page.findId('queryDataForm');
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.off('click').on('click',function(){
                var cmd = _form.serialize();
                var tpsnumbers = _form.find("[name='tpsnumbers']").val();
                var timetype= _form.find("[name='timetype']").val();
                var serviceCalledTime=_form.find("[name='serviceCalledTime']").val();
                var deployednumbers=_form.find("[name='deployednumbers']").val();
                var databases=self._checkbox("databases");
                cmd=cmd+"&databases="+databases;
                console.log('tpsnumber:'+tpsnumbers);
                console.log('timetype:'+timetype);
                console.log('serviceCalledTime:'+serviceCalledTime);
                console.log('databases:'+databases);
                console.log('deployednumbers:'+deployednumbers);
                console.log('cmd:'+cmd);
                if(tpsnumbers==null||tpsnumbers <=0) {
                    //XMS.msgbox.show('新接入业务tps(系统吞吐量)！需要正数', 'error', 2000);
                	$(".toast__cell").css("display","block");
                	$("#toast__message").text("新接入业务tps(系统吞吐量)！需要正数");
                	setTimeout('$(".toast__cell").fadeOut("slow", function() { $(".toast__cell").css("display","none"); } )',2000);
                    return
                }
                if(serviceCalledTime==null||serviceCalledTime<=0){
                    XMS.msgbox.show('服务调用时长需要正数！', 'error', 2000);
                    $(".toast__cell").css("display","block");
                    $("#toast__message").text("服务调用时长需要正数！");
                	setTimeout('$(".toast__cell").fadeOut("slow", function() { $(".toast__cell").css("display","none"); } )',2000);
                    return
                }
                self._getGridList(cmd);
            });
        },
        // 查询表格数据
        _getGridList: function(cmd) {
            var self = this;
            var _cmd = '';
            if(cmd){
                _cmd = cmd;
            }
            var _dom = Page.findId('evaluationList');
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getEvalution'),_cmd,function(json, status){
                if(status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('evaluationList'));
                    var tablebtn = _dom.find("[name='content']");
                    tablebtn.html(template(json.data));
                    Utils.eventTrClickCallback(_dom);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });
        }
    };
    module.exports = init;
});

define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");
    var pathAlias = "databaseConnectionCapacityEvaluation/";
    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('capacityEvaluation');
    //查询单库折算系数
    srvMap.add('getConversionFactor',pathAlias+"","webservice/configure/getConversionFactor");
    //数据库表格
    srvMap.add("getEvalDb", pathAlias+"", "webservice/configure/getEvalDb");
    //查询信息表
    srvMap.add("getEvalution", pathAlias+"", "webservice/configure/getEvalution");
    //单实例理论并发数评估提示语
    srvMap.add("getMarkedWord",pathAlias+"", "webservice/configure/getMarkedWord");
    var init = {
        init: function() {
            this._render();
        },
        _render: function() {
            this._query_event();
            //查询
            this._load_table();
        },

        _band_table_btn: function(cf, name,type) {
            var self = this;
            var index = 0;
            if(type == 'update') {
               var template = Handlebars.compile(Page.findTpl('primaryUpdateFrom'));
                var subData={'cf':cf};
                Page.findId('updateModal').html(template(subData));
                var _modal = Page.findId('primaryUpdateModal');
                _modal.modal('show');
                Utils.setSelectData(_modal);

                _modal.on('shown.bs.modal', function () {
                    //修改保存按钮
                    var saveBtn = _modal.find("[name='save']");
                    saveBtn.off('click').confirm({
                        title:'提示',
                        content:'确认修改',
                        confirmButtonClass:'btn-primary',
                        confirmButton: '确认',
                        cancelButton: '取消',
                        confirm: function(){
                            var num =Page.findId('firUpdateForm').find("[name='cf']").val();
                            if(num ==null||num==undefined||num==''||parseFloat(num)<=0||parseFloat(num)>1){
                                XMS.msgbox.show('修改失败,单库折算系数必须为(0,1]区间内！', 'error', 2000);
                                return;
                            }
                            $('#Page_capacityEvaluation span[name='+name+']').text(num);
                            _modal.modal('hide');
                            XMS.msgbox.show('修改成功！', 'success', 2000);
                        },
                        cancel:function(){}
                    });
                });
            }
        },
        _load_table:function(){
            this._load_table_html();
        },
        _radio_change:function () {
            $("#Page_capacityEvaluation input:radio").on('ifChecked', function(event){
                var center=$(this).attr("data-radio");
                var value=$(this).val();
                var _cmd='&center='+center+'&radiovalue='+value;
                Rose.ajax.postJson(srvMap.get('getConversionFactor'),_cmd,function(json, status){
                    if(status) {
                        window.XMS.msgbox.hide();
                        var num;
                        for (var key in json.data) {
                            if (key.indexOf("conversionFactor") >= 0) {
                                num = json.data[key];
                            }
                        }
                        $('#Page_capacityEvaluation span[name='+center+']').text(num)
                    } else {
                        XMS.msgbox.show(json.retMessage, 'error', 2000);
                    }
                });
            });
        },
        _load_table_html:function(){
            var self = this;
            var _dom = Page.findId('evalDbList');
            Rose.ajax.postJson(srvMap.get("getEvalDb"), '', function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('tableList'));
                    var tablebtn = _dom.find("[name='contentdb']");
                    tablebtn.html(template(json.data));
                    var _data = json.data;
                    $('#Page_capacityEvaluation input').iCheck({
                        labelHover : false,
                        cursor : true,
                        checkboxClass : 'icheckbox_square-blue',
                        radioClass : 'iradio_square-green',
                        increaseArea : '20%'
                    });
                    self._radio_change();
                    tablebtn.find("[name='changebutton']").off('click').on('click', function() {
                        self._band_table_btn($('#Page_capacityEvaluation span[name='+$(this).attr("data-source")+']').text(),$(this).attr("data-source"),"update");
                    });
                }else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
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
                        var text= $('#Page_capacityEvaluation span[name='+obj[k].value+']').text();
                        if(text ==null||text==undefined||text==''){
                            return -1;
                        }
                        databases.push(obj[k].value);
                        databases.push(radio_value);
                        databases.push(text);
                        checkbox_values.push(databases);
                    }
                }
                return checkbox_values;
        },
        _radio:function(name){
            var radio_value=$("input[type='radio'][name='"+name+"']:checked").val();
            return radio_value;
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
                var dbs=self._checkbox("databases");
                if(dbs!==null&&dbs!==undefined&&dbs=='-1'){
                    $(".toast__cell").css("display","block");
                    $("#toast__message").text("所选数据库对应的单库折算系数不能为空！");
                    setTimeout('$(".toast__cell").fadeOut("slow", function() { $(".toast__cell").css("display","none"); } )',2000);
                    $(".toast__close").click(function(){
                        $(".toast__cell").css("display","none");
                    });
                    return;
                }
                cmd=cmd+"&dbs="+dbs+"&tpsnumbers="+tpsnumbers+"&timetype="+timetype+"&serviceCalledTime="+serviceCalledTime+
                   "&deployednumbers="+deployednumbers;
                console.log("cmd:"+cmd);
                if(tpsnumbers==null||tpsnumbers <=0) {
                	$(".toast__cell").css("display","block");
                	$("#toast__message").text("请输入新接入业务tps(系统吞吐量)！");
                	setTimeout('$(".toast__cell").fadeOut("slow", function() { $(".toast__cell").css("display","none"); } )',2000);
                	$(".toast__close").click(function(){
                		$(".toast__cell").css("display","none");
                	});
                    return
                }
                if(serviceCalledTime==null||serviceCalledTime<=0){
                    $(".toast__cell").css("display","block");
                    $("#toast__message").text("请输入服务调用时长！");
                	setTimeout('$(".toast__cell").fadeOut("slow", function() { $(".toast__cell").css("display","none"); } )',2000);
                	$(".toast__close").click(function(){
                		$(".toast__cell").css("display","none");
                	});
                    return
                }
                if(deployednumbers==null||deployednumbers<=0){
                    $(".toast__cell").css("display","block");
                    $("#toast__message").text("请输入新增部署实例数！");
                    setTimeout('$(".toast__cell").fadeOut("slow", function() { $(".toast__cell").css("display","none"); } )',2000);
                    $(".toast__close").click(function(){
                		$(".toast__cell").css("display","none");
                	});
                    return
                }
                self._markedWord(cmd);
                self._getGridList(cmd);
            });
        },
        //单实例理论并发数评估提示语
        _markedWord:function (cmd) {
            var self = this;
            var _cmd = '';
            if(cmd){
                _cmd = cmd;
            }
            var _dom = Page.findId('evaluationList');
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get('getMarkedWord'),_cmd,function(json, status){
                if(status) {
                    window.XMS.msgbox.hide();
                    var _value="";
                    for (var key in json.data) {
                        if (key.indexOf("markedWord") >= 0) {
                            _value = json.data[key];
                        }
                    }
                    $("#markedword").text(_value);
                    Utils.eventTrClickCallback(_dom);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
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
                    var _data = json.data;
                    Utils.eventTrClickCallback(_dom);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });
        }
    };
    module.exports = init;
});

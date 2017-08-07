define(function(require,exports,module){
	// 通用工具模块
	var Utils = require("global/utils.js");
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('flow');

	//获取流水结果信息 入参：planDate=2017-05-04
    srvMap.add("getComplimeResultInfo", "flow/getComplimeResultInfo.json", "sys/plan/getComplimeResultInfo");
    //获取信息 入参：planDate=2017-05-04&sysName=CRM&complimeNum=1
    srvMap.add("getComplimeInfo","flow/getComplimeInfo.json", "sys/plan/getComplimeInfo");
    //获取某一次信息 入参：planDate=2017-05-04&sysName=CRM&complimeNum=1
    srvMap.add("getComplimeNumInfo","flow/getComplimeNumInfo.json", "sys/plan/getComplimeInfo");
    //编译 入参：planDate=2017-05-04&sysNames=4ASSO
    srvMap.add("NaCodePathCompileToBmc","flow/NaCodePathCompileToBmc.json", "sys/plan/NaCodePathCompileToBmc");

    var Data = {
        planDate:'2017-05-04' //从上个页面获取
    }

    var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			this.getComplimeResultInfo();
            this.getComplimeInfo();
		},
        getComplimeResultInfo: function() { // 获取流水结果信息
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            var cmd = "planDate="+Data.planDate;
            Rose.ajax.getJson(srvMap.get('getComplimeResultInfo'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('getComplimeResultInfo'));
                    Page.findId('getComplimeResultInfo').append(template(json.data))
                }
            });
        },
        getComplimeInfo: function() { // 获取信息
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            var cmd = "planDate="+Data.planDate;
            Rose.ajax.getJson(srvMap.get('getComplimeInfo'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('getComplimeInfo'));
                    Page.findId('getComplimeInfo').append(template(json.data));

                    //绑定编译
                    self.NaCodePathCompileToBmc();
                    self.getComplimeNumInfo();
                }
            });
        },
        NaCodePathCompileToBmc: function() { //编译
            var self = this;
            var _compile = Page.findName("compile");
            _compile.unbind('click');
            _compile.bind('click', function() {
                var _selfDom = $(this);
                var cmd = "planDate="+Data.planDate+"&sysNames="+_selfDom.data("sysnames");
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.postJson(srvMap.get('NaCodePathCompileToBmc'), cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('编译成功！', 'success', 2000)
                        var template = Handlebars.compile(Page.findTpl('getComplimeInfo'));
                        var _content = _selfDom.parents("div.flow-content");
                        _content.replaceWith(template(json.data));
                        //重新绑定编译、流水查询
                        self.NaCodePathCompileToBmc();
                        self.getComplimeNumInfo();
                    }
                });
            });
        },
        getComplimeNumInfo: function() { //查询某一次流水
            var self = this;
            var _complimeNum = Page.findName("complimeNum");
            _complimeNum.find("li").unbind('click');
            _complimeNum.find("li").bind('click', function() {
                var _selfDom = $(this);
                var _complimeNum = _selfDom.text();
                var _sysNames = _selfDom.parents("div.flow-content").find("[name='compile']").data("sysnames");
                var cmd = "planDate="+Data.planDate+"&sysNames="+_sysNames+"&complimeNum="+_complimeNum;
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.postJson(srvMap.get('getComplimeNumInfo'), _complimeNum, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.hide();
                        var template = Handlebars.compile(Page.findTpl('getComplimeInfo'));
                        var _content = _selfDom.parents("div.flow-content");
                        _content.replaceWith(template(json.data));
                        //重新绑定编译、流水查询
                        self.NaCodePathCompileToBmc();
                        self.getComplimeNumInfo();
                    }
                });
            });
        }
	}
    Handlebars.registerHelper("creatPageList", function(maxNum,activeNum) {
        var _html = '';
        var _maxNum = parseInt(maxNum);
        var _activeNum = parseInt(activeNum);
        if (_maxNum > 0) {
            for (var i=1;i<=_maxNum;i++){
                if(i ==_activeNum){
                    _html+='<li class="active">'+i+'</li>';
                } else {
                    _html+='<li>'+i+'</li>';
                }
            }
        }
        return _html;
    });
    Handlebars.registerHelper("addStatusClass", function(result,status) {
        if(result!="false"){
            if (status == "") {
                return "status-null";
            } else if (status == "RUNNING") {
                return "status-running";
            }
        }else{
            return "status-error";
        }
    });
    Handlebars.registerHelper("stringTrim", function(value) {
       return value.trimBlanks();
    });
    module.exports = Query;
});


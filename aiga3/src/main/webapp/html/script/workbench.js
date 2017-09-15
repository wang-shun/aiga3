define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");
    var Sidebar = require('global/sidebar.js');
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
	            	var data = json.data;
	            	if(data.hasSysRole != 'true' && data.hasQuesRole != 'true') {
	                	data.dealshow = 'show-nothing';          		
	            	} else {
		                if(data.hasSysRole != 'true') {
		                	data.sysRoleSty = 'show-nothing';
		                }
		                if(data.hasQuesRole != 'true') {
		                	data.quesRoleSty = 'show-nothing';
		                }
	            	}
	                Page.findId('getOwnHomeInfo').html(template(data));
	                if(data.hasSysRole == 'true') {
	                	data.sysRoleSty = 'show-nothing';
	                }
	                Page.find(".info-box-icon").off('click').on('click',function() {
	                	var number = $(this).attr("number");
	                	var name = $(this).attr("name");
	                	if(number < 1 || typeof(name) == 'undefined') {
	                		return
	                	}
	                	if(name == 'applyFirst' || name == 'applySecond' || name == 'applyThird') {
	                		var ext = name == 'applyFirst'? 1: name== 'applySecond' ? 2: name == 'applyThird' ? 3:0;
	                        Sidebar.creatTab({
	                            id: '118',
	                            name: '架构分级认定',
	                            href: 'view/sysArchiBaselineManage/archiGradingManage/archiGradingIdentified.html',
	                            cmd: 'state=申请&ext1='+ext+'&applyUser='+data.userName
	                        });
	                	} else if ( name == 'dealFirst'|| name == 'dealSecond'|| name == 'dealThird') {
	                		var ext = name == 'dealFirst'? 1: name== 'dealSecond' ? 2: name == 'dealThird' ? 3:0;
	                        Sidebar.creatTab({
	                            id: '118',
	                            name: '架构分级认定',
	                            href: 'view/sysArchiBaselineManage/archiGradingManage/archiGradingIdentified.html',
	                            cmd: 'state=申请&ext1='+ext
	                        });
	                	} else if (name == 'applyIndentyQues' || name == 'applyResolveQues' || name == 'applyCloseQues') {
	                		var indentyQues = 'sysVersion=待确认&reportor='+name;
	                		var resolveQues = 'sysVersion=待确认&state=未解决&reportor='+name;
	                		var closeQues = 'sysVersion=待确认&state=解决中&reportor='+name;
	                        Sidebar.creatTab({
	                            id: '136',
	                            name: '架构问题检索',
	                            href: 'view/archiQuesManage/quesSearch.html',
	                            cmd: name == 'applyIndentyQues'? indentyQues: name== 'applyResolveQues' ? resolveQues: name == 'applyCloseQues' ? closeQues:''
	                        });
	                	} else if (name == 'dealIndentyQues' || name == 'dealResolveQues' || name == 'dealCloseQues') {
	                		var indentyQues = 'sysVersion=待确认&identifiedName='+name;
	                		var resolveQues = 'sysVersion=待确认&state=未解决&solvedName='+name;
	                		var closeQues = 'sysVersion=待确认&state=解决中&solvedName='+name;
	                        Sidebar.creatTab({
	                            id: '135',
	                            name: '架构问题查询',
	                            href: 'view/archiQuesManage/quesRending.html',
	                            cmd: name == 'dealIndentyQues'? indentyQues: name== 'dealResolveQues' ? resolveQues: name == 'dealCloseQues' ? closeQues:''
	                        });
	                	} else {
	    		        	XMS.msgbox.show("没有配置跳转路径", 'error', 1000);
	                	}
	                });
	            }
	        });
        }
    }
    module.exports = Query;
});
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
	                            cmd: 'state=申请&ext1='+ext+"&applyUser=admin"
	                        });
	                	} else if ( name == 'dealFirst'|| name == 'dealSecond'|| name == 'dealThird') {
	                        Sidebar.creatTab({
	                            id: '135',
	                            name: '架构问题查询',
	                            href: 'view/archiQuesManage/quesRending.html',
	                            cmd: ''
	                        });
	                	} else if (name == 'applyIndentyQues' || name == 'applyResolveQues' || name == 'applyCloseQues') {
	                        Sidebar.creatTab({
	                            id: '135',
	                            name: '架构问题查询',
	                            href: 'view/archiQuesManage/quesRending.html',
	                            cmd: ''
	                        });
	                	} else if (name == 'dealIndentyQues' || name == 'dealResolveQues' || name == 'dealCloseQues') {
	                        Sidebar.creatTab({
	                            id: '135',
	                            name: '架构问题查询',
	                            href: 'view/archiQuesManage/quesRending.html',
	                            cmd: ''
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
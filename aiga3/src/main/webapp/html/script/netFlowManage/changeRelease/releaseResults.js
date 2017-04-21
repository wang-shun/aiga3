define(function(require, exports, module) {

    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('changeRelease');

    // 路径重命名
    var pathAlias = "netFlowManage/changeRelease/releaseResults/";

     // 下拉菜单获取所有变更计划
    srvMap.add("getOnlinePlanList", pathAlias + "getOnlinePlanList.json", "sys/cache/changePlan");
    //查询发布结果表格
    srvMap.add("getReleaseResultsList", pathAlias + "getReleaseResultsList.json", "release/report/list");
    //新增
    srvMap.add("addReleaseResults", pathAlias + "addReleaseResults.json", "online/release/messagesave");
    //数据库执行异常
    srvMap.add("exlist", pathAlias + "addReleaseResults.json", "database/execution/exlist");
    //数据库脚本执行进程
    srvMap.add("process", pathAlias + "addReleaseResults.json", "database/scriptExecution/process");
    //在线系统发布准备
    srvMap.add("release", pathAlias + "addReleaseResults.json", "online/system/release");
    //在线系统发布阶段
    srvMap.add("releasestage", pathAlias + "addReleaseResults.json", "online/system/releasestage");
    //测试进程
    srvMap.add("testProcess", pathAlias + "addReleaseResults.json", "online/test/process");
    //数据库执行异常解析
    srvMap.add("exception", pathAlias + "addReleaseResults.json", "database/execution/exception");
    //数据库脚本执行进程解析
    srvMap.add("processexcels", pathAlias + "addReleaseResults.json", "database/scriptexecution/processexcel");
    //在线系统发布准备解析
    srvMap.add("releaseexcel", pathAlias + "addReleaseResults.json", "online/system/releaseexcel");
    //在线系统发布阶段解析
    srvMap.add("releasestageexcel", pathAlias + "addReleaseResults.json", "online/system/releasestageexcel");
    //测试进程解析
    srvMap.add("processexcel", pathAlias + "addReleaseResults.json", "online/test/processexcel");


    var Data = {
        queryListCmd: null
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.getReleaseResultsList();
            // 初始化查询表单
            this.queReleaseResults();
        },
        //发布结果表格显示
        getReleaseResultsList: function(cmd) {
            var self = this;
            var _cmd = '';
            if (cmd) {
                var _cmd = cmd;
            }
            // Data.queryListCmd = _cmd;
            var _dom = Page.findId('releaseResultsList');
            var _domPagination = _dom.find("[name='pagination']");
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getReleaseResultsList'), _cmd, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
                console.log(Page);
                var template = Handlebars.compile(Page.findTpl('releaseList'));

                _dom.find("[name='content']").html(template(json.data.content));
                self.addReleaseResults();
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        queReleaseResults: function() {
            var self = this;
            var _form = Page.findId('queryChangeReleaseForm');
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getReleaseResultsList(cmd);
            });
        },
        //新增
        addReleaseResults: function() {
            var self = this;
            var _dom = Page.findId('releaseResultsList');
            // Utils.setSelectData(_form);
            var _addBtn = _dom.find("[name='add']");
            _addBtn.unbind('click');
            _addBtn.bind('click', function() {
                var data = Utils.getRadioCheckedRow(_dom);
                if (data) {
                    var cmd = 'planId=' + data.planId;
                    var _modal = Page.findModal('getChangeRelease');
                    // 显示弹框
                    _modal.modal('show').on('shown.bs.modal', function() {
                    	//保存
                    	self.saveReleaseResults(data.planId);
                    	self.queTable1(data.planId);
                    	self.queTable2(data.planId);
                    	self.queTable3(data.planId);
                    	self.queTable4(data.planId);
                    	self.queTable5(data.planId);
                    	self.daoRu(data.planId);
                    });

                    
                }
            });
        },
        //保存
        saveReleaseResults: function(planId) {
            var self = this;
            var _form = Page.findModalCId('addReleaseMessageForm');
            console.log(_form.length)
            var _saveBtn = _form.find("[name='save']");
            console.log(_saveBtn);
            _saveBtn.unbind('click');
            _saveBtn.bind('click', function() {
                var cmd = _form.serialize();
                cmd = cmd + "&planId=" + planId;
                console.log(cmd)
                Rose.ajax.postJson(srvMap.get('addReleaseResults'), cmd, function(json, status) {
                    if (status) {
                        // 添加用户成功后，刷新用户列表页
                        XMS.msgbox.show('添加成功！', 'success', 2000)
                        setTimeout(function() {
                            self.getReleaseResultsList();
                        }, 1000)
                    }
                });
            });
        },
        //tab页显示
	    queTable1 : function(planId){
	    	var self = this;
	    	
	            var _cmd = 'planId='+planId;
	            // Data.queryListCmd = _cmd;
	            var _dom = Page.findModalCId('dataBaseTab_1');
	            var _content = _dom.find("[name='content']");
	            var _domPagination = _dom.find("[name='pagination']");
	            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	            // 设置服务器端分页
	            Utils.getServerPage(srvMap.get('exlist'), _cmd, function(json) {
	                window.XMS.msgbox.hide();

	                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
	                var template = Handlebars.compile(Page.findTpl('dataBaseTab_1'));
	                _content.html(template(json.data.content));
	                Utils.eventTrClickCallback(_dom);
	            }, _domPagination);
	    },
	    //tab页显示
	    queTable2 : function(planId){
	    	var self = this;
	            var _cmd = 'planId='+planId;
	            // Data.queryListCmd = _cmd;
	            var _dom = Page.findModalCId('dataBaseTab_2');
	            var _content = _dom.find("[name='content']");
	            var _domPagination = _dom.find("[name='pagination']");
	            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	            // 设置服务器端分页
	            Utils.getServerPage(srvMap.get('process'), _cmd, function(json) {
	                window.XMS.msgbox.hide();

	                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
	                var template = Handlebars.compile(Page.findTpl('dataBaseTab_2'));
	                _content.html(template(json.data.content));
	                Utils.eventTrClickCallback(_dom);
	            }, _domPagination);
	    },
	    //tab页显示
	    queTable3 : function(planId){
	    	var self = this;
	            var _cmd = 'planId='+planId;
	            // Data.queryListCmd = _cmd;
	            var _dom = Page.findModalCId('dataBaseTab_3');
	            var _content = _dom.find("[name='content']");
	            var _domPagination = _dom.find("[name='pagination']");
	            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	            // 设置服务器端分页
	            Utils.getServerPage(srvMap.get('release'), _cmd, function(json) {
	                window.XMS.msgbox.hide();

	                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
	                var template = Handlebars.compile(Page.findTpl('dataBaseTab_3'));
	                _content.html(template(json.data.content));
	                Utils.eventTrClickCallback(_dom);
	            }, _domPagination);
	    },
	    //tab页显示
	    queTable4 : function(planId){
	    	var self = this;
	            var _cmd = 'planId='+planId;
	            // Data.queryListCmd = _cmd;
	            var _dom = Page.findModalCId('dataBaseTab_4');
	            var _content = _dom.find("[name='content']");
	            var _domPagination = _dom.find("[name='pagination']");
	            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	            // 设置服务器端分页
	            Utils.getServerPage(srvMap.get('releasestage'), _cmd, function(json) {
	                window.XMS.msgbox.hide();

	                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
	                var template = Handlebars.compile(Page.findTpl('dataBaseTab_4'));
	                _content.html(template(json.data.content));
	                Utils.eventTrClickCallback(_dom);
	            }, _domPagination);
	    },
	    //tab页显示
	    queTable5 : function(planId){
	    	var self = this;
	            var _cmd = 'planId='+planId;
	            // Data.queryListCmd = _cmd;
	            var _dom = Page.findModalCId('dataBaseTab_5');
	            var _content = _dom.find("[name='content']");
	            var _domPagination = _dom.find("[name='pagination']");
	            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
	            // 设置服务器端分页
	            Utils.getServerPage(srvMap.get('testProcess'), _cmd, function(json) {
	                window.XMS.msgbox.hide();

	                // 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
	                var template = Handlebars.compile(Page.findTpl('dataBaseTab_5'));
	                _content.html(template(json.data.content));
	                Utils.eventTrClickCallback(_dom);
	            }, _domPagination);
	    },
	    //导入文件
	    daoRu:function(planId){
	    	var self = this;
            var _form = Page.findModalCId('fileUploadForm');
            console.log(_form.length)
            var _saveBtn = _form.find("[name='add']");
            _saveBtn.unbind('click');
            _saveBtn.bind('click', function() {
            	var a=_form.find("[name='environmentType']").val();
            	var cmd = {
            			"file":_form.find("[name='fileName']")[0].files[0],
            			"planId":planId,
            	}
            	console.log(_form.find("[name='fileName']"));
            	switch(a){
            		case "1":
            			var task = srvMap.get('exception');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "2":
            			var task = srvMap.get('processexcels');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "3":
            			var task = srvMap.get('releaseexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "4":
            			var task = srvMap.get('releasestageexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            		case "5":
            			var task = srvMap.get('processexcel');
            			self.jieko(task,cmd,a,planId)
            			break;
            	}
                
            });
            
	    },
	    jieko : function(task,cmd,a,planId){
	    	var self = this;
	    	$.ajaxUpload({
                url: task,
                data: cmd,
                success: function(data, status, xhr) {
                    console.log(data);
                    if (status) {
                        window.XMS.msgbox.show('发送成功！', 'success', 2000);
                        setTimeout(function() {
                            switch(a){
			            		case "1":
			            			self.queTable1(planId);
			            			break;
			            		case "2":
			            			self.queTable2(planId);
			            			break;
			            		case "3":
			            			self.queTable3(planId);
			            			break;
			            		case "4":
			            			self.queTable4(planId);
			            			break;
			            		case "5":
			            			self.queTable5(planId);
			            			break;
			            	}
                        }, 1000)
                    }
                }
            });
        },
    };
    module.exports = Query;
});

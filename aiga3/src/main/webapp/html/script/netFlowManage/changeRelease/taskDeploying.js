define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('taskDeploying');

    // 路径重命名
    var pathAlias = "netFlowManage/changeRelease/taskDeploying/";

    // 下拉菜单获取所有变更计划
    srvMap.add("getOnlinePlanList", pathAlias + "getOnlinePlanList.json", "sys/cache/changePlan");
    //获取验收任务列表
    srvMap.add("getDeployingTaskList", pathAlias + "getDeployingTaskList.json", "accept/onlineTask/list");
    //保存回归子任务
    srvMap.add("saveDeployingTask", pathAlias + "retMessage.json", "accept/onlineTask/save");

    // // 模板对象
    // var Tpl = {
    //     getOnlineTaskList: $("#TPL_getOnlineReviewTaskList").html(),
    //     getOnlineTaskDistributeList: $("#TPL_getOnlineReviewTaskDistributeList").html()

    // };

    // // 容器对象
    // var Dom = {
    //     queryOnlineTaskForm: '#JS_queryOnlineTaskForm',
    //     getOnlineTaskList: '#JS_getOnlineTaskList',
    //     getOnlineTaskDistributeModal: '#JS_getOnlineTaskDistributeModal',
    //     addOnlineTaskDistributeForm: '#JS_addOnlineTaskDistributeForm',
    //     getOnlineTaskDistributeList: '#JS_getOnlineTaskDistributeList'

    // };

    var Data = {
        queryListCmd: null,
        onlinePlan: null,
        opreation: "new"
    }

    var Query = {
        init: function() {
            // 默认查询所有
            this.getDeployingTaskList();
            // 初始化查询表单
            this.queryDeployingTaskForm();
            //注册helper
            this.registerHelper();
        },

        // 按条件查询
        queryDeployingTaskForm: function() {
            var self = this;
            var _form = Page.findId('queryDeployingTaskForm');
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getDeployingTaskList(cmd);
            });
        },
        // 查询计划变更列表
        getDeployingTaskList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getDeployingTaskList');
            //真分页时解开
            var _domPagination = _dom.find("[name='pagination']");

            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Utils.getServerPage(srvMap.get('getDeployingTaskList'), _cmd, function(json) {
                var template = Handlebars.compile(Page.findTpl('getDeployingTaskList'));
                _dom.find("[name='content']").html(template(json.data.content));
                // Utils.setSelectData(_dom);
                var da = json.data.content;
                var i = 0
                _dom.find("tbody").find("tr").each(function() {
                    var tdArr = $(this).children();
                    tdArr.eq(1).find("select").val(da[i].deployState);
                    i++;
                });
                Utils.eventTrClickCallback(_dom)
                    //设置滚动条
                    //self.initPaging(_dom, true);
            }, _domPagination);

            // 保存监控任务
            self.saveDeployingTask();

        },
        // 保存监控任务
        saveDeployingTask: function() {
            var self = this;
            var _dom = Page.findId('getDeployingTaskList');
            var _save = _dom.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var data = Utils.getCheckboxCheckedRow(_dom);
                if (data) {
                    var _dataArray = self.getSendCheckedRow(_dom);
                    var cmd = _dataArray;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get("saveDeployingTask"), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('保存成功', 'success', 2000);
                            setTimeout(function() {
                                self.getDeployingTaskList();
                            }, 1000)
                        }
                    });
                }
            })
        },

        //获取选中子任务的数据
        getSendCheckedRow: function(obj) {
            var dataArray = []
            obj.find("input[type='checkbox']:checked").each(function() {
                var data = {};
                $(this).parents("tr").find("input,select").each(function() {
                    var key = $(this).attr("name");
                    var value = $(this).val();
                    data[key] = value;
                });
                dataArray.push(data);
            });
            return dataArray;
        },
        // 事件：假分页(滚动条)
        initPaging: function(obj, scrollX) {
            obj.find("table").DataTable({
                "paging": false,
                "lengthChange": false,
                "searching": false,
                "ordering": false,
                "autoWidth": false,
                "info": false,
                "language": {
                    "emptyTable": "暂无数据...",
                    "infoEmpty": "第0-0条，共0条"
                },
                "scrollX": scrollX
            });
        },
        registerHelper: function() {
            Handlebars.registerHelper('getMonitorState', function(value, fn) {
                if (value == "1") {
                    return "未监控";
                }
                if (value == "2") {
                    return "已监控";
                }
                if (value == "3") {
                    return "取消监控";
                }
            });
        },
    };
    module.exports = Query;
});

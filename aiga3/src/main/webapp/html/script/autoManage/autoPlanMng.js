define(function(require, exports, module) {
    // 路径重命名
    var pathAlias = "autoManage/autoPlanMng/";
    var Utils = require("global/utils.js");
    var planTag; //计划编号

    // 计划列表显示
    srvMap.add("getAutoPlanList", pathAlias + "autoPlanList.json", "sys/autoPlan/queryList");
    //计划保存接口
    srvMap.add("saveAutoPlan", pathAlias + "autoPlanList.json", "sys/autoPlan/save");
    //删除计划接口
    srvMap.add("deleAutoPlan", pathAlias + "autoPlanList.json", "sys/autoPlan/delete");
    //关联用例
    srvMap.add("linkCase", pathAlias + "autoPlanList.json", "sys/autoPlan/connectCase");
    //关联用例组
    srvMap.add("linkCaseGroup", pathAlias + "autoPlanList.json", "sys/autoPlan/connectGroup");
    //关联用例集
    srvMap.add("linkCaseCollect", pathAlias + "autoPlanList.json", "sys/autoPlan/connectCollect");

    //查询未关联用例
    srvMap.add("unLinkCaseList", pathAlias + "unLinkCaseList.json", "sys/autoPlan/queryUnconnectCase");
    //查询已关联用例
    srvMap.add("linkCaseList", pathAlias + "unLinkCaseList.json", "sys/autoPlan/queryConnectCase");
    //取消关联用例
    srvMap.add("deleLinkCase", pathAlias + "autoPlanList.json", "sys/autoPlan/deleteConnectCase");

    //查询未关联用例组
    srvMap.add("unLinkCaseGroupList", pathAlias + "unLinkCaseGroupList.json", "sys/autoPlan/queryUnconnectGroup");
    //查询关联用例组
    srvMap.add("linkCaseGroupList", pathAlias + "unLinkCaseGroupList.json", "sys/autoPlan/queryConnectGroup");
    //取消关联用例组
    srvMap.add("deleLinkCaseGroup", pathAlias + "autoPlanList.json", "sys/autoPlan/deleteConnectGroup");

    //查询未关联用例集
    srvMap.add("unLinkCaseCollectList", pathAlias + "unLinkCaseCollectList.json", "sys/autoPlan/queryUnconnectCollect");
    //查询关联用例集
    srvMap.add("linkCaseCollectList", pathAlias + "unLinkCaseCollectList.json", "sys/autoPlan/queryConnectCollect");
    //取消关联用例集
    srvMap.add("deleLinkCaseCollect", pathAlias + "autoPlanList.json", "sys/autoPlan/deleteConnectCollect");


    // 模板对象
    var Tpl = {
        getAutoPlanList: require('tpl/autoManage/autoPlanMng/autoPlanList.tpl'), //计划列表
        modalAutoPlanInfo: require('tpl/autoManage/autoPlanMng/modalAutoPlanInfo.tpl'), //modal新增
        modalLinkCase: require('tpl/autoManage/autoPlanMng/modalLinkCase.tpl'), //modal关联
        modalNewTaskInfo: require('tpl/autoManage/autoPlanMng/modalNewTaskInfo.tpl'), //modal生成任务

        autoCaseList: require('tpl/autoManage/autoPlanMng/autoCaseList.tpl'), //用例列表
        caseGroupList: require('tpl/autoManage/autoPlanMng/caseGroupList.tpl'), //用例组列表
        caseCollectList: require('tpl/autoManage/autoPlanMng/caseCollectList.tpl'), //用例集列表

        machineList: require('tpl/autoManage/autoPlanMng/machineList.tpl'), //主机列表

    };

    // 容器对象
    var Dom = {
        getAutoPlanList: '#JS_getAutoPlanList', //table对象
        queryPlanForm: '#JS_queryPlanForm', //查询表单
        addPlan: '#JS_addPlan',
        planInfoForm: '#Js_planInfoForm',
        modalPlanForm: '#modal_autoPlanForm',

    };


    var Init = {
        init: function() {
            this._render();
        },
        _render: function() {
            this.getPlanList();
            this.queryAutoPlan();
            this.addAutoPlan();
            this.deleAutoPlan();
        },
        getPlanList: function(cmd) {
            var self = this;
            Rose.ajax.getJson(srvMap.get('getAutoPlanList'), cmd, function(json, status) {
                if (status) {
                    var template = Handlebars.compile(Tpl.getAutoPlanList);
                    console.log(json.data)
                    $(Dom.getAutoPlanList).html(template(json.data.content));
                    self.eventClickChecked($(Dom.getAutoPlanList));
                    Utils.eventDClickCallback($(Dom.getAutoPlanList), function() {
                        self.editAutoPlan();
                    });
                    // Utils.setScroll($(Dom.getAutoPlanList),380px);
                }
            });
        },
        queryAutoPlan: function() {
            var self = this;
            var _form = $(Dom.queryPlanForm);
            // 表单提交
            _form.find('button[name="submit"]').bind('click', function() {

                    var cmd = $(Dom.queryPlanForm).serialize();
                    self.getPlanList(cmd);
                })
                // 表单重置
            _form.find('button[name="reset"]').bind('click', function() {
                $("#queryPlanName").val('');
                $("#query_runType").val('');
                $("#createTime").val('');
                $("#updateTime").val('');
            });
        },
        //新增计划
        addAutoPlan: function() {
            var self = this;
            $(Dom.addPlan).bind('click', function() {
                var time = self.getDate();
                $(Dom.modalPlanForm).find("[name='planTag']").val(time.planTag);
                $(Dom.modalPlanForm).find("[name='planId']").val('');
                $(Dom.modalPlanForm).find("[name='planName']").val('');
                $(Dom.modalPlanForm).find("[name='cycleType']").val('');
                $(Dom.modalPlanForm).find("[name='runType']").val('');
                $(Dom.modalPlanForm).find("[name='machineIp']").val('');                
            });
            $(Dom.modalPlanForm).find("button[name='submit']").bind('click', function() {
                var cmd = $(Dom.planInfoForm).serialize();
                self.saveAutoPlan(cmd);
                $(Dom.modalPlanForm).modal('hide');
            });
            $(Dom.modalPlanForm).find("button[name='cancel']").bind('click', function() {
                $(Dom.modalPlanForm).modal('hide');
            });
        },
        //查看修改计划
        editAutoPlan: function() {
            var data = this.getPlanInfo();
            if (data) {
                $(Dom.modalPlanForm).modal('show');
                $(Dom.modalPlanForm).find("[name='planId']").val(data.planId);
                $(Dom.modalPlanForm).find("[name='planTag']").val(data.planTag);
                $(Dom.modalPlanForm).find("[name='planName']").val(data.planName);
                $(Dom.modalPlanForm).find("[name='cycleType']").val(data.cycleType);
                $(Dom.modalPlanForm).find("[name='runType']").val(data.runType);
                $(Dom.modalPlanForm).find("[name='machineIp']").val(data.machineIp);
            }
        },
        //保存计划
        saveAutoPlan: function(cmd) {
        	var self = this;
            Rose.ajax.getJson(srvMap.get('saveAutoPlan'), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.show('计划保存成功！', 'success', 2000);
                    setTimeout(function() {
                        self.getPlanList();
                    }, 1000)
                }
            });
        },
        //删除计划
        deleAutoPlan: function() {
            var self = this;
            $("#JS_delePlan").bind('click', function() {
                var cmd = 'planId=';
                var  id;
                $(Dom.getAutoPlanList).find("tr").each(function() {
                    var tdArr = $(this).children();
                    if (tdArr.eq(0).find("input").is(':checked')) {
                        id = tdArr.eq(0).find("input").val();
                        cmd += id+',';
                    }
                });
                cmd=cmd.substring(0,cmd.Length-1);
                if (id) {
                    Rose.ajax.getJson(srvMap.get('deleAutoPlan'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('计划删除成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getPlanList();
                            }, 1000)
                        }
                    });
                } else {
                    window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
                }

            });


        },

        //获取选中当前计划数据
        getPlanInfo: function() {
            var obj = this.getCheckedRow(Dom.getAutoPlanList);
            var data = {
                planId: "",
                planTag: "",
                planName: "",
                cycleType: "",
                runType: "",
                creatorId: "",
                machineIp: "",
                createTime: "",
                updateTime: "",
            };
            if (obj.length == 0) {
                window.XMS.msgbox.show('请先选择一个计划！', 'error', 2000);
                return;
            } else if (obj.length > 1) {
                window.XMS.msgbox.show('请选择一个计划！', 'error', 2000);
                return;

            } else {
                data.planId = obj.find("[name='planId']").val();
                data.planTag = obj.find("[name='planTag']").val();
                data.planName = obj.find("[name='planName']").val();
                data.cycleType = obj.find("[name='cycleType']").val();
                data.runType = obj.find("[name='runType']").val();
                data.creatorId = obj.find("[name='creatorId']").val();
                data.machineIp = obj.find("[name='machineIp']").val();
                data.createTime = obj.find("[name='createTime']").val();
                data.updateTime = obj.find("[name='updateTime']").val();
            }
            return data;
        },
        // 获取复选列表当前选中行
        getCheckedRow: function(obj) {
            var _obj = $(obj).find("input[type='checkbox']:checked").parents("tr");
            return _obj;
        },
        eventClickChecked: function(obj, callback) {
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue'
            });
            obj.find("tr").bind('click', function(event) {
                $(this).find('.minimal').iCheck('check');
                if (callback) {
                    callback();
                }
            });
        },
        getDate: function() {
            var myDate = new Date();
            //获取当前年
            var year = myDate.getFullYear();
            //获取当前月
            var month = myDate.getMonth() + 1;
            //获取当前日
            var date = myDate.getDate();
            var h = myDate.getHours(); //获取当前小时数(0-23)
            var m = myDate.getMinutes(); //获取当前分钟数(0-59)
            var s = myDate.getSeconds();
            var ms = myDate.getMilliseconds(); //获取当前毫秒数(0-999)

            var MM = this.prefixInteger(month, 2);
            var DD = this.prefixInteger(date, 2);
            var hh = this.prefixInteger(h, 2);
            var mm = this.prefixInteger(m, 2);
            var ss = this.prefixInteger(s, 2);
            var mms = this.prefixInteger(ms, 3);

            var planTag = 'AP' + year + MM + DD + hh + mm + ss + mms;
            var nowTime = year + '-' + MM + "-" + DD + " " + hh + ':' + mm + ":" + ss + ":" + mms;
            var timeData = {
                planTag: planTag,
                nowTime: nowTime,
            };
            return timeData;

        },
        prefixInteger: function(num, length) {
            return ("0000000000000000" + num).substr(-length);
        },

    };
    module.exports = Init;
});
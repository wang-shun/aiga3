define(function(require, exports, module) {
    // 用工具模块通
    var Utils = require('global/utils.js');
    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('changeAlterReview');

    // 路径重命名
    var pathAlias = "netFlowManage/changeDeliverableReview/";

    //交付物评审结论&历史记录
    srvMap.add("getDeliverableReviewConclusionC", pathAlias + "getDeliverableReviewConclusion.json", "sys/review/findchangeReviewList");
    //保存结论
    srvMap.add("saveConclusionC", pathAlias + "retMessage.json", "sys/review/saveChangeReviewResult");
    //保存下一次评审时间
    srvMap.add("saveReviewTime", pathAlias + "retMessage.json", "sys/review/setReviewDate");
    //变更概况列表
    srvMap.add("getChangeProfileList", pathAlias + "getChangeProfileList.json", "sys/review/findNaChangeCondition");
    //保存变更概况
    srvMap.add("saveProfile", pathAlias + "retMessage.json", "sys/review/saveNaChangeCondition");
    //变更内容列表
    srvMap.add("getChangeContentList", pathAlias + "getChangeContentList.json", "sys/review/findNaChangeContents");
    //保存变更内容
    srvMap.add("saveContent", pathAlias + "retMessage.json", "sys/review/saveNaChangeContents");
    //时间及人员列表
    srvMap.add("getTimePersonList", pathAlias + "getTimePersonList.json", "sys/review/findNaChangeTimePerson");
    //保存时间及人员
    srvMap.add("saveTimePerson", pathAlias + "retMessage.json", "sys/review/saveNaChangeTimePerson");
    //变更目标设备列表
    srvMap.add("getChangeDeviceList", pathAlias + "getChangeDeviceList.json", "sys/review/findNaChangeGoalDevice");
    //保存变更目标设备
    srvMap.add("saveDevice", pathAlias + "retMessage.json", "sys/review/saveNaChangeGoalDevice");
    //变更配置更新列表
    srvMap.add("getChangeConfigList", pathAlias + "getChangeConfigList.json", "sys/review/findNaChangeUpdate");
    //保存配置更新
    srvMap.add("saveConfig", pathAlias + "retMessage.json", "sys/review/saveNaChangeUpdate");
    //告警屏蔽列表
    srvMap.add("getWarningList", pathAlias + "getWarningList.json", "sys/review/findNaWarningShield");
    //保存告警屏蔽
    srvMap.add("saveWarning", pathAlias + "retMessage.json", "sys/review/saveNaWarningShield");
    //变更准备工作列表
    srvMap.add("getPrepareList", pathAlias + "getPrepareList.json", "sys/review/findNaChangePrepareWork");
    //保存准备工作
    srvMap.add("savePrepare", pathAlias + "retMessage.json", "sys/review/saveNaChangePrepareWork");
    //变更实施步骤列表
    srvMap.add("getRunStepList", pathAlias + "getRunStepList.json", "sys/review/findNaChangeRunStep");
    //保存变更实施步骤
    srvMap.add("saveRunStep", pathAlias + "retMessage.json", "sys/review/saveNaChangeRunStep");
    //变更运维管理列表
    srvMap.add("getOperationManageList", pathAlias + "getOperationManageList.json", "sys/review/findNaChangeOperationManager");
    //保存变更运维管理
    srvMap.add("saveOperationManage", pathAlias + "retMessage.json", "sys/review/saveNaChangeOperationManager");
    //变更回退方案列表
    srvMap.add("getRollbackList", pathAlias + "getRollbackList.json", "sys/review/findNaRollbackMethod");
    //保存变更回退方案
    srvMap.add("saveRollback", pathAlias + "retMessage.json", "sys/review/saveNaRollbackMethod");
    //结果验证列表
    srvMap.add("getResultValidateList", pathAlias + "getResultValidateList.json", "sys/review/findNaChangeResultValidate");
    //保存结果验证
    srvMap.add("saveResultValidate", pathAlias + "retMessage.json", "sys/review/saveNaChangeResultValidate");
    //业务验证列表
    srvMap.add("getServiceValidateList", pathAlias + "getServiceValidateList.json", "sys/review/findNaChangeServiceValidate");
    //保存业务验证
    srvMap.add("saveServiceValidate", pathAlias + "retMessage.json", "sys/review/saveNaChangeServiceValidate");
    //风险评估列表
    srvMap.add("getDangerList", pathAlias + "getDangerList.json", "sys/review/findNaChangeDangurousEstimate");
    //保存风险评估
    srvMap.add("saveDanger", pathAlias + "retMessage.json", "sys/review/saveNaChangeDangurousEstimate");
    //风险评估量化表
    srvMap.add("getRiskRatingList", pathAlias + "getRiskRatingList.json", "sys/review/findNaRiskRatingScale");
    //保存风险评估量化
    srvMap.add("saveRiskRating", pathAlias + "retMessage.json", "sys/review/saveNaRiskRatingScale");
    //风险等级列表
    srvMap.add("getRiskScoreList", pathAlias + "getRiskScoreList.json", "sys/review/findNaQuantitativeRisk");  
    //保存风险等级
    srvMap.add("saveRiskScore", pathAlias + "retMessage.json", "sys/review/saveNaQuantitativeRisk");
    //信息通告列表
    srvMap.add("getNoticeList", pathAlias + "getNoticeList.json", "sys/review/findNaInformationNotice");
    //保存信息通告
    srvMap.add("saveNotice", pathAlias + "retMessage.json", "sys/review/saveNaInformationNotice");
    //附件列表
    srvMap.add("getFileList", pathAlias + "getFileList.json", "produce/plan/findNaFileUpload");

    //回退
    srvMap.add("rollback", "netFlowManage/deliverableReview/retMessage.json", "sys/plan/returnToADClod");

    var Data = {
        setPageType: function(type) {
            return {
                "data": {
                    "type": type
                }
            }
        },
        queryListCmd: null
    }

    var changeAlterReview = {
        init: function() {
            this._render();
        },
        _render: function() {
            this.getDeliverableReviewConclusionC();
            this.getHistoryList();
            this.queryFileTypeForm();
            this.getChangeProfileList();
            this.getChangeContentList();
            this.getTimePersonList();
            this.getChangeDeviceList();
            this.getChangeConfigList();
            this.getWarningList();
            this.getPrepareList();
            this.getRunStepList();
            this.getOperationManageList();
            this.getRollbackList();
            this.getResultValidateList();
            this.getServiceValidateList();
            this.getDangerList();
            this.getRiskRatingList();
            this.getRiskScoreList(); 
            this.getNoticeList();         
            this.getFileList();
            
            this.hdbarHelp();
        },
        hdbarHelp: function() {
            Handlebars.registerHelper("states", function(value) {
                if (value == 1) {
                    return "新增";
                } else if (value == 2) {
                    return "修改";
                } else if (value == 3) {
                    return "删除";
                }
            });
            Handlebars.registerHelper("yesOrNo", function(value) {
                if (value == 0) {
                    return "否";
                } else if (value == 1) {
                    return "是";
                }
            });
        },
        getDeliverableReviewConclusionC: function() {
            var self = this;
            var data = Page.getParentCmd();
            var _cmd = 'planId=' + data.onlinePlan + '&type=1';
            var _dom = Page.findId('getChangeReviewConclusionC');
            var _domPagination = _dom.find("[name='pagination']");
            Utils.getServerPage(srvMap.get('getDeliverableReviewConclusionC'), _cmd, function(json) {                  
                    var template = Handlebars.compile(Page.findTpl('getDeliverableReviewConclusionC'));
                    console.log(json.data.content)
                    _dom.find("[name='content']").html(template(json.data.content));
                    //下拉框赋值
                    var da = json.data.content;
                    var i = 0
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        tdArr.eq(3).find("select").val(da[i].conclusion);
                        i++;
                    });
                    // var _onlinePlanName = _dom.find("[name='onlinePlanName']");
                    // _onlinePlanName.html(data.onlinePlanName);
                    //引入单选框样式
                    Utils.eventTrClickCallback(_dom);
                    //设置出一下次评审时间
                    self.setReviewTime();
                    var _saveConclusionC = Page.findId('getChangeReviewConclusionC').find("[name='saveConclusionC']");
                    if (data.planState == "3" || data.planState == "4") {
                        _saveConclusionC.attr("disabled", true);
                    } else {
                        _saveConclusionC.removeAttr("disabled");
                    }
                    _saveConclusionC.unbind('click');
                    //点击保存
                    _saveConclusionC.bind('click', function() {
                        var _checkObj = _dom.find("input[type='checkbox']:checked");
                        if (_checkObj.length == 0) {
                            window.XMS.msgbox.show('请选中结论！', 'error', 2000);
                            return false;
                        }
                        var id;
                        var fileId;
                        var conclusion;
                        var reviewResult;
                        var remark;
                        var saveState = [];
                        var cmd;
                        _dom.find("tbody").find("tr").each(function() {
                            var tdArr = $(this).children();
                            if (tdArr.eq(0).find("input").is(':checked')) {
                                id = tdArr.eq(0).find("input").val();
                                fileId = tdArr.eq(1).find("input").val();
                                conclusion = tdArr.eq(3).find("select").val();
                                reviewResult = tdArr.eq(4).find("input").val();
                                remark = tdArr.eq(9).find("input").val();
                                saveState.push({
                                    "id": id,
                                    "fileId": fileId,
                                    "conclusion": conclusion,
                                    "reviewResult": reviewResult,
                                    "remark": remark,
                                    "planId": data.onlinePlan
                                });
                            }
                        });
                        cmd = saveState;
                        console.log(cmd);
                        //var _data = self.getCheckboxCheckedRow(_dom);
                        //var cmd = 'reviewId=' + _data.reviewId + '&conclusion=' + _data.conclusion + '&reviewResult=' + _data.reviewResult + '&remark=' + _data.remark + '&planId=' + data.onlinePlan;
                        Rose.ajax.postJson(srvMap.get('saveConclusionC'), cmd, function(json, status) {
                            if (status) {
                                // 保存结论成功后，刷新变更评审结论页
                                XMS.msgbox.show('保存成功！', 'success', 2000)
                                setTimeout(function() {
                                    self.getDeliverableReviewConclusionC();
                                }, 1000)
                            }
                        });
                    });
                    //点击回退
                    var _rollback = _dom.find("[name='rollback']");
                    _rollback.bind('click', function() {
                        Rose.ajax.postJson(srvMap.get('rollback'), 'planDate=' + data.planDate, function(json, status) {
                            if (status) {
                                XMS.msgbox.show('回退成功！', 'success', 2000)
                            }
                        });
                    });
            },_domPagination);
        },
        getHistoryList: function() {
            var self = this;
            var data = Page.getParentCmd();
            var _cmd = 'planId=' + data.onlinePlan + '&type=2';
            var _dom = Page.findId('getHistoryList');
            var _domPagination = _dom.find("[name='pagination']");
            Utils.getServerPage(srvMap.get('getDeliverableReviewConclusionC'), _cmd, function(json) {
                    var template = Handlebars.compile(Page.findTpl('getHistoryList'));
                    console.log(json.data)
                    _dom.find("[name='content']").html(template(json.data.content));
                    //引入单选框样式
                    Utils.eventTrClickCallback(_dom);
            },_domPagination);
        },
        //设置下一次评审时间
        setReviewTime: function() {
            var self = this;
            var data = Page.getParentCmd();
            var _dom = Page.findId('getChangeReviewConclusionC');
            var _setTime = _dom.find("[name='setTime']");
            _setTime.unbind('click');
            _setTime.bind('click', function() {
                var _modal = Page.findModal('setReviewTimeModal');
                _modal.modal('show');
                var _form = Page.findId('setReviewTimeForm');
                var _save = _form.find("[name='save']");
                _save.unbind('click');
                _save.bind('click', function() {
                    var cmd = _form.serialize() + "&planId=" + data.onlinePlan;
                    Utils.checkForm(_form, function() {
                        Rose.ajax.postJson(srvMap.get('saveReviewTime'), cmd, function(json, status) {
                            if (status) {
                                XMS.msgbox.show('设置成功！', 'success', 2000)
                                setTimeout(function() {
                                    _modal.modal('hide');
                                    _form.find("[name='reset']").click();
                                }, 1000)
                            }
                        });
                    })
                });
                var _close = _modal.find("[name='close']")
                _close.unbind('click');
                _close.bind('click', function() {
                    _form.find("[name='reset']").click();
                });
            });
        },
        queryFileTypeForm: function() {
            var self = this;
            var _form = Page.findId('queryFileTypeForm');
            Utils.setSelectData(_form);
            var _queryBtn = _form.find("[name='query']");
            _queryBtn.unbind('click');
            _queryBtn.bind('click', function() {
                var cmd = _form.serialize();
                self.getChangeProfileList(cmd);
                self.getChangeContentList(cmd);
                self.getTimePersonList(cmd);
                this.getChangeDeviceList(cmd);
                this.getChangeConfigList(cmd);
                this.getWarningList(cmd);
                this.getPrepareList(cmdcmd);
                this.getRunStepList(cmd);
                this.getOperationManageList(cmd);
                this.getRollbackList(cmd);
                this.getResultValidateList(cmd);
                this.getServiceValidateList(cmd);
                this.getDangerList(cmd);
                this.getRiskRatingList(cmd);
                this.getRiskScoreList(cmd); 
                this.getNoticeList(cmd);         
                this.getFileList(cmd);
            });
        },
        //变更概况列表
        getChangeProfileList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getChangeProfileList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getChangeProfileList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getChangeProfileList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var changeTitle;
                    var changeGroup;
                    var changeObjectType;
                    var changeMethodType;
                    var changeReason;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            changeTitle = tdArr.eq(1).find("input").val();
                            changeGroup = tdArr.eq(2).find("input").val();
                            changeObjectType = tdArr.eq(3).find("input").val();
                            changeMethodType = tdArr.eq(4).find("input").val();
                            changeReason = tdArr.eq(5).find("input").val();
                            saveState.push({
                                "id": id,
                                "changeTitle": changeTitle,
                                "changeGroup": changeGroup,
                                "changeObjectType": changeObjectType,
                                "changeMethodType": changeMethodType,
                                "changeReason": changeReason,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveProfile'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getChangeProfileList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更内容列表
        getChangeContentList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getChangeContentList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getChangeContentList'), _cmd + 　'&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getChangeContentList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var questionDesc;
                    var changeObject;
                    var sysName;
                    var changeGoal;
                    var changeDangerous;
                    var impactRange;
                    var groupCheckImpact;
                    var groupImpact;
                    var imfoImpact;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            questionDesc = tdArr.eq(1).find("input").val();
                            changeObject = tdArr.eq(2).find("input").val();
                            sysName = tdArr.eq(3).find("input").val();
                            changeGoal = tdArr.eq(4).find("input").val();
                            changeDangerous = tdArr.eq(5).find("input").val();
                            impactRange = tdArr.eq(6).find("input").val();
                            groupCheckImpact = tdArr.eq(7).find("input").val();
                            groupImpact = tdArr.eq(8).find("input").val();
                            imfoImpact = tdArr.eq(9).find("input").val();
                            saveState.push({
                                "id": id,
                                "questionDesc": questionDesc,
                                "changeObject": changeObject,
                                "sysName": sysName,
                                "changeGoal": changeGoal,
                                "changeDangerous": changeDangerous,
                                "impactRange": impactRange,
                                "groupCheckImpact": groupCheckImpact,
                                "groupImpact": groupImpact,
                                "imfoImpact": imfoImpact,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveContent'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getChangeContentList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //时间及人员列表
        getTimePersonList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getTimePersonList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getTimePersonList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getTimePersonList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var applyTime;
                    var startRunTime;
                    var stopRunTime;
                    var personRole;
                    var personName;
                    var personPhone;
                    var personGroup;
                    var personDuty;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            applyTime = tdArr.eq(1).find("input").val();
                            startRunTime = tdArr.eq(2).find("input").val();
                            stopRunTime = tdArr.eq(3).find("input").val();
                            personRole = tdArr.eq(4).find("input").val();
                            personName = tdArr.eq(5).find("input").val();
                            personPhone = tdArr.eq(6).find("input").val();
                            personGroup = tdArr.eq(7).find("input").val();
                            personDuty = tdArr.eq(8).find("input").val();
                            saveState.push({
                                "id": id,
                                "applyTime": applyTime,
                                "startRunTime": startRunTime,
                                "stopRunTime": stopRunTime,
                                "personRole": personRole,
                                "personName": personName,
                                "personPhone": personPhone,
                                "personGroup": personGroup,
                                "personDuty": personDuty,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveTimePerson'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getTimePersonList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更目标设备列表
        getChangeDeviceList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getChangeDeviceList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getChangeDeviceList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getChangeDeviceList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var resourceType;
                    var deviceName;
                    var ipAddressee;
                    var sysName;
                    var searchCode;
                    var changeContent;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            resourceType = tdArr.eq(1).find("input").val();
                            deviceName = tdArr.eq(2).find("input").val();
                            ipAddressee = tdArr.eq(3).find("input").val();
                            sysName = tdArr.eq(4).find("input").val();
                            searchCode = tdArr.eq(5).find("input").val();
                            changeContent = tdArr.eq(6).find("input").val();
                            saveState.push({
                                "id": id,
                                "resourceType": resourceType,
                                "deviceName": deviceName,
                                "ipAddressee": ipAddressee,
                                "sysName": sysName,
                                "searchCode": searchCode,
                                "changeContent": changeContent,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveDevice'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getChangeDeviceList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更配置更新列表
        getChangeConfigList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getChangeConfigList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getChangeConfigList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getChangeConfigList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var configIsUpdate;
                    var resourceType;
                    var name;
                    var configUpdateBefore;
                    var configUpdateAfter;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            configIsUpdate = tdArr.eq(1).find("input").val();
                            resourceType = tdArr.eq(2).find("input").val();
                            name = tdArr.eq(3).find("input").val();
                            configUpdateBefore = tdArr.eq(4).find("input").val();
                            configUpdateAfter = tdArr.eq(5).find("input").val();
                            saveState.push({
                                "id": id,
                                "configIsUpdate": configIsUpdate,
                                "resourceType": resourceType,
                                "name": name,
                                "configUpdateBefore": configUpdateBefore,
                                "configUpdateAfter": configUpdateAfter,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveConfig'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getChangeConfigList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //告警屏蔽列表
        getWarningList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getWarningList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getWarningList'), _cmd + 　'&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getWarningList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var shieldHost;
                    var ipAddress;
                    var shieldTime;
                    var shieldContent;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            shieldHost = tdArr.eq(1).find("input").val();
                            ipAddress = tdArr.eq(2).find("input").val();
                            shieldTime = tdArr.eq(3).find("input").val();
                            shieldContent = tdArr.eq(4).find("input").val();
                            saveState.push({
                                "id": id,
                                "shieldHost": shieldHost,
                                "ipAddress": ipAddress,
                                "shieldTime": shieldTime,
                                "shieldContent": shieldContent,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveWarning'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getWarningList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更准备工作列表
        getPrepareList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getPrepareList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getPrepareList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getPrepareList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var isReady;
                    var runStep;
                    var runPerson;
                    var startRunTime;
                    var stopRunTime;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            isReady = tdArr.eq(1).find("input").val();
                            runStep = tdArr.eq(2).find("input").val();
                            runPerson = tdArr.eq(3).find("input").val();
                            startRunTime = tdArr.eq(4).find("input").val();
                            stopRunTime = tdArr.eq(5).find("input").val();
                            saveState.push({
                                "id": id,
                                "isReady": isReady,
                                "runStep": runStep,
                                "runPerson": runPerson,
                                "startRunTime": startRunTime,
                                "stopRunTime": stopRunTime,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('savePrepare'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getPrepareList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更实施步骤列表
        getRunStepList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getRunStepList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getRunStepList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getRunStepList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var isConfigArg;
                    var isRestartHost;
                    var isRestartCollection;
                    var isChangeHardware;
                    var isRestartDb;
                    var isRestartApplication;
                    var runStep;
                    var runPerson;
                    var startRunTime;
                    var stopRunTime;
                    var opreationScript;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            isConfigArg = tdArr.eq(1).find("input").val();
                            isRestartHost = tdArr.eq(2).find("input").val();
                            isRestartCollection = tdArr.eq(3).find("input").val();
                            isChangeHardware = tdArr.eq(4).find("input").val();
                            isRestartDb = tdArr.eq(5).find("input").val();
                            isRestartApplication = tdArr.eq(6).find("input").val();
                            runStep = tdArr.eq(7).find("input").val();
                            runPerson = tdArr.eq(8).find("input").val();
                            startRunTime = tdArr.eq(9).find("input").val();
                            stopRunTime = tdArr.eq(10).find("input").val();
                            opreationScript = tdArr.eq(11).find("input").val();
                            saveState.push({
                                "id": id,
                                "isConfigArg": isConfigArg,
                                "isRestartHost": isRestartHost,
                                "isRestartCollection": isRestartCollection,
                                "isChangeHardware": isChangeHardware,
                                "isRestartDb": isRestartDb,
                                "isRestartApplication": isRestartApplication,
                                "runStep": runStep,
                                "runPerson": runPerson,
                                "startRunTime": startRunTime,
                                "stopRunTime": stopRunTime,
                                "opreationScript": opreationScript,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveRunStep'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRunStepList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更运维列表管理
        getOperationManageList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getOperationManageList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getOperationManageList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getOperationManageList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var isAddMonitor;
                    var runPerson;
                    var startRunTime;
                    var stopRunTime;
                    var opreationScript;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            isAddMonitor = tdArr.eq(1).find("input").val();
                            runPerson = tdArr.eq(2).find("input").val();
                            startRunTime = tdArr.eq(3).find("input").val();
                            stopRunTime = tdArr.eq(4).find("input").val();
                            opreationScript = tdArr.eq(5).find("input").val();
                            saveState.push({
                                "id": id,
                                "isAddMonitor": isAddMonitor,
                                "runPerson": runPerson,
                                "startRunTime": startRunTime,
                                "stopRunTime": stopRunTime,
                                "opreationScript": opreationScript,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveOperationManage'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getOperationManageList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //变更回退方案列表
        getRollbackList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getRollbackList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getRollbackList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getRollbackList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var isRollback;
                    var rollbackStep;
                    var rollbackPerson;
                    var startRollbackTime;
                    var stopRollbackTime;
                    var rollbackScript;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            isRollback = tdArr.eq(1).find("input").val();
                            rollbackStep = tdArr.eq(2).find("input").val();
                            rollbackPerson = tdArr.eq(3).find("input").val();
                            startRollbackTime = tdArr.eq(4).find("input").val();
                            stopRollbackTime = tdArr.eq(5).find("input").val();
                            rollbackScript = tdArr.eq(6).find("input").val();
                            saveState.push({
                                "id": id,
                                "isRollback": isRollback,
                                "rollbackStep": rollbackStep,
                                "rollbackPerson": rollbackPerson,
                                "startRollbackTime": startRollbackTime,
                                "stopRollbackTime": stopRollbackTime,
                                "rollbackScript": rollbackScript,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveRollback'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRollbackList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //结果验证列表
        getResultValidateList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getResultValidateList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getResultValidateList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getResultValidateList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var isChangeValidate;
                    var validateContent;
                    var testTime;
                    var testStep;
                    var testMan;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            isChangeValidate = tdArr.eq(1).find("input").val();
                            validateContent = tdArr.eq(2).find("input").val();
                            testTime = tdArr.eq(3).find("input").val();
                            testStep = tdArr.eq(4).find("input").val();
                            testMan = tdArr.eq(5).find("input").val();
                            saveState.push({
                                "id": id,
                                "isChangeValidate": isChangeValidate,
                                "validateContent": validateContent,
                                "testTime": testTime,
                                "testStep": testStep,
                                "testMan": testMan,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveResultValidate'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getResultValidateList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //业务验证列表
        getServiceValidateList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getServiceValidateList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getServiceValidateList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getServiceValidateList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var isServiceValidate;
                    var sysName;
                    var serviceFunction;
                    var testTime;
                    var testStep;
                    var testMan;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            isServiceValidate = tdArr.eq(1).find("input").val();
                            sysName = tdArr.eq(2).find("input").val();
                            serviceFunction = tdArr.eq(3).find("input").val();
                            testTime = tdArr.eq(4).find("input").val();
                            testStep = tdArr.eq(5).find("input").val();
                            testMan = tdArr.eq(6).find("input").val();
                            saveState.push({
                                "id": id,
                                "isServiceValidate": isServiceValidate,
                                "sysName": sysName,
                                "serviceFunction": serviceFunction,
                                "testTime": testTime,
                                "testStep": testStep,
                                "testMan": testMan,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveServiceValidate'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getServiceValidateList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //风险评估列表
        getDangerList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getDangerList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getDangerList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getDangerList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var types;
                    var content;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            types = tdArr.eq(1).find("input").val();
                            content = tdArr.eq(2).find("input").val();
                            saveState.push({
                                "id": id,
                                "types": types,
                                "content": content,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveDanger'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getDangerList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //风险等级评估量化表
        getRiskRatingList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getRiskRatingList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getRiskRatingList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getRiskRatingList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var measureFactor;
                    var condition;
                    var selfEvalution;
                    var score;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            measureFactor = tdArr.eq(1).find("input").val();
                            condition = tdArr.eq(2).find("input").val();
                            selfEvalution = tdArr.eq(3).find("input").val();
                            score = tdArr.eq(4).find("input").val();
                            saveState.push({
                                "id": id,
                                "measureFactor": measureFactor,
                                "condition": condition,
                                "selfEvalution": selfEvalution,
                                "score": score,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveRiskRating'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRiskRatingList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        //风险等级列表
        getRiskScoreList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getRiskScoreList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getRiskScoreList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getRiskScoreList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var totalScore;
                    var actualScore;
                    var riskGrade;
                    var approvalLevel;
                    var guaranteeMechanism;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            totalScore = tdArr.eq(1).find("input").val();
                            actualScore = tdArr.eq(2).find("input").val();
                            riskGrade = tdArr.eq(3).find("input").val();
                            approvalLevel = tdArr.eq(4).find("input").val();
                            guaranteeMechanism = tdArr.eq(5).find("input").val();
                            saveState.push({
                                "id": id,
                                "totalScore": totalScore,
                                "actualScore": actualScore,
                                "riskGrade": riskGrade,
                                "approvalLevel": approvalLevel,
                                "guaranteeMechanism":guaranteeMechanism,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveRiskScore'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getRiskScoreList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        
        getNoticeList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getNoticeList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getNoticeList'), _cmd + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getNoticeList'));
                _dom.find("[name='content']").html(template(json.data.content));
                var _save = _dom.find("[name='save']");
                if (data.planState == "3" || data.planState == "4") {
                    _save.attr("disabled", true);
                } else {
                    _save.removeAttr("disabled");
                }
                _save.unbind('click');
                //点击保存
                _save.bind('click', function() {
                    var _checkObj = _dom.find("input[type='checkbox']:checked");
                    if (_checkObj.length == 0) {
                        window.XMS.msgbox.show('请选择要保存的数据！', 'error', 2000);
                        return false;
                    }
                    var id;
                    var noticeType;
                    var noticeType;
                    var noticeContent;
                    var saveState = [];
                    var cmd;
                    _dom.find("tbody").find("tr").each(function() {
                        var tdArr = $(this).children();
                        if (tdArr.eq(0).find("input").is(':checked')) {
                            id = tdArr.eq(0).find("input").val();
                            noticeType = tdArr.eq(1).find("input").val();
                            noticeType = tdArr.eq(2).find("input").val();
                            noticeContent = tdArr.eq(3).find("input").val();
                            saveState.push({
                                "id": id,
                                "noticeType": noticeType,
                                "noticeType": noticeType,
                                "noticeContent": noticeContent,
                                "planId": data.onlinePlan
                            });
                        }
                    });
                    cmd = saveState;
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('saveNotice'), cmd, function(json, status) {
                        if (status) {
                            XMS.msgbox.show('保存成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getNoticeList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                });
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        getFileList: function(cmd) {
            var self = this;
            var _cmd = '' || cmd;
            Data.queryListCmd = _cmd;
            var _dom = Page.findId('getFileList');
            var _domPagination = _dom.find("[name='pagination']");
            var data = Page.getParentCmd();
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getFileList'), _cmd + '&fileType=2' + '&planId=' + data.onlinePlan, function(json) {
                window.XMS.msgbox.hide();

                // 查找页面内的Tpl，返回值html代码段
                var template = Handlebars.compile(Page.findTpl('getFileList'));
                _dom.find("[name='content']").html(template(json.data.content));
                Utils.eventTrClickCallback(_dom);
            }, _domPagination);
        },
        /*
         * 获取单选框当前值
         */
        getRadioCheckedRow: function(obj) {
            var _obj = obj.find("input[type='radio']:checked");
            if (_obj.length == 0) {
                window.XMS.msgbox.show('请先选择一行数据！', 'info', 2000);
                return;
            }
            var data = {};
            _obj.parents("tr").find("input").each(function() {
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key] = value;
            });
            _obj.parents("tr").find("select").each(function() {
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key] = value;
            });
            return data;
        }
    };

    module.exports = changeAlterReview;
});

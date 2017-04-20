define(function(require, exports, module) {

    // 通用工具模块
    var Utils = require("global/utils.js");

    // 路径重命名
    var pathAlias = "autoManage/dataBackups/";
    // 初始化页面ID，易于拷贝，不需要带'#'
    var Page = Utils.initPage('propertyConfigView');

    //分页根据条件查询
    srvMap.add("getPropertyConfigList", pathAlias + "propertyConfig.json", "sys/property/getPropertyConfigList");
    //新增备份
    srvMap.add("addPropertyConfig", pathAlias + "retMessage.json", "sys/property/addPropertyConfig");
    //删除备份
    srvMap.add("delPropertyConfig", pathAlias + "retMessage.json", "sys/property/delPropertyConfig");
    //修改备份
    srvMap.add("updatePropertyConfig", pathAlias + "retMessage.json", "sys/property/updatePropertyConfig");
    //属性下拉菜单
    srvMap.add("getPropertyName", pathAlias + "retMessage.json", "sys/backup/getPropertyConfigList");
    //数据库下拉菜单
    srvMap.add("getDbList", pathAlias + "retMessage.json", "sys/property/getDbList");
    /*// 模板对象
    var Tpl = {
        getPropertyConfigTemp: $('#JS_getPropertyConfigTemp'),
    };

    // 容器对象
    var Dom = {
        queryPropertyConfigForm: '#JS_queryPropertyConfigForm',
        getPropertyConfigList: '#JS_getPropertyConfigList',
        addPropertyConfigModal: "#JS_addPropertyConfigModal",
        addPropertyConfigInfo: "#JS_addPropertyConfigInfo",
        addPropertyModal: "#JS_addPropertyModal",
        addPropertyInfo: "#JS_addPropertyInfo",
        updatePropertyConfigModal: "#JS_updatePropertyConfigModal",
        updateMaintainInfo: "#JS_updatePropertyConfigInfo",
    };*/

    var Data = {
        queryListCmd: null,
        cfgId: null
    }

    var Query = {
            init: function() {
                // 默认查询所有
                this.getPropertyConfigList();
                // 初始化查询表单
                this.queryPropertyConfigForm();
                //映射
                this.hdbarHelp();
            },
            // 按条件查询
            queryPropertyConfigForm: function() {
                var self = this;
                var _form = Page.findId('queryPropertyConfigForm');
                Utils.setSelectData(_form);
                var _queryBtn = _form.find("[name='query']");
                _queryBtn.bind('click', function() {
                    var cmd = _form.serialize();
                    console.log(cmd);
                    self.getPropertyConfigList(cmd);
                });

            },
            // 查询数据维护
            getPropertyConfigList: function(cmd) {
                var self = this;
                var _cmd = '' || cmd;
                Data.queryListCmd = _cmd;
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                var _dom = Page.findId('getPropertyConfigList');
                var _domPagination = _dom.find("[name='pagination']");

                // 设置服务器端分页
                Utils.getServerPage(srvMap.get('getPropertyConfigList'), _cmd, function(json, status) {
                    window.XMS.msgbox.hide();
                    var template = Handlebars.compile(Page.findTpl('getPropertyConfigTemp'));
                    _dom.find("[name='content']").html(template(json.data.content));
                    //美化单机
                    Utils.eventTrClickCallback(_dom);
                    //新增属性配置
                    self.addPropertyConfig();
                    //新增属性
                    /*self.addProperty();*/
                    //删除
                    self.delPropertyConfig();
                    //双击修改
                    self.eventDClickCallback(_dom, function() {
                        //获得当前单选框值
                        var data = Utils.getRadioCheckedRow(_dom);
                        self.updatePropertyConfig(data.cfgId, data.propertyName);
                    });
                }, _domPagination);
            },
            //新增属性配置
            addPropertyConfig: function() {
                var self = this;
                var _list = Page.findId('getPropertyConfigList');;
                var _addBt = _list.find("[name='add']");
                _addBt.unbind('click');
                _addBt.bind('click', function() {
                        Page.findModal('addPropertyConfigModal').modal('show');
                        var _form = Page.findId('addPropertyConfigInfo');
                        //关闭清除
                        Page.findModal('addPropertyConfigModal').on('hide.bs.modal', function() {
                            Utils.resetForm(Dom.addPropertyConfigInfo);
                           // $('#propertyIdInput').readOnly = false;
                        });


                        //select2
                        self.getPropertyId();

                    //
                    Page.findModal('addPropertyConfigModal').modal.Constructor.prototype.enforceFocus = function() {}; Utils.setSelectData(_form);
                    var _saveBt = Page.findModal('addPropertyConfigModal').find("[name = 'save']"); _saveBt.unbind('click'); _saveBt.bind('click', function() {
                        Utils.checkForm(_form, function() {
                            var _cmd = _form.serialize();
                            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                            _cmd=_cmd+ '&propertyName='+$('#propertyNameSelect').find("option:selected").text();
                            console.log(_cmd);

                            Rose.ajax.postJson(srvMap.get('addPropertyConfig'), _cmd, function(json, status) {
                                if (status) {
                                    // 数据备份成功后，刷新用户列表页
                                    XMS.msgbox.show('添加成功！', 'success', 2000)
                                    setTimeout(function() {
                                        self.getPropertyConfigList();
                                    }, 1000);
                                    // 关闭弹出层
                                    Page.findModal('addPropertyConfigModal').modal('hide');
                                }
                            });
                        });
                    });

                });

        },
        /*//新增属性
        addProperty: function() {
        	var self = this;
        	var _list = $(Dom.getPropertyList);
        	var _addBt = _list.find("[name='addProperty']");
        	alert("新增");
        	_addBt.unbind('click');
        	_addBt.bind('click', function() {
        		$(Dom.addPropertyModal).modal('show');
        		var _form = $(Dom.addPropertyInfo);
        		$(Dom.addPropertyModal).on('hide.bs.modal', function() {
        			Utils.resetForm(Dom.addPropertyInfo);
        		});
        		Utils.setSelectData(_form);
        		var _saveBt = $(Dom.addPropertyModal).find("[name = 'save']");
        		_saveBt.unbind('click');
        		_saveBt.bind('click', function() {
        			Utils.checkForm(_form, function() {
        				var _cmd = _form.serialize();
        				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
        				console.log(_cmd);
        				Rose.ajax.postJson(srvMap.get('addPropertyConfig'), _cmd, function(json, status) {
        					if (status) {
        						// 数据备份成功后，刷新用户列表页
        						XMS.msgbox.show('添加成功！', 'success', 2000)
        						setTimeout(function() {
        							self.getPropertyConfigList();
        						}, 1000);
        						// 关闭弹出层
        						$(Dom.addPropertyModal).modal('hide');
        					}
        				});
        			});
        		});

        	});

        },*/
        //删除数据备份
        delPropertyConfig: function() {
            var self = this;
            var _dom = Page.findId('getPropertyConfigList');
            var _del = _dom.find("[name='del']");
            _del.unbind('click');
            _del.bind('click', function() {
                //获得当前单选框值
                var data = Utils.getRadioCheckedRow(_dom);
                if (data) {
                    console.log(data);
                    var cmd = 'cfgId=' + data.cfgId;
                    //alert(cmd);
                    XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                    Rose.ajax.getJson(srvMap.get('delPropertyConfig'), cmd, function(json, status) {
                        if (status) {
                            window.XMS.msgbox.show('删除成功！', 'success', 2000)
                            setTimeout(function() {
                                self.getPropertyConfigList(Data.queryListCmd);
                            }, 1000)
                        }
                    });
                }
            });
        },
        //修改
        updatePropertyConfig: function(Id, propertyName) {
            var self = this;
            var _dom =Page.findModal('updatePropertyConfigModal');
            _dom.modal("show");
            _dom.find("#JS_name").html(propertyName);
            var _save = _dom.find("[name='save']");
            _save.unbind('click');
            _save.bind('click', function() {
                var _form = Page.findId('updatePropertyConfigInfo');
                var _cmd = _form.serialize();
                Utils.setSelectData(_form);
                _cmd += '&cfgId=' + Id;
                XMS.msgbox.show('执行中，请稍候...', 'loading');
                Rose.ajax.getJson(srvMap.get('updatePropertyConfig'), _cmd, function(json, status) {
                    if (status) {
                        window.XMS.msgbox.show('更新成功！', 'success', 2000)
                        setTimeout(function() {
                            self.getPropertyConfigList(Data.queryListCmd);
                        }, 1000);
                        _dom.modal('hide');
                    }
                });
            });

        },
        // 事件：双击选中当前行
        eventDClickCallback: function(obj, callback) {
            obj.find("tr").bind('dblclick ', function(event) {
                if (callback) {
                    callback();
                }
            });
        },
        getPropertyId: function(data) {
            $('#propertyNameSelect').change(function() {
                        var _cmd = 'propertyId='+$('#propertyNameSelect').val();
                        Rose.ajax.postJson(srvMap.get('getPropertyConfigList'),_cmd, function(json, status) {
                                if (status&&json.data.content.length!=0) {
                                    $('#propertyIdInput').val(json.data.content[0].propertyId);
                                }
                                else{
                                     $('#propertyIdInput').val("");
                                   // $('#propertyIdInput').readOnly = false;
                                }

                        });

                });
        },
                //映射处理
                hdbarHelp: function() {},
                // 事件：分页
                initPaging: function(obj, length) {
                    obj.find("table").DataTable({
                        "iDisplayLength": length,
                        "paging": true,
                        "lengthChange": false,
                        "searching": false,
                        "ordering": false,
                        "info": true,
                        "autoWidth": false
                    });
                }
        };
    module.exports = Query;
});
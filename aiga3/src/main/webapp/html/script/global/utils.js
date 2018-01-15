/*
 * 通用工具集
 */
define(function(require, exports, module) {
    var Utils = {
        /**
         * 初始化页面唯一标识
         *
         * @method pageId 页面标识
         * @return object 构建的对象
         */
        initPage: function(pageId) {
            var page = new Object();
            page.id = '#Page_' + pageId;
            page.getId = function() {
                    return $(this.id);
                },
                page.find = function(obj) {
                    return $(this.id).parent().find(obj);
                },
                page.findId = function(objId) {
                    return $(this.id).parent().find("#JS_" + objId);
                },
                page.findName = function(objName) {
                    return $(this.id).parent().find("[name='" + objName + "']");
                },
                page.findTpl = function(tplId) {
                    return $(this.id).parent().find("#TPL_" + tplId).html();
                },
                page.findModal = function(modalId) {
                    return $(this.id).parent().find("#Modal_" + modalId);
                },
                page.findModalCId = function(objId) {
                    return $(this.id).parent().find("#JS_" + objId);
                },
                page.getFunId = function(obj) {
                    return $(this.id).parent().data("funid");
                },
                page.getParentCmd = function(obj) {
                    return Rose.browser.mapQuery($(this.id).parent().data("cmd"));
                }
            return page;
        },
        /**
         * 页面元素中的加载
         *
         * @method obj 父元素
         * @method isMinHeight 是否有最新高度值，默认 true
         */
        loader: function(obj, isMinHeight) {
            var _mh = true || isMinHeight;
            var _class = 'min-height';
            if (!_mh) {
                _class = '';
            }
            $(obj).html('<div class="text-loader min-height">' +
                '<div class="text-loader-content">' +
                '<i class="fa fa-refresh fa-spin"></i>数据加载中，请稍候...' +
                '</div>' +
                '</div>');
        },
        /**
         * 页面元素中的无数据提示
         *
         * @method obj 父元素
         * @method isMinHeight 是否有最新高度值，默认 true
         */
        noData: function(obj, isMinHeight) {
            var _mh = true || isMinHeight;
            var _class = 'min-height';
            if (!_mh) {
                _class = '';
            }
            $(obj).html('<div class="text-loader min-height">' +
                '<div class="text-loader-content">' +
                '<i class="iconfont icon-wushuju"></i>亲，暂无数据！' +
                '</div>' +
                '</div>');
        },
        /**
         * 权限按钮控制
         *
         * @method obj 表格父元素
         */
        tokenButton: function() {
            //var TOKEN = {};
            $('[data-token]').each(function() {
                var _isShow = false;
                var _el = $(this);
                var _token = _el.data("token").toString();
                _token = _token.indexOf(",") ? _token : _token + ',';
                var _valArray = _token.split(',');
                for (i in _valArray) {
                    var _key = Class.getConstant(_valArray[i]);
                    var _rolArray = LOGINUSER.roles;
                    for (x in _rolArray) {
                        if (_rolArray[x].code = _key) {
                            _isShow = true;
                        }
                    }
                }
                if (_isShow) {
                    _el.show();
                } else {
                    _el.hide();
                    if (LOGINUSER.staff.name == "admin") {
                        _el.show();
                    } else {
                        _el.hide();
                    }
                }

            })
        },
        /*还原下拉框*/
        restComboSelect: function(obj) {
            obj.comboSelect("dispose");
            obj.removeClass('hide');
        },
        /**
         * 自定义折叠
         *
         * @method obj 表格父元素
         */
        customCollapse: function(obj) {
            $(obj).find('[name="customCollapse"]').each(function() {
                $(this).click(function() {
                    var _el = $(this).data('collapse');
                    $('#' + _el).slideToggle();
                })
            })
        },
        /**
         * 单复选框美化、单机选中、双击执行回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventTrClickCallback: function(obj, callback) {
            this.eventClickChecked(obj);
            this.eventDClickCallback(obj, callback);
        },
        /**
         * 单选框状态改变时回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventTrClickIfChanged: function(obj, callback) {
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });

            obj.find("tbody > tr").bind('click', function(event) {
                var _input = $(this).find('.minimal');
                _input.iCheck('toggle');
                var isChecked = _input.parent("div").attr("aria-checked");
            });

            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').on('ifChecked', function(event) {
                if (callback) {
                    callback();
                }
            });
        },
        /**
         * 单复选框美化、单击执行回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventClickChecked: function(obj, callback) {
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            obj.find("tbody > tr").bind('click', function(event) {
                var _input = $(this).find('.minimal');
                _input.iCheck('toggle');
                var isChecked = _input.parent("div").attr("aria-checked");
                if (callback) {
                    callback(isChecked, _input);
                }
            });
        },
        /**
         * 表格拖动排序
         *
         * @tbodyObj 表格里的tbody
         * @indexObj  拖动后要改变值的元素
         */
        tableSortable: function(tbodyObj, indexObj) {
            var fixHelperModified = function(e, tr) {
                    var originals = tr.children();
                    var helper = tr.clone();
                    helper.children().each(function(index) {
                        $(this).width(originals.eq(index).width())
                    });
                    return helper;
                },
                updateIndex = function(e, ui) {
                    console.log(ui);
                    $('td.index', ui.item.parent()).each(function(i) {
                        $(this).text(i + 1);
                        if (indexObj) {
                            $(this).parent().find(indexObj).val(i + 1);
                        }
                    });
                };
            tbodyObj.sortable({
                helper: fixHelperModified,
                stop: updateIndex
            }).disableSelection();
        },
        /**
         * 表单验证
         *
         * @method objForm 表单父元素
         * @callback Fun 回调函数
         */
        checkForm: function(objForm, callback, value) {
            var state = true;
            var text = '';
            $(objForm).find(':input[required]')
                .not(':button, :submit, :reset, :hidden').each(function() {
                    var _val = $.trim($(this).val());
                    var _text = $.trim($(this).prev().text());
                    if (_val == null || _val == undefined || _val == '') {
                        state = false;
                        text = _text;
                        return false;
                    }
                })

            if (value) {
                $(objForm).find(':input[required]').each(function() {
                    console.log($(objForm).find(':input[required]', '.hide'))
                    if ($(this).hasClass('hide')) {
                        var _val = $.trim($(this).val());
                        var _text = $.trim($(this).parent().prev().text());
                        if (_val == null || _val == undefined || _val == '') {
                            state = false;
                            text = _text;
                            return false;
                        }
                    }

                })
            }
            if (state) {
                callback(state);
            } else {
                XMS.msgbox.show(text.trimStar() + '不能为空！', 'error', 2000);
            }

        },
        /**
         * 清空表单所有的
         *
         * @method obj 父元素
         */
        resetForm: function(objForm) {
            $(objForm).find(':input')
                .not(':button, :submit, :reset, :hidden, :disabled')
                .val('')
                .removeAttr('checked')
                .removeAttr('selected');
        },
        /**
         * 页面中多DIV跳转
         *
         * @method obj 父元素
         */
        initStep: function(obj) {
            var self = this;
            $(obj).find("[data-gostep]").bind("click", function() {
                if ($(obj).find("div[name='steps']").length) {
                    self.setSetpClass($(obj), $(this));
                } else {
                    self.goStep($(obj), $(this).data("gostep"));
                }
            })
        },
        goStep: function(obj, num) {
            $(obj).find("div[data-steps='" + num + "']").removeClass('hide').siblings().addClass('hide');
        },
        setSetpClass: function(obj, thiz) {
            var _active = $(thiz).attr("name");
            var _lenght = parseInt($(thiz).data("gostep")) - 1;
            var _el = $(obj).find("div[name='steps']").find("div[name='items']");
            var _mini = $(obj).find("div[name='steps']").hasClass("steps-mini");
            var _thisEl = _el.eq(_lenght - 1);
            var _nextEl = _el.eq(_lenght);
            var _rightTag = '<i class="fa fa-check"></i>';
            if (_active == "next") {
                if (!_thisEl.hasClass('checkSuccess')) {
                    XMS.msgbox.show('亲，请先通过该步骤的所有验证！', 'error', 2000);
                    return false;
                }
            } else if (_active == "prev") {
                _nextEl.removeClass('checkSuccess');
            }
            $(obj).find("div[data-steps='" + $(thiz).data("gostep") + "']").removeClass('hide').siblings().addClass('hide');
            if (_mini) {
                if (_active == "prev") {
                    _nextEl.find("span.round").html($(thiz).data("gostep"));
                }
                _nextEl.removeClass('todo finished').addClass("current");
                var _prevEl = _nextEl.parent().prevAll().find("div[name='items']");
                var _nextEl = _nextEl.parent().nextAll().find("div[name='items']");
                _prevEl.find("span.round").html(_rightTag);
                _prevEl.removeClass('todo current').addClass("finished");
                _nextEl.removeClass('current finished').addClass("todo");
            } else {
                _nextEl.removeClass('todo finished').addClass("current");
                _nextEl.prevAll().removeClass('todo current').addClass("finished");
                _nextEl.nextAll().removeClass('current finished').addClass("todo");
            }

        },
        setScroll: function(obj, height) {
            obj.slimScroll({
                "height": height
            });
        },
        /**
         * 获取表格所有行的数据
         *
         * @method obj 表格父元素
         */
        getTableDataRows: function(obj) {
            var dataArray = []
            obj.find("tbody > tr").each(function() {
                var data = {};
                $(this).find("input,select").each(function() {
                    var key = $(this).attr("name");
                    var value = $(this).val();
                    data[key] = value;
                });
                dataArray.push(data);
            });
            return dataArray;
        },
        /**
         * 获取单选框当前值
         *
         * @method obj 表格父元素
         */
        getRadioCheckedRow: function(obj, message) {
            var _obj = obj.find("input[type='radio']:checked");
            if (_obj.length == 0) {
                if (message) {
                    window.XMS.msgbox.show(message, 'info', 2000);
                } else {
                    window.XMS.msgbox.show('请先选择一行数据！', 'info', 2000);
                }

                return;
            }
            var data = {};
            _obj.parents("tr").find("input").each(function() {
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key] = value;
            });
            return data;
        },
                /**
         * 设置下拉框数据
         *
         * @method obj 表单父元素
         */
        setSelectDataPost: function(obj, isComboSelect, other, callback) {
            var self = this;
            obj.find("select").each(function(index) {
                var _this = $(this);
                var _url = _this.data("url");
                var _cmd = _this.data("cmd") || '';
                if (other) {
                    _cmd += "&" + other;
                }
                if (_url) {
                    if (callback) {
                        self.setSelectHtmlPost(_this,isComboSelect, _url, _cmd, callback());
                    } else {
                        self.setSelectHtmlPost(_this,isComboSelect, _url, _cmd);
                    }

                }

            });

            obj.on("change", "select[data-subname]", function() {
                var _this = $(this);

                // 判断如果有异步子项，统一做处理
                var _subname = _this.data("subname");
                if (_subname) {
                    var _thisSub = obj.find("select[name=" + _subname + "]");
                    var suburl = _thisSub.data("suburl");
                    var subcmd = _this.attr("name") + "=" + _this.val();
                    if (suburl) {
                        self.setSelectHtmlPost(_thisSub,isComboSelect, suburl, subcmd);
                    }
                }
            })
        },
		        /**
         * 设置下拉框option节点
         *
         * @method obj 元素
         * @method obj 接口
         * @method obj 接口参数
         */
        setSelectHtmlPost: function(obj,isComboSelect, url, cmd, callback) {
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.postJson(srvMap.get(url), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var _data = json.data;
                    var _html = '<option value="">请选择</option>';

                    var idv = obj.data("idkey");
                    var namev = obj.data("namekey");

                    for (var i in _data) {
                        var _json = _data[i];
                        var _key, _value;

                        if (idv && namev) {
                            _key = _json[idv];
                            _value = _json[namev];
                        } else {
                            for (var key in _json) {
                                if (key.indexOf("Id") >= 0) {
                                    _key = _json[key];
                                }
                                if (key.indexOf("Name") >= 0) {
                                    _value = _json[key];
                                }
                            }
                        }
                        _html += '<option value="' + _key + '">' + _value + '</option>';

                    }
                    obj.html(_html);
                
                    if(isComboSelect) {
                    	self.clearSubOptions(obj,isComboSelect);
                    	obj.comboSelect();
                    } else {
                        self.clearSubOptions(obj);
                    }                
                    if (callback) {
                        callback();
                    }
                }
            });
        },
        /**
         * 获取复选框当前值和状态
         *
         * @method obj 表格父元素
         */
        getCheckboxCheckedRow: function(obj) {
            var _obj = obj.find("input[type='checkbox']:checked");
            if (_obj.length == 0) {
                window.XMS.msgbox.show('请先选择一行数据！', 'info', 2000);
                return;
            }
            var data = [];
            _obj.each(function(index, el) {
                var _data = {};
                $(el).parents("tr").find("input").each(function() {
                    var key = $(this).attr("name");
                    var value = $(this).val();
                    _data[key] = value;
                });
                data.push(_data);
            });

            return data;
        },
        /**
         * 表格行双击执行回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventDClickCallback: function(obj, callback) {
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            obj.find("tbody > tr").bind('dblclick ', function(event) {
                var _input = $(this).find('.minimal');
                _input.iCheck('toggle');
                var isChecked = _input.parent("div").attr("aria-checked");
                if (callback) {
                    callback(isChecked, _input);
                }
            });
        },
        /**
         * 设置下拉框数据
         *
         * @method obj 表单父元素
         */
        setSelectData: function(obj, other, callback) {
            var self = this;
            obj.find("select").each(function(index) {
                var _this = $(this);
                var _url = _this.data("url");
                var _cmd = _this.data("cmd") || '';
                if (other) {
                    _cmd += "&" + other;
                }
                if (_url) {
                    if (callback) {
                        self.setSelectHtml(_this, _url, _cmd, callback);
                    } else {
                        self.setSelectHtml(_this, _url, _cmd);
                    }

                }

            });

            obj.on("change", "select[data-subname]", function() {
                var _this = $(this);

                // 判断如果有异步子项，统一做处理
                var _subname = _this.data("subname");
                if (_subname) {
                    var _thisSub = obj.find("select[name=" + _subname + "]");
                    var suburl = _thisSub.data("suburl");
                    var subcmd = _this.attr("name") + "=" + _this.val();
                    if (suburl) {
                        self.setSelectHtml(_thisSub, suburl, subcmd);
                    }
                }
            });
        },

        /**
         * 清除子的option
         */
        clearSubOptions: function(obj,isComboSelect) {
            // 判断如果有异步子项，统一做处理
            var _subname = obj.data("subname");
            if (_subname) {
                var _thisSub = $("select[name=" + _subname + "]");
                _thisSub.html('<option value="">请选择</option>');
                if(isComboSelect) {      
                	_thisSub.comboSelect();
                	 this.clearSubOptions(_thisSub,isComboSelect);
                } else {
                    this.clearSubOptions(_thisSub);
                }                  
            }
        },

        /**
         * 设置下拉框option节点
         *
         * @method obj 元素
         * @method obj 接口
         * @method obj 接口参数
         */
        setSelectHtml: function(obj, url, cmd, callback) {
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.getJson(srvMap.get(url), cmd, function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var _data = json.data;
                    var _html = '<option value="">请选择</option>';

                    var idv = obj.data("idkey");
                    var namev = obj.data("namekey");
                    var combo = obj.hasClass('combo');

                    for (var i in _data) {
                        var _json = _data[i];
                        var _key, _value;

                        if (idv && namev) {
                            _key = _json[idv];
                            _value = _json[namev];
                        } else {
                            for (var key in _json) {
                                if (key.indexOf("Id") >= 0) {
                                    _key = _json[key];
                                }
                                if (key.indexOf("Name") >= 0) {
                                    _value = _json[key];
                                }
                            }
                        }
                        _html += '<option value="' + _key + '">' + _value + '</option>';

                    }
                    obj.html(_html);


                    if (callback) {
                        callback();
                    }
                    if (combo) {
                        obj.comboSelect();
                    }
                    self.clearSubOptions(obj);

                }
            });
        },
        /**
         * 设置当前元素下所有下拉框选中
         *
         * @method obj 元素
         */
        setSelected: function(obj) {
            obj.find("select").each(function(index) {
                var _data = $(this).data("selected");
                $(this).find("option[value='" + _data + "']").attr("selected", true)
            });
        },
        getServerPage: function(url, cmd, callback, obj, pageSize, displayPage) {
            var self = this;
            var page_index = 0;
            var page_count = 0;
            var items_per_page = pageSize || 10;
            var num_display_entries = displayPage || 8;
            $(obj).html(''); // 初始化清空分页
            function getDataList(index, jq) {
                var _cmd = '';
                if (!jq) {
                    if (cmd) {
                        _cmd = cmd + '&';
                        $(obj).data("cmd", cmd);
                    }
                } else {
                    var dataCmd = $(obj).data("cmd");
                    if (dataCmd) {
                        _cmd = dataCmd + '&';
                    }
                }
                var _cmd = _cmd + "page=" + index + '&pageSize=' + items_per_page;
                Rose.log('服务器端分页入参：' + _cmd);
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.postJson(url, _cmd, function(json, status) {
                    if (status) {
                        callback(json);
                        page_count = json.data.totalElements;
                        XMS.msgbox.hide();
                        if ($(obj).html() == '') {
                            $(obj).pagination(json.data.totalElements, {
                                items_per_page: items_per_page, //每页显示的条目数
                                num_display_entries: num_display_entries, //连续分页主体部分显示的分页条目数
                                num_edge_entries: 2, //两侧显示的首尾分页的条目数
                                prev_text: "上页",
                                next_text: "下页",
                                callback: pageselectCallback
                            });
                            setPageInfo(index);
                        }
                    }
                });
            }
            // 回调方法
            function pageselectCallback(page_index, jq) {
                getDataList(page_index, jq);
                setPageInfo(page_index);
            }
            // 初始化
            getDataList(page_index);


            function setPageInfo(pageIndex) {
                var _startSize = pageIndex == 0 ? 1 : pageIndex * items_per_page;
                var _endSize = (pageIndex + 1) * items_per_page > page_count ? page_count : (pageIndex + 1) * items_per_page;
                var _html = "<div class='dataTables_info pull-left'>第" + _startSize + "-" + _endSize + "条，共" + page_count + "条</div>";
                $(obj).prepend(_html);
            }

        },
        /*datalist下拉框设置*/
        setDataList: function(datalistObj, data, datakey) {
            var _html = "<option></option>";
            $.each(data, function(index, val) {
                /* iterate through array or object */
                _html += '<option value="' + val[datakey] + '"></option>';
            });

            datalistObj.html(_html);
        },

        /**
         * 树的搜索版本3 配合后端的树搜索
         * @param ztreeObj 树的Dom对象
         * @param srvName 搜索接口名
         * @param setting 树的设置
         *  @param ztreeSearchObj 搜索框对象 
         *  @param [searchKey] [搜索关键字]                
         */
        treeSearchNew: function(ztreeObj, srvName, setting, ztreeSearchObj, searchKey) {
            var _searchObj = $(ztreeSearchObj);
            var _btn = $(ztreeSearchObj).find('[name=searchBtn]');
            if (typeof(ztreeObj) != 'string') {
                _zObj = $(ztreeObj).attr("id");
            }
            _btn.unbind();
            _btn.bind("click", function() {
                var _value = _searchObj.find('[name=searchValue]').val();
                _value = $.trim(_value);
                Rose.ajax.getJson(srvMap.get(srvName), searchKey + '=' + _value, function(json, status) {
                    if (status) {
                        $.fn.zTree.init($(ztreeObj), setting, json.data);
                    } else {
                        XMS.msgbox.show(json.retMessage, 'error', 2000);
                    }
                });
                // if (_value != '') {
                //     $.fn.zTree.init($(ztreeObj), setting, searchByFlag_ztree(treedata, _value, "name"));
                //     // var treeObj = $.fn.zTree.getZTreeObj(_zObj);
                //     // treeObj.expandAll(true);
                // } else {
                //     $.fn.zTree.init($(ztreeObj), setting, treedata);
                // }
            });
            $(ztreeSearchObj).keydown(function() {
                if (event.keyCode == "13") { //keyCode=13是回车键
                    _btn.click();
                }
            });
        },


        /*树的搜索（版本2） 纯前端的树搜索*/
        treeSearch: function(ztreeObj, ztreeSearchObj, setting, treedata, nameKey) {
            var _searchObj = $(ztreeSearchObj);
            var _btn = $(ztreeSearchObj).find('[name=searchBtn]');
            var _zObj;
            if (typeof(ztreeObj) != 'string') {
                _zObj = $(ztreeObj).attr("id");

            }
            // var jia = [{
            //     "id": 13654,
            //     "pId": 13181,
            //     "name": "根目录",
            //     "ifLeaf": null
            // }, {
            //     "id": 13667,
            //     "pId": 13654,
            //     "name": "父目录",
            //     "ifLeaf": null
            // }, {
            //     "id": 136327,
            //     "pId": 13667,
            //     "name": "搜索结果",
            //     "ifLeaf": null
            // }];
            // var _key = searchKey || "organizeName";
            _btn.unbind();
            _btn.bind("click", function() {
                var _value = _searchObj.find('[name=searchValue]').val();
                _value = $.trim(_value);
                if (_value != '') {
                    $.fn.zTree.init($(ztreeObj), setting, searchByFlag_ztree(treedata, _value, "name"));
                    // var treeObj = $.fn.zTree.getZTreeObj(_zObj);
                    // treeObj.expandAll(true);
                } else {
                    $.fn.zTree.init($(ztreeObj), setting, treedata);
                }
            })

            /**
             * 搜索树，高亮显示并展示【模糊匹配搜索条件的节点s】
             * @param treeId
             * @param searchConditionId
             * @param key                  
             */
            function searchByFlag_ztree(data, searchConditionId, key) {
                //<1>.搜索条件
                var searchCondition = $.trim(searchConditionId);
                /*if(searchCondition==''){
                    XMS.msgbox.show('亲，搜索的关键字不能为空！', 'error', 2000);
                    return false;
                }*/
                //<2>.得到模糊匹配搜索条件的节点数组集合
                var highlightNodes = new Array();
                if (searchCondition != "") {
                    console.log(data);
                    $.each(data, function(index, val) {
                        /* iterate through array or object */
                        if (searchCondition == val[key]) {
                            highlightNodes.push(val);
                        }
                    });
                    highlightNodes = getChildData(data, highlightNodes)
                }
                //<3>.返回筛选后的数组
                console.log(highlightNodes)
                return highlightNodes;
            }

            /**
             * 搜索树，获取匹配结果的子节点
             * @param treeId
             * @param searchConditionId
             * @param key                  
             */
            function getChildData(data, selectData) {
                $.each(data, function(index, val) {
                    /* iterate through array or object */
                    $.each(selectData, function(i, v) {
                        /* iterate through array or object */
                        if (val.pid == v.id) {
                            if (checkData(val.id, selectData))
                                selectData.push(val);
                        }

                    });
                    console.log(index)
                });
                return selectData;
            }

            function checkData(value, data) {
                var flag = true;
                $.each(data, function(index, val) {
                    /* iterate through array or object */
                    if (value == val.id) {
                        flag = false;
                    }
                });
                return flag;
            }
        },

        /*树的搜索（初始版本）*/
        zTreeSearchInit: function(ztreeObj, ztreeSearchObj, searchKey) {
            var _searchObj = $(ztreeSearchObj);
            var _btn = $(ztreeSearchObj).find('[name=searchBtn]');
            var _key = searchKey || "organizeName";
            var _zObj = ztreeObj;
            Rose.log(ztreeObj);
            if (typeof(ztreeObj) != 'string') {
                _zObj = $(ztreeObj).attr("id");

            }

            _btn.unbind();
            _btn.bind("click", function() {
                var _value = _searchObj.find('[name=searchValue]').val();
                searchByFlag_ztree(_zObj, _value);
            })

            $(ztreeSearchObj).keydown(function() {
                if (event.keyCode == "13") { //keyCode=13是回车键
                    var _value = _searchObj.find('[name=searchValue]').val();
                    searchByFlag_ztree(_zObj, _value);
                }
            });

            /**
             * 展开树
             * @param treeId
             */
            function expand_ztree(treeId) {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.expandAll(true);
            }
            /**
             * 收起树：只展开根节点下的一级节点
             * @param treeId
             */
            function close_ztree(treeId) {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                var nodes = treeObj.transformToArray(treeObj.getNodes());
                var nodeLength = nodes.length;
                for (var i = 0; i < nodeLength; i++) {
                    if (nodes[i].id == '0') {
                        //根节点：展开
                        treeObj.expandNode(nodes[i], true, true, false);
                    } else {
                        //非根节点：收起
                        treeObj.expandNode(nodes[i], false, true, false);
                    }
                }
            }

            /**
             * 搜索树，高亮显示并展示【模糊匹配搜索条件的节点s】
             * @param treeId
             * @param searchConditionId     搜索条件Id
             * @param flag                  需要高亮显示的节点标识
             */
            function searchByFlag_ztree(treeId, searchConditionId, flag) {
                //<1>.搜索条件
                var searchCondition = $.trim(searchConditionId);
                /*if(searchCondition==''){
                    XMS.msgbox.show('亲，搜索的关键字不能为空！', 'error', 2000);
                    return false;
                }*/
                //<2>.得到模糊匹配搜索条件的节点数组集合
                var highlightNodes = new Array();
                if (searchCondition != "") {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    highlightNodes = treeObj.getNodesByParamFuzzy(_key, searchCondition, null);
                }
                //<3>.高亮显示并展示【指定节点s】
                highlightAndExpand_ztree(treeId, highlightNodes, flag);
            }

            /**
             * 高亮显示并展示【指定节点s】
             * @param treeId
             * @param highlightNodes 需要高亮显示的节点数组
             * @param flag           需要高亮显示的节点标识
             */
            function highlightAndExpand_ztree(treeId, highlightNodes, flag) {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                //<1>. 先把全部节点更新为普通样式
                var treeNodes = treeObj.transformToArray(treeObj.getNodes());
                console.log(treeNodes);
                for (var i = 0; i < treeNodes.length; i++) {
                    treeNodes[i].highlight = false;
                    treeObj.updateNode(treeNodes[i]);
                }
                //<2>.收起树, 只展开根节点下的一级节点
                close_ztree(treeId);
                //<3>.把指定节点的样式更新为高亮显示，并展开
                if (highlightNodes != null) {
                    for (var i = 0; i < highlightNodes.length; i++) {
                        if (flag != null && flag != "") {
                            if (highlightNodes[i].flag == flag) {
                                //高亮显示节点，并展开
                                highlightNodes[i].highlight = true;
                                treeObj.updateNode(highlightNodes[i]);
                                //高亮显示节点的父节点的父节点....直到根节点，并展示
                                var parentNode = highlightNodes[i].getParentNode();
                                var parentNodes = getParentNodes_ztree(treeId, parentNode);
                                treeObj.expandNode(parentNodes, true, false, true);
                                treeObj.expandNode(parentNode, true, false, true);
                            }
                        } else {
                            //高亮显示节点，并展开
                            highlightNodes[i].highlight = true;
                            treeObj.updateNode(highlightNodes[i]);
                            //高亮显示节点的父节点的父节点....直到根节点，并展示
                            var parentNode = highlightNodes[i].getParentNode();
                            var parentNodes = getParentNodes_ztree(treeId, parentNode);
                            treeObj.expandNode(parentNodes, true, false, true);
                            treeObj.expandNode(parentNode, true, false, true);
                            setFontCss_ztree(treeId, parentNode);
                        }
                    }
                }
            }

            /**
             * 递归得到指定节点的父节点的父节点....直到根节点
             */
            function getParentNodes_ztree(treeId, node) {
                if (node != null) {
                    var treeObj = $.fn.zTree.getZTreeObj(treeId);
                    var parentNode = node.getParentNode();
                    return getParentNodes_ztree(treeId, parentNode);
                } else {
                    return node;
                }
            }

            /**
             * 设置树节点字体样式
             */
            function setFontCss_ztree(treeId, treeNode) {
                if (treeNode) {
                    //Rose.log(treeNode.id);
                    if (treeNode.isParent == false) {
                        //叶子节点
                        return (!!treeNode.highlight) ? { color: "#f7245b", "font-weight": "bold" } : { color: "#333", "font-weight": "normal" };
                    } else {
                        //父节点
                        return (!!treeNode.highlight) ? { color: "#f7245b", "font-weight": "bold" } : { color: "#333", "font-weight": "normal" };
                    }
                }
            }
        },

        prenventPropagation: function(obj) {
            obj.click(function(event) {
                /* Act on the event */
                event.stopPropagation();
            });
        },
        /*全选*/
        selectAll: function(_table) {
            _table.find("[name='selectAll']").iCheck('destroy');
            _table.find("[name='selectAll']").iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            _table.find("[name='selectAll']").iCheck('uncheck');
            _table.find("[name='selectAll']").unbind();
            _table.find("[name='selectAll']").bind('ifChecked', function(event) {
                /* Act on the event */
                _table.find("input[type='checkbox']").iCheck('check');
            });
            _table.find("[name='selectAll']").bind('ifUnchecked', function(event) {
                /* Act on the event */
                _table.find("input[type='checkbox']").iCheck('uncheck');
            });
        },
        /*设置表格复选框的选中状态*/
        setCheckedTable: function(_obj) {
            _obj.find("[name='compId']").each(function(index, el) {
                if ($(el).attr('checkFlag') == 1) {
                    $(el).iCheck('check');
                }
            });
        },
        /**
         * 设置表格列宽度的可调节
         * @param table 表格的Dom对象 必须要用js来获取，不能用jquery
         *               
         */
        setWeithTable: function(table) {
            var tTD; //用来存储当前更改宽度的Table Cell,避免快速移动鼠标的问题   

            for (j = 0; j < table.rows[0].cells.length; j++) {
                table.rows[0].cells[j].onmousedown = function() {
                    //记录单元格
                    tTD = this;
                    if (event.offsetX > tTD.offsetWidth - 10) {
                        tTD.mouseDown = true;
                        tTD.oldX = event.x;
                        tTD.oldWidth = tTD.offsetWidth;
                    }
                    //记录Table宽度   
                    //table = tTD; while (table.tagName != ‘TABLE') table = table.parentElement;   
                    //tTD.tableWidth = table.offsetWidth;   
                };
                table.rows[0].cells[j].onmouseup = function() {
                    //结束宽度调整   
                    if (tTD == undefined) tTD = this;
                    tTD.mouseDown = false;
                    tTD.style.cursor = 'default';
                };
                table.rows[0].cells[j].onmousemove = function() {
                    //更改鼠标样式
                    if (event.offsetX > this.offsetWidth - 10)
                        this.style.cursor = 'col-resize';
                    else
                        this.style.cursor = 'default';
                    //取出暂存的Table Cell   
                    if (tTD == undefined) tTD = this;
                    //调整宽度   
                    if (tTD.mouseDown != null && tTD.mouseDown == true) {
                        tTD.style.cursor = 'default';
                        if (tTD.oldWidth + (event.x - tTD.oldX) > 0)
                            tTD.width = tTD.oldWidth + (event.x - tTD.oldX);
                        //调整列宽   
                        tTD.style.width = tTD.width;
                        tTD.style.cursor = 'col-resize';
                        //调整该列中的每个Cell   
                        table = tTD;
                        while (table.tagName != 'TABLE') table = table.parentElement;
                        for (j = 0; j < table.rows.length; j++) {
                            table.rows[j].cells[tTD.cellIndex].width = tTD.width;
                        }
                        //调整整个表   
                        //table.width = tTD.tableWidth + (tTD.offsetWidth – tTD.oldWidth);   
                        //table.style.width = table.width;   
                    }
                };
            }
        },
        /**
         * 公用确认框
         * @param callback 确认后执行的操作
         *               
         */
        confirmModal: function(callback) {
            var _modal = $("#Modal_confirm");
            _modal.modal('show');
            _modal.find("[name='Y']").unbind();
            _modal.find("[name='Y']").bind('click', function(event) {
                /* Act on the event */
                if (callback) {
                    callback();
                    _modal.modal('hide');
                }
            });
        },
        //json 序列化 url格式
        jsonToUrl: function(param, key) {
        	var self = this;
		    var paramStr="";
		    if(param instanceof String||param instanceof Number||param instanceof Boolean){
		        paramStr+="&"+key+"="+encodeURIComponent(param);
		    }else{
		        $.each(param,function(i){
		            var k= key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
		            paramStr+='&'+ self.jsonToUrl(this, k);
		        });
		    }
		    return paramStr.substr(1);
        },
        //初始化时间框
		showMonthFirstDay:function () {     
			var date=new Date();
			date.setDate(1);
			return Rose.date.dateTime2str(date,"yyyy-MM-dd");   
		},
		showYesterDay:function() {
			var yesterday = new Date(new Date().getTime() - 86400000);
			return Rose.date.dateTime2str(yesterday,"yyyy-MM-dd")
		}
    };

	Rose.ajax.download = function(url) {
		if (url) {
			var downa = $('<form class="mydownload" action="' + url
					+ '" method="get"></form>');
			downa.appendTo("body");
			downa.submit();
			downa.remove();
		}
	},

	module.exports = Utils;
});
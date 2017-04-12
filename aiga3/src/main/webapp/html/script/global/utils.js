/*
 * 通用工具集
 */
define(function(require, exports, module) {
    var Utils = {
         /**
         * 初始化页面唯一标识
         *
         * @method pageId 页面标识
         * @return object 闭包方法
         */
        initPage:function(pageId){
            return {
                id: '#'+pageId,
                // 查找元素
                find: function(obj){
                    return $(this.id).find(obj);
                },
                // 查找name元素
                findId: function(_id){
                    return $(this.id).find("#JS_"+_id);
                },
                // 查找name元素
                findName: function(name){
                    return $(this.id).find("[name='"+name+"']");
                },
                // 查找tpl，返回值是html代码段
                findTpl:function(tplId){
                    return $(this.id).siblings("#TPL_"+tplId).html();
                },
                // 查找modal
                findModal:function(modalId){
                    return $(this.id).siblings("#Modal_"+modalId);
                },
                // 查找findModalChildrenId即findModalCId
                findModalCId:function(_id){
                    return $(this.id).siblings('.modal').find("#JS_"+_id);
                },
                // 查找菜单ID
                getFunId: function(){
                    return $(this.id).parent().data("funid");
                }
            }
        },
        /**
         * 单复选框美化、单机选中、双击执行回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventTrClickCallback:function(obj,callback){
            this.eventClickChecked(obj);
            this.eventDClickCallback(obj,callback);
        },
         /**
         * 单复选框美化、单击执行回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventClickChecked:function(obj,callback){
            obj.find('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                  checkboxClass: 'icheckbox_square-blue',
                  radioClass: 'iradio_square-blue'
            });
            obj.find("tr").bind('click', function(event) {
                var _input = $(this).find('.minimal');
                _input.iCheck('toggle');
                var isChecked = _input.parent("div").attr("aria-checked");
                if (callback) {
                    callback(isChecked,_input);
                }
            });
        },
         /**
         * 表单验证
         *
         * @method objForm 表单父元素
         * @callback Fun 回调函数
         */
        checkForm:function(objForm,callback){
            var state = true;
            var text = '';
            $(objForm).find(':input[required]')
            .not(':button, :submit, :reset, :hidden').each(function(){
                var _val = $.trim($(this).val());
                var _text = $.trim($(this).prev().text());
                if(_val == null || _val == undefined || _val == ''){
                    state = false;
                    text = _text;
                    return false;
                }
            })
            if(state){
                 callback(state);
            }else{
                XMS.msgbox.show(text.trimStar()+'不能为空！', 'error', 2000);
            }
        },
         /**
         * 清空表单所有的
         *
         * @method obj 父元素
         */
        resetForm:function(objForm){
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
        initStep:function(obj){
            var self = this;
            $(obj).find("[data-gostep]").bind("click",function(){
                self.goStep($(obj),$(this).data("gostep"));
            })
        },
        goStep:function(obj,num){
            $(obj).find("div[data-steps='"+num+"']").removeClass('hide').siblings().addClass('hide');
        },
        setScroll:function(obj,height){
            obj.slimScroll({
                "height": height
            });
        },
         /**
         * 获取表格所有行的数据
         *
         * @method obj 表格父元素
         */
        getTableDataRows:function(obj){
            var dataArray = []
            obj.find("tbody > tr").each(function(){
                var data ={};
                $(this).find("input,select").each(function(){
                    var key = $(this).attr("name");
                    var value = $(this).val();
                    data[key]=value;
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
        getRadioCheckedRow:function(obj){
            var _obj = obj.find("input[type='radio']:checked");
            if(_obj.length==0){
                window.XMS.msgbox.show('请先选择一行数据！', 'info', 2000);
                return;
            }
            var data ={};
            _obj.parents("tr").find("input").each(function(){
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key]=value;
            });
            return data;
        },
         /**
         * 获取复选框当前值和状态
         *
         * @method obj 表格父元素
         */
        getCheckboxCheckedRow:function(obj){
            var _obj = obj.find("input[type='checkbox']:checked");
            if(_obj.length==0){
                window.XMS.msgbox.show('请先选择一行数据！', 'info', 2000);
                return;
            }
            var data ={};
            _obj.parents("tr").find("input").each(function(){
                var key = $(this).attr("name");
                var value = $(this).val();
                data[key]=value;
            });
            return data;
        },
         /**
         * 表格行双击执行回调函数
         *
         * @method obj 表格父元素
         * @callback Fun 回调函数
         */
        eventDClickCallback:function(obj,callback){
            obj.find("tr").bind('dblclick ', function(event) {
                    if (callback) {
                        callback();
                    }
            });
        },
         /**
         * 设置下拉框数据
         *
         * @method obj 表单父元素
         */
        setSelectData:function(obj){
            var self = this;
            obj.find("select").each(function(index) {
                var _this = $(this);
                var _url = _this.data("url");
                var _cmd = _this.data("cmd") || '';
                if(_url){
                    self.setSelectHtml(_this,_url,_cmd);
                }

            });

            obj.on("change", "select[data-subname]", function(){
            	var _this = $(this);

            	// 判断如果有异步子项，统一做处理
                var _subname = _this.data("subname");
                if(_subname){
                    var _thisSub = obj.find("select[name="+_subname+"]");
                    var suburl = _thisSub.data("suburl");
                    var subcmd = _this.attr("name")+"="+_this.val();
                    if(suburl){
                    	 self.setSelectHtml(_thisSub,suburl,subcmd);
                    }
                }
            })
        },

        /**
         * 清除子的option
         */
        clearSubOptions:function(obj){

        	// 判断如果有异步子项，统一做处理
            var _subname = obj.data("subname");
            if(_subname){
                var _thisSub = $("select[name="+_subname+"]");
                _thisSub.html('<option value="">请选择</option>');

                this.clearSubOptions(_thisSub);
            }
        },

         /**
         * 设置下拉框option节点
         *
         * @method obj 元素
         * @method obj 接口
         * @method obj 接口参数
         */
        setSelectHtml:function(obj,url,cmd){
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Rose.ajax.getJson(srvMap.get(url),cmd, function(json, status) {
                if(status) {
                    window.XMS.msgbox.hide();
                    var _data = json.data;
                    var _html = '<option value="">请选择</option>';

                    var idv = obj.data("idkey");
                    var namev = obj.data("namekey");

                    for (var i in _data){
                        var _json = _data[i];
                        var _key,_value;

                        if(idv && namev){
                        	_key = _json[idv];
                        	_value = _json[namev];
                        }else{
                        	for (var key in _json){
                                if(key.indexOf("Id")>=0){
                                    _key = _json[key];
                                }
                                if(key.indexOf("Name")>=0){
                                    _value = _json[key];
                                }
                            }
                        }
                        _html+='<option value="'+_key+'">'+_value+'</option>';

                    }
                    obj.html(_html);
                    self.clearSubOptions(obj);
                }
            });
        },
         /**
         * 设置当前元素下所有下拉框选中
         *
         * @method obj 元素
         */
        setSelected:function(obj){
            obj.find("select").each(function(index) {
                var _data = $(this).data("selected");
                $(this).find("option[value='"+_data+"']").attr("selected",true)
            });
        },
        getServerPage:function(url,cmd,callback,obj,pageSize){
            var self = this;
            var page_index = 0;
            var items_per_page = 10 || pageSize;
            $(obj).html(''); // 初始化清空分页
            function getDataList(index,jq){
                var _cmd = '';
                if(!jq){
                    if(cmd){
                        _cmd = cmd+'&';
                        $(obj).data("cmd",cmd);
                    }
                }else{
                    var dataCmd = $(obj).data("cmd");
                    if(dataCmd){
                        _cmd = dataCmd+'&';
                    }
                }
                var _cmd = _cmd + "page="+index+'&pageSize='+items_per_page;
                alert(_cmd);
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                Rose.ajax.postJson(url, _cmd, function(json, status) {
                    if(status) {
                        callback(json);
                        if($(obj).html()== ''){
                            $(obj).pagination(json.data.totalElements, {
                                items_per_page      : items_per_page, //每页显示的条目数
                                num_display_entries : 10, //连续分页主体部分显示的分页条目数
                                num_edge_entries    : 2, //两侧显示的首尾分页的条目数
                                prev_text           : "上一页",
                                next_text           : "下一页",
                                callback           : pageselectCallback
                            });
                        }
                    }
                });
            }
            // 回调方法
            function pageselectCallback(page_index,jq){
                getDataList(page_index,jq);
            }
            // 初始化
            getDataList(page_index);

        }
    };
    module.exports = Utils;
});

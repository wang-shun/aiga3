/*
 * 通用工具集
 */
define(function(require, exports, module) {
    var Utils = {
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
                $(this).find("input").each(function(){
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
        }
    };
    module.exports = Utils;
});

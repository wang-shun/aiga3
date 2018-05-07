define(function(require,exports,module){
	// 通用工具模块
    var Utils = require("global/utils.js");

    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('staffApplyManage');
	// 路径重命名
	var pathAlias = "systemManage/staffManage/";

	// 组织结构列表查询
	srvMap.add("findApplyStaff", "", "staff/info/findApply");	
	//驳回接口	
	srvMap.add("rejectIn", pathAlias+"rejectIn.json", "staff/info/rejet");	
	//确认信息通过
	srvMap.add("acceptIn", pathAlias+"rejectIn.json", "staff/info/accept");
	//查询所有岗位信息
    srvMap.add("getStaffRoleList", pathAlias + "getStaffRoleList.json", "sys/role/list");
    
	// 模板对象
    var Tpl = {
        getStaffRoleList: require('tpl/staffRole/getStaffRoleList.tpl')
    };
    
    // 容器对象
    var Dom = {
    	getStaffRoleList: 'getStaffRoleList',
        stepDom : null,
        getStaffRoleListTable: 'getStaffRoleListTable'
    };
    
    //暂存数据
    var Cache = {
    	applyData:null,
    	data: null,
    	roleId:null
    };

	var Query = {
		init: function(){
			this._render();
		},
		_render:function() {
			this._staff_apply_load();		
			this._step_load();
		},
		_staff_apply_load:function(){
			var self = this;
			var tableDom = Page.findId('staffApplyList');
			var _domPagination = tableDom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('findApplyStaff'),"",function(json){
				Cache.applyData = json.data.content;
				var template = Handlebars.compile(Page.findTpl('getStaffApplyList'));			
				tableDom.find("[name='content']").html(template(json.data.content));
        		Utils.eventDClickCallback(tableDom,function(isChecked,_input) {

        			//清空cache
        			Cache.data = null;             			
        			var applyId = _input[0].value;
        			var datas = Cache.applyData;
        			for(var i in datas) {
        				if(datas[i].applyId == applyId){
        					Cache.data = datas[i];
        					break;
        				}
        			}
        			Dom.stepDom.toStep(0);
        			var stepTemplate = Handlebars.compile(Page.findTpl('staffApplyStep1'));			
    				Page.findId("stepContent").html(stepTemplate(Cache.data));
    				//绑定不通过按钮
    				self._reject();
    				//绑定下一步按钮
    				var _stepContent = Page.findId('stepContent');
    				_stepContent.find("[name='nextStep1']").off('click').on('click',function() {
    					Dom.stepDom.nextStep();
    					self._step2();
    				});
        		});
			},_domPagination);
		},
		_step_load:function() {
			var stepDom = Page.findId("step");
			stepDom.step({
				index: 0,
				time: 500,
				title: ["申请人信息","分配权限","确认信息","完成"]
			});
			Dom.stepDom = stepDom;
			var _stepContent = Page.findId('stepContent');
			_stepContent.find("[name='nextStep']").off('click').on('click',function() {
				stepDom.nextStep();
			});
		},
		//绑定不通过按钮事件
        _reject: function() {
			var self = this;
			var _stepContent = Page.findId('stepContent');			 
			var _rejectBtn = _stepContent.find("[name='reject']");
			var isRun = false;
			_rejectBtn.off('click').on('click',function(){
				if(isRun){
		             return;
		         } else {
		        	 isRun = true;
			         setTimeout(function(){
			             isRun=false;
			         },1500); //点击后相隔多长时间可执行
		         }

				var cmd = "applyId="+Cache.data.applyId; 
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('rejectIn'),cmd,function(json, status){
					if(status) {
						XMS.msgbox.show('申请单驳回成功', 'success', 1500);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
				
			});		
        },
        //第二步
        _step2:function(){
        	_roleIdsArray = [];
        	var stepTemplate2 = Handlebars.compile(Page.findTpl('staffApplyStep2'));			
			Page.findId("stepContent").html(stepTemplate2(Cache.data));
			this.getStaffRoleList();
			this._next_step2();
			
        },
        //获取岗位授权列表
        getStaffRoleList: function(cmd) {
            Rose.ajax.postJson(srvMap.get('getStaffRoleList'), cmd, function(json, status) {
                var self = this;
                if (status) {
                    var template = Handlebars.compile(Tpl.getStaffRoleList);
                    Page.findId(Dom.getStaffRoleList).html(template(json.data));
                    //iCheck
                    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                        checkboxClass: 'icheckbox_minimal-blue',
                        radioClass: 'iradio_minimal-blue'
                    });
                    if(_roleIdsArray.length != 0){
                    	for (x in _roleIdsArray) {
                            $("#JS_role_" + _roleIdsArray[x]).iCheck('check');
                        }
                    }
                }
            });
        },
        //分配权限
        _next_step2: function() {
            var self = this;
            nextStep2 = Page.findId("stepContent").find("[name='nextStep2']");
            nextStep2.bind('click', function() {
                var _dom = Page.findId(Dom.getStaffRoleListTable).find("input[name='roleId']:checked");
                _roleIdsArray = [];
                _dom.each(function() {
                    _roleIdsArray.push($(this).val());
                })
                Cache.roleId = _roleIdsArray.join(",");

                if (_roleIdsArray.length == 0) {
                    window.XMS.msgbox.show('请先选择一个授权权限点！', 'error', 2000);
                    return;
                }
                Dom.stepDom.nextStep();
                self._step3();
            });
        },
        
        //第三步
        _step3:function(){
        	var self = this;
        	var stepTemplate3 = Handlebars.compile(Page.findTpl('staffApplyStep3'));			
			Page.findId("stepContent").html(stepTemplate3(Cache.data));
			
			this.getStaffRoleList();
			nextStep3 = Page.findId("stepContent").find("[name='nextStep3']");
			nextStep3.off('click').on('click',function() {
				debugger
				var cmd = "applyId="+Cache.data.applyId;
				Rose.ajax.postJson(srvMap.get('acceptIn'),cmd,function(json, status){
					debugger
					if(status) {
						XMS.msgbox.show('申请单认定通过', 'success', 1500);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
				Dom.stepDom.nextStep();
				self._step4();
			});
            
        },
        //第四步
        _step4:function(){       	
        	var stepTemplate4 = Handlebars.compile(Page.findTpl('staffApplyStep4'));			
			Page.findId("stepContent").html(stepTemplate4(Cache.data));
			
        },

        
	};
	module.exports = Query;
});


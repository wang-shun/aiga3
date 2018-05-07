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
	
	// 模板对象
    var Tpl = {
        getUserinfoList: require('tpl/systemManage/staffManage/getUserinfoList.tpl'),
    };

    // 容器对象
    var Dom = {
        stepDom : null,
    };
    
    //暂存数据
    var Cache = {
    	applyData:""
    } 

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
        			var applyId = _input[0].value;
        			var datas = Cache.applyData;
        			var data = null;
        			for(var i in data) {
        				if(data[i].applyId == applyId){
        					data = data[i];
        					break;
        				}
        			}
        			Dom.stepDom.toStep(0);
        			var stepTemplate = Handlebars.compile(Page.findTpl('staffApplyStep1'));			
    				Page.findId("stepContent").html(stepTemplate(datas[0]));
    				self._reject();
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
			Page.find("[name='nextStep']").off('click').on('click',function() {
				stepDom.nextStep();
			});
		},
		//绑定不通过按钮事件
        _reject: function() {
			var self = this;
			var _stepContent = Page.findId('stepContent');			 
			//Utils.setSelectData(_form);		 
			var _rejectBtn = _stepContent.find("[name='reject']");
			var isRun = false;
			_rejectBtn.off('click').on('click',function(){
				debugger
				if(isRun){
		             return;
		         } else {
		        	 isRun = true;
			         setTimeout(function(){
			             isRun=false;
			         },1500); //点击后相隔多长时间可执行
		         }
				var datas = Cache.applyData;
				
				//var _cmd = jQuery.param(datas[0]);
				cmd = "applyId="+datas[0].applyId; 
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('rejectIn'),cmd,function(json, status){
					debugger
					if(status) {
						XMS.msgbox.show('审批不通过', 'error', 1500);
						datas[0].state = '3';
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
				
			});		
        },
	};
	module.exports = Query;
});


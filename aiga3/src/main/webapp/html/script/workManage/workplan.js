define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "workManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('workPlan');


	//显示查询信息表
	srvMap.add("getWorkPlanList", pathAlias+"getList.json", "archi/workplan/queryByCondition");
	//申请页面保存
	srvMap.add("workPlanSave", pathAlias+"workSave.json", "archi/workplan/save");
	//申请页面下拉框查询
	srvMap.add("workplanState", pathAlias+"workState.json", "archi/static/workplanState");
	//更新页面
	srvMap.add("workplanUpdate", pathAlias+"workUpdate.json", "archi/workplan/update");
	//删除页面
	srvMap.add("workplanDelete", pathAlias+"workDelete.json", "archi/workplan/delete");
	
	
	var cache = {
			datas : ""
		};
	var init = {
				
		init: function() {			 
			this._render();
		},
		
		_render: function() {
			//查询
			this._query_event();
			this._applydomain();
			this._getGridList();
			
		},
		
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('workPlanList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getWorkPlanList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('workPlanTemp'));				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));

        		Utils.eventTrClickCallback(_dom);
        		
        		//删除
				self.delDataMaintain();
				//双击修改
				self.eventDClickCallback(_dom, function() {
					//获得当前单选框值
					var data = Utils.getRadioCheckedRow(_dom);

					self.updateDataMain(data.id, json.data);
				});
			},_domPagination);
		},
       
        //申请页面里面的数据校验
        _applydomain: function() {
			var self = this;
				var _modal = Page.findId('workApplyModal');
				//打开模态框
				//_modal.modal('show');

				Utils.setSelectData(_modal);

				var saveBtn = _modal.find("[name='save']");

				saveBtn.off('click').on('click',function(){
					//获取表单数据
					var _form = Page.findId("workApplyForm");
					var _cmd = _form.serialize();	
				
					//数据校验
					if(_cmd.indexOf('name=&')>-1) {
						XMS.msgbox.show('责任人为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('matters=&')>-1) {
						XMS.msgbox.show('事项为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('completion=&')>-1) {
						XMS.msgbox.show('完成情况为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('classification=&')>-1) {
						XMS.msgbox.show('分类为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('jobContent=&')>-1) {
						XMS.msgbox.show('工作内容为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('projectCompletion=&')>-1) {
						XMS.msgbox.show('计划完成率为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('submitTimely=&')>-1) {
						XMS.msgbox.show('提交及时性为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('Quality=&')>-1) {
						XMS.msgbox.show('质量说明为空！', 'error', 2000);
						return
					}
					if(_cmd.indexOf('fillQuality=&')>-1) {
						XMS.msgbox.show('填写质量为空！', 'error', 2000);
						return
					}
					
					if(_cmd.indexOf('begainTime=&')>-1) {
						XMS.msgbox.show('开始时间为空！', 'error', 2000);
						return
					}
					var endTimeValue = _form.find('[name="endTime"]').val();
					if(endTimeValue == ''){
						XMS.msgbox.show('结束时间为空！', 'error', 2000);
						return
					}
					var _CMD = _cmd.replace(/-/g,"/");
					
					//调用服务
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					
					Rose.ajax.postJson(srvMap.get('workPlanSave'),_CMD,function(json, status){
						if(status) {							
							_modal.modal('hide');
							XMS.msgbox.show('新增成功！', 'success', 2000);
							setTimeout(function() {
								self._getGridList();
							}, 1000);
						} else {
							
							XMS.msgbox.show(json.retMessage, 'error', 2000);
						}					
					});
				});
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
        _query_event: function() {
        	 
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			 
			Utils.setSelectData(_form);
			 
			var _queryBtn = _form.find("[name='query']");
/*			var _applyBtn = _form.find("[name='add']");*/
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				if (cmd.indexOf('name=&')>-1) {
					XMS.msgbox.show('请选择责任人', 'error', 1000);
					return
				}
				if (cmd.indexOf('classification=&')>-1) {
					XMS.msgbox.show('请选择分类', 'error', 1000);
					return
				}
								
				self._getGridList(cmd);
			});		
        },
      
        //更新数据
		updateDataMain: function(Id, json) {
			var self = this;
			var i=0;			
			while(json.content[i].id != Id){
				i++;
			}
			var data = json.content[i];
						
			var template = Handlebars.compile(Page.findTpl('workUpdateFrom'));
			Page.findId('updateDataWorkPlan').html(template(data));
			var _dom = Page.findModal('workUpdateModal');
			_dom.modal('show');			
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				debugger
				var _form = Page.findId('updateDataWorkPlan');
				var _cmd = _form.serialize();
				_cmd=_cmd.replace(/-/g,"/");
				_cmd = _cmd + "&id=" + Id;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('workplanUpdate'), _cmd, function(json, status) {
					if (status) {
						debugger
						window.XMS.msgbox.show('更新成功！', 'success', 2000);
						setTimeout(function() {
							self._getGridList();
							_dom.modal('hide');
						}, 1000);
					} else {
							window.XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
				});
			});

		},
		
		
		//删除数据
		delDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('workPlanList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'id=' + data.id;
					//alert(cmd);//////////
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('workplanDelete'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self._getGridList();
							}, 1000);
						}
					});
				}
			});
		},
		
    
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tbody tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		}
        
        
        
        
	};
	 
	module.exports = init;
});
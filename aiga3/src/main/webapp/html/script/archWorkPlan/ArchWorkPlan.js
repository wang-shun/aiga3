define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchWorkPlan/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('workPlan');


	//显示查询信息表
	srvMap.add("getWorkPlanList", pathAlias+"getList.json", "archi/workplan/queryByCondition");
	//申请页面保存
	srvMap.add("workPlanSave", pathAlias+"workSave.json", "archi/workplan/save");
	//分类下拉框
	srvMap.add("workplanState", pathAlias+"workplanState.json", "archi/static/workplanState");
	//工作状态下拉框
	srvMap.add("workState", pathAlias+"workState.json", "archi/static/workState");
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
			var _cmd = 'name=&classification=';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('workPlanList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			_cmd = _cmd.replace(/-/g,"/");
					
			//初始化时间框
			function showMonthFirstDay() {     
				var date=new Date();
			 	date.setDate(1);
			 	return Rose.date.dateTime2str(date,"yyyy-MM-dd");   
			}
			var _form = Page.findId('queryDataForm'); 
			_form.find('[name="begaintime"]').val(showMonthFirstDay());
			_form.find('[name="endtime"]').val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
			var _formQuery = Page.findId("workApplyForm");
			_formQuery.find('[name="begaintime"]').val(showMonthFirstDay());
			_formQuery.find('[name="endtime"]').val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getWorkPlanList'),_cmd,function(json){
				window.XMS.msgbox.hide();				
				var template = Handlebars.compile(Page.findTpl('workPlanTemp'));				
        		var tablebtn = _dom.find("[name='content']");
        		for(var index in json.data.content){
					json.data.content[index].begaintime = json.data.content[index].begaintime.substring(0,10);
					json.data.content[index].endtime = json.data.content[index].endtime.substring(0,10);
				}
        		tablebtn.html(template(json.data.content));

        		Utils.eventTrClickCallback(_dom);
        		//新增
        		self._applydomain();
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
       
        //添加页面
        _applydomain: function() {
			var self = this;
				var _modal = Page.findId('workApplyModal');
				Utils.setSelectData(_modal);

				var saveBtn = _modal.find("[name='save']");

				saveBtn.off('click').on('click',function(){
					//获取表单数据
					var _form = Page.findId("workApplyForm");
					var _cmd = _form.serialize();	
				
					//数据校验
					var name = _form.find("[name='name']").val();					
					if(name == 0) {
						XMS.msgbox.show('责任人为空！', 'error', 2000);
						return
					}
					var matters = _form.find("[name='matters']").val();
					if(matters == 0) {
						XMS.msgbox.show('事项为空！', 'error', 2000);
						return
					}
					var classification = _form.find("[name='classification']").val();
					if(classification == 0) {
						XMS.msgbox.show('分类为空！', 'error', 2000);
						return
					}
					var jobcontent = _form.find("[name='jobcontent']").val();
					if(jobcontent == 0) {
						XMS.msgbox.show('工作内容为空！', 'error', 2000);
						return
					}
					var workstate = _form.find("[name='workstate']").val();
					if(workstate == 0) {
						XMS.msgbox.show('工作状态为空！', 'error', 2000);
						return
					}
					var bgtime = _form.find("[name='begaintime']").val();
					var endtime = _form.find("[name='endtime']").val();
					if(bgtime == 0) {
						XMS.msgbox.show('开始时间为空！', 'error', 2000);
						return
					}
					if(endtime == 0) {
						XMS.msgbox.show('结束时间为空！', 'error', 2000);
						return
					}
					if(bgtime>endtime){
						XMS.msgbox.show('结束时间小于开始时间！', 'error', 2000);
						return
					}
					
					var person = _form.find("[name='person']").val();
					if(person == 0) {
						XMS.msgbox.show('提出人为空！', 'error', 2000);
						return
					}
					
					var _CMD = _cmd.replace(/-/g,"/");
					
					//调用服务
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					
					Rose.ajax.postJson(srvMap.get('workPlanSave'),_CMD,function(json, status){
						if(status) {
							var _form = Page.findId("workApplyForm");
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
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');			 
			Utils.setSelectData(_form);		 
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				
				var bgtime = _form.find("[name='begaintime']").val();
				var endtime = _form.find("[name='endtime']").val();
				if(bgtime == 0) {
					XMS.msgbox.show('开始时间为空！', 'error', 2000);
					return
				}
				if(endtime == 0) {
					XMS.msgbox.show('结束时间为空！', 'error', 2000);
					return
				}
				if(bgtime>endtime){
					XMS.msgbox.show('结束时间小于开始时间！', 'error', 2000);
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
			Utils.setSelectData(_dom,'',function (){
				var _mod = Page.findId('updateDataWorkPlan');
				var stateDom = _mod.find("[name='workstate']");
				var classDom = _mod.find("[name='classification']");
				stateDom.val(data.workstate);
				classDom.val(data.classification);
			});

			
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Page.findId('updateDataWorkPlan');
				var _cmd = _form.serialize();	
			
				//数据校验
				var name = _form.find("[name='name']").val();					
				if(name == 0) {
					XMS.msgbox.show('责任人为空！', 'error', 2000);
					return
				}
				var matters = _form.find("[name='matters']").val();
				if(matters == 0) {
					XMS.msgbox.show('事项为空！', 'error', 2000);
					return
				}

				var classification = _form.find("[name='classification']").val();
				if(classification == 0) {
					XMS.msgbox.show('分类为空！', 'error', 2000);
					return
				}
				var jobcontent = _form.find("[name='jobcontent']").val();
				if(jobcontent == 0) {
					XMS.msgbox.show('工作内容为空！', 'error', 2000);
					return
				}
				var workstate = _form.find("[name='workstate']").val();
				if(workstate == 0) {
					XMS.msgbox.show('工作状态为空！', 'error', 2000);
					return
				}
				var bgtime = _form.find("[name='begaintime']").val();
				if(bgtime == 0) {
					XMS.msgbox.show('开始时间为空！', 'error', 2000);
					return
				}
				var endtime = _form.find("[name='endtime']").val();
				if(endtime == 0) {
					XMS.msgbox.show('结束时间为空！', 'error', 2000);
					return
				}
				if(bgtime>endtime){
					XMS.msgbox.show('结束时间小于开始时间！', 'error', 2000);
					return
				}	
				var person = _form.find("[name='person']").val();
				if(person == 0) {
					XMS.msgbox.show('提出人为空！', 'error', 2000);
					return
				}
				_cmd=_cmd.replace(/-/g,"/");
				_cmd = _cmd + "&id=" + Id;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('workplanUpdate'), _cmd, function(json, status) {
					if (status) {
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
					var cmd = 'id=' + data.id;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('workplanDelete'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self._getGridList();
							}, 1000);
						}else {
							window.XMS.msgbox.show(json.retMessage, 'error', 2000);
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
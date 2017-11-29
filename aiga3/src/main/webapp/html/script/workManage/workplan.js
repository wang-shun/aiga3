define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "workManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('workPlan');
    //一级域下拉框查询
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
    //根据一级查询二级子域
    srvMap.add("getSecondByFirst", pathAlias+"secondDomainList.json", "archi/second/listByfirst");
    //二级子域查询
    srvMap.add("getSecondDomainList", pathAlias+"secondDomainList.json", "archi/second/list");
	//显示系统信息表
	srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
	//三级系统操作信息保存
	srvMap.add("workPlanSave", pathAlias+"getSysMessageList.json", "archi/grading/thirdGradingAdd");
	//系统状态静态数据  
	srvMap.add("thirdSysState", pathAlias+"getSysMessageList.json", "archi/static/archiBuildingState");

	//等级信息
    srvMap.add("rankInfoStatic", pathAlias + "getDeliverablesList.json", "archi/static/rankInfo");    
	var idcache = {
		onlysysId : ""
	}
	
	var cache = {	
		datas : "",     	//查询出的系统信息
		secName: ""			//二级子域名称
	};

	var init = {
		init: function() {
			var self = this;
			self.jumpPage();
			self._render();
			
		},
		_render: function() {
			var self = this;
			self._queryTypeDomain();
		},
		jumpPage : function(){
			var syscmd = Page.getParentCmd();
			var result = Utils.jsonToUrl(syscmd);
			if(result!=null){
				var self = this;
				var _form = Page.findId('querySysDomainTypeForm');
				Utils.setSelectData(_form);
				var _queryBtn = _form.find("[name='query']");
				_queryBtn.unbind('click').bind('click', function() {
					var cmd = result;

					self._getGridList(cmd);
				});
				_queryBtn.click();
			}
		},
        
        //申请页表单清除
        _apply_data_clear: function() {
        	var domInput = Page.findId("thirdApplyForm").find('input,textarea');
        	domInput.each(function() {
        		var domNow = $(this);
        		if(domNow.attr("type") == 'text') {
        			domNow.val('');
        		} else if(domNow.attr("type") == 'checkbox') {
        			domNow.removeAttr("checked");
        		} else if(domNow.attr("type") == 'textarea') {
        			domNow.val('');
        		} else if(domNow.attr("type") == 'file') {
        			domNow.after(domNow.clone().val(""));      
        			domNow.remove();  
        		}
        	});
        },
		//申请页中保存按钮提交事件
        _apply_save_event: function(_modal, planId) {
        	var self = this;
			//获取表单数据
			var _form = Page.findId("workApplyForm");
			var _cmd = _form.serialize();	
			//获取分层层级
			var applyHierarchy = Page.find("[name='hierarchy']");	
			var belongLevel = '';
			Page.find("[name='hierarchy']:checked").each(function() {
				belongLevel += $(this).val()+',';
	        });			
			belongLevel=belongLevel.substring(0,belongLevel.length-1);
			_cmd += '&belongLevel='+belongLevel;
			if(planId) {
				_cmd += '&fileId='+planId;
			}	
			_cmd += '&ext3='+ Page.find("[name='groupApply']:checked").val();
			_cmd += '&ext1=3&description=新增';
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
			if(_cmd.indexOf('endTime=&')>-1) {
				XMS.msgbox.show('结束时间为空！', 'error', 2000);
				return
			}
			
			//调用服务
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('workPlanSave'),_cmd,function(json, status){
				if(status) {
					_modal.modal('hide');
					if(planId) {
						XMS.msgbox.show('文件已上传，申请成功，请等待认定！', 'success', 2000);
					} else {
						XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
					}	
					//成功后表单清除
					self._apply_data_clear();
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
        },
		//查询下拉框数据加载，绑定查询按钮事件
        _queryTypeDomain: function() {debugger
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			var _applyBtn = _form.find("[name='add']");
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
				if (cmd.indexOf('begainTime=&')>-1) {
					XMS.msgbox.show('请选择开始时间', 'error', 1000);
					return
				}
				if (cmd.indexOf('endTime=&')>-1) {
					XMS.msgbox.show('请选择结束时间', 'error', 1000);
					return
				}
								
				self._getGridList(cmd);
			});		
			_applyBtn.off('click').on('click',function() {
				//打开模态框
				var _modal = Page.findId('workApplyModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);	
				//保存按钮
				var saveBtn =_modal.find("[name='save']");
				saveBtn.off('click').on('click',function(){
					//先文件上传，成功后再提交
					self.uploadAnNiu(_modal);
				});
			});
			//查询二级域名称
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('getSecondDomainList'),'',function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					cache.secName = json.data;
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},	
/*		//condition绑定查询按钮事件
		_queryConditionDomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainConditionForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			var _applyBtn = _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();			
				self._getGridList(cmd);
			});		
			_applyBtn.off('click').on('click',function() {
				//打开模态框
				var _modal = Page.findId('thirdApplyModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);	
				//保存按钮
				var saveBtn = _modal.find("[name='save']");
				saveBtn.off('click').on('click',function(){
					//先文件上传，成功后再提交
					self.uploadAnNiu(_modal);
				});
			});
		},*/
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
			Utils.getServerPage(srvMap.get('getSysMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				json.data.content.sort(function(a,b){
		            return a.idThird-b.idThird;});
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('workPlanTemp'));				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventTrClickCallback(_dom);
        		tablebtn.find("[class='btn btn-primary btn-table-update']").off('click').on('click', function() {
        			self._band_table_btn($(this),"update");
        		});
        		tablebtn.find("[class='btn btn-primary btn-table-delete']").off('click').on('click', function() {
        			self._band_table_btn($(this),"delete");
        		});
			},_domPagination);
		},
		
		_band_table_btn: function(dom, type) {
			//取出选中数据
			var id = dom.attr("temp");
			var index = 0;
			while(id != cache.datas[index].onlysysId) {
				index++;
			}
			var subData = cache.datas[index];
			subData.modifyDate = subData.modifyDate.replace(/-/g,"/");
			subData.createDate = subData.createDate.replace(/-/g,"/");
			//修改操作
			if(type == 'update') {
				var m=0;	
				while(cache.secName[m].idSecond!=subData.idSecond) {
					m++;
				}
				subData.idSecondName = cache.secName[m].name;
				var template = Handlebars.compile(Page.findTpl('workUpdateFrom'));
				Page.findId('updateModal').html(template(subData));
				var _modal = Page.findId('workUpdateModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
				_modal.off('shown.bs.modal').on('shown.bs.modal', function () {

					//状态下拉框赋值
					var stateDom= _modal.find("[name='classification']");
					stateDom.val(subData.sysState);
					//修改保存按钮事件
					var saveBtn = _modal.find("[name='save']");
					saveBtn.off('click').on('click',function(){
						var updateDom = Page.findId('workUpdateFrom');
						var _cmd = updateDom.serialize();
						//获取分层层级
						var belongLevel = '';
						Page.find("[name='hierarchysec']:checked").each(function() {
							belongLevel += $(this).val()+',';
				        });		
						belongLevel=belongLevel.substring(0,belongLevel.length-1);
						_cmd += '&'+'belongLevel='+belongLevel;
						_cmd += '&ext3='+ Page.find("[name='groupUpdate']:checked").val();
						_cmd += '&ext1=3&description=修改';
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('workPlanSave'),_cmd,function(json, status){
							if(status) {
								_modal.modal('hide');
								XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
							} else {
								XMS.msgbox.show(json.retMessage, 'error', 2000);
							}					
						});
					});
				});		
			}
			//删除操作
			if(type == 'delete') {	
				subData.description = '删除';
				subData.ext1= '3';
				subData.sysId = subData.idThird;
				subData.idBelong = subData.idSecond;
				var _cmd = jQuery.param(subData);
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('workPlanSave'),_cmd,function(json, status){
					if(status) {
						XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
			}
		}
	};
	module.exports = init;
});
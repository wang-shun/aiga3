define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('thirdSysManage');
    //一级域下拉框查询
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "webservice/archiFirst/list");
    //根据一级查询二级子域
    srvMap.add("getSecondByFirst", pathAlias+"secondDomainList.json", "webservice/archiSecond/listByfirst");
    //二级子域查询
    srvMap.add("getSecondDomainList", pathAlias+"secondDomainList.json", "webservice/archiSecond/list");
	//显示系统信息表
	srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "webservice/archiThird/findTransPage");
	//三级系统操作信息保存
	srvMap.add("thirdSysMessageSave", pathAlias+"getSysMessageList.json", "webservice/archiGrading/thirdGradingAdd");
	//系统状态静态数据  
	srvMap.add("thirdSysState", pathAlias+"getSysMessageList.json", "webservice/static/archiBuildingState");
	//上传文件
    srvMap.add("uploadFile", pathAlias + "getDeliverablesList.json", "group/require/uploadFile");
	//等级信息
    srvMap.add("rankInfoStatic", pathAlias + "getDeliverablesList.json", "webservice/static/rankInfo");    
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
			self._jumpPage();
			self._render();
			
		},
		_render: function() {
			var self = this;
			self._queryTypeDomain();
			self._queryConditionDomain();
		},
		_jumpPage : function(){
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
		
		//上传附件，提交申请单
		uploadAnNiu: function(_modal) {	
    		var self = this;
    		var _cmd = "";
			var planId = new Date().getTime();
			var _form = Page.findModalCId('thirdApplyForm');
	        Utils.checkForm(Page.findId('thirdApplyForm'),function(){
	    		var a = '88888';
	            var cmd = {
	                "file": _form.find("[name='fileName']")[0].files[0],
	                "planId": planId,
	                "fileType": a
	            };
	            if(cmd.file) {
		            var task = srvMap.get('uploadFile');
                	self.uploadAjax(task, cmd, planId, _modal);
	            } else {
	            	//修改必须传文件
	            	window.XMS.msgbox.show('请上传系统总设文档', 'info', 2000);            	
//	            	//没有文件上传直接提交
//	            	self._apply_save_event(_modal);
	            }
	            
	        });
       	},
       	uploadAjax: function(task, cmd, planId, _modal) {
        	var self = this;
        	$.ajaxUpload({
        		url: task,
        		data: cmd,
        		success: function(date, status, xhr) {
        			console.log(date);
        			if (date.retCode==200) {
                    	self._apply_save_event(_modal, planId);
        			}else{
        				window.XMS.msgbox.show(date.retMessage, 'error', 2000);
                    }
                }
            });
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
			var _form = Page.findId("thirdApplyForm");
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
				XMS.msgbox.show('名称为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('sysId=&')>-1) {
				XMS.msgbox.show('系统编号为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('idBelong=&')>-1) {
				XMS.msgbox.show('所属二级域为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('sysState=&')>-1) {
				XMS.msgbox.show('建设状态为空！', 'error', 2000);
				return
			}
			if(!belongLevel) {
				XMS.msgbox.show('分层层级为空！', 'error', 2000);
				return
			}
			
			//调用服务
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('thirdSysMessageSave'),_cmd,function(json, status){
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
        _queryTypeDomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainTypeForm');
			Utils.setSelectDataPost(_form,true);
			var _queryBtn = _form.find("[name='query']");
			var _applyBtn = _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				if (cmd.indexOf('idFirst=&')>-1) {
					XMS.msgbox.show('请选择一级域', 'error', 1000);
					return
				}
				if (cmd.charAt(cmd.length - 1) == '=') {
					XMS.msgbox.show('请选择二级子域', 'error', 1000);
					return
				}				
				self._getGridList(cmd);
			});		
			_applyBtn.off('click').on('click',function() {
				//打开模态框
				var _modal = Page.findId('thirdApplyModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);	
				//保存按钮
				var saveBtn = _modal.find("[name='save']");
				saveBtn.off('click').confirm({
        			title:'提示',
        			content:'确认提交申请单',
        			confirmButtonClass:'btn-primary',
				    confirmButton: '确认',
				    cancelButton: '取消',
				    confirm: function(){
						self.uploadAnNiu(_modal);
				    },
				    cancel:function(){}
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
		//condition绑定查询按钮事件
		_queryConditionDomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainConditionForm');
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
				saveBtn.off('click').confirm({
        			title:'提示',
        			content:'确认提交申请单',
        			confirmButtonClass:'btn-primary',
				    confirmButton: '确认',
				    cancelButton: '取消',
				    confirm: function(){
						self.uploadAnNiu(_modal);
				    },
				    cancel:function(){
				    }
				});
			});
		},
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('thirdSysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				json.data.content.sort(function(a,b){
		            return a.idThird-b.idThird;});
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getThirdMessageList'));				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventTrClickCallback(_dom);
        		tablebtn.find("[class='btn btn-primary btn-table-update']").off('click').on('click', function() {
        			self._band_table_btn($(this).attr("data-source"),"update");
        		});
        		tablebtn.find("[class='btn btn-primary btn-table-delete']").off('click').confirm({
        			title:'提示',
        			content:'确认删除数据',
        			confirmButtonClass:'btn-primary',
				    confirmButton: '确认',
				    cancelButton: '取消',
				    confirm: function(){
				        self._band_table_btn(this.dataSource,"delete");
				    },
				    cancel:function(){
				    }
				});
//        		tablebtn.find("[class='btn btn-primary btn-table-delete']").off('click').on('click', function() {
//        			self._band_table_btn($(this),"delete");
//        		});
			},_domPagination);
		},
		
		_band_table_btn: function(sysId, type) {
			//取出选中数据
			var id = sysId;
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
				var template = Handlebars.compile(Page.findTpl('thirdUpdateFrom'));
				Page.findId('updateModal').html(template(subData));
				var _modal = Page.findId('thirdUpdateModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
				_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
					var hierarchy = Page.find("[name='hierarchysec']");
					Page.find("[name='groupUpdate'][value='"+subData.ext3+"']").attr("checked",true);
					var belongLevel = subData.belongLevel.split(',');
					for(var i=0;i<belongLevel.length;i++) {
						if(belongLevel[i] == 'SaaS') {
							hierarchy[0].checked = true;
						}
						if(belongLevel[i] == 'IPaaS') {
							hierarchy[1].checked = true;
						}
						if(belongLevel[i] == 'DaaS') {
							hierarchy[2].checked = true;
						}
						if(belongLevel[i] == 'UPaaS') {
							hierarchy[3].checked = true;
						}
						if(belongLevel[i] == 'BPaaS') {
							hierarchy[4].checked = true;
						}
						if(belongLevel[i] == 'IaaS') {
							hierarchy[5].checked = true;
						}
						if(belongLevel[i] == 'TPaaS') {
							hierarchy[6].checked = true;
						}
					}		
					//状态下拉框赋值
					var stateDom = _modal.find("[name='sysState']");
					stateDom.val(subData.sysState);
					var timeDom = _modal.find("[name='ext2']");
					timeDom.val(subData.ext2);
					var rankDom = _modal.find("[name='rankInfo']");
					rankDom.val(subData.rankInfo);
					//修改保存按钮事件
					var saveBtn = _modal.find("[name='save']");
					saveBtn.off('click').confirm({
	        			title:'提示',
	        			content:'确认提交申请单',
	        			confirmButtonClass:'btn-primary',
					    confirmButton: '确认',
					    cancelButton: '取消',
					    confirm: function(){
							var updateDom = Page.findId('thirdUpdateForm');
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
							Rose.ajax.postJson(srvMap.get('thirdSysMessageSave'),_cmd,function(json, status){
								if(status) {
									_modal.modal('hide');
									XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
								} else {
									XMS.msgbox.show(json.retMessage, 'error', 2000);
								}					
							});
					    },
					    cancel:function(){
					    }
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
				Rose.ajax.postJson(srvMap.get('thirdSysMessageSave'),_cmd,function(json, status) {
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
define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('secondSysManage');
    //一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
	//显示系统信息表
	srvMap.add("getCenMessageList", pathAlias+"getSysMessageList.json", "archi/second/listByfirstPage");
	//二级系统操作信息保存
	srvMap.add("secSysMessageSave", pathAlias+"getSysMessageList.json", "archi/grading/gradingAdd");
	var cache = {
		datas : "",
		firName : ""         //一级域名称
	};
	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		};
    	}
    };


	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
		
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('select_data');
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			var _applyBtn =  _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var cmd = "idFirst="+_form.find("[name='idFirst']").val();
				//用于解决long型不可空传的问题
				if(cmd.charAt(cmd.length - 1) == '=') {
					cmd+='0';
				}
				self._getSecGridList(cmd);
			});
			_applyBtn.off('click').on('click',function() {
				var _modal = Page.findId('secondApplyModal');
				//打开模态框
				_modal.modal('show');
				Utils.setSelectData(_modal);
				var saveBtn = _modal.find("[name='save']");
				saveBtn.off('click').on('click',function(){
					//获取表单数据
					var _form = Page.findId("secApplyForm");
					var _cmd = _form.serialize();	
					//获取分层层级
					var applyHierarchy = Page.find("[name='hierarchy']");	
					var belongLevel = '';
					if(applyHierarchy[0].checked == true) {
						belongLevel += 'SaaS' + ',';
					}
					if(applyHierarchy[1].checked == true) {
						belongLevel += 'IPaaS' + ',';
					}
					if(applyHierarchy[2].checked == true) {
						belongLevel += 'DaaS' + ',';
					}
					if(applyHierarchy[3].checked == true) {
						belongLevel += 'UPaaS' + ',';
					}
					if(applyHierarchy[4].checked == true) {
						belongLevel += 'BPaaS' + ',';
					}
					if(applyHierarchy[5].checked == true) {
						belongLevel += 'PaaS' + ',';
					}
					if(applyHierarchy[6].checked == true) {
						belongLevel += 'IaaS' + ',';
					}
					if(applyHierarchy[7].checked == true) {
						belongLevel += 'TPaaS' + ',';
					}	
					belongLevel=belongLevel.substring(0,belongLevel.length-1);
					_cmd += '&belongLevel='+belongLevel;
					_cmd += '&ext1=2&description=新增';
					//调用服务
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.postJson(srvMap.get('secSysMessageSave'),_cmd,function(json, status){
						if(status) {
							_modal.modal('hide');
							XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
						} else {
							XMS.msgbox.show(json.retMessage, 'error', 2000);
						}					
					});
				});
			});
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('getPrimaryDomainList'),'',function(json, status){
				if(status) {
					window.XMS.msgbox.hide();
					cache.firName = json.data;
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},

		// 查询表格数据
		_getSecGridList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				var _cmd = cmd;
			}
			Data.queryListCmd = _cmd;
			var _dom = Page.findId('secSysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getCenMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getSysMessageList'));
				
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
			var id = dom.attr("temp");
			var index = 0;
			while(id != cache.datas[index].idSecond) {
				index++;
			}
			var subData = cache.datas[index];
			subData.modifyDate = subData.modifyDate.replace(/-/g,"/");
			subData.createDate = subData.createDate.replace(/-/g,"/");
			if(type == 'update') {
				var m=0;	
				while(cache.firName[m].idFirst!=subData.idFirst) {
					m++;
				}
				subData.idFirstName = cache.firName[m].name;
				var template = Handlebars.compile(Page.findTpl('secondUpdateFrom'));
				Page.findId('updateModal').html(template(subData));
				var _modal = Page.findId('secondUpdateModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
				_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
					var hierarchy = Page.find("[name='hierarchysec']");	
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
						if(belongLevel[i] == 'PaaS') {
							hierarchy[5].checked = true;
						}
						if(belongLevel[i] == 'IaaS') {
							hierarchy[6].checked = true;
						}
						if(belongLevel[i] == 'TPaaS') {
							hierarchy[7].checked = true;
						}
					}					
					//修改保存按钮
					var saveBtn = _modal.find("[name='save']");
					saveBtn.off('click').on('click',function(){
						var updateDom = Page.findId('secUpdateForm');
						var _cmd = updateDom.serialize();
						//获取分层层级
						var updateHierarchy = Page.find("[name='hierarchysec']");	
						var belongLevel = '';
						if(updateHierarchy[0].checked == true) {
							belongLevel += 'SaaS' + ',';
						}
						if(updateHierarchy[1].checked == true) {
							belongLevel += 'IPaaS' + ',';
						}
						if(updateHierarchy[2].checked == true) {
							belongLevel += 'DaaS' + ',';
						}
						if(updateHierarchy[3].checked == true) {
							belongLevel += 'UPaaS' + ',';
						}
						if(updateHierarchy[4].checked == true) {
							belongLevel += 'BPaaS' + ',';
						}
						if(updateHierarchy[5].checked == true) {
							belongLevel += 'PaaS' + ',';
						}
						if(updateHierarchy[6].checked == true) {
							belongLevel += 'IaaS' + ',';
						}
						if(updateHierarchy[7].checked == true) {
							belongLevel += 'TPaaS' + ',';
						}	
						belongLevel=belongLevel.substring(0,belongLevel.length-1);
						_cmd += '&'+'belongLevel='+belongLevel;
						_cmd += '&ext1=2&description=修改';
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						Rose.ajax.postJson(srvMap.get('secSysMessageSave'),_cmd,function(json, status){
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
			if(type == 'delete') {
				subData.description = '删除';
				subData.ext1= '2';
				subData.sysId = subData.idSecond;
				subData.idBelong = subData.idFirst;
				subData.applyId = 0;
				var _cmd = jQuery.param(subData);
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('secSysMessageSave'),_cmd,function(json, status){
					if(status) {
						XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
			}
		},

		// 事件：双击绑定事件
		eventDClickCallback:function(obj,callback){
			obj.find("tr").bind('dblclick ', function(event) {
		        if (callback) {
					callback();
				}
		    });
		}
	};
	module.exports = init;
});
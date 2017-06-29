define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('primarySysManage');
    //一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
    //一级域查询带分页  
    srvMap.add("getPrimaryMessageList", pathAlias+"primaryDomainList.json", "archi/first/list");
//    srvMap.add("getPrimaryMessageList", pathAlias+"primaryDomainList.json", "archi/view/secView");
	//一级系统操作信息保存
	srvMap.add("firSysMessageSave", pathAlias+"getSysMessageList.json", "archi/grading/gradingAdd");
	var cache = {
		datas : ""	
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
			var _form = Page.findId('operatButton');
			var _queryBtn =  _form.find("[name='query']");
			var _applyBtn =  _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				self._getCaseTempList(cmd);
			});
			_applyBtn.off('click').on('click',function() {
				var _modal = Page.findId('primaryApplyModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
				
				var saveBtn = _modal.find("[name='save']");
				saveBtn.off('click').on('click',function(){
					//获取表单数据
					var _form = Page.findId("firApplyForm");
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
					_cmd += '&ext1=1&description=新增';
					//调用服务
					Utils.getServerPage(srvMap.get('firSysMessageSave'),_cmd,function(json){						
						_modal.modal('hide');
					});
				});
			});
		},

		// 查询表格数据
		_getCaseTempList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd) {
				var _cmd = cmd;
			}
			Data.queryListCmd = _cmd;
			var _dom = Page.findId('primarySysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getPrimaryMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getSysMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data));
        		cache.datas = json.data;
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
			var self = this;
			var id = dom.attr("temp");
			var index = 0;
			while(id != cache.datas[index].idFirst) {
				index++;
			}
			var subData = cache.datas[index];
			subData.modifyDate = subData.modifyDate.replace(/-/g,"/");
			subData.createDate = subData.createDate.replace(/-/g,"/");
			if(type == 'update') {
				var template = Handlebars.compile(Page.findTpl('primaryUpdateFrom'));
				Page.findId('updateModal').html(template(subData));
				var _modal = Page.findId('primaryUpdateModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
//				_dom.modal('hide');
				_modal.on('shown.bs.modal', function () {
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
						var updateDom = Page.findId('firUpdateForm');
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
						_cmd += '&ext1=1&description=修改';
						Utils.getServerPage(srvMap.get('firSysMessageSave'),_cmd,function(json){						
							_modal.modal('hide');
						});
					});
				});
			}
			if(type == 'delete') {
				subData.description = '删除';
				subData.ext1= '1';
				subData.sysId = subData.idFirst;
				var _cmd = jQuery.param(subData);
				Utils.getServerPage(srvMap.get('firSysMessageSave'),_cmd,function(json){				
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
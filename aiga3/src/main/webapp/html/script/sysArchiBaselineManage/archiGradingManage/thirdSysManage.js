define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('thirdSysManage');
    //一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
    //根据一级查询二级子域
    srvMap.add("getSecondByFirst", pathAlias+"secondDomainList.json", "archi/second/listByfirst");
    //二级子域查询
    srvMap.add("getSecondDomainList", pathAlias+"secondDomainList.json", "archi/second/list");
	//显示系统信息表
	srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findBySec");
	//三级系统操作信息保存
	srvMap.add("thirdSysMessageSave", pathAlias+"getSysMessageList.json", "archi/grading/gradingAdd");
	//三级系统信息修改
	srvMap.add("thirdSysMessageUpdate", pathAlias+"getSysMessageList.json", "archi/grading/gradingUpdate");
	//系统状态静态数据  
	srvMap.add("thirdSysState", pathAlias+"getSysMessageList.json", "archi/static/archiBuildingState");
	var cache = {	
		datas : "",     	//查询出的系统信息
		secName: ""			//二级子域名称
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
			var self = this;
			self._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
	
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			var _applyBtn = _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				self._getCaseTempList(cmd);
			});
			_applyBtn.off('click').on('click',function() {
				//打开模态框
				var _modal = Page.findId('thirdApplyModal');
				_modal.modal('show');
				Utils.setSelectData(_modal);
				var saveBtn = _modal.find("[name='save']");
				saveBtn.off('click').on('click',function(){
					//获取表单数据
					var _form = Page.findId("thirdApplyForm");
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
					_cmd += '&ext1=3&description=新增';
					//调用服务
					Utils.getServerPage(srvMap.get('thirdSysMessageSave'),_cmd,function(json){						
						_modal.modal('hide');
					});
				});
			});
			//查询二级域名称
			Utils.getServerPage(srvMap.get('getSecondDomainList'),'',function(json){	
				cache.secName = json.data;
			});
		},

		// 查询表格数据
		_getCaseTempList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				var _cmd = cmd;
			}
			Data.queryListCmd = _cmd;
			var _dom = Page.findId('thirdSysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysMessageList'),_cmd,function(json){
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
			//取出选中数据
			var id = dom.attr("temp");
			var index = 0;
			while(id != cache.datas[index].idThird) {
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
						var updateDom = Page.findId('thirdUpdateForm');
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
						_cmd += '&ext1=3&description=修改';
						Utils.getServerPage(srvMap.get('thirdSysMessageSave'),_cmd,function(json){						
							_modal.modal('hide');
						});
					});
				});		
//				_dom.modal('hide');
			}
			//删除操作
			if(type == 'delete') {	
				subData.description = '删除';
				subData.ext1= '3';
				subData.sysId = subData.idThird;
				subData.idBelong = subData.idSecond;
				var _cmd = jQuery.param(subData);
				Utils.getServerPage(srvMap.get('thirdSysMessageSave'),_cmd,function(json){				
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
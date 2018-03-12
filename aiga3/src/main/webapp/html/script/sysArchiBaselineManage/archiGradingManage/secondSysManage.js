define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var Tab = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('secondSysManage');
    //一级域下拉框查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "webservice/archiFirst/list");
	//显示系统信息表
	srvMap.add("getCenMessageList", pathAlias+"getSysMessageList.json", "webservice/archiSecond/listByfirstPage");
	//二级系统操作信息保存
	srvMap.add("secSysMessageSave", pathAlias+"getSysMessageList.json", "webservice/archiGrading/secGradingAdd");
	var cache = {
		datas : "",
		firName : ""         //一级域名称
	};

	var init = {
		init: function() {
			this._jumpPage();
			this._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
		},
		_jumpPage : function(){
			var syscmd = Page.getParentCmd();
			var result = Utils.jsonToUrl(syscmd);
			if(result!=null){
				var self = this;
				var _form = Page.findId('querySysDomainForm');
				Utils.setSelectData(_form);
				var _queryBtn = _form.find("[name='query']");
				_queryBtn.unbind('click').bind('click', function() {
					var cmd = result;
					if(cmd ==''){
						cmd = 'idFirst=0';
					}
					self._getSecGridList(cmd);
				});
				_queryBtn.click();
			}
		},
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('select_data');
			Utils.setSelectDataPost(_form,true);
			var _queryBtn = _form.find("[name='query']");
			var _applyBtn = _form.find("[name='apply']");
			_queryBtn.off('click').on('click',function(){
				var idFirst = _form.find("[name='idFirst']").val();
				var secName = _form.find("[name='name']").val();
				var cmd = '';
				if(idFirst) {
					cmd= "idFirst="+idFirst;
				} else {
					cmd= "idFirst=0";
				}
				cmd += '&name='+secName;
				self._getSecGridList(cmd);
			});
			_applyBtn.off('click').on('click',function() {
				var _modal = Page.findId('secondApplyModal');
				//打开模态框
				_modal.modal('show');
				Utils.setSelectData(_modal);
				var saveBtn = _modal.find("[name='save']");
				saveBtn.off('click').confirm({
        			title:'提示',
        			content:'确认提交申请单',
        			confirmButtonClass:'btn-primary',
				    confirmButton: '确认',
				    cancelButton: '取消',
				    confirm: function(){
							//获取表单数据
						var _form = Page.findId("secApplyForm");
						var _cmd = _form.serialize();	
						//获取分层层级	
						var belongLevel = '';
						Page.find("[name='hierarchy']:checked").each(function() {
							belongLevel += $(this).val()+',';
				        });	
						belongLevel=belongLevel.substring(0,belongLevel.length-1);
						_cmd += '&belongLevel='+belongLevel;
						_cmd += '&ext1=2&description=新增';
						//数据校验
					
						if(_cmd.indexOf('name=&')>-1) {
							XMS.msgbox.show('名称为空！', 'error', 2000);
							return
						}
						if(_cmd.indexOf('sysId=&')>-1) {
							XMS.msgbox.show('编号为空！', 'error', 2000);
							return
						}
						var _for = Page.findId("secApplyForm");
						var str = _for.find("[name='sysId']").val();
						var _str = $.trim(str);
						var patt1 =  /^\d{1,8}$/;
						if(_str.length !=8 || !patt1.test(_str) ){
							XMS.msgbox.show('请输入8位纯数字！', 'error', 2000);
							return
						}
						if(_cmd.indexOf('code=&')>-1) {
							XMS.msgbox.show('简称为空！', 'error', 2000);
							return
						}
						if(_cmd.indexOf('idBelong=&')>-1) {
							XMS.msgbox.show('所属一级域为空！', 'error', 2000);
							return
						}
						if(!belongLevel) {
							XMS.msgbox.show('分层层级为空！', 'error', 2000);
							return
						}
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
				    },
				    cancel:function(){}
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
			var _dom = Page.findId('secSysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getCenMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getSecMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventTrClickCallback(_dom);
        		self.eventDClickCallback(_dom, function(event){
        			//获得二级域id
        			var data = Utils.getRadioCheckedRow(_dom);
        			var objData = {
    						id : '114',
    						name : '三级系统管理',
    						href : "view/sysArchiBaselineManage/archiGradingManage/thirdSysManage.html",
    	                    cmd : "idFirst=" + data.idSecond.substring(0,1)+"0000000" + "&idSecond=" + data.idSecond
    				};
                	Tab.creatTab(objData);
        		});
        		
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
			},_domPagination);
		},
		
		_band_table_btn: function(sysId, type) {
			var self= this;
			var id = sysId;
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
						if(belongLevel[i] == 'IaaS') {
							hierarchy[5].checked = true;
						}
						if(belongLevel[i] == 'TPaaS') {
							hierarchy[6].checked = true;
						}
					}					
					//修改保存按钮
					var saveBtn = _modal.find("[name='save']");
					saveBtn.off('click').confirm({
	        			title:'提示',
	        			content:'确认提交申请单',
	        			confirmButtonClass:'btn-primary',
					    confirmButton: '确认',
					    cancelButton: '取消',
					    confirm: function(){
							self._update_submit();	
						},
					    cancel:function(){}
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
		
		_update_submit:function() {
			var _modal = Page.findId('secondUpdateModal');
			var updateDom = Page.findId('secUpdateForm');
			var _cmd = updateDom.serialize();
			//获取分层层级
			var belongLevel = '';
			Page.find("[name='hierarchysec']:checked").each(function() {
				belongLevel += $(this).val()+',';
	        });	
			belongLevel=belongLevel.substring(0,belongLevel.length-1);
			_cmd += '&'+'belongLevel='+belongLevel;
			_cmd += '&ext1=2&description=修改';
			
			//数据校验
			if(_cmd.indexOf('name=&')>-1) {
				XMS.msgbox.show('名称为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('sysId=&')>-1) {
				XMS.msgbox.show('编号为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('code=&')>-1) {
				XMS.msgbox.show('简称为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('idBelong=&')>-1) {
				XMS.msgbox.show('所属一级域为空！', 'error', 2000);
				return
			}
			if(!belongLevel) {
				XMS.msgbox.show('分层层级为空！', 'error', 2000);
				return
			}
			//调服务
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('secSysMessageSave'),_cmd,function(json, status){
				if(status) {
					_modal.modal('hide');
					XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},
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
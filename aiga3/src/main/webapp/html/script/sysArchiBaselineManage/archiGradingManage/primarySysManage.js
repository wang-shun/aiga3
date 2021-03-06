define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('primarySysManage');
    //一级域查询带分页  
    srvMap.add("getPrimaryMessageList", pathAlias+"primaryDomainList.json", "webservice/archiFirst/listPage");
	//一级系统操作信息保存
	srvMap.add("firSysMessageSave", pathAlias+"getSysMessageList.json", "webservice/archiGrading/firstGradingAdd");
	var cache = {
		datas : ""	
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
				self._getGridList(cmd);
			});
			_queryBtn.click();
			_applyBtn.off('click').on('click',function() {
				var _modal = Page.findId('primaryApplyModal');
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
						self._apply_submit();
				    },
				    cancel:function(){}
				});
			});
		},
		
		_apply_submit: function() {
			//获取表单数据
			var _form = Page.findId("firApplyForm");
			var _cmd = _form.serialize();	
			//设置cmd中默认字段的值
			_cmd = _cmd.replace('sysId=&', 'sysId=0&');
			_cmd += '&ext1=1&description=新增';
			//数据校验
			if(_cmd.indexOf('name=&')>-1) {
				XMS.msgbox.show('名称为空！', 'error', 2000);
				return
			}
			if(_cmd.indexOf('sysId=0&')>-1) {
				XMS.msgbox.show('编号为空！', 'error', 2000);
				return
			}
			var _for = Page.findId("firApplyForm");
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
			//调用服务
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('firSysMessageSave'),_cmd,function(json, status){
				if(status) {
					_modal.modal('hide');
					XMS.msgbox.show('申请成功，请等待认定！', 'success', 2000);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd) {
				var _cmd = cmd;
			}
			var _dom = Page.findId('primarySysMessageList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getPrimaryMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getfirMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		//复选框美化
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
			},_domPagination);
		},
		
		_band_table_btn: function(sysId, type) {
			var self = this;
			var id = sysId;
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
				_modal.on('shown.bs.modal', function () {
					//修改保存按钮
					var saveBtn = _modal.find("[name='save']");
					saveBtn.off('click').confirm({
	        			title:'提示',
	        			content:'确认提交申请单',
	        			confirmButtonClass:'btn-primary',
					    confirmButton: '确认',
					    cancelButton: '取消',
					    confirm: function(){
							var updateDom = Page.findId('firUpdateForm');
							var _cmd = updateDom.serialize();
							_cmd = _cmd.replace('sysId=&', 'sysId=0&');
							_cmd += '&ext1=1&description=修改';
							//数据校验
							if(_cmd.indexOf('name=&')>-1) {
								XMS.msgbox.show('名称为空！', 'error', 2000);
								return
							}
							if(_cmd.indexOf('code=&')>-1) {
								XMS.msgbox.show('简称为空！', 'error', 2000);
								return
							}
							//调服务
							XMS.msgbox.show('数据加载中，请稍候...', 'loading');
							Rose.ajax.postJson(srvMap.get('firSysMessageSave'),_cmd,function(json, status){
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
			}
			if(type == 'delete') {
				subData.description = '删除';
				subData.ext1= '1';
				subData.sysId = subData.idFirst;
				var _cmd = jQuery.param(subData);
				//调服务
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('firSysMessageSave'),_cmd,function(json, status){
					if(status) {
						XMS.msgbox.hide();
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
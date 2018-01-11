define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "ArchStaticData/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('staticData');


	//显示查询信息表
	srvMap.add("getStaticDataList", pathAlias+"getList.json", "webservice/archiStaticData/queryStaticData");
	//申请页面保存
	srvMap.add("staticDataSave", pathAlias+"dataSave.json", "webservice/archiStaticData/save");
	//更新页面
	srvMap.add("staticDataUpdate", pathAlias+"dataUpdate.json", "webservice/archiStaticData/update");
	//删除页面
	srvMap.add("staticDataDelete", pathAlias+"dataDelete.json", "webservice/archiStaticData/delete");
	
	
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
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('staticDataList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');					

			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getStaticDataList'),_cmd,function(json){
				window.XMS.msgbox.hide();				
				var template = Handlebars.compile(Page.findTpl('staticDataTemp'));				
        		var tablebtn = _dom.find("[name='content']");
   
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

					self.updateDataMain(data.dataId, json.data);
				});
			},_domPagination);
		},
       
        //添加页面
        _applydomain: function() {
			var self = this;
				var _modal = Page.findId('staticDataModal');
				Utils.setSelectData(_modal);

				var saveBtn = _modal.find("[name='save']");

				saveBtn.off('click').on('click',function(){
					//获取表单数据
					var _form = Page.findId("staticDataForm");
					var _cmd = _form.serialize();	
				
					//数据校验
					var codeType = _form.find("[name='codeType']").val();					
					if(codeType == 0) {
						XMS.msgbox.show('CODE_TYPE为空！', 'error', 2000);
						return
					}
					var codeValue = _form.find("[name='codeValue']").val();					
					if(codeValue == 0) {
						XMS.msgbox.show('CODE_VALUE为空！', 'error', 2000);
						return
					}
					var codeName = _form.find("[name='codeName']").val();					
					if(codeName == 0) {
						XMS.msgbox.show('CODE_NAME为空！', 'error', 2000);
						return
					}
					//调用服务
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					
					Rose.ajax.postJson(srvMap.get('staticDataSave'),_cmd,function(json, status){
						if(status) {
							var _form = Page.findId("staticDataForm");
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
				if(isNaN(_form.find("[name='dataId']").val())) {
					XMS.msgbox.show('DATA_ID类型必须为数字！', 'error', 1000);
					return 
				}
				if(cmd.indexOf('dataId=&')>-1){
					cmd = cmd.replace(/dataId=&/,'dataId=0&');
				}
				if(cmd.indexOf('+')>-1){
					cmd = cmd.replace(/\+/g,'');
				}
				self._getGridList(cmd);
			});		
        },
      
        //更新数据
		updateDataMain: function(dataId, json) {
			var self = this;
			var i=0;			
			while(json.content[i].dataId != dataId){
				i++;
			}
			var data = json.content[i];
						
			var template = Handlebars.compile(Page.findTpl('staticDataFrom'));
			Page.findId('updateDataStaticData').html(template(data));
			var _dom = Page.findModal('dataUpdateModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);

			
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Page.findId('updateDataStaticData');
				var _cmd = _form.serialize();	
			
				//数据校验
				var codeType = _form.find("[name='codeType']").val();					
				if(codeType == 0) {
					XMS.msgbox.show('CODE_TYPE为空！', 'error', 2000);
					return
				}
				var codeValue = _form.find("[name='codeValue']").val();					
				if(codeValue == 0) {
					XMS.msgbox.show('CODE_VALUE为空！', 'error', 2000);
					return
				}
				var codeName = _form.find("[name='codeName']").val();					
				if(codeName == 0) {
					XMS.msgbox.show('CODE_NAME为空！', 'error', 2000);
					return
				}
				
				_cmd = _cmd + "&dataId=" + dataId;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get('staticDataUpdate'), _cmd, function(json, status) {
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
			var _dom = Page.findId('staticDataList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					var cmd = 'dataId=' + data.dataId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('staticDataDelete'), cmd, function(json, status) {
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
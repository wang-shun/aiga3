define(function(require, exports, module) {
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/"; 
	// Page
	var Page = Utils.initPage('personalBaseApply');	
	
	/*后台接口 start*/
	//获取报表时间类型
	//显示认定信息表
	srvMap.add("getSysGradingMessageList", pathAlias+"getSysMessageList.json", "archi/grading/findByConditionPage");
    //数据翻译
	srvMap.add("MessageTranslate", pathAlias+"getSysMessageList.json", "archi/grading/gradingTranslate");
	//系统状态静态数据  
	srvMap.add("thirdSysState", pathAlias+"getSysMessageList.json", "archi/static/archiBuildingState");
	//等级信息
    srvMap.add("rankInfoStatic", pathAlias + "getDeliverablesList.json", "archi/static/rankInfo");
    //重提申请单
    srvMap.add("archiGradingReSubmit", pathAlias + "getDeliverablesList.json", "archi/grading/reSubmit");
    //撤销申请单
    srvMap.add("archiGradingApplyCancel", pathAlias + "getDeliverablesList.json", "archi/grading/applyCancel");
	/*后台接口 end*/
    
    //模板
    var Tpl = {
    };
    //节点
    Dom = {
    	group: ''
    };
    
    Data = {
    	code: '', //用户账号
    	allDatas: '',//当前页所有数据
    	selectData : '' //当前双击选中数据
    };
	//向外暴露的模块
	var init = {
		init: function() {
			//参数初始化
			this._param_init();
			//绑定按钮事件
			this._btn_event_init();		
		},	
		
		_param_init: function() {
			//初始化节点
			Dom.group = Page.findId("selectGroup");
			//初始化时间框
			function showMonthFirstDay() {     
				var date=new Date();
			 	date.setDate(1);
			 	return Rose.date.dateTime2str(date,"yyyy-MM-dd");   
			} 
			Dom.group.find('[name="begainTime"]').val(showMonthFirstDay());
			Dom.group.find('[name="endTime"]').val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
			//页面初始化时查询
			var _data = Page.getParentCmd();
			if(_data) {
				Data.code = _data.applyUser;
				this._getSysMessageList(Utils.jsonToUrl(_data));
			}
		},
		
		//绑定按钮事件
		_btn_event_init: function() {
			var self = this;
			//查询按钮事件绑定
			Dom.group.find("[name='query']").off('click').on('click',function() {
				var cmd = Dom.group.serialize();
				//默认参数
				var defaultCMD = 'state=审批未通过&applyUser='+Data.code+'&';
				self._getSysMessageList(defaultCMD+cmd);
			});
			//重置
			Dom.group.find("[name='reset']").off('click').on('click',function() {
				Dom.group.find('[name="name"]').val('');
				Dom.group.find('[name="sysId"]').val('');
			});
			//model按钮事件
			this._model_btn_event();
		},

		// 查询表格数据
		_getSysMessageList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd){
				_cmd = cmd;
			}
			var _dom = Page.findId('applyBillList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysGradingMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				Data.allDatas = json.data.content;
				var template = Handlebars.compile(Page.findTpl('getGrandingMessageList'));				
        		_dom.find("[name='content']").html(template(json.data.content));
        		//表格双击时间绑定
        		Utils.eventDClickCallback(_dom,function(isChecked,_input) {
        			var applyId = _input[0].value;
        			var allDatas = Data.allDatas;
        			if(allDatas) {
        				//获取当前选中数据
        				var index = 0;
        				while(allDatas[index].applyId != applyId) {
        					index++;
        				}
        				Data.selectData = $.extend(true,{},allDatas[index]);
        				self._grid_double_click();
        			} else {
        				XMS.msgbox.show('未获取到数据', 'error', 2000);
        			}
        		});
			},_domPagination);
		},
		//表格双击事件
		_grid_double_click: function() {
			var selectData = Data.selectData;
			var templateFrom = Handlebars.compile(Page.findTpl('thirdMessageFrom'));
			var _selectDataModal = Page.findId('modelSelectData');
			selectData.isChange = false;
			if(selectData.description != '申请') {
				selectData.isChange = true;
			}
			_selectDataModal.html(templateFrom(selectData));
			var _modal = Page.findId('sysMessageFrom');
			_modal.modal('show');
        	//模态框加载后事件
			_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
				//获取系统建设状态和所属域的名称
				var _cmdTrans = 'idBelong='+selectData.idBelong+'&ext1='+selectData.ext1+'&sysState='+selectData.sysState+'&idThird='+selectData.sysId;
				Rose.ajax.postJsonSync(srvMap.get('MessageTranslate'),_cmdTrans,function(messageTranslateJson, status) {
					//认定页允许所属域修改
					if(messageTranslateJson.data) {
  						var idBelongDom = _selectDataModal.find('[name="idBelong"]');
	                    var secData = messageTranslateJson.data.secData;
	                    var _html = '';
	                    for (var i in secData) {
	                        var _json = secData[i];
	                        _html += '<option value="' + _json.idSecond + '">' + _json.name + '</option>';
	                    }
	                    idBelongDom.html(_html);
	                    idBelongDom.val(selectData.idBelong);
					} 
					//系统建设状态
					Utils.setSelectData(_selectDataModal,'',function() {
						_selectDataModal.find('[name="sysState"]').val(selectData.sysState);
						_selectDataModal.find('[name="rankInfo"]').val(selectData.rankInfo);
					});
					
					//附件下载事件绑定	        				
 					var downloadButton = _modal.find('[name="download"]');
					downloadButton.off('click').on('click',function() {
						if(selectData.fileId) {
    						var downloadParam = 'ids=' + selectData.fileIndex;
    	                    var downloadurl = srvMap.get('downloadFile')+"?"+downloadParam;
    	                    downloadButton.attr("href", downloadurl.toString());
						} else {
							XMS.msgbox.show('没有可下载的附件！', 'error', 1000);
						}
					});      
				});
			});
		},
		//申请单操作页面按钮
		_model_btn_event: function() {
			var _dom = Page.findId('sysMessageFrom');
			_dom.find("[name='reSubmit']").off('click').on('click',function() {
				var _from = Page.findId("modelSelectData");
				var applyData = _from.serializeJSON();
				var data = Data.selectData;
				var sumitparam = $.extend(true,{},data,applyData);
				//删除冗余属性，避免提交
				delete sumitparam.isChange;				
				//编号校验 不允许异常数据认定通过
				var _sysValue = $.trim(sumitparam.sysId);
				var condition =  /^\d{1,8}$/;
				if(!_sysValue) {
                    XMS.msgbox.show('编号为空！', 'error', 1000);
					return;
				} 
	            if(_sysValue.length !=8 || !condition.test(_sysValue) ){          
	                XMS.msgbox.show('请输入8位纯数字编号！', 'error', 2000);
        			return;
				} 
				//调用接口
				Rose.ajax.postJson(srvMap.get('archiGradingReSubmit'), sumitparam, function(json, status){
					if(status) {
						_dom.modal('hide');
						XMS.msgbox.show('申请单重新提交成功！', 'success', 1000);
						setTimeout(function() {
							Dom.group.find("[name='query']").click();
						}, 1000);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
				});	               
			});
			//撤销申请单
			_dom.find("[name='cancel']").off('click').on('click',function() {
				var cancelParam = {
					applyId : Data.selectData.applyId
				};
	            //调接口
				Rose.ajax.postJson(srvMap.get('archiGradingApplyCancel'), cancelParam, function(json, status){
					if(status) {
						_dom.modal('hide');
						XMS.msgbox.show('撤销成功！', 'success', 1000);
						setTimeout(function() {
							Dom.group.find("[name='query']").click();
						}, 1000);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
				});	 
			});
		}
	};
	module.exports = init;
});
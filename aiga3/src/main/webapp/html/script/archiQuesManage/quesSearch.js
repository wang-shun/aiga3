define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesSearchView');
	//问题展示
	srvMap.add("getQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/question/list");
	//新增问题
	srvMap.add("saveQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/save");
	//修改问题
	srvMap.add("updateQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/update")
	//刪除問題
	srvMap.add("deleQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/delete");
    //问题分类下拉框
    srvMap.add("getQuestypeList", "", "sys/cache/listQuestype");
    //一级分类下拉框
    srvMap.add("getFirstcategoryList", "", "sys/cache/listFirstcategory");
    //二级分类下拉框
    srvMap.add("getSecondcategoryList", "", "sys/cache/listSecondcategory");
    //三级分类下拉框
    srvMap.add("getThirdcategoryList", "", "sys/cache/listThirdcategory");
    //级联查询
    srvMap.add("getQueryQuesInfo", "", "archi/question/queryInfo");
    //显示系统信息表
    srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
    //所属工单状态静态数据  
	srvMap.add("staticProductState", pathAlias+"getSysMessageList.json", "archi/static/archiProductState");
    //所属问题状态静态数据  
	srvMap.add("staticQuestionState", pathAlias+"getSysMessageList.json", "archi/static/archiQuestionState");

	// 模板对象
	var Tpl = {
		//getDataMaintainTemp: $('#JS_getDataMaintainTemp'),
		getQuestionInfoList: require('tpl/archiQuesManage/quesSearchTemplate.tpl')

	};

	/*// 容器对象
	var Dom = {
		queryDataMaintainForm: '#JS_queryDataMaintainForm',
		getDataMaintainList: '#JS_getDataMaintainList',
		addDataMaintainModal: "#JS_addDataMaintainModal",
		addDataMaintainInfo: "#JS_addDataMaintainInfo",
		updateDataMaintainModal: "#JS_updateDataMaintainModal",
		updateMaintainInfo: "#JS_updateDataMaintainInfo",
	};*/

	var Data = {
		queryListCmd: null
	};
	
	var cache = {
		datas : '',
		selectData : ''	
	};
	
	var Query = {
		init: function() {
			this.jumpPage();
			
			this.searchBox();
			
			this._querydomain();
			// 默认查询所有
//			this.getDataMaintainList();
			// 初始化查询表单
			this.queryDataMaintainForm();
			//映射
			this.hdbarHelp();
		},
		jumpPage : function(){
			var syscmd = Page.getParentCmd();
			var result = Utils.jsonToUrl(syscmd);
			if(result!=null){
				var self = this;
				var _form = Page.findId('queryDataMaintainForm');
				Utils.setSelectData(_form);
				var _queryBtn = _form.find("[name='query']");
				_queryBtn.unbind('click').bind('click', function() {
					var cmd = result;
					self.getDataMaintainList(cmd);
				});
				_queryBtn.click();
			}
		},
		searchBox: function(){
			var self = this;
			var _dom = Page.findId('queryDataMaintainForm');
			var _checkBt = _dom.find("[name='checkbox']");

			_checkBt.unbind('click');
			_checkBt.bind('click', function() {
//				var template = Handlebars.compile(Page.findTpl('getSearchBoxTemp'));
//				Page.findId('insertBelongProj').html(template(data));
				var _modal = Page.findModal('addSearchBoxModal');
				_modal.modal('show');
//				Utils.setSelectData(_modal);
				var sureBt = _modal.find("[name='sure']");
				sureBt.unbind('click');
				sureBt.bind('click',function(){
					_modal.modal('hide');
//					var node = _modal.find("[name='sysMessageQuery']");
					var node = Page.findId('sysMessageQuery');
					var value = Utils.getRadioCheckedRow(node);
					var idThird = value.idThird;
					for(var i=0;i<cache.datas.length;i++){
						if(cache.datas[i].idThird==idThird){
							var nameValue = cache.datas[i].name;
							_dom.find("[name='belongProject']").val(nameValue);
							break;
						}
					};
				});
			});
		},
		/*--------------------------------------------------*/
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('querySysDomainForm');
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();
				//用于解决long型不可空传的问题
				if(cmd.charAt(cmd.length - 1) == '=') {
					cmd+='0';
				}
				self._getTableDataList(cmd);
			});
		},

		// 查询表格数据
		_getTableDataList: function(cmd){
			var self = this;
			var _cmd = '' ;
			if(cmd) {
				var _cmd = cmd;
			}
			var _dom = Page.findId('sysMessageQuery');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getSysMessageList'),_cmd,function(json){
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Page.findTpl('getSysMessageList'));
				
        		var tablebtn = _dom.find("[name='content']");
        		tablebtn.html(template(json.data.content));
        		cache.datas = json.data.content;
        		Utils.eventTrClickCallback(_dom);
			},_domPagination);
		},
		/*--------------------------------------------------*/
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.unbind('click').bind('click', function() {
				var cmd = _form.serialize();
				self.getDataMaintainList(cmd);
			});

		},
		// 查询数据维护
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			if(_cmd!=null){
				if(_cmd.indexOf('quesId=&')>-1){
					_cmd=_cmd.replace("quesId=&","quesId=0&");
				}
			}
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');

			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getQueryQuesInfo'), _cmd, function(json, status) {
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getQuestionInfoList);
				_dom.find("[name='content']").html(template(json.data.content));
				//美化单机
				Utils.eventTrClickCallback(_dom);
				//新增
				self.addDataMaintain();
				//删除
				self.delDataMaintain();
				//双击修改
				self.eventDClickCallback(_dom, function() {
					//获得当前单选框值
					var data = Utils.getRadioCheckedRow(_dom);
					//alert(data.correlationId);
					self.updateDataMaintain(data.quesId, json.data);
				});
			}, _domPagination);
		},
		//新增数据维护
		addDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _addBt = _dom.find("[name='add']");

			_addBt.unbind('click');
			_addBt.bind('click', function() {
				//alert(Page.findModal('addDataMaintainModal').html());
				Page.findModal('addDataMaintainModal').modal('show');
				Page.findModal('addDataMaintainModal').on('hide.bs.modal', function() {
					Utils.resetForm(Page.findId('addDataMaintainInfo'));
				});
				var _form = Page.findId('addDataMaintainInfo');
				Utils.setSelectData(_form);
				var _saveBt = Page.findModal('addDataMaintainModal').find("[name = 'save']");
				_saveBt.unbind('click');
				_saveBt.bind('click', function() {
					Utils.checkForm(_form, function() {
						var _cmd = _form.serialize();
						XMS.msgbox.show('数据加载中，请稍候...', 'loading');
						console.log(_cmd);
						Rose.ajax.postJson(srvMap.get('saveQuestionInfo'), _cmd, function(json, status) {
							if (status) {
								// 数据备份成功后，刷新用户列表页
								XMS.msgbox.show('添加成功！', 'success', 2000)
								setTimeout(function() {
									self.getDataMaintainList();
								}, 1000);
								// 关闭弹出层
								Page.findModal('addDataMaintainModal').modal('hide');
							}
						});
					});
				});

			});

		},
		//删除数据备份
		delDataMaintain: function() {
			var self = this;
			var _dom = Page.findId('getDataMaintainList');
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if (data) {
					console.log(data);
					var cmd = 'quesId=' + data.quesId;
					//alert(cmd);
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('deleQuestionInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function() {
								self.queryDataMaintainForm(Data.queryListCmd);
							}, 1000)
						}
					});
				}
			});
		},
		updateDataMaintain: function(Id,json) {
			var self = this;
			var i=0;
			while(json.content[i].quesId != Id){
				i++;
			}
			var data = json.content[i];
			data.modifyDate = data.modifyDate.replace(/-/g,"/");
			data.createDate = data.modifyDate;
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
		},
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tbody tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		},
		//映射处理
		hdbarHelp: function() {
		
			Handlebars.registerHelper("transformatRoot", function(value) {
                if (value == '1') {
                    return "技术巡检";
                } else if (value == '2') {
                    return "系统巡检";
                } else if (value == '3') {
                    return "疑难问题";
                }
            });
			Handlebars.registerHelper("transformatFirst", function(value) {
                if (value == '1001') {
                    return "容量规划";
                } else if (value == '1002') {
                    return "高可用";
                } else if (value == '1003') {
                    return "分层";
                } else if (value == '1004') {
                	return "柔性可用";
                } else if (value == '1005') {
                    return "日志";
                } else if (value == '1006') {
                    return "配置";
                } else if (value == '1007') {
                    return "监控";
                } else if (value == '1008') {
                    return "安全";
                }
            });
			Handlebars.registerHelper("transformatSecond", function(value) {
                if (value == '2001') {
                    return "系统容量";
                } else if (value == '2002') {
                    return "业务容量";
                } else if (value == '2003') {
                    return "系统性能";
                } else if (value == '2004') {
                    return "系统级别";
                } else if (value == '2005') {
                    return "容灾等级";
                } else if (value == '2006') {
                    return "演练结果";
                } else if (value == '2007') {
                    return "核心进程主备";
                } else if (value == '2008') {
                    return "IaaS";
                } else if (value == '2009') {
                    return "PaaS";
                } else if (value == '2010') {
                    return "SaaS";
                } else if (value == '2011') {
                    return "交互应用";
                } else if (value == '2012') {
                    return "数据访问";
                } else if (value == '2013') {
                    return "业务设计";
                } else if (value == '2014') {
                    return "服务设计";
                } else if (value == '2015') {
                    return "其他柔性手段";
                } else if (value == '2016') {
                    return "完整性";
                } else if (value == '2017') {
                    return "便捷性";
                } else if (value == '2018') {
                    return "合规性";
                } else if (value == '2019') {
                    return "集中配置";
                } else if (value == '2020') {
                    return "配置刷新";
                } else if (value == '2021') {
                    return "稽核手段";
                } else if (value == '2022') {
                    return "采集方式";
                } else if (value == '2023') {
                    return "端到端监控";
                } else if (value == '2024') {
                    return "数据一致性稽核";
                } else if (value == '2025') {
                    return "单笔业务跟踪";
                } else if (value == '2026') {
                    return "账号密码";
                } else if (value == '2027') {
                    return "4A系统对接";
                } else if (value == '2028') {
                    return "权限管控粒度";
                }
            });
			Handlebars.registerHelper("transformatThird", function(value) {
                if (value == '3001') {
                    return "数据库容量";
                } else if (value == '3002') {
                    return "网络带宽";
                } else if (value == '3003') {
                    return "TPCC";
                } else if (value == '3004') {
                    return "存储";
                } else if (value == '3005') {
                    return "工单并发量";
                } else if (value == '3006') {
                    return "移动号码新入网业务";
                } else if (value == '3007') {
                    return "合约业务";
                } else if (value == '3008') {
                    return "移动宽带新入网业务";
                } else if (value == '3009') {
                    return "亲情网和流量包办理";
                } else if (value == '3010') {
                    return "用户量";
                } else if (value == '3011') {
                    return "主机运营指标";
                } else if (value == '3012') {
                    return "业务并发量";
                } else if (value == '3013') {
                    return "CPU";
                } else if (value == '3014') {
                    return "内存";
                } else if (value == '3015') {
                    return "系统级别";
                } else if (value == '3016') {
                    return "容灾等级";
                } else if (value == '3017') {
                    return "演练结果";
                } else if (value == '3018') {
                    return "核心进程主备";
                } else if (value == '3019') {
                    return "硬件标准化";
                } else if (value == '3020') {
                    return "软件定义化";
                } else if (value == '3021') {
                    return "组件标准化";
                } else if (value == '3022') {
                    return "DCOS化";
                } else if (value == '3023') {
                    return "服务统一接入管控";
                }
            });
            
		
		},
		// 事件：分页
		initPaging: function(obj, length) {
			obj.find("table").DataTable({
				"iDisplayLength": length,
				"paging": true,
				"lengthChange": false,
				"searching": false,
				"ordering": false,
				"info": true,
				"autoWidth": false
			});
		}
	};
	module.exports = Query;
});
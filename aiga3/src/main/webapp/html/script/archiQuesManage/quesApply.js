define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('quesShenqingView');
	//分页根据条件查询功能点归属
	srvMap.add("getDataMaintainList", pathAlias + "dataMaintain.json", "sys/property/getPropertyCorrelationList");
	//新增备份
	srvMap.add("addDataMaintain", pathAlias + "retMessage.json", "sys/property/addPropertyCorrelation");
	//删除备份
	srvMap.add("delDataMaintain", pathAlias + "retMessage.json", "sys/property/delPropertyCorrelation");
	//修改备份
	srvMap.add("updateDataMaintain", pathAlias + "retMessage.json", "sys/property/updatePropertyCorrelation");
	//属性下拉菜单
	srvMap.add("getPropertyName", pathAlias + "retMessage.json", "sys/backup/getPropertyConfigList");
	//数据库下拉菜单
	srvMap.add("getDbList", pathAlias + "retMessage.json", "sys/property/getDbList");
	//cfgId下拉菜单

	srvMap.add("getCfgIdList", pathAlias + "retMessage.json", "sys/property/getCigIdList");

	srvMap.add("getPropertyConfigList", pathAlias + "propertyConfig.json", "sys/property/getPropertyFieldList");

	//问题展示
	srvMap.add("getQuestionInfoList", "archiQuesManage/questionInfoList.json", "archi/question/list");
	//新增问题
	srvMap.add("saveQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/save");
	//修改问题
	srvMap.add("updateQuestionInfo", "archiQuesManage/questionInfoList.json", "archi/question/update");
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
    //所属系统静态数据  
	srvMap.add("getBelongSystem", "", "archi/third/list");
    //所属处理科室静态数据  
	srvMap.add("staticDealApartment", pathAlias+"getSysMessageList.json", "archi/static/archiDealApartment");
    //所属工单状态静态数据  
	srvMap.add("staticProductState", pathAlias+"getSysMessageList.json", "archi/static/archiProductState");
	//上传文件
    srvMap.add("uploadFile", pathAlias + "getDeliverablesList.json", "group/require/uploadFile");
	//一级域查询  
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");
	//显示系统信息表
//	srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findByConditionPage");
    srvMap.add("getSysMessageList", pathAlias+"getSysMessageList.json", "archi/third/findTransPage");
	var cache = {
		datas : ""	
	};
    // 模板对象
	var Tpl = {
		//getDataMaintainTemp: $('#JS_getDataMaintainTemp'),
		getQuestionInfoList: require('tpl/archiQuesManage/quesRequest.tpl')
		//modifyQuesIdentifiedInfo: $("#TPL_modifyQuesIdentifiedInfo").html()

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

	var Query = {
		init: function() {
			this.initialise();
			
			var planId = '99999';
			
			this.searchBox();
			
			this.uploadAnNiu(planId);
			
			this.judgeQuesType();
			// 默认查询所有
			this.getDataMaintainList();
			// 初始化查询表单
			this.queryDataMaintainForm();
			
			this._querydomain();
			//映射
			this.hdbarHelp();
		},
		initialise: function(){
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
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
//					var value = $("#sbbelongProject").find("option:selected").text();
//					var value = node.text();
					
				});
			});
		},
		        //上传上线交付物按钮
        uploadAnNiu: function() {
        	var planId = "8888";
            var self = this;
            var _form = Page.findModalCId('queryDataMaintainForm');
            console.log(_form.length);
	        Utils.checkForm(Page.findId('queryDataMaintainForm'),function(){
	    		var a = '99999';
	            var cmd = {
	                "file": _form.find("[name='fileName']")[0].files[0],
	                "planId": planId,
	                "fileType": a
	            };
	            console.log(_form.find("[name='fileName']"));
	            console.log(a);
	                var task = srvMap.get('uploadFile');
	                self.jieko(task, cmd, planId);
	        });
        },
        jieko: function(task, cmd, planId) {
            var self = this;
            $.ajaxUpload({
                url: task,
                data: cmd,
                success: function(date, status, xhr) {
                    console.log(date);
                    if (date.retCode==200) {
                        //window.XMS.msgbox.show('上传成功！', 'success', 2000);
   						return true;
                    }else{
                        window.XMS.msgbox.show(date.retMessage, 'error', 2000);
                        return false;
                    }
                }
            });
        },
		//判断下拉框quesTypez值
		judgeQuesType: function(){
			$("#quesType").unbind('click');
			$("#quesType").bind('click',function(){
				var checkValue=$("#quesType").val();  //获取Select选择的Value 
				if(checkValue=="2" || checkValue=="3"){
					$("#firstCategorydiv").hide();     
					$("#secondCategorydiv").hide();          
					$("#thirdCategorydiv").hide();           
				}else if(checkValue=="1"){
					$("#firstCategorydiv").show();   
					$("#secondCategorydiv").show();          
					$("#thirdCategorydiv").show();   
				}
			});
		},
		// 按条件查询
		queryDataMaintainForm: function() {
			var self = this;
			var _form = Page.findId('queryDataMaintainForm');
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.unbind('click').bind('click', function() {
				var planId = "8888";
				var upState = false;
		        Utils.checkForm(Page.findId('queryDataMaintainForm'),function(){
		    		var a = '99999';

		            var cmd = {
		                "file": _form.find("[name='fileName']")[0].files[0],
		                "planId": planId,
		                "fileType": a
		            };
		            
		            if(cmd.file) {
		    			upState = true;
		    			var task = srvMap.get('uploadFile');
	                	if(!self.jieko(task, cmd, planId)){
	                		return;
	                	}  
		    		}        
		        });
//				self.getDataMaintainList(cmd);
				Utils.checkForm(_form, function() {
					var _cmd = _form.serialize();
					_cmd=_cmd.replace(/-/g,"/");
					//XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					console.log(_cmd);
					Rose.ajax.postJson(srvMap.get('saveQuestionInfo'), _cmd, function(json, status) {
						if (status) {
							// 数据备份成功后，刷新用户列表页
							if(upState) {
								XMS.msgbox.show('文件上传完毕，申报成功！', 'success', 2000);
							} else {
								XMS.msgbox.show('申报成功！', 'success', 2000);
							}
//							XMS.msgbox.show('添加成功！', 'success', 2000);

//							alert("恭喜，申报成功！");
							setTimeout(function() {
								self.getDataMaintainList();
							}, 1000);
							Page.findId('queryDataMaintainForm').hide();
							// 关闭弹出层
							Page.findModal('addDataMaintainModal').modal('hide');
						}
					});
				});
				
				
				
			});

		},
		// 查询数据维护
		getDataMaintainList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');

			var _dom = Page.findId('getDataMaintainList');
			var _domPagination = _dom.find("[name='pagination']");
			// 设置服务器端分页
			Utils.getServerPage(srvMap.get('getQuestionInfoList'), _cmd, function(json, status) {//getQuestionInfoList
				window.XMS.msgbox.hide();
				// 查找页面内的Tpl，返回值html代码段，'#TPL_getCaseTempList' 即传入'getCaseTempList'
				var template = Handlebars.compile(Tpl.getQuestionInfoList);
				_dom.find("[name='content']").html(template(json.data));
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

//					alert(data.quesId);
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
								XMS.msgbox.show('添加成功！', 'success', 2000);
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
					//alert(cmd);//////////
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('deleQuestionInfo'), cmd, function(json, status) {
						if (status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000);
							setTimeout(function() {
								self.queryDataMaintainForm(Data.queryListCmd);
							}, 1000);
						}
					});
				}
			});
		},
		updateDataMaintain: function(Id, json) {
		
			var self = this;
			var i=0;
			while(json[i].quesId != Id){
				i++;
			}
			var data = json[i];
//			var _dom = Page.findModal('updateDataMaintainModal');
			
//			var index = _dom.attr("temp");
//			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
//			Page.findId('updateModal').html(template(json.data[index]));
//			var _modal = Page.findId('updateDataMaintainModal');
//			_modal.modal('show');
//			Utils.setSelectData(_modal);
			var template = Handlebars.compile(Page.findTpl('modifyQuesIdentifiedInfo'));
			Page.findId('updateDataMaintainInfo').html(template(data));
			var _dom = Page.findModal('updateDataMaintainModal');
			_dom.modal('show');
			Utils.setSelectData(_dom);
			
//			_dom.modal('show');
			var html = "<input readonly='readonly' type='text' class='form-control' value='" + Id + "' />";
			_dom.find("#JS_name").html(html);

			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _form = Page.findId('updateDataMaintainInfo');
				Utils.setSelectData(_form);
				var _cmd = _form.serialize();
				_cmd = _cmd + "&quesId=" + Id;
				XMS.msgbox.show('执行中，请稍候...', 'loading');
				Rose.ajax.getJson(srvMap.get('updateQuestionInfo'), _cmd, function(json, status) {
					if (status) {
						window.XMS.msgbox.show('更新成功！', 'success', 2000);
						setTimeout(function() {
							self.queryDataMaintainForm(Data.queryListCmd);
							_dom.modal('hide');
						}, 1000);
					}
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

		
		
		// 事件：双击选中当前行
		eventDClickCallback: function(obj, callback) {
			obj.find("tbody tr").bind('dblclick ', function(event) {
				if (callback) {
					callback();
				}
			});
		},
		//映射处理
		hdbarHelp: function() {},
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
define(function(require,exports,module){

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/autoCaseTempMng/";

	//系统大类下拉框显示
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");

	// 分页根据条件查询自动化用例模板信息
	srvMap.add("getCaseTempList", pathAlias + "getCaseTempList.json", "auto/template/listInfo");
	//获取模板信息
	srvMap.add("getCaseTempInfo", pathAlias + "getCaseTempInfo.json", "case/template/get");
	// 新增
	srvMap.add("addCaseTempInfo", pathAlias + "retMessage.json", "auto/template/save");
	// 保存
	srvMap.add("updateCaseTempInfo", pathAlias + "retMessage.json", "auto/template/update");
	// 删除角色
	srvMap.add("delCaseTempInfo", pathAlias + "retMessage.json", "auto/template/delete");
	// 根据模板ID获取组件列表
	srvMap.add("getTempCompList", pathAlias + "getTempCompList.json", "auto/templateComp/listInfo");
	//获取组件信息
    srvMap.add("getTempCompInfo", pathAlias + "getTempCompInfo.json", "sys/component/findone");
	// 获取组件树
	srvMap.add("getCompTree", pathAlias + "getCompTree.json", "sys/cache/commenCompTree");
	//请求参数列表
	srvMap.add("getParameterList",pathAlias + "getParameterList.json","sys/component/compParamList");
	//请求参数列表
	srvMap.add("saveAutoCompParam",pathAlias + "retMessage.json","auto/case/saveAutoCompParam");
	// 模板对象
    var Tpl = {
        getCaseTempList: require('tpl/autoManage/autoCaseTempMng/getCaseTempList.tpl'),
        getTempCompList: require('tpl/autoManage/autoCaseTempMng/getTempCompList.tpl'),
        getSideTempCompList: require('tpl/autoManage/autoCaseTempMng/getSideTempCompList.tpl'),
        getCaseTempInfo: require('tpl/autoManage/autoCaseTempMng/getCaseTempInfo.tpl'),
        getParameterList: require('tpl/autoManage/autoCaseTempMng/getParameterList.tpl')
    };

    // 容器对象
    var Dom = {
        queryCaseTempForm: '#JS_queryCaseTempForm',
        getCaseTempList: '#JS_getCaseTempList',
        getTempCompList: '#JS_getTempCompList',
        getSideTempCompList: '#JS_getSideTempCompList',
        getCaseTempInfo: '#JS_getCaseTempInfo',
        updateCaseTempInfoModal: '#JS_updateCaseTempInfoModal',
        generateCaseInfoModal:'#JS_generateCaseInfoModal',
        getParameterList:'#JS_getParameterList'
    };

    var Data = {
    	queryListCmd: null
    }

	var Query = {
		init: function(){
			// 默认查询所有
			this.getCaseTempList();
			// 初始化查询表单
			this.queryCaseTempForm();
		},
		// 按条件查询
		queryCaseTempForm: function(){
			var self = this;
			var _form = $(Dom.queryCaseTempForm);
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.getCaseTempList(cmd);
			});
		},
		// 查询自动化用例模板
		getCaseTempList: function(cmd){
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			//alert(_cmd);
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getCaseTempList'), _cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getCaseTempList);
            		$(Dom.getCaseTempList).html(template(json.data.content))
            		// 编辑模板
            		self.updateCaseTempInfo();
            		// 生成用例
            		self.generateCaseInfo();
            		// 废弃模板
            		self.delCaseTempInfo();
            		Utils.eventTrClickCallback($(Dom.getCaseTempList));
				}
  			});

		},
		// 删除自动化用例模板信息
		delCaseTempInfo: function(){
			var self = this;
			var _dom = $(Dom.getCaseTempList);
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				var data = Utils.getRadioCheckedRow(_dom);
				if(data){
					console.log(data);
					alert(data.tempId)
					var cmd = 'tempId='+data.tempId;
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delCaseTempInfo'), cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.show('删除成功！', 'success', 2000)
							setTimeout(function(){
								self.getCaseTempList(Data.queryListCmd);
							},1000)
						}
		  			});
				}
			});

		},
		updateCaseTempInfo:function(){
			var self = this;
			var _dom = $(Dom.getCaseTempList);
			var _edit = _dom.find("[name='edit']");
			_edit.unbind('click');
			_edit.bind('click', function() {
				var data = Utils.getRadioCheckedRow(_dom);
				if(data){
					alert(data.tempId)
					var cmd = 'tempId='+data.tempId;
					var _modal = $(Dom.updateCaseTempInfoModal);
			        _modal.modal('show');
			        // 获取组件树
			        self.getCompTree(_modal);
			        //获取已选组件
			        self.getTempCompList(cmd);
			        // 获取模板基本信息
			        self.getCaseTempInfo('caseId='+data.caseId);
		    	}
			});
		},
		// 生成用例信息
		generateCaseInfo:function(cmd){
			var self = this;
			var _dom = $(Dom.getCaseTempList);
			var _edit = _dom.find("[name='generate']");
			_edit.unbind('click');
			_edit.bind('click', function() {
				var data = Utils.getRadioCheckedRow(_dom);
				if(data){
					alert(data.tempId)
					var cmd = 'tempId='+data.tempId;
					var _modal = $(Dom.generateCaseInfoModal);
			        _modal.modal('show');
			        // 获取组件列表
			        self.getSideTempCompList('caseId='+data.caseId);
			        //保存组件
			        self.saveAutoCompParam(cmd);
		    	}
			});
		},
		// 获取侧边组件列表
		getSideTempCompList:function(cmd){
			alert('获取侧边组件列表'+cmd);
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getTempCompList'), cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					var _dom = $(Dom.getSideTempCompList);
					var template = Handlebars.compile(Tpl.getSideTempCompList);
            		_dom.html(template(json.data));
            		Utils.eventClickChecked(_dom,function(isChecked,thisDom){
            			var _name = thisDom.attr("name");
            			var _val = thisDom.val();
            			if(isChecked=="true"){
							var cmd = _name+'='+_val;
					        // 获取参数列表
					        self.getParameterList(cmd,_val);
            			}else{
            				$(Dom.getParameterList).find("tbody[name=compId_"+_val+"]").remove();
            			}
            		})
				}
  			});
		},
		// 获取参数列表
		getParameterList:function(cmd,compId){
			alert('参数列表'+cmd);
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getParameterList'), cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					json.data["compId"] = compId;
					var template = Handlebars.compile(Tpl.getParameterList);
            		$(Dom.getParameterList).append(template(json.data))

				}
  			});
		},
		// 保存生成用例
		saveAutoCompParam:function(cmd){
			var self = this;
			var _dom = $(Dom.generateCaseInfoModal);
			var _table = $(Dom.getParameterList);
			var _save = _dom.find("[name='save']");
			_save.unbind('click');
			_save.bind('click', function() {
				var _autoName = _dom.find("[name='autoName']").val();
				if(_autoName==''){
					XMS.msgbox.show('用例模板名称不能为空！', 'error',2000);
					return;
				}
				var hasData = Utils.getCheckboxCheckedRow($(Dom.getSideTempCompList));
				if(hasData){
					var cmd = {
						"autoName":_autoName,
						"compList":[]
					};
					// 抓取参数列表
		            _table.find("tbody").each(function(){
		            	var data ={};
		                data["compId"]= $(this).find("[name='compId']").val();
		                data["paramList"] = []
		                $(this).find("tr").each(function(){
		                	var paramData = {}
			                	$(this).find("input").each(function(){
		                    	var key = $(this).attr("name");
		                    	var value = $(this).val();
		                    	paramData[key]=value;
		                	});
			                data["paramList"].push(paramData);
		                })
	                	cmd.compList.push(data);
		            });
		            console.log("参数测试")
		            console.log(cmd)
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('saveAutoCompParam'), cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.show('保存成功！', 'success', 2000)
							_dom.modal('hide');
							setTimeout(function(){
								//self.getCaseTempList(Data.queryListCmd);
							},1000)
						}
		  			});
				}
			});
		},
		// 模板基本信息
		getCaseTempInfo:function(cmd){
			alert(cmd);
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getCaseTempInfo'), cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getCaseTempInfo);
            		$(Dom.getCaseTempInfo).html(template(json.data))
				}
  			});
		},
		// 获取已选组件
		getTempCompList:function(cmd){
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getTempCompList'), cmd, function(json, status) {
				if(status) {
					var _dom = $(Dom.getTempCompList);
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getTempCompList);
            		_dom.html(template(json.data))

            		Utils.eventTrClickCallback(_dom);
            		// 设置滚动条
            		self.setCompListScroll();
            		// 绑定删除
            		self.delTempCompInfo();
				}
  			});
		},
		setCompListScroll:function(){
			// 设置滚动条高度
            Utils.setScroll($(Dom.getTempCompList).find(".box-body"),'200px');
		},
		getTempCompInfo:function(cmd){
			var self = this;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.getJson(srvMap.get('getTempCompInfo'), cmd, function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					var _dom = $(Dom.getTempCompList);
					var template = Handlebars.compile(Tpl.getTempCompList);
					var dataArray = Utils.getTableDataRows(_dom);
					json.data["compOrder"] = dataArray.length+1;
					dataArray.push(json.data);
					console.log(dataArray);
            		$(Dom.getTempCompList).html(template(dataArray))
					// 设置滚动条
            		self.setCompListScroll();
            		self.delTempCompInfo();
            		Utils.eventTrClickCallback($(Dom.getTempCompList));
				}
  			});
		},
		delTempCompInfo:function(){
			var self = this;
			var _dom = $(Dom.getTempCompList);
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				_dom.find("input[type='radio']:checked").parents("tr").remove();
			});
			// 设置滚动条
    		self.setCompListScroll();

		},
		// 获取组件树
		getCompTree:function(obj){
			var self = this
			Rose.ajax.getJson(srvMap.get('getCompTree'), '', function(json, status) {
				if(status) {
					console.log(json.data)
					var setting = {
						check: {
							enable: false
						},
						data: {
							simpleData: {
								enable: true,
								idKey: "id",
				                pIdKey: "pid"
							}
						},
						callback:{
							 beforeClick: function(treeId, treeNode, clickFlag) {
		                        return (!treeNode.isParent);
		                     },
							 onClick: function(event, treeId, treeNode){
	                            console.log(treeNode);
		                        var cmd = "compId=" + treeNode.id;
		                        self.getTempCompInfo(cmd);
							 }
						}
					};
					$.fn.zTree.init(obj.find("[name='tree']"), setting, json.data);
				    // 设置滚动条高度
            		Utils.setScroll(obj.find("[name='scroll']"),'370px');
				}
	  		});
		}
	};
	module.exports = Query;
});


define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "dataMaintenance/";


	//分页根据条件查询功能点归属
	srvMap.add("getAsciptionList",pathAlias+"getAsciptionList.json","sys/systemfolder/listByName");
	// 删除所选条目
	srvMap.add("delSysInfo", pathAlias + "retMessage.json", "sys/systemfolder/del");
	//新增条目
    srvMap.add("addSysInfo", pathAlias + "retMessage.json", "sys/systemfolder/save");



	// 模板对象
	var Tpl = {
		getAsciptionList: require('tpl/dataMaintenance/getAsciptionList.tpl'),
	};

	// 容器对象
	var Dom = {
		queryCaseTempForm: '#JS_queryCaseTempForm',
		getAsciptionList:'#JS_getAsciptionList',

		addSysInfoModel : '#JS_addSysInfoModel',
		addSysInfo : '#JS_addSysInfo',



	};

	var Data = {
		queryListCmd: null
	}

	var Query = {
		init: function() {
			// 默认查询所有
			this.getCaseTempList();
			// 初始化查询表单
			this.queryCaseTempForm();
			//映射
			this.hdbarHelp();
		},
		// 按条件查询
		queryCaseTempForm: function() {
			var self = this;
			var _form = $(Dom.queryCaseTempForm);
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.getCaseTempList(cmd);
			});
		},
		// 查询自动化用例模板
		getCaseTempList: function(cmd) {
			var self = this;
			var _cmd = '' || cmd;
			Data.queryListCmd = _cmd;
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get('getAsciptionList'), _cmd, function(json, status) {
				if (status) {
					window.XMS.msgbox.hide();
					var template = Handlebars.compile(Tpl.getAsciptionList);
					$(Dom.getAsciptionList).html(template(json.data.content));

					//删除所选条目
					self.delCaseSysInfo();
					//新增条目
					self.addSysInfo();
					Utils.eventTrClickCallback($(Dom.getAsciptionList));

					//滚动条
					 $('#AsciptionListTable').parent().slimScroll({
                        "height": '250px'
                    });
				}
			});


		},
	// 删除所选条目
		delCaseSysInfo: function(){
			var self = this;
			var _dom = $(Dom.getAsciptionList);
			var _del = _dom.find("[name='del']");
			_del.unbind('click');
			_del.bind('click', function() {
				//获得当前单选框值
				var data = Utils.getRadioCheckedRow(_dom);
				if(data){
					console.log(data);
					var cmd = 'sysId='+data.sysId;
					//alert(cmd);
					XMS.msgbox.show('数据加载中，请稍候...', 'loading');
					Rose.ajax.getJson(srvMap.get('delSysInfo'), cmd, function(json, status) {
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
		//新增
		addSysInfo:function(){
			 var self = this;
			 var _dom = $(Dom.getAsciptionList);
			 var _add = _dom.find("[name='add']");
            _add.unbind('click');
            _add.bind('click', function() {
                // 弹出层
                $(Dom.addSysInfoModel).modal('show');
                //组件表单校验初始化
                var _form = $(Dom.addSysInfo);
                 // 表单提交
                $("#addSysInfoButton").unbind('click');
                $("#addSysInfoButton").bind('click', function() {
                    var cmd = _form.serialize();
                    console.log(cmd);
                    Rose.ajax.postJson(srvMap.get('addSysInfo'), cmd, function(json, status) {
                        if (status) {
                            // 添加用户成功后，刷新用户列表页
                            XMS.msgbox.show('添加成功！', 'success', 2000)
                                // 关闭弹出层
                            $(Dom.addSysInfoModel).modal('hide');

                            setTimeout(function() {
                                self.getCaseTempList();
                            }, 1000)
                        }
                    });
                })
            })
		},
		//映射处理
		hdbarHelp: function() {
			Handlebars.registerHelper("transformatImc", function(value) {
				if (value == 1) {
					return "一般系统";
				} else if (value == 2) {
					return "核心系统名";
				} else if (value == 3) {
					return "重要系统";
				}

			});
			Handlebars.registerHelper("transformatDomain", function(value) {
				if (value == "1") {
					return "—全部—";
				} else if (value == 2) {
					return "基础域";
				} else if (value == 3) {
					return "电子渠道";
				}else if (value == 4) {
					return "BOSS";
				} else if (value == 5) {
					return "CRM";
				}else if (value == 6) {
					return "渠道接入";
				} else if (value == 7) {
					return "接口域";
				}

			});			
		}
	};
		module.exports = Query;
});
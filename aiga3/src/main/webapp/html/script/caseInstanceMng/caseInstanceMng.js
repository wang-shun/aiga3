define(function(require, exports, module) {
	
	// 通用工具模块
	var Utils = require("global/utils.js");
	
	// 路径重命名
	var pathAlias = "caseInstanceMng/";

	srvMap.add("list", pathAlias + "getCaseTempList.json", "case/instance/list");
	srvMap.add("delete", pathAlias + "getCaseTempList.json", "case/instance/del");
	srvMap.add("get", pathAlias + "getCaseTempList.json", "case/instance/get");
	srvMap.add("update", pathAlias + "getCaseTempList.json", "case/instance/update");
	//srvMap.add("funcList", "componentManage/getFunList.json", "sys/component/compTree");
	
	//系统大类下拉框显示
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");


	// 模板对象
	var Tpl = {
		//getFactorList: $("#tpl_getFactorList"),
	};
	

	// 容器对象
	var Dom = {
		queryForm : "#JS_queryCaseInstanceForm",
		editForm: "#JS_TestForm",
		table: "#caseInstanceTable",
		delBtn: "#JS_delCaseInstance",
		editTable: "#JS_testCaseFactorList"
	};

	var fundId = null;

	var Init = {
		init: function() {
			this._render();
		},
		_render: function() {
			//加载功能树
			
			//this.initFunctionTree();
			
			this.getCaseInstanceList();
			
			this.addQueryFormListener();
			this.addBtnListener();
			
			// 默认只加载组织结构及条件查询
			this.addEditModelListener();
		},
		
		initFunctionTree: function(){
			Rose.ajax.getJson(srvMap.get('funcList'), '', function(json, status) {
				if(status) {
					var setting = {
						data: {
							key: {
								name:"name"
							},
							simpleData: {
								enable: true,
							}
						},
						callback:{
//							 beforeClick: function(treeId, treeNode, clickFlag) {
//                                return (treeNode.ifLeaf !== "N");
//                             }, 
							 onClick: function(event, treeId, treeNode){
                                var _funId = treeNode.id;
                                console.log(_funId);
                                //存储在全局变量中
                                fundId = _funId;
                                var cmd = "funId=" + fundId;
                                console.log(cmd)
							 }
						}
					};
					$.fn.zTree.init($("#Tree_getRightTree"), setting, json.data);
				}
			});
		},
		
		// 按条件查询模板
		addQueryFormListener: function() {
			var _form = $(Dom.queryForm);
			
			Utils.setSelectData(_form);
			
			_form.submit(function(e){
				$(Dom.table).bootstrapTable('refresh');
				return false;
			});
			
			_form.find('button[name="submit"]').on('click', function() {
				$(Dom.table).bootstrapTable('refresh');
			});
			
		},
		
		// 按条件查询模板
		addBtnListener: function() {
			
			$(Dom.delBtn).click(function(){
				var ids = $.map($(Dom.table).bootstrapTable('getSelections'), function (row) {
		            return row.testId;
		        })
		        
		        var date = {
					caseIds : ids
				}
		        
		        Rose.ajax.getJson(srvMap.get('delete'), date, function(json, status) {
					if(status) {
						$(Dom.table).bootstrapTable('refresh');
					}
				});
			});
			
			$(Dom.table).on('check.bs.table uncheck.bs.table ' +
	                'check-all.bs.table uncheck-all.bs.table', function () {
				$(Dom.delBtn).prop('disabled', !$(Dom.table).bootstrapTable('getSelections').length);
	        });
			
			
//			$(Dom.table).on('click', ".operation-run", function () {
//				
//	        });
//			
//			$(Dom.table).on('click', "a.operation-edit", function () {
//				console.log("a.operation-edit");
//				console.log($(Dom.table).bootstrapTable('getSelections'));
//				$('#modal_testCaseForm').modal();
//	        });
//
//			$(Dom.table).on('click', "operation-copy", function () {
//				
//			});
						
		},
		
		showEdit : function(row){
			var date = {
				testId : row.testId
			}
			$(Dom.editForm)[0].reset();
			$(Dom.editTable).find("tbody").html("");
			Rose.ajax.getJson(srvMap.get('get'), date, function(json, status) {
				if(status){
					console.log(json);
					var data = json["data"];
					//$(Dom.editForm).val(json);
					$(Dom.editForm).find("input[name='testId']").val(data["testId"]);
					$(Dom.editForm).find("input[name='testName']").val(data["testName"]);
					$(Dom.editForm).find("textarea[name='preResult']").val(data["preResult"]);
					$(Dom.editForm).find("textarea[name='testDesc']").val(data["testDesc"]);
					
					var factor_template = Handlebars.compile($("#tpl_getFactorList").html());
					$(Dom.editTable).find("tbody").html(factor_template(data.factors));
				}
			});
			$('#modal_testCaseForm').modal();
		},
		
		// 用例模板列表
		getCaseInstanceList: function(cmd) {
			var self = this;
			/**/
			$(Dom.table).bootstrapTable({
				url: srvMap.get('list'),
				method : "post",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				queryParams : function(params){
					jQuery.extend(params, $(Dom.queryForm).serializeJSON());
					return params;
				},
				responseHandler : function(data){
					return data["data"];
				},
		        totalField: 'totalElements',
		        dataField: 'content',
				pagination: true,
				sidePagination: "server",
				queryParamsType : "page",
				pageNumber: 1,
		        pageSize: 10,
		        pageList: [10, 25, 50, 100],
		        idField: "testId",
//		        clickToSelect: true,
		        buttonsClass: "xs",
		        smartDisplay: false,
		        paginationLoop: false,
		        columns :[
		        	{
                        checkbox: true
                    },
		        	{
		        		title: '测试用例名称',
		        		field: 'testName'
		        	},
		        	{
		        		title: '操作说明',
		        		field: 'testDesc',
		        		formatter : function(value, row, index) {
		                    // 在源代码中加入getPage方法
		                   if(typeof "abc" === 'string' && value && value.length > 20){
		                	   return value.substring(0, 20);
		                   }
		                   
		                   return value;
		                }
		        	},
		        	{
		        		title: '预期结果',
		        		field: 'preResult',
		        		formatter : function(value, row, index) {
		                    // 在源代码中加入getPage方法
		                   if(typeof "abc" === 'string' && value && value.length > 20){
		                	   return value.substring(0, 20);
		                   }
		                   
		                   return value;
		                }
		        	},
		        	{
		        		title: '用例类型',
		        		field: 'caseType',
		        		formatter : function(value, row, index) {
		                    // 在源代码中加入getPage方法
		                   if(value == 1){
		                	   return "UI类";
		                   }else if(value == 2){
		                	   return "接口类";
		                   }else if(value == 3){
		                	   return "后台进程类";
		                   }else{
		                	   return "其他类型";
		                   }
		                }
		        	},
		        	{
		        		title: '重要程度',
		        		field: 'important',
		        		formatter : function(value, row, index) {
		                    // 在源代码中加入getPage方法
		                   if(value == 1){
		                	   return "一级";
		                   }else if(value == 2){
		                	   return "二级";
		                   }else if(value == 3){
		                	   return "三级";
		                   }else if(value == 4){
		                	   return "四级";
		                   }else{
		                	   return "其他";
		                   }
		                }
		        	},{
		        		title: '系统',
		        		field: 'sysName'
		        	},{
		        		title: '子系统',
		        		field: 'sysSubName'
		        	},{
		        		title: '功能',
		        		field: 'funName'
		        	},
		        	{
		        		field: 'operate',
		        		title: '操作',
		        		formatter : function(){
		        			return [
		        	            '<a class="operation-edit" href="javascript:void(0)" title="编辑测试用例">',
		        	            '编辑',
		        	            '</a>  ',
		        	            '<a class="operation-copy" href="javascript:void(0)" title="复制测试用例">',
		        	            '复制',
		        	            '</a> ',
		        	            '<a class="operation-genauto" href="javascript:void(0)" title="生成自动化用例">',
		        	            '生成',
		        	            '</a>  ',
		        	        ].join('');
		        		},
		        		events: {
		        			'click .operation-edit': function (e, value, row, index) {
		        	        	self.showEdit(row);
		        	        },
		        	        'click .operation-run': function (e, value, row, index) {
		        	           	 console.log(e);
		        	           	 console.log(value);
		        	           	 console.log(row);
		        	           	 console.log(index);
		        	        },
		        	        'click .operation-genauto': function (e, value, row, index) {
		        	        	console.log(e);
		        	           	 console.log(value);
		        	           	 console.log(row);
		        	           	 console.log(index);
		        	        }
		        		}
		        	}
		        ]
			});
			
		},
		
		addEditModelListener: function(){
			$(Dom.editForm).find("button[name='save']").click(function(){
				
				var cmd = "testId=" + $(Dom.editForm).find("input[name='testId']").val();
				cmd += "&testName=" + $(Dom.editForm).find("input[name='testName']").val();
				cmd += "&preResult=" + $(Dom.editForm).find("textarea[name='preResult']").val();
				cmd += "&testDesc=" + $(Dom.editForm).find("textarea[name='testDesc']").val();

				var factors = [];
				$(Dom.editTable).find("tbody").find("tr").each(function(){
				    var tdArr = $(this).children();
				    
				    factors.push({
				    	"paramId":$(this).attr("data-paramId"),
				    	"factorName":tdArr.eq(0).find("input").val(),
				    	"factorValue":tdArr.eq(1).find("input").val(),
				    	"remark":tdArr.eq(2).find("input").val(),
				    	"factorOrder":tdArr.eq(3).find("input").val()
				    });
				});
				cmd += "&factors="+JSON.stringify(factors);
				
				Rose.ajax.postJson(srvMap.get('update'), cmd, function(json, status) {
					if (status) {
						// 添加用户成功后，刷新用户列表页
						XMS.msgbox.show('修改测试用例成功！', 'success', 2000)
						// 关闭弹出层
						$('#modal_testCaseForm').modal('hide');
					}
				});
			});
		}

	};
	module.exports = Init;
});
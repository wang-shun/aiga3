define(function(require, exports, module) {
	// 路径重命名
	var pathAlias = "caseInstanceMng/";

	srvMap.add("list", pathAlias + "getCaseTempList.json", "case/instance/list");
	srvMap.add("delete", pathAlias + "getCaseTempList.json", "case/instance/del");
	srvMap.add("get", pathAlias + "getCaseTempList.json", "case/instance/get");
	//srvMap.add("funcList", "componentManage/getFunList.json", "sys/component/compTree");
	
	//系统大类下拉框显示
	srvMap.add("getSysList", pathAlias + "getSysList.json", "sys/cache/listSysid");
	//系统子类下拉框
	srvMap.add("getSubsysList", pathAlias + "getSubsysList.json", "sys/cache/listSubsysid");
	//功能点下拉框
	srvMap.add("getFunList", pathAlias + "getFunList.json", "sys/cache/listFun");


	// 模板对象
	var Tpl = {

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
			Rose.ajax.postJson(srvMap.get('get'), date, function(json, status) {
				if(status){
					console.log(json);
					$(Dom.editForm).val(json);
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
		        clickToSelect: true,
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
		        		field: 'testDesc'
		        	},
		        	{
		        		title: '预期结果',
		        		field: 'preResult'
		        	},
		        	{
		        		title: '是否实现自动化'
		        	},
		        	{
		        		title: '可实现自动化'
		        	},
		        	{
		        		title: '已实现自动化'
		        	},
		        	{
		        		field: 'operate',
		        		title: '操作',
		        		formatter : function(){
		        			return [
		        	            '<a class="operation-run" href="javascript:void(0)" title="">',
		        	            '运行',
		        	            '</a>  ',
		        	            '<a class="operation-edit" href="javascript:void(0)" title="">',
		        	            '编辑',
		        	            '</a>  ',
		        	            '<a class="operation-copy" href="javascript:void(0)" title="">',
		        	            '复制',
		        	            '</a>'
		        	        ].join('');
		        		},
		        		events: {
		        	        'click .operation-run': function (e, value, row, index) {
		        	           	 console.log(e);
		        	           	 console.log(value);
		        	           	 console.log(row);
		        	           	 console.log(index);
		        	        },
		        	        'click .operation-edit': function (e, value, row, index) {
		        	        	self.showEdit(row);
		        	        },
		        	        'click .operation-copy': function (e, value, row, index) {
		        	        	console.log(e);
		        	           	 console.log(value);
		        	           	 console.log(row);
		        	           	 console.log(index);
		        	        }
		        		}
		        	}
		        ]
			});
			
		}

	};
	module.exports = Init;
});
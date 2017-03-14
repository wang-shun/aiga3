define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 初始化列表
	srvMap.add("getOrganize", "organize/getOrganize.json", "sys/organize/list");

	//树的现实
	srvMap.add("organizeTree", "organize/organizeTree.json", "sys/organize/treeList");

	//添加
	srvMap.add("saveOrganize", "organize/saveOrganize.json", "sys/organize/save");

	//修改
	srvMap.add("updateOrganize", "organize/updateOrganize.json", "sys/organize/update");

	//删除
	srvMap.add("deleOrganize", "organize/deleOrganize.json", "sys/organize/del");

	//删除
	srvMap.add("constantOrganize", "organize/constantOrganize.json", "sys/organize/constants");


	// 模板对象
	var Tpl = {
		getOrganize: require('tpl/organize/getOrganize.tpl')
	};


	//操作状态
	var Operate_state = "update";

	var Dom = {
		organizeId: null,
		getUserinfoForm: '#JS_getOrganizeForm',
		getOrganize: '#organizeinfo',
		sflxDataArray: [],
		sflxOrganize: []
	}


	var setting = {
		check: {
			enable: true
		},
		data: {
			key: {
				name: "organizeName"
			},
			simpleData: {
				enable: true,
				idKey: "organizeId",
				pIdKey: "parentOrganizeId"
			}
		},
		callback: {
			onClick: function(event, treeId, treeNode) {
				Dom.organizeId = treeNode.organizeId;
				var cmd = "organizeId=" + Dom.organizeId;
				
				Rose.ajax.postJson(srvMap.get('getOrganize'), cmd, function(json, status) {
					if (status) {
						var template = Handlebars.compile(Tpl.getOrganize);

						var a = json.data[0];
						a["sflxDataArray"] = Dom.sflxDataArray;
						a["sflxOrganize"] = Dom.sflxOrganize;
                       var orginazeType =  JSON.stringify(json.data[0].orgRoleTypeId);
                       var certificateType  =  JSON.stringify(json.data[0].contactCardType);
						$(Dom.getOrganize).html(template(a));
				        $("#connectCardType").val(certificateType);
	                    $("#orgRoleTypeId").val(orginazeType);

					}
				});
			}
		}
	};

	var getOrganize = {
		init: function() {
			this._render();
		},
		_render: function() {
			this.initOrganize();
			this.organizeSave();
			this.organizeDele();
			this.organizeAdd();

			// this.organizeSave();
			Rose.ajax.postJson(srvMap.get('organizeTree'), '', function(json, status) {
				if (status) {
					$.fn.zTree.init($("#treeDemo"), setting, json.data);
				}
			});

		},
		///////初始化///////////
		initOrganize: function() {

			var cmd1 = "category=certificateType";

			var cmd2 = "category=organizeType";
			Rose.ajax.postJson(srvMap.get('constantOrganize'), cmd2, function(json, status) {
				if (status) {
					Dom.sflxOrganize = json.data;
				}
			});

			Rose.ajax.postJson(srvMap.get('constantOrganize'), cmd1, function(json, status) {
				if (status) {
					Dom.sflxDataArray = json.data;
				}
			});

			Rose.ajax.postJson(srvMap.get('getOrganize'), '', function(json, status) {
				if (status) {
					// var template = Handlebars.compile(Tpl.getOrganize);
					// console.log(json.data)
					// var aaa= {};
					// aaa["sflxDataArray"] = Dom.sflxDataArray;
					// aaa["sflxOrganize"] = Dom.sflxOrganize;
					// alert(aaa);
					// console.log(json.data)
					// $(Dom.getOrganize).html(template(aaa));


					var template = Handlebars.compile(Tpl.getOrganize);

					var a = json.data;
					a["sflxDataArray"] = Dom.sflxDataArray;
					a["sflxOrganize"] = Dom.sflxOrganize;
					$(Dom.getOrganize).html(template(a));


				}
			});
		},
		//新增
		organizeAdd: function() {
			$("#organizeAdd").bind('click', function() {

				Operate_state = "new";
				// $("#JS_getOrganizeForm").resetForm(true);
				$("#organizeName").val("");
				$("#districtId").val("");
				$("#memberNum").val("");
				$("#phoneId").val("");
				$("#connectCardType").val("");
				$("#faxId").val("");
				$("#code").val("");
				$("#shortName").val("");
				$("#managerName").val("");
				$("#connectCardId").val("");
				$("#isLeaf").val("");
				$("#orgRoleTypeId").val("");
				$("#englishName").val("");
				$("#email").val("");
				$("#connectName").val("");
				$("#connectBillId").val("");

			});
		},

		//保存
		organizeSave: function() {
			// var _form = $(Dom.getUserinfoForm);
			// _form.find('button[name="organizeSave"]').bind('click', function() {
			// 	// 表单校验：成功后调取接口
			// 	_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
			// 		var cmd = $("#Form_getUserinfo").serialize();
			// 	});

			// })
			$("#organizeSave").bind('click', function() {

				if (Operate_state == "new" || Dom.organizeId == null) {
					var _dom = $("#JS_getOrganizeForm").find("input[name='parentOrganizeId']");
					if (Dom.organizeId == null) {
						_dom.val(-1);
					} else {
						_dom.val(Dom.organizeId);
					}
					var q = $("#connectCardType option").map(function() {
						return $(this).text();
					}).get().join(", ");
					var cmd = $("#JS_getOrganizeForm").serialize();

					Rose.ajax.postJson(srvMap.get('saveOrganize'), cmd, function(json, status) {
						if (status) {
							Operate_state = "update";
							alert("保存成功！");
							var q = $("#connectCardType option").map(function() {
								return $(this).text();
							}).get().join(", ");
							Rose.ajax.postJson(srvMap.get('organizeTree'), '', function(json, status) {
								if (status) {
									$.fn.zTree.init($("#treeDemo"), setting, json.data);
								}
							});
						}
					});

				} else {
					var cmd = {
						"parentOrganizeId": $("#parentOrganizeId").val(),
						"organizeId": Dom.organizeId,
						"organizeName": $("#organizeName").val(),
						"districtId": $("#districtId").val(),
						"memberNum": $("#memberNum").val(),
						"phoneId": $("#phoneId").val(),
						"connectCardType": $("#connectCardType option:selected").val(),
						"faxId": $("#faxId").val(),
						"code": $("#code").val(),
						"shortName": $("#shortName").val(),
						"managerName": $("#managerName").val(),
						"connectCardId": $("#connectCardId").val(),
						"isLeaf": $("#isLeaf").val(),
						"orgRoleTypeId": $("#orgRoleTypeId option:selected").val(),
						"englishName": $("#englishName").val(),
						"email": $("#email").val(),
						"connectName": $("#connectName").val(),
						"connectBillId": $("#connectBillId").val(),
					}
					Rose.ajax.postJson(srvMap.get('updateOrganize'), cmd, function(json, status) {
						if (status) {
							alert("保存成功！");
							Rose.ajax.getJson(srvMap.get('organizeTree'), '', function(json, status) {
								if (status) {
									$.fn.zTree.init($("#treeDemo"), setting, json.data);
								}
							});
						}
					});
				}

			});
		},
		organizeDele: function() {
			//删除
			$("#organizeDele").bind('click', function() {
				var cmd = "organizeId=" + Dom.organizeId;
				Rose.ajax.postJson(srvMap.get('deleOrganize'), cmd, function(json, status) {
					if (status) {
						alert("删除成功！");

						$("#organizeName").val("");
						$("#districtId").val("");
						$("#memberNum").val("");
						$("#phoneId").val("");
						$("#connectCardType").val("");
						$("#faxId").val("");
						$("#code").val("");
						$("#shortName").val("");
						$("#managerName").val("");
						$("#connectCardId").val("");
						$("#isLeaf").val("");
						$("#orgRoleTypeId").val("");
						$("#englishName").val("");
						$("#email").val("");
						$("#connectName").val("");
						$("#connectBillId").val("");

						Rose.ajax.postJson(srvMap.get('organizeTree'), '', function(json, status) {
							if (status) {
								$.fn.zTree.init($("#treeDemo"), setting, json.data);
							}
						});
					}
				});
			})
		}

	};
	module.exports = getOrganize;
});
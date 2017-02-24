define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 初始化列表
	srvMap.add("getOrganize", "organize/getOrganize.json", "/sys/organize/list");

	//树的现实
	srvMap.add("organizeTree", "organize/organizeTree.json", "/sys/organize/treeList");

	//添加
	srvMap.add("saveOrganize", "organize/saveOrganize.json", "/sys/organize/save");

	//修改
	srvMap.add("updateOrganize", "organize/updateOrganize.json", "/sys/organize/update");

	//删除
	srvMap.add("deleOrganize", "organize/deleOrganize.json", "/sys/organize/del");

	//删除
	srvMap.add("constantOrganize", "organize/constantOrganize.json", "/sys/organize/constants");


	// 按条件查询
	//srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	// 模板对象
	var Tpl = {
		getOrganize: require('tpl/organize/getOrganize.tpl')
	};


	// // 容器对象
	// var D = {
	// 	organizeinfo: $('#organizeinfo')
	// };

	//操作状态
	var Operate_state = "update";

	var Dom = {
		organizeId: null,
		getUserinfoForm: '#JS_getOrganizeForm',
		getOrganize: '#organizeinfo'
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
				alert(treeNode.organizeName);
				alert(treeNode.organizeId);
				alert(treeNode.parentOrganizeId);
				var cmd = "organizeId="+Dom.organizeId;
				var cmd1 = "category=certificateType";//////////////
				var sflxDataArray = [];
				Rose.ajax.getJson(srvMap.get('constantOrganize'), cmd1, function(json, status) {
					if (status) {
						sflxDataArray = json.data;
					}
				});

				Rose.ajax.getJson(srvMap.get('getOrganize'), cmd, function(json, status) {
					if (status) {
						var template = Handlebars.compile(Tpl.getOrganize);
						console.log(json.data)
						json.data["sflxDataArray"] = sflxDataArray;
						console.log(json.data)
						$(Dom.getOrganize).html(template(json.data[0]));

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
			Rose.ajax.getJson(srvMap.get('organizeTree'), '', function(json, status) {
				if (status) {
					console.log(json.data)
					$.fn.zTree.init($("#treeDemo"), setting, json.data);
				}
			});

		},
		//////////////////
		initOrganize: function() {
			var cmd1 = "category=certificateType";

			Rose.ajax.getJson(srvMap.get('constantOrganize'),cmd1, function(json, status) {
				if (status) {
					var template = Handlebars.compile(Tpl.getOrganize);
					console.log(json.data)
					var sflxDataArray = json.data;
					json.data = {};
					json.data["sflxDataArray"] = sflxDataArray;
					console.log(json.data)
					$(Dom.getOrganize).html(template(json.data));

				}
			});
		},
		//新增
		organizeAdd: function() {
			$("#organizeAdd").bind('click', function() {

				Operate_state = "new";

				$("#organizeName").val("");  
				$("#districtId").val("");
				$("#memberNum").val("");
				$("#phoneId").val("");
				$("#connectCardType option:selected").text("");
				$("#faxId").val("");
				$("#code").val("");
				$("#shortName").val("");
				$("#managerName").val("");
				$("#connectCardId").val("");
				$("#isLeaf option:selected").text("");
				$("#orgRoleTypeId option:selected").text("");
				$("#englishName").val("");
				$("#email").val("");
				$("#connectName").val("");
				$("#connectBillId").val("");

			});
		},

		//保存
		organizeSave: function() {
			var _form = $(Dom.getUserinfoForm);
			_form.find('button[name="organizeSave"]').bind('click',function(){
				// 表单校验：成功后调取接口
				_form.bootstrapValidator('validate').on('success.form.bv', function(e) {
		            var cmd = $("#Form_getUserinfo").serialize();
	        	});

			})
			$("#organizeSave").bind('click', function() {

				if (Operate_state == "new") {
					var cmd = {
						"organizeId": Dom.organizeId,
						"organizeName": $("#organizeName").val(),
						"districtId": $("#districtId").val(),
						"memberNum": $("#memberNum").val(),
						"phoneId": $("#phoneId").val(),
						"connectCardType": $("#connectCardType option:selected").text(),
						"faxId": $("#faxId").val(),
						"code": $("#code").val(),
						"shortName": $("#shortName").val(),
						"managerName": $("#managerName").val(),
						"connectCardId": $("#connectCardId").val(),
						"isLeaf": $("#isLeaf option:selected").text(),
						"orgRoleTypeId": $("#orgRoleTypeId option:selected").text(),
						"englishName": $("#englishName").val(),
						"email": $("#email").val(),
						"connectName": $("#connectName").val(),
						"connectBillId": $("#connectBillId").val(),
					}
					Rose.ajax.postJson(srvMap.get('saveOrganize'), cmd, function(json, status) {
						if (status) {
							Operate_state = "update";
							alert("保存成功！");
						}
					});
				} else {
					var cmd = {
						"organizeId": Dom.organizeId,
						"organizeName": $("#organizeName").val(),
						"districtId": $("#districtId").val(),
						"memberNum": $("#memberNum").val(),
						"phoneId": $("#phoneId").val(),
						"connectCardType": $("#connectCardType option:selected").text(),
						"faxId": $("#faxId").val(),
						"code": $("#code").val(),
						"shortName": $("#shortName").val(),
						"managerName": $("#managerName").val(),
						"connectCardId": $("#connectCardId").val(),
						"isLeaf": $("#isLeaf option:selected").text(),
						"orgRoleTypeId": $("#orgRoleTypeId option:selected").text(),
						"englishName": $("#englishName").val(),
						"email": $("#email").val(),
						"connectName": $("#connectName").val(),
						"connectBillId": $("#connectBillId").val(),
					}
					Rose.ajax.postJson(srvMap.get('updateOrganize'), cmd, function(json, status) {
						if (status) {
							alert("保存成功！");
						}
					});
				}

			});
		},
		organizeDele: function() {
			//删除
			$("#organizeDele").bind('click', function() {
				var cmd = "organizeId="+Dom.organizeId;
				Rose.ajax.getJson(srvMap.get('deleOrganize'), cmd, function(json, status) {
					if (status) {
						alert("删除成功！");

						$("#organizeName").val("");
						$("#districtId").val("");
						$("#memberNum").val("");
						$("#phoneId").val("");
						$("#connectCardType option:selected").text("");
						$("#faxId").val("");
						$("#code").val("");
						$("#shortName").val("");
						$("#managerName").val("");
						$("#connectCardId").val("");
						$("#isLeaf option:selected").text("");
						$("#orgRoleTypeId option:selected").text("");
						$("#englishName").val("");
						$("#email").val("");
						$("#connectName").val("");
						$("#connectBillId").val("");
					}
				});
			})
		}

	};
	module.exports = getOrganize;
});
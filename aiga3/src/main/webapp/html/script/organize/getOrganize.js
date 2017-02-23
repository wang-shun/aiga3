define(function(require,exports,module){

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
	srvMap.add("constantOrganize", "organize/constantOrganize.json", "/sys/organize/del");


	// 按条件查询
	//srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	// 模板对象
    var Tpl = {
        getOrganize: require('tpl/organize/getOrganize.tpl')
    };


    // 容器对象
    var Mod = {
        organizeinfo: $('#organizeinfo')
    };

    //操作状态
    var Operate_state = "update";

    var Data = {
    	"organizeId":null,
    	getUserinfoForm: '#JS_getOrganizeForm'
    }


	var getOrganize = {
		init: function(){
			this._render();
		},
		_render: function() {


		var setting = {
			check: {
				enable: true
			},
			data: {
				key: {
				name:"organizeName"
				},
				simpleData: {
					enable: true,
					idKey:"organizeId",
					pIdKey:"parentOrganizeId"
				}
			},
			callback:{
				 onClick: function(event, treeId, treeNode){
					Data.organizeId = treeNode.organizeId;
			        alert(treeNode.organizeName);
					alert(treeNode.organizeId);
			        alert(treeNode.parentOrganizeId);
			        var cmd = {
			        	"organizeId" : Data.organizeId
			        }
			        var cmd1 = {
			        	"organizeType" : "organizeType"
			        }
			        var sflxDataArray = [];
			        Rose.ajax.getJson(srvMap.get('constantOrganize'), cmd1, function(json, status) {
			        	if (status) {
			        		sflxDataArray = json.data;
			        	}
			        });
			        
			        Rose.ajax.getJson(srvMap.get('getOrganize'), cmd, function(json, status) {
						if(status) {
							var template = Handlebars.compile(Tpl.getOrganize);
							console.log(json.data)
							json.data["sflxDataArray"] = sflxDataArray;
							console.log(json.data);
            				Mod.organizeinfo.html(template(json.data));
				    		
						}
					});
				 }
			}
		};



			Rose.ajax.getJson(srvMap.get('organizeTree'), '', function(json, status) {
				if(status) {
					console.log(json.data)
					$.fn.zTree.init($("#treeDemo"), setting, json.data);
				}
			});


			// Rose.ajax.getJson(srvMap.get('getOrganize'), '', function(json, status) {
			// 	if(status) {
			// 		var template = Handlebars.compile(Tpl.getOrganize);
			// 		console.log(json.data)
   //          		Mod.organizeinfo.html(template(json.data));
   //          		 //iCheck
			// 	    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
			// 	      checkboxClass: 'icheckbox_minimal-blue',
			// 	      radioClass: 'iradio_minimal-blue'
			// 	    });
					
			// 	}
	  // 		});
			

			$("#organizeAdd").bind('click',function(){

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

			//保存
			$("#organizeSave").bind('click',function(){

				if(Operate_state == "new"){
		  			var cmd = {
		  				"organizeId":organizeId,
		  				"organizeName":$("#organizeName").val(),
		  				"districtId":$("#districtId").val(),
		  				"memberNum":$("#memberNum").val(),
		  				"phoneId":$("#phoneId").val(),
		  				"connectCardType":$("#connectCardType option:selected").text(),
		  				"faxId":$("#faxId").val(),
		  				"code":$("#code").val(),
		  				"shortName":$("#shortName").val(),
		  				"managerName":$("#managerName").val(),
		  				"connectCardId":$("#connectCardId").val(),
		  				"isLeaf":$("#isLeaf option:selected").text(),
		  				"orgRoleTypeId":$("#orgRoleTypeId option:selected").text(),
		  				"englishName":$("#englishName").val(),
		  				"email":$("#email").val(),
		  				"connectName":$("#connectName").val(),
		  				"connectBillId":$("#connectBillId").val(),	  				
		  			}
		  			Rose.ajax.postJson(srvMap.get('saveOrganize'), cmd, function(json, status) {
						if(status) {
							Operate_state = "update";
							alert("保存成功！");
						}
	  				});
	  			}else{
	  				var cmd = {
		  				"organizeId":organizeId,
		  				"organizeName":$("#organizeName").val(),
		  				"districtId":$("#districtId").val(),
		  				"memberNum":$("#memberNum").val(),
		  				"phoneId":$("#phoneId").val(),
		  				"connectCardType":$("#connectCardType option:selected").text(),
		  				"faxId":$("#faxId").val(),
		  				"code":$("#code").val(),
		  				"shortName":$("#shortName").val(),
		  				"managerName":$("#managerName").val(),
		  				"connectCardId":$("#connectCardId").val(),
		  				"isLeaf":$("#isLeaf option:selected").text(),
		  				"orgRoleTypeId":$("#orgRoleTypeId option:selected").text(),
		  				"englishName":$("#englishName").val(),
		  				"email":$("#email").val(),
		  				"connectName":$("#connectName").val(),
		  				"connectBillId":$("#connectBillId").val(),	  				
		  			}
		  			Rose.ajax.postJson(srvMap.get('updateOrganize'), cmd, function(json, status) {
						if(status) {
							alert("保存成功！");
						}
	  				});
	  			}

			});


			//删除
			$("#organizeDele").bind('click',function(){
					var cmd = {
						"organizeId":organizeId,  				
		  			}
					Rose.ajax.postJson(srvMap.get('deleOrganize'), cmd, function(json, status) {
						if(status) {
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
	  	
		},
	};
	module.exports = getOrganize;
});


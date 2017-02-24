define(function(require,exports,module){

	//引入公用模块
    //路径重命名
    var pathAlias = "roleFunc/";
	// 初始化列表
	// srvMap.add("getUserinfoList", "home/getUserinfoList.json", "/sys/role/list");
    //获取所有角色
	srvMap.add("getStaffRoleList","staffRole/getStaffRoleList.json","/sys/role/list");
	//获取所有功能菜单	
	srvMap.add("getFuncList", "roleFunc/getFuncList.json", "/sys/menu/list");
	//根据当前角色ID调取已选择的功能菜单funcIds
	srvMap.add("getRoleFuncCheckedList","roleFunc/getRoleFuncCheckedList.json","/sys/rolefunc/list");
    //更新角色菜单
    srvMap.add("roleFuncUpdate",pathAlias + "retMessage.json","/sys/rolefunc/update");
	// 按条件查询
	// srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	// 模板对象
    // var Tpl = {
    //     getUserinfoList: require('tpl/home/getUserinfoList.tpl')
    // };
    var Tpl2 = {
    	getStaffRoleList:require('tpl/roleFunc/getStaffRoleList.tpl')
    };
    // 容器对象
    // var Mod = {
    //     getUserinfoList: '#Page_getUserinfoList'
    //     // updateRoleFunc: $('#JS_updateRoleFunc')
    // };
    var Mod2 = {
    	getStaffRoleList:'#Page_getStaffRoleList'
    };
    var Dom = {
    	getRoleFuncTable: "#JS_getRoleFuncTable",
    	rolefuncUpdate: "#JS_rolefuncUpdate"
    }
    var funcIdNum;
    var Data = {
        roleId:null,
        getFuncListTree:null

    }
	var indexInfoQuery = {
		init: function(){
			this._render();
		},     
		_render: function() {
			this.getLeftTree();
			this.getStaffRoleList();
			this.rolefuncUpdate();
		},
		getStaffRoleList: function(){
			    var self = this;
			    Rose.ajax.getJson(srvMap.get('getStaffRoleList'), '', function(json, status) {                    
				if(status) {
					var template = Handlebars.compile(Tpl2.getStaffRoleList);
					console.log(json.data)
            		$(Mod2.getStaffRoleList).html(template(json.data));
            		 //iCheck
				    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				      checkboxClass: 'icheckbox_minimal-blue',
				      radioClass: 'iradio_minimal-blue'
				    });
			        // 事件：双击选中当前行数据
			        $(Dom.getRoleFuncTable).find("tr").bind('click', function(event) {			        	
			        	$(this).find('.minimal').iCheck('check');
			        	var _data = self.getCheckedRole();
			        	var _roleId =_data.roleId;
			        	var cmd = "roleId="+_roleId;
			        	Data.roleId = _roleId;
			        	console.log(cmd);
			        	self.getRoleFuncCheckedList(cmd);
			        });

					// 表格分页
					// $('#example3').DataTable({
			  //         "paging": true,
			  //         "lengthChange": false,
			  //         "searching": false,
			  //         "ordering": false,
			  //         "info": true,
			  //         "autoWidth": false
			  //       });

				}
	  		});
		},
		getRoleFuncCheckedList :function(cmd){
        	var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTree");
            treeObj.checkAllNodes(false);
        	Rose.ajax.getJson(srvMap.get('getRoleFuncCheckedList'), cmd, function(json, status) {
				if(status) {
					var _json = json.data;
					console.log(_json);
					var zTree_Menu = $.fn.zTree.getZTreeObj("Tree_getRightTree");  

					for(i in _json){
						var node = zTree_Menu.getNodeByParam('funcId',_json[i].funcId);  
	                	zTree_Menu.checkNode(node,true);//指定选中ID的节点  
	                	zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开  
					}
	                
				}
			})
        },
		getLeftTree:function(cmd){

				Rose.ajax.getJson(srvMap.get('getFuncList'), 'cmd', function(json, status) {
				if(status) {
					console.log(json.data)
					require('zTreeExcheckJS');
					//checkbox代码块
					var setting = {
						check: {
							enable: true
						},
						data: {
							key: {
								name:"name"
							},
							simpleData: {
								enable: true,
								idKey:"funcId",
								pIdKey:"parentId"
							}
						},
						callback:{
							 onCheck: function(event, treeId, treeNode){
                                funcIdNum = treeNode.funcId;
                                console.log(funcIdNum);
							 }
						}
					};
					$.fn.zTree.init($("#Tree_getRightTree"), setting, json.data);


	                
				}
	  		});

		},
		
        rolefuncUpdate: function(){
			var self = this;
			$("#JS_rolefuncUpdate").bind('click', function() {
				var _data = self.getCheckedRole();
					var cmd = {
						roleId:_data.roleId,
						funcIds:_data.funcIds
					};
							console.log(cmd);					
						Rose.ajax.getJson(srvMap.get('roleFuncUpdate'),cmd, function(json, status) {
							if(status) {
								// 启用成功后，重新加载用户列表
								// self.getUserinfoList();
								window.XMS.msgbox.show('功能菜单更新成功！', 'success', 2000)
							}
			  			});
			});
        },
        getCheckedRole : function(){
			// funcIdNum = treeNode.funcId;
			var _obj = $('#Page_getStaffRoleList').find("input[type='radio']:checked").parents("tr");
			var treeObj = $.fn.zTree.getZTreeObj("Tree_getRightTree");
			console.log(treeObj);
			var nodes = treeObj.getCheckedNodes(true);
  			console.log(nodes);
  			var funcIds="";
  			for(var i=0;i<nodes.length;i++){
  				if(i==0){
  					funcIds=nodes[i].funcId;
  				}else{
  					funcIds+=','+nodes[i].funcId;
  				}
  			}
  			console.log(funcIds);
	  //       var _roleAuthorId = _obj1.find("input[name='roleAuthorId']")		
            var _roleId = _obj.find("input[name='roleId']")		
			// console.log(_roleAuthorId);
			// var _roleId = _obj.find("input[name='roleId']")
			var data = {
				roleId: "",
		        funcIds: ""
		    }
		    if(_roleId.length==0){
		    	window.XMS.msgbox.show('请先选择一个角色！', 'error', 2000);
		    	return;
		    }else{
		    	data.roleId = _roleId.val();
		    	data.funcIds = funcIds;
		    }
		    return data;
		}
		// updateRoleFunc:function(){
		// 	var self = this;
		// 	$(Mod.updateRoleFunc).bind('click', function() {
		// 		var _data = self.getCheckedRow();
		// 		if(_data){
		// 			var _stafId =_data.staffId;
		// 			if(_data.state == '失效'){
		// 				Rose.ajax.getJson(srvMap.get('startUserinfo'), 'stafId:'+_stafId, function(json, status) {
		// 					if(status) {
		// 						// 启用成功后，重新加载用户列表
		// 						self.getUserinfoList();
		// 						window.XMS.msgbox.show('员工启用成功！', 'success', 2000)
		// 					}
		// 	  			});
		// 			}else{
		// 				window.XMS.msgbox.show('只允许操作失效员工！', 'error', 2000);
		// 			}
		// 		}
		// 	});
		// }
	};
	module.exports = indexInfoQuery;
});


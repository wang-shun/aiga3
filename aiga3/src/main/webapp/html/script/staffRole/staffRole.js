define(function(require,exports,module){

	//引入公用模块
	//路径重命名
	var pathAlias = "staffRole/";
	// 查询所有员工
	srvMap.add("getUserinfoList", "staffRole/getUserinfoList.json", "/aiga/staff/list");
	//查询所有岗位信息
	srvMap.add("getStaffRoleList","staffRole/getStaffRoleList.json","/sys/role/list");
	//根据当前员工ID调取已选择的岗位信息roleAuthorID,roleID
	srvMap.add("getStaffRoleCheckedList","staffRole/getStaffRoleCheckedList.json","/sys/staffrole/list");
	//修改员工角色
    srvMap.add("saveStaffRole", pathAlias + "retMessage.json","/sys/staffrole/update");
    //删除员工角色
    // srvMap.add("delStaffRole", pathAlias + "retMessage.json","/sys/staffrole/del");
	// 按条件查询
	// srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	// 模板对象
    var Tpl1 = {
        getUserinfoList: require('tpl/staffRole/getUserinfoList.tpl')
    };
    var Tpl2 = {
    	getStaffRoleList:require('tpl/staffRole/getStaffRoleList.tpl')
    };

    // 容器对象
    var Mod1 = {
        getUserinfoList: '#Page_getUserinfoList'
    };
    var Mod2 = {
    	getStaffRoleList:'#Page_getStaffRoleList'
    };
    var Dom = {
    	saveStaffRole:"#JS_saveStaffRole",
    	getUserinfoListTable:'#JS_getUserinfoListTable',
    	getStaffRoleListTable:'#JS_getStaffRoleListTable'
    };
    var Data = {
    	stafId:null
    }

	var indexInfoQuery = {
		init: function(){
			this._render();
		},
		_render: function() {
		 // 查询所有员工
         this.getUserinfoList();
		 // 查询所有角色
         this.getStaffRoleList();

         this.saveStaffRole();
         // this.delStaffRole();
      },
      getUserinfoList: function(){
      	 	var self = this;
　　　　　　Rose.ajax.getJson(srvMap.get('getUserinfoList'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl1.getUserinfoList);
					console.log(json.data)
            		$(Mod1.getUserinfoList).html(template(json.data));

            		 //iCheck
				    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				      checkboxClass: 'icheckbox_minimal-blue',
				      radioClass: 'iradio_minimal-blue'
				    });

			        $(Dom.getUserinfoListTable).find("tr").bind('click', function(event) {
			        	$(this).find('.minimal').iCheck('check');
			        	var _data = self.getCheckedRole();
			        	var _stafId =_data.staffId;			        	
			        	var cmd = "staffId="+_stafId;
			        	Data.staffId = _stafId;
			        	console.log(cmd);
			        	self.getStaffRoleCheckedList(cmd)
			        });

					// 表格分页
					/*$('#example1').DataTable({
			          "paging": true,
			          "lengthChange": false,
			          "searching": false,
			          "ordering": false,
			          "info": true,
			          "autoWidth": false
			        });*/
			        // 事件：双击选中当前行数据

				}
	  		});
        },
        getStaffRoleCheckedList :function(cmd){
        	$("input[name='roleId']").iCheck('uncheck');
        	Rose.ajax.getJson(srvMap.get('getStaffRoleCheckedList'), cmd, function(json, status) {
				if(status) {
					var _array = json.data.StaffRoleList;
					console.log(_array);
					/*$(Dom.getStaffRoleListTable).find('input[name="staffId"]').each(function(){
						$(this).val(Data.staffId);	
					})*/
					for(x in _array){
						$("#JS_role_"+_array[x].roleId).iCheck('check');
					}
				}
			});

        },
		getStaffRoleList: function(cmd){
			Rose.ajax.getJson(srvMap.get('getStaffRoleList') ,cmd, function(json, status) {
                var self = this;
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
			        $('#JS_getStaffRoleListTable').find("tr").bind('click', function(event) {
			        	$(this).find('.minimal').iCheck('check');
			        	 //点击员工后，重新加载岗位列表
						 // self.getStaffRoleList();
			        });
/*					// 表格分页
					$('#example2').DataTable({
			          "paging": true,
			          "lengthChange": false,
			          "searching": false,
			          "ordering": false,
			          "info": true,
			          "autoWidth": false
			        });
*/
				}
	  		});
		},
		saveStaffRole:function(){
			var self = this;
			$(Dom.saveStaffRole).bind('click', function() {
				var _dom = $(Dom.getStaffRoleListTable).find("input[name='roleId']:checked");
				var _roleIdsArray = [];
				_dom.each(function(){
					_roleIdsArray.push($(this).val());
				})
				var cmd = {
					"staffId":Data.staffId,
					"roleIds":_roleIdsArray.join(",")
				}
				console.log(cmd);
			
				Rose.ajax.postJson(srvMap.get('saveStaffRole'), cmd, function(json, status) {
					if(status) {
						// 启用成功后，重新加载用户列表
						// self.getUserinfoList();
						window.XMS.msgbox.show('保存成功！', 'success', 2000)
					}
	  			});
			});
		},
		// delStaffRole:function(){
		// 	var self = this;
		// 	$(Dom.delStaffRole).bind('click', function() {
		// 		var _data = self.getCheckedRole();
		// 		if(_data){
		// 			var _roleAuthId =_data.roleAuthorId;
		// 				Rose.ajax.postJson(srvMap.get('delStaffRole'), 'roleAuthorId'+_roleAuthId, function(json, status) {
		// 					console.log(_roleAuthId);
		// 					if(status) {
		// 						// 启用成功后，重新加载用户列表
		// 						self.getUserinfoList();
		// 						window.XMS.msgbox.show('角色删除成功！', 'success', 2000)
		// 					}
		// 	  			});
		// 		}
		// 	});
		// },
		getCheckedRole : function(){
			var _obj = $(Dom.getUserinfoListTable).find("input[type='radio']:checked").parents("tr");
				// var _obj1 = $('#Page_getStaffRoleList').find("input[type='checkbox']:checked").parents("tr");
		        // var _roleAuthorId = _obj1.find("input[name='roleAuthorId']")		
		       //  var _roleId = _obj1.find("input[name='roleId']")		
				// console.log(_roleAuthorId);
			var _staffId = _obj.find("input[name='staffId']")
		    if(_staffId.length==0){
		    	window.XMS.msgbox.show('请先选择一个员工！', 'error', 2000);
		    	return;
		    }else{
 				var data = {
					roleAuthorId: "",
			        staffId: "",
			        roleId: ""
			    }
		    	/*data.roleAuthorId = _roleAuthorId.each(function(){
                   data.roleAuthorId+=this.value +　',';
		    	});*/
		    	// console.log(data.roleAuthorId);
		    	data.staffId = _staffId.val();
		    	// data.roleId = _roleId.val();
		    }
		    return data;
		}
	};
	module.exports = indexInfoQuery;
});



	  	// 	$("#JS_queryUserinfoList").bind('click',function(){
	  	// 		var cmd = {
	  	// 			"name":$("#exampleInputName").val(),
	  	// 			"id":$("#exampleInputNumber").val(),
	  	// 			"tel":$("#exampleInputTel").val()
	  	// 		}
	  	// 		Rose.ajax.postJson(srvMap.get('queryUserinfoList'), cmd, function(json, status) {
				// if(status) {
				// 	var template = Handlebars.compile(Mtpl.getUserinfoList);
				// 	console.log(json.data)
    //         		$getUserinfoList.html(template(json.data));
				// }
	  	// 	});
	  	// 	})


			/*// 首页菜单折叠
		    $("#JS_toggleMenu").on('click', function () {
		      if (!$('body').hasClass('sidebar-collapse')){
		      	$('body').addClass("sidebar-collapse")
		      }else{
		      	$('body').removeClass("sidebar-collapse")
		      }
		    });
*/
		    

			/*Rose.ajax.getJson(srvMap.get('myLinks'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Mtpl.myLinks);
            		$myLinks.html(template(json.data));
				}
	  		});

	  		Rose.ajax.getJson(srvMap.get('myMenus'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Mtpl.myMenus);
            		$myMenus.html(template(json.data));
				}
	  		});*/

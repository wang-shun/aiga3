define(function(require,exports,module){

	
	
	// 初始化菜单列表
	srvMap.add("getMenulist", "systemManage/menuManage/getMenulist.json", "/sys/menu/list");
    // 获取菜单信息
	srvMap.add("getMenuinfo", "systemManage/menuManage/getMenuinfo.json", "/sys/menu/get");	
    //获取保存结果
    srvMap.add("addMenu", "systemManage/menuManage/addMenu.json", "/sys/menu/save");
    //获取修改结果
    srvMap.add("updateMenu", "systemManage/menuManage/updateMenu.json", "/sys/menu/update");	
    //删除结果
    srvMap.add("deleMenu", "systemManage/menuManage/deleMenu.json", "/sys/menu/del");	

	// 模板对象
    var Tpl = {
        getMenuinfo: require('tpl/MenuMng/getMenuinfo.tpl')
    };

    // 容器对象
    var Mod = {
        getMenuinfo: $('#Page_menuInfo')
    };


    //操作状态
    var Operate_state = "update";
    //当前菜单id
    var currentMenu = null;
    

	var MenuMng = {
		init: function(){
			this._render();
		},
		_render: function() {


		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "funcId",
                    pIdKey: "parentId"					
				}
			},
			callback:{
				 onClick: function(event, treeId, treeNode){
				 	currentMenu = treeNode.funcId;
					var cmd = {
						"funcId":currentMenu
					}			 	
				 	Rose.ajax.getJson(srvMap.get('getMenuinfo'), 'cmd', function(json, status) {
						if(status) {
							var template = Handlebars.compile(Tpl.getMenuinfo);
							console.log(json.data)
				    		Mod.getMenuinfo.html(template(json.data));
				    		
						}
					});
				 }
			}			
		};
		Rose.ajax.getJson(srvMap.get('getMenulist'), '', function(json, status) {
				if(status) {
					console.log(json.data)
            		$.fn.zTree.init($("#treeDemo"), setting, json.data);
				}
	  	});		

		
			
			



			
			//添加
			$("#menuAdd").bind('click',function(){
				Operate_state = "new";
				
				$("#funcCode").val("");
  				$("#name").val("");
  				$("#funcImg").val(""),
				$("#funcType option:selected").text("");
  				$("#funcArg").val("");
  				$("#dllPath").val("");
  				$("#viewname").val("");
  				$("#notes").val("");	
	  		})

	  		//保存
	  		$("#menuSave").bind('click',function(){
	  			if(Operate_state == "new"){
		  			var cmd = {
		  				"patentId":currentMenu,
		  				"funcCode":$("#funcCode").val(),
		  				"name":$("#name").val(),
		  				"funcImg":$("#funcImg").val(),
						"funcType":$("#funcType option:selected").text(),
		  				"funcArg":$("#funcArg").val(),
		  				"dllPath":$("#dllPath").val(),
		  				"viewname":$("#viewname").val(),
		  				"notes":$("#notes").val()	  				
		  			}
		  			Rose.ajax.postJson(srvMap.get('addMenu'), cmd, function(json, status) {
						if(status) {
							Operate_state = "update";
							alert("保存成功！");
							console.log(json.data)
					        $.fn.zTree.init($("#treeDemo"), setting, json.data);							
						}
	  				});
		  		}
		  		else{
					var cmd = {
						"funcId":currentMenu,
		  				"funcCode":$("#funcCode").val(),
		  				"name":$("#name").val(),
		  				"funcImg":$("#funcImg").val(),
						"funcType":$("#funcType option:selected").text(),
		  				"funcArg":$("#funcArg").val(),
		  				"dllPath":$("#dllPath").val(),
		  				"viewname":$("#viewname").val(),
		  				"notes":$("#notes").val()  				
		  			}
		  			Rose.ajax.postJson(srvMap.get('updateMenu'), cmd, function(json, status) {
						if(status) {
							alert("保存成功！");
						}
	  				});		  			

		  		}
	  		})

	  		//删除
			$("#menuDele").bind('click',function(){
					var cmd = {
						"funcId":currentMenu  				
		  			}
					Rose.ajax.postJson(srvMap.get('deleMenu'), cmd, function(json, status) {
						if(status) {
							alert("删除成功！");

							$("#funcCode").val("");
			  				$("#name").val("");
			  				$("#funcImg").val(""),
							$("#funcType option:selected").text("");
			  				$("#funcArg").val("");
			  				$("#dllPath").val("");
			  				$("#viewname").val("");
			  				$("#notes").val("");
							console.log(json.data)
					        $.fn.zTree.init($("#treeDemo"), setting, json.data);			  				
							  											
						}
	  				});		  							
	  		})	  		


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
		},
	};
	module.exports = MenuMng;
});


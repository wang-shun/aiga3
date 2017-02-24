define(function(require,exports,module){

	
	
	// 初始化菜单列表
	srvMap.add("getMenulist", "systemManage/menuManage/getMenulist.json", "sys/menu/list");
    // 获取菜单信息
	srvMap.add("getMenuinfo", "systemManage/menuManage/getMenuinfo.json", "sys/menu/get");	
    //获取保存结果
    srvMap.add("addMenu", "systemManage/menuManage/addMenu.json", "sys/menu/save");
    //获取修改结果
    srvMap.add("updateMenu", "systemManage/menuManage/updateMenu.json", "sys/menu/update");	
    //删除结果
    srvMap.add("deleMenu", "systemManage/menuManage/deleMenu.json", "sys/menu/del");	

	// 模板对象
    var Tpl = {
        getMenuinfo: require('tpl/MenuMng/getMenuinfo.tpl')
    };

    // 容器对象
    var Dom = {
        getMenuinfo: '#Page_menuInfo',
        getMenuinfoForm :'#JS_getMenuinfoForm'

    };



    //操作状态
    var OperateState = "new";
    //当前菜单id
    var currentMenu = null;
    
	var setting = {
		check: {
			enable: false
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
			 	if(!(currentMenu == 0)){
					var cmd = "funcId="+currentMenu;
				 	Rose.ajax.getJson(srvMap.get('getMenuinfo') + "?" + cmd, '', function(json, status) {
						if(status) {
							OperateState = "update";
							var template = Handlebars.compile(Tpl.getMenuinfo);
							console.log(json.data)
				    		$(Dom.getMenuinfo).html(template(json.data));
				            $('#funcType').val(json.data.funcType);
						}
					});
			 	}else{
					$("#funcCode").val("");
					$("#name").val("");
					$("#funcImg").val("");
					$("#funcType").val("");
					$("#funcArg").val("");
					$("#dllPath").val("");
					$("#viewname").val("");
					$("#notes").val("");			 		
			 	}
			 }
		}			
	};    

	var MenuMng = {
		init: function(){
			this._render();
		},
		_render: function() {

			this.initMenuList();
			this.menuAdd();
			this.menuSave();
			this.menuDel();
			//展示表单
			var temp = Handlebars.compile(Tpl.getMenuinfo);
			$(Dom.getMenuinfo).html(temp());		
			
				
		},
		
		initMenuList:function(){

			Rose.ajax.getJson(srvMap.get('getMenulist'), '', function(json, status) {
					if(status) {
						console.log(json.data);
						json.data.push({
							funcId : 0,
							name: "功能菜单"
						});
	            		$.fn.zTree.init($("#treeDemo"), setting, json.data);
					}
		  	});
		},

		menuAdd: function(){
			
			$("#menuAdd").bind('click',function(){

				OperateState = "new";
				$("#funcCode").val("");
				$("#name").val("");
				$("#funcImg").val("");
				$("#funcType").val("");
				$("#funcArg").val("");
				$("#dllPath").val("");
				$("#viewname").val("");
				$("#notes").val("");
	  		})			
		},

        menuSave: function(){
			var _form = $(Dom.getMenuinfoForm);        	
    		_form.find('button[name="save"]').bind('click',function(){
   				// 表单校验：成功后调取接口
				var cmd = $("#JS_getMenuinfoForm").serialize();
				_form.bootstrapValidator('validate').on('success.form.bv', function(e) {	
					if(OperateState == "new"){
						if(currentMenu){
							cmd = "parentId="+currentMenu+"&"+cmd;
						}else{
							cmd = "parentId=0&"+cmd;
						}
			  			
			  			console.log(cmd);
			  			Rose.ajax.postJson(srvMap.get('addMenu') + "?" + cmd, "", function(json, status) {
							if(status) {
								OperateState = "update";
								Rose.ajax.getJson(srvMap.get('getMenulist'), '', function(json, status) {
									if(status) {
										console.log(json.data)
										json.data.push({
											funcId : 0,
											name: "功能菜单"
										});										
					            		$.fn.zTree.init($("#treeDemo"), setting, json.data);
									}
						  		});
								
								Operate_state = "update";
								XMS.msgbox.show('添加菜单成功！', 'success', 3000)						
							}
		  				});
			  		}
			  		else{
//						 var cmd = 
//						 	"funcId="+currentMenu+"funcCode="+$("#funcCode").val()+
//			  				"name="+$("#name").val()+"funcImg="+$("#funcImg").val()+
//						 	"funcType="+$("#funcType").val()+
//			  	 			"funcArg="+$("#funcArg").val()+
//			  	 			"dllPath="+$("#dllPath").val()+
//			  	 			"viewname="+$("#viewname").val()+
//			  	 			"notes="+$("#notes").val()  				
						cmd = "funcId="+currentMenu+"&"+cmd;
			  			console.log(cmd);
			  			Rose.ajax.postJson(srvMap.get('updateMenu')+"?"+cmd, '', function(json, status) {
							if(status) {
								OperateState = "update";
								Rose.ajax.getJson(srvMap.get('getMenulist'), '', function(json, status) {
									if(status) {
										console.log(json.data)
										json.data.push({
											funcId : 0,
											name: "功能菜单"
										});										
					            		$.fn.zTree.init($("#treeDemo"), setting, json.data);
									}
						  		});								
								XMS.msgbox.show('修改菜单成功！', 'success', 3000)
							}
		  				});	  			

			  		}		         	   
	        	});				
  				
		},
		
		menuDel: function(){
			var _form = $(Dom.getMenuinfoForm);

			_form.find('button[name="del"]').bind('click',function(){
				
				var cmd = "funcId="+currentMenu;
				console.log(cmd);
	  			
				Rose.ajax.postJson(srvMap.get('deleMenu')+"?"+cmd, '', function(json, status) {
					if(status) {
						
						Rose.ajax.getJson(srvMap.get('getMenulist'), '', function(json, status) {
							if(status) {
								console.log(json.data);
								json.data.push({
									funcId : 0,
									name: "功能菜单"
								});								
			            		$.fn.zTree.init($("#treeDemo"), setting, json.data);
							}
				  	});
						OperateState = null;
						XMS.msgbox.show('删除菜单成功！', 'success', 3000)
						$("#funcCode").val("");
						$("#name").val("");
						$("#funcImg").val("");
						$("#funcType").val("");
						$("#funcArg").val("");
						$("#dllPath").val("");
						$("#viewname").val("");
						$("#notes").val("");
						console.log(json.data);
					}
				});
								
			})	  			
		},

	};

		
	module.exports = MenuMng;
});


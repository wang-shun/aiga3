define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');
	// 初始化列表
	srvMap.add("getUserinfoList", "home/getUserinfoList.json", "/sys/role/list");
	// 按条件查询
	srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	// 模板对象
    var Tpl = {
        getUserinfoList: require('tpl/home/getUserinfoList.tpl')
    };

    // 容器对象
    var Mod = {
        getUserinfoList: $('#Page_getUserinfoList')
    };

	var indexInfoQuery = {
		init: function(){
			this._render();
		},
		_render: function() {


			var setting = {
				view: {
					showLine: false
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};

			var zNodes =[
				{ id:1, pId:0, name:"父节点1 - 展开", open:true},
				{ id:11, pId:1, name:"父节点11 - 折叠"},
				{ id:111, pId:11, name:"叶子节点111"},
				{ id:112, pId:11, name:"叶子节点112"},
				{ id:113, pId:11, name:"叶子节点113"},
				{ id:114, pId:11, name:"叶子节点114"},
				{ id:12, pId:1, name:"父节点12 - 折叠"},
				{ id:121, pId:12, name:"叶子节点121"},
				{ id:122, pId:12, name:"叶子节点122"},
				{ id:123, pId:12, name:"叶子节点123"},
				{ id:124, pId:12, name:"叶子节点124"},
				{ id:13, pId:1, name:"父节点13 - 没有子节点", isParent:true},
				{ id:2, pId:0, name:"父节点2 - 折叠"},
				{ id:21, pId:2, name:"父节点21 - 展开", open:true},
				{ id:211, pId:21, name:"叶子节点211"},
				{ id:212, pId:21, name:"叶子节点212"},
				{ id:213, pId:21, name:"叶子节点213"},
				{ id:214, pId:21, name:"叶子节点214"},
				{ id:22, pId:2, name:"父节点22 - 折叠"},
				{ id:221, pId:22, name:"叶子节点221"},
				{ id:222, pId:22, name:"叶子节点222"},
				{ id:223, pId:22, name:"叶子节点223"},
				{ id:224, pId:22, name:"叶子节点224"},
				{ id:23, pId:2, name:"父节点23 - 折叠"},
				{ id:231, pId:23, name:"叶子节点231"},
				{ id:232, pId:23, name:"叶子节点232"},
				{ id:233, pId:23, name:"叶子节点233"},
				{ id:234, pId:23, name:"叶子节点234"},
				{ id:3, pId:0, name:"父节点3 - 没有子节点", isParent:true}
			];

			$.fn.zTree.init($("#treeDemo"), setting, zNodes);

			Rose.ajax.getJson(srvMap.get('getUserinfoList'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getUserinfoList);
					console.log(json.data)
            		Mod.getUserinfoList.html(template(json.data));
            		 //iCheck
				    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
				      checkboxClass: 'icheckbox_minimal-blue',
				      radioClass: 'iradio_minimal-blue'
				    });


					// 表格分页
					$('#example1').DataTable({
			          "paging": true,
			          "lengthChange": false,
			          "searching": false,
			          "ordering": false,
			          "info": true,
			          "autoWidth": false
			        });
				}
	  		});


	  		$("#JS_queryUserinfoList").bind('click',function(){
	  			var cmd = {
	  				"name":$("#exampleInputName").val(),
	  				"id":$("#exampleInputNumber").val(),
	  				"tel":$("#exampleInputTel").val()
	  			}
	  			Rose.ajax.postJson(srvMap.get('queryUserinfoList'), cmd, function(json, status) {
				if(status) {
					var template = Handlebars.compile(Mtpl.getUserinfoList);
					console.log(json.data)
            		$getUserinfoList.html(template(json.data));
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
	module.exports = indexInfoQuery;
});


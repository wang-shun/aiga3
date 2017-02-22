define(function(require,exports,module){

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
        getUserinfoList: '#Page_getUserinfoList'
    };

	var Query = {
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
					enable: true
				}
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"随意勾选 1", open:true},
			{ id:11, pId:1, name:"随意勾选 1-1", open:true},
			{ id:111, pId:11, name:"随意勾选 1-1-1"},
			{ id:112, pId:11, name:"随意勾选 1-1-2"},
			{ id:12, pId:1, name:"随意勾选 1-2", open:true},
			{ id:121, pId:12, name:"随意勾选 1-2-1"},
			{ id:122, pId:12, name:"随意勾选 1-2-2"},
			{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
			{ id:21, pId:2, name:"随意勾选 2-1"},
			{ id:22, pId:2, name:"随意勾选 2-2", open:true},
			{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
			{ id:222, pId:22, name:"随意勾选 2-2-2"},
			{ id:23, pId:2, name:"随意勾选 2-3"}
		];
			var code;
			function setCheck() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
				py = $("#py").attr("checked")? "p":"",
				sy = $("#sy").attr("checked")? "s":"",
				pn = $("#pn").attr("checked")? "p":"",
				sn = $("#sn").attr("checked")? "s":"",
				type = { "Y":py + sy, "N":pn + sn};
				zTree.setting.check.chkboxType = type;
				showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
			}
			function showCode(str) {
				if (!code) code = $("#code");
				code.empty();
				code.append("<li>"+str+"</li>");
			}
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);


			alert(1);
			Rose.ajax.getJson(srvMap.get('getUserinfoList'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Tpl.getUserinfoList);
					console.log(json.data)
					alert(template(json.data));
            		$(Mod.getUserinfoList).html(template(json.data));
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
	module.exports = Query;
});


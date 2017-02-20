define(function(require,exports,module){

	// 初始化列表
	srvMap.add("getUserinfoList", "home/getUserinfoList.json", "/sys/role/list");

	// 按条件查询
	srvMap.add("queryUserinfoList", "home/queryUserinfoList.json", "/sys/role/query");

	var Mtpl = {
		getUserinfoList: require('tpl/home/getUserinfoList.tpl')
	};
	var $getUserinfoList = $('#Page_getUserinfoList');

	var indexInfoQuery = {
		init: function(){
			this._render();
		},
		_render: function() {

			Rose.ajax.getJson(srvMap.get('getUserinfoList'), '', function(json, status) {
				if(status) {
					var template = Handlebars.compile(Mtpl.getUserinfoList);
					console.log(json.data)
            		$getUserinfoList.html(template(json.data));
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


			// 首页菜单折叠
		    $("#JS_toggleMenu").on('click', function () {
		      if (!$('body').hasClass('sidebar-collapse')){
		      	$('body').addClass("sidebar-collapse")
		      }else{
		      	$('body').removeClass("sidebar-collapse")
		      }
		    });

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


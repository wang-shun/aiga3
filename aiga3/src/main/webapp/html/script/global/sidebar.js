/*
 * 侧边栏菜单模块
 * author:qijc
 */
define(function(require, exports, module) {

    // 侧边栏菜单列表接口
    srvMap.add("getSidebarMenuList", "global/getSidebarMenuList.json", "global/getSidebarMenuList.json");

    // 模板对象
    var Tpl = {
        sidebar: require('tpl/global/sidebar.tpl')
    };

    // 容器对象
    var Mod = {
        sidebar: $('#Mod_sidebar')
    };

    // 渲染对象
    var Query = {
        init: function(){
            this._render();
            this.menulist = $('#JS_MenuList');
            this.mainContent = $('#JS_MainContent');
        },
        _render: function() {
        	var self = this;
            // 请求：侧边栏菜单列表接口
		    Rose.ajax.getJson(srvMap.get('getSidebarMenuList'), '', function(json, status) {
		        if (status) {
		            var template = Handlebars.compile(Tpl.sidebar);
		            Mod.sidebar.html(template(json.data));

		            self.initLoadPage(json.data.sidebarMenuList);
		            self.convertURL();
		        }
		    });
        },
        convertURL: function() {
        	var self = this;
			$('#JS_MenuList').find("a").bind('click', function() {
				var _href = $(this).data('href');
				self.loadHtml(_href); 
			});
		},
		setPath: function(){

		},
		loadHtml: function(href){
			if(href.indexOf('.html')>=0){
				alert('load');
				Rose.ajax.loadHtml($('#JS_MainContent'),href)
			}
		},
		initLoadPage:function (data){
			var _href = '#';
			for (x in data){
				var _thisData = data[x];
				if(_thisData.isActive){
					_href = _thisData.menuURL;
				}
				if(_thisData.hasChild){
					var _thisArrayChild = _thisData.childMenuList;
					for (i in _thisArrayChild){
						var _thisDataChild = _thisArrayChild[i]
						if(_thisDataChild.isActive){
							_href = _thisDataChild.menuURL;
						}
						if(_thisDataChild.hasChild){
							var _thisArrayThirdChild = _thisDataChild.childMenuList;
							for (t in _thisArrayThirdChild){
								var _thisDataThirdChild = _thisArrayThirdChild[t]
								if(_thisDataThirdChild.isActive){
									_href = _thisDataThirdChild.menuURL;
								}
							}
						}
					}
				}
			}
			this.loadHtml(_href)
		}
    };
	Query.init();
    // 暴露渲染对象
    module.exports = Query;
});

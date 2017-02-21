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
        },
        _render: function() {
        	var self = this;
            // 请求：侧边栏菜单列表接口
		    Rose.ajax.getJson(srvMap.get('getSidebarMenuList'), '', function(json, status) {
		        if (status) {
		            var template = Handlebars.compile(Tpl.sidebar);
		            Mod.sidebar.html(template(json.data));
		            self.convertURL();
		        }
		    });
        },
        convertURL: function() {
			var _Mod = {
				menulist: $('#JS_MenuList'),
				mainContent: $('#JS_MainContent')
			}
			_Mod.menulist.find("a").on('click', function(event) {
				var _href = $(this).data('href');
				if(_href != '#' && _href != '#nogo' && _href != ''){
					Rose.ajax.loadHtml(_Mod.mainContent,_href)
				}
			});
		},
		setPath: function(){

		}
    };
	Query.init();
    // 暴露渲染对象
    module.exports = Query;
});

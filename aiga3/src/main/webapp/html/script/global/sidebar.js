/*
 * 侧边栏菜单模块
 * author:qijc
 */
define(function(require, exports, module) {

    // 侧边栏菜单列表接口
    srvMap.add("getSidebarMenuList", "global/getSidebarMenuList.json", "global/menu");

    // 模板对象
    var Tpl = {
        sidebar: require('tpl/global/sidebar.tpl')
    };

    // 容器对象
    var Mod = {
        sidebar: $('#Mod_sidebar'),
        menuList: $("#JS_MenuList")
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
		            Mod.sidebar.html(template(json));
		            
		            self.convertURL();
		            //self.initLoadPage(json.data);
		            self.initLoadPage($('#JS_MenuList'));
		        }
		    });
        },
        convertURL: function() {
        	var self = this;
			$('#JS_MenuList').find("a").bind('click', function() {
				var _href = $(this).data('href');
				if(_href){
					self.loadHtml(_href); 
				}
			});
			
		},
		setPath: function(){

		},
		loadHtml: function(href){
			if(href.indexOf('.html')>=0){
				Rose.ajax.loadHtml($('#JS_MainContent'),href)
			}
		},
		initLoadPage:function (ulHtml){
			
//			if(data && data.length > 0){
//				var _thisData = data[0];
//				var _href = _thisData.viewname;
//				if(_href){
//					this.loadHtml(_href);
//				}else{
//					this.initLoadPage(_thisData.subMenus);
//				}
//			}
			var firstLi = ulHtml.children("li:first");
			var a = firstLi.children("a:first");
			if(a.length > 0){
				a.click();
				var _href = a.data('href');
				if(!_href){
					var nextUl = firstLi.children("ul:first");
					this.initLoadPage(nextUl);
				}
			}
		}
		
    };
	Query.init();
    // 暴露渲染对象
    module.exports = Query;
});

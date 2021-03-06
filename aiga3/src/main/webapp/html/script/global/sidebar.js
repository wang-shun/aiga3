define(function(require, exports, module) {
	// 通用工具模块
    var Utils = require("global/utils.js");

    // 侧边栏菜单列表接口
    srvMap.add("getSidebarMenuList", "global/getSidebarMenuList.json", "global/menu");
    // 侧边栏菜单列表接口
    srvMap.add("recordMenu", "global/getSidebarMenuList.json", "webservice/menuRecord/add");
    // 模板对象
    var Tpl = {
        sidebar: require('tpl/global/sidebar.tpl')
    };

    // 容器对象
    var Dom = {
        sidebar: '#JS_sidebar',
        menuList: '#JS_menuList',
        mainTabs: '#JS_mainTabs',
        mainTabsContent: '#JS_mainTabsContent'
    };

    var Data = {
    	sidebar:[]
    }

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
		            $(Dom.sidebar).html(template(json));
		            Data.sidebar = json.data;
		            var height = $(Dom.sidebar).height();
                   //$("body").addClass('sidebar-collapse');
		            Utils.setScroll($(Dom.sidebar).children(".sidebar"),height)
		            self.bindMenuClickEvent();
		            self.initHomePage();
		        }
		    });
        },
        getSidebarList:function(){
        	return Data.sidebar;
        },
        getSidebarInfo:function(id){
        	// something todo
        },
        write_tab_record:function(objData) {
        	if(objData.name == '首页' ) {
        		return
        	}     	
        	var cmd = {
        		recordId : 0,
        		menuUrl : objData.href,
        		menuCode : objData.id,
        		menuName : objData.name     		
        	}
        	Rose.ajax.postJson(srvMap.get('recordMenu'), cmd, function(json, status) {
        		if(status){
        		} else {
        			//失败
        		}
        	});
        },
        creatTab: function(objData) {
        	var self = this;
    		var objId = '#JS_childTab_'+objData.id;
        	if($(objId).length > 0){
                $("a[href='" + objId + "']").click();
                $('#JS_childTab_' + objData.id).data("cmd", objData.cmd); // 页面重新加载时，传入新参数
                Rose.ajax.loadHtml($('#JS_childTab_' + objData.id), objData.href);
        	}else{
                if($(Dom.mainTabs).children("li").length >= 8) {
                    $(Dom.mainTabs).find("li:not(.active) .fa-remove").eq(0).click();
                }
				var _delDom = '';
				if(objData.name!="首页"){
					_delDom = '<i class="fa fa-remove"></i>';
				}
				var _dataCmd = objData.cmd || '';
				$(Dom.mainTabs).children('li.active').removeClass('active').end().append('<li class="active">'+
							'<a href="#JS_childTab_'+objData.id+'" data-toggle="tab" title="'+objData.name+'" data-id="'+objData.id+'">'+
								Rose.string.substr(objData.name,6) + _delDom +
							'</a>'+
						'</li>');
				$(Dom.mainTabsContent).children('div.active').removeClass('active').end().append('<div class="tab-pane active" id="JS_childTab_'+objData.id+'" data-funid ="'+objData.id+'" data-cmd ="'+_dataCmd+'"></div>');
				Rose.ajax.loadHtml($('#JS_childTab_'+objData.id),objData.href);

				// 绑定删除事件
				self.bindTabDelEvent();
        	}
			self.write_tab_record(objData);
        },
        delTab: function(id){
        	var objContent = '#JS_childTab_'+id;
        	var $li = $("a[href='"+objContent+"']").parent("li");

        	// 当期为active，删除前显示前一个节点
        	if($li.hasClass("active")){
        		if($li.next().length > 0){
        			$li.next().find("a").click();
        		}else{
        			$li.prev().find("a").click();
        		}
        	}
        	// 删除节点
        	$li.remove();
        	$(objContent).remove();

        },
        // 绑定点击事件
        bindMenuClickEvent: function() {
        	var self = this;
			$(Dom.menuList).find("a").bind('click', function() {
				var objData = {
					id : $(this).data('id'),
					name : $.trim($(this).text()),
					href : $(this).data('href'),
                    cmd : $(this).data('cmd')
				}
				if(objData.href.indexOf('.html')>=0){
					self.creatTab(objData);
				}
			});
		},
		bindTabDelEvent: function(){
			var self = this;
			var $removeObj = $(Dom.mainTabs).find(".fa-remove");
			$removeObj.unbind();
    		$removeObj.bind("click",function(){
				var _id = $(this).parent().data("id");
				self.delTab(_id);
			})
		},
		initHomePage:function (){
			$(Dom.sidebar).find("li:first > a").click();
//			if(data && data.length > 0){
//				var _thisData = data[0];
//				var _href = _thisData.viewname;
//				if(_href){
//					this.loadHtml(_href);
//				}else{
//					this.initLoadPage(_thisData.subMenus);
//				}
//			}
			/*var firstLi = ulHtml.children("li:first");
			var a = firstLi.children("a:first");
			if(a.length > 0){
				a.click();
				var _href = a.data('href');
				if(!_href){
					var nextUl = firstLi.children("ul:first");
					this.initLoadPage(nextUl);
				}
			}*/
		}
    };
    // 暴露渲染对象
    module.exports = Query;
});

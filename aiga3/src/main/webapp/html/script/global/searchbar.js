/*
 * 搜索栏公用模块
 * author:qijc
 */
define(function(require,exports,module){

	//更多条件查询接口，订单查询页面搜索框更多条件查询
	srvMap.add("queryOrderMoreCon", "global/queryOrderMoreCon.json", "");
	var _html = require("tpl/global/searchbar.tpl");
	var _objId = '#Mod_searchbar';

	//搜索栏
	Rose.ajax.postJson(srvMap.get('queryOrderMoreCon'), '', function(json, status) {
		if(status) {
			var template = Handlebars.compile(_html);
			$(_objId).html(template(json.data));
			
			//搜索框下拉交互
            $("[name='searchOption']").each(function() {
                var _currentObj = $(this).find(".current");
                var _thisObj = $(this);

                //划入滑出隐藏
                $(this).hover(
                    function() {
                        $(this).addClass("hover").find(".dropdown-list li").click(function() {
		                    $(this).hide().siblings().show();
		                    if($(this).attr('data-type')) {
		                    	_currentObj.find(".text").html($(this).text()).attr('data-type',$(this).attr('data-type'));
		                    } else {
		                    	_currentObj.find(".text").html($(this).text()).removeAttr('data-type');
		                    }
		                    
		                    _thisObj.removeClass("hover");
		                })
                    },
                    function() {
                        $(this).removeClass("hover");
                    }
                )
            })

			//更多条件展开
			$("#J_showMore").click(function(){
				$("#J_showMoreContent").toggleClass('hide');
			})

			//更多条件选择
			$("[name='searchType']").each(function(){
				Rose.dom.clickClass($(this).find("dd"),"current");	
			});
    } 
  });
	//module.exports = navBar;
});


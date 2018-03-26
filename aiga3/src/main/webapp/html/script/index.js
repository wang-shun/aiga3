define(function(require,exports,module){

	//引入公用模块
	var sidebar = require('global/sidebar.js');
	var header = require('global/header.js');
	//公告
	require('lib/loading/loading.js');
	var loadingText = require('lib/loading/loadingText.js');
	
	
	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			sidebar.init();
			header.init();
			this._noties();
		},
		_noties:function() {		
			$(document).ready(function(){
				$(".bulletin").Scroll({line:1,speed:1000,timer:2000});//修改此数字调整滚动状态
			});
			loadingText.init();
		}
	}
    module.exports = Query;
});


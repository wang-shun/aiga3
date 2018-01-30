define(function(require,exports,module){

	//引入公用模块
	var sidebar = require('global/sidebar.js');
	var header = require('global/header.js');

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
			sidebar.init();
			header.init();
		}
	}
    module.exports = Query;
});


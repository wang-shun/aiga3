define(function(require,exports,module){

	//引入公用模块
	require('global/header.js');
	require('global/sidebar.js');

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
		}
	}
    module.exports = Query;
});


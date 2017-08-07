define(function(require,exports,module){

	//引入公用模块
	require('global/sidebar.js');
	require('global/header.js');

	var Query = {
		init: function(){
			this._render();
		},
		_render: function() {
		}
	}
    module.exports = Query;
});


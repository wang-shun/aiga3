define(function(request){
	var otherModule = require('otherModule');
});

define(function(request){
	var isReady = false,foobar;
	require(['foo','bar'],function(foo,bar){
		isReady = true;
		foobar = foo()+bar();
	});
	
	return {
		isReady:isReady,
		foobar:foobar
	};
});

define(['lib/Deferred'], function( Deferred ){
    var defer = new Deferred(); 
    require(['lib/templates/?index.html','lib/data/?stats'],
        function( template, data ){
            defer.resolve({ template: template, data:data });
        }
    );
    return defer.promise();
});

require.config({
    paths: {
        jquery: [
            '//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.0/jquery.min.js',
            'lib/jquery'
        ]
    }
});

require.config({
	path:{
		jquery:[
			'//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.0/jquery.min.js',
			'lib/jquery'
		]
	}
});


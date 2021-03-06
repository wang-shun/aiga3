define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('TopListView');
	//查询指标top10
	srvMap.add("queryTopList", pathAlias + "dataMaintain.json", "archi/toplist/queryByCondition");

	var cache = {
		datas : ""	,
		datas2: ""  ,
		datas3: ""  ,
		datas4: ""  ,
	};
    // 模板对象
	var Tpl = {
		getTopList: require('tpl/toplist/toplist.tpl'),
	};

	var Data = {
		
	};

	var Query = {
		init: function() {
			this.queryTopListForm();
			this.refreshStyle();
			this.toplistShow();
			
			},
		refreshStyle: function(){
			var self = this;
			var _dom = Page.findId('showMyImageForm');
			
		    $(".weekly-list li").bind("mouseenter", weekly_ani).bind("mouseleave",
		    function() {
		        clearTimeout($(this).data("setTime"));
		    });
		    function weekly_ani(e) {
		        var me = $(e.target).closest("li");
		        if (me.hasClass("current")) return;
		        var orili = me.parent().find(".current");
		        $(this).data("setTime", setTimeout(function() {
		            weekly_move(me, orili, 100, 39)
		        },
		        150));
		    }
		    function weekly_move(me, orili, h, h2) {
		        me.addClass("current");
		        $(".weekly-list li").unbind("mouseenter", weekly_ani);
		        setTimeout(function() {
		            var cur_h = me.height();
		            if (cur_h < h - 2) {
		                var cur_orih = orili.height();
		                var dh = Math.round((h - cur_h) / 2.5);
		                me.css("height", cur_h + dh);
		                orili.css("height", cur_orih - dh);
		                setTimeout(arguments.callee, 25);
		            } else {
		                me.addClass("current").css("height", h);
		                orili.css("height", h2);
		                $(".weekly-list li").bind("mouseenter", weekly_ani);
		                orili.removeClass("current");
		            }
		        },
		        25);
		    }
		    $(".weekly-list").find("li:first").addClass("current").animate({
		        height: 100
		    },
		    300);
		},
		//按条件查询
		queryTopListForm: function() {
			var self = this;
			var _form = Page.findId('queryTopListForm');
			var now = new Date(); 
			_form.find('input[name="startMonth"]').val(this.formatDate(now));
			Utils.setSelectData(_form);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.bind('click', function() {
				var cmd = _form.serialize();
				self.toplistShow(cmd);
			});
		},
		toplistShow: function(cmd){
			var self = this;
			var _dom = Page.findId('showTopListForm');
			var _cmd = 'indexGroup=3001001';
			var _cmd2 = 'indexGroup=3001002';
			var _cmd3 = 'indexGroup=3001003';
			var _cmd4 = 'indexGroup=3001004';
			if(cmd) {
				_cmd = _cmd + "&" + cmd;
				_cmd2 = _cmd2 + "&" + cmd;
				_cmd3 = _cmd3 + "&" + cmd;
				_cmd4 = _cmd4 + "&" + cmd;
			}
			Rose.ajax.postJson(srvMap.get('queryTopList'),_cmd,function(json, status){
				if(status) {
					cache.datas=json.data;
					var template = Handlebars.compile(Tpl.getTopList);
					_dom.find("[name='content']").html(template(json.data.content));
					if(json.data.content.length==0){
						_dom.attr({style:"display:none"});
					}
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
			
			var _dom2 = Page.findId('showTopListForm2');
			Rose.ajax.postJson(srvMap.get('queryTopList'),_cmd2,function(json2, status){
				if(status) {
					cache.datas2=json2.data;
					var template2 = Handlebars.compile(Tpl.getTopList);
					_dom2.find("[name='content2']").html(template2(json2.data.content));
					if(json2.data.content.length==0){
						_dom2.attr({style:"display:none"});
					}
				} else {
					XMS.msgbox.show(json2.retMessage, 'error', 2000);
				}					
			});
			
			var _dom3 = Page.findId('showTopListForm3');
			Rose.ajax.postJson(srvMap.get('queryTopList'),_cmd3,function(json3, status){
				if(status) {
					cache.datas3=json3.data;
					var template3 = Handlebars.compile(Tpl.getTopList);
					_dom3.find("[name='content3']").html(template3(json3.data.content));
					if(json3.data.content.length==0){
						_dom3.attr({style:"display:none"});
					}
				} else {
					XMS.msgbox.show(json3.retMessage, 'error', 2000);
				}					
			});
			
			var _dom4 = Page.findId('showTopListForm4');
			Rose.ajax.postJson(srvMap.get('queryTopList'),_cmd4,function(json4, status){
				if(status) {
					cache.datas4=json4.data;
					var template4 = Handlebars.compile(Tpl.getTopList);
					_dom4.find("[name='content4']").html(template4(json4.data.content));
					if(json4.data.content.length==0){
						_dom4.attr({style:"display:none"});
					}
				} else {
					XMS.msgbox.show(json4.retMessage, 'error', 2000);
				}					
			});
		},	
		//时间格式化
		formatDate: function(date) {
			var d = new Date(date),
				month = '' + (d.getMonth() + 1),
				day = '' + d.getDate(),
				year = d.getFullYear(); 
			if (month.length < 2) month = '0' + month;
			if (day.length < 2) day = '0' + day;
			return [year, month, day].join('-');	
		}
	};
	module.exports = Query;
});
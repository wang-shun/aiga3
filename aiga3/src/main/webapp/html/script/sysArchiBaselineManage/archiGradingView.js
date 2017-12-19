define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	var sidebar = require('global/sidebar.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "sysArchiBaselineManage/archiGradingManage/";
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('archiGradingView');
   //一级域查询
    srvMap.add("getPrimaryDomainList", pathAlias+"primaryDomainList.json", "archi/first/list");

    //二级数据接口 入参：idFirst
    srvMap.add("getSecView", pathAlias+"getSecView.json", "archi/view/secView");

    //二级跨层数据接口 入参：idFirst
    srvMap.add("getCrossSecView", pathAlias+"getCrossSecView.json", "archi/view/secView");


    //三级数据接口 入参：idFirst
    srvMap.add("getThirdSecView", pathAlias+"getThirdSecView.json", "archi/view/thirdView");

    var Tpl = {
		getSecView: require('tpl/sysArchiBaselineManage/archiGradingManage/getSecView.tpl'),
		getCrossSecView: require('tpl/sysArchiBaselineManage/archiGradingManage/getCrossSecView.tpl'),
		getThirdSecView: require('tpl/sysArchiBaselineManage/archiGradingManage/getThirdSecView.tpl'),
		getOneSecView: require('tpl/sysArchiBaselineManage/archiGradingManage/getOneSecView.tpl')
	};


	var cache = {
		datas : ""
	};


	var Data = {
        setPageType:function(type){
    		return {
    			"data":{
    				"type":type
    			}
    		};
    	}
    };

	var init = {
		init: function() {
			this._render();
		},
		_render: function() {
			var self = this;
			self._querydomain();
			self._exportPNG();
		},
		_exportPNG:function(){
			var _form = Page.findId('selectData');
			var _exportBtn =  _form.find("[name='export']");
			var _queryBtn =  _form.find("[name='query']");
			_exportBtn.off('click').on('click',function(){
				if(_queryBtn.data("level")=="0"){
					XMS.msgbox.show('请先完成查询！', 'error', 2000);
				}else{
					XMS.msgbox.show('导出中，请稍候...', 'loading');
					html2canvas($("#archiView"), {
						onrendered: function (canvas) {
	                    	var url = canvas.toDataURL();
	                    	//以下代码为下载此图片功能
	                    	var triggerDownload = $("<a>").attr("href", url).attr("download", "系统架构图.png").appendTo("body");
	                        triggerDownload[0].click();
	                        triggerDownload.remove();
	                        window.XMS.msgbox.hide();
	                    }
	          		});
				}
			});
		},
		//查询下拉框数据加载，绑定查询按钮事件
		_querydomain: function() {
			var self = this;
			var _form = Page.findId('selectData');
			Utils.setSelectData(_form);
			var _queryBtn =  _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var _self = $(this);
				var _idFirst = _form.find("[name='primaryDomain']").val();
				var _viewLevel = _form.find("[name='viewLevel']:checked").val();
				var _srvMap = "getSecView";
				if(_viewLevel=="1"){
					var template = Handlebars.compile(Tpl.getOneSecView);
					Page.findName("archiView").html(template({}));
					_self.data("level",_viewLevel);

					// 一级域点击直接跳转对应的二级域
					$(".menu-href").on("click",function(){
						_form.find("[name='primaryDomain']").val($(this).data("id"));
						_form.find("input[name=viewLevel][value=2]").click();
						_queryBtn.click();
					})
					return;
				}else if(_viewLevel=="3"){
					_srvMap = "getThirdSecView";
					if( _idFirst == 0) {
						XMS.msgbox.show('一级域为空！', 'error', 2000);
						return
					}
				}else{
					if( _idFirst == 0) {
						XMS.msgbox.show('一级域为空！', 'error', 2000);
						return
					}
				}
				var cmd= "idFirst="+_idFirst;
				XMS.msgbox.show('数据加载中，请稍候...', 'loading');
				Rose.ajax.postJson(srvMap.get(_srvMap),cmd, function(json, status) {
					if(status) {
						if(_viewLevel=="2"){
							if(json.data.isCross=="0"){
								var template = Handlebars.compile(Tpl.getSecView);
							}else{
								var template = Handlebars.compile(Tpl.getCrossSecView);
							}
						}else if(_viewLevel=="3"){
							if(json.data.hasDaaS){
								json.data.content[1].item[0].item.splice(0, 0, json.data.hasDaaS);
							}
							var template = Handlebars.compile(Tpl.getThirdSecView);
						}

						_self.data("level",_viewLevel);
		        		Page.findName("archiView").html(template(json.data));
		        		Page.find(".mxgif-third-item").on('click', function() {
							$(this).fadeOut(function(){
								self.setSidebarHeight();
							});
						});
						Page.find(".mxgif-item-title").on('click', function() {
							$(this).parent().fadeOut(function(){
								self.setSidebarHeight();
							});
						});
		        		window.XMS.msgbox.hide();

		        		// 计算垂直高度值
		        		self.setSidebarHeight();

		        		// 处理跨层
		        		if(_viewLevel=="2"){
							if(json.data.isCross=="1"){
		        				self.setCrossContent(json);
				        	}
						}
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}
	  			});
			});
		},
		setSidebarHeight:function(){
			// 循环计算出所有节点的高度值，用于垂直居中对齐
			Page.find('.mxgif-sidebar').each(function(){
				var _thiz = $(this);
				_thiz.height(0);
				var _pHeight = _thiz.parent().height();
				_thiz.height(_pHeight);
			});
		},
		setActiveSidebarHeight:function(obj){
			// 循环计算出所有节点的高度值，用于垂直居中对齐
			var $sidebar = $(obj).parents(".mxgif-lists").prev();
			var containerHeight = $sidebar.parent().height();
			$sidebar.height(0);
			//$sidebar.height(containerHeight);
		},
		setCrossContent:function(json){
			var contentArray = json.data.content;
    		//处理跨层区域
    		if(json.data.crossContent.length>0){

    			// 设定二级浮层容器的高度值
    			Page.findName("crossContent").height(Page.find(".mxgif-wrapper").outerHeight());

    			var crossContentArray = json.data.crossContent,
    				positionLeft = 0;

    			// 循环计算得出跨层向上的元素
    			for (x in crossContentArray){
    				var startCrossId=crossContentArray[x].startCrossId,
    					endCrossId=crossContentArray[x].endCrossId,
    					name=crossContentArray[x].name;
    					positionTop = 0,
    					PositionTopArray = [];

    				for(i in contentArray){
    					var thiz = contentArray[i];
    					if(thiz["isNodeName"]=="1"){
    						if(thiz["item"].length>0){
    							if(thiz["name"]!="PaaS"){
    								if(thiz["id"]!=startCrossId){
	        							PositionTopArray.push(thiz["id"]);
	        						}else{
	        							break;
	        						}
    							}
				                var thizItem = thiz["item"];
				                for(h in thizItem){
				                	var thizChild = thizItem[h];
				                	if(thizChild["isNodeName"]=="1"){
				                		if(thizChild["id"]!=startCrossId){
		        							PositionTopArray.push(thizChild["id"]);
		        						}else{
		        							break;
		        						}
				                	}
				                }
				            }else{
				            	if(thiz["id"]!=startCrossId){
        							PositionTopArray.push(thiz["id"]);
        						}else{
        							break;
        						}
				            }
    					}
    				}

    				// 通过元素计算出向上定位的总高度值
    				for(y in PositionTopArray){
    					positionTop+=Page.find("#cross_"+PositionTopArray[y]).outerHeight();
    				}

    				// 增加高度值无法计算的两个层之间的间距
    				if(PositionTopArray.length==1){
    					positionTop+=15;
    				}else if(PositionTopArray.length==2){
						positionTop+=25;
    				}else if(PositionTopArray.length==3){
						positionTop+=35;
    				}

    				// 计算元素的自身高度值，并赋值height,left及top
    				var startHeight = Page.find("#cross_"+startCrossId).outerHeight();
    				var endHeight = Page.find("#cross_"+endCrossId).outerHeight();
    				var height = startHeight+endHeight;
    				var html = $("<div><p style='margin-top:10px;line-height:16px;'>"+name+"</p></div>");
    				html.css({ "height": height, "left": positionLeft, "top": positionTop});
    				Page.findName("crossContent").append(html);
    				positionLeft+=50;
    			}

    			// 根据左侧宽度，计算右侧的宽度值
    			Page.findName("noCrossContent").each(function(){
    				if($(this).hasClass("width-auto")){
    					$(this).css({"padding-left":positionLeft});
    				}
    			});

    		}
		}
	};
	module.exports = init;
});
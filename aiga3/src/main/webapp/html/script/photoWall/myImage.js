define(function(require, exports, module) {

	// 通用工具模块
	var Utils = require("global/utils.js");

	// 路径重命名
	var pathAlias = "autoManage/dataBackups/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('myImageView');
	//查询我的照片墙
	srvMap.add("findMyImages", pathAlias + "dataMaintain.json", "archi/image/findMyImages");
	//查询公共的照片墙
	srvMap.add("findCommonImages", pathAlias + "dataMaintain.json", "archi/image/findCommonImages");
	//我喜欢后isShared改变共享状态
	srvMap.add("updateIsSharedState", pathAlias + "dataMaintain.json", "archi/image/updateIsSharedState");

	var cache = {
		datas : ""	
	};
    // 模板对象
	var Tpl = {
		getImageList: require('tpl/photoWall/image.tpl')
	};

	var Data = {
		
	};

	var Query = {
		init: function() {
			this.refreshStyle();
			this.waterFall();
			this.uploadImage();
			this.imageShow();
			this.ilikeButton();
			},
		refreshStyle: function(){
			var self = this;
			$(document).ready(function(){	
				$(".zoom,.ilike").hide();
			
				$(".zoom").each(function(){//遍历所有对象
				var src=$(this).siblings("img").attr("src");
					$(this).attr({href:src});
				});
				
				$("#nav li").click(function(){
					$("#nav a").removeClass("hover");
					$(this).find("a").addClass("hover");
				});
				
				$("#waterfall li").mouseover(function(){
					$(this).addClass("hover");
					$(this).find(".zoom,.ilike").show();
				});
				
				$("#waterfall li").mouseout(function(){
					$(this).removeClass("hover");
					$(this).find(".zoom,.ilike").hide();
				});
			});
		},
		waterFall: function(){
		  $(function(){
		    var $waterfall = $('#waterfall');
		    $waterfall.masonry({
		      columnWidth: 230
		    });
		  });
		},
		uploadImage: function(){
		    $("#fileUploadContent").initUpload({
	        "uploadUrl":"http://localhost:8080/aiga3/group/require/uploadImage",//上传文件信息地址
//	        "progressUrl":"#",//获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead: 102516060, contentLength: 102516060, items: 1, percent: 100, startTime: 1489223136317, useTime: 2767}）
	        //"showSummerProgress":false,//总进度条，默认限制
	        //"size":350,//文件大小限制，单位kb,默认不限制
	        "maxFileNumber":1,//文件个数限制，为整数
	        "filelSavePath":"http://localhost:8080/aiga3/group/require/uploadImage",//文件上传地址，后台设置的根目录
	        //"beforeUpload":beforeUploadFun,//在上传前执行的函数
	        //"onUpload":onUploadFun，//在上传后执行的函数
	        //autoCommit:true,//文件是否自动上传
		    "fileType":['png','jpg','docx','doc','PNG','JPG','DOCX','DOC']//文件类型限制，默认不限制，注意写的是文件后缀
		    }),
		    function beforeUploadFun(opt){
		        opt.otherData =[{"name":"你要上传的参数","value":"你要上传的值"}];
		    },
		    function onUploadFun(opt,data){
		        alert(data);
		        uploadTools.uploadError(opt);//显示上传错误
		    }
		},
		imageShow: function(){
			var self = this;
			var _dom = Page.findId('showMyImageForm');
			var _cmd = 'isShared=N&createId=11';
			
			Rose.ajax.postJson(srvMap.get('findMyImages'),_cmd,function(json, status){
				if(status) {
					var template = Handlebars.compile(Tpl.getImageList);
					_dom.find("[name='content']").html(template(json.data));
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}					
			});
		},	
		ilikeButton: function(){
			var self = this;
			var _form = Page.findId('showMyImageForm');
			Utils.setSelectData(_form);
			var _ilikeBtn = _form.find("[name='ilike']");
			_ilikeBtn.unbind('click').bind('click', function() {
				var _cmd = _form.serialize();
				if(_cmd!=null){
					if(_cmd.indexOf('isShared=N')>-1){
						_cmd=_cmd.replace("isShared=N","isShared=Y");
					}
				}
				Rose.ajax.postJson(srvMap.get('updateIsSharedState'),_cmd,function(json, status){
					if(status) {
						window.XMS.msgbox.show('共享成功！', 'success', 2000);
					} else {
						XMS.msgbox.show(json.retMessage, 'error', 2000);
					}					
				});
			});
		},	
		
	};
	module.exports = Query;
});
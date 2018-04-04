define(function(require, exports, module) {
    // 通用工具模块
    var Utils = require("global/utils.js");
    var Sidebar = require('global/sidebar.js');
    // 初始化页面ID，易于拷贝，不需要带'#'
    var Page = Utils.initPage('workbench');

    srvMap.add("getOwnHomeInfo", "", "sys/home/taskInfo");

    var Query = {
        init: function() {
            this._render();
        },
        _render: function() {
			this.getOwnHomeInfo();
			this._helper_reg();
        },

        getOwnHomeInfo: function() { // 获取待办任务信息
            var self = this;         
	        Rose.ajax.postJson(srvMap.get('getOwnHomeInfo'), '', function(json, status) {
	            if (status) {
	            	var template = Handlebars.compile(Page.findTpl('newOwnHomeInfo'));
	            	var data = json.data;
	                Page.findId('getOwnHomeInfo').html(template(data));
	                Page.find("li").off('click').on('click',function() {
	                	var number = $(this).find('[class="bill-apply-num"]:first-child').val();
	                	var name = $(this).find('[class~="bill-apply-name"]').attr("name");
	                	if(number < 1 || typeof(name) == 'undefined') {
	                		return
	                	}
	                	if(name == 'applyFirst' || name == 'applySecond' || name == 'applyThird') {
	                		var ext = name == 'applyFirst'? 1: name== 'applySecond' ? 2: name == 'applyThird' ? 3:0;
	                        Sidebar.creatTab({
	                            id: '118',
	                            name: '架构分级认定',
	                            href: 'view/sysArchiBaselineManage/archiGradingManage/archiGradingIdentified.html',
	                            cmd: 'state=申请&ext1='+ext+'&applyUser='+data.userCode
	                        });
	                	} else if(name == 'noPassSystem') {
	                		//驳回三级申请单处理
	                		Sidebar.creatTab({
	                            id: '26',
	                            name: '驳回申请单处理',
	                            href: 'view/personalBaseApply/personalBaseApply.html',
	                            cmd: 'ext1=3&state=审批未通过&applyUser='+data.userCode
	                        });
	                	} else if ( name == 'dealFirst'|| name == 'dealSecond'|| name == 'dealThird') {
	                		var ext = name == 'dealFirst'? 1: name== 'dealSecond' ? 2: name == 'dealThird' ? 3:0;
	                        Sidebar.creatTab({
	                            id: '118',
	                            name: '架构分级认定',
	                            href: 'view/sysArchiBaselineManage/archiGradingManage/archiGradingIdentified.html',
	                            cmd: 'state=申请&ext1='+ext
	                        });
	                	} else if (name == 'applyIndentyQues' || name == 'applyResolveQues' || name == 'applyCloseQues') {
	                		var indentyQues = 'sysVersion=待确认&reportor='+data.userCode;
	                		var resolveQues = 'sysVersion=已确认&state=daijiejue&reportor='+data.userCode;
	                		var closeQues = 'sysVersion=已确认&state=jiejuezhong&reportor='+data.userCode;
	                        Sidebar.creatTab({
	                            id: '136',
	                            name: '架构问题维护',
	                            href: 'view/archiQuesManage/questionRending.html',
	                            cmd: name == 'applyIndentyQues'? indentyQues: name== 'applyResolveQues' ? resolveQues: name == 'applyCloseQues' ? closeQues:''
	                        });
	                	} else if (name == 'dealIndentyQues' || name == 'dealResolveQues' || name == 'dealCloseQues') {
	                		var indentyQues = 'sysVersion=待确认&identifiedName='+data.userCode;
	                		var resolveQues = 'sysVersion=已确认&state=未解决&solvedName='+data.userCode;
	                		var closeQues = 'sysVersion=已确认&state=解决中&solvedName='+data.userCode;
	                        Sidebar.creatTab({
	                            id: '135',
	                            name: '架构问题查询',
	                            href: 'view/archiQuesManage/quesRending.html',
	                            cmd: name == 'dealIndentyQues'? indentyQues: name== 'dealResolveQues' ? resolveQues: name == 'dealCloseQues' ? closeQues:''
	                        });
	                	} else {
	    		        	XMS.msgbox.show("没有配置跳转路径", 'error', 1000);
	                	}
	                });
	            }
	        });
        },
        _helper_reg:function() {
			Handlebars.registerHelper({
				'helloword': function() {
					var hour = new Date().getHours();
					var helloword = '';
					if(hour < 6){helloword="凌晨好！"} 
					else if (hour < 9){helloword="凌晨好！";} 
					else if (hour < 12){helloword="上午好！";} 
					else if (hour < 14){helloword="中午好！";} 
					else if (hour < 17){helloword="下午好！";} 
					else if (hour < 19){helloword="傍晚好！";} 
					else if (hour < 22){helloword="晚上好！";} 
					else {helloword="夜里好！";} 
					return helloword;
				},
				'presentDate' : function() {//格式化时间  
			        var format = 'yyyy年MM月dd日';  
			        if(arguments.length > 1){  
			            format = arguments[0];  
			        }   
			        return Rose.date.getDatetime(format);  
			    },
        		'computeAdd': function() {  
			        var big = 0;  
			        try{  
			            var len = arguments.length - 1;  
			            for(var i = 0; i < len; i++){  
			                if(arguments[i]){  
			                    big = eval(big +"+"+ arguments[i]);  
			                }  
			            }  
			        }catch(e){  
			            throw new Error('Handlerbars Helper "computeAdd" can not deal with wrong expression:'+arguments);  
			        }  
			        return big;  
			    }  
        	});
        }
    }
    module.exports = Query;
});
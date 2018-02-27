define(function(require,exports,module){
	require("macarons");
	// 通用工具模块
	var Utils = require("global/utils.js");
	//		
	var Tab = require('global/sidebar.js');

	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('welcome');

    // 指标展示信息
    srvMap.add("getWelcomeKpiList", "welcome/getWelcomeKpiList.json" , "sys/home/kpiList");
    // 饼图
    srvMap.add("getWelcomePie", "welcome/getWelcomePie.json", "webservice/archiThird/welcomePie");
    // 获取问题
    srvMap.add("getQueryInfo", "welcome/getQueryInfo.json", "archi/question/queryInfo");
    // 获取上线时间
    srvMap.add("onlineTimeFind", "", "webservice/archiOnline/timeFind");
    // 添加上线时间
    srvMap.add("onlineTimeSet", "", "webservice/archiOnline/timeSet");
    // 工作台查询
    srvMap.add("getOwnHomeInfo", "", "sys/home/taskInfo");

    var Data = {
        planDate:null // 获取日期日期
    };
    var cache = {
    	anifun : '',
    	clickTime : 0
    };
	var Query = {
		init: function(){
			Data.planDate = Rose.date.dateTimeWrapper('yyyy-MM-dd');
			Page.findName("showTime").html(Rose.date.dateTimeWrapper('yyyy年MM月dd日'));
			this._render();			
		},
		_render: function() {
			this._getWelcomeKpiList();
			this._getWelcomePie();//首页饼图初始化
			this._questionShow();
			this._getWelcomePlanDate();
			this._load_workbench_size();
		},
		_load_workbench_size: function() {
	        Rose.ajax.postJson(srvMap.get('getOwnHomeInfo'), '', function(json, status) {
	        	if (status) {
	        		var workBenchSize = 0;
	        		var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/ 
	        		$.each(json.data, function(key, value){        			
	        			if (typeof(value) != 'undefined' && re.test(value)) {
	        				workBenchSize += parseInt(value);
	        			}
	        		});
	                $("span[name='getWorkBenchShowSize']").text(workBenchSize);
	        	} else {	
    				XMS.msgbox.show(json.retMessage, 'error', 2000);
	        	}
	        });
			//设置工作台显示数字
		},		
		_getWelcomePlanDate: function() {
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 初始化日历
            var specialDatesInit = function(specialDates) {
                WdatePicker({
                	skin: 'welcome',
                    eCont: 'JS_getWelcomePlanDate',
                    specialDates: specialDates,
                    onpicked: function(dp) {
                    	cache.clickTime++;
						setTimeout(function() {
							cache.clickTime--;
						}, 200);
						if(cache.clickTime>1) {
				            Rose.ajax.postJson(srvMap.get('onlineTimeSet'), 'onlineTime='+Rose.date.dateTime2str(new Date(dp.cal.getDateStr()), 'yyyy/MM/dd'), function(json, status) {
				                if (status) {
				                	if(json.data.ifSynchroData == true) {
				                		XMS.msgbox.show(json.data.outputMessage, 'info', 1500);
				                	} else {
					                    XMS.msgbox.show(json.data.outputMessage, 'success', 1500);
					                    specialDatesInit(json.data.onlineDate);		
				                	}	                   
				                } else {
				    				XMS.msgbox.show(json.retMessage, 'error', 2000);
				                }
				            });
						} else {
	                        //alert('你选择的日期是:' + dp.cal.getDateStr())
	                        Data.planDate = dp.cal.getDateStr();
	                        Page.findName("showTime").html(Rose.date.dateTime2str(new Date(dp.cal.getDateStr()), 'yyyy年MM月dd日'));
						}
//                        //alert('你选择的日期是:' + dp.cal.getDateStr())
//                        Data.planDate = dp.cal.getDateStr();
//                        Page.findName("showTime").html(Rose.date.dateTime2str(new Date(dp.cal.getDateStr()), 'yyyy年MM月dd日'));			
                    }
                });
            };
            //var cmd = "year="+Data.planDateYear+"&month="+Data.planDateMonth;
            Rose.ajax.getJson(srvMap.get('onlineTimeFind'), '', function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    specialDatesInit(json.data);	
                } else {
                	specialDatesInit('');
    				XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });
        },
		_questionShow: function() {
			var self = this;
			Rose.ajax.postJson(srvMap.get("getQueryInfo"), 'pageSize=100&sysVersion=已确认', function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					var docthis = Page.find('ul[name="wordGull"]');
                    var quesList = json.data.content;
                    var _html = '';
                    var dataLength = 0;
                    for (var i in quesList) {
                        var _json = quesList[i];
                        if(_json.state == '已解决') {
                        	continue;
                        }
                        dataLength++;
                        _html += '<li data-quesid="'+_json.quesId+'" style="margin-top: 0px;"><a title="' + _json.occurEnvironment +'" class="ques-word" href="javascript:void(0)">'
                        + _json.occurEnvironment +'</a><span class="ques-state">' + _json.state + '</span><span class="ques-time">' + _json.createDate + '</span></li>';
                    }
                    if(dataLength != 0) {
                        while(dataLength <9) {
                        	dataLength += dataLength;
                        	_html+=_html;
                        }
                    }
                    docthis.html(_html);
                    //click事件绑定
                    Page.find("ul[name='wordGull']").delegate('li', "click",function(){
                    	var quesId = $(this).attr("data-quesid");
                    	//问题解决页面
        				var objData = {
        						id : '136',
        						name : '架构问题维护',
        						href : "view/archiQuesManage/questionRending.html",
        	                    cmd : "quesId="+quesId
        				};
                    	Tab.creatTab(objData);
                    	
                    });
					self._wordRoll();
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
  			});
	
		},
		_wordRoll: function(value) {			
			var docthis = Page.find('ul[name="wordGull"]');
			//默认参数
			value=$.extend({
				 "li_h":"30",
				 "time":2000,
				 "movetime":1000
			},value);
			
			//向上滑动动画
			function autoani(){
				var newDom = docthis.find("li:first");
				newDom.animate({"margin-top":-value.li_h},value.movetime,function(){
					newDom.css("margin-top",0).appendTo(".indexline");
				});
			}
			if(cache.anifun) {
				clearInterval(cache.anifun);
			}
			//自动间隔时间向上滑动
			cache.anifun = setInterval(autoani,value.time);
			
			//悬停时停止滑动，离开时继续执行
			$(docthis).children("li").hover(function(){
				clearInterval(cache.anifun);			//清除自动滑动动画
			},function(){
				cache.anifun = setInterval(autoani,value.time);	//继续执行动画
			});
		},
		
		_getWelcomeKpiList: function() { // 获取指标信息
            var self = this;
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            //var cmd = "planDate="+Data.planDate;
            Rose.ajax.getJson(srvMap.get('getWelcomeKpiList'), '', function(json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    var newDataArray = [];
                    for (x in json.data) {
                        if (json.data[x].isShow == 1) {
                            newDataArray.push(json.data[x]);
                        }
                    }
                    //设置显示数字
                    $("span[name='getWelcomeKpiListShowSize']").text(newDataArray.length);
                    var chunk = function(array, size) {
                        var result = [];
                        for (var x = 0; x < Math.ceil(array.length / size); x++) {
                            var start = x * size;
                            var end = start + size;
                            result.push(array.slice(start, end));
                        }
                        return result;
                    };
                    var template = Handlebars.compile(Page.findTpl('getWelcomeKpiList'));
                    Page.findId('getWelcomeKpiList').html(template(chunk(newDataArray, 4)));
                } else {
                	//指标获取失败
					XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });
        },
		_getWelcomePie: function(){
			var self = this;
			//XMS.msgbox.show('数据加载中，请稍候...', 'loading');
			Rose.ajax.postJson(srvMap.get("getWelcomePie"), '', function(json, status) {
				if(status) {
					window.XMS.msgbox.hide();
					self.getMyEchartsPie(json);
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
  			});
		},
	
		getMyEchartsPie: function(json){//饼图模块
			var myChart = echarts.init(Page.findId('echartsPie')[0],'macarons');
        	option = {
               /* title : {
                    text: '架构分层管理',
                    subtext: '一级域管理',
                    left:'center'
                },*/
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'horizontal',
                    left: 'center',
                    top:'5%',
                    data: ['业务支撑域','管信域','BOMC域','大数据域','安全域','公共域','网络域','地市域','开放域'],
                    width:400
                },
                series : [
                    {
                        name: '一级域',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '50%'],
                        label: {
                            normal: {
                            	formatter:"{b} : {c}",
                                position: 'outside'
                            }
                        },
                        labelLine: {
                            normal: {
                                show: true
                            }
                        },
                        data:[
                            {value:6, name:'业务支撑域'},
                            {value:1, name:'管信域'},
                            {value:3, name:'BOMC域'},
                            {value:2, name:'大数据域'},
                            {value:1, name:'安全域'},
                            {value:7, name:'公共域'},
                            {value:5, name:'网络域'},
                            {value:6, name:'地市域'},
                            {value:3, name:'开放域'}
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
        	if(json && json.data) {
				option.legend.data = json.data.legend;
				option.series[0].data = json.data.series;
			}
			myChart.setOption(option);
			myChart.on('click',function(params){
				console.log(params,params.data.id);
				var objData = {
						id : '116',
						name : '二级系统管理',
						href : "view/sysArchiBaselineManage/archiGradingManage/secondSysManage.html",
	                    cmd : "idFirst=" + params.data.id
				};
            	Tab.creatTab(objData);
            });
			window.onresize = myChart.resize;
			Page.findId('echartsPie').resize(function(){
				myChart.resize(); 			
			});
		}
	};
	
	Handlebars.registerHelper("setSmallTag", function(str) {
        return str.replace("%","<small>%</small>");
    });
    Handlebars.registerHelper("checkIsNotHide", function(index,activeNum) {
        var _index = parseInt(index+1);
        var _activeNum = parseInt(activeNum);
        if (_index == _activeNum) {
            return "";
        }else{
        	return "hide";
        }
    });
    Handlebars.registerHelper("setPercent", function(type) {
        if (type == 2) {
            return "%";
        }
    });
    Handlebars.registerHelper("isActive", function(index) {
        if (index == 0) {
            return "active";
        }
    });
    Handlebars.registerHelper("isChecked", function(type) {
        if (type == 1) {
            return "checked='checked'";
        }
    });
    Handlebars.registerHelper("getTypeClassName", function(type) {
        if (type == 1) {
            return "fa-check bg-blue"; //正处理
        } else if (type == 2) {
            return "fa-check bg-green"; //已处理
        } else if (type == 3) {
            return "fa-check bg-gray"; //未处理
        }
    });
    Handlebars.registerHelper("getTypeTitle", function(type) {
        if (type == 1) {
            return "正处理"; //正处理
        } else if (type == 2) {
            return "已处理"; //已处理
        } else if (type == 3) {
            return "未处理"; //未处理
        } else if (type == 4) {
            return "不需处理"; //未处理
        }
    });
    module.exports = Query;
});


define(function(require,exports,module){
	require("macarons");
	// 通用工具模块
	var Utils = require("global/utils.js");
	//		
	var Tab = require('global/sidebar.js');
	var pathAlias = "script/";
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('welcome');

    // 指标展示信息
    srvMap.add("getWelcomeKpiList", "welcome/getWelcomeKpiList.json" , "sys/home/kpiList");
    // 饼图
    srvMap.add("getWelcomePie", "welcome/getWelcomePie.json", "webservice/archiThird/welcomePie");
    // 获取问题
    srvMap.add("getQueryInfo", "welcome/getQueryInfo.json", "archi/question/queryInfo");
    // 获取上线时间
    srvMap.add("onlineTimeFind", '', "webservice/archiOnline/timeFind");
    // 添加上线时间
    srvMap.add("onlineTimeSet", '', "webservice/archiOnline/timeSet");
    // 工作台查询
    srvMap.add("getOwnHomeInfo", '', "sys/home/taskInfo");
    // 菜单记录查询
	srvMap.add("getMenuTopData", '', "webservice/menuRecord/topData");
	// 查询连接池配置
	srvMap.add("getPoolText", '', "webservice/configure/getText");
	// 系统变更信息查询
	srvMap.add("getArchAigaList", '', "webservice/archiGrading/sysMonthReport");	
	// 菜单权限校验
	srvMap.add("menuFuncCheck", '', "webservice/menuCheck/grant");
	// 显示查询信息表
	srvMap.add("querybylistreport", '', "webservice/csferrcode/querybylistreport");

    var Data = {
        planDate:null // 获取日期日期
    };
    var cache = {
    	anifun : '',
    	clickTime : 0,
    	hotMenu : []
    };
	var Query = {
		init: function(){
			Data.planDate = Rose.date.dateTimeWrapper('yyyy-MM-dd');
			Page.findName("showTime").html(Rose.date.dateTimeWrapper('yyyy年MM月dd日'));
			this._render();			
		},
		_render: function() {
			//渲染工作台数量
			this._load_workbench_size(); 
			//helper
			this._handlebar_help_register(); 
			//模块一数据渲染
			this._load_first_module();
			//模块二加载
			this._load_sec_module();
			//模块三加载
			this._load_trd_module();
		},
		
		_load_first_module: function() {
			//首页指标
			this._getWelcomeKpiList();
			//连接池配置加载
			this._connection_pool();
			//错误码
			this._error_code();
			//基线数据查询
			this._base_system();
		},
		
		_load_sec_module: function() {
			//热门菜单
			this._hot_function();
			//首页饼图初始化
			this._getWelcomePie();    
			//日历
			this._getWelcomePlanDate();
		},
		
		_load_trd_module:function() {
			//架构视图设置
			this._archi_view();
			//巡检问题
			this._question_show();   
		},
		
		//巡检问题
		_question_show: function() {
			var self = this;
			var pagenation = Page.find('[name="pagination"]');
			Utils.getServerPage(srvMap.get("getQueryInfo"), 'sysVersion=已确认', function(json, status) {
				var quesShowDom = Page.findId('quesShow');
				var template = Handlebars.compile(Page.findTpl('questionShow'));
				quesShowDom.html(template(json.data.content));
        		Utils.eventTrClickCallback(Page.findId('quesTable'),function(isChecked, _input) {
                	var quesId = _input.val();
                	//问题解决页面
    				var objData = {
						id : '136',
						name : '架构问题维护',
						href : "view/archiQuesManage/questionRending.html",
	                    cmd : "quesId="+quesId
    				};
    				self._newpage_open(objData);    
        		});
			},pagenation);
		},
		
		//架构视图
		_archi_view:function() {
			var self = this;
			Page.findId('archView').off('click').on('click',function(){
				var obj = {
					id:	'112',
					name: '系统架构分级视图展示',
					href: "view/sysArchiBaselineManage/archiGradingView.html",
					cmd: ''
				};
				self._newpage_open(obj);
			});
		},
		
		//三级系统管理模块加载
		_base_system: function() {
			var self = this;
			//事件绑定
			Page.find("[name='sysButton']").off('click').on('click',function() {
				var obj= {
					id : '114',
					name : '三级系统管理',
					href : "view/sysArchiBaselineManage/archiGradingManage/thirdSysManage.html",
                    cmd : 'type=apply'
				};
				self._newpage_open(obj);
			});
			//调用服务
			var _cmd = "applyTime="+Rose.date.dateTime2str(new Date(),"yyyyMM");
			Rose.ajax.postJson(srvMap.get('getArchAigaList'),_cmd,function(json, status){
				var _monthReportNowData = Page.findId('monthReportNowData');
				if(status) {
					var templateA = Handlebars.compile(Page.findTpl('baseDataChangeTempA'));
					_monthReportNowData.html(templateA(json.data.sysMonthApplyReport));
					//末尾加句号
					var _punctuation = _monthReportNowData.find("[name='punctuation']").last();
					if(_punctuation.text() == '、'){
						_punctuation.text('。');
					}
					//判空校验
					var _spanA = _monthReportNowData.find("[name='span']").length;
					if(_spanA == 0){
					var templateC = Handlebars.compile(Page.findTpl('baseDataChangeTempC'));
					_monthReportNowData.html(templateC(json.data.sysMonthApplyReport));
				} else {
					_monthReportNowData.html(json.retMessage);
				}
			}});
		},
		//连接池配置模块加载
		_connection_pool: function() {
			var self = this;
			//设置默认参数
			var today = Rose.date.dateTime2str(new Date(),"yyyy-MM-dd");
			//事件绑定
			Page.find("[name='poolButton']").off('click').on('click',function() {
				var obj= {
					id : '425',
					name : '业务系统连接池配置总览',
					href : "view/connectionPoolConfiguration/connectionPoolConfiguration.html",
                    cmd : ''
				};
				self._newpage_open(obj);
			});
			Rose.ajax.postJson(srvMap.get('getPoolText'),"insertTime="+today,function(jsontxt, status){
				var poolDom = Page.findId('connectionPoolText');
				if(status) {				
					var templateText = Handlebars.compile(Page.findTpl('connectionPoolTempText'));
					poolDom.html(templateText(jsontxt.data[0]));
        			//判空校验
					var _textA = poolDom.find("[name='textShow']").length;
					var _textB = poolDom.find("[name='textShowTwo']");
					if(_textA != 0){
						_textB.css ('display','none');
					}else{
						_textB.css ('display','block');
					}
        			//打印查询月份
					poolDom.find("[name='timeShow']").text(today);
					poolDom.find("[name='timeShowTwo']").text(today);
				} else {
					poolDom.html(jsontxt.retMessage);					
				}
			});
		},
		
		//错误码
		_error_code:function() {
			var self = this;
			//设置默认参数
			var yesterday = Rose.date.dateTime2str(new Date(),"yyyyMMdd");
			Page.find("[name='errCodeButton']").off('click').on('click',function() {
				var obj= {
					id : '463',
					name : '各中心CSF错误码规范',
					href : "view/errorCode/centerCsfErrorCode.html",
                    cmd : ''
				};
				self._newpage_open(obj);
			});
			// 设置服务器端分页
			var _topcmd={
				insertTime:yesterday
			};
			Rose.ajax.postJson(srvMap.get('querybylistreport'),_topcmd,function(json,status){
				var poolDom = Page.findTpl('errorCodeText');
			if(status){
				var docthis = Page.find('ul[name="wordGull"]');
                var errCodeList = json.data;
                var _html = '';
                var dataLength = 0;
                for (var i in errCodeList) {
                    var _json = errCodeList[i];
                    dataLength++;
                    _html += '<li style="margin-top: 0px;"><a title="' + _json.centerName +'" class="errcode-center">'
                    + _json.centerName +'</a>配置覆盖率为<span class="errcode-percentage">' + _json.errcodeCoverRate + '</span>%;规范率为<span class="errcode-standard">' + _json.errcodeSpecRate + '</span>%;</li>';
                }
                docthis.html(_html);
                self._wordRoll();
                Page.find("[name='errcode-title']").html("CSF错误码配置规范("+yesterday+"采集)");
				}else{
					poolDom.html(json.retMessage);
				}
			});
		},
		//错误码滚动
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
                clearInterval(cache.anifun);            //清除自动滑动动画
            },function(){
                cache.anifun = setInterval(autoani,value.time);    //继续执行动画
            });
        },
		//热门菜单
		_hot_function: function() {
			var self = this;
			//获取模板
			var template = Handlebars.compile(Page.findTpl('getWelcomeHotfunction'));	
			//热门菜单查询
			Rose.ajax.postJson(srvMap.get("getMenuTopData"),"type=menuNameMonth",function(json, state){
				cache.hotMenu = json.data;
				var hotmenu = Page.findId("functionRecordMenu");
				hotmenu.html(template(json.data));
				//热门事件绑定
				hotmenu.find("li").off('click').on('click',function() {
					var index = $(this).find('i').text();
					var selectValue = cache.hotMenu[--index];
					var obj= {
						id : selectValue.menuCode,
						name : selectValue.menuName,
						href : selectValue.menuUrl,
	                    cmd : ''
					};
					self._newpage_open(obj);
				});
			});
		},
		//更新工作台提示数
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
                    
                    //设置点击事件
                    Page.findId('getWelcomeKpiList').find('.top-icon-declick').off('click').on('click',function(){
                    	var nodeId = $(this).attr("data-nodeid");
                    	if(nodeId) {
                        	//查找页面
    	    				var objData = {
    	    						id : '191',
    	    						name : '八大军规指标树形展示',
    	    						href : "view/archiQuesManage/quesIndexR.html",
    	    	                    cmd : "indexId="+nodeId
    	    				};
    	                	Tab.creatTab(objData);            		
                    	}
                    });
                } else {
                   	//指标获取失败
					XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });
        },
        //菜单open方法，便于权限校验
        _newpage_open: function(obj){           	
            //权限校验
		    Rose.ajax.getJson(srvMap.get('menuFuncCheck'), 'menuName='+obj.href, function(json, status) {
		        if (status) {
		        	if(obj.name == '工作台' || json.data) {
						var objData = {
								id : obj.id,
								name : obj.name,
								href : obj.href,
			                    cmd : obj.cmd
						};
			        	Tab.creatTab(objData);
		        	} else {
		        		XMS.msgbox.show('权限不足,如需使用请联系管理员赋权', 'error', 2000);
		        	}          
		        } else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
		        }
		    });
        	//提炼成公共方法,方便以后拓展

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
                title : {
                    text: '各一级域系统分布情况',
                    left:'left'
                },

                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    data: ['业务支撑域','管信域','BOMC域','大数据域','安全域','公共域','网络域','地市域','开放域'],
                    show:false
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
		},
		//helper注册
		_handlebar_help_register: function() {
			Handlebars.registerHelper("nextGroupTitle",function(index) {
				var num = index+1;
				return num;
			});
			Handlebars.registerHelper("iconContrll",function(index) {
				if(index==0) {
					return "toplist-primicon0";
				} else if(index==1) {
					return "toplist-primicon1";
				}else if(index==2) {
					return "toplist-primicon2";
				}else {
					return '';
				}
			});
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
		    Handlebars.registerHelper("quesStateColor", function(state) {
		        if (state == '解决中') {
		            return "bg-yellow"; 
		        } else if (state == '待立项规划') {
		            return "bg-teal"; 
		        } else if (state == '任务单跟踪') {
		            return "bg-green"; 
		        } else if (state == '需求单跟踪') {
		            return "bg-light-blue"; 
		        } else if (state == "变更单跟踪") {
		        	return "bg-lime"; 
		        }
		    });
		}
	};


    module.exports = Query;
});


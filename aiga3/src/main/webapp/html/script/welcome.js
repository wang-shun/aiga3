define(function(require,exports,module){
	// 通用工具模块
	var Utils = require("global/utils.js");
	// 初始化页面ID，易于拷贝，不需要带'#'
	var Page = Utils.initPage('welcome');

    // 工作台
    srvMap.add("getWelcomeKpiList", "welcome/getWelcomeKpiList.json", "sys/home/kpiList");
    // 饼图
    srvMap.add("getWelcomePie", "welcome/getWelcomePie.json", "archi/third/welcomePie");

    var Data = {
        planDate:null // 获取日期日期
    };

	var Query = {
		init: function(){
			Data.planDate = Rose.date.dateTimeWrapper('yyyy-MM-dd');
			Page.findName("showTime").html(Rose.date.dateTimeWrapper('yyyy年MM月dd日'));
			this._render();
			
		},
		_render: function() {
			this.getWelcomeKpiList();
			this._getWelcomePie();//首页饼图初始化
			this._start('');
		},
		_start: function(value) {			
			var docthis = Page.find('[name="wordGull"]');
			//默认参数
			value=$.extend({
				 "li_h":"30",
				 "time":2000,
				 "movetime":1000
			},value);
			
			//向上滑动动画
			function autoani(){
				$("li:first",docthis).animate({"margin-top":-value.li_h},value.movetime,function(){
					$(this).css("margin-top",0).appendTo(".line");
				});
			}
			
			//自动间隔时间向上滑动
			var anifun = setInterval(autoani,value.time);
			
			//悬停时停止滑动，离开时继续执行
			$(docthis).children("li").hover(function(){
				clearInterval(anifun);			//清除自动滑动动画
			},function(){
				anifun = setInterval(autoani,value.time);	//继续执行动画
			});
		},
		
		getWelcomeKpiList: function() { // 获取工作台信息
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
			var myChart = echarts.init(document.getElementById('echartsPie')); 
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
	                                    width:400,
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
	                                                        position: 'outside',
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
	                                            {value:3, name:'开放域'},

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
	            			window.onresize = myChart.resize;
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


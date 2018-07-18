define(function(require, exports, module) {

    //引入公用模块
    require('global/header.js');
    // 通用工具模块
    var Utils = require("global/utils.js");
    var pathAlias = "ArchTaskMonitoring/";
    // 初始化页面ID(和文件名一致)，不需要带'#Page_'
    var Page = Utils.initPage('taskMonitoring');

    //第一页
    //一周的任务成功情况
    srvMap.add("getTaskMonitoringList", pathAlias + "getList.json", "arch/taskMonitoring/queryByCondition");
    //各个进程一天不同时间段的任务总数
    srvMap.add("getTaskNumCountList", pathAlias + "getList.json", "arch/taskNumCount/queryByTime");
    //一天的任务运行频率分布情况
    srvMap.add("getTaskFrequencyList", pathAlias + "getList.json", "arch/getTaskFrequencyList/queryByFrequency");
    //一天内任务在时长内运行次数
    srvMap.add("getTaskTimesList", pathAlias + "getList.json", "arch/TaskTimes/queryByTimes");


    //第二页
    //系统表格信息查询,监控信息表格一
    srvMap.add("getTableList", pathAlias + "getList.json", "arch/TableList/findTableList");
    // // //系统表格信息查询,监控信息表格二
    // srvMap.add("getTableListSecond", pathAlias + "getList.json", "arch/TableListSecond/findTableListSecond");
    // //系统表格信息查询，监控信息表格三
    // srvMap.add("getTableListThird", pathAlias + "getList.json", "arch/TableListThird/findTableListThird");

    //Top10  表格一  一天中任务指定时长内任务运行信息
    srvMap.add("getTopListFirst", pathAlias + "getList.json", "arch/TopListFirst/findTopListFirst");

    var init = {

        init: function () {
            this._render();
        },

        _render: function () {
            //查询
            this._query_event();

            //三个页面实现时间框初始化
            var _form1 = Page.findId('queryDataForm');
            _form1.find('[name="startDate"]').val(Rose.date.dateTime2str(new Date(), "yyyy-MM-dd"));

            var _form2 = Page.findId('queryDataFormTable');
            _form2.find('[name="startDate"]').val(Rose.date.dateTime2str(new Date(), "yyyy-MM-dd"));

            var _form3 = Page.findId('queryDataFormTop');
            _form3.find('[name="startDate"]').val(Rose.date.dateTime2str(new Date(), "yyyy-MM-dd"));
        },

        // 查询视图数据(第一个tab页)
        _getGridList: function (cmd) {
            var self = this;
            var _cmd = '';
            if (cmd) {
                _cmd = cmd;
            }

            _cmd = _cmd.replace(/-/g, "/");
            var _form = Page.findId('queryDataForm');
            var lastTime = _form.find('[name="startDate"]').val();
            var dateArr = [];
            for (var i = 1; i < 7; i++) {
                dateArr[7 - i] = format(new Date(lastTime).getTime() - 1000 * 60 * 60 * 24 * i);
            }
            function format(timestamp) {
                var time = new Date(timestamp);
                var y = time.getFullYear();
                var m = time.getMonth() + 1;
                var d = time.getDate();
                return y + '-' + m + '-' + d;
            }

            var timeArr = [dateArr[1], dateArr[2], dateArr[3], dateArr[4], dateArr[5], dateArr[6], lastTime];

            XMS.msgbox.show('数据加载中，请稍候...', 'loading');

            //第一个box图标展示一周的任务成功情况
            Rose.ajax.postJson(srvMap.get('getTaskMonitoringList'), _cmd, function (json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    console.log("进入第一个box");
                    var check = [];
                    var session = [];
                    var report = [];
                    var collect = [];
                    var successRate = [];

                    for (var i = 0; i < json.data.length; i++) {
                        check.push(json.data[i].checkTotal);
                        session.push(json.data[i].sessionTotal);
                        report.push(json.data[i].reportTotal);
                        collect.push(json.data[i].collectTotal);
                        successRate.push(json.data[i].successRate);
                    }
                    //折线图展示失败成功率
                    var myChart = echarts.init(document.getElementById('taskSumAndSuccessful'));
                    var maxTaskNum = getMaxTaskNum();
                    option = {
                        title: {
                            text: '周任务运行分布图',
                            textStyle: {
                                color: 'black',
                                fontStyle: 'normal',
                                fontWeight: 'normal',
                                fontFamily: 'sans-serif',
                                fontSize: 18,
                            }
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross',
                                crossStyle: {
                                    color: '#999'
                                }
                            }
                        },
                        toolbox: {
                            feature: {
                                // dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        legend: {
                            data: ['check', 'session', 'report', 'collect']
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: timeArr,
                                axisPointer: {
                                    type: 'shadow'
                                }
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: '任务数',
                                min: 0,
                                max: maxTaskNum,
                                interval: maxTaskNum / 10,
                                axisLabel: {
                                    formatter: '{value}'
                                }
                            },
                            {
                                type: 'value',
                                name: '成功率',
                                min: 0,
                                max: 100,
                                interval: 10,
                                axisLabel: {
                                    formatter: '{value} %'
                                }
                            }
                        ],
                        series: [
                            {
                                name: 'check',
                                type: 'bar',
                                data: check,
                            },
                            {
                                name: 'session',
                                type: 'bar',
                                data: session,
                            },
                            {
                                name: 'report',
                                type: 'bar',
                                data: report,
                            },
                            {
                                name: 'collect',
                                type: 'bar',
                                data: collect,
                            },
                            {
                                name: 'successRate',
                                type: 'line',
                                yAxisIndex: 1,
                                data: successRate,
                            }
                        ]
                    };

                    //为了让柱状图数据不超过图高，需要知道任务数的最大值，这里返回一个柱状图内任务数最大的一个值，并且%100==0
                    function getMaxTaskNum() {
                        var maxvalue = 10;
                        var maxArr = [];
                        maxArr[0] = getArrMax(check);
                        maxArr[1] = getArrMax(session);
                        maxArr[2] = getArrMax(report);
                        maxArr[3] = getArrMax(collect);
                        for (var i = 0; i < maxArr.length; i++) {
                            if (maxArr[i] > maxvalue)
                                maxvalue = maxArr[i];
                        }
                        //maxvalue加上num之后%100==0
                        if (maxvalue % 100 != 0) {
                            var remainder = maxvalue % 100;
                            var num = 100 - remainder;
                            maxvalue += num;
                        }
                        return maxvalue;
                    }

                    function getArrMax(array) {
                        var max = 0;
                        for (var i = 0; i < array.length; i++) {
                            if (array[i] > max) {
                                max = array[i];
                            }
                        }
                        return max;
                    }

                    myChart.setOption(option);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });

            //第二个box图标展示各个进程一天不同时间段的任务总数
            Rose.ajax.postJson(srvMap.get('getTaskNumCountList'), _cmd, function (json, status) {
                if (status) {
                    console.log("进入第二个box");
                    window.XMS.msgbox.hide();
                    var startTime = [];
                    var checkTotal = [];
                    var sessionTotal = [];
                    var reportTotal = [];
                    var collectTotal = [];
                    var taskTotal = [];

                    for (var i = 0; i < json.data.length; i++) {
                        startTime.push(json.data[i].startTime);
                        checkTotal.push(json.data[i].checkTotal);
                        sessionTotal.push(json.data[i].sessionTotal);
                        reportTotal.push(json.data[i].reportTotal);
                        collectTotal.push(json.data[i].collectTotal);
                        taskTotal.push(json.data[i].taskTotal);
                    }
                    var myChart = echarts.init(document.getElementById('taskRuntimeByTime'));
                    option = {
                        title: {
                            text: '各时段任务总数图',
                            textStyle: {
                                color: 'black',
                                fontStyle: 'normal',
                                fontWeight: 'normal',
                                fontFamily: 'sans-serif',
                                fontSize: 18,
                            }
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['check', 'session', 'report', 'collect', 'taskTotal']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataZoom: {
                                    yAxisIndex: 'none'
                                },
                                // dataView: {readOnly: false},
                                magicType: {type: ['line', 'bar']},
                                restore: {},
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            name:'时间',
                            // boundaryGap: false,
                            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]
                        },
                        yAxis: {
                            name: '任务数',
                            type: 'value',
                            axisLabel: {
                                formatter: '{value} '
                            }
                        },
                        series: [
                            {
                                name: 'check',
                                type: 'line',
                                data: checkTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name: 'session',
                                type: 'line',
                                data: sessionTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name: 'report',
                                type: 'line',
                                data: reportTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name: 'collect',
                                type: 'line',
                                data: collectTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name: 'taskTotal',
                                type: 'line',
                                data: taskTotal,
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                        ]
                    };
                    option.xAxis.data = startTime;
                    // option.yAxis.data=
                    myChart.setOption(option);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });

            //第三个box图标展示一天的任务运行频率分布情况
            Rose.ajax.postJson(srvMap.get('getTaskFrequencyList'), _cmd, function (json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    console.log("进入第三个box");
                    var firstTimes = json.data[0].firstTimes;
                    var secondTimes = json.data[0].secondTimes;
                    var thirdTimes = json.data[0].thirdTimes;
                    var fourTimes = json.data[0].fourTimes;
                    var myChart1 = echarts.init(document.getElementById('taskRuntimeByFrequency'));
                    option1 = {
                        title: {
                            text: '一天内任务运行频率分布情况',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: ['[0,5]次', '[6,10]次', '[11-20]次', '21次及以上']
                        },
                        series: [
                            {
                                name: '运行次数',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: firstTimes, name: '[0,5]次'},
                                    {value: secondTimes, name: '[6,10]次'},
                                    {value: thirdTimes, name: '[11-20]次'},
                                    {value: fourTimes, name: '21次及以上'}
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
                    myChart1.setOption(option1);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });

            //第四个box图标展示一天内任务在时长内运行次数
            Rose.ajax.postJson(srvMap.get('getTaskTimesList'), _cmd, function (json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    console.log("进入第四个box");
                    var firstMinutes = json.data[0].firstMinutes;
                    var secondMinutes = json.data[0].secondMinutes;
                    var thirdMinutes = json.data[0].thirdMinutes;
                    var fourMinutes = json.data[0].fourMinutes;
                    var myChart1 = echarts.init(document.getElementById('taskRuntimeByTimes'));
                    option = {
                        title: {
                            text: '一天某时长内任务运行次数',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'right',
                            data: ['[0,5]分钟', '(5,10]分钟', '(10,15]分钟', '15分钟以上']
                        },
                        series: [
                            {
                                name: '运行次数',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: firstMinutes, name: '[0,5]分钟'},
                                    {value: secondMinutes, name: '(5,10]分钟'},
                                    {value: thirdMinutes, name: '(10,15]分钟'},
                                    {value: fourMinutes, name: '15分钟以上'}
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
                    myChart1.setOption(option);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });


        },

        //查询表格数据(第二个tab页)
        _getTableList: function (cmd) {
            console.log("查询表格数据");
            var self = this;
            var _cmd = '';
            if (cmd) {
                var _cmd = cmd;
            }

            //入参规定，将字符串'2018-06-26'转换成'2018/06/26'才能解析
            _cmd = _cmd.replace(/-/g, "/");

            //获得select下拉框的值
            var _form = Page.findId('queryDataFormTable');
            var condition = _form.find('[name="condition"]').val();
            console.log("condition:"+condition);

            //第n个表格内容渲染
            var _dom1 = Page.findId('taskMonitoringListTable');
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            // 设置服务器端分页
            Utils.getServerPage(srvMap.get('getTableList'), _cmd, function (json) {
                window.XMS.msgbox.hide();
                if(condition=="failTaskList"){
                    var tablebtn = _dom1.find("[name='content']");
                    var template = Handlebars.compile(Page.findTpl('getFullTableList'));
                    tablebtn.html(template(json.data));
                }else if(condition=="taskRunningFrequency"){
                    var tablebtn = _dom1.find("[name='content']");
                    var template = Handlebars.compile(Page.findTpl('getFullTableListSecond'));
                    tablebtn.html(template(json.data));
                }else if(condition=="taskRunInTime"){
                    var tablebtn = _dom1.find("[name='content']");
                    var template = Handlebars.compile(Page.findTpl('getFullTableListThird'));
                    tablebtn.html(template(json.data));
                }

            });
        },

        //查询Top数据(第三个tab页)
        _getTopTableList: function (cmd) {
            console.log("查询表格数据");
            var self = this;
            var _cmd = '';
            if (cmd) {
                var _cmd = cmd;
            }

            //入参规定，将字符串'2018-06-26'转换成'2018/06/26'才能解析
            _cmd = _cmd.replace(/-/g, "/");

            //第一个Top内容渲染
            var _dom1 = Page.findId('taskMonitoringListTopFirst');
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Utils.getServerPage(srvMap.get('getTopListFirst'), _cmd, function (json) {
                window.XMS.msgbox.hide();
                var tablebtn = _dom1.find("[name='content']");
                var template = Handlebars.compile(Page.findTpl('getTopTableFirst'));
                tablebtn.html(template(json.data));
            });

        },


        //绑定查询按钮事件
        _query_event: function () {
            var self = this;

            //第一页查询按钮点击事件
            var _form1 = Page.findId('queryDataForm');
            Utils.setSelectData(_form1);
            var _queryBtn1 = _form1.find("[name='query']");
            _queryBtn1.off('click').on('click', function () {
                var cmd = _form1.serialize();
                var startDate = _form1.find("[name='startDate']").val();
                var today = new Date();
                if (new Date(startDate) > today) {
                    XMS.msgbox.show('查询时间不能超过今天！', 'error', 2000);
                    return
                }
                if (startDate == 0) {
                    XMS.msgbox.show('查询时间为空！', 'error', 2000);
                    return
                }

                //三张图提示信息渲染：页面查询按钮点击后才显示提示信息
                //图一
                var str1 = "<p style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">对应表格信息<br>请到<br>表格信息查询页<br>查询内容表一<br></p>";
                document.getElementById("JS_tableFirstHint").innerHTML=str1;
                //图二
                var str2 = "<p style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">例：x轴时间2表示<br>2-3点之间<br></p>";
                document.getElementById("JS_tableSecondHint").innerHTML=str2;
                //图三图四
                var str3 = "<p  style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">对应表格信息<br>请到<br>表格信息查询页<br>查询内容表二表三</p>";
                document.getElementById("JS_tableThirdHint").innerHTML=str3;

                self._getGridList(cmd);
            });

            //第二页查询按钮点击事件
            var _form2 = Page.findId('queryDataFormTable');
            Utils.setSelectData(_form2);
            var _queryBtn2 = _form2.find("[name='query']");
            _queryBtn2.off('click').on('click', function () {
                var cmd = _form2.serialize();
                var startDate = _form2.find("[name='startDate']").val();
                var condition = _form2.find('[name="condition"]').val();
                var today = new Date();
                if (new Date(startDate) > today) {
                    XMS.msgbox.show('查询时间不能超过今天！', 'error', 2000);
                    return
                }
                if (startDate == 0) {
                    XMS.msgbox.show('查询时间为空！', 'error', 2000);
                    return
                }
                if(condition==""){
                    XMS.msgbox.show('请选择查询内容！', 'error', 2000);
                    return
                }
                //用于解决long型不可空传的问题
                // if (cmd.charAt(cmd.length - 1) == '=') {
                //     cmd += '0';
                // }
                self._getTableList(cmd);
            });

            //第二页重置按钮清空select下拉框
            var _resetBtn = _form2.find("[name='resetSelect']");
            _resetBtn.off('click').on('click', function () {
                var a = document.getElementById("findTableByContent");
                a.options[0].selected = true;
            });

            //第三页查询按钮点击事件
            var _form3 = Page.findId('queryDataFormTop');
            Utils.setSelectData(_form3);
            var _queryBtn3 = _form3.find("[name='query']");
            _queryBtn3.off('click').on('click', function () {
                var cmd = _form3.serialize();
                var startDate = _form3.find("[name='startDate']").val();
                var today = new Date();
                if (new Date(startDate) > today) {
                    XMS.msgbox.show('查询时间不能超过今天！', 'error', 2000);
                    return
                }
                if (startDate == 0) {
                    XMS.msgbox.show('查询时间为空！', 'error', 2000);
                    return
                }
                self._getTopTableList(cmd);
            });
        },

    };
    module.exports = init;
});

	

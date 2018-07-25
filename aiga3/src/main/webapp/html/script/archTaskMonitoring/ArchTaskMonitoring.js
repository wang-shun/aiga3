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
    //第二张图隐藏页
    srvMap.add("getTaskNumCountListHint", pathAlias + "getList.json", "arch/taskNumCountListHint/queryByTimeHint");
    //一天的任务运行频率分布情况
    srvMap.add("getTaskFrequencyList", pathAlias + "getList.json", "arch/getTaskFrequencyList/queryByFrequency");
    //一天内任务在时长内运行次数
    srvMap.add("getTaskTimesList", pathAlias + "getList.json", "arch/TaskTimes/queryByTimes");


    //第二页
    //系统表格信息查询,监控信息表格
    srvMap.add("getTableList", pathAlias + "getList.json", "arch/TableList/findTableList");

    //第三页  Top10
    //表格一  一周内任务失败次数Top10
    srvMap.add("getTopListFirst", pathAlias + "getList.json", "arch/TopListFirst/findTopListFirst");
    //表格二   一周内任务执行失败率Top10
    srvMap.add("getTopListSecond", pathAlias + "getList.json", "arch/TopListSecond/findTopListSecond");

    var isHint = false;

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

            this._selectChange();
        },

        //第二页下拉框change事件
        _selectChange:function(){

            $("#condition").change(function(){
                var ptitle = $(this).find("option:selected").text();
                var numbers = $("#condition").find("option"); //获取select下拉框的所有值
                var temp = -1;
                for(var i=0;i<numbers.size();i++){
                    if(ptitle==numbers[i].text){
                        temp = i;
                    }
                }
                if(temp==0){_secLevSelectRender()}
                else if(temp==1){_firstSecLevSelectRender()}
                else if(temp==2){_secondSecLevSelectRender()}

            });

            //不显示二级下拉框
            function _secLevSelectRender(){
                document.getElementById("secondLevelSelect").innerHTML="";
            }

            //第1个二级下拉框渲染
            function _firstSecLevSelectRender () {
                var str = "<label class=\"col-sm-2  control-label\" style=\"margin-top: 5px\">二级查询:</label>\n" +
                    "                                        <div class=\"col-sm-2\">\n" +
                    "                                            <select class=\"selectpicker\"  name=\"secondLevelCondition\" id=\"secondLevelCondition\" data-live-search=\"true\" >\n" +
                    "                                                <option value=\"noChoice\">详细信息请选择</option>\n" +
                    "                                                <option value=\"timesOne\">[0,5]次任务运行次数信息</option>\n" +
                    "                                                <option value=\"timesTwo\">[6,10]次任务运行次数信息</option>\n" +
                    "                                                <option value=\"timesThree\">[11,20]次任务运行次数信息</option>\n" +
                    "                                                <option value=\"timesFour\">21次及以上任务运行次数信息</option>\n" +
                    "                                            </select>\n" +
                    "                                        </div>";
                document.getElementById("secondLevelSelect").innerHTML=str;
            }

            //第2个二级下拉框渲染
            function _secondSecLevSelectRender () {
                var str = "<label class=\"col-sm-2  control-label\" style=\"margin-top: 5px\">二级查询:</label>\n" +
                    "                                        <div class=\"col-sm-2\">\n" +
                    "                                            <select class=\"selectpicker\"  name=\"secondLevelCondition\" id=\"thirdLevelCondition\" data-live-search=\"true\" title=\"请选择\">\n" +
                    "                                                <option value=\"noChoice\">详细信息请选择</option>\n" +
                    "                                                <option value=\"minutesOne\">[0,5]分钟任务运行平均时间在内的次数</option>\n" +
                    "                                                <option value=\"minutesTwo\">(5,10]分钟任务运行平均时间在内的次数</option>\n" +
                    "                                                <option value=\"minutesThree\">(10,15]分钟任务运行平均时间在内的次数</option>\n" +
                    "                                                <option value=\"minutesFour\">15分钟以上任务运行平均时间在内的次数</option>\n" +
                    "                                            </select>\n" +
                    "                                        </div>";
                document.getElementById("secondLevelSelect").innerHTML=str;
            }
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
                    var checkRate = [];
                    var sessionRate = [];
                    var reportRate = [];
                    var collectRate = [];

                    for (var i = 0; i < json.data.length; i++) {
                        check.push(json.data[i].checkTotal);
                        session.push(json.data[i].sessionTotal);
                        report.push(json.data[i].reportTotal);
                        collect.push(json.data[i].collectTotal);
                        checkRate.push(json.data[i].checkRate);
                        sessionRate.push(json.data[i].sessionRate);
                        reportRate.push(json.data[i].reportRate);
                        collectRate.push(json.data[i].collectRate);
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
                            data: ['task_check', 'task_session', 'task_report', 'task_collect']
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
                                name: 'task_check',
                                type: 'bar',
                                data: check,
                            },
                            {
                                name: 'task_session',
                                type: 'bar',
                                data: session,
                            },
                            {
                                name: 'task_report',
                                type: 'bar',
                                data: report,
                            },
                            {
                                name: 'task_collect',
                                type: 'bar',
                                data: collect,
                            },
                            {
                                name: 'checkRate',
                                type: 'line',
                                yAxisIndex: 1,
                                data: checkRate,
                            },
                            {
                                name: 'sessionRate',
                                type: 'line',
                                yAxisIndex: 1,
                                data: sessionRate,
                            },
                            {
                                name: 'reportRate',
                                type: 'line',
                                yAxisIndex: 1,
                                data: reportRate,
                            },
                            {
                                name: 'collectRate',
                                type: 'line',
                                yAxisIndex: 1,
                                data: collectRate,
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
                    myChart.setOption(option,true);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });

            //第二个box图标展示各个进程一天不同时间段的任务总数-----封装到方法
            self._getGridListSecondBox(_cmd);

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
                            data: ['[0,5]次', '[6,10]次', '[11,20]次', '21次及以上']
                        },
                        series: [
                            {
                                name: '任务数量',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {value: firstTimes, name: '[0,5]次'},
                                    {value: secondTimes, name: '[6,10]次'},
                                    {value: thirdTimes, name: '[11,20]次'},
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

                    //echarts图表点击跳转
                    myChart1.on('click', function (param){
                        var name=param.name;

                        //饼状图点击后，先渲染出二级下拉框
                        _firstSecLevSelectRender();

                        //对二级下拉框进行默认设值
                        var numbers = $("#condition").find("option"); //获取select下拉框的所有值
                        $(numbers[1]).attr("selected", "selected");  //默认选中第2个

                        //对日期默认设值
                        var _form = Page.findId("queryDataForm");
                        var startDate = _form.find("[name='startDate']").val();

                        var _form1 = Page.findId("queryDataFormTable");
                        _form1.find('[name="startDate"]').val(startDate);

                        var number = $("#secondLevelCondition").find("option");//获取第一个二级下拉框
                        if(name=="详细信息请选择"){
                            $(number[0]).attr("selected","selected");
                        }else if(name=="[0,5]次"){
                            $(number[1]).attr("selected","selected");
                        }else if(name=="[6,10]次"){
                            $(number[2]).attr("selected","selected");
                        }else if(name=="[11,20]次"){
                            $(number[3]).attr("selected","selected");
                        }else if(name=="21次及以上"){
                            $(number[4]).attr("selected","selected");
                        }
                        //标签页第二页展示
                        $('#Page_taskMonitoring li:eq(1) a').tab('show');
                    });

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
                            text: '一天内任务执行平均时长区间分布数量',
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
                                name: '任务数量',
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
                    //echarts图表点击跳转
                    myChart1.on('click', function (param){
                        var name=param.name;

                        //饼状图点击后，先渲染出二级下拉框
                        _secondSecLevSelectRender();

                        //对二级下拉框进行默认设值
                        var numbers = $("#condition").find("option"); //获取select下拉框的所有值
                        $(numbers[2]).attr("selected", "selected");  //默认选中第3个

                        //对日期默认设值
                        var _form = Page.findId("queryDataForm");
                        var startDate = _form.find("[name='startDate']").val();

                        var _form1 = Page.findId("queryDataFormTable");
                        _form1.find('[name="startDate"]').val(startDate);

                        var number = $("#thirdLevelCondition").find("option");//获取第2个二级下拉框
                        if(name=="详细信息请选择"){
                            $(number[0]).attr("selected","selected");
                        }else if(name=="[0,5]分钟"){
                            $(number[1]).attr("selected","selected");
                        }else if(name=="(5,10]分钟"){
                            $(number[2]).attr("selected","selected");
                        }else if(name=="(10,15]分钟"){
                            $(number[3]).attr("selected","selected");
                        }else if(name=="15分钟以上"){
                            $(number[4]).attr("selected","selected");
                        }
                        //标签页第二页展示
                        $('#Page_taskMonitoring li:eq(1) a').tab('show');
                    });
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
            });

            //第1个二级下拉框渲染
            function _firstSecLevSelectRender () {
                var str = "<label class=\"col-sm-2  control-label\" style=\"margin-top: 5px\">二级查询</label>\n" +
                    "                                        <div class=\"col-sm-2\">\n" +
                    "                                            <select class=\"selectpicker\"  name=\"secondLevelCondition\" id=\"secondLevelCondition\" data-live-search=\"true\" title=\"请选择\">\n" +
                    "                                                <option value=\"noChoice\">详细信息请选择</option>\n" +
                    "                                                <option value=\"timesOne\">[0,5]次任务运行次数信息</option>\n" +
                    "                                                <option value=\"timesTwo\">[6,10]次任务运行次数信息</option>\n" +
                    "                                                <option value=\"timesThree\">[11,20]次任务运行次数信息</option>\n" +
                    "                                                <option value=\"timesFour\">21次及以上任务运行次数信息</option>\n" +
                    "                                            </select>\n" +
                    "                                        </div>";
                document.getElementById("secondLevelSelect").innerHTML=str;
            }

            //第2个二级下拉框渲染
            function _secondSecLevSelectRender () {
                var str = "<label class=\"col-sm-2  control-label\" style=\"margin-top: 5px\">二级查询:</label>\n" +
                    "                                        <div class=\"col-sm-2\">\n" +
                    "                                            <select class=\"selectpicker\"  name=\"secondLevelCondition\" id=\"thirdLevelCondition\" data-live-search=\"true\" title=\"请选择\">\n" +
                    "                                                <option value=\"noChoice\">详细信息请选择</option>\n" +
                    "                                                <option value=\"minutesOne\">[0,5]分钟任务运行平均时间在内的次数</option>\n" +
                    "                                                <option value=\"minutesTwo\">(5,10]分钟任务运行平均时间在内的次数</option>\n" +
                    "                                                <option value=\"minutesThree\">(10,15]分钟任务运行平均时间在内的次数</option>\n" +
                    "                                                <option value=\"minutesFour\">15分钟以上任务运行平均时间在内的次数</option>\n" +
                    "                                            </select>\n" +
                    "                                        </div>";
                document.getElementById("secondLevelSelect").innerHTML=str;
            }

        },

        //第二个box图标展示各个进程一天不同时间段的任务总数-----封装到方法
        _getGridListSecondBox: function (_cmd) {
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
                            data: ['task_check', 'task_session', 'task_report', 'task_collect', 'taskTotal']
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
                                name: 'task_check',
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
                                name: 'task_session',
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
                                name: 'task_report',
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
                                name: 'task_collect',
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
                    myChart.setOption(option);
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

            //获得一级下拉框的值
            var _form = Page.findId('queryDataFormTable');
            var condition = _form.find('[name="condition"]').val();
            var conditionSecond = _form.find('[name="secondLevelCondition"]').val();
            if(conditionSecond==null || conditionSecond=="noChoice"){
                console.log("conditionSecond="+conditionSecond);
                //表格内容渲染
                var _dom1 = Page.findId('taskMonitoringListTable');
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                // 暂不设置服务器端分页
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
            }else{
                console.log("conditionSecond!=null");
                //表格内容渲染
                var _dom1 = Page.findId('taskMonitoringListTable');
                XMS.msgbox.show('数据加载中，请稍候...', 'loading');
                console.log(conditionSecond.substring(0,1));
                Utils.getServerPage(srvMap.get('getTableList'), _cmd, function (json) {
                    window.XMS.msgbox.hide();
                    //第二张表对应的四张表timesOne,timesTwo,timesThree,timesFour
                    if(conditionSecond.substring(0,1)=='t'){
                        var tablebtn = _dom1.find("[name='content']");
                        var template = Handlebars.compile(Page.findTpl('getFullTableListTimes'));
                        tablebtn.html(template(json.data));
                    }
                    //第三张表对应的四张表minutesOne,minutesTwo,minutesThree,minutesFour
                    else if(conditionSecond.substring(0,1)=='m'){
                        var tablebtn = _dom1.find("[name='content']");
                        var template = Handlebars.compile(Page.findTpl('getFullTableListMinutes'));
                        tablebtn.html(template(json.data));
                    }
                });
            }
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

            //第二个Top内容渲染
            var _dom2 = Page.findId('taskMonitoringListTopSecond');
            XMS.msgbox.show('数据加载中，请稍候...', 'loading');
            Utils.getServerPage(srvMap.get('getTopListSecond'), _cmd, function (json) {
                window.XMS.msgbox.hide();
                var tablebtn = _dom2.find("[name='content']");
                var template = Handlebars.compile(Page.findTpl('getTopTableSecond'));
                tablebtn.html(template(json.data));
            });
        },

        //查询按钮点击后渲染提示信息
        _infoRender:function(){
            var self=this;
            //三张图提示信息渲染：页面查询按钮点击后才显示提示信息
            //图一  提示
            var str1 = "<p style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">对应表格信息<br>请到<br>表格信息查询页<br>查询内容表一<br></p>";
            document.getElementById("JS_tableFirstHint").innerHTML=str1;
            //图一 “监控信息图一”
            var str2 = " <h3 class=\"box-title\">监控信息图一</h3>";
            document.getElementById("JS_monitorInfoViewFirst").innerHTML=str2;

            //图二 提示信息
            self._infoTableSecondRender();
            //图二 隐藏图按钮
            var str3 = "<button  id=\"hintButton\" name=\"hintButton\" class=\"btn btn-default\" style=\"float:right;margin-right:20px\">10分钟颗粒度查询</button>";
            document.getElementById("JS_tableSecondHintButton").innerHTML=str3;
            //图二 “监控信息图二”
            var str4 = "<h3 class=\"box-title\">监控信息图二</h3>";
            document.getElementById("JS_monitorInfoViewSecond").innerHTML=str4;

            //图三图四提示
            var str5 = "<p  style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">对应表格信息<br>请到<br>表格信息查询页<br>查询内容表二表三</p>";
            document.getElementById("JS_tableThirdHint").innerHTML=str5;
            //图三 “监控信息图三”
            var str6 = " <h3 class=\"box-title\">监控信息图三</h3>";
            document.getElementById("JS_monitorInfoViewThird").innerHTML=str6;

            self._hintButtonClick();

        },

        //第二张图左侧提示信息和按钮文本change
        _infoTableSecondRender: function () {
            var str2 = "<p style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">例：x轴时间2表示<br>2-3点之间<br></p>";
            document.getElementById("JS_tableSecondHint").innerHTML=str2;
        },

        //隐藏图提示信息（左侧）和按钮文本change
        _infoHintRender: function () {
            var str = "<p style=\"float: left;width: 120px;height:100px; color:cadetblue;margin-top: 250px\">例：x轴5表示5点<br>13.2表示13点20分<br></p>";
            document.getElementById("JS_tableSecondHint").innerHTML=str;
        },

        //隐藏按钮点击事件
        _hintButtonClick: function() {

            var self = this;
            var isHint = true;
            //第一页查询按钮点击事件
            var a=Page.findId("tableSecondHintButton");
            var hintButton = a.find("[name='hintButton']");

            hintButton.off('click').on('click', function () {
                console.log("隐藏按钮已点击");
                if(isHint){
                    self._getHintList();
                    self._infoHintRender();
                    document.getElementById("hintButton").innerText="各时段任务总数图";
                }else{
                    var _form1 = Page.findId('queryDataForm');
                    var cmd = _form1.serialize();
                    cmd = cmd.replace(/-/g, "/");
                    self._getGridListSecondBox(cmd);
                    self._infoTableSecondRender();
                    document.getElementById("hintButton").innerText="10分钟颗粒度查询";
                }
                if(isHint){
                    isHint = false;
                }else{
                    isHint = true;
                }

            });
         },

        //渲染隐藏图
        _getHintList:function(){
            // var self=this;
            var _form = Page.findId('queryDataForm');
            var startDate = _form.find('[name="startDate"]').val();
            console.log("startDate:"+startDate);

            var _cmd = "startDate="+startDate;
            _cmd=_cmd.replace(/-/g, "/");
            Rose.ajax.postJson(srvMap.get('getTaskNumCountListHint'), _cmd, function (json, status){
                if (status) {
                    console.log("拿到接口，返回");
                    console.log("json.data.length:"+json.data.length);
                    var keys = [];
                    var values = [];

                    for (var i = 0; i < json.data.length; i++) {
                        keys.push(json.data[i].keys);
                        values.push(json.data[i].values);
                    }
                    var myChart = echarts.init(document.getElementById('taskRuntimeByTime'));
                    option1 = {
                        title: {
                            text: '一天10分钟颗粒度任务数查询'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['任务总数']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataZoom: {
                                    yAxisIndex: 'none'
                                },
                                magicType: {type: ['line', 'bar']},
                                restore: {},
                                saveAsImage: {}
                            }
                        },
                        xAxis:  {
                            type: 'category',
                            boundaryGap: false,
                            data: keys
                        },
                        yAxis: {
                            type: 'value',
                            name:'任务数',
                            axisLabel: {
                                formatter: '{value} '
                            }
                        },
                        series: [
                            {
                                name:'任务总数',
                                type:'line',
                                data:values,
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
                            }
                        ]
                    };

                    myChart.setOption(option1,true);
                } else {
                    XMS.msgbox.show(json.retMessage, 'error', 2000);
                }
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

                self._infoRender();

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

	

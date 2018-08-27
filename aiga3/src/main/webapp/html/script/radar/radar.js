define(function(require, exports, module) {

    //引入公用模块
    require('global/header.js');
    // 通用工具模块
    var Utils = require("global/utils.js");
    //引入系统得分图
    var Meter = require("./recentScoreView");
    // 初始化页面
    var Page = Utils.initPage('radar');

    //下拉框信息查询
    srvMap.add("getSelectList",  "", "webservice/radar/sysList");
    //系统信息展示，使用旧接口
    srvMap.add("getSystemInfo", "", "webservice/archiThird/findTransPage");
    //流程图、部署图下载
    srvMap.add("getFileId", "", "webservice/radar/sysFileInfo");
    srvMap.add("downloadFile","", "sys/changeplanonile/downloadFileBatch");
    //系统得分
    srvMap.add("getRecentRecord", "", "webservice/radar/recentRecord");
    //历史得分信息弹出模态框，折线图
    srvMap.add("getHistoryRecord", "", "webservice/radar/historyRecord");



    var init = {

        init: function () {
            this._render();
        },

        _render: function () {

            var self = this;
            //下拉框渲染
            self._load_combo_select();
        },

        //下拉框渲染
        _load_combo_select:function(){
            var self = this;
            var sysGroup = Page.findId("selectSysGroup");
            //通过data-url属性找对应接口，拿回对应idkey和namekey
            Utils.setSelectDataPost(sysGroup,true);
            //查询
            sysGroup.find("[name='query']").off('click').on('click', function () {
                var sysId = sysGroup.find("[name='sysName']").val();
                if (sysId==""){
                    XMS.msgbox.show('查询内容为空或Id不存在！', 'error', 2000);
                    return;
                }
                //系统信息
                self._getSysInfo(sysId);
                //系统信息若为空，字体变红
                self._setNullFontColor();
                //系统recent综合评分
                self._recentScore(sysId);
                //信息图点击下载事件
                self._photoDownload(sysId);
                //历史得分曲线
                self._histroyShow(sysId);
            });
        },

        //系统recent综合评分和八大军规雷达图
        _recentScore:function(sysId){

            Rose.ajax.postJson(srvMap.get('getRecentRecord'), "sysId="+sysId, function (json, status) {

                if (status) {

                    var totalMark = 0;
                    var createDate = "****-**-**";
                    var rlMark = 0;
                    var gkyMark = 0;
                    var rxkyMark = 0;
                    var pzMark = 0;
                    var rzMark = 0;
                    var aqMark = 0;
                    var fcMark = 0;
                    var jkMark = 0;

                    if(json.data==null){
                        _showRadar();
                        _showRencentScore();
                        return ;
                    }

                    //雷达图数据
                    if(json.data.totalMark!=null){
                        totalMark = json.data.totalMark;
                    }
                    if(json.data.createDate!=null){
                        createDate = json.data.createDate;
                    }
                    if(json.data.rlMark!=null){
                        rlMark = json.data.rlMark;
                    }
                    if(json.data.gkyMark!=null){
                        gkyMark = json.data.gkyMark;
                    }
                    if(json.data.rxkyMark!=null){
                        rxkyMark = json.data.rxkyMark;
                    }
                    if(json.data.pzMark!=null){
                        pzMark = json.data.pzMark;
                    }
                    if(json.data.rzMark!=null){
                        rzMark = json.data.rzMark;
                    }
                    if(json.data.aqMark!=null){
                        aqMark = json.data.aqMark;
                    }
                    if(json.data.fcMark!=null){
                        fcMark = json.data.fcMark;
                    }
                    if(json.data.jkMark!=null){
                        jkMark = json.data.jkMark;
                    }

                    _showRadar();

                    _showRencentScore();

                }else{
                    XMS.msgbox.show('返回接口信息失败！', 'error', 2000);
                }

                //雷达图
                function _showRadar() {
                    var myChart = echarts.init(document.getElementById("JS_eightRadarView"));
                    option = {
                        backgroundColor: 'white',//背景色
                        title: {
                            text: ''
                        },
                        tooltip: {},
                        radar: {
                            indicator: [
                                { name: '分层', max: 100},
                                { name: '高可用', max: 100},
                                { name: '容量', max: 100},
                                { name: '柔性可用', max: 100},
                                { name: '配置', max: 100},
                                { name: '日志', max: 100},
                                { name: '安全', max: 100},
                                { name: '监控', max: 100}
                            ]
                        },
                        series: [{
                            type: 'radar',
                            data : [
                                {
                                    value : [fcMark, gkyMark, rlMark, rxkyMark, pzMark, rzMark, aqMark, jkMark],
                                    name : '八大军规',
                                    //这里的配置显示数值
                                    label: {
                                        normal: {
                                            show: true,
                                            formatter:function(params) {
                                                return params.value;
                                            }
                                        }
                                    }
                                }
                            ]
                        }]
                    };
                    myChart.setOption(option);
                }

                //系统评分图
                function _showRencentScore() {
                    Meter.setOptions({
                        element: 'meter',
                        centerPoint: {
                            x: 160,
                            y: 160
                        },
                        radius: 160,
                        data: {
                            value: totalMark,
                            title: '系统综合评分{t}',
                            subTitle: '评估时间: '+createDate,
                            area: [{
                                min: 0, max: 85, text: '较差'
                            },{
                                min: 85, max: 90, text: '一般'
                            },{
                                min: 90, max: 95, text: '良好'
                            },{
                                min: 95, max: 100, text: '优秀'
                            }]
                        }
                    }).init();
                }
            });

        },

        // 系统信息
        _getSysInfo: function (sysId) {

            XMS.msgbox.show('数据加载中，请稍候...', 'loading');

            Rose.ajax.postJson(srvMap.get('getSystemInfo'), "onlysysId="+sysId, function (json, status) {

                if (status) {
                    window.XMS.msgbox.hide();
                    if(json.data==null){
                        //系统信息设为空
                        _nullSysInfo();
                        return;
                    }
                    if(json.data.content.length>1){
                        XMS.msgbox.show('错误，查询结果集大于1', 'error', 2000);
                        return;
                    }
                    var firName ;
                    var secName ;
                    var name ;
                    var belongLevel ;
                    var rankInfo ;
                    var tempStr = "信息待维护";
                    if(json.data.content[0].firName==null){
                        firName = tempStr;
                        _sysNullFontChange("JS_firstDomainValue");
                    }else{
                        firName = json.data.content[0].firName;
                    }
                    if(json.data.content[0].secName==null){
                        secName = tempStr;
                        _sysNullFontChange("JS_secondDomainValue");
                    }else{
                        secName = json.data.content[0].secName;
                    }
                    if(json.data.content[0].name==null){
                        name = tempStr;
                        _sysNullFontChange("JS_systemNameValue");
                    }else{
                        name = json.data.content[0].name;
                    }
                    if(json.data.content[0].belongLevel==null){
                        belongLevel = tempStr;
                        _sysNullFontChange("JS_levelValue");
                    }else{
                        belongLevel = json.data.content[0].belongLevel;
                    }
                    if(json.data.content[0].rankInfo==null){
                        rankInfo = tempStr;
                        _sysNullFontChange("JS_rankInfo");
                    }else{
                        rankInfo = json.data.content[0].rankInfo;
                    }
                    document.getElementById("JS_systemNameValue").innerText = name;
                    document.getElementById("JS_levelValue").innerText = belongLevel;
                    document.getElementById("JS_firstDomainValue").innerText = firName;
                    document.getElementById("JS_secondDomainValue").innerText = secName;
                    document.getElementById("JS_rankInfo").innerText = rankInfo;
                }

                //设置为空项字体变红
                function _sysNullFontChange(fontId) {
                    var nullView = document.getElementById(fontId);
                    nullView.style.color="red";
                }

                //系统信息设为不显示
                function _nullSysInfo() {
                    document.getElementById("JS_systemNameValue").innerText = "";
                    document.getElementById("JS_levelValue").innerText = "";
                    document.getElementById("JS_firstDomainValue").innerText = "";
                    document.getElementById("JS_secondDomainValue").innerText = "";
                    document.getElementById("JS_rankInfo").innerText = "";
                }

        });
        },

        //空值字体变红
        _setNullFontColor: function () {
            var nullView1 = document.getElementById("JS_firstDomainValue");
            var nullView2 = document.getElementById("JS_secondDomainValue");
            var nullView3 = document.getElementById("JS_systemNameValue");
            var nullView4 = document.getElementById("JS_levelValue");
            var nullView5 = document.getElementById("JS_rankInfo");
            nullView1.style.color="black";
            nullView2.style.color="black";
            nullView3.style.color="black";
            nullView4.style.color="black";
            nullView5.style.color="black";
        },

        //图片下载
        _photoDownload:function(sysId){

            var processFileId;
            var deployFileId

            var _form = Page.findId('connectionPo');

            var _sysFlowBtn = _form.find("[name='sysFlowChart']");
            var _sysDeployBtn = _form.find("[name='sysDeployemntChart']");

            Rose.ajax.postJson(srvMap.get('getFileId'), "sysId="+sysId, function (json, status) {
                if (status) {
                    window.XMS.msgbox.hide();
                    if(json.data==null){
                        XMS.msgbox.show('下载图片Id返回为空！', 'error', 2000);
                        return;
                    }
                    processFileId = json.data.processFile;
                    deployFileId = json.data.deployFile;
                }else{
                    XMS.msgbox.show('文件接口返回失败！', 'error', 2000);
                }
            });

            //图片附件下载事件绑定
            _sysFlowBtn.off('click').on('click',function() {
                if(processFileId){
                    var downloadurl = srvMap.get('downloadFile')+"?"+'ids=' + processFileId;
                    _sysFlowBtn.attr("href", downloadurl.toString());
                }else{
                    XMS.msgbox.show('没有可下载的附件！', 'error', 2000);
                }
            });

            _sysDeployBtn.off('click').on('click', function () {
                if(deployFileId){
                    var downloadurl = srvMap.get('downloadFile')+"?"+'ids=' + deployFileId;
                    _sysDeployBtn.attr("href", downloadurl.toString());
                }else{
                    XMS.msgbox.show('没有可下载的附件！', 'error', 2000);
                }
            });
        },

        //历史得分信息，折线图
        _histroyShow:function(sysId){
            var _historyScore = Page.findId("historyScore");
            var _historyScoreBtn = _historyScore.find("[name='historyScore']");

            _historyScoreBtn.off('click').on('click', function () {
                Rose.ajax.postJson(srvMap.get('getHistoryRecord'), "sysId="+sysId, function (json, status) {
                    if (status) {
                        window.XMS.msgbox.hide();

                        if(json.data==null){
                            return;
                        }

                        var createDate = [];
                        var totalMark = [];
                        var aqMark = [];
                        var rlMark = [];
                        var jkMark = [];
                        var gkyMark = [];
                        var rxkyMark = [];
                        var pzMark = [];
                        var rzMark = [];
                        var fcMark = [];
                        //赋值
                        for(var i=0;i<json.data.length;i++){
                            createDate[i] = json.data[i].createDate;
                            if((totalMark[i] = json.data[i].totalMark)==null){
                                totalMark[i] = 0;
                            }
                            if((aqMark[i] = json.data[i].aqMark)==null){
                                aqMark[i] = 0;
                            }
                            if((rlMark[i] = json.data[i].rlMark)==null){
                                rlMark[i] = 0;
                            }
                            if((jkMark[i] = json.data[i].jkMark)==null){
                                jkMark[i] = 0;
                            }
                            if((gkyMark[i] = json.data[i].gkyMark)==null){
                                gkyMark[i] = 0;
                            }
                            if((rxkyMark[i] = json.data[i].rxkyMark)==null){
                                rxkyMark[i] = 0;
                            }
                            if((pzMark[i] = json.data[i].pzMark)==null){
                                pzMark[i] = 0;
                            }
                            if((rzMark[i] = json.data[i].rzMark)==null){
                                rzMark[i] = 0;
                            }
                            if((fcMark[i] = json.data[i].fcMark)==null){
                                fcMark[i] = 0;
                            }
                        }

                        //预编译模板
                        // var template = Handlebars.compile(Page.findTpl('historyScoreShow'));
                        // Page.findId("hisEchartShow").html(template(json.data));

                        //模态框history折线图展示
                        var myChart = echarts.init(document.getElementById("JS_historyEcharts"));
                        option = {
                            title: {
                                text: ''
                            },
                            tooltip: {
                                trigger: 'axis'
                            },
                            legend: {
                                data:['总得分','分层','高可用','容量','柔性可用','日志','配置','安全','监控'],
                                selected:{
                                    "分层":false,
                                    "高可用":false,
                                    "容量":false,
                                    "柔性可用":false,
                                    "日志":false,
                                    "配置":false,
                                    "安全":false,
                                    "监控":false
                                }
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            toolbox: {
                                feature: {
                                    saveAsImage: {}
                                }
                            },
                            xAxis: {
                                type: 'category',
                                boundaryGap: false,
                                data: createDate
                            },
                            yAxis: {
                                type: 'value'
                            },
                            series: [
                                {
                                    name:'总得分',
                                    type:'line',
                                    stack: '总量',
                                    data:totalMark
                                },
                                {
                                    name:'分层',
                                    type:'line',
                                    stack: '总量',
                                    data:fcMark
                                },
                                {
                                    name:'高可用',
                                    type:'line',
                                    stack: '总量',
                                    data:gkyMark
                                },
                                {
                                    name:'容量',
                                    type:'line',
                                    stack: '总量',
                                    data:rlMark
                                },
                                {
                                    name:'柔性可用',
                                    type:'line',
                                    stack: '总量',
                                    data:rxkyMark
                                },
                                {
                                    name:'日志',
                                    type:'line',
                                    stack: '总量',
                                    data:rzMark
                                },
                                {
                                    name:'配置',
                                    type:'line',
                                    stack: '总量',
                                    data:pzMark
                                },
                                {
                                    name:'安全',
                                    type:'line',
                                    stack: '总量',
                                    data:aqMark
                                },
                                {
                                    name:'监控',
                                    type:'line',
                                    stack: '总量',
                                    data:jkMark
                                }
                            ]
                        };

                        myChart.setOption(option,false,false);

                        //添加模态框点击事件
                        // $("#JS_totalMark").on("change",function (argument) {
                        //     var checked = document.getElementById("JS_totalMark");
                        //         if(checked.checked){
                        //             option.series[0].name="总得分";
                        //             option.series[0].type="line";
                        //             option.series[0].stack="总量";
                        //             option.series[0].data=totalMark;
                        //         }else{
                        //             option.series[0] = '';
                        //         }
                        // });
                    }else{
                        XMS.msgbox.show('历史得分接口返回失败！', 'error', 2000);
                    }
                });
            });
        },

    };
    //模块化
    module.exports = init;
});



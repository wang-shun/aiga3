define(function(require, exports, module) {

	//引入公用模块
	require('global/header.js');
	// 通用工具模块
	var Utils = require("global/utils.js");
	var pathAlias = "errorCode/"; 
	// 初始化页面ID(和文件名一致)，不需要带'#Page_'
	var Page = Utils.initPage('errorCodePool');


	//显示查询信息表
	srvMap.add("querybylistreport", pathAlias+"poolConfigurationList.json", "webservice/csferrcode/querybylistreport");
	//查询状态下拉框 center
	srvMap.add("select1", pathAlias+"distinctCenter.json", "webservice/csferrcode/select1");

	//显示查询信息表
	srvMap.add("poolConfigurationList", pathAlias+"poolConfigurationList.json", "webservice/configure/query");
	//显示查询信息表    前七天
	srvMap.add("echartshow", pathAlias+"poolConfigurationList.json", "webservice/csferrcode/echartshow");

	//业务系统下拉框
	srvMap.add("businessSystem", pathAlias+"businessSystem.json", "webservice/static/businessSystem");
	//查询状态下拉框
	srvMap.add("queryState", pathAlias+"queryState.json", "webservice/static/queryState");
	//查询状态下拉框 center
	srvMap.add("distinctCenter", pathAlias+"distinctCenter.json", "webservice/configure/distinctCenter");
	//查询状态下拉框 db
	srvMap.add("distinctDb", pathAlias+"distinctDb.json", "webservice/configure/distinctDb");
	//excle export uncover
	srvMap.add("uncover", pathAlias+"getText.json", "webservice/csferrcode/uncover");
	//excle export unstandard
	srvMap.add("unstandard", pathAlias+"getText.json", "webservice/csferrcode/unstandard");
	
	var cache = {
			datas : ""
	};
	var init = {
				
		init: function() {			 
			this._render();
		},
		
		_render: function() {
			//查询
			this._time();
			var _form = Page.findId('queryDataForm');
			var cmd = _form.serialize();
			this._getGridList(cmd);
			this._query_event();
			this._handlebar_help_register();
		},
		
		//初始化时间框
		_time:function(){			
			var _form = Page.findId('queryDataForm'); 
			_form.find("[name='insertTime']").val(Rose.date.dateTime2str(new Date(),"yyyy-MM-dd"));
		},
		
		// 查询表格数据
		_getGridList: function(cmd){
			var self = this;
			var _cmd = '';
			if(cmd){
				_cmd = cmd;
			}
			var stime = _cmd.substring(11,21);
			var ftime = stime.replaceAll('-','');
			var scenter = _cmd.split('center')[1]==''?'':_cmd.split('center=')[1];
			var _topcmd = {
				insertTime:ftime,
				center:scenter
			}
			var _dom = Page.findId('connectionPoolList');
			var _domPagination = _dom.find("[name='pagination']");
			XMS.msgbox.show('数据加载中，请稍候...', 'loading');			
			// 设置服务器端分页
			Rose.ajax.postJson(srvMap.get('querybylistreport'),_topcmd,function(json){
				window.XMS.msgbox.hide();		
				var template = Handlebars.compile(Page.findTpl('connectionPoolTemp'));                
                var tablebtn = _dom.find("[name='content']");
                var newjson =[] ;
                var totaldata = null;
                for(index in json.data){
                	if(json.data[index].centerName=='合计'){
                		totaldata=json.data[index];
                	}
                	newjson[index]=json.data[index];
                };
                tablebtn.html(template(newjson));
        		Utils.eventTrClickCallback(_dom);
        		//展示报告内容
				var templateText = Handlebars.compile(Page.findTpl('errorCodeTempText'));
				var _text = Page.findId('errorCodeText');
				var result ={
					total:"0",
					percentage:"0",
					standard:"0"
				}
				if(totaldata){
					result.total=totaldata.errcodeCfgNum;
					result.percentage=totaldata.errcodeCoverRate;
					result.standard=totaldata.errcodeSpecRate;
				}else{
					var tmp = 0;
					tmp = json.data.length;
					var all = 0;
					var totalPercentage = 0;
					var totalStandard = 0;
					for(index in json.data){
						all += json.data[index].errcodeCfgNum;
						totalPercentage += parseInt(json.data[index].errcodeCoverRate);
						totalStandard += parseInt(json.data[index].errcodeSpecRate);
					}
					result.total=all;
					result.percentage=Math.round((totalPercentage/tmp)*100)/100;
					result.standard=Math.round((totalStandard/tmp)*100)/100;
				}
    			_text.html(templateText(result));
    			//打印查询月份
				var _form = Page.findId('queryDataForm');
				var _insertTime = _form.find("[name='insertTime']").val();
				var timeShow = Page.findId('text');
				timeShow.find("[name='timeShow']").text(_insertTime);
				_text.find("[name='timeShowTwo']").text(_insertTime);
        		
        		//下载未覆盖------按钮
        		tablebtn.find("[class='btn btn-primary btn-table-uncover']").off('click').on('click', function() {
					var selectCenter = $(this).attr("data-center");
        			var selectDate = $(this).attr("data-date");
        			var url = srvMap.get('uncover')+"?center="+selectCenter+"&insertTime="+selectDate;
					location.href = encodeURI(encodeURI(url));
        		});
        		//下载不规范------按钮
        		tablebtn.find("[class='btn btn-primary btn-table-unstandard']").off('click').on('click', function() {
					var selectCenter = $(this).attr("data-center");
        			var selectDate = $(this).attr("data-date");
        			var url = srvMap.get('unstandard')+"?center="+selectCenter+"&insertTime="+selectDate;
					location.href = encodeURI(encodeURI(url));
        		});
    			//7------按钮
        		tablebtn.find("[class='btn btn-primary btn-table-detail7']").off('click').on('click', function() {
        			var center = $(this).attr("data-center");
        			var time = $(this).attr("data-date");
					var pattern = /(\d{4})(\d{2})(\d{2})/;
					var formatedDate = time.replace(pattern, '$1-$2-$3');
					// 转换日期格式
					end = formatedDate.replace(/-/g, '/'); // "2010/08/01";
					// 创建日期对象
					var date = new Date(end);
					// -6 
					date.setDate(date.getDate() - 6);
					// 没有格式化的功能，只能一个一个取  // 因为js里month从0开始，所以要加1
					var month = parseInt(date.getMonth()) + 1;
					if(month<10){
						month = '0' + month;
					}
					var day = date.getDate();
					if(day<10){
						day = '0' + day;
					}
					var start = date.getFullYear() + '-' + month + '-' + day;
					var _ggcmd = {
						startMonth : start,
						endMonth : formatedDate,
						centerName: center
					}
        			var _7cmd = "startMonth="+_ggcmd.startMonth+"&endMonth="+_ggcmd.endMonth+"&centerName="+_ggcmd.centerName;
					Rose.ajax.postJson(srvMap.get("echartshow"), _7cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.hide();
							self._graphSec(7,json);
			        		var _modal = Page.findId('show7dayModal');
							_modal.modal('show');
							Utils.setSelectData(_modal);
							_modal.off('shown.bs.modal').on('shown.bs.modal', function () {
							});	
						} else {
							XMS.msgbox.show(json.retMessage, 'error', 2000);
						}
		  			});
        		});
        		//30------按钮
        		tablebtn.find("[class='btn btn-primary btn-table-detail30']").off('click').on('click', function() {
        			var center = $(this).attr("data-center");
        			var time = $(this).attr("data-date");
					var pattern = /(\d{4})(\d{2})(\d{2})/;
					var formatedDate = time.replace(pattern, '$1-$2-$3');
					// 转换日期格式
					end = formatedDate.replace(/-/g, '/'); // "2010/08/01";
					// 创建日期对象
					var date = new Date(end);
					// - 30
					date.setDate(date.getDate() - 30);
					// 没有格式化的功能，只能一个一个取  // 因为js里month从0开始，所以要加1
					var month = parseInt(date.getMonth()) + 1;
					if(month<10){
						month = '0' + month;
					}
					var day = date.getDate();
					if(day<10){
						day = '0' + day;
					}
					var start = date.getFullYear() + '-' + month + '-' + day;
					var _ggcmd = {
						startMonth : start,
						endMonth : formatedDate,
						centerName: center
					}
        			var _30cmd = "startMonth="+_ggcmd.startMonth+"&endMonth="+_ggcmd.endMonth+"&centerName="+_ggcmd.centerName;
					Rose.ajax.postJson(srvMap.get("echartshow"), _30cmd, function(json, status) {
						if(status) {
							window.XMS.msgbox.hide();
							self._graphSec(30,json);
			        		var _modal = Page.findId('show30dayModal');
							_modal.modal('show');
							Utils.setSelectData(_modal);
							_modal.off('shown.bs.modal').on('shown.bs.modal', function () {

							});
						} else {
							XMS.msgbox.show(json.retMessage, 'error', 2000);
						}
		  			});
        		});
			});
		},
		
		//绑定查询按钮事件
        _query_event: function() {
			var self = this;
			var _form = Page.findId('queryDataForm');
			Utils.setSelectDataPost(_form,true);
			var _queryBtn = _form.find("[name='query']");
			_queryBtn.off('click').on('click',function(){
				var cmd = _form.serialize();				
				var insertTime = _form.find("[name='insertTime']").val();
				if(insertTime == 0) {
					XMS.msgbox.show('采集时间为空！', 'error', 2000);
					return
				}
				self._getGridList(cmd);
			});		
        },
        //
        _handlebar_help_register: function() {
			Handlebars.registerHelper("changePowerSty",function(value) {
				if((parseInt(value))>0) {
					return 'change-font-green';
				} else if((parseInt(value))<0){
					return 'change-font-red';
				}else{
					return 'change-font-green';
				}
			});
			Handlebars.registerHelper("pesentAdd",function(value) {
				return value+"%";
			});
			Handlebars.registerHelper("isZero",function(value) {
				if(value==null || value==0.00) {
					return '0.00';
				} else {
					return value;
				}
			});
			Handlebars.registerHelper("changeRed",function(value) {
				if((parseInt(value))<60) {
					return 'change-font-red';
				} else {
					return '';
				}
			});
		},
        //
        _graphSec: function(day,json) {
			var echartsinitdom = null;
			if(day==7){
				echartsinitdom = "archiIndex7View";
			}else if(day==30){
				echartsinitdom = "archiIndex30View";
			}
			var myChart = echarts.init(Page.findId(echartsinitdom)[0]);
            myChart.showLoading({
                text: '读取数据中...' //loading，是在读取数据的时候显示
            });
                
			var option = {
				title : {
			        text: '指标情况',
			        subtext: '数据采集截止时间：XX月XX日XX:XX'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	y:'bottom',
                    type: 'scroll',
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']
/*                    type: 'scroll',
                    orient: 'vertical',
                    right: 10,
                    top: 40,
                    bottom: 20,
			        data:['营业库A','营业库B','营业库C','营业库D','渠道资源库']*/
			    },
			    toolbox: {
			        show : true,
			        x:120,
        			y:0,
			        feature : {
	                    restore: { //重置
	                        show: true
	                    },
	                    dataZoom: { //数据缩放视图
	                        show: true
	                    },
	                    saveAsImage: {//保存图片
	                        show: true
	                    },
	                    magicType: {//动态类型切换
	                        type: ['bar', 'line']
	                    },
				        dataView: { //数据视图
	                        show: true
	                    },
	                    myButtons:{//自定义按钮 danielinbiti,这里增加，selfbuttons可以随便取名字    
		                   show:true,//是否显示    
		                   title:'切换图例位置', //鼠标移动上去显示的文字    
		                   icon:'path://M432.45,595.444c0,2.177-4.661,6.82-11.305,6.82c-6.475,0-11.306-4.567-11.306-6.82s4.852-6.812,11.306-6.812C427.841,588.632,432.452,593.191,432.45,595.444L432.45,595.444z M421.155,589.876c-3.009,0-5.448,2.495-5.448,5.572s2.439,5.572,5.448,5.572c3.01,0,5.449-2.495,5.449-5.572C426.604,592.371,424.165,589.876,421.155,589.876L421.155,589.876z M421.146,591.891c-1.916,0-3.47,1.589-3.47,3.549c0,1.959,1.554,3.548,3.47,3.548s3.469-1.589,3.469-3.548C424.614,593.479,423.062,591.891,421.146,591.891L421.146,591.891zM421.146,591.891', //图标    
		                   option:{},
		                   onclick:function(param) {//点击事件,这里的param是chart的信息 底下有option   	
	                   			if(option.legend.orient) {
		                   			delete option.legend.orient;
		                   			delete option.legend.right;
		                   			delete option.legend.top;
		                   			option.legend.y='bottom';
		                   		} else {
		                   			option.legend.orient = 'vertical';
		                   			option.legend.right = 20;
		                   			option.legend.top = 40;
		                   		}
		                   		myChart.clear();
		                   		myChart.setOption(option);
		                   }    
		                }
			        }
			    },
				calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap: false,
			            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'营业库A',
			            type:'line',
			            data:[0, 0, 0, 0, 16970, 14747, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库B',
			            type:'line',
			            data:[0, 0, 0, 0, 18045, 15594, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库C',
			            type:'line',
			            data:[0, 0, 0, 0, 17468, 15024, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'营业库D',
			            type:'line',
			            data:[0, 0, 0, 0, 17909, 15358, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        },
			        {
			            name:'渠道资源库',
			            type:'line',
			            data:[0, 0, 0, 0, 19932, 19793, 4012, 0, 0, 0, 0, 0],
			            markLine : {
                			data : [{type : 'average', name: '平均值'}]
            			}
			        }
			    ]
			};
			Rose.ajax.postJson(srvMap.get("onlineTimeFind"), '', function(onlinejson, status) {
				if(status) {
					window.XMS.msgbox.hide();
				} else {
					XMS.msgbox.show(json.retMessage, 'error', 2000);
				}
				if(json && json.data) {
					option.legend.data = json.data.legend;
					option.series = json.data.series;
					if(json.data.xAxis) {
						var onlinePonint = new Array(100);
						for(var indexXAxis in json.data.xAxis){
							for(var indexOnline in onlinejson.data){
								if(json.data.xAxis[indexXAxis]==onlinejson.data[indexOnline]){
									json.data.xAxis[indexXAxis] += "上线";
									onlinePonint.push(indexXAxis);
								}
							}
						}
						option.xAxis[0].data = json.data.xAxis;
					}
					for(var indexSeries in option.series) {
						var markData = [];
						for(var indexPoint in onlinePonint){
							var pzs = parseInt(onlinePonint[indexPoint]);
                			var plan =  {name : '上线',value:option.series[indexSeries].data[pzs], xAxis: pzs, yAxis: option.series[indexSeries].data[pzs]};
	                		markData.push(plan);
						}
			            option.series[indexSeries].markPoint = {
			                data : markData
			            };
					}
					option.title.subtext=cache.deadline;
				}
				//加载前数据刷新
				myChart.clear();
				myChart.setOption(option);	
				myChart.hideLoading();//隐藏loading

				window.onresize = myChart.resize;
				Page.findId(echartsinitdom).resize(function(){
	                myChart.resize();             
	            });

  			});			
		}
	};
				 
	module.exports = init;
	
});
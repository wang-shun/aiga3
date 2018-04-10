package com.ai.aiga.view.controller.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.ArchFunctionRecord;
import com.ai.aiga.service.functionrecord.ArchFunctionRecordSv;
import com.ai.aiga.view.controller.common.dto.operationTimeInput;
import com.ai.aiga.view.controller.function.dto.FunctionRecord;
import com.ai.aiga.view.controller.function.dto.WeekDataOutPut;
import com.ai.aiga.view.controller.indexOperation.dto.EchartInfoOutput;
import com.ai.aiga.view.controller.indexOperation.dto.EchartViewSeries;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.SessionMgrUtil;

@Controller
@Api(value = "FunctionRecordController", description = "菜单使用记录api")
public class FunctionRecordController {
	@Autowired
	private ArchFunctionRecordSv archFunctionRecordSv;
	
	@RequestMapping(path = "/webservice/menuRecord/weekLog")
	@ApiOperation(value = "查询菜单近一周使用情况", notes = "查询菜单近一周使用情况", response=ArchFunctionRecord.class, httpMethod="GET")
	public @ResponseBody JsonBean weekLog(@RequestBody FunctionRecord request) throws ParseException{
		JsonBean bean = new JsonBean();
		List<String> xAxis = getMonthBetween(request.getStartTime(),request.getEndTime());
		EchartInfoOutput output = new EchartInfoOutput();
		//动态设置初始化的大小
		final int constValue = xAxis.size();
		List<WeekDataOutPut> result = archFunctionRecordSv.weekData(request);
		//横轴
		output.setXAxis(xAxis);
		//图例
		List<String> legend = new ArrayList<String>();
		for(WeekDataOutPut resultBase :result) {
			if(legend.contains(resultBase.getDataName())) {		
				//不做处理
			} else {
				legend.add(resultBase.getDataName());
			}
		}
		output.setLegend(legend);
		//y数据	
		List<EchartViewSeries> series = new ArrayList<EchartViewSeries>();
		for(String legendBase:legend) {
			int[] data = new int[constValue];
			EchartViewSeries seriesBase = new EchartViewSeries();
			seriesBase.setName(legendBase);
			seriesBase.setType("line");
			for(WeekDataOutPut resultInfo :result) {
				if(legendBase.equals(resultInfo.getDataName())) {
					for(int i = 0;i<xAxis.size();i++) {
						if(xAxis.get(i).equals(resultInfo.getDataTime())) {
							data[i] = (int)resultInfo.getDataNum();
						}
					}										
				}				
			}
			seriesBase.setData(data);
			series.add(seriesBase);
		}
		output.setSeries(series);
		bean.setData(output);
		return bean;
	}
	
	@RequestMapping(path = "/webservice/menuRecord/findByCondition")
	@ApiOperation(value = "查询菜单使用记录", notes = "查询所有的菜单使用记录", response=ArchFunctionRecord.class, httpMethod="GET")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			FunctionRecord request) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archFunctionRecordSv.findByConditionPage(request, pageNumber, pageSize));
		return bean;
	}
	
	
	@RequestMapping(path = "/webservice/menuRecord/topData")
	@ApiOperation(value = "查询菜单使用TOP10", notes = "查询菜单使用TOP10", response=ArchFunctionRecord.class, httpMethod="GET")
	public @ResponseBody JsonBean topData(String type) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archFunctionRecordSv.queryTopData(type));
		return bean;
	}
	@RequestMapping(path = "/webservice/menuCheck/grant")
	@ApiOperation(value = "菜单权限校验", notes = "菜单权限校验", httpMethod="GET")
	public @ResponseBody JsonBean grant(String menuName){
		JsonBean bean = new JsonBean();
		try {
			AigaStaff staffInfo = SessionMgrUtil.getStaff();
			if(staffInfo == null) {
				bean.fail("获取用户信息失败");
				return bean;
			}
			bean.setData(archFunctionRecordSv.menuGrant(staffInfo,menuName));
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	}
	@RequestMapping(path = "/webservice/menuRecord/add")
	@ApiOperation(value = "添加菜单使用记录", notes = "添加菜单使用记录", httpMethod="POST")
	public @ResponseBody JsonBean add(@RequestBody ArchFunctionRecord request){
		JsonBean bean = new JsonBean();
		try {
			AigaStaff staffInfo = SessionMgrUtil.getStaff();
			if(staffInfo == null) {
				bean.fail("获取用户信息失败");
				return bean;
			}
			request.setRecordTime(new Date());
			request.setUserCode(staffInfo.getCode());
			request.setUserName(staffInfo.getName());
			archFunctionRecordSv.save(request);
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	}
    private List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月  
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
	    min.setTime(sdf.parse(minDate));	
	    max.setTime(sdf.parse(maxDate));
	    if(max.before(min)) {
	    	return result;
	    }
	    Calendar curr = min;
	    while (curr.before(max)) {
	    	result.add(sdf.format(curr.getTime()));
	    	curr.add(Calendar.DATE, 1);
	    }
	    result.add(sdf.format(max.getTime()));
	    return result;
	}
}

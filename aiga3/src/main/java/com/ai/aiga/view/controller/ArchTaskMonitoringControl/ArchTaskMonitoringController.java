package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.text.ParseException;

import com.ai.aiga.service.archmonitoringtask.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.archmonitoringtask.ArchTaskMonitoringSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "TaskMonitoringController", description = "平台任务监控")
public class ArchTaskMonitoringController {

	@Autowired
	private ArchTaskMonitoringSv archTaskMonitoringSv;

	//以下视图
	@RequestMapping(path="/arch/taskMonitoring/queryByCondition")
	public @ResponseBody JsonBean queryByCondition( ArchTaskMonitoring condition) throws ParseException{
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryByCondition(condition));
			return bean;
	}

	@RequestMapping(path="/arch/taskNumCount/queryByTime")
	public @ResponseBody JsonBean queryTaskClassSuccess(ArchTaskMonitoringByTime condition2) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskCount(condition2));
		return bean;
	}

	@RequestMapping(path="/arch/getTaskFrequencyList/queryByFrequency")
	public @ResponseBody JsonBean queryTaskFrequencyAndTimes(ArchTaskMonitoringByFrequencyAndTimes condition3) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskByFrequency(condition3));
		return bean;
	}

	@RequestMapping(path="/arch/TaskTimes/queryByTimes")
	public @ResponseBody JsonBean queryTaskTimes(ArchTaskMonitoringByFrequencyAndTimes condition4) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskByTimes(condition4));
		return bean;
	}


	//以下表格
	@RequestMapping(path="/arch/TableListFirst/findTableListFirst")
	public @ResponseBody JsonBean findTableListFirst(ArchTaskMonitoringTable condition1) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTable(condition1));
		System.out.println("bean----------------------"+bean);
		return bean;
	}

	@RequestMapping(path="/arch/TableListSecond/findTableListSecond")
	public @ResponseBody JsonBean findTableListSecond(ArchTaskMonitoringTableSecond condition2) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTableSecond(condition2));
		return bean;
	}

	@RequestMapping(path="/arch/TableListThird/findTableListThird")
	public @ResponseBody JsonBean findTableListThird(ArchTaskMonitoringTableThird condition3) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTableThird(condition3));
		return bean;
	}

	//以下Top
	@RequestMapping(path="/arch/TopListFirst/findTopListFirst")
	public @ResponseBody JsonBean findTopListFirst(ArchTaskMonitoringTopFirst condition1) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTopFirst(condition1));
		return bean;
	}


}

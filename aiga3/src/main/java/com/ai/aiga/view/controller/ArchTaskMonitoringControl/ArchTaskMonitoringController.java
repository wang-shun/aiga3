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
	@RequestMapping(path="/arch/TableList/findTableList")
	public @ResponseBody JsonBean findTableList(ArchTaskMonitoringTable condition) throws ParseException {
		JsonBean bean = new JsonBean();
		System.out.println("Controller  ---------- condition.getCondition():------------------"+condition.getCondition());
		if(condition.getCondition().equals("failTaskList")){
			bean.setData(archTaskMonitoringSv.queryByConditionTable(condition));
			System.out.println("Controller  bean1----------------------"+bean);
		}else if(condition.getCondition().equals("taskRunningFrequency")){
			bean.setData(archTaskMonitoringSv.queryByConditionTableSecond(condition));
			System.out.println("Controller  bean2----------------------"+bean);
		}else if(condition.getCondition().equals("taskRunInTime")){
			bean.setData(archTaskMonitoringSv.queryByConditionTableThird(condition));
			System.out.println("Controller  bean3----------------------"+bean);
		}



		return bean;
	}

//	@RequestMapping(path="/arch/TableListSecond/findTableListSecond")
//	public @ResponseBody JsonBean findTableListSecond(ArchTaskMonitoringTableSecond condition2) throws ParseException {
//		JsonBean bean = new JsonBean();
//		bean.setData(archTaskMonitoringSv.queryByConditionTableSecond(condition2));
//		return bean;
//	}
//
//	@RequestMapping(path="/arch/TableListThird/findTableListThird")
//	public @ResponseBody JsonBean findTableListThird(ArchTaskMonitoringTableThird condition3) throws ParseException {
//		JsonBean bean = new JsonBean();
//		bean.setData(archTaskMonitoringSv.queryByConditionTableThird(condition3));
//		return bean;
//	}

	//以下Top
	@RequestMapping(path="/arch/TopListFirst/findTopListFirst")
	public @ResponseBody JsonBean findTopListFirst(ArchTaskMonitoringTop condition) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTop(condition));
		return bean;
	}


}

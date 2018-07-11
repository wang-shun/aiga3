package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.text.ParseException;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.*;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.ArchTaskMonitoringSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "TaskMonitoringController", description = "平台任务监控")
public class ArchTaskMonitoringController {

	@Autowired
	private ArchTaskMonitoringSv archTaskMonitoringSv;

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

	@RequestMapping(path="/arch/TableListFirst/findTableListFirst")
	public @ResponseBody JsonBean findTableListFirst(ArchTaskMonitoringTable condition5) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTable(condition5));
		return bean;
	}

	@RequestMapping(path="/arch/TableListSecond/findTableListSecond")
	public @ResponseBody JsonBean findTableListSecond(ArchTaskMonitoringTableSecond condition6) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTableSecond(condition6));
		return bean;
	}

	@RequestMapping(path="/arch/TableListThird/findTableListThird")
	public @ResponseBody JsonBean findTableListThird(ArchTaskMonitoringTableThird condition7) throws ParseException {
		System.out.println("Sv-------condition7:_______________---------------_"+condition7);
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTableThird(condition7));
		System.out.println("Sv7----bean.toString():-------------"+bean.toString());
		return bean;
	}

	@RequestMapping(path="/arch/TableListFour/findTableListFour")
	public @ResponseBody JsonBean findTableListFour(ArchTaskMonitoringTableFour condition8) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryByConditionTableFour(condition8));
		return bean;
	}


}

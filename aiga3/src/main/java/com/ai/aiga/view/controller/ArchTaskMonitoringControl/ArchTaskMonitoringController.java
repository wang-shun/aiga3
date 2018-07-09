package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.text.ParseException;

import com.ai.aiga.domain.ArchTaskMonitoringByTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.domain.ArchTaskMonitoring;
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
			System.out.println("进入Controller1***************");
			System.out.println("condion:       "+condition);
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryByCondition(condition));
			System.out.println("bean:**********************"+bean.toString());
			return bean;
	}

	@RequestMapping(path="/arch/taskNumCount/queryByTime")
	public @ResponseBody JsonBean queryTaskClassSuccess(ArchTaskMonitoringByTime condition2) throws ParseException{
		System.out.println("进入Controller2***************");
		System.out.println("condion2:       "+condition2);
		JsonBean bean = new JsonBean();
		bean.setData(archTaskMonitoringSv.queryTaskCount(condition2));
		System.out.println("bean:**************box********"+bean.toString());
		return bean;
	}

}

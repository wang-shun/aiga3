package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.text.ParseException;
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
	public @ResponseBody JsonBean queryByCondition(
            ArchTaskMonitoring condition) throws ParseException{
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryByCondition(condition));
			return bean;
	}
	
/*	@RequestMapping(path="/arch/taskClassSuccess/queryByCondition")
	public @ResponseBody JsonBean queryTaskClassSuccess(
            ArchTaskMonitoring condition) throws ParseException{
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryTaskClassSuccess(condition));
			return bean;
	}*/

}

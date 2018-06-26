package com.ai.aiga.view.controller.ArchTaskMonitoringControl;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
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
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchTaskMonitoring condition) throws ParseException{
			JsonBean bean = new JsonBean();
			bean.setData(archTaskMonitoringSv.queryByCondition(condition, pageNumber, pageSize));
			return bean;
	}

}

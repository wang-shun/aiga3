package com.ai.aiga.view.controller.inspectradar;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.inspectradar.InspectRadarResultSv;
import com.ai.aiga.service.inspectradar.InspectRadarRuleSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "InspectRadarRuleController", description = "巡检雷达巡检规则相关api")
public class InspectRadarRuleController {
	@Autowired
	private InspectRadarRuleSv inspectRadarRuleSv;
	
	@RequestMapping(path="/webservice/radar/sysList")
	public @ResponseBody JsonBean recentRecord(){
		JsonBean bean = new JsonBean();
		bean.setData(inspectRadarRuleSv.sysList());
		return bean;
	}
	
}

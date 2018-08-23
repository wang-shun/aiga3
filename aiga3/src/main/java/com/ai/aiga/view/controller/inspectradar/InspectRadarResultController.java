package com.ai.aiga.view.controller.inspectradar;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.inspectradar.InspectRadarResultSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "InspectRadarResultController", description = "巡检雷达巡检结果相关api")
public class InspectRadarResultController {
	@Autowired
	private InspectRadarResultSv inspectRadarResultSv;
	
	@RequestMapping(path="/webservice/radar/recentRecord")
	public @ResponseBody JsonBean recentRecord(Long sysId){
		JsonBean bean = new JsonBean();
		if(sysId == null||sysId ==0L) {
			bean.fail("请传入系统ID");
		}
		bean.setData(inspectRadarResultSv.recentRecord(sysId));
		return bean;
	}
	
	@RequestMapping(path="/webservice/radar/historyRecord")
	public @ResponseBody JsonBean historyRecord(Long sysId){
		JsonBean bean = new JsonBean();
		if(sysId == null||sysId ==0L) {
			bean.fail("请传入系统ID");
		}
		bean.setData(inspectRadarResultSv.historyRecord(sysId));
		return bean;
	}
	
}

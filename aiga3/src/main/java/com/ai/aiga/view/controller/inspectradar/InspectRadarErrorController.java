package com.ai.aiga.view.controller.inspectradar;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.QuestionInfoSv;
import com.ai.aiga.service.inspectradar.InspectRadarErrorSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "InspectRadaErrorController", description = "巡检雷达巡检不合格相关api")
public class InspectRadarErrorController {
	@Autowired
	private InspectRadarErrorSv inspectRadarErrorSv;
	@Autowired
	private QuestionInfoSv questionInfoSv;
	
	@RequestMapping(path="/webservice/radar/sysRecentError")
	public @ResponseBody JsonBean sysRecentError(Long sysId){
		JsonBean bean = new JsonBean();
		if(sysId == null||sysId ==0L) {
			bean.fail("请传入系统ID");
		}
		bean.setData(inspectRadarErrorSv.sysRecentError(sysId));
		return bean;
	}
}

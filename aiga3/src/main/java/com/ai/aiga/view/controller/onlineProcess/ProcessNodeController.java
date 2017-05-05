package com.ai.aiga.view.controller.onlineProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.onlineProcess.NodeRecordSv;
import com.ai.aiga.view.controller.onlineProcess.dto.NodeRecordRequest;
import com.ai.aiga.view.controller.team.dto.TeamInfoRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ProcessNodeController {
	@Autowired
	private NodeRecordSv nodeRecordSv;
	

	@RequestMapping(path = "/process/node/saveChangeBegin")
	public @ResponseBody JsonBean saveChangeBegin(String onlinePlanName) {
		nodeRecordSv.saveChangeBegin(onlinePlanName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/process/node/saveChangeInteraction")
	public @ResponseBody JsonBean saveChangeInteraction(String onlinePlanName) {
		nodeRecordSv.saveChangeInteraction(onlinePlanName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/process/node/saveCompile")
	public @ResponseBody JsonBean saveCompile(String onlinePlanName) {
		nodeRecordSv.saveCompile(onlinePlanName);
		return JsonBean.success;
	}
	
	
	@RequestMapping(path = "/process/node/saveCheck")
	public @ResponseBody JsonBean saveCheck(String onlinePlanName) {
		nodeRecordSv.saveCheck(onlinePlanName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/process/node/saveReview")
	public @ResponseBody JsonBean saveReview(String onlinePlanName) {
		nodeRecordSv.saveReview(onlinePlanName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/process/node/saveRelease")
	public @ResponseBody JsonBean saveRelease(String onlinePlanName) {
		nodeRecordSv.saveRelease(onlinePlanName);
		return JsonBean.success;
	}
	
	
	@RequestMapping(path = "/process/node/saveVerification")
	public @ResponseBody JsonBean saveVerification(String onlinePlanName) {
		nodeRecordSv.saveVerification(onlinePlanName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/process/node/saveSummary")
	public @ResponseBody JsonBean saveSummary(String onlinePlanName) {
		nodeRecordSv.saveRelease(onlinePlanName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/process/node/update")
	public @ResponseBody JsonBean update(Long onlinePlan ,Long node) {
		nodeRecordSv.update(onlinePlan,node);
		return JsonBean.success;
	}
	@RequestMapping(path = "/process/node/commit")
	public @ResponseBody JsonBean commit(Long onlinePlan ,Long node) {
		nodeRecordSv.commit(onlinePlan,node);
		return JsonBean.success;
	}
   
}

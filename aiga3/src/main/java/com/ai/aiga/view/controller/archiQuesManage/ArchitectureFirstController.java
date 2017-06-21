package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchitectureFirstController", description = "架构分层相关api")
public class ArchitectureFirstController {

	@Autowired
	private ArchitectureFirstSv architectureFirstSv;
	
	@RequestMapping(path = "/archi/first/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureFirstSv.findArchitectureFirsts());
		return bean;
	} 
	
	@RequestMapping(path = "/archi/first/findOne")
	public @ResponseBody JsonBean findOne(
			@RequestParam Long idFirst){
		JsonBean bean = new JsonBean();
		bean.setData(architectureFirstSv.findOne(idFirst));
		return bean;
	}
	
	@RequestMapping(path = "/archi/first/save")
	public @ResponseBody JsonBean save(ArchitectureFirstRequest architectureFirstRequest){
		architectureFirstSv.save(architectureFirstRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/first/update")
	public @ResponseBody JsonBean update(ArchitectureFirstRequest architectureFirstRequest){
		architectureFirstSv.update(architectureFirstRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/first/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long idFirst){
		architectureFirstSv.delete(idFirst);
		return JsonBean.success;
	}

}
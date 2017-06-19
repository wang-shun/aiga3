package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchitectureThirdController", description = "架构分层相关api")
public class ArchitectureThirdController {

	@Autowired
	private ArchitectureThirdSv architectureThirdSv;
	
	@RequestMapping(path = "/archi/third/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findArchitectureThirds());
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findOne")
	public @ResponseBody JsonBean findOne(
				@RequestParam Long idThird){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findOne(idThird));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/save")
	public @ResponseBody JsonBean save(ArchitectureThirdRequest architectureThirdRequest){
		architectureThirdSv.save(architectureThirdRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/third/update")
	public @ResponseBody JsonBean update(ArchitectureThirdRequest architectureThirdRequest){
		architectureThirdSv.update(architectureThirdRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/third/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long idThird){
		architectureThirdSv.delete(idThird);
		return JsonBean.success;
	}
}

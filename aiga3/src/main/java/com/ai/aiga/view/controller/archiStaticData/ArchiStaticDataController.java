package com.ai.aiga.view.controller.archiStaticData;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiStaticDataController", description = "架构分层相关api")
public class ArchiStaticDataController {
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	@RequestMapping(path = "/archi/static/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findAll());
		return bean;
	} 

}

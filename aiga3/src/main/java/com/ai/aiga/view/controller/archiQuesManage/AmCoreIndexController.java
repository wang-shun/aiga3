package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.AmCoreIndexSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "AmCoreIndexController", description = "指标主表")
public class AmCoreIndexController {

	@Autowired
	private AmCoreIndexSv amCoreIndexSv;
	
	@RequestMapping(path = "/archi/index/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.findAmCoreIndex());
		return bean;
	}
}

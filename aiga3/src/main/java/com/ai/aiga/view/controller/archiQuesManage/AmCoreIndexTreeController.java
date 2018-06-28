package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.AmCoreIndexTreeSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "AmCoreIndexTreeController", description = "指标主表")
public class AmCoreIndexTreeController {

	@Autowired
	private AmCoreIndexTreeSv amCoreIndexTreeSv;

	@RequestMapping(path = "/index/tree/findAllIndexByDay")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexTreeSv.findAllIndexByDay());
		return bean;
	}
	@RequestMapping(path = "/index/tree/findAllIndexByMonth")
	public @ResponseBody JsonBean findAll2(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexTreeSv.findAllIndexByMonth());
		return bean;
	}
	@RequestMapping(path = "/index/tree/findByGroupId")
	public @ResponseBody JsonBean findByGroupId(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexTreeSv.findByGroupId(1002L));
		return bean;
	}
}

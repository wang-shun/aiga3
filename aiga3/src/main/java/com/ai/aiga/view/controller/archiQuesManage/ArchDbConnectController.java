package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchDbConnectSv;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.base.JsonBean;
@Controller
@Api(value = "ArchDbConnectController", description = "指标分表")
public class ArchDbConnectController extends BaseService {

	@Autowired
	private ArchDbConnectSv archDbConnectSv;
	
	@RequestMapping(path = "/archi/dbconnect/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectSv.findArchDbConnect());
		return bean;
	}
}

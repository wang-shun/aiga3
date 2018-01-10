package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchitectureFirstSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureFirstRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiFirstController", description = "架构一级域相关api")
public class ArchiFirstController {

	@Autowired
	private ArchitectureFirstSv architectureFirstSv;
	/**
	 * 查询一级域
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiFirst/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureFirstSv.findArchitectureFirsts());
		return bean;
	} 
	/**
	 * 查询一级域，带分业
	 * @return
	 */
	@RequestMapping(path = "/webservice/archiFirst/listPage")
	public @ResponseBody JsonBean listPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean bean = new JsonBean();
		bean.setData(architectureFirstSv.findArchitectureFirstsPage(pageNumber, pageSize));
		return bean;
	} 

}

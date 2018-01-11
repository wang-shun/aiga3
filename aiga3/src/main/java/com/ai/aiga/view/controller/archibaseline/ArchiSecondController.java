package com.ai.aiga.view.controller.archibaseline;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchitectureSecondSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiSecondController", description = "架构二级子域相关api")
public class ArchiSecondController {

	@Autowired
	private ArchitectureSecondSv architectureSecondSv;
	
	@RequestMapping(path = "/archi/second/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureSecondSv.findArchitectureSeconds());
		return bean;
	} 
	
	@RequestMapping(path = "/archi/second/listByfirstPage")
	public @ResponseBody JsonBean listByfirstPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            Long idFirst,String name){
		JsonBean bean = new JsonBean();
		bean.setData(architectureSecondSv.findByFirstPage(idFirst, name, pageNumber, pageSize));
		return bean;
	} 
	
	@RequestMapping(path = "/archi/second/listByfirst")
	public @ResponseBody JsonBean listByfirst(Long idFirst){
		JsonBean bean = new JsonBean();
		if(idFirst!= null && idFirst>0) {
			bean.setData(architectureSecondSv.findArchiSecondsByFirst(idFirst));
		} else {
			bean.setData(architectureSecondSv.findArchitectureSeconds());
		}	
		return bean;
	} 
	
}

package com.ai.am.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.service.ArchitectureSecondSv;
import com.ai.am.view.controller.archiQuesManage.dto.ArchitectureSecondRequest;
import com.ai.am.view.json.base.JsonBean;

@Controller
@Api(value = "ArchitectureSecondController", description = "架构分层相关api")
public class ArchitectureSecondController {

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
            long idFirst){
		JsonBean bean = new JsonBean();
		bean.setData(architectureSecondSv.findByFirstPage(idFirst, pageNumber, pageSize));
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
	
	@RequestMapping(path = "/archi/second/findOne")
	public @ResponseBody JsonBean findOne(
			@RequestParam Long idSecond){
		JsonBean bean = new JsonBean();
		bean.setData(architectureSecondSv.findOne(idSecond));
		return bean;
	}
	
	@RequestMapping(path = "/archi/second/save")
	public @ResponseBody JsonBean save(ArchitectureSecondRequest architectureSecondRequest){
		architectureSecondSv.save(architectureSecondRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/second/update")
	public @ResponseBody JsonBean update(ArchitectureSecondRequest architectureSecondRequest){
		architectureSecondSv.update(architectureSecondRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/second/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long idSecond){
		architectureSecondSv.delete(idSecond);
		return JsonBean.success;
	}
}

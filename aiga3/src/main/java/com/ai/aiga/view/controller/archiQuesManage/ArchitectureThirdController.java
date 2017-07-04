package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchiThirdConditionParam;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchitectureThirdRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;
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
	
	@RequestMapping(path="/archi/question/findByConditionPage")
	public @ResponseBody JsonBean findByConditionPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchiThirdConditionParam condition){
				JsonBean bean = new JsonBean();
				bean.setData(architectureThirdSv.findbyCoditionPage(condition.getIdThird(), condition.getName(), pageNumber, pageSize));
			return bean;
	}
	
	@RequestMapping(path = "/archi/third/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchiThirdConditionParam input){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findbyCodition(input.getIdThird(), input.getName()));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findBySecPage")
	public @ResponseBody JsonBean findBySecPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            Long idSecond){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findbySecPage(idSecond, pageNumber, pageSize));

		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findBySec")
	public @ResponseBody JsonBean findBySec(Long idSecond){
		JsonBean bean = new JsonBean();
		if(idSecond != null && idSecond>0) {
			bean.setData(architectureThirdSv.findbySec(idSecond));
		} else {
			bean.setData(architectureThirdSv.findArchitectureThirds());
		}
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/findOne")
	public @ResponseBody JsonBean findOne(
				@RequestParam Long onlysysId){
		JsonBean bean = new JsonBean();
		bean.setData(architectureThirdSv.findOne(onlysysId));
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
				@RequestParam Long onlysysId){
		architectureThirdSv.delete(onlysysId);
		return JsonBean.success;
	}
}

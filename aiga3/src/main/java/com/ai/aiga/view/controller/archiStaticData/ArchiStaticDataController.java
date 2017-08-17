package com.ai.aiga.view.controller.archiStaticData;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiStaticDataController", description = "静态数据api")
public class ArchiStaticDataController {
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	@RequestMapping(path = "/archi/static/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findAll());
		return bean;
	} 
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/type")
	public @ResponseBody JsonBean type(String codeType){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type和Value查询静态数据
	@RequestMapping(path = "/archi/static/typeAndValue")
	public @ResponseBody JsonBean typeAndValue(String codeType,String codeValue){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findByCodeTypeAndCodeValue(codeType, codeValue));
		return bean;
	} 

}

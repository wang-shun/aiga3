package com.ai.am.view.controller.archiStaticData;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.service.ArchitectureStaticDataSv;
import com.ai.am.view.json.base.JsonBean;

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

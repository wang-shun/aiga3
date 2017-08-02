package com.ai.aiga.view.controller.archiStaticData;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchibuildingStateController", description = "架构分层相关api")
public class ArchibuildingStateController {
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/archiBuildingState")
	public @ResponseBody JsonBean type(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_BUILDING_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/archiBelongSystem")
	public @ResponseBody JsonBean getBelongSystem(){
		JsonBean bean = new JsonBean();
		String codeType = "ARCH_BELONG_SYSTEM";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/archiDealApartment")
	public @ResponseBody JsonBean getDealApartment(){
		JsonBean bean = new JsonBean();
		String codeType = "ARCH_DEAL_APARTMENT";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/archiProductState")
	public @ResponseBody JsonBean getProductState(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_PRODUCT_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/archiQuestionState")
	public @ResponseBody JsonBean getQuestionState(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_QUESTION_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/archiQuesCategory")
	public @ResponseBody JsonBean getQuesCategory(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_QUESTION_CATEGORY";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/archi/static/eventState")
	public @ResponseBody JsonBean getEventState(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_EVENT_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
}

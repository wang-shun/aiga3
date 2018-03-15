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
public class StaticTnterfaceController {
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	//根据Type查询连接池配置业务系统
	@RequestMapping(path = "/webservice/static/businessSystem")
	public @ResponseBody JsonBean businessSystem(){
		JsonBean bean = new JsonBean();
		String codeType = "POOLCONFIGURATION_PRO_BUSINESS";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询连接池配置查询状态
	@RequestMapping(path = "/webservice/static/queryState")
	public @ResponseBody JsonBean queryState(){
		JsonBean bean = new JsonBean();
		String codeType = "POOLCONFIGURATION_PRO_QUERYSTATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询一级数据库
		@RequestMapping(path = "/webservice/static/primaryDatabase")
		public @ResponseBody JsonBean primaryDatabase(){
			JsonBean bean = new JsonBean();
			String codeType = "FLUCTUATION_PRO_PRIMARY";
			bean.setData(architectureStaticDataSv.findByCodeType(codeType));
			return bean;
		}
	
	//根据Type查询静态数据分类
	@RequestMapping(path = "/webservice/static/workplanState")
	public @ResponseBody JsonBean workplanState(){
		JsonBean bean = new JsonBean();
		String codeType = "WORKPLAN_PRO_CLASSIFICATION";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	//根据Type查询静态数据工作状态
	@RequestMapping(path = "/webservice/static/workState")
	public @ResponseBody JsonBean workState(){
		JsonBean bean = new JsonBean();
		String codeType = "WORKPLAN_PRO_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	//根据Type查询静态数据优先级
	@RequestMapping(path = "/webservice/static/priorityList")
	public @ResponseBody JsonBean priorityList(){
		JsonBean bean = new JsonBean();
		String codeType = "WORKPLAN_PRO_PRIORITY";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	}
	//查询 系统建设状态
	@RequestMapping(path = "/webservice/static/archiBuildingState")
	public @ResponseBody JsonBean type(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_BUILDING_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/static/archiDealApartment")
	public @ResponseBody JsonBean getDealApartment(){
		JsonBean bean = new JsonBean();
		String codeType = "ARCH_DEAL_APARTMENT";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/static/archiProductState")
	public @ResponseBody JsonBean getProductState(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_PRODUCT_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/static/archiQuestionState")
	public @ResponseBody JsonBean getQuestionState(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_QUESTION_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/static/archiQuesCategory")
	public @ResponseBody JsonBean getQuesCategory(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_QUESTION_CATEGORY";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/static/archiFileCategory")
	public @ResponseBody JsonBean getFileCategory(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_FILE_CATEGORY";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/static/eventState")
	public @ResponseBody JsonBean getEventState(){
		JsonBean bean = new JsonBean();
		String codeType = "SYS_EVENT_STATE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	//查询系统等级信息
	@RequestMapping(path = "/webservice/static/rankInfo")
	public @ResponseBody JsonBean getRankInfo(){
		JsonBean bean = new JsonBean();
		String codeType = "SYSTEM_RANK_INFO";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//获取报表时间类型
	@RequestMapping(path = "/webservice/static/logReportTimeType")
	public @ResponseBody JsonBean getLogReportTimeType(){
		JsonBean bean = new JsonBean();
		String codeType = "LOGREPORT_TIME_TYPE";
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//获取指定时间类型下报表模板
	@RequestMapping(path = "/webservice/static/getLogReportModel")
	public @ResponseBody JsonBean getLogReportModel(String reportType){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findByCodeType(reportType));
		return bean;
	}
}

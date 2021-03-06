package com.ai.aiga.view.controller.archiStaticData;

import java.text.ParseException;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchitectureStaticData;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiStaticDataController", description = "静态数据api")
public class ArchiStaticDataController {
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	@RequestMapping(path = "/webservice/archiStaticData/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findAll());
		return bean;
	} 
	//根据Type查询静态数据
	@RequestMapping(path = "/webservice/archiStaticData/type")
	public @ResponseBody JsonBean type(String codeType){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findByCodeType(codeType));
		return bean;
	} 
	
	//根据Type和Value查询静态数据
	@RequestMapping(path = "/webservice/archiStaticData/typeAndValue")
	public @ResponseBody JsonBean typeAndValue(String codeType,String codeValue){
		JsonBean bean = new JsonBean();
		bean.setData(architectureStaticDataSv.findByCodeTypeAndCodeValue(codeType, codeValue));
		return bean;
	} 
	@RequestMapping(path = "/webservice/archiStaticData/save")
	public @ResponseBody JsonBean save(ArchitectureStaticData request){
		architectureStaticDataSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/webservice/archiStaticData/delete")
	public @ResponseBody JsonBean delete(long dataId){
		architectureStaticDataSv.delete(dataId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/webservice/archiStaticData/update")
	public @ResponseBody JsonBean update(ArchitectureStaticData request){
		JsonBean bean = new JsonBean();
		architectureStaticDataSv.update(request);
		return bean;
	}
	@RequestMapping(path="/webservice/archiStaticData/queryStaticData")
	public @ResponseBody JsonBean findAllByPage(
        @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
        ArchitectureStaticData request){
			JsonBean bean = new JsonBean();
			bean.setData(architectureStaticDataSv.findAllByPage(request, pageNumber, pageSize));
		return bean;
	}

}

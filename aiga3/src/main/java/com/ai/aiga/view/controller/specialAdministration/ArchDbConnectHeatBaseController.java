package com.ai.aiga.view.controller.specialAdministration;

import java.text.ParseException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.ArchDbConnectHeatBaseSv;
import com.ai.aiga.service.ArchitectureStaticDataSv;
import com.ai.aiga.view.controller.specialAdministration.dto.ArchDbConnectHeatBaseSelects;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchDbConnectHeatBaseController", description = "专项治理控制层")
public class ArchDbConnectHeatBaseController {

	@Autowired
	private ArchDbConnectHeatBaseSv archDbConnectHeatBaseSv;
	@Autowired
	private ArchitectureStaticDataSv architectureStaticDataSv;
	
	@RequestMapping(path="/webservice/dbconnect/heatbasequery")
	public @ResponseBody JsonBean heatbasequery(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            ArchDbConnectHeatBaseSelects condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archDbConnectHeatBaseSv.queryHeatBase(condition, pageNumber, pageSize));
			return bean;
	}
	
	@RequestMapping(path="/webservice/dbconnect/heatbasequeryDetail")
	public @ResponseBody JsonBean heatbasequeryDetail(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			ArchDbConnectHeatBaseSelects condition) throws ParseException{
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectHeatBaseSv.queryDetail(condition, pageNumber, pageSize));
		return bean;
	}	
	
	@RequestMapping(path = "/webservice/dbconnect/heatbaseselect1")
	public @ResponseBody JsonBean heatbaseselect1(){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectHeatBaseSv.select1());
		return bean;
	}
	
	@RequestMapping(path = "/webservice/dbconnect/heatbaseselect2")
	public @ResponseBody JsonBean heatbaseselect2(ArchDbConnectHeatBaseSelects condition){
		JsonBean bean = new JsonBean();
		bean.setData(archDbConnectHeatBaseSv.select2(condition));
		return bean;
	}
	
	//根据Type查询连接池配置查询状态
	@RequestMapping(path = "/webservice/dbconnect/heatbasequeryState")
	public @ResponseBody JsonBean queryState(){
		JsonBean bean = new JsonBean();
		String codeType = "ARCH_HEAT_BASE_VALUE";
		bean.setData(archDbConnectHeatBaseSv.findByCodeType(codeType));
		return bean;
	} 
}

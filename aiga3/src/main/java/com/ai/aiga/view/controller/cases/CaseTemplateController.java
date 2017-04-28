package com.ai.aiga.view.controller.cases;

import javax.validation.Valid;

import com.ai.aiga.domain.AigaEsbInterface;
import com.ai.aiga.service.cases.EsbInterfaceSv;
import com.ai.aiga.view.json.cases.EsbInterfaceRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.service.cases.CaseTemplateSv;
import com.ai.aiga.view.json.cases.TemplateRequest;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.WebValidUtil;

@Controller
public class CaseTemplateController {
	
	@Autowired
	private CaseTemplateSv caseTemplateSv;
	
	@Autowired
	private EsbInterfaceSv esbInterfaceSv;
	
	@RequestMapping(path = "/case/template/list" )
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaCaseTemplate condition){
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.listTmeplate(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/case/template/save" )
	public @ResponseBody JsonBean save(@Valid TemplateRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		caseTemplateSv.saveTmeplate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/template/update" )
	public @ResponseBody JsonBean update(@Valid TemplateRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		caseTemplateSv.updateTmeplate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/template/del" )
	public @ResponseBody JsonBean del(Long caseId){
		caseTemplateSv.delTmeplate(caseId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/template/get" )
	public @ResponseBody JsonBean get(Long caseId){
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.getTmeplate(caseId));
		return bean;
	}

	@RequestMapping(path = "/case/template/getFactor" )
	@ApiOperation(value="获取因子",notes = "根据接口信息解析报文获取因子")
	public @ResponseBody JsonBean getFactor(
			@ApiParam(name = "messageId",value = "接口对应主键ID") Long messageId,
			@ApiParam(name="interfaceType",value = "接口类型") Long interfaceType)throws Exception{
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.getFactor(messageId,interfaceType));
		return bean;
	}
	
	@RequestMapping(path="/case/template/EsbList",method = {RequestMethod.GET,RequestMethod.POST})
	@ApiOperation(value="获取esb报文",notes = "根据分页查询ESB报文信息")
	public @ResponseBody JsonBean listEsb(
			@ApiParam(name = "page",value = "页码")@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@ApiParam(name = "pageSize",value = "每页数量") @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			@ApiParam(name = "condition",value = "查询条件") EsbInterfaceRequest condition){
		JsonBean bean = new JsonBean();
		bean.setData(esbInterfaceSv.listByPage(condition,pageNumber,pageSize));
		return bean;
	}
	
	
	
	
	

}

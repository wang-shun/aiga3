package com.ai.aiga.view.controller.cases;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.cases.CaseSv;
import com.ai.aiga.view.json.CaseInstanceRequest;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.WebValidUtil;

@Controller
public class CaseInstanceController {
	
	@Autowired
	private CaseSv caseSv;
	
	@RequestMapping(path = "/case/instance/toAutoCaseGenerate")
	public String goToEdit(){
		return "view/caseInstanceMng/caseInstanceToAutoCase";
	}
	
	@RequestMapping(path = "/case/instance/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "pageNumber", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			@RequestParam(value = "funId", defaultValue = "0") int funId,
			@RequestParam(value = "sysId", defaultValue = "0") int sysId,
			@RequestParam(value = "sysSubId", defaultValue = "0") int sysSubId,
			@RequestParam(value = "testName", defaultValue = "") String testName,
			@RequestParam(value = "important", defaultValue = "0") int important
			){
		JsonBean bean = new JsonBean();
		bean.setData(caseSv.listCase(sysId, sysSubId, funId, testName, important, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/case/instance/save" )
	public @ResponseBody JsonBean save(@Valid CaseInstanceRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		caseSv.saveTest(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/update" )
	public @ResponseBody JsonBean update(@Valid CaseInstanceRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		caseSv.updateTest(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/copy" )
	public @ResponseBody JsonBean copy(Long testId, String testName){
		caseSv.copyTest(testId, testName);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/del" )
	public @ResponseBody JsonBean del(@RequestParam List<Long> caseIds){
		caseSv.delCase(caseIds);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/get" )
	public @ResponseBody JsonBean get(Long testId){
		JsonBean bean = new JsonBean();
		bean.setData(caseSv.getCaseTest(testId));
		return bean;
	}
	
	
	

}

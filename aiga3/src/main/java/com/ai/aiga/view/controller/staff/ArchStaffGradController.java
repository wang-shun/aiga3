package com.ai.aiga.view.controller.staff;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchStaffGrad;
import com.ai.aiga.service.staff.ArchStaffGradSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ArchStaffGradController {
	@Autowired
	private ArchStaffGradSv archStaffGradSv;
	@RequestMapping(path = "/staff/info/findApply")
	public @ResponseBody JsonBean findApply(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean bean = new JsonBean();
		bean.setData(archStaffGradSv.findApply(pageNumber,pageSize));
		return bean;
	}

	@RequestMapping(path = "/staff/info/rejet")
	public @ResponseBody JsonBean rejet(ArchStaffGrad request){
		JsonBean bean = new JsonBean();
		try {
			archStaffGradSv.reject(request);
		} catch (Exception e) {
			bean.fail(e.getMessage());	
		}
		return bean;
	}
	
	@RequestMapping(path = "/staff/info/apply")
	public @ResponseBody JsonBean apply(ArchStaffGrad request){
		JsonBean bean = new JsonBean();
		
		try {
			String backState = archStaffGradSv.check(request);
			if("true".equals(backState)) {
				request.setCreateDate(new Date());
				archStaffGradSv.save(request);
			} else {
				bean.fail(backState);
			}
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	} 
}

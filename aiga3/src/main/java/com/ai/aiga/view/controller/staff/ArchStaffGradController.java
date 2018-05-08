package com.ai.aiga.view.controller.staff;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchStaffGrad;
import com.ai.aiga.service.staff.ArchStaffGradSv;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.view.controller.staff.dto.StaffInfoRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ArchStaffGradController {
	@Autowired
	private ArchStaffGradSv archStaffGradSv;
	@Autowired
	private StaffSv staffSv;
	@Autowired
	private MailCmpt mailCmpt;
	
	@RequestMapping(path = "/staff/info/findApply")
	public @ResponseBody JsonBean findApply(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean bean = new JsonBean();
		bean.setData(archStaffGradSv.findApply(pageNumber,pageSize));
		return bean;
	}

	@RequestMapping(path = "/staff/info/rejet")
	public @ResponseBody JsonBean rejet(Long applyId){
		JsonBean bean = new JsonBean();
		try {
			String msg = archStaffGradSv.reject(applyId);
			if(!"true".equals(msg)) {
				bean.fail(msg);	
			} else {
				//发送邮件
				String email = archStaffGradSv.getRejectEmail(applyId);
				mailCmpt.sendMail(email, "", "架构资产管控平台账号申请未通过", "", null);
			}
		} catch (Exception e) {
			bean.fail(e.getMessage());	
		}
		return bean;
	}
	
	@RequestMapping(path = "/staff/info/accept")
	public @ResponseBody JsonBean accept(ArchStaffGrad request){
		JsonBean bean = new JsonBean();
		try {
			String msg = archStaffGradSv.acceptCheck(request);
			if("true".equals(msg)) {			
				StaffInfoRequest input = new StaffInfoRequest();
				ArchStaffGrad theOne = archStaffGradSv.findOne(request.getApplyId());
				input.setCode(theOne.getStaffCode());
				input.setName(theOne.getStaffName());
				input.setPassword(theOne.getStaffPassword());
				input.setEmail(theOne.getEmail());
				input.setBillId(theOne.getBillId());
				theOne.setRoleId(request.getRoleId());
				//存表
				staffSv.saveStaffOrgSignIn(input, Long.valueOf(theOne.getOrganizeId()), request.getRoleId());
				//更新申请表
				archStaffGradSv.acceptSave(theOne);
				//发送邮件
				mailCmpt.sendMail(request.getEmail(), "", "架构资产管控平台账号申请通过", "账号申请成功", null);
			} else {
				bean.fail(msg);	
			}
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
				//发送邮件
				mailCmpt.sendMail(request.getEmail(), "", "架构资产管控平台账号申请", "账号申请单已提交请耐心等待审核", null);
			} else {
				bean.fail(backState);
			}
		} catch (Exception e) {
			bean.fail(e.getMessage());
		}
		return bean;
	} 
}

package com.ai.aiga.view.controller.staff;


import io.swagger.annotations.ApiOperation;

import net.sf.ehcache.search.Results;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.component.MailCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.service.staff.StaffSv;
import com.ai.aiga.view.controller.staff.dto.StaffInfoRequest;
import com.ai.aiga.view.controller.staff.dto.StaffOrgRelatRequest;
import com.ai.aiga.view.controller.staff.dto.StaffSignIn;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class StaffController {
	@Autowired
	private MailCmpt mailCmpt;
	@Autowired
	private StaffSv aigaStaffSv;
	
	/**
	 * 找回密码
	 * @param type
	 * @param value
	 * @return
	 */
	@RequestMapping(path = "/pwdreset", method=RequestMethod.POST)
	@ApiOperation(value = "密码找回", notes = "暂无")
	public @ResponseBody JsonBean pwdReset(String type, String value){
		JsonBean bean = new JsonBean();
		if(StringUtils.isBlank(value)) {
			bean.fail("请填入信息");
			return bean;
		}
		AigaStaff satff = new AigaStaff();
		if("account".equals(type)) {
			satff = aigaStaffSv.findStaffByCode(value);
		} else if("pwdEml".equals(type)) {
			satff = aigaStaffSv.findStaffByEmail(value);
		}
		if(satff==null) {
			bean.fail("没有查找到相关用户");
			return bean;
		}
		if(StringUtils.isBlank(satff.getEmail())) {
			bean.fail("账号邮箱为空！");
			return bean;
		}
		String content = "<h3>架构资产管控平台账号密码找回</h3>"+
				"<table style='width:100%;background-color:#eee;'>"+
				"<tr><td>用户账户为："+satff.getCode()+"</td></tr>"+
				"<tr><td>用户密码为："+satff.getPassword()+"</td></tr>"+
				"<tr><td>平台地址：</td></tr>"+
				"<tr><td>http://arch.zj.chinamobile.com/aiga3/html/index.html</td></tr>"+
				"</table>";
		//发送邮件
		mailCmpt.sendMail("dupeng5@asiainfo.com" , null, "架构资产管控平台账号密码找回", content, null);
		return bean;
	}	
	/**
	/*
	 * 按条件查询员工信息
	 * */
	@RequestMapping(path = "/aiga/staff/list")
	public @ResponseBody JsonBean listB(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			StaffInfoRequest condition,
			Long organizeId){
		JsonBean bean = new JsonBean();
		bean.setData(aigaStaffSv.findStaff(condition, organizeId, pageNumber, pageSize));
		return bean;
	}
	/*
	 * 新增操作员信息,及其与组织关系的保存
	 * */
	@RequestMapping(path = "/aiga/staff/save")
	public @ResponseBody JsonBean save(StaffInfoRequest staffRequest,Long organizeId){
		aigaStaffSv.saveStaffOrg(staffRequest,organizeId);
		return JsonBean.success;
	}
	/*
	 * 注册
	 * */
	@RequestMapping(path = "/aiga/staff/saveSignIn")
	public @ResponseBody JsonBean saveSignIn(StaffInfoRequest staffRequest,Long organizeId,String roleId){
		aigaStaffSv.saveStaffOrgSignIn(staffRequest,organizeId,roleId);
		return JsonBean.success;
	}
	/*
	 * 改密
	 * */
	@RequestMapping(path = "/aiga/staff/changeMyPass")
	public @ResponseBody JsonBean changeMyPass(StaffSignIn staffRequest){
		aigaStaffSv.changeStaff(staffRequest);
		return JsonBean.success;
	}
	/*
	 * 修改操作员信息
	 * */
	@RequestMapping(path = "/aiga/staff/update")
	public @ResponseBody JsonBean update(StaffInfoRequest staffRequest){
		aigaStaffSv.updateStaff(staffRequest);
		return JsonBean.success;
	}
	/*
	 * 员工启用
	 * */
	@RequestMapping(path = "/aiga/staff/start")
	public @ResponseBody JsonBean start(Long staffId){
		aigaStaffSv.staffStart(staffId);
		return JsonBean.success;
	}
	/*
	 * 员工停用
	 * */
	@RequestMapping(path = "/aiga/staff/stop")
	public @ResponseBody JsonBean stop(Long staffId){
		aigaStaffSv.staffStop(staffId);
		return JsonBean.success;
	}
	/*
	 * 操作员修改密码
	 * */
	@RequestMapping(path = "/aiga/staff/changePass")
	public @ResponseBody JsonBean changePass(Long staffId,String password){
		Boolean change = aigaStaffSv.changePass(staffId,password);
		if(!change){
			return new JsonBean("isChange","false");
		}
		return JsonBean.success;
	}
	/*
	 * 操作员重置密码
	 * */
	@RequestMapping(path = "/aiga/staff/resetPass")
	public @ResponseBody JsonBean resetPass(Long staffId){
		aigaStaffSv.resetPass(staffId);
		return JsonBean.success;
	}
	/*
	 * 操作员关联组织查看
	 * */
	@RequestMapping(path = "/aiga/staff/staffOrgList")
	public @ResponseBody JsonBean staffOrgList(Long staffId){
		System.out.println("******"+staffId);
		JsonBean bean = new JsonBean();
		bean.setData(aigaStaffSv.staffOgrList(staffId));
		return bean;
	}
	/*
	 * 操作员关联组织新增
	 * */
	@RequestMapping(path = "/aiga/staff/staffOrgAdd")
	public @ResponseBody JsonBean staffOrgAdd(StaffOrgRelatRequest sorRequest){
		aigaStaffSv.staffOrgAdd(sorRequest);
		return JsonBean.success;
	}
	/*
	 * 操作员关联组织修改接口
	 * */
	@RequestMapping(path = "/aiga/staff/ogrUpdate")
	public @ResponseBody JsonBean ogrUpdate(StaffOrgRelatRequest sorRequest){
		aigaStaffSv.ogrUpdate(sorRequest);
		return JsonBean.success;
	}
	/*
	 * 操作员关联组织删除
	 * */
	@RequestMapping(path = "/aiga/staff/staffOrgDel")
	public @ResponseBody JsonBean staffOrgDel(Long staffId, Long organizeId){
		aigaStaffSv.deleteStaffRelatOrg(staffId,organizeId);
		return JsonBean.success;
	}
	/*
	 * 查看操作员信息
	 * */
	@RequestMapping(path = "/aiga/staff/select")
	public @ResponseBody JsonBean select(Long staffId){
		JsonBean bean = new JsonBean();
		bean.setData(aigaStaffSv.findByStaff(staffId));
		return bean;
	}
	/*
	 * 清空权限*/
	@RequestMapping(path = "/aiga/staff/clearPower")
	public @ResponseBody JsonBean clear(Long staffId){
		aigaStaffSv.clear(staffId);
		return JsonBean.success;
	}

}

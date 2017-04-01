package com.ai.aiga.view.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.service.AigaStaffSv;
import com.ai.aiga.view.json.StaffOrgRelatRequest;
import com.ai.aiga.view.json.StaffRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class StaffController {
	
	@Autowired
	private AigaStaffSv aigaStaffSv;
	/*
	 * 员工角色复权菜单员工列表调用
	 * */
	@RequestMapping(path = "/aiga/staff/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(aigaStaffSv.findStaffAll());
		return bean;
	}
	/*
	 * 按组织查询员工信息
	 * */
	@RequestMapping(path = "/aiga/staff/listA")
	public @ResponseBody JsonBean listA(Long organizeId){
		JsonBean bean = new JsonBean();
		bean.setData(aigaStaffSv.findStaffByOrg(organizeId));
		return bean;
	}
	/*
	 * 按条件查询员工信息
	 * */
	@RequestMapping(path = "/aiga/staff/listB")
	public @ResponseBody JsonBean listB(String code,String name){
		JsonBean bean = new JsonBean();
		bean.setData(aigaStaffSv.findStaff(code,name));
		return bean;
	}
	/*
	 * 新增操作员信息,及其与组织关系的保存
	 * */
	@RequestMapping(path = "/aiga/staff/save")
	public @ResponseBody JsonBean save(AigaStaff staffRequest,Long organizeId){
		aigaStaffSv.saveStaffOrg(staffRequest,organizeId);
		return JsonBean.success;
	}
	/*
	 * 修改操作员信息
	 * */
	@RequestMapping(path = "/aiga/staff/update")
	public @ResponseBody JsonBean update(AigaStaff staffRequest){
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
		aigaStaffSv.changePass(staffId,password);
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

package com.ai.aiga.view.controller.auto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.domain.NaAutoGroup;
import com.ai.aiga.service.auto.AutoGroupSv;
import com.ai.aiga.view.json.auto.AutoGroupCaseRequest;
import com.ai.aiga.view.json.auto.AutoGroupRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class AutoGroupController {
	
	@Autowired
	private AutoGroupSv autoGroupSv;
	
	/**
	 * 按条件查询用例组*/
	@RequestMapping(path = "/sys/autoGroup/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaAutoGroup condition
			){
		JsonBean bean = new JsonBean();
		bean.setData(autoGroupSv.fingGroups(condition,pageNumber,pageSize));
		return bean;
		
	}
	
	@RequestMapping(path = "/sys/autoGroup/findOne")
	public @ResponseBody JsonBean findOne(Long groupId){
		JsonBean bean = new JsonBean();
		bean.setData(autoGroupSv.findOne(groupId));
		return bean;
	}
	/**
	 * 用例组新增*/
	@RequestMapping(path = "/sys/autoGroup/save")
	public @ResponseBody JsonBean save(AutoGroupRequest autoGroupRequest){
		autoGroupSv.save(autoGroupRequest);
		return JsonBean.success;
		
	}
	/**
	 * 用例组修改*/
	@RequestMapping(path = "/sys/autoGroup/update")
	public @ResponseBody JsonBean update(AutoGroupRequest autoGroupRequest){
		autoGroupSv.update(autoGroupRequest);
		return JsonBean.success;
	}
	/**
	 * 用例组删除*/
	@RequestMapping(path = "/sys/autoGroup/delete")
	public @ResponseBody JsonBean delete(Long groupId){
		autoGroupSv.delete(groupId);
		return JsonBean.success;
	}
	/**
	 * 用例用例组关联关系新增*/
	@RequestMapping(path = "/sys/autoGroup/caseRelatGroupSave")
	public @ResponseBody JsonBean caseRelatGroupSave(AutoGroupCaseRequest autoGroupCaseRequest, String autoIds){
		JsonBean bean = new JsonBean();
		bean.setData(autoGroupSv.caseRelatGroupSave(autoGroupCaseRequest,autoIds));
		return bean;
	}
	/**
	 * 用例用例組关联关系删除*/
	@RequestMapping(path = "/sys/autoGroup/caseRelatGroupDel")
	public @ResponseBody JsonBean caseRelatGroupDel(Long groupId, String autoIds){
		System.out.println("***"+autoIds);
		autoGroupSv.caseRelatGroupDel(groupId, autoIds);
		return JsonBean.success;
	}
	/**
	 * 用例执行顺序修改*/
	@RequestMapping(path = "/sys/autoGroup/groupOrderUpdate")
	public @ResponseBody JsonBean groupOrderUpdate(Long groupId, String autoIds, String groupOrders){
		autoGroupSv.groupOrderUpdate(groupId, autoIds, groupOrders);
		return JsonBean.success;
	}
	/**
	 * 自动化用例查看*/
	@RequestMapping(path = "/sys/autoGroup/caseList")
	public @ResponseBody JsonBean caseList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaAutoCase condition){
		JsonBean bean = new JsonBean();
		bean.setData(autoGroupSv.caseList(condition, pageNumber, pageSize));
		return bean;
	}
	/**
	 * 查看用例组下已关联列表*/
	@RequestMapping(path = "/sys/autoGroup/caseRelatGroupList")
	public @ResponseBody JsonBean caseRelatGroupList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long groupId){
		JsonBean bean = new JsonBean();
		bean.setData(autoGroupSv.caseRelatGroupList(groupId, pageNumber, pageSize));
		return bean;
	}
}

package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.cache.AigaFunFolderCacheCmpt;
import com.ai.aiga.cache.AigaSubSysFolderCacheCmpt;
import com.ai.aiga.cache.AigaSystemFolderCacheCmpt;
import com.ai.aiga.cache.CommomCompTreeCacheCmpt;
import com.ai.aiga.cache.NaBusinessCacheCmpt;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class SysInfoController {
	
	@Autowired
	private AigaSystemFolderCacheCmpt aigaSystemFolderCacheCmpt;
	
	@Autowired
	private AigaSubSysFolderCacheCmpt aigaSubSysFolderCacheCmpt;
	
	@Autowired
	private AigaFunFolderCacheCmpt aigaFunFolderCacheCmpt;
	
	@Autowired
	private CommomCompTreeCacheCmpt commomCompTreeCacheCmpt;
	
	@Autowired
	private NaBusinessCacheCmpt naBusinessCacheCmpt;
	
	@RequestMapping(path = "/sys/cache/listSysid" )
	public @ResponseBody JsonBean listSysId(){
		JsonBean bean = new JsonBean();
		bean.setData(aigaSystemFolderCacheCmpt.getSysList());
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/listSubsysid" )
	public @ResponseBody JsonBean listSubsysid(@RequestParam Long sysId){
		JsonBean bean = new JsonBean();
		if(sysId != null){
			bean.setData(aigaSubSysFolderCacheCmpt.getSubSysList(sysId));
		}
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/listFun" )
	public @ResponseBody JsonBean listFun(@RequestParam Long sysSubId){
		JsonBean bean = new JsonBean();
		if(sysSubId != null){
			bean.setData(aigaFunFolderCacheCmpt.getFunsBySubsysid(sysSubId));
		}
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/commenCompTree")
	public @ResponseBody JsonBean commenCompTree(){
		JsonBean bean = new JsonBean();
		bean.setData(commomCompTreeCacheCmpt.getCompList());
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/busi")
	public @ResponseBody JsonBean busi(){
		JsonBean bean = new JsonBean();
		bean.setData(naBusinessCacheCmpt.getBusiList());
		return bean;
	}
	
}

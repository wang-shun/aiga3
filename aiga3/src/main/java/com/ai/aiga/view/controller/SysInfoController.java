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
	
	
	@RequestMapping(path = "/sys/cache/listSysid" )
	public @ResponseBody JsonBean listSysid(){
		JsonBean bean = new JsonBean();
		bean.setData(aigaSystemFolderCacheCmpt.getSysList());
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/listSubsysid" )
	public @ResponseBody JsonBean listSubsysid(@RequestParam Long sysid){
		JsonBean bean = new JsonBean();
		if(sysid != null){
			bean.setData(aigaSubSysFolderCacheCmpt.getSubSysList(sysid));
		}
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/listFun" )
	public @ResponseBody JsonBean listFun(@RequestParam Long subsysid){
		JsonBean bean = new JsonBean();
		if(subsysid != null){
			bean.setData(aigaFunFolderCacheCmpt.getFunsBySubsysid(subsysid));
		}
		return bean;
	}
	
	@RequestMapping(path = "/sys/cache/commenCompTree")
	public @ResponseBody JsonBean commenCompTree(){
		JsonBean bean = new JsonBean();
		bean.setData(commomCompTreeCacheCmpt.getCompList());
		return bean;
	}
}

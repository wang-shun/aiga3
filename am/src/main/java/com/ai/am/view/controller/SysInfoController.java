package com.ai.am.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.cache.AigaFunFolderCacheCmpt;
import com.ai.am.cache.AigaSubSysFolderCacheCmpt;
import com.ai.am.cache.AigaSystemFolderCacheCmpt;
import com.ai.am.cache.NaBusinessCacheCmpt;
import com.ai.am.view.json.base.JsonBean;

@Controller
public class SysInfoController {
	
	@Autowired
	private AigaSystemFolderCacheCmpt aigaSystemFolderCacheCmpt;
	
	@Autowired
	private AigaSubSysFolderCacheCmpt aigaSubSysFolderCacheCmpt;
	
	@Autowired
	private AigaFunFolderCacheCmpt aigaFunFolderCacheCmpt;
	
	
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
	
	@RequestMapping(path = "/sys/cache/busi")
	public @ResponseBody JsonBean busi(){
		JsonBean bean = new JsonBean();
		bean.setData(naBusinessCacheCmpt.getBusiList());
		return bean;
	}
	
}

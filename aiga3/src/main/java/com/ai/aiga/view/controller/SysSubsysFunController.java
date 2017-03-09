package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.cache.AigaFunFolderCacheCmpt;
import com.ai.aiga.cache.AigaSubSysFolderCacheCmpt;
import com.ai.aiga.cache.AigaSystemFolderCacheCmpt;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * 查询系统大类、系统子类、功能点
 * @author liuxx
 *@date 2017-03-09
 */
@Controller
public class SysSubsysFunController {
	@Autowired
	private AigaSystemFolderCacheCmpt systerm ;
	@Autowired
	private AigaSubSysFolderCacheCmpt subSysterm;
	@Autowired 
	private AigaFunFolderCacheCmpt functionId;
	
	/**
	 *   获得系统大类
	 * @return
	 */
	@RequestMapping(path="sys/common/system")
	public @ResponseBody JsonBean getSystem(){
		JsonBean json = new JsonBean();
		json.setData(systerm.getSysList());
		return json;
	}

	/**
	 *   获得系统子类
	 * @param sysId 系统大类Id
	 * @return
	 */
	@RequestMapping(path="sys/common/subSystem")
	public @ResponseBody JsonBean getSubSystem(Long sysId){
		JsonBean json = new JsonBean();
		json.setData(subSysterm.getSubSysList(sysId));
		return json;
	}

	/**
	 *   获得功能点
	 * @param subsysId 系统子类 Id
	 * @return
	 */
	@RequestMapping(path="sys/common/fun")
	public @ResponseBody JsonBean getFun(Long subsysId){
		JsonBean json = new JsonBean();
		json.setData(functionId.getFunsBySubsysid(subsysId));
		return json;
	}

}

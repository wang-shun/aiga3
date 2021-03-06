package com.ai.am.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.cache.AmCoreIndexCacheCmpt;
import com.ai.am.cache.ArchDbConnectCacheCmpt;
import com.ai.am.cache.ArchSrvManageCacheCmpt;
import com.ai.am.service.AmCoreIndexSv;
import com.ai.am.view.controller.archiQuesManage.dto.AmCoreIndexSelects;
import com.ai.am.view.json.base.JsonBean;

@Controller
@Api(value = "AmCoreIndexController", description = "指标主表")
public class AmCoreIndexController {

	@Autowired
	private AmCoreIndexSv amCoreIndexSv;

	@RequestMapping(path = "/archi/index/list")
	public @ResponseBody JsonBean list(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		condition.setIndexGroup(condition.getIndexGroup().trim());
		bean.setData(amCoreIndexSv.findAmCoreIndex(condition));
		return bean;
	}
	
	@RequestMapping(path = "/archi/index/distinct")
	public @ResponseBody JsonBean distinct(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.distinctAmCoreIndex());
		return bean;
	}
	
	@RequestMapping(path = "/archi/index/selectName")
	public @ResponseBody JsonBean selectName(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		condition.setIndexGroup(condition.getIndexGroup().trim());
		bean.setData(amCoreIndexSv.selectIndexName(condition));
		return bean;
	}
}

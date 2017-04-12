package com.ai.aiga.view.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.service.NaAutoBackupFrontSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaAutoBackupController {
	@Autowired
	private  NaAutoBackupFrontSv  naAutoBackupFrontSv;
	
	@RequestMapping(path = "/sys/backup/getBackupDealList")
	public @ResponseBody JsonBean getBackupDealList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			String propertyResource,String resourceValue) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getBackupDealList(pageNumber, pageSize, propertyResource, resourceValue));
		return bean;
	}
	
	@RequestMapping(path = "/sys/backup/getPropertyConfigList")
	public @ResponseBody JsonBean getPropertyConfigList(){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getDistinctPropertyConfigList());
		return bean;
	}
	
	@RequestMapping(path = "/sys/backup/addBackup")
	public @ResponseBody JsonBean saveBackupDeal(String propertyResource,String resourceValue){
		naAutoBackupFrontSv.saveBackupDeal(propertyResource, resourceValue);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/backup/delBackup")
	public @ResponseBody JsonBean delBackupDeal(Long dealId){
		naAutoBackupFrontSv.deleteBackupDeal(dealId);
		return JsonBean.success;
	}
	
	public @ResponseBody JsonBean save(@RequestBody List<NaAutoPropertyConfig> saveState){
		return JsonBean.success;
	}
	
}

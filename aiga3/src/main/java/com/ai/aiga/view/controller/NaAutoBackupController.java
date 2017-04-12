package com.ai.aiga.view.controller;

import java.text.ParseException;

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
	
	@RequestMapping(path = "/sys/property/getPropertyConfigList")
	public @ResponseBody JsonBean getPropertyConfigList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			String propertyID, String dependencyTable, String dependencyField) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getPropertyConfigList(pageNumber, pageSize, propertyID, dependencyTable, dependencyField));
		return bean;
	}
	
	@RequestMapping(path = "/sys/property/addPropertyConfig")
	public @ResponseBody JsonBean addPropertyConfig(@RequestBody NaAutoPropertyConfig config){
		naAutoBackupFrontSv.addPropertyConfig(config);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/delPropertyConfig")
	public @ResponseBody JsonBean delPropertyConfig(Long propertyId){
		naAutoBackupFrontSv.deletePropertyConfig(propertyId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/updatePropertyConfig")
	public @ResponseBody JsonBean updatePropertyConfig(@RequestBody NaAutoPropertyConfig config){
		naAutoBackupFrontSv.updatePropertyConfig(config);
		return JsonBean.success;
	}
	
}

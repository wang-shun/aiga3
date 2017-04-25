package com.ai.aiga.view.controller.auto;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.domain.NaAutoPropertyCorrelation;
import com.ai.aiga.service.auto.AutoBackupFrontSv;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.process.jta.task.impl.AutoBackupTask;

@Controller
public class NaAutoBackupController {
	@Autowired
	private AutoBackupFrontSv naAutoBackupFrontSv;
	
	
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
			String propertyId, String dependencyTable, String dependencyField) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getPropertyConfigList(pageNumber, pageSize, propertyId, dependencyTable, dependencyField));
		return bean;
	}
	
	@RequestMapping(path = "/sys/property/addPropertyConfig")
	public @ResponseBody JsonBean addPropertyConfig(NaAutoPropertyConfig config){
		naAutoBackupFrontSv.addPropertyConfig(config);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/delPropertyConfig")
	public @ResponseBody JsonBean delPropertyConfig(Long cfgId){
		naAutoBackupFrontSv.deletePropertyConfig(cfgId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/updatePropertyConfig")
	public @ResponseBody JsonBean updatePropertyConfig(NaAutoPropertyConfig config){
		naAutoBackupFrontSv.updatePropertyConfig(config);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/getPropertyCorrelationList")
	public @ResponseBody JsonBean getPropertyCorrelationList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			String propertyId, String correlationTable, String correlationField) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getPropertyCorrelationList(pageNumber, pageSize, propertyId, correlationTable, correlationField));
		return bean;
	}
	
	@RequestMapping(path = "/sys/property/addPropertyCorrelation")
	public @ResponseBody JsonBean addPropertyConfig(NaAutoPropertyCorrelation correlation){
		naAutoBackupFrontSv.addPropertyCorrelation(correlation);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/delPropertyCorrelation")
	public @ResponseBody JsonBean delPropertyCorrelation(Long correlationId){
		naAutoBackupFrontSv.deletePropertyCorrelation(correlationId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/updatePropertyCorrelation")
	public @ResponseBody JsonBean updatePropertyCorrelation(NaAutoPropertyCorrelation correlation){
		naAutoBackupFrontSv.updatePropertyCorrelation(correlation);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/property/getDbList")
	public @ResponseBody JsonBean getDbList(){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getDbList());
		return bean;
	}
	@RequestMapping(path = "/sys/property/getCigIdList")
	public @ResponseBody JsonBean getCigIdList(){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getPropertyCfgIdList());
		return bean;
	}
	
	@RequestMapping(path = "/sys/property/getPropertyFieldList")
	public @ResponseBody JsonBean getCigIdPropertyField(String propertyId){
		JsonBean bean = new JsonBean();
		bean.setData(naAutoBackupFrontSv.getPropertyFleldName(propertyId));
		return bean;
	}
	
	@RequestMapping(path = "/sys/backup/dataBackup")
	public @ResponseBody JsonBean BackupDeal(){
		String[] a = new String[]{"1"};
		//AutoBackupTask.main(a);
		return JsonBean.success;
	}
}

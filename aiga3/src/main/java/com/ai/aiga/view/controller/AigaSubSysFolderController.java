package com.ai.aiga.view.controller;

import java.math.BigDecimal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaSubSysFolder;
import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.service.AigaSubSysFolderSv;
import com.ai.aiga.service.AigaSystemFolderSv;
import com.ai.aiga.view.json.AigaSubSysFolderRequest;
import com.ai.aiga.view.json.AigaSystemFolderRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class AigaSubSysFolderController {
	@Autowired
	private AigaSubSysFolderSv aigaSubSysFolderSv;

	@RequestMapping(path = "/sys/subsysfolder/list")
	public @ResponseBody JsonBean list() {
		JsonBean bean = new JsonBean();
		bean.setData(aigaSubSysFolderSv.findSubSysFolder());
		return bean;
	}

	@RequestMapping(path = "/sys/subsysfolder/del")
	public @ResponseBody JsonBean del(@RequestParam BigDecimal subsysId) {
		aigaSubSysFolderSv.deleteSubSysFolder(subsysId);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/subsysfolder/findone")
	public @ResponseBody JsonBean findone(@RequestParam BigDecimal subsysId) {
		JsonBean bean = new JsonBean();
		bean.setData(aigaSubSysFolderSv.findOne(subsysId));
		return bean;
	}

	@RequestMapping(path = "/sys/subsysfolder/save")

	public @ResponseBody JsonBean save(AigaSubSysFolderRequest request) {
		aigaSubSysFolderSv.saveSubSysFolder(request);

		return JsonBean.success;

	}
	@RequestMapping(path = "/sys/subsysfolder/update")
	public @ResponseBody JsonBean update(AigaSubSysFolderRequest request) {
		aigaSubSysFolderSv.updateSubSysFolder(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/subsysfolder/listByName")
	public @ResponseBody JsonBean listByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
		
			AigaSubSysFolder condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(aigaSubSysFolderSv.list(pageNumber, pageSize,condition));
		
		return bean;
	}
}

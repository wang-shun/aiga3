package com.ai.am.view.controller;


import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.AigaSubSysFolder;
import com.ai.am.domain.AigaSystemFolder;
import com.ai.am.service.AigaSubSysFolderSv;
import com.ai.am.service.AigaSystemFolderSv;
import com.ai.am.view.json.AigaSubSysFolderRequest;
import com.ai.am.view.json.AigaSystemFolderRequest;
import com.ai.am.view.json.base.JsonBean;

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
	public @ResponseBody JsonBean del(@RequestParam Long subsysId) {
		aigaSubSysFolderSv.deleteSubSysFolder(subsysId);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/subsysfolder/findone")
	public @ResponseBody JsonBean findone(@RequestParam Long subsysId) {
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

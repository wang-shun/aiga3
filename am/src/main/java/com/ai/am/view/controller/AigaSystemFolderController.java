package com.ai.am.view.controller;


import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.AigaSystemFolder;
import com.ai.am.domain.NaUiControl;
import com.ai.am.service.AigaSystemFolderSv;
import com.ai.am.view.json.AigaSystemFolderRequest;
import com.ai.am.view.json.base.JsonBean;

@Controller
public class AigaSystemFolderController {
	@Autowired
	private AigaSystemFolderSv aigaSystemFolderSv;

	@RequestMapping(path = "/sys/systemfolder/list")
	public @ResponseBody JsonBean list() {
		JsonBean bean = new JsonBean();
		bean.setData(aigaSystemFolderSv.findSystemFolder());
		return bean;
	}

	@RequestMapping(path = "/sys/systemfolder/del")
	public @ResponseBody JsonBean del(@RequestParam Long sysId) {
		aigaSystemFolderSv.deleteSystemFolder(sysId);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/systemfolder/findone")
	public @ResponseBody JsonBean findone(@RequestParam Long sysId) {
		JsonBean bean = new JsonBean();
		bean.setData(aigaSystemFolderSv.findOne(sysId));
		return bean;
	}

	@RequestMapping(path = "/sys/systemfolder/save")

	public @ResponseBody JsonBean save(AigaSystemFolderRequest request) {
		aigaSystemFolderSv.saveSystemFolder(request);

		return JsonBean.success;

	}
	@RequestMapping(path = "/sys/systemfolder/update")
	public @ResponseBody JsonBean update(AigaSystemFolderRequest request) {
		aigaSystemFolderSv.updateSystemFolder(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/systemfolder/listByName")
	public @ResponseBody JsonBean listByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
		
			AigaSystemFolder condition) throws ParseException {
		System.out.println("进入查询");
		  JsonBean bean = new JsonBean();
		  System.out.println(condition);
		bean.setData(aigaSystemFolderSv.list(pageNumber, pageSize,condition));
		//System.out.println("bean"+bean);
		return bean;
	}
}
package com.ai.am.view.controller;


import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.AigaFunFolder;
import com.ai.am.domain.AigaSystemFolder;
import com.ai.am.service.AigaFunFolderSv;
import com.ai.am.service.AigaSystemFolderSv;
import com.ai.am.view.json.AigaFunFolderRequest;
import com.ai.am.view.json.AigaSystemFolderRequest;
import com.ai.am.view.json.base.JsonBean;

@Controller
public class AigaFunFolderController {
	@Autowired
	private AigaFunFolderSv aigaFunFolderSv;

	/*@RequestMapping(path = "/sys/funfolder/list")
	public @ResponseBody JsonBean list() {
		JsonBean bean = new JsonBean();
		bean.setData(aigaFunFolderSv.findfunFolder());
		return bean;
	}*/

	@RequestMapping(path = "/sys/funfolder/del")
	public @ResponseBody JsonBean del(@RequestParam Long funId) {
		aigaFunFolderSv.deletefunFolder(funId);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/funfolder/findone")
	public @ResponseBody JsonBean findone(@RequestParam Long funId) {
		JsonBean bean = new JsonBean();
		bean.setData(aigaFunFolderSv.findOne(funId));
		return bean;
	}

	@RequestMapping(path = "/sys/funfolder/save")

	public @ResponseBody JsonBean save(AigaFunFolderRequest request) {
		aigaFunFolderSv.savefunFolder(request);

		return JsonBean.success;

	}
	@RequestMapping(path = "/sys/funfolder/update")
	public @ResponseBody JsonBean update(AigaFunFolderRequest request) {
		aigaFunFolderSv.updatefunFolder(request);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/funfolder/listByName")
	public @ResponseBody JsonBean listByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
		
			AigaFunFolder condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(aigaFunFolderSv.list(pageNumber, pageSize,condition));
		
		return bean;
	}
}

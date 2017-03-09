package com.ai.aiga.view.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.AigaSystemFolderSv;
import com.ai.aiga.view.json.AigaSystemFolderRequest;
import com.ai.aiga.view.json.base.JsonBean;

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
	public @ResponseBody JsonBean del(@RequestParam BigDecimal sysId) {
		aigaSystemFolderSv.deleteSystemFolder(sysId);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/systemfolder/findone")
	public @ResponseBody JsonBean findone(@RequestParam BigDecimal sysId) {
		JsonBean bean = new JsonBean();
		bean.setData(aigaSystemFolderSv.findOne(sysId));
		return bean;
	}

	@RequestMapping(path = "/sys/systemfolder/sava")

	public @ResponseBody JsonBean save(AigaSystemFolderRequest request) {
		aigaSystemFolderSv.saveSystemFolder(request);

		return JsonBean.success;

	}
	@RequestMapping(path = "/sys/systemfolder/update")
	public @ResponseBody JsonBean update(AigaSystemFolderRequest request) {
		aigaSystemFolderSv.updateSystemFolder(request);
		return JsonBean.success;
	}
}
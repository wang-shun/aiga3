package com.ai.aiga.view.controller;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.NaFileUploadSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "NaFileUploadController", description = "文件上传下载API")
public class NaFileUploadController {

	@Autowired
	private NaFileUploadSv naFileUploadSv;
	
	@RequestMapping(path = "/archi/question/getFileName")
	public @ResponseBody JsonBean getFileName(
				@RequestParam Long quesId){
		JsonBean bean = new JsonBean();
		bean.setData(naFileUploadSv.findFileName(quesId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByPlanIdAndFileType")
	public @ResponseBody JsonBean findByPlanIdAndFileType(
			@RequestParam Long planId, @RequestParam Long fileType){
		JsonBean bean = new JsonBean();
		bean.setData(naFileUploadSv.findByPlanIdAndFileType(planId, fileType));
		return bean;
	}
	
}

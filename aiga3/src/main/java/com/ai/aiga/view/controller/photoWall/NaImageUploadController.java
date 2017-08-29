package com.ai.aiga.view.controller.photoWall;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.NaImageUploadSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;
import com.ai.aiga.view.controller.photoWall.dto.NaImageUploadRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "NaImageUploadController", description = "图片上传下载API")
public class NaImageUploadController {

	@Autowired
	private NaImageUploadSv naImageUploadSv;
	
	@RequestMapping(path = "/archi/image/getFileName")
	public @ResponseBody JsonBean getFileName(
				@RequestParam Long quesId){
		JsonBean bean = new JsonBean();
		bean.setData(naImageUploadSv.findFileName(quesId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/image/findByPlanIdAndFileType")
	public @ResponseBody JsonBean findByPlanIdAndFileType(
			@RequestParam Long planId, @RequestParam Long fileType){
		JsonBean bean = new JsonBean();
		bean.setData(naImageUploadSv.findByPlanIdAndFileType(planId, fileType));
		return bean;
	}
	//更改isShared共享状态
	@RequestMapping(path = "/archi/image/updateIsSharedState")
	public @ResponseBody JsonBean updateIsSharedState(NaImageUploadRequest request){
		naImageUploadSv.updateIsSharedState(request);
		return JsonBean.success;
	}
	//查询我的图片库
	@RequestMapping(path = "/archi/image/findMyImages")
	public @ResponseBody JsonBean findMyImages(
			@RequestParam String isShared, @RequestParam Long createId){
		JsonBean bean = new JsonBean();
		bean.setData(naImageUploadSv.findMyImages2(isShared, createId));
		return bean;
	}
	//查询公共图片库
	@RequestMapping(path = "/archi/image/findCommonImages")
	public @ResponseBody JsonBean findCommonImages(
			@RequestParam String isShared){
		JsonBean bean = new JsonBean();
		bean.setData(naImageUploadSv.findCommonImages(isShared));
		return bean;
	}
}

package com.ai.aiga.view.controller.archiQuesManage;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.QuestionInfoSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "QuestionInfoController", description = "架构问题相关api")
public class QuestionInfoController {

	@Autowired
	private QuestionInfoSv questionInfoSv;
	
	@RequestMapping(path = "/archi/question/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findQuestionInfos());
		return bean;
	}
	
	@RequestMapping(path="/archi/question/queryInfo")
	public @ResponseBody JsonBean queryInfo(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            QuestionInfoRequest condition){
				JsonBean bean = new JsonBean();
				bean.setData(questionInfoSv.listInfo(condition, pageNumber, pageSize));
			return null;
	}
	
	@RequestMapping(path = "/archi/question/findOne")
	public @ResponseBody JsonBean findone(
				@RequestParam Long quesId){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findOne(quesId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByQuesId")
	public @ResponseBody JsonBean findByQuesId(
				@RequestParam Long quesId){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByQuesId(quesId));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByQuesType")
	public @ResponseBody JsonBean findByQuesType(
				@RequestParam String quesType){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByQuesType(quesType));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByFirstCategory")
	public @ResponseBody JsonBean findByFirstCategory(
				@RequestParam String firstCategory){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByFirstCategory(firstCategory));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByFirstCategoryAndSecondCategory")
	public @ResponseBody JsonBean findByFirstCategoryAndSecondCategory(
				@RequestParam String firstCategory, @RequestParam String secondCategory){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByFirstCategoryAndSecondCategory(firstCategory, secondCategory));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/findByFirstCategoryAndSecondCategoryAndThirdCategory")
	public @ResponseBody JsonBean findByFirstCategoryAndSecondCategoryAndThirdCategory(
				@RequestParam String firstCategory, @RequestParam String secondCategory, @RequestParam String thirdCategory){
		JsonBean bean = new JsonBean();
		bean.setData(questionInfoSv.findByFirstCategoryAndSecondCategoryAndThirdCategory(firstCategory, secondCategory, thirdCategory));
		return bean;
	}
	
	@RequestMapping(path = "/archi/question/save")
	public @ResponseBody JsonBean save(QuestionInfoRequest questionInfoRequest){
		questionInfoSv.save(questionInfoRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/update")
	public @ResponseBody JsonBean update(QuestionInfoRequest questionInfoRequest){
		questionInfoSv.update(questionInfoRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/archi/question/delete")
	public @ResponseBody JsonBean delete(
				@RequestParam Long quesId){
		questionInfoSv.delete(quesId);
		return JsonBean.success;
	}

}

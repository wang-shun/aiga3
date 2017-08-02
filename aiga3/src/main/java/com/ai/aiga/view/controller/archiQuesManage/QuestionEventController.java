package com.ai.aiga.view.controller.archiQuesManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.service.QuestionEventSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionEventRequest;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "QuestionEventController", description = "巡检事件单")
public class QuestionEventController {

	@Autowired
	private QuestionEventSv questionEventSv;
	
	@RequestMapping(path = "/archi/event/findAll")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(questionEventSv.findAll());
		return bean;
	}
	@RequestMapping(path = "/archi/event/save")
	public @ResponseBody JsonBean save(QuestionEvent request){
		JsonBean bean = new JsonBean();
		questionEventSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/event/delete")
	public @ResponseBody JsonBean delete(long id){
		questionEventSv.delete(id);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/event/update")
	public @ResponseBody JsonBean update(QuestionEvent request){
		questionEventSv.update(request);
		return JsonBean.success;
	}

}

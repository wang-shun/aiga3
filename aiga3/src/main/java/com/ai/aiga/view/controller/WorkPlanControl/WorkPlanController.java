package com.ai.aiga.view.controller.WorkPlanControl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.domain.WorkPlan;
import com.ai.aiga.service.QuestionEventSv;
import com.ai.aiga.service.WorkPlanSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionEventRequest;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "WorkPlanController", description = "巡检事件单")
public class WorkPlanController {

	@Autowired
	private WorkPlanSv workPlanSv;
	
	@RequestMapping(path = "/archi/workplan/findAll")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(workPlanSv.findAll());
		return bean;
	}
	@RequestMapping(path="/archi/workplan/queryByCondition")
	public @ResponseBody JsonBean queryByCondition(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            WorkPlan condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(workPlanSv.queryByCondition(condition, pageNumber, pageSize));
			return bean;
	}
	@RequestMapping(path = "/archi/workplan/save")
	public @ResponseBody JsonBean save(WorkPlan request){
		JsonBean bean = new JsonBean();
		workPlanSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/workplan/delete")
	public @ResponseBody JsonBean delete(long id){
		workPlanSv.delete(id);
		return JsonBean.success;
	}
	@RequestMapping(path = "/archi/workplan/update")
	public @ResponseBody JsonBean update(WorkPlan request){
		workPlanSv.update(request);
		return JsonBean.success;
	}
	@RequestMapping(path="/archi/workplan/findAllByPage")
	public @ResponseBody JsonBean findAllByPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            WorkPlan request){
				JsonBean bean = new JsonBean();
				bean.setData(workPlanSv.findAllByPage(request, pageNumber, pageSize));
			return bean;
	}
}

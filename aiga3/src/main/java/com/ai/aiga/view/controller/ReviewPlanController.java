package com.ai.aiga.view.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.reviewPlanSv;
import com.ai.aiga.view.json.base.JsonBean;


/**
 * 计划评审controller
 * @author lovestar
 * @date 2017-03-28
 */
@Controller
public class ReviewPlanController {
	@Autowired
	private reviewPlanSv sv;
	/**
	 * 评审不合格，会退给ADClod
	 * @param planDate 计划上线时间
	 * @return
	 */
	@RequestMapping(value="sys/plan/returnToADClod")
	public @ResponseBody  JsonBean returnToADClod(String planDate){
		sv.returnToADClod(planDate);
		return JsonBean.success;
	}
	
	
}

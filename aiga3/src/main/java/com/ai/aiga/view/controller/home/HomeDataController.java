package com.ai.aiga.view.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.home.HomeDataSv;
import com.ai.aiga.view.json.base.JsonBean;

import net.sf.json.JSON;

/**
 * @ClassName: HomeDataController
 * @author: dongch
 * @date: 2017年5月3日 下午3:44:08
 * @Description:首页相关数据展示
 * 
 */
@Controller
public class HomeDataController {
	
	@Autowired
	private HomeDataSv homeDataSv;
	
	@RequestMapping(path = "/sys/home/caseCount")
	public @ResponseBody JsonBean caseCount(String planDate){
		JsonBean bean = new JsonBean();
		bean.setData(homeDataSv.caseCount(planDate));
		return bean;
	}
	
	@RequestMapping(path = "/sys/home/flowText")
	public @ResponseBody JsonBean flowText(Long onlinePlan){
		
		homeDataSv.flowText(onlinePlan);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/home/flowList")
	public @ResponseBody JsonBean flowList(String planDate){
		JsonBean bean = new JsonBean();
		bean.setData(homeDataSv.flowList(planDate));
		return bean;
	}
}


package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.cache.FirstCacheCmpt;
import com.ai.aiga.cache.RootCacheCmpt;
import com.ai.aiga.cache.SecondCacheCmpt;
import com.ai.aiga.cache.ThirdCacheCmpt;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class QuesController {

	@Autowired
	private RootCacheCmpt rootCacheCmpt;
	
	@Autowired
	private FirstCacheCmpt firstCacheCmpt;
	
	@Autowired
	private SecondCacheCmpt secondCacheCmpt;
	
	@Autowired
	private ThirdCacheCmpt thirdCacheCmpt;
	
	@RequestMapping(path="/sys/cache/listQuestype")
	public @ResponseBody JsonBean listQuestype(){
		JsonBean bean = new JsonBean();
		bean.setData(rootCacheCmpt.getQuesList());
		return bean;
	}
	
	@RequestMapping(path="/sys/cache/listFirstcategory")
	public @ResponseBody JsonBean listFirstcategory(@RequestParam Long quesType){
		JsonBean bean = new JsonBean();
		if(quesType!=null){
			bean.setData(firstCacheCmpt.getFirstCateList(quesType));
		}
		return bean;
	}
	
	@RequestMapping(path="/sys/cache/listSecondcategory")
	public @ResponseBody JsonBean listSecondcategory(@RequestParam Long firstCategory){
		JsonBean bean = new JsonBean();
		if(firstCategory!=null){
			bean.setData(secondCacheCmpt.getSecondCateByFirstcategory(firstCategory));
		}
		return bean;
	}
	
	@RequestMapping(path="/sys/cache/listThirdcategory")
	public @ResponseBody JsonBean listThirdcategory(@RequestParam Long secondCategory){
		JsonBean bean = new JsonBean();
		if(secondCategory!=null){
			bean.setData(thirdCacheCmpt.getThirdCateBySecondcategory(secondCategory));
		}
		return bean;
	}
}

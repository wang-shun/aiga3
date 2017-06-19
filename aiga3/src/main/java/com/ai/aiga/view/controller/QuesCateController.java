package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.cache.FirstCategoryCacheCmpt;
import com.ai.aiga.cache.QuesCategoryCacheCmpt;
import com.ai.aiga.cache.SecondCategoryCacheCmpt;
import com.ai.aiga.cache.ThirdCategoryCacheCmpt;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class QuesCateController {

	@Autowired
	private QuesCategoryCacheCmpt quesCategoryCacheCmpt;
	
	@Autowired
	private FirstCategoryCacheCmpt firstCategoryCacheCmpt;
	
	@Autowired
	private SecondCategoryCacheCmpt secondCategoryCacheCmpt;
	
	@Autowired
	private ThirdCategoryCacheCmpt thirdCategoryCacheCmpt;
	
	@RequestMapping(path="/sys/cache/listRootid")
	public @ResponseBody JsonBean listRootid(){
		JsonBean bean = new JsonBean();
		bean.setData(quesCategoryCacheCmpt.getQuesList());
		return bean;
	}
	
	@RequestMapping(path="/sys/cache/listFirstid")
	public @ResponseBody JsonBean listFirstid(@RequestParam Long rootId){
		JsonBean bean = new JsonBean();
		if(rootId!=null){
			bean.setData(firstCategoryCacheCmpt.getFirstCateList(rootId));
		}
		return bean;
	}
	
	@RequestMapping(path="/sys/cache/listSecondid")
	public @ResponseBody JsonBean listSecondid(@RequestParam Long firstId){
		JsonBean bean = new JsonBean();
		if(firstId!=null){
			bean.setData(secondCategoryCacheCmpt.getSecondCateByFirstid(firstId));
		}
		return bean;
	}
	
	@RequestMapping(path="/sys/cache/listThirdid")
	public @ResponseBody JsonBean listThirdid(@RequestParam Long secondId){
		JsonBean bean = new JsonBean();
		if(secondId!=null){
			bean.setData(thirdCategoryCacheCmpt.getThirdCateBySecondid(secondId));
		}
		return bean;
	}
}

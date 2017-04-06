package com.ai.aiga.view.controller;





import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.reviewPlanSv;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.webservice.soap.dto.AdclodArgs;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 计划评审controller
 * @author lovestar
 * @date 2017-03-28
 */
@Controller
public class ReviewPlanController {
	@Autowired
	private reviewPlanSv sv;
	

	@RequestMapping(value="sys/plan/returnToADClod",method=RequestMethod.POST)
	@ApiOperation(value="同步数据", notes="从ADCLOD获取代码包路径信息")
	@ApiParam(name="planDate", value="计划上线时间", required=true)
	public @ResponseBody  JsonBean returnToADClod(String planDate){
		JsonBean json = new JsonBean();
		json.setData(sv.returnToADClod(planDate));
		return json;
	}
	
	@RequestMapping(value="sys/plan/copytNaCodePathFromADClod" ,method=RequestMethod.POST)
	@ApiOperation(value="同步数据", notes="从ADCLOD获取代码包路径信息")
	@ApiParam(name="naCodePathAgr", value="本次上线代码包清单", required=true)
	public @ResponseBody JsonBean copytNaCodePathFromADClod(@RequestBody AdclodArgs name) throws ParseException {
		JsonBean json = new JsonBean();
		json.setData(sv.copytNaCodePathFromADClod(name));
		return 	json;
	}
	
	
    
}
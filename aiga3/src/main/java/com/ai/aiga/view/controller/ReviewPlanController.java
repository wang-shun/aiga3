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
import com.ai.aiga.webservice.soap.dto.BMCArgs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author liuxx
 * @date 2017-03-28
 */
@Api(value="计划评审controller")
@Controller
public class ReviewPlanController {
	@Autowired
	private reviewPlanSv sv;
	

	@RequestMapping(value="sys/plan/returnToADClod",method=RequestMethod.POST)
	@ApiOperation(value="回退信息", notes="不合格的信息回退给ADCLOD")
	@ApiParam(name="planDate", value="计划上线时间", required=true)
	public @ResponseBody  JsonBean returnToADClod(String planDate){
		JsonBean json = new JsonBean();
		json.setData(sv.returnToADClod(planDate));
		return json;
	}
	
	@RequestMapping(value="sys/plan/copytNaCodePathFromADClod" ,method=RequestMethod.POST)
	@ApiOperation(value="同步数据", notes="从ADCLOD获取代码包路径信息")
	@ApiImplicitParam(name="naCodePathAgr", value="本次上线代码包清单", required=true)
	public @ResponseBody JsonBean copytNaCodePathFromADClod(@RequestBody AdclodArgs name) throws ParseException {
		JsonBean json = new JsonBean();
		json.setData(sv.copytNaCodePathFromADClod(name));
		return 	json;
	}
	
	@RequestMapping(value="/sys/plan/copytNaCodePathComplieFromBMC" ,method=RequestMethod.POST)
	@ApiOperation(value="从BMC获取信息", notes="从BMC获取发布信息")
	@ApiImplicitParam(name="BMCArgs", value="各个系统发布信息", required=true)
	public @ResponseBody JsonBean copytNaCodePathComplieFromBMC(@RequestBody BMCArgs name) throws ParseException {
		JsonBean json = new JsonBean();
		json.setData(sv.copytNaCodePathComplieFromBMC(name));
		return 	json;
	}
    
}

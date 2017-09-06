package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AmCoreIndex;
import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.service.AmCoreIndexSv;
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexSelects;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "AmCoreIndexController", description = "指标主表")
public class AmCoreIndexController {

	@Autowired
	private AmCoreIndexSv amCoreIndexSv;

	@RequestMapping(path = "/archi/index/list")
	public @ResponseBody JsonBean list(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		condition.setIndexGroup(condition.getIndexGroup().trim());
		bean.setData(amCoreIndexSv.findAmCoreIndex(condition));
		return bean;
	}
	
	@RequestMapping(path = "/archi/index/distinct")
	public @ResponseBody JsonBean distinct(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.distinctAmCoreIndex());
		return bean;
	}
	@RequestMapping(path = "/archi/index/distinctMonth")
	public @ResponseBody JsonBean distinctMonth(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.distinctMonthAmCoreIndex());
		return bean;
	}
	
	@RequestMapping(path = "/archi/index/selectName")
	public @ResponseBody JsonBean selectName(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		condition.setIndexGroup(condition.getIndexGroup().trim());
		bean.setData(amCoreIndexSv.selectIndexName(condition));
		return bean;
	}
	
	@RequestMapping(path="/index/typein/queryAmCores")
	public @ResponseBody JsonBean queryAmCores(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AmCoreIndex condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(amCoreIndexSv.queryAmCores(condition, pageNumber, pageSize));
			return bean;
	}
	@RequestMapping(path = "/index/typein/findAllAmCores")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.findAll());
		return bean;
	}
	@RequestMapping(path = "/index/typein/saveAmCores")
	public @ResponseBody JsonBean save(AmCoreIndex request){
		JsonBean bean = new JsonBean();
		amCoreIndexSv.save(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/deleteAmCores")
	public @ResponseBody JsonBean delete(long indexId){
		amCoreIndexSv.delete(indexId);
		return JsonBean.success;
	}
	@RequestMapping(path = "/index/typein/updateAmCores")
	public @ResponseBody JsonBean update(AmCoreIndex request){
		amCoreIndexSv.update(request);
		return JsonBean.success;
	}
}

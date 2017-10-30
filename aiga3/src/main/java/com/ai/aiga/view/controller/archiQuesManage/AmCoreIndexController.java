package com.ai.aiga.view.controller.archiQuesManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ai.aiga.view.controller.archiQuesManage.dto.AmCoreIndexSelectsNew;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "AmCoreIndexController", description = "指标主表")
public class AmCoreIndexController {

	@Autowired
	private AmCoreIndexSv amCoreIndexSv;

	@RequestMapping(path = "/archi/index/list")
	public @ResponseBody JsonBean list(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		if(condition.getIndexGroup()!=null){
			condition.setIndexGroup(condition.getIndexGroup().trim());
		}
		bean.setData(amCoreIndexSv.findAmCoreIndex(condition));
		return bean;
	}
	@RequestMapping(path = "/archi/index/list2")
	public @ResponseBody JsonBean list2(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		if(condition.getIndexGroup()!=null){
			condition.setIndexGroup(condition.getIndexGroup().trim());
		}
		bean.setData(amCoreIndexSv.findAmCoreIndex2(condition));
		return bean;
	}
	@RequestMapping(path = "/archi/index/list3")
	public @ResponseBody JsonBean list3(AmCoreIndexSelects condition){
		JsonBean bean = new JsonBean();
		if(condition.getIndexGroup()!=null){
			condition.setIndexGroup(condition.getIndexGroup().trim());
		}
		bean.setData(amCoreIndexSv.findAmCoreIndex3(condition));
		return bean;
	}
	@RequestMapping(path = "/archi/index/listidx")
	public @ResponseBody JsonBean listidx(AmCoreIndexSelectsNew condition){
		JsonBean bean = new JsonBean();
		if(condition.getIndexGroup()!=null){
			condition.setIndexGroup(condition.getIndexGroup().trim());
		}
		bean.setData(amCoreIndexSv.findAmCoreIndexNew(condition));
		return bean;
	}
	
	@RequestMapping(path="/archi/index/listByPage")
	public @ResponseBody JsonBean listByPage(
            @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
            AmCoreIndexSelects condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(amCoreIndexSv.findAmCoreIndexByPage(condition, pageNumber, pageSize));
			return bean;
	}	
	
	@RequestMapping(path = "/archi/index/distinctdbname")
	public @ResponseBody JsonBean distinctdbname(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.distinctAmCoreIndexDbname());
		return bean;
	}
	@RequestMapping(path = "/archi/index/distinct")
	public @ResponseBody JsonBean distinct(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.distinctAmCoreIndex());
		return bean;
	}
	@RequestMapping(path = "/archi/index/distinctsec")
	public @ResponseBody JsonBean distinctsec(){
		JsonBean bean = new JsonBean();
		bean.setData(amCoreIndexSv.distinctAmCoreIndex2());
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
		bean.setData(amCoreIndexSv.findAllIndexs());
		return bean;
	}
	@RequestMapping(path = "/index/typein/saveAmCores")
	public @ResponseBody JsonBean save(AmCoreIndex request){
		Date date = new Date();
		if(request!= null){
			request.setState("U".charAt(0));
			request.setCreateOpId(10208021L);
			request.setCreateDate(date);
		}
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
		Date date = new Date();
		if(request!= null){
			request.setState("U".charAt(0));
			request.setCreateOpId(10208021L);
			request.setCreateDate(date);
		}
		amCoreIndexSv.update(request);
		return JsonBean.success;
	}
}

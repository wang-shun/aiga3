package com.ai.am.view.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.NaChangeList;
import com.ai.am.domain.NaRequireList;
import com.ai.am.domain.NaUiControl;
import com.ai.am.service.NaChangeListSv;
import com.ai.am.service.NaRequireListSv;
import com.ai.am.view.json.base.JsonBean;

@Controller
public class NaChangeListController {
	@Autowired
	private  NaChangeListSv  naChangeListSv;
	
	@RequestMapping(path = "/sys/change/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			
			String changeName,String onlinePlan) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(naChangeListSv.selectList(pageNumber, pageSize,changeName,onlinePlan));
		
		return bean;
	}
	
	
	
	
	@RequestMapping(path = "/sys/change/save")
	public @ResponseBody JsonBean save(@RequestBody List<NaChangeList> saveState){
		naChangeListSv.save(saveState);
		return JsonBean.success;
	}
}

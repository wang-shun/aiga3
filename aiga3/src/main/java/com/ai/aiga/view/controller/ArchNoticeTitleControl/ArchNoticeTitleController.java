package com.ai.aiga.view.controller.ArchNoticeTitleControl;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.ArchNoticeTitle;
import com.ai.aiga.service.ArchNoticeTitleSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "ArchNoticeTitleController", description = "公告标题")
public class ArchNoticeTitleController {

	@Autowired
	private ArchNoticeTitleSv archNoticeTitleSv;
	
	@RequestMapping(path = "/arch/archNoticeTitle/findAll")
	public @ResponseBody JsonBean findAll(){
		JsonBean bean = new JsonBean();
		bean.setData(archNoticeTitleSv.findAll());
		return bean;
	}
	@RequestMapping(path="/arch/archNoticeTitle/queryByCondition")
	public @ResponseBody JsonBean queryByCondition(
			ArchNoticeTitle condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archNoticeTitleSv.queryByCondition(condition));
			return bean;
	}

	@RequestMapping(path="/arch/archNoticeTitle/findAllByPage")
	public @ResponseBody JsonBean findAllByPage(
            ArchNoticeTitle request){
				JsonBean bean = new JsonBean();
				bean.setData(archNoticeTitleSv.findAllByPage(request));
			return bean;
	}
}

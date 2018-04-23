package com.ai.aiga.view.controller.ArchDbSessionControl;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchDbSession;

import com.ai.aiga.service.ArchDbSessionSv;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "ArchDbSessionController", description = "接口")
public class ArchDbSessionController {

	@Autowired
	private ArchDbSessionSv archDbSessionSv;
	
	@RequestMapping(path="/archi/archDbSession/queryByCondition")
	public @ResponseBody JsonBean queryByCondition(
            ArchDbSession condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(archDbSessionSv.queryByCondition(condition));
			return bean;
	}

}

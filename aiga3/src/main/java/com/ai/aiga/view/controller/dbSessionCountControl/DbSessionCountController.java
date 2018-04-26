package com.ai.aiga.view.controller.dbSessionCountControl;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.ArchDbSession;

import com.ai.aiga.service.ArchDbSessionSv;
import com.ai.aiga.service.DbSessionCountSv;
import com.ai.aiga.view.controller.dbSessionCountControl.dto.DbSessionData;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;

@Controller
@Api(value = "DbSessionCountController", description = "接口")
public class DbSessionCountController {

	@Autowired
	private DbSessionCountSv dbSessionCountSv;
	
	@RequestMapping(path="/archi/DbSessionCount/queryByCondition")
	public @ResponseBody JsonBean queryByCondition(
            DbSessionData condition) throws ParseException{
				JsonBean bean = new JsonBean();
				bean.setData(dbSessionCountSv.queryByCondition(condition));
			return bean;
	}

}

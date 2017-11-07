package com.ai.aiga.view.controller.cloudManage.archiExternalApi;

import io.swagger.annotations.Api;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionInput;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiThirdSystemController", description = "系统三级架构申请单相关api")
public class ArchiThirdSystemController {
	@Autowired 
	private ArchitectureGradingSv architectureGradingSv;

	@RequestMapping(path = "/archi/grading/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchiGradingConditionInput input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findByCondition(input));
		return bean;
	}
	
}

package com.ai.aiga.view.controller.cloudManage.archiExternalApi;

import io.swagger.annotations.Api;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.QuestionEvent;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.ArchitectureThirdSv;
import com.ai.aiga.view.controller.archibaseline.dto.ArchiGradingConditionInput;
import com.ai.aiga.view.controller.archibaseline.dto.thirdview.ArchiThirdApplyParams;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
@Api(value = "ArchiThirdSystemController", description = "系统三级架构申请单相关api")
public class ArchiThirdSystemController {
	@Autowired 
	private ArchitectureGradingSv architectureGradingSv;
	@Autowired 
	private ArchitectureThirdSv architectureThirdSv;

	@RequestMapping(path = "/archi/grading/findByCondition")
	public @ResponseBody JsonBean findByCondition(ArchiGradingConditionInput input) throws ParseException {
		JsonBean bean = new JsonBean();
		bean.setData(architectureGradingSv.findByCondition(input));
		return bean;
	}
	
	@RequestMapping(path = "/archi/third/apply")
	public @ResponseBody JsonBean apply(ArchiThirdApplyParams request){
		JsonBean bean = new JsonBean();
		if(StringUtils.isBlank(request.getCloudOrderId())){
			bean.fail("未选择云管平台创建业务系统订单编号！");
			return bean;
		}
		architectureGradingSv.newSave(request);
		return JsonBean.success;
	}
	
}

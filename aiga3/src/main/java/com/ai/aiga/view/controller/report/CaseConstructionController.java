package com.ai.aiga.view.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.report.CaseConstructionSv;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.json.report.CaseConstructionRequest;

/**
 * @ClassName: CaseConstructionController
 * @author: dongch
 * @date: 2017年4月24日 下午2:43:14
 * @Description:功能用例建设报表
 * 
 */
@Controller
public class CaseConstructionController {
	
	@Autowired
	private CaseConstructionSv caseConstructionSv;
	
	@RequestMapping(path = "accept/caseConstruction/list")
	public @ResponseBody JsonBean list(CaseConstructionRequest request){
		JsonBean bean = new JsonBean();
		bean.setData(caseConstructionSv.list(request));
		return bean;
	}
}


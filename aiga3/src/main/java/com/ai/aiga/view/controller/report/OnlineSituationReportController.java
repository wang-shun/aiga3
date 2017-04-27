package com.ai.aiga.view.controller.report;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.report.OnlineSituationReportSv;
import com.ai.aiga.view.json.base.JsonBean;

/** * @author  lh 
    * @date 创建时间：2017年4月26日 下午3:20:38
    */
public class OnlineSituationReportController {
	
	private OnlineSituationReportSv onlineSituationReportSv;
	
	/**
	 * @ClassName: CaseConstructionController :: findOnlineSituationReport
	 * @author: lh
	 * @date: 2017年4月26日 下午3:05:31
	 *
	 * @Description:查询变更情况报表
	 * @param oninePlan
	 * @param date
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	@RequestMapping(path = "/accept/caseConstruction/findOnlineSituationReport")
	public @ResponseBody JsonBean findOnlineSituationReport(Long oninePlan,String date,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		onlineSituationReportSv.findOnlineSituationReport(oninePlan, date,pageNumber,pageSize);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/caseConstruction/saveOnlineSituationReport")
	public @ResponseBody JsonBean saveOnlineSituationReport(){
		onlineSituationReportSv.countAsync();
		return JsonBean.success;
	}
}

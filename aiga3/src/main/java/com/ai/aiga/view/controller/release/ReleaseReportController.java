package com.ai.aiga.view.controller.release;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaReleaseReport;
import com.ai.aiga.service.release.ReleaseReportSv;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.json.ExecutionExceptionExcel;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.POIExcelUtil;

/**
 * @ClassName: ReleaseReportController
 * @author: liujinfang
 * @date: 2017年4月17日 下午2:24:40
 * @Description:
 * 
 */
@Controller
public class ReleaseReportController {
	@Autowired
	private  ReleaseReportSv  releaseReportSv;
	
	@RequestMapping(path = "/release/report/list")
	public @ResponseBody JsonBean findByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaReleaseReport condition,
			NaChangePlanOnile condition1
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(releaseReportSv.list(pageNumber, pageSize, condition,condition1));
		return bean;
	}
	
	//解析数据库执行异常
	@RequestMapping(path = "/database/execution/exception")
	public @ResponseBody JsonBean upload(
			@RequestParam Long planId,
			@RequestParam MultipartFile file){
		JsonBean bean = new JsonBean();
		try {
			List<ExecutionExceptionExcel> list = POIExcelUtil.excelToList(file, ExecutionExceptionExcel.class);
			
			releaseReportSv.saveExcel(planId, list);
			
		} catch (Exception e) {
			//log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	
}


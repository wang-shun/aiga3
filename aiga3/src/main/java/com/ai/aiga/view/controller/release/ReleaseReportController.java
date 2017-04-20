package com.ai.aiga.view.controller.release;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaDbExecutionException;
import com.ai.aiga.domain.NaDbScriptExecutionProcess;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaOnlineGeneralSteps;
import com.ai.aiga.domain.NaOnlineStaffArrange;
import com.ai.aiga.domain.NaOnlineSysRelease;
import com.ai.aiga.domain.NaOnlineSystemReleaseStage;
import com.ai.aiga.domain.NaReleaseMessage;
import com.ai.aiga.domain.NaReleaseReport;
import com.ai.aiga.domain.NaTestProcess;
import com.ai.aiga.service.TestProcessExcel;
import com.ai.aiga.service.release.ReleaseReportSv;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.json.ExecutionExceptionExcel;
import com.ai.aiga.view.json.OnlineSysReleaseExcel;
import com.ai.aiga.view.json.OnlineSysReleaseStageExcel;
import com.ai.aiga.view.json.ScriptExecutionProcessExcel;
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
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private  ReleaseReportSv  releaseReportSv;
	
	@RequestMapping(path = "/release/report/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaReleaseReport condition,
			NaChangePlanOnile condition1,
			Long dealOpId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(releaseReportSv.list(pageNumber, pageSize, condition,condition1,dealOpId));
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
			
			releaseReportSv.saveExecutionExceptionExcel(planId, list);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	//解析数据库脚本执行过程
		@RequestMapping(path = "/database/scriptexecution/processexcel")
		public @ResponseBody JsonBean exceptionexcel(
				@RequestParam Long planId,
				@RequestParam MultipartFile file){
			JsonBean bean = new JsonBean();
			try {
				List<ScriptExecutionProcessExcel> list = POIExcelUtil.excelToList(file, ScriptExecutionProcessExcel.class);
				
				releaseReportSv.saveDbScriptExecutionProcessExcel(planId, list);
				
			} catch (Exception e) {
			log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
	//数据库执行异常
	@RequestMapping(path = "/database/execution/exlist")
	public @ResponseBody JsonBean findDbExecutionException(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaDbExecutionException condition
			
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(releaseReportSv.findDbExecutionException(pageNumber, pageSize, condition));
		return bean;
	}
	//数据库脚本执行进程
	@RequestMapping(path = "/database/scriptExecution/process")
	public @ResponseBody JsonBean findDbScriptExecutionProcess(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaDbScriptExecutionProcess condition
			
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(releaseReportSv.findDbScriptExecutionProcess(pageNumber, pageSize, condition));
		return bean;
	}
	
	//解析在线系统发布准备
		@RequestMapping(path = "/online/system/releaseexcel")
		public @ResponseBody JsonBean findreleaseexcel(
				@RequestParam Long planId,
				@RequestParam MultipartFile file){
			JsonBean bean = new JsonBean();
			try {
				List<OnlineSysReleaseExcel> list = POIExcelUtil.excelToList(file, OnlineSysReleaseExcel.class);
				
				releaseReportSv.saveOnlineSysReleaseExcel(planId, list);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
		//解析在线系统发布开发
				@RequestMapping(path = "/online/system/releasestageexcel")
				public @ResponseBody JsonBean releasestageexcel(
						@RequestParam Long planId,
						@RequestParam MultipartFile file){
					JsonBean bean = new JsonBean();
					try {
						List<OnlineSysReleaseStageExcel> list = POIExcelUtil.excelToList(file, OnlineSysReleaseStageExcel.class);
						
						releaseReportSv.saveOnlineSysReleaseStageExcel(planId, list);
						
					} catch (Exception e) {
						log.error("解析excel失败", e);
						bean.fail("解析excel失败!");
					}
					return bean;
				}	
		
	//在线系统发布准备
	@RequestMapping(path = "/online/system/release")
	public @ResponseBody JsonBean findOnlineSysRelease(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaOnlineSysRelease condition
			
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(releaseReportSv.findOnlineSysRelease(pageNumber, pageSize, condition));
		return bean;
	}
	
	//在线系统发布阶段
		@RequestMapping(path = "/online/system/releasestage")
		public @ResponseBody JsonBean findOnlineSysReleaseStage(
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
				NaOnlineSystemReleaseStage condition
				
				) throws ParseException {
			
			  JsonBean bean = new JsonBean();
			bean.setData(releaseReportSv.findOnlineSysReleaseStage(pageNumber, pageSize, condition));
			return bean;
		}
//测试进程
	@RequestMapping(path = "/online/test/process")
	public @ResponseBody JsonBean findTestProcess(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaTestProcess condition
			
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(releaseReportSv.findTestProcess(pageNumber, pageSize, condition));
		return bean;
	}
	//测试进程解析
		@RequestMapping(path = "/online/test/processexcel")
		public @ResponseBody JsonBean findTestProcessexcel(
				@RequestParam Long planId,
				@RequestParam MultipartFile file){
			JsonBean bean = new JsonBean();
			try {
				List<TestProcessExcel> list = POIExcelUtil.excelToList(file, TestProcessExcel.class);
				
				releaseReportSv.saveTestProcessExcel(planId, list);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}	
	//發佈消息
		@RequestMapping(path = "/online/release/message")
		public @ResponseBody JsonBean findReleaseMessage(
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
				NaReleaseMessage condition
				
				) throws ParseException {
			
			  JsonBean bean = new JsonBean();
			bean.setData(releaseReportSv.findReleaseMessage(pageNumber, pageSize, condition));
			return bean;
		}
		
		//保存發佈消息
		@RequestMapping(path = "/online/release/messagesave")
		public @ResponseBody JsonBean save(NaReleaseMessage request){
			releaseReportSv.saveReleaseMessage(request);
			return JsonBean.success;
		}
		
		//上线人员安排
				@RequestMapping(path = "/online/staff/arrange")
				public @ResponseBody JsonBean findStaffArrange(
						@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
						@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
						NaOnlineStaffArrange condition
						
						) throws ParseException {
					
					  JsonBean bean = new JsonBean();
					bean.setData(releaseReportSv.findStaffArrange(pageNumber, pageSize, condition));
					return bean;
				}
				
				//总体步骤
				
				@RequestMapping(path = "/online/general/steps")
				public @ResponseBody JsonBean findOnlineGeneralSteps(
						@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
						@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
						NaOnlineGeneralSteps condition
						
						) throws ParseException {
					
					  JsonBean bean = new JsonBean();
					bean.setData(releaseReportSv.findOnlineGeneralSteps(pageNumber, pageSize, condition));
					return bean;
				}
				
}


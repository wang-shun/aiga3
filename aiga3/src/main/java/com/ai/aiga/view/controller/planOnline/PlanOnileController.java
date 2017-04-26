package com.ai.aiga.view.controller.planOnline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.service.PlanOnile.ChangePlanOnileSv;

import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.controller.planOnline.dto.CodePathRequestExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaChangePlanOnileRequest;
import com.ai.aiga.view.controller.planOnline.dto.RequireListExcel;
import com.ai.aiga.view.controller.planOnline.dto.TestLeaveOverExcel;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.POIExcelUtil;

@Controller
public class PlanOnileController {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChangePlanOnileSv naChangePlanOnileSv ;
//保存
	@RequestMapping(path = "/sys/changeplanonile/save")
	public @ResponseBody JsonBean save(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.saveChangePlanOnile(request);
		return JsonBean.success;
	}
	//修改
	@RequestMapping(path = "/sys/changeplanonile/update")
	public @ResponseBody JsonBean update(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.updateChangePlanOnile(request);
		return JsonBean.success;
	}
	//取消
	@RequestMapping(path = "/sys/changeplanonile/del")
	public @ResponseBody JsonBean del(
			@RequestParam	Long onlinePlan){
		naChangePlanOnileSv.delectChangePlanOnile(onlinePlan);
		return JsonBean.success;
	}
	//废弃
	@RequestMapping(path = "/sys/changeplanonile/abandon")
	public @ResponseBody JsonBean abandon(
			@RequestParam Long onlinePlan){
		naChangePlanOnileSv.abandonChangePlanOnile(onlinePlan);
		return JsonBean.success;
	}
	
	/*//添加上线总结修改
	@RequestMapping(path = "/sys/changeplanonile/resultsave")
	public @ResponseBody JsonBean resultsave(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.summaryChangePlanOnile(request);
		return JsonBean.success;
	}
	*/
	
	//添加上线总结
	@RequestMapping(path = "/sys/changeplanonile/resultupdate")
	public @ResponseBody JsonBean resultupdate(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.select(request);
		return JsonBean.success;
	}
	//查找一个
	@RequestMapping(path = "/sys/changeplanonile/findone")
	public @ResponseBody JsonBean findone(
				@RequestParam Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(naChangePlanOnileSv.findOne1(onlinePlan));
		return bean;
	}
	
	
	//计划上线清单解析
	@RequestMapping(path = "/produce/plan/upload")
	public @ResponseBody JsonBean upload(
			@RequestParam Long planId,
			@RequestParam MultipartFile file){
		JsonBean bean = new JsonBean();
		try {
			List<PlanDetailManifestExcel> list = POIExcelUtil.excelToList(file, PlanDetailManifestExcel.class);
			
			naChangePlanOnileSv.saveExcel(planId, list);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	//上线系统模块清单解析
	@RequestMapping(path = "/change/code/Path")
	public @ResponseBody JsonBean codePath(
			@RequestParam Long planId,
			@RequestParam MultipartFile file){
		JsonBean bean = new JsonBean();
		try {
			List<CodePathRequestExcel> list = POIExcelUtil.excelToList(file, CodePathRequestExcel.class);
			
			naChangePlanOnileSv.saveCodeExcel(planId, list);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	
	//测试遗留情况解析
		@RequestMapping(path = "/test/leaveover/leaveexcel")
		public @ResponseBody JsonBean testLeaveOverExcel(
				@RequestParam Long planId,
				@RequestParam MultipartFile file){
			JsonBean bean = new JsonBean();
			try {
				List<TestLeaveOverExcel> list = POIExcelUtil.excelToList(file, TestLeaveOverExcel.class);
				
				naChangePlanOnileSv.testLeaveOverExcel(planId, list);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
		//测试情况解析
				@RequestMapping(path = "/test/leaveover/leaveexcel")
				public @ResponseBody JsonBean requireListExcel(
						@RequestParam Long planId,
						@RequestParam MultipartFile file){
					JsonBean bean = new JsonBean();
					try {
						List<RequireListExcel> list = POIExcelUtil.excelToList(file, RequireListExcel.class);
						
						naChangePlanOnileSv.requireListExcel(planId, list);
						
					} catch (Exception e) {
						log.error("解析excel失败", e);
						bean.fail("解析excel失败!");
					}
					return bean;
				}
}

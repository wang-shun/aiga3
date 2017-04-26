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

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaServiceChangeOnlineList;
import com.ai.aiga.service.PlanOnile.ChangePlanOnileSv;
import com.ai.aiga.service.workFlowNew.dto.NaHostConfigListExcel;
import com.ai.aiga.service.workFlowNew.dto.NaProcessChangeListExcel;
import com.ai.aiga.service.workFlowNew.dto.NaServiceChangeOnlineListExcel;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.controller.planOnline.dto.CodePathRequestExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaChangePlanOnileRequest;
import com.ai.aiga.view.controller.planOnline.dto.NaGroupAdjustListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaGroupRequireListExcel;
import com.ai.aiga.view.controller.planOnline.dto.RequireListExcel;
import com.ai.aiga.view.controller.planOnline.dto.TestLeaveOverExcel;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.POIExcelUtil;

import io.swagger.annotations.ApiParam;

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
			String fileName = file.getName();
			naChangePlanOnileSv.saveExcel(planId, list,fileName);
			
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
			String fileName = file.getName();
			naChangePlanOnileSv.saveCodeExcel(planId, list,fileName);
			
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
				String fileName = file.getName();
				naChangePlanOnileSv.testLeaveOverExcel(planId, list,fileName);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
		//测试情况解析
				@RequestMapping(path = "/test/leave/testsituationexcel")
				public @ResponseBody JsonBean requireListExcel(
						@RequestParam Long planId,
						@RequestParam MultipartFile file){
					JsonBean bean = new JsonBean();
					try {
						List<RequireListExcel> list = POIExcelUtil.excelToList(file, RequireListExcel.class);
						String fileName = file.getName();
						naChangePlanOnileSv.requireListExcel(planId, list,fileName);
						
					} catch (Exception e) {
						log.error("解析excel失败", e);
						bean.fail("解析excel失败!");
					}
					return bean;
				}

	
	
	/**
	 * @ClassName: PlanOnileController :: uplodaNaProcessChangeList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:19:58
	 *
	 * @Description:进程变更清单
	 * @param planId
	 * @param file
	 * @return          
	 */
	@RequestMapping(path = "/produce/plan/uploadNaProcessChangeList")
	public @ResponseBody JsonBean uplodaNaProcessChangeList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ){
		JsonBean bean = new JsonBean();
		try {
			List<NaProcessChangeListExcel> list = POIExcelUtil.excelToList(file, NaProcessChangeListExcel.class);
			String fileName = file.getName();
			naChangePlanOnileSv.saveExcelNaProcessChangeList(planId, list,fileName);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	/**
	 * @ClassName: PlanOnileController :: uploadNaServiceChangeOnlineList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:36:49
	 *
	 * @Description:服务变更上线清单
	 * @param planId
	 * @param file
	 * @return          
	 */
	@RequestMapping(path = "/produce/plan/uploadNaServiceChangeOnlineList")
	public @ResponseBody JsonBean uploadNaServiceChangeOnlineList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ){
		JsonBean bean = new JsonBean();
		try {
			List<NaServiceChangeOnlineListExcel> list = POIExcelUtil.excelToList(file, NaServiceChangeOnlineListExcel.class);
			String fileName = file.getName();
			naChangePlanOnileSv.saveExcelNaServiceChangeOnlineList(planId, list,fileName);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	/**
	 * @ClassName: PlanOnileController :: uploadNaHostConfigList
	 * @author: lh
	 * @date: 2017年4月26日 下午12:44:45
	 *
	 * @Description:主机配置
	 * @param planId
	 * @param file
	 * @return          
	 */
	@RequestMapping(path = "/produce/plan/uploadNaHostConfigList")
	public @ResponseBody JsonBean uploadNaHostConfigList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ){
		JsonBean bean = new JsonBean();
		try {
			List<NaHostConfigListExcel> list = POIExcelUtil.excelToList(file, NaHostConfigListExcel.class);
			String fileName = file.getName();
			naChangePlanOnileSv.saveExcelNaHostConfigList(planId, list,fileName);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	
	@RequestMapping(path = "/produce/plan/findNaFileUpload")
	public @ResponseBody JsonBean findNaFileUpload(
			@ApiParam(name="page",value="页码")@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @ApiParam(name="pageSize",value="页数")@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize ){
		JsonBean bean = new JsonBean();
		Object NaFileUploadList = naChangePlanOnileSv.findNaFileUpload(pageNumber, pageSize);
		bean.setData(NaFileUploadList);
		return bean;
	}
	
	//需联调需求解析
	@RequestMapping(path = "/group/adjust/adjustListExcel")
	public @ResponseBody JsonBean naGroupAdjustListExcel(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ){
		JsonBean bean = new JsonBean();
		try {
			List<NaGroupAdjustListExcel> list = POIExcelUtil.excelToList(file, NaGroupAdjustListExcel.class);
			String fileName = file.getName();
			naChangePlanOnileSv.naGroupAdjustListExcel(planId, list,fileName);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	//集团需求解析
	@RequestMapping(path = "/group/require/adjustListExcel")
	public @ResponseBody JsonBean naGroupRequireList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ){
		JsonBean bean = new JsonBean();
		try {
			List<NaGroupRequireListExcel> list = POIExcelUtil.excelToList(file, NaGroupRequireListExcel.class);
			String fileName = file.getName();
			naChangePlanOnileSv.naGroupRequireList(planId, list, fileName);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}

}

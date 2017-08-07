package com.ai.aiga.view.controller.planOnline;

import java.util.Date;
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
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.FileUtil;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.controller.planOnline.dto.CodePathRequestExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaChangePlanOnileRequest;
import com.ai.aiga.view.controller.planOnline.dto.NaGroupAdjustListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaGroupRequireListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaHasDeployMenuListExcel;
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
	public @ResponseBody JsonBean upload(@RequestParam Long planId, @RequestParam MultipartFile file,
			@RequestParam Long fileType) {
		JsonBean bean = new JsonBean();

		// 获取文件名称
		String fileName = file.getOriginalFilename();

		Date date = new Date();

		// 设置主机上的文件名
		String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);

		// 把文件上传到主机
		FileUtil.uploadFile(file, fileNameNew);

		try {
			List<PlanDetailManifestExcel> list = POIExcelUtil.excelToList(file, PlanDetailManifestExcel.class);

			naChangePlanOnileSv.saveExcel(planId, list, fileName, fileType, date);

		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}

	// 测试遗留情况解析
	@RequestMapping(path = "/test/leaveover/leaveexcel")
	public @ResponseBody JsonBean testLeaveOverExcel(@RequestParam Long planId, @RequestParam MultipartFile file,
			@RequestParam Long fileType) {
		JsonBean bean = new JsonBean();
		try {
			// 获取文件名称
			String fileName = file.getOriginalFilename();

			Date date = new Date();

			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);

			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew);

			// 解析excel，把数据存入表里
			List<TestLeaveOverExcel> list = POIExcelUtil.excelToList(file, TestLeaveOverExcel.class);

			naChangePlanOnileSv.testLeaveOverExcel(planId, list, fileName, fileType, date);

		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
/*
	// 测试情况解析
	@RequestMapping(path = "/test/leave/testsituationexcel")
	public @ResponseBody JsonBean requireListExcel(@RequestParam Long planId, @RequestParam MultipartFile file,
			@RequestParam Long fileType) {
		JsonBean bean = new JsonBean();
		try {

			// 获取文件名称
			String fileName = file.getOriginalFilename();
			Date date = new Date();
			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);
			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew);

			HashMap<String, Object> resultMap = POIExcelUtil.excelToList(file, RequireListExcel.class,
					RequireChangeExcel.class);
			List<RequireListExcel> list = (List<RequireListExcel>) resultMap.get("sheet1");

			//List<RequireChangeExcel> list2 = (List<RequireChangeExcel>) resultMap.get("sheet2");
			naChangePlanOnileSv.requireListExcel(planId, list, fileName, fileType, date);
			//naChangePlanOnileSv.requireChangeExcel(planId, list2, fileName, fileType, date);
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}

	*//**
	 * @ClassName: PlanOnileController :: uplodaNaProcessChangeList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:19:58
	 *
	 * @Description:进程变更清单
	 * @param planId
	 * @param file
	 * @return          
	 *//*
	@RequestMapping(path = "/produce/plan/uploadNaProcessChangeList")
	public @ResponseBody JsonBean uplodaNaProcessChangeList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file,
			@RequestParam Long fileType){
		JsonBean bean = new JsonBean();
		try {
			List<NaProcessChangeListExcel> list = POIExcelUtil.excelToList(file, NaProcessChangeListExcel.class);
			String fileName = file.getOriginalFilename();
			naChangePlanOnileSv.saveExcelNaProcessChangeList(planId, list,fileName,fileType);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	*//**
	 * @ClassName: PlanOnileController :: uploadNaServiceChangeOnlineList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:36:49
	 *
	 * @Description:服务变更上线清单
	 * @param planId
	 * @param file
	 * @return          
	 *//*
	@RequestMapping(path = "/produce/plan/uploadNaServiceChangeOnlineList")
	public @ResponseBody JsonBean uploadNaServiceChangeOnlineList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ,
			@RequestParam Long fileType){
		JsonBean bean = new JsonBean();
		try {
			List<NaServiceChangeOnlineListExcel> list = POIExcelUtil.excelToList(file, NaServiceChangeOnlineListExcel.class);
			String fileName = file.getOriginalFilename();
			naChangePlanOnileSv.saveExcelNaServiceChangeOnlineList(planId, list,fileName,fileType);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	*//**
	 * @ClassName: PlanOnileController :: uploadNaHostConfigList
	 * @author: lh
	 * @date: 2017年4月26日 下午12:44:45
	 *
	 * @Description:主机配置
	 * @param planId
	 * @param file
	 * @return          
	 *//*
	@RequestMapping(path = "/produce/plan/uploadNaHostConfigList")
	public @ResponseBody JsonBean uploadNaHostConfigList(
			@RequestParam Long planId,
			@RequestParam MultipartFile file,
			@RequestParam Long fileType){
		JsonBean bean = new JsonBean();
		try {
			List<NaHostConfigListExcel> list = POIExcelUtil.excelToList(file, NaHostConfigListExcel.class);
			String fileName = file.getOriginalFilename();
			naChangePlanOnileSv.saveExcelNaHostConfigList(planId, list,fileName,fileType);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	
	
	@RequestMapping(path = "/produce/plan/findNaFileUpload")
	public @ResponseBody JsonBean findNaFileUpload(
			Long planId,
			Long fileType,
			@ApiParam(name="page",value="页码")@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
            @ApiParam(name="pageSize",value="页数")@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize ){
		JsonBean bean = new JsonBean();
		Object NaFileUploadList = naChangePlanOnileSv.findNaFileUpload(planId,fileType,pageNumber, pageSize);
		bean.setData(NaFileUploadList);
		return bean;
	}
	
	//需联调需求解析
	@RequestMapping(path = "/group/adjust/adjustListExcel")
	public @ResponseBody JsonBean naGroupAdjustListExcel(
			@RequestParam Long planId,
			@RequestParam MultipartFile file ,
			@RequestParam Long fileType){
		JsonBean bean = new JsonBean();
		try {
			List<NaGroupAdjustListExcel> list = POIExcelUtil.excelToList(file, NaGroupAdjustListExcel.class);
			String fileName = file.getOriginalFilename();
			naChangePlanOnileSv.naGroupAdjustListExcel(planId, list,fileName,fileType);
			
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
			@RequestParam MultipartFile file,
			@RequestParam Long fileType ){
		JsonBean bean = new JsonBean();
		try {
			List<NaGroupRequireListExcel> list = POIExcelUtil.excelToList(file, NaGroupRequireListExcel.class);
			String fileName = file.getOriginalFilename();
			naChangePlanOnileSv.naGroupRequireList(planId, list, fileName,fileType);
			
		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}
	//生产环境需配置菜单需求
		@RequestMapping(path = "/group/deploy/MenuListExcel")
		public @ResponseBody JsonBean naHasDeployMenuListExcel(
				@RequestParam Long planId,
				@RequestParam MultipartFile file,
				@RequestParam Long fileType){
			JsonBean bean = new JsonBean();
			try {
				List<NaHasDeployMenuListExcel> list = POIExcelUtil.excelToList(file, NaHasDeployMenuListExcel.class);
				String fileName = file.getOriginalFilename();
				naChangePlanOnileSv.naHasDeployMenuListExcel(planId, list, fileName,fileType);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}*/
		
		// 系统架构上传文件
		@RequestMapping(path = "/group/require/uploadFile")
		public @ResponseBody JsonBean naSystemArchitectureListExcel(@RequestParam Long planId,
				@RequestParam MultipartFile file, @RequestParam Long fileType) {
			JsonBean bean = new JsonBean();

			// 获取文件名称
			String fileName = file.getOriginalFilename();

			Date date = new Date();

			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);

			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew);

			naChangePlanOnileSv.saveFileInfo(planId, fileName, fileType, date);

			return bean;
		}
		
		//excelBatch批量处理
		@RequestMapping(path = "/group/excel/batch")
		public @ResponseBody JsonBean excelBatch(@RequestParam Long planId, @RequestParam MultipartFile file,
				@RequestParam Long fileType) {
			JsonBean bean = new JsonBean();
			try {
				// 获取文件名称
				String fileName = file.getOriginalFilename();

				Date date = new Date();

				// 设置主机上的文件名
				String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);

				// 把文件上传到主机
				FileUtil.uploadFile(file, fileNameNew);

				// 解析excel，把数据存入表里
				List<TestLeaveOverExcel> list = POIExcelUtil.excelToList(file, TestLeaveOverExcel.class);

				naChangePlanOnileSv.testLeaveOverExcel(planId, list, fileName, fileType, date);

			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
		//EXCEL BATCH DEAL
		@RequestMapping(path = "/group/excel/batchDeal")
		public @ResponseBody JsonBean excelBatchDeal(@RequestParam Long planId, @RequestParam MultipartFile file,
				@RequestParam Long fileType) {
			JsonBean bean = new JsonBean();
			// 获取文件名称
			String fileName = file.getOriginalFilename();

			Date date = new Date();

			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);

			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew);

			try {
				List<NaGroupAdjustListExcel> list = POIExcelUtil.excelToList(file, NaGroupAdjustListExcel.class);

				naChangePlanOnileSv.naGroupAdjustListExcel(planId, list, fileName, fileType, date);

			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
		// sql清单，后续包括所有的上传
		@RequestMapping(path = "/produce/sqlList/upload")
		public @ResponseBody JsonBean sqlList(@RequestParam Long planId, @RequestParam MultipartFile file,
				@RequestParam Long fileType) {
			JsonBean bean = new JsonBean();

			// 获取文件名称
			String fileName = file.getOriginalFilename();

			Date date = new Date();

			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);

			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew);

			naChangePlanOnileSv.saveFileInfo(planId, fileName, fileType, date);

			return bean;
		}
		
		// 查看上线交付物列表
		@RequestMapping(path = "/produce/plan/findNaFileUpload")
		public @ResponseBody JsonBean findNaFileUpload(Long planId, Long fileType,
				@ApiParam(name = "page", value = "页码") @RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT
						+ "") int pageNumber,
				@ApiParam(name = "pageSize", value = "页数") @RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT
						+ "") int pageSize,String fileName) {
			JsonBean bean = new JsonBean();
			Object NaFileUploadList = naChangePlanOnileSv.findNaFileUpload(planId, fileType, pageNumber, pageSize,fileName);
			bean.setData(NaFileUploadList);
			return bean;
		}
}
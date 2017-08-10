package com.ai.aiga.view.util;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaServiceChangeOnlineList;
import com.ai.aiga.service.DealFileSv;
import com.ai.aiga.service.NaChangePlanOnileSv;
import com.ai.aiga.service.ArchIndex.dto.QuestionInfoListExcel;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.FileUtil;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.POIExcelUtil;

import io.swagger.annotations.ApiParam;

@Controller
public class DealFileController {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DealFileSv dealFileSv ;
	@Autowired
	private NaChangePlanOnileSv changePlanOnileSv;
	
	
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

			dealFileSv.saveExcel(planId, list, fileName, fileType, date);

		} catch (Exception e) {
			log.error("解析excel失败", e);
			bean.fail("解析excel失败!");
		}
		return bean;
	}

		
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

			dealFileSv.saveFileInfo(planId, fileName, fileType, date);

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
			Object NaFileUploadList = dealFileSv.findNaFileUpload(planId, fileType, pageNumber, pageSize,fileName);
			bean.setData(NaFileUploadList);
			return bean;
		}
		
		/**
		 * 批量下载并打包文件成zip
		 * 
		 */
		@RequestMapping(value = "/sys/changeplanonile/downloadFileBatch")
	    public @ResponseBody ResponseEntity downloadFileBatch(String ids) {
			try {
				return changePlanOnileSv.downloadFileBatch(ids);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		/**
		 * 下载文档
		 * 
		 * @param fileName
		 * @return
		 * id用于更新下载文件的信息，例如更新文件下载时间
		 */
		@RequestMapping(path = "/sys/changeplanonile/downloadFile")
		public @ResponseBody ResponseEntity downloadFile(String fileName, Long id) {
			return changePlanOnileSv.downloadFile(fileName, id);
		}
		
/*		// 集团需求解析(上传文件)
		@RequestMapping(path = "/group/require/adjustListExcel")
		public @ResponseBody JsonBean naGroupRequireList(@RequestParam Long planId, @RequestParam MultipartFile file,
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
				List<NaGroupRequireListExcel> list = POIExcelUtil.excelToList(file, NaGroupRequireListExcel.class);

				naChangePlanOnileSv.naGroupRequireList(planId, list, fileName, fileType, date);

			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		*//**
		 * @ClassName: PlanOnileController :: uplodaNaProcessChangeList
		 * @author: ly
		 * @date: 2017年8月9日 
		 *
		 * @Description:进程变更清单
		 * @param planId
		 * @param file
		 * @return
		 *//*
		@RequestMapping(path = "/produce/plan/uploadNaProcessChangeList")
		public @ResponseBody JsonBean uplodaNaProcessChangeList(@RequestParam Long planId, @RequestParam MultipartFile file,
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
				List<NaProcessChangeListExcel> list = POIExcelUtil.excelToList(file, NaProcessChangeListExcel.class);

				naChangePlanOnileSv.saveExcelNaProcessChangeList(planId, list, fileName, fileType, date);

			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}*/
		
		/**
		 * @ClassName: PlanOnileController :: uplodaNaProcessChangeList
		 * @author: ly
		 * @date: 2017年8月9日 
		 *
		 * @Description:进程变更清单
		 * @param planId
		 * @param file
		 * @return
		 */
		@RequestMapping(path = "/produce/plan/uplodaQuestionInfo")
		public @ResponseBody JsonBean uplodaQuestionInfo(@RequestParam Long planId, @RequestParam MultipartFile file,
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
				List<QuestionInfoListExcel> list = POIExcelUtil.excelToList(file, QuestionInfoListExcel.class);
				
				dealFileSv.saveExcelQuestionInfoList(planId, list, fileName, fileType, date);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
}
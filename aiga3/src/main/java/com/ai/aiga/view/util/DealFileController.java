package com.ai.aiga.view.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

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
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.service.ArchitectureGradingSv;
import com.ai.aiga.service.DealFileSv;
import com.ai.aiga.service.NaChangePlanOnileSv;
import com.ai.aiga.service.ArchIndex.dto.QuestionInfoListExcel;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.FileUtil;
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
	@Autowired
	private ArchitectureGradingSv architectureGradingSv;
		
		// 系统架构上传文件
		@RequestMapping(path = "/group/require/uploadFile")
		public @ResponseBody JsonBean naSystemArchitectureListExcel(@RequestParam String planId,
				@RequestParam MultipartFile file, @RequestParam Long fileType) {
			JsonBean bean = new JsonBean();

			// 获取文件名称
			String fileName = file.getOriginalFilename();

			if(fileName.contains("?")){
				String hzm = fileName.split("\\.")[1];
				fileName = "文件名乱码"+"."+hzm;
			}

			Date date = new Date();

			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);
//			String fileNameNew2 = fileName.split("\\.")[0]+"_"+DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS)+"."+fileName.split("\\.")[1];

			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew);
			if(fileType==3){
				Long planId2 = date.getTime();
				if(planId.length()>=20){
					ArchitectureGrading architectureGrading= architectureGradingSv.findByCloudOrderIdAndState(planId);
					if(architectureGrading != null){
						architectureGrading.setFileId(new BigDecimal(planId2));
						architectureGradingSv.update(architectureGrading);
					}
				}
				dealFileSv.saveFileInfo(planId2, fileName, fileType, date);
			}else{
				Long planId2 = Long.parseLong(planId);
				dealFileSv.saveFileInfo(planId2, fileName, fileType, date);
			}
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
		 * 批量上传QuestionInfo
		 * @param planId
		 * @param file
		 * @param fileType
		 * @return
		 */
		@RequestMapping(path = "/produce/plan/uploadQuestionInfo")
		public @ResponseBody JsonBean uploadQuestionInfo(@RequestParam Long planId, @RequestParam MultipartFile file,
				@RequestParam Long fileType) {
			JsonBean bean = new JsonBean();
			
			// 获取文件名称
			String fileName = file.getOriginalFilename();
			
			Date date = new Date();
			
			// 设置主机上的文件名
			String fileNameNew = fileName + "_" + DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS);
			String fileNameNew2 = fileName.split("\\.")[0]+"_"+DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS)+"."+fileName.split("\\.")[1];
			System.out.println("cccccc"+fileNameNew+"\t"+fileNameNew2);
			// 把文件上传到主机
			FileUtil.uploadFile(file, fileNameNew2);
			
			try {
				List<QuestionInfoListExcel> list = POIExcelUtil.excelToList(file, QuestionInfoListExcel.class);
				
				dealFileSv.saveExcelQuestionInfoList(planId, list, fileName, fileType, date);
				
			} catch (Exception e) {
				log.error("解析excel失败", e);
				bean.fail("解析excel失败!");
			}
			return bean;
		}
		
		// 上传 图片
		@RequestMapping(path = "/group/require/uploadImage")
		public @ResponseBody JsonBean uploadImage(@RequestParam MultipartFile file) {
			JsonBean bean = new JsonBean();

			// 获取图片名称
			String fileName = file.getOriginalFilename();

			Date date = new Date();

			// 设置主机上的文件名
			String fileNameNew2 = fileName.split("\\.")[0]+"_"+DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS)+"."+fileName.split("\\.")[1];

			// 把图片上传到主机
			FileUtil.uploadImage(file, fileName);
			
			String isShared = new String("N");
			long fileType = 66666;

			dealFileSv.saveImageInfo(fileName, isShared, fileType, date);

			return bean;
		}
		// 上传 公共图片
		@RequestMapping(path = "/group/require/uploadImageCommon")
		public @ResponseBody JsonBean uploadImageCommon(@RequestParam MultipartFile file) {
			JsonBean bean = new JsonBean();
			
			// 获取图片名称
			String fileName = file.getOriginalFilename();
			
			Date date = new Date();
			
			// 设置主机上的文件名
			String fileNameNew2 = fileName.split("\\.")[0]+"_"+DateUtil.getDateStringByDate(date, DateUtil.YYYYMMDDHHMMSS)+"."+fileName.split("\\.")[1];
			
			// 把图片上传到主机
			FileUtil.uploadImage(file, fileName);
			
			String isShared = new String("Y");
			long fileType = 66666;
			
			dealFileSv.saveImageInfo(fileName, isShared, fileType, date);
			
			return bean;
		}
}
package com.ai.aiga.view.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ChangeReviewDao;
import com.ai.aiga.dao.CodePathDao;
import com.ai.aiga.dao.DatabaseConfiDao;
import com.ai.aiga.dao.DatabaseScriptListDao;
import com.ai.aiga.dao.NaRequireListDao;
import com.ai.aiga.dao.PlanDetailManifestDao;
import com.ai.aiga.dao.TestLeaveOverDao;
import com.ai.aiga.domain.NaChangeList;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaDatabaseConfiScript;
import com.ai.aiga.domain.NaDatabaseScriptList;
import com.ai.aiga.domain.NaDbScriptList;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaTeamInfo;
import com.ai.aiga.domain.NaTestLeaveOver;
import com.ai.aiga.domain.NaTestSituation;
import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.service.ChangeReviewSv;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * @ClassName: ChangeReviewController
 * @author: liujinfang
 * @date: 2017年4月11日 下午8:02:03
 * @Description:
 * 
 */
@Controller
public class ChangeReviewController {
	@Autowired
	private  ChangeReviewSv changeReviewSv;
	//交互物变更评审
	@RequestMapping(path = "/sys/changerevier/list")
	public @ResponseBody JsonBean changerevier(Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.selectall(onlinePlan));
		return bean;
	}    
	//交互物变更评审保存
	@RequestMapping(path = "/sys/changerevier/save")
	public @ResponseBody JsonBean save(NaChangeReview request){
		changeReviewSv.save(request);;
		return JsonBean.success;
	}
	//保存代码包清单
	@RequestMapping(path = "/sys/codepath/save")
	public @ResponseBody JsonBean save(@RequestBody List<NaCodePath> saveState){
		changeReviewSv.saveCodePath(saveState);
		return JsonBean.success;
	}
	//需求清单
	@RequestMapping(path = "/sys/detailManifest/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			PlanDetailManifest condition) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findPlanDetailManifest(pageNumber, pageSize, condition));
		return bean;
	}
	
	//代码包清单
	@RequestMapping(path = "/sys/codepath/list")
	public @ResponseBody JsonBean codepath(
		@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
		@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
		NaCodePath condition
		) throws ParseException {
	
	  JsonBean bean = new JsonBean();
	bean.setData(changeReviewSv.findCodePath(pageNumber, pageSize, condition));
	return bean;
	}   
	//测试遗留情况
	@RequestMapping(path = "/sys/testLeaveOver/list")
	public @ResponseBody JsonBean findtestLeaveOver(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaTestLeaveOver condition
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findTestLeaveOver(pageNumber, pageSize, condition));
		return bean;
	}
	//功能测试报告
	@RequestMapping(path = "/sys/requireList/list")
	public @ResponseBody JsonBean findrequireList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaRequireList condition
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findRequireList(pageNumber, pageSize, condition));
		return bean;
	}
	//数据库配置脚本
	@RequestMapping(path = "/sys/databaseConfiScript/list")
	public @ResponseBody JsonBean databaseScriptList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaDatabaseConfiScript condition
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findDatabaseConfi(pageNumber, pageSize, condition));
		return bean;
	}
	//数据库脚本清单
	@RequestMapping(path = "/sys/databaseScriptList/list")
	public @ResponseBody JsonBean databaseConfi(
		@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
		@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
		NaDatabaseScriptList condition
		) throws ParseException {
	
	  JsonBean bean = new JsonBean();
	bean.setData(changeReviewSv.findDatabaseScriptList(pageNumber, pageSize, condition));
	return bean;
}
	
	//数据库脚本
		@RequestMapping(path = "/sys/dbScriptList/list")
		public @ResponseBody JsonBean DbScriptList(@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
				NaDbScriptList condition
				) throws ParseException {
			
			  JsonBean bean = new JsonBean();
			bean.setData(changeReviewSv.findDbScriptList(pageNumber, pageSize, condition));
			return bean;
		}     
	//上线需求概述
	@RequestMapping(path = "/sys/planDetailManifest/list")
	public @ResponseBody JsonBean findByName(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			PlanDetailManifest condition
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.list(pageNumber, pageSize, condition));
		return bean;
	}
	
	//测试情况
		@RequestMapping(path = "/sys/testSituation/list")
		public @ResponseBody JsonBean findtestSituation(
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
				PlanDetailManifest condition
				) throws ParseException {
			
			  JsonBean bean = new JsonBean();
			bean.setData(changeReviewSv.list1(pageNumber, pageSize, condition));
			return bean;
		}
		//测试情况保存
		@RequestMapping(path = "/sys/testSituation/save")
		public @ResponseBody JsonBean savetestSituation(@RequestBody List<NaTestSituation> request){
			changeReviewSv.saveTestSituation(request);
			return JsonBean.success;
		}	
		
}


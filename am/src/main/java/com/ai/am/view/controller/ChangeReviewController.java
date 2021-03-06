package com.ai.am.view.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.ChangeReviewDao;
import com.ai.am.dao.CodePathDao;
import com.ai.am.dao.DatabaseConfiDao;
import com.ai.am.dao.DatabaseScriptListDao;
import com.ai.am.dao.NaRequireListDao;
import com.ai.am.dao.PlanDetailManifestDao;
import com.ai.am.dao.TestLeaveOverDao;
import com.ai.am.domain.NaChangeList;
import com.ai.am.domain.NaChangeReview;
import com.ai.am.domain.NaCodePath;
import com.ai.am.domain.NaDatabaseConfiScript;
import com.ai.am.domain.NaDatabaseScriptList;
import com.ai.am.domain.NaDbScriptList;
import com.ai.am.domain.NaEmployeeInfo;
import com.ai.am.domain.NaHostIp;
import com.ai.am.domain.NaRequireList;
import com.ai.am.domain.NaTeamInfo;
import com.ai.am.domain.NaTestLeaveOver;
import com.ai.am.domain.NaTestSituation;
import com.ai.am.domain.PlanDetailManifest;
import com.ai.am.service.ChangeReviewSv;
import com.ai.am.view.json.base.JsonBean;

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
	public @ResponseBody JsonBean changerevier(Long onlinePlan,String ext1){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.selectall(onlinePlan,ext1));
		return bean;
	}    
	//交互物变更评审保存
	/**
	 * @ClassName: ChangeReviewController :: save
	 * @author: lh
	 * @date: 2017年5月3日 上午10:28:21
	 *
	 * @Description:
	 * @param request
	 * @return          
	 */
	@RequestMapping(path = "/sys/changerevier/save")
	public @ResponseBody JsonBean save(NaChangeReview request){
		changeReviewSv.save(request);
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.selectReview(request.getOnlinePlanId()));
		return bean;
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
	

	//
	@RequestMapping(path = "/sys/review/findNaHostConfigListByPlanId")
	public @ResponseBody JsonBean findNaHostConfigListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaHostConfigListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/sys/review/findNaGroupAdjustListByPlanId")
	public @ResponseBody JsonBean findNaGroupAdjustListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaGroupAdjustListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/sys/review/findNaGroupRequireListByPlanId")
	public @ResponseBody JsonBean findNaGroupRequireListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaGroupRequireListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/sys/review/findNaHasDeployMenuListByPlanId")
	public @ResponseBody JsonBean findNaHasDeployMenuListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaHasDeployMenuListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/sys/review/findNaProcessChangeListByPlanId")
	public @ResponseBody JsonBean findNaProcessChangeListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaProcessChangeListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	
	@RequestMapping(path = "/sys/review/findNaServiceChangeOnlineListByPlanId")
	public @ResponseBody JsonBean findNaServiceChangeOnlineListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaServiceChangeOnlineListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/sys/review/findNaSystemArchitectureListByPlanId")
	public @ResponseBody JsonBean findNaSystemArchitectureListByPlanId(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long planId
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findNaSystemArchitectureListByPlanId(planId,pageNumber, pageSize));
		return bean;
	}
	
	 

	//测试情况
		@RequestMapping(path = "/sys/testSituation/list")
		public @ResponseBody JsonBean findtestSituation(
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
				NaTestSituation condition
				) throws ParseException {
			
			  JsonBean bean = new JsonBean();
			bean.setData(changeReviewSv.list1(pageNumber, pageSize, condition));
			return bean;
		}
		
		@RequestMapping(path = "/sys/testSituation/listtest")
		public @ResponseBody JsonBean findSituation(
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
				NaTestSituation condition
				) throws ParseException {
			
			  JsonBean bean = new JsonBean();
			bean.setData(changeReviewSv.listTest(pageNumber, pageSize, condition));
			return bean;
		}
		//测试情况保存
		@RequestMapping(path = "/sys/testSituation/save")
		public @ResponseBody JsonBean savetestSituation(@RequestBody List<NaTestSituation> request){
			changeReviewSv.saveTestSituation(request);
			return JsonBean.success;
		}	
		//主机列表
		@RequestMapping(path = "/warn/host/findhost")
		public @ResponseBody JsonBean findhost(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaHostIp condition
			) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.host(pageNumber, pageSize, condition));
		return bean;
		}   
		
		//主机保存
		@RequestMapping(path = "/warn/host/savehost")
		public @ResponseBody JsonBean savehost(@RequestBody List<NaHostIp> request){
			changeReviewSv.savehost(request);;
			return JsonBean.success;
		}
}


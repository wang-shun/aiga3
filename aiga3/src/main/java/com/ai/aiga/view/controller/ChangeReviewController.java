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
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaTeamInfo;
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
	public @ResponseBody JsonBean codepath(){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findCodePath());
		return bean;
	}   
	
	@RequestMapping(path = "/sys/testLeaveOver/list")
	public @ResponseBody JsonBean testLeaveOver(){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findTestLeaveOver());
		return bean;
	}   
	
	@RequestMapping(path = "/sys/requireList/list")
	public @ResponseBody JsonBean requireList(){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findRequireList());
		return bean;
	}   
	
	@RequestMapping(path = "/sys/databaseScriptList/list")
	public @ResponseBody JsonBean databaseScriptList(){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findDatabaseScriptList());
		return bean;
	}     
	
	
	@RequestMapping(path = "/sys/databaseConfi/list")
	public @ResponseBody JsonBean databaseConfi(){
		JsonBean bean = new JsonBean();
		bean.setData(changeReviewSv.findDatabaseConfi());
		return bean;
	}     
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
}


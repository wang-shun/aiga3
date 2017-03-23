package com.ai.aiga.view.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoGroup;
import com.ai.aiga.service.AigaOnlineCaseCollectionSv;
import com.ai.aiga.service.AutoCaseSv;
import com.ai.aiga.service.AutoGroupSv;
import com.ai.aiga.service.NaAutoRunPlanSv;
import com.ai.aiga.view.json.AutoCaseRequest;
import com.ai.aiga.view.json.CaseCollectionRequest;
import com.ai.aiga.view.json.NaAutoRunPlanRequest;
import com.ai.aiga.view.json.PlanAutoCaseRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class AutoRunPlanController {
	
	@Autowired
	private NaAutoRunPlanSv  naAutoRunPlanSv;
	@ Autowired
	private AutoCaseSv autoCaseSv;
	@Autowired 
	private  AutoGroupSv autoGroupSv;
	@Autowired
	private AigaOnlineCaseCollectionSv  caseCollectionSv;
	/**
	 * 保存自动化计划
	 * @param naAutoRunPlan 自动化计划表单
	 * @return
	 */
	@RequestMapping(value="/sys/autoPlan/save")
	public @ResponseBody  JsonBean   save(NaAutoRunPlanRequest  naAutoRunPlan){
		try {
			naAutoRunPlanSv.save(naAutoRunPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonBean.success;
	}
	
	
	
	/**
	 * 查询自动化计划
	 * @param condition
	 * @param page
	 * @param pageSize
	 * @return
	 */
		@RequestMapping(value="/sys/autoPlan/queryList")
		public @ResponseBody  JsonBean   queryList(NaAutoRunPlanRequest condition,
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
				){
			JsonBean json = new JsonBean();
			json.setData(naAutoRunPlanSv.query(condition, page, pageSize));
			return json;
		}
		

			
	
	/**
	 *根据编号 删除自动化计划
	 * @param planIds自动化计划编号
	 * @return
	 */
	@RequestMapping(value="/sys/autoPlan/delete")
	public @ResponseBody  JsonBean   delete(String planIds){
		naAutoRunPlanSv.delete(planIds);
		return JsonBean.success;
	}
	

	

	/**
	 * 关联自动化用例
	 * @param planId 当前计划Id
	 * @param caseIds 所要关联的用例Id
	 * @return
	 */
	@RequestMapping(value="/sys/autoPlan/connectCase")
	public @ResponseBody  JsonBean   connectCase(Long planId ,String caseIds){
		naAutoRunPlanSv.connectCase(planId, caseIds,null);
		return JsonBean.success;
	}
	
	/**
	 * 关联自动化用例组
	 * @param planId 当前计划Id
	 * @param caseIds 所要关联的用例Id
	 * @return
	 */
	@RequestMapping(value="/sys/autoPlan/connectGroup")
	public @ResponseBody  JsonBean   connectGroup(Long planId ,String groupIds){
		JsonBean json = new JsonBean();
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("info", 	naAutoRunPlanSv.connectGroup(planId, groupIds,null));
		json.setData(map);
		return json;
	}
	
	
	/**
	 * 关联用例集
	 * @param planId 当前计划Id
	 * @param caseIds 所要关联的用例Id
	 * @return
	 */
	@RequestMapping(value="/sys/autoPlan/connectCollect")
	public @ResponseBody  JsonBean   connectCollect(Long planId ,String collectIds){
		JsonBean json = new JsonBean();
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("info", naAutoRunPlanSv.connectCollect(planId, collectIds));
		json.setData(map);
		return json;
	}
	
	
/**
 * 查询已经关联用例
 * @param planId 当前计划Id
 * @param caseName 用例名称
 * @return 
 */
	@RequestMapping(value="/sys/autoPlan/queryConnectCase")
	public @ResponseBody  JsonBean   queryConnectCase(Long  planId ,String caseName,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
			){
		JsonBean json = new JsonBean();
		json.setData(naAutoRunPlanSv.queryConnectCase(planId, caseName, page, pageSize));
		return json;
	}
	
	
/**
 * 查询用例
 * @param condition  查询条件
 * @param page
 * @param pageSize
 * @return
 */
		@RequestMapping(value="/sys/autoPlan/queryUnconnectCase")
		public @ResponseBody  JsonBean   queryUnconnectCase(PlanAutoCaseRequest condition,
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
				){
			JsonBean json = new JsonBean();//
			json.setData(naAutoRunPlanSv.queryUnconnectCase(condition, page, pageSize));
			return json;
		}
		
		
	/**
	 * 删除当前计划关联的用例
	 * @param planId 当前计划id
	 * @param caseIds 要删除的用例id
	 * @return
	 */
			@RequestMapping(value="/sys/autoPlan/deleteConnectCase")
			public @ResponseBody  JsonBean   deleteConnectCase(Long planId,  String caseIds){
				naAutoRunPlanSv.deleteConnectCase(planId, caseIds);
				return JsonBean.success;
			}
			
			
			
			/**
			 * 删除当前计划关联的用例组
			 * @param planId 当前计划id
			 * @param groupIds 要删除的用例组id
			 * @return
			 */
			@RequestMapping(value="/sys/autoPlan/deleteConnectGroup")
			public @ResponseBody  JsonBean   deleteConnectGroup(Long planId ,  String groupIds){
				System.out.println("groupIds"+groupIds);
				naAutoRunPlanSv.deleteConnectGroup(planId, groupIds);
				return JsonBean.success;
			}
			
			
			/**
			 * 查询当前计划关联的用例组
			 * @param planId 当前计划id
			 * @param groupName 用例组名称
			 * @return
			 */
			@RequestMapping(value="/sys/autoPlan/queryConnectGroup")
			public @ResponseBody  JsonBean   queryConnectGroup(Long planId,String groupName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryConnectGroup(planId,groupName,page,pageSize));
				return json;
			}
			
			
			
			/**
			 * 删除当前计划关联的用例集
			 * @param planId 当前计划id
			 * @param collectIds 要删除的用例集id
			 * @return
			 */
			@RequestMapping(value="/sys/autoPlan/deleteConnectCollect")
			public @ResponseBody  JsonBean   deleteConnectCollect(Long planId ,  String collectIds){
				naAutoRunPlanSv.deleteConnectGroup(planId, collectIds);
				return JsonBean.success;
			}
			
			
			
			/**
			 * 查询当前计划关联的用例组
			 * @param planId 当前计划id
			 * @param groupIds 要删除的用例组id
			 * @return
			 */
			@RequestMapping(value="/sys/autoPlan/queryConnectCollect")
			public @ResponseBody  JsonBean   queryConnectCollect(Long  planId , String collectName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryConnectCollect(planId, collectName,page,pageSize));
				return json;
			}
			
			

			/**
			 * 查询未关联用例组
			 * @param planId 查询条件 计划
			 * @param collectName 查询条件 用例组名称
			 * @param page
			 * @param pageSize
			 * @return
			 */
			@RequestMapping(value="/sys/autoPlan/queryUnconnectGroup")
			public @ResponseBody  JsonBean   queryUnconnectGroup(Long  planId , String groupName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryUnconnectGroup(planId,groupName, page, pageSize));
				return json;
			}
			

			
			
		/**
		 * 查询用例集
		 * @param caseCollection 查询条件
		 * @param page
		 * @param pageSize
		 * @return
		 */
			@RequestMapping(value="/sys/autoPlan/queryUnconnectCollect")
			public @ResponseBody  JsonBean   queryUnconnectCollect(Long planId,String collectName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryUnconnectCollect(planId,collectName, page, pageSize));
				return json;
			}
}

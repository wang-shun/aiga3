package com.ai.am.view.controller.auto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.service.auto.AutoCaseSv;
import com.ai.am.service.auto.AutoGroupSv;
import com.ai.am.service.auto.AutoRunPlanSv;
import com.ai.am.service.auto.OnlineCaseCollectionSv;
import com.ai.am.view.controller.auto.dto.NaAutoRunPlanRequest;
import com.ai.am.view.controller.auto.dto.PlanAutoCaseRequest;
import com.ai.am.view.json.base.JsonBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author liuxx
 *  @date 2017-03-19
 */
@Api(value="AutoRunPlanController" , description="自动化计划操作接口")
@Controller
public class AutoRunPlanController {
	
	@Autowired
	private AutoRunPlanSv naAutoRunPlanSv;
	
	@ Autowired
	private AutoCaseSv autoCaseSv;
	
	@Autowired 
	private  AutoGroupSv autoGroupSv;
	
	@Autowired
	private OnlineCaseCollectionSv  caseCollectionSv;
	
	
	@RequestMapping(value="/sys/autoPlan/save",method=RequestMethod.POST)
	@ApiOperation(value="保存",notes="保存自动化计划")
	@ApiParam(required= true , name="naAutoRunPlan" , value="计划相关信息")
	public @ResponseBody  JsonBean   save(NaAutoRunPlanRequest  naAutoRunPlan){
		try {
			naAutoRunPlanSv.save(naAutoRunPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonBean.success;
	}
	
	
		@RequestMapping(value="/sys/autoPlan/queryList",method=RequestMethod.POST)
		@ApiOperation(value="查询自动化计划", notes="查询自动化计划，带分页")
		@ApiParam(name="condition" , value="查询条件")
		public @ResponseBody  JsonBean   queryList(NaAutoRunPlanRequest condition,
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
				){
			JsonBean json = new JsonBean();
			json.setData(naAutoRunPlanSv.query(condition, page, pageSize));
			return json;
		}
		


	@RequestMapping(value="/sys/autoPlan/delete",method=RequestMethod.POST)
	@ApiOperation(value="删除自动化计划", notes="根据计划id删除自动化计划，可以批量删除")
	@ApiParam(name="planIds" , value="计划ID" , required=true)
	public @ResponseBody  JsonBean   delete(String planIds){
		naAutoRunPlanSv.delete(planIds);
		return JsonBean.success;
	}
	


	@RequestMapping(value="/sys/autoPlan/connectCase",method=RequestMethod.POST)
	@ApiOperation(value="关联用例", notes="关联用例")
   @ApiImplicitParams({
	   @ApiImplicitParam(paramType="query", name="planId", value="当前计划id"),
	   @ApiImplicitParam(paramType="query", name="caseIds", value="需要关联的用例ids")
   })
	public @ResponseBody  JsonBean   connectCase(Long planId ,String caseIds){
		naAutoRunPlanSv.connectCase(planId, caseIds,null);
		return JsonBean.success;
	}
	

	@RequestMapping(value="/sys/autoPlan/connectGroup",method=RequestMethod.POST)
	@ApiOperation(value="关联用例组", notes="关联用例组")
	@ApiImplicitParams({
		   @ApiImplicitParam(paramType="query", name="planId", value="当前计划id"),
		   @ApiImplicitParam(paramType="query", name="groupIds", value="需要关联的用例组ids")
	   })
	public @ResponseBody  JsonBean   connectGroup(Long planId ,String groupIds){
		JsonBean json = new JsonBean();
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("info", 	naAutoRunPlanSv.connectGroup(planId, groupIds,null));
		json.setData(map);
		return json;
	}
	
	

	@RequestMapping(value="/sys/autoPlan/connectCollect",method=RequestMethod.POST)
	@ApiOperation(value="关联用例集", notes="关联用例集")
	@ApiImplicitParams({
		   @ApiImplicitParam(paramType="query", name="planId", value="当前计划id"),
		   @ApiImplicitParam(paramType="query", name="collectIds", value="需要关联的用例集ids")
	   })
	public @ResponseBody  JsonBean   connectCollect(Long planId ,String collectIds){
		JsonBean json = new JsonBean();
		 Map<String, String> map = new HashMap<String, String>();
		 map.put("info", naAutoRunPlanSv.connectCollect(planId, collectIds));
		json.setData(map);
		return json;
	}
	

	
	@RequestMapping(value="/sys/autoPlan/queryConnectCase",method=RequestMethod.POST)
	@ApiOperation(value="查询已关联的用例", notes="查询已关联的用例，带分页")
	@ApiImplicitParams({
		   @ApiImplicitParam(paramType="query", name="planId", value="当前计划id"),
		   @ApiImplicitParam(paramType="query", name="caseName", value="用例名称")
	   })
	public @ResponseBody  JsonBean   queryConnectCase(Long  planId ,String caseName,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
			){
		JsonBean json = new JsonBean();
		json.setData(naAutoRunPlanSv.queryConnectCase(planId, caseName, page, pageSize));
		return json;
	}
	

	
		@RequestMapping(value="/sys/autoPlan/queryUnconnectCase",method=RequestMethod.POST)
		@ApiOperation(value="查询未关联的用例", notes="查询未关联的用例，带分页")
		@ApiParam(name="condition", value="查询条件")
		public @ResponseBody  JsonBean   queryUnconnectCase(PlanAutoCaseRequest condition,
				@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
				@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
				){
			JsonBean json = new JsonBean();//
			json.setData(naAutoRunPlanSv.queryUnconnectCase(condition, page, pageSize));
			return json;
		}
		
		

			@RequestMapping(value="/sys/autoPlan/deleteConnectCase",method=RequestMethod.POST)
			@ApiOperation(value=" 删除已关联用例", notes="删除已关联用例")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="caseIds",value="用例ID")
			})
			public @ResponseBody  JsonBean   deleteConnectCase(Long planId,  String caseIds){
				naAutoRunPlanSv.deleteConnectCase(planId, caseIds);
				return JsonBean.success;
			}
			
			
			
			@RequestMapping(value="/sys/autoPlan/deleteConnectGroup",method=RequestMethod.POST)
			@ApiOperation(value=" 删除已关联用例组", notes="删除已关联用例组")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="groupIds",value="用例组ID")
			})
			public @ResponseBody  JsonBean   deleteConnectGroup(Long planId ,  String groupIds){
				naAutoRunPlanSv.deleteConnectGroup(planId, groupIds);
				return JsonBean.success;
			}
			
			

			@RequestMapping(value="/sys/autoPlan/queryConnectGroup",method=RequestMethod.POST)
			@ApiOperation(value=" 查询已关联用例组", notes="查询已关联用例组，带分页")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="groupName",value="用例组名称")
			})
			public @ResponseBody  JsonBean   queryConnectGroup(Long planId,String groupName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryConnectGroup(planId,groupName,page,pageSize));
				return json;
			}
			
			

			@RequestMapping(value="/sys/autoPlan/deleteConnectCollect",method=RequestMethod.POST)
			@ApiOperation(value=" 删除已关联用例集", notes="删除已关联用例集，可以批量")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="collectIds",value="用例集id")
			})
			public @ResponseBody  JsonBean   deleteConnectCollect(Long planId ,  String collectIds){
				naAutoRunPlanSv.deleteConnectCollect(planId, collectIds);
				return JsonBean.success;
			}
			
			
			@RequestMapping(value="/sys/autoPlan/queryConnectCollect",method=RequestMethod.POST)
			@ApiOperation(value=" 查询已关联用例集", notes="查询已关联用例集，带分页")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="collectName",value="用例集名称")
			})
			public @ResponseBody  JsonBean   queryConnectCollect(Long  planId , String collectName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryConnectCollect(planId, collectName,page,pageSize));
				return json;
			}
			
			
	
			@RequestMapping(value="/sys/autoPlan/queryUnconnectGroup",method=RequestMethod.POST)
			@ApiOperation(value=" 查询未关联用例组", notes="查询未关联用例组，带分页")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="groupName",value="用例组名称")
			})
			public @ResponseBody  JsonBean   queryUnconnectGroup(Long  planId , String groupName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryUnconnectGroup(planId,groupName, page, pageSize));
				return json;
			}
			

			
			@RequestMapping(value="/sys/autoPlan/queryUnconnectCollect",method=RequestMethod.POST)
			@ApiOperation(value=" 查询未关联用例集", notes="查询未关联用例集，带分页")
			@ApiImplicitParams({
				@ApiImplicitParam(paramType="query", name="planId", value="计划id"),
				@ApiImplicitParam(paramType="query", name="collectName",value="用例集名称")
			})
			public @ResponseBody  JsonBean   queryUnconnectCollect(Long planId,String collectName,
					@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
					@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize
					){
				JsonBean json = new JsonBean();
				json.setData(naAutoRunPlanSv.queryUnconnectCollect(planId,collectName, page, pageSize));
				return json;
			}
}

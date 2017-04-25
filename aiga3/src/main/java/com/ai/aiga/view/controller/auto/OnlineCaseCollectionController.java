package com.ai.aiga.view.controller.auto;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.auto.OnlineCaseCollectionSv;
import com.ai.aiga.view.json.CaseCollectionRequest;
import com.ai.aiga.view.json.QueryUnconnectCaseRequest;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @className AigaOnlineCaseCollectionController
 * @author liuxx
 * @date 2017-03-06
 */

@Api(value="AigaOnlineCaseCollectionController", description="操作用例集管理接口")
@Controller
public class OnlineCaseCollectionController {
	
	@Autowired
	private OnlineCaseCollectionSv caseCollectionSv;

    
	@RequestMapping(path = "/sys/case/addCase", method=RequestMethod.POST)
	@ApiOperation(value="新增", notes="添加用例集：caseName、caseType、RepairsId不为空")
	@ApiParam(required=true, name="caseCollection",value="用例集信息")
	public @ResponseBody JsonBean addCase(CaseCollectionRequest caseCollection) {
		caseCollectionSv.saveCase(caseCollection);
		return JsonBean.success;
	}
	


	@RequestMapping(path = "/sys/case/updateCase", method=RequestMethod.POST)
	@ApiOperation(value="修改", notes="修改用例集：caseName、caseType、RepairsId不为空")
	@ApiParam(required=true, name="caseCollection",value="用例集信息")
	public @ResponseBody JsonBean update(CaseCollectionRequest caseCollection) {
		caseCollectionSv.saveCase(caseCollection);
		return JsonBean.success;
	}

	

	@RequestMapping(path = "/sys/case/deleteCase",method=RequestMethod.POST)
	@ApiOperation(value="删除", notes="根据用例集id删除，可以批量删除")
	@ApiParam(required=true, name="collectId", value="用例集id")
	public @ResponseBody JsonBean delete(String collectId) {
		try {
			caseCollectionSv.deleteConnection(collectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonBean.success;
	}

	

	@RequestMapping(path = "/sys/case/queryCaseById",method=RequestMethod.POST)
	@ApiOperation(value="查询", notes="根据用例集id查询用例集信息"  )
	@ApiParam(required=true, name="collectId", value="用例集id")
	public @ResponseBody JsonBean queryCaseById(Long collectId) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.queryCaseById(collectId));
		return json;
	}

	

	@RequestMapping(path = "/sys/case/queryCase",method=RequestMethod.POST)
	@ApiOperation(value="查询", notes="查询用例集信息，带分页")
	@ApiParam(required=true, name="caseConllection", value="查询条件：用例集名称，用例集类型")
	public @ResponseBody JsonBean queryCase(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize,
			CaseCollectionRequest caseConllection) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.CaseCollectionList(caseConllection, page, pageSize));
		return json;
	}
	
	
	
	@RequestMapping(path = "/sys/case/connectCase",method=RequestMethod.POST)
	@ApiOperation(value="关联用例", notes="关联用例，可以关联多个")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query" ,name="collectId", value="当前用例集id", dataType="Long"),
		@ApiImplicitParam(paramType="query" ,name="caseIds", value="需要被关联的用例ids"),
		@ApiImplicitParam(paramType="query" ,name="types", value=" 1表示手工用例，0表示用例组，2表示自动化用例")
	})
	public @ResponseBody JsonBean connectCase(Long collectId, String caseIds, Long types) {
		String[] caseId = caseIds.split(",");
		caseCollectionSv.connectCase(collectId, caseId, types);
		return JsonBean.success;
	}


	
	
	@RequestMapping(path = "/sys/case/connectCaseCollection", method=RequestMethod.POST)
	@ApiOperation(value="关联用例集", notes="关联用例集，可以关联多个")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query" ,name="collectId", value="当前用例集id", dataType="Long"),
		@ApiImplicitParam(paramType="query" ,name="collectIds", value="需要被关联的用例集ids")
	})
	public @ResponseBody JsonBean connectCaseCollection(Long collectId, String collectIds) {
		caseCollectionSv.connectCaseCollection(collectId, collectIds);
		return JsonBean.success;
	}

	
	
	
	@RequestMapping(path =  "/sys/case/queryUnconnectCaseGroup", method=RequestMethod.POST)
	@ApiOperation(value="查询未关联的用例组", notes="查询当前用例集未关联的用例组，带分页")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query" ,name="collectId", value="当前用例集id", dataType="Long"),
		@ApiImplicitParam(paramType="query" ,name="groupName", value="用例组名称")
	})
	public @ResponseBody JsonBean queryUnconnectCaseGroup(Long collectId, String groupName,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.unconnectGroupList(collectId, groupName, page, pageSize));
		return json;
	}

	
	

	@RequestMapping(path = "/sys/case/queryConnectCaseGroup", method=RequestMethod.POST)
	@ApiOperation(value="查询已关联的用例组", notes="查询当前用例集已关联的用例组，带分页")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query" ,name="collectId", value="当前用例集id", dataType="Long"),
		@ApiImplicitParam(paramType="query" ,name="groupName", value="用例组名称")
	})
	public @ResponseBody JsonBean queryConnectCaseGroup(Long collectId, String groupName,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
	   json.setData(caseCollectionSv.connectGroupList(collectId, groupName, page, pageSize));
		return json;
	}
	

	
	@RequestMapping(path = "/sys/case/repairMan", method=RequestMethod.POST)
	@ApiOperation(value="查询用例集维护人", notes="查询用例集维护人")
	public @ResponseBody JsonBean repairMan() {
		JsonBean json = new JsonBean();
	   json.setData(caseCollectionSv.repairMan());
		return json;
	}
	
	


	@RequestMapping(path = "/sys/case/queryConnectCaseById", method=RequestMethod.POST)
	@ApiOperation(value="查询已关联的用例", notes="查询当前用例集已关联的用例，带分页")
	@ApiParam(name="request",value="查询条件" ,required=true)
	public @ResponseBody JsonBean queryConnectCaseById(QueryUnconnectCaseRequest  request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.connecCaseList(request, page, pageSize));
		return json;
	}

	

	@RequestMapping(path = "/sys/case/deleteConnectCaseGroup", method=RequestMethod.POST)
	@ApiOperation(value="删除已关联用例组", notes="删除当前用例集已关联用例组，可以批量删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query" ,name="collectId", value="当前用例集id", dataType="Long"),
		@ApiImplicitParam(paramType="query" ,name="groupIds", value="用例组ids")
	})
	public @ResponseBody JsonBean deleteConnectCaseGroup(Long collectId, String groupIds) {
		caseCollectionSv.deleteAutoCollGroups(0l, collectId, groupIds);
		return JsonBean.success;
	}
	
	
	
	@RequestMapping(path = "/sys/case/deleteConnectCase", method=RequestMethod.POST)
	@ApiOperation(value="删除已关联用例", notes="删除当前用例集已关联用例，可以批量删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query" ,name="collectId", value="当前用例集id", dataType="Long"),
		@ApiImplicitParam(paramType="query" ,name="groupIds", value="用例ids"),
		@ApiImplicitParam(paramType="query" ,name="types", value="1手工用例，2自动化用例")
	})
	public @ResponseBody JsonBean deleteConnectCase(Long collectId, String ids, Long types) {
		caseCollectionSv.deleteAutoCollGroups(types, collectId, ids);
		return JsonBean.success;
	}
	
	

	@RequestMapping(path = "/sys/case/queryUnconnectCase", method=RequestMethod.POST)
	@ApiOperation(value="查询未关联用例", notes="查询当前用例集未关联用例,带分页")
	@ApiParam(name="request",value="查询条件")
	public @ResponseBody JsonBean queryUnconnectCase(QueryUnconnectCaseRequest  request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.queryUnconnectCase(request, page, pageSize));
		return json;
	}
	
	
	
	@RequestMapping(path = "/sys/case/connectAllCase", method=RequestMethod.POST)
	@ApiOperation(value="关联全部用例", notes="关联全部用例")
	@ApiParam(name="request", value="关联全部用例")
	public @ResponseBody JsonBean connectAllCase(QueryUnconnectCaseRequest  request) {
		caseCollectionSv.connectAllCase(request);
		return JsonBean.success;
	}
	
	
}

package com.ai.aiga.view.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.AigaOnlineCaseCollectionSv;
import com.ai.aiga.view.json.CaseCollectionRequest;
import com.ai.aiga.view.json.QueryUnconnectCaseRequest;
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author liuxx
 * @date 2017-03-06
 */
@Api(value="操作用例集管理接口")
@Controller
public class AigaOnlineCaseCollectionController {
	
	@Autowired
	private AigaOnlineCaseCollectionSv caseCollectionSv;

    
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

	
	
	/**
	 * 删除用例集信息
	 * 
	 * @param collectId
	 *            用例集Id
	 * @return JsonBean 成功/失败
	 */
	@RequestMapping(path = "/sys/case/deleteCase")
	public @ResponseBody JsonBean delete(String collectId) {
		try {
			caseCollectionSv.deleteConnection(collectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonBean.success;
	}

	
	
	/**
	 * @param collectId
	 *            用例集编号
	 * @return 一条用例集信息
	 */
	@RequestMapping(path = "/sys/case/queryCaseById")
	public @ResponseBody JsonBean queryCaseById(Long collectId) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.queryCaseById(collectId));
		return json;
	}

	
	
	/**
	 * 查询用例集信息（用例集名称和类型）
	 * 
	 * @param page
	 * @param pageSize
	 * @param caseConllection
	 * @return
	 */
	@RequestMapping(path = "/sys/case/queryCase")
	public @ResponseBody JsonBean queryCase(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize,
			CaseCollectionRequest caseConllection) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.CaseCollectionList(caseConllection, page, pageSize));
		return json;
	}
	
	
	

	/**
	 * @param collectId
	 *            用例集Id
	 * @param caseIds用例Id/用例组Id
	 * @param types
	 *            1表示手工用例，0表示用例组，2表示自动化用例
	 * @return
	 */
	@RequestMapping(path = "/sys/case/connectCase")
	public @ResponseBody JsonBean connectCase(Long collectId, String caseIds, Long types) {
		String[] caseId = caseIds.split(",");
		caseCollectionSv.connectCase(collectId, caseId, types);
		return JsonBean.success;
	}


	
	
	
	/**
	 * 关联用例集
	 * 
	 * @param collectId
	 *            当前用例集Id
	 * @param collectIds
	 *            需要关联的用例集Id
	 * @return
	 */
	@RequestMapping(path = "/sys/case/connectCaseCollection")
	public @ResponseBody JsonBean connectCaseCollection(Long collectId, String collectIds) {
		caseCollectionSv.connectCaseCollection(collectId, collectIds);
		return JsonBean.success;
	}

	
	
	
	/**
	 * 根据用例组名称查询未关联的用例组
	 * 
	 * @param groupName
	 *            用例组名称
	 * @param 当前用例集id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(path = "/sys/case/queryUnconnectCaseGroup")
	public @ResponseBody JsonBean queryUnconnectCaseGroup(Long collectId, String groupName,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.unconnectGroupList(collectId, groupName, page, pageSize));
		return json;
	}

	
	
	
	/**
	 * 根据用例组名称查询已关联的用例组
	 * 
	 * @param groupName
	 *            用例组名称
	 * @param 当前用例集id
	 * @param page
	 * @param pageSize
	 * @return 已关联的用例组
	 */
	@RequestMapping(path = "/sys/case/queryConnectCaseGroup")
	public @ResponseBody JsonBean queryConnectCaseGroup(Long collectId, String groupName,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
	   json.setData(caseCollectionSv.connectGroupList(collectId, groupName, page, pageSize));
		return json;
	}
	
	/**
	 *  用例集维护人
	 * @return
	 */
	@RequestMapping(path = "/sys/case/repairMan")
	public @ResponseBody JsonBean repairMan() {
		JsonBean json = new JsonBean();
	   json.setData(caseCollectionSv.repairMan());
		return json;
	}
	
	

	/**
	 * 根据用例组名称查询已关联的手工用例/自动化用例
	 * 
	 * @param collectName
	 *            用例名称
	 * @param 当前用例集id
	 * @param page
	 * @param pageSize
	 * @return 已关联的用例
	 */
	@RequestMapping(path = "/sys/case/queryConnectCaseById")
	public @ResponseBody JsonBean queryConnectCaseById(QueryUnconnectCaseRequest  request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.connecCaseList(request, page, pageSize));
		return json;
	}

	

	/**
	 * 删除关联的用例组
	 * 
	 * @param collectId
	 *            用例集id
	 * @param groupIds
	 *            用例组id
	 * @return
	 */
	@RequestMapping(path = "/sys/case/deleteConnectCaseGroup")
	public @ResponseBody JsonBean deleteConnectCaseGroup(Long collectId, String groupIds) {
		caseCollectionSv.deleteAutoCollGroups(0l, collectId, groupIds);
		return JsonBean.success;
	}
	
	
	

	/**
	 * 删除已关联手工用例/自动化用例
	 * 
	 * @param collectId
	 * @param ids
	 * @param types
	 * @return
	 */
	@RequestMapping(path = "/sys/case/deleteConnectCase")
	public @ResponseBody JsonBean deleteConnectCase(Long collectId, String ids, Long types) {
		caseCollectionSv.deleteAutoCollGroups(types, collectId, ids);
		return JsonBean.success;
	}
	
	

	/**
	 * 查询未关联手工用例/自动化用例
	 * 
	 * @param collectId当前用例集id
	 * @param types
	 *            1手工用例，2自动化用例
	 * @return
	 */
	@RequestMapping(path = "/sys/case/queryUnconnectCase")
	public @ResponseBody JsonBean queryUnconnectCase(QueryUnconnectCaseRequest  request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.queryUnconnectCase(request, page, pageSize));
		return json;
	}
	
	
	
	
	/**
	 *关联全部手工用例/自动化用例
	 * 
	 * @param collectId当前用例集id
	 * @param types
	 *            1手工用例，2自动化用例
	 * @return
	 */
	@RequestMapping(path = "/sys/case/connectAllCase")
	public @ResponseBody JsonBean connectAllCase(QueryUnconnectCaseRequest  request) {
		caseCollectionSv.connectAllCase(request);
		return JsonBean.success;
	}
	
	
}

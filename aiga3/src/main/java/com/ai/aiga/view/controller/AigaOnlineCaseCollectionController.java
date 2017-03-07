package com.ai.aiga.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.service.AigaOnlineCaseCollectionSv;
import com.ai.aiga.view.json.CaseCollectionRequest;
import com.ai.aiga.view.json.base.JsonBean;
/**
 * 
 * @author liuxx
 * @version 1.0
 * @date 2017-03-06
 */
@Controller
@RequestMapping(path="/sys/case/")
public class AigaOnlineCaseCollectionController {
	@Autowired
	private AigaOnlineCaseCollectionSv caseCollectionSv ;
	
	
	
	/**
	 * 新增用例集信息
	 *  @param caseCollection 用例集信息
	 * @param session HttpSession
	 * @return JsonBean 成功/失败
	 */
	@RequestMapping(path="addCase")
	public @ResponseBody   JsonBean  addCase(CaseCollectionRequest caseCollection,HttpSession session){
		caseCollectionSv.saveCase(caseCollection,session);
		return JsonBean.success;
	}
	
	/**
	 * 修改用例集信息
	 * @param caseCollection 用例集信息
	 * @param session HttpSession
	 * @return  JsonBean 成功/失败
	 */
	@RequestMapping(path="updateCase")
	public  @ResponseBody JsonBean update(CaseCollectionRequest caseCollection,HttpSession session){
		caseCollectionSv.saveCase(caseCollection,session);
		return JsonBean.success;
	}
	
	/**
	 * 删除用例集信息
	 * @param collectId 用例集Id
	 * @return JsonBean 成功/失败
	 */
	@RequestMapping(path="deleteCase")
	public @ResponseBody  JsonBean delete(String collectId){
		caseCollectionSv.deleteConnection(collectId);
		return JsonBean.success;
	}

	/**
	 * @param collectId 用例集编号
	 * @return  一条用例集信息
	 */
	@RequestMapping(path="queryCaseById")
	public @ResponseBody  JsonBean queryCaseById(Long collectId){
		JsonBean json = new JsonBean();
		json.setData(	caseCollectionSv.queryCaseById(collectId));
		return json;
	}
	
	/**
	 * 查询用例集信息（用例集名称和类型）
	 * @param page  
	 * @param pageSize
	 * @param caseConllection 
	 * @return  
	 */
	@RequestMapping(path="queryCase")
	public @ResponseBody JsonBean queryCase(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int page,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_SIZE_DEFAULT + "") int pageSize,
			CaseCollectionRequest caseConllection) {
		JsonBean json = new JsonBean();
		json.setData(caseCollectionSv.CaseCollectionList(caseConllection,page,pageSize));
		return json;
	}
	
	/**
	 * 关联用例
	 * @return
	 */
	@RequestMapping(path="connectCase")
	public @ResponseBody JsonBean connectCase(Long collectId,String caseIds) {
		String[] caseId = caseIds.split(",");
		caseCollectionSv.connectCase(collectId, caseId, 1L); //1表示用例，0表示用例组
		return JsonBean.success;
	}
	
	/**
	 * 关联全部用例
	 * @return
	 */
	@RequestMapping(path="connectAllCase")
	public @ResponseBody JsonBean connectAllCase(Long collectId) {
		
		return JsonBean.success;
	}
	
	
	/**
	 * 关联用例组
	 * @return
	 */
	@RequestMapping(path="connectCaseGroup")
	public @ResponseBody JsonBean connectCaseGroup(Long collectId ,String groupIds) {
		String[] groupId = groupIds.split(",");
		caseCollectionSv.connectCase(collectId, groupId, 0L); //1表示用例，0表示用例组
		return JsonBean.success;
	}

	
	/**
	 * 关联用例集
	 * @param collectId 当前用例集Id
	 * @param collectIds 需要关联的用例集Id
	 * @return
	 */
	@RequestMapping(path="connectCaseCollection")
	public @ResponseBody JsonBean connectCaseCollection(Long collectId ,String collectIds) {
	     caseCollectionSv.connectCaseCollection(collectId,collectIds);
		return JsonBean.success;
	}
}

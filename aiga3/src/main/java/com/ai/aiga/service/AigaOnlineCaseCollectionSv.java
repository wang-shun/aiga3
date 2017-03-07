package com.ai.aiga.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResultUtils;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCollGroupCaseDao;
import com.ai.aiga.dao.NaAutoCollectionDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.domain.NaAutoCollection;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.CaseCollectionRequest;


@Service
@Transactional
public class AigaOnlineCaseCollectionSv extends BaseService{
	
	@Autowired
	private	NaAutoCollectionDao caseDao;
	@Autowired
	private NaAutoCollGroupCaseDao  dao;
	/**
	 * 
	 * @param caseCollection  用例集信息
	 */
	public void  saveCase(CaseCollectionRequest caseCollection,HttpSession session){
		if(caseCollection==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		//用例集名称
		if(StringUtils.isBlank(caseCollection.getCollectName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"caseName");
		}
		//用例集类型
		if(StringUtils.isBlank(String.valueOf(caseCollection.getCaseType()))){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"caseType");
		}
		//用例集维护人
		if(StringUtils.isBlank(String.valueOf(caseCollection.getRepairsId()))){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"RepairsId");
		}
		NaAutoCollection caseConnections = new NaAutoCollection();
		/**
		 * 如果用例集编号不存在，则新增。默认关联用例数量默认0
		 * 如果用例集编号存在，则修改
		 */
		System.out.println("********************88"+caseCollection.getCollectId());
		if(StringUtils.isBlank(String.valueOf(caseCollection.getCollectId()))){
			caseConnections.setCaseNum(0L);
		}else{
			caseConnections.setCollectId(caseCollection.getCollectId());
			caseConnections.setCaseNum(caseCollection.getCaseNum());
		}
		String user = (String) session.getAttribute("");
		caseConnections.setCollectName(caseCollection.getCollectName());
		caseConnections.setCaseType(caseCollection.getCaseType());
		caseConnections.setRepairsId(caseCollection.getRepairsId());
		//系统默认设定
		caseConnections.setCreateDate( new Date());
		//caseConnections.setOpId();
		caseDao.save(caseConnections);
	}
	
	/**
	 * 删除用例集信息
	 * @param collectId 用例集信息
	 */
	public void deleteConnection(String collectId){
		if(StringUtils.isBlank(collectId)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"collectId");
		}
		String[] collectIds = collectId.split(",");
		for(int i=0;i<collectIds.length;i++){
			System.out.println("*******************"+collectIds[i]);
			//删除用例集信息
			 caseDao.delete(Long.parseLong(collectIds[i]));
			 //通过collectId删除用例集-用例关联关系表里面信息
			 dao.deleteByCollectId(Long.parseLong(collectIds[i]));
			}
		}
	
	/**
	 * 根据collectId查询用例集信息
	 * @param collectId  用例集信息
	 * @return  aigaOnlineCaseCollection
	 */
	public  NaAutoCollection queryCaseById(Long collectId){
		return caseDao.findByCollectId(collectId);
	}
	
	
	/**
	 * 根据collectId查询用例集信息
	 * @param collectId  用例集信息
	 * @return  aigaOnlineCaseCollection
	 */
	public  void  connectCaseCollection(Long collectId ,String collectIds){
        //需要关联的用例集Id查询出需要关联的用例/用例组信息
		  List<NaAutoCollGroupCase> list = dao.findByCollectId(collectIds);
		if(list!=null&&list.size()>0){
			for(int i=0;i>list.size();i++){
				NaAutoCollGroupCase  collGroupCaseNew = new NaAutoCollGroupCase();
				NaAutoCollGroupCase  collGroupCase = list.get(i);
				collGroupCaseNew.setCollectId(collectId);
				collGroupCaseNew.setElementId(collGroupCase.getElementId());
				collGroupCaseNew.setElementType(collGroupCase.getElementType());
				collGroupCaseNew.setCreatorId(collGroupCase.getCreatorId());
				collGroupCaseNew.setUpdateTime(new Date());
				dao.save(collGroupCaseNew);	
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param caseCollection
	 * @param pageNumber  
	 * @param pageSize
	 * @return
	 */
public Object CaseCollectionList(CaseCollectionRequest  caseCollection, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		if(caseCollection != null){
			//用例集名称
			if(StringUtils.isNotBlank(caseCollection.getCollectName())){
				cons.add(new Condition("collectName", "%".concat(caseCollection.getCollectName()).concat("%"), Condition.Type.LIKE));
			}
			//用例集类型
			if(StringUtils.isNotBlank(caseCollection.getCollectName())){
				cons.add(new Condition("caseType",String.valueOf (caseCollection.getCaseType()), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return caseDao.search(cons, pageable);
	}


/**
 * 
 * @param collectId 用例集id
 * @param caseId   用例/用例组id
 * @param isGroup 是否是用例组
 */
public  void connectCase(Long collectId,String[] caseId,Long isGroup){
	int caseNum = 0;//关联用例数量
	if(StringUtils.isBlank(String.valueOf(collectId))){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null,"collectId");
	}
	if(caseId.length==0){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null,"caseIds");
	}
	//保存用例-用例集关联关系
	for (int i=0;i<caseId.length;i++){
		NaAutoCollGroupCase autoCollGroupCase = new NaAutoCollGroupCase();
		autoCollGroupCase.setCollectId(collectId);
		autoCollGroupCase.setElementId(Long.parseLong(caseId[i]));
		autoCollGroupCase.setElementType(isGroup);
		autoCollGroupCase.setUpdateTime(new Date());
		autoCollGroupCase.setCreatorId(0L);  //现在默认先写0
		dao.save(autoCollGroupCase);
		caseNum++;
	}
	//更新用例集表里面的关联用例数量
	if(caseNum>0){
		caseDao.updateCaseNum(caseNum, collectId);
	}
}



}

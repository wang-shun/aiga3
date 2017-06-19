package com.ai.aiga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.QuestionInfoDao;
import com.ai.aiga.domain.QuestionInfo;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.QuestionInfoRequest;

@Service
@Transactional
public class QuestionInfoSv extends BaseService {

	@Autowired
	private QuestionInfoDao questionInfoDao;
	
	public List<QuestionInfo>findQuestionInfos(){
		return questionInfoDao.findAll();
	}
	
	public QuestionInfo findOne(Long quesId){
		if(quesId==null||quesId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findOne(quesId);
	}

	//主键查
	public List<QuestionInfo>findByQuesId(Long quesId){
		if(quesId==null||quesId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findByQuesId(quesId);
	}
	
	//问题分类查
	public List<QuestionInfo>findByQuesType(String quesType){
		if(quesType==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findByQuesType(quesType);
	}
	
	//一级分类查
	public List<QuestionInfo>findByFirstCategory(String firstCategory){
		if(firstCategory==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findByFirstCategory(firstCategory);
	}
	
	//一二级分类查
	public List<QuestionInfo>findByFirstCategoryAndSecondCategory(String firstCategory, String secondCategory){
		if(firstCategory==null||secondCategory==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findByFirstCategoryAndSecondCategory(firstCategory, secondCategory);
	}
	
	//一二三级分类查
	public List<QuestionInfo>findByFirstCategoryAndSecondCategoryAndThirdCategory(String firstCategory, String secondCategory, String thirdCategory){
		if(firstCategory==null||secondCategory==null||thirdCategory==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findByFirstCategoryAndSecondCategoryAndThirdCategory(firstCategory, secondCategory, thirdCategory);
	}
	
	//疑难问题查
	public List<QuestionInfo>findByDiffProblem(String diffProblem){
		if(diffProblem==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		return questionInfoDao.findByDiffProblem(diffProblem);
	}
	
	public void delete(Long quesId){
		if(quesId==null||quesId<0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		questionInfoDao.delete(quesId);
	}
	
	public void save(QuestionInfoRequest request){
		
		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setQuesId(request.getQuesId());
		questionInfo.setQuesType(request.getQuesType());
		questionInfo.setFirstCategory(request.getFirstCategory());
		questionInfo.setSecondCategory(request.getSecondCategory());
		questionInfo.setThirdCategory(request.getThirdCategory());
		questionInfo.setDiffProblem(request.getDiffProblem());
		questionInfo.setAbstracts(request.getAbstracts());
		questionInfo.setOccurEnvironment(request.getOccurEnvironment());
		questionInfo.setBelongProject(request.getBelongProject());
		questionInfo.setIdFirst(request.getIdFirst());
		questionInfo.setIdSecond(request.getIdSecond());
		questionInfo.setIdThird(request.getIdThird());
		questionInfo.setSysVersion(request.getSysVersion());
		questionInfo.setPriority(request.getPriority());
		questionInfo.setDefectLevel(request.getDefectLevel());
		questionInfo.setState(request.getState());
		questionInfo.setRequestInfo(request.getRequestInfo());
		questionInfo.setIdentifiedInfo(request.getIdentifiedInfo());
		questionInfo.setSolvedInfo(request.getSolvedInfo());
		questionInfo.setCreateDate(request.getCreateDate());
		questionInfo.setModifyDate(request.getModifyDate());
		questionInfo.setReportor(request.getReportor());
		questionInfo.setAppointedPerson(request.getAppointedPerson());
		questionInfo.setExt1(request.getExt1());
		questionInfo.setExt2(request.getExt2());
		questionInfo.setExt3(request.getExt3());
		
		questionInfoDao.save(questionInfo);
	}
	
	public void update(QuestionInfoRequest request){
		
		QuestionInfo questionInfo = new QuestionInfo();
		questionInfo.setQuesId(request.getQuesId());
		questionInfo.setQuesType(request.getQuesType());
		questionInfo.setFirstCategory(request.getFirstCategory());
		questionInfo.setSecondCategory(request.getSecondCategory());
		questionInfo.setThirdCategory(request.getThirdCategory());
		questionInfo.setDiffProblem(request.getDiffProblem());
		questionInfo.setAbstracts(request.getAbstracts());
		questionInfo.setOccurEnvironment(request.getOccurEnvironment());
		questionInfo.setBelongProject(request.getBelongProject());
		questionInfo.setIdFirst(request.getIdFirst());
		questionInfo.setIdSecond(request.getIdSecond());
		questionInfo.setIdThird(request.getIdThird());
		questionInfo.setSysVersion(request.getSysVersion());
		questionInfo.setPriority(request.getPriority());
		questionInfo.setDefectLevel(request.getDefectLevel());
		questionInfo.setState(request.getState());
		questionInfo.setRequestInfo(request.getRequestInfo());
		questionInfo.setIdentifiedInfo(request.getIdentifiedInfo());
		questionInfo.setSolvedInfo(request.getSolvedInfo());
		questionInfo.setCreateDate(request.getCreateDate());
		questionInfo.setModifyDate(request.getModifyDate());
		questionInfo.setReportor(request.getReportor());
		questionInfo.setAppointedPerson(request.getAppointedPerson());
		questionInfo.setExt1(request.getExt1());
		questionInfo.setExt2(request.getExt2());
		questionInfo.setExt3(request.getExt3());
		
		questionInfoDao.save(questionInfo);
	}
}

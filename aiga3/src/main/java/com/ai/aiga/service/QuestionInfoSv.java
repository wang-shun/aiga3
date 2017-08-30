package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.QuestionInfoDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
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
	
	public List<Map> findQuestionStatePie(){
		String sql = "SELECT t.state ,count(T.state) as cnt FROM question_info t Group by t.state";
		return questionInfoDao.searchByNativeSQL(sql);	
	}
	
	public List<Map> findQuestionStatePie(String idFirst){
		String sql = " SELECT t.state ,count(T.state) as cnt FROM question_info t where t.id_First = :idFirst Group by t.state ";
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		params.add(new ParameterCondition("idFirst", idFirst));
		return questionInfoDao.searchByNativeSQL(sql,params);	
	}
	
	public List<Map> findQuestionId(){
		String sql = "select aiam.seq_ques_info.nextval as quesId from dual ";
		return questionInfoDao.searchByNativeSQL(sql);	
	}
	
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
		Date date = new Date();
		questionInfo.setCreateDate(date);
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

	public Page<QuestionInfo> listInfo(QuestionInfoRequest condition, int pageNumber, int pageSize) {
        List<Condition> cons = new ArrayList<Condition>();
    	if(StringUtils.isNoneBlank(condition.getQuesType())){
    		cons.add(new Condition("quesType", condition.getQuesType(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getFirstCategory())){
    		cons.add(new Condition("firstCategory", condition.getFirstCategory(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getSecondCategory())){
    		cons.add(new Condition("secondCategory", condition.getSecondCategory(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getThirdCategory())){
    		cons.add(new Condition("thirdCategory", condition.getThirdCategory(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getDiffProblem())){
    		cons.add(new Condition("diffProblem", "%".concat(condition.getDiffProblem()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getState())){
    		cons.add(new Condition("state", condition.getState(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getOccurEnvironment())){
    		cons.add(new Condition("occurEnvironment", "%".concat(condition.getOccurEnvironment()).concat("%"), Condition.Type.LIKE));
    	}
    	if(StringUtils.isNoneBlank(condition.getBelongProject())){
    		cons.add(new Condition("belongProject", condition.getBelongProject(), Condition.Type.EQ));
    	}
    	if(condition.getQuesId()==0){
    		cons.add(new Condition("quesId", condition.getQuesId(), Condition.Type.GT));
    	}
    	if(condition.getQuesId()!=0){
    		cons.add(new Condition("quesId", condition.getQuesId(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getSysVersion())){
    		cons.add(new Condition("sysVersion", condition.getSysVersion(), Condition.Type.EQ));
    	}
    	if(StringUtils.isNoneBlank(condition.getState())){
    		cons.add(new Condition("state", condition.getState(), Condition.Type.EQ));
    	}
    	
        if(pageNumber < 0){
            pageNumber = 0;
        }
        if(pageSize <= 0){
            pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
        }
        Pageable pageable = new PageRequest(pageNumber, pageSize);
		return questionInfoDao.search(cons, pageable);
	}

}

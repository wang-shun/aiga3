package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaCaseTemplateDao;
import com.ai.aiga.dao.NaTestCaseDao;
import com.ai.aiga.dao.NaTestCaseParamDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.domain.NaTestCase;
import com.ai.aiga.domain.NaTestCaseParam;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.json.CaseInstanceRequest;
import com.ai.aiga.view.json.CaseTestResponse;
import com.ai.aiga.view.json.CaseTmeplateResponse;
import com.ai.aiga.view.json.Factor;

@Service
@Transactional
public class CaseSv extends BaseService{
	
	@Autowired
	private NaTestCaseDao testCaseDao;
	
	@Autowired
	private NaTestCaseParamDao testCaseParamDao;
	
	@Autowired
	private CaseTemplateSv caseTemplateSv;

	public Page<NaTestCase> listCase(int functionId, String caseName, int important, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(StringUtils.isNoneBlank(caseName)){
			cons.add(new Condition("testName", "%".concat(caseName).concat("%"), Condition.Type.LIKE));
		}
		
		if(functionId > 0){
			cons.add(new Condition("funId", functionId, Condition.Type.EQ));
		}
		
		if(important > 0){
			cons.add(new Condition("important", important, Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return testCaseDao.search(cons, pageable);
	}

	
	public void delCase(List<Long> caseIds) {
		
		if(caseIds == null || caseIds.size() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		testCaseDao.deleteByTestIdIn(caseIds);
		testCaseParamDao.deleteByTestIdIn(caseIds);
		
	}


	public CaseTestResponse getCaseTest(Long testId) {
		
		if(testId == null || testId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "testId");
		}
		
		NaTestCase testCase = testCaseDao.findOne(testId);
		CaseTestResponse response = BeanMapper.map(testCase, CaseTestResponse.class);
		
		List<NaTestCaseParam> list = testCaseParamDao.findByTestId(testId);
		response.setFactors(list);
		
		return response;
	}


	public void saveTest(CaseInstanceRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		
		if(request.getCaseId() == null || request.getCaseId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		CaseTmeplateResponse resopnse = caseTemplateSv.getTmeplate(request.getCaseId());
		if(resopnse == null){
			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "caseId");
		}
		
		NaTestCase testcase = BeanMapper.map(resopnse, NaTestCase.class);
		testcase.setTestName(request.getTestName());
		testCaseDao.save(testcase);
		
		List<NaTestCaseParam> params = structureCaseFactor(request.getFactors());
		if(params != null){
			for(NaTestCaseParam one : params){
				one.setTestId(testcase.getTestId());
			}
			testCaseParamDao.save(params);
		}
		
	}


	public void updateTest(CaseInstanceRequest request) {
		
	}
	
	
	private List<NaTestCaseParam> structureCaseFactor(String factors) {
		
		List<NaTestCaseParam> list = new ArrayList<NaTestCaseParam>();
		
		if(factors != null){
			
			list = JsonUtil.jsonToList(factors, NaTestCaseParam.class);
			
		}
		
		return list;
	}

}
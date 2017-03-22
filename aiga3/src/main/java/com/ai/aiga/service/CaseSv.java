package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.cache.AigaFunFolderCacheCmpt;
import com.ai.aiga.cache.AigaSubSysFolderCacheCmpt;
import com.ai.aiga.cache.AigaSystemFolderCacheCmpt;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaTestCaseDao;
import com.ai.aiga.dao.NaTestCaseParamDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AigaFunFolder;
import com.ai.aiga.domain.AigaSubSysFolder;
import com.ai.aiga.domain.AigaSystemFolder;
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
import com.ai.aiga.view.json.NaTestCaseResponse;

@Service
@Transactional
public class CaseSv extends BaseService{
	
	@Autowired
	private NaTestCaseDao testCaseDao;
	
	@Autowired
	private NaTestCaseParamDao testCaseParamDao;
	
	@Autowired
	private CaseTemplateSv caseTemplateSv;
	
	@Autowired
	private AigaSystemFolderCacheCmpt sysCache;
	@Autowired
	private AigaSubSysFolderCacheCmpt subSysCache;
	@Autowired
	private AigaFunFolderCacheCmpt funSysCache;
	
	public Page<NaTestCaseResponse> listCase(int sysId, int sysSubId, int funId, 
			String testName, int important, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(StringUtils.isNoneBlank(testName)){
			cons.add(new Condition("testName", "%".concat(testName).concat("%"), Condition.Type.LIKE));
		}
		
		if(sysId > 0){
			cons.add(new Condition("sysId", sysId, Condition.Type.EQ));
		}
		
		if(sysSubId > 0){
			cons.add(new Condition("sysSubId", sysSubId, Condition.Type.EQ));
		}
		
		if(funId > 0){
			cons.add(new Condition("funId", funId, Condition.Type.EQ));
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
		
		Page<NaTestCase> result = testCaseDao.search(cons, pageable);
		
		List<NaTestCase> content = result.getContent();
		
		List<NaTestCaseResponse> list = new ArrayList<NaTestCaseResponse>();
		if(content != null){
			for(NaTestCase one : content){
				NaTestCaseResponse rep = BeanMapper.map(one, NaTestCaseResponse.class);
				if(one.getSysId() != null){
					AigaSystemFolder asf = sysCache.getSysFolder(one.getSysId());
					rep.setSysName(asf.getSysName());
				}
				
				if(one.getSysSubId() != null){
					AigaSubSysFolder assf = subSysCache.getSubSys(one.getSysSubId());
					rep.setSysSubName(assf.getSysName());
				}
				
				if(one.getFunId() != null){
					AigaFunFolder fun = funSysCache.getFunsByFunid(one.getFunId());
					rep.setFunName(fun.getBusiLabel());
				}
				
				list.add(rep);
			}
		}
		
		return new PageImpl<NaTestCaseResponse>(list, pageable, result.getTotalElements());
	}

//	public Page<NaTestCase> listCase(int functionId, String testName, int important, int pageNumber, int pageSize) {
//		
//		List<Condition> cons = new ArrayList<Condition>();
//		
//		if(StringUtils.isNoneBlank(testName)){
//			cons.add(new Condition("testName", "%".concat(testName).concat("%"), Condition.Type.LIKE));
//		}
//		
//		if(functionId > 0){
//			cons.add(new Condition("funId", functionId, Condition.Type.EQ));
//		}
//		
//		if(important > 0){
//			cons.add(new Condition("important", important, Condition.Type.EQ));
//		}
//		
//		if(pageNumber <= 0){
//			pageNumber = 0;
//		}else{
//			pageNumber--;
//		}
//		
//		if(pageSize <= 0){
//			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
//		}
//
//		Pageable pageable = new PageRequest(pageNumber, pageSize);
//		
//		return testCaseDao.search(cons, pageable);
//	}

	
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
		
		String[] names = StringUtils.split(testCase.getTestName(), "_");
		if(names.length == 2){
			response.setCaseName(names[0] + "_");
			response.setSimpleTestName(names[1]);
		}
		
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
		
		if(testCaseDao.existsByTestName(request.getTestName())){
			BusinessException.throwBusinessException("测试用例的名字已经存在.");
		}
		
		
		CaseTmeplateResponse resopnse = caseTemplateSv.getTmeplate(request.getCaseId());
		if(resopnse == null){
			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "caseId");
		}
		
		NaTestCase testcase = BeanMapper.map(resopnse, NaTestCase.class);
		testcase.setTestName(request.getTestName());
		
		List<NaTestCaseParam> params = structureCaseFactor(request.getFactors());
		
		StringBuilder testdesc = new StringBuilder();
		if(params != null){
			testdesc.append("根据因子:");
			for(int i = 0; i < params.size(); i++){
				NaTestCaseParam one = params.get(i);
				testdesc.append("[");
				testdesc.append(one.getFactorName());
				testdesc.append("=");
				testdesc.append(one.getFactorValue());
				testdesc.append("]");
				
				if(i < params.size() - 1){
					testdesc.append(",");
				}
			}
			testdesc.append("进行测试...");
		}
		testcase.setTestDesc(testdesc.toString());
		testCaseDao.save(testcase);
		
		if(params != null){
			for(NaTestCaseParam one : params){
				one.setTestId(testcase.getTestId());
			}
			testCaseParamDao.save(params);
		}
		
	}


	public void updateTest(CaseInstanceRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		
		if(request.getTestId() == null || request.getTestId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "testId");
		}
		
		NaTestCase testCase = testCaseDao.getOne(request.getTestId());
		
		if(testCase == null){
			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "testId");
		}
		
		if(request.getPreResult() != null){
			testCase.setPreResult(request.getPreResult());
		}
		
		if(request.getTestDesc() != null){
			testCase.setTestDesc(request.getTestDesc());
		}
		
		
		testCaseDao.save(testCase);
		
		List<NaTestCaseParam> params = structureCaseFactor(request.getFactors());
		if(params != null){
			List<NaTestCaseParam> saved = new ArrayList<NaTestCaseParam>(); 
			for(NaTestCaseParam one : params){
				NaTestCaseParam realOne = testCaseParamDao.getOne(one.getParamId());
				if(one.getRemark() != null){
					realOne.setRemark(one.getRemark());
				}
				
				realOne.setFactorValue(one.getFactorValue());
				realOne.setFactorOrder(one.getFactorOrder());
				
				saved.add(realOne);
			}
			testCaseParamDao.save(saved);
		}
		
	}
	
	
	private List<NaTestCaseParam> structureCaseFactor(String factors) {
		
		List<NaTestCaseParam> list = new ArrayList<NaTestCaseParam>();
		
		if(factors != null){
			
			list = JsonUtil.jsonToList(factors, NaTestCaseParam.class);
			
		}
		
		return list;
	}

	public void copyTest(Long testId, String testName) {
		
		
		if(testId == null || testId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "testId");
		}
		
		if(StringUtils.isBlank(testName)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "testName");
		}
		
		NaTestCase testCase = testCaseDao.getOne(testId);
		if(testCase == null){
			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "testId");
		}
		
		if(testCaseDao.existsByTestName(testName)){
			BusinessException.throwBusinessException("测试用例的名字已经存在.");
		}
		
		
		NaTestCase newtestCase = new NaTestCase();
		newtestCase.setBusiId(testCase.getBusiId());
		newtestCase.setCaseId(testCase.getCaseId());
		newtestCase.setCaseType(testCase.isCaseType());
		newtestCase.setFunId(testCase.getFunId());
		newtestCase.setImportant(testCase.getImportant());
		newtestCase.setPreResult(testCase.getPreResult());
		newtestCase.setScId(testCase.getScId());
		newtestCase.setSysId(testCase.getSysId());
		newtestCase.setSysSubId(testCase.getSysSubId());
		newtestCase.setTestDesc(testCase.getTestDesc());
		newtestCase.setTestType(testCase.getTestType());
		
		
		newtestCase.setTestName(testName);
		
		testCaseDao.save(newtestCase);
		
		List<NaTestCaseParam> list = testCaseParamDao.findByTestId(testId);
		List<NaTestCaseParam> newlist = BeanMapper.mapList(list, NaTestCaseParam.class, NaTestCaseParam.class);
		
		if(newlist != null){
			for(NaTestCaseParam param : newlist){
				param.setTestId(newtestCase.getTestId());
			}
		}
		testCaseParamDao.save(newlist);
	
				
//		testCase.setTestId(0);
//		testCase.setTestName(testName);
//		
//		testCaseDao.save(testCase);
		
	}

}

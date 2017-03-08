package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaCaseFactorDao;
import com.ai.aiga.dao.NaCaseTemplateDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaCaseFactor;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.json.CaseTmeplateResponse;
import com.ai.aiga.view.json.TemplateRequest;

@Service
@Transactional
public class CaseTemplateSv extends BaseService{
	
	@Autowired
	private NaCaseTemplateDao caseTemplateDao;
	@Autowired
	private NaCaseFactorDao caseFactorDao;
	
	public Object listTmeplate(NaCaseTemplate condition, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			if(StringUtils.isNotBlank(condition.getCaseName())){
				cons.add(new Condition("caseName", "%".concat(condition.getCaseName()).concat("%"), Condition.Type.LIKE));
			}
			
			if(condition.getSysId() != null){
				cons.add(new Condition("sysId", condition.getSysId(), Condition.Type.EQ));
			}
			
			if(condition.getSysSubId() != null){
				cons.add(new Condition("sysSubId", condition.getSysSubId(), Condition.Type.EQ));
			}
			
			if(condition.getFunId() != null){
				cons.add(new Condition("funId", condition.getFunId(), Condition.Type.EQ));
			}
			
			if(condition.getBusiId() != null){
				cons.add(new Condition("busiId", condition.getBusiId(), Condition.Type.EQ));
			}
			
			if(condition.getScId() != null){
				cons.add(new Condition("scId", condition.getScId(), Condition.Type.EQ));
			}
			
			
			if(condition.getImportant() != null){
				cons.add(new Condition("important", condition.getImportant(), Condition.Type.EQ));
			}
		}
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return caseTemplateDao.search(cons, pageable);
	}
	

	public void saveTmeplate(TemplateRequest request) {
		
		if(request == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}

		long start = System.currentTimeMillis();
		NaCaseTemplate template = BeanMapper.map(request, NaCaseTemplate.class);
		System.out.println("花费时间:" + (System.currentTimeMillis() - start));
		
		caseTemplateDao.save(template);
		
	}


	public void delTmeplate(Long caseId) {
		if(caseId == null || caseId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		NaCaseTemplate temp = caseTemplateDao.findOne(caseId);
		if(temp != null){
			temp.setStates((byte) 0);
			caseTemplateDao.save(temp);
			
			//caseTemplateDao.delete(caseId);
			//caseFactorDao.deleteByCaseId(caseId);
		}
		
		
		
		
	}


	public CaseTmeplateResponse getTmeplate(Long caseId) {
	
		if(caseId == null || caseId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "caseId");
		}
		
		NaCaseTemplate temp = caseTemplateDao.findOne(caseId);
		
		if(temp == null){
			BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "caseId");
		}
		
		List<NaCaseFactor> facs = caseFactorDao.findByCaseId(caseId);
		
		
		CaseTmeplateResponse response = BeanMapper.map(temp, CaseTmeplateResponse.class);
		response.setFactors(facs);
		
		return response;
	}

}
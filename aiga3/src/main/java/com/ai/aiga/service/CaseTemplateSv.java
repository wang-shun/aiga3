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
import com.ai.aiga.dao.NaCaseTemplateDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class CaseTemplateSv extends BaseService{
	
	@Autowired
	private NaCaseTemplateDao caseTemplateDao;
	
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
	
	public static void main(String[] args) {
		
		System.out.println();
		
	}

}

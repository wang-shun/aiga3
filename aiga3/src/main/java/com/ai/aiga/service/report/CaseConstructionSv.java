package com.ai.aiga.service.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.report.CaseConstructionRequest;

/**
 * @ClassName: CaseConstructionSv
 * @author: dongch
 * @date: 2017年4月24日 下午3:24:28
 * @Description:
 * 
 */
@Service
@Transactional
public class CaseConstructionSv extends BaseService{

	/**
	 * @ClassName: CaseConstructionSv :: list
	 * @author: dongch
	 * @date: 2017年4月24日 下午3:27:07
	 *
	 * @Description:功能用例建设报表查询
	 * @param request
	 * @return          
	 */
	public Object list(CaseConstructionRequest request) {
		
		return null;
	}

}


package com.ai.aiga.service.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchTaskPlanDao;
import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchTaskPlanSv extends BaseService {
	@Autowired
	private ArchTaskPlanDao archTaskPlanDao;
	
	//task 扫表查询
	public List<ArchTaskPlan> taskGet() {
		return archTaskPlanDao.findByState('U');		
	}
}

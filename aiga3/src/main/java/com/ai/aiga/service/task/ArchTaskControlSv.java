package com.ai.aiga.service.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchTaskControlDao;
import com.ai.aiga.dao.ArchTaskPlanDao;
import com.ai.aiga.domain.ArchTaskControl;

@Service
@Transactional
public class ArchTaskControlSv {
	@Autowired
	private ArchTaskControlDao archTaskControlDao;
	
	//任务校验
	public Boolean check(String taskIns,String nextFireTime) {
		List<ArchTaskControl> insTaskList = archTaskControlDao.findByTaskInsAndNextFireTime(taskIns, nextFireTime);
		if(insTaskList!=null && insTaskList.size()>0) {
			return false;
		} else {
			return true;
		}
	}
	
	//修改
	public void update(ArchTaskControl insTask) {
		archTaskControlDao.save(insTask);
	}
}

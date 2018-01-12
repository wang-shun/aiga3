package com.ai.aiga.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.AmCoreIndexTreeDao;
import com.ai.aiga.domain.AmCoreIndexTree;
import com.ai.aiga.service.base.BaseService;
@Service
@Transactional
public class AmCoreIndexTreeSv extends BaseService {
	
	@Autowired
	private AmCoreIndexTreeDao amCoreIndexTreeDao;
    
	public List<AmCoreIndexTree> findAllIndexByDay(){
		return amCoreIndexTreeDao.findAllIndexByDay();
	}
	public List<AmCoreIndexTree> findAllIndexByMonth(){
		return amCoreIndexTreeDao.findAllIndexByMonth();
	}
	
}

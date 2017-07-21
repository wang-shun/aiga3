package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.ArchDbConnectDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ArchDbConnect;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
@Service
@Transactional
public class ArchDbConnectSv extends BaseService {

	@Autowired
	private ArchDbConnectDao archDbConnectDao;
	
	public List<ArchDbConnect>findArchDbConnect(){
		return archDbConnectDao.findAll();
	}
	
	public List<ArchDbConnect>selectKey123(ArchDbConnectSelects condition){
		List<Condition>cons = new ArrayList<Condition>();
		if(condition.getIndexId() != null){
    		cons.add(new Condition("indexId", condition.getIndexId(), Condition.Type.EQ));
		}
		if(StringUtils.isNoneBlank(condition.getKey1())){
			cons.add(new Condition("key1", condition.getKey1(), Condition.Type.EQ));
		}
		if(StringUtils.isNoneBlank(condition.getKey2())){
			cons.add(new Condition("key2", condition.getKey2(), Condition.Type.EQ));
		}
		if(StringUtils.isNoneBlank(condition.getKey3())){
			cons.add(new Condition("key3", condition.getKey3(), Condition.Type.EQ));
		}
		return archDbConnectDao.search(cons);
	}
	
	public List<ArchDbConnect>justKey1(long indexId){
		return archDbConnectDao.findByIndexId(indexId);
	}
}

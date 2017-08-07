package com.ai.am.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.ArchSrvManageDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.domain.ArchDbConnect;
import com.ai.am.domain.ArchSrvManage;
import com.ai.am.service.base.BaseService;
import com.ai.am.view.controller.archiQuesManage.dto.ArchDbConnectSelects;
import com.ai.am.view.controller.archiQuesManage.dto.ArchSrvManageSelects;
@Service
@Transactional
public class ArchSrvManageSv extends BaseService {

	@Autowired
	private ArchSrvManageDao archSrvManageDao;
	
	public List<ArchSrvManage>findArchSrvManages(){
		return archSrvManageDao.findAll();
	}
	
	public List<ArchSrvManage>selectKey123(ArchSrvManageSelects condition){
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
		return archSrvManageDao.search(cons);
	}
}

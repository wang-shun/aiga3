package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.domain.ArchDbSession;
import com.ai.aiga.dao.ArchDbSessionDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchDbSessionSv extends BaseService {

	@Autowired
	private ArchDbSessionDao archDbSessionDao;
	
	//find
	public List<ArchDbSession> findAll(){
		return archDbSessionDao.findAll();
	}

	public List<ArchDbSession> queryByCondition(ArchDbSession condition) throws ParseException {
		StringBuilder nativeSql = new StringBuilder("select key3 from aiam.Arch_Db_Session_20180309 a where 1=1 "); 
		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		
		if (condition.getCreateDate() != null) {
			nativeSql.append(" and to_char(a.create_date,'YYYYMMDD')  = :createDate ");
			params.add(new ParameterCondition("createDate", condition.getCreateDate()));
		}

		return archDbSessionDao.searchByNativeSQL(nativeSql.toString(), params, ArchDbSession.class);
		
	}
}

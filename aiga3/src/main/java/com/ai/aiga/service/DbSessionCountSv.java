package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.DbSessionCountDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.dbSessionCountControl.dto.DbSessionData;

@Service
@Transactional
public class DbSessionCountSv extends BaseService {

	@Autowired
	private DbSessionCountDao dbSessionCountDao;


	public List<DbSessionData> queryByCondition(DbSessionData condition) throws ParseException {

		StringBuilder nativeSql = new StringBuilder("select am.system_name, am.system_subdomain,am.name,am.Business_Info, am.create_time,count(1) as num from Db_Session_Count am where 1=1 "); 
		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (StringUtils.isNotBlank(condition.getCreateTime())) {
			nativeSql.append(" and am.create_time = :createTime ");
			params.add(new ParameterCondition("createTime", condition.getCreateTime()));
		}
		
		nativeSql.append(" GROUP BY am.system_name,am.system_subdomain,am.create_time ");

		 List<DbSessionData>list = dbSessionCountDao.searchByNativeSQL(nativeSql.toString(), params, DbSessionData.class);
		 
		 return list;
		
	}
}

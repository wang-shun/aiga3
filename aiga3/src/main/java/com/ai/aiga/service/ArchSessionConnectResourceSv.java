package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.dao.ArchSessionConnectResourceDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSessionConnectResourceParams;
import com.ai.aiga.view.controller.archiQuesManage.dto.ArchSessionConnectResourceShow;
@Service
@Transactional
public class ArchSessionConnectResourceSv extends BaseService {
	
	@Autowired
	private ArchSessionConnectResourceDao archSessionConnectResourceDao;
	
	public List<ArchSessionConnectResourceShow>listSessionConnectResource(ArchSessionConnectResourceParams condition){
		
		StringBuilder nativeSql = new StringBuilder(
				" SELECT a.from_sys_name, avg(a.total) as total, a.db_name, substr(a.sett_month, 0, 8) as sett_month" +
				" FROM aiam.arch_session_connect_resource a where 1=1 " );
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();
		if (StringUtils.isNotBlank(condition.getEndMonth())) {
			nativeSql.append("and substr(a.sett_month,0,8) = :endMonth ");
			params.add(new ParameterCondition("endMonth", condition.getEndMonth()));
		}
		nativeSql.append(" group by a.from_sys_name, a.db_name, substr(a.sett_month, 0, 8) ");
		nativeSql.append(" order by a.db_name ");
		return archSessionConnectResourceDao.searchByNativeSQL(nativeSql.toString(), params, ArchSessionConnectResourceShow.class);
	}
    
}

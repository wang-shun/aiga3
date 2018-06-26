package com.ai.aiga.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaAuthorDao;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.ArchTaskMonitoring;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchTaskMonitoringSv extends BaseService {

	@Autowired
	private AigaAuthorDao archTaskMonitoringDao;

	public Page<ArchTaskMonitoring> queryByCondition(ArchTaskMonitoring condition, int pageNumber,
			int pageSize) throws ParseException {
		StringBuilder nativeSql = new StringBuilder("select a.index_name,c.task_name,d.results,d.state from aiam.am_core_index a,aiam.cfg_task_param_value b,aiam.cfg_task c," +
		"aiam.task_log d where translate(b.param_value ,'x1234567890','x') is null and a.group_id = b.param_value and b.cfg_task_id = c.cfg_task_id and c.cfg_task_id = d.cfg_task_id and "); 		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();		
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String dateQuery = sdf.format(condition.getFinishDate());
		if (condition.getFinishDate() != null) {
			nativeSql.append(" to_char(d.finish_date, 'YYYY-MM-DD') = :finishDate ");
			params.add(new ParameterCondition("finishDate", dateQuery));
		}		
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return archTaskMonitoringDao.searchByNativeSQL(nativeSql.toString(), params, ArchTaskMonitoring.class, pageable);
		
	}
}

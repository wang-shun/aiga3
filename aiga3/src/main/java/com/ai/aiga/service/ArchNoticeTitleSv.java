package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.domain.ArchNoticeTitle;
import com.ai.aiga.domain.IndexConnect;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ArchNoticeTitleDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ArchNoticeTitleSv extends BaseService {

	@Autowired
	private ArchNoticeTitleDao archNoticeTitleDao;
	
	//find
	public List<ArchNoticeTitle> findAll(){
		return archNoticeTitleDao.findAll();
	}
	

	public List<ArchNoticeTitle> findAllByPage(ArchNoticeTitle request) {
        List<Condition> cons = new ArrayList<Condition>();
        return archNoticeTitleDao.search(cons);
	}

	public List<ArchNoticeTitle> queryByCondition(ArchNoticeTitle condition) throws ParseException {
		StringBuilder nativeSql = new StringBuilder("select * from arch_notice_title am where 1=1  "); 
		
		List<ParameterCondition>params = new ArrayList<ParameterCondition>();

		if (condition.getId() != 0) {
			nativeSql.append(" am.id = :id ");
			params.add(new ParameterCondition("id", condition.getId()));
		}
		if (StringUtils.isNotBlank(condition.getNoticeTitle())) {
			nativeSql.append(" and am.notice_title = :noticeTitle ");
			params.add(new ParameterCondition("noticeTitle", condition.getNoticeTitle()));
		}
		if (StringUtils.isNotBlank(condition.getNoticeDescription())) {
			nativeSql.append(" and am.notice_description = :noticeDescription ");
			params.add(new ParameterCondition("noticeDescription", condition.getNoticeDescription()));
		}
		if (StringUtils.isNotBlank(condition.getNoticeDetails())) {
			nativeSql.append(" and am.notice_details = :noticeDetails ");
			params.add(new ParameterCondition("noticeDetails", condition.getNoticeDetails()));
		}


		return archNoticeTitleDao.searchByNativeSQL(nativeSql.toString(), params, ArchNoticeTitle.class);
		
	}
}

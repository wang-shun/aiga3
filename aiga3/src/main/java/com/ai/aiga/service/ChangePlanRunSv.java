package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaStaffDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.enums.CheckAcceptEnum;
import com.ai.aiga.view.json.NaOnlineTaskDistributeResponse;
import com.ai.aiga.view.util.SessionMgrUtil;
import com.huawei.msp.mmap.server.TaskMessageClient;

@Service
@Transactional
public class ChangePlanRunSv extends BaseService{

	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;
	
	@Autowired
	private AigaStaffDao aigaStaffDao;


	public List<AigaStaff> createOpId() {
		List<AigaStaff> list = aigaStaffDao.findAll();
		return list;
	}

	/**
	 * @ClassName: ChangePlanRunSv :: changePlan
	 * @author: dongch
	 * @date: 2017年4月21日 下午1:40:31
	 *变更计划下拉框
	 * @Description:
	 * @return          
	 */
	public List<NaChangePlanOnile> changePlan() {
		List<NaChangePlanOnile> list = naChangePlanOnileDao.findBySign();
		return list;
	}
}

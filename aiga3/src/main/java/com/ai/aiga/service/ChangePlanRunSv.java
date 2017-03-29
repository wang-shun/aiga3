package com.ai.aiga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaOnlineTaskDistributeDao;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class ChangePlanRunSv extends BaseService{

	@Autowired
	private NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;
}

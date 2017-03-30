package com.ai.aiga.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaRequireListDao;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class NaRequireListSv extends BaseService{
	@Autowired
	private  NaRequireListDao naRequireListDao;
	public NaRequireList selectname(String requireName){
		if(requireName == null ||requireName.equals("") ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		return naRequireListDao.select(requireName);
		
	}
	public List<NaRequireList> selectList(){
		
		return naRequireListDao.selectList();
		
	}
	public NaRequireList save(NaRequireList request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		NaRequireList naRequireList=new NaRequireList();
		naRequireList.setRequireCode(request.getRequireCode());
		naRequireList.setRequireName(request.getRequireName());
		naRequireList.setRequireMan(request.getRequireMan());
		naRequireList.setDevManager(request.getDevManager());
		naRequireList.setTestManager(request.getTestManager());
		naRequireList.setReviewState(request.getReviewState());
		
		if(request.getIntroducedState()==null||request.getIntroducedState().equals("")){
			//成功
			naRequireList.setIntroducedState(BigDecimal.valueOf((NumberUtils.toLong("1"))));
		}
		naRequireListDao.save(naRequireList);
		return naRequireList;
		
	}
}

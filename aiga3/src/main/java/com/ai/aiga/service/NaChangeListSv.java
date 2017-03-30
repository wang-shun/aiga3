package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaChangeListDao;
import com.ai.aiga.domain.NaChangeList;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

@Service
@Transactional
public class NaChangeListSv extends BaseService{
	@Autowired
	private NaChangeListDao  naChangeListDao;
	public NaChangeList selectname(String changeName){
		if(changeName == null ||changeName.equals("") ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		return naChangeListDao.select(changeName);
		
	}
	public List<NaChangeList> selectList(){
		
		return naChangeListDao.selectList();
		
	}
	public NaChangeList save(NaChangeList request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		NaChangeList nachangeList=new NaChangeList();
		nachangeList.setChangeMan(request.getChangeMan());
		nachangeList.setChangeManager(request.getChangeManager());
		nachangeList.setChangeName(request.getChangeName());
		nachangeList.setChangeTitle(request.getChangeTitle());
		nachangeList.setReviewState(request.getReviewState());
		
		if(request.getResultState()==null||request.getResultState().equals("")){
			//成功
			nachangeList.setResultState("1");
		}
		naChangeListDao.save(nachangeList);
		return nachangeList;
		
	}
	

}

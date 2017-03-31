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
	public Object selectList(int pageNumber, int pageSize,NaChangeList condition ) throws ParseException {
		   List<String> list = new ArrayList<String>();
			list.add("changeId");
			list.add("changeName");
			list.add("changeManager");
			list.add("changeMan");
			list.add("changeTitle");
			list.add("reviewState");
			list.add("resultState");
			
		   String sql = "select a.CHANGE_ID, a.change_name,a.change_manager,a.change_man,a.change_title,"
			+ "a.review_state,a.result_state"
			+ " from NA_CHANGE_LIST a,NA_CHANGE_PLAN_ONILE b "
			+ "where a.PLAN_ID=b.ONLINE_PLAN";
		
				if(StringUtils.isNotBlank(condition.getChangeName())){
					sql += " and a.change_name like '%"+condition.getChangeName()+"%'";
				}
				
				
				
			
			if(pageNumber < 0){
				pageNumber = 0;
			}
			
			if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}

			Pageable pageable = new PageRequest(pageNumber, pageSize);
			
			return naChangeListDao.searchByNativeSQL(sql, pageable, list);
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
		}else{
			nachangeList.setResultState(request.getResultState());
		}
		naChangeListDao.save(nachangeList);
		return nachangeList;
		
	}
	

}

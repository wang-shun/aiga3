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
import com.ai.aiga.domain.NaChangePlanOnile;
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
		public Object selectList(int pageNumber, int pageSize,String name, String onlinePlan) throws ParseException {
			   List<String> list = new ArrayList<String>();
				list.add("id");
				list.add("requireCode");
				list.add("requireName");
				list.add("requireMan");
				list.add("devManager");
				list.add("testManager");
				list.add("reviewState");
				list.add("introducedState");
			   String sql = "select a.id,a.REQUIRE_CODE,a.REQUIRE_NAME,a.REQUIRE_MAN,a.DEV_MANAGER,a.TEST_MANAGER,a.REVIEW_STATE,"
						+ "a.INTRODUCED_STATE from NA_REQUIRE_LIST a,NA_CHANGE_PLAN_ONILE b "
						+ "where a.PLAN_ID=b.ONLINE_PLAN";
			    
					if(StringUtils.isNotBlank(name)){
						sql += " and a.REQUIRE_NAME like '%"+name+"%'";
					}
					if(StringUtils.isNotBlank(onlinePlan)){
						sql += " and b.ONLINE_PLAN ="+onlinePlan;
					}
					
					
				
				if(pageNumber < 0){
					pageNumber = 0;
				}
				
				if(pageSize <= 0){
					pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
				}

				Pageable pageable = new PageRequest(pageNumber, pageSize);
				
				return naRequireListDao.searchByNativeSQL(sql, pageable, list);
			}

		
	public void save(List<NaRequireList> request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		for(int i = 0; i < request.size(); i++){
			
			NaRequireList naRequireList=request.get(i);
			
			if(naRequireList!=null){
			NaRequireList naRequireList1=naRequireListDao.findOne(naRequireList.getId());
			naRequireList1.setRequireCode(naRequireList.getRequireCode());
			naRequireList1.setRequireName(naRequireList.getRequireName());
			naRequireList1.setRequireMan(naRequireList.getRequireMan());
			naRequireList1.setDevManager(naRequireList.getDevManager());
			naRequireList1.setTestManager(naRequireList.getTestManager());
			naRequireList1.setReviewState(naRequireList.getReviewState());
			
			if(naRequireList.getIntroducedState()==null||naRequireList.getIntroducedState().equals("")){
				//成功
				naRequireList1.setIntroducedState(BigDecimal.valueOf((NumberUtils.toLong("1"))));
			}else{
			naRequireList1.setIntroducedState(naRequireList.getIntroducedState());
			}
			naRequireListDao.save(naRequireList1);
		
		}
		}
		
}
}
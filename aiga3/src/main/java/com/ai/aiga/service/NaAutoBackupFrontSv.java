package com.ai.aiga.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.constant.BusiConstant.DEAL_STATE;
import com.ai.aiga.dao.NaAutoBackupDealDao;
import com.ai.aiga.dao.NaAutoDbAcctDao;
import com.ai.aiga.dao.NaAutoPropertyConfigDao;
import com.ai.aiga.dao.NaAutoPropertyCorrelationDao;
import com.ai.aiga.domain.NaAutoBackupDeal;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;

@Service
@Transactional
public class NaAutoBackupFrontSv {

	@Autowired
	private NaAutoBackupDealDao autoBackupDealDao;

	@Autowired
	private NaAutoPropertyConfigDao autoPropertyConfigDao;

	@Autowired
	private NaAutoPropertyCorrelationDao autoPropertyCorrelationDao;
	
	@Autowired
	private NaAutoDbAcctDao autoDbAcctDao;
	
	public List<NaAutoPropertyConfig> getDistinctPropertyConfigList(){
		return autoPropertyConfigDao.distinctPropertyConfigList();
	}
	
	public Object getBackupDealList(int pageNumber, int pageSize, String propertyResource,String resourceValue) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("dealId");
		list.add("propertyResource");
		list.add("field1");
		list.add("field2");
		list.add("field3");
		list.add("field4");
		list.add("field5");
		list.add("field6");
		list.add("field7");
		list.add("field8");
		list.add("field9");
		list.add("field10");
		list.add("regionId");
		list.add("createDate");
		list.add("dealDate");
		list.add("opId");
		list.add("orgId");
		list.add("coverEnv");
		list.add("errMsg");
		list.add("state");
		list.add("restoreState");
		String sql = "select * from na_auto_backup_deal where 1=1 ";
		if (StringUtils.isNotEmpty(propertyResource)) {
			sql += " and PROPERTY_RESOURCE ='" + propertyResource+"'";
		}
		if (StringUtils.isNotEmpty(resourceValue)) {
			sql += " and FIELD1 ='" + resourceValue+"'";
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return autoBackupDealDao.searchByNativeSQL(sql, pageable, list);
	}
	
	public void saveBackupDeal(String propertyResource, String resourceValue){
		//autoBackupDealDao.saveBackupDeal(propertyResource, resourceValue);
		NaAutoBackupDeal backup = new NaAutoBackupDeal();
		backup.setPropertyResource(propertyResource);
		backup.setField1(resourceValue);
		backup.setDealDate(new Date());
		backup.setState(DEAL_STATE.INIT.value);
		autoBackupDealDao.save(backup);
	}
	
	public void deleteBackupDeal(Long dealId) {
		if(dealId == null || dealId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "dealId");
		}
		autoBackupDealDao.delete(dealId);
	}
	
}

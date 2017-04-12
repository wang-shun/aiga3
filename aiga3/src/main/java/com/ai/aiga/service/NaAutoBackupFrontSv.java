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
import com.ai.aiga.dao.jpa.Condition;
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
	
	public Object getPropertyConfigList(int pageNumber, int pageSize, String propertyID,
			String dependencyTable, String dependencyField)
			throws ParseException {
		List<Condition> cons = new ArrayList<Condition>();
		if (StringUtils.isNotEmpty(propertyID)) {
			cons.add(new Condition("propertyID", propertyID, Condition.Type.EQ));
		}
		if (StringUtils.isNotEmpty(dependencyTable)) {
			cons.add(new Condition("dependencyTable", dependencyTable, Condition.Type.EQ));
		}
		if (StringUtils.isNotEmpty(dependencyField)) {
			cons.add(new Condition("dependencyField", dependencyField, Condition.Type.EQ));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return autoPropertyConfigDao.search(cons, pageable);
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
	
	public void deletePropertyConfig(Long propertyId) {
		if(propertyId == null || propertyId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "propertyId");
		}
		autoPropertyConfigDao.delete(propertyId);
	}
	
	public void addPropertyConfig(NaAutoPropertyConfig config){
		if(StringUtils.isEmpty(config.getPropertyId())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "propertyId");
		}
		if(StringUtils.isEmpty(config.getPropertyName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "propertyName");
		}
		if(StringUtils.isEmpty(config.getPropertyField())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "propertyField");
		}
		if(StringUtils.isEmpty(config.getDependencyField())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "dependencyField");
		}
		if(StringUtils.isEmpty(config.getDependencyTable())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "dependencyTable");
		}
		if(StringUtils.isEmpty(config.getDb())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "db");
		}
		int maxSortId = autoPropertyConfigDao.getMaxSortId(config.getPropertyId());
		if(maxSortId >= 10){
			BusinessException.throwBusinessException("该propertyId配置记录数超过了最大允许配置数(10条)");
		}
		config.setSortId((byte) (maxSortId + 1));
		config.setCreateDate(new Date());
		autoPropertyConfigDao.save(config);
	}
	
	public void updatePropertyConfig(NaAutoPropertyConfig config){
		if(config == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		if(config.getCfgId() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "cfgId");
		}
		NaAutoPropertyConfig up = autoPropertyConfigDao.findOne(config.getCfgId());
		if(up == null){
			BusinessException.throwBusinessException("根据cfgId:"+config.getCfgId()+"查询不到记录");
		}
		if(StringUtils.isNotEmpty(config.getPropertyName())){
			up.setPropertyName(config.getPropertyName());
		}
		if(StringUtils.isNotEmpty(config.getPropertyField())){
			up.setPropertyField(config.getPropertyField());
		}
		if(StringUtils.isNotEmpty(config.getDependencyField())){
			up.setDependencyField(config.getDependencyField());
		}
		if(StringUtils.isNotEmpty(config.getDependencyTable())){
			up.setDependencyTable(config.getDependencyTable());
		}
		if(StringUtils.isNotEmpty(config.getDb())){
			up.setDb(config.getDb());
		}
		config.setDoneDate(new Date());
		autoPropertyConfigDao.save(up);
	}
	
}

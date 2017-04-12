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
import com.ai.aiga.domain.NaAutoDbAcct;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.domain.NaAutoPropertyCorrelation;
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
	
	public List<String> getDbList() {
		List<NaAutoDbAcct> dbs = autoDbAcctDao.findByState('U');
		List<String> acctList = new ArrayList<String>();
		for (NaAutoDbAcct db : dbs) {
			acctList.add(db.getDbAcctCode());
		}
		return acctList;
	}

	public List<NaAutoPropertyConfig> getDistinctPropertyConfigList(){
		return autoPropertyConfigDao.distinctPropertyConfigList();
	}
	
	public Object getPropertyConfigList(int pageNumber, int pageSize, String propertyId,
			String dependencyTable, String dependencyField)
			throws ParseException {
		List<Condition> cons = new ArrayList<Condition>();
		if (StringUtils.isNotEmpty(propertyId)) {
			cons.add(new Condition("propertyId", propertyId, Condition.Type.EQ));
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
		List<Condition> cons = new ArrayList<Condition>();
		if (StringUtils.isNotEmpty(propertyResource)) {
			cons.add(new Condition("propertyResource", propertyResource, Condition.Type.EQ));
		}
		if (StringUtils.isNotEmpty(resourceValue)) {
			cons.add(new Condition("field1", resourceValue, Condition.Type.EQ));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return autoBackupDealDao.search(cons, pageable);
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
	
	public void deletePropertyConfig(Long cfgId) {
		if(cfgId == null || cfgId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "cfgId");
		}
		autoPropertyConfigDao.delete(cfgId);
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
	
	public Object getPropertyCorrelationList(int pageNumber, int pageSize, String propertyId,
			String correlationTable, String correlationField)
			throws ParseException {
		List<Condition> cons = new ArrayList<Condition>();
		if (StringUtils.isNotEmpty(propertyId)) {
			cons.add(new Condition("propertyId", propertyId, Condition.Type.EQ));
		}
		if (StringUtils.isNotEmpty(correlationTable)) {
			cons.add(new Condition("correlationTable", correlationTable, Condition.Type.EQ));
		}
		if (StringUtils.isNotEmpty(correlationField)) {
			cons.add(new Condition("correlationField", correlationField, Condition.Type.EQ));
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return autoPropertyCorrelationDao.search(cons, pageable);
	}
	
	public void addPropertyCorrelation(NaAutoPropertyCorrelation correlation){
		if(correlation.getPropertyCfgId() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "propertyCfgId");
		}
		if(StringUtils.isEmpty(correlation.getPropertyId())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "propertyId");
		}
		if(StringUtils.isEmpty(correlation.getCorrelationField())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "correlationField");
		}
		if(StringUtils.isEmpty(correlation.getCorrelationTable())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "correlationTable");
		}
		if(StringUtils.isEmpty(correlation.getDb())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "db");
		}
		correlation.setCreateDate(new Date());
		autoPropertyCorrelationDao.save(correlation);
	}
	
	public void deletePropertyCorrelation(Long correlationId) {
		if(correlationId == null || correlationId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "correlationId");
		}
		autoPropertyCorrelationDao.delete(correlationId);
	}
	
	public void updatePropertyCorrelation(NaAutoPropertyCorrelation correlation){
		if(correlation == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		if(correlation.getCorrelationId() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "correlationId");
		}
		NaAutoPropertyCorrelation up = autoPropertyCorrelationDao.findOne(correlation.getCorrelationId());
		if(up == null){
			BusinessException.throwBusinessException("根据correlationId:"+correlation.getCorrelationId()+"查询不到记录");
		}
		if(correlation.getPropertyCfgId() > 0){
			up.setPropertyCfgId(correlation.getPropertyCfgId());
		}
		if(StringUtils.isNotEmpty(correlation.getCorrelationField())){
			up.setCorrelationField(correlation.getCorrelationField());
		}
		if(StringUtils.isNotEmpty(correlation.getCorrelationTable())){
			up.setCorrelationTable(correlation.getCorrelationTable());
		}
		if(StringUtils.isNotEmpty(correlation.getDb())){
			up.setDb(correlation.getDb());
		}
		correlation.setDoneDate(new Date());
		autoPropertyCorrelationDao.save(up);
	}
	
}

package com.ai.am.service.auto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.constant.BusiConstant.DEAL_STATE;
import com.ai.am.dao.NaAutoBackupDealDao;
import com.ai.am.dao.NaAutoDbAcctDao;
import com.ai.am.dao.NaAutoPropertyConfigDao;
import com.ai.am.dao.NaAutoPropertyCorrelationDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.domain.NaAutoBackupDeal;
import com.ai.am.domain.NaAutoDbAcct;
import com.ai.am.domain.NaAutoPropertyConfig;
import com.ai.am.domain.NaAutoPropertyCorrelation;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.process.jta.task.impl.AutoBackupTask;

@Service
@Transactional
public class AutoBackupFrontSv {

	@Autowired
	private NaAutoBackupDealDao autoBackupDealDao;

	@Autowired
	private NaAutoPropertyConfigDao autoPropertyConfigDao;

	@Autowired
	private NaAutoPropertyCorrelationDao autoPropertyCorrelationDao;
	
	@Autowired
	private NaAutoDbAcctDao autoDbAcctDao;
	
	public List<Map> getDbList() {
		List<NaAutoDbAcct> dbs = autoDbAcctDao.findByState('U');
		List<Map> acctList = new ArrayList<Map>();
		for (NaAutoDbAcct db : dbs) {
			Map acct = new HashMap();
			acct.put("db",db.getDbAcctCode());
			acctList.add(acct);
		}
		return acctList;
	}
	
	public List<Map> getPropertyCfgIdList() {
		List<NaAutoPropertyCorrelation> cigIds = autoPropertyCorrelationDao.findAll();
		List<Map> CfgIdList = new ArrayList<Map>();
		for (NaAutoPropertyCorrelation cigId : cigIds) {
			Map acct = new HashMap();
			acct.put("cigId",cigId.getPropertyCfgId());
			CfgIdList.add(acct);
		}
		return CfgIdList;
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
	
	public void restore(Long dealId) {
		if(dealId == null || dealId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "dealId");
		}
		NaAutoBackupDeal backupDeal = autoBackupDealDao.findOne(dealId);
		if(backupDeal.getState() != DEAL_STATE.SUCCESS.value){
			BusinessException.throwBusinessException("只能还原成功备份的数据");
		}
		backupDeal.setRestoreState(DEAL_STATE.INIT.value);
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
		/*if(correlation.getPropertyCfgId() > 0){
			up.setPropertyCfgId(correlation.getPropertyCfgId());
		}*/
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
	//获得数据配置字段下拉框
	public List<Map> getPropertyFleldName(String propertyId){
		List<NaAutoPropertyConfig> cigIds = autoPropertyConfigDao.findByPropertyId(propertyId);
		List<Map> CfgIdList = new ArrayList<Map>();
		for (NaAutoPropertyConfig cigId : cigIds) {
			Map acct = new HashMap();
			acct.put("propertyField",cigId.getPropertyField());
			acct.put("cigId", cigId.getCfgId());
			CfgIdList.add(acct);
		}
		return CfgIdList;
	}
	/*//启动主进程
	public void startBackupMain(){
		String[] a =new String[]{"1"};
		AutoBackupTask.main(a);
	}*/
	
}

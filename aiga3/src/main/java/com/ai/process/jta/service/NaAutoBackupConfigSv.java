package com.ai.process.jta.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.aiga.dao.NaAutoBackupDealDao;
import com.ai.aiga.dao.NaAutoPropertyConfigDao;
import com.ai.aiga.dao.NaAutoPropertyCorrelationDao;
import com.ai.aiga.domain.NaAutoBackupDeal;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.domain.NaAutoPropertyCorrelation;
import com.ai.aiga.service.base.BaseService;
import static com.ai.aiga.constant.BusiConstant.DEAL_STATE.*;

@Service
public class NaAutoBackupConfigSv extends BaseService {
	
	@Autowired
	private NaAutoBackupDealDao autoBackupDealDao;

	@Autowired
	private NaAutoPropertyConfigDao autoPropertyConfigDao;

	@Autowired
	private NaAutoPropertyCorrelationDao autoPropertyCorrelationDao;

	public NaAutoBackupDeal getOneInitBackupDeal(){
		return autoBackupDealDao.findFirstByStateOrderByDealId(INIT.value);
	}
	
	public NaAutoBackupDeal getOneInitRestoreDeal(){
		return autoBackupDealDao.findFirstByRestoreStateOrderByDealId(INIT.value);
	}
	
	public Map<Long,NaAutoPropertyConfig> getPropertyConfigMap(String propertyId){
		List<NaAutoPropertyConfig> propertyConfigList = autoPropertyConfigDao.findByPropertyId(propertyId);
		Map<Long,NaAutoPropertyConfig> propertyConfigMap = new HashMap<Long,NaAutoPropertyConfig>();
		for(NaAutoPropertyConfig config : propertyConfigList){
			propertyConfigMap.put(config.getCfgId(), config);
		}
		return propertyConfigMap;
	}
	
	public List<NaAutoPropertyCorrelation> getPropertyCorrelationList(String propertyId){
		return autoPropertyCorrelationDao.findByPropertyId(propertyId);
	}
	
	public void updateBackupDealState(byte state, long dealId) {
		autoBackupDealDao.updateState(state, dealId);
	}
	
}

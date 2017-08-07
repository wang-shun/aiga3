package com.ai.process.jta.service;

import static com.ai.am.constant.BusiConstant.DEAL_STATE.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.am.dao.NaAutoBackupDealDao;
import com.ai.am.dao.NaAutoPropertyConfigDao;
import com.ai.am.dao.NaAutoPropertyCorrelationDao;
import com.ai.am.domain.NaAutoBackupDeal;
import com.ai.am.domain.NaAutoPropertyConfig;
import com.ai.am.domain.NaAutoPropertyCorrelation;
import com.ai.am.service.base.BaseService;

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

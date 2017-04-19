package com.ai.process.jta.task.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ai.aiga.constant.BusiConstant.DEAL_STATE;
import com.ai.aiga.domain.NaAutoBackupDeal;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.domain.NaAutoPropertyCorrelation;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.util.ExceptionUtil;
import com.ai.process.jta.common.JtaSpringContext;
import com.ai.process.jta.service.NaAutoBackupConfigSv;
import com.ai.process.jta.service.NaAutoBackupSv;

@Component("autoRestoreTask")
public class AutoRestoreTask extends AbstractTask {

	private static final Logger log = LoggerFactory.getLogger(AutoRestoreTask.class);

	@Autowired
	private NaAutoBackupSv autoBackupSv;

	@Autowired
	private NaAutoBackupConfigSv autoBackupConfigSv;

	@Override
	public void doBusiness() throws Exception {
		long dealId = 0;
		try {
			NaAutoBackupDeal restoreDeal = autoBackupConfigSv.getOneInitRestoreDeal();
			if (restoreDeal == null) {
				Thread.sleep(1000);
				return;
			}
			int upNum = autoBackupSv.updateRestoreDeal((byte) 0, DEAL_STATE.TEMP.value, restoreDeal.getDealId());
			if (upNum == 0) {
				return;
			}
			dealId = restoreDeal.getDealId();
			String propertyId = restoreDeal.getPropertyResource();
			Map<Long, NaAutoPropertyConfig> propertyConfigMap = autoBackupConfigSv
					.getPropertyConfigMap(propertyId);
			if (propertyConfigMap == null || propertyConfigMap.size() == 0) {
				BusinessException.throwBusinessException("配置错误,根据property_id:"
						+ restoreDeal.getPropertyResource()
						+ "查询不到na_auto_property_config表数据，");
			}
			List<NaAutoPropertyCorrelation> propertyCorrelationList = autoBackupConfigSv.getPropertyCorrelationList(propertyId);
			if (propertyCorrelationList == null || propertyCorrelationList.size() == 0) {
				BusinessException.throwBusinessException("配置错误,根据property_id:"
						+ restoreDeal.getPropertyResource()
						+ "查询不到na_auto_property_correlation表数据，");
			}
			for (NaAutoPropertyCorrelation correlation : propertyCorrelationList) {
				NaAutoPropertyConfig propertyConfig = propertyConfigMap.get(correlation.getPropertyCfgId());
				if (propertyConfig == null) {
					BusinessException.throwBusinessException("配置错误,根据cfg_Id:"
							+ correlation.getPropertyCfgId()
							+ "查询不到na_auto_property_config表数据，");
				}
			}
			autoBackupSv.restore(restoreDeal, propertyCorrelationList,propertyConfigMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			String errMsg = ExceptionUtil.stackTraceText(e);
			autoBackupSv.updateRestoreDealMsg(errMsg, dealId);
		}
	}

}

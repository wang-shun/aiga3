package com.ai.process.jta.service;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant.DEAL_STATE;
import com.ai.aiga.domain.NaAutoBackupDeal;
import com.ai.aiga.domain.NaAutoPropertyConfig;
import com.ai.aiga.domain.NaAutoPropertyCorrelation;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.util.ExceptionUtil;
import com.ai.process.jta.DatabaseContextHolder;
import com.ai.process.jta.tabledata.ColumnModel;
import com.ai.process.jta.tabledata.ColumnTypeEnum;
import com.ai.process.jta.tabledata.TableUtils;

@Service
public class NaAutoBackupSv extends AbstractJatService {
	
	private final String BACKUP_DB_KEY = DatabaseContextHolder.DEFAULT_DB_TYPE;

	public void prepareBackupTables(List<NaAutoPropertyCorrelation> propertyCorrelationList) {
		for (NaAutoPropertyCorrelation correlation : propertyCorrelationList) {
			String dbKey = correlation.getDb();
			Connection srcConn = null;
			Connection destConn = null;
			try {
				srcConn = getConnection(dbKey);
				destConn = getConnection(BACKUP_DB_KEY);
				String srcTableName = correlation.getCorrelationTable().toUpperCase();
				String destTableName = (srcTableName + "$" + correlation.getDb()).toUpperCase();
				try {
					String testSql = "SELECT 1 FROM " + destTableName + " WHERE ROWID=null";
					destConn.createStatement().execute(testSql);
				} catch (SQLException e) {
					if(e.getMessage().startsWith("ORA-00942")){
						Map<String, ColumnModel> tableStructure = TableUtils.getTableStructure(srcTableName, srcConn);
						String createSql = TableUtils.getCreateTableSql(destTableName, tableStructure);
						destConn.createStatement().execute(createSql);
					}else{
						throw e;
					}
				}
			} catch(Exception e){
				log.error(e.getMessage(), e);
				ExceptionUtil.uncheckedAndWrap(e);
			}finally {
				try {
					closeConnection(srcConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
				try {
					closeConnection(destConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
	
	@Transactional
	public void prepareBackupDealData(NaAutoBackupDeal backupDeal, Map<Long, NaAutoPropertyConfig> propertyConfigMap) {
		for(NaAutoPropertyConfig propertyConfig : propertyConfigMap.values()){
			int sortId = propertyConfig.getSortId();
			if (sortId < 1 || sortId > 10) {
				BusinessException.throwBusinessException("na_auto_property_config表中的sort_id值不在1~10范围内");
			}
			if(sortId == 1){
				continue;
			}
			String dbKey = propertyConfig.getDb();
			Connection srcDataConn = null;
			Connection backupConn = null;
			try{
				srcDataConn =  getConnection(dbKey);
				backupConn = getConnection(BACKUP_DB_KEY);
				String dependencyTable = propertyConfig.getDependencyTable().toUpperCase();
				String dependencyField = propertyConfig.getDependencyField().toUpperCase();
				String querySql = "SELECT T_." + propertyConfig.getPropertyField() + " FROM (" + dependencyTable
						+ ") T_ WHERE T_." + dependencyField + "=?";
				PreparedStatement psQuery = srcDataConn.prepareStatement(querySql);
				String fieldTypeName = ColumnTypeEnum.VARCHAR2.getDbType();
				if(dependencyTable.trim().matches("[a-zA-z0-9_]+")){
					Map<String, ColumnModel> tableStructure = TableUtils.getTableStructure(dependencyTable, srcDataConn);
					fieldTypeName = tableStructure.get(dependencyField).getTypeName();
				}
				if (fieldTypeName.equals(ColumnTypeEnum.NUMBER.getDbType())) {
					psQuery.setLong(1, Long.parseLong(backupDeal.getField1()));
				} else {
					psQuery.setString(1, backupDeal.getField1());
				}
				ResultSet rs = psQuery.executeQuery();
				String fieldValue = null;
				if (rs.next()) {
					fieldValue = rs.getString(1);
				}
				rs.close();
				if(fieldValue != null){
					String updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET FIELD"+sortId+"=? WHERE DEAL_ID=?";
					PreparedStatement psUpdate = backupConn.prepareStatement(updateSql);
					psUpdate.setString(1, fieldValue);
					psUpdate.setLong(2, backupDeal.getDealId());
					psUpdate.executeUpdate();
				}
			}catch(Exception e){
				log.error(e.getMessage(), e);
				ExceptionUtil.uncheckedAndWrap(e);
			}finally {
				try {
					closeConnection(srcDataConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
				try {
					closeConnection(backupConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
	
	@Transactional
	public void backup(NaAutoBackupDeal backupDeal, 
			List<NaAutoPropertyCorrelation> propertyCorrelationList, 
			Map<Long, NaAutoPropertyConfig> propertyConfigMap) {
		for (NaAutoPropertyCorrelation correlation : propertyCorrelationList) {
			NaAutoPropertyConfig propertyConfig = propertyConfigMap.get(correlation.getPropertyCfgId());
			int sortId = propertyConfig.getSortId();
			if (sortId < 1 || sortId > 10) {
				BusinessException.throwBusinessException("na_auto_property_config表中的sort_id值不在1~10范围内");
			}
			String conditionValue = null;
			try {
				Method getField = NaAutoBackupDeal.class.getMethod("getField" + sortId);
				conditionValue = (String)getField.invoke(backupDeal);
			} catch (Exception e) {
				ExceptionUtil.uncheckedAndWrap(e);
			}
			String srcTableName = correlation.getCorrelationTable().toUpperCase();
			String conditionField = correlation.getCorrelationField().toUpperCase();
			String destTableName = (srcTableName + "$" + correlation.getDb()).toUpperCase();
			String dbKey = correlation.getDb();
			Connection srcConn = null;
			Connection destConn = null;
			try{
				srcConn =  getConnection(dbKey);
				destConn = getConnection(BACKUP_DB_KEY);
				Map<String, ColumnModel> tableStructure = TableUtils.getTableStructure(srcTableName, srcConn);
				String querySql = TableUtils.getSelectSqlPattern(srcTableName, tableStructure.keySet(), conditionField);
				String insertSql = TableUtils.getInsertSqlPattern(destTableName, tableStructure.keySet());
				if (!tableStructure.containsKey(conditionField)) {
					BusinessException.throwBusinessException("CORRELATION_FIELD="
									+ conditionField + ",CORRELATION_TABLE="
									+ srcTableName + "配置错误，找不到指定的字段");
				}
				String fieldTypeName = tableStructure.get(conditionField).getTypeName();
				PreparedStatement psQuery = srcConn.prepareStatement(querySql);
				if (fieldTypeName.equals(ColumnTypeEnum.NUMBER.getDbType())) {
					psQuery.setLong(1, Long.parseLong(conditionValue));
				} else {
					psQuery.setString(1, conditionValue);
				}
				ResultSet rs = psQuery.executeQuery();
				PreparedStatement psInsert = destConn.prepareStatement(insertSql);
				while (rs.next()) {
					for (int i = 1; i <= tableStructure.size(); i++) {
						psInsert.setObject(i, rs.getObject(i));
					}
					psInsert.executeUpdate();
				}
				psInsert.close();
				rs.close();
				updateBackupDeal((byte)-1, DEAL_STATE.SUCCESS.value, backupDeal.getDealId());
			}catch(Exception e){
				log.error(e.getMessage(), e);
				ExceptionUtil.uncheckedAndWrap(e);
			}finally {
				try {
					closeConnection(srcConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
				try {
					closeConnection(destConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
	
	@Transactional
	public void restore(NaAutoBackupDeal backupDeal, 
			List<NaAutoPropertyCorrelation> propertyCorrelationList, 
			Map<Long, NaAutoPropertyConfig> propertyConfigMap) {
		for (NaAutoPropertyCorrelation correlation : propertyCorrelationList) {
			NaAutoPropertyConfig propertyConfig = propertyConfigMap.get(correlation.getPropertyCfgId());
			int sortId = propertyConfig.getSortId();
			if (sortId < 1 || sortId > 10) {
				BusinessException.throwBusinessException("na_auto_property_config表中的sort_id值不在1~10范围内");
			}
			String conditionValue = null;
			try {
				Method getField = NaAutoBackupDeal.class.getMethod("getField" + sortId);
				conditionValue = (String)getField.invoke(backupDeal);
			} catch (Exception e) {
				ExceptionUtil.uncheckedAndWrap(e);
			}
			String restoreDestTableName = correlation.getCorrelationTable().toUpperCase();
			String conditionField = correlation.getCorrelationField().toUpperCase();
			String restoreSrcTableName = (restoreDestTableName + "$" + correlation.getDb()).toUpperCase();
			String dbKey = correlation.getDb();
			Connection restoreDestConn = null;
			Connection restoreSrcConn = null;
			try{
				restoreDestConn =  getConnection(dbKey);
				restoreSrcConn = getConnection(BACKUP_DB_KEY);
				Map<String, ColumnModel> tableStructure = TableUtils.getTableStructure(restoreDestTableName, restoreDestConn);
				String deleteSql = TableUtils.getDeleteSqlPattern(restoreDestTableName, conditionField);
				String querySql = TableUtils.getSelectSqlPattern(restoreSrcTableName, tableStructure.keySet(), conditionField);
				String insertSql = TableUtils.getInsertSqlPattern(restoreDestTableName, tableStructure.keySet());
				if (!tableStructure.containsKey(conditionField)) {
					BusinessException.throwBusinessException("CORRELATION_FIELD="
									+ conditionField + ",CORRELATION_TABLE="
									+ restoreDestTableName + "配置错误，找不到指定的字段");
				}
				String fieldTypeName = tableStructure.get(conditionField).getTypeName();
				PreparedStatement psDelete = restoreDestConn.prepareStatement(deleteSql);
				if (fieldTypeName.equals(ColumnTypeEnum.NUMBER.getDbType())) {
					psDelete.setLong(1, Long.parseLong(conditionValue));
				} else {
					psDelete.setString(1, conditionValue);
				}
				psDelete.execute();
				PreparedStatement psQuery = restoreSrcConn.prepareStatement(querySql);
				if (fieldTypeName.equals(ColumnTypeEnum.NUMBER.getDbType())) {
					psQuery.setLong(1, Long.parseLong(conditionValue));
				} else {
					psQuery.setString(1, conditionValue);
				}
				ResultSet rs = psQuery.executeQuery();
				PreparedStatement psInsert = restoreDestConn.prepareStatement(insertSql);
				while (rs.next()) {
					for (int i = 1; i <= tableStructure.size(); i++) {
						psInsert.setObject(i, rs.getObject(i));
					}
					psInsert.executeUpdate();
				}
				psDelete.close();
				psInsert.close();
				rs.close();
				updateRestoreDeal((byte)-1, DEAL_STATE.SUCCESS.value, backupDeal.getDealId());
			}catch(Exception e){
				log.error(e.getMessage(), e);
				ExceptionUtil.uncheckedAndWrap(e);
			}finally {
				try {
					closeConnection(restoreDestConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
				try {
					closeConnection(restoreSrcConn);
				} catch (SQLException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
	
	@Transactional
	public int updateBackupDeal(byte preState, byte upState, long dealId) {
		Connection backupConn = null;
		try {
			backupConn = getConnection(BACKUP_DB_KEY);
			String updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET STATE=?,DEAL_DATE=SYSDATE WHERE DEAL_ID=?";
			if (preState != -1) {
				updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET STATE=?,DEAL_DATE=SYSDATE WHERE DEAL_ID=? AND STATE=?";
			}
			PreparedStatement psUpdate = backupConn.prepareStatement(updateSql);
			psUpdate.setByte(1, upState);
			psUpdate.setLong(2, dealId);
			if (preState != -1) {
				psUpdate.setByte(3, preState);
			}
			int upNum = psUpdate.executeUpdate();
			return upNum;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ExceptionUtil.uncheckedAndWrap(e);
		} finally {
			try {
				closeConnection(backupConn);
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return 0;
	}
	
	@Transactional
	public int updateBackupDealMsg(String errMsg, long dealId) {
		if (errMsg == null) {
			return 0;
		}
		errMsg = substr(errMsg, 2000);
		Connection backupConn = null;
		try {
			backupConn = getConnection(BACKUP_DB_KEY);
			String updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET ERR_MSG=?,STATE=2,DEAL_DATE=SYSDATE WHERE DEAL_ID=?";
			PreparedStatement psUpdate = backupConn.prepareStatement(updateSql);
			psUpdate.setString(1, errMsg);
			psUpdate.setLong(2, dealId);
			int upNum = psUpdate.executeUpdate();
			return upNum;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ExceptionUtil.uncheckedAndWrap(e);
		} finally {
			try {
				closeConnection(backupConn);
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return 0;
	}
	
	private String substr(String str, int byteLength) {
		if (str == null || byteLength <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] chars = str.toCharArray();
		for (char c : chars) {
			if (c >= 0 && c <= 127) {
				byteLength--;
			} else {
				byteLength = byteLength - 3;
			}
			if (byteLength < 0) {
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	@Transactional
	public int updateRestoreDeal(byte preState, byte upState, long dealId) {
		Connection backupConn = null;
		try {
			backupConn = getConnection(BACKUP_DB_KEY);
			String updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET RESTORE_STATE=?,DEAL_DATE=SYSDATE WHERE DEAL_ID=?";
			if (preState != -1) {
				updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET RESTORE_STATE=?,DEAL_DATE=SYSDATE WHERE DEAL_ID=? AND RESTORE_STATE=?";
			}
			PreparedStatement psUpdate = backupConn.prepareStatement(updateSql);
			psUpdate.setByte(1, upState);
			psUpdate.setLong(2, dealId);
			if (preState != -1) {
				psUpdate.setByte(3, preState);
			}
			int upNum = psUpdate.executeUpdate();
			return upNum;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ExceptionUtil.uncheckedAndWrap(e);
		} finally {
			try {
				closeConnection(backupConn);
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return 0;
	}
	
	@Transactional
	public int updateRestoreDealMsg(String errMsg, long dealId) {
		if (errMsg == null) {
			return 0;
		}
		errMsg = substr(errMsg, 2000);
		Connection backupConn = null;
		try {
			backupConn = getConnection(BACKUP_DB_KEY);
			String updateSql = "UPDATE NA_AUTO_BACKUP_DEAL SET ERR_MSG=?,RESTORE_STATE=2,DEAL_DATE=SYSDATE WHERE DEAL_ID=?";
			PreparedStatement psUpdate = backupConn.prepareStatement(updateSql);
			psUpdate.setString(1, errMsg);
			psUpdate.setLong(2, dealId);
			int upNum = psUpdate.executeUpdate();
			return upNum;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ExceptionUtil.uncheckedAndWrap(e);
		} finally {
			try {
				closeConnection(backupConn);
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return 0;
	}

}

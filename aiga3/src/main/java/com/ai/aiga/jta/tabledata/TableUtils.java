package com.ai.aiga.jta.tabledata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableUtils {
	
	protected static final Logger log = LoggerFactory.getLogger(TableUtils.class);
	
	/**
	 * 获取表结构
	 * @param tableName
	 * @return
	 */
	public static Map<String,ColumnModel> getTableStructure(String tableName, Connection conn) {
		Map<String, ColumnModel> result = new LinkedHashMap<String, ColumnModel>();
		try {
			ResultSet columnSet = conn.getMetaData().getColumns(null, "%", tableName, "%");
			ColumnModel columnModel = null;
			while (columnSet.next()) {
				columnModel = new ColumnModel();
				columnModel.setColumnName(columnSet.getString("COLUMN_NAME"));
				columnModel.setColumnSize(columnSet.getInt("COLUMN_SIZE"));
				columnModel.setDataType(columnSet.getInt("DATA_TYPE"));
				columnModel.setRemarks(columnSet.getString("REMARKS"));
				columnModel.setTypeName(columnSet.getString("TYPE_NAME"));
				String columnClassName = ColumnTypeEnum.getColumnTypeEnumByDBType(columnModel.getTypeName());
				String fieldType = Class.forName(columnClassName).getSimpleName();
				columnModel.setColumnClassName(columnClassName);
				columnModel.setFieldType(fieldType);
				result.put(columnModel.getColumnName(), columnModel);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	public static String getInsertSqlPattern(String tableName, Set<String> fields) {
		StringBuffer result = new StringBuffer();
		StringBuffer sqlPart1 = new StringBuffer();
		StringBuffer sqlPart2 = new StringBuffer();
		int num = fields.size();
		for (String field : fields) {
			if (num == 1) {
				sqlPart1.append(field);
				sqlPart2.append("?");
			} else {
				sqlPart1.append(field).append(",");
				sqlPart2.append("?,");
			}
			num--;
		}
		result.append("INSERT INTO ").append(tableName).append(" (")
				.append(sqlPart1).append(")").append(" values (")
				.append(sqlPart2).append(")");
		return result.toString();
	}
	
	public static String getSelectSqlPattern(String tableName, Set<String> fields, String... condtions) {
		StringBuffer result = new StringBuffer();
		StringBuffer sqlPart1 = new StringBuffer();
		int num = fields.size();
		for (String field : fields) {
			if (num == 1) {
				sqlPart1.append(field);
			} else {
				sqlPart1.append(field).append(",");
			}
			num--;
		}
		StringBuffer sqlPart2 = new StringBuffer();
		for (int i = 0; i < condtions.length; i++) {
			if (i == 0) {
				sqlPart2.append(" WHERE ").append(condtions[0]).append("=?");
			} else {
				sqlPart2.append(" AND ").append(condtions[0]).append("=?");
			}
		}
		result.append("SELECT ").append(sqlPart1).append(" FROM ")
				.append(tableName).append(sqlPart2);
		return result.toString();
	}
	
	public static String getDeleteSqlPattern(String tableName, String... condtions) {
		StringBuffer result = new StringBuffer();
		StringBuffer sqlPart = new StringBuffer();
		for (int i = 0; i < condtions.length; i++) {
			if (i == 0) {
				sqlPart.append(" WHERE ").append(condtions[0]).append("=?");
			} else {
				sqlPart.append(" AND ").append(condtions[0]).append("=?");
			}
		}
		result.append("DELETE ").append(" FROM ").append(tableName).append(sqlPart);
		return result.toString();
	}

	public static String getCreateTableSql(String tableName, Map<String, ColumnModel> cols) {
		StringBuffer result = new StringBuffer();
		StringBuffer sqlPart1 = new StringBuffer();
		int i = 1;
		for (String col : cols.keySet()) {
			ColumnModel model = cols.get(col);
			String colType = model.getTypeName();
			if (!colType.equals("DATE")) {
				colType = colType + "(" + model.getColumnSize() + ")";
			}
			if (i++ == cols.size()) {
				sqlPart1.append(model.getColumnName()).append(" ").append(colType);
			} else {
				sqlPart1.append(model.getColumnName()).append(" ").append(colType).append(",");
			}
		}
		result.append("CREATE TABLE ").append(tableName).append(" (").append(sqlPart1).append(")");
		return result.toString();
	}
	
}

package com.ai.aiga.jta.tabledata;

/**
 * 数据库类型枚举
 */
public enum ColumnTypeEnum {
	VARCHAR2("VARCHAR2", "java.lang.String"), 
	NUMBER("NUMBER","java.lang.Double"), 
	DATE("DATE", "java.lang.String"), 
	CHAR("CHAR","java.lang.String");

	private String dbType;
	private String javaType;

	ColumnTypeEnum(String dbType, String javaType) {
		this.dbType = dbType;
		this.javaType = javaType;
	}

	public static String getColumnTypeEnumByDBType(String dbType) {
		for (ColumnTypeEnum columnTypeEnum : ColumnTypeEnum.values()) {
			if (columnTypeEnum.getDbType().equals(dbType)) {
				return columnTypeEnum.getJavaType();
			}
		}
		return "";
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
}
package com.ai.process.jta;

public class DatabaseContextHolder {
	
	public static final String DEFAULT_DB_TYPE = "aiga";
   public static final String  DATASOURCE_CSHP03 = "CSHP03";  //新炬数据库
   
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDBType(String type) {
		contextHolder.set(type);
	}

	public static String getDBType() {
		return contextHolder.get();
	}

	public static void clearDBType() {
		contextHolder.remove();
	}
}
package com.ai.aiga.jta;

public class DatabaseContextHolder {
	
	public static final String DEFAULT_DB_TYPE = "aiga";

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
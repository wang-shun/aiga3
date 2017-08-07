package com.ai.am.core.datasource;

/**
 * @ClassName: DataSourceInfo
 * @author: taoyf
 * @date: 2017年5月4日 下午4:10:54
 * @Description:
 * 
 */
public class DataSourceInfo {
	
    private String dbAcctCode;
    private String username;
    private String password;
    private String host;
    private Integer port;
    private String sid;
    private Short defaultConnMin;
    private Short defaultConnMax;
	public String getDbAcctCode() {
		return dbAcctCode;
	}
	public void setDbAcctCode(String dbAcctCode) {
		this.dbAcctCode = dbAcctCode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Short getDefaultConnMin() {
		return defaultConnMin;
	}
	public void setDefaultConnMin(Short defaultConnMin) {
		this.defaultConnMin = defaultConnMin;
	}
	public Short getDefaultConnMax() {
		return defaultConnMax;
	}
	public void setDefaultConnMax(Short defaultConnMax) {
		this.defaultConnMax = defaultConnMax;
	}
	@Override
	public String toString() {
		return "DataSourceInfo [dbAcctCode=" + dbAcctCode + ", username=" + username + ", password=" + password
				+ ", host=" + host + ", port=" + port + ", sid=" + sid + ", defaultConnMin=" + defaultConnMin
				+ ", defaultConnMax=" + defaultConnMax + "]";
	}

}


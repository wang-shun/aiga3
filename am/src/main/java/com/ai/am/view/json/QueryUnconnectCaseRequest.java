package com.ai.am.view.json;


/**
 *  查询未关联的用例的查询条件
 * @author lovestar
 *
 */
public class QueryUnconnectCaseRequest {
	private Long collectId; //用例集Id
	private String caseName ; //用例名称
	private Long types; //类型
	private Long sysId; //系统大类
	private Long sysSubId ;
	private Long funId;//功能点
	private String  templeteName;//用例模板名称
	private Long serviceId; //业务
	
	public QueryUnconnectCaseRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCaseName() {
		return caseName;
	}

	public Long getSysSubId() {
		return sysSubId;
	}

	public void setSysSubId(Long sysSubId) {
		this.sysSubId = sysSubId;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public Long getTypes() {
		return types;
	}
	public void setTypes(Long types) {
		this.types = types;
	}
	public Long getSysId() {
		return sysId;
	}
	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}
	public Long getFunId() {
		return funId;
	}
	public void setFunId(Long funId) {
		this.funId = funId;
	}
	public String getTempleteName() {
		return templeteName;
	}
	public void setTempleteName(String templeteName) {
		this.templeteName = templeteName;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	

}

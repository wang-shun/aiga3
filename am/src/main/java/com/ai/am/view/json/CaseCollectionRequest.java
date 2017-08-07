package com.ai.am.view.json;

public class CaseCollectionRequest {
	
	  private Long collectId;
	  private String collectName;
	  private Long caseType;
      private Long repairsId;
      private Long caseNum;
      private Long sysId;
	
	public CaseCollectionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Long getSysId() {
		return sysId;
	}




	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}




	public Long getCaseNum() {
		return caseNum;
	}




	public void setCaseNum(Long caseNum) {
		this.caseNum = caseNum;
	}




	public CaseCollectionRequest(Long collectId, String collectName, Long caseType, Long repairsId, Long caseNum) {
		super();
		this.collectId = collectId;
		this.collectName = collectName;
		this.caseType = caseType;
		this.repairsId = repairsId;
		this.caseNum = caseNum;
	}




	public Long getCollectId() {
		return collectId;
	}


	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}


	public String getCollectName() {
		return collectName;
	}


	public void setCollectName(String collectName) {
		this.collectName = collectName;
	}


	public Long getCaseType() {
		return caseType;
	}


	public void setCaseType(Long caseType) {
		this.caseType = caseType;
	}


	public Long getRepairsId() {
		return repairsId;
	}


	public void setRepairsId(Long repairsId) {
		this.repairsId = repairsId;
	}


	@Override
	public String toString() {
		return "RoleRequest [collectId=" + collectId + ", collectName=" + collectName + ", caseType=" + caseType + ", repairsId=" + repairsId + "]";
	}
	

}

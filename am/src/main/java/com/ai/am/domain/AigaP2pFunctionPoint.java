package com.ai.am.domain;
// Generated 2017-2-16 15:37:51 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AigaP2pFunctionPoint generated by hbm2java
 */
@Entity
@Table(name="AIGA_P2P_FUNCTION_POINT"
    ,schema="AIGA"
)
public class AigaP2pFunctionPoint  implements java.io.Serializable {


     private BigDecimal funId;
     private String sysName;
     private Date createTime;
     private Date updateTime;
     private String cause;
     private Short causeType;
     private BigDecimal operatorId;
     private String operatorName;
     private BigDecimal creatorId;
     private String creatorName;
     private BigDecimal status;
     private BigDecimal verifyStatus;
     private String verifyResult;

    public AigaP2pFunctionPoint() {
    }

	
    public AigaP2pFunctionPoint(BigDecimal funId) {
        this.funId = funId;
    }
    public AigaP2pFunctionPoint(BigDecimal funId, String sysName, Date createTime, Date updateTime, String cause, Short causeType, BigDecimal operatorId, String operatorName, BigDecimal creatorId, String creatorName, BigDecimal status, BigDecimal verifyStatus, String verifyResult) {
       this.funId = funId;
       this.sysName = sysName;
       this.createTime = createTime;
       this.updateTime = updateTime;
       this.cause = cause;
       this.causeType = causeType;
       this.operatorId = operatorId;
       this.operatorName = operatorName;
       this.creatorId = creatorId;
       this.creatorName = creatorName;
       this.status = status;
       this.verifyStatus = verifyStatus;
       this.verifyResult = verifyResult;
    }
   
     @Id 
    
    @Column(name="FUN_ID", unique=true, nullable=false, precision=20, scale=0)
    public BigDecimal getFunId() {
        return this.funId;
    }
    
    public void setFunId(BigDecimal funId) {
        this.funId = funId;
    }
    
    @Column(name="SYS_NAME", length=2000)
    public String getSysName() {
        return this.sysName;
    }
    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_TIME", length=7)
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @Column(name="CAUSE", length=200)
    public String getCause() {
        return this.cause;
    }
    
    public void setCause(String cause) {
        this.cause = cause;
    }
    
    @Column(name="CAUSE_TYPE", precision=4, scale=0)
    public Short getCauseType() {
        return this.causeType;
    }
    
    public void setCauseType(Short causeType) {
        this.causeType = causeType;
    }
    
    @Column(name="OPERATOR_ID", precision=20, scale=0)
    public BigDecimal getOperatorId() {
        return this.operatorId;
    }
    
    public void setOperatorId(BigDecimal operatorId) {
        this.operatorId = operatorId;
    }
    
    @Column(name="OPERATOR_NAME")
    public String getOperatorName() {
        return this.operatorName;
    }
    
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    
    @Column(name="CREATOR_ID", precision=20, scale=0)
    public BigDecimal getCreatorId() {
        return this.creatorId;
    }
    
    public void setCreatorId(BigDecimal creatorId) {
        this.creatorId = creatorId;
    }
    
    @Column(name="CREATOR_NAME", length=200)
    public String getCreatorName() {
        return this.creatorName;
    }
    
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    @Column(name="STATUS", precision=20, scale=0)
    public BigDecimal getStatus() {
        return this.status;
    }
    
    public void setStatus(BigDecimal status) {
        this.status = status;
    }
    
    @Column(name="VERIFY_STATUS", precision=20, scale=0)
    public BigDecimal getVerifyStatus() {
        return this.verifyStatus;
    }
    
    public void setVerifyStatus(BigDecimal verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
    
    @Column(name="VERIFY_RESULT")
    public String getVerifyResult() {
        return this.verifyResult;
    }
    
    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }


	@Override
	public String toString() {
		return "AigaP2pFunctionPoint [funId=" + funId + ", sysName=" + sysName + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", cause=" + cause + ", causeType=" + causeType + ", operatorId="
				+ operatorId + ", operatorName=" + operatorName + ", creatorId=" + creatorId + ", creatorName="
				+ creatorName + ", status=" + status + ", verifyStatus=" + verifyStatus + ", verifyResult="
				+ verifyResult + "]";
	}




}



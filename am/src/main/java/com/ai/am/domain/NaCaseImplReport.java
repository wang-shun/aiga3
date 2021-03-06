package com.ai.am.domain;
// Generated 2017-4-27 19:24:54 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * NaCaseImplReport generated by hbm2java
 */
@Entity
@Table(name="NA_CASE_IMPL_REPORT")
public class NaCaseImplReport  implements java.io.Serializable {


     private Long reportId;
     private Long onlinePlanId;
     private String onlinePlanName;
     private Date planDate;
     private String sysName;
     private String environment;
     private Integer caseTotalCount;
     private Integer manualCaseCount;
     private Integer autoCaseCount;
     private String firSucRate;
     private String firExecTime;
     private String secSucRate;
     private String secExecTime;
     private String thirdSucRate;
     private String thirdExecTime;
     private Byte flag;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaCaseImplReport() {
    }

	
    public NaCaseImplReport(Long reportId) {
        this.reportId = reportId;
    }
    public NaCaseImplReport(Long reportId, Long onlinePlanId, String onlinePlanName, Date planDate, String sysName, String environment, Integer caseTotalCount, Integer manualCaseCount, Integer autoCaseCount, String firSucRate, String firExecTime, String secSucRate, String secExecTime, String thirdSucRate, String thirdExecTime, Byte flag, String ext1, String ext2, String ext3) {
       this.reportId = reportId;
       this.onlinePlanId = onlinePlanId;
       this.onlinePlanName = onlinePlanName;
       this.planDate = planDate;
       this.sysName = sysName;
       this.environment = environment;
       this.caseTotalCount = caseTotalCount;
       this.manualCaseCount = manualCaseCount;
       this.autoCaseCount = autoCaseCount;
       this.firSucRate = firSucRate;
       this.firExecTime = firExecTime;
       this.secSucRate = secSucRate;
       this.secExecTime = secExecTime;
       this.thirdSucRate = thirdSucRate;
       this.thirdExecTime = thirdExecTime;
       this.flag = flag;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NA_CASE_IMPL_REPORT$SEQ")
 	@SequenceGenerator(name = "NA_CASE_IMPL_REPORT$SEQ", sequenceName = "NA_CASE_IMPL_REPORT$SEQ", allocationSize = 1)
    @Column(name="REPORT_ID", unique=true, nullable=false, precision=14, scale=0)
    public Long getReportId() {
        return this.reportId;
    }
    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    @Column(name="ONLINE_PLAN_ID", precision=14, scale=0)
    public Long getOnlinePlanId() {
        return this.onlinePlanId;
    }
    
    public void setOnlinePlanId(Long onlinePlanId) {
        this.onlinePlanId = onlinePlanId;
    }
    
    @Column(name="ONLINE_PLAN_NAME", length=200)
    public String getOnlinePlanName() {
        return this.onlinePlanName;
    }
    
    public void setOnlinePlanName(String onlinePlanName) {
        this.onlinePlanName = onlinePlanName;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PLAN_DATE", length=7)
    public Date getPlanDate() {
        return this.planDate;
    }
    
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
    
    @Column(name="SYS_NAME", length=2000)
    public String getSysName() {
        return this.sysName;
    }
    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    
    @Column(name="ENVIRONMENT", length=20)
    public String getEnvironment() {
        return this.environment;
    }
    
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    
    @Column(name="CASE_TOTAL_COUNT", precision=22, scale=0)
    public Integer getCaseTotalCount() {
        return this.caseTotalCount;
    }
    
    public void setCaseTotalCount(Integer caseTotalCount) {
        this.caseTotalCount = caseTotalCount;
    }
    
    @Column(name="MANUAL_CASE_COUNT", precision=22, scale=0)
    public Integer getManualCaseCount() {
        return this.manualCaseCount;
    }
    
    public void setManualCaseCount(Integer manualCaseCount) {
        this.manualCaseCount = manualCaseCount;
    }
    
    @Column(name="AUTO_CASE_COUNT", precision=22, scale=0)
    public Integer getAutoCaseCount() {
        return this.autoCaseCount;
    }
    
    public void setAutoCaseCount(Integer autoCaseCount) {
        this.autoCaseCount = autoCaseCount;
    }
    
    @Column(name="FIR_SUC_RATE", length=20)
    public String getFirSucRate() {
        return this.firSucRate;
    }
    
    public void setFirSucRate(String firSucRate) {
        this.firSucRate = firSucRate;
    }
    
    @Column(name="FIR_EXEC_TIME", length=20)
    public String getFirExecTime() {
        return this.firExecTime;
    }
    
    public void setFirExecTime(String firExecTime) {
        this.firExecTime = firExecTime;
    }
    
    @Column(name="SEC_SUC_RATE", length=20)
    public String getSecSucRate() {
        return this.secSucRate;
    }
    
    public void setSecSucRate(String secSucRate) {
        this.secSucRate = secSucRate;
    }
    
    @Column(name="SEC_EXEC_TIME", length=20)
    public String getSecExecTime() {
        return this.secExecTime;
    }
    
    public void setSecExecTime(String secExecTime) {
        this.secExecTime = secExecTime;
    }
    
    @Column(name="THIRD_SUC_RATE", length=20)
    public String getThirdSucRate() {
        return this.thirdSucRate;
    }
    
    public void setThirdSucRate(String thirdSucRate) {
        this.thirdSucRate = thirdSucRate;
    }
    
    @Column(name="THIRD_EXEC_TIME", length=20)
    public String getThirdExecTime() {
        return this.thirdExecTime;
    }
    
    public void setThirdExecTime(String thirdExecTime) {
        this.thirdExecTime = thirdExecTime;
    }
    
    @Column(name="FLAG", precision=2, scale=0)
    public Byte getFlag() {
        return this.flag;
    }
    
    public void setFlag(Byte flag) {
        this.flag = flag;
    }
    
    @Column(name="EXT1", length=200)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT2", length=200)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT3", length=200)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}



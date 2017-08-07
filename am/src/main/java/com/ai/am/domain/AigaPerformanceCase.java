package com.ai.am.domain;
// Generated 2017-4-27 9:13:37 by Hibernate Tools 3.2.2.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * AigaPerformanceCase generated by hbm2java
 */
@Entity
@Table(name="AIGA_PERFORMANCE_CASE"
    ,schema="AIGA"
)
public class AigaPerformanceCase  implements java.io.Serializable {


     private BigDecimal scriptId;
     private String scriptTime;
     private String interName;
     private String scriptPath;
     private String scriptDateMonth;
     private String isRealDataAuto;
     private String devId;
     private String employeeName;

    public AigaPerformanceCase() {
    }

	
    public AigaPerformanceCase(BigDecimal scriptId) {
        this.scriptId = scriptId;
    }
    public AigaPerformanceCase(BigDecimal scriptId, String scriptTime, String interName, String scriptPath, String scriptDateMonth, String isRealDataAuto, String devId, String employeeName) {
       this.scriptId = scriptId;
       this.scriptTime = scriptTime;
       this.interName = interName;
       this.scriptPath = scriptPath;
       this.scriptDateMonth = scriptDateMonth;
       this.isRealDataAuto = isRealDataAuto;
       this.devId = devId;
       this.employeeName = employeeName;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_PERFORMANCE_CASE$SEQ")
 	@SequenceGenerator(name="AIGA_PERFORMANCE_CASE$SEQ",sequenceName="AIGA_PERFORMANCE_CASE$SEQ",allocationSize=1)
    @Column(name="SCRIPT_ID", unique=true, nullable=false, precision=22, scale=0)
    public BigDecimal getScriptId() {
        return this.scriptId;
    }
    
    public void setScriptId(BigDecimal scriptId) {
        this.scriptId = scriptId;
    }
    
    @Column(name="SCRIPT_TIME", length=200)
    public String getScriptTime() {
        return this.scriptTime;
    }
    
    public void setScriptTime(String scriptTime) {
        this.scriptTime = scriptTime;
    }
    
    @Column(name="INTER_NAME", length=200)
    public String getInterName() {
        return this.interName;
    }
    
    public void setInterName(String interName) {
        this.interName = interName;
    }
    
    @Column(name="SCRIPT_PATH", length=200)
    public String getScriptPath() {
        return this.scriptPath;
    }
    
    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }
    
    @Column(name="SCRIPT_DATE_MONTH", length=200)
    public String getScriptDateMonth() {
        return this.scriptDateMonth;
    }
    
    public void setScriptDateMonth(String scriptDateMonth) {
        this.scriptDateMonth = scriptDateMonth;
    }
    
    @Column(name="IS_REAL_DATA_AUTO", length=200)
    public String getIsRealDataAuto() {
        return this.isRealDataAuto;
    }
    
    public void setIsRealDataAuto(String isRealDataAuto) {
        this.isRealDataAuto = isRealDataAuto;
    }
    
    @Column(name="DEV_ID", length=200)
    public String getDevId() {
        return this.devId;
    }
    
    public void setDevId(String devId) {
        this.devId = devId;
    }
    
    @Column(name="EMPLOYEE_NAME", length=200)
    public String getEmployeeName() {
        return this.employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }




}



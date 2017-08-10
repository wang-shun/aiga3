package com.ai.am.domain;
// Generated 2017-3-17 15:13:22 by Hibernate Tools 3.2.2.GA



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

import org.springframework.format.annotation.DateTimeFormat;

/**
 * NaAutoMachine generated by hbm2java
 */
@Entity
@Table(name="NA_AUTO_MACHINE"
    ,schema="AIGA"
)
public class NaAutoMachine  implements java.io.Serializable {


     private Long machineId;
     private String machineIp;
     private String machineName;
     private Long status;
     private String machineAccount;
     private String machinePassword;
     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date requestTime;
     private Long taskId;

    public NaAutoMachine() {
    }

	
    public NaAutoMachine(Long machineId) {
        this.machineId = machineId;
    }
    public NaAutoMachine(Long machineId, String machineIp, String machineName, Long status, String machineAccount, String machinePassword, Date requestTime, Long taskId) {
       this.machineId = machineId;
       this.machineIp = machineIp;
       this.machineName = machineName;
       this.status = status;
       this.machineAccount = machineAccount;
       this.machinePassword = machinePassword;
       this.requestTime = requestTime;
       this.taskId = taskId;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_MACHINE$SEQ")
     @SequenceGenerator(name="NA_AUTO_MACHINE$SEQ",sequenceName="NA_AUTO_MACHINE$SEQ",allocationSize=1)
    @Column(name="MACHINE_ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getMachineId() {
        return this.machineId;
    }
    
    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
    
    @Column(name="MACHINE_IP", length=20)
    public String getMachineIp() {
        return this.machineIp;
    }
    
    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }
    
    @Column(name="MACHINE_NAME", length=40)
    public String getMachineName() {
        return this.machineName;
    }
    
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
    
    @Column(name="STATUS", precision=22, scale=0)
    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    @Column(name="MACHINE_ACCOUNT", length=40)
    public String getMachineAccount() {
        return this.machineAccount;
    }
    
    public void setMachineAccount(String machineAccount) {
        this.machineAccount = machineAccount;
    }
    
    @Column(name="MACHINE_PASSWORD", length=40)
    public String getMachinePassword() {
        return this.machinePassword;
    }
    
    public void setMachinePassword(String machinePassword) {
        this.machinePassword = machinePassword;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQUEST_TIME", length=7)
    public Date getRequestTime() {
        return this.requestTime;
    }
    
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    @Column(name="TASK_ID", precision=22, scale=0)
    public Long getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }




}


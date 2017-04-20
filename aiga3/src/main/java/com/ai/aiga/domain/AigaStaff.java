package com.ai.aiga.domain;
// Generated 2017-2-22 10:28:03 by Hibernate Tools 3.2.2.GA


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
 * AigaStaff generated by hbm2java
 */
@Entity
@Table(name="AIGA_STAFF")
public class AigaStaff  implements java.io.Serializable {


     private Long staffId;
     private String code;
     private String name;
     private String password;
     private String billId;
     private Integer cardTypeId;
     private String cardNo;
     private String email;
     private String recentPassword;
     private Integer recentPassTimes;
     private Integer minPasswdLength;
     private Character allowChangePassword;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date acctEffectDate;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date acctExpireDate;
     private Character multiLoginFlag;
     private Long lastLoginLogId;
     private Integer tryTimes;
     private Character lockFlag;
     private Character isLogin;
     private Character isSuperUser;
     private String notes;
     private Long passwdValidDays;
     private Integer cancelDays;
     private Date passwordValidDate;
     private Long chgPasswdAlarmDays;
     private Long doneCode;
     private Date createDate;
     private Date doneDate;
     private Date validDate;
     private Date expireDate;
     private Long orgId;
     private Long opId;
     private Integer state;
     private String oldCode;
     private Integer opType;
     private String ext1;
     private String ext2;
     private String ext3;
     private Integer opLvl;
     private Integer bandType;

    public AigaStaff() {
    }

	
    public AigaStaff(Long staffId) {
        this.staffId = staffId;
    }
    
   
    public AigaStaff(Long staffId, String code, String name, String password, String billId, Integer cardTypeId,
			String cardNo, String email, String recentPassword, Integer recentPassTimes, Integer minPasswdLength,
			Character allowChangePassword, Date acctEffectDate, Date acctExpireDate, Character multiLoginFlag,
			Long lastLoginLogId, Integer tryTimes, Character lockFlag, Character isLogin, Character isSuperUser,
			String notes, Long passwdValidDays, Integer cancelDays, Date passwordValidDate, Long chgPasswdAlarmDays,
			Long doneCode, Date createDate, Date doneDate, Date validDate, Date expireDate, Long orgId, Long opId,
			Integer state, String oldCode, Integer opType, String ext1, String ext2, String ext3, Integer opLvl,
			Integer bandType) {
		super();
		this.staffId = staffId;
		this.code = code;
		this.name = name;
		this.password = password;
		this.billId = billId;
		this.cardTypeId = cardTypeId;
		this.cardNo = cardNo;
		this.email = email;
		this.recentPassword = recentPassword;
		this.recentPassTimes = recentPassTimes;
		this.minPasswdLength = minPasswdLength;
		this.allowChangePassword = allowChangePassword;
		this.acctEffectDate = acctEffectDate;
		this.acctExpireDate = acctExpireDate;
		this.multiLoginFlag = multiLoginFlag;
		this.lastLoginLogId = lastLoginLogId;
		this.tryTimes = tryTimes;
		this.lockFlag = lockFlag;
		this.isLogin = isLogin;
		this.isSuperUser = isSuperUser;
		this.notes = notes;
		this.passwdValidDays = passwdValidDays;
		this.cancelDays = cancelDays;
		this.passwordValidDate = passwordValidDate;
		this.chgPasswdAlarmDays = chgPasswdAlarmDays;
		this.doneCode = doneCode;
		this.createDate = createDate;
		this.doneDate = doneDate;
		this.validDate = validDate;
		this.expireDate = expireDate;
		this.orgId = orgId;
		this.opId = opId;
		this.state = state;
		this.oldCode = oldCode;
		this.opType = opType;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
		this.opLvl = opLvl;
		this.bandType = bandType;
	}


	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_STAFF$SEQ")
    @SequenceGenerator(name="AIGA_STAFF$SEQ",sequenceName="AIGA_STAFF$SEQ",allocationSize=1)
    @Column(name="STAFF_ID", unique=true, nullable=false, precision=12, scale=0)
    public Long getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    
    @Column(name="CODE", length=20)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="NAME", length=200)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="PASSWORD", length=48)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name="BILL_ID", length=50)
    public String getBillId() {
        return this.billId;
    }
    
    public void setBillId(String billId) {
        this.billId = billId;
    }
    
    @Column(name="CARD_TYPE_ID", precision=2, scale=0)
    public Integer getCardTypeId() {
        return this.cardTypeId;
    }
    
    public void setCardTypeId(Integer cardTypeId) {
        this.cardTypeId = cardTypeId;
    }
    
    @Column(name="CARD_NO", length=40)
    public String getCardNo() {
        return this.cardNo;
    }
    
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    
    @Column(name="EMAIL", length=50)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="RECENT_PASSWORD", length=300)
    public String getRecentPassword() {
        return this.recentPassword;
    }
    
    public void setRecentPassword(String recentPassword) {
        this.recentPassword = recentPassword;
    }
    
    @Column(name="RECENT_PASS_TIMES", precision=2, scale=0)
    public Integer getRecentPassTimes() {
        return this.recentPassTimes;
    }
    
    public void setRecentPassTimes(Integer recentPassTimes) {
        this.recentPassTimes = recentPassTimes;
    }
    
    @Column(name="MIN_PASSWD_LENGTH", precision=2, scale=0)
    public Integer getMinPasswdLength() {
        return this.minPasswdLength;
    }
    
    public void setMinPasswdLength(Integer minPasswdLength) {
        this.minPasswdLength = minPasswdLength;
    }
    
    @Column(name="ALLOW_CHANGE_PASSWORD", length=1)
    public Character getAllowChangePassword() {
        return this.allowChangePassword;
    }
    
    public void setAllowChangePassword(Character allowChangePassword) {
        this.allowChangePassword = allowChangePassword;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACCT_EFFECT_DATE", length=7)
    public Date getAcctEffectDate() {
        return this.acctEffectDate;
    }
    
    public void setAcctEffectDate(Date acctEffectDate) {
        this.acctEffectDate = acctEffectDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ACCT_EXPIRE_DATE", length=7)
    public Date getAcctExpireDate() {
        return this.acctExpireDate;
    }
    
    public void setAcctExpireDate(Date acctExpireDate) {
        this.acctExpireDate = acctExpireDate;
    }
    
    @Column(name="MULTI_LOGIN_FLAG", length=1)
    public Character getMultiLoginFlag() {
        return this.multiLoginFlag;
    }
    
    public void setMultiLoginFlag(Character multiLoginFlag) {
        this.multiLoginFlag = multiLoginFlag;
    }
    
    @Column(name="LAST_LOGIN_LOG_ID", precision=12, scale=0)
    public Long getLastLoginLogId() {
        return this.lastLoginLogId;
    }
    
    public void setLastLoginLogId(Long lastLoginLogId) {
        this.lastLoginLogId = lastLoginLogId;
    }
    
    @Column(name="TRY_TIMES", precision=3, scale=0)
    public Integer getTryTimes() {
        return this.tryTimes;
    }
    
    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }
    
    @Column(name="LOCK_FLAG", length=1)
    public Character getLockFlag() {
        return this.lockFlag;
    }
    
    public void setLockFlag(Character lockFlag) {
        this.lockFlag = lockFlag;
    }
    
    @Column(name="IS_LOGIN", length=1)
    public Character getIsLogin() {
        return this.isLogin;
    }
    
    public void setIsLogin(Character isLogin) {
        this.isLogin = isLogin;
    }
    
    @Column(name="IS_SUPER_USER", length=1)
    public Character getIsSuperUser() {
        return this.isSuperUser;
    }
    
    public void setIsSuperUser(Character isSuperUser) {
        this.isSuperUser = isSuperUser;
    }
    
    @Column(name="NOTES", length=400)
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Column(name="PASSWD_VALID_DAYS", precision=10, scale=0)
    public Long getPasswdValidDays() {
        return this.passwdValidDays;
    }
    
    public void setPasswdValidDays(Long passwdValidDays) {
        this.passwdValidDays = passwdValidDays;
    }
    
    @Column(name="CANCEL_DAYS", precision=3, scale=0)
    public Integer getCancelDays() {
        return this.cancelDays;
    }
    
    public void setCancelDays(Integer cancelDays) {
        this.cancelDays = cancelDays;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PASSWORD_VALID_DATE", length=7)
    public Date getPasswordValidDate() {
        return this.passwordValidDate;
    }
    
    public void setPasswordValidDate(Date passwordValidDate) {
        this.passwordValidDate = passwordValidDate;
    }
    
    @Column(name="CHG_PASSWD_ALARM_DAYS", precision=10, scale=0)
    public Long getChgPasswdAlarmDays() {
        return this.chgPasswdAlarmDays;
    }
    
    public void setChgPasswdAlarmDays(Long chgPasswdAlarmDays) {
        this.chgPasswdAlarmDays = chgPasswdAlarmDays;
    }
    
    @Column(name="DONE_CODE", precision=12, scale=0)
    public Long getDoneCode() {
        return this.doneCode;
    }
    
    public void setDoneCode(Long doneCode) {
        this.doneCode = doneCode;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DONE_DATE", length=7)
    public Date getDoneDate() {
        return this.doneDate;
    }
    
    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_DATE", length=7)
    public Date getValidDate() {
        return this.validDate;
    }
    
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIRE_DATE", length=7)
    public Date getExpireDate() {
        return this.expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    @Column(name="ORG_ID", precision=12, scale=0)
    public Long getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    
    @Column(name="OP_ID", precision=12, scale=0)
    public Long getOpId() {
        return this.opId;
    }
    
    public void setOpId(Long opId) {
        this.opId = opId;
    }
    
    @Column(name="STATE", precision=2, scale=0)
    public Integer getState() {
        return this.state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    @Column(name="OLD_CODE", length=20)
    public String getOldCode() {
        return this.oldCode;
    }
    
    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }
    
    @Column(name="OP_TYPE", precision=3, scale=0)
    public Integer getOpType() {
        return this.opType;
    }
    
    public void setOpType(Integer opType) {
        this.opType = opType;
    }
    
    @Column(name="EXT1", length=40)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT2", length=40)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT3", length=40)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
    
    @Column(name="OP_LVL", precision=3, scale=0)
    public Integer getOpLvl() {
        return this.opLvl;
    }
    
    public void setOpLvl(Integer opLvl) {
        this.opLvl = opLvl;
    }
    
    @Column(name="BAND_TYPE", precision=3, scale=0)
    public Integer getBandType() {
        return this.bandType;
    }
    
    public void setBandType(Integer bandType) {
        this.bandType = bandType;
    }




}



package com.ai.am.view.controller.staff.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @ClassName: StaffRequest
 * @author: taoyf
 * @date: 2017年4月20日 下午2:33:07
 * @Description:
 * 
 */
@Data
public class StaffInfoRequest {
	
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
    
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public Integer getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(Integer cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRecentPassword() {
		return recentPassword;
	}
	public void setRecentPassword(String recentPassword) {
		this.recentPassword = recentPassword;
	}
	public Integer getRecentPassTimes() {
		return recentPassTimes;
	}
	public void setRecentPassTimes(Integer recentPassTimes) {
		this.recentPassTimes = recentPassTimes;
	}
	public Integer getMinPasswdLength() {
		return minPasswdLength;
	}
	public void setMinPasswdLength(Integer minPasswdLength) {
		this.minPasswdLength = minPasswdLength;
	}
	public Character getAllowChangePassword() {
		return allowChangePassword;
	}
	public void setAllowChangePassword(Character allowChangePassword) {
		this.allowChangePassword = allowChangePassword;
	}
	public Date getAcctEffectDate() {
		return acctEffectDate;
	}
	public void setAcctEffectDate(Date acctEffectDate) {
		this.acctEffectDate = acctEffectDate;
	}
	public Date getAcctExpireDate() {
		return acctExpireDate;
	}
	public void setAcctExpireDate(Date acctExpireDate) {
		this.acctExpireDate = acctExpireDate;
	}
	public Character getMultiLoginFlag() {
		return multiLoginFlag;
	}
	public void setMultiLoginFlag(Character multiLoginFlag) {
		this.multiLoginFlag = multiLoginFlag;
	}
	public Long getLastLoginLogId() {
		return lastLoginLogId;
	}
	public void setLastLoginLogId(Long lastLoginLogId) {
		this.lastLoginLogId = lastLoginLogId;
	}
	public Integer getTryTimes() {
		return tryTimes;
	}
	public void setTryTimes(Integer tryTimes) {
		this.tryTimes = tryTimes;
	}
	public Character getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Character lockFlag) {
		this.lockFlag = lockFlag;
	}
	public Character getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(Character isLogin) {
		this.isLogin = isLogin;
	}
	public Character getIsSuperUser() {
		return isSuperUser;
	}
	public void setIsSuperUser(Character isSuperUser) {
		this.isSuperUser = isSuperUser;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Long getPasswdValidDays() {
		return passwdValidDays;
	}
	public void setPasswdValidDays(Long passwdValidDays) {
		this.passwdValidDays = passwdValidDays;
	}
	public Integer getCancelDays() {
		return cancelDays;
	}
	public void setCancelDays(Integer cancelDays) {
		this.cancelDays = cancelDays;
	}
	public Date getPasswordValidDate() {
		return passwordValidDate;
	}
	public void setPasswordValidDate(Date passwordValidDate) {
		this.passwordValidDate = passwordValidDate;
	}
	public Long getChgPasswdAlarmDays() {
		return chgPasswdAlarmDays;
	}
	public void setChgPasswdAlarmDays(Long chgPasswdAlarmDays) {
		this.chgPasswdAlarmDays = chgPasswdAlarmDays;
	}
	public Long getDoneCode() {
		return doneCode;
	}
	public void setDoneCode(Long doneCode) {
		this.doneCode = doneCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getOpId() {
		return opId;
	}
	public void setOpId(Long opId) {
		this.opId = opId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getOldCode() {
		return oldCode;
	}
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	public Integer getOpType() {
		return opType;
	}
	public void setOpType(Integer opType) {
		this.opType = opType;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public Integer getOpLvl() {
		return opLvl;
	}
	public void setOpLvl(Integer opLvl) {
		this.opLvl = opLvl;
	}
	public Integer getBandType() {
		return bandType;
	}
	public void setBandType(Integer bandType) {
		this.bandType = bandType;
	}

}


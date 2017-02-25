package com.ai.aiga.view.json;

import java.util.Date;

public class StaffRequest {
	private Long staffId;
    private String code;
    private String name;
    private String password;
    private String billId;
    private Byte cardTypeId;
    private String cardNo;
    private String email;
    private Byte recentPassTimes;
    private Byte minPasswdLength;
    private Character allowChangePassword;
    private Date acctEffectDate;
    private Date acctExpireDate;
    private Character multiLoginFlag;
    private Short tryTimes;
    private Character lockFlag;
    private String notes;
    private Long chgPasswdAlarmDays;
    private Short opType;
    private String ext1;
    private String ext2;
    private String ext3;
    private Short opLvl;
    private Short bandType;
    public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(long staffId) {
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
	public Byte getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(Byte cardTypeId) {
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
	public Byte getRecentPassTimes() {
		return recentPassTimes;
	}
	public void setRecentPassTimes(Byte recentPassTimes) {
		this.recentPassTimes = recentPassTimes;
	}
	public Byte getMinPasswdLength() {
		return minPasswdLength;
	}
	public void setMinPasswdLength(Byte minPasswdLength) {
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
	public Short getTryTimes() {
		return tryTimes;
	}
	public void setTryTimes(Short tryTimes) {
		this.tryTimes = tryTimes;
	}
	public Character getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Character lockFlag) {
		this.lockFlag = lockFlag;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Long getChgPasswdAlarmDays() {
		return chgPasswdAlarmDays;
	}
	public void setChgPasswdAlarmDays(Long chgPasswdAlarmDays) {
		this.chgPasswdAlarmDays = chgPasswdAlarmDays;
	}
	public Short getOpType() {
		return opType;
	}
	public void setOpType(Short opType) {
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
	public Short getOpLvl() {
		return opLvl;
	}
	public void setOpLvl(Short opLvl) {
		this.opLvl = opLvl;
	}
	public Short getBandType() {
		return bandType;
	}
	public void setBandType(Short bandType) {
		this.bandType = bandType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StaffRequest [staffId=").append(staffId).append(", code=").append(code).append(", name=")
				.append(name).append(", password=").append(password).append(", billId=").append(billId)
				.append(", cardTypeId=").append(cardTypeId).append(", cardNo=").append(cardNo).append(", email=")
				.append(email).append(", recentPassTimes=").append(recentPassTimes).append(", minPasswdLength=")
				.append(minPasswdLength).append(", allowChangePassword=").append(allowChangePassword)
				.append(", acctEffectDate=").append(acctEffectDate).append(", acctExpireDate=").append(acctExpireDate)
				.append(", multiLoginFlag=").append(multiLoginFlag).append(", tryTimes=").append(tryTimes)
				.append(", lockFlag=").append(lockFlag).append(", notes=").append(notes).append(", chgPasswdAlarmDays=")
				.append(chgPasswdAlarmDays).append(", opType=").append(opType).append(", ext1=").append(ext1)
				.append(", ext2=").append(ext2).append(", ext3=").append(ext3).append(", opLvl=").append(opLvl)
				.append(", bandType=").append(bandType).append("]");
		return builder.toString();
	}
	

}
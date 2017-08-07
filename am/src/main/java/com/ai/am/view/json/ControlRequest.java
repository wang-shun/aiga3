package com.ai.am.view.json;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class ControlRequest {
	 private Long ctrlId;
     private Long parentId;
     private String ifLeaf;
     private String ctrlName;
     private String ctrlType;
     private String ctrlXpath;
     private String ctrlDesc;
     private Long sysId;
     private Long sysSubId;
     private Long funId;
     private Long creatorId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date createTime;
     private Long updateId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     private Date updateTime;

   
    public Long getCtrlId() {
        return this.ctrlId;
    }
    
    public void setCtrlId(Long ctrlId) {
        this.ctrlId = ctrlId;
    }
    
   
    public Long getParentId() {
        return this.parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
   
    public String getIfLeaf() {
        return this.ifLeaf;
    }
    
    public void setIfLeaf(String ifLeaf) {
        this.ifLeaf = ifLeaf;
    }
    
   
    public String getCtrlName() {
        return this.ctrlName;
    }
    
    public void setCtrlName(String ctrlName) {
        this.ctrlName = ctrlName;
    }
    
   
    public String getCtrlType() {
        return this.ctrlType;
    }
    
    public void setCtrlType(String ctrlType) {
        this.ctrlType = ctrlType;
    }
    
   
    public String getCtrlXpath() {
        return this.ctrlXpath;
    }
    
    public void setCtrlXpath(String ctrlXpath) {
        this.ctrlXpath = ctrlXpath;
    }
    
    
    public String getCtrlDesc() {
        return this.ctrlDesc;
    }
    
    public void setCtrlDesc(String ctrlDesc) {
        this.ctrlDesc = ctrlDesc;
    }
    
   
    public Long getSysId() {
        return this.sysId;
    }
    
    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }
    
   
    public Long getSysSubId() {
        return this.sysSubId;
    }
    
    public void setSysSubId(Long sysSubId) {
        this.sysSubId = sysSubId;
    }
    
   
    public Long getFunId() {
        return this.funId;
    }
    
    public void setFunId(Long funId) {
        this.funId = funId;
    }
    
  
    public Long getCreatorId() {
        return this.creatorId;
    }
    
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
  
    public Long getUpdateId() {
        return this.updateId;
    }
    
    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }
    
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "ControlRequest [ctrlId=" + ctrlId + ", parentId=" + parentId + ", ifLeaf=" + ifLeaf + ", ctrlName="
				+ ctrlName + ", ctrlType=" + ctrlType + ", ctrlXpath=" + ctrlXpath + ", ctrlDesc=" + ctrlDesc
				+ ", sysId=" + sysId + ", sysSubId=" + sysSubId + ", funId=" + funId + ", creatorId=" + creatorId
				+ ", createTime=" + createTime + ", updateId=" + updateId + ", updateTime=" + updateTime + "]";
	}



    
}

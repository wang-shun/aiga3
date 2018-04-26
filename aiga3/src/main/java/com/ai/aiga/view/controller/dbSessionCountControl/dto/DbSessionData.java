package com.ai.aiga.view.controller.dbSessionCountControl.dto;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class DbSessionData  implements java.io.Serializable {
     private String systemName;
     private String systemSubdomain;
     private String createTime;
     private Long num;

    public DbSessionData() {
    }


    public DbSessionData(String systemName, String systemSubdomain, String createTime, Long num) {
       this.systemName = systemName;
       this.systemSubdomain = systemSubdomain;
       this.createTime = createTime;
       this.num = num;
    }

    
    public String getSystemName() {
        return this.systemName;
    }
    
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    
    public String getSystemSubdomain() {
        return this.systemSubdomain;
    }
    
    public void setSystemSubdomain(String systemSubdomain) {
        this.systemSubdomain = systemSubdomain;
    }
    
    public Long getNum() {
		return num;
	}


	public void setNum(Long num) {
		this.num = num;
	}


	public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }    
    

}



package com.ai.am.domain;
// Generated 2017-6-29 17:13:48 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ThirdCategory generated by hbm2java
 */
@Entity
@Table(name="THIRD_CATEGORY"
    ,schema="AIAM"
)
public class ThirdCategory  implements java.io.Serializable {


     private long thirdId;
     private String name;
     private Long rootId;
     private Long firstId;
     private long secondId;

    public ThirdCategory() {
    }

	
    public ThirdCategory(long thirdId, String name, long secondId) {
        this.thirdId = thirdId;
        this.name = name;
        this.secondId = secondId;
    }
    public ThirdCategory(long thirdId, String name, Long rootId, Long firstId, long secondId) {
       this.thirdId = thirdId;
       this.name = name;
       this.rootId = rootId;
       this.firstId = firstId;
       this.secondId = secondId;
    }
   
     @Id 
    
    @Column(name="THIRD_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getThirdId() {
        return this.thirdId;
    }
    
    public void setThirdId(long thirdId) {
        this.thirdId = thirdId;
    }
    
    @Column(name="NAME", nullable=false, length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="ROOT_ID", precision=10, scale=0)
    public Long getRootId() {
        return this.rootId;
    }
    
    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }
    
    @Column(name="FIRST_ID", precision=10, scale=0)
    public Long getFirstId() {
        return this.firstId;
    }
    
    public void setFirstId(Long firstId) {
        this.firstId = firstId;
    }
    
    @Column(name="SECOND_ID", nullable=false, precision=10, scale=0)
    public long getSecondId() {
        return this.secondId;
    }
    
    public void setSecondId(long secondId) {
        this.secondId = secondId;
    }




}



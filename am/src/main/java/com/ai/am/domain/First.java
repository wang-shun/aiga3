package com.ai.am.domain;
// Generated 2017-6-29 17:18:05 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * First generated by hbm2java
 */
@Entity
@Table(name="FIRST")
public class First  implements java.io.Serializable {


     private long firstCategory;
     private String name;
     private long quesType;

    public First() {
    }

    public First(long firstCategory, String name, long quesType) {
       this.firstCategory = firstCategory;
       this.name = name;
       this.quesType = quesType;
    }
   
     @Id 
    @Column(name="FIRST_CATEGORY", unique=true, nullable=false, precision=10, scale=0)
    public long getFirstCategory() {
        return this.firstCategory;
    }
    
    public void setFirstCategory(long firstCategory) {
        this.firstCategory = firstCategory;
    }
    
    @Column(name="NAME", nullable=false, length=50)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="QUES_TYPE", nullable=false, precision=10, scale=0)
    public long getQuesType() {
        return this.quesType;
    }
    
    public void setQuesType(long quesType) {
        this.quesType = quesType;
    }


}



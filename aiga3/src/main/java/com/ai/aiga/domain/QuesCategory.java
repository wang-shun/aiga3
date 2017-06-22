package com.ai.aiga.domain;
// Generated 2017-6-14 15:31:57 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QuesCategory generated by hbm2java
 */
@Entity
@Table(name="QUES_CATEGORY")
public class QuesCategory  implements java.io.Serializable {


     private long rootId;
     private String name;

    public QuesCategory() {
    }

	
    public QuesCategory(long rootId) {
        this.rootId = rootId;
    }
    public QuesCategory(long rootId, String name) {
       this.rootId = rootId;
       this.name = name;
    }
   
    @Id 
    @Column(name="ROOT_ID", unique=true, nullable=false, precision=10, scale=0)
    public long getRootId() {
        return this.rootId;
    }
    
    public void setRootId(long rootId) {
        this.rootId = rootId;
    }
    
    @Column(name="NAME", length=10)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

}



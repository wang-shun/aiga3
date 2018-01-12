package com.ai.aiga.domain;
// Generated 2017-7-6 15:24:46 by Hibernate Tools 3.2.2.GA


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

/**
 * AmCoreIndex generated by hbm2java
 */
@Entity
@Table(name="AM_CORE_INDEX_TREE")
public class AmCoreIndexTree  implements java.io.Serializable {

     private long indexId;
     private String indexName;
     private String indexGroup;
     private String schId;
     private char state;
     private Date createDate;
     private Long groupId;
     private String ext1;
     private String ext2;
     private String ext3;
     
    public AmCoreIndexTree() {
    }
    
    public AmCoreIndexTree(long indexId,long groupId) {
    	this.groupId = groupId;
    	this.indexId = indexId;
    }
	
    public AmCoreIndexTree(long indexId, String indexName, String indexGroup, String schId, char state, Date createDate) {
        this.indexId = indexId;
        this.indexName = indexName;
        this.indexGroup = indexGroup;
        this.schId = schId;
        this.state = state;
        this.createDate = createDate;
    }
    public AmCoreIndexTree(long indexId, String indexName, String indexGroup, String schId, char state, Date createDate, String ext1, String ext2, String ext3) {
       this.indexId = indexId;
       this.indexName = indexName;
       this.indexGroup = indexGroup;
       this.schId = schId;
       this.state = state;
       this.createDate = createDate;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
    public AmCoreIndexTree(long indexId, String indexName, String indexGroup,
			String schId, char state, Date createDate, Long groupId, 
			String ext1, String ext2, String ext3) {
		super();
		this.indexId = indexId;
		this.indexName = indexName;
		this.indexGroup = indexGroup;
		this.schId = schId;
		this.state = state;
		this.createDate = createDate;
		this.groupId = groupId;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
	}


	@Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AM_CORE_INDEX$SEQ")
 	@SequenceGenerator(name="AM_CORE_INDEX$SEQ",sequenceName="AM_CORE_INDEX$SEQ",allocationSize=1)   
    @Column(name="INDEX_ID", unique=true, nullable=false, precision=12, scale=0)
    public long getIndexId() {
        return this.indexId;
    }
    
    public void setIndexId(long indexId) {
        this.indexId = indexId;
    }
    
    @Column(name="INDEX_NAME", nullable=false, length=100)
    public String getIndexName() {
        return this.indexName;
    }
    
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    
    @Column(name="INDEX_GROUP", nullable=false, length=50)
    public String getIndexGroup() {
        return this.indexGroup;
    }
    
    public void setIndexGroup(String indexGroup) {
        this.indexGroup = indexGroup;
    }
    
    @Column(name="SCH_ID", nullable=false, length=40)
    public String getSchId() {
        return this.schId;
    }
    
    public void setSchId(String schId) {
        this.schId = schId;
    }
    
    @Column(name="STATE", nullable=false, length=1)
    public char getState() {
        return this.state;
    }
    
    public void setState(char state) {
        this.state = state;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", nullable=false, length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="GROUP_ID", precision=12, scale=0)
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Column(name="EXT_1", length=100)
	public String getExt1() {
		return ext1;
	}
	
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	
	@Column(name="EXT_2", length=100)
	public String getExt2() {
		return ext2;
	}
	
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
	@Column(name="EXT_3", length=100)
	public String getExt3() {
		return ext3;
	}
	
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
    


}



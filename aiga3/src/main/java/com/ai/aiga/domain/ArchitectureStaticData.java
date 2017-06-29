package com.ai.aiga.domain;
// Generated 2017-6-29 18:35:07 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ArchitectureStaticData generated by hbm2java
 */
@Entity
@Table(name="ARCHITECTURE_STATIC_DATA")
public class ArchitectureStaticData  implements java.io.Serializable {


     private long dataId;
     private String codeType;
     private String codeValue;
     private String codeName;
     private String codeDesc;
     private String ext1;
     private String ext2;
     private String ext3;

    public ArchitectureStaticData() {
    }

	
    public ArchitectureStaticData(long dataId) {
        this.dataId = dataId;
    }
    public ArchitectureStaticData(long dataId, String codeType, String codeValue, String codeName, String codeDesc, String ext1, String ext2, String ext3) {
       this.dataId = dataId;
       this.codeType = codeType;
       this.codeValue = codeValue;
       this.codeName = codeName;
       this.codeDesc = codeDesc;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="ARCHITECTURE_STATIC_DATA$SEQ")
  	@SequenceGenerator(name="ARCHITECTURE_STATIC_DATA$SEQ",sequenceName="ARCHITECTURE_STATIC_DATA$SEQ",allocationSize=1)   
    @Column(name="data_id", unique=true, nullable=false, precision=10, scale=0)
    public long getDataId() {
        return this.dataId;
    }
    
    public void setDataId(long dataId) {
        this.dataId = dataId;
    }
    
    @Column(name="code_type")
    public String getCodeType() {
        return this.codeType;
    }
    
    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
    
    @Column(name="code_value")
    public String getCodeValue() {
        return this.codeValue;
    }
    
    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    
    @Column(name="code_name")
    public String getCodeName() {
        return this.codeName;
    }
    
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    
    @Column(name="code_desc")
    public String getCodeDesc() {
        return this.codeDesc;
    }
    
    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
    
    @Column(name="ext1")
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="ext2")
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="ext3")
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}



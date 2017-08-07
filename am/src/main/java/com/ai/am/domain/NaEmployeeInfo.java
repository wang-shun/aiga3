package com.ai.am.domain;
// Generated 2017-4-5 11:02:45 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaEmployeeInfo generated by hbm2java
 */
@Entity
@Table(name="NA_EMPLOYEE_INFO"
    ,schema="AIGA"
)
public class NaEmployeeInfo  implements java.io.Serializable {


     private Long id;
     private String emName;
     private String phoneNum;
     private String email;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaEmployeeInfo() {
    }

	
    public NaEmployeeInfo(Long id) {
        this.id = id;
    }
    public NaEmployeeInfo(Long id, String emName, String phoneNum, String email, String ext1, String ext2, String ext3) {
       this.id = id;
       this.emName = emName;
       this.phoneNum = phoneNum;
       this.email = email;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_EMPLOYEE_INFO$SEQ")
     @SequenceGenerator(name="NA_EMPLOYEE_INFO$SEQ",sequenceName="NA_EMPLOYEE_INFO$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=12, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="EM_NAME", length=20)
    public String getEmName() {
        return this.emName;
    }
    
    public void setEmName(String emName) {
        this.emName = emName;
    }
    
    @Column(name="PHONE_NUM", length=20)
    public String getPhoneNum() {
        return this.phoneNum;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    @Column(name="EMAIL", length=20)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="EXT_1", length=20)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=20)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=20)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }




}



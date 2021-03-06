package com.ai.am.domain;
// Generated 2017-4-19 15:34:55 by Hibernate Tools 3.2.2.GA



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * NaOnlineStaffArrange generated by hbm2java
 */
@Entity
@Table(name="NA_ONLINE_STAFF_ARRANGE"
    ,schema="AIGA"
)
public class NaOnlineStaffArrange  implements java.io.Serializable {


     private Long id;
     private Long whetherNeed;
     private String role;
     private String companyName;
     private String staff;
     private String telephone;
     private String workContent;
     private Long planId;
     private Long type;
     private String ext1;
     private String ext2;
     private String ext3;

    public NaOnlineStaffArrange() {
    }

	
    public NaOnlineStaffArrange(Long id) {
        this.id = id;
    }
    public NaOnlineStaffArrange(Long id, Long whetherNeed, String role, String companyName, String staff, String telephone, String workContent, Long planId, Long type, String ext1, String ext2, String ext3) {
       this.id = id;
       this.whetherNeed = whetherNeed;
       this.role = role;
       this.companyName = companyName;
       this.staff = staff;
       this.telephone = telephone;
       this.workContent = workContent;
       this.planId = planId;
       this.type = type;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_ONLINE_STAFF_ARRANGE$SEQ")
     @SequenceGenerator(name="NA_ONLINE_STAFF_ARRANGE$SEQ",sequenceName="NA_ONLINE_STAFF_ARRANGE$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="WHETHER_NEED", precision=14, scale=0)
    public Long getWhetherNeed() {
        return this.whetherNeed;
    }
    
    public void setWhetherNeed(Long whetherNeed) {
        this.whetherNeed = whetherNeed;
    }
    
    @Column(name="ROLE", length=2000)
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    @Column(name="COMPANY_NAME", length=2000)
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Column(name="STAFF", length=2000)
    public String getStaff() {
        return this.staff;
    }
    
    public void setStaff(String staff) {
        this.staff = staff;
    }
    
    @Column(name="TELEPHONE", length=2000)
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Column(name="WORK_CONTENT", length=2000)
    public String getWorkContent() {
        return this.workContent;
    }
    
    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }
    
    @Column(name="PLAN_ID", precision=14, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="TYPE", precision=14, scale=0)
    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
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



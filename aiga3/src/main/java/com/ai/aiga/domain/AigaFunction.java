package com.ai.aiga.domain;
// Generated 2017-2-20 19:08:55 by Hibernate Tools 3.2.2.GA


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
 * AigaFunction generated by hbm2java
 */
@Entity
@Table(name="AIGA_FUNCTION")
public class AigaFunction  implements java.io.Serializable {


     private long funcId;
     private String funcCode;
     private String name;
     private String notes;
     private long parentId;
     private Byte funcLevel;
     private Short funSeq;
     private String viewname;
     private String dllPath;
     private String funcImg;
     private String funcArg;
     private Character funcType;
     private Byte state;
     private Long doneCode;
     private Date createDate;
     private Date doneDate;
     private Date validDate;
     private Date expireDate;
     private Long opId;
     private Long orgId;

    public AigaFunction() {
    }

	
    public AigaFunction(long funcId, String funcCode, String name, long parentId) {
        this.funcId = funcId;
        this.funcCode = funcCode;
        this.name = name;
        this.parentId = parentId;
    }
    public AigaFunction(long funcId, String funcCode, String name, String notes, long parentId, Byte funcLevel, Short funSeq, String viewname, String dllPath, String funcImg, String funcArg, Character funcType, Byte state, Long doneCode, Date createDate, Date doneDate, Date validDate, Date expireDate, Long opId, Long orgId) {
       this.funcId = funcId;
       this.funcCode = funcCode;
       this.name = name;
       this.notes = notes;
       this.parentId = parentId;
       this.funcLevel = funcLevel;
       this.funSeq = funSeq;
       this.viewname = viewname;
       this.dllPath = dllPath;
       this.funcImg = funcImg;
       this.funcArg = funcArg;
       this.funcType = funcType;
       this.state = state;
       this.doneCode = doneCode;
       this.createDate = createDate;
       this.doneDate = doneDate;
       this.validDate = validDate;
       this.expireDate = expireDate;
       this.opId = opId;
       this.orgId = orgId;
    }
   
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_FUNCTION$SEQ")
    @SequenceGenerator(name="AIGA_FUNCTION$SEQ",sequenceName="AIGA_FUNCTION$SEQ",allocationSize=1)
    @Column(name="FUNC_ID", unique=true, nullable=false, precision=12, scale=0)
    public long getFuncId() {
        return this.funcId;
    }
    
    public void setFuncId(long funcId) {
        this.funcId = funcId;
    }
    
    @Column(name="FUNC_CODE", nullable=false, length=80)
    public String getFuncCode() {
        return this.funcCode;
    }
    
    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }
    
    @Column(name="NAME", nullable=false, length=80)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="NOTES", length=100)
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Column(name="PARENT_ID", nullable=false, precision=12, scale=0)
    public long getParentId() {
        return this.parentId;
    }
    
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
    
    @Column(name="FUNC_LEVEL", precision=2, scale=0)
    public Byte getFuncLevel() {
        return this.funcLevel;
    }
    
    public void setFuncLevel(Byte funcLevel) {
        this.funcLevel = funcLevel;
    }
    
    @Column(name="FUN_SEQ", precision=3, scale=0)
    public Short getFunSeq() {
        return this.funSeq;
    }
    
    public void setFunSeq(Short funSeq) {
        this.funSeq = funSeq;
    }
    
    @Column(name="VIEWNAME", length=1000)
    public String getViewname() {
        return this.viewname;
    }
    
    public void setViewname(String viewname) {
        this.viewname = viewname;
    }
    
    @Column(name="DLL_PATH", length=1000)
    public String getDllPath() {
        return this.dllPath;
    }
    
    public void setDllPath(String dllPath) {
        this.dllPath = dllPath;
    }
    
    @Column(name="FUNC_IMG", length=1000)
    public String getFuncImg() {
        return this.funcImg;
    }
    
    public void setFuncImg(String funcImg) {
        this.funcImg = funcImg;
    }
    
    @Column(name="FUNC_ARG", length=1000)
    public String getFuncArg() {
        return this.funcArg;
    }
    
    public void setFuncArg(String funcArg) {
        this.funcArg = funcArg;
    }
    
    @Column(name="FUNC_TYPE", length=1)
    public Character getFuncType() {
        return this.funcType;
    }
    
    public void setFuncType(Character funcType) {
        this.funcType = funcType;
    }
    
    @Column(name="STATE", precision=2, scale=0)
    public Byte getState() {
        return this.state;
    }
    
    public void setState(Byte state) {
        this.state = state;
    }
    
    @Column(name="DONE_CODE", precision=12, scale=0)
    public Long getDoneCode() {
        return this.doneCode;
    }
    
    public void setDoneCode(Long doneCode) {
        this.doneCode = doneCode;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=7)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DONE_DATE", length=7)
    public Date getDoneDate() {
        return this.doneDate;
    }
    
    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="VALID_DATE", length=7)
    public Date getValidDate() {
        return this.validDate;
    }
    
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIRE_DATE", length=7)
    public Date getExpireDate() {
        return this.expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    @Column(name="OP_ID", precision=12, scale=0)
    public Long getOpId() {
        return this.opId;
    }
    
    public void setOpId(Long opId) {
        this.opId = opId;
    }
    
    @Column(name="ORG_ID", precision=12, scale=0)
    public Long getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }




}



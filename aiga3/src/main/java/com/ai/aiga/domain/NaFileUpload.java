package com.ai.aiga.domain;
// Generated 2017-4-24 15:00:18 by Hibernate Tools 3.2.2.GA



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
 * NaFileUpload generated by hbm2java
 */
@Entity
@Table(name="NA_FILE_UPLOAD"
)
public class NaFileUpload  implements java.io.Serializable {


     private Long id;
     private String fileName;
     private Date createTime;
     private Date lastUploadTime;
     private Long uploadCount;
     private Date downLoadTime;
     private Long planId;
     private String ext1;
     private String ext2;
     private String ext3;
     private Long fileType;
     private Long createId;

    public NaFileUpload() {
    }

	
    public NaFileUpload(Long id) {
        this.id = id;
    }
    public NaFileUpload(String fileName, Date createTime) {
        this.fileName = fileName;
        this.createTime = createTime;

    }
    public NaFileUpload(String fileName, Date createTime,Long fileType,Long planId,Long createId , Long uploadCount) {
        this.fileName = fileName;
        this.createTime = createTime;
        this.fileType=fileType;
        this.planId = planId;
        this.createId = createId;
        this.uploadCount = uploadCount;
    }
    
    public NaFileUpload(Long id, String fileName, Date createTime, Date lastUploadTime, Long uploadCount, Date downLoadTime, Long planId, String ext1, String ext2, String ext3, Long fileType) {
       this.id = id;
       this.fileName = fileName;
       this.createTime = createTime;
       this.lastUploadTime = lastUploadTime;
       this.uploadCount = uploadCount;
       this.downLoadTime = downLoadTime;
       this.planId = planId;
       this.ext1 = ext1;
       this.ext2 = ext2;
       this.ext3 = ext3;
       this.fileType = fileType;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_FILE_UPLOAD$SEQ")
     @SequenceGenerator(name="NA_FILE_UPLOAD$SEQ",sequenceName="NA_FILE_UPLOAD$SEQ",allocationSize=1)
    @Column(name="ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="FILE_NAME", length=100)
    public String getFileName() {
        return this.fileName;
    }
    
    @Column(name="CREATE_ID")
    public Long getCreateId() {
		return createId;
	}


	public void setCreateId(Long createId) {
		this.createId = createId;
	}


	public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_TIME", length=7)
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_UPLOAD_TIME", length=7)
    public Date getLastUploadTime() {
        return this.lastUploadTime;
    }
    
    public void setLastUploadTime(Date lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }
    
    @Column(name="UPLOAD_COUNT", precision=22, scale=0)
    public Long getUploadCount() {
        return this.uploadCount;
    }
    
    public void setUploadCount(Long uploadCount) {
        this.uploadCount = uploadCount;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DOWN_LOAD_TIME", length=7)
    public Date getDownLoadTime() {
        return this.downLoadTime;
    }
    
    public void setDownLoadTime(Date downLoadTime) {
        this.downLoadTime = downLoadTime;
    }
    
    @Column(name="PLAN_ID", precision=22, scale=0)
    public Long getPlanId() {
        return this.planId;
    }
    
    public void setPlanId(Long planId) {
        this.planId = planId;
    }
    
    @Column(name="EXT_1", length=100)
    public String getExt1() {
        return this.ext1;
    }
    
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    
    @Column(name="EXT_2", length=100)
    public String getExt2() {
        return this.ext2;
    }
    
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    
    @Column(name="EXT_3", length=100)
    public String getExt3() {
        return this.ext3;
    }
    
    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }
    
    @Column(name="FILE_TYPE", precision=22, scale=0)
    public Long getFileType() {
        return this.fileType;
    }
    
    public void setFileType(Long fileType) {
        this.fileType = fileType;
    }




}



package com.ai.aiga.domain;

import java.util.Date;

import javax.persistence.*;


/**
 * @author defaultekey
 * @date 2017/3/2
 */
@Entity
@Table(name = "NA_AUTO_COLLECTION")
public class NaAutoCollection {
	   private Long collectId;
	     private String collectName;
	     private Long opId;
	     private Date createDate;
	     private Long caseNum;
	     private String ext1;
	     private String ext2;
	     private String ext3;
	     private Long caseType;
	     private Long repairsId;
	     private Long sysId;

	    public NaAutoCollection() {
	    }

		
	
	     public NaAutoCollection(Long collectId, String collectName, Long opId, Date createDate, Long caseNum,
				String ext1, String ext2, String ext3, Long caseType, Long repairsId) {
			super();
			this.collectId = collectId;
			this.collectName = collectName;
			this.opId = opId;
			this.createDate = createDate;
			this.caseNum = caseNum;
			this.ext1 = ext1;
			this.ext2 = ext2;
			this.ext3 = ext3;
			this.caseType = caseType;
			this.repairsId = repairsId;
		}



		@Id 
	     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_AUTO_COLLECTION$SEQ")
	     @SequenceGenerator(name="NA_AUTO_COLLECTION$SEQ",sequenceName="NA_AUTO_COLLECTION$SEQ",allocationSize=1)
	    @Column(name="COLLECT_ID", precision=22, scale=0)
	    public Long getCollectId() {
	        return this.collectId;
	    }
	    
	    public void setCollectId(Long collectId) {
	        this.collectId = collectId;
	    }
	    @Column(name="SYS_ID", precision=22, scale=0)
	    public Long getSysId() {
			return sysId;
		}



		public void setSysId(Long sysId) {
			this.sysId = sysId;
		}



		@Column(name="COLLECT_NAME", length=2000)
	    public String getCollectName() {
	        return this.collectName;
	    }
	    
	    public void setCollectName(String collectName) {
	        this.collectName = collectName;
	    }
	    
	    @Column(name="OP_ID", precision=12, scale=0)
	    public Long getOpId() {
	        return this.opId;
	    }
	    
	    public void setOpId(Long opId) {
	        this.opId = opId;
	    }

	    @Column(name="CREATE_DATE", length=7)
	    public Date getCreateDate() {
	        return this.createDate;
	    }
	    
	    public void setCreateDate(Date createDate) {
	        this.createDate = createDate;
	    }
	    
	    @Column(name="CASE_NUM", precision=22, scale=0)
	    public Long getCaseNum() {
	        return this.caseNum;
	    }
	    
	    public void setCaseNum(Long caseNum) {
	        this.caseNum = caseNum;
	    }
	    
	    @Column(name="EXT1", length=200)
	    public String getExt1() {
	        return this.ext1;
	    }
	    
	    public void setExt1(String ext1) {
	        this.ext1 = ext1;
	    }
	    
	    @Column(name="EXT2", length=200)
	    public String getExt2() {
	        return this.ext2;
	    }
	    
	    public void setExt2(String ext2) {
	        this.ext2 = ext2;
	    }
	    
	    @Column(name="EXT3", length=200)
	    public String getExt3() {
	        return this.ext3;
	    }
	    
	    public void setExt3(String ext3) {
	        this.ext3 = ext3;
	    }
	    
	    @Column(name="CASE_TYPE", precision=22, scale=0)
	    public Long getCaseType() {
	        return this.caseType;
	    }
	    
	    public void setCaseType(Long caseType) {
	        this.caseType = caseType;
	    }
	    
	    @Column(name="REPAIRS_ID", precision=22, scale=0)
	    public Long getRepairsId() {
	        return this.repairsId;
	    }
	    
	    public void setRepairsId(Long repairsId) {
	        this.repairsId = repairsId;
	    }


}

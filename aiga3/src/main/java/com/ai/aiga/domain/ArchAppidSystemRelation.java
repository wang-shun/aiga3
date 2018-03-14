package com.ai.aiga.domain;
// Generated 2018-1-29 15:02:07 by Hibernate Tools 3.2.2.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ArchSvnDbcp generated by hbm2java
 */
@Entity
@Table(name="ARCH_APPID_SYSTEM_RELATION")
public class ArchAppidSystemRelation implements java.io.Serializable {

    private Long id;
    private Long indexId;
    private String appid;
    private String center;
    private String module;
    private String db;
    private String ext;
    
    public ArchAppidSystemRelation() {
    }

   public ArchAppidSystemRelation(Long id, Long indexId, String appid, String center, String module, String db, String ext) {
	  super();
	  this.id = id;
      this.indexId = indexId;
      this.appid = appid;
      this.center = center;
      this.module = module;
      this.db = db;
      this.ext = ext;
   }
    @Id
    @Column(name="ID", precision=10, scale=0)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
    @Column(name="INDEX_ID", precision=10, scale=0)
	public Long getIndexId() {
		return indexId;
	}
	
	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}
	@Column(name="APPID", length=256)
	public String getAppid() {
		return appid;
	}
	
	public void setAppid(String appid) {
		this.appid = appid;
	}
	@Column(name="CENTER", length=32)
	public String getCenter() {
		return center;
	}
	
	public void setCenter(String center) {
		this.center = center;
	}
	@Column(name="MODULE", length=32)
	public String getModule() {
		return module;
	}
	
	public void setModule(String module) {
		this.module = module;
	}
	@Column(name="DB", length=16)
	public String getDb() {
		return db;
	}
	
	public void setDb(String db) {
		this.db = db;
	}
	@Column(name="EXT", length=256)
	public String getExt() {
		return ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
  
   
}


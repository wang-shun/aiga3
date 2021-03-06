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
@Table(name="ARCH_SVN_DBCP")
public class SrvcallDay  implements java.io.Serializable {

	private String serviceid;
	private double avgduration;
	private long accesstimes;
	private long errortimes;
	private String timeperoid;
	private String servicestatus;
	private String errmsg;
	private long maxduration;
	private long minduration;
	private long sumduration;
	private String statskind;
	private String statscode;
    
    public SrvcallDay() {
    }

   public SrvcallDay(String serviceid, double avgduration, long accesstimes, long errortimes, String timeperoid, String servicestatus, String errmsg, long maxduration, long minduration, long sumduration, String statskind, String statscode) {
	  super();
	  this.serviceid = serviceid;
      this.avgduration = avgduration;
      this.accesstimes = accesstimes;
      this.errortimes = errortimes;
      this.timeperoid = timeperoid;
      this.servicestatus = servicestatus;
      this.errmsg = errmsg;
      this.maxduration = maxduration;
      this.minduration = minduration;
      this.sumduration = sumduration;
      this.statskind = statskind;
      this.statscode = statscode;
   }
   
    @Id
    @Column(name="SERVICEID", length=255)
	public String getServiceid() {
		return serviceid;
	}
	
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	@Column(name="AVGDURATION", precision=10, scale=15)
	public double getAvgduration() {
		return avgduration;
	}
	
	public void setAvgduration(double avgduration) {
		this.avgduration = avgduration;
	}
	@Column(name="ACCESSTIMES", precision=10)
	public long getAccesstimes() {
		return accesstimes;
	}
	
	public void setAccesstimes(long accesstimes) {
		this.accesstimes = accesstimes;
	}
	@Column(name="ERRORTIMES", precision=10)
	public long getErrortimes() {
		return errortimes;
	}
	
	public void setErrortimes(long errortimes) {
		this.errortimes = errortimes;
	}
	@Column(name="TIMEPEROID", length=64)
	public String getTimeperoid() {
		return timeperoid;
	}
	
	public void setTimeperoid(String timeperoid) {
		this.timeperoid = timeperoid;
	}
	@Column(name="SERVICESTATUS", length=1)
	public String getServicestatus() {
		return servicestatus;
	}
	
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
	@Column(name="ERRMSG", length=256)
	public String getErrmsg() {
		return errmsg;
	}
	
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	@Column(name="MAXDURATION", precision=20, scale=0)
	public long getMaxduration() {
		return maxduration;
	}
	
	public void setMaxduration(long maxduration) {
		this.maxduration = maxduration;
	}
	@Column(name="MINDURATION", precision=20, scale=0)
	public long getMinduration() {
		return minduration;
	}
	
	public void setMinduration(long minduration) {
		this.minduration = minduration;
	}
	@Column(name="SUMDURATION", precision=20, scale=0)
	public long getSumduration() {
		return sumduration;
	}
	
	public void setSumduration(long sumduration) {
		this.sumduration = sumduration;
	}
	@Column(name="STATSKIND", length=64)
	public String getStatskind() {
		return statskind;
	}
	
	public void setStatskind(String statskind) {
		this.statskind = statskind;
	}
	@Column(name="STATSCODE", length=64)
	public String getStatscode() {
		return statscode;
	}
	
	public void setStatscode(String statscode) {
		this.statscode = statscode;
	}
  
}



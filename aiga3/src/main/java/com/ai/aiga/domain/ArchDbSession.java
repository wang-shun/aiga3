package com.ai.aiga.domain;


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
@Table(name="ARCH_DB_SESSION")
public class ArchDbSession  implements java.io.Serializable {

	private Date createDate;
	private String key1;
	private String key2;
	private String key3;
	private String key4;
	private String key5;
	private String key6;
	private String key7;
	private String key8;
	private String key9;
	
    public ArchDbSession() {
    }

   public ArchDbSession(Date createDate, String key1, String key2, String key3, String key4, String key5, String key6, String key7, String key8, String key9) {
	  super();
	  this.createDate = createDate;
      this.key1 = key1;
      this.key2 = key2;
      this.key3 = key3;
      this.key4 = key4;
      this.key5 = key5;
      this.key6 = key6;
      this.key7 = key7;
      this.key8 = key8;
      this.key9 = key9;
   }
   
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", nullable=false, length=7)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name="KEY1", length=256)
	public String getKey1() {
		return key1;
	}
	
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	
	@Column(name="KEY2", length=256)
	public String getKey2() {
		return key2;
	}
	
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	
	@Column(name="KEY3", length=256)
	public String getKey3() {
		return key3;
	}
	
	public void setKey3(String key3) {
		this.key3 = key3;
	}
	
	@Column(name="KEY4", length=256)
	public String getKey4() {
		return key4;
	}
	
	public void setKey4(String key4) {
		this.key4 = key4;
	}
	
	@Column(name="KEY5", length=256)
	public String getKey5() {
		return key5;
	}
	
	public void setKey5(String key5) {
		this.key5 = key5;
	}
	
	@Column(name="KEY6", length=256)
	public String getKey6() {
		return key6;
	}
	
	public void setKey6(String key6) {
		this.key6 = key6;
	}
	
	@Column(name="KEY7", length=256)
	public String getKey7() {
		return key7;
	}
	
	public void setKey7(String key7) {
		this.key7 = key7;
	}
	
	@Column(name="KEY8", length=256)
	public String getKey8() {
		return key8;
	}
	
	public void setKey8(String key8) {
		this.key8 = key8;
	}
	
	@Column(name="KEY9", length=256)
	public String getKey9() {
		return key9;
	}
	
	public void setKey9(String key9) {
		this.key9 = key9;
	}
   
}



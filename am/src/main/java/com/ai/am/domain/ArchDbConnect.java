package com.ai.am.domain;
// Generated 2017-7-3 14:53:20 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ArchDbConnect generated by hbm2java
 */
@Entity
@Table(name="ARCH_DB_CONNECT")
public class ArchDbConnect  implements java.io.Serializable {

    private Long indexId;
    private String settMonth;
    private String key1;
    private String key2;
    private String key3;
    private String resultValue;
    
    public ArchDbConnect(){
    }

	public ArchDbConnect(Long indexId, String settMonth, String key1, String key2,
			String key3, String resultValue) {
		super();
		this.indexId = indexId;
		this.settMonth = settMonth;
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		this.resultValue = resultValue;
	}
	
	@Id 
   @Column(name="INDEX_ID", precision=12, scale=0)
   public Long getIndexId() {
       return this.indexId;
   }
   
   public void setIndexId(Long indexId) {
       this.indexId = indexId;
   }

   @Column(name="SETT_MONTH", length=6)
   public String getSettMonth() {
       return this.settMonth;
   }
   
   public void setSettMonth(String settMonth) {
       this.settMonth = settMonth;
   }

   @Column(name="KEY_1", length=100)
   public String getKey1() {
       return this.key1;
   }
   
   public void setKey1(String key1) {
       this.key1 = key1;
   }

   @Column(name="KEY_2", length=100)
   public String getKey2() {
       return this.key2;
   }
   
   public void setKey2(String key2) {
       this.key2 = key2;
   }

   @Column(name="KEY_3", length=100)
   public String getKey3() {
       return this.key3;
   }
   
   public void setKey3(String key3) {
       this.key3 = key3;
   }

   @Column(name="RESULT_VALUE", length=100)
   public String getResultValue() {
       return this.resultValue;
   }
   
   public void setResultValue(String resultValue) {
       this.resultValue = resultValue;
   }

}



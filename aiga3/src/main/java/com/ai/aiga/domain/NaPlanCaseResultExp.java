package com.ai.aiga.domain;
// Generated 2017-4-10 14:38:12 by Hibernate Tools 3.2.2.GA


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
 * NaPlanCaseResultExp generated by hbm2java
 */
@Entity
@Table(name="NA_PLAN_CASE_RESULT_EXP"
)
public class NaPlanCaseResultExp  implements java.io.Serializable {


     private Long resultId;
     private Long subTaskId;
     private Long interId;
     private String infcode;
     private String infname;
     private String ver;
     private Date exdate;
     private Date exstarttime;
     private Date exendtime;
     private Integer durtime;
     private String isnew;
     private String curnum;
     private String scene;
     private Short testtimes;
     private String rtmin;
     private String rtavg;
     private String rtmax;
     private String stdDeviation;
     private String rt90Percent;
     private String lrpass;
     private String lrfail;
     private String lrstop;
     private String tpsmin;
     private String tpsavg;
     private String tpsmax;
     private String throughput;
     private String cpuusagemax;
     private String memusagemax;
     private String functionextime;
     private String sqlextime;
     private String conclusion;
     private String failreason;
     private String bfproddata;
     private String afproddata;
     private String ismatching;
     private String infcallvolumes;
     private String prodstd;
     private String accstd;
     private String testevn;
     private String remark;
     private String onlinedemand;
     private String onlinedemandname;
     private Date importtime;
     private String ext1;
     private String ext2;

    public NaPlanCaseResultExp() {
    }

	
    public NaPlanCaseResultExp(Long resultId) {
        this.resultId = resultId;
    }
    public NaPlanCaseResultExp(Long resultId, Long subTaskId, Long interId, String infcode, String infname, String ver, Date exdate, Date exstarttime, Date exendtime, Integer durtime, String isnew, String curnum, String scene, Short testtimes, String rtmin, String rtavg, String rtmax, String stdDeviation, String rt90Percent, String lrpass, String lrfail, String lrstop, String tpsmin, String tpsavg, String tpsmax, String throughput, String cpuusagemax, String memusagemax, String functionextime, String sqlextime, String conclusion, String failreason, String bfproddata, String afproddata, String ismatching, String infcallvolumes, String prodstd, String accstd, String testevn, String remark, String onlinedemand, String onlinedemandname, Date importtime, String ext1, String ext2) {
       this.resultId = resultId;
       this.subTaskId = subTaskId;
       this.interId = interId;
       this.infcode = infcode;
       this.infname = infname;
       this.ver = ver;
       this.exdate = exdate;
       this.exstarttime = exstarttime;
       this.exendtime = exendtime;
       this.durtime = durtime;
       this.isnew = isnew;
       this.curnum = curnum;
       this.scene = scene;
       this.testtimes = testtimes;
       this.rtmin = rtmin;
       this.rtavg = rtavg;
       this.rtmax = rtmax;
       this.stdDeviation = stdDeviation;
       this.rt90Percent = rt90Percent;
       this.lrpass = lrpass;
       this.lrfail = lrfail;
       this.lrstop = lrstop;
       this.tpsmin = tpsmin;
       this.tpsavg = tpsavg;
       this.tpsmax = tpsmax;
       this.throughput = throughput;
       this.cpuusagemax = cpuusagemax;
       this.memusagemax = memusagemax;
       this.functionextime = functionextime;
       this.sqlextime = sqlextime;
       this.conclusion = conclusion;
       this.failreason = failreason;
       this.bfproddata = bfproddata;
       this.afproddata = afproddata;
       this.ismatching = ismatching;
       this.infcallvolumes = infcallvolumes;
       this.prodstd = prodstd;
       this.accstd = accstd;
       this.testevn = testevn;
       this.remark = remark;
       this.onlinedemand = onlinedemand;
       this.onlinedemandname = onlinedemandname;
       this.importtime = importtime;
       this.ext1 = ext1;
       this.ext2 = ext2;
    }
   
     @Id 
     @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="NA_PLAN_CASE_RESULT_EXP$SEQ")
     @SequenceGenerator(name="NA_PLAN_CASE_RESULT_EXP$SEQ",sequenceName="NA_PLAN_CASE_RESULT_EXP$SEQ",allocationSize=1)
    @Column(name="RESULT_ID", unique=true, nullable=false, precision=22, scale=0)
    public Long getResultId() {
        return this.resultId;
    }
    
    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }
    
    @Column(name="SUB_TASK_ID", precision=22, scale=0)
    public Long getSubTaskId() {
        return this.subTaskId;
    }
    
    public void setSubTaskId(Long subTaskId) {
        this.subTaskId = subTaskId;
    }
    
    @Column(name="INTER_ID", precision=22, scale=0)
    public Long getInterId() {
        return this.interId;
    }
    
    public void setInterId(Long interId) {
        this.interId = interId;
    }
    
    @Column(name="INFCODE", length=256)
    public String getInfcode() {
        return this.infcode;
    }
    
    public void setInfcode(String infcode) {
        this.infcode = infcode;
    }
    
    @Column(name="INFNAME", length=256)
    public String getInfname() {
        return this.infname;
    }
    
    public void setInfname(String infname) {
        this.infname = infname;
    }
    
    @Column(name="VER", length=256)
    public String getVer() {
        return this.ver;
    }
    
    public void setVer(String ver) {
        this.ver = ver;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXDATE", length=7)
    public Date getExdate() {
        return this.exdate;
    }
    
    public void setExdate(Date exdate) {
        this.exdate = exdate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXSTARTTIME", length=7)
    public Date getExstarttime() {
        return this.exstarttime;
    }
    
    public void setExstarttime(Date exstarttime) {
        this.exstarttime = exstarttime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXENDTIME", length=7)
    public Date getExendtime() {
        return this.exendtime;
    }
    
    public void setExendtime(Date exendtime) {
        this.exendtime = exendtime;
    }
    
    @Column(name="DURTIME", precision=8, scale=0)
    public Integer getDurtime() {
        return this.durtime;
    }
    
    public void setDurtime(Integer durtime) {
        this.durtime = durtime;
    }
    
    @Column(name="ISNEW", length=100)
    public String getIsnew() {
        return this.isnew;
    }
    
    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }
    
    @Column(name="CURNUM", length=100)
    public String getCurnum() {
        return this.curnum;
    }
    
    public void setCurnum(String curnum) {
        this.curnum = curnum;
    }
    
    @Column(name="SCENE", length=256)
    public String getScene() {
        return this.scene;
    }
    
    public void setScene(String scene) {
        this.scene = scene;
    }
    
    @Column(name="TESTTIMES", precision=4, scale=0)
    public Short getTesttimes() {
        return this.testtimes;
    }
    
    public void setTesttimes(Short testtimes) {
        this.testtimes = testtimes;
    }
    
    @Column(name="RTMIN", length=100)
    public String getRtmin() {
        return this.rtmin;
    }
    
    public void setRtmin(String rtmin) {
        this.rtmin = rtmin;
    }
    
    @Column(name="RTAVG", length=100)
    public String getRtavg() {
        return this.rtavg;
    }
    
    public void setRtavg(String rtavg) {
        this.rtavg = rtavg;
    }
    
    @Column(name="RTMAX", length=100)
    public String getRtmax() {
        return this.rtmax;
    }
    
    public void setRtmax(String rtmax) {
        this.rtmax = rtmax;
    }
    
    @Column(name="STD_DEVIATION", length=100)
    public String getStdDeviation() {
        return this.stdDeviation;
    }
    
    public void setStdDeviation(String stdDeviation) {
        this.stdDeviation = stdDeviation;
    }
    
    @Column(name="RT90_PERCENT", length=100)
    public String getRt90Percent() {
        return this.rt90Percent;
    }
    
    public void setRt90Percent(String rt90Percent) {
        this.rt90Percent = rt90Percent;
    }
    
    @Column(name="LRPASS", length=100)
    public String getLrpass() {
        return this.lrpass;
    }
    
    public void setLrpass(String lrpass) {
        this.lrpass = lrpass;
    }
    
    @Column(name="LRFAIL", length=100)
    public String getLrfail() {
        return this.lrfail;
    }
    
    public void setLrfail(String lrfail) {
        this.lrfail = lrfail;
    }
    
    @Column(name="LRSTOP", length=100)
    public String getLrstop() {
        return this.lrstop;
    }
    
    public void setLrstop(String lrstop) {
        this.lrstop = lrstop;
    }
    
    @Column(name="TPSMIN", length=100)
    public String getTpsmin() {
        return this.tpsmin;
    }
    
    public void setTpsmin(String tpsmin) {
        this.tpsmin = tpsmin;
    }
    
    @Column(name="TPSAVG", length=100)
    public String getTpsavg() {
        return this.tpsavg;
    }
    
    public void setTpsavg(String tpsavg) {
        this.tpsavg = tpsavg;
    }
    
    @Column(name="TPSMAX", length=100)
    public String getTpsmax() {
        return this.tpsmax;
    }
    
    public void setTpsmax(String tpsmax) {
        this.tpsmax = tpsmax;
    }
    
    @Column(name="THROUGHPUT", length=100)
    public String getThroughput() {
        return this.throughput;
    }
    
    public void setThroughput(String throughput) {
        this.throughput = throughput;
    }
    
    @Column(name="CPUUSAGEMAX", length=100)
    public String getCpuusagemax() {
        return this.cpuusagemax;
    }
    
    public void setCpuusagemax(String cpuusagemax) {
        this.cpuusagemax = cpuusagemax;
    }
    
    @Column(name="MEMUSAGEMAX", length=100)
    public String getMemusagemax() {
        return this.memusagemax;
    }
    
    public void setMemusagemax(String memusagemax) {
        this.memusagemax = memusagemax;
    }
    
    @Column(name="FUNCTIONEXTIME", length=100)
    public String getFunctionextime() {
        return this.functionextime;
    }
    
    public void setFunctionextime(String functionextime) {
        this.functionextime = functionextime;
    }
    
    @Column(name="SQLEXTIME", length=100)
    public String getSqlextime() {
        return this.sqlextime;
    }
    
    public void setSqlextime(String sqlextime) {
        this.sqlextime = sqlextime;
    }
    
    @Column(name="CONCLUSION", length=100)
    public String getConclusion() {
        return this.conclusion;
    }
    
    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
    
    @Column(name="FAILREASON", length=256)
    public String getFailreason() {
        return this.failreason;
    }
    
    public void setFailreason(String failreason) {
        this.failreason = failreason;
    }
    
    @Column(name="BFPRODDATA", length=100)
    public String getBfproddata() {
        return this.bfproddata;
    }
    
    public void setBfproddata(String bfproddata) {
        this.bfproddata = bfproddata;
    }
    
    @Column(name="AFPRODDATA", length=100)
    public String getAfproddata() {
        return this.afproddata;
    }
    
    public void setAfproddata(String afproddata) {
        this.afproddata = afproddata;
    }
    
    @Column(name="ISMATCHING", length=100)
    public String getIsmatching() {
        return this.ismatching;
    }
    
    public void setIsmatching(String ismatching) {
        this.ismatching = ismatching;
    }
    
    @Column(name="INFCALLVOLUMES", length=100)
    public String getInfcallvolumes() {
        return this.infcallvolumes;
    }
    
    public void setInfcallvolumes(String infcallvolumes) {
        this.infcallvolumes = infcallvolumes;
    }
    
    @Column(name="PRODSTD", length=100)
    public String getProdstd() {
        return this.prodstd;
    }
    
    public void setProdstd(String prodstd) {
        this.prodstd = prodstd;
    }
    
    @Column(name="ACCSTD", length=100)
    public String getAccstd() {
        return this.accstd;
    }
    
    public void setAccstd(String accstd) {
        this.accstd = accstd;
    }
    
    @Column(name="TESTEVN", length=20)
    public String getTestevn() {
        return this.testevn;
    }
    
    public void setTestevn(String testevn) {
        this.testevn = testevn;
    }
    
    @Column(name="REMARK", length=2048)
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="ONLINEDEMAND", length=20)
    public String getOnlinedemand() {
        return this.onlinedemand;
    }
    
    public void setOnlinedemand(String onlinedemand) {
        this.onlinedemand = onlinedemand;
    }
    
    @Column(name="ONLINEDEMANDNAME", length=100)
    public String getOnlinedemandname() {
        return this.onlinedemandname;
    }
    
    public void setOnlinedemandname(String onlinedemandname) {
        this.onlinedemandname = onlinedemandname;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="IMPORTTIME", length=7)
    public Date getImporttime() {
        return this.importtime;
    }
    
    public void setImporttime(Date importtime) {
        this.importtime = importtime;
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




}



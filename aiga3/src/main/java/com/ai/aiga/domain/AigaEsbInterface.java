package com.ai.aiga.domain;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * @author defaultekey
 * @date 2017/4/27
 */
@Entity
@Table(name = "AIGA_ESB_INTERFACE")
public class AigaEsbInterface {
    private Long esbId;
    private String esbName;
    private String inputSoap;
    private String outputSoap;
    private Long esbVersion;
    private String serVersion;
    private Date updateTime;
    private String subSystem;
    private String esbDescript;
    private String updatePerson;
    private String updatePersonId;
    private String updateStatus;
    private String updateSoap;
    private String operateStatus;
    private Date createTime;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AIGA_ESB_INTERFACE$SEQ")
    @SequenceGenerator(name="AIGA_ESB_INTERFACE$SEQ",sequenceName="AIGA_ESB_INTERFACE$SEQ",allocationSize=1)
    @Column(name = "ESB_ID")
    public Long getEsbId() {
        return esbId;
    }

    public void setEsbId(Long esbId) {
        this.esbId = esbId;
    }

    @Basic
    @Column(name = "ESB_NAME")
    public String getEsbName() {
        return esbName;
    }

    public void setEsbName(String esbName) {
        this.esbName = esbName;
    }

    @Basic
    @Column(name = "INPUT_SOAP")
    public String getInputSoap() {
        return inputSoap;
    }

    public void setInputSoap(String inputSoap) {
        this.inputSoap = inputSoap;
    }

    @Basic
    @Column(name = "OUTPUT_SOAP")
    public String getOutputSoap() {
        return outputSoap;
    }

    public void setOutputSoap(String outputSoap) {
        this.outputSoap = outputSoap;
    }

    @Basic
    @Column(name = "ESB_VERSION")
    public Long getEsbVersion() {
        return esbVersion;
    }

    public void setEsbVersion(Long esbVersion) {
        this.esbVersion = esbVersion;
    }

    @Basic
    @Column(name = "SER_VERSION")
    public String getSerVersion() {
        return serVersion;
    }

    public void setSerVersion(String serVersion) {
        this.serVersion = serVersion;
    }

    @Basic
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "SUB_SYSTEM")
    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    @Basic
    @Column(name = "ESB_DESCRIPT")
    public String getEsbDescript() {
        return esbDescript;
    }

    public void setEsbDescript(String esbDescript) {
        this.esbDescript = esbDescript;
    }

    @Basic
    @Column(name = "UPDATE_PERSON")
    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    @Basic
    @Column(name = "UPDATE_PERSON_ID")
    public String getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(String updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    @Basic
    @Column(name = "UPDATE_STATUS")
    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    @Basic
    @Column(name = "UPDATE_SOAP")
    public String getUpdateSoap() {
        return updateSoap;
    }

    public void setUpdateSoap(String updateSoap) {
        this.updateSoap = updateSoap;
    }

    @Basic
    @Column(name = "OPERATE_STATUS")
    public String getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(String operateStatus) {
        this.operateStatus = operateStatus;
    }

    @Basic
    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AigaEsbInterface that = (AigaEsbInterface) o;

        if (esbId != null ? !esbId.equals(that.esbId) : that.esbId != null) return false;
        if (esbName != null ? !esbName.equals(that.esbName) : that.esbName != null) return false;
        if (inputSoap != null ? !inputSoap.equals(that.inputSoap) : that.inputSoap != null) return false;
        if (outputSoap != null ? !outputSoap.equals(that.outputSoap) : that.outputSoap != null) return false;
        if (esbVersion != null ? !esbVersion.equals(that.esbVersion) : that.esbVersion != null) return false;
        if (serVersion != null ? !serVersion.equals(that.serVersion) : that.serVersion != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (subSystem != null ? !subSystem.equals(that.subSystem) : that.subSystem != null) return false;
        if (esbDescript != null ? !esbDescript.equals(that.esbDescript) : that.esbDescript != null) return false;
        if (updatePerson != null ? !updatePerson.equals(that.updatePerson) : that.updatePerson != null) return false;
        if (updatePersonId != null ? !updatePersonId.equals(that.updatePersonId) : that.updatePersonId != null)
            return false;
        if (updateStatus != null ? !updateStatus.equals(that.updateStatus) : that.updateStatus != null) return false;
        if (updateSoap != null ? !updateSoap.equals(that.updateSoap) : that.updateSoap != null) return false;
        if (operateStatus != null ? !operateStatus.equals(that.operateStatus) : that.operateStatus != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = esbId != null ? esbId.hashCode() : 0;
        result = 31 * result + (esbName != null ? esbName.hashCode() : 0);
        result = 31 * result + (inputSoap != null ? inputSoap.hashCode() : 0);
        result = 31 * result + (outputSoap != null ? outputSoap.hashCode() : 0);
        result = 31 * result + (esbVersion != null ? esbVersion.hashCode() : 0);
        result = 31 * result + (serVersion != null ? serVersion.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (subSystem != null ? subSystem.hashCode() : 0);
        result = 31 * result + (esbDescript != null ? esbDescript.hashCode() : 0);
        result = 31 * result + (updatePerson != null ? updatePerson.hashCode() : 0);
        result = 31 * result + (updatePersonId != null ? updatePersonId.hashCode() : 0);
        result = 31 * result + (updateStatus != null ? updateStatus.hashCode() : 0);
        result = 31 * result + (updateSoap != null ? updateSoap.hashCode() : 0);
        result = 31 * result + (operateStatus != null ? operateStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}

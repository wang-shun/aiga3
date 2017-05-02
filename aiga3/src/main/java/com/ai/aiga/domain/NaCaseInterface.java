package com.ai.aiga.domain;

import javax.persistence.*;

/**
 * @author defaultekey
 * @date 2017/4/27
 */
@Entity
@Table(name = "NA_CASE_INTERFACE")
public class NaCaseInterface {
    private Long interfaceId;
    private Long caseId;
    private Long interfaceType;
    private String address;
    private Long messageType;
    private String validParam;
    private String messageName;
    private Long messageId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NA_CASE_INTERFACE$SEQ")
    @SequenceGenerator(name = "NA_CASE_INTERFACE$SEQ", sequenceName = "NA_CASE_INTERFACE$SEQ", allocationSize = 1)
    @Column(name = "INTERFACE_ID")
    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    @Basic
    @Column(name = "CASE_ID")
    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    @Basic
    @Column(name = "INTERFACE_TYPE")
    public Long getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Long interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "MESSAGE_TYPE")
    public Long getMessageType() {
        return messageType;
    }

    public void setMessageType(Long messageType) {
        this.messageType = messageType;
    }

    @Basic
    @Column(name = "VALID_PARAM")
    public String getValidParam() {
        return validParam;
    }

    public void setValidParam(String validParam) {
        this.validParam = validParam;
    }

    @Basic
    @Column(name = "MESSAGE_NAME")
    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    @Basic
    @Column(name = "MESSAGE_ID")
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NaCaseInterface that = (NaCaseInterface) o;

        if (interfaceId != null ? !interfaceId.equals(that.interfaceId) : that.interfaceId != null) return false;
        if (caseId != null ? !caseId.equals(that.caseId) : that.caseId != null) return false;
        if (interfaceType != null ? !interfaceType.equals(that.interfaceType) : that.interfaceType != null)
            return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (messageType != null ? !messageType.equals(that.messageType) : that.messageType != null) return false;
        if (validParam != null ? !validParam.equals(that.validParam) : that.validParam != null) return false;
        if (messageName != null ? !messageName.equals(that.messageName) : that.messageName != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = interfaceId != null ? interfaceId.hashCode() : 0;
        result = 31 * result + (caseId != null ? caseId.hashCode() : 0);
        result = 31 * result + (interfaceType != null ? interfaceType.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (validParam != null ? validParam.hashCode() : 0);
        result = 31 * result + (messageName != null ? messageName.hashCode() : 0);
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        return result;
    }
}

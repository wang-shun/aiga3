
package com.huawei.msp.mmap.server.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PlanValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accessoryInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="bodyFiles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callBackFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="callBackHour" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="contProcName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contProcParam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dayOfMonth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dayOfWeek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="defaultNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="mediaPlanId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monthOfYear" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mspId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planCaption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planTimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processRate" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="recProcName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recProcParam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="receiverType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportProcName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportProcParam" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sendDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="spaceDay" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="spaceHour" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="spaceMinute" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="staffNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taskRecordFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="templateText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="templateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeUnlimited" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanValue", propOrder = {
    "accessoryInfo",
    "beginDate",
    "bodyFiles",
    "callBackFlag",
    "callBackHour",
    "contProcName",
    "contProcParam",
    "contentFormat",
    "contentInfo",
    "contentType",
    "dataSource",
    "dayOfMonth",
    "dayOfWeek",
    "defaultNo",
    "endDate",
    "mediaPlanId",
    "mediaType",
    "monthOfYear",
    "mspId",
    "planCaption",
    "planName",
    "planTimeType",
    "planType",
    "priority",
    "processRate",
    "recProcName",
    "recProcParam",
    "receiverInfo",
    "receiverType",
    "reportProcName",
    "reportProcParam",
    "sendDateTime",
    "spaceDay",
    "spaceHour",
    "spaceMinute",
    "staffNo",
    "status",
    "taskRecordFlag",
    "templateText",
    "templateType",
    "timeUnlimited",
    "updateTime"
})
public class PlanValue {

    @XmlElementRef(name = "accessoryInfo", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> accessoryInfo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginDate;
    @XmlElementRef(name = "bodyFiles", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> bodyFiles;
    @XmlElementRef(name = "callBackFlag", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> callBackFlag;
    protected Long callBackHour;
    @XmlElementRef(name = "contProcName", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> contProcName;
    @XmlElementRef(name = "contProcParam", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> contProcParam;
    @XmlElementRef(name = "contentFormat", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> contentFormat;
    @XmlElementRef(name = "contentInfo", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> contentInfo;
    @XmlElementRef(name = "contentType", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> contentType;
    @XmlElementRef(name = "dataSource", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> dataSource;
    @XmlElementRef(name = "dayOfMonth", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> dayOfMonth;
    @XmlElementRef(name = "dayOfWeek", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> dayOfWeek;
    @XmlElementRef(name = "defaultNo", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> defaultNo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElementRef(name = "mediaPlanId", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> mediaPlanId;
    @XmlElementRef(name = "mediaType", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> mediaType;
    @XmlElementRef(name = "monthOfYear", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> monthOfYear;
    @XmlElementRef(name = "mspId", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> mspId;
    @XmlElementRef(name = "planCaption", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> planCaption;
    @XmlElementRef(name = "planName", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> planName;
    @XmlElementRef(name = "planTimeType", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> planTimeType;
    @XmlElementRef(name = "planType", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> planType;
    @XmlElementRef(name = "priority", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> priority;
    protected Long processRate;
    @XmlElementRef(name = "recProcName", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> recProcName;
    @XmlElementRef(name = "recProcParam", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> recProcParam;
    @XmlElementRef(name = "receiverInfo", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> receiverInfo;
    @XmlElementRef(name = "receiverType", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> receiverType;
    @XmlElementRef(name = "reportProcName", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> reportProcName;
    @XmlElementRef(name = "reportProcParam", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> reportProcParam;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sendDateTime;
    protected Long spaceDay;
    protected Long spaceHour;
    protected Long spaceMinute;
    @XmlElementRef(name = "staffNo", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> staffNo;
    @XmlElementRef(name = "status", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> status;
    @XmlElementRef(name = "taskRecordFlag", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> taskRecordFlag;
    @XmlElementRef(name = "templateText", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> templateText;
    @XmlElementRef(name = "templateType", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> templateType;
    @XmlElementRef(name = "timeUnlimited", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> timeUnlimited;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateTime;

    /**
     * Gets the value of the accessoryInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAccessoryInfo() {
        return accessoryInfo;
    }

    /**
     * Sets the value of the accessoryInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAccessoryInfo(JAXBElement<String> value) {
        this.accessoryInfo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the beginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    /**
     * Sets the value of the beginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

    /**
     * Gets the value of the bodyFiles property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBodyFiles() {
        return bodyFiles;
    }

    /**
     * Sets the value of the bodyFiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBodyFiles(JAXBElement<String> value) {
        this.bodyFiles = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the callBackFlag property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCallBackFlag() {
        return callBackFlag;
    }

    /**
     * Sets the value of the callBackFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCallBackFlag(JAXBElement<String> value) {
        this.callBackFlag = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the callBackHour property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCallBackHour() {
        return callBackHour;
    }

    /**
     * Sets the value of the callBackHour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCallBackHour(Long value) {
        this.callBackHour = value;
    }

    /**
     * Gets the value of the contProcName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContProcName() {
        return contProcName;
    }

    /**
     * Sets the value of the contProcName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContProcName(JAXBElement<String> value) {
        this.contProcName = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the contProcParam property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContProcParam() {
        return contProcParam;
    }

    /**
     * Sets the value of the contProcParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContProcParam(JAXBElement<String> value) {
        this.contProcParam = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the contentFormat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContentFormat() {
        return contentFormat;
    }

    /**
     * Sets the value of the contentFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContentFormat(JAXBElement<String> value) {
        this.contentFormat = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the contentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContentInfo() {
        return contentInfo;
    }

    /**
     * Sets the value of the contentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContentInfo(JAXBElement<String> value) {
        this.contentInfo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContentType(JAXBElement<String> value) {
        this.contentType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dataSource property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDataSource() {
        return dataSource;
    }

    /**
     * Sets the value of the dataSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDataSource(JAXBElement<String> value) {
        this.dataSource = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dayOfMonth property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Sets the value of the dayOfMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDayOfMonth(JAXBElement<String> value) {
        this.dayOfMonth = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the dayOfWeek property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the value of the dayOfWeek property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDayOfWeek(JAXBElement<String> value) {
        this.dayOfWeek = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the defaultNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDefaultNo() {
        return defaultNo;
    }

    /**
     * Sets the value of the defaultNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDefaultNo(JAXBElement<String> value) {
        this.defaultNo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the mediaPlanId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMediaPlanId() {
        return mediaPlanId;
    }

    /**
     * Sets the value of the mediaPlanId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMediaPlanId(JAXBElement<String> value) {
        this.mediaPlanId = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the mediaType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMediaType() {
        return mediaType;
    }

    /**
     * Sets the value of the mediaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMediaType(JAXBElement<String> value) {
        this.mediaType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the monthOfYear property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMonthOfYear() {
        return monthOfYear;
    }

    /**
     * Sets the value of the monthOfYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMonthOfYear(JAXBElement<String> value) {
        this.monthOfYear = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the mspId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMspId() {
        return mspId;
    }

    /**
     * Sets the value of the mspId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMspId(JAXBElement<String> value) {
        this.mspId = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the planCaption property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPlanCaption() {
        return planCaption;
    }

    /**
     * Sets the value of the planCaption property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPlanCaption(JAXBElement<String> value) {
        this.planCaption = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the planName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPlanName() {
        return planName;
    }

    /**
     * Sets the value of the planName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPlanName(JAXBElement<String> value) {
        this.planName = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the planTimeType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPlanTimeType() {
        return planTimeType;
    }

    /**
     * Sets the value of the planTimeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPlanTimeType(JAXBElement<String> value) {
        this.planTimeType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the planType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPlanType() {
        return planType;
    }

    /**
     * Sets the value of the planType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPlanType(JAXBElement<String> value) {
        this.planType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPriority(JAXBElement<String> value) {
        this.priority = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the processRate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getProcessRate() {
        return processRate;
    }

    /**
     * Sets the value of the processRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setProcessRate(Long value) {
        this.processRate = value;
    }

    /**
     * Gets the value of the recProcName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecProcName() {
        return recProcName;
    }

    /**
     * Sets the value of the recProcName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecProcName(JAXBElement<String> value) {
        this.recProcName = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the recProcParam property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecProcParam() {
        return recProcParam;
    }

    /**
     * Sets the value of the recProcParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecProcParam(JAXBElement<String> value) {
        this.recProcParam = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the receiverInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReceiverInfo() {
        return receiverInfo;
    }

    /**
     * Sets the value of the receiverInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReceiverInfo(JAXBElement<String> value) {
        this.receiverInfo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the receiverType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReceiverType() {
        return receiverType;
    }

    /**
     * Sets the value of the receiverType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReceiverType(JAXBElement<String> value) {
        this.receiverType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the reportProcName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReportProcName() {
        return reportProcName;
    }

    /**
     * Sets the value of the reportProcName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReportProcName(JAXBElement<String> value) {
        this.reportProcName = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the reportProcParam property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReportProcParam() {
        return reportProcParam;
    }

    /**
     * Sets the value of the reportProcParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReportProcParam(JAXBElement<String> value) {
        this.reportProcParam = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the sendDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSendDateTime() {
        return sendDateTime;
    }

    /**
     * Sets the value of the sendDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSendDateTime(XMLGregorianCalendar value) {
        this.sendDateTime = value;
    }

    /**
     * Gets the value of the spaceDay property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSpaceDay() {
        return spaceDay;
    }

    /**
     * Sets the value of the spaceDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSpaceDay(Long value) {
        this.spaceDay = value;
    }

    /**
     * Gets the value of the spaceHour property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSpaceHour() {
        return spaceHour;
    }

    /**
     * Sets the value of the spaceHour property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSpaceHour(Long value) {
        this.spaceHour = value;
    }

    /**
     * Gets the value of the spaceMinute property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSpaceMinute() {
        return spaceMinute;
    }

    /**
     * Sets the value of the spaceMinute property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSpaceMinute(Long value) {
        this.spaceMinute = value;
    }

    /**
     * Gets the value of the staffNo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStaffNo() {
        return staffNo;
    }

    /**
     * Sets the value of the staffNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStaffNo(JAXBElement<String> value) {
        this.staffNo = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatus(JAXBElement<String> value) {
        this.status = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the taskRecordFlag property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTaskRecordFlag() {
        return taskRecordFlag;
    }

    /**
     * Sets the value of the taskRecordFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTaskRecordFlag(JAXBElement<String> value) {
        this.taskRecordFlag = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the templateText property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTemplateText() {
        return templateText;
    }

    /**
     * Sets the value of the templateText property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTemplateText(JAXBElement<String> value) {
        this.templateText = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the templateType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTemplateType() {
        return templateType;
    }

    /**
     * Sets the value of the templateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTemplateType(JAXBElement<String> value) {
        this.templateType = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the timeUnlimited property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTimeUnlimited() {
        return timeUnlimited;
    }

    /**
     * Sets the value of the timeUnlimited property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTimeUnlimited(JAXBElement<String> value) {
        this.timeUnlimited = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateTime(XMLGregorianCalendar value) {
        this.updateTime = value;
    }

}

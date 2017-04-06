
package com.huawei.msp.mmap.server.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryPlanRsp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryPlanRsp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planList" type="{http://domain.server.mmap.msp.huawei.com}ArrayOfPlanValue" minOccurs="0"/>
 *         &lt;element name="resultCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryPlanRsp", propOrder = {
    "error",
    "planList",
    "resultCode"
})
public class QueryPlanRsp {

    @XmlElementRef(name = "error", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<String> error;
    @XmlElementRef(name = "planList", namespace = "http://domain.server.mmap.msp.huawei.com", type = JAXBElement.class)
    protected JAXBElement<ArrayOfPlanValue> planList;
    protected Integer resultCode;

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setError(JAXBElement<String> value) {
        this.error = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the planList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPlanValue }{@code >}
     *     
     */
    public JAXBElement<ArrayOfPlanValue> getPlanList() {
        return planList;
    }

    /**
     * Sets the value of the planList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPlanValue }{@code >}
     *     
     */
    public void setPlanList(JAXBElement<ArrayOfPlanValue> value) {
        this.planList = ((JAXBElement<ArrayOfPlanValue> ) value);
    }

    /**
     * Gets the value of the resultCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getResultCode() {
        return resultCode;
    }

    /**
     * Sets the value of the resultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setResultCode(Integer value) {
        this.resultCode = value;
    }

}
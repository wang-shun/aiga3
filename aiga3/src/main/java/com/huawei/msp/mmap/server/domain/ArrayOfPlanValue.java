
package com.huawei.msp.mmap.server.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPlanValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPlanValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PlanValue" type="{http://domain.server.mmap.msp.huawei.com}PlanValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPlanValue", propOrder = {
    "planValue"
})
public class ArrayOfPlanValue {

    @XmlElement(name = "PlanValue", nillable = true)
    protected List<PlanValue> planValue;

    /**
     * Gets the value of the planValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the planValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlanValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PlanValue }
     * 
     * 
     */
    public List<PlanValue> getPlanValue() {
        if (planValue == null) {
            planValue = new ArrayList<PlanValue>();
        }
        return this.planValue;
    }

}


package com.huawei.msp.mmap.server.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfTaskValue complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTaskValue">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaskValue" type="{http://domain.server.mmap.msp.huawei.com}TaskValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTaskValue", propOrder = {
    "taskValue"
})
public class ArrayOfTaskValue {

    @XmlElement(name = "TaskValue", nillable = true)
    protected List<TaskValue> taskValue;

    /**
     * Gets the value of the taskValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taskValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaskValue }
     * 
     * 
     */
    public List<TaskValue> getTaskValue() {
        if (taskValue == null) {
            taskValue = new ArrayList<TaskValue>();
        }
        return this.taskValue;
    }

}

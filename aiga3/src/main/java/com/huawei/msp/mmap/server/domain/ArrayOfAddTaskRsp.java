
package com.huawei.msp.mmap.server.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfAddTaskRsp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfAddTaskRsp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddTaskRsp" type="{http://domain.server.mmap.msp.huawei.com}AddTaskRsp" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfAddTaskRsp", propOrder = {
    "addTaskRsp"
})
public class ArrayOfAddTaskRsp {

    @XmlElement(name = "AddTaskRsp", nillable = true)
    protected List<AddTaskRsp> addTaskRsp;

    /**
     * Gets the value of the addTaskRsp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addTaskRsp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddTaskRsp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddTaskRsp }
     * 
     * 
     */
    public List<AddTaskRsp> getAddTaskRsp() {
        if (addTaskRsp == null) {
            addTaskRsp = new ArrayList<AddTaskRsp>();
        }
        return this.addTaskRsp;
    }

}

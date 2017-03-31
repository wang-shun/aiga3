package com.huawei.msp.mmap.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.huawei.msp.mmap.server.domain.AddPlanRsp;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="out" type="{http://domain.server.mmap.msp.huawei.com}AddPlanRsp"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "out" })
@XmlRootElement(name = "addPlanResponse")
public class AddPlanResponse {

	@XmlElement(required = true, nillable = true)
	protected AddPlanRsp out;

	/**
	 * Gets the value of the out property.
	 * 
	 * @return possible object is {@link AddPlanRsp }
	 * 
	 */
	public AddPlanRsp getOut() {
		return out;
	}

	/**
	 * Sets the value of the out property.
	 * 
	 * @param value
	 *            allowed object is {@link AddPlanRsp }
	 * 
	 */
	public void setOut(AddPlanRsp value) {
		this.out = value;
	}

}

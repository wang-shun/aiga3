package com.huawei.msp.mmap.server.domain;

import java.util.Date;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for TaskValue complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;TaskValue&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;accessoryInfo&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;accessoryType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;appName&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;batchTaskSeq&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;clientId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;commandId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;content&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;contentFormat&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;contentType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;deliveryReport&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;destServiceCode&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;encodeType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;errorInfo&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;executeFlag&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;expiredTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}dateTime&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;fee&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;feeAddr&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;feeType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;feeUserType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;linkId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;mediaTaskId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;mediaType&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;msgId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;mspId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;orgServiceCode&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;priority&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;receiverInfo&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve1&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve10&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve11&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve12&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve13&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve14&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve15&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve16&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve17&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve18&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve19&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve2&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve20&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve21&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve22&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve23&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve24&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve25&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve26&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve27&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve28&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve29&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve3&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve30&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve31&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve32&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve33&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve34&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve35&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve36&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve37&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve38&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve39&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve4&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve40&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve41&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve42&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve43&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve44&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve45&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve46&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve47&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve48&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve49&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve5&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve50&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve51&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve52&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve53&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve54&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve55&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve56&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve57&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve58&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve59&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve6&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve60&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve61&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve62&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve63&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve64&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve65&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve66&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve67&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve68&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve69&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve7&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve70&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve71&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve72&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve73&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve74&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve75&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve76&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve77&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve78&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve79&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve8&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve80&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;reserve9&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;schduleTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}dateTime&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;sendNo&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;sendTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}dateTime&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;senderId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;spId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;spServiceId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;staffNo&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;statusFlag&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;statusTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}dateTime&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;subject&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;taskRecordFlag&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;timeUnlimited&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;ussdVersion&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;validTime&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}float&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;vasId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskValue", propOrder = { "accessoryInfo", "accessoryType",
		"appName", "batchTaskSeq", "clientId", "commandId", "content",
		"contentFormat", "contentType", "deliveryReport", "destServiceCode",
		"encodeType", "errorInfo", "executeFlag", "expiredTime", "fee",
		"feeAddr", "feeType", "feeUserType", "linkId", "mediaTaskId",
		"mediaType", "msgId", "mspId", "orgServiceCode", "priority",
		"receiverInfo", "reserve1", "reserve10", "reserve11", "reserve12",
		"reserve13", "reserve14", "reserve15", "reserve16", "reserve17",
		"reserve18", "reserve19", "reserve2", "reserve20", "reserve21",
		"reserve22", "reserve23", "reserve24", "reserve25", "reserve26",
		"reserve27", "reserve28", "reserve29", "reserve3", "reserve30",
		"reserve31", "reserve32", "reserve33", "reserve34", "reserve35",
		"reserve36", "reserve37", "reserve38", "reserve39", "reserve4",
		"reserve40", "reserve41", "reserve42", "reserve43", "reserve44",
		"reserve45", "reserve46", "reserve47", "reserve48", "reserve49",
		"reserve5", "reserve50", "reserve51", "reserve52", "reserve53",
		"reserve54", "reserve55", "reserve56", "reserve57", "reserve58",
		"reserve59", "reserve6", "reserve60", "reserve61", "reserve62",
		"reserve63", "reserve64", "reserve65", "reserve66", "reserve67",
		"reserve68", "reserve69", "reserve7", "reserve70", "reserve71",
		"reserve72", "reserve73", "reserve74", "reserve75", "reserve76",
		"reserve77", "reserve78", "reserve79", "reserve8", "reserve80",
		"reserve9", "schduleTime", "sendNo", "sendTime", "senderId", "spId",
		"spServiceId", "staffNo", "statusFlag", "statusTime", "subject",
		"taskRecordFlag", "timeUnlimited", "ussdVersion", "validTime", "vasId" })
public class TaskValue {

	protected String accessoryInfo;
	protected String accessoryType;
	protected String appName;
	protected String batchTaskSeq;
	protected String clientId;
	protected String commandId;
	protected String content;
	protected String contentFormat;
	protected String contentType;
	protected String deliveryReport;
	protected String destServiceCode;
	protected String encodeType;
	protected String errorInfo;
	protected String executeFlag;
	@XmlSchemaType(name = "dateTime")
	protected Date expiredTime;
	protected String fee;
	protected String feeAddr;
	protected String feeType;
	protected String feeUserType;
	protected String linkId;
	protected String mediaTaskId;
	protected String mediaType;
	protected String msgId;
	protected String mspId;
	protected String orgServiceCode;
	protected String priority;
	protected String receiverInfo;
	protected String reserve1;
	protected String reserve10;
	protected String reserve11;
	protected String reserve12;
	protected String reserve13;
	protected String reserve14;
	protected String reserve15;
	protected String reserve16;
	protected String reserve17;
	protected String reserve18;
	protected String reserve19;
	protected String reserve2;
	protected String reserve20;
	protected String reserve21;
	protected String reserve22;
	protected String reserve23;
	protected String reserve24;
	protected String reserve25;
	protected String reserve26;
	protected String reserve27;
	protected String reserve28;
	protected String reserve29;
	protected String reserve3;
	protected String reserve30;
	protected String reserve31;
	protected String reserve32;
	protected String reserve33;
	protected String reserve34;
	protected String reserve35;
	protected String reserve36;
	protected String reserve37;
	protected String reserve38;
	protected String reserve39;
	protected String reserve4;
	protected String reserve40;
	protected String reserve41;
	protected String reserve42;
	protected String reserve43;
	protected String reserve44;
	protected String reserve45;
	protected String reserve46;
	protected String reserve47;
	protected String reserve48;
	protected String reserve49;
	protected String reserve5;
	protected String reserve50;
	protected String reserve51;
	protected String reserve52;
	protected String reserve53;
	protected String reserve54;
	protected String reserve55;
	protected String reserve56;
	protected String reserve57;
	protected String reserve58;
	protected String reserve59;
	protected String reserve6;
	protected String reserve60;
	protected String reserve61;
	protected String reserve62;
	protected String reserve63;
	protected String reserve64;
	protected String reserve65;
	protected String reserve66;
	protected String reserve67;
	protected String reserve68;
	protected String reserve69;
	protected String reserve7;
	protected String reserve70;
	protected String reserve71;
	protected String reserve72;
	protected String reserve73;
	protected String reserve74;
	protected String reserve75;
	protected String reserve76;
	protected String reserve77;
	protected String reserve78;
	protected String reserve79;
	protected String reserve8;
	protected String reserve80;
	protected String reserve9;
	@XmlSchemaType(name = "dateTime")
	protected Date schduleTime;
	protected String sendNo;
	@XmlSchemaType(name = "dateTime")
	protected Date sendTime;
	protected String senderId;
	protected String spId;
	protected String spServiceId;
	protected String staffNo;
	protected String statusFlag;
	@XmlSchemaType(name = "dateTime")
	protected Date statusTime;
	protected String subject;
	protected String taskRecordFlag;
	protected String timeUnlimited;
	protected String ussdVersion;
	protected Float validTime;
	protected String vasId;

	/**
	 * Gets the value of the accessoryInfo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getAccessoryInfo() {
		return accessoryInfo;
	}

	/**
	 * Sets the value of the accessoryInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setAccessoryInfo(String value) {
		this.accessoryInfo = ((String) value);
	}

	/**
	 * Gets the value of the accessoryType property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getAccessoryType() {
		return accessoryType;
	}

	/**
	 * Sets the value of the accessoryType property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setAccessoryType(String value) {
		this.accessoryType = ((String) value);
	}

	/**
	 * Gets the value of the appName property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Sets the value of the appName property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setAppName(String value) {
		this.appName = ((String) value);
	}

	/**
	 * Gets the value of the batchTaskSeq property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getBatchTaskSeq() {
		return batchTaskSeq;
	}

	/**
	 * Sets the value of the batchTaskSeq property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setBatchTaskSeq(String value) {
		this.batchTaskSeq = ((String) value);
	}

	/**
	 * Gets the value of the clientId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * Sets the value of the clientId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setClientId(String value) {
		this.clientId = ((String) value);
	}

	/**
	 * Gets the value of the commandId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getCommandId() {
		return commandId;
	}

	/**
	 * Sets the value of the commandId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setCommandId(String value) {
		this.commandId = ((String) value);
	}

	/**
	 * Gets the value of the content property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the value of the content property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setContent(String value) {
		this.content = ((String) value);
	}

	/**
	 * Gets the value of the contentFormat property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getContentFormat() {
		return contentFormat;
	}

	/**
	 * Sets the value of the contentFormat property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setContentFormat(String value) {
		this.contentFormat = ((String) value);
	}

	/**
	 * Gets the value of the contentType property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the value of the contentType property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setContentType(String value) {
		this.contentType = ((String) value);
	}

	/**
	 * Gets the value of the deliveryReport property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getDeliveryReport() {
		return deliveryReport;
	}

	/**
	 * Sets the value of the deliveryReport property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setDeliveryReport(String value) {
		this.deliveryReport = ((String) value);
	}

	/**
	 * Gets the value of the destServiceCode property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getDestServiceCode() {
		return destServiceCode;
	}

	/**
	 * Sets the value of the destServiceCode property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setDestServiceCode(String value) {
		this.destServiceCode = ((String) value);
	}

	/**
	 * Gets the value of the encodeType property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getEncodeType() {
		return encodeType;
	}

	/**
	 * Sets the value of the encodeType property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setEncodeType(String value) {
		this.encodeType = ((String) value);
	}

	/**
	 * Gets the value of the errorInfo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * Sets the value of the errorInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setErrorInfo(String value) {
		this.errorInfo = ((String) value);
	}

	/**
	 * Gets the value of the executeFlag property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getExecuteFlag() {
		return executeFlag;
	}

	/**
	 * Sets the value of the executeFlag property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setExecuteFlag(String value) {
		this.executeFlag = ((String) value);
	}

	/**
	 * Gets the value of the expiredTime property.
	 * 
	 * @return possible object is {@link Date }
	 * 
	 */
	public Date getExpiredTime() {
		return expiredTime;
	}

	/**
	 * Sets the value of the expiredTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Date }
	 * 
	 */
	public void setExpiredTime(Date value) {
		this.expiredTime = value;
	}

	/**
	 * Gets the value of the fee property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * Sets the value of the fee property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setFee(String value) {
		this.fee = ((String) value);
	}

	/**
	 * Gets the value of the feeAddr property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getFeeAddr() {
		return feeAddr;
	}

	/**
	 * Sets the value of the feeAddr property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setFeeAddr(String value) {
		this.feeAddr = ((String) value);
	}

	/**
	 * Gets the value of the feeType property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * Sets the value of the feeType property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setFeeType(String value) {
		this.feeType = ((String) value);
	}

	/**
	 * Gets the value of the feeUserType property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getFeeUserType() {
		return feeUserType;
	}

	/**
	 * Sets the value of the feeUserType property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setFeeUserType(String value) {
		this.feeUserType = ((String) value);
	}

	/**
	 * Gets the value of the linkId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getLinkId() {
		return linkId;
	}

	/**
	 * Sets the value of the linkId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setLinkId(String value) {
		this.linkId = ((String) value);
	}

	/**
	 * Gets the value of the mediaTaskId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getMediaTaskId() {
		return mediaTaskId;
	}

	/**
	 * Sets the value of the mediaTaskId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setMediaTaskId(String value) {
		this.mediaTaskId = ((String) value);
	}

	/**
	 * Gets the value of the mediaType property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * Sets the value of the mediaType property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setMediaType(String value) {
		this.mediaType = ((String) value);
	}

	/**
	 * Gets the value of the msgId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * Sets the value of the msgId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setMsgId(String value) {
		this.msgId = ((String) value);
	}

	/**
	 * Gets the value of the mspId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getMspId() {
		return mspId;
	}

	/**
	 * Sets the value of the mspId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setMspId(String value) {
		this.mspId = ((String) value);
	}

	/**
	 * Gets the value of the orgServiceCode property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getOrgServiceCode() {
		return orgServiceCode;
	}

	/**
	 * Sets the value of the orgServiceCode property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setOrgServiceCode(String value) {
		this.orgServiceCode = ((String) value);
	}

	/**
	 * Gets the value of the priority property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the value of the priority property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setPriority(String value) {
		this.priority = ((String) value);
	}

	/**
	 * Gets the value of the receiverInfo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReceiverInfo() {
		return receiverInfo;
	}

	/**
	 * Sets the value of the receiverInfo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReceiverInfo(String value) {
		this.receiverInfo = ((String) value);
	}

	/**
	 * Gets the value of the reserve1 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve1() {
		return reserve1;
	}

	/**
	 * Sets the value of the reserve1 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve1(String value) {
		this.reserve1 = ((String) value);
	}

	/**
	 * Gets the value of the reserve10 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve10() {
		return reserve10;
	}

	/**
	 * Sets the value of the reserve10 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve10(String value) {
		this.reserve10 = ((String) value);
	}

	/**
	 * Gets the value of the reserve11 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve11() {
		return reserve11;
	}

	/**
	 * Sets the value of the reserve11 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve11(String value) {
		this.reserve11 = ((String) value);
	}

	/**
	 * Gets the value of the reserve12 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve12() {
		return reserve12;
	}

	/**
	 * Sets the value of the reserve12 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve12(String value) {
		this.reserve12 = ((String) value);
	}

	/**
	 * Gets the value of the reserve13 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve13() {
		return reserve13;
	}

	/**
	 * Sets the value of the reserve13 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve13(String value) {
		this.reserve13 = ((String) value);
	}

	/**
	 * Gets the value of the reserve14 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve14() {
		return reserve14;
	}

	/**
	 * Sets the value of the reserve14 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve14(String value) {
		this.reserve14 = ((String) value);
	}

	/**
	 * Gets the value of the reserve15 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve15() {
		return reserve15;
	}

	/**
	 * Sets the value of the reserve15 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve15(String value) {
		this.reserve15 = ((String) value);
	}

	/**
	 * Gets the value of the reserve16 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve16() {
		return reserve16;
	}

	/**
	 * Sets the value of the reserve16 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve16(String value) {
		this.reserve16 = ((String) value);
	}

	/**
	 * Gets the value of the reserve17 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve17() {
		return reserve17;
	}

	/**
	 * Sets the value of the reserve17 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve17(String value) {
		this.reserve17 = ((String) value);
	}

	/**
	 * Gets the value of the reserve18 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve18() {
		return reserve18;
	}

	/**
	 * Sets the value of the reserve18 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve18(String value) {
		this.reserve18 = ((String) value);
	}

	/**
	 * Gets the value of the reserve19 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve19() {
		return reserve19;
	}

	/**
	 * Sets the value of the reserve19 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve19(String value) {
		this.reserve19 = ((String) value);
	}

	/**
	 * Gets the value of the reserve2 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve2() {
		return reserve2;
	}

	/**
	 * Sets the value of the reserve2 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve2(String value) {
		this.reserve2 = ((String) value);
	}

	/**
	 * Gets the value of the reserve20 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve20() {
		return reserve20;
	}

	/**
	 * Sets the value of the reserve20 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve20(String value) {
		this.reserve20 = ((String) value);
	}

	/**
	 * Gets the value of the reserve21 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve21() {
		return reserve21;
	}

	/**
	 * Sets the value of the reserve21 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve21(String value) {
		this.reserve21 = ((String) value);
	}

	/**
	 * Gets the value of the reserve22 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve22() {
		return reserve22;
	}

	/**
	 * Sets the value of the reserve22 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve22(String value) {
		this.reserve22 = ((String) value);
	}

	/**
	 * Gets the value of the reserve23 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve23() {
		return reserve23;
	}

	/**
	 * Sets the value of the reserve23 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve23(String value) {
		this.reserve23 = ((String) value);
	}

	/**
	 * Gets the value of the reserve24 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve24() {
		return reserve24;
	}

	/**
	 * Sets the value of the reserve24 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve24(String value) {
		this.reserve24 = ((String) value);
	}

	/**
	 * Gets the value of the reserve25 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve25() {
		return reserve25;
	}

	/**
	 * Sets the value of the reserve25 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve25(String value) {
		this.reserve25 = ((String) value);
	}

	/**
	 * Gets the value of the reserve26 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve26() {
		return reserve26;
	}

	/**
	 * Sets the value of the reserve26 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve26(String value) {
		this.reserve26 = ((String) value);
	}

	/**
	 * Gets the value of the reserve27 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve27() {
		return reserve27;
	}

	/**
	 * Sets the value of the reserve27 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve27(String value) {
		this.reserve27 = ((String) value);
	}

	/**
	 * Gets the value of the reserve28 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve28() {
		return reserve28;
	}

	/**
	 * Sets the value of the reserve28 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve28(String value) {
		this.reserve28 = ((String) value);
	}

	/**
	 * Gets the value of the reserve29 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve29() {
		return reserve29;
	}

	/**
	 * Sets the value of the reserve29 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve29(String value) {
		this.reserve29 = ((String) value);
	}

	/**
	 * Gets the value of the reserve3 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve3() {
		return reserve3;
	}

	/**
	 * Sets the value of the reserve3 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve3(String value) {
		this.reserve3 = ((String) value);
	}

	/**
	 * Gets the value of the reserve30 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve30() {
		return reserve30;
	}

	/**
	 * Sets the value of the reserve30 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve30(String value) {
		this.reserve30 = ((String) value);
	}

	/**
	 * Gets the value of the reserve31 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve31() {
		return reserve31;
	}

	/**
	 * Sets the value of the reserve31 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve31(String value) {
		this.reserve31 = ((String) value);
	}

	/**
	 * Gets the value of the reserve32 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve32() {
		return reserve32;
	}

	/**
	 * Sets the value of the reserve32 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve32(String value) {
		this.reserve32 = ((String) value);
	}

	/**
	 * Gets the value of the reserve33 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve33() {
		return reserve33;
	}

	/**
	 * Sets the value of the reserve33 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve33(String value) {
		this.reserve33 = ((String) value);
	}

	/**
	 * Gets the value of the reserve34 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve34() {
		return reserve34;
	}

	/**
	 * Sets the value of the reserve34 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve34(String value) {
		this.reserve34 = ((String) value);
	}

	/**
	 * Gets the value of the reserve35 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve35() {
		return reserve35;
	}

	/**
	 * Sets the value of the reserve35 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve35(String value) {
		this.reserve35 = ((String) value);
	}

	/**
	 * Gets the value of the reserve36 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve36() {
		return reserve36;
	}

	/**
	 * Sets the value of the reserve36 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve36(String value) {
		this.reserve36 = ((String) value);
	}

	/**
	 * Gets the value of the reserve37 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve37() {
		return reserve37;
	}

	/**
	 * Sets the value of the reserve37 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve37(String value) {
		this.reserve37 = ((String) value);
	}

	/**
	 * Gets the value of the reserve38 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve38() {
		return reserve38;
	}

	/**
	 * Sets the value of the reserve38 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve38(String value) {
		this.reserve38 = ((String) value);
	}

	/**
	 * Gets the value of the reserve39 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve39() {
		return reserve39;
	}

	/**
	 * Sets the value of the reserve39 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve39(String value) {
		this.reserve39 = ((String) value);
	}

	/**
	 * Gets the value of the reserve4 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve4() {
		return reserve4;
	}

	/**
	 * Sets the value of the reserve4 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve4(String value) {
		this.reserve4 = ((String) value);
	}

	/**
	 * Gets the value of the reserve40 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve40() {
		return reserve40;
	}

	/**
	 * Sets the value of the reserve40 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve40(String value) {
		this.reserve40 = ((String) value);
	}

	/**
	 * Gets the value of the reserve41 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve41() {
		return reserve41;
	}

	/**
	 * Sets the value of the reserve41 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve41(String value) {
		this.reserve41 = ((String) value);
	}

	/**
	 * Gets the value of the reserve42 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve42() {
		return reserve42;
	}

	/**
	 * Sets the value of the reserve42 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve42(String value) {
		this.reserve42 = ((String) value);
	}

	/**
	 * Gets the value of the reserve43 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve43() {
		return reserve43;
	}

	/**
	 * Sets the value of the reserve43 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve43(String value) {
		this.reserve43 = ((String) value);
	}

	/**
	 * Gets the value of the reserve44 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve44() {
		return reserve44;
	}

	/**
	 * Sets the value of the reserve44 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve44(String value) {
		this.reserve44 = ((String) value);
	}

	/**
	 * Gets the value of the reserve45 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve45() {
		return reserve45;
	}

	/**
	 * Sets the value of the reserve45 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve45(String value) {
		this.reserve45 = ((String) value);
	}

	/**
	 * Gets the value of the reserve46 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve46() {
		return reserve46;
	}

	/**
	 * Sets the value of the reserve46 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve46(String value) {
		this.reserve46 = ((String) value);
	}

	/**
	 * Gets the value of the reserve47 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve47() {
		return reserve47;
	}

	/**
	 * Sets the value of the reserve47 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve47(String value) {
		this.reserve47 = ((String) value);
	}

	/**
	 * Gets the value of the reserve48 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve48() {
		return reserve48;
	}

	/**
	 * Sets the value of the reserve48 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve48(String value) {
		this.reserve48 = ((String) value);
	}

	/**
	 * Gets the value of the reserve49 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve49() {
		return reserve49;
	}

	/**
	 * Sets the value of the reserve49 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve49(String value) {
		this.reserve49 = ((String) value);
	}

	/**
	 * Gets the value of the reserve5 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve5() {
		return reserve5;
	}

	/**
	 * Sets the value of the reserve5 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve5(String value) {
		this.reserve5 = ((String) value);
	}

	/**
	 * Gets the value of the reserve50 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve50() {
		return reserve50;
	}

	/**
	 * Sets the value of the reserve50 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve50(String value) {
		this.reserve50 = ((String) value);
	}

	/**
	 * Gets the value of the reserve51 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve51() {
		return reserve51;
	}

	/**
	 * Sets the value of the reserve51 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve51(String value) {
		this.reserve51 = ((String) value);
	}

	/**
	 * Gets the value of the reserve52 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve52() {
		return reserve52;
	}

	/**
	 * Sets the value of the reserve52 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve52(String value) {
		this.reserve52 = ((String) value);
	}

	/**
	 * Gets the value of the reserve53 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve53() {
		return reserve53;
	}

	/**
	 * Sets the value of the reserve53 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve53(String value) {
		this.reserve53 = ((String) value);
	}

	/**
	 * Gets the value of the reserve54 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve54() {
		return reserve54;
	}

	/**
	 * Sets the value of the reserve54 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve54(String value) {
		this.reserve54 = ((String) value);
	}

	/**
	 * Gets the value of the reserve55 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve55() {
		return reserve55;
	}

	/**
	 * Sets the value of the reserve55 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve55(String value) {
		this.reserve55 = ((String) value);
	}

	/**
	 * Gets the value of the reserve56 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve56() {
		return reserve56;
	}

	/**
	 * Sets the value of the reserve56 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve56(String value) {
		this.reserve56 = ((String) value);
	}

	/**
	 * Gets the value of the reserve57 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve57() {
		return reserve57;
	}

	/**
	 * Sets the value of the reserve57 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve57(String value) {
		this.reserve57 = ((String) value);
	}

	/**
	 * Gets the value of the reserve58 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve58() {
		return reserve58;
	}

	/**
	 * Sets the value of the reserve58 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve58(String value) {
		this.reserve58 = ((String) value);
	}

	/**
	 * Gets the value of the reserve59 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve59() {
		return reserve59;
	}

	/**
	 * Sets the value of the reserve59 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve59(String value) {
		this.reserve59 = ((String) value);
	}

	/**
	 * Gets the value of the reserve6 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve6() {
		return reserve6;
	}

	/**
	 * Sets the value of the reserve6 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve6(String value) {
		this.reserve6 = ((String) value);
	}

	/**
	 * Gets the value of the reserve60 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve60() {
		return reserve60;
	}

	/**
	 * Sets the value of the reserve60 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve60(String value) {
		this.reserve60 = ((String) value);
	}

	/**
	 * Gets the value of the reserve61 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve61() {
		return reserve61;
	}

	/**
	 * Sets the value of the reserve61 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve61(String value) {
		this.reserve61 = ((String) value);
	}

	/**
	 * Gets the value of the reserve62 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve62() {
		return reserve62;
	}

	/**
	 * Sets the value of the reserve62 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve62(String value) {
		this.reserve62 = ((String) value);
	}

	/**
	 * Gets the value of the reserve63 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve63() {
		return reserve63;
	}

	/**
	 * Sets the value of the reserve63 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve63(String value) {
		this.reserve63 = ((String) value);
	}

	/**
	 * Gets the value of the reserve64 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve64() {
		return reserve64;
	}

	/**
	 * Sets the value of the reserve64 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve64(String value) {
		this.reserve64 = ((String) value);
	}

	/**
	 * Gets the value of the reserve65 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve65() {
		return reserve65;
	}

	/**
	 * Sets the value of the reserve65 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve65(String value) {
		this.reserve65 = ((String) value);
	}

	/**
	 * Gets the value of the reserve66 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve66() {
		return reserve66;
	}

	/**
	 * Sets the value of the reserve66 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve66(String value) {
		this.reserve66 = ((String) value);
	}

	/**
	 * Gets the value of the reserve67 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve67() {
		return reserve67;
	}

	/**
	 * Sets the value of the reserve67 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve67(String value) {
		this.reserve67 = ((String) value);
	}

	/**
	 * Gets the value of the reserve68 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve68() {
		return reserve68;
	}

	/**
	 * Sets the value of the reserve68 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve68(String value) {
		this.reserve68 = ((String) value);
	}

	/**
	 * Gets the value of the reserve69 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve69() {
		return reserve69;
	}

	/**
	 * Sets the value of the reserve69 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve69(String value) {
		this.reserve69 = ((String) value);
	}

	/**
	 * Gets the value of the reserve7 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve7() {
		return reserve7;
	}

	/**
	 * Sets the value of the reserve7 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve7(String value) {
		this.reserve7 = ((String) value);
	}

	/**
	 * Gets the value of the reserve70 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve70() {
		return reserve70;
	}

	/**
	 * Sets the value of the reserve70 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve70(String value) {
		this.reserve70 = ((String) value);
	}

	/**
	 * Gets the value of the reserve71 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve71() {
		return reserve71;
	}

	/**
	 * Sets the value of the reserve71 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve71(String value) {
		this.reserve71 = ((String) value);
	}

	/**
	 * Gets the value of the reserve72 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve72() {
		return reserve72;
	}

	/**
	 * Sets the value of the reserve72 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve72(String value) {
		this.reserve72 = ((String) value);
	}

	/**
	 * Gets the value of the reserve73 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve73() {
		return reserve73;
	}

	/**
	 * Sets the value of the reserve73 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve73(String value) {
		this.reserve73 = ((String) value);
	}

	/**
	 * Gets the value of the reserve74 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve74() {
		return reserve74;
	}

	/**
	 * Sets the value of the reserve74 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve74(String value) {
		this.reserve74 = ((String) value);
	}

	/**
	 * Gets the value of the reserve75 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve75() {
		return reserve75;
	}

	/**
	 * Sets the value of the reserve75 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve75(String value) {
		this.reserve75 = ((String) value);
	}

	/**
	 * Gets the value of the reserve76 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve76() {
		return reserve76;
	}

	/**
	 * Sets the value of the reserve76 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve76(String value) {
		this.reserve76 = ((String) value);
	}

	/**
	 * Gets the value of the reserve77 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve77() {
		return reserve77;
	}

	/**
	 * Sets the value of the reserve77 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve77(String value) {
		this.reserve77 = ((String) value);
	}

	/**
	 * Gets the value of the reserve78 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve78() {
		return reserve78;
	}

	/**
	 * Sets the value of the reserve78 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve78(String value) {
		this.reserve78 = ((String) value);
	}

	/**
	 * Gets the value of the reserve79 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve79() {
		return reserve79;
	}

	/**
	 * Sets the value of the reserve79 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve79(String value) {
		this.reserve79 = ((String) value);
	}

	/**
	 * Gets the value of the reserve8 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve8() {
		return reserve8;
	}

	/**
	 * Sets the value of the reserve8 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve8(String value) {
		this.reserve8 = ((String) value);
	}

	/**
	 * Gets the value of the reserve80 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve80() {
		return reserve80;
	}

	/**
	 * Sets the value of the reserve80 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve80(String value) {
		this.reserve80 = ((String) value);
	}

	/**
	 * Gets the value of the reserve9 property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getReserve9() {
		return reserve9;
	}

	/**
	 * Sets the value of the reserve9 property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setReserve9(String value) {
		this.reserve9 = ((String) value);
	}

	/**
	 * Gets the value of the schduleTime property.
	 * 
	 * @return possible object is {@link Date }
	 * 
	 */
	public Date getSchduleTime() {
		return schduleTime;
	}

	/**
	 * Sets the value of the schduleTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Date }
	 * 
	 */
	public void setSchduleTime(Date value) {
		this.schduleTime = value;
	}

	/**
	 * Gets the value of the sendNo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getSendNo() {
		return sendNo;
	}

	/**
	 * Sets the value of the sendNo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setSendNo(String value) {
		this.sendNo = ((String) value);
	}

	/**
	 * Gets the value of the sendTime property.
	 * 
	 * @return possible object is {@link Date }
	 * 
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * Sets the value of the sendTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Date }
	 * 
	 */
	public void setSendTime(Date value) {
		this.sendTime = value;
	}

	/**
	 * Gets the value of the senderId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * Sets the value of the senderId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setSenderId(String value) {
		this.senderId = ((String) value);
	}

	/**
	 * Gets the value of the spId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getSpId() {
		return spId;
	}

	/**
	 * Sets the value of the spId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setSpId(String value) {
		this.spId = ((String) value);
	}

	/**
	 * Gets the value of the spServiceId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getSpServiceId() {
		return spServiceId;
	}

	/**
	 * Sets the value of the spServiceId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setSpServiceId(String value) {
		this.spServiceId = ((String) value);
	}

	/**
	 * Gets the value of the staffNo property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 * Sets the value of the staffNo property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setStaffNo(String value) {
		this.staffNo = ((String) value);
	}

	/**
	 * Gets the value of the statusFlag property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * Sets the value of the statusFlag property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setStatusFlag(String value) {
		this.statusFlag = ((String) value);
	}

	/**
	 * Gets the value of the statusTime property.
	 * 
	 * @return possible object is {@link Date }
	 * 
	 */
	public Date getStatusTime() {
		return statusTime;
	}

	/**
	 * Sets the value of the statusTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Date }
	 * 
	 */
	public void setStatusTime(Date value) {
		this.statusTime = value;
	}

	/**
	 * Gets the value of the subject property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the value of the subject property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setSubject(String value) {
		this.subject = ((String) value);
	}

	/**
	 * Gets the value of the taskRecordFlag property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getTaskRecordFlag() {
		return taskRecordFlag;
	}

	/**
	 * Sets the value of the taskRecordFlag property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setTaskRecordFlag(String value) {
		this.taskRecordFlag = ((String) value);
	}

	/**
	 * Gets the value of the timeUnlimited property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getTimeUnlimited() {
		return timeUnlimited;
	}

	/**
	 * Sets the value of the timeUnlimited property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setTimeUnlimited(String value) {
		this.timeUnlimited = ((String) value);
	}

	/**
	 * Gets the value of the ussdVersion property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getUssdVersion() {
		return ussdVersion;
	}

	/**
	 * Sets the value of the ussdVersion property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setUssdVersion(String value) {
		this.ussdVersion = ((String) value);
	}

	/**
	 * Gets the value of the validTime property.
	 * 
	 * @return possible object is {@link Float }
	 * 
	 */
	public Float getValidTime() {
		return validTime;
	}

	/**
	 * Sets the value of the validTime property.
	 * 
	 * @param value
	 *            allowed object is {@link Float }
	 * 
	 */
	public void setValidTime(Float value) {
		this.validTime = value;
	}

	/**
	 * Gets the value of the vasId property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public String getVasId() {
		return vasId;
	}

	/**
	 * Sets the value of the vasId property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	public void setVasId(String value) {
		this.vasId = ((String) value);
	}

}

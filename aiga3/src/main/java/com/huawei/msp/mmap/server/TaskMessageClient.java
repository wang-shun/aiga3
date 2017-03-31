package com.huawei.msp.mmap.server;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.huawei.msp.mmap.server.domain.AddTaskRsp;
import com.huawei.msp.mmap.server.domain.TaskValue;

public class TaskMessageClient {
	public static void sendMessageForCycle(String phoneNumber, String content) {
		JaxWsProxyFactoryBean soapFactoryBean=new JaxWsProxyFactoryBean();

		//soapFactoryBean.setAddress("http://10.70.181.7:8080/MSP/services/MSPWebService");

		soapFactoryBean.setAddress("http://smsinfo.yw.zj.chinamobile.com:8080/MSP/services/MSPWebService");
		soapFactoryBean.setServiceClass(MspWebServicePortType.class);
		MspWebServicePortType service=(MspWebServicePortType) soapFactoryBean.create();
		TaskValue taskValue = new TaskValue();
		
		taskValue.setContentFormat("1010001");					
		taskValue.setMediaType("1014001");							
		taskValue.setReceiverInfo(phoneNumber);	
		taskValue.setSendNo("10086");		
		
		taskValue.setReserve25("1475063181305");	
		taskValue.setContent(content);		
		
	
		taskValue.setReserve21("1005");	
		taskValue.setReserve22("日常运维平台");	
		taskValue.setReserve23("1005002");	
		taskValue.setReserve24("任务分派短信通知短信");	
		taskValue.setValidTime(new Float(2));
		AddTaskRsp message=service.addTask(taskValue);
		System.out.println(message.getError());
	}
	public static void main(String[] args) {
		sendMessageForCycle("15988409381", "AIGA_SMS~this is for test");
	}
}

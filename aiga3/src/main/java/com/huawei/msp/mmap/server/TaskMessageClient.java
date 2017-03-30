package com.huawei.msp.mmap.server;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.huawei.msp.mmap.server.domain.AddTaskRsp;
import com.huawei.msp.mmap.server.domain.TaskValue;

public class TaskMessageClient {
	public static void sendMessageForCycle(String phoneNumber, String content) {
		JaxWsProxyFactoryBean soapFactoryBean=new JaxWsProxyFactoryBean();
		//测试环境
		//soapFactoryBean.setAddress("http://localhost:8080/webservice/soap/SoapServiceImpl");
		//生产
		soapFactoryBean.setAddress("http://smsinfo.yw.zj.chinamobile.com:8080/MSP/services/MSPWebService");
		soapFactoryBean.setServiceClass(MspWebServicePortType.class);
		MspWebServicePortType service=(MspWebServicePortType) soapFactoryBean.create();
		TaskValue taskValue = new TaskValue();
		
		taskValue.setContentFormat("1010001");					//内容格式[普通文本形式]
		taskValue.setMediaType("1014001");								//媒体类型[短信]
		taskValue.setReceiverInfo(phoneNumber);	//设置接收人号码
		taskValue.setSendNo("10086");			//设置发送号码
		
		taskValue.setReserve25("1475063181305");	//统一短信业务管理平台配置的短信模板ID
		taskValue.setContent(content);		//宏变量字符串
		
		//根据模版获取业务ID、业务名称、渠道ID、渠道名称
		taskValue.setReserve21("1005");	//渠道id
		taskValue.setReserve22("日常运维平台");	//渠道名称
		taskValue.setReserve23("1005002");	//业务id
		taskValue.setReserve24("任务分派短信通知短信");	//业务id
		//taskValue.setReserve24("用例执行失败告警短信");
		// 发送报告有效时间 现在默认为2小时
		taskValue.setValidTime(new Float(2));
		AddTaskRsp message=service.addTask(taskValue);
		System.out.println(message.getError());
	}
	public static void main(String[] args) {
		System.out.println("666");
		sendMessageForCycle("13567177436", "AIGA_SMS~this is for test");
		System.out.println("133");
	}
}

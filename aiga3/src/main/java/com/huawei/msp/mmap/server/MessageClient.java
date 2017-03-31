package com.huawei.msp.mmap.server;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.huawei.msp.mmap.server.domain.AddTaskRsp;
import com.huawei.msp.mmap.server.domain.TaskValue;

public class MessageClient {
	public static void sendMessageForCycle(String phoneNumber, String content) {
		JaxWsProxyFactoryBean soapFactoryBean=new JaxWsProxyFactoryBean();
		//���Ի���
		//soapFactoryBean.setAddress("http://10.70.181.7:8080/MSP/services/MSPWebService");
		//����
		soapFactoryBean.setAddress("http://smsinfo.yw.zj.chinamobile.com:8080/MSP/services/MSPWebService");
		soapFactoryBean.setServiceClass(MspWebServicePortType.class);
		MspWebServicePortType service=(MspWebServicePortType) soapFactoryBean.create();
		TaskValue taskValue = new TaskValue();
		
		taskValue.setContentFormat("1010001");					//���ݸ�ʽ[��ͨ�ı���ʽ]
		taskValue.setMediaType("1014001");								//ý������[����]
		taskValue.setReceiverInfo(phoneNumber);	//���ý����˺���
		taskValue.setSendNo("10086");			//���÷��ͺ���
		
		taskValue.setReserve25("1456391320135");	//ͳһ����ҵ�����ƽ̨���õĶ���ģ��ID
		//"TASK_NAME~ͣ����|CASE_NAME~ͣ��������|FAIL_REASON~����ʱ|SYSTEM_NAME~��Ӫҵ��|FUNCTION_NAME~ͣ����"
		taskValue.setContent(content);		//������ַ���
		
		//����ģ���ȡҵ��ID��ҵ�����ơ�����ID����������
		taskValue.setReserve21("1005");	//����id
		taskValue.setReserve22("�ճ���άƽ̨");	//��������
		taskValue.setReserve23("1005001");	//ҵ��id
		taskValue.setReserve24("����ִ��ʧ�ܸ澯����");	//ҵ��id
		
		// ���ͱ�����Чʱ�� ����Ĭ��Ϊ2Сʱ
		taskValue.setValidTime(new Float(2));
		AddTaskRsp message=service.addTask(taskValue);
		System.out.println(message.getError());
	}
	public static void main(String[] args) {
		sendMessageForCycle("13567177436", "this is for test");
	}
}

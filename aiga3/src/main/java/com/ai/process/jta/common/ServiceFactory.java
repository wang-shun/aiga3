package com.ai.process.jta.common;

import org.springframework.context.ApplicationContext;

public class ServiceFactory {

	public static Object getService(String serviceId) {
		
		ApplicationContext appContext = JtaSpringContext.getInstance().getApplicationContext();

		return appContext.getBean(serviceId);

	}
}

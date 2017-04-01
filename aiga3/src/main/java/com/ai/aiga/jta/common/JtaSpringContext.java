package com.ai.aiga.jta.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 单例SpringContext 供web容器外的进程使用，如单独启动的main进程，或其他多线程环境
 * @author baofd
 *
 */
public class JtaSpringContext {

	private static JtaSpringContext singletonSc;
	private static Boolean LOCK = Boolean.FALSE;
	private ApplicationContext ac;

	private JtaSpringContext() {
		if (ac == null) {
			ac = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-jta.xml");
		}
	}

	public ApplicationContext getApplicationContext() {
		return ac;
	}

	public static JtaSpringContext getInstance() {
		if (singletonSc == null) {
			synchronized (LOCK) {
				if (singletonSc == null) {
					singletonSc = new JtaSpringContext();
				}
			}
		}
		return singletonSc;
	}

}

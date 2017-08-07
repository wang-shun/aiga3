package com.ai.am.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: ApplicationContextUtil
 * @author: taoyf
 * @date: 2017年4月24日 下午3:34:45
 * @Description:
 * 
 */
public class ApplicationContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext; 

	/**
	 * @Function: ApplicationContextAware :: setApplicationContext
	 * @author: taoyf
	 * @date: 2017年4月24日 下午3:36:04
	 * @Description: 
	 * @return
	 * @throws 
	 */
	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		applicationContext = ac;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	
	
	
	

}


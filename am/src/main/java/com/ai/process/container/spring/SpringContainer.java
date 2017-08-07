package com.ai.process.container.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.process.config.ProcessDefaultInfo;
import com.ai.process.container.Container;

/**
 * @ClassName: SpringContainer
 * @author: taoyf
 * @date: 2017年4月24日 下午6:19:59
 * @Description:
 * 
 */
public class SpringContainer implements Container {
	
	private ClassPathXmlApplicationContext context;

	/**
	 * @Function: Container :: start @author: taoyf @date: 2017年4月24日
	 * 下午6:20:13 @Description: @return @throws
	 */
	@Override
	public void start() {

		String sprongLocation = ProcessDefaultInfo.SPRING_LOCATION;
		
		context = new ClassPathXmlApplicationContext(sprongLocation.split("[,\\s]+"));
		String profile = System.getProperty(ProcessDefaultInfo.SPRING_PFOFILE_KEY);
		if (StringUtils.isNotBlank(profile)) {
			String[] profiles = StringUtils.split(profile, ",");
			context.getEnvironment().setActiveProfiles(profiles);
		}

		context.start();
		
		
	}

	/**
	 * @Function: Container :: stop @author: taoyf @date: 2017年4月24日
	 * 下午6:20:13 @Description: @return @throws
	 */
	@Override
	public void stop() {
		context.stop();
	}

}

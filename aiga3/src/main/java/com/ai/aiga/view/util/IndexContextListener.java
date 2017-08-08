package com.ai.aiga.view.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class IndexContextListener implements ServletContextListener {

	private java.util.Timer timer = null; 
	public void contextInitialized(ServletContextEvent event) {
	    timer = new java.util.Timer(true); 
	    event.getServletContext().log("定时器已启动");  
	    timer.schedule(new IndexInfoUpdateTask(event.getServletContext()), 0, 60*60*1000);
	    event.getServletContext().log("已经添加任务调度表");
	}
	public void contextDestroyed(ServletContextEvent event) {
	    timer.cancel();
	    event.getServletContext().log("定时器销毁");
	}

}

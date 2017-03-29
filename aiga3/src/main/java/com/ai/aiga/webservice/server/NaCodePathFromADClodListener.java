package com.ai.aiga.webservice.server;

import java.net.InetAddress;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.ws.Endpoint;

import com.ai.aiga.webservice.service.NaCodePathWebServiceSvImp;

public class NaCodePathFromADClodListener implements ServletContextListener {

	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String ip = "";
		System.out.println("11111");
		try {
			InetAddress addr = InetAddress.getLocalHost();
			ip=addr.getHostAddress().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	   if(!"".equals(ip)){
		   ip=   "http://"+ip+":9091/NaCodePathWebServiceSv";  
		   Endpoint.publish( ip, new NaCodePathWebServiceSvImp());  
		   System.out.println("server start");
	   }

	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}

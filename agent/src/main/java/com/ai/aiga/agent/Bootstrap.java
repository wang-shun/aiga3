package com.ai.aiga.agent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.agent.container.Container;
import com.ai.aiga.agent.container.netty.NettyContainer;
import com.ai.aiga.agent.container.netty.NettyContext;


/**
 * @ClassName: Bootstrap
 * @author: taoyf
 * @date: 2017年4月21日 下午3:24:46
 * @Description:
 * 
 * 进程启动类.
 * 抄 com.alibaba.dubbo.container.Main
 * 
 */
public class Bootstrap {
	
    public static final String SHUTDOWN_HOOK_KEY = "ai.process.shutdown.hook";
	
	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
    private static volatile boolean running = true;
    
    public static final int defaultPort = 5201;
	
	public static void main(String[] args) {
		
        try {
        	int port = defaultPort;
        	
            if (args != null && args.length >= 1) {
            	
            	try{
            		port = Integer.parseInt(args[0]);
            	}catch (Exception e) {
            		port = defaultPort;
				}
            }
            
            logger.info("start to run asiainfo agant. prot = " + port);
            
            final List<Container> containers = new ArrayList<Container>();
            containers.add(new NettyContainer());
            NettyContext.instance().setPort(port);
            
            
            //if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
            if ("true".equals("true")) {
	            Runtime.getRuntime().addShutdownHook(new Thread() {
	                public void run() {
	                	logger.info("开始安全关闭系统!");
	                	
	                	for (Container container : containers) {
	                        try {
	                            container.stop();
	                            logger.info("Container " + container.getClass().getSimpleName() + " stopped!");
	                        } catch (Throwable t) {
	                        	logger.error("Container " + container.getClass().getSimpleName() + " stopp failure!");
	                            logger.error(t.getMessage(), t);
	                        }
	                    }
	                	
	                	synchronized (Bootstrap.class) {
                            running = false;
                            Bootstrap.class.notify();
                        }
	                	
	                }
	            });
            }
            
            for (Container container : containers) {
                container.start();
                logger.info("Container " + container.getClass().getSimpleName() + " started!");
            }
            System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " run asiainfo process started!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
        synchronized (Bootstrap.class) {
            while (running) {
                try {
                	Bootstrap.class.wait();
                } catch (Throwable e) {
                }
            }
        }
		
	}

}


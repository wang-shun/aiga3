package com.ai.aiga.agent.container;

import com.ai.aiga.agent.container.netty.server.AgentServer;

/**
 * @ClassName: Container
 * @author: taoyf
 * @date: 2017年4月26日 下午8:16:36
 * @Description:
 * 
 */
public interface Container {
	
    /**
     * start.
     */
    void start();
    
    /**
     * stop.
     */
    void stop();

}


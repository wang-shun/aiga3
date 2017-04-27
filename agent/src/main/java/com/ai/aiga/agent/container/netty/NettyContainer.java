package com.ai.aiga.agent.container.netty;

import com.ai.aiga.agent.container.Container;
import com.ai.aiga.agent.container.netty.server.AgentServer;

/**
 * @ClassName: NettyContainer
 * @author: taoyf
 * @date: 2017年4月26日 下午6:56:29
 * @Description:
 * 
 */
public class NettyContainer implements Container {
	
	private AgentServer server;

	/**
	 * @throws Exception 
	 * @Function: Container :: start
	 * @author: taoyf
	 * @date: 2017年4月26日 下午6:56:52
	 * @Description: 
	 * @return
	 * @throws 
	 */
	@Override
	public void start() {
		
		server = new AgentServer(NettyContext.instance().getPort());
		try {
			server.run();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * @Function: Container :: stop
	 * @author: taoyf
	 * @date: 2017年4月26日 下午6:56:52
	 * @Description: 
	 * @return
	 * @throws 
	 */
	@Override
	public void stop() {
		server.stop();
	}

}


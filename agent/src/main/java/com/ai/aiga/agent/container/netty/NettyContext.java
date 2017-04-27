package com.ai.aiga.agent.container.netty;

/**
 * @ClassName: ProcessContext
 * @author: taoyf
 * @date: 2017年4月24日 下午3:22:36
 * @Description:
 * 
 */
public class NettyContext {
	
	private static NettyContext quartzContext;
	
	private NettyContext(){}
	
	public static NettyContext instance(){
		if(quartzContext == null){
			synchronized (NettyContext.class) {
				if(quartzContext == null){
					quartzContext = new NettyContext(); 
				}
			}
		}
		
		return quartzContext;
	}
	
	
	private int port;
	
	private volatile boolean init = false;

	public boolean isInit() {
		return init;
	}

	public void hasInit() {
		this.init = true;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	

}


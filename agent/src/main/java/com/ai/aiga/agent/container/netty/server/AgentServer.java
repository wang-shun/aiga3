package com.ai.aiga.agent.container.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName: DiscardServer
 * @author: taoyf
 * @date: 2017年4月27日 上午11:16:27
 * @Description:
 * 
 */
public class AgentServer {

	private static final Logger logger = LoggerFactory.getLogger(AgentServer.class);

	private int port;

	EventLoopGroup bossGroup = null;
	EventLoopGroup workerGroup = null;

	ChannelFuture channelFuture = null;

	public AgentServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		
		try{
			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new AgentServerInitializer());

			// Bind and start to accept incoming connections.
			channelFuture = b.bind(port).sync();
			logger.info("服务启动, 开始监听!");
		}catch (Exception e) {
			stop();
			throw e;
		}

	}

	public void stop() {

		try {
			if (channelFuture != null) {
				channelFuture.channel().closeFuture().sync();
			}
		} catch (InterruptedException e) {
			logger.error("关闭失败!", e);
		} finally {
			if (workerGroup != null) {
				workerGroup.shutdownGracefully();
			}
			if (bossGroup != null) {
				bossGroup.shutdownGracefully();
			}
		}
	}

}

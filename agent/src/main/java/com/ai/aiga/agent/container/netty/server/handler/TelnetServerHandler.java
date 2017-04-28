/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ai.aiga.agent.container.netty.server.handler;

import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.agent.sh.Command;
import com.ai.aiga.agent.sh.ProcessHandler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles a server-side channel.
 */
@Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger logger = LoggerFactory.getLogger(TelnetServerHandler.class);

	private Command command = new Command();

//	ConcurrentHashMap<ChannelHandlerContext, ProcessHandler> ProcessHandlerMap = new ConcurrentHashMap<ChannelHandlerContext, ProcessHandler>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// Send greeting for a new connection.
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
		ctx.write("It is " + new Date() + " now.\r\n");
		ctx.flush();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("channelUnregistered");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
		// Generate and write a response.
		String response = null;
		boolean close = false;
		if (request.isEmpty()) {
			response = "Please type something.\r\n";
		} else if ("bye".equals(request.toLowerCase())) {
			response = "Have a good day!\r\n";
			close = true;
//		} else if ("bash".equals(request.toLowerCase())) {
//			ProcessHandler baseHandler = command.executeInBase();
//			if (baseHandler != null) {
//				ProcessHandlerMap.put(ctx, baseHandler);
//				response = "base is ready!\r\n";
//			} else {
//				response = "can't open the /bin/base!\r\n";
//			}
		} else {

			logger.info("命令 : " + request);

//			ProcessHandler baseHandler = ProcessHandlerMap.get(ctx);
//			if (baseHandler == null) {
				response = command.executeReturn(request);
//			} else {
//				response = baseHandler.execute(request);
//				if ("exit".equals(request.toLowerCase())) {
//					this.destroyBaseHandler(ctx);
//				}
//			}

			if (response == null) {
				response = "I don't understand the order!\r\n";
			}

		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(response);

		ChannelFuture future = ctx.write(sb.toString());

		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * @ClassName: TelnetServerHandler :: destroyBaseHandler
	 * @author: taoyf
	 * @date: 2017年4月27日 下午5:11:02
	 *
	 * @Description:
	 * @param ctx          
	 */
	private void destroyBaseHandler(ChannelHandlerContext ctx) {
//		ProcessHandler baseHandler = ProcessHandlerMap.remove(ctx);
//		if(baseHandler != null){
//			baseHandler.destroy();
//		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.info("exceptionCaught");
		destroyBaseHandler(ctx);
		cause.printStackTrace();
		ctx.close();
	}
}

package com.ai.aiga.agent.sh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName: ProcessHandler
 * @author: taoyf
 * @date: 2017年4月27日 下午4:50:45
 * @Description:
 * 
 */
public class ProcessHandler {
	
	private Process process;
	private PrintWriter out;
	private BufferedReader in;
	

	public ProcessHandler(Process process){
		this.process = process;
		
		init();
	}

	/**
	 * @ClassName: ProcessHandler :: init
	 * @author: taoyf
	 * @date: 2017年4月27日 下午5:02:20
	 *
	 * @Description:          
	 */
	private void init() {
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())), true);
		in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
	
	
	public String execute(String cmd){
		out.println(cmd);
		return readReturn();
	}
	
	private String readReturn(){
		
		StringBuilder stb = new StringBuilder();
		try {
			String str = null;
			while ((str = in.readLine()) != null) {
				stb.append(str);
				stb.append("\r\n");
			}

			return stb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	public void destroy(){
		if(process != null){
			process.destroy();
		}
		
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
		
		if (out != null) {
			out.close();
		}
	}
	

}


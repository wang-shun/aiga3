package com.ai.aiga.agent.sh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {
	
	public static final String BASE = "/bin/bash";

	/**
	 * 暂时只支持简命令
	 * @ClassName: Command :: executeReturn
	 * @author: taoyf
	 * @date: 2017年4月28日 上午9:44:48
	 *
	 * @Description:
	 * @param cmdStr
	 * @return
	 */
	public String executeReturn(String cmdStr) {

		if (cmdStr == null || cmdStr.trim().length() == 0) {
			return "";
		}

		StringBuilder stb = new StringBuilder();
		Runtime run = Runtime.getRuntime();
		BufferedReader br = null;
		
		Process process = null;
		try {
			process = run.exec(cmdStr);

			// 将调用结果打印到控制台上
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String str = null;
			while ((str = br.readLine()) != null) {
				stb.append(str);
				stb.append("\r\n");
			}

			return stb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage() + "\r\n";
		} finally {
			if(process != null){
				process.destroyForcibly();
			}
			
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
//	public ProcessHandler executeInBase(){
//		Process proc = null;
//		try {
//			proc = Runtime.getRuntime().exec("/bin/bash");
//			return new ProcessHandler(proc);
//		} catch (IOException e) {
//		} catch (InterruptedException e) {
//		}
//		return null;
//	}


	public static void main(String[] args) throws IOException, InterruptedException {

	}

}

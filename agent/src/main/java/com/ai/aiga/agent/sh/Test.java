package com.ai.aiga.agent.sh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @ClassName: Test
 * @author: taoyf
 * @date: 2017年4月27日 下午4:47:11
 * @Description:
 * 
 */
public class Test {
	
	public static void main(String[] args) {
		
		File wd = new File("/bin");
		System.out.println(wd);
		Process proc = null;
		try {
		   proc = Runtime.getRuntime().exec("/bin/bash", null, wd);
		}
		catch (IOException e) {
		   e.printStackTrace();
		}
		if (proc != null) {
		   BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		   PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
		   out.println("cd ..");
		   out.println("pwd");
		   out.println("exit");
		   try {
		      String line;
		      while ((line = in.readLine()) != null) {
		         System.out.println(line);
		      }
		      proc.waitFor();
		      in.close();
		      out.close();
		      proc.destroy();
		   }
		   catch (Exception e) {
		      e.printStackTrace();
		   }
		}
		
	}

}


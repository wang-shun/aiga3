package com.ai.am.core.datasource;

import java.util.Stack;


public class DatabaseContextHolder {
	
	public static final String DEFAULT_BASE = "AI_DEFAULT_BASE_T";

	private static final ThreadLocal<Stack<String>> DbnameHolder = new ThreadLocal<Stack<String>>(){
		@Override
		protected Stack<String> initialValue() {
			return new Stack<String>();
		}
	};

	/**
	 * @author: taoyf
	 * @date: 2017年5月4日 下午8:48:54
	 * @Description: 压入栈
	 * @param dbname
	 */
	public static void setDbName(String dbname) {
		Stack<String> stack = DbnameHolder.get();
		if(dbname == null){
			stack.push(DEFAULT_BASE);
		}else{
			stack.push(dbname);
		}
	}

	public static String getDbName() {
		Stack<String> stack = DbnameHolder.get();
		if(!stack.isEmpty()){
			return stack.peek();
		}else{
			return null;
		}
	}

	public static void clearDbName() {
		Stack<String> stack = DbnameHolder.get();
		if(!stack.isEmpty()){
			stack.pop();
		}
	}

}
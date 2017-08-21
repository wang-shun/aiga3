package com.ai.aiga.exception;

import org.apache.commons.lang3.StringUtils;

public enum ErrorCode {
	
	Unknown("com0000", "未知错误!"),
	Parameter_null("com0001", "请求参数 [{0}] 未传"),
	Parameter_invalid("com0002", "请求参数 [{0}] 不符合规范"),
	Parameter_com_null("com0003", "请求参数未传"),
	Parameter_only("com0004", "请求参数 [{0}]已存在"),
	

	BAD_REQUEST("400", "无法找到您要的资源"), 
	UNAUTHORIZED("401", "对不起, 您无法访问该资源"), 
	INTERNAL_SERVER_ERROR("500", "出现无法预知的错误");


	public String code;
	public String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getMessageAndCompletion(String... strings){
		if(strings == null){
			return this.message;
		}else{
			String tmp = this.message;
			for(int i = 0; i < strings.length; i++){
				String one = strings[i];
				tmp = StringUtils.replace(tmp, "{" + i + "}", one);
			}
			return tmp;
		}
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(ErrorCode.Parameter_null.getMessageAndCompletion("code"));
		
	}

}

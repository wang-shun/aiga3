package com.ai.aiga.exception;

public enum ErrorCode {

	BAD_REQUEST(400, "无法找到您要的资源"), 
	UNAUTHORIZED(401, "对不起, 您无法访问该资源"), 
	INTERNAL_SERVER_ERROR(500, "出现无法预知的错误");


	public int code;
	public String message;

	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

}

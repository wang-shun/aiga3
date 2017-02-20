package com.ai.aiga.exception;

public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242111829918405178L;


	public ErrorCode errorCode;

	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

}

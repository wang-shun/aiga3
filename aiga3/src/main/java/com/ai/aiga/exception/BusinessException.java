package com.ai.aiga.exception;

public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242111829918405178L;


	public ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode, String... message) {
		super(errorCode.getMessageAndCompletion(message));
		this.errorCode = errorCode;
	}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public static void throwBusinessException(ErrorCode errorCode, String... message){
		throw new BusinessException(errorCode, message);
	}
	
	public static void throwBusinessException(String message){
		throw new BusinessException(message);
	}
	


}

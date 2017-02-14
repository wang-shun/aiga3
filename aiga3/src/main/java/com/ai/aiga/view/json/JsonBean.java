package com.ai.aiga.view.json;

public class JsonBean {
	
	private String code = "success";
	private String message = "success";
	
	private Object bean;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}
	
	/**
	 * 根据失败原因调用该方法
	 * @param reason
	 */
	public void fail(String reason){
		this.setCode("fail");
		this.setMessage(reason);
		this.bean = null;
	}
	
	/**
	 * 根据失败原因调用该方法
	 * @param reason
	 */
	public void success(String reason){
		this.setCode("success");
		this.setMessage(reason);
	}
	
	/**
	 * 根据失败原因调用该方法
	 * @param reason
	 */
	public void fail4hasLogout(){
		this.setCode("logout");
		this.bean = null;
	}
}

package com.ai.aiga.dao.jpa;

/**
 * @ClassName: Parameter
 * @author: taoyf
 * @date: 2017年4月19日 下午3:11:57
 * @Description:
 * 
 */
public class ParameterCondition {
	
	private String name;
	private Object val;
	
	/**
	 * 
	 */
	public ParameterCondition() {
	}
	/**
	 * @param name
	 * @param val
	 */
	public ParameterCondition(String name, Object val) {
		this.name = name;
		this.val = val;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	
	

}


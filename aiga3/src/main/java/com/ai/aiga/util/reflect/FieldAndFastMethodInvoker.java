package com.ai.aiga.util.reflect;

/**
 * @ClassName: FieldAndFastMethodInvoker
 * @author: taoyf
 * @date: 2017年4月18日 下午3:42:47
 * @Description:
 * 
 */
public class FieldAndFastMethodInvoker{
	
	private FastMethodInvoker fastMethodInvoker;
	private String filedName;
	private Class fieldType;
	
	public FieldAndFastMethodInvoker() {
	}
	
	/**
	 * @param fastMethodInvoker
	 * @param fieldType
	 */
	public FieldAndFastMethodInvoker(FastMethodInvoker fastMethodInvoker, String filedName, Class fieldType) {
		this.fastMethodInvoker = fastMethodInvoker;
		this.filedName = filedName;
		this.fieldType = fieldType;
	}
	
	public FastMethodInvoker getFastMethodInvoker() {
		return fastMethodInvoker;
	}
	public void setFastMethodInvoker(FastMethodInvoker fastMethodInvoker) {
		this.fastMethodInvoker = fastMethodInvoker;
	}
	
	public Class getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class fieldType) {
		this.fieldType = fieldType;
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	
}


package com.ai.am.core.transaction;

import org.springframework.transaction.support.ResourceHolderSupport;

/**
 * @ClassName: ResourceHolder
 * @author: taoyf
 * @date: 2017年5月5日 下午5:37:29
 * @Description:
 * 
 */
public class ResourceHolder {
	
	private ResourceHolderSupport resourceHolder;
	
	private String name;
	
	private boolean newResource;
	
	private Object data;
	
	private Object DataSource;
	
	private boolean error = false;

	public ResourceHolderSupport getResourceHolder() {
		return resourceHolder;
	}

	public void setResourceHolder(ResourceHolderSupport resourceHolder) {
		this.resourceHolder = resourceHolder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNewResource() {
		return newResource;
	}

	public void setNewResource(boolean newResource) {
		this.newResource = newResource;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getDataSource() {
		return DataSource;
	}

	public void setDataSource(Object dataSource) {
		DataSource = dataSource;
	}

	
}


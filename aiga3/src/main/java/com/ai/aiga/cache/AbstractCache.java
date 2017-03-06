package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

//暂时用map实现, 等下个阶段考虑用ehcache实现.
public abstract class AbstractCache implements Icache, InitializingBean{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private Map cacheMap;
	
	protected abstract Map load();
	
	public void afterPropertiesSet() throws Exception{
		reload();
	}
	
	public final void reload(){
		cacheMap = load();
	}
	
	public Object getValue(Object key){
		return cacheMap.get(key);
	}
	
	public final Map getAll(){
		return cacheMap;
	}
}

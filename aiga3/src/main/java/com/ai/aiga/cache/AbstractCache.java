package com.ai.aiga.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

//暂时用map实现, 等下个阶段考虑用ehcache实现.
public abstract class AbstractCache implements Icache, InitializingBean{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private Object monitor = new Object();
	
	private Map cacheMap;
	
	protected abstract Map load();
	
	@Value("${app.cache.init}")
	private String init;
	
	public void afterPropertiesSet() throws Exception{
		if(Boolean.TRUE.toString().equalsIgnoreCase(init)){
			reload();
		}
	}
	
	public final void reload(){
		cacheMap = load();
	}
	
	public Object getValue(Object key){
		if(cacheMap == null){
			synchronized (monitor) {
				if(cacheMap == null){
					reload();
				}
			}
		}
		
		return cacheMap.get(key);
	}
	
	public final Map getAll(){
		if(cacheMap == null){
			synchronized (monitor) {
				if(cacheMap == null){
					reload();
				}
			}
		}
		
		return cacheMap;
	}
	
	
}

package com.ai.aiga.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//暂时用map实现, 等下个阶段考虑用ehcache实现.
public abstract class AbstractCache implements Icache{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private Map cacheMap = structureMap();
	
	protected Map structureMap(){
		return new HashMap();
	}
	protected abstract void load();
	
	public final void reload(){
		cacheMap = structureMap();
		load();
	}
	
	public Object getValue(Object key){
		return cacheMap.get(key);
	}
	
	public final Map getAll(){
		return cacheMap;
	}
}
